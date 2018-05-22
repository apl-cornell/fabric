package fabric.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fabric.common.SerializedObject;
import fabric.common.exceptions.FabricException;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.net.RemoteNode;
import fabric.worker.metrics.treaties.TreatySet;

public class TransactionPrepareFailedException extends FabricException {
  /**
   * A set of objects used by the transaction and were out of date.
   */
  public final OidKeyHashMap<SerializedObject> versionConflicts;

  /**
   * Set of objects whose treaties were longer than observed by the transaction.
   */
  public final OidKeyHashMap<TreatySet> longerContracts;

  public final List<String> messages;

  public TransactionPrepareFailedException(
      TransactionRestartingException cause) {
    this.messages = new ArrayList<>();
    this.versionConflicts = new OidKeyHashMap<>();
    this.longerContracts = new OidKeyHashMap<>();
  }

  public TransactionPrepareFailedException(
      OidKeyHashMap<SerializedObject> versionConflicts,
      OidKeyHashMap<TreatySet> longerContracts) {
    this.versionConflicts = versionConflicts;
    this.longerContracts = longerContracts;
    this.messages = new ArrayList<>();
    for (SerializedObject o : versionConflicts.values()) {
      this.messages.add("Version conflict on " + o.getClassName() + " "
          + o.getOnum() + " (should be ver. " + o.getVersion() + ")");
    }
  }

  public TransactionPrepareFailedException(
      OidKeyHashMap<SerializedObject> versionConflicts, Store s,
      LongKeyMap<TreatySet> longerContracts) {
    this.versionConflicts = versionConflicts;
    this.longerContracts = new OidKeyHashMap<>();
    for (LongKeyMap.Entry<TreatySet> entry : longerContracts.entrySet()) {
      this.longerContracts.put(s, entry.getKey(), entry.getValue());
    }
    this.messages = new ArrayList<>();
    for (SerializedObject o : versionConflicts.values()) {
      this.messages.add("Version conflict on " + o.getClassName() + " "
          + o.getOnum() + " (should be ver. " + o.getVersion() + ")");
    }
  }

  public TransactionPrepareFailedException(
      OidKeyHashMap<SerializedObject> versionConflicts,
      OidKeyHashMap<TreatySet> longerContracts, List<String> messages) {
    this.versionConflicts = versionConflicts;
    this.longerContracts = longerContracts;
    this.messages = messages;
  }

  public TransactionPrepareFailedException(
      Map<RemoteNode<?>, TransactionPrepareFailedException> failures) {
    this.versionConflicts = new OidKeyHashMap<>();
    this.longerContracts = new OidKeyHashMap<>();

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
    this.versionConflicts = new OidKeyHashMap<>();
    this.longerContracts = new OidKeyHashMap<>();

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
      OidKeyHashMap<SerializedObject> versionConflicts,
      OidKeyHashMap<TreatySet> longerContracts, String message) {
    this.versionConflicts = versionConflicts;
    this.longerContracts = longerContracts;
    messages = java.util.Collections.singletonList(message);
  }

  public TransactionPrepareFailedException(
      OidKeyHashMap<SerializedObject> versionConflicts, Store s,
      LongKeyMap<TreatySet> longerContracts, String message) {
    this.versionConflicts = versionConflicts;
    this.longerContracts = new OidKeyHashMap<>();
    for (LongKeyMap.Entry<TreatySet> entry : longerContracts.entrySet()) {
      this.longerContracts.put(s, entry.getKey(), entry.getValue());
    }
    messages = java.util.Collections.singletonList(message);
  }

  public TransactionPrepareFailedException(String message) {
    this(new OidKeyHashMap<SerializedObject>(), new OidKeyHashMap<TreatySet>(),
        message);
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
