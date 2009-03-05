package fabric.client;

import java.util.*;

import fabric.common.exceptions.FabricException;

public class TransactionPrepareFailedException extends FabricException {
  /**
   * A list of onums of objects used by the transaction and were out of date.
   */
  public final Set<Long> versionConflicts;

  public final List<String> messages;

  public TransactionPrepareFailedException(Set<Long> versionConflicts) {
    this.versionConflicts = versionConflicts;
    this.messages = null;
  }

  public TransactionPrepareFailedException(
      Map<RemoteNode, TransactionPrepareFailedException> failures) {
    this.versionConflicts = null;

    messages = new ArrayList<String>();
    for (Map.Entry<RemoteNode, TransactionPrepareFailedException> entry : failures
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
    this.versionConflicts = new HashSet<Long>();

    messages = new ArrayList<String>();
    for (TransactionPrepareFailedException exc : causes) {
      if (exc.versionConflicts != null)
        versionConflicts.addAll(exc.versionConflicts);

      if (exc.messages != null) messages.addAll(exc.messages);
    }
  }

  public TransactionPrepareFailedException(Set<Long> versionConflicts,
      String message) {
    this.versionConflicts = versionConflicts;
    messages = java.util.Collections.singletonList(message);
  }

  public TransactionPrepareFailedException(String message) {
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
