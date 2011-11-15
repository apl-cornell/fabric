package jif.types;

import polyglot.types.ArrayType;

/**
 * A <code>ConstArrayType</code> represents an array of base types,
 * whose elements cannot change after initialization.
 */
public interface ConstArrayType extends ArrayType
{
    /**
     * The array type is a const array (or castable to one).
     */
    boolean isConst();

    /**
     * The array type is a non-const array (or castable to one).
     */
    boolean isNonConst();
}
