package fabric.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.lang.ref.SoftReference;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Principal;
import java.util.*;

import javax.net.ssl.SSLSocket;
import javax.security.auth.x500.X500Principal;

import fabric.common.AccessError;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.core.SerializedObject;
import fabric.lang.Object;
import fabric.messages.*;

/**
 * Encapsulates a Core. This class maintains the connection to the core and
 * manages all communication. Cores can only be obtained through the
 * <code>Client.getCore()</code> interface.
 */
public class RemoteCore implements Core {
  private transient Socket conn;

  /**
   * The core identifier that this Core encapsulates.
   */
  public final long coreID;

  /**
   * A queue of fresh object identifiers.
   */
  private transient Queue<Long> fresh_ids;

  /**
   * The object table: locally resident objects.
   */
  private transient Map<Long, SoftReference<Object.$Impl>> objects;

  /**
   * The connection to the actual Core.
   */
  private transient ObjectInputStream in;
  private transient ObjectOutputStream out;

  /**
   * Cached but unserialized objects that the core has sent us.
   */
  private transient Map<Long, SoftReference<SerializedObject>> serialized;

  /**
   * Create a core representing the given coreID
   */
  RemoteCore(long coreID) {
    this.coreID = coreID;
    this.objects = new HashMap<Long, SoftReference<Object.$Impl>>();
    this.fresh_ids = new LinkedList<Long>();
    this.serialized = new HashMap<Long, SoftReference<SerializedObject>>();
  }

  public ObjectInputStream objectInputStream() {
    return in;
  }

  public ObjectOutputStream objectOutputStream() {
    return out;
  }

  /**
   * Establishes a connection with a core node at a given host. A helper for
   * <code>Message.send(Core)</code>. XXX Is this in the right place?
   * 
   * @param client
   *                The Client instance.
   * @param host
   *                The host to connect to.
   * @param hostPrincipal
   *                The principal associated with the host we're connecting to.
   * @throws IOException
   *                 if there was an error.
   */
  public void connect(Client client, InetSocketAddress host,
      Principal hostPrincipal) throws IOException {
    SSLSocket socket = (SSLSocket) client.sslSocketFactory.createSocket();
    socket.setKeepAlive(true);
    socket.connect(host, client.timeout);
    socket.startHandshake();

    // Make sure we're talking to the right node.
    X500Principal peer = (X500Principal) socket.getSession().getPeerPrincipal();
    if (!peer.equals(hostPrincipal)) throw new IOException();
    out = new ObjectOutputStream(socket.getOutputStream());
    out.flush();
    in = new ObjectInputStream(socket.getInputStream());

    try {
      new ConnectMessage(coreID).send(in, out);
    } catch (FabricException e) {
      // Shouldn't get an exception from the core.
      throw new InternalError(e);
    }

    out.reset();

    conn = socket;
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
      Map<Long, Integer> reads, Collection<Object.$Impl> writes)
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
  public Object.$Impl readObject(long onum) throws AccessError,
      UnreachableCoreException {
    // Check object table
    Object.$Impl result = null;
    SoftReference<Object.$Impl> resultRef = objects.get(onum);
    if (resultRef != null && (result = resultRef.get()) != null) return result;
    return readObjectFromCore(onum);
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
  private Object.$Impl readObjectFromCore(long onum) throws AccessError,
      UnreachableCoreException {
    SerializedObject serial = null;
    SoftReference<SerializedObject> serialRef = serialized.remove(onum);

    if (serialRef == null || (serial = serialRef.get()) == null) {
      // no serial copy --- fetch from core
      ReadMessage.Response response = new ReadMessage(onum).send(this);
      serial = response.result;
      for (Map.Entry<Long, SerializedObject> entry : response.related
          .entrySet())
        serialized.put(entry.getKey(), new SoftReference<SerializedObject>(
            entry.getValue()));
    }

    try {
      Object.$Impl result = serial.deserialize(this, onum);
      objects.put(onum, new SoftReference<Object.$Impl>(result));
      return result;
    } catch (ClassNotFoundException e) {
      // TODO handle this
      return null;
    }
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
    return Client.getClient().getCore(coreID);
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
  public boolean equals(java.lang.Object obj) {
    return obj instanceof RemoteCore && ((RemoteCore) obj).coreID == coreID;
  }

  @Override
  public int hashCode() {
    return new Long(coreID).hashCode();
  }

  @Override
  public String toString() {
    return "Core@" + coreID;
  }

  public Object getRoot() throws UnreachableCoreException {
    return new Object.$Proxy(this, 0);
  }

  public long id() {
    return coreID;
  }
}
