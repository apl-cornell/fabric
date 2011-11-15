import java.io.*;
import java.lang.*;

public class UniqueID extends Object implements Serializable {
    private static int nextID = 0;
    private int id;

    public UniqueID() {
	id = nextID;
	nextID ++;
    }

    public boolean isEqual(UniqueID that) {
	return (this.id == that.id);
    }
}
