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
  public final OidKeyHashMap<TreatySet> longerTreaties;

  public final List<String> messages;

  public TransactionPrepareFailedException(
      TransactionRestartingException cause) {
    this.messages = new ArrayList<>();
    this.versionConflicts = new OidKeyHashMap<>();
    this.longerTreaties = new OidKeyHashMap<>();
  }

  public TransactionPrepareFailedException(
      OidKeyHashMap<SerializedObject> versionConflicts,
      OidKeyHashMap<TreatySet> longerTreaties) {
    this.versionConflicts = versionConflicts;
    this.longerTreaties = longerTreaties;
    this.messages = new ArrayList<>();
    for (SerializedObject o : versionConflicts.values()) {
      this.messages.add("Version conflict on " + o.getClassName() + " "
          + o.getOnum() + " (should be ver. " + o.getVersion() + ")");
    }
  }

  public TransactionPrepareFailedException(
      OidKeyHashMap<SerializedObject> versionConflicts, Store s,
      LongKeyMap<TreatySet> longerTreaties) {
    this.versionConflicts = versionConflicts;
    this.longerTreaties = new OidKeyHashMap<>();
    for (LongKeyMap.Entry<TreatySet> entry : longerTreaties.entrySet()) {
      this.longerTreaties.put(s, entry.getKey(), entry.getValue());
    }
    this.messages = new ArrayList<>();
    for (SerializedObject o : versionConflicts.values()) {
      this.messages.add("Version conflict on " + o.getClassName() + " "
          + o.getOnum() + " (should be ver. " + o.getVersion() + ")");
    }
  }

  public TransactionPrepareFailedException(
      OidKeyHashMap<SerializedObject> versionConflicts,
      OidKeyHashMap<TreatySet> longerTreaties, List<String> messages) {
    this.versionConflicts = versionConflicts;
    this.longerTreaties = longerTreaties;
    this.messages = messages;
  }

  public TransactionPrepareFailedException(
      Map<RemoteNode<?>, TransactionPrepareFailedException> failures) {
    this.versionConflicts = new OidKeyHashMap<>();
    this.longerTreaties = new OidKeyHashMap<>();

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
    this.longerTreaties = new OidKeyHashMap<>();

    messages = new ArrayList<>();
    for (TransactionPrepareFailedException exc : causes) {
      if (exc.versionConflicts != null)
        versionConflicts.putAll(exc.versionConflicts);

      if (exc.longerTreaties != null) longerTreaties.putAll(exc.longerTreaties);

      if (exc.messages != null) messages.addAll(exc.messages);
    }
  }

  public TransactionPrepareFailedException(
      OidKeyHashMap<SerializedObject> versionConflicts,
      OidKeyHashMap<TreatySet> longerTreaties, String message) {
    this.versionConflicts = versionConflicts;
    this.longerTreaties = longerTreaties;
    messages = java.util.Collections.singletonList(message);
  }

  public TransactionPrepareFailedException(
      OidKeyHashMap<SerializedObject> versionConflicts, Store s,
      LongKeyMap<TreatySet> longerTreaties, String message) {
    this.versionConflicts = versionConflicts;
    this.longerTreaties = new OidKeyHashMap<>();
    for (LongKeyMap.Entry<TreatySet> entry : longerTreaties.entrySet()) {
      this.longerTreaties.put(s, entry.getKey(), entry.getValue());
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
