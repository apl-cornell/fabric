package fabric;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jif.JifOptions;
import polyglot.main.OptFlag;
import polyglot.main.OptFlag.Arg;
import polyglot.main.OptFlag.Switch;
import polyglot.main.UsageError;
import fabric.common.FabricLocation;
import fabric.common.FabricLocation_c;
import fabric.common.NSUtil;

public class FabricOptions extends JifOptions {
  /**
   * Name of worker for compiling source from Fabric.
   */
  protected String workerName;

  /**
   * The name of the store for writing generated codebase and classes.
   */
  protected String destinationStore;

  /**
   * Class path. May include Fabric references to codebases. NB: This field
   * hides the corresponding field in polyglot.main.Options
   */
  protected List<FabricLocation> classpath;

  /**
   * Source path. May include Fabric references to codebases. NB: This field
   * hides the corresponding field in polyglot.main.Options
   */
  protected List<FabricLocation> source_path;

  /**
   * Boot classpath. Location of the FabIL runtime classes.
   */
  public List<FabricLocation> filbootclasspath;

  /**
   * Default java boot classpath.
   */
  public List<FabricLocation> bootclasspath;

//  /**
//   * Codebase names.
//   */
//  protected Map<String, FabricLocation> codebase_aliases;

  public FabricOptions(ExtensionInfo extension) {
    super(extension);
    
    classpath = new ArrayList<FabricLocation>();
    source_path = new ArrayList<FabricLocation>();
    filbootclasspath = new ArrayList<FabricLocation>();
    bootclasspath = new ArrayList<FabricLocation>();
//    codebase_aliases = new LinkedHashMap<String, FabricLocation>();
  }

  public FabricLocation source_output;
  public FabricLocation class_output;
  
  /**
   * Whether we're running in signature mode.
   */
  public boolean signatureMode;

  /**
   * Whether to fully compile classes or just verify and publish.
   */
  public boolean publishOnly;

  /**
   * Name of file to write URL of new codebase to.
   */
  protected String codebaseFilename;

  /**
   * The classpath for the Fabric signatures of FabIL and Java classes.
   */
  public List<FabricLocation> sigcp;

  /**
   * Whether we are building platform classes.
   */
  protected boolean platform_mode;

  /**
   * Whether to create a Java skeleton for each class.
   */
  public boolean createSkeleton;

  /**
   * Use optimizations.
   */
  public int optLevel;

  /**
   * Codebase names.
   */
  protected Map<String, FabricLocation> codebase_aliases;

  @Override
  public void setDefaultValues() {
    super.setDefaultValues();

    // Override default in Jif: do not trust providers.
    this.trustedProviders = false;

    this.fully_qualified_names = true;
    this.fatalExceptions = true;
    this.publishOnly = false;

    this.sigcp = new ArrayList<FabricLocation>();
  }

  /* FabIL Options (forwarded to delegate ) ********************************** */

  //protected FabILOptions_c delegate;

  /**
   * Whether to publish source to Fabric.
   */
  protected boolean publish;

  /**
   * Whether to run a Fabric worker.
   */
  protected boolean needWorker;

//  @Override
//  public boolean dumpDependencies() {
//    return delegate.dumpDependencies;
//  }
//
//  @Override
//  public boolean signatureMode() {
//    return delegate.signatureMode();
//  }

//  /* Parsing ***************************************************************** */
//  @Override
//  public void usage(PrintStream out) {
//    super.usage(out);
//    usageForFlag(out, "-filsigcp <path>",
//        "path for FabIL signatures (e.g. for fabric.lang.Object)");
//    usageForFlag(out, "-addfilsigcp <path>",
//        "additional path for FabIL signatures; prefixed to sigcp");
//    usageForFlag(out, "-worker <worker>", "compile as a specific worker");
//    usageForFlag(out, "-deststore <store>",
//        "publish source to a Fabric store (all source on the commandline and"
//            + " loaded through the sourcepath will be published)");
//    usageForFlag(out, "-codebase-alias <name>=<URI>",
//        "associate a codebase with an alias in source files");
//    usageForFlag(out, "-publish", "Publish source to Fabric.");
//    usageForFlag(out, "-publish-only",
//        "Verify and publish source, do not compile to bytecode.");
//    usageForFlag(out, "-codebase-output-file <filename>",
//        "Write Fabric reference of published codebase to file.");
//    usageForFlag(out, "-generate-native-skeletons",
//        "generate FabIL and Java bootstrap skeletons for each class");
//    usageForFlag(out, "-no-fail-on-exception",
//        "Force runtime exceptions to be caught or declared.");
//
//    usageForFlag(out, "-O", "turn optimizations on");
//
//    out.println("Most <path> arguments accept local directory paths as well as");
//    out.println("Fabric references to codebases objects in the following form:");
//    usageForFlag(out, "<path>",
//        "\"<fab://store/codebase_onum>:/path/to/local/dir/:...\"");
//  }

  @Override
  protected void populateFlags(Set<OptFlag<?>> flags) {
    
    flags.add(new OptFlag<List<FabricLocation>>("-sigcp", "<path>",
        "path for Fabric signatures (e.g. for fabric.lang.Object)") {
      @Override
      public Arg<List<FabricLocation>> handle(String[] args, int index) {
        List<FabricLocation> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new OptFlag<List<FabricLocation>>("-addsigcp", "<path>",
        "additional path for Fabric signatures; prefixed to sigcp") {
      @Override
      public Arg<List<FabricLocation>> handle(String[] args, int index) {
        List<FabricLocation> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new OptFlag<List<FabricLocation>>("-filsigcp", "<path>",
        "path for Fabric signatures (e.g. for fabric.lang.Object)") {
      @Override
      public Arg<List<FabricLocation>> handle(String[] args, int index) {
        List<FabricLocation> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new OptFlag<List<FabricLocation>>("-addfilsigcp", "<path>",
        "additional path for Fabric signatures; prefixed to sigcp") {
      @Override
      public Arg<List<FabricLocation>> handle(String[] args, int index) {
        List<FabricLocation> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new Switch("-publish", "Publish source to Fabric"));
    flags.add(new Switch("-publish-only", "Verify and publish source, do not compile to bytecode"));
    
    flags.add(new OptFlag<String>("-codebase-output-file", "<name>", 
        "Write Fabric reference of published codebase to file.") {
      @Override
      public Arg<String> handle(String[] args, int index) {
        return createArg(index + 1, args[index]);
      }
    });
    flags.add(new Switch("-no-fail-on-exception", "Force runtime exceptions to be caught or declared."));
    
    flags.add(new OptFlag<List<FabricLocation>>("-classpath", "<path>",
        "where to find class files or mobile code to link against,"
            + " may contain <escaped> URIs of codebases") {
      @Override
      public Arg<List<FabricLocation>> handle(String[] args, int index) {
        List<FabricLocation> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new OptFlag<List<FabricLocation>>("-sourcepath", "<path>",
        "where to find source files to compile or publish, "
            + "may contain <escaped> URIs of codebases") {
      @Override
      public Arg<List<FabricLocation>> handle(String[] args, int index) {
        List<FabricLocation> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new OptFlag<List<FabricLocation>>("-bootclasspath", "<path>",
        "where to find Fabric runtime classes") {
      @Override
      public Arg<List<FabricLocation>> handle(String[] args, int index) {
        List<FabricLocation> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });

    super.populateFlags(flags);
  }
  
  @SuppressWarnings("unchecked")
  @Override
  protected void handleArg(Arg<?> arg) throws UsageError {
    // parse new options from fabric
    if (arg.flag().ids().contains("-filsigcp")) {
      // handled by FabIL
    } else if (arg.flag().ids().contains("-addfilsigcp")) {
      // handled by FabIL
    } else if (arg.flag().ids().contains("-sigcp")) {
      sigcp.clear();
      sigcp.addAll((List<FabricLocation>) arg.value());

    } else if (arg.flag().ids().contains("-addsigcp")) {
      sigcp.addAll((List<FabricLocation>) arg.value());    
      
    } else if (arg.flag().ids().contains("-classpath")) {
      classpath.addAll((List<FabricLocation>) arg.value());
    
    } else if (arg.flag().ids().contains("-sourcepath")) {
      source_path.addAll((List<FabricLocation>) arg.value());
      
    } else if (arg.flag().ids().contains("-bootclasspath")) {
      bootclasspath.addAll((List<FabricLocation>) arg.value());
      
    } else if (arg.flag().ids().contains("-publish")) {
      publish = (Boolean) arg.value();
      
    } else if (arg.flag().ids().contains("-publish-only")) {
      publishOnly = (Boolean) arg.value();
      
    } else if (arg.flag().ids().contains("-codebase-output-file")) {
      this.codebaseFilename = (String) arg.value();
      
    } else if (arg.flag().ids().contains("-no-fail-on-exception")) {
      fatalExceptions = (Boolean) arg.value();
    }
    else super.handleArg(arg);
  }

  @Override
  protected void postApplyArgs() {
    // Signature mode implies platform mode
    if (signatureMode)
      platform_mode = true;
    // Don't serialize types with skeletons
    if (createSkeleton)
      serialize_type_info = false;
    
    source_output =
        new FabricLocation_c("SOURCE_OUTPUT", true,
            source_output_directory.toURI());
    class_output =
        new FabricLocation_c("CLASS_OUTPUT", true,
            class_output_directory.toURI());
    
    // We need a worker if any path entry or source file 
    // is a remote URI
    for (FabricLocation loc : classpath) {
      if (loc.isFabricReference())
        needWorker = true;
    }
    for (FabricLocation loc : source_path) {
     if (loc.isFabricReference())
       needWorker = true;
    }
  }

  public boolean publishOnly() {
    return publishOnly;
  }

  public String codebaseFilename() {
    return codebaseFilename;
  }

  public List<FabricLocation> signaturepath() {
    return sigcp;
  }

//  @Override
//  public List<File> javaClasspathDirs() {
//    return delegate.javaClasspathDirs();
//  }
//
//  @Override
//  public List<FabricLocation> filsignaturepath() {
//    return delegate.sigcp;
//  }
//
  public Map<String, FabricLocation> codebaseAliases() {
    return codebase_aliases;
  }
//
//  // / Options processed by FabIL delegate
//  @Override
  public boolean createSkeleton() {
    return createSkeleton;
  }
//
  public String destinationStore() {
    return destinationStore;
  }

  public String workerName() {
    return workerName;
  }

  public FabricLocation outputLocation() {
    return source_output;
  }

  public FabricLocation classOutputDirectory() {
    return class_output;
  }

  public List<FabricLocation> classpath() {
    return classpath;
  }

  public List<FabricLocation> sourcepath() {
    return source_path;
  }

  public List<FabricLocation> filbootclasspath() {
    return filbootclasspath;
  }

  public List<FabricLocation> bootclasspath() {
    return bootclasspath;
  }

  public boolean platformMode() {
    return platform_mode;
  }

  public boolean needWorker() {
    return needWorker;
  }
  
  public boolean signatureMode() {
    return signatureMode;
  }

  /**
   * Should source be published to Fabric? Always false in signature or platform
   * modes.
   */
  public boolean publish() {
    // Never publish in signature or platform mode
    return publish & !signatureMode() && !platformMode();
  }
  
  @Override
  public String constructPostCompilerClasspath() {
    StringBuilder sb = new StringBuilder(super.constructPostCompilerClasspath());
    for (FabricLocation l : bootclasspath) {
      sb.append(File.pathSeparator);
      sb.append(l.getUri().getPath());
    }
    for (FabricLocation l : classpath) {
      sb.append(File.pathSeparator);
      sb.append(l.getUri().getPath());
    }
    return sb.toString();
  }
}
