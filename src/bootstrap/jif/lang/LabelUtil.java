package jif.lang;

import fabric.client.Core;
import fabric.lang.Object;

public interface LabelUtil extends Object {
  
  public static class $Impl extends Object.$Impl implements LabelUtil {

    protected $Impl(Core core, Label label) {
      super(core, label);
    }
    
    public static native boolean isReadableBy(Label lbl, Principal p);
    
    public static native boolean isWritableBy(Label lbl, Principal p);
    
    public static native ConfPolicy readerPolicy(Core core, Principal owner,
        Principal reader);
    
    public static native IntegPolicy writerPolicy(Core core, Principal owner,
        Principal writer);
    
    public static native Label toLabel(Core core, ConfPolicy confidPolicy,
        IntegPolicy integPolicy);
    
    public static native boolean relabelsTo(Label from, Label to);
  }
}
