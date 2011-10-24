package fabric.types;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import jif.types.JifMethodInstance;
import jif.types.JifParsedPolyType_c;
import jif.types.label.Label;
import polyglot.frontend.Source;
import polyglot.types.DeserializedClassInitializer;
import polyglot.types.FieldInstance;
import polyglot.types.LazyClassInitializer;
import polyglot.types.MethodInstance;
import polyglot.types.Type;
import polyglot.util.Position;
import codebases.frontend.CodebaseSource;

public class FabricParsedClassType_c extends JifParsedPolyType_c implements FabricParsedClassType {
  private transient Label singleFieldLabel = null;
  private transient Label singleAccessLabel = null;
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
  public Label singleFieldLabel() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    if (!fieldLabelFound) {
      if (ts.isFabricClass(this)) {
        FabricClassType superType = (FabricClassType)superType();

        Label classLabel = ts.pairLabel(Position.compilerGenerated(), 
            ts.bottomConfPolicy(Position.compilerGenerated()),
            ts.topIntegPolicy(Position.compilerGenerated()));
        
        Label superLabel = superType == null ? classLabel : superType.singleFieldLabel();
        
        for (FieldInstance fi : fields()) {
          if (fi.flags().isStatic()) continue;
          Type t = fi.type();
          if (ts.isLabeled(t)) {
            Label tslabel = ts.labelOfType(t);
            classLabel = ts.tjoin(classLabel, tslabel);
          }
        }
        singleFieldLabel = superLabel == null ? classLabel : ts.tjoin(classLabel, superLabel);
      }
      fieldLabelFound = true;
    }
    return singleFieldLabel;
  }
  
  /**
   * This method returns the upper bound of the labels of 
   * all the fields and begin labels of methods of this class and
   * its superclasses. It computes this by taking a join of all labels
   * concerned.
   */
  @SuppressWarnings("unchecked")
  @Override
  public Label singleAccessLabel() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    if (!accessLabelFound) {
      if (ts.isFabricClass(this)) {
        FabricClassType superType = (FabricClassType)superType();
        
        Label classAccessLabel = ts.pairLabel(Position.compilerGenerated(), 
            ts.bottomConfPolicy(Position.compilerGenerated()),
            ts.topIntegPolicy(Position.compilerGenerated()));

        Label superAccessLabel = superType == null ? classAccessLabel : superType.singleAccessLabel();

        for (FieldInstance fi_ : fields()) {
          if (fi_.flags().isStatic()) continue;
          FabricFieldInstance fi = (FabricFieldInstance) fi_;
          Label al = fi.accessLabel();
          if (al != null) {
            // a tjoin is not necessary since access labels only have a conf component
            classAccessLabel = ts.join(classAccessLabel, al);
          }
        }

        for (MethodInstance mi_ : (List<MethodInstance>) methods()) {
          if (mi_.flags().isStatic()) continue;
          JifMethodInstance mi = (JifMethodInstance) mi_;
          Label bl = mi.pcBound();
          if (bl != null) {
            // a tjoin is not necessary since access labels only have a conf component
            classAccessLabel = ts.join(classAccessLabel, bl);
          }
        }
        
        // a tjoin is not necessary since access labels only have a conf component
        singleAccessLabel = superAccessLabel == null ? classAccessLabel : ts.join(classAccessLabel, superAccessLabel);
      }
      accessLabelFound = true;
    }
    return singleAccessLabel;
  }
  
  // TODO The provider label should actually be folded into the access labels
  // since each field of a class will be accessed from some method of the class, no?
  @Override
  public Label getFoldedAccessLabel() {
    return singleAccessLabel();
    // XXX: this code folded in the provider label to the access label. This is
    // probably the wrong thing, but it is true that accessing the class object
    // represents a read channel. We'll just use the regular access label for
    // now, but we need to figure out how to prevent read channels to the stores 
    //   of Fabric class objects.
    //    if (!accessLabelFound) singleAccessLabel();
    //    
    //    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();
    //    
    //    if (!providerLabelFolded && singleAccessLabel != null) {
    //      // Fold in the provider confidentiality label into the access label
    //      singleAccessLabel = ts.join(singleAccessLabel, 
    //          ts.pairLabel(Position.compilerGenerated(), 
    //              provider().confProjection(),
    //              ts.bottomIntegPolicy(Position.compilerGenerated())));
    //    }
    //    providerLabelFolded = true;
    //    return singleAccessLabel;
  }

  // XXX Why do we need these fabil'ed versions?
  @Override
  public Label singleFabilAccessLabel() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();
    if (isSubtype(ts.DelegatingPrincipal())) {
      singleAccessLabel = null;
      // recompute the access label
    }
    return singleAccessLabel();
//    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();
//
//    // or if this is a DelegatingPrincipal (XXX Principal instead?)
//    // then compute access label
//    if (!accessLabelFound || isSubtype(ts.DelegatingPrincipal())) {
//      FabricClassType superType = (FabricClassType)superType();
//      if (superType != null && superType.singleFabilAccessLabel() != null) {
//        singleAccessLabel = superType.singleFabilAccessLabel();
//      }
//      else {
//        for (FieldInstance fi : (List<FieldInstance>)fields()) {
//          if (fi.flags().isStatic()) continue;
//          Type t = fi.type();
//          if (ts.isLabeled(t)) {
//            singleAccessLabel = ts.labelOfType(t);
//            break;
//          }
//        }
//      }
//      accessLabelFound = true;
//    }
//    return singleAccessLabel;
  }
  
  
  @Override
  public Label singleFabilFieldLabel() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();
    if (isSubtype(ts.DelegatingPrincipal())) {
      singleFieldLabel = null;
      // recompute the field label
    }
    return singleFieldLabel();
    
//    // Type checking has been done, so all field labels are guaranteed to
//    // be the same
//    // ThisLabelChecker has already run and checked that 'this' is
//    // being used correctly.
//    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();
//
//    // If the default field label has not been computed yet
//    // or if this is a DelegatingPrincipal (XXX Principal instead?)
//    // then compute defaultFieldLabel
//    if (!fieldLabelFound || isSubtype(ts.DelegatingPrincipal())) {
//      FabricClassType superType = (FabricClassType)superType();
//      if (superType != null && superType.singleFabilFieldLabel() != null) {
//        singleFieldLabel = superType.singleFabilFieldLabel();
//      }
//      else {
//        for (FieldInstance fi : (List<FieldInstance>)fields()) {
//          if (fi.flags().isStatic()) continue;
//          Type t = fi.type();
//          if (ts.isLabeled(t)) {
//            singleFieldLabel = ts.labelOfType(t);
//            break;
//          }
//        }
//      }
//      fieldLabelFound = true;
//    }
//    return singleFieldLabel;
  }
  
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

}
