package fabric.messages;

import static fabric.common.Logging.NETWORK_MESSAGE_RECEIVE_LOGGER;
import static fabric.common.Logging.NETWORK_MESSAGE_SEND_LOGGER;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.net.SubSocket;
import fabric.lang.Object._Proxy;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

/**
 * Messages provide an interface for serializing requests and responses. The
 * <code>Message</code> class itself provides facilities for serialization and
 * deserialization, while the concrete subclasses give the structure of each
 * type of message.</p>
 * <p>
 * Messages are intended to be used in a synchronous, call-return style. To
 * support this, each Message type is bound to a specific Response type. On the
 * sender side, this allows type safety in the <code>send</code> method, for
 * example:<br>
 *
 * <pre>
 * ReadMessage.Response r = new ReadMessage(...).send(...);
 * </pre>
 *
 * while on the receiver side, type safety is enforced by only accepting
 * <code>R</code> in the <code>respond(...)</code> method.
 * </p>
 * <p>
 * Messages use two instances of the visitor pattern, one for Messages bound for
 * the store ({@link MessageToStore}) and one for Messages bound for the worker
 * ({@link MessageToWorker}). These interfaces would be subclasses of
 * <code>Message</code>, except that some message types (e.g.
 * <code>CommitTransactionMessage</code>) go to both, and Java doesn't support
 * multiple inheritance.
 * </p>
 *
 * @param <N>
 *          The kind of node that can send messages of this type.
 * @param <R>
 *          The response type
 * @param <E>
 *          The exception type that may occur at the remote node while handling
 *          the message.
 */
public abstract class Message<R extends Message.Response, E extends FabricException> {

  static AtomicLong msgCount = new AtomicLong();

  // ////////////////////////////////////////////////////////////////////////////
  // public API //
  // ////////////////////////////////////////////////////////////////////////////

  /** Marker interface for Message responses. */
  public static interface Response {
  }

  /**
   * Sends this message to the given node and awaits a response.
   *
   * @return the reply from the node.
   * @throws E
   *           if an error occurs at the remote node while handling the message.
   * @throws IOException
   *           in the event of a communications failure.
   */
  public final R send(SubSocket<?> s) throws IOException, E {
    DataInputStream in = new DataInputStream(s.getInputStream());
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    long msgId = msgCount.incrementAndGet();

    // Write this message out.
    out.writeBoolean(true); // This requires response.
    out.writeLong(msgId); // Helps with debugging, should be removed.
    out.writeByte(messageType.ordinal());
    writeMessage(out);
    out.flush();

    Logging.log(NETWORK_MESSAGE_SEND_LOGGER, Level.FINE,
        "Sent message {0} {1} to {2}", msgId, messageType, s);

    // Read in the reply. Determine if an error occurred.
    if (in.readBoolean()) {
      // We have an error.
      E exc = readObject(in, this.exceptionClass);
      exc.fillInStackTrace();

      Logging.log(NETWORK_MESSAGE_RECEIVE_LOGGER, Level.FINE,
          "Received error response for {0} from {1}", messageType, s);

      throw exc;
    }

    // Read the response.
    R response = readResponse(in);

    Logging.log(NETWORK_MESSAGE_RECEIVE_LOGGER, Level.FINE,
        "Received response for {0} from {1}", messageType, s);

    return response;
  }

  /**
   * Visitor method.
   *
   * @param client
   *          the node that is issuing the request.
   * @param handler
   *          the handler to which this message is to be dispatched.
   * @throws
   */
  public abstract R dispatch(RemoteIdentity<RemoteWorker> client,
      MessageHandler handler) throws ProtocolError, E;

  /**
   * Read a Message from the given <code>DataInput</code>
   *
   * @throws IOException
   *           If a malformed message is sent, or in the case of a failure in
   *           the <code>DataInput</code> provided.
   */
  public static Message<?, ?> receive(DataInput in,
      SubSocket<RemoteWorker> connection, long msgId) throws IOException {
    Message<?, ?> m = null;
    try {
      MessageType messageType = MessageType.values()[in.readByte()];

      m = messageType.parse(in);

      Logging.log(NETWORK_MESSAGE_RECEIVE_LOGGER, Level.FINE,
          "Received {0} {1} on {2}", msgId, messageType, connection);

      return m;

    } catch (final ArrayIndexOutOfBoundsException e) {
      throw new IOException("Unrecognized message");
    }
  }

  /**
   * Send a successful response this message.
   *
   * @param out
   *          the channel on which to send the response
   * @param response
   *          the response to send.
   * @throws IOException
   *           if the provided <code>DataOutput</code> fails.
   */
  public void respond(DataOutput out, Message.Response response)
      throws IOException {
    // Signal that no error occurred.
    out.writeBoolean(false);

    // Write out the response.
    @SuppressWarnings("unchecked")
    R r = (R) response;
    writeResponse(out, r);

    Logging.log(NETWORK_MESSAGE_SEND_LOGGER, Level.FINE,
        "Sent successful response to {0}", messageType);
  }

  /**
   * Send a response to this message that indicates an exception.
   *
   * @param out
   *          the channel on which to send the response
   * @param e
   *          the exception to send
   * @throws IOException
   *           if the provided <code>DataOutput</code> fails.
   */
  public void respond(DataOutput out, FabricException e) throws IOException {
    // Clear out the stack trace before sending an exception out.
    e.setStackTrace(new StackTraceElement[0]);

    // Signal that an error occurred and write out the exception.
    out.writeBoolean(true);

    // write out the exception
    writeObject(out, e);

    Logging.log(NETWORK_MESSAGE_SEND_LOGGER, Level.FINE,
        "Sent error response to {0}", messageType);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // API for concrete message implementations //
  // ////////////////////////////////////////////////////////////////////////////

  /**
   * This enum gives a mapping between message types and ordinals. This is used
   * for efficient encoding and decoding of the type of a message.
   */
  protected static enum MessageType {
    ALLOCATE_ONUMS {
      @Override
      AllocateMessage parse(DataInput in) throws IOException {
        return new AllocateMessage(in);
      }
    },
    READ_ONUM {
      @Override
      ReadMessage parse(DataInput in) throws IOException {
        return new ReadMessage(in);
      }
    },
    DISSEM_READ_ONUM {
      @Override
      DissemReadMessage parse(DataInput in) throws IOException {
        return new DissemReadMessage(in);
      }
    },
    NONATOMIC_CALL {
      @Override
      NonAtomicCallMessage parse(DataInput in) throws IOException {
        return new NonAtomicCallMessage(in);
      }
    },
    REMOTE_CALL {
      @Override
      RemoteCallMessage parse(DataInput in) throws IOException {
        return new RemoteCallMessage(in);
      }
    },
    DIRTY_READ {
      @Override
      DirtyReadMessage parse(DataInput in) throws IOException {
        return new DirtyReadMessage(in);
      }
    },
    TAKE_OWNERSHIP {
      @Override
      TakeOwnershipMessage parse(DataInput in) throws IOException {
        return new TakeOwnershipMessage(in);
      }
    },
    GET_CERT_CHAIN {
      @Override
      GetCertChainMessage parse(DataInput in) throws IOException {
        return new GetCertChainMessage(in);
      }
    },
    MAKE_PRINCIPAL {
      @Override
      MakePrincipalMessage parse(DataInput in) throws IOException {
        return new MakePrincipalMessage(in);
      }
    },
    STALENESS_CHECK {
      @Override
      StalenessCheckMessage parse(DataInput in) throws IOException {
        return new StalenessCheckMessage(in);
      }
    },
    INTERWORKER_STALENESS {
      @Override
      InterWorkerStalenessMessage parse(DataInput in) throws IOException {
        return new InterWorkerStalenessMessage(in);
      }
    },;

    /** Read a message of the appropriate type from the given DataInput. */
    abstract Message<?, ?> parse(DataInput in) throws IOException;
  }

  /** The <code>MessageType</code> corresponding to this class. */
  protected final MessageType messageType;

  /** The class of Exceptions that may be thrown in response to this Message */
  protected final Class<E> exceptionClass;

  /**
   * Constructs a message of the given <code>MessageType</code>
   *
   * @param exceptionClass
   *          TODO
   * @param exceptionClass
   *          TODO
   */
  protected Message(MessageType messageType, Class<E> exceptionClass) {
    this.messageType = messageType;
    this.exceptionClass = exceptionClass;
  }

  /**
   * Serializes a fabric object reference.
   */
  protected void writeRef(_Proxy ref, DataOutput out) throws IOException {
    out.writeUTF(ref.$getStore().name());
    out.writeLong(ref.$getOnum());
  }

  /**
   * Deserializes a fabric object reference.
   *
   * @param type
   *          The type of the reference being read. This must be the interface
   *          corresponding to the Fabric type, and not the _Proxy or _Impl
   *          classes.
   */
  protected static _Proxy readRef(Class<?> type, DataInput in)
      throws IOException {
    Store store = Worker.getWorker().getStore(in.readUTF());
    Class<? extends _Proxy> proxyType = null;
    for (Class<?> c : type.getClasses()) {
      if (c.getSimpleName().equals("_Proxy")) {
        @SuppressWarnings("unchecked")
        Class<? extends _Proxy> tmp = (Class<? extends _Proxy>) c;
        proxyType = tmp;
        break;
      }
    }

    if (proxyType == null)
      throw new InternalError("Unable to find proxy class for " + type);

    try {
      Constructor<? extends _Proxy> constructor =
          proxyType.getConstructor(Store.class, long.class);

      return constructor.newInstance(store, in.readLong());
    } catch (SecurityException e) {
      throw new InternalError(e);
    } catch (NoSuchMethodException e) {
      throw new InternalError(e);
    } catch (IllegalArgumentException e) {
      throw new InternalError(e);
    } catch (InstantiationException e) {
      throw new InternalError(e);
    } catch (IllegalAccessException e) {
      throw new InternalError(e);
    } catch (InvocationTargetException e) {
      throw new InternalError(e);
    }
  }

  /**
   * Serialize a java object to a DataOutput
   */
  protected void writeObject(DataOutput out, Object o) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(o);
    oos.flush();
    baos.flush();

    byte[] buf = baos.toByteArray();
    out.writeInt(buf.length);
    out.write(buf);
  }

  /**
   * Deserialize a java object from a DataOutput
   */
  protected <T> T readObject(DataInput in, Class<T> type) throws IOException {
    byte[] buf = new byte[in.readInt()];
    in.readFully(buf);

    ObjectInputStream ois =
        new ObjectInputStream(new ByteArrayInputStream(buf));

    try {
      Object o = ois.readObject();
      return type.cast(o);
    } catch (ClassCastException e) {
      throw new IOException("Unable to deserialize java object -- wrong type");
    } catch (ClassNotFoundException e) {
      throw new IOException(
          "Unable to deserialize java object -- no such class");
    }
  }

  /**
   * Concrete message implementations may use this class to indicate that no
   * exceptions should be thrown during message processing.
   */
  public static final class NoException extends FabricException {
    private NoException() {
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // abstract serialization methods //
  // ////////////////////////////////////////////////////////////////////////////

  /**
   * Writes this message out on the given output stream.
   *
   * @throws IOException
   *           if the <code>DataOutput</code> fails.
   */
  protected abstract void writeMessage(DataOutput out) throws IOException;

  /**
   * Each subclass should have a constructor of the form: protected
   * Message(DataInput in) throws IOException that constructs a message of the
   * given type, reading the data from the provided <code>DataInput</code>.
   *
   * @throws IOException
   *           if the message is malformed, or if the <code>DataInput</code>
   *           fails.
   */
  /* readMessage */
  // protected Message(DataInput in) throws IOException

  /**
   * Creates a Response message of the appropriate type using the provided
   * <code>DataOutput</code>
   *
   * @throws IOException
   *           if the response is malformed, or if the <code>DataInput</code>
   *           fails.
   */
  protected abstract R readResponse(DataInput in) throws IOException;

  /**
   * Writes a Response message of the appropriate type using the provided
   * <code>DataOutput</code>.
   *
   * @throws IOException
   *           if the <code>DataOutput</code> fails.
   */
  protected abstract void writeResponse(DataOutput out, R response)
      throws IOException;
}
