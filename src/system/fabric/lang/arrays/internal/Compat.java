package fabric.lang.arrays.internal;

import fabric.worker.Store;
import fabric.lang.arrays.*;
import fabric.lang.Object;
import fabric.lang.security.Label;

/**
 * A utility class for converting Java arrays into Fabric arrays.
 */
public class Compat {
  public static booleanArray convert(Store store, Label label, boolean[] array) {
    booleanArray result = new booleanArray._Impl(store, label, array.length);
    for (int i = 0; i < array.length; i++)
      result.set(i, array[i]);
    return (booleanArray) result.$getProxy();
  }

  public static byteArray convert(Store store, Label label, byte[] array) {
    byteArray result = new byteArray._Impl(store, label, array.length);
    for (int i = 0; i < array.length; i++)
      result.set(i, array[i]);
    return (byteArray) result.$getProxy();
  }

  public static charArray convert(Store store, Label label, char[] array) {
    charArray result = new charArray._Impl(store, label, array.length);
    for (int i = 0; i < array.length; i++)
      result.set(i, array[i]);
    return (charArray) result.$getProxy();
  }

  public static doubleArray convert(Store store, Label label, double[] array) {
    doubleArray result = new doubleArray._Impl(store, label, array.length);
    for (int i = 0; i < array.length; i++)
      result.set(i, array[i]);
    return (doubleArray) result.$getProxy();
  }

  public static floatArray convert(Store store, Label label, float[] array) {
    floatArray result = new floatArray._Impl(store, label, array.length);
    for (int i = 0; i < array.length; i++)
      result.set(i, array[i]);
    return (floatArray) result.$getProxy();
  }

  public static intArray convert(Store store, Label label, int[] array) {
    intArray result = new intArray._Impl(store, label, array.length);
    for (int i = 0; i < array.length; i++)
      result.set(i, array[i]);
    return (intArray) result.$getProxy();
  }

  public static longArray convert(Store store, Label label, long[] array) {
    longArray result = new longArray._Impl(store, label, array.length);
    for (int i = 0; i < array.length; i++)
      result.set(i, array[i]);
    return (longArray) result.$getProxy();
  }

  public static shortArray convert(Store store, Label label, short[] array) {
    shortArray result = new shortArray._Impl(store, label, array.length);
    for (int i = 0; i < array.length; i++)
      result.set(i, array[i]);
    return (shortArray) result.$getProxy();
  }

  public static ObjectArray convert(Store store, Label label, Object[] array) {
    ObjectArray result =
        new ObjectArray._Impl(store, label, array.getClass().getComponentType(),
            array.length);
    for (int i = 0; i < array.length; i++)
      result.set(i, array[i]);
    return (ObjectArray) result.$getProxy();
  }
}
