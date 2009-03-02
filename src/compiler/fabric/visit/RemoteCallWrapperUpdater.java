package fabric.visit;

import java.util.Collections;

import jif.ast.JifMethodDecl;
import jif.ast.JifUtil;
import jif.ast.LabelExpr;
import jif.types.JifMethodInstance;
import jif.types.label.AccessPath;
import jif.types.label.Label;
import jif.types.principal.Principal;
import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;
import polyglot.ast.*;
import polyglot.frontend.Job;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class RemoteCallWrapperUpdater extends NodeVisitor {
  protected Job job;
  protected FabricTypeSystem ts;
  protected FabricNodeFactory nf;

  public RemoteCallWrapperUpdater(Job job, FabricTypeSystem ts, FabricNodeFactory nf) {
    this.job = job;
    this.ts = ts;
    this.nf = nf;
  }
  
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof JifMethodDecl) {
      // Now add wrappers for remote call authentication check.
      JifMethodDecl md = (JifMethodDecl)n;
      
      if (md.name().endsWith("_remote")) {
        JifMethodInstance mi = (JifMethodInstance)md.methodInstance();
        
        Formal f = (Formal)md.formals().get(0);
        LocalInstance li = f.localInstance();

        Principal clientPrincipal;
        
        try {
          AccessPath ap = JifUtil.varInstanceToAccessPath(li, li.position());
          clientPrincipal = ts.dynamicPrincipal(ap.position(), ap);
        }
        catch (SemanticException e) {
          throw new InternalCompilerError(e);
        }
          
//          Label defaultBound = ts.defaultSignature().defaultArgBound(f);
//          JifLocalInstance li = (JifLocalInstance)ts.localInstance(f.position(), Flags.FINAL, ts.Principal(), "client$principal");
//          ArgLabel al = ts.argLabel(f.position(), li, null);
//          al.setUpperBound(defaultBound);
//          li.setLabel(al);
//          f = f.localInstance(li);
//          Type labeled = ts.labeledType(Position.compilerGenerated(), ts.Principal(), al);
//          f = f.type(f.type().type(labeled));
          
        Label startLabel = mi.pcBound();
        Label returnLabel = mi.returnLabel();
          
//          Label clientLabel = 
//            ts.pairLabel(li.position(), 
//                         ts.readerPolicy(li.position(), 
//                                         clientPrincipal, ts.bottomPrincipal(li.position())), 
//                         ts.writerPolicy(li.position(), 
//                                         clientPrincipal, clientPrincipal));
//          li.setLabel(clientLabel);
//          Type labeled = ts.labeledType(Position.compilerGenerated(), ts.Principal(), clientLabel);
//          f = f.type(f.type().type(labeled));

//          List<Formal> formals = new ArrayList<Formal>(md.formals().size() + 1);
//          formals.add(f);
//          formals.addAll(md.formals());
          
//          System.err.println(md);
//          System.err.println(mi.container());
//          System.err.println(startLabel);
//          System.err.println(returnLabel);
          
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

//          List<Expr> args = new ArrayList<Expr>(md.formals().size());
//          for (Formal formal : (List<Formal>)md.formals()) {
//            Local l = nf.Local(Position.compilerGenerated(), formal.id());
//            l = l.localInstance(formal.localInstance());
//            args.add(l);
//          }

        Eval eval = (Eval)md.body().statements().get(0);
        
        Stmt conseq;
        
        if (mi.returnType().isVoid()) {
          conseq = eval;
        }
        else {
          conseq = nf.Return(eval.position(), eval.expr());
        }
        
        Stmt s = nf.If(Position.compilerGenerated(), labelComp, conseq, conseq);

        md = (JifMethodDecl)md.body(md.body().statements(Collections.singletonList(s)));
      }
      
      return md;
    }
    
    return n;
  }
}
