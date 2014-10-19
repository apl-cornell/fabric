package fabric;

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

import jif.visit.LabelChecker;
import polyglot.filemanager.FileManager;
import polyglot.frontend.Compiler;
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
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.reflect.ClassFileLoader;
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
import fabil.types.FabILTypeSystem;
import fabric.ast.FabricNodeFactory;
import fabric.ast.FabricNodeFactory_c;
import fabric.common.NSUtil;
import fabric.filemanager.FabricFileManager;
import fabric.filemanager.FabricFileObject;
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
public class ExtensionInfo extends jif.ExtensionInfo implements
codebases.frontend.ExtensionInfo {

  /*
   * Note: jif.ExtensionInfo has a jif.OutputExtensionInfo field jlext. The only
   * unoverridden place this is used is in a call to initCompiler, so it should
   * never leak out.
   */
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

  public ExtensionInfo(Map<String, byte[]> bytecode) {
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
      ((CodebaseTypeSystem) ts).initialize(this);
    } catch (SemanticException e) {
      throw new InternalCompilerError("Unable to initialize type system: ", e);
    }
  }

  @Override
  protected FileManager createFileManager() {
    return new FabricFileManager(this);
  }

  @Override
  protected void configureFileManager() throws IOException {
    FabricOptions options = getOptions();
    ((FabricFileManager) extFM).setLocation(options.classpath,
        options.classpathURIs());
    //Also load source from codebases
    List<URI> sourcedirs = new ArrayList<>();
    for (URI cpdir : options.classpathURIs()) {
      if (cpdir.getScheme().equals("fab")) sourcedirs.add(cpdir);
    }
    sourcedirs.addAll(options.sourcepathURIs());
    ((FabricFileManager) extFM).setLocation(options.source_path, sourcedirs);
    List<File> dirs = new ArrayList<>();
    dirs.addAll(options.sigcp);
    dirs.addAll(options.bootclasspathDirectories());
    extFM.setLocation(options.bootclasspath, dirs);
    extFM.setLocation(options.source_output,
        Collections.singleton(options.sourceOutputDirectory()));
    extFM.setLocation(options.class_output,
        Collections.singleton(options.classOutputDirectory()));
  }

  @Override
  public void configureFileManagerForPostCompiler() throws IOException {
    FabricOptions opt = getOptions();

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
    } else return scheduler().FabricToFabilRewritten(job);
  }

  @Override
  public Parser parser(Reader reader, Source source, ErrorQueue eq) {
    CodebaseSource src = (CodebaseSource) source;
    Lexer lexer = new Lexer_c(reader, source, eq);
    Grm grm =
        new Grm(lexer, typeSystem(), nodeFactory(), eq,
            src.canonicalNamespace());
    return new CupParser(grm, source, eq);
  }

  @Override
  public Set<String> keywords() {
    return new Lexer_c(null).keywords();
  }

  @Override
  public FabILTypeSystem jlTypeSystem() {
    return filext.typeSystem();
  }

  /* Overridden Factory Methods ********************************************** */
  @Override
  public CBTypeEncoder typeEncoder() {
    if (typeEncoder == null) {
      typeEncoder = new CBTypeEncoder(ts);
    }
    return typeEncoder;
  }

  @Override
  public ClassFileLoader classFileLoader() {
    return extFileManager();
  }

  @Override
  protected FabricNodeFactory createNodeFactory() {
    return new FabricNodeFactory_c();
  }

  @Override
  protected FabricTypeSystem createTypeSystem() {
    return new FabricTypeSystem_c(filext.typeSystem());
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
  public ClassFile createClassFile(FileObject classFileSource, byte[] code)
      throws IOException {
    return new ClassFile_c(classFileSource, code, this);
  }

  @Override
  public JobExt jobExt() {
    return new CBJobExt();
  }

  /* Overridden typed accessors ********************************************** */

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
  public LabelChecker createLabelChecker(Job job, boolean warningsEnabled,
      boolean solvePerClassBody, boolean solvePerMethod, boolean doLabelSubst) {
    return new FabricLabelChecker(job, typeSystem(), nodeFactory(),
        warningsEnabled, solvePerClassBody, solvePerMethod, doLabelSubst);
  }

  @Override
  public FabricOptions getOptions() {
    return (FabricOptions) super.getOptions();
  }

  @Override
  // TODO: support multiple local namespaces
  public FileSource createFileSource(FileObject f, Kind kind)
      throws IOException {
    if (f instanceof FabricFileObject) {
      fabric.lang.FClass fcls = ((FabricFileObject) f).getData();
      if (!LabelUtil._Impl.relabelsTo(fcls.get$$updateLabel(), fcls
          .getCodebase().get$$updateLabel())) {
        // XXX: should we throw a more security-related exception here?
        throw new IOException("The label of class " + NSUtil.absoluteName(fcls)
            + " is higher than the label of its codebase ");
      }

      return new RemoteSource(f, fcls, kind);
    } else {
      URI ns =
          getOptions().platformMode() ? platformNamespace() : localNamespace();
          LocalSource src = new LocalSource(f, kind, ns);
          // Publish all local source unless we're in platform mode.
          // TODO: generalize and make this better. We should only publish
          // source in the sourcepath. Plus, the user may be re-publishing remote
          // source with a new codebase.
          src.setPublish(getOptions().publish());
          return src;
    }
  }

  @Override
  public Store destinationStore() {
    return filext.destinationStore();
  }

  /**
   * Creates namespace resolvers for Fabric namespaces.
   *
   * @param ns
   * @return
   */
  public NamespaceResolver createNamespaceResolver(URI ns) {
    if (ns == null) throw new NullPointerException("Namespace is null");
    if (Report.should_report("resolver", 3))
      Report.report(3, "Creating namespace resolver for " + ns);

    FabricOptions opt = getOptions();
//    // XXX: Order is important here since the localnamespace may
//    // by the platform namespace when compiling the runtime
    if (ns.equals(platformNamespace())) {
      return new SimpleResolver(this, ns);
    } else if (ns.equals(localNamespace())) {
      return new LocalResolver(this, ns, null, opt.codebaseAliases());
    } else if (ns.getScheme().equals("fab") && !ns.isOpaque()) {
      return new CodebaseResolver(this, ns);
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
          new CBTargetFactory(this, extFileManager(),
              getOptions().source_output, getOptions().output_ext,
              getOptions().output_stdout);
    }

    return target_factory;
  }

  @Override
  public List<URI> classpath() {
    return getOptions().classpathURIs();
  }

  @Override
  public List<URI> sourcepath() {
    return getOptions().sourcepathURIs();
  }

  public List<File> signaturepath() {
    return getOptions().signaturepath();
  }

//  @Override
//  public List<FabricLocation> filsignaturepath() {
//    return getFabricOptions().filsignaturepath();
//  }

//  @Override
//  public List<FabricLocation> filbootclasspath() {
//    return getFabricOptions().filbootclasspath();
//  }

  @Override
  public List<File> bootclasspath() {
    return getOptions().bootclasspathDirectories();
  }

  @Override
  public Map<String, URI> codebaseAliases() {
    return getOptions().codebaseAliases();
  }

}
