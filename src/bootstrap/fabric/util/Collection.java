package fabric.util;

import fabric.lang.Object;
import fabric.worker.Store;

public interface Collection extends Object {
  Iterator iterator();
  int size();
}
