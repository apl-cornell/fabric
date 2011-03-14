package fabric.frontend;

import polyglot.frontend.Compiler;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.FileSource;
import polyglot.types.ClassType;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.SourceClassResolver;
import polyglot.types.reflect.ClassFile;
import polyglot.types.reflect.ClassFileLoader;
import fabil.frontend.CodebaseSource;
import fabric.common.SysUtil;
import fabric.lang.Codebase;

public class FabricSourceClassResolver extends SourceClassResolver {

  public FabricSourceClassResolver(Compiler compiler, ExtensionInfo ext,
      String classpath, ClassFileLoader loader,
      boolean allowRawClasses,
      boolean compileCommandLineOnly, boolean ignoreModTimes) {
    super(compiler, ext, classpath, loader, allowRawClasses, compileCommandLineOnly,
        ignoreModTimes);
  }

  /**
   * Find a type by name.
   */
//  @Override
//  public Named find(String name) throws SemanticException {
//    URI uri = URI.create(name);
//    //If the name is not a fabric reference, we can 
//    // use the regular sourceclassresolver to find the 
//    // name, even if the name ends up being stored in
//    // fabric (i.e. its codebase is in our path).
//    if(!uri.isAbsolute())
//      return super.find(name);
//
//    //This type is stored in Fabric
//    else {
//      if (Report.should_report(report_topics, 3))
//        Report.report(3, "FabSourceCR.find(" + name + ")");
//  
//      ClassFile clazz = null;
//      ClassFile encodedClazz = null;
//      FileSource source = null;
//  
//      //Have we already downloaded an compiled this Fabric type?
//      clazz = loadFile(name);
//      if (clazz != null) {
//        // Check for encoded type information.
//        if (clazz.encodedClassType(version.name()) != null) {
//          if (Report.should_report(report_topics, 4))
//            Report.report(4, "Class file " + name + " has encoded type info");
//          encodedClazz = clazz;
//        }
//      }
//  
//      // Now, try and find the source file using the actual name
//      FabricSourceLoader src_loader = (FabricSourceLoader) ext.sourceLoader();
//      try {
//        source = src_loader.fileSource(uri,false);
//      } catch (IOException e) {
//        Report.report(4, "IOException while loading " + uri + ":" + e.getMessage());
//      }
//  
//      // Check if a job for the source already exists.
//      if (ext.scheduler().sourceHasJob(source)) {
//        //Already compiled. get type using actual name
//        return getTypeFromSource(source, name);
//      }
//  
//      if (Report.should_report(report_topics, 4)) {
//        if (source == null)
//          Report.report(4, "Class " + name + " not found in source file");
//        else Report.report(4, "Class " + name + " found in source " + source);
//      }
//  
//      // Don't use the raw class if the source or encoded class is available.
//      if (encodedClazz != null || source != null) {
//        if (Report.should_report(report_topics, 4))
//          Report.report(4, "Not using raw class file for " + name);
//        clazz = null;
//      }
//  
//      // If both the source and encoded class are available, we decide which to
//      // use based on compiler compatibility and modification times.
//      if (encodedClazz != null && source != null) {
//        long classModTime = encodedClazz.sourceLastModified(version.name());
//        long sourceModTime = source.lastModified().getTime();
//  
//        int comp =
//            checkCompilerVersion(encodedClazz.compilerVersion(version.name()));
//  
//        if (!ignoreModTimes && classModTime < sourceModTime) {
//          if (Report.should_report(report_topics, 3))
//            Report.report(3, "Source file version is newer than compiled for "
//                + name + ".");
//          encodedClazz = null;
//        } else if (comp != COMPATIBLE) {
//          // Incompatible or older version, so go with the source.
//          if (Report.should_report(report_topics, 3))
//            Report
//                .report(3, "Incompatible source file version for " + name + ".");
//          encodedClazz = null;
//        }
//      }
//  
//      Named result = null;
//  
//      if (encodedClazz != null) {
//        if (Report.should_report(report_topics, 4))
//          Report.report(4, "Using encoded class type for " + name);
//        try {
//          result = getEncodedType(encodedClazz, name);
//        } catch (BadSerializationException e) {
//          throw e;
//        } catch (SemanticException e) {
//          if (Report.should_report(report_topics, 4))
//            Report.report(4, "Could not load encoded class " + name);
//          encodedClazz = null;
//        }
//      }
//    
//      if (result == null && source != null) {
//        if (Report.should_report(report_topics, 4))
//          Report.report(4, "Using source file for " + name);
//        result = getTypeFromSource(source, name);
//      }
//  
//      if (result != null) {
//        return result;
//      }
//  
//      if (clazz != null && !this.allowRawClasses) {
//        // We have a raw class only. We do not have the source code,
//        // or encoded class information.
//        throw new SemanticException("Class \"" + name + "\" not found."
//            + " A class file was found at " + clazz.getClassFileLocation()
//            + ", but it did not contain appropriate"
//            + " information for the Polyglot-based compiler "
//            + ext.compilerName() + ". Try using " + ext.compilerName()
//            + " to recompile the source code.");
//      }
//      throw new NoClassException(name);
//    }
//  }

  @Override
  protected Named getTypeFromSource(FileSource source, String name)
      throws SemanticException {
    //System.out.println("LOADING FROM SOURCE: " + name);
    Codebase cb = ((CodebaseSource) source).codebase();
    return super.getTypeFromSource(source, SysUtil.codebasePrefix(cb)+name);
  }

  @Override
  protected ClassType getEncodedType(ClassFile clazz, String name)
      throws SemanticException {
    // TODO Support loading from classfiles
    //System.out.println("LOADING FROM ENCODED CLASS: " + name);
    return super.getEncodedType(clazz, name);
  }
  
}
