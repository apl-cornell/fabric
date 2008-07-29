package fabric.lang.auth;

import fabric.client.Core;
import fabric.lang.Object;
import fabric.util.Set;

public interface Label extends Object {
  public class $Proxy extends Object.$Proxy implements Label {
    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    @SuppressWarnings("unchecked")
    public native boolean relabelsTo(Label l, Set s);

    public native Label join(Label l);

    public native Label meet(Label l);

    public native ConfPolicy confPolicy();

    public native IntegPolicy integPolicy();
  }
}
