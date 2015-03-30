package fabric.worker.memoize;

import static fabric.common.Logging.COMPUTATION_WARRANTY_LOGGER;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

import fabric.common.Logging;
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
    int valueType = in.readInt();
    Object value = null;
    if (valueType == 1) {
      byte[] inlinedData = new byte[in.readInt()];
      in.readFully(inlinedData);
      try {
        value =
            WrappedJavaInlineable.$wrap((new ObjectInputStream(
                new ByteArrayInputStream(inlinedData))).readObject());
      } catch (ClassNotFoundException e) {
        Logging.log(COMPUTATION_WARRANTY_LOGGER, Level.FINEST,
            "Couldn't read in supposedly inlineable object: {0}", e);
      }
    } else if (valueType == 2) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      value = new Object._Proxy(s, in.readLong());
    }
    this.value = value;
  }

  public void write(DataOutput out) throws IOException {
    if (value == null) {
      out.writeInt(0);
    } else if (value instanceof WrappedJavaInlineable) {
      out.writeInt(1);

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream objOut = new ObjectOutputStream(bos);
      objOut.writeObject(this.value.$unwrap());
      objOut.flush();
      bos.flush();
      byte[] objData = bos.toByteArray();
      out.writeInt(objData.length);
      out.write(objData);
    } else {
      out.writeInt(2);
      out.writeUTF(value.$getStore().name());
      out.writeLong(value.$getOnum());
    }
  }
}
