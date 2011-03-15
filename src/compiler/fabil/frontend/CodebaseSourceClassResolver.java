package fabil.frontend;

import java.net.URI;

import polyglot.frontend.Compiler;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.FileSource;
import polyglot.types.ClassType;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.SourceClassResolver;
import polyglot.types.reflect.ClassFile;
import polyglot.types.reflect.ClassFileLoader;
import fabil.types.CodebaseTypeSystem;
import fabric.lang.Codebase;

public class CodebaseSourceClassResolver extends SourceClassResolver {

  public CodebaseSourceClassResolver(Compiler compiler, ExtensionInfo ext,
      String classpath, ClassFileLoader loader,
      boolean allowRawClasses,
      boolean compileCommandLineOnly, boolean ignoreModTimes) {
    super(compiler, ext, classpath, loader, allowRawClasses, compileCommandLineOnly,
        ignoreModTimes);
  }
  
  @Override
  protected Named getTypeFromSource(FileSource source, String name)
      throws SemanticException {
    URI uri = URI.create(name);
    if(uri.isAbsolute())
      return super.getTypeFromSource(source, name);

    Codebase cb = ((CodebaseSource) source).codebase();
    String absName = ((CodebaseTypeSystem)ts).absoluteName(cb, name, true);
    return super.getTypeFromSource(source, absName);
  }

  @Override
  protected ClassType getEncodedType(ClassFile clazz, String name)
      throws SemanticException {
    // TODO Support loading from classfiles in fabric
    return super.getEncodedType(clazz, name);
  }
  
}
