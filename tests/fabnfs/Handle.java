package fabnfs;

import java.util.Hashtable;
import java.util.Vector;

class Handle extends java.lang.Object  {
    Hashtable nameToNumber;
    Vector    numberToName;
    long      sequence; // the last id used
    BackupFile backup;

    Handle(String cacheFileName, PathMapper pm) {
	nameToNumber = new Hashtable(2500, 2500);
	numberToName = new Vector(2500, 2500);
	sequence = 0;

	// once this is created load the backup file into the handle table.
	backup = new BackupFile(this, cacheFileName);
    }

    String Lookup(long id) {
	try {
	    Object result = numberToName.elementAt((int) id);
	    if (result instanceof String) {
	        return (String) result;
	    }
	    return null;
	} catch(ArrayIndexOutOfBoundsException e) {
	    System.err.print("handle " + id + " not registered\n");
	    return null;
	}
    }

    // look up the id for the named path
    synchronized long Lookup(String path) {
	Object o = nameToNumber.get(path);
	if ((o != null) && (o instanceof Long)) {
	    Long lval = (Long) o;
	    return lval.longValue();
	}
	return -1;
    }

    // add this name to the table and return the id.
    synchronized long Allocate(String name) {
	// see if it is already registered, if so return it
	long id = Lookup(name);
	if (id >= 0)
	    return id;

	// if not register and return new id, store data in the backup file
	id = sequence++;
	backup.StoreItem(name, id);
	return Add(name, id);
    }

    // add this name at this id, this is an internal method
    synchronized long Add(String name, long id) {
	// put this name in the hash table keyed to the handle value
	nameToNumber.put(name, new Long(id));
	// expand vector so it has items through id and put name at id offset
	while (numberToName.size() <= (int) id)
	    numberToName.addElement(null);
	numberToName.setElementAt(name, (int) id);
	
	// update sequence number to be bigger than id
	if (sequence <= id)
	    sequence = id + 1;
	return id;
    }

    void Report() {
	System.out.print("Hashtab has " + nameToNumber.size() + " elements\n");
	System.out.print("Vector has " + numberToName.size() + " elements\n");
	System.out.print("next sequence is " + sequence);
    }
};
