package fabric.common;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Util {
  public static final String ALG_SIGNATURE = "MD5withRSA";

  public static final String ALG_SECRET_KEY_GEN = "AES";
  public static final String ALG_SECRET_CRYPTO = "AES/CBC/PKCS5Padding";

  public static final String ALG_PUBLIC_KEY_GEN = "RSA";
  public static final String ALG_PUBLIC_CRYPTO = "RSA/CBC/PKCS5Padding";

  public static Object fromArray(byte[] buf)
    throws ClassNotFoundException {
	  try {
		  ByteArrayInputStream bis = new ByteArrayInputStream(buf);
		  ObjectInputStream ois = new ObjectInputStream(bis);
		  return ois.readUnshared();
	  } catch( final IOException exc ) {
		  throw new InternalError( exc );
	  }
  }

  public static byte[] toArray(Serializable obj) {
    try
    {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	ObjectOutputStream oos = new ObjectOutputStream(bos);
    	oos.writeUnshared(obj);
    	oos.close();
        return bos.toByteArray();
    } catch( final IOException exc ) {
    	throw new InternalError( exc );
    }
  }

  /**
   * Returns an iterable that iterates over the elements of the iterables passed
   * in. The common use is:
   * 
   * <pre>
   * for (T o : Utils.chain(a, b, c))
   *  o.f()
   * </pre>
   * 
   * This is intended to be more efficient and easier than creating a new
   * collection containing the contents.
   * 
   * @param <T>
   *          the types of the elements of the iterables
   * @param iters
   *          the iterables to chain
   * @return an object that can be used to traverse iters in order
   */
  public static <T> Iterable<T> chain(final Iterable<T> ... iters) {
    return new Iterable<T> () {
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
            while (current == null && i < iters.length)
            {
              current = iters[i++].iterator();
              if (!current.hasNext())
                current = null;
            }
          }

          public boolean hasNext() {
            advance();
            return current != null;
          }

          public T next() {
            advance();
            if (current == null)
              throw new NoSuchElementException();
            return current.next();
          }

          public void remove() {
            // TODO Auto-generated method stub

          }
        };
      }
    };
  }
}

