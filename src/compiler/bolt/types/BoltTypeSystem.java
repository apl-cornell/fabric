package bolt.types;

import polyglot.ext.jl7.types.JL7TypeSystem;
import polyglot.types.Type;
import polyglot.util.Position;

public interface BoltTypeSystem extends JL7TypeSystem {

  FabricArrayType fabricArrayOf(Type type);

  FabricArrayType fabricArrayOf(Position pos, Type type);

}
