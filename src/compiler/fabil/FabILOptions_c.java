package fabil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static fabric.common.FabricLocationFactory.getLocation;

import polyglot.frontend.ExtensionInfo;
import polyglot.main.Main.TerminationException;
import polyglot.main.UsageError;
import polyglot.util.InternalCompilerError;
import fabric.common.FabricLocation;
import fabric.common.FabricLocation_c;
import fabric.common.NSUtil;

/**
 * This is the same as the JL options, except by default, we always generate
 * fully qualified class names. This is here because the logic for qualifying
 * class names seems a bit wonky.
 */
public class FabILOptions_c extends polyglot.main.Options implements
    FabILOptions {
  public FabricLocation source_output_dir;
  public FabricLocation class_output_dir;
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
  public Set<FabricLocation> sigcp;

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
  protected Set<FabricLocation> classpath;

  /**
   * Source path. May include Fabric references to codebases. NB: This field
   * hides the corresponding field in polyglot.main.Options
   */
  protected Set<FabricLocation> source_path;

  /**
   * Boot classpath. Location of the FabIL runtime classes.
   */
  public Set<FabricLocation> filbootclasspath;

  /**
   * Default java boot classpath.
   */
  public Set<FabricLocation> bootclasspath;

  /**
   * Codebase names.
   */
  protected Map<String, FabricLocation> codebase_aliases;

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
    this.sigcp = new LinkedHashSet<FabricLocation>();
    this.classpath = new LinkedHashSet<FabricLocation>();
    this.source_path = new LinkedHashSet<FabricLocation>();
    this.filbootclasspath = new LinkedHashSet<FabricLocation>();
    this.bootclasspath = new LinkedHashSet<FabricLocation>();

    this.codebase_aliases = new LinkedHashMap<String, FabricLocation>();

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
      this.needWorker =
          NSUtil.processPathString(filbootclasspath, args[index++]);
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
      classpath_given = true;
    } else if (args[index].equals("-sourcepath")) {
      index++;
      source_path = processPathString(args[index++]);
    } else if (args[index].equals("-bootclasspath")) {
      index++;
      filbootclasspath = processPathString(args[index++]);
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
        u = NSUtil.file.resolve(f.toURI());
        source.add(u.toString());
        FabricLocation l = getLocation(false, NSUtil.dirname(u));
        source_path.add(l);
        if (!classpath_given) classpath.add(l);
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
    codebase_aliases.put(alias[0], getLocation(false, uri));
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
  public Set<File> javaClasspathDirs() {
    return classpath_directories;
  }

  @Override
  public Set<FabricLocation> filsignaturepath() {
    return sigcp;
  }

  @Override
  public Set<FabricLocation> classpath() {
    return classpath;
  }

  @Override
  public Set<FabricLocation> sourcepath() {
    return source_path;
  }

  @Override
  public Set<FabricLocation> filbootclasspath() {
    return filbootclasspath;
  }

  @Override
  public Set<FabricLocation> bootclasspath() {
    return bootclasspath;
  }

  @Override
  public Map<String, FabricLocation> codebaseAliases() {
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
  public FabricLocation outputDirectory() {
    if (source_output_dir == null) {
      File source_output_file = null;
      if (!isSourceOutputGiven() && !isClassOutputGiven())
        super.source_output_dir =
            super.class_output_dir = Collections.singleton(currFile);
      else if (!isSourceOutputGiven() && isClassOutputGiven())
        super.source_output_dir = super.class_output_dir;
      else if (!isClassOutputGiven())
        super.class_output_dir = Collections.singleton(currFile);
      for (File f : super.source_output_dir) {
        source_output_file = f;
        break;
      }
      if (source_output_file == null)
        throw new InternalCompilerError("Source output location is not set.");
      source_output_dir =
          new FabricLocation_c("SOURCE_OUTPUT", true,
              source_output_file.toURI());
    }
    return source_output_dir;
  }

  @Override
  public FabricLocation classOutputDirectory() {
    if (class_output_dir == null) {
      File class_output_file = null;
      if (!isSourceOutputGiven() && !isClassOutputGiven())
        super.source_output_dir =
            super.class_output_dir = Collections.singleton(currFile);
      else if (!isSourceOutputGiven() && isClassOutputGiven())
        super.source_output_dir = super.class_output_dir;
      else if (!isClassOutputGiven())
        super.class_output_dir = Collections.singleton(currFile);
      for (File f : super.class_output_dir) {
        class_output_file = f;
        break;
      }
      if (class_output_file == null)
        throw new InternalCompilerError("Class output location is not set.");
      class_output_dir =
          new FabricLocation_c("CLASS_OUTPUT", true, class_output_file.toURI());
    }
    return class_output_dir;
  }

  @Override
  public boolean needWorker() {
    return needWorker;
  }

  private Set<FabricLocation> processPathString(String path) {
    Set<FabricLocation> locations = new LinkedHashSet<FabricLocation>();
    this.needWorker = NSUtil.processPathString(locations, path);
    return locations;
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
