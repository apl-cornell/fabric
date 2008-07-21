package fabric.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Util {
  public static final String ALG_SIGNATURE = "MD5withRSA";

  public static final String ALG_SECRET_KEY_GEN = "AES";
  public static final String ALG_SECRET_CRYPTO = "AES/CBC/PKCS5Padding";

  public static final String ALG_PUBLIC_KEY_GEN = "RSA";
  public static final String ALG_PUBLIC_CRYPTO = "RSA/CBC/PKCS5Padding";

  /**
   * <p>Returns an iterable that iterates over the elements of the iterables passed
   * in. The common use is:
   * 
   * <pre>
   * for (T o : Utils.chain(a, b, c))
   *  o.f()
   * </pre>
   * 
   * which will call f() on each element of a, then each element of b, then each
   * element of c. </p>
   * 
   * <p>This is intended to be more efficient and easier than
   * creating a new collection containing the contents.</p>
   * 
   * @param <T>
   *                the types of the elements of the iterables
   * @param iters
   *                the iterables to chain
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
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }
}
