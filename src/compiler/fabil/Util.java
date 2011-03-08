package fabil;

import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.worker.Store;
import fabric.worker.Worker;

public class Util {
  public static String packagePrefix(Codebase cb) {
    if (cb != null) {
      return pseudoname(cb) + ".";
    }
    else return "";
  }
  
  public static String packageName(Codebase cb) {
    if (cb != null) {
      return pseudoname(cb);
    }
    else return "";
  }

  private static String pseudoname(Codebase cb) {
    String[] host = cb.$getStore().name().split("[.]");
    StringBuilder sb = new StringBuilder("$$");
    for(int i = host.length - 1; i>0; i--) {
      sb.append(host[i]);
      sb.append('.');
    }
    sb.append("onum_");
    sb.append(cb.$getOnum());
    sb.append("$$");
    return sb.toString();
  }
  
  public static String fabricOid(Codebase cb) {
    return "";
  }

  public static String codebasePart(String mangled) {
    int b = mangled.indexOf("$$");
    int e = mangled.indexOf("$$", b+2);
    return mangled.substring(b+2, e);
  }

  public static long onumPart(String codebaseName) {
    int e = codebaseName.lastIndexOf('.');
    int b = codebaseName.lastIndexOf('.', e-1); 
    String onum = codebaseName.substring(b+".onum_".length(), e);
    return Long.parseLong(onum);
  }
  
  public static String storePart(String codebaseName) {
    int e = codebaseName.lastIndexOf(".onum_");
    return codebaseName.substring(0, e);
  }

  public static FClass toProxy(String mangled) {
    String cb = codebasePart(mangled);    
    String className = mangled.substring(cb.length());
    Store store = Worker.getWorker().getStore(storePart(cb));
    long onum = onumPart(cb); 

    Codebase codebase =
        (Codebase) fabric.lang.Object._Proxy
            .$getProxy(new fabric.lang.Object._Proxy(store, onum));
    
    return codebase.resolveClassName(className);
  }
}
