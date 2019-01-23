package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import java.util.SortedSet;

/**
 * Utility to make treaties and observers exist outside of metrics for the
 * purposes of managing contention and conflicts between transactions.
 *
 * This acts as a proxy for the Metric's treaties and observers.
 */
public interface TreatiesBox
  extends fabric.metrics.util.Observer, fabric.metrics.util.AbstractSubject {
    public fabric.metrics.Metric get$owner();

    public fabric.metrics.Metric set$owner(fabric.metrics.Metric val);

    public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
      fabric.metrics.Metric m);

    public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
      boolean includesObserver, java.util.SortedSet treaties);

    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();

    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.util.TreatiesBox {
        public fabric.metrics.Metric get$owner() {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).get$owner(
                                                                       );
        }

        public fabric.metrics.Metric set$owner(fabric.metrics.Metric val) {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).set$owner(
                                                                       val);
        }

        public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
          fabric.metrics.Metric arg1) {
            return ((fabric.metrics.util.TreatiesBox) fetch()).
              fabric$metrics$util$TreatiesBox$(arg1);
        }

        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean arg1, java.util.SortedSet arg2) {
            return ((fabric.metrics.util.TreatiesBox) fetch()).handleUpdates(
                                                                 arg1, arg2);
        }

        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.util.TreatiesBox) fetch()).getLeafSubjects(
                                                                 );
        }

        public _Proxy(TreatiesBox._Impl impl) { super(impl); }

        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }

    public static class _Impl extends fabric.metrics.util.AbstractSubject._Impl
      implements fabric.metrics.util.TreatiesBox {
        public fabric.metrics.Metric get$owner() { return this.owner; }

        public fabric.metrics.Metric set$owner(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.owner = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }

        public fabric.metrics.Metric owner;

        public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
          fabric.metrics.Metric m) {
            this.set$owner(m);
            fabric$metrics$util$AbstractSubject$();
            return (fabric.metrics.util.TreatiesBox) this.$getProxy();
        }

        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean includesObserver, java.util.SortedSet treaties) {
            return this.get$owner().handleUpdates(includesObserver, treaties);
        }

        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return this.get$owner().getLeafSubjects();
        }

        public _Impl(fabric.worker.Store $location) { super($location); }

        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.TreatiesBox._Proxy(this);
        }

        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.owner, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }

        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,

                     fabric.worker.metrics.treaties.TreatySet treaties, long expiry,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, treaties, expiry,
                  labelStore, labelOnum, accessPolicyStore, accessPolicyOnum,
                  in, refTypes, intraStoreRefs, interStoreRefs);
            this.owner = (fabric.metrics.Metric)
                           $readRef(fabric.metrics.Metric._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
        }

        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.TreatiesBox._Impl src =
              (fabric.metrics.util.TreatiesBox._Impl) other;
            this.owner = src.owner;
        }
    }

    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.TreatiesBox._Static {
            public _Proxy(fabric.metrics.util.TreatiesBox._Static._Impl impl) {
                super(impl);
            }

            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }

            public static final fabric.metrics.util.TreatiesBox._Static
              $instance;

            static {
                fabric.
                  metrics.
                  util.
                  TreatiesBox.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.TreatiesBox._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.TreatiesBox._Static._Impl.class);
                $instance = (fabric.metrics.util.TreatiesBox._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }

        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.TreatiesBox._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }

            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,

                         fabric.worker.metrics.treaties.TreatySet treaties, long expiry,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, treaties, expiry,
                      labelStore, labelOnum, accessPolicyStore,
                      accessPolicyOnum, in, refTypes, intraStoreRefs,
                      interStoreRefs);
            }

            public _Impl(fabric.worker.Store store) { super(store); }

            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.TreatiesBox._Static._Proxy(this);
            }

            private void $init() {  }
        }

    }

    public static final byte[] $classHash = new byte[] { 78, 77, 58, -100, 50,
    -25, -105, -46, 61, 51, -119, -5, 75, 31, 55, -100, 21, -24, -91, -97, -17,
    111, -105, 42, -66, 65, -30, -31, 65, 79, -61, -15 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1537039040000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
