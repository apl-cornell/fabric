package fabric.worker.memoize;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class StaticCallTuple extends CallTuple {

  public final String className;

  public StaticCallTuple(String method, String className, List<Object> args) {
    super(method, null, args);
    this.className = className;
  }

  /**
   * HashCode is the xor of method's name, callee name, and args hashcodes.
   */
  @Override
  public int hashCode() {
    int ret = className.hashCode() ^ method.hashCode();
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
    if (o instanceof StaticCallTuple) {
      StaticCallTuple other = (StaticCallTuple) o;

      if (!this.method.equals(other.method) ||
          !this.className.equals(other.className) ||
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
    String callStr = className + "." + method + "(";
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
      return Class.forName(className).getMethod("$memoizedCaller",
                                                String.class,
                                                Object[].class).invoke(callee,
                                                                        method,
                                                                        args.toArray());
    } catch (ClassNotFoundException e) {
      System.err.println("Memoized call class not found!");
    } catch (NoSuchMethodException e) {
      System.err.println("Memoized call method not found!");
    } catch (SecurityException e) {
      System.err.println("Memoized call method security error!");
    } catch (IllegalAccessException e) {
      System.err.println("Memoized call method illegal access!");
    } catch (InvocationTargetException e) {
      System.err.println("Memoized call method bad invocation target!");
    }
    return null;
  }
}
