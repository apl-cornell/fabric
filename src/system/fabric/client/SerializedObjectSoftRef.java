package fabric.client;

import java.lang.ref.SoftReference;

import fabric.common.SerializedObject;

public class SerializedObjectSoftRef extends SoftReference<SerializedObject> {
  
  final long onum;

  public SerializedObjectSoftRef(RemoteCore core, SerializedObject obj) {
    super(obj, core.serializedRefQueue);
    this.onum = obj.getOnum();
  }

}
