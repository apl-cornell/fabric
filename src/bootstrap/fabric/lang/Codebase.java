package fabric.lang;

public interface Codebase extends fabric.lang.Object {

  public fabric.util.Map get$classes();

  public fabric.util.Map set$classes(fabric.util.Map val);

  public fabric.util.Map get$codebases();

  public fabric.util.Map set$codebases(fabric.util.Map val);

  public fabric.lang.FClass resolveClassName(java.lang.String name);

  public fabric.lang.Codebase resolveCodebaseName(java.lang.String name);

  public void addCodebaseName(java.lang.String name,
      fabric.lang.Codebase codebase);

  public fabric.lang.Codebase fabric$lang$Codebase$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy);

  public fabric.lang.Codebase fabric$lang$Codebase$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, fabric.util.Map classes);

  public void insertClass(java.lang.String name, fabric.lang.FClass fcls);

  public fabric.util.Map getClasses();

  @Override
  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.Codebase {

    @Override
    native public fabric.util.Map get$classes();

    @Override
    native public fabric.util.Map set$classes(fabric.util.Map val);

    @Override
    native public fabric.util.Map get$codebases();

    @Override
    native public fabric.util.Map set$codebases(fabric.util.Map val);

    @Override
    native public fabric.lang.FClass resolveClassName(java.lang.String arg1);

    @Override
    native public fabric.lang.Codebase resolveCodebaseName(java.lang.String arg1);

    @Override
    native public void addCodebaseName(java.lang.String arg1,
        fabric.lang.Codebase arg2);

    @Override
    native public fabric.lang.Codebase fabric$lang$Codebase$(
        fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2);

    @Override
    native public fabric.lang.Codebase fabric$lang$Codebase$(
        fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
        fabric.util.Map arg3);

    @Override
    native public void insertClass(java.lang.String arg1,
        fabric.lang.FClass arg2);

    @Override
    native public fabric.util.Map getClasses();

    @Override
    native public fabric.lang.Object $initLabels();

    public _Proxy(Codebase._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.lang.Codebase {

    @Override
    native public fabric.util.Map get$classes();

    @Override
    native public fabric.util.Map set$classes(fabric.util.Map val);

    @Override
    native public fabric.util.Map get$codebases();

    @Override
    native public fabric.util.Map set$codebases(fabric.util.Map val);

    @Override
    native public fabric.lang.FClass resolveClassName(java.lang.String name);

    @Override
    native public fabric.lang.Codebase resolveCodebaseName(java.lang.String name);

    @Override
    native public void addCodebaseName(java.lang.String name,
        fabric.lang.Codebase codebase);

    @Override
    native public fabric.lang.Codebase fabric$lang$Codebase$(
        fabric.lang.security.Label updateLabel,
        fabric.lang.security.ConfPolicy accessPolicy);

    @Override
    native public fabric.lang.Codebase fabric$lang$Codebase$(
        fabric.lang.security.Label updateLabel,
        fabric.lang.security.ConfPolicy accessPolicy, fabric.util.Map classes);

    @Override
    native public void insertClass(java.lang.String name,
        fabric.lang.FClass fcls);

    @Override
    native public fabric.util.Map getClasses();

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
        long expiry, long label, long accessLabel, java.io.ObjectInput in,
        java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
	java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, expiry, label, accessLabel, in, refTypes,
          intraStoreRefs, interStoreRefs);
    }

    @Override
    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.Codebase._Static {

      public _Proxy(fabric.lang.Codebase._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.Codebase._Static {

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
