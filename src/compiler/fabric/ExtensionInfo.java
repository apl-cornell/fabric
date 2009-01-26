package fabric;

import java.io.Reader;

import polyglot.ast.NodeFactory;
import polyglot.frontend.CupParser;
import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.frontend.Parser;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.lex.Lexer;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import fabric.ast.FabricNodeFactory;
import fabric.ast.FabricNodeFactory_c;
import fabric.parse.Grm;
import fabric.parse.Lexer_c;
import fabric.types.FabricTypeSystem;
import fabric.types.FabricTypeSystem_c;

/**
 * Extension information for ../../fabric extension.
 */
public class ExtensionInfo extends jif.ExtensionInfo {
  static {
    // force Topics to load
    new Topics();
  }

  @Override
  public String defaultFileExtension() {
    return "fab";
  }

  @Override
  public String compilerName() {
    return "fabc";
  }

  @Override
  public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
    Lexer lexer = new Lexer_c(reader, source, eq);
    Grm grm = new Grm(lexer, (FabricTypeSystem) ts, (FabricNodeFactory) nf, eq);
    return new CupParser(grm, source, eq);
  }

  @Override
  protected NodeFactory createNodeFactory() {
    return new FabricNodeFactory_c();
  }

  @Override
  protected TypeSystem createTypeSystem() {
    return new FabricTypeSystem_c(jlext.typeSystem());
  }

  @Override
  protected FabricOptions createOptions() {
    return new FabricOptions(this);
  }
  
  @Override
  protected Scheduler createScheduler() {
    return new FabricScheduler(this, this.jlext);
  }
  
  @Override
  public FabricScheduler scheduler() {
    return (FabricScheduler) super.scheduler();
  }
  
  @Override
  public Goal getCompileGoal(Job job) {
    return scheduler().HandoffToFil(job);
  }

  public ExtensionInfo() {
    super();
    this.jlext = new OutputExtensionInfo(this);
  }
}
