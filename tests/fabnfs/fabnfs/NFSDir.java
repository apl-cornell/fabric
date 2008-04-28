package fabnfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import fabric.io.RandomAccessFile;
import fabric.io.File;


class NFSDir extends java.lang.Object  implements NFSConsts {
  Handle handles;
  PathMapper pm;
  TimeMapper tm;
  FileSystemInfo fsinfo;

  NFSDir(Handle h, PathMapper p, TimeMapper t, FileSystemInfo f) {
    handles = h;
    pm = p;
    tm = t;
    fsinfo = f;
  }

  XDRPacket GetAttr(long xid, XDRPacket packet) throws NFSException {
    try {
      // get info out of the packet
      fhandle f = new fhandle(packet);

      // carry out the operation
      fattr fa = new fattr(fsinfo, handles, tm);
      String file = GetNameFromHandle(f.Handle(), xid);
      // make sure the file exists
      File fd = fsinfo.factory.makeFile(fsinfo.localCore, fsinfo.core, file);
      if (fd.exists() == false)
        throw new NFSException(xid, NFSERR_NOENT);
      // System.out.print("getattr on " + file + "\n");
      fa.Load(file);

      // make the reply packet
      XDRPacket reply = new XDRPacket(128);
      reply.AddReplyHeader(xid);
      reply.AddLong(NFS_OK);
      fa.Emit(reply);
      return reply;

    } catch (FileNotFoundException e) {
      throw new NFSException(xid, NFSERR_NOENT);
    }
  }

  XDRPacket SetAttr(long xid, XDRPacket packet) throws NFSException {
    try {
      // get info out of the packet
      fhandle f = new fhandle(packet);
      String fileName = GetNameFromHandle(f.Handle(), xid);

      // the attributes
      int mode = (int) packet.GetLong();
      int uid = (int) packet.GetLong();
      int gid = (int) packet.GetLong();
      int size = (int) packet.GetLong();
      timeval atime = new timeval(packet);
      timeval mtime = new timeval(packet);

      // do the work - the only attribute that can be set is the size can
      //   be set to 0 to truncate the file 
      if (size == 0) {
        // truncate by deleting and recreating the file
        File fd = fsinfo.factory.makeFile(fsinfo.localCore, fsinfo.core, fileName);
        fd.reset();
//      RandomAccessFile ra = new RandomAccessFile(fileName, "rw");
//      ra.close();
      }

      // make the reply
      XDRPacket reply = new XDRPacket(128);
      reply.AddReplyHeader(xid);
      reply.AddLong(NFS_OK);
      fattr fa = new fattr(fsinfo, handles, tm);
      fa.Load(fileName);
      fa.Emit(reply);
      return reply;

    } catch (FileNotFoundException e) {
      throw new NFSException(xid, NFSERR_NOENT);
    } catch (IOException e) {
      throw new NFSException(xid, NFSERR_PERM);
    }
  }

  XDRPacket Lookup(long xid, XDRPacket packet) throws NFSException {
    try {
      fhandle dir = new fhandle(packet);
      String entry = packet.GetString();

      // figure out the file name being requested and make a handle for it
      //   if it exists.
      String dirName = GetNameFromHandle(dir.Handle(), xid);
      String fileName = pm.MakePath(dirName, entry);

      File fd = fsinfo.factory.makeFile(fsinfo.localCore, fsinfo.core, fileName); // open it to make sure it exists
      if (fd.exists() != true)
        throw new NFSException(xid, NFSERR_NOENT);
      fattr childFA = new fattr(fsinfo, handles, tm);
      childFA.Load(fileName);

      // make a fhandle for this new path
      long childHandle = handles.Allocate(fileName);
      fhandle childFH = new fhandle();
      childFH.Set(dir.Root(), childHandle, dir.ReadOnly());

      // make the reply
      XDRPacket reply = new XDRPacket(128);
      reply.AddReplyHeader(xid);
      reply.AddLong(NFS_OK);
      childFH.Emit(reply);
      childFA.Emit(reply);
      return reply;

    } catch(FileNotFoundException e) {
      throw new NFSException(xid, NFSERR_NOENT);
    }
  }

  // keep these between calls so subsequent calls getting the rest of the 
  //   contents of a directory are fast.
  String cachedDirName;
  String [] cachedFiles;

  XDRPacket Readdir(long xid, XDRPacket packet) throws NFSException {
    fhandle fh = new fhandle(packet);
    long cookie = packet.GetLong();
    long count = packet.GetLong();

    // if this is a new call to readdir (cookie=0) or it is a new
    //   directory to read, replace the cache.
    String dirName = GetNameFromHandle(fh.Handle(), xid);
    System.out.print("Reading dir " + dirName + " cookie=" + cookie
        + " count=" + count + "\n");
    if (cookie == 0 || (dirName.equals(cachedDirName) == false)) {
      File dirfd = fsinfo.factory.makeFile(fsinfo.localCore, fsinfo.core, dirName);
      if (dirfd == null)
        throw new NFSException(xid, NFSERR_NOENT);

      String [] dirfiles = dirfd.list().contents;
      if (dirfiles == null)
        throw new NFSException(xid, NFSERR_NOENT);
      System.out.println("dir has " + dirfiles.length + " entries");
//    if (dirfiles.length <= 0)
      if (dirfiles.length < 0)
        throw new NFSException(xid, NFSERR_NOENT);

      // sort the files by name
      Sort s = new Sort();
      // turns out my bubblesort implementation is faster than my 
      //   qsort for the numbers of files that listdir gets
      s.BubbleSort(dirfiles);

      // make a new list that contains the old list plus . and ..
      String [] files = new String[dirfiles.length + 2];
      files[0] = new String(".");
      files[1] = new String("..");
      for (int i = 0; i < dirfiles.length; i++) 
        files[i + 2] = dirfiles[i];

      cachedFiles = files;
      cachedDirName = dirName;
    }

    // prepare the reply packet.
    XDRPacket reply = new XDRPacket((int) count);
    reply.AddReplyHeader(xid);
    reply.AddLong(NFS_OK);

    // Add files to the list until there are no more files or all of
    //   the count bytes have been used.
    int current = reply.Length();
    boolean more = false; // are there more files to get
    // if there are any files to add
    if (cachedFiles != null && cachedFiles.length > 0) {
      for (int i = (int) cookie; i < cachedFiles.length; i++) {
        // see if there is enough room for another file - 3 longs of id,
        //   the name (rounded up to 4 bytes) and a trailing long 
        //   indicating whether there are more files to get
        int needed = 3 * 4 + (cachedFiles[i].length() + 3) + 8;
        if (needed + current >= count) {
          more = true;
          break;
        }
        // get the handle for this file
        String fileName = pm.MakePath(cachedDirName, cachedFiles[i]);
        long handle = handles.Allocate(fileName);

        // add an entry to the packet for this file
        reply.AddLong(NFS_TRUE);
        reply.AddLong(handle);
        reply.AddString(cachedFiles[i]);
        reply.AddLong(i + 1); // this is the cookie
        current = reply.Length();
      }
    }
    reply.AddLong(NFS_FALSE); // no more entries in this packet

    // tell the client if this packet has returned the last of the files
    if (more) 
      reply.AddLong(NFS_FALSE);
    else
      reply.AddLong(NFS_TRUE);

    return reply;
  }

  XDRPacket Create(long xid, XDRPacket packet) throws NFSException {
    try {
      fhandle dirFH = new fhandle(packet);
      String entry = packet.GetString();
      String dirName = GetNameFromHandle(dirFH.Handle(), xid);
      String path = pm.MakePath(dirName, entry);

      // make the file
      File fd = fsinfo.factory.makeFile(fsinfo.localCore, fsinfo.core, path);
      if (fd.exists()) 
        throw new NFSException(xid, NFSERR_EXIST);

      // TODO attributes not supported yet
//    RandomAccessFile ra = new RandomAccessFile(path, "rw");
      RandomAccessFile ra = fsinfo.factory.makeRAFile(fsinfo.localCore, fsinfo.core, path);
      ra.close();

      // make a new handle for this file
      fhandle fh = new fhandle();
      long handle = handles.Allocate(path);
      fh.Set(dirFH.Root(), handle, dirFH.ReadOnly());

      // get the attributes of this new file
      fattr fa = new fattr(fsinfo, handles, tm);
      fa.Load(path);

      // create the reply packet
      XDRPacket reply = new XDRPacket(128);
      reply.AddReplyHeader(xid);
      reply.AddLong(NFS_OK);
      fh.Emit(reply);
      fa.Emit(reply);
      return reply;

    } catch (FileNotFoundException e) {
      throw new NFSException(xid, NFSERR_IO);
    } catch (IOException e) {
      throw new NFSException(xid, NFSERR_IO);
    } catch (SecurityException e) {
      throw new NFSException(xid, NFSERR_PERM);
    }
  }

  XDRPacket Remove(long xid, XDRPacket packet) throws NFSException {
    fhandle dirFH = new fhandle(packet);
    String entry = packet.GetString();

    // open and delete the file
    String dirName = GetNameFromHandle(dirFH.Handle(), xid);
    File fd = fsinfo.factory.makeFile(fsinfo.localCore, fsinfo.core, dirName + fsinfo.separatorChar + entry);
    if (fd.exists() == false) 
      throw new NFSException(xid, NFSERR_NOENT);
    if (fd.delete() == false) 
      throw new NFSException(xid, NFSERR_IO);

    // create the reply packet
    XDRPacket reply = new XDRPacket(128);
    reply.AddReplyHeader(xid);
    reply.AddLong(NFS_OK);
    return reply;
  }

  XDRPacket Mkdir(long xid, XDRPacket packet) throws NFSException {
    try {
      fhandle dirFH = new fhandle(packet);
      String entry = packet.GetString();

      String dirName = GetNameFromHandle(dirFH.Handle(), xid);
      String newdir = pm.MakePath(dirName, entry);
      File fd = fsinfo.factory.makeFile(fsinfo.localCore, fsinfo.core, newdir);

      if (fd.exists() == true) 
        throw new NFSException(xid, NFSERR_EXIST);
      fd.mkdir();

      // make a fhandle for this directory
      long handle = handles.Allocate(newdir);
      fhandle newFH = new fhandle();
      newFH.Set(dirFH.Root(), handle, dirFH.ReadOnly());

      // get the attributes
      fattr fa = new fattr(fsinfo, handles, tm);
      fa.Load(newdir);

      XDRPacket reply = new XDRPacket(128);
      reply.AddReplyHeader(xid);
      reply.AddLong(NFS_OK);
      newFH.Emit(reply);
      fa.Emit(reply);
      return reply;

    } catch (FileNotFoundException e) {
      throw new NFSException(xid, NFSERR_IO);
    }
  }

  XDRPacket Rmdir(long xid, XDRPacket packet) throws NFSException {
    fhandle dirFH = new fhandle(packet);
    String name = packet.GetString();

    String dirname = GetNameFromHandle(dirFH.Handle(), xid);
    File fd = fsinfo.factory.makeFile(fsinfo.localCore, fsinfo.core, dirname + fsinfo.separatorChar + name);
    // do some correctness checking
    if (fd.exists() == false)
      throw new NFSException(xid, NFSERR_NOENT);
    if (fd.isDirectory() == false) 
      throw new NFSException(xid, NFSERR_NOTDIR);
    // try to remove the directory
    if (fd.delete() == false)
      throw new NFSException(xid, NFSERR_IO);

    XDRPacket reply = new XDRPacket(128);
    reply.AddReplyHeader(xid);
    reply.AddLong(NFS_OK);
    return reply;
  }

  XDRPacket StatFS(long xid, XDRPacket packet) throws NFSException {
    fhandle fh = new fhandle(packet);
    // tell the fsinfo the path to get information about
    fsinfo.SetFS(GetNameFromHandle(fh.Handle(), xid));

    XDRPacket reply = new XDRPacket(128);
    reply.AddReplyHeader(xid);
    reply.AddLong(NFS_OK);
    reply.AddLong(fsinfo.TransferSize());
    reply.AddLong(fsinfo.BlockSize());
    reply.AddLong(fsinfo.TotalBlocks());
    reply.AddLong(fsinfo.FreeBlocks());
    reply.AddLong(fsinfo.AvailableBlocks());
    return reply;
  }

  /*
   * TODO: Not supported yet
   */

//XDRPacket Rename(long xid, XDRPacket packet) throws NFSException {
//// collect arguments from RPC packet
//fhandle srcFH = new fhandle(packet);
//String srcentry = packet.GetString();
//fhandle destFH = new fhandle(packet);
//String destentry = packet.GetString();

//// compute the path names specified
//String srcdir = GetNameFromHandle(srcFH.Handle(), xid);
//String destdir = GetNameFromHandle(destFH.Handle(), xid);

//File src = new File(srcdir, srcentry);
//if (src.exists() == false)
//throw new NFSException(xid, NFSERR_NOENT);
//File dest = new File(destdir, destentry);
//if (dest.exists()) 
//throw new NFSException(xid, NFSERR_EXIST);
//// do the rename operation
//if (src.renameTo(dest) == false)
//throw new NFSException(xid, NFSERR_IO);

//XDRPacket reply = new XDRPacket(128);
//reply.AddReplyHeader(xid);
//reply.AddLong(NFS_OK);
//return reply;
//}

  // local procedure to get the associated with a handle, throws an exception
  //   if there is a problem.
  private String GetNameFromHandle(long handle, long xid) throws NFSException{
    String result = handles.Lookup(handle);
    if (result == null)
      throw new NFSException(xid, NFSERR_STALE);
    return result;
  }
};
