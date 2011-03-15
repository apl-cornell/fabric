package fabil;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;

import polyglot.ast.NodeFactory;
import polyglot.frontend.CupParser;
import polyglot.frontend.FileSource;
import polyglot.frontend.Parser;
import polyglot.frontend.Scheduler;
import polyglot.frontend.TargetFactory;
import polyglot.lex.Lexer;
import polyglot.main.Options;
import polyglot.types.LoadedClassResolver;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import fabil.ast.FabILNodeFactory;
import fabil.ast.FabILNodeFactory_c;
import fabil.frontend.CodebaseSourceClassResolver;
import fabil.frontend.CodebaseTargetFactory;
import fabil.frontend.FabILScheduler;
import fabil.frontend.LocalSource;
import fabil.frontend.RemoteSource;
import fabil.parse.Grm;
import fabil.parse.Lexer_c;
import fabil.types.FabILTypeSystem;
import fabil.types.FabILTypeSystem_c;
import fabric.lang.Codebase;
import fabric.lang.FClass;

/**
 * Extension information for FabIL extension.
 */
public class ExtensionInfo extends polyglot.frontend.JLExtensionInfo {
  //Map<Codebase, TargetFactory> codebaseTargets = new HashMap<Codebase, TargetFactory>();
  protected Codebase codebase = null;

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
    return new FabILOptions_c(this);
  }

  @Override
  protected Scheduler createScheduler() {
    return new FabILScheduler(this);
  }

  @Override
  protected TypeSystem createTypeSystem() {
    return new FabILTypeSystem_c();
  }

  @Override
  public TargetFactory targetFactory() {
    if (target_factory == null) {
      target_factory =
          new CodebaseTargetFactory(getOptions().output_directory,
              getOptions().output_ext, getOptions().output_stdout);
    }

    return target_factory;
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
    return "fil";
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
    FabILOptions options = (FabILOptions) getOptions();
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
    return new CodebaseSourceClassResolver(compiler,
                                   this,
                                   getFabILOptions().constructFabILClasspath(),
                                   compiler.loader(),
                                   true,
                                   getOptions().compile_command_line_only,
                                   getOptions().ignore_mod_times);
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
  
  public FabILOptions getFabILOptions() {
    return (FabILOptions) getOptions();
  }
  
  public Codebase codebase() {
    return codebase;
  }
  
  @Override
  public FileSource createFileSource(File f, boolean user) throws IOException {
    return new LocalSource(f, user, codebase());
  }
  
  public FileSource createFileSource(String path, String name, Date lastModified, boolean user, Codebase codebase) throws IOException {
    return new LocalSource(path, name, lastModified, user, codebase());
  }
  
  public FileSource createRemoteSource(FClass fcls, boolean user) throws IOException {
    return new RemoteSource(fcls, user);
  }

}
