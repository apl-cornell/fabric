package webapp;

import fabric.util.*;

public class FabricUtils {

  public static Integer length(Object o) {
    if (o instanceof fabric.util.Collections.UnmodifiableCollection._Proxy)
      return length((fabric.util.Collections.UnmodifiableCollection._Proxy)o);
    else if (o instanceof fabric.util.Map._Proxy) {
      return length((fabric.util.Map._Proxy)o);
    } else {
      return -1;
    }
  }

  public static Integer length(fabric.util.Collections.UnmodifiableCollection._Proxy p) {
    return new Integer(p.size());
  }

  public static Integer length(fabric.util.Collection p) {
    return new Integer(p.size());
  }

  public static Integer length(fabric.util.Map._Proxy c) {
    return new Integer(c.size());
  }
}
