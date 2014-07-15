package fabil;

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

import javax.tools.JavaFileManager.Location;

import polyglot.frontend.ExtensionInfo;
import polyglot.main.OptFlag;
import polyglot.main.OptFlag.Arg;
import polyglot.main.OptFlag.IntFlag;
import polyglot.main.OptFlag.Switch;
import polyglot.main.UsageError;
import polyglot.util.InternalCompilerError;
import polyglot.util.Pair;
import fabric.common.NSUtil;

/**
 * This is the same as the JL options, except by default, we always generate
 * fully qualified class names. This is here because the logic for qualifying
 * class names seems a bit wonky.
 */
public class FabILOptions extends polyglot.main.Options {
  /**
   * Whether we're running in signature mode.
   */
  public boolean signatureMode;

  /**
   * Whether to dump class dependencies for each class.
   */
  public boolean dumpDependencies;

  protected final List<URI> sourcepath_uris;
  protected final List<URI> classpath_uris;

  /**
   * The classpath for the FabIL signatures of Java objects.
   */
  public final List<File> sigcp;

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
   * Use optimizations.
   */
  public int optLevel;

  /**
   * Codebase names.
   */
  protected Map<String, URI> codebase_aliases;

  /**
   * Whether we are building platform classes.
   */
  protected boolean platform_mode;

  public FabILOptions(ExtensionInfo extension) {
    super(extension);
    sigcp = new ArrayList<>();
    sourcepath_uris = new ArrayList<>();
    classpath_uris = new ArrayList<>();
    codebase_aliases = new HashMap<>();
  }

  @Override
  protected void populateFlags(Set<OptFlag<?>> flags) {
    flags.add(new Switch("-sig", "compile sources to signatures"));

    flags.add(new Switch("-dumpdeps", "output dependencies for each class"));

    // Override all the path options
    flags.add(new OptFlag<List<URI>>("-sigcp", "<path>",
        "path for FabIL signatures (e.g. for fabric.lang.Object)") {
      @Override
      public Arg<List<URI>> handle(String[] args, int index) {
        List<URI> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
    flags.add(new OptFlag<List<URI>>("-addsigcp", "<path>",
        "additional path for FabIL signatures; prefixed to sigcp") {
      @Override
      public Arg<List<URI>> handle(String[] args, int index) {
        List<URI> path = NSUtil.processPathString(args[index]);
        return createArg(index + 1, path);
      }
    });
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
        "where to find classes for the Fabric platform",
        "JVM property: sun.boot.class.path (or all jars in java.home/lib)") {
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

    flags.add(new OptFlag<String>("-worker", "<name>",
        "The worker to use for fetching and publishing mobile code.") {
      @Override
      public Arg<String> handle(String[] args, int index) {
        return createArg(index + 1, args[index]);
      }

      @Override
      public Arg<String> defaultArg() {
        try {
          return createDefault(java.net.InetAddress.getLocalHost()
              .getHostName());
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
    flags.add(new OptFlag<Pair<String, URI>>(new String[] { "-codebase-alias",
        "-cb-alias" }, "<name>",
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
  }

  @Override
  protected int parseSourceArg(String[] args, int index) {
    URI u = URI.create(args[index]);
    if (!u.isAbsolute()) {
      File f = new File(args[index]).getAbsoluteFile();
      u = NSUtil.file.resolve(f.toURI());
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
    if (arg.flag().ids().contains("-sig")) {
      // Signature mode implies platform mode. The local namespace should be the
      // platform ns
      signatureMode = (Boolean) arg.value();

    } else if (arg.flag().ids().contains("-dumpdeps")) {
      dumpDependencies = (Boolean) arg.value();

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
      sourcepathURIs().addAll(
          this.<List<URI>, URI> sccast(arg.value(), URI.class));

    } else if (arg.flag().ids().contains("-bootclasspath")) {
      List<URI> uris = this.<List<URI>, URI> sccast(arg.value(), URI.class);
      bootclasspathDirectories().addAll(URIsToFiles(uris));

    } else if (arg.flag().ids().contains("-addbootcp")) {
      List<URI> uris = this.<List<URI>, URI> sccast(arg.value(), URI.class);
      bootclasspathDirectories().addAll(URIsToFiles(uris));

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

  @Override
  protected void postApplyArgs() {
    // Don't serialize types with skeletons
    if (createSkeleton) serialize_type_info = false;

    // We need a worker if any path entry or source file
    // is a remote URI
    for (URI loc : classpathURIs()) {
      if (loc.getScheme().equals("fab")) needWorker = true;
    }
    for (URI loc : sourcepathURIs()) {
      if (loc.getScheme().equals("fab")) needWorker = true;
    }
  }

//  public List<File> javaClasspathDirs() {
//    return classpath_directories;
//  }

  public List<File> signaturepath() {
    return sigcp;
  }

  public List<URI> classpath() {
    return classpathURIs();
  }

  public List<URI> sourcepath() {
    return sourcepathURIs();
  }

  public List<File> bootclasspath() {
    return bootclasspathDirectories();
  }

  public Map<String, URI> codebaseAliases() {
    return codebase_aliases;
  }

  public boolean dumpDependencies() {
    return dumpDependencies;
  }

  public boolean createSkeleton() {
    return createSkeleton;
  }

  public boolean platformMode() {
    return platform_mode;
  }

  public boolean signatureMode() {
    return signatureMode;
  }

  public static FabILOptions global() {
    return (FabILOptions) global;
  }

  public String workerName() {
    return workerName;
  }

  public String destinationStore() {
    return destinationStore;
  }

  @Override
  public Location outputLocation() {
    return source_output;
  }

  @Override
  public Location classOutputLocation() {
    return class_output;
  }

  public boolean needWorker() {
    return needWorker;
  }

  public int optLevel() {
    return optLevel;
  }

  public static List<File> URIsToFiles(List<URI> uris) {
    List<File> files = new ArrayList<>(uris.size());
    for (URI u : uris) {
      files.add(new File(u));
    }
    return files;
  }

  @Override
  public String constructPostCompilerClasspath() {
    StringBuilder sb =
        new StringBuilder(super.constructPostCompilerClasspath());
    for (File l : bootclasspathDirectories()) {
      sb.append(File.pathSeparator);
      sb.append(l.getPath());
    }
    for (URI l : classpath()) {
      if (l.getScheme().equals("file")) {
        sb.append(File.pathSeparator);
        sb.append(l.getPath());
      }
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
