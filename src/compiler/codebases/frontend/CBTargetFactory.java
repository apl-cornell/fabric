package codebases.frontend;

import java.io.File;
import java.net.URI;

import polyglot.frontend.Source;
import polyglot.frontend.TargetFactory;
import fabric.common.SysUtil;

public class CBTargetFactory extends TargetFactory {

  public CBTargetFactory(File outDir, String outExt, boolean so) {
    super(outDir, outExt, so);
  }

  @Override
  public File outputFile(String packageName, String className, Source source) {
    URI ns = ((CodebaseSource) source).namespace();
    String prefix = SysUtil.namespaceToPackageName(ns);
    if (packageName != null && !packageName.equals(""))
      packageName = prefix + "." + packageName;
    else packageName = prefix;

    return super.outputFile(packageName, className, source);
  }

}
