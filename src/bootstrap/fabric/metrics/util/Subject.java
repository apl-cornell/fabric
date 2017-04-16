package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.HashSet;
import fabric.util.Iterator;
import fabric.util.LinkedHashSet;
import fabric.util.Set;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;

/**
 * Represents an observable object having a set of {@link Observer}s. After an
 * observable object changes, an application can call
 * {@link #getObserversCopy()} to get the current set of {@link Observer}s.
 * {@link Observer}s are then notified of a change via a call to
 * {@link Observer#handleUpdates()}.
 */
public interface Subject extends fabric.lang.Object {
    public fabric.metrics.util.Subject fabric$metrics$util$Subject$();
    
    public fabric.util.Set get$observers();
    
    public fabric.util.Set set$observers(fabric.util.Set val);
    
    /**
   * Adds an observer to the set of observers for this object. Nothing is done
   * if the given observer {@link #equals(Object) equals} an existing
   * observer.
   *
   * @param o
   *        {@link Observer} to add
   */
    public void addObserver(fabric.metrics.util.Observer o);
    
    /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   */
    public void removeObserver(fabric.metrics.util.Observer o);
    
    /**
   * @param o
   *        an observer that might observe this subject.
   * @return true iff o observes this subject.
   */
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    /**
   * @return true iff there are any observers of this subject, currently.
   */
    public boolean isObserved();
    
    /**
   * @return a copy of the set of the current observers of this subject.
   */
    public fabric.util.Set getObserversCopy();
    
    /**
   * @return the set of the current observers of this subject.
   */
    public fabric.util.Set getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.Subject {
        public fabric.util.Set get$observers() {
            return ((fabric.metrics.util.Subject._Impl) fetch()).get$observers(
                                                                   );
        }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            return ((fabric.metrics.util.Subject._Impl) fetch()).set$observers(
                                                                   val);
        }
        
        public fabric.metrics.util.Subject fabric$metrics$util$Subject$() {
            return ((fabric.metrics.util.Subject) fetch()).
              fabric$metrics$util$Subject$();
        }
        
        public void addObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.Subject) fetch()).addObserver(arg1);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.Subject) fetch()).removeObserver(arg1);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.Subject) fetch()).observedBy(arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.util.Subject) fetch()).isObserved();
        }
        
        public fabric.util.Set getObserversCopy() {
            return ((fabric.metrics.util.Subject) fetch()).getObserversCopy();
        }
        
        public fabric.util.Set getObservers() {
            return ((fabric.metrics.util.Subject) fetch()).getObservers();
        }
        
        public static void processSamples(java.util.LinkedList arg1,
                                          java.util.List arg2) {
            fabric.metrics.util.Subject._Impl.processSamples(arg1, arg2);
        }
        
        public _Proxy(Subject._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.Subject {
        public fabric.metrics.util.Subject fabric$metrics$util$Subject$() {
            fabric$lang$Object$();
            return (fabric.metrics.util.Subject) this.$getProxy();
        }
        
        public fabric.util.Set get$observers() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.observers;
        }
        
        public fabric.util.Set set$observers(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.observers = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Set observers =
          ((fabric.util.HashSet)
             new fabric.util.HashSet._Impl(this.$getStore()).$getProxy()).
          fabric$util$HashSet$();
        
        /**
   * Adds an observer to the set of observers for this object. Nothing is done
   * if the given observer {@link #equals(Object) equals} an existing
   * observer.
   *
   * @param o
   *        {@link Observer} to add
   */
        public void addObserver(fabric.metrics.util.Observer o) {
            fabric.common.Logging.METRICS_LOGGER.
              log(java.util.logging.Level.FINEST,
                  "SUBJECT {0} ADDED OBSERVER {1}",
                  new fabric.lang.Object[] { (fabric.metrics.util.Subject)
                                               this.$getProxy(),
                    o });
            this.get$observers().add(o);
        }
        
        /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   */
        public void removeObserver(fabric.metrics.util.Observer o) {
            fabric.common.Logging.METRICS_LOGGER.
              log(java.util.logging.Level.FINEST,
                  "SUBJECT {0} REMOVED OBSERVER {1}",
                  new fabric.lang.Object[] { (fabric.metrics.util.Subject)
                                               this.$getProxy(),
                    o });
            this.get$observers().remove(o);
        }
        
        /**
   * @param o
   *        an observer that might observe this subject.
   * @return true iff o observes this subject.
   */
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$observers().contains(o);
        }
        
        /**
   * @return true iff there are any observers of this subject, currently.
   */
        public boolean isObserved() { return !this.get$observers().isEmpty(); }
        
        /**
   * @return a copy of the set of the current observers of this subject.
   */
        public fabric.util.Set getObserversCopy() {
            return ((fabric.util.LinkedHashSet)
                      new fabric.util.LinkedHashSet._Impl(this.$getStore()).
                      $getProxy()).fabric$util$LinkedHashSet$(
                                     this.get$observers());
        }
        
        /**
   * @return the set of the current observers of this subject.
   */
        public fabric.util.Set getObservers() { return this.get$observers(); }
        
        /**
   * Utility for processing a batch of samples for the transaction manager.
   */
        public static void processSamples(java.util.LinkedList unobserved,
                                          java.util.List extensions) {
            {
                fabric.worker.transaction.TransactionManager $tm18 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff19 = 1;
                boolean $doBackoff20 = true;
                $label14: for (boolean $commit15 = false; !$commit15; ) {
                    if ($doBackoff20) {
                        if ($backoff19 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff19);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e16) {  }
                            }
                        }
                        if ($backoff19 < 5000) $backoff19 *= 2;
                    }
                    $doBackoff20 = $backoff19 <= 32 || !$doBackoff20;
                    $commit15 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        java.util.LinkedList queue = new java.util.LinkedList();
                        while (!unobserved.isEmpty()) {
                            fabric.metrics.SampledMetric
                              sm =
                              (fabric.metrics.SampledMetric)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  fabric.lang.WrappedJavaInlineable.
                                      $wrap(unobserved.poll()));
                            fabric.util.Iterator obsIter =
                              sm.getObservers().iterator();
                            while (obsIter.hasNext()) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(obsIter.next()));
                            }
                            while (!queue.isEmpty()) {
                                fabric.metrics.util.Observer
                                  unhandled =
                                  (fabric.metrics.util.Observer)
                                    fabric.lang.Object._Proxy.
                                    $getProxy(
                                      fabric.lang.WrappedJavaInlineable.
                                          $wrap(queue.poll()));
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
                                    boolean modified =
                                      unhandled.handleUpdates();
                                    if (fabric.lang.Object._Proxy.
                                          $getProxy(
                                            (java.lang.Object)
                                              fabric.lang.WrappedJavaInlineable.
                                              $unwrap(
                                                unhandled)) instanceof fabric.metrics.util.Subject &&
                                          modified) {
                                        fabric.metrics.util.Subject
                                          s =
                                          (fabric.metrics.util.Subject)
                                            fabric.lang.Object._Proxy.
                                            $getProxy(unhandled);
                                        if (fabric.lang.Object._Proxy.
                                              $getProxy(
                                                (java.lang.Object)
                                                  fabric.lang.WrappedJavaInlineable.
                                                  $unwrap(
                                                    s)) instanceof fabric.metrics.contracts.Contract) {
                                            extensions.
                                              remove(
                                                (java.lang.Object)
                                                  fabric.lang.WrappedJavaInlineable.
                                                  $unwrap(s));
                                        }
                                        fabric.util.Iterator parentIter =
                                          s.getObservers().iterator();
                                        while (parentIter.hasNext()) {
                                            queue.
                                              add(
                                                (java.lang.Object)
                                                  fabric.lang.WrappedJavaInlineable.
                                                  $unwrap(parentIter.next()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    catch (final fabric.worker.RetryException $e16) {
                        $commit15 = false;
                        continue $label14;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e16) {
                        $commit15 = false;
                        fabric.common.TransactionID $currentTid17 =
                          $tm18.getCurrentTid();
                        if ($e16.tid.isDescendantOf($currentTid17))
                            continue $label14;
                        if ($currentTid17.parent != null) throw $e16;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e16) {
                        $commit15 = false;
                        if ($tm18.checkForStaleObjects()) continue $label14;
                        throw new fabric.worker.AbortException($e16);
                    }
                    finally {
                        if ($commit15) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e16) {
                                $commit15 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e16) {
                                $commit15 = false;
                                fabric.common.TransactionID $currentTid17 =
                                  $tm18.getCurrentTid();
                                if ($currentTid17 != null) {
                                    if ($e16.tid.equals($currentTid17) ||
                                          !$e16.tid.isDescendantOf(
                                                      $currentTid17)) {
                                        throw $e16;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit15) {  }
                    }
                }
            }
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.Subject._Proxy(this);
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
            fabric.metrics.util.Subject._Impl src =
              (fabric.metrics.util.Subject._Impl) other;
            this.observers = src.observers;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.Subject._Static {
            public _Proxy(fabric.metrics.util.Subject._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.Subject._Static $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  Subject.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.Subject._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.Subject._Static._Impl.class);
                $instance = (fabric.metrics.util.Subject._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.Subject._Static {
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
                return new fabric.metrics.util.Subject._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -28, -29, 127, 102, 77,
    -19, 66, -117, -104, -55, 113, -82, -110, 63, 68, 18, -128, -82, -84, -36,
    39, -95, -124, 28, -77, -34, -75, 3, 36, -96, -111, 121 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492372571000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0YbWwcR3XubJ99tptznObLcRzHuaYkTe6UABKtgRIfdXPNpbZ8TqXaaq5zu3P21nu7m9055xwITWlpIiKCSNzgSiS0UiBp66YVVQRSiNQfLW0UqARCfEilRIKKQsiPCvHxAyjvzc597X00ljhp5s3NvPfmzfuaN7t4k7Q4NhnI0LSmR/icxZzIME3HE6PUdpga06njjMNsSulojp/+4Lza5yf+BOlUqGEamkL1lOFwsizxGJ2lUYPx6L6x+OAkCSpIuJs605z4J4fyNum3TH1uSje53KSK/zN3Ree/vb/rB00kNEFCmpHklGtKzDQ4y/MJ0pll2TSznV2qytQJstxgTE0yW6O6dggQTWOCdDvalEF5zmbOGHNMfRYRu52cxWyxZ2ESxTdBbDuncNMG8btc8XNc06MJzeGDCRLIaExXnQPkK6Q5QVoyOp0CxFWJwimigmN0GOcBvV0DMe0MVViBpHlGM1RONngpiicO7wEEIG3NMj5tFrdqNihMkG5XJJ0aU9EktzVjClBbzBzswklPXaaA1GZRZYZOsRQna7x4o+4SYAWFWpCEk5VeNMEJbNbjsVmZtW4++NkTXzJ2G37iA5lVpugofxsQ9XmIxliG2cxQmEvYuTVxmq66csxPCCCv9CC7OD/88odf2Nb3+tsuzroaOCPpx5jCU8q59LKf98a23N2EYrRZpqOhK1ScXFh1VK4M5i3w9lVFjrgYKSy+PvaTh4+8yG74SXucBBRTz2XBq5YrZtbSdGbfzwxmU87UOAkyQ42J9ThphXFCM5g7O5LJOIzHSbMupgKm+A8qygALVFErjDUjYxbGFuXTYpy3CCFd0IgP2lMw7ge4mhD/BU4eiE6bWRZN6zl2ENw7Co1RW5mOQtzamhJ1bCVq5wyuAZKcAi8C4LjnT+aEwiIghfV/5ZZH2bsO+nyg1g2KqbI0dcBG0l+GRnUIid2mrjI7pegnrsTJiivPCp8Jop874KtCKz6wc683Q5TTzueG7vvwYuqa629IK5XGyTpXxIgU0bWpFBGk6sRAikBqikBqWvTlI7Gz8ZeEvwQcEVhFRp3A6B5Lpzxj2tk88fnEqW4X9IIpmHkG0gdkiM4tyUceePTYQBN4qHWwGY0GqGFvvJSyTBxGFIIgpYSOfvCPV04fNkuRw0m4KqCrKTEgB7wqsk2FqZDwSuy39tNLqSuHw35MJkHIc5yCJ0LS6PPuURGYg4Ukh9poSZAO1AHVcamQmdr5tG0eLM0I0y/Drtv1AlSWR0CRHz+XtM785p0/f1LcHIVUGirLuUnGB8vCF5mFRKAuL+l+3GYM8H63MHrqmZtHJ4XiAWNTrQ3D2McgbCnEq2l/7e0Dv/39e+d+6S8Zi5OAlUvrmpIXZ1n+Efx80P6LDWMQJxBCJo7J+O8vJgALd95ckg1SgQ7OBqI74X1G1lS1jEbTOkNP+Xfojh2X/nqiyzW3DjOu8myy7eMZlObXDpEj1/b/s0+w8Sl4FZX0V0Jz89uKEuddtk3nUI78E79Y/+xb9Ax4PmQnRzvERMIhQh9EGHCn0MV20e/wrH0KuwFXW71Fh/fm+mG8NEu+OBFd/E5P7PM33IAv+iLy2Fgj4B+iZWGy88Xs3/0DgTf9pHWCdIn7mhr8IQo5C9xgAm5cJyYnE+S2ivXK29O9KgaLsdbrjYOybb1RUEo0MEZsHLe7ju86DiiiHZWUhAb5uuldCU/h6goL+9vzPiIG9wiSTaLfjN2WgjO2WrY2C56VLzL1I9OgZHZSwuNlTDkJmmmH2bNQEwmqlRBTMhO6GZBxMb/Wm9hErOZry+LH4VZO2mga8g9VeEki8QvJK+m8hAtlEpX5BsmDc6yvVz2IyufcV+fPqiPf2+He8d2VN/J9Ri778q/+89PIwvWrNTJ+kJvWdp3NMr1szzbYcmNVGbtXFFclt7p+Y/3dsZn3p9xtN3hE9GK/sHfx6v2blZN+0lT0n6qKrpJosNJr2m0GBakxXuE7/UWldqKydkJbC8q8IeGb5b7jZtZ6jhO0bJODezPV4zodktcbEl72Gqp2mD/cYG0SuzEIINfLwvK+DaPFwvK+DZfEHSkKhG5M7oC2lZDmv0j4zq0eUnik53RtksnPJHyr/ul8MlnJEOmtVSyMFAKpZrwI6ZQGitGwe4STDqqqRVbVyXHU1rJwv83KQpgdm//6R5ET865/u6+FTVUFezmN+2IQW96G3V0YZRsb7SIohv/0yuHLFw4f9Utx93DSPGtqai0rfQLapwkJfEbCtXWshN2j1TZBkjUSdn+sTfCvKrjONtCu6A7AG9NmWXOWFRSMs9laR7gT2r2EtD4pIVvaEZBElXD/Eo7weIMjPIHdIU7aZcZWh+YE3oy0IgJ4tLSmTVNn1Kh1qvXQhkHCfgk7l3YqJOmQsOXWUsHxBmvfwO5pjpewtIeKM0/Wknw7tL2w7TUJzy9NciT5voTP3Zrk8w3WTmP3TXjyTzFejNWYac3h/K5a8m+BNg7jUxI6S5MfSWwJ9VuT/0yDte9itwApqlz+WrKLl2QK2iRUKNMS1gvnemUAvJDwI4wn+4YktzUSdtQ/lt9lV8i+Nd9ShawbxKyrmwrV8wX8ZeX4buX8kpD7QgMVvYrd80Bs4evIcZI0a+numyWbhyiTVxUWyutqvFjl1xMl9gY79/6ebSvrvFbXVH3PknQXz4baVp/d92vxACt+GQnC+yaT0/XygrJsHLBsltGE+EG3vLQEuMTJiho3FmRwBOJMr7mYP4IDV2Jy8WkJR+V4l8GqLh7++7GwWk+pK2i+W/LCEjriltANbseenI2f+Rb/tvpfgbbx6+KhBebo/+MfHs/svTl0fOHqgYsn7/1i95GLi+/e+fxTva+9d6kp/Ny35v4HjYXcYH4UAAA=";
}
