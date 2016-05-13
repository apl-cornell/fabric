package fabric.visit;

import java.util.ArrayList;
import java.util.List;

import fabric.ast.FabricMethodDecl;
import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;

import jif.ast.ConstraintNode;
import jif.types.Assertion;

import polyglot.ast.Block;
import polyglot.ast.ClassDecl;
import polyglot.ast.Formal;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.frontend.Job;
import polyglot.qq.QQ;
import polyglot.types.Flags;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

/**
 * Adds a type specific implementation of the stageTxn utility method.
 */
public class StageTxnMethodAdder extends NodeVisitor {
  protected Job job;
  protected FabricTypeSystem ts;
  protected FabricNodeFactory nf;
  protected QQ qq;
  protected static final Position cg = Position.compilerGenerated();

  public static String STAGE_TXN_MD_NAME = "stageTxn";

  public StageTxnMethodAdder(Job job, FabricTypeSystem ts,
      FabricNodeFactory nf) {
    this.job = job;
    this.ts = ts;
    this.nf = nf;
    this.qq = new QQ(job.extensionInfo());
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof ClassDecl) {
      ClassDecl cd = (ClassDecl) n;

      // Don't add this to interfaces
      if (cd.flags().isInterface())
        return cd;

      // One argument, an unlabeled boolean.
      List<Formal> args = new ArrayList<>();
      args.add(nf.Formal(cg, Flags.NONE, nf.CanonicalTypeNode(cg, ts.Boolean()),
            nf.Id(cg, "b")));

      // Body: if (b) stage; return this;
      Block body = nf.Block(cg, nf.If(cg, nf.Local(cg, nf.Id(cg, "b")),
            nf.StageStmt(cg)), nf.Return(cg, nf.This(cg)));

      // Declaration:
      // public CLASS{this} stageTxnCLASS{*->}(boolean b):{_<-} { ... }
      FabricMethodDecl stageTxnMd =
        nf.FabricMethodDecl(cg, Flags.PUBLIC,
          nf.LabeledTypeNode(cg, nf.AmbTypeNode(cg, nf.Id(cg, cd.name())), nf.AmbThisLabelNode(cg)),
          nf.Id(cg, STAGE_TXN_MD_NAME + cd.name()), nf.CanonicalLabelNode(cg, ts.topLabel()), null,
          args, nf.CanonicalLabelNode(cg, ts.bottomLabel()), null,
          new ArrayList<TypeNode>(), new ArrayList<ConstraintNode<Assertion>>(),
          body, nf.Javadoc(cg, ""));

      stageTxnMd.prettyPrint(System.out);
      System.out.println();
      cd = cd.body(cd.body().addMember(stageTxnMd));
      return cd;
    }
    return n;
  }
}
