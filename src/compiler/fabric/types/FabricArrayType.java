package fabric.types;

import jif.types.ConstArrayType;

/**
 * Unlike FabIL, we follow the jif const array design here. All ArrayTypes
 * created by Jif's Type System are Jif ConstArrayTypes. Similarly, the
 * FabricTypeSystem creates only FabricArrayTypes. In addition to the flags to
 * determine whether an ArrayType is const or not (inherited from
 * ConstArrayType), we add a flag to determine whether the ArrayType is native.
 *
 * @author mdgeorge
 */
public interface FabricArrayType extends ConstArrayType, FabricReferenceType {
  /** Type is a native (java) array type */
  boolean isNative();
}
