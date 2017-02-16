package bolt.types;

import polyglot.ext.jl7.types.JL7TypeSystem;
import polyglot.types.ArrayType;
import polyglot.types.Type;

public interface BoltTypeSystem extends JL7TypeSystem {

  ArrayType fabricArrayOf(Type type);

}
