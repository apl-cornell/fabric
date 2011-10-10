package fabric.common;

import java.net.URI;

import fabric.common.exceptions.InternalError;

public final class NSUtil {
  /**
   * Creates a mangled Java package name from a Fabric codebase oid.
   * @param codebase_oid The oid of a codebase
   * @return the parent package name for classes in the codebase
   */
  public static String javaPackageName(URI codebase_oid) {
    if(codebase_oid.isOpaque()) 
      throw new InternalError("Cannot create java package name for " + codebase_oid);
    //URI is of form : 'fab://store/123'
    String store = codebase_oid.getAuthority();
    String onum = codebase_oid.getPath().substring(1);
    String[] host = store.split("[.]");
    StringBuilder sb = new StringBuilder("$$");
    for(int i = host.length - 1; i>=0; i--) {
      sb.append(escapeHost(host[i]));
      sb.append('.');
    }
    sb.append("onum_");
    sb.append(onum);
    sb.append("$$");
    //String is of form : '$$store.onum_123$$'
    return sb.toString();
  }

  /**
   * Escapes characters that are legal in host names, but not in package names.
   * 
   * @param name
   * @return
   */
  private static String escapeHost(String name) {
    name = name.replaceFirst("^([0-9])", "\\$$1");
    name = name.replace("-", "$_");
    return name;
  }
}
