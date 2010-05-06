package fabric.common;

import static fabric.common.Logging.CLASS_HASHING_LOGGER;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.util.*;
import java.util.logging.Level;

public final class Util {

  private static final Map<String, byte[]> classHashCache =
      Collections.synchronizedMap(new HashMap<String, byte[]>());

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

    Logging.log(CLASS_HASHING_LOGGER, Level.FINEST,
        "  Using {0} to load class bytecode from {1}", classLoader,
        classFileName);

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

    return result;
  }

  public static byte[] hashClass(String className) throws IOException,
      ClassNotFoundException {
    CLASS_HASHING_LOGGER.log(Level.FINE, "Hashing class by name: {0}",
        className);
    byte[] result = classHashCache.get(className);
    if (result != null) {
      CLASS_HASHING_LOGGER.finer("  Hash found in cache");
      return result;
    }
    return hash(Class.forName(className));
  }

  public static URL locateClass(String className) throws ClassNotFoundException {
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

}
