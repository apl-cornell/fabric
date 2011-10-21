package fabil;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import polyglot.ast.NodeFactory;
import polyglot.frontend.CupParser;
import polyglot.frontend.FileSource;
import polyglot.frontend.Parser;
import polyglot.frontend.Scheduler;
import polyglot.frontend.SourceLoader;
import polyglot.frontend.TargetFactory;
import polyglot.lex.Lexer;
import polyglot.main.Options;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.types.reflect.ClassFileLoader;
import polyglot.types.reflect.ClassPathLoader;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
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
import fabil.ast.FabILNodeFactory;
import fabil.ast.FabILNodeFactory_c;
import fabil.frontend.FabILScheduler;
import fabil.parse.Grm;
import fabil.parse.Lexer_c;
import fabil.types.ClassFile;
import fabil.types.FabILTypeSystem;
import fabil.types.FabILTypeSystem_c;
import fabric.common.NSUtil;
import fabric.common.SysUtil;
import fabric.lang.FClass;
import fabric.lang.security.LabelUtil;
import fabric.worker.Worker;

/**
 * Extension information for FabIL extension.
 */
public class ExtensionInfo extends polyglot.frontend.JLExtensionInfo implements codebases.frontend.ExtensionInfo {
  protected static URI platform_ns = URI.create("fab:platform");
  protected static URI local_ns = URI.create("fab:local");

  static {
    // force Topics to load
    new Topics();
  }
  protected CBTypeEncoder typeEncoder;

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
          new CBTargetFactory(this, getFabILOptions().outputDirectory(),
              getOptions().output_ext, getOptions().output_stdout);
    }

    return target_factory;
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

  @Override
  public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
    CodebaseSource src = (CodebaseSource) source;
    Lexer lexer = new Lexer_c(reader, source, eq);
    Grm grm = new Grm(lexer, ts, nf, eq, src.canonicalNamespace());
    return new CupParser(grm, source, eq);
  }
  
  @Override
  protected void initTypeSystem() {
    try {
      // There isn't a single toplevel resolver.
      ((CodebaseTypeSystem)ts).initialize(this);
    } catch (SemanticException e) {
      throw new InternalCompilerError("Unable to initialize type system: ", e);
    }
  }

  @Override
  public FabILTypeSystem typeSystem() {
    return (FabILTypeSystem) super.typeSystem();
  }

  @Override
  public FabILNodeFactory nodeFactory() {
    return (FabILNodeFactory) super.nodeFactory();
  }

  @Override
  public String defaultFileExtension() {
    return "fil";
  }

  @Override
  public Version version() {
    return new Version();
  }

  public FabILOptions getFabILOptions() {
    return (FabILOptions) getOptions();
  }

//  public Codebase codebase() {
//    return codebase;
//  }

  @Override
  public FileSource createFileSource(File f, boolean user) throws IOException {
    if (Report.should_report(Report.loader, 2))
      Report.report(2, "Creating filesource from " + f);

    return new LocalSource(f, user, localNamespace());
  }

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

  /**
   * Creates namespace resolvers for FabIL namespaces.
   * 
   * @param ns
   * @return
   */
  public NamespaceResolver createNamespaceResolver(URI ns) {
    if (Report.should_report("resolver", 3))
      Report.report(3, "Creating namespace resolver for " + ns);

    FabILOptions opt = (FabILOptions) getOptions();
    //XXX: Order is important here since the localnamespace may
    // by the platform namespace when compiling the runtime
    if (ns.equals(platformNamespace())) {
      // A platform resolver is really just a path resolver that is treated
      // specially. Loading the appropriate platform classes and signatures
      // is handled by the classpathloader and sourceloader

      List<NamespaceResolver> path = new ArrayList<NamespaceResolver>();
      path.addAll(typeSystem().signatureResolvers());
      path.addAll(typeSystem().runtimeResolvers());

      // FabIL also allows direct linking to Java classes, so we
      // include the JRE classes here. Other Java classes should be
      // specified on the classpath
      // TODO: Make this play nice with cmdline args
      String java_path = System.getProperty("sun.boot.class.path");
      for (String dir : java_path.split(File.pathSeparator)) {
        File f = new File(dir);
        NamespaceResolver nr =
            new SimpleResolver(this, NSUtil.file.resolve(f.getAbsolutePath()));
        nr.loadRawClasses(true);
        path.add(nr);
      }
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

  //TODO: support multiple platform namespaces

  @Override
  public URI platformNamespace() {
    return platform_ns;
  }

  //TODO: support multiple local namespaces
  @Override
  public URI localNamespace() {
    return getFabILOptions().platformMode() ? platformNamespace() : local_ns;
  }

  @Override
  public CBTypeEncoder typeEncoder() {
    if (typeEncoder == null) {
      typeEncoder = new CBTypeEncoder(ts);
    }
    return typeEncoder;
  }

  // Loads source files
  @Override
  public URISourceLoader sourceLoader(URI uri) {
    if ("fab".equals(uri.getScheme())) {
      if (uri.isOpaque())
        throw new InternalCompilerError("Unexpected URI:" + uri);
      return new CodebaseSourceLoader(this, uri);
    } else if ("file".equals(uri.getScheme())) {
      return new FileSourceLoader(this, uri);
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + uri);
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
      String cachedir = getFabILOptions().outputDirectory() + File.separator
          + java_pkg.replace('.', File.separatorChar);          
      return new ClassPathLoader(cachedir, new ClassFileLoader(this));
      
    } else if ("file".equals(ns.getScheme())) {
      return new ClassPathLoader(ns.getPath(), new ClassFileLoader(this));
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + ns);
  }

  @Override
  public String namespaceToJavaPackagePrefix(URI ns) {
    if (ns.equals(localNamespace()) || ns.equals(platformNamespace()))
      return "";
    else if (ns.getScheme().equals("fab")) {
      return NSUtil.javaPackageName(ns) + ".";
    }
    else {
      throw new InternalCompilerError("Cannot create Java package prefix for " + ns);
    }
      
  }

  @Override
  public List<URI> classpath() {
    return getFabILOptions().classpath();
  }

  @Override
  public List<URI> sourcepath() {
    return getFabILOptions().sourcepath();
  }

  @Override
  public ClassFile createClassFile(File classFileSource, byte[] code) {
      return new ClassFile(classFileSource, code, this);
  }

  @Override
  public List<URI> signaturepath() {
    return getFabILOptions().signaturepath();
  }

  @Override
  public List<URI> bootclasspath() {
    return getFabILOptions().bootclasspath();
  }

  @Override
  public Map<String, URI> codebaseAliases() {
    return getFabILOptions().codebaseAliases();
  }

  @Override
  public fabric.worker.Store destinationStore() {
    // Worker must be running!
    if (!Worker.isInitialized())
      throw new InternalCompilerError("Worker is not initialized.");

    String storeName = getFabILOptions().destinationStore();

    if (storeName == null)
      return Worker.getWorker().getLocalStore();
    return Worker.getWorker().getStore(storeName);
  }
}
