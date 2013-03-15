package fabric.worker.memoize;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import fabric.lang.Object;
import fabric.lang.WrappedJavaInlineable;

/**
 * Represents a unique memoizable call in the Fabric system.
 */
public class CallInstance {

  public final Object target;
  public final String method;
  public final Object[] arguments;
  private CallID id;

  public CallInstance(Object target, String method, Object...  arguments) {
    this.target = target;
    this.method = method;
    this.arguments = arguments;

    try {
      MessageDigest algorithm = MessageDigest.getInstance("MD5");
      algorithm.reset();
      algorithm.update(method.getBytes("UTF-8"));
      algorithm.update(ByteBuffer.allocate(8).putLong(target.$getOnum()));
      for (Object arg : arguments) {
        if (arg instanceof WrappedJavaInlineable) {
          algorithm.update((byte) 0);
          //TODO: I need to double check that this is the same across all
          //instances of any primitive's wrapper type.
          algorithm.update(arg.$unwrap().toString().getBytes("UTF-8"));
        } else {
          algorithm.update((byte) 1);
          algorithm.update(ByteBuffer.allocate(8).putLong(arg.$getOnum()));
        }
      }
      this.id = new CallID(algorithm.digest());
    } catch (NoSuchAlgorithmException e) {
    } catch (UnsupportedEncodingException e) {
    }
  }

  @Override
  public String toString() {
    String result = target.toString() + "." + method + "(";
    boolean first = true;
    for (Object arg : arguments) {
      if (!first) {
        result += ", ";
      }
      first = false;
      result += arg.toString();
    }
    result += ")";
    return result;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (o instanceof CallInstance) {
      CallInstance oCallTup = (CallInstance) o;
      return (target == oCallTup.target)
        && (method == oCallTup.method)
        && (Arrays.equals(arguments, oCallTup.arguments));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  /**
   * ID number to uniquely represent this call signature.  This is
   * deterministically computed based on the target, methodName, and arguments.
   * This should be usable wherever one could use a object ID while not
   * conflicting with any OIDs in the system.
   */
  public CallID id() {
    return id;
  }
}
