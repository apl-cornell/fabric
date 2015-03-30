package fabric.worker.memoize;

import static fabric.common.Logging.COMPUTATION_WARRANTY_LOGGER;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Represents a unique memoizable call in the Fabric system.
 */
public class CallInstance implements Serializable, Comparable<CallInstance> {

  public Object target;
  public String method;
  public Object[] arguments;
  public byte[] id;

  private static byte[] generateId(Object target, String method,
      Object[] arguments) {
    byte[] idVal = null;
    try {
      MessageDigest algorithm = MessageDigest.getInstance("MD5");
      algorithm.reset();
      algorithm.update(method.getBytes("UTF-8"));
      algorithm.update(ByteBuffer.allocate(8).putLong(target.$getOnum())
          .array());
      for (Object arg : arguments) {
        if (arg == null) {
          algorithm.update((byte) 2);
        } else if (arg instanceof WrappedJavaInlineable) {
          algorithm.update((byte) 0);
          //I need to double check that this is the same across all instances of
          //any primitive's wrapper type.
          algorithm.update(arg.$unwrap().toString().getBytes("UTF-8"));
        } else {
          algorithm.update((byte) 1);
          algorithm.update(ByteBuffer.allocate(8).putLong(arg.$getOnum())
              .array());
        }
      }
      idVal = algorithm.digest();
    } catch (NoSuchAlgorithmException e) {
    } catch (UnsupportedEncodingException e) {
    }
    return idVal;
  }

  public CallInstance(Object target, String method, Object... arguments) {
    this.target = target;
    this.method = method;
    this.arguments = arguments;
    this.id = generateId(target, method, arguments);
  }

  public CallInstance(DataInput in) throws IOException {
    Store s = Worker.getWorker().getStore(in.readUTF());
    Object targetObj = new Object._Proxy(s, in.readLong());

    String methodName = in.readUTF();

    int numArgs = in.readInt();
    Object[] argsArray = new Object[numArgs];
    for (int i = 0; i < numArgs; i++) {
      int type = in.readInt();
      if (type == 0) {
        argsArray[i] = null;
      } else if (type == 1) {
        byte[] inlinedData = new byte[in.readInt()];
        in.readFully(inlinedData);
        try {
          argsArray[i] =
              WrappedJavaInlineable.$wrap((new ObjectInputStream(
                  new ByteArrayInputStream(inlinedData))).readObject());
        } catch (ClassNotFoundException e) {
          Logging.log(COMPUTATION_WARRANTY_LOGGER, Level.FINEST,
              "Couldn't read in supposedly inlineable object: {0}", e);
        }
      } else if (type == 2) {
        Store argStore = Worker.getWorker().getStore(in.readUTF());
        argsArray[i] = new Object._Proxy(argStore, in.readLong());
      } else {
        throw new InternalError("This should not happen!");
      }
    }

    this.target = targetObj;
    this.method = methodName;
    this.arguments = argsArray;
    this.id = generateId(targetObj, methodName, argsArray);
  }

  public void write(DataOutput out) throws IOException {
    out.writeUTF(target.$getStore().name());
    out.writeLong(target.$getOnum());

    out.writeUTF(method);

    out.writeInt(arguments.length);
    for (Object arg : arguments) {
      if (arg == null) {
        out.writeInt(0);
      } else if (arg instanceof WrappedJavaInlineable) {
        out.writeInt(1);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(bos);
        objOut.writeObject(arg.$unwrap());
        objOut.flush();
        bos.flush();
        byte[] objData = bos.toByteArray();
        out.writeInt(objData.length);
        out.write(objData);
      } else {
        out.writeInt(2);
        out.writeUTF(arg.$getStore().name());
        out.writeLong(arg.$getOnum());
      }
    }
  }

  @Override
  public String toString() {
    String result = "OBJ" + target.$getOnum() + "." + method + "(";
    boolean first = true;
    for (Object arg : arguments) {
      if (!first) {
        result += ", ";
      }
      first = false;
      if (arg == null) {
        result += "null";
      } else if (arg instanceof WrappedJavaInlineable) {
        result += arg.$unwrap().toString();
      } else {
        result += "OBJ" + arg.$getOnum();
      }
    }
    result += ")";
    return result;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (o instanceof CallInstance) {
      CallInstance oCallTup = (CallInstance) o;
      return Arrays.equals(id, oCallTup.id);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(id);
  }

  /**
   * Runs the call and gives back the return value.  This should be used only
   * for semantic warranty defense purposes.
   */
  public java.lang.Object runCall() {
    Class<?> c = target.fetch().getClass();
    try {
      Method m =
          c.getMethod("$callNonMemoized", String.class,
              java.lang.Object[].class);

      java.lang.Object[] fetchedArgs = new java.lang.Object[arguments.length];
      for (int i = 0; i < arguments.length; i++) {
        fetchedArgs[i] = arguments[i].fetch();
        if (fetchedArgs[i] instanceof WrappedJavaInlineable) {
          fetchedArgs[i] = ((Object) fetchedArgs[i]).$unwrap();
        }
      }

      return m.invoke(target.fetch(), method, fetchedArgs);
    } catch (NoSuchMethodException e) {
      throw new InternalError("NoSuchMethodException running call "
          + toString() + ": " + e.getMessage());
    } catch (IllegalAccessException e) {
      throw new InternalError("IllegalAccessException running call "
          + toString() + ": " + e.getMessage());
    } catch (InvocationTargetException e) {
      e.getTargetException().printStackTrace();
      throw new InternalError("InvocationTargetException running call "
          + toString() + ":\n\t" + e.getTargetException() + "\n\t" + method
          + "\n\t" + c.getName());
    }
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    DataOutputStream outD = new DataOutputStream(out);
    write(outD);
  }

  private void readObject(ObjectInputStream in) throws IOException,
      ClassNotFoundException {
    CallInstance copy = new CallInstance(new DataInputStream(in));
    this.target = copy.target;
    this.method = copy.method;
    this.arguments = copy.arguments;
    this.id = copy.id;
  }

  private void readObjectNoData(ObjectInputStream in)
      throws ObjectStreamException {
    throw new ObjectStreamException() {
    };
  }

  @Override
  public int compareTo(CallInstance other) {
    if (other.hashCode() == hashCode()) return 0;
    return other.hashCode() > hashCode() ? -1 : 1;
  }
}
