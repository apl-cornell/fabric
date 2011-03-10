package fabil.frontend;

import fabric.common.SysUtil;
import fabric.lang.Codebase;
import polyglot.frontend.Compiler;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.FileSource;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.SourceClassResolver;
import polyglot.types.reflect.ClassFileLoader;

public class FabILSourceClassResolver extends SourceClassResolver {

  public FabILSourceClassResolver(Compiler compiler, ExtensionInfo ext,
      String classpath, ClassFileLoader loader, boolean allowRawClasses,
      boolean compileCommandLineOnly, boolean ignoreModTimes) {
    super(compiler, ext, classpath, loader, allowRawClasses,
        compileCommandLineOnly, ignoreModTimes);
  }
  
  @Override
  protected Named getTypeFromSource(FileSource source, String name)
      throws SemanticException {
    Codebase cb = ((CodebaseSource) source).codebase();
    return super.getTypeFromSource(source, SysUtil.codebasePrefix(cb)+name);
  }

}
