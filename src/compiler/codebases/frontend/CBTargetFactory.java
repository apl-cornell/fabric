package codebases.frontend;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import fabric.common.FabricLocation;

import polyglot.frontend.Source;
import polyglot.frontend.TargetFactory;

public class CBTargetFactory extends TargetFactory {
  protected final ExtensionInfo extInfo;

  public CBTargetFactory(ExtensionInfo extInfo, JavaFileManager fm,
      FabricLocation outDir, String outExt, boolean so) {
    super(fm, outDir, outExt, so);
    this.extInfo = extInfo;
  }

  @Override
  public JavaFileObject outputFileObject(String packageName, String className,
      Source source) {
    // Prefix java package name to create a unique class for this namespace.
    FabricLocation ns = ((CodebaseSource) source).canonicalNamespace();
    // System.out.println(extInfo.namespaceToJavaPackagePrefix(ns) + " " +
    // packageName + " " + className);
    return super.outputFileObject(extInfo.namespaceToJavaPackagePrefix(ns)
        + packageName, className, source);
  }

}
