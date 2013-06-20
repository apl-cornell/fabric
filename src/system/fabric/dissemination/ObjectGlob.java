package fabric.dissemination;

import static fabric.common.Logging.NETWORK_MESSAGE_RECEIVE_LOGGER;
import static fabric.common.ONumConstants.EMPTY_LABEL;

import java.io.DataInput;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.VersionWarranty;
import fabric.common.util.Pair;
import fabric.lang.security.Label;
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
    for (Pair<SerializedObject, VersionWarranty> pair : group.objects()
        .values()) {
      if (pair.first.getUpdateLabelOnum() != EMPTY_LABEL)
        throw new SignatureException("Failed to verify signature");
    }
  }

  private static Label getLabel(Store store, ObjectGroup group) {
    SerializedObject obj =
        group.objects().entrySet().iterator().next().getValue().first;
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
    ObjectGroup result = new ObjectGroup(in);

    if (NETWORK_MESSAGE_RECEIVE_LOGGER.isLoggable(Level.FINE)) {
      Logging.log(NETWORK_MESSAGE_RECEIVE_LOGGER, Level.FINE,
          "Decrypted object group containing {0} objects", result.objects()
              .size());
    }

    return result;
  }
}