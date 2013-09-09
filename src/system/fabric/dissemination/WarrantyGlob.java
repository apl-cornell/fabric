package fabric.dissemination;

import java.io.DataInput;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.SignatureException;

import fabric.common.WarrantyGroup;
import fabric.lang.security.Label;
import fabric.worker.RemoteStore;
import fabric.worker.Store;

/**
 * A WarrantyGroup that has been encrypted and signed.
 */
public class WarrantyGlob extends AbstractGlob<WarrantyGroup> {
  /**
   * Used by the store to encrypt and sign a warranty group.
   * 
   * @param store
   *          The store from which the group originated.
   * @param signingKey
   *          The store's private signing key.
   * @param group
   *          The group to encapsulate.
   */
  public WarrantyGlob(Store store, long labelOnum, PrivateKey key,
      WarrantyGroup group) {
    this(new Label._Proxy(store, labelOnum), key, group);
  }

  public WarrantyGlob(Label label, PrivateKey key, WarrantyGroup group) {
    super(label, key, group);
  }

  @Override
  protected void verifyNoSignatureRequired(WarrantyGroup payload)
      throws SignatureException {
    // XXX TODO Implement this.
  }

  /**
   * Deserializer.
   */
  public WarrantyGlob(DataInput in) throws IOException {
    super(in);
  }

  @Override
  protected WarrantyGroup deserializePayload(DataInput in) throws IOException {
    return new WarrantyGroup(in);
  }

  @Override
  public boolean updateCache(Cache dissemCache, RemoteStore store, long onum) {
    return dissemCache.updateEntry(store, onum, this);
  }

}
