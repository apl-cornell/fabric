package fabric.io;

import fabric.client.Core;
import java.io.IOException;

public interface RandomAccessFile {

  public RandomAccessFile makeNew(Core localCore, Core core, String path) throws IOException;

  public void seek(int offset);

  public void write(FileByteArray b, int off, int len);

  public void close();
}