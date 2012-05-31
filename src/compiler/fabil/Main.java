package fabil;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.tools.FileObject;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaCompiler.CompilationTask;

import polyglot.frontend.Compiler;
import polyglot.main.Options;
import polyglot.main.Report;
import polyglot.util.ErrorInfo;
import polyglot.util.ErrorQueue;
import polyglot.util.QuotedStringTokenizer;

/**
 * Main is the main program of the compiler extension. It simply invokes
 * Polyglot's main, passing in the extension's ExtensionInfo.
 */
public class Main extends polyglot.main.Main {
  public static void main(String[] args) {
    Main main = new Main();

    try {
      main.start(args, new fabil.ExtensionInfo());
    } catch (polyglot.main.Main.TerminationException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  // /HACK :: copied from superclass
  @Override
  protected boolean invokePostCompiler(Options options, Compiler compiler,
      ErrorQueue eq) {
    if (options.post_compiler != null && !options.output_stdout) {
      QuotedStringTokenizer st =
          new QuotedStringTokenizer(options.post_compiler_args);
      int pc_size = st.countTokens();

      ArrayList<String> javacArgs = new ArrayList<String>(pc_size);
      while (st.hasMoreTokens()) {
        javacArgs.add(st.nextToken());
      }

      if (options.generate_debugging_info) {
        javacArgs.add("-g");
      }

      if (Report.should_report(verbose, 1)) {
        Report.report(1,
            "Executing post-compiler " + options.post_compiler.getClass()
                + " with arguments " + javacArgs);
      }
      try {
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        Writer javac_err = new OutputStreamWriter(err);
        JavaCompiler javac = options.post_compiler;
        JavaFileManager fileManager =
            compiler.sourceExtension().extFileManager();

        CompilationTask task =
            javac.getTask(javac_err, fileManager, null, javacArgs, null,
                compiler.outputFiles());

        if (!task.call())
          eq.enqueue(ErrorInfo.POST_COMPILER_ERROR, err.toString());

        if (!options.keep_output_files) {
          for (FileObject fo : compiler.outputFiles()) {
            fo.delete();
          }
        }
      } catch (Exception e) {
        eq.enqueue(ErrorInfo.POST_COMPILER_ERROR, e.getMessage());
        return false;
      }
    }
    return true;
  }

}
