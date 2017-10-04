package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Set;

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
    public fabric.util.Set getObservers();
    
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
        
        public fabric.util.Set getObservers() {
            return ((fabric.metrics.util.Subject) fetch()).getObservers();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 59, -6, 36, 25, 3,
    -113, 43, 76, -83, -75, -75, 119, -13, -76, -28, 45, -105, 22, -17, -72,
    -87, 69, 38, -14, 125, 2, -29, 38, 81, 27, -72, -123 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1506966071000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
