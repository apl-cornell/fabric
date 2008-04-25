package fabnfs;

import java.util.Hashtable;
import java.util.Vector;


public class XDRPacketCache extends java.lang.Object  {
    Hashtable cache;
    int attempts;
    int hits;

    XDRPacketCache() {
        cache = new Hashtable();
        attempts = 0;
        hits = 0;
    }

    void add(XDRPacket item) {
        Integer key = new Integer(item.Data().length);
        Vector bucket = (Vector) cache.get(key);
        if (bucket == null) {
            bucket = new Vector();
            cache.put(key, bucket);
        }
        bucket.add(item);
    }

    XDRPacket find(int desiredLen) {
        attempts++;
        
        Integer key = new Integer(desiredLen);
        Vector bucket = (Vector) cache.get(key);
        XDRPacket result = null;
        
        // make sure the packet is reinitialized
        if (bucket != null && bucket.size() > 0) {
            hits++;
            // get the last item out of the bucket
            synchronized(bucket) {
                int idx = bucket.size() - 1;
                result = (XDRPacket) bucket.get(bucket.size() - 1);
                bucket.removeElementAt(idx);
            }
            result.Reset();
        }
        else {
            result = new XDRPacket(desiredLen);
        }

        // print performance information
        if (((attempts % 100) == 0) && (attempts != 0)) {
            System.out.println("XDRPacketCache: " + attempts + " attempts with "
                               + hits + " hits for hit rate of: "
                               + ((double) hits / (double) attempts));
        }
        return result;
    }
}
