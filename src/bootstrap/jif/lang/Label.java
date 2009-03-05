package jif.lang;

import fabric.client.Core;
import fabric.lang.SecretKeyObject;
import fabric.lang.Object;
import fabric.util.Set;

public interface Label extends Object {
  ConfPolicy confPolicy();

  IntegPolicy integPolicy();
  
  SecretKeyObject keyObject();
  
  public class $Proxy extends Object.$Proxy implements Label {
    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public native boolean relabelsTo(Label l, Set s);

    public native Label join(Label l);

    public native Label meet(Label l);

    public native ConfPolicy confPolicy();

    public native IntegPolicy integPolicy();
    
    public native SecretKeyObject keyObject();
  }
}
