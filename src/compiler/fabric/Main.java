package fabric;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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
import java.util.StringTokenizer;

import polyglot.frontend.Compiler;
import polyglot.frontend.ExtensionInfo;
import polyglot.main.Options;
import polyglot.main.Report;
import polyglot.main.UsageError;
import polyglot.util.ErrorInfo;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
import polyglot.util.QuotedStringTokenizer;
import polyglot.util.StdErrorQueue;
import fabric.common.NSUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.worker.AbortException;
import fabric.worker.Worker;

/**
 * Main is the main program of the compiler extension. It simply invokes
 * Polyglot's main, passing in the extension's ExtensionInfo.
 */
public class Main extends polyglot.main.Main {
  protected Compiler compiler;

  /**
   * @return System clock time between compilation and loading for timing
   *         purposes.
   * @throws IOException
   */
  public static long compile(FClass fcls, Map<String, byte[]> bytecodeMap)
      throws GeneralSecurityException, IOException {
    if (fcls == null || bytecodeMap == null)
      throw new GeneralSecurityException("Invalid arguments to compile");

    Worker worker = Worker.getWorker();
    List<String> args = new LinkedList<String>();
    args.add("-worker");
    args.add(worker.config.name);

    /* options for performance */
    args.add("-w");
    args.add("1000");
    args.add("-mergestrings");
    args.add("-simpleoutput");

    /* print time to complete Fabric and FabIL passes */
    args.add("-report");
    args.add("-profile=1");
    args.add("-report");
    args.add("-resolve=3");

    if (worker.sigcp != null) {
      args.add("-sigcp");
      args.add(worker.sigcp);
    }
    if (worker.filsigcp != null) {
      args.add("-filsigcp");
      args.add(worker.filsigcp);
    }
    if (worker.code_cache != null) {
      args.add("-d");
      args.add(worker.code_cache);
      args.add("-classpath");
      args.add(worker.code_cache);
    }
    if (worker.bootcp != null) {
      args.add("-bootclasspath");
      args.add(worker.bootcp);
    }

    URI cb = NSUtil.namespace(fcls.getCodebase());
    //XXX: It might be better to use a URI method here, but 
    // cb.resolve(name) doesn't do what you might think 
    args.add(cb.resolve(fcls.getName()).toString());
    Main main = new Main();
    try {
      fabric.ExtensionInfo extInfo = new fabric.ExtensionInfo(bytecodeMap);
      main.start(args.toArray(new String[0]), extInfo);

      long endCompileTime = System.currentTimeMillis(); 

      @SuppressWarnings("unchecked")
      Collection<String> outputFiles = main.compiler.outputFiles();
      File output_directory = extInfo.getFabricOptions().outputDirectory();
      String[] suffixes =
          new String[] { "$_Impl", "$_Proxy", "$_Static", "$_Static$_Impl",
              "$_Static$_Proxy" };
      for (String fname : outputFiles) {
        int e = fname.lastIndexOf(".java");
        String baseFileName = fname.substring(0, e);
        String baseClassName =
            baseFileName.substring(output_directory.getPath().length() + 1);
        baseClassName = baseClassName.replace(File.separator, ".");
        // load base class file
        File classFile = new File(baseFileName + ".class");
        if (classFile.exists()) {
          if (Report.should_report(Topics.mobile, 2))
            Report.report(1, "Inserting bytecode for " + classFile);
          bytecodeMap.put(baseClassName, getBytecode(classFile));
        }

        // load member classes
        for (int i = 0; i < suffixes.length; i++) {
          String fileName = baseFileName + suffixes[i];
          classFile = new File(fileName + ".class");
          if (classFile.exists()) {
            if (Report.should_report(Topics.mobile, 2))
              Report.report(1, "Inserting bytecode for " + classFile);
            bytecodeMap
                .put(baseClassName + suffixes[i], getBytecode(classFile));
          }
        }
      }
      return endCompileTime;
    } catch (TerminationException e) {
      throw new GeneralSecurityException(e);
    }
  }

  public static void compile_from_shell(List<String> args, InputStream in,
      PrintStream out) {
    InputStream save_in = System.in;
    PrintStream save_out = System.out;
    PrintStream save_err = System.err;

    System.setIn(in);
    System.setOut(out);
    System.setErr(out);

    List<String> new_args = new ArrayList<String>(args.size() + 2);
    Worker worker = Worker.getWorker();
    // Set code cache first to allow it to be overridden
    if (worker.code_cache != null) {
      new_args.add("-d");
      new_args.add(worker.code_cache);
    }
    new_args.addAll(args);

    polyglot.main.Main main = new Main();
    fabric.ExtensionInfo extInfo = new fabric.ExtensionInfo();

    try {
      main.start(new_args.toArray(new String[] {}), extInfo);

    } catch (TerminationException e) {
      System.err.println(e.getMessage());
    } finally {
      System.setIn(save_in);
      System.setOut(save_out);
      System.setErr(save_err);
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
  public void start(String[] args, ExtensionInfo _extInfo) {
    fabric.ExtensionInfo extInfo = (fabric.ExtensionInfo) _extInfo;
    super.start(args, extInfo);
  }

  @Override
  public void start(String[] argv, ExtensionInfo ext, ErrorQueue eq)
      throws TerminationException {
    Set<String> source = new LinkedHashSet<String>();
    List<String> args = explodeOptions(argv);
    if (ext == null) {
      ext = getExtensionInfo(args);
    }
    FabricOptions options = (FabricOptions) ext.getOptions();

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
      if (Report.should_report(Topics.mobile, 2))
        Report.report(2, "Compiling in worker with args:" + Arrays.toString(argv));

      compileInWorker(options, source, ext, eq);
    }
    else start(options, source, ext, eq);

  }

  public void start(Options options, Set<String> source, ExtensionInfo ext,
      ErrorQueue eq) {

    if (eq == null) {
      eq =
          new StdErrorQueue(System.err, options.error_count, ext.compilerName());
    }

    this.compiler = new Compiler(ext, eq);

    if (!compiler.compileFiles(source)) {
      throw new TerminationException(1);
    }

    if (Report.should_report(verbose, 1))
      Report.report(1, "Output files: " + compiler.outputFiles());

    /* Now call javac or jikes, if necessary. */
    if (!invokePostCompiler(options, compiler, eq)) {
      throw new TerminationException(1);
    }
  }

  public void compileInWorker(Options options, Set<String> source,
      ExtensionInfo ext, ErrorQueue eq) {
    try {

      final FabricOptions o = (FabricOptions) options;
      final Set<String> s = source;
      final fabric.ExtensionInfo extInfo = (fabric.ExtensionInfo) ext;
      final ErrorQueue q = eq;

      try {
        if (!Worker.isInitialized())
          Worker.initialize(o.workerName());
        else if (!Worker.getWorker().config.name.equals(o.workerName()))
          throw new InternalCompilerError("Can not compile as "
              + o.workerName() + " from " + Worker.getWorker().config.name);
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

      Worker.runInTopLevelTransaction(new Worker.Code<Void>() {
        @Override
        public Void run() {
          try {
            start(o, s, extInfo, q);

            if (extInfo.getFabricOptions().codebaseFilename() != null) {
              FabricOptions opt = extInfo.getFabricOptions();
              File f = new File(opt.codebaseFilename());
              if (!f.isAbsolute()) f = new File(opt.outputDirectory(), f.getPath());
              FileWriter fw;
              try {
                fw = new FileWriter(f);
                URI localNS = extInfo.localNamespace();
                Codebase cb = extInfo.typeSystem().codebaseFromNS(localNS);
                URI ns = NSUtil.namespace(cb);
                fw.write("<" + ns + ">");
                fw.close();
              } catch (fabric.common.exceptions.InternalError e) {
                throw new TerminationException("Error writing codebase reference to "
                    + extInfo.getFabricOptions().codebaseFilename() + ": "
                    + e.getMessage(), 1);
              } catch (IOException e) {
                throw new TerminationException("Error writing codebase reference to "
                    + extInfo.getFabricOptions().codebaseFilename() + ": "
                    + e.getMessage(), 1);
              }
            }

          } catch (Throwable e) {
            // Always abort the transaction on an exception
            throw new AbortException(e);
          }
          return null;
        }
      }, false);
    } catch (Throwable e) {
      throw new InternalCompilerError(e);
    }
  }

  private List<String> explodeOptions(String[] args) throws TerminationException {
    LinkedList<String> ll = new LinkedList<String>();

    for (int i = 0; i < args.length; i++) {
      // special case for the @ command-line parameter
      if (args[i].startsWith("@")) {
        String fn = args[i].substring(1);
        try {
          BufferedReader lr = new BufferedReader(new FileReader(fn));
          LinkedList<String> newArgs = new LinkedList<String>();

          while (true) {
            String l = lr.readLine();
            if (l == null) break;

            StringTokenizer st = new StringTokenizer(l, " ");
            while (st.hasMoreTokens())
              newArgs.add(st.nextToken());
          }

          lr.close();
          ll.addAll(newArgs);
        } catch (java.io.IOException e) {
          throw new TerminationException(
              "cmdline parser: couldn't read args file " + fn);
        }
        continue;
      }

      ll.add(args[i]);
    }

    return ll;
  }

  protected static void insertByteCode(String className) {

  }

  protected static byte[] getBytecode(File classfile) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    FileInputStream in = new FileInputStream(classfile);
    byte[] buf = new byte[4096];
    int n = 0;

    do {
      n = in.read(buf);
      if (n >= 0) out.write(buf, 0, n);
    } while (n >= 0);

    return out.toByteArray();
  }

  // /HACK :: copied from superclass
  @SuppressWarnings("unchecked")
  @Override
  protected boolean invokePostCompiler(Options options, Compiler compiler,
      ErrorQueue eq) {
    if (options.post_compiler != null && !options.output_stdout) {
      QuotedStringTokenizer st =
          new QuotedStringTokenizer(options.post_compiler);
      int pc_size = st.countTokens();
      int options_size = 2;
      if (options.class_output_directory != null) {
        options_size += 2;
      }
      if (options.generate_debugging_info) options_size++;
      String[] javacCmd =
          new String[pc_size + options_size + compiler.outputFiles().size() - 1];
      int j = 0;
      // skip "javac"
      st.nextToken();
      for (int i = 1; i < pc_size; i++) {
        javacCmd[j++] = st.nextToken();
      }
      javacCmd[j++] = "-classpath";
      javacCmd[j++] = options.constructPostCompilerClasspath();
      if (options.class_output_directory != null) {
        javacCmd[j++] = "-d";
        javacCmd[j++] = options.class_output_directory.toString();
      }
      if (options.generate_debugging_info) {
        javacCmd[j++] = "-g";
      }

      for (String s : (Collection<String>) compiler.outputFiles()) {
        javacCmd[j++] = s;
      }

      if (Report.should_report(verbose, 1)) {
        StringBuffer cmdStr = new StringBuffer();
        for (int i = 0; i < javacCmd.length; i++)
          cmdStr.append(javacCmd[i] + " ");
        Report.report(1, "Executing post-compiler " + cmdStr);
      }

      try {
        if (options.class_output_directory != null) {
          options.class_output_directory.mkdirs();
        }

        int exitVal = com.sun.tools.javac.Main.compile(javacCmd);

        if (exitVal > 0) {
          eq.enqueue(ErrorInfo.POST_COMPILER_ERROR, "Non-zero return code: "
              + exitVal);
          return false;
        }
      } catch (Exception e) {
        eq.enqueue(ErrorInfo.POST_COMPILER_ERROR, e.getMessage());
        return false;
      }
    }
    return true;
  }

}
