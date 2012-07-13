package fabil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
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
    if (!options.output_stdout) {
      try {
        if (options.post_compiler == null) {
          ArrayList<String> postCompilerArgs = new ArrayList<String>(1);
          if (options.generate_debugging_info) postCompilerArgs.add("-g");
          ByteArrayOutputStream err = new ByteArrayOutputStream();
          Writer javac_err = new OutputStreamWriter(err);
          JavaFileManager fileManager =
              compiler.sourceExtension().extFileManager();
          CompilationTask task =
              ToolProvider.getSystemJavaCompiler().getTask(javac_err,
                  fileManager, null, postCompilerArgs, null,
                  compiler.outputFiles());

          if (!task.call())
            eq.enqueue(ErrorInfo.POST_COMPILER_ERROR, err.toString());
        } else {
          QuotedStringTokenizer st =
              new QuotedStringTokenizer(options.post_compiler);
          int pcSize = st.countTokens();
          int optionsSize = 2;
          if (options.class_output_directory != null) {
            optionsSize += 2;
          }
          if (options.generate_debugging_info) optionsSize++;
          String[] javacCmd =
              new String[pcSize + optionsSize + compiler.outputFiles().size()
                  - 1];
          int j = 0;
          // skip "javac"
          st.nextToken();
          for (int i = 1; i < pcSize; i++) {
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

          for (JavaFileObject jfo : compiler.outputFiles())
            javacCmd[j++] = new File(jfo.toUri()).getAbsolutePath();

          if (Report.should_report(verbose, 1)) {
            StringBuffer cmdStr = new StringBuffer();
            for (int i = 0; i < javacCmd.length; i++)
              cmdStr.append(javacCmd[i] + " ");
            Report.report(1, "Executing post-compiler " + cmdStr);
          }

          int exitVal = com.sun.tools.javac.Main.compile(javacCmd);
          
          if (!options.keep_output_files) {
            for (FileObject fo : compiler.outputFiles())
              fo.delete();
          }

          if (exitVal > 0) {
            eq.enqueue(ErrorInfo.POST_COMPILER_ERROR, "Non-zero return code: "
                + exitVal);
            return false;
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
