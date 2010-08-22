package fabric.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.util.*;

import fabric.common.exceptions.InternalError;
import fabric.common.util.MutableInteger;
import fabric.common.util.Pair;

/**
 * Multiplexes several channels over a single channel.
 */
public final class ChannelMultiplexerThread extends Thread {
  public static final int BUFFER_SIZE = 8192; // At most 65535.
  private static final int BUFFER_POOL_SIZE = 100;

  public static interface CallbackHandler {
    /**
     * Signals that the remote end has created a new sub-stream. Implementations
     * should call registerChannels() to register a pair of channels at the
     * given streamID.
     */
    void newStream(ChannelMultiplexerThread muxer, int streamID);

    /**
     * Signals that the remote host has closed the connection.
     */
    void connectionClosed();

    /**
     * Signals that the channel multiplexer thread is shutting down.
     */
    void shutdown();
  }

  /**
   * The actual connection to the node.
   */
  private final SocketChannel socketChannel;

  private final CallbackHandler callback;

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
  private final List<Integer> streamsToClose;

  /**
   * Creates a new thread that will multiplex/demultiplex multiple streams over
   * the given socket channel.
   * 
   * @param handler
   *          a handler for callbacks.
   * @param name
   *          the name of the thread.
   * @param socketChannel
   *          the socket channel to use.
   */
  public ChannelMultiplexerThread(CallbackHandler handler, String name,
      SocketChannel socketChannel) throws IOException {
    super(name);
    this.callback = handler;
    this.socketChannel = socketChannel;
    socketChannel.configureBlocking(false);

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
    this.networkOutputBuffer.clear().flip();

    try {
      this.selector = Selector.open();
    } catch (IOException e) {
      throw new InternalError(e);
    }

    this.newSourceChannels =
        Collections.synchronizedList(new ArrayList<SourceChannel>());
    this.streamsToClose =
        Collections.synchronizedList(new ArrayList<Integer>());
  }

  public void shutdown() {
    this.destroyed = true;
    this.callback.shutdown();

    try {
      socketChannel.close();
    } catch (IOException e) {
    }

    try {
      selector.close();
    } catch (IOException e) {
    }

    interrupt();
  }

  @Override
  public void run() {
    //
    // The following invariants are established before each select operation:
    //
    //  - Each CommManager-facing source channel (i.e., pipe inbound from the
    //    app) is either in the readQueue or is registered with the selector
    //    for reading.
    //
    //  - Either the outputBuffer and streamsToClose are both empty or the
    //    network channel is registered with the selector for writing.
    //
    //  - If the outputBuffer is empty, then there are no channels in the
    //    readQueue.
    //
    //  - The network channel is always registered with the selector for
    //    reading.
    //
    //  - For each CommManager-facing sink channel (i.e., pipe outbound to the
    //    app), all buffers in the corresponding buffer queue are non-empty.
    //
    //  - For each CommManager-facing sink channel (i.e, pipe outbound to the
    //    app), either the corresponding buffer queue is empty or the channel is
    //    registered with the selector for writing.
    //

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

        if (!streamsToClose.isEmpty()) {
          socketChannel.keyFor(selector).interestOps(
              SelectionKey.OP_READ | SelectionKey.OP_WRITE);
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
          } else if (key.isWritable()) {
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
      } catch (ClosedSelectorException e) {
        if (!destroyed) throw new InternalError(e);
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
    if (buffer.hasRemaining()) {
      return;
    }

    bufferPool.recycle(bufferQueue.remove());
    if (bufferQueue.isEmpty()) {
      synchronized (sink) {
        if (sink.isOpen()) sink.keyFor(selector).interestOps(0);
      }
    }
  }

  /**
   * A helper method for run(). Assumes the network socket is ready to write and
   * the output buffer is non-empty.
   */
  private void writeToNetwork() throws IOException {
    socketChannel.write(networkOutputBuffer);
    if (networkOutputBuffer.hasRemaining()) {
      return;
    }

    // Buffer is empty. Check whether we have streams to close.
    synchronized (streamsToClose) {
      if (!streamsToClose.isEmpty()) {
        networkOutputBuffer.clear();
        for (Iterator<Integer> it = streamsToClose.iterator(); it.hasNext();) {
          if (networkOutputBuffer.remaining() < 8) break;
          networkOutputBuffer.putInt(it.next());
          networkOutputBuffer.putInt(-1);
          it.remove();
        }

        networkOutputBuffer.flip();
        return;
      }
    }

    // Read from the next pipe in the readQueue and re-register the pipe for
    // reading.
    if (!readQueue.isEmpty()) {
      SourceChannel source = readQueue.remove();
      readFromPipe(source);
      if (source.isOpen())
        source.keyFor(selector).interestOps(SelectionKey.OP_READ);
      return;
    }

    // No streams to close and the readQueue is empty. Disable write-selection
    // for the network socket channel.
    socketChannel.keyFor(selector).interestOps(SelectionKey.OP_READ);
  }

  /**
   * A helper for run(). Assumes the given channel is ready to read.
   */
  private void readFromPipe(SourceChannel source) throws IOException {
    if (networkOutputBuffer.hasRemaining()) {
      // Output buffer not empty. Queue the pipe and unregister it from the
      // selector.
      readQueue.add(source);
      source.keyFor(selector).interestOps(0);
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

      streamIDBySourceChannel.remove(source);

      // Flush the buffer.
      networkOutputBuffer.clear().flip();
      return;
    }

    socketChannel.keyFor(selector).interestOps(
        SelectionKey.OP_READ | SelectionKey.OP_WRITE);
  }

  /**
   * A helper for run(). Assumes the network socket is ready to read.
   */
  private void readFromNetwork() throws IOException {
    if (socketChannel.read(networkInputBuffer) == -1) {
      callback.connectionClosed();
      shutdown();
    }

    networkInputBuffer.flip();

    while (networkInputBuffer.hasRemaining()) {
      if (bytesRemaining == 0) {
        // Read header.
        if (networkInputBuffer.remaining() < 8) break;

        int streamID = networkInputBuffer.getInt();
        currentStream = getStream(streamID);
        bytesRemaining = networkInputBuffer.getInt();

        // Check whether the remote host is closing the stream.
        if (bytesRemaining == -1) {
          cleanupStream(streamID);
          bytesRemaining = 0;
        }
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
        dataBuffer.limit(bytesRemaining);
        bytesRemaining = 0;
      } else {
        // All remaining bytes on the input buffer belong to the same stream.
        // Use the input buffer as the data buffer and create a new input
        // buffer.
        dataBuffer = networkInputBuffer;
        networkInputBuffer = bufferPool.getBuffer();
        networkInputBuffer.clear().flip();
        bytesRemaining -= dataBuffer.remaining();
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
    } else {
      networkInputBuffer.clear();
    }
  }

  private Pair<SinkChannel, Queue<ByteBuffer>> getStream(int streamID) {
    Pair<SinkChannel, Queue<ByteBuffer>> result =
        sinkChannelByStreamID.get(streamID);
    if (result == null) {
      callback.newStream(this, streamID);
      result = sinkChannelByStreamID.get(streamID);
    }
    return result;
  }

  private int getStreamID(ReadableByteChannel channel) {
    return streamIDBySourceChannel.get(channel);
  }

  /**
   * Registers a pair of channels for communicating with a message-handler
   * thread, assigning them a designated streamID, and registers the source
   * channel with the selector.
   */
  public synchronized void registerChannels(int streamID, SourceChannel source,
      SinkChannel sink) throws IOException {
    if (sinkChannelByStreamID.containsKey(streamID)) {
      throw new InternalError(
          "Attempting to register a second stream pair at streamID=" + streamID);
    }

    source.configureBlocking(false);
    sink.configureBlocking(false);

    Queue<ByteBuffer> bufferQueue = new LinkedList<ByteBuffer>();
    sinkChannelByStreamID.put(streamID,
        new Pair<SinkChannel, Queue<ByteBuffer>>(sink, bufferQueue));
    bufferQueueBySinkChannel.put(sink, bufferQueue);
    streamIDBySourceChannel.put(source, streamID);

    newSourceChannels.add(source);
    selector.wakeup();
  }

  /**
   * Registers a pair of channels for communicating with a message-handler
   * thread, assigning them a fresh streamID, and registers the source channel
   * with the selector.
   */
  public int registerChannels(SourceChannel source, SinkChannel sink)
      throws IOException {
    int streamID;
    synchronized (nextStreamID) {
      streamID = nextStreamID.value++;
    }

    registerChannels(streamID, source, sink);

    return streamID;
  }

  void closeStream(int streamID) throws IOException {
    streamsToClose.add(streamID);
    selector.wakeup();

    cleanupStream(streamID);
  }

  private synchronized void cleanupStream(int streamID) throws IOException {
    // No need to remove from streamIDBySourceChannel -- this will be taken care
    // of in readFromPipe() when the app closes the DataOutputStream.
    // (See Stream.close().)
    SinkChannel sink = sinkChannelByStreamID.remove(streamID).first;
    bufferQueueBySinkChannel.remove(sink);
    sink.close();
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
