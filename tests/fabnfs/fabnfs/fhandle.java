package fabnfs;

//
// The fhandle is used by NFS to represent files.  It is a 32 byte
//   piece of data that the NFS client gets from mountd or the NFS
//   server.
//
// This implementation stores 3 things in the fhandle: the handle of
//   the root of this file system (the handle of the mount point),
//   the handle of the file, and a flag indicating whether this handle
//   is read only.  The rest of the 32 bytes are not used, but since
//   NFS clients think two files are the same iff the fhandles are the
//   same, make sure this always gives out the same fhandle by
//   setting the rest of the data to 0.
//
class fhandle extends java.lang.Object  {
    long root;     // handle of the root of this mount point
    long handle;   // handle of the file
    long readonly; // is the mount read only?

    // Initialize this fhandle from the packet, leave the position in the
    //   packet just past the fhandle.
    fhandle(XDRPacket source) {
	Read(source);
    };
    fhandle() {
	// nothing
    };

    // accessors
    long Root() { return root; }
    long Handle() { return handle; }
    long ReadOnly() { return readonly; }

    boolean Read(XDRPacket source) {
	// the first long in the packet is the handle of the root
	root = source.GetLong();
	// the next long is the handle of the file
	handle = source.GetLong();
	// the next is a read only flag
	readonly = source.GetLong();
	// the rest is nothing.  There are 32 bytes in a fhandle and
	//   this has read in 3 words, or 12 bytes, so advance past
	//   the rest
	source.Advance(32 - 3 * 4);

	return true;
    };

    boolean Set(long toroot, long tohandle, long toreadonly) {
	root = toroot;
	handle = tohandle;
	readonly = toreadonly;

	return true;
    };

    // Put this handle into a packet as an XDR fhandle that the client
    //   side can read.
    boolean Emit(XDRPacket to) {
	to.AddLong(root);
	to.AddLong(handle);
	to.AddLong(readonly);
	// the rest of the words of the handle should be 0.  Since there
	//   are 32 bytes in a handle, there are 8 words and the above
	//   consumed 3 of them, so there are 5 left.
	for (int i = 0; i < 5; i++)
	    to.AddLong(0);

	return true;
    };

    void Print() {
	System.out.print("File handle root=" + root +
			 " handle=" + handle +
			 " readonly=" + readonly + "\n");
    }
};

