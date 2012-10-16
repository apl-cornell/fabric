package fabric.lang.security;

public interface Closure extends fabric.lang.Object {

  java.lang.Object invoke();

  fabric.lang.security.Principal jif$getfabric_lang_security_Closure_P();

  fabric.lang.security.Label jif$getfabric_lang_security_Closure_L();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.Closure {

    @Override
    native public java.lang.Object invoke();

    @Override
    native public fabric.lang.security.Principal jif$getfabric_lang_security_Closure_P();

    @Override
    native public fabric.lang.security.Label jif$getfabric_lang_security_Closure_L();

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
