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

  @Override
  public boolean readBoolean() throws IOException {
    return buf.readBoolean();
  }

  @Override
  public byte readByte() throws IOException {
    return buf.readByte();
  }

  @Override
  public char readChar() throws IOException {
    return buf.readChar();
  }

  @Override
  public double readDouble() throws IOException {
    return buf.readDouble();
  }

  @Override
  public float readFloat() throws IOException {
    return buf.readFloat();
  }

  @Override
  public void readFully(byte[] b) throws IOException {
    buf.read(b);
  }

  @Override
  public void readFully(byte[] b, int off, int len) throws IOException {
    buf.read(b, off, len);
  }

  @Override
  public int readInt() throws IOException {
    return buf.readInt();
  }

  @Override
  public String readLine() {
    throw new UnsupportedOperationException();
  }

  @Override
  public long readLong() throws IOException {
    return buf.readLong();
  }

  @Override
  public short readShort() throws IOException {
    return buf.readShort();
  }

  @Override
  public String readUTF() throws IOException {
    return buf.readUTF();
  }

  @Override
  public int readUnsignedByte() throws IOException {
    return 0xff & buf.readByte();
  }

  @Override
  public int readUnsignedShort() throws IOException {
    return 0xffff & buf.readShort();
  }

  @Override
  public int skipBytes(int n) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int bytesRemaining() {
    return buf.bytesRemaining();
  }

  @Override
  public int read(byte[] b) throws IOException {
    return buf.read(b);
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    return buf.read(b, off, len);
  }

}
