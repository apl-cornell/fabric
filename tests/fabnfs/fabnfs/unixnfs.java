package fabnfs;

import fabric.worker.Core;
import fabnfs.util.FileSystemFactory;

//
// Copyright 1997, 1998 Steven James Procter
// All rights under copyright reserved.
//
// Everyone is granted permission to copy, modify and redistribute this code.
// Modification or redistribution for profit is expressely disallowed.
// The copyright notice and this notice must be preserved on all copies.
//
// This software is distributed as-is.  No warranty of merchantability or
// fitness for any purpose is stated or implied.
//
// It is my intention that anyone be able to use this software but not
// incorporate it or any part of it into any product or any software which
// is sold without first obtaining a licensing agreement from me the author.
//
// 							Steven James Procter
// 							steven@void.org
// 							March 1997
//


class unixnfs extends nfs implements NFSConsts, MountdConsts, RPCConsts {
//    public static void main(String args[]) {
//	PathMapper pm = new UnixPathMapper();
//	TimeMapper tm = new Java3TimeMapper(); // new NTTimeMapper();
//	FileSystemInfo fsi = new FileSystemInfo();
//	new ntnfs(pm, tm, fsi, args);
//    };

  public static void startServer(Core localCore, Core core, FileSystemFactory factory, String[] args) {
    PathMapper pm = new UnixPathMapper();
    TimeMapper tm = new Java3TimeMapper(); // new NTTimeMapper();
    FileSystemInfo fsi = new FileSystemInfo(core, localCore, factory, "/");
    new unixnfs(pm, tm, fsi, args);
}


    unixnfs(PathMapper pm, TimeMapper tm, FileSystemInfo fsi, String args[]) {
	super(pm, tm, fsi, args);
	Run();
    }
};

