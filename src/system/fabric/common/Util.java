package fabric.common;

import java.io.*;

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
}

