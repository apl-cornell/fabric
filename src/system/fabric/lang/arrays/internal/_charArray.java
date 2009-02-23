package fabric.lang.arrays.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.client.Core;
import fabric.client.transaction.TransactionManager;
import fabric.common.Pair;
import fabric.common.RefTypeEnum;
import fabric.lang.Object;
import jif.lang.Label;

public interface _charArray extends Object {
  int get$length();

  char set(int i, char value);

  char get(int i);

  public static class $Impl extends Object.$Impl implements _charArray {
    private char[] value;

    /**
     * Creates a new char array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public $Impl(Core core, Label label, int length) {
      this(core, label, new char[length]);
    }

    /**
     * Creates a new char array at the given Core using the given backing
     * array.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public $Impl(Core core, Label label, char[] value) {
      super(core, label);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    public $Impl(Core core, long onum, int version, long label,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      super(core, onum, version, label, in, refTypes, intracoreRefs);
      value = new char[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readChar();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.charArray#getLength()
     */
    public int get$length() {
      TransactionManager.getInstance().registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.charArray#get(int)
     */
    public char get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.charArray#set(int, char)
     */
    public char set(int i, char value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      char result = this.value[i] = value;
      if (transactionCreated) TransactionManager.getInstance().commitTransaction();
      return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$copyAppStateFrom(fabric.lang.Object.$Impl)
     */
    @Override
    public void $copyAppStateFrom(Object.$Impl other) {
      super.$copyAppStateFrom(other);
      _charArray.$Impl src = (_charArray.$Impl) other;
      value = new char[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected _charArray.$Proxy $makeProxy() {
      return new _charArray.$Proxy(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$serialize(java.io.ObjectOutput)
     */
    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intracoreRefs, List<Pair<String, Long>> intercoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intracoreRefs, intercoreRefs);
      out.writeInt(value.length);
      for (int i = 0; i < value.length; i++)
        out.writeChar(value[i]);
    }
  }

  public static class $Proxy extends Object.$Proxy implements _charArray {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(_charArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.charArray#getLength()
     */
    public int get$length() {
      return ((_charArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.charArray#get(int)
     */
    public char get(int i) {
      return ((_charArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.charArray#set(int, char)
     */
    public char set(int i, char value) {
      return ((_charArray) fetch()).set(i, value);
    }
  }
}
