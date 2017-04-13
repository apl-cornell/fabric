package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;

/**
 * A warrantiable computation. Returns values wrapped in a {@link WarrantyValue}
 * along with an appropriate {@link Metric} for the result.
 */
public interface WarrantyComp extends fabric.lang.Object {
    /**
   * Run this warranty computation at the given time.
   */
    public fabric.metrics.contracts.warranties.WarrantyValue apply(long time);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.metrics.contracts.warranties.WarrantyValue apply(
          long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 14, -72, -89, -100,
    -105, -69, 100, -68, 12, -11, -65, -94, -89, -45, -88, 22, 85, -86, -93, 17,
    90, 27, 40, 126, -72, 22, -27, -9, 67, -26, 31, -120 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1492109732000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
