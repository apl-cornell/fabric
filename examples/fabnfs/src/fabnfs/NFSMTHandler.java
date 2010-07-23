package fabnfs;

class NFSMTHandler extends rpcHandler implements RPCConsts, NFSConsts
{
  MTList packetq;
  NFSHandler handler;

  NFSMTHandler(Handle handles, PathMapper pm, FileSystemInfo fsi,
      TimeMapper tm) {
    // tell parent about me
    super(NFS_PROG, NFS_VERS);

    handler = new NFSHandler(handles, pm, fsi, tm);
    packetq = new MTList();
    for (int i = 0; i < 30; i++) {
      NFSOperation op = new NFSOperation(packetq, handler);
      new Thread(op).start();
    }
  }

  public void Run(UDPPacketPort port, long xid,
      long procedure, XDRPacket packet) {
    NFSItem item = new NFSItem(port, xid, procedure, packet);
    // System.out.println("NFSMTHandler adding item to queue xid=" + xid);
    packetq.Add(item);
  }
}
