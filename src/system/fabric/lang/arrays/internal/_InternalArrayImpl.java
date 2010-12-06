package fabric.lang.arrays.internal;

/**
 * A marker interface for internal array types. This allows
 * fabric.lang.Object.clone() to clone array data in a generic manner.
 */
public interface _InternalArrayImpl {
  void cloneValues();
}
