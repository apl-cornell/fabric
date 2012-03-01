package fabil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import polyglot.frontend.ExtensionInfo;
import polyglot.main.Main.TerminationException;
import polyglot.main.UsageError;
import polyglot.util.InternalCompilerError;
import fabric.common.NSUtil;

/**
 * This is the same as the JL options, except by default, we always generate
 * fully qualified class names. This is here because the logic for qualifying
 * class names seems a bit wonky.
 */
public class FabILOptions_c extends polyglot.main.Options implements
    FabILOptions {
  /**
   * Whether we're running in signature mode.
   */
  public boolean signatureMode;

  /**
   * Whether to dump class dependencies for each class.
   */
  public boolean dumpDependencies;

  /**
   * The classpath for the FabIL signatures of Java objects.
   */
  public List<URI> sigcp;

  /** Whether to perform optimizations. */
  public int optLevel;

  /**
   * Whether to create a Java skeleton for each class.
   */
  public boolean createSkeleton;

  /**
   * Name of worker for compiling source from Fabric.
   */
  protected String workerName;

  /**
   * Whether to run a Fabric worker so that Fabric-hosted source code can be
   * compiled.
   */
  protected boolean needWorker;

  /**
   * The name of the store for writing generated codebase and classes.
   */
  protected String destinationStore;

  /**
   * Class path. May include Fabric references to codebases. NB: This field
   * hides the corresponding field in polyglot.main.Options
   */
  protected List<URI> classpath;

  /**
   * Source path. May include Fabric references to codebases. NB: This field
   * hides the corresponding field in polyglot.main.Options
   */
  protected List<URI> source_path;

  /**
   * Boot classpath. Location of the FabIL runtime classes.
   */
  public List<URI> bootclasspath;

  /**
   * Codebase names.
   */
  protected Map<String, URI> codebase_aliases;

  /**
   * Whether we are building platform classes.
   */
  protected boolean platform_mode;

  public FabILOptions_c(ExtensionInfo extension) {
    super(extension);
  }

  @Override
  public void setDefaultValues() {
    super.setDefaultValues();
    this.fully_qualified_names = true;
    this.signatureMode = false;
    this.dumpDependencies = false;
    this.optLevel = 0;
    this.createSkeleton = false;
    try {
      this.workerName = java.net.InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      this.workerName = "localhost";
    }
    this.sigcp = new ArrayList<URI>();
    this.classpath = new ArrayList<URI>();
    this.source_path = new ArrayList<URI>();
    this.bootclasspath = new ArrayList<URI>();

    this.codebase_aliases = new LinkedHashMap<String, URI>();

    this.platform_mode = false;
    this.needWorker = false;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public int parseCommand(String[] args, int index, Set source)
      throws UsageError, TerminationException {
    return parseCommand(args, index, source, true);
  }

  public int parseCommand(String[] args, int index, Set<String> source,
      boolean call_super) throws UsageError, TerminationException {
    if (args[index].equals("-sig")) {
      index++;
      // Signature mode implies platform mode. The local namespace should be the
      // platform ns
      platform_mode = true;
      signatureMode = true;
    } else if (args[index].equals("-dumpdeps")) {
      index++;
      dumpDependencies = true;
    } else if (args[index].equals("-sigcp")) {
      index++;
      sigcp.clear();
      addSigcp(args[index++]);
    } else if (args[index].equals("-addsigcp")) {
      index++;
      addSigcp(args[index++]);
    } else if (args[index].equals("-addbootcp")) {
      index++;
      this.needWorker = NSUtil.processPathString(bootclasspath, args[index++]);
    } else if (args[index].startsWith("-O")) {
      if (args[index].length() == 2) {
        this.optLevel = Integer.MAX_VALUE;
      } else {
        try {
          this.optLevel = Integer.parseInt(args[index].substring(2));
        } catch (NumberFormatException e) {
        }
      }
      index++;
    } else if (args[index].equals("-classpath") || args[index].equals("-cp")) {
      index++;
      classpath = processPathString(args[index++]);
    } else if (args[index].equals("-sourcepath")) {
      index++;
      source_path = processPathString(args[index++]);
    } else if (args[index].equals("-bootclasspath")) {
      index++;
      bootclasspath = processPathString(args[index++]);
    } else if (args[index].equals("-worker")) {
      index++;
      this.needWorker = true;
      this.workerName = args[index++];
      return index;
    } else if (args[index].equals("-deststore")) {
      index++;
      this.needWorker = true;
      this.destinationStore = args[index++];
      return index;
    } else if (args[index].equals("-codebase-alias")
        || args[index].equals("-cb-alias")) {
      index++;
      this.needWorker = true;
      String arg = args[index++];
      if (arg.startsWith("@")) {
        try {
          BufferedReader lr =
              new BufferedReader(new FileReader(arg.substring(1)));
          addCodebaseAlias(lr.readLine());
        } catch (FileNotFoundException e) {
          throw new InternalCompilerError(e);
        } catch (IOException e) {
          throw new InternalCompilerError(e);
        }
      } else {
        addCodebaseAlias(arg);
      }
      return index;
    } else if (args[index].equals("-generate-native-skeletons")) {
      index++;
      createSkeleton = true;
      serialize_type_info = false;
    } else if (args[index].equals("-platform-mode")) {
      index++;
      platform_mode = true;
    } else if (!args[index].startsWith("-")) {
      URI u = URI.create(args[index]);
      if (u.isAbsolute()) {
        needWorker = true;
        source.add(u.toString());
      } else {
        File f = new File(args[index]).getAbsoluteFile();
        source.add(NSUtil.file.resolve(f.toString()).toString());
      }
      index++;
    } else if (call_super) {
      return super.parseCommand(args, index, source);
    }

    return index;
  }

  public void addSigcp(String arg) {
    this.needWorker = NSUtil.processPathString(sigcp, arg);
  }

  protected void addCodebaseAlias(String arg) throws UsageError {

    String[] alias = arg.split("=");
    if (alias.length != 2)
      throw new UsageError("Invalid codebase alias:" + arg);
    String cb = alias[1];

    if (cb.startsWith("@")) {
      try {
        BufferedReader lr =
            new BufferedReader(new FileReader(alias[1].substring(1)));
        cb = lr.readLine().replaceAll("[<>]", "");
      } catch (FileNotFoundException e) {
        throw new InternalCompilerError(e);
      } catch (IOException e) {
        throw new InternalCompilerError(e);
      }
    }
    if (!cb.endsWith("/")) cb += "/";
    URI uri = URI.create(cb);
    if (uri.isOpaque() || !uri.isAbsolute())
      throw new UsageError("Invalid codebase reference in alias:" + arg);
    codebase_aliases.put(alias[0], uri);
  }

  @Override
  public void usage(PrintStream out) {
    super.usage(out);
    usageForFlag(out, "-sig", "compile sources to signatures");
    usageForFlag(out, "-sigcp <path>",
        "path for FabIL signatures (e.g. for fabric.lang.Object)");
    usageForFlag(out, "-addsigcp <path>",
        "additional path for FabIL signatures; prefixed to sigcp");
    usageForFlag(out, "-dumpdeps", "output dependencies for each class");
    usageForFlag(out, "-deststore <store>",
        "the destination store for published classes");
    usageForFlag(out, "-codebase-alias <name>=<URI>",
        "associate a codebase with an alias in source files");
    usageForFlag(out, "-generate-native-skeletons",
        "generate java bootstrap skeletons for each class");
    usageForFlag(out, "-platform-mode",
        "build platform classes (sets local namespace to platform namespace)");
    usageForFlag(out, "-O", "turn optimizations on");

    out.println("Most <path> arguments accept local directory paths as well as");
    out.println("Fabric references to codebases objects in the following form:");
    usageForFlag(out, "<path>",
        "\"<fab://store/codebase_onum>:/path/to/local/dir/:...\"");
  }

  @Override
  public List<URI> signaturepath() {
    return sigcp;
  }

  @Override
  public List<URI> classpath() {
    return classpath;
  }

  @Override
  public List<URI> sourcepath() {
    return source_path;
  }

  @Override
  public List<URI> bootclasspath() {
    return bootclasspath;
  }

  @Override
  public Map<String, URI> codebaseAliases() {
    return codebase_aliases;
  }

  @Override
  public int optLevel() {
    return optLevel;
  }

  @Override
  public boolean dumpDependencies() {
    return dumpDependencies;
  }

  @Override
  public boolean createSkeleton() {
    return createSkeleton;
  }

  @Override
  public boolean platformMode() {
    return platform_mode;
  }

  @Override
  public boolean signatureMode() {
    return signatureMode;
  }

  public static FabILOptions global() {
    return (FabILOptions) global;
  }

  public String workerName() {
    return workerName;
  }

  @Override
  public String destinationStore() {
    return destinationStore;
  }

  @Override
  public File outputDirectory() {
    return output_directory;
  }

  @Override
  public boolean needWorker() {
    return needWorker;
  }

  private List<URI> processPathString(String path) {
    List<URI> uris = new ArrayList<URI>();
    this.needWorker = NSUtil.processPathString(uris, path);
    return uris;
  }

}
