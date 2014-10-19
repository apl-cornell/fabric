package fabil;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.StandardLocation;

import polyglot.ast.NodeFactory;
import polyglot.filemanager.FileManager;
import polyglot.frontend.CupParser;
import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.frontend.JobExt;
import polyglot.frontend.Parser;
import polyglot.frontend.Scheduler;
import polyglot.frontend.Source;
import polyglot.frontend.Source.Kind;
import polyglot.frontend.TargetFactory;
import polyglot.frontend.goals.Goal;
import polyglot.lex.Lexer;
import polyglot.main.Options;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
import codebases.frontend.CBJobExt;
import codebases.frontend.CBTargetFactory;
import codebases.frontend.CodebaseSource;
import codebases.frontend.LocalSource;
import codebases.frontend.RemoteSource;
import codebases.types.CBTypeEncoder;
import codebases.types.ClassFile;
import codebases.types.ClassFile_c;
import codebases.types.CodebaseResolver;
import codebases.types.CodebaseTypeSystem;
import codebases.types.NamespaceResolver;
import codebases.types.SimpleResolver;
import fabil.ast.FabILNodeFactory;
import fabil.ast.FabILNodeFactory_c;
import fabil.frontend.FabILScheduler;
import fabil.parse.Grm;
import fabil.parse.Lexer_c;
import fabil.types.FabILTypeSystem;
import fabil.types.FabILTypeSystem_c;
import fabric.LocalResolver;
import fabric.common.NSUtil;
import fabric.filemanager.FabricFileManager;
import fabric.filemanager.FabricFileObject;
import fabric.lang.FClass;
import fabric.lang.security.LabelUtil;
import fabric.worker.Worker;

/**
 * Extension information for FabIL extension.
 */
public class ExtensionInfo extends polyglot.frontend.JLExtensionInfo implements
codebases.frontend.ExtensionInfo {
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
  public FileManager createFileManager() {
    return new FabricFileManager(this);
  }

  @Override
  protected void configureFileManager() throws IOException {
    FabILOptions options = getOptions();
    ((FabricFileManager) extFM).setLocation(options.classpath,
        options.classpathURIs());
    ((FabricFileManager) extFM).setLocation(options.source_path,
        options.sourcepathURIs());

    extFM.setLocation(options.source_output,
        Collections.singleton(options.sourceOutputDirectory()));
    extFM.setLocation(options.class_output,
        Collections.singleton(options.classOutputDirectory()));

    List<File> platform_directories = new ArrayList<>();
    platform_directories.addAll(options.signaturepath());
    platform_directories.addAll(options.bootclasspath());
    extFM.setLocation(options.bootclasspath, platform_directories);
  }

  @Override
  public void configureFileManagerForPostCompiler() throws IOException {
    FabILOptions opt = getOptions();

    extFM.setLocation(StandardLocation.PLATFORM_CLASS_PATH,
        opt.defaultPlatformClasspath());

    List<File> sourcepath =
        Collections.singletonList(opt.sourceOutputDirectory());
    extFM.setLocation(StandardLocation.SOURCE_PATH, sourcepath);

    List<File> classpath = new ArrayList<>();
    classpath.addAll(opt.bootclasspathDirectories());
    for (URI u : opt.classpathURIs()) {
      if (u.getScheme().equals("file")) {
        classpath.add(new File(u));
      }
    }
    extFM.setLocation(StandardLocation.CLASS_PATH, classpath);

    List<File> classout = Collections.singletonList(opt.classOutputDirectory());
    extFM.setLocation(StandardLocation.CLASS_OUTPUT, classout);
  }

  @Override
  protected NodeFactory createNodeFactory() {
    return new FabILNodeFactory_c();
  }

  @Override
  protected Options createOptions() {
    return new FabILOptions(this);
  }

  @Override
  protected Scheduler createScheduler() {
    return new FabILScheduler(this);
  }

  @Override
  public ClassFile createClassFile(FileObject classFileSource, byte[] code)
      throws IOException {
    return new ClassFile_c(classFileSource, code, this);
  }

  @Override
  protected TypeSystem createTypeSystem() {
    return new FabILTypeSystem_c();
  }

  @Override
  public TargetFactory targetFactory() {
    if (target_factory == null) {
      target_factory =
          new CBTargetFactory(this, extFileManager(), getOptions()
              .outputLocation(), getOptions().output_ext,
              getOptions().output_stdout);
    }

    return target_factory;
  }

  @Override
  public Parser parser(Reader reader, Source source, ErrorQueue eq) {
    CodebaseSource src = (CodebaseSource) source;
    Lexer lexer = new Lexer_c(reader, source, eq);
    Grm grm = new Grm(lexer, ts, nf, eq, src.canonicalNamespace());
    return new CupParser(grm, source, eq);
  }

  @Override
  public Set<String> keywords() {
    return new Lexer_c(null).keywords();
  }

  @Override
  protected void initTypeSystem() {
    try {
      // There isn't a single toplevel resolver.
      ((CodebaseTypeSystem) ts).initialize(this);
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

  @Override
  public FabILOptions getOptions() {
    return (FabILOptions) super.getOptions();
  }

  @Override
  public FileSource createFileSource(FileObject f, Kind kind)
      throws IOException {
    if (f instanceof FabricFileObject) {
      fabric.lang.Object obj = ((FabricFileObject) f).getData();
      if (!(obj instanceof FClass))
        throw new IOException(
            "Expected an FClass inside the FabricSourceObject instead of a "
                + obj.getClass());
      FClass fcls = (FClass) obj;
      if (!LabelUtil._Impl.relabelsTo(fcls.get$$updateLabel(), fcls
          .getCodebase().get$$updateLabel())) {
        // XXX: should we throw a more security-related exception here?
        throw new IOException("The label of class " + NSUtil.absoluteName(fcls)
            + " is higher than the label of its codebase ");
      }

      return new RemoteSource(f, fcls, kind);
    } else {
      if (Report.should_report(Report.loader, 2))
        Report.report(2, "Creating filesource from " + f);
      URI ns =
          getOptions().platformMode() ? platformNamespace() : localNamespace();
          return new LocalSource(f, kind, ns);
    }
  }

  /**
   * Creates namespace resolvers for FabIL namespaces.
   *
   * @param ns
   * @return
   */
  public NamespaceResolver createNamespaceResolver(URI ns) {
    if (ns == null) throw new NullPointerException("Namespace is null");
    if (Report.should_report("resolver", 3))
      Report.report(3, "Creating namespace resolver for " + ns);

    FabILOptions opt = getOptions();
    if (ns.equals(platformNamespace())) {
      return new SimpleResolver(this, ns);
    } else if (ns.equals(localNamespace())) {
      return new LocalResolver(this, ns, null, opt.codebaseAliases());
    } else if (ns.getScheme().equals("fab") && !ns.isOpaque()) {
      return new CodebaseResolver(this, ns);
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + ns);

  }

  // TODO: support multiple platform namespaces

  @Override
  public URI platformNamespace() {
    return platform_ns;
  }

  // TODO: support multiple local namespaces
  @Override
  public URI localNamespace() {
    return local_ns;
  }

  @Override
  public CBTypeEncoder typeEncoder() {
    if (typeEncoder == null) {
      typeEncoder = new CBTypeEncoder(ts);
    }
    return typeEncoder;
  }

  @Override
  public String namespaceToJavaPackagePrefix(URI ns) {
    if (ns.equals(localNamespace()) || ns.equals(platformNamespace()))
      return "";
    else if (ns.getScheme().equals("fab")) {
      return NSUtil.javaPackageName(ns) + ".";
    } else {
      throw new InternalCompilerError("Cannot create Java package prefix for "
          + ns);
    }
  }

  @Override
  public Map<String, URI> codebaseAliases() {
    return getOptions().codebaseAliases();
  }

  @Override
  public fabric.worker.Store destinationStore() {
    // Worker must be running!
    if (!Worker.isInitialized())
      throw new InternalCompilerError("Worker is not initialized.");

    String storeName = getOptions().destinationStore();

    if (storeName == null) return Worker.getWorker().getLocalStore();
    return Worker.getWorker().getStore(storeName);
  }

  @Override
  public JobExt jobExt() {
    return new CBJobExt();
  }

  @Override
  public Goal getCompileGoal(Job job) {
    FabILOptions opts = (FabILOptions) job.extensionInfo().getOptions();
    if (opts.createSkeleton())
      return ((FabILScheduler) scheduler()).CreateJavaSkeleton(job);
    else return super.getCompileGoal(job);
  }

  @Override
  public List<URI> classpath() {
    return getOptions().classpathURIs();
  }

  @Override
  public List<URI> sourcepath() {
    return getOptions().sourcepathURIs();
  }

  @Override
  public List<File> bootclasspath() {
    return getOptions().bootclasspathDirectories();
  }
}
