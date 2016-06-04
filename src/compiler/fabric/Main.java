package fabric;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import fabric.common.NSUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.util.Iterator;
import fabric.worker.AbortException;
import fabric.worker.Worker;
import polyglot.filemanager.ExtFileManager;
import polyglot.frontend.Compiler;
import polyglot.frontend.ExtensionInfo;
import polyglot.main.Options;
import polyglot.main.Report;
import polyglot.main.UsageError;
import polyglot.types.reflect.ClassFile;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
import polyglot.util.StdErrorQueue;

/**
 * Main is the main program of the compiler extension. It simply invokes
 * Polyglot's main, passing in the extension's ExtensionInfo.
 */
public class Main extends polyglot.main.Main {
  protected Compiler compiler;
  protected fabric.ExtensionInfo extInfo;

  protected static String[] buildArgs(FClass fcls) {

    Worker worker = Worker.getWorker();
    List<String> args = new LinkedList<>();
    args.add("-worker");
    args.add(worker.config.name);

    /* options for performance */
    args.add("-w");
    args.add("1000");
    args.add("-mergestrings");
    args.add("-simpleoutput");
    args.add("-no-warnings");

    // For debugging.
    args.add("-g");

    /* print time to complete Fabric and FabIL passes */
//    args.add("-report");
//    args.add("frontend=2");
    // args.add("-report");
    // args.add("profile=1");
    // args.add("-report");
    // args.add("resolver=3");
    // args.add("-report");
    // args.add("mobile=3");

    if (worker.sigcp != null) {
      args.add("-sigcp");
      args.add(worker.sigcp);
    }
    if (worker.filsigcp != null) {
      args.add("-filsigcp");
      args.add(worker.filsigcp);
    }
    if (worker.codeCache != null) {
      args.add("-d");
      args.add(worker.codeCache);
      args.add("-classpath");
      args.add(worker.codeCache);
    }
    if (worker.bootcp != null) {
      args.add("-bootclasspath");
      args.add(worker.bootcp);
    }
    if (!worker.outputToLocalFS) {
      args.add("-no-output-to-fs");
    }

    URI cb = NSUtil.namespace(fcls.getCodebase());
    args.add(cb.resolve(fcls.getName()).toString());
    return args.toArray(new String[0]);
  }

  /**
   * @throws IOException
   */
  public static void compile(FClass fcls, Map<String, byte[]> bytecodeMap)
      throws GeneralSecurityException, IOException {

    if (fcls == null || bytecodeMap == null)
      throw new GeneralSecurityException("Invalid arguments to compile");

    try {
//      if (this.compiler != null) {
//        compiler.compileFiles(Collections.singleton(NSUtil.absoluteName(fcls)
//            .toString()));
//      } else {
      Main main = new Main();
      fabric.ExtensionInfo extInfo = new fabric.ExtensionInfo(bytecodeMap);
      if (Report.should_report(Topics.mobile, 2)) {
        Report.report(2, "Compiling mobile class " + fcls.toString());
      }
      main.start(buildArgs(fcls), extInfo);
      if (Report.should_report(Topics.mobile, 2)) {
        Report.report(2, "Finished compiling mobile class " + fcls.toString());
      }
//      }
      Collection<JavaFileObject> outputFiles = main.compiler.outputFiles();
      String[] suffixes = new String[] { "", "$_Impl", "$_Proxy", "$_Static",
          "$_Static$_Impl", "$_Static$_Proxy" };
      Location classOutput = extInfo.getOptions().classOutputLocation();
      for (JavaFileObject jfo : outputFiles) {
        URI src = jfo.toUri();
        //XXX: This is still a hack, but it is a struggle to come
        // up with a robust way of extracting the package name
        // with the current setup.
        String fileName = src.getPath();
        int index = fileName.indexOf("$$");
        fileName = fileName.substring(index);
        for (String ext : suffixes) {
          String classFileName =
              fileName.substring(0, fileName.lastIndexOf(".java")) + ext;
          classFileName = classFileName.replace(File.separator, ".");

          FileObject classFo = extInfo.extFileManager()
              .getJavaFileForInput(classOutput, classFileName, Kind.CLASS);
          if (classFo == null) continue;
          byte[] code = ExtFileManager.getBytes(classFo);
          ClassFile classFile = extInfo.createClassFile(classFo, code);
          String fullName = classFile.name().replace(File.separator, ".");
          if (Report.should_report(Topics.mobile, 2)) {
            Report.report(2, "Inserting bytecode for " + fullName);
          }
          bytecodeMap.put(fullName, code);
        }
      }
    } catch (TerminationException e) {
      throw new GeneralSecurityException(e);
    }
  }

  public static void compileFromShell(List<String> args, InputStream in,
      PrintStream out) {
    InputStream saveIn = System.in;
    PrintStream saveOut = System.out;
    PrintStream saveErr = System.err;

    System.setIn(in);
    System.setOut(out);
    System.setErr(out);

    List<String> newArgs = new ArrayList<>(args.size() + 2);
    Worker worker = Worker.getWorker();
    // Set code cache first to allow it to be overridden
    if (worker.codeCache != null) {
      newArgs.add("-d");
      newArgs.add(worker.codeCache);
    }
    newArgs.addAll(args);

    polyglot.main.Main main = new Main();
    fabric.ExtensionInfo extInfo = new fabric.ExtensionInfo();

    try {
      main.start(newArgs.toArray(new String[] {}), extInfo);

    } catch (TerminationException e) {
      System.err.println(e.getMessage());
    } finally {
      System.setIn(saveIn);
      System.setOut(saveOut);
      System.setErr(saveErr);
    }
  }

  public static void main(String[] args) {
    polyglot.main.Main main = new Main();
    fabric.ExtensionInfo extInfo = new fabric.ExtensionInfo();
    try {
      main.start(args, extInfo);
    } catch (TerminationException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } finally {
      if (Worker.isInitialized()) {
        Worker.getWorker().shutdown();
      }
    }
  }

  @Override
  protected List<String> getSystemJavacArgs(Options options) {
    List<String> result = new ArrayList<>(super.getSystemJavacArgs(options));
    result.add("-source");
    result.add("1.7");
    result.add("-target");
    result.add("1.7");
    return result;
  }

  @Override
  public void start(String[] args, ExtensionInfo extInfo) {
    fabric.ExtensionInfo fabExtInfo = (fabric.ExtensionInfo) extInfo;
    super.start(args, fabExtInfo);
  }

  @Override
  public void start(String[] argv, ExtensionInfo ext, ErrorQueue eq)
      throws TerminationException {
    Set<String> source = new LinkedHashSet<>();
    List<String> args = argsToList(argv);
    if (ext == null) {
      ext = getExtensionInfo(args);
    }
    FabricOptions options = (FabricOptions) ext.getOptions();
    this.extInfo = (fabric.ExtensionInfo) ext;

    // Allow all objects to get access to the Options object. This hack should
    // be fixed somehow. XXX###@@@
    Options.global = options;
    try {
      argv = args.toArray(new String[0]);
      options.parseCommandLine(argv, source);
    } catch (UsageError ue) {
      PrintStream out = (ue.exitCode() == 0 ? System.out : System.err);
      if (ue.getMessage() != null && ue.getMessage().length() > 0) {
        out.println(ext.compilerName() + ": " + ue.getMessage());
      }
      options.usage(out);
      throw new TerminationException(ue.exitCode());
    }

    if (options.needWorker()) {
      if (Report.should_report(Topics.mobile, 2)) Report.report(2,
          "Compiling in worker with args:" + Arrays.toString(argv));
      compileInWorker(options, source, ext, eq);
    } else start(options, source, ext, eq);

  }

  public void start(Options options, Set<String> source, ExtensionInfo ext,
      ErrorQueue eq) {

    if (eq == null) {
      eq = new StdErrorQueue(System.err, options.error_count,
          ext.compilerName());
    }

    this.compiler = new Compiler(ext, eq);

    if (!compiler.compileFiles(source)) {
      throw new TerminationException(1);
    }

    if (Report.should_report(verbose, 1))
      Report.report(1, "Output files: " + compiler.outputFiles());

    Collection<JavaFileObject> outputFiles = compiler.outputFiles();
    if (outputFiles == null || outputFiles.size() == 0) return;
    /* Now call javac or jikes, if necessary. */
    if (!invokePostCompiler(options, compiler, eq)) {
      throw new TerminationException(1);
    }

  }

  public void compileInWorker(Options options, Set<String> source,
      ExtensionInfo ext, ErrorQueue eq) throws TerminationException {
    try {

      final FabricOptions o = (FabricOptions) options;
      final Set<String> s = source;
      final fabric.ExtensionInfo extInfo = (fabric.ExtensionInfo) ext;
      final ErrorQueue q = eq;

      try {
        if (!Worker.isInitialized())
          Worker.initialize(o.workerName());
        else if (!Worker.getWorker().config.name.equals(o.workerName()))
          throw new InternalCompilerError("Can not compile as " + o.workerName()
              + " from " + Worker.getWorker().config.name);
      } catch (fabric.common.exceptions.UsageError x) {
        throw new InternalCompilerError("Could not initialize Fabric worker.",
            x);
      } catch (GeneralSecurityException x) {
        throw new InternalCompilerError("Could not initialize Fabric worker.",
            x);
      } catch (IOException x) {
        throw new InternalCompilerError("Could not initialize Fabric worker.",
            x);
      }

      Worker.runInSubTransaction(new Worker.Code<Void>() {
        @Override
        public Void run() {
          try {
            start(o, s, extInfo, q);

            if (extInfo.getOptions().codebaseFilename() != null) {
              FabricOptions opt = extInfo.getOptions();
              File f = new File(opt.codebaseFilename());
              if (!f.isAbsolute())
                f = new File(opt.classOutputDirectory(), f.getPath());
              FileWriter fw;
              try {
                fw = new FileWriter(f);
                URI localNS = extInfo.localNamespace();
                Codebase cb = extInfo.typeSystem().codebaseFromNS(localNS);
                initializeStaticInstances(extInfo, cb);
                URI ns = NSUtil.namespace(cb);
                System.err.println("Creating codebase file "
                    + f.getAbsolutePath() + " with " + ns);
                fw.write("<" + ns + ">");
                fw.close();
              } catch (fabric.common.exceptions.InternalError e) {
                throw new TerminationException(
                    "Error writing codebase reference to "
                        + extInfo.getOptions().codebaseFilename() + ": "
                        + e.getMessage(),
                    1);
              } catch (IOException e) {
                throw new TerminationException(
                    "Error writing codebase reference to "
                        + extInfo.getOptions().codebaseFilename() + ": "
                        + e.getMessage(),
                    1);
              }
            }
          } catch (Throwable e) {

            // Always abort the transaction on an exception
            throw new AbortException(e);
          }
          return null;
        }
      });
    } catch (AbortException e) {
      if (e.getCause() != null) {
        Throwable abortCause = e.getCause();
        if (abortCause.getCause() != null) {
          Throwable failCause = abortCause.getCause();
          throw new TerminationException(failCause.getMessage());
        } else {
          throw new TerminationException(abortCause.getMessage());
        }
      }
      throw new TerminationException(e.getMessage());
    }
  }

  /**
   * Load all published classes so that their static state is initialized.
   * @param extInfo
   * @throws ClassNotFoundException
   */
  protected void initializeStaticInstances(fabric.ExtensionInfo extInfo,
      Codebase cb) throws ClassNotFoundException {
    fabric.util.Map classes = cb.get$classes();
    List<String> files = new ArrayList<>();
    for (Iterator it =
        classes.keySet().iterator(Worker.getWorker().getLocalStore()); it
            .hasNext();) {
      String className =
          (String) fabric.lang.WrappedJavaInlineable.$unwrap(it.next());
      FClass fcls = cb.resolveClassName(className);
      files.add(NSUtil.absoluteName(fcls).toString());
    }
    compiler.compileFiles(files);
  }

  private List<String> argsToList(String[] args) throws TerminationException {
    LinkedList<String> ll = new LinkedList<>();

    for (String arg : args) {
      // special case for the @ command-line parameter
      ll.add(arg);
    }

    return ll;
  }
}
