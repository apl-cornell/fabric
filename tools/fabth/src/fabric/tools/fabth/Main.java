package fabric.tools.fabth;

import java.util.Iterator;

import fabric.tools.fabth.Options;
import polyglot.pth.OutputController;

public class Main extends polyglot.pth.Main {
  
  @Override
  public void start(String[] args) {
    Options loc_options = new Options();
    options = loc_options;
    try {
      loc_options.parseCommandLine(args);
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    if (loc_options.getInputFilenames().isEmpty()) {
      System.err.println("Need at least one script file.");
      System.exit(1);
    }

    OutputController outCtrl = createOutputController(options);

    for (Iterator iter = loc_options.getInputFilenames().iterator(); iter.hasNext();) {
      String filename = (String) iter.next();
      FabricScriptTestSuite t = new FabricScriptTestSuite(filename);
      t.setOutputController(outCtrl);
      if (loc_options.isShowResultsOnly()) {
        outCtrl.displayTestSuiteResults(t.getName(), t);
      } else {
        t.run();
      }
    }
  }

}
