package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import fabric.common.FastSerializable;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Utility for an inlineable reference to some Fabric object.  Primarily
 * intended to help with inlineable structures like TreatySet.
 */
@SuppressWarnings("serial")
public final class OidRef<T extends fabric.lang.Object>
    implements FastSerializable, Serializable {

  public final String objStoreName;
  public final long objOnum;

  private transient T cached;

  public OidRef(String objStoreName, long objOnum) {
    this.objStoreName = objStoreName;
    this.objOnum = objOnum;
  }

  public OidRef(Store objStore, long objOnum) {
    this(objStore.name(), objOnum);
  }

  public <S extends T> OidRef(S obj) {
    this(obj.$getStore(), obj.$getOnum());
  }

  public OidRef(DataInput in) throws IOException {
    this.objStoreName = in.readUTF();
    this.objOnum = in.readLong();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeUTF(this.objStoreName);
    out.writeLong(this.objOnum);
  }

  public T get() {
    if (cached == null) cached = (T) new fabric.lang.Object._Proxy(
        Worker.getWorker().getStore(objStoreName), objOnum).$getProxy();
    return cached;
  }
}
