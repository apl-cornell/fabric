package fabric;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import jif.visit.LabelChecker;
import polyglot.frontend.Compiler;
import polyglot.frontend.CupParser;
import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.frontend.Parser;
import polyglot.frontend.Scheduler;
import polyglot.frontend.SourceLoader;
import polyglot.frontend.goals.Goal;
import polyglot.lex.Lexer;
import polyglot.types.LoadedClassResolver;
import polyglot.types.SemanticException;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
import fabil.frontend.CodebaseSourceClassResolver;
import fabil.types.ClassFile;
import fabil.types.FabILTypeSystem;
import fabric.ast.FabricNodeFactory;
import fabric.ast.FabricNodeFactory_c;
import fabric.common.SysUtil;
import fabric.frontend.FabricSourceLoader;
import fabric.frontend.LocalSource;
import fabric.frontend.RemoteSource_c;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;
import fabric.parse.Grm;
import fabric.parse.Lexer_c;
import fabric.types.FabricTypeSystem;
import fabric.types.FabricTypeSystem_c;
import fabric.visit.FabricLabelChecker;
import fabric.worker.Store;


/**
 * Extension information for fabric extension.
 */
public class ExtensionInfo extends jif.ExtensionInfo {
  /* Note: jif.ExtensionInfo has a jif.OutputExtensionInfo field jlext.  The
   * only unoverridden place this is used is in a call to initCompiler, so it
   * should never leak out. */
  
  protected OutputExtensionInfo filext = new OutputExtensionInfo(this);
  protected Map<String, byte[]> bytecode;
  static {
    // force Topics to load
    new Topics();
  }

  public ExtensionInfo() {
    super();
  }
  
  public ExtensionInfo(Map<String,byte[]> bytecode) {
    this.bytecode = bytecode;
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
    FabricOptions opts = (FabricOptions) job.extensionInfo().getOptions();
    if(opts.createJavaSkel())
      return scheduler().FabILSkeletonGenerated(job);
    else if(opts.publishOnly()) {
      return scheduler().ConsistentNamespace();
    } else
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
  
  @Override
  public ClassFile createClassFile(File classFileSource, byte[] code) {
      return new ClassFile(classFileSource, code, this);
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
  
  @Override
  protected void initTypeSystem() {
    try {
      LoadedClassResolver lr;
      
      lr = new CodebaseSourceClassResolver(compiler, this, getJifOptions().constructJifClasspath(), 
              compiler.loader(), false,
              getOptions().compile_command_line_only,
              getOptions().ignore_mod_times);
      ts.initialize(lr, this);
    } catch (SemanticException e) {
      throw new InternalCompilerError("Unable to initialize type system: ", e);
    }
  }
  
  @Override
  public SourceLoader sourceLoader() {
    if (source_loader == null) {
      URI file = URI.create("file:///");
      @SuppressWarnings("unchecked")
      Collection<File> sp = getOptions().source_path;
      Collection<URI> cbp = getFabricOptions().codebasePath();        
      Collection<URI> loadpath = new LinkedList<URI>();
      for(File f : sp) {
        URI uri = URI.create(f.getAbsolutePath());
        loadpath.add(file.resolve(uri));
      }
      loadpath.addAll(cbp);
      source_loader = new FabricSourceLoader(this, loadpath);
    }
    return source_loader;
  }

  public FabricOptions getFabricOptions() {
    return (FabricOptions) getOptions();
  }
  
  public FileSource createRemoteSource(FClass fcls, boolean user) throws IOException {
    if (!LabelUtil._Impl.relabelsTo(fcls.get$label(),fcls.getCodebase().get$label()))
      //XXX: should we throw a more security-related exception here?
      throw new IOException("The label of class "
          + SysUtil.absoluteName(fcls)
          + " is higher than the label of its codebase ");
      return new RemoteSource_c(fcls, user);
  }
  
  @Override
  public FileSource createFileSource(File f, boolean user) throws IOException {
    return new LocalSource(f, user, filext.codebase());
  }

  public Codebase codebase() {
    return filext.codebase();
  }
  
  public Store destinationStore() {
    return filext.destinationStore();
  }

  public Label destinationLabel() {
    return filext.destinationLabel();
  }
  
}
