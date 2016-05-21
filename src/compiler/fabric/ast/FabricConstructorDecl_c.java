package fabric.ast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fabric.types.FabricConstructorInstance;
import fabric.types.FabricDefaultSignature;
import fabric.types.FabricTypeSystem;

import jif.ast.ConstraintNode;
import jif.ast.JifConstructorDecl_c;
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

/**
 * An implementation of the <code>JifConstructor</code> interface.
 */
// XXX should be replaced with extension
@Deprecated
public class FabricConstructorDecl_c extends JifConstructorDecl_c implements
FabricConstructorDecl {
  //Carried over from JifConstructorDecl_c
  private static final long serialVersionUID = SerialVersionUID.generate();

  protected LabelNode beginConflictLabel;
  protected LabelNode endConflictLabel;
//  @Deprecated
  public FabricConstructorDecl_c(Position pos, Flags flags, Id name,
      LabelNode startLabel, LabelNode beginConflictLabel, LabelNode returnLabel,
      List<Formal> formals, LabelNode endConflictLabel, List<TypeNode> throwTypes,
      List<ConstraintNode<Assertion>> constraints, Block body,
      Javadoc javadoc) {
    this(pos, flags, name, startLabel, beginConflictLabel, returnLabel, formals,
        endConflictLabel, throwTypes, constraints, body, javadoc, null);
  }

  public FabricConstructorDecl_c(Position pos, Flags flags, Id name,
      LabelNode startLabel, LabelNode beginConflictLabel, LabelNode returnLabel,
      List<Formal> formals, LabelNode endConflictLabel, List<TypeNode> throwTypes,
      List<ConstraintNode<Assertion>> constraints, Block body, Javadoc javadoc,
      Ext ext) {
    super(pos, flags, name, startLabel, returnLabel, formals, throwTypes,
        constraints, body, javadoc, ext);
    this.beginConflictLabel = beginConflictLabel;
    this.endConflictLabel = endConflictLabel;
  }

  @Override
  public LabelNode beginConflictLabel() {
    return this.beginConflictLabel;
  }

  @Override
  public FabricConstructorDecl beginConflictLabel(LabelNode beginConflictLabel) {
    return beginConflictLabel(this, beginConflictLabel);
  }

  protected <N extends FabricConstructorDecl_c> N beginConflictLabel(N n,
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
  public FabricConstructorDecl endConflictLabel(LabelNode endConflictLabel) {
    return endConflictLabel(this, endConflictLabel);
  }

  protected <N extends FabricConstructorDecl_c> N endConflictLabel(N n,
    LabelNode endConflictLabel) {
    if (n.endConflictLabel == endConflictLabel) return n;
    n = copyIfNeeded(n);
    n.endConflictLabel = endConflictLabel;
    return n;
  }

  protected <N extends FabricConstructorDecl_c> N reconstruct(N n, Id name,
      LabelNode startLabel, LabelNode beginConflictLabel, LabelNode returnLabel,
      List<Formal> formals, LabelNode endConflictLabel, List<TypeNode> throwTypes,
      List<ConstraintNode<Assertion>> constraints, Block body) {
    n = super.reconstruct(n, name, startLabel, returnLabel, formals, throwTypes,
        constraints, body);
    n = beginConflictLabel(n, beginConflictLabel);
    n = endConflictLabel(n, endConflictLabel);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Id name = visitChild(this.name, v);
    LabelNode startLabel = visitChild(this.startLabel, v);
    LabelNode beginConflictLabel = visitChild(this.beginConflictLabel, v);
    LabelNode returnLabel = visitChild(this.returnLabel, v);
    List<Formal> formals = visitList(this.formals, v);
    LabelNode endConflictLabel = visitChild(this.endConflictLabel, v);
    List<TypeNode> throwTypes = visitList(this.throwTypes, v);
    List<ConstraintNode<Assertion>> constraints =
        visitList(this.constraints, v);
    Block body = visitChild(this.body, v);
    return reconstruct(this, name, startLabel, beginConflictLabel, returnLabel,
        formals, endConflictLabel, throwTypes, constraints, body);
  }

  @Override
  public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
    FabricConstructorDecl n = (FabricConstructorDecl) super.disambiguate(ar);

    FabricConstructorInstance fci =
        (FabricConstructorInstance) n.constructorInstance();
    FabricTypeSystem fts = (FabricTypeSystem) ar.typeSystem();
    FabricDefaultSignature fds = (FabricDefaultSignature) fts.defaultSignature();

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
    fci.setBeginConflictLabel(bal, isDefaultBeginConflict);

    Label eal; // end conflict label
    boolean isDefaultEndConflict = false;
    if (n.endConflictLabel() == null) {
      eal = fds.defaultEndConflict(n);
      isDefaultEndConflict = true;
    } else {
      eal = n.endConflictLabel().label();
    }
    fci.setEndConflictLabel(eal, isDefaultEndConflict);

    return n.constructorInstance(fci);
  }
}
