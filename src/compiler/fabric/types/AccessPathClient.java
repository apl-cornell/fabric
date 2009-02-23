package fabric.types;

import polyglot.types.Type;
import polyglot.util.Position;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathRoot;

/**
 * <code>AccessPathClient</code> represents the special access path 
 * <code>client</code> that represents the local client.
 * 
 * @author qixin
 */
public class AccessPathClient extends AccessPathRoot {
  protected FabricTypeSystem ts;
  
  protected AccessPathClient(Position pos, FabricTypeSystem ts) {
    super(pos);
    this.ts = ts;
  }
  
  @Override
  public boolean equals(Object o) {
    return o instanceof AccessPathClient;
  }

  @Override
  public int hashCode() {
    return 19810614;
  }

  @Override
  public boolean isCanonical() {
    return true;
  }

  @Override
  public boolean isNeverNull() {
    return true;
  }

  @Override
  public AccessPath subst(AccessPathRoot r, AccessPath e) {
    return this;
  }

  @Override
  public Type type() {
    return ts.Client();
  }
}
