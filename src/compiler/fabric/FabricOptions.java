package fabric;

import java.io.File;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jif.JifOptions;
import polyglot.main.Main.TerminationException;
import polyglot.main.UsageError;
import polyglot.util.InternalCompilerError;
import fabil.FabILOptions;
import fabil.FabILOptions_c;
import fabric.common.FabricLocation;
import fabric.common.FabricLocation_c;
import fabric.common.NSUtil;

public class FabricOptions extends JifOptions implements FabILOptions {

  public FabricOptions(ExtensionInfo extension) {
    super(extension);
    this.delegate = new FabILOptions_c(extension);
  }

//  public FabricLocation source_output;
//  public FabricLocation class_output;
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

  protected FabILOptions_c delegate;

  /**
   * Whether to publish source to Fabric.
   */
  protected boolean publish;

  /**
   * Whether to run a Fabric worker.
   */
  protected boolean needWorker;

  @Override
  public boolean dumpDependencies() {
    return delegate.dumpDependencies;
  }

  @Override
  public boolean signatureMode() {
    return delegate.signatureMode();
  }

  /* Parsing ***************************************************************** */
  @Override
  public void usage(PrintStream out) {
    super.usage(out);
    usageForFlag(out, "-filsigcp <path>",
        "path for FabIL signatures (e.g. for fabric.lang.Object)");
    usageForFlag(out, "-addfilsigcp <path>",
        "additional path for FabIL signatures; prefixed to sigcp");
    usageForFlag(out, "-worker <worker>", "compile as a specific worker");
    usageForFlag(out, "-deststore <store>",
        "publish source to a Fabric store (all source on the commandline and"
            + " loaded through the sourcepath will be published)");
    usageForFlag(out, "-codebase-alias <name>=<URI>",
        "associate a codebase with an alias in source files");
    usageForFlag(out, "-publish", "Publish source to Fabric.");
    usageForFlag(out, "-publish-only",
        "Verify and publish source, do not compile to bytecode.");
    usageForFlag(out, "-codebase-output-file <filename>",
        "Write Fabric reference of published codebase to file.");
    usageForFlag(out, "-generate-native-skeletons",
        "generate FabIL and Java bootstrap skeletons for each class");
    usageForFlag(out, "-no-fail-on-exception",
        "Force runtime exceptions to be caught or declared.");

    usageForFlag(out, "-O", "turn optimizations on");

    out.println("Most <path> arguments accept local directory paths as well as");
    out.println("Fabric references to codebases objects in the following form:");
    usageForFlag(out, "<path>",
        "\"<fab://store/codebase_onum>:/path/to/local/dir/:...\"");
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected int parseCommand(String[] args, int index, Set source)
      throws UsageError, TerminationException {
    // parse new options from fabric
    if (args[index].equals("-filsigcp")) {
      index++;
      delegate.sigcp.clear();
      delegate.addSigcp(args[index++]);
      return index;
    } else if (args[index].equals("-addfilsigcp")) {
      index++;
      delegate.addSigcp(args[index++]);
      return index;
    } else if (args[index].equals("-publish")) {
      index++;
      publish = true;
      needWorker = true;
    } else if (args[index].equals("-publish-only")) {
      index++;
      publish = true;
      publishOnly = true;
      needWorker = true;
    } else if (args[index].equals("-codebase-output-file")) {
      index++;
      this.codebaseFilename = args[index++];
      needWorker = true;
      return index;
    } else if (args[index].equals("-sigcp")) {
      index++;
      sigcp.clear();
      this.needWorker = NSUtil.processPathString(sigcp, args[index++]);
    } else if (args[index].equals("-addsigcp")) {
      index++;
      this.needWorker = NSUtil.processPathString(sigcp, args[index++]);
    } else if (args[index].equals("-no-fail-on-exception")) {
      index++;
      fatalExceptions = false;
    }

    // parse fabil options before jif's, otherwise some options
    // will get gobbled by jif's superclass, BUT-- don't call
    // FabILOption_c superclass.
    int i = delegate.parseCommand(args, index, source, false);
    if (i != index) {
      index = i;
      return index;
    }

    // parse jif options
    return super.parseCommand(args, index, source);
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

  @Override
  public List<File> javaClasspathDirs() {
    return delegate.javaClasspathDirs();
  }

  @Override
  public List<FabricLocation> filsignaturepath() {
    return delegate.sigcp;
  }

  @Override
  public Map<String, FabricLocation> codebaseAliases() {
    return delegate.codebaseAliases();
  }

  // / Options processed by FabIL delegate
  @Override
  public boolean createSkeleton() {
    return delegate.createSkeleton;
  }

  @Override
  public String destinationStore() {
    return delegate.destinationStore();
  }

  public String workerName() {
    return delegate.workerName();
  }

  @Override
  public FabricLocation outputLocation() {
//    if (source_output == null) {
//      File source_output_file = null;
//      if (!isSourceOutputGiven() && !isClassOutputGiven())
//        super.source_output_directory =
//            super.class_output_directory = Collections.singleton(current_directory);
//      else if (!isSourceOutputGiven() && isClassOutputGiven())
//        super.source_output_directory = super.class_output_directory;
//      else if (!isClassOutputGiven())
//        super.class_output_directory = Collections.singleton(current_directory);
//      for (File f : super.source_output_directory) {
//        source_output_file = f;
//        break;
//      }
//      if (source_output_file == null)
//        throw new InternalCompilerError("Source output location is not set.");
//      source_output =
//          new FabricLocation_c("SOURCE_OUTPUT", true,
//              source_output_file.toURI());
//    }
    return (FabricLocation) source_output;
  }

  @Override
  public FabricLocation classOutputDirectory() {
//    if (class_output == null) {
//      File class_output_file = null;
//      if (!isSourceOutputGiven() && !isClassOutputGiven())
//        super.source_output_directory =
//            super.class_output_directory = Collections.singleton(current_directory);
//      else if (!isSourceOutputGiven() && isClassOutputGiven())
//        super.source_output_directory = super.class_output_directory;
//      else if (!isClassOutputGiven())
//        super.class_output_directory = Collections.singleton(current_directory);
//      for (File f : super.class_output_directory) {
//        class_output_file = f;
//        break;
//      }
//      if (class_output_file == null)
//        throw new InternalCompilerError("Class output location is not set.");
//      class_output =
//          new FabricLocation_c("CLASS_OUTPUT", true, class_output_file.toURI());
//    }
    return (FabricLocation) class_output;
  }

  @Override
  public Set<FabricLocation> classpath() {
    return delegate.classpath();
  }

  @Override
  public Set<FabricLocation> sourcepath() {
    return delegate.sourcepath();
  }

  @Override
  public Set<FabricLocation> filbootclasspath() {
    return delegate.filbootclasspath();
  }

  @Override
  public Set<FabricLocation> bootclasspath() {
    return delegate.bootclasspath();
  }

  @Override
  public boolean platformMode() {
    return delegate.platformMode();
  }

  @Override
  public boolean needWorker() {
    return delegate.needWorker() || needWorker;
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
    for (FabricLocation l : bootclasspath()) {
      sb.append(File.pathSeparator);
      sb.append(l.getUri().getPath());
    }
    for (FabricLocation l : classpath()) {
      sb.append(File.pathSeparator);
      sb.append(l.getUri().getPath());
    }
    return sb.toString();
  }
}
