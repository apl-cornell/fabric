package fabric.client;

import java.io.*;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.Principal;
import java.security.PublicKey;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.security.auth.x500.X500Principal;

import fabric.common.*;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.FetchException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NoSuchNodeError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.dissemination.Glob;
import fabric.lang.Object;
import fabric.lang.Object._Impl;
import fabric.lang.NodePrincipal;
import fabric.messages.*;
import fabric.util.Map;

/**
 * Encapsulates a Core. This class maintains two connections to the core (one
 * with SSL, for client requests; and one without, for dissemination requests)
 * and manages all communication. Cores can only be obtained through the
 * <code>Client.getCore()</code> interface. For each remote core, there should
 * be at most one <code>RemoteCore</code> object representing that core.
 */
public class RemoteCore implements Core, RemoteNode {
  private transient Socket sslConn;
  private transient Socket unencryptedConn;

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
  private transient LongKeyMap<FabricSoftRef> objects;

  /**
   * The connections to the actual Core.
   */
  private transient DataInputStream sslIn;
  private transient DataOutputStream sslOut;
  private transient DataInputStream unencryptedIn;
  private transient DataOutputStream unencryptedOut;

  /**
   * The core's public SSL key. Used for verifying signatures on object groups.
   * XXX Should use core's NodePrincipal key instead.
   */
  private transient final PublicKey publicKey;

  /**
   * Cache of serialized objects that the core has sent us.
   */
  private transient LongKeyMap<SerializedObjectSoftRef> serialized;
  final transient ReferenceQueue<SerializedObject> serializedRefQueue;

  /**
   * A thread for removing entries from <code>serialized</code> as
   * SerializedObjects are collected from memory.
   */
  private transient final SerializedCollector collector;

  private class SerializedCollector extends Thread {
    private final ReferenceQueue<SerializedObject> queue;
    private boolean destroyed;

    SerializedCollector() {
      super("Serialized object collector for core " + name);
      queue = new ReferenceQueue<SerializedObject>();
      destroyed = false;
    }
    
    @Override
    public void run() {
      while (!destroyed) {
        try {
          SerializedObjectSoftRef ref =
              (SerializedObjectSoftRef) queue.remove();
          
          synchronized (serialized) {
            serialized.remove(ref.onum);
          }
        } catch (InterruptedException e) {
        }
      }
    }
  }

  /**
   * Creates a core representing the core at the given host name.
   */
  protected RemoteCore(String name, PublicKey key) {
    this.name = name;
    this.objects = new LongKeyHashMap<FabricSoftRef>();
    this.fresh_ids = new LinkedList<Long>();
    this.serialized = new LongKeyHashMap<SerializedObjectSoftRef>();
    this.publicKey = key;
    this.serializedRefQueue = new ReferenceQueue<SerializedObject>();
    this.collector = new SerializedCollector();
    this.collector.start();
  }

  public DataInputStream dataInputStream(boolean ssl) {
    if (ssl) return sslIn;
    return unencryptedIn;
  }

  public DataOutputStream dataOutputStream(boolean ssl) {
    if (ssl) return sslOut;
    return unencryptedOut;
  }
  
  /**
   * Cleans up the SerializedObject collector thread.
   */
  public void destroy() {
    collector.destroyed = true;
    collector.interrupt();
  }

  /**
   * <p>
   * Establishes a connection with a core node at a given host. A helper for
   * <code>Message.send(Core)</code>.
   * </p>
   * <p>
   * NOTE: If you fix a bug in this method, then you'll probably want to fix a
   * bug in RemoteClient.connect() as well.
   * </p>
   * 
   * @param withSSL
   *          Whether to establish an encrypted connection.
   * @param host
   *          The host to connect to.
   * @param corePrincipal
   *          The principal associated with the core we're connecting to.
   * @throws IOException
   *           if there was an error.
   */
  public void connect(boolean withSSL, InetSocketAddress host,
      Principal corePrincipal) throws NoSuchNodeError, IOException {
    Client client = Client.getClient();
    Socket socket = new Socket();
    socket.setTcpNoDelay(true);
    socket.setKeepAlive(true);

    // Connect to the core node and identify the core we're interested in.
    socket.connect(host, client.timeout);
    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
    dataOut.writeUTF(name);

    // Specify whether we're encrypting.
    dataOut.writeBoolean(withSSL);
    dataOut.flush();

    // Determine whether the core exists at the node.
    if (socket.getInputStream().read() == 0) throw new NoSuchNodeError();

    if (withSSL && client.useSSL) {
      // Start encrypting.
      SSLSocket sslSocket;
      synchronized (client.sslSocketFactory) {
        sslSocket =
            (SSLSocket) client.sslSocketFactory.createSocket(socket, name, host
                .getPort(), true);
      }
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

      sslOut =
          new DataOutputStream(new BufferedOutputStream(sslSocket
              .getOutputStream()));
      sslOut.flush();
      sslIn =
          new DataInputStream(new BufferedInputStream(sslSocket
              .getInputStream()));

      sslConn = sslSocket;
    } else {
      DataOutputStream out =
          new DataOutputStream(new BufferedOutputStream(socket
              .getOutputStream()));
      if (withSSL) out.writeUTF(client.javaPrincipal.getName());
      out.flush();
      DataInputStream in =
          new DataInputStream(new BufferedInputStream(socket.getInputStream()));

      if (withSSL) {
        sslOut = out;
        sslIn = in;
        sslConn = socket;
      } else {
        unencryptedOut = out;
        unencryptedIn = in;
        unencryptedConn = socket;
      }
    }

    if (withSSL) {
      // Send to the core a pointer to our principal object.
      NodePrincipal principal = client.getPrincipal();
      sslOut.writeBoolean(principal != null);
      if (principal != null) {
        sslOut.writeUTF(principal.$getCore().name());
        sslOut.writeLong(principal.$getOnum());
      }
    }
  }

  public boolean isConnected(boolean ssl) {
    if (ssl) return sslConn != null && !sslConn.isClosed();
    return unencryptedConn != null && !unencryptedConn.isClosed();
  }

  public synchronized long createOnum() throws UnreachableNodeException {
    reserve(1);
    return fresh_ids.poll();
  }

  /**
   * Sends a PREPARE message to the core.
   */
  public boolean prepareTransaction(long tid, long commitTime,
      Collection<Object._Impl> toCreate, LongKeyMap<Integer> reads,
      Collection<Object._Impl> writes)
      throws TransactionPrepareFailedException, UnreachableNodeException {
    PrepareTransactionMessage.Response response =
        new PrepareTransactionMessage(tid, commitTime, toCreate, reads, writes)
            .send(this);

    if (!response.success)
      throw new TransactionPrepareFailedException(response.versionConflicts,
          response.message);

    return response.subTransactionCreated;
  }

  /**
   * Returns the requested _Impl object. If the object is not resident, it is
   * fetched from the Core via dissemination.
   * 
   * @param onum
   *          The identifier of the requested object
   * @return The requested object
   * @throws FabricException
   */
  public final Object._Impl readObject(long onum) throws FetchException {
    return readObject(true, onum);
  }

  /*
   * (non-Javadoc)
   * @see fabric.client.Core#readObjectNoDissem(long)
   */
  public final Object._Impl readObjectNoDissem(long onum) throws FetchException {
    return readObject(false, onum);
  }

  private final Object._Impl readObject(boolean useDissem, long onum)
      throws FetchException {
    // Intercept reads of global constants and redirect them to the local core.
    if (ONumConstants.isGlobalConstant(onum))
      return Client.instance.localCore.readObject(onum);

    // Check object table. Lock it to avoid a race condition when the object is
    // not in the cache and another thread attempts to read the same object.

    // XXX Deadlock if we simultaneously fetch surrogates from two cores that
    // refer to each other.
    synchronized (objects) {
      Object._Impl result = readObjectFromCache(onum);
      if (result != null) return result;
      return fetchObject(useDissem, onum);
    }
  }

  public Object._Impl readObjectFromCache(long onum) {
    synchronized (objects) {
      FabricSoftRef ref = objects.get(onum);
      if (ref == null) return null;
      return ref.get();
    }
  }

  /**
   * Fetches the object from the serialized cache, or goes to the core if it is
   * not present. Places the result in the object cache.
   * 
   * @param useDissem
   *          Whether to use the dissemination network. If false, the
   *          dissemination network will be bypassed.
   * @param onum
   *          The object number to fetch
   * @return The constructed _Impl
   * @throws FabricException
   */
  private Object._Impl fetchObject(boolean useDissem, long onum)
      throws FetchException {
    Object._Impl result = null;
    SoftReference<SerializedObject> serialRef;
    // Lock the table to keep the serialized-reference collector from altering
    // it.
    synchronized (serialized) {
      serialRef = serialized.remove(onum);
    }

    if (serialRef != null) {
      SerializedObject serial = serialRef.get();
      if (serial != null) result = serial.deserialize(this);
    }

    if (result == null) {
      // no serial copy --- fetch from the network.
      ObjectGroup g;
      if (useDissem) {
        g = Client.getClient().fetchManager().fetch(this, onum);
      } else {
        g = readObjectFromCore(onum);
      }

      // Lock the table to keep the serialized-reference collector from altering
      // it.
      synchronized (serialized) {
        for (LongKeyMap.Entry<SerializedObject> entry : g.objects().entrySet()) {
          long curOnum = entry.getKey();
          SerializedObject curObj = entry.getValue();

          if (curOnum == onum) {
            result = curObj.deserialize(this);
          } else {
            // Add to the cache if object not already in memory.
            serialized.put(entry.getKey(), new SerializedObjectSoftRef(this,
                entry.getValue()));
          }
        }
      }
    }

    while (result instanceof Surrogate) {
      // XXX Track surrogates for reuse?
      Surrogate surrogate = (Surrogate) result;
      if (useDissem)
        result = surrogate.core.readObject(surrogate.onum);
      else result = surrogate.core.readObjectNoDissem(surrogate.onum);
    }

    synchronized (objects) {
      objects.put(onum, result.$ref);
    }

    return result;
  }

  /**
   * Goes to the core to get object.
   * 
   * @param onum
   *          The object number to fetch
   * @return An ObjectGroup whose head object is the requested object.
   * @throws FetchException
   *           if there was an error while fetching the object from the core.
   */
  public ObjectGroup readObjectFromCore(long onum) throws FetchException {
    ReadMessage.Response response = new ReadMessage(onum).send(this);
    return response.group;
  }

  /**
   * Called by dissemination to fetch an encrypted object from the core.
   * 
   * @param onum
   *          The object number to fetch.
   */
  public final Glob readEncryptedObjectFromCore(long onum)
      throws FetchException {
    DissemReadMessage.Response response =
        new DissemReadMessage(onum).send(this);
    return response.glob;
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
   *          The number of objects to allocate
   */
  protected void reserve(int num) throws UnreachableNodeException {
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
   * @see fabric.client.Core#abortTransaction(long)
   */
  public void abortTransaction(TransactionID tid) {
    new AbortTransactionMessage(tid).send(this);
  }

  /*
   * (non-Javadoc)
   * @see fabric.client.Core#commitTransaction(int)
   */
  public void commitTransaction(long transactionID)
      throws UnreachableNodeException, TransactionCommitFailedException {
    CommitTransactionMessage.Response response =
        new CommitTransactionMessage(transactionID).send(this);
    if (!response.success)
      throw new TransactionCommitFailedException(response.message);
  }

  @Override
  public String toString() {
    return "Core@" + name;
  }

  public Map getRoot() {
    return new Map._Proxy(this, ONumConstants.ROOT_MAP);
  }

  public final String name() {
    return name;
  }
  
  public NodePrincipal getPrincipal() {
    return new NodePrincipal._Proxy(this, ONumConstants.CORE_PRINCIPAL);
  }

  public final boolean isLocalCore() {
    return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  /**
   * Notifies that an object has been evicted from cache.
   */
  public void notifyEvict(long onum) {
    synchronized (objects) {
      FabricSoftRef r = objects.get(onum);

      if (r != null && r.get() == null) {
        objects.remove(onum);
      }
    }
  }

  public void evict(long onum) {
    synchronized (objects) {
      FabricSoftRef r = objects.get(onum);
      r.evict();
    }
  }

  public void cache(_Impl impl) {
    FabricSoftRef ref = impl.$ref;
    if (ref.core != this)
      throw new InternalError("Caching object at wrong core");

    synchronized (objects) {
      if (objects.get(ref.onum) != null)
        throw new InternalError("Conflicting cache entry");

      objects.put(ref.onum, ref);
    }
  }

  /**
   * @return The core's public key for verifying Glob signatures.
   */
  public PublicKey getPublicKey() {
    return publicKey;
  }

}
