package fabric.worker.memoize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Union type for primitives and references to Fabric objects.  Meant to be used
 * for sending a call's result across the network.
 */
public class CallResult {
  public final Object value;

  public CallResult(Object value) {
    this.value = value;
  }

  public CallResult(DataInput in) throws IOException {
    boolean primitiveValue = in.readBoolean();
    Object value = null;
    if (primitiveValue) {
      byte[] inlinedData = new byte[in.readInt()];
      in.readFully(inlinedData);
      try {
        value = WrappedJavaInlineable.$wrap((new ObjectInputStream(new
                ByteArrayInputStream(inlinedData))).readObject());
      } catch (ClassNotFoundException e) {
        SEMANTIC_WARRANTY_LOGGER.finest(
            "Couldn't read in supposedly inlineable object: " + e);
      }
    } else {
      Store s = Worker.getWorker().getStore(in.readUTF());
      value = new Object._Proxy(s, in.readLong());
    }
    this.value = value;
  }

  public void write(DataOutput out) throws IOException {
    if (value instanceof WrappedJavaInlineable) {
      out.writeBoolean(true);

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream objOut = new ObjectOutputStream(bos);
      objOut.writeObject(this.value.$unwrap());
      objOut.flush();
      bos.flush();
      byte[] objData = bos.toByteArray();
      out.writeInt(objData.length);
      out.write(objData);
    } else {
      out.writeBoolean(false);
      out.writeUTF(value.$getStore().name());
      out.writeLong(value.$getOnum());
    }
  }
}
