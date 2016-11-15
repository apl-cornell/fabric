package fabric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fabric.common.NSUtil;
import jif.JifOptions;
import polyglot.main.OptFlag;
import polyglot.main.OptFlag.Arg;
import polyglot.main.OptFlag.IntFlag;
import polyglot.main.OptFlag.Switch;
import polyglot.main.UsageError;
import polyglot.util.InternalCompilerError;
import polyglot.util.Pair;

public class FabricOptions extends JifOptions {
  /**
   * Name of worker for compiling source from Fabric.
   */
  protected String workerName;

  /**
   * The name of the store for writing generated codebase and classes.
   */
  protected String destinationStore;

//  /**
//   * Class path. May include Fabric references to codebases. NB: This field
//   * hides the corresponding field in polyglot.main.Options
//   */
//  protected List<FabricLocation> classpath;
//
//  /**
//   * Source path. May include Fabric references to codebases. NB: This field
//   * hides the corresponding field in polyglot.main.Options
//   */
//  protected List<FabricLocation> source_path;
//
//  /**
//   * Boot classpath. Location of the FabIL runtime classes.
//   */
//  public List<FabricLocation> filbootclasspath;
//
//  public final List<File> filbootclasspath_directories;

//  /**
//   * Default java boot classpath.
//   */
//  public List<FabricLocation> bootclasspath;
  protected final List<URI> sourcepath_uris;
  protected final List<URI> classpath_uris;

  public FabricOptions(ExtensionInfo extension) {
    super(extension);
    sourcepath_uris = new ArrayList<>();
    classpath_uris = new ArrayList<>();
    codebase_aliases = new HashMap<>();
  }

//  public FabricLocation source_output;
//  public FabricLocation class_output;

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
  protected Map<String, URI> codebase_aliases;

  /**
   * Whether to publish source to Fabric.
   */
  protected boolean publish;

  /**
   * Whether to run a Fabric worker.
   */
  protected boolean needWorker;

  @Override
  protected void populateFlags(Set<OptFlag<?>> flags) {
    flags.add(new Switch("-sig", "compile sources to signatures"));

    flags.add(new OptFlag<List<URI>>("-sigcp", "<path>",
        "path for Fabric signatures (e.g. for fabric.lang.Object)") {
      @Override
      public Arg<List<URI>> handle(String[] args, int index) {
        List<URI> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new OptFlag<List<URI>>("-addsigcp", "<path>",
        "additional path for Fabric signatures; prefixed to sigcp") {
      @Override
      public Arg<List<URI>> handle(String[] args, int index) {
        List<URI> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new OptFlag<List<URI>>("-filsigcp", "<path>",
        "path for Fabric signatures (e.g. for fabric.lang.Object)") {
      @Override
      public Arg<List<URI>> handle(String[] args, int index) {
        List<URI> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new OptFlag<List<URI>>("-addfilsigcp", "<path>",
        "additional path for Fabric signatures; prefixed to sigcp") {
      @Override
      public Arg<List<URI>> handle(String[] args, int index) {
        List<URI> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new Switch("-publish", "Publish source to Fabric"));
    flags.add(new Switch("-publish-only",
        "Verify and publish source, do not compile to bytecode"));

    flags.add(new OptFlag<String>("-codebase-output-file", "<name>",
        "Write Fabric reference of published codebase to file.") {
      @Override
      public Arg<String> handle(String[] args, int index) {
        return createArg(index + 1, args[index]);
      }
    });
    flags.add(new Switch("-no-fail-on-exception",
        "Force runtime exceptions to be caught or declared."));

    flags.add(new OptFlag<List<URI>>(new String[] { "-classpath", "-cp" },
        "<path>", "where to find class files or mobile code to link against,"
            + " may contain <escaped> URIs of codebases") {
      @Override
      public Arg<List<URI>> handle(String[] args, int index) {
        List<URI> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new OptFlag<List<URI>>("-sourcepath", "<path>",
        "where to find source files to compile or publish, "
            + "may contain <escaped> URIs of codebases") {
      @Override
      public Arg<List<URI>> handle(String[] args, int index) {
        List<URI> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new OptFlag<List<URI>>("-bootclasspath", "<path>",
        "where to find classes for the Fabric platform") {
      @Override
      public Arg<List<URI>> handle(String[] args, int index) {
        List<URI> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }

      @Override
      public Arg<List<URI>> defaultArg() {
        return createDefault(NSUtil.processPathString(jvmbootclasspath()));
      }
    });
    flags.add(new OptFlag<List<URI>>("-addbootcp", "<path>",
        "prepend <path> to the bootclasspath") {
      @Override
      public Arg<List<URI>> handle(String[] args, int index) {
        List<URI> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new Switch("-trusted-providers",
        "set the providers of the sources being compiled to be trusted"));

    flags.add(new OptFlag<String>("-worker", "<name>",
        "The worker to use for fetching and publishing mobile code.") {
      @Override
      public Arg<String> handle(String[] args, int index) {
        return createArg(index + 1, args[index]);
      }

      @Override
      public Arg<String> defaultArg() {
        try {
          return createDefault(
              java.net.InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
          return createDefault("localhost");
        }
      }

    });
    flags.add(new OptFlag<String>("-deststore", "<name>",
        "The the destination store for published classes.") {
      @Override
      public Arg<String> handle(String[] args, int index) {
        return createArg(index + 1, args[index]);
      }
    });
    flags.add(new OptFlag<Pair<String, URI>>(
        new String[] { "-codebase-alias", "-cb-alias" }, "<name>",
        "The the destination store for published classes.") {
      @Override
      public Arg<Pair<String, URI>> handle(String[] args, int index)
          throws UsageError {

        String arg = args[index];
        if (arg.startsWith("@")) {
          try (BufferedReader lr =
              new BufferedReader(new FileReader(arg.substring(1)))) {
            arg = lr.readLine();
          } catch (FileNotFoundException e) {
            throw new InternalCompilerError(e);
          } catch (IOException e) {
            throw new InternalCompilerError(e);
          }
        }
        String[] alias = arg.split("=");
        if (alias.length != 2)
          throw new UsageError("Invalid codebase alias:" + arg);
        String cb = alias[1];

        if (cb.startsWith("@")) {
          try (BufferedReader lr =
              new BufferedReader(new FileReader(alias[1].substring(1)))) {
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

        return createArg(index + 1, new Pair<>(alias[0], uri));
      }
    });
    flags.add(new Switch("-generate-native-skeletons",
        "generate java bootstrap skeletons for each class"));
    flags.add(new Switch("-platform-mode", "build platform classes"));
    flags.add(new IntFlag("-optlevel", "<num>",
        "perform level <num> optimizations", 0));

    super.populateFlags(flags);

    OptFlag.removeFlag("-untrusted-providers", flags);
    OptFlag.removeFlag("-fail-on-exception", flags);

  }

  @Override
  protected int parseSourceArg(String[] args, int index) {
    URI u = null;
    try {
      u = URI.create(args[index]);
    } catch (IllegalArgumentException e) {
    }

    if (u == null || !u.isAbsolute()) {
      // Have a local file path.
      File f = new File(args[index]);
      u = f.toURI();
    }

    Arg<URI> src = new Arg<>(index + 1, u);
    arguments.add(src);
    return src.next();
  }

  @Override
  protected void handleSourceArg(Arg<?> arg, Set<String> source) {
    URI uri = (URI) arg.value();
    if (uri.getScheme().equals("fab")) needWorker = true;
    source.add(uri.toString());
  }

  @Override
  protected void handleArg(Arg<?> arg) throws UsageError {
    // parse new options from fabric
    if (arg.flag().ids().contains("-sig")) {
      // Signature mode implies platform mode. The local namespace should be the
      // platform ns
      signatureMode = (Boolean) arg.value();
    } else if (arg.flag().ids().contains("-filsigcp")) {
      // handled by FabIL
    } else if (arg.flag().ids().contains("-addfilsigcp")) {
      // handled by FabIL
    } else if (arg.flag().ids().contains("-sigcp")) {
      sigcp.clear();
      List<URI> uris = this.<List<URI>, URI> sccast(arg.value(), URI.class);
      sigcp.addAll(URIsToFiles(uris));

    } else if (arg.flag().ids().contains("-addsigcp")) {
      List<URI> uris = this.<List<URI>, URI> sccast(arg.value(), URI.class);
      sigcp.addAll(URIsToFiles(uris));

    } else if (arg.flag().ids().contains("-classpath")) {
      List<URI> uris = this.<List<URI>, URI> sccast(arg.value(), URI.class);
      classpathURIs().addAll(uris);

    } else if (arg.flag().ids().contains("-sourcepath")) {
      sourcepathURIs()
          .addAll(this.<List<URI>, URI> sccast(arg.value(), URI.class));

    } else if (arg.flag().ids().contains("-bootclasspath")) {
      List<URI> uris = this.<List<URI>, URI> sccast(arg.value(), URI.class);
      bootclasspathDirectories().addAll(URIsToFiles(uris));

    } else if (arg.flag().ids().contains("-addbootcp")) {
      List<URI> uris = this.<List<URI>, URI> sccast(arg.value(), URI.class);
      bootclasspathDirectories().addAll(URIsToFiles(uris));

    } else if (arg.flag().ids().contains("-publish")) {
      publish = (Boolean) arg.value();

    } else if (arg.flag().ids().contains("-publish-only")) {
      publishOnly = (Boolean) arg.value();

    } else if (arg.flag().ids().contains("-codebase-output-file")) {
      this.codebaseFilename = (String) arg.value();

    } else if (arg.flag().ids().contains("-no-fail-on-exception")) {
      fatalExceptions = !(Boolean) arg.value();

    } else if (arg.flag().ids().contains("-trusted-providers")) {
      trustedProviders = (Boolean) arg.value();

    } else if (arg.flag().ids().contains("-worker")) {
      workerName = (String) arg.value();

    } else if (arg.flag().ids().contains("-deststore")) {
      destinationStore = (String) arg.value();
      needWorker = true;

    } else if (arg.flag().ids().contains("-codebase-alias")) {
      Pair<String, URI> pair = (Pair<String, URI>) arg.value();
      String alias = pair.part1();
      URI loc = pair.part2();
      codebase_aliases.put(alias, loc);

    } else if (arg.flag().ids().contains("-generate-native-skeletons")) {
      createSkeleton = (Boolean) arg.value();

    } else if (arg.flag().ids().contains("-platform-mode")) {
      platform_mode = (Boolean) arg.value();

    } else if (arg.flag().ids().contains("-optlevel")) {
      optLevel = (Integer) arg.value();

    } else super.handleArg(arg);
  }

  public static List<File> URIsToFiles(List<URI> uris) {
    List<File> files = new ArrayList<>(uris.size());
    for (URI u : uris) {
      files.add(new File(u));
    }
    return files;
  }

  /**
   * Filter and add arguments for FabIL.
   * @param flags
   * @return FabIL-safe args
   * @throws UsageError
   */
  public List<OptFlag.Arg<?>> fabilArgs(Set<OptFlag<?>> flags)
      throws UsageError {
    List<Arg<?>> fabILArgs = new ArrayList<>();

    OptFlag<List<URI>> sigcp =
        (OptFlag<List<URI>>) OptFlag.lookupFlag("-sigcp", flags);
    OptFlag<List<URI>> addsigcp =
        (OptFlag<List<URI>>) OptFlag.lookupFlag("-addsigcp", flags);

    for (Arg<?> arg : arguments) {
      if (arg.flag() != null) {

        // Skip Fabric sigcp args
        if (arg.flag().ids().contains("-sigcp")
            || arg.flag().ids().contains("-addsigcp")) {
          continue;
        }
        // Rewrite FabIL sigcp args
        else if (arg.flag().ids().contains("-filsigcp")) {
          fabILArgs.add(sigcp.createArg(-1, (List<URI>) arg.value()));
        } else if (arg.flag().ids().contains("-addfilsigcp")) {
          fabILArgs.add(addsigcp.createArg(-1, (List<URI>) arg.value()));
        }
        if (flags.contains(arg.flag())) {
          fabILArgs.add(arg);
        }
      }
    }
    // FabIL's bootclasspath is the same, but should include the JRE
    OptFlag<?> addboot = OptFlag.lookupFlag("-addbootcp", flags);
    fabILArgs.add(addboot.handle(new String[] { jvmbootclasspath() }, 0));

    return fabILArgs;
  }

  @Override
  protected void postApplyArgs() {
    // publishOnly mode implies publish
    if (publishOnly) publish = true;

    // Don't serialize types with skeletons
    if (createSkeleton) serialize_type_info = false;

//    try {
//      source_output =
//          new FabricLocation_c("SOURCE_OUTPUT", true, source_output_directory
//              .getCanonicalFile().toURI());
//      class_output =
//          new FabricLocation_c("CLASS_OUTPUT", true, class_output_directory
//              .getCanonicalFile().toURI());
//    } catch (IOException e) {
//      throw new InternalCompilerError(e);
//    }

    // We need a worker if any path entry or source file
    // is a remote URI
    for (URI loc : classpathURIs()) {
      if (loc.getScheme().equals("fab")) needWorker = true;
    }
    for (URI loc : sourcepathURIs()) {
      if (loc.getScheme().equals("fab")) needWorker = true;
    }

  }

  public boolean publishOnly() {
    return publishOnly;
  }

  public String codebaseFilename() {
    return codebaseFilename;
  }

  public List<File> signaturepath() {
    return sigcp;
  }

  public Map<String, URI> codebaseAliases() {
    return codebase_aliases;
  }

// Options processed by FabIL delegate
  public boolean createSkeleton() {
    return createSkeleton;
  }

  public String destinationStore() {
    return destinationStore;
  }

  public String workerName() {
    return workerName;
  }

//  @Override
//  public FabricLocation outputLocation() {
//    return source_output;
//  }
//
//  @Override
//  public FabricLocation classOutputDirectory() {
//    return class_output;
//  }

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
    StringBuilder sb =
        new StringBuilder(super.constructPostCompilerClasspath());
    for (File l : bootclasspathDirectories()) {
      sb.append(File.pathSeparator);
      sb.append(l.getPath());
    }
    for (URI l : classpathURIs()) {
      sb.append(File.pathSeparator);
      sb.append(l.getPath());
    }
    return sb.toString();
  }

  public List<URI> classpathURIs() {
    return classpath_uris;
  }

  public List<URI> sourcepathURIs() {
    return sourcepath_uris;
  }
}
