package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.metrics.ImmutableObserverSet;

/**
 * Allows for a separate object to hold the observer set, if we want.
 */
public interface ObserverSetBox extends fabric.lang.Object {
    public fabric.worker.metrics.ImmutableObserverSet get$value();
    
    public fabric.worker.metrics.ImmutableObserverSet set$value(
      fabric.worker.metrics.ImmutableObserverSet val);
    
    public fabric.metrics.util.ObserverSetBox
      fabric$metrics$util$ObserverSetBox$();
    
    public void addObserver(fabric.metrics.util.Observer o);
    
    public void removeObserver(fabric.metrics.util.Observer o);
    
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    public boolean isObserved();
    
    public fabric.worker.metrics.ImmutableObserverSet getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.ObserverSetBox {
        public fabric.worker.metrics.ImmutableObserverSet get$value() {
            return ((fabric.metrics.util.ObserverSetBox._Impl) fetch()).
              get$value();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet set$value(
          fabric.worker.metrics.ImmutableObserverSet val) {
            return ((fabric.metrics.util.ObserverSetBox._Impl) fetch()).
              set$value(val);
        }
        
        public fabric.metrics.util.ObserverSetBox
          fabric$metrics$util$ObserverSetBox$() {
            return ((fabric.metrics.util.ObserverSetBox) fetch()).
              fabric$metrics$util$ObserverSetBox$();
        }
        
        public void addObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.ObserverSetBox) fetch()).addObserver(arg1);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.ObserverSetBox) fetch()).removeObserver(arg1);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.ObserverSetBox) fetch()).observedBy(
                                                                    arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.util.ObserverSetBox) fetch()).isObserved();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
            return ((fabric.metrics.util.ObserverSetBox) fetch()).getObservers(
                                                                    );
        }
        
        public _Proxy(ObserverSetBox._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.ObserverSetBox {
        public fabric.worker.metrics.ImmutableObserverSet get$value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.value;
        }
        
        public fabric.worker.metrics.ImmutableObserverSet set$value(
          fabric.worker.metrics.ImmutableObserverSet val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.worker.metrics.ImmutableObserverSet value;
        
        public fabric.metrics.util.ObserverSetBox
          fabric$metrics$util$ObserverSetBox$() {
            fabric$lang$Object$();
            this.set$value(
                   fabric.worker.metrics.ImmutableObserverSet.emptySet());
            return (fabric.metrics.util.ObserverSetBox) this.$getProxy();
        }
        
        public void addObserver(fabric.metrics.util.Observer o) {
            if (!this.get$value().contains(o))
                this.set$value(this.get$value().add(o));
        }
        
        public void removeObserver(fabric.metrics.util.Observer o) {
            if (this.get$value().contains(o))
                this.set$value(this.get$value().remove(o));
        }
        
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$value().contains(o);
        }
        
        public boolean isObserved() { return !this.get$value().isEmpty(); }
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
            return this.get$value();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.ObserverSetBox._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeInline(out, this.value);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.value = (fabric.worker.metrics.ImmutableObserverSet)
                           in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.ObserverSetBox._Impl src =
              (fabric.metrics.util.ObserverSetBox._Impl) other;
            this.value = src.value;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.ObserverSetBox._Static {
            public _Proxy(fabric.metrics.util.ObserverSetBox._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.ObserverSetBox._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  ObserverSetBox.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.ObserverSetBox._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.ObserverSetBox._Static._Impl.class);
                $instance = (fabric.metrics.util.ObserverSetBox._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.ObserverSetBox._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.ObserverSetBox._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -69, -68, -10, -97,
    -14, -77, 31, -33, 28, 24, -54, 11, 36, 66, -81, -102, 44, 103, 21, -21, -7,
    103, 22, 42, 79, 111, 29, 108, -72, 59, -18, 90 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525209021000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUYbWwcR3XubJ8/EztO7KSu7Tj2NRDXuVMKQkpNIPWpaY5esYmdCBw1Zm537rz13u52ds4+t7gqoCpRhSxK3dCUNlVRUEsxjQSqQCpBFeIjoQgJhPj4Ac0PqgaFBBVEAQko783Mfa0vbv0DSzNvPPPem/c9b2/1GmnwORnM0LRlx8Six/zYIZpOpiYo95mZsKnvT8HujNFanzx95XmzP0zCKdJmUMd1LIPaM44vyObUfXSexh0m4kePJEePk2YDCQ9Tf1aQ8PGxAicDnmsvZm1X6EvW8H/i1vjKl090fKuOtE+TdsuZFFRYRsJ1BCuIadKWY7k04/4dpsnMabLFYcycZNyitvUAILrONOn0raxDRZ4z/wjzXXseETv9vMe4vLO4ieK7IDbPG8LlIH6HEj8vLDuesnwxmiKRjMVs07+fPETqU6QhY9MsIHanilrEJcf4IdwH9BYLxOQZarAiSf2c5ZiC7AxSlDSO3g0IQNqYY2LWLV1V71DYIJ1KJJs62fik4JaTBdQGNw+3CNJzQ6aA1ORRY45m2YwgO4J4E+oIsJqlWZBEkK4gmuQEPusJ+KzCW9c+/uHlB53DTpiEQGaTGTbK3wRE/QGiIyzDOHMMpgjbhlOnafeFU2FCALkrgKxwvvOZtw6O9L96UeHcXANnPH0fM8SMcS69+Re9iT3761CMJs/1LQyFKs2lVyf0yWjBg2jvLnHEw1jx8NUjP/7Uwy+yq2HSkiQRw7XzOYiqLYab8yyb8buYwzgVzEySZuaYCXmeJI2wTlkOU7vjmYzPRJLU23Ir4sr/wUQZYIEmaoS15WTc4tqjYlauCx4hpBEGCcHYS0jdEsBmGC8I8on4rJtj8bSdZwsQ3nEYjHJjNg55yy0j7nMjzvOOsABJb0EUAfCV/uNpn/F5xieZGHMLMRDG+38wLaAmHQuhEBh5p+GaLE198JiOnrEJGxLksGubjM8Y9vKFJNl64YyMoGaMeh8iV9ooBF7vDdaLStqV/Nidb70085qKPqTVJhSykoGIMS2p8nC1pCBcG2ZXDOpVDOrVaqgQS5xNfkMGUcSX2Vbi1wb8bvdsKjIuzxVIKCSV2ybpJW/w/RzUFCgbbXsm7/3Yp08N1kHYegv16ElAjQaTqFx6krCikBkzRvvJK2+fP73kltNJkOiaLF9LiVk6GLQUdw1mQhUssx8eoC/PXFiKhrHCNEPxExTCEypJf/COqmwdLVY+tEZDirSiDaiNR8Vy1SJmubtQ3pERsBmnThUMaKyAgLJoHpj0nvntz//0AfmcFOtre0UhBl+NVuQ0MmuX2bulbPspzhjg/f7JicefuHbyuDQ8YAzVujCKcwJymUISu/yRi/f/7vU/nPtVuOwsQSJePm1bRkHqsuUd+AvB+C8OTEzcQAjlOaGLwkCpKnh48+6ybFAfbKhRILofPerkXNPKWDRtM4yUf7ffsu/lPy93KHfbsKOMx8nIuzMo7980Rh5+7cQ/+iWbkIHvU9l+ZTRV9LaWOd/BOV1EOQqf/WXfmZ/QZyDyoWT51gNMViEi7UGkA2+Tttgr532Bsw/iNKis1VsK+OADcAhf0nIsTsdXn+5JfOSqyvtSLCKPXTXy/hitSJPbXsz9PTwY+VGYNE6TDvmIU0cco1DBIAym4Rn2E3ozRTZVnVc/qer9GC3lWm8wDyquDWZBud7AGrFx3aICXwWOrtskCqMVxh81vISnWz2ctxVCRC5ulyRDct6N0x5lSFwOF0r8wsivSfO5qOEPKvgJ0jCPekqKLkGGdR1ccPkc46VymMzl8gKjqKIkSpKbgqVOZq8SYAinAyVZ5F9EK/m8hs9VyFIREASrbd+N+gjZA5373MpZc/xr+9Rr31n9Nt/p5HPf/PV/fhZ78vKlGtW+WbjeXpvNM7vizghcuWtNQ3uPbLPKsXT5at/+xNwbWXXtzoCIQeyv37N66a7dxpfCpK4UNGt6u2qi0epQaeEMWlNnqipgBqoD5gCMTeD8IQXJXyoDRpXTGv4oxQaSXNfwStAftVN4ap2zYziNCzKkQimqYyiKjolWP6nRsnCpapVugbENVPq+hl/dmEpI8pyGX7mxSiFdfXTw967XBCBOj7z63nWUpzh9UpBWappFyhrFbYJbOXif5nV3y06tPPpObHlFhar6BBha04VX0qjPAHnlJpxuxYTZtd4tkuLQm+eXXnlh6WRYi3tYkPp51zJrueD9MHrAph/ScPvGXIAk3Rp2vKsL8N8TkquzjnUlBws+HDnLufOs0jWslgrvgzEA95/X8LGNqYAkX9Tw0Q2osLCOCos4QQvV4irhzbFFiWdoLyLICNKYdl2bUaeWVn0qP+rCCoavb0wrJLmm4ZvvLd0/v87ZIzg9JPAR1f4wcefBWpKPwBgGyQ0ND25MciT5qIb735vkX1jnbBmnk/CAZZko5SruHSxAiFVXKmyFbq7xaaI/mo3ED9m5N+4e6brBZ8mONT9jaLqXzrY3bT979DeyxS59EDdDB5vJ23Zly1CxjnicZSypQ7NqIDwJHhdka40SBjmOQKr2mMI8DQpWYwr5iwKuKvHOQHOr8PC/p6S5e8pTsXB2al7YJMVUk1S7O5BMe/Icf91Z/dv2f0aapi7LVhqz7JXvvf3sX7+98/XeHT9tjY6df2ok23X1X9nu4XG3z/7u6PXp/wEE+VPOdRIAAA==";
}
