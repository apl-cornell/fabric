package fabric.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.lang.ref.SoftReference;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Principal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.security.auth.x500.X500Principal;

import fabric.common.FabricException;
import fabric.common.FetchException;
import fabric.common.InternalError;
import fabric.common.NoSuchCoreError;
import fabric.common.SerializedObject;
import fabric.common.Surrogate;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.Glob;
import fabric.lang.Object;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.AllocateMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.PrepareTransactionMessage;
import fabric.messages.ReadMessage;

/**
 * Encapsulates a Core. This class maintains the connection to the core and
 * manages all communication. Cores can only be obtained through the
 * <code>Client.getCore()</code> interface. For each remote core, there should
 * be at most one <code>RemoteCore</code> object representing that core.
 */
public class RemoteCore implements Core {
  private transient Socket conn;

  /**
   * The DNS host name of the host.
   */
  public final String name;

  /**
   * A queue of fresh object identifiers.
   */
  private transient Queue<Long> fresh_ids;

  /**
   * The object table: locally resident objects.
   */
  private transient LongKeyMap<SoftReference<Object.$Impl>> objects;

  /**
   * The connection to the actual Core.
   */
  private transient ObjectInputStream in;
  private transient ObjectOutputStream out;

  /**
   * Cache of serialized objects that the core has sent us.
   */
  private transient LongKeyMap<SoftReference<SerializedObject>> serialized;

  /**
   * Creates a core representing the core at the given host name.
   */
  RemoteCore(String name) {
    this.name = name;
    this.objects = new LongKeyHashMap<SoftReference<Object.$Impl>>();
    this.fresh_ids = new LinkedList<Long>();
    this.serialized = new LongKeyHashMap<SoftReference<SerializedObject>>();
  }

  public ObjectInputStream objectInputStream() {
    return in;
  }

  public ObjectOutputStream objectOutputStream() {
    return out;
  }

  /**
   * Establishes a connection with a core node at a given host. A helper for
   * <code>Message.send(Core)</code>.
   * 
   * @param client
   *                The Client instance.
   * @param core
   *                The core being connected to.
   * @param host
   *                The host to connect to.
   * @param corePrincipal
   *                The principal associated with the core we're connecting to.
   * @throws IOException
   *                 if there was an error.
   */
  public void connect(Client client, Core core, InetSocketAddress host,
      Principal corePrincipal) throws NoSuchCoreError, IOException {
    Socket socket = new Socket();
    socket.setTcpNoDelay(true);
    socket.setKeepAlive(true);

    // Connect to the core node and identify the core we're interested in.
    socket.connect(host, client.timeout);
    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
    dataOut.writeUTF(core.name());
    dataOut.flush();

    // Determine whether the core exists at the node.
    if (socket.getInputStream().read() == 0) throw new NoSuchCoreError();

    if (client.useSSL) {
      // Start encrypting.
      SSLSocket sslSocket =
          (SSLSocket) client.sslSocketFactory.createSocket(socket, core.name(),
              host.getPort(), true);
      sslSocket.setUseClientMode(true);
      sslSocket.startHandshake();

      // Make sure we're talking to the right node.
      X500Principal peer =
          (X500Principal) sslSocket.getSession().getPeerPrincipal();
      if (!peer.equals(corePrincipal)) {
        Logger.getLogger(this.getClass().getName()).info(
            "Rejecting connection to " + host + ": got principal " + peer
                + " when we expected " + corePrincipal);
        sslSocket.close();
        throw new IOException();
      }

      out =
          new ObjectOutputStream(new BufferedOutputStream(sslSocket
              .getOutputStream()));
      out.flush();
      in =
          new ObjectInputStream(new BufferedInputStream(sslSocket
              .getInputStream()));

      conn = sslSocket;
    } else {
      out =
          new ObjectOutputStream(new BufferedOutputStream(socket
              .getOutputStream()));
      out.writeObject(client.principal);
      out.flush();
      in =
          new ObjectInputStream(
              new BufferedInputStream(socket.getInputStream()));
      conn = socket;
    }
  }

  public boolean isConnected() {
    return conn != null && !conn.isClosed();
  }

  public long createOnum() throws UnreachableCoreException {
    reserve(1);
    return fresh_ids.poll();
  }

  /**
   * Sends a PREPARE message to the core.
   * 
   * @return a core-specific transaction ID iff the operation succeeded.
   */
  public int prepareTransaction(Collection<Object.$Impl> toCreate,
      LongKeyMap<Integer> reads, Collection<Object.$Impl> writes)
      throws UnreachableCoreException, TransactionPrepareFailedException {
    PrepareTransactionMessage.Response response =
        new PrepareTransactionMessage(toCreate, reads, writes).send(this);
    return response.transactionID;
  }

  /**
   * Returns the requested $Impl, fetching it from the Core if it is not
   * resident.
   * 
   * @param onum
   *                The identifier of the requested object
   * @return The requested object
   * @throws FabricException
   */
  public Object.$Impl readObject(long onum) throws FetchException {
    // Check object table
    Object.$Impl result = readObjectFromCache(onum);
    if (result != null) return result;
    return fetchObject(onum);
  }
  
  public Object.$Impl readObjectFromCache(long onum) {
    SoftReference<Object.$Impl> ref = objects.get(onum);
    if (ref == null) return null;
    return ref.get();
  }

  /**
   * Fetches the object from the serialized cache, or goes to the core if it is
   * not present. Places the result in the object cache.
   * 
   * @param onum
   *                The object number to fetch
   * @return The constructed $Impl
   * @throws FabricException
   */
  private Object.$Impl fetchObject(long onum) throws FetchException {
    Object.$Impl result = null;
    SoftReference<SerializedObject> serialRef = serialized.remove(onum);
    
    if (serialRef != null) {
      SerializedObject serial = serialRef.get();
      try {
        if (serial != null) result = serial.deserialize(this);
      } catch (ClassNotFoundException e) {
        // TODO handle this
        return null;
      }
    }

    if (result == null) {
      // no serial copy --- fetch from dissemination
      Glob g = Client.getClient().fetchManager().fetch(this, onum);
      
      try {
        result = g.obj().deserialize(this);
      } catch (ClassNotFoundException e) {
        throw new InternalError(e);
      }
      
      for (LongKeyMap.Entry<SerializedObject> entry : g.related().entrySet())
        serialized.put(entry.getKey(), new SoftReference<SerializedObject>(
            entry.getValue()));
    }

    while (result instanceof Surrogate) {
      // XXX Track surrogates for reuse?
      Surrogate surrogate = (Surrogate) result;
      result = surrogate.core.readObject(surrogate.onum);
    }
    objects.put(onum, new SoftReference<Object.$Impl>(result));
    return result;
  }

  /**
   * Fetches the object from the serialized cache, or goes to the core if it is
   * not present. Places the result in the object cache.
   * 
   * @param onum
   *                The object number to fetch
   * @return The constructed $Impl
   * @throws FabricException
   */
  public Glob readObjectFromCore(long onum) throws FetchException {
    ReadMessage.Response response = new ReadMessage(onum).send(this);
    Glob g = new Glob(response.serializedResult, response.related);
    return g;
  }
  
  /**
   * Looks up the actual Core object when this core is deserialized. While this
   * method is not explicitly called in the code, it is used by the Java
   * serialization framework when deserializing a Core object.
   * 
   * @return The canonical Core object corresponding to this Core's onum
   * @throws ObjectStreamException
   */
  public java.lang.Object readResolve() {
    return Client.getClient().getCore(name);
  }

  /**
   * Ensure that a given number of objects can be created without contacting the
   * core.
   * 
   * @param num
   *                The number of objects to allocate
   */
  protected void reserve(int num) throws UnreachableCoreException {
    while (fresh_ids.size() < num) {
      // log.info("Requesting new onums, coreid=" + coreID);
      if (num < 512) num = 512;
      AllocateMessage.Response response = new AllocateMessage(num).send(this);

      for (long oid : response.oids)
        fresh_ids.add(oid);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.client.Core#abortTransaction(int)
   */
  public void abortTransaction(int transactionID) {
    new AbortTransactionMessage(transactionID).send(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.client.Core#commitTransaction(int)
   */
  public void commitTransaction(int transactionID)
      throws UnreachableCoreException, TransactionCommitFailedException {
    new CommitTransactionMessage(transactionID).send(this);
  }

  @Override
  public String toString() {
    return "Core@" + name;
  }

  public Object getRoot() {
    return new Object.$Proxy(this, 0);
  }

  public String name() {
    return name;
  }
}
