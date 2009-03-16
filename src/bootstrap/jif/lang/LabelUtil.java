package jif.lang;

import fabric.client.Core;
import fabric.lang.Object;
import fabric.lang.Principal;

public interface LabelUtil extends Object {
  
  public static class _Impl extends Object._Impl implements LabelUtil {

    protected _Impl(Core core, Label label) {
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
