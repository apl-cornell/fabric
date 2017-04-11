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

/**
 * Represents an observable object having a set of {@link Observer}s. After an
 * observable object changes, an application can call
 * {@link #getObserversCopy()} to get the current set of {@link Observer}s.
 * {@link Observer}s are then notified of a change via a call to
 * {@link Observer#handleUpdates()}.  */
public interface Subject extends fabric.lang.Object {
    public fabric.metrics.util.Subject fabric$metrics$util$Subject$();
    
    public fabric.util.Set get$observers();
    
    public fabric.util.Set set$observers(fabric.util.Set val);
    
    public boolean get$modified();
    
    public boolean set$modified(boolean val);
    
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
   * Mark this subject as modified.
   */
    public void markModified();
    
    /**
   * Clear the modified flag on this subject.
   */
    public void clearModified();
    
    /**
   * @return true iff the modified flag is set.
   */
    public boolean isModified();
    
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
        
        public boolean get$modified() {
            return ((fabric.metrics.util.Subject._Impl) fetch()).get$modified();
        }
        
        public boolean set$modified(boolean val) {
            return ((fabric.metrics.util.Subject._Impl) fetch()).set$modified(
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
        
        public void markModified() {
            ((fabric.metrics.util.Subject) fetch()).markModified();
        }
        
        public void clearModified() {
            ((fabric.metrics.util.Subject) fetch()).clearModified();
        }
        
        public boolean isModified() {
            return ((fabric.metrics.util.Subject) fetch()).isModified();
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
        
        public boolean get$modified() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.modified;
        }
        
        public boolean set$modified(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.modified = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private boolean modified = false;
        
        /**
   * Adds an observer to the set of observers for this object. Nothing is done
   * if the given observer {@link #equals(Object) equals} an existing
   * observer.
   *
   * @param o
   *        {@link Observer} to add
   */
        public void addObserver(fabric.metrics.util.Observer o) {
            this.get$observers().add(o);
        }
        
        /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   */
        public void removeObserver(fabric.metrics.util.Observer o) {
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
            return ((fabric.util.HashSet)
                      new fabric.util.HashSet._Impl(this.$getStore()).$getProxy(
                                                                        )).
              fabric$util$HashSet$(this.get$observers());
        }
        
        /**
   * Mark this subject as modified.
   */
        public void markModified() { this.set$modified(true); }
        
        /**
   * Clear the modified flag on this subject.
   */
        public void clearModified() { this.set$modified(false); }
        
        /**
   * @return true iff the modified flag is set.
   */
        public boolean isModified() { return this.get$modified(); }
        
        /**
   * @return the set of the current observers of this subject.
   */
        public fabric.util.Set getObservers() { return this.get$observers(); }
        
        /**
   * Utility for processing a batch of samples for the transaction manager.
   */
        public static void processSamples(java.util.LinkedList unobserved,
                                          java.util.List extensions) {
            while (!unobserved.isEmpty()) {
                fabric.metrics.SampledMetric
                  sm =
                  (fabric.metrics.SampledMetric)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(
                                                          unobserved.poll()));
                sm.clearModified();
                java.util.LinkedList queue = new java.util.LinkedList();
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
                        fabric.util.Set parents =
                          fabric.util.Collections._Static._Proxy.$instance.
                          get$EMPTY_SET();
                        if (fabric.lang.Object._Proxy.
                              $getProxy(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      unhandled)) instanceof fabric.metrics.util.Subject) {
                            parents =
                              ((fabric.metrics.util.Subject)
                                 fabric.lang.Object._Proxy.$getProxy(
                                                             unhandled)).
                                getObserversCopy();
                        }
                        unhandled.handleUpdates();
                        if (fabric.lang.Object._Proxy.
                              $getProxy(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      unhandled)) instanceof fabric.metrics.util.Subject &&
                              ((fabric.metrics.util.Subject)
                                 fabric.lang.Object._Proxy.$getProxy(
                                                             unhandled)).
                              isModified()) {
                            fabric.metrics.util.Subject s =
                              (fabric.metrics.util.Subject)
                                fabric.lang.Object._Proxy.$getProxy(unhandled);
                            if (fabric.lang.Object._Proxy.
                                  $getProxy(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(s)) instanceof fabric.metrics.contracts.Contract) {
                                extensions.
                                  remove(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                          s));
                            }
                            fabric.util.Iterator parentIter =
                              parents.iterator();
                            while (parentIter.hasNext()) {
                                queue.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(parentIter.next()));
                            }
                            s.clearModified();
                        }
                        if (fabric.lang.Object._Proxy.
                              $getProxy(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      unhandled)) instanceof fabric.metrics.contracts.Contract &&
                              ((fabric.metrics.contracts.Contract)
                                 fabric.lang.Object._Proxy.$getProxy(
                                                             unhandled)).
                              extended()) {
                            fabric.metrics.contracts.Contract curContract =
                              (fabric.metrics.contracts.Contract)
                                fabric.lang.Object._Proxy.$getProxy(unhandled);
                            curContract.clearExtended();
                            if (!extensions.
                                  contains(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(curContract)))
                                extensions.
                                  add(
                                    (java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.
                                      $unwrap(curContract));
                        }
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
            out.writeBoolean(this.modified);
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
            this.modified = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.Subject._Impl src =
              (fabric.metrics.util.Subject._Impl) other;
            this.observers = src.observers;
            this.modified = src.modified;
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
    
    public static final byte[] $classHash = new byte[] { 46, -79, 60, -68, -31,
    34, 21, -47, 87, -43, 21, 15, 0, -68, -60, -20, 8, 10, -69, -11, 31, -115,
    -106, -124, 69, 104, 114, 105, 14, 34, -2, 12 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491931564000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1YfWwcxRWfO3+ebXy2EwewHcc4R9qE5E4xqBJxaLFPCb7kUps4SVtH4M7tztmL93aX3TnnkhJEPyKnVXFp4wQMxKqqVEBwoUVF/QiW+CMtIKJWaWmBSm3TP1CpQiqhirZ/tND3Zue+1neHLfWkmTc3896b37x5783MLl4jNY5NepM0oelhftRiTng3TcTiI9R2mBrVqeMcgN5xpbE6dubdp9RuP/HHSZNCDdPQFKqPGw4nzfH76DSNGIxHDu6P9R8mAQUFh6gzyYn/8GDGJj2WqR+d0E0uJ1mm//QtkblH7215oYoEx0hQM0Y55ZoSNQ3OMnyMNKVYKsFsZ0BVmTpGWg3G1FFma1TXjgGjaYyRNkebMChP28zZzxxTn0bGNidtMVvMme1E+CbAttMKN22A3+LCT3NNj8Q1h/fHSW1SY7rq3E8eJNVxUpPU6QQwrotnVxERGiO7sR/YGzSAaSepwrIi1VOaoXKywSuRW3FoLzCAaF2K8UkzN1W1QaGDtLmQdGpMREa5rRkTwFpjpmEWTjrKKgWmeosqU3SCjXNyg5dvxB0CroAwC4pw0u5lE5pgzzo8e1awW9c+u3P2S8aQ4Sc+wKwyRUf89SDU7RHaz5LMZobCXMGmLfEzdN3SST8hwNzuYXZ5fvLA+3du7X75VZenswTPcOI+pvBx5Vyi+XJXdPPtVQij3jIdDV2haOViV0fkSH/GAm9fl9OIg+Hs4Mv7f/mFh86zq37SECO1iqmnU+BVrYqZsjSd2Xcxg9mUMzVGAsxQo2I8RuqgHdcM5vYOJ5MO4zFSrYuuWlP8BxMlQQWaqA7ampE0s22L8knRzliEkBYoxAflLCGtl4C2EeK/m5M9kUkzxSIJPc2OgHtHoDBqK5MRiFtbUyKOrUTstME1YJJd4EVAHHf9o2lhsDCgsP6v2jKIveWIzwdm3aCYKktQB/ZI+svgiA4hMWTqKrPHFX12KUbWLM0Lnwmgnzvgq8IqPtjnLm+GKJSdSw/uev+58dddf0NZaTROOl2IYQnR3VMJEVA1YSCFITWFITUt+jLh6ELsWeEvtY4IrJyiJlC0w9IpT5p2KkN8PrGqtUJeKIVtnoL0ARmiafPoPXu+eLK3CjzUOlKNmwasIW+85LNMDFoUgmBcCc68+8/nzxw385HDSWhZQC+XxIDs9ZrINhWmQsLLq9/SQ18cXzoe8mMyCUCe4xQ8EZJGt3eOosDszyY5tEZNnDSiDaiOQ9nM1MAnbfNIvkdsfTNWba4XoLE8AEV+vGPUOvvWr/52qzg5sqk0WJBzRxnvLwhfVBYUgdqat/0BmzHg++NjI6dOX5s5LAwPHBtLTRjCOgphSyFeTfvEq/e//ec/nXvDn98sTmqtdELXlIxYS+tH8PNB+RALxiB2IIVMHJXx35NLABbOvCmPDVKBDs4G0J3QQSNlqlpSowmdoaf8J3jz9hffm21xt1uHHtd4Ntn68Qry/TcOkodev/df3UKNT8GjKG+/PJub39bkNQ/YNj2KODJf/s36+VfoWfB8yE6OdoyJhEOEPYjYwD5hi22i3u4Zuw2rXtdaXaLf7yzP9bvx0Mz74lhk8cmO6KevugGf80XUcVOJgD9EC8Kk73zqA39v7S/8pG6MtIjzmhr8EIWcBW4wBieuE5WdcXJd0Xjx6ekeFf25WOvyxkHBtN4oyCcaaCM3thtcx3cdBwzRgEYahdJJSNUJSXfi6BoL67UZHxGNHUJko6g3YbU564x1lq1Ng2dlckr9qDQglfVLeluBUk4CZsJh9jTciYRUO8SUzIRuBmTC0zrc0MT6U8WQN0JZD1rPS/rtEpCjLmSs7liODaUekfTrRdjqXR9magknGbG1FMT5tLwQsJNz3/goPDvnBoh7a9q47OJSKOPenMQuXIfVLRmY5aZKswiJ3X99/viFp4/PuLeKtuI7wC4jnfrB7/97KfzYlddKnDF1CdPUGRWpqSVTeh/92NwCa6cJyN1U4fndFL+gPM5HJB0osFhBXBFczfpyNy+xknNfmVtQh7+/3S+DMw7OwE1rm86mmV6gqhHtsuxmv0/cN/ORduXq+tujU+9MuHbZ4JnZy/3MvsXX7tqkfMdPqnIhteySWyzUXxxIDTaDO7pxoCicenK2akIb9EFZCzaak9Qo9M28R5eKpYBlmxwinqmeaGqUulKSql77l858iQpjQsVhyClu4IXkFSSELhWSV5BQHu7nc4AwesjNUD5BSPVPJX1ypYsUjuZZXb1U8oSkp8uvzicvLDJrdJW6Pw3L3CJSiACjV7CDmCbJSSNV1aykYByS8YlkLyfV06amljLFJ6GA0poZSSfLmAIrbfnCUWRCUvqxC8e/KaH1WIU1PYBVGt62NkuZ06zQIHapJYDRyQ5Cau+W9NbVLQFF+iTduoolfLXCEk5g9SAnDfKkUAePYs+eUvAhh5MBmPvvkv5hdfBR5G1Jf7uywPpmhbFZrGY4nvLS8GpZ5NugDBFSNy9penXIUYRLaqwM+ekKY49i9QgnLROM50IhalrC8gOl8HdBGQYsEUm7VocfRTolbV8Z/oUKY9/Fah4eQylqT+2Tp3hZp0evOQQTPy3p3Oqwo8gpSR9eGfanKow9g9X3OFwH4ay2VwT+HmiHJG1ZHXgUCUoaWBn4H1YYewGrZ4XLFyIv6fKboajQ/rmkj68OOYrMS3pqZch/VmHsAlY/BpcpdPlS7i6+boxDmYIr6LckvbMM9nLXK3i144dBz/EXlNo+I2lf+WX55fMF/8PxV/J9LwZvhMsEfhLQTYXqmSx/cyG/+5p7ReC+WMFEl7BaAmELX+yOM0pTlu6+o+0MXC/lXQEfb50lvqLIL3pK9CI7987ere1lvqDcsOwbq5R7biFYf/3CwTfFR4Hc17oAvLmTaV0vfOQUtGstmyU1AT/gPnksQS5zsqbElQFOdyRiTb92Od+ABRdzcvG5E1uFfG/Crrp8+O8tsWsd+Spr+TapC591YfdZl92n4k83QmlH2sZPz4v/uP7ftfUHrojHP2xHT/hHO1/6S2/75c/9rj1IXrr4Xn3DhQ82PHzma7smba2598Om/wGZzp+IEhcAAA==";
}
