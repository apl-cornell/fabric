package fabil.types;

import java.net.URI;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.frontend.Source;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.DeserializedClassInitializer;
import polyglot.types.LazyClassInitializer;
import polyglot.types.ParsedClassType_c;
import polyglot.types.Resolver;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import codebases.frontend.CodebaseSource;
import codebases.types.CodebaseClassType;
import fabil.ExtensionInfo;
import fabil.visit.ProviderRewriter;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;

public class FabILParsedClassType_c extends ParsedClassType_c implements
    CodebaseClassType {

  /**
   * The namespace used to resolve the dependencies of this class
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
    Type type1 = this;
    Type type2 = t;

    if(t instanceof CodebaseClassType) {
//      if (type1.isClass()
//          && ((ClassType) type1).package_().fullName().startsWith("java")
//          && type1.isClass()
//          && ((ClassType) type1).package_().fullName().startsWith("java")) {
//        CodebaseClassType t1 = (CodebaseClassType) type1;
//        CodebaseClassType t2 = (CodebaseClassType) type2;
//        System.err.println("T1 NS: " + t1.canonicalNamespace() + " NAME: "
//            + t1.fullName() + " :" + t1.getClass());
//        System.err.println("T2 NS: " + t2.canonicalNamespace() + " NAME: "
//            + t2.fullName() + " :" + t2.getClass());
//      }

      CodebaseClassType ct = (CodebaseClassType) t;
      boolean res = fullName().equals(ct.fullName()) 
          && canonicalNamespace().equals(ct.canonicalNamespace());
//      System.err.println("T1 == T2: " +res);

      return res;
    }
    else
      return super.typeEqualsImpl(t);
  }

  @Override
  public String translate(Resolver c) {
    if (isTopLevel()) {
      ExtensionInfo extInfo = (ExtensionInfo) ts.extensionInfo();

      if (package_() == null) {
        return extInfo.namespaceToJavaPackagePrefix(canonical_ns) + name();
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
