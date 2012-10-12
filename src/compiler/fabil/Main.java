package fabil;


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
}
