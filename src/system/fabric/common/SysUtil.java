package fabric.common;

import static fabric.common.Logging.CLASS_HASHING_LOGGER;

import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import polyglot.types.Named;
import fabric.common.ClassRef.FabricClassRef;
import fabric.common.exceptions.InternalError;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Convenience class containing generally useful methods such as functional
 * programming idioms and hashing methods.
 * 
 * This class has also adopted a bunch of mucky name mangling code that should
 * be removed.
 */

public final class SysUtil {

  /**
   * Caches hashes of platform classes that weren't compiled with fabc. Maps
   * class names to their hashes.
   */
  private static final Map<String, byte[]> classHashCache = Collections
      .synchronizedMap(new HashMap<String, byte[]>());

  /**
   * Size of buffer to use for reading bytecode while hashing classes.
   */
  private static final int BUF_LEN = 4096;

  /**
   * Generates a cryptographically secure hash of a platform class.
   * 
   * @param c
   *          the Class object for a class that is not stored in Fabric. If it's
   *          a Fabric class, this is the interface corresponding to the Fabric
   *          type, and not the _Proxy or _Impl classes.
   */
  @SuppressWarnings("unchecked")
  public static byte[] hashPlatformClass(Class<?> c) throws IOException {
    boolean hashing_Impl = false;
    Class<?> ifaceClass = null;
    
    // There are two cases here. If the class extends fabric.lang.Object and was
    // compiled with filc/fabc, we use the compiler-generated hash. Otherwise,
    // we hash the class's bytecode.
    
    if (fabric.lang.Object.class.isAssignableFrom(c)) {
      // We have a Fabric class. Use the filc/fabc-generated hash, if any. If we
      // get any exceptions from attempting to do this, we assume that the class
      // wasn't compiled by filc/fabc and hash the bytecode instead.
      try {
        return classHashFieldValue((Class<? extends fabric.lang.Object>) c);
      } catch (NoSuchFieldException e) {
      } catch (SecurityException e) {
      } catch (IllegalArgumentException e) {
      } catch (IllegalAccessException e) {
      }
      
      // Fabric class wasn't compiled by filc/fabc. Instead, we hash the
      // bytecode for the _Impl class, if any, to ensure we cover the class's
      // code.
      for (Class<?> nested : c.getClasses()) {
        if (nested.getSimpleName().equals("_Impl")) {
          hashing_Impl = true;
          ifaceClass = c;
          c = nested;
          break;
        }
      }
    }
    
    // Class wasn't compiled by filc/fabc.  Hash the bytecode instead.
    String className = c.getName();

    CLASS_HASHING_LOGGER.log(Level.FINE, "Hashing platform class: {0}",
        className);

    byte[] result = classHashCache.get(className);
    if (result != null) {
      CLASS_HASHING_LOGGER.finer("  Hash found in cache");
      return result;
    }

    MessageDigest digest = Crypto.digestInstance();

    ClassLoader classLoader;
    if (Worker.isInitialized()) {
      classLoader = Worker.getWorker().getClassLoader();
    } else {
      classLoader = c.getClassLoader();
    }
    
    if (classLoader == null) {
      classLoader = ClassLoader.getSystemClassLoader();
    }

    String classFileName = className.replace('.', '/') + ".class";

    if (CLASS_HASHING_LOGGER.isLoggable(Level.FINEST)) {
      URL classResource = classLoader.getResource(classFileName);
      Logging.log(CLASS_HASHING_LOGGER, Level.FINEST,
          "  Using {0} to load class bytecode from {1}", classLoader,
          classResource);
    }

    InputStream classIn = classLoader.getResourceAsStream(classFileName);

    if (classIn == null) {
      Logging.log(CLASS_HASHING_LOGGER, Level.WARNING,
          "Unable to load {0} from {1} using {2}", className, classFileName,
          classLoader);
      throw new InternalError("Class not found: " + className);
    }

    byte[] buf = new byte[BUF_LEN];
    int count = classIn.read(buf);
    while (count != -1) {
      digest.update(buf, 0, count);
      count = classIn.read(buf);
    }
    classIn.close();

    if (!c.isInterface()) {
      // Include the super class.
      Class<?> superClass = c.getSuperclass();
      if (superClass != null) {
        // Assume the superclass is also a platform class.
        digest.update(hashPlatformClass(superClass));
      }
    }
    
    // Include declared interfaces, if any.
    Class<?>[] interfaces =
        hashing_Impl ? ifaceClass.getInterfaces() : c.getInterfaces();
    for (Class<?> iface : interfaces) {
      // Assume the interface is also a platform class.
      digest.update(hashPlatformClass(iface));
    }

    result = digest.digest();

    classHashCache.put(className, result);

    if (CLASS_HASHING_LOGGER.isLoggable(Level.FINEST)) {
      String hash = new BigInteger(1, result).toString(16);
      Logging.log(CLASS_HASHING_LOGGER, Level.FINEST, "  Hash for {0} is {1}",
          className, hash);
    }

    return result;
  }
  
  /**
   * Returns the class hash for the Fabric class referred by the given
   * FabricClassRef.
   */
  public static byte[] hashFClass(FabricClassRef fcr) {
    Class<? extends fabric.lang.Object> clazz = fcr.toClass();
    try {
      return classHashFieldValue(clazz);
    } catch (NoSuchFieldException e) {
      throw new InternalError(e);
    } catch (SecurityException e) {
      throw new InternalError(e);
    } catch (IllegalArgumentException e) {
      throw new InternalError(e);
    } catch (IllegalAccessException e) {
      throw new InternalError(e);
    }
  }

  /**
   * Returns the value of the static "$classHash" field in the given class. This
   * contains the class hash that was computed by the compiler.
   */
  private static byte[] classHashFieldValue(
      Class<? extends fabric.lang.Object> clazz) throws NoSuchFieldException,
      SecurityException, IllegalArgumentException, IllegalAccessException {
    return (byte[]) clazz.getField("$classHash").get(null);
  }

  /**
   * Returns the _Impl class for the given Fabric class.
   * 
   * @param clazz
   *          the Class object for a Fabric class. This is the interface
   *          corresponding to the Fabric type, and not the _Proxy or _Impl
   *          classes.
   * @return the _Impl corresponding to <code>clazz</code>. If no _Impl class is
   *         found (i.e., clazz represents a Fabric interface),
   *         <code>null</code> is returned.
   */
  @SuppressWarnings("unchecked")
  public static Class<? extends _Impl> getImplClass(
      Class<? extends fabric.lang.Object> clazz) {
    for (Class<?> nested : clazz.getClasses()) {
      if (nested.getSimpleName().equals("_Impl")) {
        return (Class<? extends _Impl>) nested;
      }
    }
    
    return null;
  }

  public static URL locateClass(String className)
      throws ClassNotFoundException {
    // TODO: copied from hash(className)
    Class<?> c = Class.forName(className);

    ClassLoader classLoader = c.getClassLoader();
    if (classLoader == null) {
      classLoader = ClassLoader.getSystemClassLoader();
    }

    URL result =
        classLoader.getResource(className.replace('.', '/') + ".class");

    return result;

  }

  /**
   * <p>
   * Returns an iterable that iterates over the elements of the iterables passed
   * in. The common use is:
   * 
   * <pre>
   * for (T o : Utils.chain(a, b, c))
   *  o.f()
   * </pre>
   * 
   * which will call f() on each element of a, then each element of b, then each
   * element of c.
   * </p>
   * <p>
   * This is intended to be more efficient and easier than creating a new
   * collection containing the contents.
   * </p>
   * 
   * @param <T>
   *          the types of the elements of the iterables
   * @param iters
   *          the iterables to chain
   * @return an object that can be used to traverse iters in order
   */
  public static <T> Iterable<T> chain(final Iterable<T>... iters) {
    return new Iterable<T>() {
      public Iterator<T> iterator() {
        return new Iterator<T>() {
          private int i = 0;
          private Iterator<T> current = null;

          /**
           * Skip over any empty iters. After this method returns, either
           * current is null (and there are no further elements to return), or
           * current.hasNext().
           */
          private void advance() {
            while (current == null && i < iters.length) {
              current = iters[i++].iterator();
              if (!current.hasNext()) current = null;
            }
          }

          public boolean hasNext() {
            advance();
            return current != null;
          }

          public T next() {
            advance();
            if (current == null) throw new NoSuchElementException();
            T result = current.next();
            if (!current.hasNext()) current = null;
            return result;
          }

          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }
  
  /**
   * Turns an object into an array of bytes using Java serialization.
   */
  public static byte[] serialize(Object obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(obj);
    oos.flush();
    baos.flush();
    
    return baos.toByteArray();
  }
  
  /**
   * Turns an array of bytes into an object using Java serialization.
   */
  public static Object deserialize(byte[] bytes) throws IOException,
      ClassNotFoundException {
    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    ObjectInputStream ois = new ObjectInputStream(bais);
    return ois.readObject();
  }

  /**
   * A Thunk<T> represents a delayed execution resulting in type T.  The
   * computation create() is executed the first time get() is called, and its
   * value is stored and returned on subsequent calls to get(). 
   */
  public static abstract class Thunk<T> {
    private boolean initialized = false;
    private T value = null; 
    
    public final T get() {
      if (! initialized) {
        value = create();
        initialized = true;
      }
      
      return value;
    }
    
    protected abstract T create();
  }
  
  /*
  ** crufty name mangling stuff below this line. *******************************
  */

  /**
   * The following words are used in this documentation
   * 
   *  - fabric name
   *      fully qualified names from the source language
   *      example: fabric.lang.Object
   *
   *  - java name
   *      fully qualified Java names that the compiler outputs
   *      example: fabric.lang.Object$_Impl
   *      example: [mangled codebase].my.app.Foo$_Proxy
   *      example: [mangled codebase].my.app.Foo
   *      platform classes do not have codebase parts
   *
   *  - mangled codebase
   *      $$[codebase store].onum_[codebase onum]$$
   */

  
  /**
   * @param app
   *
   * @return
   *    
   * @throws
   *    ClassNotFoundException 
   */
  public static String mangle(String app) throws ClassNotFoundException {
    URI app_uri = URI.create(app);
    
    if(!app_uri.isAbsolute())
      return app_uri.toString() + "$_Impl";
    else {
      FClass fcls = toFClass(app_uri);
      if(fcls == null)
        throw new ClassNotFoundException(app_uri.toString());
      return pseudoname(fcls) + "$_Impl";
    }
  }
  
  /**
   * @param cb
   *    either a codebase or null
   * @return
   *    "" if cb is null
   *    "[mangled codebase reference]." if cb is not null
   */
  public static String packagePrefix(Codebase cb) {
    if (cb != null) {
      return pseudoname(cb) + ".";
    }
    else return "";
  }
  
  /**
   * @param cb
   *    either a codebase or null
   * @return
   *    "" if cb is null
   *    "[mangled codebase reference]" if cb is not null
   */
  public static String packageName(Codebase cb) {
    if (cb != null) {
      return pseudoname(cb);
    }
    else return "";
  }

  /**
   * @param o
   *    a fabric reference
   * @return
   *    fab://[object store]/[object onum]
   */
  public static String oid(fabric.lang.Object o) {
    return "fab://" + o.$getStore().name() + "/" + o.$getOnum();
  }

  /**
   * @param mangled
   *    the java name of a class stored in Fabric (see above)
   * @return
   *    the FClass associated with the java name
   * @throws ClassNotFoundException
   *    if the name is malformed, refers to a platform class, or fails to resolve
   */
  public static FClass toProxy(String javaName) throws ClassNotFoundException {
    Matcher m = javaNameRegex.matcher(javaName);
    if (!m.matches())
      throw new ClassNotFoundException("failed to parse java class name " + javaName);
    
    Store  codebaseStore = Worker.getWorker().getStore(m.group(1));
    long   codebaseOnum  = Long.parseLong(m.group(2)); 
    String className     = m.group(3);

    Codebase codebase =
        (Codebase) _Proxy.$getProxy(new _Proxy(codebaseStore, codebaseOnum));
    
    FClass result = codebase.resolveClassName(className);
    if (result == null)
      throw new ClassNotFoundException("Failed to load " + className + " in codebase " + codebase);
    
    return result;
  }
  
  // matches a java class name.
  // group 1: codebase store or null for platform class
  // group 2: codebase onum  or null for platform class
  // group 3: fabric class name
  // group 4: $_Impl or $_Proxy or ""
  private static final Pattern javaNameRegex = Pattern.compile("\\$\\$(.*)\\.onum_(\\d*)\\$\\$\\.(.*?)((?:\\$_Impl)|(?:\\$_Proxy)|)");
  
  /**
   * @param cb
   *    a codebase or null
   * @return
   *    "" if cb is null
   *    "fab://[codebase store]/[codebase onum]" otherwise
   */
  public static String codebasePrefix(Codebase cb) {
    if (cb != null)
      return oid(cb) + "/";
    else return "";
  }
  
  /**
   * @param mangled
   *    
   * @return
   *    TODO
   */
  public static String codebasePart(String mangled) {
    int b = mangled.indexOf("$$");
    int e = mangled.indexOf("$$", b+2);
    if(b<0) return "";

    return mangled.substring(b+2, e);
  }

  
  public static long onumPart(String codebaseName) {
    int e = codebaseName.lastIndexOf('.');
    String onum = codebaseName.substring(e+".onum_".length());
    return Long.parseLong(onum);
  }
  
  public static String storePart(String codebaseName) {
    int e = codebaseName.lastIndexOf(".onum_");
    return codebaseName.substring(0, e);
  }

  /**
   * @param pseudoname
   *    [mangled codebase]
   * @return
   *    null if pseudoname does not contain "$$"
   */
  public static String fabricname(String pseudoname) {    
    String cb = codebasePart(pseudoname);    
    if("".equals(cb))
      return null;

    String[] host = unEscapeHost(storePart(cb)).split("[.]");
    int i = host.length-1;
    StringBuilder sb = new StringBuilder(host[i--]);
    for(; i>=0; i--) {
      sb.append('.');
      sb.append(host[i]);
    }
    String store = sb.toString();
    long onum = onumPart(cb);
    String className = pseudoname.substring("$$".length()+cb.length()+"$$.".length());
    return "fab://"+store+"/"+onum+"/"+className;
  }
  
  private static String escapeHost(String name) {
    name = name.replaceFirst("^([0-9])", "\\$$1");
    name = name.replace("-", "$_");
    return name;
  }
  
  private static String unEscapeHost(String cbpart) {
    cbpart = cbpart.replaceAll("\\$([0-9])", "$1");
    cbpart = cbpart.replaceAll("\\$_", "-");
    return cbpart;
  }

  /**
   * @param fabref a URI either of the form
   *        fab://codebase_store/codebase_onum/class_name
   *        where class_name is a fabric name
   *        or
   *        fab://class_store/class_onum
   * @return the corresponding FClass object
   * @throws ClassCastException if the oid's do not resolve to the correct types
   */
  public static FClass toFClass(URI fabref) {
    Store store = Worker.getWorker().getStore(fabref.getHost());
    String path = fabref.getPath().substring(1);

    if (path.contains("/")) {
      // parse as a codebase oid + class name
      String[] pair = path.split("/");
      long onum = Long.parseLong(pair[0]);
      String className = pair[1];
      Object o = _Proxy.$getProxy(new _Proxy(store, onum));
      if (!(o instanceof Codebase))
        throw new ClassCastException("The Fabric object at " + fabref
            + " is not a Codebase.");
      Codebase cb = (Codebase) o;
      return cb.resolveClassName(className);
    }
    else {
      // parse as an fclass oid
      long onum = Long.parseLong(path); 
      Object o = _Proxy.$getProxy(new _Proxy(store, onum));
      if (!(o instanceof FClass))
        throw new ClassCastException("The Fabric object at " + fabref
            + " is not a Fabric class.");
      return (FClass)o;
    }
  }

  public static String pseudoname(FClass fcls) {
    return packagePrefix(fcls.getCodebase()) + fcls.getName();
  }
  
  public static String pseudoname(String store, long onum) {
    String[] host = store.split("[.]");
    StringBuilder sb = new StringBuilder("$$");
    for(int i = host.length - 1; i>=0; i--) {
      sb.append(escapeHost(host[i]));
      sb.append('.');
    }
    sb.append("onum_");
    sb.append(onum);
    sb.append("$$");
    return sb.toString();
  }
  
  public static String pseudoname(Codebase cb) {
    return pseudoname(cb.$getStore().name(), cb.$getOnum());
  }

  public static String absoluteName(FClass f) {
    Codebase cb = f.getCodebase();
    return "fab://" + cb.$getStore().name() + "/" + cb.$getOnum() + "/"
        + f.getName();
  }
  
  public static boolean isPlatformType(Named name) {
    return isPlatformType(name.fullName());
  }

  public static boolean isPlatformType(String fullName) {
    return fullName.startsWith("java")
    || fullName.startsWith("fabric")
    || fullName.startsWith("jif");
  }

}
