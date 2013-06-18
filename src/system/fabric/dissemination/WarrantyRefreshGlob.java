package fabric.dissemination;

import java.io.DataInput;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.SignatureException;

import fabric.common.WarrantyRefreshGroup;
import fabric.lang.security.Label;
import fabric.worker.Store;

/**
 * A WarrantyRefreshGroup that has been encrypted and signed.
 */
public class WarrantyRefreshGlob extends AbstractGlob<WarrantyRefreshGroup> {
  /**
   * Used by the store to encrypt and sign a warranty-refresh group.
   * 
   * @param store
   *          The store at from which the refresh originated.
   * @param signingKey
   *          The store's private signing key.
   * @param group
   *          The group to encapsulate.
   */
  public WarrantyRefreshGlob(Store store, PrivateKey key,
      WarrantyRefreshGroup group) {
    super(getLabel(store, group), key, group);
  }

  private static Label getLabel(Store store, WarrantyRefreshGroup group) {
    long memberOnum = group.iterator().next().onum;
    fabric.lang.Object obj = new fabric.lang.Object._Proxy(store, memberOnum);
    return obj.get$$updateLabel();
  }

  @Override
  protected void verifyNoSignatureRequired(WarrantyRefreshGroup payload)
      throws SignatureException {
    // XXX TODO Implement this.
  }

  /**
   * Deserializer.
   */
  public WarrantyRefreshGlob(DataInput in) throws IOException {
    super(in);
  }

  @Override
  protected WarrantyRefreshGroup deserializePayload(DataInput in)
      throws IOException {
    return new WarrantyRefreshGroup(in);
  }

}
