package fabric.worker.memoize;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Item to represent the tuple (m, c, a) where m is the method name, c is either
 * the object or class the method was called on, and a is a list of arguments
 * passed to the method.
 */
public class CallTuple implements Callable<Object> {
  public final String method;
  public final fabric.lang.Object callee;
  public final List<Object> args;

  public CallTuple(String method, fabric.lang.Object callee, List<Object> args) {
    this.method= method;
    this.callee = callee;
    this.args = args;
  }

  /**
   * HashCode is the xor of method's name, callee, and args hashcodes.
   */
  @Override
  public int hashCode() {
    int ret = callee.hashCode() ^ method.hashCode();
    for (Object arg : args) {
      ret ^= arg.hashCode();
    }
    return ret;
  }

  /**
   * An object is equal to a given call tuple if it is also a call tuple and has
   * fields which are equal.
   */
  @Override
  public boolean equals(java.lang.Object o) {
    if (o instanceof CallTuple) {
      CallTuple other = (CallTuple) o;

      if (!this.method.equals(other.method) ||
          !this.callee.idEquals(other.callee) ||
          this.args.size() != other.args.size()) {
        return false;
      }

      for (int i = 0; i < this.args.size(); i++) {
        Object thisItem = this.args.get(i);
        Object otherItem = other.args.get(i);
        if ((thisItem instanceof fabric.lang.Object) && (otherItem instanceof
              fabric.lang.Object)) {
          fabric.lang.Object tItem  = (fabric.lang.Object) thisItem;
          fabric.lang.Object oItem  = (fabric.lang.Object) otherItem;
          if (!tItem.idEquals(oItem)) {
            return false;
          }
        } else if (!thisItem.equals(otherItem)) {
          return false;
        }
      }

      return true;
    }

    return false;
  }

  @Override
  public String toString() {
    String callStr = (callee.toString()) + "." + method+ "(";
    boolean first = true;
    for (Object arg : args) {
      if (first) {
        first = false;
      } else {
        callStr += ", ";
      }
      callStr += arg.toString();
    }
    callStr += ")";
    return callStr;
  }

  @Override
  public Object call() {
    try {
      return callee.getClass().getMethod("$memoizedCaller",
                                          String.class,
                                          Object[].class).invoke(callee,
                                                                  method,
                                                                  args.toArray());
    } catch (NoSuchMethodException e) {
      System.err.println("Memoized call method not found: " + this);
    } catch (SecurityException e) {
      System.err.println("Memoized call method security error: " + this);
    } catch (IllegalAccessException e) {
      System.err.println("Memoized call method illegal access: " + this);
    } catch (InvocationTargetException e) {
      System.err.println("Memoized call method bad target invocation: " + this);
    }
    return null;
  }
}
