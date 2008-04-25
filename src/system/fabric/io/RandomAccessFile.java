package fabric.io;

import fabric.client.Core;
import java.io.IOException;

public interface RandomAccessFile {

  public void seek(long offset);

  public void write(FileByteArray b, int off, int len);

  public void close();
}