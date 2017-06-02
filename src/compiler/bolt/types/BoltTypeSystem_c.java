package bolt.types;

import java.util.HashMap;
import java.util.Map;

import bolt.ast.BoltLang_c;
import polyglot.ext.jl7.types.JL7TypeSystem_c;
import polyglot.types.Context;
import polyglot.types.Flags;
import polyglot.types.Type;
import polyglot.util.Position;

public class BoltTypeSystem_c extends JL7TypeSystem_c
    implements BoltTypeSystem {

  @Override
  public Context createContext() {
    return new BoltContext_c(BoltLang_c.INSTANCE, this);
  }

  @Override
  public FabricArrayType fabricArrayOf(Type type) {
    assert_(type);
    return fabricArrayOf(type.position(), type);
  }

  @Override
  public FabricArrayType fabricArrayOf(Position pos, Type type) {
    assert_(type);
    return fabricArrayType(pos, type);
  }

  private Map<Type, FabricArrayType> fabricArrayTypeCache = new HashMap<>();

  protected FabricArrayType fabricArrayType(Position pos, Type type) {
    FabricArrayType t = fabricArrayTypeCache.get(type);
    if (t == null) {
      t = createFabricArrayType(pos, type);
      fabricArrayTypeCache.put(type, t);
    }

    return t;
  }

  /**
   * Factory method for FabricArrayTypes.
   */
  protected FabricArrayType createFabricArrayType(Position pos, Type type) {
    return new FabricArrayType_c(this, pos, type);
  }

  @Override
  protected JavaArrayType createArrayType(Position pos, Type type,
      boolean isVarArgs) {
    return new JavaArrayType_c(this, pos, type, isVarArgs);
  }

  @Override
  public Flags legalInitializerFlags() {
    // Initializers can be declared final.
    return super.legalInitializerFlags().Final();
  }

}
