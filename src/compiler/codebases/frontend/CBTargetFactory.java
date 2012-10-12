package codebases.frontend;

import java.net.URI;

import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject;

import polyglot.filemanager.FileManager;
import polyglot.frontend.Source;
import polyglot.frontend.TargetFactory;

public class CBTargetFactory extends TargetFactory {
  protected final ExtensionInfo extInfo;

  public CBTargetFactory(ExtensionInfo extInfo, FileManager fm,
      Location outDir, String outExt, boolean so) {
    super(fm, outDir, outExt, so);
    this.extInfo = extInfo;
  }

  @Override
  public JavaFileObject outputFileObject(String packageName, String className,
      Source source) {
    // Prefix java package name to create a unique class for this namespace.
    URI ns = ((CodebaseSource) source).canonicalNamespace();
    return super.outputFileObject(extInfo.namespaceToJavaPackagePrefix(ns)
        + packageName, className, source);
  }

}
