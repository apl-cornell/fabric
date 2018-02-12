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
