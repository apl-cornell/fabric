package fabric.dissemination;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.SerializedObject;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;

/**
 * A glob is a serialized object and a set of related objects.
 */
public class Glob {
  
  private final SerializedObject obj;
  private final LongKeyMap<SerializedObject> related;
  
  private transient int level;
  private transient int frequency;
  private transient int popularity;
  
  private transient boolean home;
  
  public Glob(SerializedObject obj, LongKeyMap<SerializedObject> related) {
    this(obj, related, false);
  }

  public Glob(SerializedObject obj, LongKeyMap<SerializedObject> related, 
      boolean home) {
    this.obj = obj;
    this.related = related;
    this.home = home;
  }
  
  /**
   * The main object of this glob.
   */
  public SerializedObject obj() {
    return obj;
  }
  
  /**
   * The related objects as a map from their onums to the objects.
   */
  public LongKeyMap<SerializedObject> related() {
    return related;
  }
  
  /** The dissemination level of the glob. 0 is replicated to all nodes. */
  public int level() {
    return level;
  }
  
  /** Sets the level. */
  public void level(int level) {
    this.level = level;
  }
  
  /** How many times the object has been accessed since last aggregation. */
  public int frequency() {
    return frequency;
  }
  
  /** Sets the frequency. */
  public void frequency(int frequency) {
    this.frequency = frequency;
  }
  
  /** Increments frequency by 1. */
  public void touch() {
    this.frequency++;
  }
  
  /** The popularity of the glob. An exponential-decayed valued. */
  public int popularity() {
    return popularity;
  }
  
  /** Sets the popularity. */
  public void popularity(int popularity) {
    this.popularity = popularity;
  }
  
  /** Whether this is the home node for this glob. */
  public boolean home() {
    return home;
  }
  
  /** Serializer. */
  public void write(DataOutput out) throws IOException {
    obj.write(out);
    out.writeInt(related.size());
    
    for (LongKeyMap.Entry<SerializedObject> e : related.entrySet()) {
      out.writeLong(e.getKey());
      e.getValue().write(out);
    }
  }
  
  /** Deserializer. */
  public Glob(DataInput in) throws IOException {
    obj = new SerializedObject(in);
    
    int n = in.readInt();
    related = new LongKeyHashMap<SerializedObject>(n);
    
    for (int i = 0; i < n; i++) {
      long onum = in.readLong();
      SerializedObject o = new SerializedObject(in);
      related.put(onum, o);
    }
  }

}
