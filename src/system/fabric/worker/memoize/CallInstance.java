package fabric.worker.memoize;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import fabric.lang.Object;

/**
 * Represents a unique memoizable call in the Fabric system.
 */
public class CallInstance {

  public final fabric.lang.Object target;
  public final String method;
  public final Object[] arguments;
  private byte[] id;

  public CallInstance(Object target, String method, Object...  arguments) {
    this.target = target;
    this.method = method;
    this.arguments = arguments;

    try {
      MessageDigest algorithm = MessageDigest.getInstance("MD5");
      algorithm.reset();
      algorithm.update(method.getBytes("UTF-8"));
      algorithm.update(ByteBuffer.allocate(8).putLong(target.$getOnum()).array());
      for (Object arg : arguments) {
        algorithm.update(ByteBuffer.allocate(8).putLong(arg.$getOnum()).array());
      }
      this.id = algorithm.digest();
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
    return target.hashCode() ^ method.hashCode() ^ Arrays.hashCode(arguments);
  }

  /**
   * ID number to uniquely represent this call signature.  This is
   * deterministically computed based on the target, methodName, and arguments.
   * This should be usable wherever one could use a object ID while not
   * conflicting with any OIDs in the system.
   */
  public byte[] id() {
    return id;
  }
}
