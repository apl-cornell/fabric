package fabric.types;

import java.net.URI;
import java.util.Iterator;

import jif.types.JifParsedPolyType_c;
import jif.types.label.Label;
import polyglot.frontend.Source;
import polyglot.types.DeserializedClassInitializer;
import polyglot.types.FieldInstance;
import polyglot.types.LazyClassInitializer;
import polyglot.types.MethodInstance;
import polyglot.types.Type;
import codebases.frontend.CodebaseSource;
import codebases.types.CodebaseClassType;

public class FabricParsedClassType_c extends JifParsedPolyType_c implements FabricParsedClassType {
  private transient Label singleFieldLabel = null;
  private transient Label singleAccessLabel = null;
//  private transient ConfPolicy accessPolicy = null;
  private transient boolean fieldLabelFound = false;
  private transient boolean accessLabelFound = false;
//  private transient boolean providerLabelFolded = false;
//  private transient boolean confPolicyExtracted = false;
  
  protected transient URI canonical_ns;

  public FabricParsedClassType_c() {
    super();
  }

  public FabricParsedClassType_c(FabricTypeSystem ts, LazyClassInitializer init,
      Source fromSource) {
    super(ts, init, fromSource);
    if (fromSource == null)
      throw new NullPointerException("fromSource cannot be null!");
    this.canonical_ns = ((CodebaseSource) fromSource).canonicalNamespace();
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.ClassType_c#descendsFromImpl(polyglot.types.Type)
   */
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
  
  @Override
  public Label singleFieldLabel() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    // TODO: check that the labels of fields in this class
    // are the same as in the superType, if the superType
    // defaultFieldLabel is not null.
    if (!fieldLabelFound) {
      if (ts.isFabricClass(this)) {
        FabricClassType superType = (FabricClassType)superType();
        if (superType != null && superType.singleFieldLabel() != null) {
          singleFieldLabel = superType.singleFieldLabel();
        }
        else {
          for (FieldInstance fi : fields()) {
            if (fi.flags().isStatic()) continue;
            Type t = fi.type();
            if (ts.isLabeled(t)) {
              singleFieldLabel = ts.labelOfType(t);
              break;
            }
          }
        }
      }
      fieldLabelFound = true;
    }
    return singleFieldLabel;
  }
  
  @Override
  public Label singleAccessLabel() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    if (!accessLabelFound) {
      if (ts.isFabricClass(this)) {
        FabricClassType superType = (FabricClassType)superType();
        if (superType != null && superType.singleAccessLabel() != null) {
          singleAccessLabel = superType.singleAccessLabel();
        }
        else {
          for (FieldInstance fi_ : fields()) {
            if (fi_.flags().isStatic()) continue;
            FabricFieldInstance fi = (FabricFieldInstance) fi_;
            Label al = fi.accessLabel();
            if (al != null) {
              singleAccessLabel = al;
              break;
            }
          }
        }
      }
      accessLabelFound = true;
    }

    return singleAccessLabel;
  }
  
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

  @Override
  public Label singleFabilAccessLabel() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    // or if this is a DelegatingPrincipal (XXX Principal instead?)
    // then compute access label
    if (!accessLabelFound || isSubtype(ts.DelegatingPrincipal())) {
      FabricClassType superType = (FabricClassType)superType();
      if (superType != null && superType.singleFabilAccessLabel() != null) {
        singleAccessLabel = superType.singleFabilAccessLabel();
      }
      else {
        for (FieldInstance fi : fields()) {
          if (fi.flags().isStatic()) continue;
          Type t = fi.type();
          if (ts.isLabeled(t)) {
            singleAccessLabel = ts.labelOfType(t);
            break;
          }
        }
      }
      accessLabelFound = true;
    }
    return singleAccessLabel;
  }
  
  
  @Override
  public Label singleFabilFieldLabel() {
    // Type checking has been done, so all field labels are guaranteed to
    // be the same
    // ThisLabelChecker has already run and checked that 'this' is
    // being used correctly.
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    // If the default field label has not been computed yet
    // or if this is a DelegatingPrincipal (XXX Principal instead?)
    // then compute defaultFieldLabel
    if (!fieldLabelFound || isSubtype(ts.DelegatingPrincipal())) {
      FabricClassType superType = (FabricClassType)superType();
      if (superType != null && superType.singleFabilFieldLabel() != null) {
        singleFieldLabel = superType.singleFabilFieldLabel();
      }
      else {
        for (FieldInstance fi : fields()) {
          if (fi.flags().isStatic()) continue;
          Type t = fi.type();
          if (ts.isLabeled(t)) {
            singleFieldLabel = ts.labelOfType(t);
            break;
          }
        }
      }
      fieldLabelFound = true;
    }
    return singleFieldLabel;
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
  public boolean typeEqualsImpl(Type t) {
    
    if (t instanceof CodebaseClassType) {
      CodebaseClassType ct = (CodebaseClassType) t;
      return fullName().equals(ct.fullName())
          && canonicalNamespace().equals(ct.canonicalNamespace());

    } else return super.typeEqualsImpl(t);
  }
  
  @Override
  public URI canonicalNamespace() {
    return canonical_ns;
  }

}
