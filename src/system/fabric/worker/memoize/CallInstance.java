package fabric.worker.memoize;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;

import fabric.common.Logging;
import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;
import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Represents a unique memoizable call in the Fabric system.
 */
public class CallInstance {

  public final Object target;
  public final String method;
  public final Object[] arguments;
  public final byte[] id;

  private static byte[] generateId(Object target, String method,
      Object[] arguments) {
    byte[] idVal = null;
    try {
      MessageDigest algorithm = MessageDigest.getInstance("MD5");
      algorithm.reset();
      algorithm.update(method.getBytes("UTF-8"));
      algorithm.update(ByteBuffer.allocate(8).putLong(target.$getOnum()).array());
      for (Object arg : arguments) {
        if (arg instanceof WrappedJavaInlineable) {
          algorithm.update((byte) 0);
          //I need to double check that this is the same across all instances of
          //any primitive's wrapper type.
          algorithm.update(arg.$unwrap().toString().getBytes("UTF-8"));
        } else {
          algorithm.update((byte) 1);
          algorithm.update(ByteBuffer.allocate(8).putLong(arg.$getOnum()).array());
        }
      }
      idVal = algorithm.digest();
    } catch (NoSuchAlgorithmException e) {
    } catch (UnsupportedEncodingException e) {
    }
    return idVal;
  }

  public CallInstance(Object target, String method, Object...  arguments) {
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
      if (in.readBoolean()) {
        byte[] inlinedData = new byte[in.readInt()];
        in.readFully(inlinedData);
        try {
          argsArray[i] = WrappedJavaInlineable.$wrap((new ObjectInputStream(new
                  ByteArrayInputStream(inlinedData))).readObject());
        } catch (ClassNotFoundException e) {
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
              "Couldn't read in supposedly inlineable object: {0}", e);
        }
      } else {
        Store argStore = Worker.getWorker().getStore(in.readUTF());
        argsArray[i] = new Object._Proxy(argStore, in.readLong());
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
      if (arg instanceof WrappedJavaInlineable) {
        out.writeBoolean(true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(bos);
        objOut.writeObject(arg.$unwrap());
        objOut.flush();
        bos.flush();
        byte[] objData = bos.toByteArray();
        out.writeInt(objData.length);
        out.write(objData);
      } else {
        out.writeBoolean(false);
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
      if (arg instanceof WrappedJavaInlineable) {
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
      Method m = c.getMethod("$callNonMemoized", String.class,
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
      throw new InternalError("Error running call " + toString() + ": " +
          e.getMessage());
    } catch (IllegalAccessException e) {
      throw new InternalError("Error running call " + toString() + ": " +
          e.getMessage());
    } catch (InvocationTargetException e) {
      throw new InternalError("Error running call " + toString() + ": " +
          e.getMessage());
    }
  }
}
