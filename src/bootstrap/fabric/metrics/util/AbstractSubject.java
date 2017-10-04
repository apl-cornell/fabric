package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.LinkedHashSet;
import java.util.LinkedList;
import fabric.util.Set;
import fabric.util.HashSet;
import fabric.util.Iterator;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.Store;
import java.util.logging.Level;

/**
 * Base implementation of {@link Subject}
 */
public interface AbstractSubject
  extends fabric.metrics.util.Subject, fabric.lang.Object {
    public fabric.worker.Store getStore();
    
    public fabric.metrics.util.AbstractSubject
      fabric$metrics$util$AbstractSubject$();
    
    public fabric.util.Set get$observers();
    
    public fabric.util.Set set$observers(fabric.util.Set val);
    
    public void addObserver(fabric.metrics.util.Observer o);
    
    public void removeObserver(fabric.metrics.util.Observer o);
    
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    public boolean isObserved();
    
    public fabric.util.Set getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.AbstractSubject {
        public fabric.util.Set get$observers() {
            return ((fabric.metrics.util.AbstractSubject._Impl) fetch()).
              get$observers();
        }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            return ((fabric.metrics.util.AbstractSubject._Impl) fetch()).
              set$observers(val);
        }
        
        public fabric.worker.Store getStore() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).getStore();
        }
        
        public fabric.metrics.util.AbstractSubject
          fabric$metrics$util$AbstractSubject$() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).
              fabric$metrics$util$AbstractSubject$();
        }
        
        public void addObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.AbstractSubject) fetch()).addObserver(arg1);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.AbstractSubject) fetch()).removeObserver(
                                                              arg1);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.AbstractSubject) fetch()).observedBy(
                                                                     arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).isObserved();
        }
        
        public fabric.util.Set getObservers() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).getObservers(
                                                                     );
        }
        
        public static void processSamples(java.util.LinkedList arg1) {
            fabric.metrics.util.AbstractSubject._Impl.processSamples(arg1);
        }
        
        public _Proxy(AbstractSubject._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.AbstractSubject {
        public fabric.worker.Store getStore() { return $getStore(); }
        
        public fabric.metrics.util.AbstractSubject
          fabric$metrics$util$AbstractSubject$() {
            this.set$observers(
                   ((fabric.util.LinkedHashSet)
                      new fabric.util.LinkedHashSet._Impl(
                        this.$getStore()).$getProxy(
                                            )).fabric$util$LinkedHashSet$());
            fabric$lang$Object$();
            return (fabric.metrics.util.AbstractSubject) this.$getProxy();
        }
        
        public fabric.util.Set get$observers() { return this.observers; }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observers = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set observers;
        
        public void addObserver(fabric.metrics.util.Observer o) {
            {
                fabric.worker.transaction.TransactionManager $tm253 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled256 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff254 = 1;
                boolean $doBackoff255 = true;
                $label249: for (boolean $commit250 = false; !$commit250; ) {
                    if ($backoffEnabled256) {
                        if ($doBackoff255) {
                            if ($backoff254 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff254);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e251) {
                                        
                                    }
                                }
                            }
                            if ($backoff254 < 5000) $backoff254 *= 2;
                        }
                        $doBackoff255 = $backoff254 <= 32 || !$doBackoff255;
                    }
                    $commit250 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.get$observers().add(o); }
                    catch (final fabric.worker.RetryException $e251) {
                        $commit250 = false;
                        continue $label249;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e251) {
                        $commit250 = false;
                        fabric.common.TransactionID $currentTid252 =
                          $tm253.getCurrentTid();
                        if ($e251.tid.isDescendantOf($currentTid252))
                            continue $label249;
                        if ($currentTid252.parent != null) throw $e251;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e251) {
                        $commit250 = false;
                        if ($tm253.checkForStaleObjects()) continue $label249;
                        throw new fabric.worker.AbortException($e251);
                    }
                    finally {
                        if ($commit250) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e251) {
                                $commit250 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e251) {
                                $commit250 = false;
                                fabric.common.TransactionID $currentTid252 =
                                  $tm253.getCurrentTid();
                                if ($currentTid252 != null) {
                                    if ($e251.tid.equals($currentTid252) ||
                                          !$e251.tid.isDescendantOf(
                                                       $currentTid252)) {
                                        throw $e251;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit250) {
                            {  }
                            continue $label249;
                        }
                    }
                }
            }
        }
        
        public void removeObserver(fabric.metrics.util.Observer o) {
            {
                fabric.worker.transaction.TransactionManager $tm261 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled264 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff262 = 1;
                boolean $doBackoff263 = true;
                $label257: for (boolean $commit258 = false; !$commit258; ) {
                    if ($backoffEnabled264) {
                        if ($doBackoff263) {
                            if ($backoff262 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff262);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e259) {
                                        
                                    }
                                }
                            }
                            if ($backoff262 < 5000) $backoff262 *= 2;
                        }
                        $doBackoff263 = $backoff262 <= 32 || !$doBackoff263;
                    }
                    $commit258 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.get$observers().remove(o); }
                    catch (final fabric.worker.RetryException $e259) {
                        $commit258 = false;
                        continue $label257;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e259) {
                        $commit258 = false;
                        fabric.common.TransactionID $currentTid260 =
                          $tm261.getCurrentTid();
                        if ($e259.tid.isDescendantOf($currentTid260))
                            continue $label257;
                        if ($currentTid260.parent != null) throw $e259;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e259) {
                        $commit258 = false;
                        if ($tm261.checkForStaleObjects()) continue $label257;
                        throw new fabric.worker.AbortException($e259);
                    }
                    finally {
                        if ($commit258) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e259) {
                                $commit258 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e259) {
                                $commit258 = false;
                                fabric.common.TransactionID $currentTid260 =
                                  $tm261.getCurrentTid();
                                if ($currentTid260 != null) {
                                    if ($e259.tid.equals($currentTid260) ||
                                          !$e259.tid.isDescendantOf(
                                                       $currentTid260)) {
                                        throw $e259;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit258) {
                            {  }
                            continue $label257;
                        }
                    }
                }
            }
        }
        
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$observers().contains(o);
        }
        
        public boolean isObserved() { return !this.get$observers().isEmpty(); }
        
        public fabric.util.Set getObservers() { return this.get$observers(); }
        
        /**
   * Utility for processing a batch of samples for the transaction manager.
   *
   * @param unobserved
   *            a {@link LinkedList} of {@link SampledMetric}s that have been
   *            updated and still need to have Observers compute the resulting
   *            effects for.
   */
        public static void processSamples(java.util.LinkedList unobserved) {
            java.util.LinkedList queue = new java.util.LinkedList();
            java.util.LinkedList delayed = new java.util.LinkedList();
            while (!unobserved.isEmpty()) {
                fabric.metrics.SampledMetric
                  sm =
                  (fabric.metrics.SampledMetric)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(
                                                          unobserved.poll()));
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINER,
                    "HANDLING OBSERVATION OF " +
                      java.lang.String.
                        valueOf(fabric.lang.WrappedJavaInlineable.$unwrap(sm)));
                fabric.util.Iterator obsIter = sm.getObservers().iterator();
                while (obsIter.hasNext()) {
                    queue.add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  obsIter.next(
                                                                            )));
                }
                while (!queue.isEmpty()) {
                    fabric.metrics.util.Observer
                      unhandled =
                      (fabric.metrics.util.Observer)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(
                                                              queue.poll()));
                    delayed.remove(
                              (java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                    unhandled));
                    boolean needToWait = false;
                    fabric.util.Iterator leavesIter =
                      unhandled.getLeafSubjects().iterator();
                    while (leavesIter.hasNext()) {
                        if (unobserved.
                              contains(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap(leavesIter.next()))) {
                            needToWait = true;
                            break;
                        }
                    }
                    if (!needToWait) {
                        fabric.common.Logging.METRICS_LOGGER.
                          log(
                            java.util.logging.Level.FINER,
                            "HANDLING OBSERVER " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.
                                      $unwrap(unhandled)));
                        boolean modified = unhandled.handleUpdates();
                        if (fabric.lang.Object._Proxy.
                              $getProxy(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      unhandled)) instanceof fabric.metrics.util.Subject &&
                              modified) {
                            fabric.common.Logging.METRICS_LOGGER.
                              log(
                                java.util.logging.Level.FINER,
                                "QUEUING PARENTS OF OBSERVER " +
                                  java.lang.String.
                                    valueOf(
                                      fabric.lang.WrappedJavaInlineable.
                                          $unwrap(unhandled)));
                            fabric.util.Iterator parentIter =
                              ((fabric.metrics.util.Subject)
                                 fabric.lang.Object._Proxy.$getProxy(
                                                             unhandled)).
                              getObservers().iterator();
                            while (parentIter.hasNext()) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(parentIter.next()));
                            }
                        }
                    }
                    else {
                        fabric.common.Logging.METRICS_LOGGER.
                          log(
                            java.util.logging.Level.FINER,
                            "DELAYING OBSERVER " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.
                                      $unwrap(unhandled)));
                        delayed.
                          add(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                  unhandled));
                    }
                    if (queue.isEmpty()) {
                        java.util.Iterator delayedIter = delayed.iterator();
                        while (delayedIter.hasNext()) {
                            fabric.metrics.util.Observer
                              withheld =
                              (fabric.metrics.util.Observer)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(delayedIter.next()));
                            boolean doneWaiting = true;
                            fabric.util.Iterator withheldLeavesIter =
                              withheld.getLeafSubjects().iterator();
                            while (withheldLeavesIter.hasNext()) {
                                if (unobserved.
                                      contains(
                                        (java.lang.Object)
                                          fabric.lang.WrappedJavaInlineable.
                                          $unwrap(withheldLeavesIter.next()))) {
                                    doneWaiting = false;
                                    break;
                                }
                            }
                            if (doneWaiting) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(withheld));
                            }
                        }
                    }
                }
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.AbstractSubject._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.observers, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.observers = (fabric.util.Set)
                               $readRef(fabric.util.Set._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.AbstractSubject._Impl src =
              (fabric.metrics.util.AbstractSubject._Impl) other;
            this.observers = src.observers;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.AbstractSubject._Static {
            public _Proxy(fabric.metrics.util.AbstractSubject._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.AbstractSubject._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  AbstractSubject.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.AbstractSubject._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.AbstractSubject._Static._Impl.class);
                $instance = (fabric.metrics.util.AbstractSubject._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.AbstractSubject._Static {
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
                return new fabric.metrics.util.AbstractSubject._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -60, -53, -1, 73, -55,
    99, -44, -61, -64, -14, -61, -46, 88, 49, 87, 69, 120, 33, -47, 31, 30, 72,
    -41, 94, 124, 25, -6, 26, 38, 62, 113, 3 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507054106000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYfWwcRxWfO3+e7cZfTdo4juM4V0PS9I4UBGpcqsanfBy9YsuXBOpAzNzunL313u5mds6+hCSUSigpSBEqbppKrcUfrkpS9wOktkJVUKAtNAoCtQotHwIiQUVRyB8BQRECwnuzc19756v9ByftvL2Z9968eR+/mdnFa6TB5WQgTVOGGRGHHeZGdtFUPDFKucv0mElddy/0Tmit9fHT7z+j9wVJMEHaNGrZlqFRc8JyBVmVeJDO0KjFRHTfWHzoAAlpKLiHulOCBA8M5zjpd2zz8KRpCzVJhf7Hbo/OPX6w43t1pH2ctBtWUlBhaDHbEiwnxklbhmVSjLs7dJ3p46TTYkxPMm5Q0zgCjLY1TrpcY9KiIsuZO8Zc25xBxi436zAu58x3ovk2mM2zmrA5mN/hmZ8VhhlNGK4YSpDGtMFM3T1EjpP6BGlIm3QSGNck8quISo3RXdgP7C0GmMnTVGN5kfppw9IF2eCXKKw4fB8wgGhThokpuzBVvUWhg3R5JpnUmowmBTesSWBtsLMwiyA9SyoFpmaHatN0kk0Icqufb9QbAq6QdAuKCLLazyY1Qcx6fDErida1z9596svWHitIAmCzzjQT7W8GoT6f0BhLM84sjXmCbVsSp+ma8yeDhADzah+zx/PK0ev3bu278KbHs64Kz0jqQaaJCW0hteqt3tjmu+rQjGbHdg1MhbKVy6iOqpGhnAPZvqagEQcj+cELYz9+4KFz7GqQtMRJo2ab2QxkVadmZxzDZHw3sxingulxEmKWHpPjcdIE7wnDYl7vSDrtMhEn9absarTlf3BRGlSgi5rg3bDSdv7doWJKvuccQkgHPCQAzxlCuq8DXQN/fyHIWHTKzrBoysyyWUjvKDyMcm0qCnXLDS3qci3Ks5YwgEl1QRYBcb3170hBulNNJLPScRGwxvm/aM3hWjpmAwFw8wbN1lmKuhAzlT/DoyaUyB7b1Bmf0MxT5+Ok+/wTModCmPcu5K70UgDi3utHjFLZuezwzuvPT1zy8g9llRMF2eSZGlGmejH2mQrWtWGBRQCyIgBZi4FcJDYff1bmUaMrC66gsA0UbndMKtI2z+RIICBXd7OUl8oh/NMAK4AcbZuTX/zMl04O1EHmOrP1GExgDfvrqIg+cXijUBwTWvuJ9//xwuljdrGiBAlXFHqlJBbqgN9V3NaYDkBYVL+ln740cf5YOIggEwL8ExQyFMCkzz9HWcEO5cEPvdGQIK3oA2riUB6xWsQUt2eLPTIFVmHT5WUDOstnoMTNTyedp375sz9/XO4oeYhtL8HiJBNDJWWNytplAXcWfb+XMwZ8vz0z+q3Hrp04IB0PHJuqTRjGNgblTKGObf61Nw/96ve/W7gcLAZLkEYnmzINLSfX0nkDfgF4/osP1iZ2IAWEjilc6C8Ag4MzDxZtA4gwIdnAdDe8z8rYupE2aMpkmCn/br9t20t/OdXhhduEHs95nGz9cAXF/rXD5KFLBz/ok2oCGm5RRf8V2Tzc6y5q3sE5PYx25L769vonfkKfgswH1HKNI0wCEZH+IDKAd0pf3CHbbb6xT2Az4Hmrt5Dw/j1gF26mxVwcjy4+2RO756pX+IVcRB0bqxT+flpSJneey/w9OND4RpA0jZMOuY9TS+yngGGQBuOwE7sx1ZkgN5WNl++q3hYyVKi1Xn8dlEzrr4Ii4MA7cuN7i5f4XuLksXwrPLcRUndB0QUc7XawvTkXIPJluxTZJNtBbDZLRwYFaXK4MQOZJUjIyGSyAmMvZ7kdeuyUy/gMHImk4GooHQV8MsDgDNm/1o9fsiRzS0yJr1sEaaYKLnOF1chfu9qRLiv6RslqylJAWbSuGhTnIbiqdTnInvVLHTvkkWnh4bl5feTpbd7hoKt8K99pZTPPvfOfn0bOXLlYZWsICdu5w2QzzCyxthmm3Fhx/r1fnsqKeXfl6vq7YtPvTXrTbvCZ6Oc+e//ixd2D2qNBUldIsIqjYLnQUHlatXAGJ1lrb1ly9RfCEcJwrPWe4G5FP1WaXB70Vg1zQIa5GNsgKmtWSj6p6Mf8sa0OAF+oMXYQm/2QTpNMJAFwWT4vulVezNp8mvFIcawiH2RvsmBpG+oehmc9WHhWUXe5y4YEcLgtIPmY7lt9q9LFFTWWt/rpGmMZbOAKEPYWG1ZFEMZEDfvOI+Gi2cnyGANokI8CcHyg6G+WWCw2D1RGFEV+rejlpdcUKK/b3mp1O5LHm+qFi72ihjuOYGML0kp1vaCqcqsY5UYGdvsZdV1gJ+e+fiNyas4rZu9OtaniWlMq492r5JQ3SahESNlYaxYpsetPLxx79TvHTgSVuSOC1M/Yhl4tJuBMEiWkPqVobGUxQZFhRe/+0Jjg36zUeqKGdx/B5mG4iXOWsWdY3sHYe7zaEj4CD1R6/c8V/e7KloAiLyp6bgVL+GaNJTyKzTcEaVEbmz58WPIdVVFE8hXYE1O2bTJqVVsVVC4ZIqRhUNGula0KRToVbV0eADxZY2wem8cFHklUPHTsmatm+WZ47oVpTyo6tTLLUWRSUbo8y5+uMfYMNt+GEgfgLiv7HT7b5RlnOzwJQppWebTxj8vE4/xpA+5b+KnHh8jtStsfFH1n2ehV9WaWR60QopZpa9T0IOvFGm54GZuzUFQO3qdcN0kzjultVMdzcN7ygTgesddVufOq7zFa7HW28N59W1cvcd+9teILmZJ7fr69+Zb5fe/Kq1vhW0sIbkbprGmWHkVL3hsdztKGXEbIO5g6krxa3H1L0R3QDolc2/c9zh/Awss5hfxYhW+lfD+ECHp8+O9HMkI9xSYflS6lCw/fkZFaR0ApluX44XDxb7f8s7F57xV5RYOw9L9+6Ub8onb5tQt/fe3tz2/73M7cxrc29O159+DRtf/qGbznUN3/ALHUetvQFAAA";
}
