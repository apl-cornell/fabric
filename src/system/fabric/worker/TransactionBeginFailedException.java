package fabric.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fabric.common.SerializedObject;
import fabric.common.exceptions.FabricException;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.net.RemoteNode;

public class TransactionBeginFailedException extends FabricException {
  /**
   * A set of objects used by the transaction and were out of date.
   */
  public final LongKeyMap<SerializedObject> versionConflicts;

  public final List<String> messages;

  public TransactionBeginFailedException(
      LongKeyMap<SerializedObject> versionConflicts) {
    this.versionConflicts = versionConflicts;
    this.messages = null;
  }

  public TransactionBeginFailedException(
      Map<RemoteNode, TransactionBeginFailedException> failures) {
    this.versionConflicts = null;

    messages = new ArrayList<String>();
    for (Map.Entry<RemoteNode, TransactionBeginFailedException> entry : failures
        .entrySet()) {
      TransactionBeginFailedException exn = entry.getValue();

      if (exn.messages != null) {
        for (String s : exn.messages)
          messages.add(entry.getKey() + ": " + s);
      }
    }
  }

  public TransactionBeginFailedException(
      List<TransactionBeginFailedException> causes) {
    this.versionConflicts = new LongKeyHashMap<SerializedObject>();

    messages = new ArrayList<String>();
    for (TransactionBeginFailedException exc : causes) {
      if (exc.versionConflicts != null)
        versionConflicts.putAll(exc.versionConflicts);

      if (exc.messages != null) messages.addAll(exc.messages);
    }
  }

  public TransactionBeginFailedException(
      LongKeyMap<SerializedObject> versionConflicts, String message) {
    this.versionConflicts = versionConflicts;
    messages = java.util.Collections.singletonList(message);
  }

  public TransactionBeginFailedException(String message) {
    this(null, message);
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
