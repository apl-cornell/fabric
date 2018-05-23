package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collection;
import fabric.util.Collections;
import java.util.LinkedList;
import fabric.util.Set;
import fabric.util.ArrayList;
import fabric.util.List;
import java.util.Iterator;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.Store;

/**
 * Base implementation of {@link Subject}
 */
public interface AbstractSubject
  extends fabric.metrics.util.Subject, fabric.lang.Object {
    public fabric.worker.Store getStore();
    
    public fabric.metrics.util.AbstractSubject
      fabric$metrics$util$AbstractSubject$();
    
    public void addObserver(fabric.metrics.util.Observer o);
    
    public void removeObserver(fabric.metrics.util.Observer o);
    
    public boolean observedBy(fabric.metrics.util.Observer o);
    
    public boolean isObserved();
    
    public fabric.worker.metrics.ImmutableObserverSet getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.AbstractSubject {
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
        
        public static void static_removeObserver(
          fabric.metrics.util.AbstractSubject arg1,
          fabric.metrics.util.Observer arg2) {
            fabric.metrics.util.AbstractSubject._Impl.static_removeObserver(
                                                        arg1, arg2);
        }
        
        public boolean observedBy(fabric.metrics.util.Observer arg1) {
            return ((fabric.metrics.util.AbstractSubject) fetch()).observedBy(
                                                                     arg1);
        }
        
        public boolean isObserved() {
            return ((fabric.metrics.util.AbstractSubject) fetch()).isObserved();
        }
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
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
            fabric$lang$Object$();
            this.set$$observers(
                   fabric.worker.metrics.ImmutableObserverSet.emptySet());
            return (fabric.metrics.util.AbstractSubject) this.$getProxy();
        }
        
        public void addObserver(fabric.metrics.util.Observer o) {
            fabric.metrics.util.AbstractSubject._Impl.
              static_addObserver((fabric.metrics.util.AbstractSubject)
                                   this.$getProxy(), o);
        }
        
        private static void static_addObserver(
          fabric.metrics.util.AbstractSubject tmp,
          fabric.metrics.util.Observer o) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (!tmp.get$$observers().contains(o))
                    tmp.set$$observers(tmp.get$$observers().add(o));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm623 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled626 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff624 = 1;
                    boolean $doBackoff625 = true;
                    boolean $retry620 = true;
                    $label618: for (boolean $commit619 = false; !$commit619; ) {
                        if ($backoffEnabled626) {
                            if ($doBackoff625) {
                                if ($backoff624 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff624);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e621) {
                                            
                                        }
                                    }
                                }
                                if ($backoff624 < 5000) $backoff624 *= 2;
                            }
                            $doBackoff625 = $backoff624 <= 32 || !$doBackoff625;
                        }
                        $commit619 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$$observers().contains(o))
                                tmp.set$$observers(tmp.get$$observers().add(o));
                        }
                        catch (final fabric.worker.RetryException $e621) {
                            $commit619 = false;
                            continue $label618;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e621) {
                            $commit619 = false;
                            fabric.common.TransactionID $currentTid622 =
                              $tm623.getCurrentTid();
                            if ($e621.tid.isDescendantOf($currentTid622))
                                continue $label618;
                            if ($currentTid622.parent != null) {
                                $retry620 = false;
                                throw $e621;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e621) {
                            $commit619 = false;
                            if ($tm623.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid622 =
                              $tm623.getCurrentTid();
                            if ($e621.tid.isDescendantOf($currentTid622)) {
                                $retry620 = true;
                            }
                            else if ($currentTid622.parent != null) {
                                $retry620 = false;
                                throw $e621;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e621) {
                            $commit619 = false;
                            if ($tm623.checkForStaleObjects())
                                continue $label618;
                            $retry620 = false;
                            throw new fabric.worker.AbortException($e621);
                        }
                        finally {
                            if ($commit619) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e621) {
                                    $commit619 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e621) {
                                    $commit619 = false;
                                    fabric.common.TransactionID $currentTid622 =
                                      $tm623.getCurrentTid();
                                    if ($currentTid622 != null) {
                                        if ($e621.tid.equals($currentTid622) ||
                                              !$e621.tid.isDescendantOf(
                                                           $currentTid622)) {
                                            throw $e621;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit619 && $retry620) {
                                {  }
                                continue $label618;
                            }
                        }
                    }
                }
            }
        }
        
        public void removeObserver(fabric.metrics.util.Observer o) {
            fabric.metrics.util.AbstractSubject._Impl.
              static_removeObserver((fabric.metrics.util.AbstractSubject)
                                      this.$getProxy(), o);
        }
        
        public static void static_removeObserver(
          fabric.metrics.util.AbstractSubject tmp,
          fabric.metrics.util.Observer o) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$$observers().contains(o))
                    tmp.set$$observers(tmp.get$$observers().remove(o));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm632 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled635 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff633 = 1;
                    boolean $doBackoff634 = true;
                    boolean $retry629 = true;
                    $label627: for (boolean $commit628 = false; !$commit628; ) {
                        if ($backoffEnabled635) {
                            if ($doBackoff634) {
                                if ($backoff633 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff633);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e630) {
                                            
                                        }
                                    }
                                }
                                if ($backoff633 < 5000) $backoff633 *= 2;
                            }
                            $doBackoff634 = $backoff633 <= 32 || !$doBackoff634;
                        }
                        $commit628 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$$observers().contains(o))
                                tmp.set$$observers(
                                      tmp.get$$observers().remove(o));
                        }
                        catch (final fabric.worker.RetryException $e630) {
                            $commit628 = false;
                            continue $label627;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e630) {
                            $commit628 = false;
                            fabric.common.TransactionID $currentTid631 =
                              $tm632.getCurrentTid();
                            if ($e630.tid.isDescendantOf($currentTid631))
                                continue $label627;
                            if ($currentTid631.parent != null) {
                                $retry629 = false;
                                throw $e630;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e630) {
                            $commit628 = false;
                            if ($tm632.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid631 =
                              $tm632.getCurrentTid();
                            if ($e630.tid.isDescendantOf($currentTid631)) {
                                $retry629 = true;
                            }
                            else if ($currentTid631.parent != null) {
                                $retry629 = false;
                                throw $e630;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e630) {
                            $commit628 = false;
                            if ($tm632.checkForStaleObjects())
                                continue $label627;
                            $retry629 = false;
                            throw new fabric.worker.AbortException($e630);
                        }
                        finally {
                            if ($commit628) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e630) {
                                    $commit628 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e630) {
                                    $commit628 = false;
                                    fabric.common.TransactionID $currentTid631 =
                                      $tm632.getCurrentTid();
                                    if ($currentTid631 != null) {
                                        if ($e630.tid.equals($currentTid631) ||
                                              !$e630.tid.isDescendantOf(
                                                           $currentTid631)) {
                                            throw $e630;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit628 && $retry629) {
                                {  }
                                continue $label627;
                            }
                        }
                    }
                }
            }
        }
        
        public boolean observedBy(fabric.metrics.util.Observer o) {
            return this.get$$observers().contains(o);
        }
        
        public boolean isObserved() { return !this.get$$observers().isEmpty(); }
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
            return this.get$$observers();
        }
        
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
                fabric.worker.metrics.ImmutableObserverSet obs =
                  sm.getObservers();
                for (java.util.Iterator iter = obs.iterator(); iter.hasNext();
                     ) {
                    queue.add(iter.next());
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
                    fabric.worker.metrics.ImmutableMetricsVector leaves =
                      unhandled.getLeafSubjects();
                    for (int i = 0; i < leaves.length(); i++) {
                        if (unobserved.
                              contains(
                                (java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.
                                  $unwrap(leaves.get(i)))) {
                            needToWait = true;
                            break;
                        }
                    }
                    if (!needToWait) {
                        fabric.worker.metrics.ImmutableObserverSet
                          parentsToCheck = unhandled.handleUpdates();
                        for (java.util.Iterator iter =
                               parentsToCheck.iterator();
                             iter.hasNext();
                             ) {
                            queue.add(iter.next());
                        }
                    }
                    else {
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
                            fabric.worker.metrics.ImmutableMetricsVector
                              withheldLeaves = withheld.getLeafSubjects();
                            for (int i = 0; i < withheldLeaves.length(); i++) {
                                if (unobserved.
                                      contains(
                                        (java.lang.Object)
                                          fabric.lang.WrappedJavaInlineable.
                                          $unwrap(withheldLeaves.get(i)))) {
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
                return new fabric.metrics.util.AbstractSubject._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -86, 30, -58, -82, 94,
    96, 24, 125, -115, -110, 46, -31, 110, -123, -82, 17, -98, 1, -41, -115, 25,
    -98, -28, 2, 2, 99, -118, 21, 17, -41, 21, 35 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527097933000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUYbWwcR3Xu7Ng+x+T8EaeJkziOfQ3ETe8I8Kc1X7GVNEcujeVLJHAg7tzunL313u5mdu58bjEKFVWiqPWP1klbpLqqakQJIW2RCj8googqTdWC+FJDQUCQqNqqBKlCAn4A5b3Zudvz3vliS3DSztubee/N+34ze/EGWedy0p+lGcOMi1mHufEDNJNMjVLuMn3EpK57FGYntPWNyfPvfFPvDZNwirRp1LItQ6PmhOUKsiF1Ly3QhMVE4thYcug4iWhIeJC6U4KEjw8XOelzbHN20rSF2qSK/7nbEguPnWj/bgOJjpOoYaUFFYY2YluCFcU4acuxXIZxd5+uM32cdFiM6WnGDWoa9wGibY2TTteYtKjIc+aOMdc2C4jY6eYdxuWepUkU3waxeV4TNgfx2z3x88IwEynDFUMp0pQ1mKm7J8lXSGOKrMuadBIQN6VKWiQkx8QBnAf0VgPE5FmqsRJJ47Rh6YLsCFKUNY4dAgQgbc4xMWWXt2q0KEyQTk8kk1qTibTghjUJqOvsPOwiSM+KTAGpxaHaNJ1kE4JsDuKNekuAFZFmQRJBuoNokhP4rCfgswpv3bj7k/P3WwetMAmBzDrTTJS/BYh6A0RjLMs4szTmEbYNps7TTZfPhAkB5O4Asofz/S+//9k9vS9d9XC21sA5krmXaWJCW8ps+MW2kd13NKAYLY7tGhgKyzSXXh1VK0NFB6J9U5kjLsZLiy+NXfnCqQvsvTBpTZImzTbzOYiqDs3OOYbJ+F3MYpwKpidJhFn6iFxPkmZ4TxkW82aPZLMuE0nSaMqpJlv+BxNlgQWaqBneDStrl94dKqbke9EhhLTDQ0LwLBGy8ScAewgJ5wUZS0zZOZbImHk2A+GdgIdRrk0lIG+5oSVcriV43hIGIKkpiCIArqf/vgyEO9VEOi8NFwdpnP8L1yLq0j4TCoGZd2i2zjLUBZ+p+BkeNSFFDtqmzviEZs5fTpKuy0/IGIpg3LsQu9JKIfD7tmDFqKRdyA/vf//SxGte/CGtMqIgA56ocSWq5+OAqCBdGyZYHEpWHErWxVAxPrKY/LaMoyZXJlyZYRswvNMxqcjaPFckoZDUbqOkl8zB/dNQVqBytO1Of+lz95zpb4DIdWYa0ZmAGgvmkV99kvBGITkmtOjpd/7+3Pk5288oQWJViV5NiYnaHzQVtzWmQyH02Q/20RcnLs/FwlhkIlD/BIUIhWLSG9xjWcIOlYofWmNdiqxHG1ATl0oVq1VMcXvGn5EhsAGHTi8a0FgBAWXd/FTaefI3P3v347KjlEpstKIWp5kYqkhrZBaVCdzh2/4oZwzwfv/46KPnbpw+Lg0PGAO1NozhOALpTCGPbf7g1ZNv/vEPS78O+84SpMnJZ0xDK0pdOj6AXwie/+CDuYkTCKFCj6i60FcuDA7uvMuXDUqECcEGoruxY1bO1o2sQTMmw0j5V/TWvS/+Zb7dc7cJM57xONlzcwb+/JZhcuq1E//olWxCGrYo334+mlf3unzO+zinsyhH8au/3P7EK/RJiHyoWq5xH5OFiEh7EOnAj0lb3C7HvYG1T+DQ71lrm5qXfwbkuAuH3XI+jK+DgrRQlYrKxET9oqraCQVlQnc5OG6sYB+S792CbK2V5iq9EaWnCBpvX6mDye679MDCon7kG3u9PtO5vCvst/K577zx79fjj19/tUaViQjbud1kBWZWCNcKW+6sOkodlg3eT8Xr722/Y2T6rUlv2x0BEYPY3zp88dW7dmmPhElDuS5UnSqWEw1VCgsJyhkciixUG2dapeP6ytaPoPW3wLMdrP62gtcqrK+yuKZXQ9KrvivDyKxFMXlDwZ8HXVk7lg7VWTuMw36Inkkm0pC7rBQGXSoMZmw+zXjcX9sSLNxydrgsaRvyHoZnJyENSQW7Vqs2BIDDbQGxxvSA9usVr04FI6vTfrzO2hdxSEM/8JSNqZiPYaDGAq0t5os9vNzHt8LzERDoXQV/uoKyOBys9iiSvK7glZV1Ci1P02210vRIxmW8wLiXpzhqdbSXnfiEIOuprpco3eqD5ig3ctAnCuqgyc4snP0gPr/g5a53Gh+oOhBX0ngncrnlh3C4DSvIznq7SIoDbz8394Nn506HlbifFqSxYBt6wAWy0GGc7SGk8biCA6uMN6iKzQ43CtBlBJ5P8GoUCLt2xbJfwY0ruyjsF+R2HHS59UwdF8ziYEPT87aeqPAErkzXCjeIEzJESNOdCm5dW7ghSY+CdXQJ+WXIU+NUHTUewOF+uK9ylrMLrJ4K8iR+CJ7PwP4vKFhYrbtkVcRBBLwUVZzyCk6t1Utn66j3EA5fgwud8tLNtZSO+jA8d4PRzytYXJujkGRGwZNrcNSjdTQ5h8O8IK22J7w+PCvxDJWXCKAPNmds22TUqqUVNB6SBpH+quBv16YVkryp4K9WV8GfqrP2NA5fB4UMV/lDx5nHakkO2Us+D+9HFdy7NsmR5KMKDq5O8mfrrF3A4RloptB5y9W3VN0Hl3ffUpFP5nJ5gQfVEgGc0FfVlGXWYcHQ4TD1OwVf+J9kHXJ6XsFnbhqmJQVr3vJKqkRQFdPWqFmUcn2vjhl/iMPzUHocvJu5bprmHNM7qUwX4doT6OJ4XN9a4/6svu1oIy+zpbcO7ele4e68ueprm6K7tBhtuWXx2DV5DSx/t4nALSubN82KQ2PlAbLJ4SxrSDUi3u3OkeDH/vGrsr1D/0MgdfuRh/kyKL4cU8gPX/hWifcKdDcPD/9dlR7q8YeSVzoVL/w8Fve+BdUOL8m0J8/xI+TFv93yz6aWo9fldQ/c0neh98qlE/dsnnv4kfifrAcvdSyGrj28ZfHP4bB2trvjWvfAfwGtp0JXHBUAAA==";
}
