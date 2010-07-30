package fabric.visit;

import java.util.*;

import jif.ast.*;
import jif.types.JifTypeSystem;
import jif.types.LabeledType;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathThis;
import jif.types.label.ConfPolicy;
import jif.types.label.ConfProjectionPolicy_c;
import jif.types.label.IntegPolicy;
import jif.types.label.JoinConfPolicy_c;
import jif.types.label.JoinIntegPolicy_c;
import jif.types.label.MeetConfPolicy_c;
import jif.types.label.PairLabel_c;
import jif.types.label.ReaderPolicy;
import jif.types.label.WriterPolicy;
import jif.types.label.Label;
import jif.types.principal.ConjunctivePrincipal;
import jif.types.principal.DisjunctivePrincipal;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;
import jif.types.principal.TopPrincipal;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricClassType;
import fabric.types.FabricDynamicPrincipal_c;
import fabric.types.FabricTypeSystem;
import polyglot.ast.*;
import polyglot.frontend.Job;
import polyglot.qq.QQ;
import polyglot.types.FieldInstance;
import polyglot.types.Flags;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.ErrorHandlingVisitor;
import polyglot.visit.NodeVisitor;

public class ThisLabelChecker extends ErrorHandlingVisitor {

  public ThisLabelChecker(Job job, FabricTypeSystem ts, FabricNodeFactory nf) {
    super(job,ts,nf);
  }
  
  // return true if p is {this←}
  private boolean isThisInteg(WriterPolicy p) {
      Principal owner = p.owner();
      Principal writer = p.writer();
      if (owner instanceof FabricDynamicPrincipal_c) {
          AccessPath path = ((FabricDynamicPrincipal_c)owner).path();
          if ((path instanceof AccessPathThis) && (writer instanceof TopPrincipal))
              return true;
      }
      return false;
  }
  
  private boolean hasThis(ConfPolicy p) throws SemanticException {
      
      if (p instanceof ConfProjectionPolicy_c) {
          throw new SemanticException("No projection policies allowed here");
      }
      
      if (p instanceof JoinConfPolicy_c) {
          Iterator ji = ((JoinConfPolicy_c)p).joinComponents().iterator();
          while (ji.hasNext()) {
              ConfPolicy cp = (ConfPolicy) ji.next();
              if (hasThis(cp)) return true;
          }
      }
      
      if (p instanceof MeetConfPolicy_c) {
          Iterator mi = ((MeetConfPolicy_c)p).meetComponents().iterator();
          while (mi.hasNext()) {
              ConfPolicy cp = (ConfPolicy) mi.next();
              if (hasThis(cp)) return true;
          }
      }
      
      if (p instanceof ReaderPolicy) {
        ReaderPolicy rp = (ReaderPolicy) p;
        Principal owner = rp.owner();
        Principal reader = rp.reader();
        if (hasThis(owner) || hasThis(reader)) return true;
      }
      
      return false;
  }
  
  private boolean hasThis(Principal p) {
    if (p instanceof DynamicPrincipal) {
      if (p instanceof FabricDynamicPrincipal_c) {
        AccessPath path = ((FabricDynamicPrincipal_c)p).path();
        if (path instanceof AccessPathThis) return true;
      }
      return false;
    }
    if (p instanceof ConjunctivePrincipal) {
        Iterator tokens = ((ConjunctivePrincipal)p).conjuncts().iterator();
        while (tokens.hasNext()) {
            Principal conp = (Principal) tokens.next();
            if (hasThis(conp)) return true;
        }
    }
    if (p instanceof DisjunctivePrincipal) {
        Iterator tokens = ((DisjunctivePrincipal)p).disjuncts().iterator();
        while (tokens.hasNext()) {
            Principal disp = (Principal) tokens.next();
            if (hasThis(disp)) return true;
        }
    }
    return false;      
  }

  @Override
  public Node leaveCall(Node old, Node n, NodeVisitor v) throws SemanticException {
    if (n instanceof ClassDecl) {
      ClassDecl cd = (ClassDecl)n;
      ParsedClassType pctype = cd.type();
      
      // XXX Should the subtype check be against Principal instead?
      if (pctype instanceof FabricClassType &&
              !pctype.isSubtype(((FabricTypeSystem)ts).DelegatingPrincipal())) {
          return n;
      }
      
      // If this is a DelegatingPrincipal class
      List members = cd.body().members();
      ArrayList<ClassMember> newMembers = new ArrayList<ClassMember>();
      for (int i = 0; i < members.size(); i++) {
          ClassMember member = (ClassMember) members.get(i);
          if (!(member instanceof FieldDecl)) {
              newMembers.add(member);
              continue;
          }
          
          FieldDecl fd = (FieldDecl)member;
          FieldInstance fi = fd.fieldInstance();
          Type type = fd.declType();
          if (type instanceof LabeledType) {
              LabeledType ltype = (LabeledType) type;
              Label fieldLabel = ltype.labelPart();
              IntegPolicy ip = fieldLabel.integProjection();
              ConfPolicy cp = fieldLabel.confProjection();
              
              if (hasThis(cp)) {
                  throw new SemanticException("Conf policy of field cannot mention 'this'");
              }
              
              IntegPolicy newPol = null;
              if (ip instanceof JoinIntegPolicy_c) {
                  HashSet<IntegPolicy> set = new HashSet<IntegPolicy>();
                  Iterator cit = ((JoinIntegPolicy_c)ip).joinComponents().iterator();
                  boolean thisInteg = false;
                  while (cit.hasNext()) {
                      IntegPolicy p = (IntegPolicy) cit.next();
                      if ((p instanceof WriterPolicy) && isThisInteg((WriterPolicy)p)) {
                          thisInteg = true;
                      } else {
                          set.add(p);
                      }
                  }
                  if (!thisInteg) 
                      throw new SemanticException("Integrity label is required to be of the form {this←} ⊔ L");
                  newPol = new JoinIntegPolicy_c(set, 
                          (JifTypeSystem) ip.typeSystem(), ip.position());
              }
              
              if (ip instanceof WriterPolicy) {
                  if (!isThisInteg((WriterPolicy)ip)) throw new SemanticException("Integrity label is required to be of the form {this←} ⊔ L");
                  newPol = ((FabricTypeSystem)ts).bottomIntegPolicy(Position.compilerGenerated());
              }
              
              if (newPol != null) {
                FabricTypeSystem ts = (FabricTypeSystem) fieldLabel.typeSystem();
                Label newLabel = ts.pairLabel(fieldLabel.position(), cp, newPol);
                LabeledType newType = ltype.labelPart(newLabel);
                FieldDecl newDecl = fd.type(fd.type().type(newType));
                fi.setType(newType);
                newDecl = newDecl.fieldInstance(fi);
                newMembers.add(newDecl);
                continue;
              }

              throw new SemanticException("Only join labels and simple writer policies allowed.");
          } else {
              throw new InternalCompilerError("Labeled Type not available");
          }
      }
      return cd.body(cd.body().members(newMembers));
    }
    return n;
  }
}
