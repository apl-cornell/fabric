package fabric.lang;

import fabric.common.VersionWarranty;

public interface FClass extends fabric.lang.Object {

  public fabric.lang.Object get$staticInstance();

  public fabric.lang.Object set$staticInstance(fabric.lang.Object val);

  public fabric.lang.Codebase get$codebase();

  public fabric.lang.Codebase set$codebase(fabric.lang.Codebase val);

  public java.lang.String get$name();

  public java.lang.String set$name(java.lang.String val);

  public java.lang.String get$source();

  public java.lang.String set$source(java.lang.String val);

  public fabric.lang.arrays.byteArray get$bytecode();

  public fabric.lang.arrays.byteArray set$bytecode(
      fabric.lang.arrays.byteArray val);

  public fabric.lang.FClass fabric$lang$FClass$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy,
      fabric.lang.Codebase codebase, java.lang.String name,
      java.lang.String source, fabric.lang.arrays.byteArray bytecode);

  public fabric.lang.Codebase getCodebase();

  public java.lang.String getName();

  public java.lang.String getSource();

  public fabric.lang.arrays.byteArray getBytecode();

  @Override
  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.FClass {
    @Override
    native public fabric.lang.Object get$staticInstance();

    @Override
    native public fabric.lang.Object set$staticInstance(fabric.lang.Object val);

    @Override
    native public fabric.lang.Codebase get$codebase();

    @Override
    native public fabric.lang.Codebase set$codebase(fabric.lang.Codebase val);

    @Override
    native public java.lang.String get$name();

    @Override
    native public java.lang.String set$name(java.lang.String val);

    @Override
    native public java.lang.String get$source();

    @Override
    native public java.lang.String set$source(java.lang.String val);

    @Override
    native public fabric.lang.arrays.byteArray get$bytecode();

    @Override
    native public fabric.lang.arrays.byteArray set$bytecode(
        fabric.lang.arrays.byteArray val);

    @Override
    native public fabric.lang.FClass fabric$lang$FClass$(
        fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
        fabric.lang.Codebase arg3, java.lang.String arg4,
        java.lang.String arg5, fabric.lang.arrays.byteArray arg6);

    @Override
    native public fabric.lang.Codebase getCodebase();

    @Override
    native public java.lang.String getName();

    @Override
    native public java.lang.String getSource();

    @Override
    native public fabric.lang.arrays.byteArray getBytecode();

    @Override
    native public fabric.lang.Object $initLabels();

    public _Proxy(FClass._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.lang.FClass {
    @Override
    native public fabric.lang.Object get$staticInstance();

    @Override
    native public fabric.lang.Object set$staticInstance(fabric.lang.Object val);

    @Override
    native public fabric.lang.Codebase get$codebase();

    @Override
    native public fabric.lang.Codebase set$codebase(fabric.lang.Codebase val);

    @Override
    native public java.lang.String get$name();

    @Override
    native public java.lang.String set$name(java.lang.String val);

    @Override
    native public java.lang.String get$source();

    @Override
    native public java.lang.String set$source(java.lang.String val);

    @Override
    native public fabric.lang.arrays.byteArray get$bytecode();

    @Override
    native public fabric.lang.arrays.byteArray set$bytecode(
        fabric.lang.arrays.byteArray val);

    @Override
    native public fabric.lang.FClass fabric$lang$FClass$(
        fabric.lang.security.Label updateLabel,
        fabric.lang.security.ConfPolicy accessPolicy,
        fabric.lang.Codebase codebase, java.lang.String name,
        java.lang.String source, fabric.lang.arrays.byteArray bytecode);

    @Override
    native public fabric.lang.Codebase getCodebase();

    @Override
    native public java.lang.String getName();

    @Override
    native public java.lang.String getSource();

    @Override
    native public fabric.lang.arrays.byteArray getBytecode();

    @Override
    native public fabric.lang.Object $initLabels();

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    @Override
    native protected fabric.lang.Object._Proxy $makeProxy();

    @Override
    native public void $serialize(java.io.ObjectOutput out,
        java.util.List refTypes, java.util.List intraStoreRefs,
        java.util.List interStoreRefs) throws java.io.IOException;

    public _Impl(fabric.worker.Store store, long onum, int version,
        VersionWarranty warranty, long label, long accessLabel,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, warranty, label, accessLabel, in, refTypes,
          intraStoreRefs, interStoreRefs);
    }

    @Override
    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.FClass._Static {

      public _Proxy(fabric.lang.FClass._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.FClass._Static {

      public _Impl(fabric.worker.Store store)
          throws fabric.net.UnreachableNodeException {
        super(store);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      native private void $init();
    }

  }

}
