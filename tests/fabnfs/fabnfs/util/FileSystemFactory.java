package fabnfs.util;

import java.io.IOException;

import fabric.client.Core;

public interface FileSystemFactory {
  
  public File makeFile(Core core, Core remoteCore, String name);
  
  public RandomAccessFile makeRAFile(Core localCore, Core core, String path) throws IOException;
  
  public FileInputStream makeFIStream(Core localCore, Core core, String name) throws IOException;

}
