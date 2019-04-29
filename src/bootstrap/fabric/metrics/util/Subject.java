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
    long jlc$SourceLastModified$fabil = 1556306458000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
