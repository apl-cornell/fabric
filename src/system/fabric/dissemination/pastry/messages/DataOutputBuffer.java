package fabric.dissemination.pastry.messages;

import java.io.DataOutput;
import java.io.IOException;

import rice.p2p.commonapi.rawserialization.OutputBuffer;

/**
 * A class to merge DataOutput and OutputBuffer. Unfortunately, pastry
 * introduced the OutputBuffer interface which is almost like a DataOutput, but
 * isn't.
 */
public class DataOutputBuffer implements DataOutput, OutputBuffer {
  
  private final OutputBuffer buf;
  
  public DataOutputBuffer(OutputBuffer buf) {
    this.buf = buf;
  }

  public void write(int b) throws IOException {
    buf.writeInt(b);
  }

  public void write(byte[] b) throws IOException {
    buf.write(b, 0, b.length);
  }

  public void write(byte[] b, int off, int len) throws IOException {
    buf.write(b, off, len);
  }

  public void writeBoolean(boolean v) throws IOException {
    buf.writeBoolean(v);
  }

  public void writeByte(int v) throws IOException {
    buf.writeByte((byte) v);
  }

  public void writeBytes(String s) {
    throw new UnsupportedOperationException();
  }

  public void writeChar(int v) throws IOException {
    buf.writeChar((char) v);
  }

  public void writeChars(String s) {
    throw new UnsupportedOperationException();
  }

  public void writeDouble(double v) throws IOException {
    buf.writeDouble(v);
  }

  public void writeFloat(float v) throws IOException {
    buf.writeFloat(v);
  }

  public void writeInt(int v) throws IOException {
    buf.writeInt(v);
  }

  public void writeLong(long v) throws IOException {
    buf.writeLong(v);
  }

  public void writeShort(int v) throws IOException {
    buf.writeShort((short) v);
  }

  public void writeUTF(String s) throws IOException {
    buf.writeUTF(s);
  }

  public int bytesRemaining() {
    return buf.bytesRemaining();
  }

  public void writeByte(byte v) throws IOException {
    buf.writeByte(v);
  }

  public void writeChar(char v) throws IOException {
    buf.writeChar(v);
  }

  public void writeShort(short v) throws IOException {
    buf.writeShort(v);
  }

}
