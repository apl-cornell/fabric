package fabric.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import fabric.common.SerializedObject;
import fabric.common.VersionWarranty;
import fabric.common.exceptions.FabricException;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;
import fabric.net.RemoteNode;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.WarrantiedCallResult;

public class TransactionPrepareFailedException extends FabricException
  implements Serializable {
  /**
   * A set of objects used by the transaction and were out of date.
   */
  public LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts;

  /**
   * A set of calls used by the transaction that were out of date and the store
   * had a fresh value for.
   */
  public Map<CallInstance, WarrantiedCallResult> callConflictUpdates;

  /**
   * A set of calls used by the transaction that were out of date and the store
   * did _not_ have a fresh value for.
   */
  public Set<CallInstance> callConflicts;

  public List<String> messages;

  /* XXX Methods for explicitly serializing this thing because honestly I don't
   * know of a better way to handle my current problem.
   */
  private void writeObject(ObjectOutputStream out) throws IOException {
    DataOutputStream outD = new DataOutputStream(out);

    //Write out version conflicts
    if (versionConflicts == null) {
      outD.writeInt(0);
    } else {
      outD.writeInt(versionConflicts.size());
      for (LongKeyMap.Entry<Pair<SerializedObject, VersionWarranty>> e :
          versionConflicts.entrySet()) {
        outD.writeLong(e.getKey());
        e.getValue().first.write(outD);
        outD.writeLong(e.getValue().second.expiry());
      }
    }

    if (callConflictUpdates == null) {
      outD.writeInt(0);
    } else {
      outD.writeInt(callConflictUpdates.size());
      for (Map.Entry<CallInstance, WarrantiedCallResult> e :
          callConflictUpdates.entrySet()) {
        e.getKey().write(outD);
        e.getValue().write(outD);
      }
    }

    if (callConflicts == null) {
      outD.writeInt(0);
    } else {
      outD.writeInt(callConflicts.size());
      for (CallInstance call : callConflicts)
        call.write(outD);
    }

    if (messages == null) {
      outD.writeInt(0);
    } else {
      outD.writeInt(messages.size());
      for (String s : messages)
        outD.writeUTF(s);
    }
    outD.flush();
  }

  private void readObject(ObjectInputStream in) throws IOException,
          ClassNotFoundException {
    DataInputStream inD = new DataInputStream(in);
    int verSize = inD.readInt();
    this.versionConflicts = new LongKeyHashMap<Pair<SerializedObject, VersionWarranty>>(verSize);
    for (int i = 0; i < verSize; i++) {
      long id = inD.readLong();
      SerializedObject obj = new SerializedObject(inD);
      VersionWarranty war = new VersionWarranty(inD.readLong());
      this.versionConflicts.put(id, new Pair<SerializedObject,
          VersionWarranty>(obj, war));
    }

    int callUpSize = inD.readInt();
    this.callConflictUpdates = new HashMap<CallInstance,
      WarrantiedCallResult>(callUpSize);
    for (int i = 0; i < callUpSize; i++) {
      CallInstance call = new CallInstance(inD);
      this.callConflictUpdates.put(call, new WarrantiedCallResult(inD));
    }

    int callConSize = inD.readInt();
    this.callConflicts = new HashSet<CallInstance>(callConSize);
    for (int i = 0; i < callConSize; i++) {
      this.callConflicts.add(new CallInstance(inD));
    }

    int messagesSize = inD.readInt();
    this.messages = new ArrayList<String>(messagesSize);
    for (int i = 0; i < messagesSize; i++) {
      this.messages.add(inD.readUTF());
    }
  }

  private void readObjectNoData(ObjectInputStream in) throws
    ObjectStreamException {
      throw new ObjectStreamException() {};
  }

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
      Map<RemoteNode<?>, TransactionPrepareFailedException> failures) {
    this.versionConflicts = null;
    this.callConflictUpdates = null;
    this.callConflicts = null;

    messages = new ArrayList<String>();
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
    this.versionConflicts =
        new LongKeyHashMap<Pair<SerializedObject, VersionWarranty>>();
    this.callConflictUpdates =
        new HashMap<CallInstance, WarrantiedCallResult>();
    this.callConflicts = new HashSet<CallInstance>();

    messages = new ArrayList<String>();
    for (TransactionPrepareFailedException exc : causes) {
      if (exc.versionConflicts != null)
        versionConflicts.putAll(exc.versionConflicts);

      if (exc.callConflictUpdates != null)
        callConflictUpdates.putAll(exc.callConflictUpdates);

      if (exc.callConflicts != null) callConflicts.addAll(exc.callConflicts);

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
      Set<CallInstance> callConflicts, String message) {
    messages = java.util.Collections.singletonList(message);
    this.versionConflicts = null;
    this.callConflictUpdates = callConflictUpdates;
    this.callConflicts = callConflicts;
  }

  public TransactionPrepareFailedException(
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts,
      Map<CallInstance, WarrantiedCallResult> callConflictUpdates,
      Set<CallInstance> callConflicts, String message) {
    messages = java.util.Collections.singletonList(message);
    this.versionConflicts = versionConflicts;
    this.callConflictUpdates = callConflictUpdates;
    this.callConflicts = callConflicts;
  }

  public TransactionPrepareFailedException(
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts,
      Map<CallInstance, WarrantiedCallResult> callConflictUpdates,
      Set<CallInstance> callConflicts, List<String> messages) {
    this.messages = messages;
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
