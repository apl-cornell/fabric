package fabric;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.StandardJavaFileManager;
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
import codebases.types.CodebaseResolver;
import codebases.types.CodebaseTypeSystem;
import codebases.types.NamespaceResolver;
import codebases.types.PathResolver;
import codebases.types.SafeResolver;
import codebases.types.SimpleResolver;
import fabil.FabILOptions;
import fabil.types.ClassFile;
import fabil.types.ClassFile_c;
import fabil.types.FabILTypeSystem;
import fabric.ast.FabricNodeFactory;
import fabric.ast.FabricNodeFactory_c;
import fabric.common.FabricLocation;
import fabric.common.NSUtil;
import fabric.lang.FClass;
import fabric.lang.security.LabelUtil;
import fabric.parse.Grm;
import fabric.parse.Lexer_c;
import fabric.types.FabricTypeSystem;
import fabric.types.FabricTypeSystem_c;
import fabric.filemanager.FabricSourceObject;
import fabric.filemanager.FabricFileManager;
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
  public FileManager extFileManager() {
    if (extFM == null) extFM = new FabricFileManager(this);
    return extFM;
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
    FabricOptions options = getFabricOptions();
    StandardJavaFileManager fm = extFileManager();
    setFabricLocations(options.filbootclasspath(), fm);
    setFabricLocations(options.classpath(), fm);
    setFabricLocations(options.signaturepath(), fm);
    setFabricLocations(options.filsignaturepath(), fm);
    setFabricLocations(options.sourcepath(), fm);
    setFabricLocations(Collections.singleton(options.outputDirectory()), fm);
    setFabricLocations(Collections.singleton(options.classOutputDirectory()),
        fm);
    setJavaClasspath(options, fm);
    filext.addLocationsToFileManager();
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
  public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
    CodebaseSource src = (CodebaseSource) source;
    Lexer lexer = new Lexer_c(reader, source, eq);
    Grm grm =
        new Grm(lexer, typeSystem(), nodeFactory(), eq,
            src.canonicalNamespace());
    return new CupParser(grm, source, eq);
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
  public LabelChecker createLabelChecker(Job job, boolean solvePerClassBody,
      boolean solvePerMethod, boolean doLabelSubst) {
    return new FabricLabelChecker(job, typeSystem(), nodeFactory(),
        solvePerClassBody, solvePerMethod, doLabelSubst);
  }

  public FabricOptions getFabricOptions() {
    return (FabricOptions) getOptions();
  }

  @Override
  // TODO: support multiple local namespaces
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
      LocalSource src = new LocalSource(f, user, localNamespace());
      // Publish all local source unless we're in platform mode.
      // TODO: generalize and make this better. We should only publish
      // source in the sourcepath. Plus, the user may be re-publishing remote
      // source with a new codebase.
      src.setPublish(getFabricOptions().publish());
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
  public NamespaceResolver createNamespaceResolver(FabricLocation ns) {
    if (ns == null) throw new NullPointerException("Namespace is null");
    if (Report.should_report("resolver", 3))
      Report.report(3, "Creating namespace resolver for " + ns);

    FabricOptions opt = getFabricOptions();
    // XXX: Order is important here since the localnamespace may
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
    } else if (ns.isFabricReference()) {
      // Codebases may never resolve platform types, so always resolve against
      // the platformResolver first.
      return new SafeResolver(this, new CodebaseResolver(this, ns));
    } else if (ns.isFileReference()) {
      return new SimpleResolver(this, ns);
    } else throw new InternalCompilerError("Unexpected scheme in URI: " + ns);
  }

  @Override
  public FabricLocation platformNamespace() {
    return filext.platformNamespace();
  }

  @Override
  public FabricLocation localNamespace() {
    return filext.localNamespace();
  }

  @Override
  public String namespaceToJavaPackagePrefix(FabricLocation ns) {
    return filext.namespaceToJavaPackagePrefix(ns);
  }

  @Override
  public TargetFactory targetFactory() {
    if (target_factory == null) {
      target_factory =
          new CBTargetFactory(this, extFileManager(), getFabricOptions()
              .outputDirectory(), getOptions().output_ext,
              getOptions().output_stdout);
    }

    return target_factory;
  }

  @Override
  public Set<FabricLocation> classpath() {
    return getFabricOptions().classpath();
  }

  @Override
  public Set<FabricLocation> sourcepath() {
    return getFabricOptions().sourcepath();
  }

  public Set<FabricLocation> signaturepath() {
    return getFabricOptions().signaturepath();
  }

  @Override
  public Set<FabricLocation> filsignaturepath() {
    return getFabricOptions().filsignaturepath();
  }

  @Override
  public Set<FabricLocation> filbootclasspath() {
    return getFabricOptions().filbootclasspath();
  }

  @Override
  public Set<FabricLocation> bootclasspath() {
    return getFabricOptions().bootclasspath();
  }

  @Override
  public Map<String, FabricLocation> codebaseAliases() {
    return getFabricOptions().codebaseAliases();
  }

}
