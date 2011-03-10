package fabric.common;

import static fabric.common.Logging.CLASS_HASHING_LOGGER;

import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.util.*;
import java.util.logging.Level;

import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.worker.Store;
import fabric.worker.Worker;

public final class SysUtil {

  private static final Map<String, byte[]> classHashCache = Collections
      .synchronizedMap(new HashMap<String, byte[]>());

  private static final int BUF_LEN = 4096;

  /**
   * Generates a cryptographically secure hash of the given class.
   */
  public static byte[] hash(Class<?> c) throws IOException {
    
    String className = c.getName();

    CLASS_HASHING_LOGGER.log(Level.FINE, "Hashing class by class object: {0}",
        className);

    byte[] result = classHashCache.get(className);
    if (result != null) {
      CLASS_HASHING_LOGGER.finer("  Hash found in cache");
      return result;
    }

    MessageDigest digest = Crypto.digestInstance();

    ClassLoader classLoader = c.getClassLoader();
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

    Class<?> superClass = c.getSuperclass();
    if (superClass != null) digest.update(hash(superClass));

    result = digest.digest();
    classHashCache.put(className, result);

    if (CLASS_HASHING_LOGGER.isLoggable(Level.FINEST)) {
      String hash = new BigInteger(1, result).toString(16);
      Logging.log(CLASS_HASHING_LOGGER, Level.FINEST, "  Hash for {0} is {1}",
          className, hash);
    }

    return result;
  }

  public static byte[] hashClass(String className)
      throws IOException, ClassNotFoundException {
    CLASS_HASHING_LOGGER.log(Level.FINE, "Hashing class by name: {0}",
        className);
    byte[] result = classHashCache.get(className);

    if (result != null) {
      CLASS_HASHING_LOGGER.finer("  Hash found in cache");
      return result;
    }

    return hash(Class.forName(className));

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
  
  /**
   * XXX: This 
   * @throws ClassNotFoundException 
   */
  public static String mangle(String app) throws ClassNotFoundException {
    URI app_uri = URI.create(app);
    
    if(!app_uri.isAbsolute())
      return app_uri.toString();
    else {
      FClass fcls = toFClass(app_uri);
      if(fcls == null)
        throw new ClassNotFoundException(app_uri.toString());
      return pseudoname(fcls);
    }    
  }
  
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

  public static String oid(fabric.lang.Object o) {
    return "fab://" + o.$getStore().name() + "/" + o.$getOnum();
  }

  public static FClass toProxy(String mangled) {
    String cb = codebasePart(mangled);    
    if(cb == null)
      return null;
    
    String className = mangled.substring(cb.length());
    String host = unEscapeHost(storePart(cb));
    Store store = Worker.getWorker().getStore(host);
    long onum = onumPart(cb); 

    Codebase codebase =
        (Codebase) fabric.lang.Object._Proxy
            .$getProxy(new fabric.lang.Object._Proxy(store, onum));
    
    return codebase.resolveClassName(className);
  }
  
  public static String codebasePrefix(Codebase cb) {
    if (cb != null)
      return oid(cb) + "/";
    else return "";
  }
    
  public static String codebasePart(String mangled) {
    int b = mangled.indexOf("$$");
    int e = mangled.indexOf("$$", b+2);
    if(b<0) return "";

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
  
  public static String pseudoname(URI fabref) {
    String store = fabref.getHost();
    String path = fabref.getPath();
    if (path.contains("/")) {
      // parse as a codebase oid + class name
      String[] pair = path.split("/");
      long onum = Long.parseLong(pair[0]);
      String className = pair[1];
      return pseudoname(store, onum) + "." + className;
    }
    return null;
  }
  
  public static String escapeHost(String name) {
    name = name.replaceFirst("^([0-9])", "\\$$1");
    name = name.replace("-", "$_");
    return name;
  }
  
  public static String unEscapeHost(String cbpart) {
    cbpart = cbpart.replaceAll("\\$([0-9])", "$1");
    cbpart = cbpart.replaceAll("\\$_", "-");
    return cbpart;
  }

  public static FClass toFClass(URI fabref) {
    Store store = Worker.getWorker().getStore(fabref.getHost());
    String path = fabref.getPath().substring(1);

    if (path.contains("/")) {
      // parse as a codebase oid + class name
      String[] pair = path.split("/");
      long onum = Long.parseLong(pair[0]);
      String className = pair[1];
      Object o =
          fabric.lang.Object._Proxy.$getProxy(new fabric.lang.Object._Proxy(
              store, onum));
      if (!(o instanceof Codebase))
        throw new ClassCastException("The Fabric object at " + fabref
            + " is not a Codebase.");
      Codebase cb = (Codebase) o;
      return cb.resolveClassName(className);
    }
    else {
      // parse as an fclass oid
      long onum = Long.parseLong(path); 
      Object o =
          fabric.lang.Object._Proxy.$getProxy(new fabric.lang.Object._Proxy(
              store, onum));
      if (!(o instanceof FClass))
        throw new ClassCastException("The Fabric object at " + fabref
            + " is not a Fabric class.");
      return (FClass)o;
    }
  }
  public static String pseudoname(FClass fcls) {
    return packagePrefix(fcls.getCodebase()) + "." + fcls.getName();
  }
  
  public static String pseudoname(Codebase cb) {
    return pseudoname(cb.$getStore().name(), cb.$getOnum());
  }


}
