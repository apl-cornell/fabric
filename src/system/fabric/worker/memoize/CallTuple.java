package fabric.worker.memoize;

import java.util.*;
//import fabric.lang.Object;

/**
 * Item to represent the tuple (m, c, a) where m is the method name, c is either
 * the object or class the method was called on, and a is a list of arguments
 * passed to the method.
 */
public class CallTuple {
  private String method;
  private Object callee;
  private List<Object> args;

  public CallTuple(String methodName, Object callee, List<Object> args) {
    this.method = methodName;
    this.callee = callee;
    this.args = args;
  }

  /**
   * An object is equal to a given call tuple if it is also a call tuple and has
   * fields which are equal.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof CallTuple) {
      CallTuple other = (CallTuple) o;
      return this.method.equals(other.method) &&
        this.callee.equals(other.callee) && this.args.equals(other.args);
    }
    return false;
  }

  public String getMethod() {
    return method;
  }

  public Object getCallee() {
    return callee;
  }

  public List<Object> getArgs() {
    return args;
  }
}
