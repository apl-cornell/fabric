package fabric.extension;

import java.util.ArrayList;
import java.util.Collections;

import fabric.ast.Worker;
import fabric.types.FabricTypeSystem;

import jif.extension.JifCallDel;
import jif.types.JifMethodInstance;
import jif.types.JifSubstType;
import jif.types.Param;
import jif.types.label.VarLabel;

import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Receiver;

public class FabricCallDel extends JifCallDel {
  @Override
  public boolean targetIsNeverNull() {
    Receiver r = ((Call) node()).target();
    return super.targetIsNeverNull() || r instanceof Worker;
  }

  /**
   * XXX Ugly hack to set the delegate receiverVarLabel for stageTxn method
   * calls added by the label checker.
   * Normally this is done during typeChecking...
   */
  public void setReceiverVarLabel(FabricTypeSystem ts) {
    Call c = (Call) node();
    this.receiverVarLabel = ts.freshLabelVariable(c.position(), "receiver",
        "label of receiver of call " + c.toString());
    this.argVarLabels = new ArrayList<>(c.arguments().size());
    for (int i = 0; i < c.arguments().size(); i++) {
      Expr arg = c.arguments().get(i);
      VarLabel argLbl = ts.freshLabelVariable(arg.position(), "arg" + (i + 1) +
          "label", "label of arg " + (i + 1) + " of call " + c.toString());
      this.argVarLabels.add(argLbl);
    }

    
    JifMethodInstance mi = (JifMethodInstance) c.methodInstance();

    if (ts.unlabel(mi.container()) instanceof JifSubstType) {
      JifSubstType jst = (JifSubstType) ts.unlabel(mi.container());
      this.paramVarLabels = new ArrayList<>(
          jst.instantiatedFrom().formals().size());

      for (Param param : jst.actuals()) {
        VarLabel paramLbl = ts.freshLabelVariable(param.position(), "param_" +
            param + "_label", "label of param " + param + " of call " +
            c.toString());
        this.paramVarLabels.add(paramLbl);
      }
    } else {
        this.paramVarLabels = Collections.emptyList();
    }
  }
}
