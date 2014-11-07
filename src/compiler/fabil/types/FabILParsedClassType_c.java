package fabil.types;

import java.io.IOException;
import java.net.URI;
import java.security.MessageDigest;
import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.frontend.Source;
import polyglot.qq.QQ;
import polyglot.types.DeserializedClassInitializer;
import polyglot.types.LazyClassInitializer;
import polyglot.types.ParsedClassType_c;
import polyglot.types.Resolver;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import codebases.frontend.CodebaseSource;
import codebases.types.CBLazyClassInitializer;
import codebases.types.ClassFile;
import fabil.ExtensionInfo;
import fabil.visit.ClassHashGenerator;
import fabil.visit.ProviderRewriter;
import fabric.common.Crypto;
import fabric.common.NSUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;

public class FabILParsedClassType_c extends ParsedClassType_c implements
FabILParsedClassType {

  /**
   * The namespace used to resolve the dependencies of this class
   */
  protected URI canonical_ns;

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
      Source fromSource, URI ns) {
    super(ts, init, fromSource);
    this.canonical_ns = ns;
  }

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

  @Override
  public boolean typeEqualsImpl(Type t) {
    if (t instanceof FabILParsedClassType) {
      FabILParsedClassType ct = (FabILParsedClassType) t;
      return ct.canonicalNamespace().equals(canonical_ns)
          && ct.fullName().equals(fullName());
    }
    return false;
  }

  @Override
  public String translate(Resolver c) {
    if (isTopLevel()) {
      ExtensionInfo extInfo = (ExtensionInfo) ts.extensionInfo();

      if (package_() == null) {
        return extInfo.namespaceToJavaPackagePrefix(canonical_ns) + name();
      }
      //FIXME: To avoid codegen bugs where the package of a class
      // conflicts with an in-scope variable, we should avoid generating
      // fully qualified names whenever possible.
      // Unfortunately, since the ProxyRewriter transforms our FabIL AST into
      // java in-place, there are a lot of Ambiguous nodes in the tree,
      // causing the Translator to set the context to null.
      // When the context is null, the only option is to create a FQ name,
      // so we can't avoid these kinds of naming conflicts.
      // The best option would be to refactor the FabIL->Java phase
      // into an ExtensionRewriter pass.
      // TODO: Also -- there are some remaining issues regarding classes in the
      // same Fabric package that are linked from different codebases.
      // these need to either be emitted as FQ names, or an import statement
      // needs to be added to the emitted Java source.
//      if (c != null && !Options.global.fully_qualified_names
//      // Have to be explicit about default imports
//          && !ts.defaultPackageImports().contains(package_().fullName())) {
//        try {
//          Named x = c.find(name());
//
//          if (ts.equals(this, x)) {
//            return name();
//          }
//        } catch (SemanticException e) {
//        }
//      }

      return extInfo.namespaceToJavaPackagePrefix(canonical_ns)
          + package_().translate(c) + "." + name();
    } else {
      return super.translate(c);
    }
  }

  public Node rewriteProvider(ProviderRewriter pr) {
    ExtensionInfo extInfo = (ExtensionInfo) ts.extensionInfo();
    QQ qq = pr.qq();
    if (!canonical_ns.equals(extInfo.localNamespace())
        && !canonical_ns.equals(extInfo.platformNamespace())) {
      Codebase codebase = NSUtil.fetch_codebase(canonical_ns);
      FClass fclass = codebase.resolveClassName(fullName());
      // Convert to an OID.
      String storeName = fclass.$getStore().name();
      long onum = fclass.$getOnum();

      // Emit a call to get$$updateLabel() on an appropriate proxy object.
      Expr store =
          qq.parseExpr("fabric.worker.Worker.getWorker().getStore(\""
              + storeName + "\")");
      Expr fclassProxy =
          qq.parseExpr("new fabric.lang.FClass._Proxy(%E, " + onum + ")", store);
      return qq.parseExpr("%E.get$$updateLabel()", fclassProxy);
    } else {
      return qq
          .parseExpr("fabric.lang.security.LabelUtil._Impl.toLabel(fabric.worker.Worker.getWorker().getLocalStore(),fabric.lang.security.LabelUtil._Impl.topInteg())");
    }
  }

  @Override
  public URI canonicalNamespace() {
    return canonical_ns;
  }

  @Override
  public byte[] getClassHash() {
    if (classHash != null) return classHash;

    MessageDigest digest = Crypto.digestInstance();

    if (!init().fromClassFile()) {
      // Hash the class's source code.
      try {
        String code =
            ClassHashGenerator.toSourceString((CodebaseSource) fromSource);
        digest.update(code.getBytes("UTF-8"));
      } catch (IOException e) {
        throw new InternalCompilerError(e);
      }

    } else {
      // Type was probably obtained from a Java class file. Hash the bytecode.
      CBLazyClassInitializer init = (CBLazyClassInitializer) init();
      ClassFile classFile = init.classFile();
      digest.update(classFile.getHash());
    }

    if (!flags.isInterface()) {
      // Include the super class's hash.
      FabILParsedClassType_c superClassType =
          (FabILParsedClassType_c) superType();
      if (superClassType != null) {
        digest.update(superClassType.getClassHash());
      }
    }

    // Include declared interfaces' hashes.
    List<FabILParsedClassType> interfaces = interfaces();
    for (FabILParsedClassType iface : interfaces) {
      digest.update(iface.getClassHash());
    }

    return classHash = digest.digest();
  }

  @Override
  public List<FabILParsedClassType> interfaces() {
    return (List<FabILParsedClassType>) super.interfaces();
  }
}
