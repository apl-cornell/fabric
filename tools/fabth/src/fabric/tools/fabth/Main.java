package fabric.tools.fabth;

public class Main extends polyglot.pth.Main {

  public static void main(String[] args) {
    new Main().start(args);
  }

  @Override
  protected FabricTestFactory TestFactory() {
    return new FabricTestFactory();
  }

}
