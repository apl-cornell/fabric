package fabric.tools.fabth;

import java.util.List;

import polyglot.pth.SourceFileTest;
import polyglot.pth.polyglot.PolyglotSourceFileTestCollection;
import polyglot.pth.polyglot.PolyglotTestDriver;

public class FabricSourceFileTestCollection
    extends PolyglotSourceFileTestCollection {

  public FabricSourceFileTestCollection(String testCommand, String name,
      String testDir, String args, List<SourceFileTest> tests) {
    super(testCommand, name, testDir, args, tests);
  }

  @Override
  protected PolyglotTestDriver createTestDriver() {
    switch (testCommand) {
    case "fabric.ExtensionInfo":
      return new FabricTestDriver(this);
    default:
      return super.createTestDriver();
    }
  }

}
