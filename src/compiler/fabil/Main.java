package fabil;

import java.util.ArrayList;
import java.util.List;

import polyglot.main.Options;

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

  @Override
  protected List<String> getSystemJavacArgs(Options options) {
    List<String> result = new ArrayList<>(super.getSystemJavacArgs(options));
    result.add("-source");
    result.add("1.7");
    result.add("-target");
    result.add("1.7");
    return result;
  }
}
