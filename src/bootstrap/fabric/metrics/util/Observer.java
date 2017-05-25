package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Set;
import fabric.metrics.SampledMetric;

/**
 * Abstraction for tracking trees of observers/subjects. Unifies tracking
 * between measure trees and contract trees.
 *
 * TODO: bulk handling of updates rather than per-update handling.
 */
public interface Observer extends fabric.lang.Object {
    /**
   * Handles observation of subjects. This is called whenever any associated
   * {@link Subject}s are changed prior to the transaction completing.
   *
   * @return true iff there was a modification that needs to be processed by
   * any parents (if any) of this object.
   */
    public boolean handleUpdates();
    
    /**
   * @return the set of {@link SampledMetric}s this Observer observes at the
   *         bottom of the dependency tree.
   */
    public fabric.util.Set getLeafSubjects();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.Observer {
        public boolean handleUpdates() {
            return ((fabric.metrics.util.Observer) fetch()).handleUpdates();
        }
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.util.Observer) fetch()).getLeafSubjects();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 70, 11, 72, 77, -9,
    -100, -7, -85, 20, -40, -24, 125, 100, -120, 102, 43, -19, 96, 18, 22, -100,
    39, 103, -124, -37, 121, 4, -74, -15, -50, -85, -28 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1495740956000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
