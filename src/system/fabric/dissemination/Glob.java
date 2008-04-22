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
  
  public int level() {
    return level;
  }
  
  public void level(int level) {
    this.level = level;
  }
  
  public int frequency() {
    return frequency;
  }
  
  public void frequency(int frequency) {
    this.frequency = frequency;
  }
  
  public void touch() {
    this.frequency++;
  }
  
  public int popularity() {
    return popularity;
  }
  
  public void popularity(int popularity) {
    this.popularity = popularity;
  }
  
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
