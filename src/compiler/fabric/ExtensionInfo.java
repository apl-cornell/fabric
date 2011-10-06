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
import polyglot.frontend.Parser;
import polyglot.frontend.Scheduler;
import polyglot.frontend.SourceLoader;
import polyglot.frontend.goals.Goal;
import polyglot.lex.Lexer;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.reflect.ClassFileLoader;
import polyglot.types.reflect.ClassPathLoader;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
import polyglot.util.TypeEncoder;
import codebases.frontend.CodebaseSourceLoader;
import codebases.frontend.FileSourceLoader;
import codebases.frontend.LocalSource;
import codebases.frontend.RemoteSource;
import codebases.frontend.URISourceDispatcher;
import codebases.frontend.URISourceLoader;
import codebases.types.CodebaseResolver;
import codebases.types.CodebaseTypeSystem;
import codebases.types.PathResolver;
import codebases.types.NamespaceResolver;
import codebases.types.NamespaceResolver_c;
import fabil.SimpleResolver;
import fabil.types.FabILTypeSystem;
import fabric.ast.FabricNodeFactory;
import fabric.ast.FabricNodeFactory_c;
import fabric.common.SysUtil;
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
public class ExtensionInfo extends jif.ExtensionInfo implements codebases.frontend.ExtensionInfo {
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
    if(opts.createSkeleton())
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
    return new LocalSource(f, user, localNamespace());
  }
  
  public Store destinationStore() {
    return filext.destinationStore();
  }

  @Override
  public TypeEncoder typeEncoder() {
    return new TypeEncoder(ts);
  }

  // Loads source files
  @Override
  public URISourceLoader sourceLoader(URI uri) {
    if ("fab".equals(uri.getScheme())) {
      if(uri.isOpaque())
        throw new InternalCompilerError("Unexpected URI:" + uri);
      return new CodebaseSourceLoader(this, uri);
    } else if ("file".equals(uri.getScheme())) {
      return new FileSourceLoader(this, uri);
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + uri);
  }

  //Loads class files
  @Override
  public ClassPathLoader classpathLoader(URI uri) {
    if ("fab".equals(uri.getScheme())) {
      // Load previously compiled classes from cache
      if(uri.isOpaque())
        throw new InternalCompilerError("Unexpected URI:" + uri);
 
      String store = uri.getAuthority();
      long onum = Long.parseLong(uri.getPath().substring(1));   
      
      //At the Fabric/FabIL layer, class names do not include the codebases
      String cachedir = getFabricOptions().output_directory + File.separator
          + SysUtil.pseudoname(store, onum).replace('.', File.separatorChar);          
      return new ClassPathLoader(cachedir, new ClassFileLoader(this));
      
    } else if ("file".equals(uri.getScheme())) {
      return new ClassPathLoader(uri.getPath(), new ClassFileLoader(this));
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + uri);
  }

  // Resolves types
  public NamespaceResolver createNamespaceResolver(URI ns) {
    if (Report.should_report("resolver", 3))
      Report.report(3, "Creating namespace resolver for " + ns);

    if ("fab".equals(ns.getScheme())) {
      if(ns.getSchemeSpecificPart().startsWith("local")) {
        List<NamespaceResolver> path = new ArrayList<NamespaceResolver>();
        path.add(typeSystem().platformResolver());
        path.addAll(typeSystem().classpathResolvers());
        path.addAll(typeSystem().sourcepathResolvers());
        return new PathResolver(this, ns, path, getFabricOptions().codebaseAliases());
      }
      else if(ns.getSchemeSpecificPart().startsWith("platform")) {
        // A platform resolver is really just a local resolver that is treated
        // specially.
        // Loading the appropriate platform classes and signatures
        // is handled by the classpathloader and sourceloader
        List<NamespaceResolver> path = new ArrayList<NamespaceResolver>();
        path.addAll(typeSystem().runtimeResolvers());
        path.addAll(typeSystem().signatureResolvers());
        return new PathResolver(this, ns, path);
      }
      else {
        List<NamespaceResolver> path = new ArrayList<NamespaceResolver>(2);
        //Codebases may never resolve platform types.
        path.add(typeSystem().platformResolver());
        path.add(new CodebaseResolver(this, ns));
        return new PathResolver(this, ns, path);
      }
    } else if ("file".equals(ns.getScheme())) {
      return new SimpleResolver(this, ns);
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + ns);
  }

  //TODO: support multiple platform namespaces
  public URI platformNamespace() {
    return filext.platformNamespace();
  }

  public URI localNamespace() {
    return filext.localNamespace();
  }  
  
}
