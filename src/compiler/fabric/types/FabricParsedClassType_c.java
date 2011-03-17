package fabric.types;

import java.util.Iterator;
import java.util.List;

import fabil.frontend.CodebaseSource;
import fabric.lang.Codebase;

import jif.types.JifParsedPolyType_c;
import jif.types.label.Label;
import polyglot.frontend.Source;
import polyglot.types.*;

public class FabricParsedClassType_c extends JifParsedPolyType_c implements FabricParsedClassType {
  private transient Label singleFieldLabel = null;
  private transient Label singleAccessLabel = null;
  private transient boolean fieldLabelFound = false;
  private transient boolean accessLabelFound = false;
  protected transient Codebase codebase;

  public FabricParsedClassType_c() {
    super();
  }

  public FabricParsedClassType_c(FabricTypeSystem ts, LazyClassInitializer init,
      Source fromSource) {
    super(ts, init, fromSource);
    this.codebase = ((CodebaseSource) fromSource).codebase();
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
  
  @SuppressWarnings("unchecked")
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
          for (FieldInstance fi : (List<FieldInstance>)fields()) {
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
  
  public Label singleAccessLabel() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    if (!accessLabelFound) {
      if (ts.isFabricClass(this)) {
        FabricClassType superType = (FabricClassType)superType();
        if (superType != null && superType.singleAccessLabel() != null) {
          singleAccessLabel = superType.singleAccessLabel();
        }
        else {
          for (FieldInstance fi_ : (List<FieldInstance>)fields()) {
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
  
  
  public Label defaultFabilFieldLabel() {
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
      if (superType != null && superType.defaultFabilFieldLabel() != null) {
        singleFieldLabel = superType.defaultFabilFieldLabel();
      }
      else {
        for (FieldInstance fi : (List<FieldInstance>)fields()) {
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
  
  
  public void removeMethod(MethodInstance mi) {
    for (Iterator<MethodInstance> it = methods.iterator(); it.hasNext(); ) {
      if (it.next() == mi) {
        it.remove();
      }
    }
  }

  public Codebase codebase() {
    return codebase;
  }
}
