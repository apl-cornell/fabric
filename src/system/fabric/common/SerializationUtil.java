package fabric.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class SerializationUtil {
  /**
   * Returns the boolean at the given position in the given byte array.
   */
  public static final boolean booleanAt(byte[] data, int pos) {
    return data[pos] == 1;
  }

  /**
   * Returns the unsigned short that starts at the given position in the given
   * byte array.
   */
  public static final int unsignedShortAt(byte[] data, int pos) {
    return ((data[pos + 0] & 0xff) << 8) | ((data[pos + 1] & 0xff) << 0);
  }

  /**
   * Returns the int that starts at the given position in the given byte array.
   */
  public static final int intAt(byte[] data, int pos) {
    return ((data[pos + 0] & 0xff) << 24) | ((data[pos + 1] & 0xff) << 16)
        | ((data[pos + 2] & 0xff) << 8) | ((data[pos + 3] & 0xff) << 0);
  }

  /**
   * Sets the int that starts at the given position in the given byte array.
   */
  public static final void setIntAt(byte[] data, int pos, int value) {
    data[pos + 0] = (byte) (0xff & (value >> 24));
    data[pos + 1] = (byte) (0xff & (value >> 16));
    data[pos + 2] = (byte) (0xff & (value >> 8));
    data[pos + 3] = (byte) (0xff & (value >> 0));
  }

  /**
   * Returns the long that starts at the given position in the given byte array.
   */
  public static final long longAt(byte[] data, int pos) {
    return ((long) (data[pos + 0] & 0xff) << 56)
        | ((long) (data[pos + 1] & 0xff) << 48)
        | ((long) (data[pos + 2] & 0xff) << 40)
        | ((long) (data[pos + 3] & 0xff) << 32)
        | ((long) (data[pos + 4] & 0xff) << 24)
        | ((long) (data[pos + 5] & 0xff) << 16)
        | ((long) (data[pos + 6] & 0xff) << 8)
        | ((long) (data[pos + 7] & 0xff) << 0);
  }

  /**
   * Sets the long that starts at the given position in the given byte array.
   */
  public static final void setLongAt(byte[] data, int pos, long value) {
    data[pos + 0] = (byte) (0xff & (value >> 56));
    data[pos + 1] = (byte) (0xff & (value >> 48));
    data[pos + 2] = (byte) (0xff & (value >> 40));
    data[pos + 3] = (byte) (0xff & (value >> 32));
    data[pos + 4] = (byte) (0xff & (value >> 24));
    data[pos + 5] = (byte) (0xff & (value >> 16));
    data[pos + 6] = (byte) (0xff & (value >> 8));
    data[pos + 7] = (byte) (0xff & (value >> 0));
  }

  public static final int BUF_LEN = 128;
  private static final byte BUF_LEN_LOG_2 = 7;
  private static final byte BUF_LEN_MASK = 0x7f;

  /**
   * Copies the specified number of bytes from the given DataInput to the given
   * DataOutput.
   *
   * @param in
   *          the DataInput to read from.
   * @param out
   *          the DataOutput to write to.
   * @param length
   *          the number of bytes to copy.
   * @param buf
   *          the buffer to use. Must be of length BUF_LEN.
   */
  public static final void copyBytes(DataInput in, DataOutput out, int length,
      byte[] buf) throws IOException {
    int numLoops = length >> BUF_LEN_LOG_2;
    for (int count = 0; count < numLoops; count++) {
      in.readFully(buf);
      out.write(buf);
    }
    in.readFully(buf, 0, length & BUF_LEN_MASK);
    out.write(buf, 0, length & BUF_LEN_MASK);
  }
}
