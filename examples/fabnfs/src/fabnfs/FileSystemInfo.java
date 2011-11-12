package fabnfs;

import fabric.worker.Store;

import fabnfs.util.FileSystemFactory;

// this class is used to encapsulate all of the file system information.  The
//   idea is that it be replaced by native code to get better NFS behavior.
class FileSystemInfo extends java.lang.Object  {

    Store store;
    Store localStore;
    FileSystemFactory factory;
    public String separatorChar;

    FileSystemInfo(Store store, Store localStore, FileSystemFactory factory, String sepChar) {
      this.store = store;
      this.localStore = localStore;
      this.factory = factory;
      this.separatorChar = sepChar;
    }

    void SetFS(String path) { /* nothing */ };

    long TransferSize() { return 8192; }
    long BlockSize()    { return 512; }
    long TotalBlocks()  { return 0; }
    long FreeBlocks()   { return 0; }
    long AvailableBlocks() { return 0; }
}
