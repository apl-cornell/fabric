package fabric.tools.fabth;

import java.io.File;
import java.util.List;

import polyglot.pth.SourceFileTest;
import fabric.worker.Worker;

public class FabricSourceFileTest extends SourceFileTest {
  public FabricSourceFileTest(List<String> filenames) {
    super(filenames);
  }

  @Override
  protected void invokePolyglot(List<String> files)
      throws polyglot.main.Main.TerminationException {
    File tmpdir = new File("pthOutput");

    int i = 1;
    while (tmpdir.exists()) {
      tmpdir = new File("pthOutput." + i);
      i++;
    }

    tmpdir.mkdir();

    setDestDir(tmpdir.getPath());

    List<String> cmdLine = buildCmdLine(files);
    fabric.Main fabricMain = new fabric.Main();

    try {
      fabricMain.start(cmdLine.toArray(new String[cmdLine.size()]), eq);
    } finally {
      if (Main.options.shouldDeleteOutputFiles()) {
        deleteDir(tmpdir);
      }

      setDestDir(null);
      if (Worker.isInitialized()) {
        Worker.getWorker().shutdown();
      }
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
