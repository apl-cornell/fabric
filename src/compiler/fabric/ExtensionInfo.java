package fabric;

import java.io.Reader;

import jif.visit.LabelChecker;

import polyglot.frontend.*;
import polyglot.frontend.Compiler;
import polyglot.frontend.goals.Goal;
import polyglot.lex.Lexer;
import polyglot.util.ErrorQueue;
import fabil.types.FabILTypeSystem;
import fabric.ast.FabricNodeFactory;
import fabric.ast.FabricNodeFactory_c;
import fabric.parse.Grm;
import fabric.parse.Lexer_c;
import fabric.types.FabricTypeSystem;
import fabric.types.FabricTypeSystem_c;
import fabric.visit.FabricLabelChecker;

/**
 * Extension information for fabric extension.
 */
public class ExtensionInfo extends jif.ExtensionInfo {
  /* Note: jif.ExtensionInfo has a jif.OutputExtensionInfo field jlext.  The
   * only unoverridden place this is used is in a call to initCompiler, so it
   * should never leak out. */
  
  protected fabil.ExtensionInfo filext = new OutputExtensionInfo(this);
  
  static {
    // force Topics to load
    new Topics();
  }

  public ExtensionInfo() {
    super();
  }
  
  @Override
  public void initCompiler(Compiler compiler) {
     super.initCompiler(compiler);
    filext.initCompiler(compiler);
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
  public Goal getCompileGoal(Job job) {
    return scheduler().FabricToFabilRewritten(job);
  }

  @Override
  public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
    Lexer lexer = new Lexer_c(reader, source, eq);
    Grm grm     = new Grm(lexer, typeSystem(), nodeFactory(), eq);
    return new CupParser(grm, source, eq);
  }
  
  @Override
  public FabILTypeSystem jlTypeSystem() {
    return filext.typeSystem();
  }

  /* Overridden Factory Methods ***********************************************/
  
  @Override
  protected FabricNodeFactory createNodeFactory() {
    return new FabricNodeFactory_c();
  }

  @Override
  protected FabricTypeSystem createTypeSystem() {
    return new FabricTypeSystem_c(jlext.typeSystem());
  }
  
  @Override
  protected FabricOptions createOptions() {
    return new FabricOptions(this);
  }
  
  @Override
  protected Scheduler createScheduler() {
    return new FabricScheduler(this, this.filext);
  }
  
  /* Overridden typed accessors ***********************************************/
  
  @Override
  public FabricNodeFactory nodeFactory() {
    return (FabricNodeFactory) super.nodeFactory();
  }
  
  @Override
  public FabricTypeSystem typeSystem() {
    return (FabricTypeSystem) super.typeSystem();
  }

  @Override
  public FabricScheduler scheduler() {
    return (FabricScheduler) super.scheduler();
  }

  @Override
  public Version version() {
    return new Version();
  }
  
  @Override
  public LabelChecker createLabelChecker(Job job, boolean solvePerClassBody, boolean solvePerMethod, boolean doLabelSubst) {
    return new FabricLabelChecker(job, typeSystem(), nodeFactory(), solvePerClassBody, solvePerMethod, doLabelSubst);
  }
}
