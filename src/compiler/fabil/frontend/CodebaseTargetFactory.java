package fabil.frontend;

import java.io.File;

import fabil.Codebases;
import fabric.common.SysUtil;
import fabric.lang.Codebase;

import polyglot.frontend.Source;
import polyglot.frontend.TargetFactory;

public class CodebaseTargetFactory extends TargetFactory {

  public CodebaseTargetFactory(File outDir, String outExt, boolean so) {
    super(outDir, outExt, so);
  }
//
//  @Override
//  public File outputFile(String packageName, Source source) {
//    if(source instanceof Codebases) {
//      Codebase cb = ((Codebases) source).codebase();
//      return super.outputFile( packageName, source);
//    }
//    return super.outputFile(packageName, source);
//  }

  @Override
  public File outputFile(String packageName, String className, Source source) {
    if(source instanceof Codebases) {
      Codebase cb = ((Codebases) source).codebase();
      return super.outputFile(SysUtil.packagePrefix(cb) + packageName, className, source);
    }
    return super.outputFile(packageName, className, source);
  }
  
}
