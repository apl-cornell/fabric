package fabric.dissemination.pastry.messages;

import java.io.DataInput;
import java.io.IOException;

import rice.p2p.commonapi.rawserialization.InputBuffer;

/**
 * A class to merge DataInput and InputBuffer. Unfortunately, pastry had to
 * introduce the InputBuffer interface, which is essentially identical to
 * DataInput, but isn't.
 */
public class DataInputBuffer implements DataInput, InputBuffer {
  
  private final InputBuffer buf;
  
  public DataInputBuffer(InputBuffer buf) {
    this.buf = buf;
  }

  public boolean readBoolean() throws IOException {
    return buf.readBoolean();
  }

  public byte readByte() throws IOException {
    return buf.readByte();
  }

  public char readChar() throws IOException {
    return buf.readChar();
  }

  public double readDouble() throws IOException {
    return buf.readDouble();
  }

  public float readFloat() throws IOException {
    return buf.readFloat();
  }

  public void readFully(byte[] b) throws IOException {
    buf.read(b);
  }

  public void readFully(byte[] b, int off, int len) throws IOException {
    buf.read(b, off, len);
  }

  public int readInt() throws IOException {
    return buf.readInt();
  }

  public String readLine() {
    throw new UnsupportedOperationException();
  }

  public long readLong() throws IOException {
    return buf.readLong();
  }

  public short readShort() throws IOException {
    return buf.readShort();
  }

  public String readUTF() throws IOException {
    return buf.readUTF();
  }

  public int readUnsignedByte() throws IOException {
    return 0xff & buf.readByte();
  }

  public int readUnsignedShort() throws IOException {
    return 0xffff & buf.readShort();
  }

  public int skipBytes(int n) {
    throw new UnsupportedOperationException();
  }

  public int bytesRemaining() {
    return buf.bytesRemaining();
  }

  public int read(byte[] b) throws IOException {
    return buf.read(b);
  }

  public int read(byte[] b, int off, int len) throws IOException {
    return buf.read(b, off, len);
  }

}
