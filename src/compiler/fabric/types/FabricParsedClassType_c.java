package fabric.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jif.ast.JifNodeFactory;
import jif.extension.CallHelper;
import jif.extension.JifProcedureDeclExt_c;
import jif.types.ActsForConstraint;
import jif.types.ActsForParam;
import jif.types.Assertion;
import jif.types.JifClassType;
import jif.types.JifMethodInstance;
import jif.types.JifParsedPolyType_c;
import jif.types.JifProcedureInstance;
import jif.types.JifTypeSystem;
import jif.types.LabelLeAssertion;
import jif.types.LabelSubstitution;
import jif.types.TypeSubstitutor;
import jif.types.hierarchy.LabelEnv;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathLocal;
import jif.types.label.AccessPathRoot;
import jif.types.label.ArgLabel;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.ast.Local;
import polyglot.ast.Receiver;
import polyglot.frontend.Source;
import polyglot.types.DeserializedClassInitializer;
import polyglot.types.FieldInstance;
import polyglot.types.LazyClassInitializer;
import polyglot.types.LocalInstance;
import polyglot.types.MethodInstance;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import codebases.frontend.CodebaseSource;

public class FabricParsedClassType_c extends JifParsedPolyType_c implements FabricParsedClassType {
  private transient Label singleFieldLabel = null;
  private transient ConfPolicy accessPolicy = null;
  private transient boolean fieldLabelFound = false;
  private transient boolean accessLabelFound = false;
  
  protected URI canonical_ns = null;

  public FabricParsedClassType_c() {
    super();
  }

  public FabricParsedClassType_c(FabricTypeSystem ts, LazyClassInitializer init,
      Source fromSource) {
    super(ts, init, fromSource);
    if (fromSource == null)
      throw new NullPointerException("fromSource cannot be null!");
    this.canonical_ns =
        ((CodebaseSource) fromSource).canonicalNamespace();
  }

  @Override
  public boolean descendsFromImpl(Type ancestor) {
    FabricTypeSystem ts = (FabricTypeSystem) typeSystem();

    if (flags().contains(FabricFlags.NONFABRIC) && ts.typeEquals(ancestor, ts.FObject())) {
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
   * This method returns the upper bound of the labels of 
   * all the fields of this class and its superclasses.
   * It computes this by taking a join of all labels concerned.
   */
  @Override
  //XXX: These methods should be revisited post Oakland.
  public Label updateLabel() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    if (!fieldLabelFound) {
      if (ts.isFabricClass(this)) {
        FabricClassType superType = (FabricClassType)superType();

        Label classLabel = ts.pairLabel(Position.compilerGenerated(), 
            ts.bottomConfPolicy(Position.compilerGenerated()),
            ts.topIntegPolicy(Position.compilerGenerated()));
        
        Label superLabel = superType == null ? classLabel : superType.updateLabel();
        
        LabelEnv classEnv = classEnv();
        for (FieldInstance fi : fields()) {
          if (fi.flags().isStatic()) continue;
          Type t = fi.type();
          if (ts.isLabeled(t)) {
            Label tslabel = ts.labelOfType(t);
            classLabel = trustUpperBound(classEnv, classLabel, tslabel);
          }
        }
        singleFieldLabel =
            superLabel == null ? classLabel : trustUpperBound(
                classEnv, classLabel, superLabel);
      }
      fieldLabelFound = true;
    }
    return singleFieldLabel;
  }
  //XXX: These methods should be revisited post Oakland.
  private ConfPolicy upperBound(LabelEnv env, ConfPolicy P1, ConfPolicy P2) {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    ConfPolicy join = ts.join(P1, P2);
    if(env.leq(join, P2)) {
      //join is bound by L2, use L2
      return P2;
    } else if(!env.leq(join, P1)) {
      //join is not bound by L1, use join
      return join;
    } else {
      return P1;
    }
    //otherwise, keep P1
  }
  

  
  //XXX: These methods should be revisited post Oakland.
  private Label trustUpperBound(LabelEnv env, Label L1, Label L2) {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    Label join = ts.tjoin(L1, L2);
    if(ts.tleq(env, join, L2)) {
      //join is bound by L2, use L2
      return L2;
    } else if(!ts.tleq(env, join, L1)) {
      //join is not bound by L1, use join
      return join;
    } else {
      //otherwise, keep classLabel
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
   * Adds method constraints to environment.
   */
  private LabelEnv methodEnv(JifProcedureInstance mi) {
    FabricContext methodctx = classContext();
    try {
      CallHelper ch = classLabelHelper(mi);
      JifProcedureDeclExt_c.constrainLabelEnv(mi, methodctx, ch);
    } catch (SemanticException e) {
      e.printStackTrace();
      throw new InternalCompilerError("Unexpected semantic exception", e);
    }
    
    return methodctx.labelEnv();
  }

  /**
   * This method returns the upper bound of the labels of 
   * all the fields and begin labels of methods of this class and
   * its superclasses. It computes this by taking a join of all labels
   * concerned.
   */
  //XXX: These methods should be revisited post Oakland.
  @SuppressWarnings("unchecked")
  @Override
  public ConfPolicy accessPolicy() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    if (!accessLabelFound) {
      if(ts.FObject().equals(this)) {
        accessPolicy = ts.bottomConfPolicy(Position.compilerGenerated());
      }
      else if (ts.isFabricClass(this)) {
        FabricClassType superType = (FabricClassType)superType();
        
        ConfPolicy classAccessPolicy = ts.bottomConfPolicy(Position.compilerGenerated());

        ConfPolicy superAccessPolicy = superType == null ? classAccessPolicy : superType.accessPolicy();

        LabelEnv classEnv = classEnv();
        for (FieldInstance fi : fields()) {
          if (fi.flags().isStatic()) continue;
          FabricFieldInstance ffi = (FabricFieldInstance) fi;
          Type t = fi.type();
          if (ts.isLabeled(t)) {
            Label tslabel = ffi.accessLabel();

            classAccessPolicy = upperBound(classEnv, classAccessPolicy, tslabel.confProjection());
          }
        }
        if(ts.containsThisLabel(ts.toLabel(classAccessPolicy)))
          throw new InternalError("Access label contains \"this\" in " + this + ":" + classAccessPolicy);

        try {
          int i = 0;
          
          // Join the access policy with the begin labels of all methods
          // we need this because the object needs to be fetched for each method call
          // and requiring that the begin label of a method is bounded above by
          // the access label ensures that there are no read channels
          
          for (JifMethodInstance pi_ : (List<JifMethodInstance>) methods()) {
            if (pi_.flags().isStatic()) continue;
            JifMethodInstance mi = (JifMethodInstance) pi_.copy();
            renameArgs(mi, new TypeSubstitutor(new ArgRenamer("$" + i)));
            Label bl = mi.pcBound();
            if(bl == null) {
              bl = ts.noComponentsLabel();
            }
            // a tjoin is not necessary since access labels only have a conf
            // component

            LabelEnv methodEnv = methodEnv(mi);
            ConfPolicy begin_policy =
                upperBound(methodEnv, provider().confProjection(),
                    bl.confProjection());

            classAccessPolicy =
                upperBound(methodEnv, classAccessPolicy, begin_policy);
          }
        } catch (SemanticException e) {
          throw new InternalCompilerError("Unexpected semantic exception",e);
        }
        // a tjoin is not necessary since access labels only have a conf component
        accessPolicy =
            superAccessPolicy == null ? classAccessPolicy : upperBound(
                classEnv, classAccessPolicy, superAccessPolicy);
      }
      else {
        // There is no access channel since it is not a persistent object
        accessPolicy = ts.topConfPolicy(Position.compilerGenerated());
      }
      accessLabelFound = true;
    }
    return accessPolicy;
  }
  
  // TODO The provider label should actually be folded into the access labels
  // since each field of a class will be accessed from some method of the class, no?
//  @Override
//  public Label providerFoldedClassAccessLabel() {
//    return classAccessPolicy();
//    // XXX: this code folded in the provider label to the access label. This is
//    // probably the wrong thing, but it is true that accessing the class object
//    // represents a read channel. We'll just use the regular access label for
//    // now, but we need to figure out how to prevent read channels to the stores 
//    //   of Fabric class objects.
//    //    if (!accessLabelFound) accessPolicy();
//    //    
//    //    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();
//    //    
//    //    if (!providerLabelFolded && accessPolicy != null) {
//    //      // Fold in the provider confidentiality label into the access label
//    //      accessPolicy = ts.join(accessPolicy, 
//    //          ts.pairLabel(Position.compilerGenerated(), 
//    //              provider().confProjection(),
//    //              ts.bottomIntegPolicy(Position.compilerGenerated())));
//    //    }
//    //    providerLabelFolded = true;
//    //    return accessPolicy;
//  }
  
  @SuppressWarnings("unchecked")
  @Override
  public void removeMethod(MethodInstance mi) {
    for (Iterator<MethodInstance> it = methods.iterator(); it.hasNext(); ) {
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
    if (canonical_ns == null)
      canonical_ns = ((CodebaseSource) fromSource).canonicalNamespace();
    
    return canonical_ns;
  }

  //XXX: Stolen from JifMethodDecl_c
  /**
   * Rename args so that access label computation doesn't cause collisions.
   */
  private static void renameArgs(JifProcedureInstance jmi, TypeSubstitutor tsub) throws SemanticException {
      // formal types
      List<Type> newFormalTypes = new ArrayList<Type>(jmi.formalTypes().size());
      @SuppressWarnings("unchecked")
      List<Type> formalTypes = jmi.formalTypes();
      for (Type t : formalTypes) {
          newFormalTypes.add(tsub.rewriteType(t));
      }
      jmi.setFormalTypes(newFormalTypes);

      // return type
      if(jmi instanceof MethodInstance) {
        ((MethodInstance) jmi).setReturnType(tsub.rewriteType(((MethodInstance) jmi).returnType()));
      }
      // pc bound label
      jmi.setPCBound(tsub.rewriteLabel(jmi.pcBound()), jmi.isDefaultPCBound());

      // return label
      jmi.setReturnLabel(tsub.rewriteLabel(jmi.returnLabel()), jmi.isDefaultReturnLabel());

      // throw types
      List<Type> newThrowTypes = new ArrayList<Type>(jmi.throwTypes().size());
      @SuppressWarnings("unchecked")
      List<Type> throwTypes = jmi.throwTypes();
      for (Type t : throwTypes) {
          newThrowTypes.add(tsub.rewriteType(t));
      }
      jmi.setThrowTypes(newThrowTypes);


      // constraints
      List<Assertion> constraints = new ArrayList<Assertion>(jmi.constraints().size());
      for (Assertion c : jmi.constraints()) {
          constraints.add(tsub.rewriteAssertion(c));
      }
      jmi.setConstraints(constraints);
  }

  private static class ArgRenamer extends LabelSubstitution {
    final String postFix;

    ArgRenamer(String postFix) {
      this.postFix = postFix;
    }
    
    @Override
    public Label substLabel(Label L) {
        if (L instanceof ArgLabel) {
            ArgLabel al = (ArgLabel)L;
            if (!al.name().endsWith(postFix)) {
                // change the name to end with a prime
                al = (ArgLabel)al.copy();
                al.setName(al.name() + postFix);
                return al;
            }
        }

        return L;
    }

    @Override
    public AccessPath substAccessPath(AccessPath ap) {            
        AccessPathRoot r = ap.root();
        if (r instanceof AccessPathLocal) {
            AccessPathLocal apl = (AccessPathLocal)r;
            if (!apl.name().endsWith(postFix)) {
                apl = apl.name(apl.name() + postFix);
                AccessPath newPath = ap.subst(r, apl);
                return newPath;
            }
        }
        return ap;
    }        
  }
  
  /**
   * Produces a <code>CallHelper</code> for constraining the label environment
   * for the given method.
   */
  @SuppressWarnings("unchecked")
  private CallHelper classLabelHelper(
          JifProcedureInstance method) {

      JifTypeSystem jts = (JifTypeSystem)method.typeSystem();
      JifNodeFactory nf = (JifNodeFactory)ts.extensionInfo().nodeFactory();
      JifClassType subContainer = (JifClassType)method.container();
      Label receiverLabel = subContainer.thisLabel();
      Receiver receiver = nf.This(method.position());
      ReferenceType calleeContainer = method.container().toReference();

      List<Expr> actualArgs = new ArrayList<Expr>(method.formalTypes().size());

      for (Type t : (List<Type>) method.formalTypes()) {
          if (jts.isLabeled(t)) {
              ArgLabel al = (ArgLabel)jts.labelOfType(t);
              LocalInstance formalInst = (LocalInstance)al.formalInstance();
              Local l = nf.Local(formalInst.position(), nf.Id(al.position(), al.name())).
              localInstance(formalInst);
              actualArgs.add(l);
          }
          else {
              throw new InternalCompilerError("Formal type is not labeled!");
          }
      }


      CallHelper ch = new ClassAccessPolicyHelper(receiverLabel, 
                                     receiver, 
                                     calleeContainer,
                                     method, 
                                     actualArgs,
                                     method.position());
      return ch;
  }
  private static class ClassAccessPolicyHelper extends CallHelper {

    /**
     * @param receiverLabel
     * @param receiver
     * @param calleeContainer
     * @param pi
     * @param actualArgs
     * @param position
     */
    @SuppressWarnings("unchecked")
    public ClassAccessPolicyHelper(Label receiverLabel, Receiver receiver,
        ReferenceType calleeContainer, JifProcedureInstance pi,
        List<Expr> actualArgs, Position position) {
      super(receiverLabel, receiver, calleeContainer, pi, actualArgs, position);
      FabricTypeSystem ts = (FabricTypeSystem) pi.typeSystem();
      actualArgLabels = new ArrayList<Label>(pi.formalTypes().size());

      for (Type t : (List<Type>) pi.formalTypes()) {
          ArgLabel al = (ArgLabel)ts.labelOfType(t);
          actualArgLabels.add(al);
      }

    }
    
  }
}
