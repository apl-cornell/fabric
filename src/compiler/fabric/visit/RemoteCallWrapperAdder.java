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
import polyglot.util.InternalCompilerError;
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
          
          String cm_fmt = "%T " + md.name() + "_remote(%LF) { %LS }";
          
          List<Formal> formals = new ArrayList<Formal>(md.formals().size() + 1);
          formals.add(createWorkerFormal());
          formals.addAll(md.formals());

          String call_fmt = "%s(%LE);";
          List<Expr> args = new ArrayList<Expr>(md.formals().size());
          for (Formal formal : (List<Formal>)md.formals()) {
            Local l = nf.Local(Position.compilerGenerated(md.name() + " " + formal.name()), formal.id());
            args.add(l);
          }
          Stmt call = qq.parseStmt(call_fmt, md.name(), args);
          Stmt thrw = qq.parseStmt("throw new NullPointerException();");
          List<Stmt> stmts = new LinkedList<Stmt>();
          stmts.add(call);
          stmts.add(thrw);
          
          List<TypeNode> throwTypes = new ArrayList<TypeNode>(md.throwTypes().size() + 1);
          throwTypes.addAll(md.throwTypes());

          ClassMember newCM = qq.parseMember(cm_fmt, md.returnType(), formals, stmts);
          if(newCM instanceof JifMethodDecl) {
            JifMethodDecl rmd = (JifMethodDecl) newCM;
            rmd = (JifMethodDecl) rmd.flags(md.flags()); //is this necessary?
            rmd = (JifMethodDecl) rmd.throwTypes(md.throwTypes());
            rmd = rmd.startLabel(md.startLabel());
            rmd = rmd.returnLabel(md.returnLabel());
            rmd = rmd.constraints(md.constraints());
            members.add(rmd);
          }
          else throw new InternalCompilerError("Error creating remote wrapper." + newCM.getClass());
        }
      }

      return cd.body(nf.ClassBody(cd.body().position(), members));
    }

    return n;
  }
  
  protected Formal createWorkerFormal() {
    List<LabelComponentNode> components = new ArrayList<LabelComponentNode>(2);
    List<PrincipalNode> writers = new ArrayList<PrincipalNode>();

    writers.add(nf.CanonicalPrincipalNode(Position.compilerGenerated(), 
                ts.topPrincipal(Position.compilerGenerated())));
    PolicyNode writer = 
      nf.WriterPolicyNode(Position.compilerGenerated(),
                          nf.CanonicalPrincipalNode(Position.compilerGenerated(), 
                                                    ts.topPrincipal(Position.compilerGenerated())),
                          writers);
    components.add(writer);

    TypeNode formalType = 
      nf.LabeledTypeNode(Position.compilerGenerated(), 
                         nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Principal()),
                         nf.JoinLabelNode(Position.compilerGenerated(), components));
    return nf.Formal(Position.compilerGenerated(), 
                         Flags.FINAL,
                         formalType, 
                         nf.Id(Position.compilerGenerated(), "worker$principal"));
  }
}
