package fabric.dissemination;

import static fabric.common.ONumConstants.EMPTY_LABEL;

import java.io.DataInput;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.SignatureException;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.lang.security.Label;
import fabric.worker.RemoteStore;
import fabric.worker.Store;

/**
 * An object glob is an ObjectGroup that has been encrypted and signed.
 */
public class ObjectGlob extends AbstractGlob<ObjectGroup> {

  /**
   * Used by the store to encrypt and sign an object group.
   *
   * @param store
   *          The store at which the group resides.
   * @param group
   *          The group to encapsulate.
   * @param key
   *          The store's private key. Used to sign the glob.
   */
  public ObjectGlob(Store store, ObjectGroup group, PrivateKey key) {
    super(getLabel(store, group), key, group);
  }

  /**
   * Verifies that the given group does not require a signature.
   */
  @Override
  protected void verifyNoSignatureRequired(ObjectGroup group)
      throws SignatureException {
    for (SerializedObject obj : group.objects().values()) {
      if (obj.getUpdateLabelOnum() != EMPTY_LABEL)
        throw new SignatureException("Failed to verify signature");
    }
  }

  private static Label getLabel(Store store, ObjectGroup group) {
    SerializedObject obj =
        group.objects().entrySet().iterator().next().getValue();
    return new Label._Proxy(store, obj.getUpdateLabelOnum());
  }

  /**
   * Deserializer.
   */
  public ObjectGlob(DataInput in) throws IOException {
    super(in);
  }

  @Override
  protected ObjectGroup deserializePayload(DataInput in) throws IOException {
    return new ObjectGroup(in);
  }

  @Override
  public boolean updateCache(Cache dissemCache, RemoteStore store, long onum) {
    return dissemCache.updateEntry(store, onum, this);
  }
}
