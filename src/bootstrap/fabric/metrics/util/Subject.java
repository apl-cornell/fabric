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
   * Adds an observer to the set of observers for this object. Nothing is done
   * if the given observer {@link #equals(Object) equals} an existing
   * observer.
   *
   * @param o
   *        {@link Observer} to add
   * @param id
   *        id of a treaty in the observer.
   */
    public void addObserver(fabric.metrics.util.Observer o, long id);
    
    /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   */
    public void removeObserver(fabric.metrics.util.Observer o);
    
    /**
   * Removes an observer from the set of observers of this object.
   *
   * @param o
   *        {@link Observer} to remove
   * @param id
   *        id of a treaty in the observer.
   */
    public void removeObserver(fabric.metrics.util.Observer o, long id);
    
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
        
        public void addObserver(fabric.metrics.util.Observer arg1, long arg2) {
            ((fabric.metrics.util.Subject) fetch()).addObserver(arg1, arg2);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1) {
            ((fabric.metrics.util.Subject) fetch()).removeObserver(arg1);
        }
        
        public void removeObserver(fabric.metrics.util.Observer arg1,
                                   long arg2) {
            ((fabric.metrics.util.Subject) fetch()).removeObserver(arg1, arg2);
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
    
    public static final byte[] $classHash = new byte[] { -99, 121, 93, 110, -89,
    62, -101, 64, -103, -108, 21, -89, 47, -124, -115, -104, 6, -88, -26, 46,
    -53, -96, 32, -14, -100, 5, 85, 11, -89, 101, 109, 49 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1528903876000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
