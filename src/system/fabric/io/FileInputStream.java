package fabric.io;

import fabric.client.Core;
import java.io.IOException;

public interface FileInputStream {
  
  public FileInputStream makeNew(Core localCore, Core core, String name) throws IOException;
  public long skip(long n);

  public int read(FileByteArray barray);

  public int read();

  public void close();

}