class RSAPrivateKey extends Key {
    public RSAPublicKey createPublicKey() {
	return new RSAPublicKey(this.keyData);
    }

    public SignedObject signObject(Object o) {
	return new SignedObject(this.keyData, o);
    }
}
