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
   * Handles a new observation. This is called whenever any associated
   * {@link Subject}s are changed prior to the transaction completing.
   */
    public void handleUpdates();
    
    /**
   * @return the set of {@link SampledMetric}s this Observer observes at the
   *         bottom of the dependency tree.
   */
    public fabric.util.Set getLeafSubjects();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.Observer {
        public void handleUpdates() {
            ((fabric.metrics.util.Observer) fetch()).handleUpdates();
        }
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.util.Observer) fetch()).getLeafSubjects();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -92, 79, 67, -24, -62,
    -30, -115, 56, 51, -63, 36, 104, 60, 65, 118, -16, 121, 43, -26, 47, -41,
    125, -83, -35, -70, 126, -66, 100, -97, -100, -2, 16 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1492101363000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
