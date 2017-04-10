package fabric.tools.fabth;

import java.util.List;

import fabric.worker.Worker;
import polyglot.pth.SourceFileTest;
import polyglot.pth.SourceFileTestCollection;
import polyglot.pth.polyglot.PolyglotSourceFileTest;
import polyglot.pth.polyglot.PolyglotTestDriver;
import polyglot.util.ErrorQueue;

public class FabricTestDriver extends PolyglotTestDriver {

  public FabricTestDriver(SourceFileTestCollection sftc) {
    super(sftc);
  }

  @Override
  public int invokeCompiler(PolyglotSourceFileTest sft, List<String> cmdLine) {
    ErrorQueue eq = sft.errorQueue();
    try {
      fabric.Main fabricMain = new fabric.Main();
      fabricMain.start(cmdLine.toArray(new String[cmdLine.size()]), eq);
    } catch (polyglot.main.Main.TerminationException e) {
      if (e.getMessage() != null) {
        sft.appendFailureMessage(e.getMessage());
        return 1;
      } else {
        if (!eq.hasErrors()) {
          sft.appendFailureMessage(
              "Failed to compile for unknown reasons: " + e.toString());
          return 1;
        }
      }
    }
    return 0;
  }

  @Override
  public boolean postTest(SourceFileTest t) {
    if (Worker.isInitialized()) Worker.getWorker().shutdown();
    return super.postTest(t);
  }

}
