package generated;

import diaspora.client.DObject;
import diaspora.client.Core;
import diaspora.common.Policy;

public class DInt extends DObject {
  private int[] value;

  public int get_value() {
    return this.value[0];
  }

  public void set_value(int v) {
    this.value[0] = v;
  }

  public DInt(Core core, Policy p) {
    super(core, p);
    value = new int[100];
  }

  public DInt(Core core) {
    this(core, null);
  }

  public void copyStateFrom(DObject obj) {
    super.copyStateFrom(obj);
    
    if (!(obj instanceof DInt))
      throw new InternalError("Expected DInt, got something else");
    
    DInt other = (DInt) obj;
    this.value = other.value;
  }
}
