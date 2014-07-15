package fabric.extension;

import jif.extension.JifCallDel;
import polyglot.ast.Call;
import polyglot.ast.Receiver;
import fabric.ast.Worker;

public class FabricCallDel extends JifCallDel {
  @Override
  public boolean targetIsNeverNull() {
    Receiver r = ((Call) node()).target();
    return super.targetIsNeverNull() || r instanceof Worker;
  }
}
