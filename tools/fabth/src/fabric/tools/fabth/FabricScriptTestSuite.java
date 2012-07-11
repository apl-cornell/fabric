package fabric.tools.fabth;

import java.io.File;
import java.util.List;
import fabric.tools.fabth.Grm;
import polyglot.pth.ScriptTestSuite;
import polyglot.pth.Test;

public class FabricScriptTestSuite extends ScriptTestSuite {

  public FabricScriptTestSuite(File scriptFile) {
    super(scriptFile);
  }

  public FabricScriptTestSuite(String scriptFilename) {
    super(scriptFilename);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected boolean parseScript() {
    Grm grm = new Grm(this.scriptFile);
    try {
      this.tests = (List<Test>) grm.parse().value;
    } catch (Exception e) {
      e.printStackTrace();
      this.setFailureMessage("Parsing error: " + e + ":" + e.getMessage());
      return false;
    }
    return true;
  }

}
