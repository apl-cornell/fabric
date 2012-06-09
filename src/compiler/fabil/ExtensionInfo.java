package fabil;

import static fabric.common.FabricLocationFactory.getLocation;
import static java.io.File.pathSeparator;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;

import polyglot.ast.NodeFactory;
import polyglot.filemanager.FileManager;
import polyglot.frontend.CupParser;
import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.frontend.JobExt;
import polyglot.frontend.Parser;
import polyglot.frontend.Scheduler;
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
import codebases.types.CodebaseResolver;
import codebases.types.CodebaseTypeSystem;
import codebases.types.NamespaceResolver;
import codebases.types.PathResolver;
import codebases.types.SafeResolver;
import codebases.types.SimpleResolver;
import fabil.ast.FabILNodeFactory;
import fabil.ast.FabILNodeFactory_c;
import fabil.frontend.FabILScheduler;
import fabil.parse.Grm;
import fabil.parse.Lexer_c;
import fabil.types.ClassFile;
import fabil.types.ClassFile_c;
import fabil.types.FabILTypeSystem;
import fabil.types.FabILTypeSystem_c;
import fabric.common.FabricLocation;
import fabric.common.NSUtil;
import fabric.filemanager.FabricFileManager;
import fabric.lang.FClass;
import fabric.lang.security.LabelUtil;
import fabric.filemanager.FabricSourceObject;
import fabric.worker.Worker;

/**
 * Extension information for FabIL extension.
 */
public class ExtensionInfo extends polyglot.frontend.JLExtensionInfo implements
    codebases.frontend.ExtensionInfo {
  protected static FabricLocation platform_ns = getLocation(false,
      URI.create("fab:platform"));
  protected static FabricLocation local_ns = getLocation(false,
      URI.create("fab:local"));

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
  public FileManager extFileManager() {
    if (extFM == null) extFM = new FabricFileManager(this, getFabILOptions().needMemClassObjects());
    return extFM;
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
          new CBTargetFactory(this, extFileManager(), getFabILOptions()
              .outputDirectory(), getOptions().output_ext,
              getOptions().output_stdout);
    }

    return target_factory;
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
      ((CodebaseTypeSystem) ts).initialize(this);
    } catch (SemanticException e) {
      throw new InternalCompilerError("Unable to initialize type system: ", e);
    }
  }

  private void setFabricLocations(Collection<FabricLocation> locations,
      StandardJavaFileManager fm) {
    for (FabricLocation location : locations) {
      if (location.isFileReference()) {
        try {
          fm.setLocation(location,
              Collections.singleton(new File(location.getUri())));
        } catch (IOException e) {
          throw new InternalCompilerError(e);
        }
      }
    }
  }

  private void setJavaClasspath(FabILOptions options, StandardJavaFileManager fm) {
    Set<File> s = new LinkedHashSet<File>();
    s.addAll(options.javaClasspathDirs());
    for (FabricLocation location : options.filbootclasspath())
      if (location.isFileReference()) s.add(new File(location.getUri()));
    for (FabricLocation location : options.classpath())
      if (location.isFileReference()) s.add(new File(location.getUri()));
    try {
      fm.setLocation(StandardLocation.CLASS_PATH, s);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public void addLocationsToFileManager() {
    FabILOptions options = getFabILOptions();
    StandardJavaFileManager fm = extFileManager();
    for (String dir : System.getProperty("sun.boot.class.path").split(
        pathSeparator))
      options.bootclasspath().add(getLocation(false, new File(dir).toURI()));
    setFabricLocations(options.bootclasspath(), fm);
    setFabricLocations(options.filbootclasspath(), fm);
    setFabricLocations(options.classpath(), fm);
    setFabricLocations(options.filsignaturepath(), fm);
    setFabricLocations(options.sourcepath(), fm);
    setFabricLocations(Collections.singleton(options.outputDirectory()), fm);
    setFabricLocations(Collections.singleton(options.classOutputDirectory()),
        fm);
    setJavaClasspath(options, fm);
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

  @Override
  public FileSource createFileSource(FileObject f, boolean user)
      throws IOException {
    if (f instanceof FabricSourceObject) {
      fabric.lang.Object obj = ((FabricSourceObject) f).getData();
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

      return new RemoteSource(f, fcls, user);
    } else {
      if (Report.should_report(Report.loader, 2))
        Report.report(2, "Creating filesource from " + f);

      return new LocalSource(f, user, localNamespace());
    }
  }

  /**
   * Creates namespace resolvers for FabIL namespaces.
   * 
   * @param ns
   * @return
   */
  public NamespaceResolver createNamespaceResolver(FabricLocation ns) {
    if (Report.should_report("resolver", 3))
      Report.report(3, "Creating namespace resolver for " + ns);

    FabILOptions opt = (FabILOptions) getOptions();
    // XXX: Order is important here since the localnamespace may
    // by the platform namespace when compiling the runtime
    if (ns.equals(platformNamespace())) {
      // A platform resolver is really just a path resolver that is treated
      // specially. Loading the appropriate platform classes and signatures
      // is handled by the classpathloader and sourceloader

      List<NamespaceResolver> path = new ArrayList<NamespaceResolver>();
      path.addAll(typeSystem().signatureResolvers());
      path.addAll(typeSystem().runtimeResolvers());
      path.addAll(typeSystem().javaruntimeResolvers());
      return new PathResolver(this, ns, path);
    } else if (ns.equals(localNamespace())) {
      List<NamespaceResolver> path = new ArrayList<NamespaceResolver>();
      path.add(typeSystem().platformResolver());
      path.addAll(typeSystem().classpathResolvers());
      path.addAll(typeSystem().sourcepathResolvers());
      return new PathResolver(this, ns, path, opt.codebaseAliases());

    } else if (ns.isFabricReference()) {
      // Codebases may never resolve platform types, so always resolve against
      // the platformResolver first.
      return new SafeResolver(this, new CodebaseResolver(this, ns));
    } else if (ns.isFileReference()) {
      return new SimpleResolver(this, ns);
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + ns);
  }

  // TODO: support multiple platform namespaces

  @Override
  public FabricLocation platformNamespace() {
    return platform_ns;
  }

  // TODO: support multiple local namespaces
  @Override
  public FabricLocation localNamespace() {
    return getFabILOptions().platformMode() ? platformNamespace() : local_ns;
  }

  @Override
  public CBTypeEncoder typeEncoder() {
    if (typeEncoder == null) {
      typeEncoder = new CBTypeEncoder(ts);
    }
    return typeEncoder;
  }

  @Override
  public String namespaceToJavaPackagePrefix(FabricLocation ns) {
    if (ns.equals(localNamespace()) || ns.equals(platformNamespace()))
      return "";
    else if (ns.isFabricReference()) {
      return NSUtil.javaPackageName(ns.getUri()) + ".";
    } else {
      throw new InternalCompilerError("Cannot create Java package prefix for "
          + ns);
    }

  }

  @Override
  public Set<FabricLocation> classpath() {
    return getFabILOptions().classpath();
  }

  @Override
  public Set<FabricLocation> sourcepath() {
    return getFabILOptions().sourcepath();
  }

  @Override
  public Set<FabricLocation> filsignaturepath() {
    return getFabILOptions().filsignaturepath();
  }

  @Override
  public Set<FabricLocation> filbootclasspath() {
    return getFabILOptions().filbootclasspath();
  }

  @Override
  public Set<FabricLocation> bootclasspath() {
    return getFabILOptions().bootclasspath();
  }

  @Override
  public Map<String, FabricLocation> codebaseAliases() {
    return getFabILOptions().codebaseAliases();
  }

  @Override
  public fabric.worker.Store destinationStore() {
    // Worker must be running!
    if (!Worker.isInitialized())
      throw new InternalCompilerError("Worker is not initialized.");

    String storeName = getFabILOptions().destinationStore();

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

}
