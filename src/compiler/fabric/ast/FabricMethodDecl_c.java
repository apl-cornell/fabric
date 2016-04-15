package fabric.ast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fabric.types.FabricDefaultSignature;
import fabric.types.FabricMethodInstance;
import fabric.types.FabricTypeSystem;

import jif.ast.ConstraintNode;
import jif.ast.JifMethodDecl_c;
import jif.ast.LabelNode;
import jif.types.Assertion;
import jif.types.label.Label;

import polyglot.ast.Block;
import polyglot.ast.Ext;
import polyglot.ast.Formal;
import polyglot.ast.Id;
import polyglot.ast.Javadoc;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;

/** An implementation of the <code>FabricMethod</code> interface.
 */
// XXX Should be replaced with extension
@Deprecated
public class FabricMethodDecl_c extends JifMethodDecl_c implements FabricMethodDecl {
  //Carried over from JifMethodDecl_c
  private static final long serialVersionUID = SerialVersionUID.generate();

  protected LabelNode beginConflictLabel;
  protected LabelNode endConflictLabel;

  public FabricMethodDecl_c(Position pos, Flags flags, TypeNode returnType,
      Id name, LabelNode startLabel, LabelNode beginConflictLabel,
      List<Formal> formals, LabelNode returnLabel, LabelNode endConflictLabel,
      List<TypeNode> throwTypes, List<ConstraintNode<Assertion>> constraints,
      Block body, Javadoc javadoc) {
    this(pos, flags, returnType, name, startLabel, beginConflictLabel, formals,
        returnLabel, endConflictLabel, throwTypes, constraints, body, javadoc,
        null);
  }

  public FabricMethodDecl_c(Position pos, Flags flags, TypeNode returnType,
      Id name, LabelNode startLabel, LabelNode beginConflictLabel, List<Formal>
      formals, LabelNode returnLabel, LabelNode endConflictLabel,
      List<TypeNode> throwTypes, List<ConstraintNode<Assertion>> constraints,
      Block body, Javadoc javadoc, Ext ext) {
    super(pos, flags, returnType, name, startLabel, formals, returnLabel,
        throwTypes, constraints, body, javadoc, ext);
    this.beginConflictLabel = beginConflictLabel;
    this.endConflictLabel = endConflictLabel;
  }

  @Override
  public LabelNode beginConflictLabel() {
    return this.beginConflictLabel;
  }

  @Override
  public FabricMethodDecl beginConflictLabel(LabelNode beginConflictLabel) {
    return beginConflictLabel(this, beginConflictLabel);
  }

  protected <N extends FabricMethodDecl_c> N beginConflictLabel(N n,
      LabelNode beginConflictLabel) {
    if (n.beginConflictLabel == beginConflictLabel) return n;
    n = copyIfNeeded(n);
    n.beginConflictLabel = beginConflictLabel;
    return n;
  }

  @Override
  public LabelNode endConflictLabel() {
    return this.endConflictLabel;
  }

  @Override
  public FabricMethodDecl endConflictLabel(LabelNode endConflictLabel) {
    return endConflictLabel(this, endConflictLabel);
  }

  protected <N extends FabricMethodDecl_c> N endConflictLabel(N n,
    LabelNode endConflictLabel) {
    if (n.endConflictLabel == endConflictLabel) return n;
    n = copyIfNeeded(n);
    n.endConflictLabel = endConflictLabel;
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Id name = visitChild(this.name, v);
    TypeNode returnType = visitChild(this.returnType, v);
    LabelNode startLabel = visitChild(this.startLabel, v);
    LabelNode beginConflictLabel = visitChild(this.beginConflictLabel, v);
    List<Formal> formals = visitList(this.formals, v);
    LabelNode returnLabel = visitChild(this.returnLabel, v);
    LabelNode endConflictLabel = visitChild(this.endConflictLabel, v);
    List<TypeNode> throwTypes = visitList(this.throwTypes, v);
    List<ConstraintNode<Assertion>> constraints =
        visitList(this.constraints, v);
    Block body = visitChild(this.body, v);
    return reconstruct(this, name, returnType, startLabel, beginConflictLabel,
        formals, returnLabel, endConflictLabel, throwTypes, constraints, body);
  }

  protected <N extends FabricMethodDecl_c> N reconstruct(N n, Id name,
      TypeNode returnType, LabelNode startLabel, LabelNode beginConflictLabel,
      List<Formal> formals, LabelNode returnLabel, LabelNode endConflictLabel,
      List<TypeNode> throwTypes, List<ConstraintNode<Assertion>> constraints,
      Block body) {
    n = super.reconstruct(n, name, returnType, startLabel, formals, returnLabel,
        throwTypes, constraints, body);
    n = beginConflictLabel(n, beginConflictLabel);
    n = endConflictLabel(n, endConflictLabel);
    return n;
  }

  //TODO: Ugh, this is basically redoing the work of the JifMethodDecl_c's
  //disambiguate.  Both this and JifMethodDecl_c should be refactored.
  @Override
  public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
    //Unclear whether the arg renamer bit needs to be extended (which would
    //require changing the protection level of the original implementation...)
    //
    //I don't think this is the case, since it only handles argument names,
    //which we're not really doing anything interesting with.
    FabricMethodDecl n = (FabricMethodDecl) super.disambiguate(ar);

    FabricMethodInstance fmi = (FabricMethodInstance) n.methodInstance();
    FabricTypeSystem fts = (FabricTypeSystem) ar.typeSystem();
    FabricDefaultSignature fds = (FabricDefaultSignature) fts.defaultSignature();

    // set the formal types
    List<Type> formalTypes = new ArrayList<>(n.formals().size());
    List<Formal> formals = n.formals();
    for (Formal f : formals) {
      if (!f.isDisambiguated()) {
        // formals are not disambiguated yet.
        ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
        return this;
      }
      formalTypes.add(f.declType());
    }
    fmi.setFormalTypes(formalTypes);

    // return type
    if (!n.returnType().isDisambiguated()) {
      // return type node not disambiguated yet
      ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
      return this;
    }

    if (n.startLabel() != null && !n.startLabel().isDisambiguated()) {
      // the startlabel node hasn't been disambiguated yet
      ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
      return this;
    }

    Type declrt = n.returnType().type();
    if (!declrt.isVoid() && !fts.isLabeled(declrt)) {
      // return type isn't labeled. Add the default label.
      declrt =
          fts.labeledType(declrt.position(), declrt,
              fds.defaultReturnValueLabel(n));
      n = (FabricMethodDecl) n.returnType(n.returnType().type(declrt));
    }
    fmi.setReturnType(declrt);

    if (n.returnLabel() != null && !n.returnLabel().isDisambiguated()) {
      // the return label node hasn't been disambiguated yet
      ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
      return this;
    }

    Label Li; // pc bound for the method
    boolean isDefaultPCBound = false;
    if (n.startLabel() == null) {
      Li = fds.defaultPCBound(n.position(), n.name());
      isDefaultPCBound = true;
    } else {
      Li = n.startLabel().label();
    }
    fmi.setPCBound(Li, isDefaultPCBound);

    Label Lr; // return label
    boolean isDefaultReturnLabel = false;
    if (n.returnLabel() == null) {
      Lr = fds.defaultReturnLabel(n);
      isDefaultReturnLabel = true;
    } else {
      Lr = n.returnLabel().label();
    }
    fmi.setReturnLabel(Lr, isDefaultReturnLabel);

    // set the labels for the throwTypes.
    List<Type> newThrowTypes = new LinkedList<>();
    List<TypeNode> throwTypes = n.throwTypes();
    for (TypeNode tn : throwTypes) {
      if (!tn.isDisambiguated()) {
        // throw types haven't been disambiguated yet.
        ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
        return this;
      }

      Type xt = tn.type();
      if (!fts.isLabeled(xt)) {
        // default exception label is the return label
        xt = fts.labeledType(xt.position(), xt, Lr);
      }
      newThrowTypes.add(xt);
    }
    fmi.setThrowTypes(newThrowTypes);

    List<Assertion> constraints = new ArrayList<>(n.constraints().size());
    for (ConstraintNode<Assertion> cn : n.constraints()) {
      if (!cn.isDisambiguated()) {
        // constraint nodes haven't been disambiguated yet.
        ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
        return this;
      }
      constraints.addAll(cn.constraints());
    }
    fmi.setConstraints(constraints);

    if (n.beginConflictLabel() != null && !n.beginConflictLabel().isDisambiguated()) {
      // the beginConflictLabel node hasn't been disambiguated yet
      ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
      return this;
    }

    if (n.endConflictLabel() != null && !n.endConflictLabel().isDisambiguated()) {
      // the endConflictLabel node hasn't been disambiguated yet
      ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
      return this;
    }

    Label bal; // begin conflict label
    boolean isDefaultBeginConflict = false;
    if (n.beginConflictLabel() == null) {
      bal = fds.defaultBeginConflict(n);
      isDefaultBeginConflict = true;
    } else {
      bal = n.beginConflictLabel().label();
    }
    fmi.setBeginConflictLabel(bal, isDefaultBeginConflict);

    Label eal; // end conflict label
    boolean isDefaultEndConflict = false;
    if (n.endConflictLabel() == null) {
      eal = fds.defaultEndConflict(n);
      isDefaultEndConflict = true;
    } else {
      eal = n.endConflictLabel().label();
    }
    fmi.setEndConflictLabel(eal, isDefaultEndConflict);

    return n.methodInstance(fmi);
  }
}
