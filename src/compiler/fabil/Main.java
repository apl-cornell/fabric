package fabil;

import java.util.Iterator;

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
  ///HACK :: copied from superclass
  @Override
  protected boolean invokePostCompiler(Options options, Compiler compiler,
      ErrorQueue eq) {
    if (options.post_compiler != null && !options.output_stdout) {
      Runtime runtime = Runtime.getRuntime();
      QuotedStringTokenizer st =
          new QuotedStringTokenizer(options.post_compiler);
      int pc_size = st.countTokens();
      int options_size = 2;
      if (options.class_output_directory != null) {
        options_size += 2;
      }
      if (options.generate_debugging_info) options_size++;
      String[] javacCmd =
          new String[pc_size + options_size + compiler.outputFiles().size() -1];
      int j = 0;
      //skip "javac"
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

      Iterator iter = compiler.outputFiles().iterator();
      for (; iter.hasNext(); j++) {
        javacCmd[j] = (String) iter.next();
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
