package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collection;
import fabric.util.List;

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
    public fabric.util.List getObservers();
    
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
        
        public fabric.util.List getObservers() {
            return ((fabric.metrics.util.Subject) fetch()).getObservers();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -29, -30, 123, -119,
    58, -120, 110, -8, -17, 17, 29, 74, -27, -65, 78, 40, -51, -22, 42, 26, -34,
    -127, 22, 31, 34, 81, -68, 91, -67, 123, 79, 60 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1520977993000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
