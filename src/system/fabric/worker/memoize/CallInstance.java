package fabric.worker.memoize;

import java.util.Arrays;

/**
 * Represents a unique memoizable call in the Fabric system.
 */
public class CallInstance {

  public final fabric.lang.Object target;
  public final String method;
  public final Object[] arguments;

  public CallInstance(fabric.lang.Object target, String method, Object...
      arguments) {
    this.target = target;
    this.method = method;
    this.arguments = arguments;
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
  public boolean equals(Object o) {
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
  public long id() {
    /* TODO: Actually implement */
    return 0l;
  }
}
