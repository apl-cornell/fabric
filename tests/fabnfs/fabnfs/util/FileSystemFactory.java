package fabnfs.util;

import java.io.IOException;

import fabric.worker.Store;

public interface FileSystemFactory {

  public File makeFile(Store store, Store remoteStore, String name);

  public RandomAccessFile makeRAFile(Store localStore, Store store, String path) throws IOException;

  public FileInputStream makeFIStream(Store localStore, Store store, String name) throws IOException;

}
