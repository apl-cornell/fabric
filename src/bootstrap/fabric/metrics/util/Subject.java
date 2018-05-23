package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collection;
import fabric.util.List;
import fabric.worker.metrics.ImmutableObserverSet;

/**
 * Represents an observable object that can be monitored by {@link Observer}s.
 * After a {@link Subject} changes, the API uses the current set of
 * {@link Observer}s returned by {@link #getObservers()} to compute resulting
 * changes for {@link Observer}s due to the update.
 */
public interface Subject extends fabric.lang.Object {
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
   * @return the set of the current observers of this subject.
   */
    public fabric.worker.metrics.ImmutableObserverSet getObservers();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.Subject {
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
        
        public fabric.worker.metrics.ImmutableObserverSet getObservers() {
            return ((fabric.metrics.util.Subject) fetch()).getObservers();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 93, -98, 111, 0, -19,
    100, -44, -4, -14, -65, 71, -114, -17, -42, 62, 42, -11, -59, 104, -16, 42,
    110, 89, 2, 91, 4, 94, -5, -14, -112, 87, -102 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1527096999000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
