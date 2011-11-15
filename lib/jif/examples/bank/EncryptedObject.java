import java.lang.*;
import java.io.*;

public class EncryptedObject extends Object implements Serializable {
    private UniqueID tag;
    private Object o;

    public EncryptedObject(UniqueID tag, Object o) {
	this.tag = tag;
	this.o = o;
    }

    public UniqueID getTag() {
	return tag;
    }

    public Object getObject() {
	return o;
    }
}
