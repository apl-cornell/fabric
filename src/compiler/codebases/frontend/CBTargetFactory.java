package codebases.frontend;

import java.io.File;
import java.net.URI;

import polyglot.frontend.Source;
import polyglot.frontend.TargetFactory;

public class CBTargetFactory extends TargetFactory {
  protected final ExtensionInfo extInfo;

  public CBTargetFactory(ExtensionInfo extInfo, File outDir, String outExt, boolean so) {
    super(outDir, outExt, so);
    this.extInfo = extInfo;
  }

  @Override
  public File outputFile(String packageName, String className, Source source) {
    // Prefix java package name to create a unique class for this namespace.
    URI ns = ((CodebaseSource) source).namespace();
    return super.outputFile(extInfo.namespaceToJavaPackagePrefix(ns)
        + packageName, className, source);
  }

}
