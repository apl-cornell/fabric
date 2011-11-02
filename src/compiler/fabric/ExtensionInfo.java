package fabric;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jif.visit.LabelChecker;
import polyglot.frontend.Compiler;
import polyglot.frontend.CupParser;
import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.frontend.JobExt;
import polyglot.frontend.Parser;
import polyglot.frontend.Scheduler;
import polyglot.frontend.SourceLoader;
import polyglot.frontend.TargetFactory;
import polyglot.frontend.goals.Goal;
import polyglot.lex.Lexer;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.reflect.ClassFileLoader;
import polyglot.types.reflect.ClassPathLoader;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
import codebases.frontend.CBJobExt;
import codebases.frontend.CBTargetFactory;
import codebases.frontend.CodebaseSource;
import codebases.frontend.CodebaseSourceLoader;
import codebases.frontend.FileSourceLoader;
import codebases.frontend.LocalSource;
import codebases.frontend.RemoteSource;
import codebases.frontend.URISourceDispatcher;
import codebases.frontend.URISourceLoader;
import codebases.types.CBTypeEncoder;
import codebases.types.CodebaseResolver;
import codebases.types.CodebaseTypeSystem;
import codebases.types.NamespaceResolver;
import codebases.types.PathResolver;
import codebases.types.SafeResolver;
import codebases.types.SimpleResolver;
import fabil.types.ClassFile;
import fabil.types.FabILTypeSystem;
import fabric.ast.FabricNodeFactory;
import fabric.ast.FabricNodeFactory_c;
import fabric.common.NSUtil;
import fabric.common.SysUtil;
import fabric.lang.FClass;
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
public class ExtensionInfo extends jif.ExtensionInfo implements codebases.frontend.ExtensionInfo {
  /* Note: jif.ExtensionInfo has a jif.OutputExtensionInfo field jlext.  The
   * only unoverridden place this is used is in a call to initCompiler, so it
   * should never leak out. */
  
  protected OutputExtensionInfo filext = new OutputExtensionInfo(this);
  protected Map<String, byte[]> bytecode;
  protected CBTypeEncoder typeEncoder;
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
  protected void initTypeSystem() {
    try {
      ((CodebaseTypeSystem)ts).initialize(this);
    } catch (SemanticException e) {
      throw new InternalCompilerError("Unable to initialize type system: ", e);
    }
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
    if (opts.createSkeleton())
      return scheduler().FabILSkeletonGenerated(job);
    else if (opts.publishOnly()) {
       return scheduler().Serialized(job);
    } else
      return scheduler().FabricToFabilRewritten(job);
  }
  @Override
   public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
      CodebaseSource src = (CodebaseSource) source;
      Lexer lexer = new Lexer_c(reader, source, eq);
      Grm grm     = new Grm(lexer, typeSystem(), nodeFactory(), eq, src.canonicalNamespace());
      return new CupParser(grm, source, eq);
    }

  @Override
  public FabILTypeSystem jlTypeSystem() {
    return filext.typeSystem();
  }

  
  /* Overridden Factory Methods ***********************************************/
  @Override
  public CBTypeEncoder typeEncoder() {
    if (typeEncoder == null) {
      typeEncoder = new CBTypeEncoder(ts);
    }
    return typeEncoder;
  }


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
  
  @Override
  public JobExt jobExt() {
      return new CBJobExt();
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
  public SourceLoader sourceLoader() {
    //Create a dispatcher that routes ns's to the appropriate 
    // loader
    if (source_loader == null) {
      source_loader = new URISourceDispatcher(this);
    }
    return source_loader;
  }

  public FabricOptions getFabricOptions() {
    return (FabricOptions) getOptions();
  }
  
  // XXX: the obj argument really should be some more general interface that
  // FClass implements
  @Override
  public FileSource createRemoteSource(URI ns, fabric.lang.Object obj, boolean user)
      throws IOException {
    if (!(obj instanceof FClass))
      throw new InternalCompilerError("Expected FClass.");
    
    FClass fcls = (FClass) obj;    
    if (!LabelUtil._Impl.relabelsTo(fcls.get$label(), fcls.getCodebase()
        .get$label())) {
      // XXX: should we throw a more security-related exception here?
      throw new IOException("The label of class " + SysUtil.absoluteName(fcls)
          + " is higher than the label of its codebase ");
    }
    
    return new RemoteSource(ns, fcls, user);
  }
  
  @Override
  //TODO: support multiple local namespaces
  public LocalSource createFileSource(File f, boolean user) throws IOException {
    LocalSource src = new LocalSource(f, user, localNamespace());
    //Publish all local source unless we're in platform mode.
    //TODO: generalize and make this better.  We should only publish 
    // source in the sourcepath. Plus, the user may be re-publishing remote 
    // source with a new codebase.
    src.setPublish(getFabricOptions().publish());
    return src;
  }
  
  @Override
  public Store destinationStore() {
    return filext.destinationStore();
  }

  // Loads source files
  @Override
  public URISourceLoader sourceLoader(URI ns) {
    if ("fab".equals(ns.getScheme())) {
      if (ns.isOpaque())
        throw new InternalCompilerError("Unexpected URI:" + ns);
      return new CodebaseSourceLoader(this, ns);
    } else if ("file".equals(ns.getScheme())) {
      return new FileSourceLoader(this, ns);
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + ns);
  }

  //Loads class files
  @Override
  public ClassPathLoader classpathLoader(URI ns) {
    if ("fab".equals(ns.getScheme())) {
      // Load previously compiled classes from cache
      if (ns.isOpaque())
        throw new InternalCompilerError("Unexpected URI:" + ns);
 
      String java_pkg = NSUtil.javaPackageName(ns);      
      // At the Fabric/FabIL layer, class names do not include the codebases,
      // so we turn the java package name into a directory name and create a
      // classpath loader that will search for class files there.
      String cachedir = getFabricOptions().outputDirectory() + File.separator
          + java_pkg.replace('.', File.separatorChar);          
      return new ClassPathLoader(cachedir, new ClassFileLoader(this));
      
    } else if ("file".equals(ns.getScheme())) {
      return new ClassPathLoader(ns.getPath(), new ClassFileLoader(this));
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + ns);
  }

  /**
   * Creates namespace resolvers for Fabric namespaces.
   * 
   * @param ns
   * @return
   */
  public NamespaceResolver createNamespaceResolver(URI ns) {
    if (ns == null)
      throw new NullPointerException("Namespace is null");
    if (Report.should_report("resolver", 3))
      Report.report(3, "Creating namespace resolver for " + ns);

    FabricOptions opt = getFabricOptions();
    //XXX: Order is important here since the localnamespace may
    // by the platform namespace when compiling the runtime
    if (ns.equals(platformNamespace())) {
      // A platform resolver is really just a path resolver that is treated
      // specially. Loading the appropriate platform classes and signatures
      // is handled by the classpathloader and sourceloader
      List<NamespaceResolver> path = new ArrayList<NamespaceResolver>();
      path.addAll(typeSystem().signatureResolvers());
      path.addAll(typeSystem().runtimeResolvers());
      return new PathResolver(this, ns, path);

    } else if (ns.equals(localNamespace())) {
      List<NamespaceResolver> path = new ArrayList<NamespaceResolver>();
      path.add(typeSystem().platformResolver());
      path.addAll(typeSystem().classpathResolvers());
      path.addAll(typeSystem().sourcepathResolvers());
      return new PathResolver(this, ns, path, opt.codebaseAliases());

    } else if ("fab".equals(ns.getScheme())) {
      // Codebases may never resolve platform types, so always resolve against
      //  the platformResolver first.
      return new SafeResolver(this, new CodebaseResolver(this, ns));    

    } else if ("file".equals(ns.getScheme())) {
      return new SimpleResolver(this, ns);

    } else throw new InternalCompilerError("Unexpected scheme in URI: " + ns);
  }

  @Override
  public URI platformNamespace() {
    return filext.platformNamespace();
  }

  @Override
  public URI localNamespace() {
    return filext.localNamespace();
  }

  @Override
  public String namespaceToJavaPackagePrefix(URI ns) {
    return filext.namespaceToJavaPackagePrefix(ns);
  }    
  
  @Override
  public TargetFactory targetFactory() {
    if (target_factory == null) {
      target_factory =
          new CBTargetFactory(this, getFabricOptions().outputDirectory(),
              getOptions().output_ext, getOptions().output_stdout);
    }

    return target_factory;
  }

  @Override
  public List<URI> classpath() {
    return getFabricOptions().classpath();
  }

  @Override
  public List<URI> sourcepath() {
    return getFabricOptions().sourcepath();
  }

  @Override
  public List<URI> signaturepath() {
    return getFabricOptions().signaturepath();
  }
  
  public List<URI> fabILSignaturePath() {
    return getFabricOptions().fabILSignaturePath();
  }

  @Override
  public List<URI> bootclasspath() {
    return getFabricOptions().bootclasspath();
  }

  @Override
  public Map<String, URI> codebaseAliases() {
    return getFabricOptions().codebaseAliases();
  }

}
