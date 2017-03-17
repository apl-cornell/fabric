package fabric.tools.fabth;

import java.util.List;

import polyglot.pth.SourceFileTest;
import polyglot.pth.polyglot.PolyglotTestFactory;

public class FabricTestFactory extends PolyglotTestFactory {

  @Override
  public FabricSourceFileTestCollection SourceFileTestCollection(
      String testCommand, String name, String testDir, String args,
      List<SourceFileTest> tests) {
    return new FabricSourceFileTestCollection(testCommand, name, testDir, args,
        tests);
  }

}
