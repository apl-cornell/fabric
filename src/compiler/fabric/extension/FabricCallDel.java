package fabric.extension;

import fabric.ast.Client;
import polyglot.ast.Call;
import polyglot.ast.Receiver;
import jif.extension.JifCallDel;

public class FabricCallDel extends JifCallDel {
  @Override
  public boolean targetIsNeverNull() {
    Receiver r = ((Call)node()).target();
    return super.targetIsNeverNull() || r instanceof Client;
  }
}
