package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collection;

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
    public fabric.util.Collection getObservers();
    
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
        
        public fabric.util.Collection getObservers() {
            return ((fabric.metrics.util.Subject) fetch()).getObservers();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 84, -82, 115, 25, -69,
    -119, -88, -64, -110, 105, 90, -34, 35, -20, 117, 121, 2, 82, 98, 18, 124,
    121, 21, -109, -67, 106, 85, -48, 17, 59, 10, 45 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1518448064000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
