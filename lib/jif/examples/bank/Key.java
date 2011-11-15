import java.lang.*;
import java.io.*;

class Key extends Object implements Serializable {
    UniqueID keyData;
    public Key() {
	keyData = new UniqueID();
    }

    public Key(UniqueID keyData) {
	this.keyData = keyData;
    }

    public EncryptedObject encrypt(Object o) {
	return new EncryptedObject(keyData, o);
    }
    public Object decrypt(EncryptedObject e) {
	if (keyData.isEqual(e.getTag())) {
	    return e.getObject();
	} else {
	    return new Object();
	}
    }
}
