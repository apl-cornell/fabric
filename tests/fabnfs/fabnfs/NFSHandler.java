package fabnfs;

import java.util.Date;

class NFSHandler extends rpcHandler implements RPCConsts, NFSConsts
{
    // state variables passed from parent, to deal with platform local issues
    Handle fileHandles;
    PathMapper pathmapper;
    FileSystemInfo fsinfo;
    TimeMapper timemapper;

    // Queue of received packets, used to avoid redoing retransmissions
    // XXX Remove this module from the system - I don't think it is useful
    // XIDCache packetQueue;

    // cache of packets that were allocated so we don't have to allocate
    //   memory buffers a lot, that is slow in java
    XDRPacketCache packetCache;

    // classes that carry out various nfs operations
    NFSDir dirService;
    NFSIO  ioService;

    NFSHandler(Handle handles, PathMapper pm, FileSystemInfo fsi, TimeMapper tm) {
	// tell parent about me
	super(NFS_PROG, NFS_VERS);

	// assign state variables
	fileHandles = handles; // file handle to file name mapping class
	pathmapper = pm; // maps network paths to local paths
	fsinfo = fsi; // gets info from file system, eg. free blocks, used blocks
	timemapper = tm;  // converts java returned file mod times to UNIX times

	// directory helper, does readdir
	dirService = new NFSDir(fileHandles, pathmapper, timemapper, fsinfo);

	// io helper, does file read and write
	ioService = new NFSIO(fileHandles, timemapper, fsinfo);

        // XXX the xid cache has been removed
	// packetQueue = new XIDCache();
        packetCache = new XDRPacketCache();
    };

    // process a packet and send a reply packet.
    int iteration = 0;
    public void Run(UDPPacketPort port, long xid,
		    long procedure, XDRPacket packet) {
	iteration++;
	Date begin = new Date();

	XDRPacket result = null;

//        System.err.println("Calling NFS Procedure " + procedure);
	//
	// see if this packet has already been received
	//
        // XXX Turn off the XIDCache - is this thing really helpful?
        //
	//XIDCache.XIDCacheItem qi = packetQueue.Find(xid);
	//if (qi != null) {
	//    System.out.print("handling duplicate request " + xid + "\n");
	//    if (qi.inprogress == false)
	//        port.SendPacket(packet.Source(), packet.Port(), qi.packet);
	//    return;
	//}
	//packetQueue.Start(xid);

	// get rid of authentication recorde in packet, I don't use them
	packet.ReadAuthentication();
	packet.ReadAuthentication();
	// run the requested procedure
	try {
	    switch((int) procedure) {
	    case (int) NFS_NULL:
	      result = NFSNull(port, xid, procedure, packet);
	      break;
	    case (int) NFS_GETATTR:
	      result = dirService.GetAttr(xid, packet);
	      break;
	    case (int) NFS_SETATTR:
	      result = dirService.SetAttr(xid, packet);
	      break;
	    case (int) NFS_LOOKUP:
	      result = dirService.Lookup(xid, packet);
	      break;
	    case (int) NFS_READ:
	      result = ioService.Read(xid, packet, packetCache);
	      break;
	    case (int) NFS_WRITE:
	      result = ioService.Write(xid, packet, packetCache);
	      break;
	    case (int) NFS_CREATE:
	      result = dirService.Create(xid, packet);
	      break;
	    case (int) NFS_REMOVE:
	      result = dirService.Remove(xid, packet);
	      break;
	    case (int) NFS_RENAME:
	      result = dirService.Rename(xid, packet);
	      break;
	    case (int) NFS_MKDIR:
	      result = dirService.Mkdir(xid, packet);
	      break;
	    case (int) NFS_RMDIR:
	      result = dirService.Rmdir(xid, packet);
	      break;
	    case (int) NFS_READDIR:
	      result = dirService.Readdir(xid, packet);
	      break;
	    case (int) NFS_STATFS:
	      result = dirService.StatFS(xid, packet);
	      break;
	    default:
	      System.err.print("Unsupported NFS procedure called ("
                               + procedure + ") from "
                               + packet.Source().getHostAddress() + "\n");
	      throw new NFSException(xid, NFSERR_IO);
	    }
	} catch(NFSException e) {
	    // make a reply packet that includes the error
	    result = new XDRPacket(64);
	    result.AddReplyHeader(e.GetXID());
	    result.AddLong(e.GetError());
	}
	Date end = new Date();

//	if (iteration % 10 == 0) {
//	    System.out.print("call took " + (end.getTime() - begin.getTime())
//			       + "ms");
//	    System.out.println(" memory is at "
//			       + Runtime.getRuntime().freeMemory());
//	}

        // XXX disabled the xid queue
	// packetQueue.SetPacket(xid, result);
	port.SendPacket(packet.Source(), packet.Port(), result);

        // put this packet back into the cache
        packetCache.add(result);
    };

    // the trivial RPC, does nothing but send back an OK message.
    XDRPacket NFSNull(UDPPacketPort port, long xid, long procedure,
		   XDRPacket packet) {
	// Put together an XDR reply packet
	XDRPacket result = new XDRPacket(128);
	result.AddReplyHeader(xid);
	return result;
    };

    // report to the client that the requested proc is not supported
    XDRPacket NFSProtoNotSupported(UDPPacketPort port, long xid,
				   long procedure, XDRPacket packet) {
	// Put together an XDR reply packet
	XDRPacket result = new XDRPacket(128);
	result.AddReplyHeader(xid);
	result.AddLong(NFSERR_STALE);

	return result;
    };

};

