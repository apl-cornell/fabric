package fabric.visit;

import java.util.*;

import jif.ast.*;
import jif.extension.JifBinaryDel;
import jif.types.*;
import jif.types.label.*;
import jif.types.principal.Principal;
import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;
import polyglot.ast.*;
import polyglot.frontend.Job;
import polyglot.qq.QQ;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class RemoteCallWrapperUpdater extends NodeVisitor {
  protected Job job;
  protected FabricTypeSystem ts;
  protected FabricNodeFactory nf;
  protected QQ qq;

  public RemoteCallWrapperUpdater(Job job, FabricTypeSystem ts, FabricNodeFactory nf) {
    this.job = job;
    this.ts = ts;
    this.nf = nf;
    this.qq = new QQ(job.extensionInfo());
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof JifMethodDecl) {
      // Now add wrappers for remote call authentication check.
      JifMethodDecl md = (JifMethodDecl)n;

      if (md.name().endsWith("_remote")) {
        JifMethodInstance mi = (JifMethodInstance)md.methodInstance();

        Formal f = (Formal)md.formals().get(0);
//        Formal lbf = (Formal)md.formals().get(1);
        LocalInstance li = f.localInstance();

        Principal clientPrincipal;

        try {
          AccessPath ap = JifUtil.varInstanceToAccessPath(li, li.position());
          clientPrincipal = ts.dynamicPrincipal(ap.position(), ap);
        }
        catch (SemanticException e) {
          throw new InternalCompilerError(e);
        }

        //        Principal clientPrincipal = ts.clientPrincipal(Position.compilerGenerated());

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

//        // (rv meet {conf.top;integ.bot}) join ({client$<-}) <= ({client$->}) join (m meet {conf.bot;integ.top})
//        Label left = ts.join(
//            ts.meet(
//                returnLabel,
//                ts.pairLabel(Position.compilerGenerated(),
//                    ts.topConfPolicy(Position.compilerGenerated()), 
//                    ts.bottomIntegPolicy(Position.compilerGenerated()))),
//                    ts.pairLabel(Position.compilerGenerated(),
//                        ts.bottomConfPolicy(Position.compilerGenerated()),
//                        ts.writerPolicy(Position.compilerGenerated(), clientPrincipal, clientPrincipal)));
//
//        Label right = ts.join(
//            ts.meet(
//                startLabel,
//                ts.pairLabel(Position.compilerGenerated(),
//                    ts.bottomConfPolicy(Position.compilerGenerated()), 
//                    ts.topIntegPolicy(Position.compilerGenerated()))),
//                    ts.pairLabel(Position.compilerGenerated(),
//                        ts.readerPolicy(Position.compilerGenerated(), clientPrincipal, clientPrincipal),
//                        ts.bottomIntegPolicy(Position.compilerGenerated())));

        // Fold in all integrity policies of method parameters.
        IntegPolicy startIntegPolicy = ts.integProjection(startLabel);
        Iterator<Type> it = ((List<Type>)mi.formalTypes()).iterator();
        it.next();
        while (it.hasNext()) {
          Type ft = it.next();
          Label l = ts.labelOfType(ft);
          IntegPolicy ip = ts.integProjection(l);
          startIntegPolicy = ts.meet(startIntegPolicy, ip);
        }
        
        // {C(rv), *<-client$} <= {*->client$, I(m)}
        // XXX Note, it is better NOT to blindly insert the projection translation, if not needed.
        Label left = ts.pairLabel(Position.compilerGenerated(md.name()), 
                                  ts.confProjection(returnLabel), 
                                  ts.writerPolicy(Position.compilerGenerated(), 
                                                  ts.topPrincipal(Position.compilerGenerated()), 
                                                  clientPrincipal));
        Label right = ts.pairLabel(Position.compilerGenerated(), 
                                   ts.readerPolicy(Position.compilerGenerated(), 
                                                   ts.topPrincipal(Position.compilerGenerated()), 
                                                   clientPrincipal), 
                                   startIntegPolicy);

        LabelExpr leftExpr = nf.LabelExpr(left.position(), left);
        LabelExpr rightExpr = nf.LabelExpr(right.position(), right);

        Expr labelComp = nf.Binary(Position.compilerGenerated(), leftExpr, Binary.LE, rightExpr);

        //          List<Expr> args = new ArrayList<Expr>(md.formals().size());
        //          for (Formal formal : (List<Formal>)md.formals()) {
        //            Local l = nf.Local(Position.compilerGenerated(), formal.id());
        //            l = l.localInstance(formal.localInstance());
        //            args.add(l);
        //          }
        
        for (Assertion as : (List<Assertion>)mi.constraints()) {
          if (as instanceof CallerConstraint) {
            CallerConstraint cc = (CallerConstraint)as;
            for (Principal p : (List<Principal>)cc.principals()) {
              Expr check = 
                nf.Binary(as.position(), 
                          nf.CanonicalPrincipalNode(clientPrincipal.position(), clientPrincipal), 
                          JifBinaryDel.ACTSFOR, 
                          nf.CanonicalPrincipalNode(p.position(), p));
              labelComp = nf.Binary(Position.compilerGenerated(), labelComp, Binary.COND_AND, check);
            }
          }
          // TODO other constraints in the where clause of the method.
        }
        
        Eval eval = (Eval)md.body().statements().get(0);

        Stmt conseq;

        if (mi.returnType().isVoid()) {
          conseq = eval;
        }
        else {
          conseq = nf.Return(eval.position(), eval.expr());
        }

//        Stmt t = qq.parseStmt("try { _npe(lbl); %S; } catch (NullPointerException e) {}", s);

        Stmt s = nf.If(Position.compilerGenerated(), labelComp, conseq, conseq);
        
//        Call npecall = nf.Call(Position.compilerGenerated(),
//            nf.TypeNodeFromQualifiedName(Position.compilerGenerated(), "fabric.lang.Object"),
//            nf.Id(Position.compilerGenerated(), "_npe"));
//        ClassType objtype = null;
//        LabeledTypeNode npe = null;
////        Type npetype = null;
//        Type npetype = ts.NullPointerException();
        
//        try {
//          objtype = (ClassType) ts.typeForName("fabric.lang.Object");          
//          npetype = ts.typeForName("java.lang.NullPointerException");
//        } catch (SemanticException e1) {
//          e1.printStackTrace();
//        } 
//        MethodInstance npemi = (MethodInstance) objtype.methodsNamed("_npe").get(0);
//        npecall = npecall.methodInstance(npemi);
//        Local npearg = nf.Local(Position.compilerGenerated(), lbf.id());
//        npearg = npearg.localInstance(lbf.localInstance());
//        npecall = (Call) npecall.arguments(Collections.singletonList(npearg));

//        Label unknownLabel = ts.freshLabelVariable(Position.compilerGenerated(), "np", "The label of the exception");
//        npe = nf.LabeledTypeNode(Position.compilerGenerated(), 
//            nf.CanonicalTypeNode(Position.compilerGenerated(), npetype),
//            nf.AmbDynamicLabelNode(Position.compilerGenerated(), nf.LabelExpr(Position.compilerGenerated(), unknownLabel)));
//        LabeledType npeltype = ts.labeledType(Position.compilerGenerated(), npetype, unknownLabel);

//        JifLocalInstance npeli = (JifLocalInstance) ts.localInstance(Position.compilerGenerated(), Flags.NONE, npeltype, "e");
//        npeli = (JifLocalInstance) npeli.type(npeltype);
//        npeli.setLabel(unknownLabel);

//        Formal npeformal = nf.Formal(Position.compilerGenerated(), Flags.NONE, npe, nf.Id(Position.compilerGenerated(), "e"));
//        npeformal = npeformal.localInstance(npeli);

//        Try t = nf.Try(Position.compilerGenerated(), 
//            nf.Block(Position.compilerGenerated(), nf.Eval(Position.compilerGenerated(), npecall), s),
//            Collections.singletonList(
//                nf.Catch(Position.compilerGenerated(),
//                    npeformal,
//                    nf.Block(Position.compilerGenerated(md.name())))));

        md = (JifMethodDecl)md.body(md.body().statements(Collections.singletonList(s)));
      }

      return md;
    }

    return n;
  }
}
