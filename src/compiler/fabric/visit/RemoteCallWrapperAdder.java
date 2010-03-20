package fabric.visit;

import java.util.*;

import jif.ast.*;
import jif.types.principal.Principal;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;
import polyglot.ast.*;
import polyglot.frontend.Job;
import polyglot.qq.QQ;
import polyglot.types.Flags;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class RemoteCallWrapperAdder extends NodeVisitor {
  protected Job job;
  protected FabricTypeSystem ts;
  protected FabricNodeFactory nf;
  protected QQ qq;

  public RemoteCallWrapperAdder(Job job, FabricTypeSystem ts, FabricNodeFactory nf) {
    this.job = job;
    this.ts = ts;
    this.nf = nf;
    this.qq = new QQ(job.extensionInfo());
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
//    if (n instanceof FabricCall) {
//      FabricCall c = (FabricCall)n;
//      if (c.remoteWorker() != null) {
//        c = (FabricCall)c.name(c.name() + "_remote");
//
//        List<Expr> args = new ArrayList<Expr>();
//        args.add(nf.Worker(Position.compilerGenerated()));
//        args.addAll(c.arguments());
//        c = (FabricCall)c.arguments(args);
//      }
//
//      return c;
//    }
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
          // Also skip abstract method.
          if (md.body() == null) continue;
          
//          // If the first argument of the method isn't a label, then this method cannot be
//          // called remotely
//          if(md.formals().size()<=0) continue;
//          Formal firstFormal = (Formal) md.formals().get(0);
//          LabeledTypeNode typeNode = (LabeledTypeNode) firstFormal.type();
//          Type formalType = typeNode.typePart().type(); 
//          if(formalType == null || !formalType.equals(ts.Label())) continue;

          Principal workerPrincipal = ts.workerPrincipal(Position.compilerGenerated(md.position().toString()));

          // Send the pc label as a runtime method parameter

          // TypeNode for the pc label and the principal parameter
          
// Not adding a separate parameter: assuming the first parameter is enough
//          LabelNode pclbl = nf.AmbDynamicLabelNode(Position.compilerGenerated(),
//              nf.Local(Position.compilerGenerated(), nf.Id(Position.compilerGenerated(), "pcLabel"))); 
//          TypeNode pcfType = nf.LabeledTypeNode(Position.compilerGenerated(), 
//              nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Label()),
//              pclbl);
//          Formal pcf = nf.Formal(Position.compilerGenerated(md.name()), 
//              Flags.NONE,
//              pcfType,
//              nf.Id(Position.compilerGenerated(), "pcLabel"));

          List<PolicyNode> components = new ArrayList<PolicyNode>(2);
//          PolicyNode reader = 
//            nf.ReaderPolicyNode(Position.compilerGenerated(), 
//                                nf.AmbPrincipalNode(Position.compilerGenerated(), 
//                                    nf.Id(Position.compilerGenerated(), "worker$principal")), 
//                                Collections.singletonList(nf.CanonicalPrincipalNode(Position.compilerGenerated(), 
//                                    ts.bottomPrincipal(Position.compilerGenerated()))));
//          PolicyNode writer = 
//            nf.WriterPolicyNode(Position.compilerGenerated(),
//                                nf.CanonicalPrincipalNode(workerPrincipal.position(), workerPrincipal),
//                                Collections.EMPTY_LIST);
          List<PrincipalNode> writers = new ArrayList<PrincipalNode>();
//          writers.add(nf.CanonicalPrincipalNode(workerPrincipal.position(), 
//                                                workerPrincipal));
//          writers.add(nf.AmbPrincipalNode(Position.compilerGenerated(), 
//                                          nf.Id(Position.compilerGenerated(), "worker$principal")));
          writers.add(nf.CanonicalPrincipalNode(Position.compilerGenerated(md.position().toString()), 
                      ts.topPrincipal(Position.compilerGenerated(md.position().toString()))));
          PolicyNode writer = 
            nf.WriterPolicyNode(Position.compilerGenerated(md.position().toString()),
                                nf.CanonicalPrincipalNode(Position.compilerGenerated(md.position().toString()), 
                                                          ts.topPrincipal(Position.compilerGenerated(md.position().toString()))),
                                writers);
//          components.add(reader);
          components.add(writer);

          TypeNode formalType = 
            nf.LabeledTypeNode(Position.compilerGenerated(md.position().toString()), 
                               nf.CanonicalTypeNode(Position.compilerGenerated(md.position().toString()), ts.Principal()),
                               nf.JoinLabelNode(Position.compilerGenerated(md.position().toString()), components));
          Formal f = nf.Formal(Position.compilerGenerated(md.position().toString()), 
                               Flags.FINAL,
                               formalType, 
                               nf.Id(Position.compilerGenerated(md.position().toString()), "worker$principal"));
                    
//          LabelNode pclbl = nf.AmbDynamicLabelNode(Position.compilerGenerated(),
//              nf.Local(Position.compilerGenerated(), nf.Id(Position.compilerGenerated(), firstFormal.name())));
//          TypeNode prfType = nf.LabeledTypeNode(Position.compilerGenerated(), 
//              nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Principal()),
//              pclbl);
//          Formal prf = nf.Formal(Position.compilerGenerated(), 
//              Flags.NONE,
//              prfType, 
//              nf.Id(Position.compilerGenerated(), "worker$principal"));

          //          Label defaultBound = ts.defaultSignature().defaultArgBound(f);
          //          JifLocalInstance li = (JifLocalInstance)ts.localInstance(f.position(), Flags.FINAL, ts.Principal(), "worker$principal");
          //          ArgLabel al = ts.argLabel(f.position(), li, null);
          //          al.setUpperBound(defaultBound);
          //          li.setLabel(al);
          //          f = f.localInstance(li);
          //          Type labeled = ts.labeledType(Position.compilerGenerated(), ts.Principal(), al);
          //          f = f.type(f.type().type(labeled));

          //          Label startLabel = mi.pcBound();
          //          Label returnLabel = mi.returnLabel();

          //          Principal workerPrincipal;
          //          
          //          try {
          //            AccessPath ap = JifUtil.varInstanceToAccessPath(li, li.position());
          //            workerPrincipal = ts.dynamicPrincipal(ap.position(), ap);
          //          }
          //          catch (SemanticException e) {
          //            throw new InternalCompilerError(e);
          //          }
          //          
          //          Label workerLabel = 
          //            ts.pairLabel(li.position(), 
          //                         ts.readerPolicy(li.position(), 
          //                                         workerPrincipal, ts.bottomPrincipal(li.position())), 
          //                         ts.writerPolicy(li.position(), 
          //                                         workerPrincipal, workerPrincipal));
          //          li.setLabel(workerLabel);
          //          Type labeled = ts.labeledType(Position.compilerGenerated(), ts.Principal(), workerLabel);
          //          f = f.type(f.type().type(labeled));

          List<Formal> formals = new ArrayList<Formal>(md.formals().size() + 1);
//          formals.add(pcf);
//          formals.add(prf);
          formals.add(f);
          formals.addAll(md.formals());

          // {C(rv), worker$<-} <= {worker$->, I(m)}
          //          Label left = ts.pairLabel(Position.compilerGenerated(), 
          //                                    ts.confProjection(returnLabel), 
          //                                    ts.writerPolicy(Position.compilerGenerated(), 
          //                                                    workerPrincipal, workerPrincipal));
          //          Label right = ts.pairLabel(Position.compilerGenerated(), 
          //                                     ts.readerPolicy(Position.compilerGenerated(), 
          //                                                     workerPrincipal, workerPrincipal), 
          //                                     ts.integProjection(startLabel));
          //          LabelExpr leftExpr = nf.LabelExpr(left.position(), left);
          //          LabelExpr rightExpr = nf.LabelExpr(Position.compilerGenerated(), right);
          //          
          //          Expr labelComp = nf.Binary(Position.compilerGenerated(), leftExpr, Binary.LE, rightExpr);
          //
          List<Expr> args = new ArrayList<Expr>(md.formals().size());
          for (Formal formal : (List<Formal>)md.formals()) {
            Local l = nf.Local(Position.compilerGenerated(md.name() + " " + formal.name()), formal.id());
            args.add(l);
          }

          Stmt s1 = nf.Eval(Position.compilerGenerated(), 
                           nf.Call(Position.compilerGenerated(), 
                                   nf.This(Position.compilerGenerated()), 
                                   md.id(), args));
          
          Stmt s2 = qq.parseStmt("throw new java.lang.NullPointerException();");

//          List constraints = md.constraints();
//          List newConstraints = new ArrayList();
//          if(md.startLabel() != null) {
//            for(int i = 0; i < constraints.size(); i++) {
//              newConstraints.add(constraints.get(i));
//            }
//            // add a constraint for the pcLabel argument so that the actual method can be called
//            newConstraints.add(nf.LabelLeAssertionNode(Position.compilerGenerated(), pclbl, md.startLabel(), false));
//          } else {
//            newConstraints = constraints;
//          }
          
          List<TypeNode> throwTypes = new ArrayList<TypeNode>(md.throwTypes().size() + 1);
          throwTypes.addAll(md.throwTypes());
          
//          TypeNode retType = md.returnType();
//          if (retType instanceof LabeledTypeNode) {
//            retType = ((LabeledTypeNode)retType).typePart();
//          }
//          
//          if (retType instanceof CanonicalTypeNode && ((CanonicalTypeNode)retType).type().isVoid()) {
//            s = qq.parseStmt("try { %S } catch (NullPointerException $e) {}", s);
//          }
//          else {
//            ClassType npeType = ts.NullPointerException();
//            TypeNode npeTypeNode = nf.CanonicalTypeNode(Position.compilerGenerated(), npeType);
//  //          npeTypeNode = nf.LabeledTypeNode(Position.compilerGenerated(), 
//  //                                           npeTypeNode, 
//  //                                           nf.CanonicalLabelNode(Position.compilerGenerated(), 
//  //                                                                 ts.topLabel()));
//            if (md.returnLabel() != null) {
//              npeTypeNode = nf.LabeledTypeNode(Position.compilerGenerated(), npeTypeNode, md.returnLabel());
//            }
//            throwTypes.add(npeTypeNode);
//          }
          
          JifMethodDecl wrapper = 
            nf.JifMethodDecl(Position.compilerGenerated(md.position().toString()), 
                             md.flags(), 
                             md.returnType(), 
                             nf.Id(Position.compilerGenerated(md.position().toString()), md.name() + "_remote"), 
                             md.startLabel(), 
                             formals, 
                             md.returnLabel(), 
                             throwTypes, 
                             md.constraints(), 
                             nf.Block(Position.compilerGenerated(md.position().toString()), s1, s2));

          members.add(wrapper);
        }
      }

      return cd.body(nf.ClassBody(cd.body().position(), members));
    }

    return n;
  }
}
