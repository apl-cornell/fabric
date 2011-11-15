import java.lang.*;

class RSAPublicKey extends Key {
    public RSAPublicKey(UniqueID keyData) {
	super(keyData);
    }

    public Object decrypt(EncryptedObject e) {
	return new Object();   // Just return junk...
    }

    public boolean verifySignature(SignedObject s) {
	return (s.signature.isEqual(this.keyData));
    }
}
