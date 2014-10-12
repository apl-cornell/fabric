package fabric.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import polyglot.util.InternalCompilerError;
import fabric.common.exceptions.InternalError;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.Object._Proxy;
import fabric.worker.Store;
import fabric.worker.Worker;

public final class NSUtil {
  private static List<String> TOPICS = new ArrayList<>();
  static {
    TOPICS.add("nsutil");
  }
  /**
   * Convenience field for building filesystem URIs
   */
  public static URI file = URI.create("file:///");

  public static boolean isRemoteNamespace(URI ns) {
    return ns.getScheme().equals("fab") && !ns.isOpaque();
  }

  /**
   * Creates a mangled Java package name from a Fabric codebase oid.
   *
   * @param codebase_oid
   *          The oid of a codebase
   * @return the parent package name for classes in the codebase
   */
  public static String javaPackageName(URI codebase_oid) {
    if (codebase_oid.isOpaque())
      throw new InternalError("Cannot create java package name for "
          + codebase_oid);
    // URI is of form : 'fab://store/123'
    String store = codebase_oid.getAuthority();

    // URI may contain trailing '/'
    String onum = codebase_oid.getPath().substring(1);
    if (onum.endsWith("/")) onum = onum.substring(0, onum.length() - 1);

    String[] host = store.split("[.]");
    StringBuilder sb = new StringBuilder("$$");
    for (int i = host.length - 1; i >= 0; i--) {
      sb.append(escapeHost(host[i]));
      sb.append('.');
    }
    sb.append("onum_");
    sb.append(onum);
    sb.append("$$");
    // String is of form : '$$store.onum_123$$'
    return sb.toString();
  }

  /**
   * Return the mangled Java interface name for the Fabric class specified by
   * the absolute name fclass_ref.
   */
  public static String javaClassName(URI fclass_ref) {
    URI codebase = dirname(fclass_ref);
    String className = basename(fclass_ref);
    return javaPackageName(codebase) + "." + className;
  }

  /**
   * Return the mangled Java interface name of a Fabric class object.
   */
  public static String javaClassName(fabric.lang.FClass._Proxy fClass) {
    return javaClassName(absoluteName(fClass));
  }

  /**
   * Return the mangled Java Impl class name for the Fabric class specified by
   * the absolute name fclass_ref.
   */
  public static String javaImplName(URI fclass_ref) {
    return javaClassName(fclass_ref) + "$_Impl";
  }

  /**
   * Return the mangled Java Impl class name for the Fabric class specified by
   * the absolute name fclass_ref.
   */
  public static String javaProxyName(URI fclass_ref) {
    return javaClassName(fclass_ref) + "$_Proxy";
  }

  /**
   * Returns the Java _Impl class name of a Fabric or FabIL class suitable for passing to
   * ClassLoader.loadClass(). If className is the absolute name of a published
   * Fabric class qualified by a fabric codebase URI, toJavaImplName will return
   * a mangled java name.
   *
   * @param className
   * @return
   */
  public static String toJavaImplName(String className) {
    return toJavaName(className, "$_Impl");
  }

  /**
   * Returns the Java _Proxy class name of a Fabric or FabIL class suitable for passing to
   * ClassLoader.loadClass(). If className is the absolute name of a published
   * Fabric class qualified by a fabric codebase URI, toJavaImplName will return
   * a mangled java name.
   *
   * @param className
   * @return
   */
  public static String toJavaProxyName(String className) {
    return toJavaName(className, "$_Proxy");
  }

  /**
   * Returns the Java _Static interface name of a Fabric or FabIL class suitable for passing to
   * ClassLoader.loadClass(). If className is the absolute name of a published
   * Fabric class qualified by a fabric codebase URI, toJavaImplName will return
   * a mangled java name.
   *
   * @param className
   * @return
   */
  public static String toJavaStaticName(String className) {
    return toJavaName(className, "$_Static");
  }

  /**
   * Returns the Java _Static._Impl class name of a Fabric or FabIL class suitable for passing to
   * ClassLoader.loadClass(). If className is the absolute name of a published
   * Fabric class qualified by a fabric codebase URI, toJavaImplName will return
   * a mangled java name.
   *
   * @param className
   * @return
   */
  public static String toJavaStaticImplName(String className) {
    return toJavaName(className, "$_Static$_Impl");
  }

  /**
   * Returns the Java _Static._Proxy class name of a Fabric or FabIL class suitable for passing to
   * ClassLoader.loadClass(). If className is the absolute name of a published
   * Fabric class qualified by a fabric codebase URI, toJavaImplName will return
   * a mangled java name.
   *
   * @param className
   * @return
   */
  public static String toJavaStaticProxyName(String className) {
    return toJavaName(className, "$_Static$_Proxy");
  }

  /**
   * Returns the Java name of a Fabric or FabIL class suitable for passing to
   * ClassLoader.loadClass(). If className is the absolute name of a published
   * Fabric class qualified by a fabric codebase URI, toJavaProxyName will return
   * a mangled java name.
   *
   * @param className The Fabric or FabIL class name.
   * @param suffix The Java inner class name desired. 
   * @return
   */
  private static String toJavaName(String className, String suffix) {
    URI cls = URI.create(className);
    if (!cls.isAbsolute()) {
      return className + suffix;
    } else return javaImplName(cls);
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
    if (uri.isOpaque())
      throw new InternalCompilerError("Cannot get dirname of opaque URI: "
          + uri);
    String scheme = uri.getScheme();
    String auth = uri.getAuthority();
    String path = uri.getPath();
    int idx = path.lastIndexOf('/');
    if (idx + 1 < path.length()) path = path.substring(0, idx + 1);

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
    if (uri.isOpaque())
      throw new InternalCompilerError("Cannot get basename of opaque URI: "
          + uri);

    String path = uri.getPath();
    int idx = path.lastIndexOf('/');
    if (idx == path.length() - 1)
      return path.substring(path.lastIndexOf(idx - 1, '/') + 1, idx);
    else return path.substring(path.lastIndexOf('/') + 1);
  }

  /**
   * Fetch the codebase object with the specified OID
   *
   * @param uri
   * @return
   */
  public static Codebase fetch_codebase(URI oid) {
    try {
      Store store = Worker.getWorker().getStore(oid.getAuthority());

      // URI may contain trailing '/'
      String s = oid.getPath().substring(1);
      if (s.endsWith("/")) s = s.substring(0, s.length() - 1);
      Long onum = Long.parseLong(s); // skip leading

      Object o =
          fabric.lang.Object._Proxy.$getProxy(new fabric.lang.Object._Proxy(
              store, onum));
      if (!(o instanceof Codebase))
        throw new InternalCompilerError("The Fabric object at " + oid
            + " is not a codebase.");
      return (Codebase) o;
    } catch (StringIndexOutOfBoundsException e) {
      throw new InternalCompilerError("Invalid oid for codebase: " + oid);
    }
  }

  /**
   * Fetch the class object for an absolute classname of the form:
   * fab://store/codebase_onum/pkg.name
   *
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
   * Returns false if <code>javaName</code> has a mangled codebase prefix,
   * otherwise the name refers to a platform class.
   *
   * @param javaName
   * @return
   */
  public static boolean isPlatformName(String javaName) {
    Matcher m = javaNameRegex.matcher(javaName);
    return m.matches() && m.group(1) == null;
  }

  /**
   * @param javaName
   *          the java name of a class stored in Fabric (see above)
   * @return the FClass associated with the java name
   * @throws ClassNotFoundException
   *           if the name is malformed, refers to a platform class, or fails to
   *           resolve
   */
  public static FClass toProxy(String javaName) throws ClassNotFoundException {
    Matcher m = javaNameRegex.matcher(javaName);
    if (!m.matches() || m.group(2) == null)
      throw new ClassNotFoundException("failed to parse java class name "
          + javaName);

    Store codebaseStore = Worker.getWorker().getStore(m.group(1));
    long codebaseOnum;
    try {
      codebaseOnum = Long.parseLong(m.group(2));
    } catch (NumberFormatException e) {
      throw new ClassNotFoundException("Invalid onum in java name:" + javaName
          + ":" + m.group(2));
    }
    String className = m.group(3);

    Codebase codebase =
        (Codebase) _Proxy.$getProxy(new _Proxy(codebaseStore, codebaseOnum));

    FClass result = codebase.resolveClassName(className);
    if (result == null)
      throw new ClassNotFoundException("Failed to load " + className
          + " in codebase " + codebase + "[" + javaName + "]");

    return result;
  }

  // matches a java class name.
  // group 1: codebase store or null for platform class
  // group 2: codebase onum or null for platform class
  // group 3: fabric class name
  // group 4: $_Impl or $_Proxy or ""
  private static final Pattern javaNameRegex =
      Pattern
          .compile("(?:\\$\\$(.*)\\.onum_(\\d*)\\$\\$\\.)?(.*?)([$.]_Static)?((?:[$.]_Impl)|(?:[$.]_Proxy)|)");

  /**
   * Return the namespace representing a codebase.
   *
   * @param o
   *          the codebase object.
   * @return the URI representing the codebase
   */
  public static URI namespace(Codebase codebase) {
    return URI.create("fab://" + codebase.$getStore().name() + "/"
        + codebase.$getOnum() + "/");
  }

  /**
   * Returns the "absolute name" of an FClass, a human-friendly(-ish) URI of the
   * form : "fab://codebaseStore/codebaseOnum/className"
   */
  public static URI absoluteName(FClass f) {
    Codebase cb = f.getCodebase();
    return namespace(cb).resolve(f.getName());
  }

  /**
   * Process a path string of the form <URI>:/localdir/:... into URIs and add to a list
   * @param uris the list to add the URIs to
   * @param path the path-style string of URIs and directories, with URIs delimited by '<' and '>'
   * @return true if path contains any remote references
   */
  public static List<URI> processPathString(String path) {
    // XXX This breaks in the presence of Windows paths.
    List<URI> locations = new ArrayList<>();
    while (!path.isEmpty()) {
      String remaining = "";
      if (path.startsWith("@")) {
        try {
          int next_idx = path.indexOf(":");
          String pathFile;
          if (next_idx > 0)
            pathFile = path.substring(1, next_idx);
          else pathFile = path.substring(1);

          if (next_idx > 0) remaining = path.substring(next_idx + 1);

          try (BufferedReader lr = new BufferedReader(new FileReader(pathFile))) {
            path = lr.readLine();
          }
        } catch (FileNotFoundException e) {
          throw new InternalCompilerError(e);
        } catch (IOException e) {
          throw new InternalCompilerError(e);
        }
      }
      int idx = 0;
      while (idx < path.length()) {
        if (path.charAt(idx) == '<') {
          int end = path.indexOf('>', idx);
          if (end < 0) throw new InternalCompilerError("Invalid path");
          String cb = path.substring(idx + 1, end);
          if (!cb.endsWith("/")) cb += "/";

          URI u = URI.create(cb);
          locations.add(u);

          //          if (u.getScheme().equals("fab")) needWorker = true;
          idx = end + 1;

        } else if (path.charAt(idx) == File.pathSeparatorChar) {
          idx++;
        } else {
          int end = path.indexOf(File.pathSeparatorChar, idx);

          String dir = "";
          if (end < 0) {
            dir = path.substring(idx);
            idx = path.length();
          } else {
            dir = path.substring(idx, end);
            idx = end;
          }
          if (!"".equals(dir)) {
            if (!dir.endsWith("/")) dir += "/";

            URI u = URI.create(dir);

            if (u.isAbsolute())
              locations.add(u);
            else {
              File f = new File(dir);
              String suf = f.isDirectory() ? "/" : "";
              locations.add(file.resolve(f.getAbsolutePath() + suf));
            }
          }
        }
      }
      path = remaining;
    }
    return locations;
  }
}
