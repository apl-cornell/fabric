package fabric.visit;

import java.util.*;

import jif.ast.*;
import jif.types.JifMethodInstance;
import jif.types.label.AccessPath;
import jif.types.label.Label;
import jif.types.principal.Principal;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;
import polyglot.ast.*;
import polyglot.frontend.Job;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class RemoteCallWrapperAdder extends NodeVisitor {
  protected Job job;
  protected FabricTypeSystem ts;
  protected FabricNodeFactory nf;
  
  public RemoteCallWrapperAdder(Job job, FabricTypeSystem ts, FabricNodeFactory nf) {
    this.job = job;
    this.ts = ts;
    this.nf = nf;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof ClassDecl) {
      // Now add wrappers for remote call authentication check.
      ClassDecl cd = (ClassDecl)n;
      
      if (cd.flags().isInterface()) return cd;
      
      List<ClassMember> members = new ArrayList<ClassMember>();
      
      for (ClassMember cm : (List<ClassMember>)cd.body().members()) {
        members.add(cm);
        if (cm instanceof JifMethodDecl) {
          JifMethodDecl md = (JifMethodDecl)cm;
          if (!md.flags().isPublic() || md.flags().isStatic()) continue;
          
          JifMethodInstance mi = (JifMethodInstance)md.methodInstance();
          
          Formal f = nf.Formal(Position.compilerGenerated(), 
                               Flags.FINAL, 
                               nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Label()), 
                               nf.Id(Position.compilerGenerated(), "client$principal"));
          f = f.localInstance(ts.localInstance(f.position(), Flags.FINAL, ts.Label(), "client$principal"));
          
          List<Formal> formals = new ArrayList<Formal>(md.formals().size() + 1);
          formals.add(f);
          formals.addAll(md.formals());
          
          Label startLabel = mi.pcBound();
          Label returnLabel = mi.returnLabel();
          
          Principal clientPrincipal;
          
          try {
            AccessPath ap = JifUtil.varInstanceToAccessPath(f.localInstance(), f.position());
            clientPrincipal = ts.dynamicPrincipal(ap.position(), ap);
          }
          catch (SemanticException e) {
            throw new InternalCompilerError(e);
          }
          
          System.err.println(md);
          System.err.println(mi.container());
          System.err.println(startLabel);
          System.err.println(returnLabel);
          
          // {C(rv), client$<-} <= {client$->, I(m)}
          Label left = ts.pairLabel(Position.compilerGenerated(), 
                                    ts.confProjection(returnLabel), 
                                    ts.writerPolicy(Position.compilerGenerated(), 
                                                    clientPrincipal, clientPrincipal));
          Label right = ts.pairLabel(Position.compilerGenerated(), 
                                     ts.readerPolicy(Position.compilerGenerated(), 
                                                     clientPrincipal, clientPrincipal), 
                                     ts.integProjection(startLabel));
          LabelExpr leftExpr = nf.LabelExpr(left.position(), left);
          LabelExpr rightExpr = nf.LabelExpr(Position.compilerGenerated(), right);
          
          Expr labelComp = nf.Binary(Position.compilerGenerated(), leftExpr, Binary.LE, rightExpr);

          List<Expr> args = new ArrayList<Expr>(md.formals().size());
          for (Formal formal : (List<Formal>)md.formals()) {
            Local l = nf.Local(Position.compilerGenerated(), formal.id());
            l = l.localInstance(formal.localInstance());
            args.add(l);
          }
          
          Stmt conseq;
          if (mi.returnType().isVoid()) {
            conseq = nf.Eval(Position.compilerGenerated(), 
                             nf.Call(Position.compilerGenerated(), 
                                     nf.This(Position.compilerGenerated()), 
                                     md.id(), args));
          }
          else {
            conseq = nf.Return(Position.compilerGenerated(), 
                               nf.Call(Position.compilerGenerated(), 
                                       nf.This(Position.compilerGenerated()), 
                                       md.id(), args));
          }
          
          Stmt s = nf.If(Position.compilerGenerated(), labelComp, conseq, conseq);
          
          JifMethodDecl wrapper = 
            nf.JifMethodDecl(Position.compilerGenerated(), 
                             md.flags(), 
                             md.returnType(), 
                             nf.Id(Position.compilerGenerated(), md.name() + "$remote"), 
                             md.startLabel(), 
                             formals, 
                             md.returnLabel(), 
                             md.throwTypes(), 
                             md.constraints(), 
                             nf.Block(Position.compilerGenerated(), s));
          
          MethodInstance wrapperMi = (MethodInstance)mi.copy();
          List<Type> ftypes = new ArrayList<Type>(mi.formalTypes().size() + 1);
          ftypes.add(ts.Principal());
          ftypes.addAll(mi.formalTypes());
          wrapperMi.setFormalTypes(ftypes);
          
          wrapper = (JifMethodDecl)wrapper.methodInstance(wrapperMi);
          members.add(wrapper);
          
          cd.type().addMethod(wrapperMi);
        }
      }
      
      return cd.body(nf.ClassBody(cd.body().position(), members));
    }
    
    return n;
  }
}
