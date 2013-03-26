package fabric.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fabric.common.SerializedObject;
import fabric.common.VersionWarranty;
import fabric.common.exceptions.FabricException;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;
import fabric.net.RemoteNode;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.WarrantiedCallResult;

public class TransactionPrepareFailedException extends FabricException {
  /**
   * A set of objects used by the transaction and were out of date.
   */
  public final LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts;
  
  /**
   * A set of calls used by the transaction that were out of date and the store
   * had a fresh value for.
   */
  public final Map<CallInstance, WarrantiedCallResult> callConflictUpdates;

  /**
   * A set of calls used by the transaction that were out of date and the store
   * did _not_ have a fresh value for.
   */
  public final Set<CallInstance> callConflicts;

  public final List<String> messages;

  public TransactionPrepareFailedException(TransactionRestartingException cause) {
    this.messages = null;
    this.versionConflicts = null;
    this.callConflictUpdates = null;
    this.callConflicts = null;
  }

  public TransactionPrepareFailedException(
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts) {
    this.versionConflicts = versionConflicts;
    this.messages = null;
    this.callConflictUpdates = null;
    this.callConflicts = null;
  }

  /**
   * XXX: This could use a comment.
   */
  public TransactionPrepareFailedException(
      Map<RemoteNode, TransactionPrepareFailedException> failures) {
    this.versionConflicts = null;
    this.callConflictUpdates = null;
    this.callConflicts = null;

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
    this.versionConflicts =
        new LongKeyHashMap<Pair<SerializedObject, VersionWarranty>>();
    this.callConflictUpdates =
        new HashMap<CallInstance, WarrantiedCallResult>();
    this.callConflicts =
        new HashSet<CallInstance>();

    messages = new ArrayList<String>();
    for (TransactionPrepareFailedException exc : causes) {
      if (exc.versionConflicts != null)
        versionConflicts.putAll(exc.versionConflicts);

      if (exc.callConflictUpdates != null)
        callConflictUpdates.putAll(exc.callConflictUpdates);

      if (exc.callConflicts != null)
        callConflicts.addAll(exc.callConflicts);

      if (exc.messages != null) messages.addAll(exc.messages);
    }
  }

  public TransactionPrepareFailedException(
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts,
      String message) {
    this.versionConflicts = versionConflicts;
    messages = java.util.Collections.singletonList(message);
    this.callConflictUpdates = null;
    this.callConflicts = null;
  }

  public TransactionPrepareFailedException(String message) {
    this(null, message);
  }

  public TransactionPrepareFailedException(
      Map<CallInstance, WarrantiedCallResult> callConflictUpdates,
      Set<CallInstance> callConflicts,
      String message) {
    messages = java.util.Collections.singletonList(message);
    this.versionConflicts = null;
    this.callConflictUpdates = callConflictUpdates;
    this.callConflicts = callConflicts;
  }

  public TransactionPrepareFailedException(
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts,
      Map<CallInstance, WarrantiedCallResult> callConflictUpdates,
      Set<CallInstance> callConflicts,
      String message) {
    messages = java.util.Collections.singletonList(message);
    this.versionConflicts = versionConflicts;
    this.callConflictUpdates = callConflictUpdates;
    this.callConflicts = callConflicts;
  }

  public TransactionPrepareFailedException(
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts,
      Map<CallInstance, WarrantiedCallResult> callConflictUpdates,
      Set<CallInstance> callConflicts) {
    this.messages = null;
    this.versionConflicts = versionConflicts;
    this.callConflictUpdates = callConflictUpdates;
    this.callConflicts = callConflicts;
  }

  public TransactionPrepareFailedException(
      Map<CallInstance, WarrantiedCallResult> callConflictUpdates,
      Set<CallInstance> callConflicts) {
    this(null, callConflictUpdates, callConflicts);
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
