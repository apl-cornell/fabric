package fabric.dissemination;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.ObjectGroup;

/**
 * A glob is a serialized object and a set of related objects.
 */
public class Glob {
  /**
   * The head object's version number.
   */
  private final int version;
  
  // XXX This needs to be replaced with an encrypted and signed thing.
  private final ObjectGroup group;
  
  private transient int level;
  private transient int frequency;
  private transient int popularity;
  
  private transient boolean home;
  
  public Glob(ObjectGroup group) {
    this.version = group.obj().getVersion();
    this.group = group;
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
  
  /**
   * Whether this Glob is older than the given Glob.
   */
  public boolean isOlderThan(Glob glob) {
    return version < glob.version;
  }
  
  /** Serializer. */
  public void write(DataOutput out) throws IOException {
    out.writeInt(version);
    group.write(out);
  }
  
  /** Deserializer. */
  public Glob(DataInput in) throws IOException {
    this.version = in.readInt();
    this.group = new ObjectGroup(in);
  }

  public ObjectGroup decrypt() {
    return group;
  }

}
