package fabric;

import fabil.FabILOptions;
import jif.JifOptions;

public class FabricOptions extends JifOptions implements FabILOptions {

  FabILOptions delegate;
  
  public FabricOptions(ExtensionInfo extension) {
    super(extension);
  }

  public String constructFabILClasspath() {
    // TODO Auto-generated method stub
    return null;
  }

  public int optLevel() {
    // TODO Auto-generated method stub
    return 0;
  }

  public boolean signatureMode() {
    // TODO Auto-generated method stub
    return false;
  }

}
