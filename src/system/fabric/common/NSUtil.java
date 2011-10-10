package fabric.common;

import java.net.URI;
import java.net.URISyntaxException;

import polyglot.util.InternalCompilerError;

import fabric.common.exceptions.InternalError;
import fabric.lang.Codebase;
import fabric.worker.Store;
import fabric.worker.Worker;

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

  /** 
   * @param uri
   * @return the uri representing the parent directory
   */
  public static URI dirname(URI uri) {
    if(uri.isOpaque())
      throw new InternalCompilerError("Cannot get dirname of opaque URI: " + uri);
    String scheme = uri.getScheme();
    String auth = uri.getAuthority();
    String path = uri.getPath();
    try {
      return new URI(scheme, auth, path.substring(0, path.lastIndexOf('/')), null, null);
    } catch (URISyntaxException e) {
      throw new InternalCompilerError(e);
    }
  }
  
  /** 
   * @param uri
   * @return the last component in uri's path
   */
  public static String basename(URI uri) {
    if(uri.isOpaque())
      throw new InternalCompilerError("Cannot get basename of opaque URI: " + uri);

    String path = uri.getPath();
    return path.substring(path.lastIndexOf('/'));
  }
  /**
   * Fetch the codebase object with the specified OID
   * @param uri
   * @return
   */
  public static Codebase fetch_codebase(URI oid) {
    Store store = Worker.getWorker().getStore(oid.getAuthority());
    Long onum = Long.parseLong(oid.getPath().substring(1)); // skip leading
    // '/'
    Object o =
        fabric.lang.Object._Proxy.$getProxy(new fabric.lang.Object._Proxy(
            store, onum));
    if (!(o instanceof Codebase))
      throw new InternalCompilerError("The Fabric object at " + oid
          + " is not a codebase.");
    return (Codebase) o;
  }

  /**
   * Return the OID of a Fabric object.
   * @param o the Fabric object.
   * @return the URI representing the OID
   */
  public static URI oid(fabric.lang.Object o) {
    return URI.create("fab://" + o.$getStore().name() + "/" + o.$getOnum());
  }

}
