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
    long jlc$SourceLastModified$fabil = 1548260582000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
