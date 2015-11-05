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

  protected LabelNode beginAccessLabel;
  protected LabelNode endAccessLabel;

  public FabricMethodDecl_c(Position pos, Flags flags, TypeNode returnType,
      Id name, LabelNode startLabel, LabelNode beginAccessLabel,
      List<Formal> formals, LabelNode returnLabel, LabelNode endAccessLabel,
      List<TypeNode> throwTypes, List<ConstraintNode<Assertion>> constraints,
      Block body, Javadoc javadoc) {
    this(pos, flags, returnType, name, startLabel, beginAccessLabel, formals,
        returnLabel, endAccessLabel, throwTypes, constraints, body, javadoc,
        null);
  }

  public FabricMethodDecl_c(Position pos, Flags flags, TypeNode returnType,
      Id name, LabelNode startLabel, LabelNode beginAccessLabel, List<Formal>
      formals, LabelNode returnLabel, LabelNode endAccessLabel,
      List<TypeNode> throwTypes, List<ConstraintNode<Assertion>> constraints,
      Block body, Javadoc javadoc, Ext ext) {
    super(pos, flags, returnType, name, startLabel, formals, returnLabel,
        throwTypes, constraints, body, javadoc, ext);
    this.beginAccessLabel = beginAccessLabel;
    this.endAccessLabel = endAccessLabel;
  }

  @Override
  public LabelNode beginAccessLabel() {
    return this.beginAccessLabel;
  }

  @Override
  public FabricMethodDecl beginAccessLabel(LabelNode beginAccessLabel) {
    return beginAccessLabel(this, beginAccessLabel);
  }

  protected <N extends FabricMethodDecl_c> N beginAccessLabel(N n,
      LabelNode beginAccessLabel) {
    if (n.beginAccessLabel == beginAccessLabel) return n;
    n = copyIfNeeded(n);
    n.beginAccessLabel = beginAccessLabel;
    return n;
  }

  @Override
  public LabelNode endAccessLabel() {
    return this.endAccessLabel;
  }

  @Override
  public FabricMethodDecl endAccessLabel(LabelNode endAccessLabel) {
    return endAccessLabel(this, endAccessLabel);
  }

  protected <N extends FabricMethodDecl_c> N endAccessLabel(N n,
    LabelNode endAccessLabel) {
    if (n.endAccessLabel == endAccessLabel) return n;
    n = copyIfNeeded(n);
    n.endAccessLabel = endAccessLabel;
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Id name = visitChild(this.name, v);
    TypeNode returnType = visitChild(this.returnType, v);
    LabelNode startLabel = visitChild(this.startLabel, v);
    LabelNode beginAccessLabel = visitChild(this.beginAccessLabel, v);
    List<Formal> formals = visitList(this.formals, v);
    LabelNode returnLabel = visitChild(this.returnLabel, v);
    LabelNode endAccessLabel = visitChild(this.endAccessLabel, v);
    List<TypeNode> throwTypes = visitList(this.throwTypes, v);
    List<ConstraintNode<Assertion>> constraints =
        visitList(this.constraints, v);
    Block body = visitChild(this.body, v);
    return reconstruct(this, name, returnType, startLabel, beginAccessLabel,
        formals, returnLabel, endAccessLabel, throwTypes, constraints, body);
  }

  protected <N extends FabricMethodDecl_c> N reconstruct(N n, Id name,
      TypeNode returnType, LabelNode startLabel, LabelNode beginAccessLabel,
      List<Formal> formals, LabelNode returnLabel, LabelNode endAccessLabel,
      List<TypeNode> throwTypes, List<ConstraintNode<Assertion>> constraints,
      Block body) {
    n = super.reconstruct(n, name, returnType, startLabel, formals, returnLabel,
        throwTypes, constraints, body);
    n = beginAccessLabel(n, beginAccessLabel);
    n = endAccessLabel(n, endAccessLabel);
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

    if (n.beginAccessLabel() != null && !n.beginAccessLabel().isDisambiguated()) {
      // the beginAccessLabel node hasn't been disambiguated yet
      ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
      return this;
    }

    if (n.endAccessLabel() != null && !n.endAccessLabel().isDisambiguated()) {
      // the endAccessLabel node hasn't been disambiguated yet
      ar.job().extensionInfo().scheduler().currentGoal().setUnreachableThisRun();
      return this;
    }

    Label bal; // begin access label
    boolean isDefaultBeginAccess = false;
    if (n.beginAccessLabel() == null) {
      bal = fds.defaultBeginAccess(n);
      isDefaultBeginAccess = true;
    } else {
      bal = n.beginAccessLabel().label();
    }
    fmi.setBeginAccessLabel(bal, isDefaultBeginAccess);

    Label eal; // end access label
    boolean isDefaultEndAccess = false;
    if (n.endAccessLabel() == null) {
      eal = fds.defaultEndAccess(n);
      isDefaultEndAccess = true;
    } else {
      eal = n.endAccessLabel().label();
    }
    fmi.setEndAccessLabel(eal, isDefaultEndAccess);

    return n.methodInstance(fmi);
  }
}
