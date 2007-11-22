package fabric;

import java.io.Reader;

import polyglot.ast.NodeFactory;
import polyglot.frontend.*;
import polyglot.lex.Lexer;
import polyglot.types.LoadedClassResolver;
import polyglot.types.SourceClassResolver;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import fabric.ast.FabricNodeFactory_c;
import fabric.frontend.FabricScheduler;
import fabric.parse.Grm;
import fabric.parse.Lexer_c;
import fabric.types.FabricTypeSystem;
import fabric.types.FabricTypeSystem_c;

/**
 * Extension information for fabric extension.
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
    return "loom";
  }

  @Override
  protected NodeFactory createNodeFactory() {
    return new FabricNodeFactory_c();
  }

  @Override
  protected Options createOptions() {
    return new Options(this);
  }

  @Override
  protected Scheduler createScheduler() {
    return new FabricScheduler(this);
  }

  @Override
  protected TypeSystem createTypeSystem() {
    return new FabricTypeSystem_c();
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
  public FabricTypeSystem typeSystem() {
    return (FabricTypeSystem) super.typeSystem();
  }

  @Override
  public String defaultFileExtension() {
    return "fab";
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.frontend.ParserlessJLExtensionInfo#makeLoadedClassResolver()
   */
  @Override
  protected LoadedClassResolver makeLoadedClassResolver() {
    Options options = getOptions();
    String cp = options.constructFabricClasspath();
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
