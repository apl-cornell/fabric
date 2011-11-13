package fabric.lang.arrays;

import fabric.worker.Store;
import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;

public interface byteArray extends Object {
  byte get(int i);

  byte set(int i, byte value);
  
  int  get$length();

  public static class _Impl extends Object._Impl implements byteArray {
    public _Impl(Store store, Label label, ConfPolicy accessLabel, int length) {
      super(store);
    }

    public native byte get(int i);

    public native byte set(int i, byte value);
    
    public native int  get$length();
  }
}
