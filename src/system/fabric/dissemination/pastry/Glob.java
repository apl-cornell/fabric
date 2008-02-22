package fabric.dissemination.pastry;

import java.util.Map;

import fabric.core.SerializedObject;

/**
 * A glob is a serialized object and a set of related objects.
 */
public class Glob {
  
  private final SerializedObject obj;
  private final Map<Long, SerializedObject> related;
  
  private transient int level;
  private transient int frequency;
  private transient int popularity;
  
  public Glob(SerializedObject obj, Map<Long, SerializedObject> related) {
    this.obj = obj;
    this.related = related;
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
  public Map<Long, SerializedObject> related() {
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
  
  public int popularity() {
    return popularity;
  }
  
  public void popularity(int popularity) {
    this.popularity = popularity;
  }

}
