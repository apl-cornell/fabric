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
            this.get$observers().add(o);
        }
        
        public void removeObserver(fabric.metrics.util.Observer o) {
            this.get$observers().remove(o);
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
                    java.util.logging.Level.FINE,
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
                            java.util.logging.Level.FINE,
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
                                java.util.logging.Level.FINE,
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
                            java.util.logging.Level.FINE,
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
    
    public static final byte[] $classHash = new byte[] { 37, 27, 100, 38, 80,
    -77, 6, -24, 80, 5, 83, -2, 42, 49, 85, 66, -42, 79, 68, -76, 27, 20, -25,
    -1, 36, -125, 124, 76, 120, -123, 125, 74 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500579643000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYfWwcRxWfO9tnn+PGjvPRxnYcx7mkJE3uSEBEjduK+GiSay615YsrcETcud05e+u93c3snH0uMUqpQqKqsgS4aYpoEMIVbeq2iBLxESLlDwqtipBAoXwJCBJVgkL+qBCUP0rDe7NzX+uza//BSTtvb+a9N2/ex29mdu4WqXM56c7QtGFGxaTD3Oh+mk4k+yl3mR43qesegd5hbUVt4uyN7+idQRJMkiaNWrZlaNQctlxBViYfo+M0ZjERGxxI9BwlYQ0FD1J3VJDg0d48J12ObU6OmLZQk8zT/8w9sZlnj7V8r4Y0D5Fmw0oJKgwtbluC5cUQacqybJpxd5+uM32IrLIY01OMG9Q0HgdG2xoira4xYlGR48wdYK5tjiNjq5tzGJdzFjrRfBvM5jlN2BzMb/HMzwnDjCUNV/QkSShjMFN3j5MvktokqcuYdAQY1yULq4hJjbH92A/sjQaYyTNUYwWR2jHD0gXZ6JcorjhyCBhAtD7LxKhdnKrWotBBWj2TTGqNxFKCG9YIsNbZOZhFkLYFlQJTg0O1MTrChgW5y8/X7w0BV1i6BUUEWetnk5ogZm2+mJVF69bD901/wTpoBUkAbNaZZqL9DSDU6RMaYBnGmaUxT7Bpe/IsXXf5TJAQYF7rY/Z4fnDivU/v6LzypsfTXoWnL/0Y08SwNpte+auO+LZ7a9CMBsd2DUyFipXLqParkZ68A9m+rqgRB6OFwSsDP/vcyQvsZpA0JkhIs81cFrJqlWZnHcNk/ACzGKeC6QkSZpYel+MJUg/vScNiXm9fJuMykSC1puwK2fI/uCgDKtBF9fBuWBm78O5QMSrf8w4hpAUeEoDnWULWXAK6Dv7+RpCB2KidZbG0mWMTkN4xeBjl2mgM6pYbWszlWoznLGEAk+qCLALieuvfl4Z0p5pI5aTjomCN83/Rmse1tEwEAuDmjZqtszR1IWYqf3r7TSiRg7apMz6smdOXE2T15edkDoUx713IXemlAMS9w48Y5bIzud4H33t1+G0v/1BWOVGQzZ6pUWWqF2OfqWBdExZYFCArCpA1F8hH4+cTL8s8Crmy4IoKm0DhXsekImPzbJ4EAnJ1a6S8VA7hHwNYAeRo2pb6/EOPnumugcx1JmoxmMAa8ddRCX0S8EahOIa15tM3/v3a2Sm7VFGCROYV+nxJLNRuv6u4rTEdgLCkfnsXvTh8eSoSRJAJA/4JChkKYNLpn6OiYHsK4IfeqEuSFegDauJQAbEaxSi3J0o9MgVWYtPqZQM6y2egxM37U87zv/vl3z8hd5QCxDaXYXGKiZ6yskZlzbKAV5V8f4QzBnx/Otf/tWdunT4qHQ8cm6tNGME2DuVMoY5tfurN47//y59nrwZLwRIk5OTSpqHl5VpW3YZfAJ4P8cHaxA6kgNBxhQtdRWBwcOatJdsAIkxINjDdjQxaWVs3MgZNmwwz5YPmLbsu/mO6xQu3CT2e8zjZ8dEKSv3re8nJt4+93ynVBDTcokr+K7F5uLe6pHkf53QS7cg/8esNz/2cPg+ZD6jlGo8zCURE+oPIAO6Wvtgp212+sU9i0+15q6OY8P49YD9upqVcHIrNfaMt/sBNr/CLuYg6NlUp/EdoWZnsvpD9V7A79EaQ1A+RFrmPU0s8QgHDIA2GYCd246ozSe6oGK/cVb0tpKdYax3+Oiib1l8FJcCBd+TG90Yv8b3EKWD5Dni2EFJzRdFZHF3tYLsmHyDyZa8U2Szbrdhsk44MClLvcGMcMkuQsJHN5gTGXs5yD/TYaZfxcTgSScG1UDoK+GSAwRnY3SYrML/ADPi6XZAGqtAxXzRe/prVBnRV0TfKjK+IuDKgvRryKsSVxuQhNzYsdKiQB6LZL82c1/te2OVt/a2VG/WDVi77yjv//UX03LW3qgB/WNjOTpONM7PMuAaYctO80+1heeYqZdW1mxvujY+9O+JNu9Fnop/7pcNzbx3Yqn01SGqK6TPvoFcp1FOZNI2cwTnVOlKROl1F74fR++u9J3hA0T3lqeMBa9WoBmRUS6EMorIGpeRTin7cH8rq5f3ZRcaGsBmA7BlhIgVwygppsFqlwYTNxxiPlsbW+/dS2dtXtLQJdffCswEsfElRd6nLhgRwuC0g15juW/0KpYsraixt9SOLjEkVj8IW7S02onI+goka8Z02IiWz+ypjDJBAPgaw8L6if1xgsdgMzo8oivxB0asLrylQWaYd1cq0T6GJV6fYOousXpbzmCArqK4XJKvgfj83srB1j6uzPzsz89Tt6PSMV7veBWnzvDtKuYx3SZJT3iFxDxFk02KzSIn911+buvTi1OmgMveQILXjtqFXCwH4juwkpPawotHlhQBFdip690eGAP8el1qfWMS7T2JzAq7VnGXtcVYeGn/JyCXcDc9umP8FRU8vbwko8mVFTy5jCU8tsoSnsTklSKPapfTeSck3rqKIZBI2uLRtm4xa1VYFhUr2gEkfKHpjeatCkeuK/nVp9X52kbFz2HxF4PlCxUPHnulqlm+D5z5C6o4p+sDyLEeR+xXdszTLv7nI2Lew+TpAL+B0sVaxb5/Pdnlg2QvPQUJCf1P0x0uE38JZAi5P+N3GB8DNStuPFJ1bMlhVvWYVdpMw7iamrVEzL227sIgbvovNt6GoHLwcuW6KZh3T25fyeTg8+TAbz8vtVS6w6uOKFv8pm3330I61C1xe75r3uUvJvXq+ueHO84O/lfew4oeTMFxzMjnTLD9Xlr2HHM4yhlxG2DtlOpJcLG225WAOaIdEru11j/OHsPBKTiG/POFbOd8liKDHh/9+IiPUVmoKUWlVuvAkHfVO0tV3eKm0LcfxK+DcP+/8T6jhyDV534KwdG1p17f2vx660V+X+nD7rsHed/o+8/32NddvR548kcyfmnrof6Sj5HidFAAA";
}
