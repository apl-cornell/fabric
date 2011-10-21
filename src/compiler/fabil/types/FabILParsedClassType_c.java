package fabil.types;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;

import polyglot.frontend.FileSource;
import polyglot.frontend.Source;
import polyglot.main.Options;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import fabil.frontend.CodebaseSource;
import fabil.visit.ClassHashGenerator;
import fabric.common.Crypto;
import fabric.common.SysUtil;
import fabric.lang.Codebase;

public class FabILParsedClassType_c extends ParsedClassType_c implements CodebaseClassType {

  protected transient Codebase codebase;
  
  /**
   * Memoizes a secure hash of the class.
   */
  protected transient byte[] classHash;

  /**
   * Used for deserialization.
   */
  protected FabILParsedClassType_c() {
    super();
  }

  public FabILParsedClassType_c(TypeSystem ts, LazyClassInitializer init,
      Source fromSource) {
    super(ts, init, fromSource);
    if(fromSource != null) {
      this.codebase = ((CodebaseSource) fromSource).codebase();
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
        return SysUtil.packagePrefix(codebase) + name();
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
      return SysUtil.packagePrefix(codebase) + package_().translate(c) + "." + name();
    } else {
      return super.translate(c);
    }
  }
  
  @SuppressWarnings("unchecked")
  public byte[] getClassHash() {
    if (classHash != null) return classHash;
    
    MessageDigest digest = Crypto.digestInstance();
    
    if (fromSource instanceof FileSource) {
      // Hash the class's source code.
      try {
        String code =
            ClassHashGenerator.toSourceString((FileSource) fromSource);
        digest.update(code.getBytes("UTF-8"));
      } catch (IOException e) {
        throw new InternalCompilerError(e);
      }
    } else {
      // XXX Type was probably deserialized from a signature.  What do we do?
    }
    
    // Include the super class's hash.
    FabILParsedClassType_c superClassType = (FabILParsedClassType_c) superType();
    if (superClassType != null) {
      digest.update(superClassType.getClassHash());
    }
    
    // Include declared interfaces' hashes, if any.
    for (FabILParsedClassType_c ifaceType : (List<FabILParsedClassType_c>) interfaces()) {
      digest.update(ifaceType.getClassHash());
    }
    
    return classHash = digest.digest();
  }
}
