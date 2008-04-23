package fabnfs;

class MountdHandler extends rpcHandler 
	implements RPCConsts, MountdConsts, NFSConsts {
    Handle fileHandles = null;
    PathMapper mapper = null;
    Exports exports = null;
    
    MountdHandler(Handle handles, Exports exp, PathMapper pm) {
	// tell parent class about me
	super(MD_PROG, MD_VERS);
	
	fileHandles = handles;
	exports = exp;
	mapper = pm;
    };
    
    public void Run(UDPPacketPort port, long xid,
		    long procedure, XDRPacket packet) {
	System.out.print("Mountd run method called\n");

	switch((int) procedure) {
	case (int) MD_NULL:
	    System.out.print("MD_NULL called\n");
	    MDNull(port, xid, procedure, packet);
	    break;
	case (int) MD_MNT:
	    System.out.print("MD_MNT called\n");
	    MDMount(port, xid, procedure, packet);
	    break;
	case (int) MD_UMNT:
	    System.out.print("MD_UMNT called\n");
	    MDUnMount(port, xid, procedure, packet);
	    break;
	    
	default:
	    System.err.print("Unsupported mountd procedure called\n");
	    break;
	}
    };

    void MDNull(UDPPacketPort port, long xid, long procedure,
		XDRPacket packet) {
	// Put together an XDR reply packet
	XDRPacket result = new XDRPacket(128);
	result.AddReplyHeader(xid);

	// send the reply back
	System.out.print("Sending reply back to address " +
			 packet.Source().getHostAddress() + " port " +
			 packet.Port() + "\n");
	port.SendPacket(packet.Source(), packet.Port(), result);
    };
    
    void MDMount(UDPPacketPort port, long xid, long procedure,
		 XDRPacket packet) {
	// Keep track of whether any errors have been encountered
	long err = NFS_OK;
	
	// skip the authentication records
	packet.ReadAuthentication();
	packet.ReadAuthentication();
	
	// next should be a dirpath, which is a string.  Replace unix 
	//   style path with local style path
	String path = packet.GetString();
        System.out.println("Asking for path " + path);
	if (path == null) {
	    err = NFSERR_STALE; // what should go here?
            System.out.println("Uh Oh!");
	}
	else {
	    String old = path;
	    path = mapper.Convert(old);
	    path = mapper.Canonicalize(path);
	    System.out.print("Mount request for " + path 
			     + "was " + old + "\n");
	}
	
	XDRPacket result = new XDRPacket(128);
	result.AddReplyHeader(xid);

	//
	// Try to validate this mount, if there is an error make an error
	//   packet, otherwise send back the handle.
	//
	if (err != NFS_OK) {
	    result.AddLong(err);
	}
	else if (exports.Matches(packet.Source(), path) == false) {
	    // No permission for this mount in the exports file
	    result.AddLong(NFSERR_PERM);
	    System.err.print("!!! Mount request for " + path + 
			     "from " + packet.Source() + " denied.\n");
	}
	else {
	    // put together a file handle
	    long handle = fileHandles.Allocate(path);
	    fhandle fh = new fhandle();
	    fh.Set(handle, handle, 0);

	    System.out.print(">>> emiting fhandle root " + fh.Root()
			     + " file " + fh.Handle() + "\n");

	    // Put together the reply packet.
	    result.AddLong(NFS_OK);
	    fh.Emit(result);
	}
	
	// Send the result
	port.SendPacket(packet.Source(), packet.Port(), result);
    };
    
    void MDUnMount(UDPPacketPort port, long xid, long procedure,
		   XDRPacket packet) {
	// skip the authentication records
	packet.ReadAuthentication();
	packet.ReadAuthentication();
	
	// next should be a dirpath, which is a string
	String path = mapper.Convert(packet.GetString());
	System.out.print("Mount request for " + path + "\n");
	
	// put together a file handle
	long handle = fileHandles.Allocate(path);
	
	XDRPacket result = new XDRPacket(128);
	result.AddReplyHeader(xid);

	result.AddLong(NFS_OK);

	// Send the result
	System.out.print("Sending reply handle " + handle 
			 + " address " + packet.Source().getHostAddress()
			 + " port " + packet.Port() + "\n");
	port.SendPacket(packet.Source(), packet.Port(), result);
    };
};
