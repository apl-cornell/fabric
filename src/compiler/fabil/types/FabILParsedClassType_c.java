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
   * Memoizes a secure hash of the class. If this class-type information is
   * derived from a FabIL or Fabric signature, this field holds a hash of the
   * signature source. If the class-type information is derived from a source
   * file, this field holds a hash of that source file. Otherwise, the
   * class-type information is derived from a Java class file, and this field
   * holds a hash of that class's bytecode.
   */
  protected byte[] classHash;

  /**
   * Used for deserialization.
   */
  protected FabILParsedClassType_c() {
    super();
  }

  public FabILParsedClassType_c(TypeSystem ts, LazyClassInitializer init,
      Source fromSource) {
    super(ts, init, fromSource);
    if (fromSource != null) {
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
    } else if (fromSource instanceof CodebaseSource) {
      // XXX Jif impl ugliness
    } else {
      // Type was probably obtained from a Java class file. Hash the bytecode.
      LazyClassInitializer lci = this.init();
      if (lci instanceof ClassFileLazyClassInitializer) {
        ClassFileLazyClassInitializer cflci = (ClassFileLazyClassInitializer) lci;
        ClassFile classFile = cflci.classFile();
        digest.update(classFile.getHash());
      } else {
        // No clue where this class came from.  Complain loudly.
        throw new InternalError("Unexpected class initializer type: "
            + lci.getClass());
      }
    }
    
    if (!flags.isInterface()) {
      // Include the super class's hash.
      FabILParsedClassType_c superClassType = (FabILParsedClassType_c) superType();
      if (superClassType != null) {
        digest.update(superClassType.getClassHash());
      }
    }
    
    // Include declared interfaces' hashes.
    @SuppressWarnings("unchecked")
    List<FabILParsedClassType_c> interfaces = interfaces();
    for (FabILParsedClassType_c iface : interfaces) {
      digest.update(iface.getClassHash());
    }
    
    return classHash = digest.digest();
  }
}
