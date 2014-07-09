/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.tools.storebrowser;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import fabric.util.Iterator;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Provides data for the GUI browser based on Fabric
 */
public class FabricDataProvider implements DataProvider {
  StoreObject store;

  /**
   * Abstract Node object, contains abstract methods for getting 
   * children nodes and node values.
   */
  abstract class Getter {
    protected List<Object> getChildrenForObject(Object obj) {
      if (obj == null) return Collections.emptyList();
      try {
        Class<?> c = obj.getClass();

        List<Object> toReturn = new LinkedList<Object>();

        if (c.getDeclaringClass() != null
            && fabric.util.Map.class.isAssignableFrom(c.getDeclaringClass())) {

          fabric.util.Map m =
              (fabric.util.Map) ((fabric.lang.Object) obj).$getProxy();
          Iterator i = m.keySet().iterator();
          while (i.hasNext()) {
            fabric.lang.Object key = i.next();
            String keyStr = key.toString();
            toReturn.add(new RootObject(keyStr, m.get(key)));
          }
        } else {
          for (Field f : c.getDeclaredFields()) {
            f.setAccessible(true);

            toReturn.add(new DataObject(f.getName(), f.get(obj), f.getType()));
          }
        }

        return toReturn;
      } catch (Exception ex) {
        return generateError("Exception: " + ex.getMessage(), ex);
      }

    }

    abstract List<Object> getChildren();

    abstract String getValue();
  }

  /**
   * Basic class Node 
   */
  class DataObject extends Getter {
    Object obj;
    String fieldName;
    Class<?> t;

    DataObject(String fieldName, Object val, Class<?> t) {
      //Fetch if it is a fabric object
      if (val != null && val instanceof fabric.lang.Object) {
        fabric.lang.Object fabObj = (fabric.lang.Object) val;
        obj = fabObj.fetch();
        this.t = obj.getClass();
      } else {
        obj = val;
        this.t = t;
      }
      this.fieldName = fieldName;
    }

    @Override
    public String toString() {
      if (t.isPrimitive()) {
        return fieldName + " = " + obj.toString();
      } else if (obj != null && obj instanceof String) {
        return fieldName + " = \"" + obj.toString() + "\"";
      } else {
        return fieldName + " : " + t;
      }
    }

    @Override
    public String getValue() {
      return (obj == null) ? "null" : obj.toString();
    }

    @Override
    public List<Object> getChildren() {
      if (t.isPrimitive() || (obj != null && obj instanceof String))
        return Collections.emptyList();
      else return getChildrenForObject(obj);
    }
  }

  List<Object> generateError(String msg, Throwable ex) {
    return Arrays.asList((Object) new ErrorObject(msg, ex));
  }

  /**
   * Error object, used to display exceptions while browsing the tree
   */
  class ErrorObject extends Getter {
    String msg;
    Throwable ex;

    ErrorObject(String msg, Throwable ex) {
      this.msg = msg;
      this.ex = ex;
    }

    @Override
    public List<Object> getChildren() {
      return Collections.emptyList();
    }

    @Override
    public String toString() {
      return msg;
    }

    @Override
    public String getValue() {
      return ex.getMessage();
    }
  }

  /**
   * A root map key-value pair node.
   */
  class RootObject extends Getter {
    fabric.lang.Object obj;
    String key;

    public RootObject(String key, fabric.lang.Object val) {
      obj = val.fetch();
      this.key = key;
    }

    @Override
    public String toString() {
      return "\"" + key + "\"" + " : " + obj.getClass();
    }

    @Override
    public List<Object> getChildren() {
      return getChildrenForObject(obj);
    }

    @Override
    public String getValue() {
      return key;
    }

  }

  /**
   * A store node. The children of a store node are all of its keys 
   * in the root map.
   */
  class StoreObject extends Getter {
    Store s;
    List<Object> rootObjects;

    StoreObject(String storeName) {
      s = Worker.getWorker().getStore(storeName);
      rootObjects = new LinkedList<Object>();
      Worker.runInSubTransaction(new fabric.worker.Worker.Code<Void>() {
        @Override
        public Void run() {
          fabric.util.Map m = s.getRoot();
          Iterator i = m.keySet().iterator();
          while (i.hasNext()) {
            fabric.lang.Object key = i.next();
            String keyStr = key.toString();
            rootObjects.add(new RootObject(keyStr, m.get(key)));
          }
          return null;
        }
      });

    }

    StoreObject(String storeName, long onum) {
      s = Worker.getWorker().getStore(storeName);
      String name = "fab://" + storeName + "/" + onum;
      fabric.lang.Object obj = new fabric.lang.Object._Proxy(s, onum);
      DataObject dataObj = new DataObject(name, obj, obj.getClass());
      rootObjects = new LinkedList<Object>();
      rootObjects.add(dataObj);
    }

    @Override
    public List<Object> getChildren() {
      return Collections.unmodifiableList(rootObjects);
    }

    @Override
    public String getValue() {
      return s.name();
    }

    @Override
    public String toString() {
      return "fab://" + s.name();
    }
  }

  public FabricDataProvider(String workerName, String storeName) {
    try {
      Worker.initialize(workerName);
      store = new StoreObject(storeName);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public FabricDataProvider(String workerName, String storeName, long onum) {
    try {
      Worker.initialize(workerName);
      store = new StoreObject(storeName, onum);

    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public Object getRoot() {
    return store;
  }

  @Override
  public List<Object> getChildrenForNode(final Object obj) {
    return Worker.runInSubTransaction(new Worker.Code<List<Object>>() {
      @Override
      public List<Object> run() {
        Getter g = (Getter) obj;
        return g.getChildren();
      }
    });
  }

  @Override
  public String getDescriptionForNode(final Object obj) {
    return Worker.runInSubTransaction(new Worker.Code<String>() {
      @Override
      public String run() {
        Getter g = (Getter) obj;
        return g.getValue();
      }
    });
  }
}
