package fabric.types;

import java.net.URI;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jif.types.ActsForConstraint;
import jif.types.ActsForParam;
import jif.types.Assertion;
import jif.types.JifMethodInstance;
import jif.types.JifParsedPolyType_c;
import jif.types.LabelLeAssertion;
import jif.types.LabelSubstitution;
import jif.types.hierarchy.LabelEnv;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import jif.types.label.ThisLabel;
import jif.types.principal.Principal;
import polyglot.frontend.Source;
import polyglot.types.DeserializedClassInitializer;
import polyglot.types.FieldInstance;
import polyglot.types.LazyClassInitializer;
import polyglot.types.MethodInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import codebases.types.CodebaseClassType;
import fabil.types.FabILFlags;

public class FabricParsedClassType_c extends JifParsedPolyType_c implements
FabricParsedClassType {
  private transient Label singleFieldLabel = null;
  private transient boolean fieldLabelFound = false;

  private ConfPolicy accessPolicy = null;
  protected URI canonical_ns = null;
  protected Set<CodebaseClassType> namespaceDependencies;

  public FabricParsedClassType_c() {
    super();
  }

  public FabricParsedClassType_c(FabricTypeSystem ts,
      LazyClassInitializer init, Source fromSource, URI ns) {
    super(ts, init, fromSource);
    this.canonical_ns = ns;
  }

  @Override
  public boolean descendsFromImpl(Type ancestor) {
    FabricTypeSystem ts = (FabricTypeSystem) typeSystem();

    if (flags().contains(FabILFlags.NONFABRIC)
        && ts.typeEquals(ancestor, ts.FObject())) {
      // XXX nonfabric interfaces do not descend from fabric.lang.Object.
      return false;
    }

    // All Fabric interface types descend from fabric.lang.Object.
    if (ancestor.isCanonical() && !ancestor.isNull()
        && !ts.typeEquals(this, ancestor) && ancestor.isReference()
        && ts.typeEquals(ancestor, ts.FObject()) && flags().isInterface()) {
      // Determine whether we have a Fabric interface.
      // XXX Assume any class loaded from the DeserializedClassInitializer was
      // compiled with loom.
      if (job() != null
          || initializer() instanceof DeserializedClassInitializer)
        return true;
    }

    return super.descendsFromImpl(ancestor);
  }

  /**
   * This method returns the upper bound of the labels of all the fields of this
   * class and its superclasses. It computes this by taking a join of all labels
   * concerned.
   */
  @Override
  // XXX: These methods should be revisited post Oakland.
  public Label updateLabel() {
    final FabricTypeSystem ts = (FabricTypeSystem) typeSystem();

    if (!fieldLabelFound) {
      if (ts.isFabricClass(this)) {
        final FabricClassType superType = (FabricClassType) superType();

        Label classLabel =
            ts.pairLabel(Position.compilerGenerated(),
                ts.bottomConfPolicy(Position.compilerGenerated()),
                ts.topIntegPolicy(Position.compilerGenerated()));

        Label superLabel =
            superType == null ? classLabel : superType.updateLabel();

        LabelEnv classEnv = classEnv();
        for (FieldInstance fi : fields()) {
          if (fi.flags().isStatic()) continue;
          Type t = fi.type();
          if (ts.isLabeled(t)) {
            Label tslabel = ts.labelOfType(t);
            classLabel = trustUpperBound(classEnv, classLabel, tslabel);
          }
        }
        if (superLabel != null) {
          final FabricClassType subType = this;
          LabelSubstitution replaceThis = new LabelSubstitution() {

            @Override
            public Label substLabel(Label L) throws SemanticException {
              if (L instanceof ThisLabel) {
                ThisLabel ths = (ThisLabel) L;
                if (ths.classType().equals(superType))
                  return ts.thisLabel(ths.position(), subType);
              }
              return L;
            }

          };
          try {
            superLabel = superLabel.subst(replaceThis);
          } catch (SemanticException e) {
            throw new InternalCompilerError(e);
          }
        }
        singleFieldLabel =
            superLabel == null ? classLabel : trustUpperBound(classEnv,
                classLabel, superLabel);
      }
      fieldLabelFound = true;
    }
    return singleFieldLabel;
  }

  // XXX: These methods should be revisited post Oakland.
  private Label trustUpperBound(LabelEnv env, Label L1, Label L2) {
    FabricTypeSystem ts = (FabricTypeSystem) typeSystem();

    Label join = ts.tjoin(L1, L2);
    if (ts.tleq(env, join, L2)) {
      // join is bound by L2, use L2
      return L2;
    } else if (!ts.tleq(env, join, L1)) {
      // join is not bound by L1, use join
      return join;
    } else {
      // otherwise, keep classLabel
      return L1;
    }
  }

  private FabricContext classContext() {
    FabricContext classctx = (FabricContext) ts.createContext();
    classctx = (FabricContext) classctx.pushClass(this, this);
    for (Assertion constraint : constraints()) {
      if (constraint instanceof ActsForConstraint) {
        @SuppressWarnings("unchecked")
        ActsForConstraint<ActsForParam, ActsForParam> pi =
        (ActsForConstraint<ActsForParam, ActsForParam>) constraint;
        ActsForParam actor = pi.actor();
        ActsForParam granter = pi.granter();
        if (actor instanceof Principal && granter instanceof Principal) {
          classctx.addActsFor((Principal) actor, (Principal) granter);
        } else if (actor instanceof Label) {
          classctx.addActsFor((Label) actor, (Principal) granter);
        } else {
          throw new InternalCompilerError("Unexpected ActsForParam type: "
              + actor.getClass() + " actsfor " + granter.getClass());
        }
      } else if (constraint instanceof LabelLeAssertion) {
        LabelLeAssertion lle = (LabelLeAssertion) constraint;
        classctx.addAssertionLE(lle.lhs(), lle.rhs());
      } else {
        throw new InternalCompilerError("Unexpected assertion type: "
            + constraint, constraint.position());
      }
    }
    return classctx;
  }

  private LabelEnv classEnv() {
    return classContext().labelEnv();
  }

  /**
   * This method returns the upper bound of the labels of all the fields of this
   * class and its superclasses. It computes this by taking a join of all labels
   * concerned.
   */
  @Override
  public ConfPolicy accessPolicy() throws SemanticException {
    if (accessPolicy == null) accessPolicy = defaultAccessPolicy();
    return accessPolicy;
  }

  @Override
  public void setAccessPolicy(ConfPolicy policy) throws SemanticException {
    if (accessPolicy != null)
      throw new SemanticException("Duplicate access policy.", policy.position());
    accessPolicy = policy;
  }

  protected ConfPolicy defaultAccessPolicy() throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) typeSystem();
    ConfPolicy accessPolicy = null;
    if (ts.FObject().equals(this))
      accessPolicy = ts.bottomConfPolicy(Position.compilerGenerated());
    else if (ts.isFabricInterface(this)) {
      FabricClassType superType = (FabricClassType) superType();
//      if (superType.accessPolicy() == null) superType.setDefaultAccessPolicy();
      accessPolicy = superType.accessPolicy();
      for (MethodInstance mi : (List<MethodInstance>) methods()) {
        if (mi.flags().isStatic()) continue;
        JifMethodInstance jmi = (JifMethodInstance) mi;
        ConfPolicy beginConf = ts.confProjection(jmi.pcBound());
        if (beginConf == null) continue;

        if (!ts.accessPolicyValid(beginConf)) {
          throw new SemanticException("Default access policy for "
              + this.name() + " contains variable components from method "
              + jmi.signature() + ". "
              + " Must specify an access policy explicitly.");
        }
        accessPolicy = ts.join(accessPolicy, beginConf);
      }
    } else if (!ts.isFabricClass(this))
      // There is no access channel since it is not a persistent object
      accessPolicy = ts.topConfPolicy(Position.compilerGenerated());
    else {
      FabricClassType superType = (FabricClassType) superType();
//      if (superType.accessPolicy() == null) superType.setDefaultAccessPolicy();
      accessPolicy = superType.accessPolicy();
      for (FieldInstance fi : fields()) {
        if (fi.flags().isStatic()) continue;
        FabricFieldInstance ffi = (FabricFieldInstance) fi;
        Type t = fi.type();
        if (ts.isLabeled(t)) {
          ConfPolicy facc = ffi.accessPolicy();
          if (!ts.accessPolicyValid(facc)) {
            throw new SemanticException("Access policy for " + this.name()
                + " contains variable components from field " + ffi.name()
                + ". " + " Must specify an access policy explicitly.");
          }
          accessPolicy = ts.join(accessPolicy, facc);
        }
      }
    }

    accessPolicy = (ConfPolicy) accessPolicy.simplify();
    return accessPolicy;
  }

  @Override
  public void removeMethod(MethodInstance mi) {
    for (Iterator<MethodInstance> it = methods.iterator(); it.hasNext();) {
      if (it.next() == mi) {
        it.remove();
      }
    }
  }

  @Override
  public void setCanonicalNamespace(URI ns) {
    this.canonical_ns = ns;
  }

  @Override
  public URI canonicalNamespace() {
    // HACK superclass constructor accesses canonical namespace before it can be
    // initialized.
//    if (canonical_ns == null) {
//      if (fromSource == null) {
//        // XXX:Java classes may be loaded w/o encoded types
//        ExtensionInfo extInfo = (ExtensionInfo) ts.extensionInfo();
//        this.canonical_ns = extInfo.unsafeNamespace();
//      } else {
//        canonical_ns = ((CodebaseSource) fromSource).canonicalNamespace();
//      }
//    }
    return canonical_ns;
  }

  @Override
  public void setNamespaceDependencies(Set<CodebaseClassType> dependencies) {
    namespaceDependencies = dependencies;
  }

  @Override
  public Collection<CodebaseClassType> namespaceDependencies() {
    return namespaceDependencies;
  }

}
