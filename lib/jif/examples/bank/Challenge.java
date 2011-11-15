class Challenge extends Object {
    public EncryptedObject customerPart;
    private UniqueID uid;

    public Challenge (RSAPublicKey customerKey) {
	uid = new UniqueID();
	customerPart = customerKey.encrypt(uid);
    }

    public boolean confirm(Object o) {
	try {
	    UniqueID id = (UniqueID) o;
	    return (uid.isEqual(id));
	} catch (Exception e) {
	    return false;
	}
    }
}
