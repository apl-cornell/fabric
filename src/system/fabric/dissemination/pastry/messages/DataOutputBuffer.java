/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
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

  @Override
  public void write(int b) throws IOException {
    buf.writeInt(b);
  }

  @Override
  public void write(byte[] b) throws IOException {
    buf.write(b, 0, b.length);
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    buf.write(b, off, len);
  }

  @Override
  public void writeBoolean(boolean v) throws IOException {
    buf.writeBoolean(v);
  }

  @Override
  public void writeByte(int v) throws IOException {
    buf.writeByte((byte) v);
  }

  @Override
  public void writeBytes(String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void writeChar(int v) throws IOException {
    buf.writeChar((char) v);
  }

  @Override
  public void writeChars(String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void writeDouble(double v) throws IOException {
    buf.writeDouble(v);
  }

  @Override
  public void writeFloat(float v) throws IOException {
    buf.writeFloat(v);
  }

  @Override
  public void writeInt(int v) throws IOException {
    buf.writeInt(v);
  }

  @Override
  public void writeLong(long v) throws IOException {
    buf.writeLong(v);
  }

  @Override
  public void writeShort(int v) throws IOException {
    buf.writeShort((short) v);
  }

  @Override
  public void writeUTF(String s) throws IOException {
    buf.writeUTF(s);
  }

  @Override
  public int bytesRemaining() {
    return buf.bytesRemaining();
  }

  @Override
  public void writeByte(byte v) throws IOException {
    buf.writeByte(v);
  }

  @Override
  public void writeChar(char v) throws IOException {
    buf.writeChar(v);
  }

  @Override
  public void writeShort(short v) throws IOException {
    buf.writeShort(v);
  }

}
