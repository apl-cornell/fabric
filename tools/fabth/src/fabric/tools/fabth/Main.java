package fabric.tools.fabth;

import java.util.Iterator;

import fabric.tools.fabth.Options;
import polyglot.pth.OutputController;

public class Main extends polyglot.pth.Main {
  public static Options options;

  public static void main(String[] args) {
    new Main().start(args);
  }

  @Override
  public void start(String[] args) {
    options = new Options();
    //gross hack since several fields 
    // are package visible
    polyglot.pth.Main.options = options;
    try {
      options.parseCommandLine(args);
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    if (options.getInputFilenames().isEmpty()) {
      System.err.println("Need at least one script file.");
      System.exit(1);
    }

    OutputController outCtrl = createOutputController(options);

    for (Iterator iter = options.getInputFilenames().iterator(); iter
        .hasNext();) {
      String filename = (String) iter.next();
      FabricScriptTestSuite t = new FabricScriptTestSuite(filename);
      t.setOutputController(outCtrl);
      if (options.shouldShowResultsOnly()) {
        outCtrl.displayTestSuiteResults(t.getName(), t);
      } else {
        t.run();
      }
    }
  }

}
