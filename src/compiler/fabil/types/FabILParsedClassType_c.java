package fabil.types;

import java.net.URI;

import codebases.frontend.CodebaseSource;
import codebases.types.CodebaseClassType;
import codebases.types.CodebaseTypeSystem;
import fabil.ExtensionInfo;
import fabil.visit.ProviderRewriter;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.WrappedJavaInlineable;
import fabric.util.Map;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.frontend.Source;
import polyglot.main.Options;
import polyglot.qq.QQ;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;

public class FabILParsedClassType_c extends ParsedClassType_c implements
    CodebaseClassType {

  /**
   * 
   */
  protected URI canonical_ns;

  public FabILParsedClassType_c() {
    super();
  }

  public FabILParsedClassType_c(TypeSystem ts, LazyClassInitializer init,
      Source fromSource) {
    super(ts, init, fromSource);
    if (fromSource == null) {
      //XXX:Java classes may be loaded w/o encoded types
      ExtensionInfo extInfo = (ExtensionInfo) ts.extensionInfo();
      this.canonical_ns = extInfo.platformNamespace();
    } else
      this.canonical_ns = ((CodebaseSource) fromSource).canonicalNamespace();
  }

  /*
   * (non-Javadoc)
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

  @Override
  public String translate(Resolver c) {
    if (isTopLevel()) {
      if (package_() == null) {
        return SysUtil.namespaceToPackageName(canonical_ns) + "." + name();
      }

      // XXX: Never use short name
      // if (c != null && !Options.global.fully_qualified_names
      // && codebase == null) {
      // try {
      // Named x = c.find(name());
      //
      // if (ts.equals(this, x)) {
      // return name();
      // }
      // } catch (SemanticException e) {
      // }
      // }

      return SysUtil.namespaceToPackageName(canonical_ns)
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
      Codebase codebase = SysUtil.fetch_codebase(canonical_ns);
      FClass fclass = codebase.resolveClassName(fullName());
      // Convert to an OID.
      String storeName = fclass.$getStore().name();
      long onum = fclass.$getOnum();

      // Emit a call to get$label() on an appropriate proxy object.
      Expr store =
          qq.parseExpr("fabric.worker.Worker.getWorker().getStore(\""
              + storeName + "\")");
      Expr fclassProxy =
          qq.parseExpr("new fabric.lang.FClass._Proxy(%E, " + onum + ")", store);
      return qq.parseExpr("%E.get$label()", fclassProxy);
    } else {
      return qq
          .parseExpr("fabric.lang.security.LabelUtil._Impl.toLabel(fabric.worker.Worker.getWorker().getLocalStore(),fabric.lang.security.LabelUtil._Impl.topInteg())");
    }
  }

  @Override
  public URI canonicalNamespace() {
    return canonical_ns;
  }
}
