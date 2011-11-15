import java.lang.*;
import java.io.*;

class SignedObject extends Object implements Serializable {
    public UniqueID signature;  // should be the UniquedID of an RSAPrivateKey
    private Object o;

    public SignedObject(UniqueID signature, Object o) {
	this.signature = signature;
	this.o = o;
    }

    public Object getObject() {
	return o;
    }
}
    
