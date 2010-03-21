package fabric.worker;

import java.lang.ref.SoftReference;

import fabric.common.SerializedObject;

public class SerializedObjectSoftRef extends SoftReference<SerializedObject> {
  
  final long onum;

  public SerializedObjectSoftRef(RemoteStore store, SerializedObject obj) {
    super(obj, store.serializedRefQueue);
    this.onum = obj.getOnum();
  }

}
