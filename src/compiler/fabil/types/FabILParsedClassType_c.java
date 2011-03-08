package fabil.types;

import fabil.Codebases;
import fabil.Util;
import fabric.lang.Codebase;
import polyglot.frontend.Source;
import polyglot.main.Options;
import polyglot.types.*;

public class FabILParsedClassType_c extends ParsedClassType_c implements Codebases {

  protected transient Codebase codebase;

  public FabILParsedClassType_c() {
    super();
  }

  public FabILParsedClassType_c(TypeSystem ts, LazyClassInitializer init,
      Source fromSource) {
    super(ts, init, fromSource);
    if(fromSource != null) {
      this.codebase = ((Codebases) fromSource).codebase();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.ClassType_c#descendsFromImpl(polyglot.types.Type)
   */
  @Override
  public boolean descendsFromImpl(Type ancestor) {
    FabILTypeSystem ts = (FabILTypeSystem) typeSystem();

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
  
  public Codebase codebase() {
    return codebase;
  }

  @Override
  public String translate(Resolver c) {
    if (isTopLevel()) {
      if (package_() == null) {
        return Util.packagePrefix(codebase) + name();
      }

      // Use the short name if it is unique and there is no 
      // codebase
      if (c != null && !Options.global.fully_qualified_names && codebase == null) {
        try {
          Named x = c.find(name());

          if (ts.equals(this, x)) {
            return name();
          }
        } catch (SemanticException e) {
        }
      }
      return Util.packagePrefix(codebase) + package_().translate(c) + "." + name();
    } else {
      return super.translate(c);
    }
  }
}
