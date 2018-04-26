package fabric.lang.security;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.List;

import fabric.common.Crypto;
import fabric.common.RefTypeEnum;
import fabric.common.util.Pair;
import fabric.lang.Object;
import fabric.net.UnreachableNodeException;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.transaction.TransactionManager;

/**
 * This is implemented in Java so that the constructor can create a key pair for
 * the principal.
 */
public interface Principal extends fabric.lang.Object {
  /**
   * Jif initializer.
   */
  Principal fabric$lang$security$Principal$();

  String name();

  boolean delegatesTo(final Principal p);

  boolean equals(final Principal p);

  boolean isAuthorized(final java.lang.Object authPrf, final Closure closure,
      final Label lb, final boolean executeNow);

  ActsForProof findProofUpto(final Store store, final Principal p,
      final java.lang.Object searchState);

  ActsForProof findProofDownto(final Store store, final Principal q,
      final java.lang.Object searchState);

  PublicKey getPublicKey();

  PrivateKeyObject getPrivateKeyObject();

  public static class _Proxy extends fabric.lang.Object._Proxy
      implements Principal {

    public _Proxy(Principal._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }

    @Override
    public Principal fabric$lang$security$Principal$() {
      return ((Principal) fetch()).fabric$lang$security$Principal$();
    }

    @Override
    public String name() {
      return ((Principal) fetch()).name();
    }

    @Override
    public boolean delegatesTo(Principal p) {
      return ((Principal) fetch()).delegatesTo(p);
    }

    @Override
    public boolean equals(Principal p) {
      return ((Principal) fetch()).equals(p);
    }

    @Override
    public boolean isAuthorized(java.lang.Object authPrf, Closure closure,
        Label lb, boolean executeNow) {
      return ((Principal) fetch()).isAuthorized(authPrf, closure, lb,
          executeNow);
    }

    @Override
    public ActsForProof findProofUpto(Store store, Principal p,
        java.lang.Object searchState) {
      return ((Principal) fetch()).findProofUpto(store, p, searchState);
    }

    @Override
    public ActsForProof findProofDownto(Store store, Principal q,
        java.lang.Object searchState) {
      return ((Principal) fetch()).findProofDownto(store, q, searchState);
    }

    @Override
    public PublicKey getPublicKey() {
      return ((Principal) fetch()).getPublicKey();
    }

    @Override
    public PrivateKeyObject getPrivateKeyObject() {
      return ((Principal) fetch()).getPrivateKeyObject();
    }

    public static Principal jif$cast$fabric_lang_security_Principal(Object o) {
      return Principal._Impl.jif$cast$fabric_lang_security_Principal(o);
    }
  }

  abstract public static class _Impl extends fabric.lang.Object._Impl
      implements Principal {

    private PublicKey publicKey;
    private PrivateKeyObject privateKeyObject;

    public _Impl(Store store) {
      super(store);
    }

    @Override
    public Object $initLabels() {
      Store store = this.$getStore();
      Principal._Proxy thisProxy = (Principal._Proxy) this.$getProxy();

      // Always ensure that the principal can modify its own object.
      // {this <- this}
      ConfPolicy bottomConf =
          Worker.getWorker().getLocalStore().getBottomConfidPolicy();
      IntegPolicy integ =
          LabelUtil._Impl.writerPolicy(store, thisProxy, thisProxy);
      Label thisIntegLabel = LabelUtil._Impl.toLabel(store, bottomConf, integ);

      this.set$$updateLabel(thisIntegLabel);
      this.set$$accessPolicy(bottomConf);

      return thisProxy;
    }

    @Override
    public Principal fabric$lang$security$Principal$() {
      fabric$lang$Object$();
      // Generate a new key pair for this principal.
      KeyPair keyPair = Crypto.genKeyPair();
      this.publicKey = keyPair.getPublic();

      this.privateKeyObject = new PrivateKeyObject._Impl($getStore())
          .fabric$lang$security$PrivateKeyObject$((Principal) $getProxy(),
              keyPair.getPrivate());

      return (Principal) this.$getProxy();
    }

    @Override
    abstract public String name();

    @Override
    abstract public boolean delegatesTo(final Principal p);

    @Override
    abstract public boolean equals(final Principal p);

    @Override
    abstract public boolean isAuthorized(final java.lang.Object authPrf,
        final Closure closure, final Label lb, final boolean executeNow);

    @Override
    abstract public ActsForProof findProofUpto(final Store store,
        final Principal p, final java.lang.Object searchState);

    @Override
    abstract public ActsForProof findProofDownto(final Store store,
        final Principal q, final java.lang.Object searchState);

    @Override
    protected fabric.lang.Object._Proxy $makeProxy() {
      return new Principal._Proxy(this);
    }

    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
    }

    public _Impl(Store store, long onum, int version, long expiry,
        ImmutableObserverSet observers, Store labelStore, long labelOnum,
        Store accessPolicyStore, long accessPolicyOnum, ObjectInput in,
        Iterator<RefTypeEnum> refTypes, Iterator<Long> intraStoreRefs,
        Iterator<Pair<String, Long>> interStoreRefs)
        throws java.io.IOException, ClassNotFoundException {
      super(store, onum, version, expiry, observers, labelStore, labelOnum,
          accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
          interStoreRefs);
    }

    @Override
    public final PublicKey getPublicKey() {
      TransactionManager.getInstance().registerRead(this);
      return publicKey;
    }

    @Override
    public final PrivateKeyObject getPrivateKeyObject() {
      TransactionManager.getInstance().registerRead(this);
      return privateKeyObject;
    }

    public static Principal jif$cast$fabric_lang_security_Principal(Object o) {
      //XXX: What is the right access label check??
      return (Principal) fabric.lang.Object._Proxy.$getProxy(o);
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements Principal._Static {

      public _Proxy(Principal._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(Store store, long onum) {
        super(store, onum);
      }

      final public static Principal._Static $instance;

      static {
        Principal._Static._Impl impl =
            (Principal._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(Principal._Static._Impl.class);
        $instance = (Principal._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.security.Principal._Static {

      public _Impl(Store store) throws UnreachableNodeException {
        super(store);
      }

      @Override
      protected fabric.lang.Object._Proxy $makeProxy() {
        return new fabric.lang.security.Principal._Static._Proxy(this);
      }

      private void $init() {
      }
    }
  }
}
