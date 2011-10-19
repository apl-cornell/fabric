package fabric.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import polyglot.util.InternalCompilerError;

import fabric.common.exceptions.InternalError;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.worker.Store;
import fabric.worker.Worker;

public final class NSUtil {
  private static List TOPICS = new ArrayList<String>();
  static {
    TOPICS.add("nsutil");
  }
  /**
   * Convenience field for building filesystem URIs
   */
  public static URI file = URI.create("file:///");

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

    //URI may contain trailing '/'
    String onum = codebase_oid.getPath().substring(1);
    if(onum.endsWith("/"))
      onum = onum.substring(0, onum.length()-1);

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
    int idx = path.lastIndexOf('/');
    if(idx+1 < path.length())
      path = path.substring(0,idx+1);
    
    try {
      return new URI(scheme, auth, path, null, null);
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
    int idx = path.lastIndexOf('/');
    if(idx == path.length()-1) 
      return path.substring(path.lastIndexOf(idx-1,'/')+1,idx);
    else
      return path.substring(path.lastIndexOf('/')+1);
  }
  /**
   * Fetch the codebase object with the specified OID
   * @param uri
   * @return
   */
  public static Codebase fetch_codebase(URI oid) {
    try {
    Store store = Worker.getWorker().getStore(oid.getAuthority());

    //URI may contain trailing '/'
    String s = oid.getPath().substring(1);
    if(s.endsWith("/"))
      s = s.substring(0, s.length()-1);
    Long onum = Long.parseLong(s); // skip leading

    Object o =
        fabric.lang.Object._Proxy.$getProxy(new fabric.lang.Object._Proxy(
            store, onum));
    if (!(o instanceof Codebase))
      throw new InternalCompilerError("The Fabric object at " + oid
          + " is not a codebase.");
    return (Codebase) o;
    } catch (StringIndexOutOfBoundsException e) {
      throw new InternalCompilerError("Invalid oid for codebase: "+ oid);
    }
  }
  /**
   * Fetch the class object for an absolute classname of the form:
   * fab://store/codebase_onum/pkg.name
   * @param uri
   * @return
   */
  public static FClass fetch_fclass(URI classname) {
      URI cb_oid = dirname(classname);
      String name = basename(classname);
      Codebase codebase = fetch_codebase(cb_oid);
      return codebase.resolveClassName(name);
  }

  /**
   * Return the namespace representing a codebase.
   * @param o the codebase object.
   * @return the URI representing the codebase
   */
  public static URI namespace(Codebase codebase) {
    return URI.create("fab://" + codebase.$getStore().name() + "/"
        + codebase.$getOnum() + "/");  
  }
  
  /**
   * Process a path string of the form <URI>:/localdir/:... into URIs and add to a list
   * @param uris the list to add the URIs to
   * @param path the path-style string of URIs and directories, with URIs delimited by '<' and '>'
   * @return true if path contains any remote references
   */
  public static boolean processPathString(List<URI> uris, String path) {
    boolean needWorker = false;
    if (path.startsWith("@")) {
      try {
        BufferedReader lr =
            new BufferedReader(new FileReader(path.substring(1)));
        path = lr.readLine();
      } catch (FileNotFoundException e) {
        throw new InternalCompilerError(e);
      } catch (IOException e) {
        throw new InternalCompilerError(e);
      }
    }
    int idx = 0;
    while (idx < path.length()) {
      if (path.charAt(idx) == '<') {
        int end = path.indexOf('>',idx);
        if(end < 0)
          throw new InternalCompilerError("Invalid path");
        String cb = path.substring(idx + 1, end);
        if(!cb.endsWith("/"))
          cb +="/";
        
        URI u = URI.create(cb);
        uris.add(u);

        if(u.getScheme().equals("fab"))
          needWorker = true;
        idx = end + 1;
        
      } else if (path.charAt(idx) == File.pathSeparatorChar) {
        idx++;
      } else {
        int end = path.indexOf(File.pathSeparatorChar,idx);

        String dir="";
        if(end < 0) {
          dir = path.substring(idx);
          idx = path.length();
        }
        else {
          dir = path.substring(idx, end);
          idx = end;
        }
        if(!"".equals(dir)) {
          if(!dir.endsWith("/"))
            dir +="/";

          URI u = URI.create(dir);
          
          if (u.isAbsolute())
            uris.add(u);
          else {
            File f = new File(dir);
            uris.add(file.resolve(f.getAbsolutePath() + "/"));
          }
        }
      }
    }
    return needWorker;
  }

}
