package fabric;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import fabric.worker.Worker;

import polyglot.frontend.Compiler;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.main.Options;
import polyglot.main.Report;
import polyglot.main.UsageError;
import polyglot.main.Main.TerminationException;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
import polyglot.util.StdErrorQueue;

/**
 * Main is the main program of the compiler extension.
 * It simply invokes Polyglot's main, passing in the extension's
 * ExtensionInfo.
 */
public class Main extends polyglot.main.Main
{
  public static void main(String[] args) {
      Main fabricMain = new Main();
      
      try {
          fabricMain.start(args, new fabric.ExtensionInfo(), null);
      }
      catch (polyglot.main.Main.TerminationException e) {
          System.err.println(e.getMessage());
          System.exit(1);
      }
  }
  public void start(String[] argv, ExtensionInfo ext, ErrorQueue eq) throws TerminationException {
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
    }
    catch (UsageError ue) {
        PrintStream out = (ue.exitCode()==0 ? System.out : System.err);
        if (ue.getMessage() != null && ue.getMessage().length() > 0) {
            out.println(ext.compilerName() +": " + ue.getMessage());
        }
        options.usage(out);
        throw new TerminationException(ue.exitCode());
    }

    if(options.runWorker) 
      compileInWorker(options, source, ext, eq);
    else 
      start(options, source, ext, eq);
    
  }
  
  public void start(Options options, Set source, ExtensionInfo ext,
      ErrorQueue eq) {
    
    if (eq == null) {
      eq = new StdErrorQueue(System.err,
                             options.error_count,
                             ext.compilerName());
    }

    Compiler compiler = new Compiler(ext, eq);

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
    
    final FabricOptions o = (FabricOptions) options;
    final Set s = source;
    final ExtensionInfo e = ext;
    final ErrorQueue q = eq;

    try {
      Worker.initialize(o.workerName);
    } catch (fabric.common.exceptions.UsageError x) {
      throw new InternalCompilerError("Could not initialize Fabric worker.", x);
    } catch (GeneralSecurityException x) {
      throw new InternalCompilerError("Could not initialize Fabric worker.", x);
    } catch (IOException x) {
      throw new InternalCompilerError("Could not initialize Fabric worker.", x);
    }
    
    Worker.runInTransaction(null, new Worker.Code<Void>() {
      public Void run() {
        start(o,s,e,q);
        return null;
      }
    });
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
                    if (l == null)
                        break;

                    StringTokenizer st = new StringTokenizer(l, " ");
                    while (st.hasMoreTokens())
                        newArgs.add(st.nextToken());
                }

                lr.close();
                ll.addAll(newArgs);
            }
            catch (java.io.IOException e) {
                throw new TerminationException("cmdline parser: couldn't read args file "+fn);
            }
            continue;
        }

        ll.add(args[i]);
    }

    return ll;
}


}
