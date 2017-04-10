package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Arrays;
import fabric.util.TreeSet;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.DerivedMetricContract;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.worker.Store;

/**
 * General base class for {@link Metric}s built by computing over other
 * {@link Metrics}. Each {@link DerivedMetric} implementation is responsible for
 * defining how to
 * <ul>
 * <li>construct the {@link #value()}, {@link #velocity()}, and {@link #noise()}
 * from its terms</li>
 * <li>provide a {@link EnforcementPolicy} to enforce a
 * {@link DerivedMetricContract} on it given the {@link Bound}, typically using
 * a {@link WitnessPolicy} using {@link MetricContract}s on the terms it is
 * derived from.</li>
 * </ul>
 */
public interface DerivedMetric
  extends fabric.metrics.util.Observer, fabric.metrics.Metric {
    public double get$lastValue();
    
    public double set$lastValue(double val);
    
    public double postInc$lastValue();
    
    public double postDec$lastValue();
    
    public fabric.lang.arrays.ObjectArray get$terms();
    
    public fabric.lang.arrays.ObjectArray set$terms(fabric.lang.arrays.ObjectArray val);
    
    /**
   * @param s
   *        the {@link Store} this {@link DerivedMetric} will be stored on
   * @param terms
   *        the {@link Metric}s that this {@link DerivedMetric} is
   *        computed from
   */
    public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(fabric.lang.arrays.ObjectArray terms);
    
    /**
   * Method to be called at the end of a constructor for any subclass of
   * {@link DerivedMetric}. Ensures that the {@link DerivedMetric} is added as
   * an {@link Observer} of its terms as well as caches the initial value of
   * the {@link Metric}.
   */
    public void initialize();
    
    public void handleUpdates();
    
    public boolean isSingleStore();
    
    /**
   * Method to be called when a {@link DerivedMetric} is no longer stored.
   * Removes itself from the {@link Observer}s of its terms.
   */
    public void cleanup();
    
    /**
   * @return the terms this {@link DerivedMetric} is defined over
   */
    public fabric.lang.arrays.ObjectArray terms();
    
    /**
   * @param i
   *        an index into the terms array
   * @return the ith term this {@link DerivedMetric} is defined over
   */
    public fabric.metrics.Metric term(int i);
    
    /**
   * @param s
   *        a {@link Store} that will hold the copy of this
   *        {@link DerivedMetric}
   * @return a copy of this {@link DerivedMetric} that is stored on s
   */
    public abstract fabric.metrics.DerivedMetric copyOn(fabric.worker.Store s);
    
    /**
   * @param bound
   *        a {@link Bound} that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *       being above bound.
   */
    public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
      policyFor(fabric.metrics.contracts.Bound bound);
    
    public static class _Proxy extends fabric.metrics.Metric._Proxy
      implements fabric.metrics.DerivedMetric {
        public double get$lastValue() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$lastValue(
                                                                    );
        }
        
        public double set$lastValue(double val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$lastValue(
                                                                    val);
        }
        
        public double postInc$lastValue() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postInc$lastValue();
        }
        
        public double postDec$lastValue() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).
              postDec$lastValue();
        }
        
        public fabric.lang.arrays.ObjectArray get$terms() {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).get$terms();
        }
        
        public fabric.lang.arrays.ObjectArray set$terms(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.metrics.DerivedMetric._Impl) fetch()).set$terms(
                                                                    val);
        }
        
        public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(
          fabric.lang.arrays.ObjectArray arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).
              fabric$metrics$DerivedMetric$(arg1);
        }
        
        public void initialize() {
            ((fabric.metrics.DerivedMetric) fetch()).initialize();
        }
        
        public void handleUpdates() {
            ((fabric.metrics.DerivedMetric) fetch()).handleUpdates();
        }
        
        public void cleanup() {
            ((fabric.metrics.DerivedMetric) fetch()).cleanup();
        }
        
        public fabric.lang.arrays.ObjectArray terms() {
            return ((fabric.metrics.DerivedMetric) fetch()).terms();
        }
        
        public fabric.metrics.Metric term(int arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).term(arg1);
        }
        
        public fabric.metrics.DerivedMetric copyOn(fabric.worker.Store arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).copyOn(arg1);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy policyFor(
          fabric.metrics.contracts.Bound arg1) {
            return ((fabric.metrics.DerivedMetric) fetch()).policyFor(arg1);
        }
        
        public _Proxy(DerivedMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.metrics.Metric._Impl
      implements fabric.metrics.DerivedMetric {
        public double get$lastValue() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.lastValue;
        }
        
        public double set$lastValue(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.lastValue = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$lastValue() {
            double tmp = this.get$lastValue();
            this.set$lastValue((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$lastValue() {
            double tmp = this.get$lastValue();
            this.set$lastValue((double) (tmp - 1));
            return tmp;
        }
        
        protected double lastValue;
        
        public fabric.lang.arrays.ObjectArray get$terms() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.terms;
        }
        
        public fabric.lang.arrays.ObjectArray set$terms(
          fabric.lang.arrays.ObjectArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.terms = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.arrays.ObjectArray terms;
        
        /**
   * @param s
   *        the {@link Store} this {@link DerivedMetric} will be stored on
   * @param terms
   *        the {@link Metric}s that this {@link DerivedMetric} is
   *        computed from
   */
        public fabric.metrics.DerivedMetric fabric$metrics$DerivedMetric$(
          fabric.lang.arrays.ObjectArray terms) {
            fabric$metrics$Metric$();
            this.set$terms(
                   (fabric.lang.arrays.ObjectArray)
                     new fabric.lang.arrays.ObjectArray._Impl(
                       this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                           this.get$$updateLabel(),
                                           this.get$$updateLabel().confPolicy(),
                                           fabric.metrics.Metric._Proxy.class,
                                           terms.get$length()).$getProxy());
            fabric.util.Arrays._Impl.arraycopy(terms, 0, this.get$terms(), 0,
                                               terms.get$length());
            if (((fabric.util.TreeSet)
                   new fabric.util.TreeSet._Impl(this.$getStore()).$getProxy()).
                  fabric$util$TreeSet$(fabric.util.Arrays._Impl.asList(terms)).
                  size() != terms.get$length())
                throw new java.lang.IllegalArgumentException(
                        "DerivedMetric terms must not contain duplicates!");
            return (fabric.metrics.DerivedMetric) this.$getProxy();
        }
        
        /**
   * Method to be called at the end of a constructor for any subclass of
   * {@link DerivedMetric}. Ensures that the {@link DerivedMetric} is added as
   * an {@link Observer} of its terms as well as caches the initial value of
   * the {@link Metric}.
   */
        public void initialize() {
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                ((fabric.metrics.Metric) this.get$terms().get(i)).
                  addObserver((fabric.metrics.DerivedMetric) this.$getProxy());
            }
            this.set$lastValue((double) value());
        }
        
        public void handleUpdates() {
            double newValue = value();
            if (newValue != this.get$lastValue()) {
                this.set$lastValue((double) newValue);
                markModified();
            }
        }
        
        public boolean isSingleStore() {
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                if (!((fabric.metrics.Metric) this.get$terms().get(i)).
                      $getStore().equals($getStore()) ||
                      !((fabric.metrics.Metric) this.get$terms().get(i)).
                      isSingleStore())
                    return false;
            }
            return true;
        }
        
        /**
   * Method to be called when a {@link DerivedMetric} is no longer stored.
   * Removes itself from the {@link Observer}s of its terms.
   */
        public void cleanup() {
            for (int i = 0; i < this.get$terms().get$length(); i++) {
                ((fabric.metrics.Metric) this.get$terms().get(i)).
                  removeObserver((fabric.metrics.DerivedMetric)
                                   this.$getProxy());
            }
        }
        
        /**
   * @return the terms this {@link DerivedMetric} is defined over
   */
        public fabric.lang.arrays.ObjectArray terms() {
            fabric.lang.arrays.ObjectArray cTerms =
              (fabric.lang.arrays.ObjectArray)
                new fabric.lang.arrays.ObjectArray._Impl(
                  this.$getStore()).fabric$lang$arrays$ObjectArray$(
                                      this.get$$updateLabel(),
                                      this.get$$updateLabel().confPolicy(),
                                      fabric.metrics.Metric._Proxy.class,
                                      this.get$terms().get$length()).$getProxy(
                                                                       );
            fabric.util.Arrays._Impl.arraycopy(this.get$terms(), 0, cTerms, 0,
                                               this.get$terms().get$length());
            return cTerms;
        }
        
        /**
   * @param i
   *        an index into the terms array
   * @return the ith term this {@link DerivedMetric} is defined over
   */
        public fabric.metrics.Metric term(int i) {
            return (fabric.metrics.Metric) this.get$terms().get(i);
        }
        
        /**
   * @param s
   *        a {@link Store} that will hold the copy of this
   *        {@link DerivedMetric}
   * @return a copy of this {@link DerivedMetric} that is stored on s
   */
        public abstract fabric.metrics.DerivedMetric copyOn(fabric.worker.Store s);
        
        /**
   * @param bound
   *        a {@link Bound} that the returned policy enforces.
   * @return a {@link EnforcementPolicy} that enforces this {@link Metric}
   *       being above bound.
   */
        public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
          policyFor(fabric.metrics.contracts.Bound bound);
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.DerivedMetric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeDouble(this.lastValue);
            $writeRef($getStore(), this.terms, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.lastValue = in.readDouble();
            this.terms = (fabric.lang.arrays.ObjectArray)
                           $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.DerivedMetric._Impl src =
              (fabric.metrics.DerivedMetric._Impl) other;
            this.lastValue = src.lastValue;
            this.terms = src.terms;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.DerivedMetric._Static {
            public _Proxy(fabric.metrics.DerivedMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.DerivedMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  DerivedMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.DerivedMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.DerivedMetric._Static._Impl.class);
                $instance = (fabric.metrics.DerivedMetric._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.DerivedMetric._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.DerivedMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 117, -101, 76, 107, 97,
    88, 60, 91, 78, -121, 61, 78, -42, 55, -120, -105, 71, 12, 42, 80, -4, 17,
    -36, -18, -111, 122, -10, -106, 91, -100, -19, -102 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0YbWwcR3Xu/O34O592bCdN3KB83ZECrRo30OQSJ0cusRvHEbVFzNzunL3x3u52d865pE1JERALRKqCmza0yQ8UVFpMiiJVFaoihSpA26AKIlSKFGgQKm0VgqgQJT9aynszc19756st9aSZN7fz3tv33ryv2ZmbpMpzyaoEjRtmiB9xmBfqo/FobIC6HtMjJvW8/fB0VFtQGT317jN6d5AEY6RBo5ZtGRo1Ry2Pk6bYITpJwxbj4aF90d4RUqch4S7qjXMSHNmWdslKxzaPjJk2Vy8p4v/4+vD0EwdbLlSQ5mHSbFiDnHJDi9gWZ2k+TBqSLBlnrrdV15k+TFotxvRB5hrUNI4Com0NkzbPGLMoT7nM28c825xExDYv5TBXvDPzEMW3QWw3pXHbBfFbpPgpbpjhmOHx3hipThjM1L0HyMOkMkaqEiYdA8QlsYwWYcEx3IfPAb3eADHdBNVYhqRywrB0Tlb4KbIa9+wGBCCtSTI+bmdfVWlReEDapEgmtcbCg9w1rDFArbJT8BZOOmZlCki1DtUm6Bgb5WSZH29AbgFWnTALknCy2I8mOMGZdfjOLO+0bu695+SD1i4rSAIgs840E+WvBaJuH9E+lmAuszQmCRvWxU7RJRengoQA8mIfssR58aH3793QfekVibO8BE5//BDT+Kh2Lt70+87I2rsrUIxax/YMdIUCzcWpDqid3rQD3r4kyxE3Q5nNS/t+ff/x59iNIKmPkmrNNlNJ8KpWzU46hsncncxiLuVMj5I6ZukRsR8lNbCOGRaTT/sTCY/xKKk0xaNqW/wHEyWABZqoBtaGlbAza4fycbFOO4SQFhgkAOMhQppOAlxOSMXLnOwOj9tJFo6bKXYY3DsMg1FXGw9D3LqGFvZcLeymLG4AknoEXgTAC2+HIAGn3yP+hkAM59Nll0bpWw4HAmDYFZqtszj14JSUx2wbMCEodtmmztxRzTx5MUoWXjwtvKYOPd0DbxV2CcBJd/pzRD7tdGrbjvfPj16RHoe0ymycdEoZQ0rGUIGMIFYDxlIIslMIstNMIB2KnI3+VLhMtSdiK8upAThtdkzKE7abTJNAQKi1SNALX4GTnoAMAkmiYe3gV7/8talVFeCkzuFKPDdA7fGHTC7RRGFFIQ5GteYT737w/Kljdi54OOkpiuliSozJVX4bubbGdMh5OfbrVtIXRi8e6wliPqmDVMcpOCPkjW7/OwpiszeT59AaVTGyAG1ATdzKJKd6Pu7ah3NPxNk34dQm3QCN5RNQpMgtg86ZN19/73OieGSyaXNe2h1kvDcvgpFZs4jV1pzt97uMAd6fnxz4weM3T4wIwwPG6lIv7ME5ApFLIWRt91uvPPCnt/5y7g/B3GFxUu2k4qahpYUurR/DLwDjfzgwDPEBQkjGEZUCVmZzgINvXpOTDbKBCRkJRPd6hqykrRsJg8ZNhp7yYfPtm174x8kWedwmPJHGc8mGT2aQe96+jRy/cvC/3YJNQMNqlLNfDk2muIU5zltdlx5BOdKPXO06/Rt6BjwfEpRnHGUi5xBhDyIO8A5hi41i3uTb+zxOq6S1OsXzoFec7vuwbuZ8cTg883RH5Is3ZMRnfRF53FYi4g/QvDC547nkf4Krqn8VJDXDpEWUbGrxAxSyFrjBMBRdL6IexkhjwX5hAZXVojcba53+OMh7rT8KcpkG1oiN63rp+NJxwBANaKQuGJ2EVC5TsAZ3Fzo4L0oHiFhsFiSrxbwGp7UZZ6xzXJuDlExPZ9kGke0Cxa5awoqP8tgCGZRkqXKJsxhwjSSE06QqvWxq+jsfh05OSz+U/cnqohYhn0b2KELZRpzWp+Ett5V7i6Doe+f5Yy/95NgJWb/bCqvtDiuV/NkbH/029OT1V0vk8mrdhqhkMpvgfGehlaEgkm4wxxcUXFPCyruklXHaUmxMpLpdwc4CY1ZBd5X0QMUuXzsMZUREkbTj68/car/Y894tqZ6/ScpD/NfMWzeuNnadF4m4EuuicB5/d1ncPBb0hML8DYVWaFPybyhlBYG6GPo6X1mU9RA3O7IBHlCVS1gapwE0ou8vLoZKe24Ql+vAdAnDorIXWA+HaDJrjI8L5K3KcRBs56QClMVl/yyRIPhJPjjdj9OwIEhnhQ6q7KP0lLkOIx06VNtimDbFXjvEBxZ004aLStYsspobdih7fVD+Fk8XmQW8vehmtEecTS5NXb/RdXdk4u0x6Q4rfO7gx352z8yrO9do3w+Simw+KrokFBL1FmahepfBHcfaX5CLVsrDmqNly2R5q8yecIRDcNgamjljz5ac+WWilbYUEZwu7zW1NA49DtV4LueJX7PqfH+p4IU8986rP4GMCP4GUCSa/rjH3ElZazowcXXNdp0RSevcN6bP6v0/3hRUyu4A5+G2s9Fkk8zMe2mdWI9lBa5DgbfD+AwhVf0KLsqPx1wuEynJLExJtYpkoYKNfl03+r1SRgPO3yxzWN/G6etc5DIwSo+yTU9Bc9yTk82nUQeMuwipuabglflphCSvKXh5do3yBf5emb1HcZrimCqh1GDQslLJpXLSNvRS2mBtvhckO66gOT9tkGRCQTY3bU6X2XsKp2lOGseppZtsyNGhpRSYj5USfiWMKKw/q2D7/IRHkmUKts1N+B+V2TuH0xkQ3vAG4epkskHor0ueRk3ctk1GrVI6LYUxREj9owo+Mj+dkOS4gkfnptP5Mns/x+lZkFhDeVPOrEfRDuMgtBEHFOybn9hIskPBL81N7BfL7P0CpwuZvkWkhVJCY8tkQOPwsILzdH4kmVCwjPPnJadhwfVSGclfxuklCFiUHNf3+QTHBoeEYXiENE4pODGL4KXLC06Tvma6VXE6pODIJ+qTbTJUiTlsuxPMDeWcvt3/1UBI9loZ5X+H02WOn5icI/1WThGf+pthPEhI03cV1D4V9ZFTXMHBOavf7auweJPDwu2FttkpS0esDiHTm2UUv4bTVbzr2HDvPtJnuxn2d87KnllgVI0lmcXh6pBdDwgO8rWQiwqKGt5/l5f4EqW+i2qRy+zc27s3LJ7lK9Syoi/Viu782ebapWeH/ijb+cw3z7oYqU2kTDP/npi3rnZcljCE+nXy1ugI8DdOmgqV5qLtx5Wwyl8l3t/BTyQe/ntHnFVHdrpP4HSkXPzKPvPvpbeqa/dfFx85sG6knopN0K/cM7L3xJa9b9w19cTOhnUDH7Ze++djRz84NfL0zR/+H815QU39FwAA";
}
