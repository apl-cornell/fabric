package fabric.common;

import static fabric.common.Logging.CLASS_HASHING_LOGGER;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import fabric.common.ClassRef.FabricClassRef;
import fabric.common.exceptions.InternalError;
import fabric.lang.Object._Impl;
import fabric.worker.Worker;

/**
 * Convenience class containing generally useful methods such as functional
 * programming idioms and hashing methods.
 */

public final class SysUtil {

  /**
   * Caches hashes of platform classes that weren't compiled with fabc. Maps
   * class names to their hashes.
   */
  private static final Map<String, byte[]> classHashCache =
      Collections.synchronizedMap(new HashMap<String, byte[]>());

  /**
   * Size of buffer to use for reading bytecode while hashing classes.
   */
  private static final int BUF_LEN = 4096;

  public static String getNodeCN(String nodeName, long principalOnum) {
    return principalOnum + ":" + nodeName;
  }

  public static long getPrincipalOnum(String nodeCN) {
    try {
      int idx = nodeCN.indexOf(':');
      return Long.parseLong(nodeCN.substring(0, idx));
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(e);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static String getNodeName(String nodeCN) {
    int idx = nodeCN.indexOf(':');
    return nodeCN.substring(idx + 1);
  }

  /**
   * Generates a cryptographically secure hash of a platform class.
   *
   * @param c
   *          the Class object for a class that is not stored in Fabric. If it's
   *          a Fabric class, this is the interface corresponding to the Fabric
   *          type, and not the _Proxy or _Impl classes.
   */
  public static byte[] hashPlatformClass(Class<?> c) throws IOException {
    boolean hashing_Impl = false;
    Class<?> ifaceClass = null;

    // There are three cases here. If the class extends fabric.lang.Object and
    // was compiled with filc/fabc, we use the compiler-generated hash.
    // Otherwise, if the class is java.lang.Object or java.lang.Cloneable, we
    // hash its name. Otherwise, we hash the class's bytecode.

    if (fabric.lang.Object.class.isAssignableFrom(c)) {
      // We have a Fabric class. Use the filc/fabc-generated hash, if any. If we
      // get any exceptions from attempting to do this, we assume that the class
      // wasn't compiled by filc/fabc and hash the bytecode instead.
      try {
        @SuppressWarnings("unchecked")
        Class<? extends fabric.lang.Object> fabricClass =
            (Class<? extends fabric.lang.Object>) c;
        return classHashFieldValue(fabricClass);
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

    // Class wasn't compiled by filc/fabc.
    String className = c.getName();

    CLASS_HASHING_LOGGER.log(Level.FINE, "Hashing platform class: {0}",
        className);

    // Check the cache.
    byte[] result = classHashCache.get(className);
    if (result != null) {
      CLASS_HASHING_LOGGER.finer("  Hash found in cache");
      return result;
    }

    // Not found in cache. Need to compute the hash.
    MessageDigest digest = Crypto.digestInstance();

    if (c.equals(java.lang.Object.class)
        || c.equals(java.lang.Cloneable.class)) {
      // Have java.lang.Object or java.lang.Cloneable. Hash the class's name.
      // This is a bit of a hack to get different versions of Java to play with
      // each other.
      digest.update(className.getBytes("UTF-8"));
    } else {
      // Hash the class's byte code.
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

      try (InputStream classIn =
          classLoader.getResourceAsStream(classFileName)) {
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
      }
    }

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
    Class<? extends fabric.lang.Object> clazz = fcr.toDeclaringClass();

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
  public static Class<? extends _Impl> getImplClass(
      Class<? extends fabric.lang.Object> clazz) {
    for (Class<?> nested : clazz.getClasses()) {
      if (nested.getSimpleName().equals("_Impl")) {
        @SuppressWarnings("unchecked")
        Class<? extends _Impl> implClass = (Class<? extends _Impl>) nested;
        return implClass;
      }
    }

    return null;
  }

  public static URL locateClass(String className)
      throws ClassNotFoundException {
    Class<?> c = Worker.getWorker().getClassLoader().loadClass(className);

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
  @SafeVarargs
  public static <T> Iterable<T> chain(final Iterable<T>... iters) {
    return new Iterable<T>() {
      @Override
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

          @Override
          public boolean hasNext() {
            advance();
            return current != null;
          }

          @Override
          public T next() {
            advance();
            if (current == null) throw new NoSuchElementException();
            T result = current.next();
            if (!current.hasNext()) current = null;
            return result;
          }

          @Override
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
  public static Object deserialize(byte[] bytes)
      throws IOException, ClassNotFoundException {
    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    ObjectInputStream ois = new ObjectInputStream(bais);
    return ois.readObject();
  }

  /**
   * A Thunk<T> represents a delayed execution resulting in type T. The
   * computation create() is executed the first time get() is called, and its
   * value is stored and returned on subsequent calls to get().
   */
  public static abstract class Thunk<T> {
    private boolean initialized = false;
    private T value = null;

    public final T get() {
      if (!initialized) {
        value = create();
        initialized = true;
      }

      return value;
    }

    protected abstract T create();
  }

}
