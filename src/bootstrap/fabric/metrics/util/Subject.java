package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Set;

/**
 * Represents an observable object having a set of {@link Observer}s. After an
 * observable object changes, an application can call
 * {@link #getObserversCopy()} to get the current set of {@link Observer}s.
 * {@link Observer}s are then notified of a change via a call to
 * {@link Observer#handleUpdates()}.
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
    
    public static final byte[] $classHash = new byte[] { 74, 21, 15, 19, -90,
    -127, -44, -43, -35, -75, -64, -8, -106, 105, 12, -21, 117, -38, 104, -86,
    -55, 32, -17, 84, 106, -127, -106, 44, 8, -42, 82, -72 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1495740956000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
