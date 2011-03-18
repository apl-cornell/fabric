package fabric;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
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
import polyglot.types.reflect.ClassFileLoader;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
import polyglot.util.StdErrorQueue;
import fabric.common.SysUtil;
import fabric.lang.FClass;
import fabric.worker.AbortException;
import fabric.worker.Worker;

/**
 * Main is the main program of the compiler extension. It simply invokes
 * Polyglot's main, passing in the extension's ExtensionInfo.
 */
public class Main extends polyglot.main.Main {
  
  public static void compile(FClass fcls, Map<String, byte[]> bytecodeMap)
      throws GeneralSecurityException {
    if (fcls == null || bytecodeMap == null)
      throw new GeneralSecurityException("Invalid arguments to compile");
    
    Worker worker = Worker.getWorker();
    String name = worker.config.name;
    List<String> args = new LinkedList<String>();
    args.add("-worker");
    args.add(name);
    args.add("-report");
    args.add("frontend=2");
  
    if(worker.sigcp != null) {
      args.add("-sigcp");
      args.add(worker.sigcp);
    }
    if(worker.filsigcp != null) {
      args.add("-filsigcp");
      args.add(worker.filsigcp);
    }
    args.add(SysUtil.oid(fcls));

    Main main = new Main();
    try {
      ExtensionInfo extInfo = new fabric.ExtensionInfo(bytecodeMap);
      main.start(args.toArray(new String[0]), extInfo);
      ClassFileLoader loader = main.compiler.loader();
      loader.loadClass(extInfo.getOptions().output_directory,
          SysUtil.pseudoname(fcls) + "$_Impl");
    } catch (TerminationException e) {
      throw new GeneralSecurityException(e);
    }
  }

  public static void main(String[] args) {
    polyglot.main.Main main = new Main(); 
    try {
      main.start(args, new fabric.ExtensionInfo());
    } catch (TerminationException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    } finally {
      if (Worker.isInitialized()) {
        Worker.getWorker().shutdown();
      }
    }
  }

  protected Compiler compiler;

  @Override
  public void start(String[] argv, ExtensionInfo ext, ErrorQueue eq)
      throws TerminationException {
    Set source = new LinkedHashSet();
    List args = explodeOptions(argv);
    if (ext == null) {
      ext = getExtensionInfo(args);
    }
    FabricOptions options = (FabricOptions) ext.getOptions();

    // Allow all objects to get access to the Options object. This hack should
    // be fixed somehow. XXX###@@@
    Options.global = options;
    try {
      argv = (String[]) args.toArray(new String[0]);
      options.parseCommandLine(argv, source);
    } catch (UsageError ue) {
      PrintStream out = (ue.exitCode() == 0 ? System.out : System.err);
      if (ue.getMessage() != null && ue.getMessage().length() > 0) {
        out.println(ext.compilerName() + ": " + ue.getMessage());
      }
      options.usage(out);
      throw new TerminationException(ue.exitCode());
    }

    if (options.runWorker())
      compileInWorker(options, source, ext, eq);
    else start(options, source, ext, eq);

  }

  public void start(Options options, Set source, ExtensionInfo ext,
      ErrorQueue eq) {

    if (eq == null) {
      eq =
          new StdErrorQueue(System.err, options.error_count, ext.compilerName());
    }

    this.compiler = new Compiler(ext, eq);

    long time0 = System.currentTimeMillis();

    if (!compiler.compileFiles(source)) {
      throw new TerminationException(1);
    }

    if (Report.should_report(verbose, 1))
      Report.report(1, "Output files: " + compiler.outputFiles());

    long start_time = System.currentTimeMillis();

    /* Now call javac or jikes, if necessary. */
    if (!invokePostCompiler(options, compiler, eq)) {
      throw new TerminationException(1);
    }
  }

  public void compileInWorker(Options options, Set source, ExtensionInfo ext,
      ErrorQueue eq) {
    try {

      final FabricOptions o = (FabricOptions) options;
      final Set s = source;
      final ExtensionInfo e = ext;
      final ErrorQueue q = eq;

      try {
        if (!Worker.isInitialized()) Worker.initialize(o.workerName());
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

      Worker.runInTransaction(null, new Worker.Code<Void>() {
        public Void run() {
          try {
            start(o, s, e, q);
            // insert code into map

          } catch (Throwable e) {
            // Always abort the transaction on an exception
            throw new AbortException(e);
          }
          return null;
        }
      });
    } catch (Throwable e) {
      throw new InternalCompilerError(e);
    }
  }

  private List explodeOptions(String[] args) throws TerminationException {
    LinkedList ll = new LinkedList();

    for (int i = 0; i < args.length; i++) {
      // special case for the @ command-line parameter
      if (args[i].startsWith("@")) {
        String fn = args[i].substring(1);
        try {
          BufferedReader lr = new BufferedReader(new FileReader(fn));
          LinkedList newArgs = new LinkedList();

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
}
