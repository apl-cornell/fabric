package fabnfs;

import java.net.DatagramPacket;
import java.util.Hashtable;

class rpcManager extends java.lang.Object  implements RPCConsts {
  UDPPacketPort socket;
  Hashtable handlers;

  // control debugging output
  boolean debug = false;

  rpcManager(int portNumber) {
    socket = new UDPPacketPort(portNumber);
    handlers = new Hashtable();
  }
  rpcManager() {
    socket = new UDPPacketPort(-1);
    handlers = new Hashtable();
  }

  // This procedure gets the next RPC packet from the socket connection
  //   and initiates handling that packet.
  public void MainLoop() {
    // Create some threads to collect packets from the net
    MTList packets = new MTList();
    for (int i = 0; i < 4; i++) 
    {
      PacketCollector pc = new PacketCollector(socket, packets);
      new Thread(pc).start();
    }

    for (;;) {
      PacketCollector pc = (PacketCollector) packets.Get();
      if (pc != null) {
        DatagramPacket packet = pc.Packet();
//        System.out.println("Server: Received UDP Packet - " + packet.getAddress() + ":" + packet.getPort() + "(" + packet.getSocketAddress() + ")" + arrayToString(packet.getData(), packet.getOffset(), packet.getLength()));
        Dispatch(new XDRPacket(packet));
        pc.Done();
      }
    }
  };

  String arrayToString(byte[] array, int offset, int length) {
    StringBuffer bf = new StringBuffer();
    bf.append('[');
    for(int i = offset; i < offset + length; i++) {
      bf.append(Integer.toString((int)array[i]));
      if(i<offset + length) bf.append(',');
    }
    bf.append(']');
    return bf.toString();
  }

  // Find out if the packet is a call or a reply packet and dispatch the
  //   appropriate handler.
  void Dispatch(XDRPacket xdr) {
    long xid = xdr.GetLong();
    long type = xdr.GetLong();

    if (type == RPCCall)
      RPCCall(xdr, xid);
    else if (type == RPCReply)
      RPCReply(xdr, xid);
    else
      System.out.print("Invalid RPC packet type"
          + " (neither call nor reply)");
  };

  // Handle the call packet by looking up the registered handler for
  //   the (program, version) requested in this packet and calling it.
  void RPCCall(XDRPacket xdr, long xid) {
    long rpcvers = xdr.GetLong();
    long prog = xdr.GetLong();
    long vers = xdr.GetLong();
    long proc = xdr.GetLong();

    if (debug)
      System.out.print("call rpcvers = " + rpcvers
          + " prog = " + prog
          + " vers = " + vers
          + " proc = " + proc
          + "\n");

    // dispatch the procedure call and return the reply
    RunHandler(xdr, xid, prog, vers, proc);
  };

  // This rpcmanager doesn't send requests or handle replys.
  void RPCReply(XDRPacket xdr, long xid) {
    System.out.print("RPC reply - I don't handle these\n");
  };

  // handler used for last call, probably > 99% of calls will be for the
  //   NFS handler so this should almost always be the correct one
  long lastprog = -1, lastvers = -1;
  rpcHandler lastHandler = null;
  public void RunHandler(XDRPacket packet, long xid,
      long prog, long vers, long proc) {
    long time0 = System.currentTimeMillis();
    if (prog == lastprog && vers == lastvers) {
      // run handler from cache
      lastHandler.Run(socket, xid, proc, packet);
    }
    else {
      // don't have the correct handler cached, so find it and cache it
      rpcHandler handler = FindHandler(packet, xid, prog, vers, proc);
      if (handler != null) {
        // update cache
        lastHandler = handler;
        lastprog = prog;
        lastvers = vers;
        // run handler
        lastHandler.Run(socket, xid, proc, packet);
      }
    }
    long time1 = System.currentTimeMillis() - time0;
//    System.out.println("Handler Program " + prog + " for " + xid + " took " + time1 + "ms");
  }

  // Find the handler for this request and return it if it exists, or
  //   send an appropriate error message back if not found.
  public rpcHandler FindHandler(XDRPacket packet, long xid,
      long prog, long vers, long proc) {
    // get the head of the handler chain for this program number
    Long l = new Long(prog);
    System.out.print("Looking for prog " + prog + " vers "
        + vers + " proc " + proc + "\n");

    rpcHandler chain = (rpcHandler) handlers.get(l);

    if (chain == null) {
      // this program is not registered
      System.err.print("No handlers for program " + prog + "\n");
      ReportUnavail(packet, xid, proc, vers);
      return null;
    }

    // look for the handler for the requested version in the chain of
    //   handlers for this program number
    rpcHandler handler = chain; 
    long minvers = handler.Version();
    long maxvers = handler.Version();
    while (handler != null) {
      if (handler.Version() < minvers)
        minvers = handler.Version();
      if (handler.Version() > maxvers)
        maxvers = handler.Version();

      // see if this is the one we want
      if (handler.Version() == vers)
        break;
      handler = handler.Next();
    }

    if (handler != null)
      return handler;
    else {
      System.out.print("this version (" + vers + ") not handled:"
          + " min=" + minvers + " max=" + maxvers + "\n");
      ReportMismatch(packet, xid, proc, vers, minvers, maxvers);
      return null;
    }
  };

  // Register a handler for this version of this program.  The procedure
  //   is passed to the handler along with the packet, and it is expected
  //   to select the correct method based on the procedure number.
  public int RegisterHandler(rpcHandler handler) {
    // get the chain of handlers for this program
    Long l = new Long(handler.Program());
    rpcHandler chain = (rpcHandler) handlers.get(l);

    // If there is no handler then this is the head of a new chain
    if (chain == null) { 
      handlers.put(l, handler);
      return 0;
    }

    // Search the chain for the end or another handler for this 
    //   program, version pair.  If end found, append this handler
    //   to the chain.
    for (;;) {
      if (chain.Version() == handler.Version()) {
        System.err.print("!!! handler already registered for"
            + " program " + handler.Program()
            + " version " + handler.Version() + "\n");
        return -1;
      }
      else if (chain.Next() == null) {
        chain.SetNext(handler);
        return 0;
      }
      else
        chain = chain.Next();
    }
  };

  public void ReportMismatch(XDRPacket packet, long xid, long proc, 
      long vers, long minvers, long maxvers) {
    XDRPacket reply = new XDRPacket(128);
    reply.AddLong(xid);
    reply.AddLong(RPCReply);
    reply.AddLong(RPCMsgAccepted);
    reply.AddNullAuthentication();
    reply.AddLong(RPCProgMismatch);
    reply.AddLong(minvers);
    reply.AddLong(maxvers);

    socket.SendPacket(packet.Source(), packet.Port(), reply);
  };

  public void ReportUnavail(XDRPacket packet, long xid, 
      long proc, long vers) {
    XDRPacket reply = new XDRPacket(128);
    reply.AddLong(xid);
    reply.AddLong(RPCReply);
    reply.AddLong(RPCMsgAccepted);
    reply.AddNullAuthentication();
    reply.AddLong(RPCProgUnavail);

    socket.SendPacket(packet.Source(), packet.Port(), reply);
  };

  public int Port() {
    return socket.Port();
  };
};
