package fabric.messages;

import static fabric.common.Logging.NETWORK_MESSAGE_RECEIVE_LOGGER;
import static fabric.common.Logging.NETWORK_MESSAGE_SEND_LOGGER;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.net.SubSocket;
import fabric.worker.remote.RemoteWorker;

/**
 * Async messages provide an interface for serializing messages which expect no
 * response. The <code>AsyncMessage</code> class itself provides facilities for
 * serialization and deserialization, while the concrete subclasses give the
 * structure of each type of message.</p>
 * <p>
 * AsyncMessages are intended to be used in a asynchronous, fire-and-forget
 * style.
 *
 * @param <N>
 *          The kind of node that can send messages of this type.
 */
public abstract class AsyncMessage {

  // ////////////////////////////////////////////////////////////////////////////
  // public API //
  // ////////////////////////////////////////////////////////////////////////////

  /**
   * Sends this message to the given node and awaits a response.
   *
   * @return the reply from the node.
   * @throws E
   *           if an error occurs at the remote node while handling the message.
   * @throws IOException
   *           in the event of a communications failure.
   */
  public final void send(SubSocket<?> s) throws IOException {
    DataOutputStream out = new DataOutputStream(s.getOutputStream());
    long msgId = Message.msgCount.incrementAndGet();

    // Write this message out.
    out.writeBoolean(false); // This does not require response.
    out.writeLong(msgId);
    out.writeByte(messageType.ordinal());
    writeMessage(out);
    out.flush();

    Logging.log(NETWORK_MESSAGE_SEND_LOGGER, Level.FINE,
        "Sent async message {0} {1} to {2}", msgId, messageType, s);
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
  public abstract void dispatch(RemoteIdentity<RemoteWorker> client,
      MessageHandler handler) throws ProtocolError;

  /**
   * Read a Message from the given <code>DataInput</code>
   *
   * @throws IOException
   *           If a malformed message is sent, or in the case of a failure in
   *           the <code>DataInput</code> provided.
   */
  public static AsyncMessage receive(DataInput in,
      SubSocket<RemoteWorker> connection, long msgId) throws IOException {
    AsyncMessage m = null;
    try {
      MessageType messageType = MessageType.values()[in.readByte()];

      m = messageType.parse(in);

      Logging.log(NETWORK_MESSAGE_RECEIVE_LOGGER, Level.FINE,
          "Received async message {0} {1} on {2}", msgId, messageType,
          connection);

      return m;

    } catch (final ArrayIndexOutOfBoundsException e) {
      throw new IOException("Unrecognized message");
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // API for concrete message implementations //
  // ////////////////////////////////////////////////////////////////////////////

  /**
   * This enum gives a mapping between message types and ordinals. This is used
   * for efficient encoding and decoding of the type of a message.
   */
  protected static enum MessageType {
    TREATY_EXTENSION {
      @Override
      TreatyExtensionMessage parse(DataInput in) throws IOException {
        return new TreatyExtensionMessage(in);
      }
    },
    OBJECT_UPDATE {
      @Override
      ObjectUpdateMessage parse(DataInput in) throws IOException {
        return new ObjectUpdateMessage(in);
      }
    },
    PREPARE_TRANSACTION {
      @Override
      PrepareTransactionMessage parse(DataInput in) throws IOException {
        return new PrepareTransactionMessage(in);
      }
    },
    STORE_PREPARE_FAILED {
      @Override
      StorePrepareFailedMessage parse(DataInput in) throws IOException {
        return new StorePrepareFailedMessage(in);
      }
    },
    STORE_PREPARE_SUCCESS {
      @Override
      StorePrepareSuccessMessage parse(DataInput in) throws IOException {
        return new StorePrepareSuccessMessage(in);
      }
    },
    WORKER_PREPARE_FAILED {
      @Override
      WorkerPrepareFailedMessage parse(DataInput in) throws IOException {
        return new WorkerPrepareFailedMessage(in);
      }
    },
    WORKER_PREPARE_SUCCESS {
      @Override
      WorkerPrepareSuccessMessage parse(DataInput in) throws IOException {
        return new WorkerPrepareSuccessMessage(in);
      }
    },
    COMMIT_TRANSACTION {
      @Override
      CommitTransactionMessage parse(DataInput in) throws IOException {
        return new CommitTransactionMessage(in);
      }
    },
    STORE_COMMITTED {
      @Override
      StoreCommittedMessage parse(DataInput in) throws IOException {
        return new StoreCommittedMessage(in);
      }
    },
    WORKER_COMMITTED {
      @Override
      WorkerCommittedMessage parse(DataInput in) throws IOException {
        return new WorkerCommittedMessage(in);
      }
    },
    ABORT_TRANSACTION {
      @Override
      AbortTransactionMessage parse(DataInput in) throws IOException {
        return new AbortTransactionMessage(in);
      }
    },
    UNSUBSCRIBE {
      @Override
      UnsubscribeMessage parse(DataInput in) throws IOException {
        return new UnsubscribeMessage(in);
      }
    };

    /** Read a message of the appropriate type from the given DataInput. */
    abstract AsyncMessage parse(DataInput in) throws IOException;
  }

  /** The <code>MessageType</code> corresponding to this class. */
  protected final MessageType messageType;

  /**
   * Constructs a message of the given <code>MessageType</code>
   *
   * @param messageType
   *          Identifier for serialization format the type of message this is.
   */
  protected AsyncMessage(MessageType messageType) {
    this.messageType = messageType;
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
}
