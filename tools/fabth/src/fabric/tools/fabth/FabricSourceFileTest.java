package fabric.tools.fabth;

import java.io.File;
import java.util.List;

import polyglot.pth.SourceFileTest;
import polyglot.util.SilentErrorQueue;

public class FabricSourceFileTest extends SourceFileTest {
  public FabricSourceFileTest(String filename) {
    super(filename);
  }

  public FabricSourceFileTest(List<String> filenames) {
    super(filenames);
  }

  public FabricSourceFileTest(String[] filenames) {
    super(filenames);
  }

  @Override
  protected void invokePolyglot(String[] files)
      throws polyglot.main.Main.TerminationException {
    File tmpdir = new File("pthOutput");

    int i = 1;
    while (tmpdir.exists()) {
      tmpdir = new File("pthOutput." + i);
      i++;
    }

    tmpdir.mkdir();

    setDestDir(tmpdir.getPath());

    String[] cmdLine = buildCmdLine(files);
    fabric.Main fabricMain = new fabric.Main();

    try {
      fabricMain.start(cmdLine, eq);
    } finally {
       if (Main.options.shouldDeleteOutputFiles()) {
       deleteDir(tmpdir);
       }

      setDestDir(null);
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
