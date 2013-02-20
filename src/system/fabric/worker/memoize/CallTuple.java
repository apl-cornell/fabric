package fabric.worker.memoize;

import java.util.Arrays;

/**
 * Represents a unique memoizable call in the Fabric system.
 */
public class CallTuple {

  protected final fabric.lang.Object callee;
  protected final String method;
  protected final Object[] arguments;

  public CallTuple(fabric.lang.Object callee, String method, Object...
      arguments) {
    this.callee = callee;
    this.method = method;
    this.arguments = arguments;
  }

  @Override
  public String toString() {
    String result = callee.toString() + "." + method + "(";
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
    if (o instanceof CallTuple) {
      CallTuple oCallTup = (CallTuple) o;
      return (callee == oCallTup.callee)
        && (method == oCallTup.method)
        && (Arrays.equals(arguments, oCallTup.arguments));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return callee.hashCode() ^ method.hashCode() ^ Arrays.hashCode(arguments);
  }
}
