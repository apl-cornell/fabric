package fabric.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.security.Principal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NoSuchNodeError;
import fabric.common.util.MutableInteger;
import fabric.common.util.Pair;
import fabric.lang.NodePrincipal;

/**
 * <p>
 * Manages a connection with a remote node. The connection is used to send
 * requests to and receive responses from the node.
 * </p>
 * <p>
 * XXX Assumes connections never get dropped.
 * </p>
 */
class CommManager extends Thread {
  final static Logger LOGGER = Logger.getLogger("fabric.messages");
  private static final int BUFFER_SIZE = 8192; // At most 65535.
  private static final int BUFFER_POOL_SIZE = 100;

  private final RemoteNode node;
  private final boolean useSSL;

  /**
   * The actual connection to the node.
   */
  private final SocketChannel socketChannel;

  /**
   * A thread-local pair of streams for the app to communicate with the remote
   * node. XXX TODO Ought to be able to maintain this information by attaching
   * the appropriate objects to the appropriate selection keys.
   */
  private final ThreadLocal<Pair<DataInputStream, DataOutputStream>> streams;

  /**
   * Maps stream IDs to CommManager-facing sink channels and their buffer queue.
   * XXX TODO Ought to be able to maintain this information by attaching the
   * appropriate objects to the appropriate selection keys.
   */
  private final Map<Integer, Pair<SinkChannel, Queue<ByteBuffer>>> sinkChannelByStreamID;

  /**
   * Maps CommManager-facing sink channels to their buffer queue. XXX TODO Ought
   * to be able to maintain this information by attaching the appropriate
   * objects to the appropriate selection keys.
   */
  private final Map<SinkChannel, Queue<ByteBuffer>> bufferQueueBySinkChannel;

  /**
   * Maps CommManager-facing source channels to stream IDs. XXX TODO Ought to be
   * able to maintain this information by attaching the appropriate objects to
   * the appropriate selection keys.
   */
  private final Map<SourceChannel, Integer> streamIDBySourceChannel;

  /**
   * A queue of channels that are waiting to be read.
   */
  private final Queue<SourceChannel> readQueue;

  private volatile boolean destroyed;

  private final BufferPool bufferPool;

  /**
   * A counter for enumerating stream IDs.
   */
  private final MutableInteger nextStreamID;

  private ByteBuffer networkInputBuffer;

  /**
   * The sink channel and buffer queue for the data currently being read off the
   * network.
   */
  private Pair<SinkChannel, Queue<ByteBuffer>> currentStream;

  /**
   * The amount of data for the current stream that still needs to be read off
   * the network.
   */
  private int bytesRemaining;

  private final ByteBuffer networkOutputBuffer;
  private final Selector selector;

  private final List<SourceChannel> newSourceChannels;

  /**
   * @param useSSL
   *          whether the data should be sent on the network with SSL
   *          encryption.
   */
  CommManager(RemoteNode remoteNode, boolean useSSL) {
    super("Thread for communicating with node " + remoteNode.name);

    this.node = remoteNode;
    this.useSSL = useSSL;
    this.socketChannel = connect();
    this.streams = new ThreadLocal<Pair<DataInputStream, DataOutputStream>>() {
      @Override
      public Pair<DataInputStream, DataOutputStream> initialValue() {
        try {
          // APP --[outbound]--> CommManager
          // APP <--[inbound ]-- CommManager
          Pipe inbound = Pipe.open();
          Pipe outbound = Pipe.open();
          registerChannels(outbound.source(), inbound.sink());

          DataInputStream in =
              new DataInputStream(Channels.newInputStream(inbound.source()));
          DataOutputStream out =
              new DataOutputStream(Channels.newOutputStream(outbound.sink()));

          return new Pair<DataInputStream, DataOutputStream>(in, out);
        } catch (IOException e) {
          throw new InternalError(e);
        }
      }
    };

    this.sinkChannelByStreamID =
        Collections
            .synchronizedMap(new HashMap<Integer, Pair<SinkChannel, Queue<ByteBuffer>>>());
    this.bufferQueueBySinkChannel =
        Collections
            .synchronizedMap(new WeakHashMap<SinkChannel, Queue<ByteBuffer>>());
    this.streamIDBySourceChannel =
        Collections.synchronizedMap(new WeakHashMap<SourceChannel, Integer>());
    this.readQueue = new LinkedList<SourceChannel>();

    this.destroyed = false;

    this.bufferPool = new BufferPool();

    this.nextStreamID = new MutableInteger(0);

    this.networkInputBuffer = bufferPool.getBuffer();
    this.currentStream = null;
    this.bytesRemaining = 0;

    this.networkOutputBuffer = bufferPool.getBuffer();

    try {
      this.selector = Selector.open();

      Client client = Client.getClient();
      if (useSSL && !client.useSSL) {
        networkOutputBuffer.clear();
        networkOutputBuffer.put(client.javaPrincipal.getName()
            .getBytes("UTF-8"));
        networkOutputBuffer.flip();
        socketChannel.write(networkOutputBuffer);
      }

      if (useSSL) {
        // Send to the core a pointer to our principal object.
        NodePrincipal principal = client.getPrincipal();
        networkOutputBuffer.clear();
        networkOutputBuffer.put((byte) (principal != null ? 1 : 0));
        if (principal != null) {
          networkOutputBuffer
              .put(principal.$getCore().name().getBytes("UTF-8"));
          networkOutputBuffer.putLong(principal.$getOnum());
        }

        networkOutputBuffer.flip();
        socketChannel.write(networkOutputBuffer);
      }

      // Connection is now set up. Go into non-blocking mode.
      socketChannel.configureBlocking(false);
    } catch (IOException e) {
      throw new InternalError(e);
    }

    this.newSourceChannels =
        Collections.synchronizedList(new ArrayList<SourceChannel>());

    start();
  }

  /**
   * @return a connected SocketChannel configured in blocking mode.
   */
  private SocketChannel connect() {
    Client client = Client.getClient();
    final int retries = client.retries;

    int hostIdx = 0;

    // These will be filled in with real values if needed.
    List<InetSocketAddress> hosts = null;
    Principal nodePrincipal = null;
    int numHosts = 0;
    int startHostIdx = 0;

    for (int retry = 0; retries < 0 || retry < retries;) {
      try {
        if (hosts == null) {
          Pair<List<InetSocketAddress>, Principal> entry =
              client.nameService.lookup(node);
          hosts = entry.first;
          nodePrincipal = entry.second;

          numHosts = hosts.size();
          startHostIdx = Client.RAND.nextInt(numHosts);
        }

        // Attempt to establish a connection.
        int hostNum = (startHostIdx + hostIdx) % numHosts;
        return connect(hosts.get(hostNum), nodePrincipal);
      } catch (NoSuchNodeError e) {
        // Connected to a system that doesn't host the node we're interested
        // in.
        // Increment loop counter variables.

        LOGGER.log(Level.WARNING, "Failed to connect", e);

        hostIdx++;
        if (hostIdx == numHosts) {
          hostIdx = 0;
          if (retries >= 0) retry++;
        }
        continue;
      } catch (IOException e) {
        // Retry.
        LOGGER.log(Level.WARNING, "Failed to connect", e);

        if (hosts == null) {
          // Attempt to reuse an existing connection failed. Just restart the
          // loop.
          continue;
        }

        // Increment loop counter variables.
        hostIdx++;
        if (hostIdx == numHosts) {
          hostIdx = 0;
          if (retries >= 0) retry++;
        }
        continue;
      }
    }

    throw new UnreachableNodeException(node);
  }

  /**
   * <p>
   * Establishes a connection with a node at the given host. A helper for
   * <code>send(ByteBuffer)</code>.
   * </p>
   * 
   * @param addr
   *          The host to connect to.
   * @param remotePrincipal
   *          The principal associated with the node we're connecting to.
   * @throws NoSuchNodeError
   *           If the node is not hosted at the given address.
   * @throws IOException
   *           If there was an I/O error.
   * @return a connected SocketChannel configured in blocking mode.
   */
  private SocketChannel connect(InetSocketAddress addr,
      Principal remotePrincipal) throws NoSuchNodeError, IOException {
    Client client = Client.getClient();
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.configureBlocking(true);

    Socket socket = socketChannel.socket();
    socket.setTcpNoDelay(true);
    socket.setKeepAlive(true);

    // Attempt to connect to the remote host.
    socketChannel.socket().connect(addr, client.timeout);

    // Give the name of the node we're interested in.
    DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
    dataOut.writeUTF(node.name);

    // Specify whether we're encrypting.
    dataOut.writeBoolean(useSSL);
    dataOut.flush();

    // Determine whether the core exists at the node.
    if (socket.getInputStream().read() == 0) throw new NoSuchNodeError();

    if (useSSL && client.useSSL) {
      // XXX TODO Start encrypting.
      // SSLSocket sslSocket;
      // synchronized (client.sslSocketFactory) {
      // sslSocket =
      // (SSLSocket) client.sslSocketFactory.createSocket(socket, node.name,
      // addr.getPort(), true);
      // }
      // sslSocket.setUseClientMode(true);
      // sslSocket.startHandshake();
      //
      // // Make sure we're talking to the right node.
      // X500Principal peer =
      // (X500Principal) sslSocket.getSession().getPeerPrincipal();
      // if (!peer.equals(remotePrincipal)) {
      // Logger.getLogger(this.getClass().getName()).info(
      // "Rejecting connection to " + addr + ": got principal " + peer
      // + " when we expected " + remotePrincipal);
      // sslSocket.close();
      // throw new IOException();
      // }

      return socketChannel;
    } else {
      return socketChannel;
    }
  }

  /**
   * @return the DataInputStream/DataOutputStream pair for the substream that is
   *         associated with the currently running thread.
   */
  Pair<DataInputStream, DataOutputStream> getStreams() {
    return streams.get();
  }

  public void cleanup() {
    this.destroyed = true;
    interrupt();
  }

  @Override
  public void run() {
    /**
     * The following invariants are established before each select operation.
     * <ul>
     * <li>Each CommManager-facing source channel (i.e., pipe inbound from the
     * app) is either in the readQueue or is registered with the selector for
     * reading.</li>
     * <li>Either the outputBuffer is empty or the network channel is registered
     * with the selector for writing.</li>
     * <li>If the outputBuffer is empty, then there are no channels in the
     * readQueue.</li>
     * <li>The network channel is always registered with the selector for
     * reading.</li>
     * <li>For each CommManager-facing sink channel (i.e., pipe outbound to the
     * app), all buffers in the corresponding buffer queue are non-empty.</li>
     * <li>For each CommManager-facing sink channel (i.e, pipe outbound to the
     * app), either the corresponding buffer queue is empty or the channel is
     * registered with the selector for writing.</li>
     * </ul>
     */

    // Establish invariants.
    try {
      socketChannel.register(selector, SelectionKey.OP_READ);
    } catch (ClosedChannelException e) {
      throw new InternalError(e);
    }

    while (!destroyed) {
      try {
        // Process selector changes.
        synchronized (newSourceChannels) {
          for (SourceChannel newSource : newSourceChannels) {
            newSource.register(selector, SelectionKey.OP_READ);
          }

          newSourceChannels.clear();
        }

        // Wait for I/O.
        selector.select();

        // Process I/O.
        for (Iterator<SelectionKey> keyIt = selector.selectedKeys().iterator(); keyIt
            .hasNext();) {
          SelectionKey key = keyIt.next();
          keyIt.remove();

          if (!key.isValid()) continue;

          if (key.isReadable()) {
            ReadableByteChannel source = (ReadableByteChannel) key.channel();
            if (source == socketChannel) {
              readFromNetwork();
            } else {
              readFromPipe((SourceChannel) source);
            }
          }

          if (key.isWritable()) {
            WritableByteChannel sink = (WritableByteChannel) key.channel();
            if (sink == socketChannel) {
              writeToNetwork();
            } else {
              SinkChannel sinkChannel = (SinkChannel) sink;
              writeToPipe(sinkChannel, bufferQueueBySinkChannel
                  .get(sinkChannel));
            }
          }
        }
      } catch (IOException e) {
        throw new InternalError(e);
      }
    }
  }

  /**
   * A helper for run(). Assumes the given channel is ready to write, the
   * associated buffer queue is non-empty, and the first buffer in the queue is
   * non-empty.
   */
  private void writeToPipe(SinkChannel sink, Queue<ByteBuffer> bufferQueue)
      throws IOException {
    ByteBuffer buffer = bufferQueue.peek();
    sink.write(buffer);

    // Maintain invariants.
    if (buffer.hasRemaining()) return;

    bufferPool.recycle(bufferQueue.remove());
    if (bufferQueue.isEmpty()) {
      sink.keyFor(selector).cancel();
    }
  }

  /**
   * A helper method for run(). Assumes the network socket is ready to write and
   * the output buffer is non-empty.
   */
  private void writeToNetwork() throws IOException {
    socketChannel.write(networkOutputBuffer);
    if (networkOutputBuffer.hasRemaining()) return;

    // Buffer is empty. Read from the next pipe in the readQueue.
    if (!readQueue.isEmpty()) {
      readFromPipe(readQueue.remove());
      return;
    }

    // The readQueue is empty. Disable write-selection for the network socket
    // channel.
    socketChannel.register(selector, SelectionKey.OP_READ);
  }

  /**
   * A helper for run(). Assumes the given channel is ready to read.
   */
  private void readFromPipe(SourceChannel source) throws IOException {
    if (networkOutputBuffer.hasRemaining()) {
      // Output buffer not empty. Queue the pipe and unregister it from the
      // selector.
      readQueue.add(source);
      source.keyFor(selector).cancel();
      return;
    }

    // Create the header.
    networkOutputBuffer.clear();
    networkOutputBuffer.putInt(getStreamID(source));
    networkOutputBuffer.putInt(0);

    // Read into the output buffer, keep the pipe's selector registration, fix
    // the header, and register the network channel with the selector for
    // writing.
    int numRead = source.read(networkOutputBuffer);
    networkOutputBuffer.putInt(4, numRead);
    networkOutputBuffer.flip();

    if (numRead == -1) {
      // End of source channel. Close the channel and unregister it from the
      // selector.
      try {
        source.close();
      } catch (IOException e) {
      }

      SelectionKey key = source.keyFor(selector);
      if (key != null) key.cancel();

      // Flush the buffer.
      networkOutputBuffer.clear().flip();
      return;
    }

    socketChannel.register(selector, SelectionKey.OP_READ
        | SelectionKey.OP_WRITE);
  }

  /**
   * A helper for run(). Assumes the network socket is ready to read.
   */
  private void readFromNetwork() throws IOException {
    socketChannel.read(networkInputBuffer);
    networkInputBuffer.flip();

    while (networkInputBuffer.hasRemaining()) {
      if (bytesRemaining == 0) {
        // Read header.
        if (networkInputBuffer.remaining() < 8) break;

        int streamID = networkInputBuffer.getInt();
        currentStream = sinkChannelByStreamID.get(streamID);
        bytesRemaining = networkInputBuffer.getInt();
        continue;
      }

      // Separate out the data for the current stream from the rest of the input
      // buffer.
      ByteBuffer dataBuffer;
      if (networkInputBuffer.remaining() > bytesRemaining) {
        // Get a new buffer and copy the remaining bytes from the network input
        // buffer.
        byte[] data = new byte[BUFFER_SIZE];
        networkInputBuffer.get(data, 0, bytesRemaining);
        dataBuffer = ByteBuffer.wrap(data);
        bytesRemaining = 0;
      } else {
        // All remaining bytes on the input buffer belong to the same stream.
        // Use the input buffer as the data buffer and create a new input
        // buffer.
        dataBuffer = networkInputBuffer;
        networkInputBuffer = bufferPool.getBuffer();
        networkInputBuffer.clear().flip();
        bytesRemaining -= networkInputBuffer.remaining();
      }

      // Register the sink channel for writing if necessary.
      if (currentStream.second.isEmpty()) {
        currentStream.first.register(selector, SelectionKey.OP_WRITE);
      }

      // Queue the data for writing.
      currentStream.second.add(dataBuffer);
    }

    if (networkInputBuffer.hasRemaining()) {
      // Get a new input buffer and copy the remaining bytes from the old
      // buffer.
      ByteBuffer oldBuffer = networkInputBuffer;
      networkInputBuffer = bufferPool.getBuffer();
      networkInputBuffer.clear();
      networkInputBuffer.put(oldBuffer);
    } else networkInputBuffer.clear();
  }

  private int getStreamID(ReadableByteChannel channel) {
    return streamIDBySourceChannel.get(channel);
  }

  /**
   * Registers a pair of channels for communicating with a worker thread,
   * assigning them a fresh streamID, and registers the source channel with the
   * selector.
   */
  private void registerChannels(SourceChannel source, SinkChannel sink) {
    Integer streamID;
    synchronized (nextStreamID) {
      streamID = nextStreamID.value++;
    }

    Queue<ByteBuffer> bufferQueue = new LinkedList<ByteBuffer>();
    sinkChannelByStreamID.put(streamID,
        new Pair<SinkChannel, Queue<ByteBuffer>>(sink, bufferQueue));
    bufferQueueBySinkChannel.put(sink, bufferQueue);
    streamIDBySourceChannel.put(source, streamID);

    newSourceChannels.add(source);
  }

  private static class BufferPool {
    private final ByteBuffer[] pool;
    private int poolSize;

    BufferPool() {
      pool = new ByteBuffer[BUFFER_POOL_SIZE];
      poolSize = 0;
    }

    ByteBuffer getBuffer() {
      if (poolSize == 0) return ByteBuffer.allocate(BUFFER_SIZE);

      poolSize--;
      ByteBuffer result = pool[poolSize];
      pool[poolSize] = null;
      return result;
    }

    void recycle(ByteBuffer buffer) {
      if (poolSize == pool.length) return;
      pool[poolSize] = buffer;
      poolSize++;
    }
  }
}
