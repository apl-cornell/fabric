package fabric.tools.fabth;

import java.util.List;

import polyglot.pth.SourceFileTest;
import fabric.worker.Worker;

public class FabricSourceFileTest extends SourceFileTest {
  public FabricSourceFileTest(List<List<String>> compilationUnits) {
    super(compilationUnits);
  }

  @Override
  protected void invokePolyglot(List<String> cmdLine)
      throws polyglot.main.Main.TerminationException {
    fabric.Main fabricMain = new fabric.Main();
    fabricMain.start(cmdLine.toArray(new String[cmdLine.size()]), eq);
  }

  @Override
  protected boolean runTest() {
    try {
      return super.runTest();
    } finally {
      if (Worker.isInitialized()) Worker.getWorker().shutdown();
    }
  }

  @Override
  protected void setExtensionClassname(String extClassname) {
    super.setExtensionClassname(extClassname);
  }

  @Override
  protected void setExtraCmdLineArgs(String args) {
    super.setExtraCmdLineArgs(args);
  }

}
