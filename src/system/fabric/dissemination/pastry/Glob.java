package fabric.dissemination.pastry;

import java.util.Map;

import fabric.core.SerializedObject;

public class Glob {
  
  private final SerializedObject obj;
  private final Map<Long, SerializedObject> related;
  
  public Glob(SerializedObject obj, Map<Long, SerializedObject> related) {
    this.obj = obj;
    this.related = related;
  }
  
  public SerializedObject obj() {
    return obj;
  }
  
  public Map<Long, SerializedObject> related() {
    return related;
  }

}
