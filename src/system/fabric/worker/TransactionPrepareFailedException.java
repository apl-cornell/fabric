package fabric.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fabric.common.SerializedObject;
import fabric.common.exceptions.FabricException;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.net.RemoteNode;

public class TransactionPrepareFailedException extends FabricException {
  /**
   * A set of objects used by the transaction and were out of date.
   */
  public final LongKeyMap<SerializedObject> versionConflicts;

  /**
   * Set of objects whose expiration times were longer than observed by the
   * transaction.
   */
  public final LongKeyMap<Long> longerContracts;

  public final List<String> messages;

  public TransactionPrepareFailedException(
      TransactionRestartingException cause) {
    this.messages = null;
    this.versionConflicts = null;
    this.longerContracts = null;
  }

  public TransactionPrepareFailedException(
      LongKeyMap<SerializedObject> versionConflicts,
      LongKeyMap<Long> longerContracts) {
    this.versionConflicts = versionConflicts;
    this.longerContracts = longerContracts;
    this.messages = new ArrayList<>();
    for (SerializedObject o : versionConflicts.values()) {
      this.messages.add("Version conflict on " + o.getClassName() + " "
          + o.getOnum() + " (should be ver. " + o.getVersion() + ")");
    }
  }

  public TransactionPrepareFailedException(
      Map<RemoteNode<?>, TransactionPrepareFailedException> failures) {
    this.versionConflicts = null;
    this.longerContracts = null;

    messages = new ArrayList<>();
    for (Map.Entry<RemoteNode<?>, TransactionPrepareFailedException> entry : failures
        .entrySet()) {
      TransactionPrepareFailedException exn = entry.getValue();

      if (exn.messages != null) {
        for (String s : exn.messages)
          messages.add(entry.getKey() + ": " + s);
      }
    }
  }

  public TransactionPrepareFailedException(
      List<TransactionPrepareFailedException> causes) {
    this.versionConflicts = new LongKeyHashMap<>();
    this.longerContracts = new LongKeyHashMap<>();

    messages = new ArrayList<>();
    for (TransactionPrepareFailedException exc : causes) {
      if (exc.versionConflicts != null)
        versionConflicts.putAll(exc.versionConflicts);

      if (exc.longerContracts != null)
        longerContracts.putAll(exc.longerContracts);

      if (exc.messages != null) messages.addAll(exc.messages);
    }
  }

  public TransactionPrepareFailedException(
      LongKeyMap<SerializedObject> versionConflicts,
      LongKeyMap<Long> longerContracts, String message) {
    this.versionConflicts = versionConflicts;
    this.longerContracts = longerContracts;
    messages = java.util.Collections.singletonList(message);
  }

  public TransactionPrepareFailedException(String message) {
    this(null, null, message);
  }

  @Override
  public String getMessage() {
    String result = "Transaction failed to prepare.";

    if (messages != null) {
      for (String m : messages) {
        result += System.getProperty("line.separator") + "    " + m;
      }
    }

    return result;
  }

}
