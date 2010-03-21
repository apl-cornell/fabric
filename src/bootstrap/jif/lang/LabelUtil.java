package jif.lang;

import fabric.worker.Store;
import fabric.lang.Object;
import fabric.lang.Principal;

public interface LabelUtil extends Object {
  
  public static class _Impl extends Object._Impl implements LabelUtil {

    protected _Impl(Store store, Label label) {
      super(store, label);
    }
    
    public static native boolean isReadableBy(Label lbl, Principal p);
    
    public static native boolean isWritableBy(Label lbl, Principal p);
    
    public static native ConfPolicy readerPolicy(Store store, Principal owner,
        Principal reader);
    
    public static native IntegPolicy writerPolicy(Store store, Principal owner,
        Principal writer);
    
    public static native Label toLabel(Store store, ConfPolicy confidPolicy,
        IntegPolicy integPolicy);
    
    public static native boolean relabelsTo(Label from, Label to);
  }
}
