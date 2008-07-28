package fabil;

import java.io.Reader;

import polyglot.ast.NodeFactory;
import polyglot.frontend.CupParser;
import polyglot.frontend.FileSource;
import polyglot.frontend.Parser;
import polyglot.frontend.Scheduler;
import polyglot.lex.Lexer;
import polyglot.types.LoadedClassResolver;
import polyglot.types.SourceClassResolver;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import fabil.ast.FabILNodeFactory;
import fabil.ast.FabILNodeFactory_c;
import fabil.frontend.FabILScheduler;
import fabil.parse.Grm;
import fabil.parse.Lexer_c;
import fabil.types.FabILTypeSystem;
import fabil.types.FabILTypeSystem_c;

/**
 * Extension information for FabIL extension.
 */
public class ExtensionInfo extends polyglot.frontend.JLExtensionInfo {
  static {
    // force Topics to load
    new Topics();
  }

  public ExtensionInfo() {
  }

  @Override
  public String compilerName() {
    return "filc";
  }

  @Override
  protected NodeFactory createNodeFactory() {
    return new FabILNodeFactory_c();
  }

  @Override
  protected Options createOptions() {
    return new Options(this);
  }

  @Override
  protected Scheduler createScheduler() {
    return new FabILScheduler(this);
  }

  @Override
  protected TypeSystem createTypeSystem() {
    return new FabILTypeSystem_c();
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.frontend.AbstractExtensionInfo#getOptions()
   */
  @Override
  public Options getOptions() {
    return (Options) super.getOptions();
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.frontend.AbstractExtensionInfo#typeSystem()
   */
  @Override
  public FabILTypeSystem typeSystem() {
    return (FabILTypeSystem) super.typeSystem();
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.frontend.AbstractExtensionInfo#nodeFactory()
   */
  @Override
  public FabILNodeFactory nodeFactory() {
    return (FabILNodeFactory) super.nodeFactory();
  }

  @Override
  public String defaultFileExtension() {
    return "fab";
  }

  @Override
  protected void initTypeSystem() {
    super.initTypeSystem();

    // Also give the FabIL type system a resolver that ignores source files.
    // This is needed for the type system to resolve types like
    // fabric.lang.arrays.ObjectArray that
    // are compiled from Fabric. For these types, we want to see the Java
    // translation of the class, not the original Fabric source.
    // See fabil.types.FabILTypeSystem_c.fArrayImplOf(polyglot.types.Type)
    Options options = getOptions();
    String classpath = options.constructFabILClasspath();
    LoadedClassResolver lcr = new LoadedClassResolver(ts, classpath,
        compiler.loader(), version(), true);
    ((FabILTypeSystem) ts).setRuntimeClassResolver(lcr);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.frontend.ParserlessJLExtensionInfo#makeLoadedClassResolver()
   */
  @Override
  protected LoadedClassResolver makeLoadedClassResolver() {
    Options options = getOptions();
    String cp = options.constructFabILClasspath();
    return new SourceClassResolver(compiler, this, cp, compiler.loader(), true,
        options.compile_command_line_only, options.ignore_mod_times);
  }

  @Override
  public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
    Lexer lexer = new Lexer_c(reader, source, eq);
    Grm grm = new Grm(lexer, ts, nf, eq);
    return new CupParser(grm, source, eq);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.frontend.ParserlessJLExtensionInfo#version()
   */
  @Override
  public Version version() {
    return new Version();
  }

}
