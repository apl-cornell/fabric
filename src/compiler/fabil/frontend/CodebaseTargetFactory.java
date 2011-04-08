package fabil.frontend;

import java.io.File;

import polyglot.frontend.Source;
import polyglot.frontend.TargetFactory;
import fabric.common.SysUtil;
import fabric.lang.Codebase;

public class CodebaseTargetFactory extends TargetFactory {

  public CodebaseTargetFactory(File outDir, String outExt, boolean so) {
    super(outDir, outExt, so);
  }

  @Override
  public File outputFile(String packageName, String className, Source source) {
    if(source instanceof CodebaseSource) {
      Codebase cb = ((CodebaseSource) source).codebase();
      return super.outputFile(SysUtil.packagePrefix(cb) + packageName, className, source);
    }
    return super.outputFile(packageName, className, source);
  }
  
}
