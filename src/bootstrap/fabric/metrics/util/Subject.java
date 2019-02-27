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
    long jlc$SourceLastModified$fabil = 1550000445000L;
    java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUYa2wcxXnufH6cY+JHSAJOYgeTmMYkd40qVQK3lPiUONcctWUnqhIrMXO7c/bivd3N7Jx9BkKToippf1g8DIRKuA8lagGTvoT6oBFI5Vkq6COFtlIhrYQKDVEhFSpV09Lvm9m7XZ8v54QkJ803ezPfN/O9v5mZPU2qXU46MjRtmDEx6TA3toWmk6l+yl2mJ0zqutthdFhbFEk++PZ39LYwCadIg0Yt2zI0ag5briCLU7fRcRq3mIjvGEh2D5GohoRbqTsqSHioJ8/Jasc2J0dMW3ibzFv/gevj0w/tafphFWncRRoNa1BQYWgJ2xIsL3aRhizLphl3N+k603eRZosxfZBxg5rG7YBoW7tIi2uMWFTkOHMHmGub44jY4uYcxuWehUFk3wa2eU4TNgf2mxT7OWGY8ZThiu4UqckYzNTdveQuEkmR6oxJRwBxWaogRVyuGN+C44BebwCbPEM1ViCJjBmWLkh7KUVR4jXbAAFIa7NMjNrFrSIWhQHSolgyqTUSHxTcsEYAtdrOwS6CtJ5zUUCqc6g2RkfYsCBXleL1qynAikq1IIkgS0vR5Epgs9YSmwWsdfoLn5m6w9pqhUkIeNaZZiL/dUDUVkI0wDKMM0tjirChK/UgXXb8UJgQQF5agqxwfnzn+zevb3vmRYWzogxOX/o2polh7Uh68W9WJtbdUIVs1Dm2a6ArzJFcWrXfm+nOO+Dty4or4mSsMPnMwPM79z/GToVJfZLUaLaZy4JXNWt21jFMxnuZxTgVTE+SKLP0hJxPklr4ThkWU6N9mYzLRJJETDlUY8v/oKIMLIEqqoVvw8rYhW+HilH5nXcIIbXQSAjaRkKqO6C/Av4eE6QvPmpnWTxt5tgEuHccGqNcG41D3HJD26DZzmTc5Vqc5yxhAKYaj4MrQecqJQzmpNZiwIpz6ZfMoxRNE6EQKLhds3WWpi5Yy/Ocnn4TgmOrbeqMD2vm1PEkWXL8Yek9UfR4F7xW6icEFl9ZmiuCtNO5ns3vHxt+WXke0nrqE2SFYjHmsais67EIXDVgSMUgScUgSc2G8rHETPJx6Tk1rgyx4kINsNCNjklFxubZPAmFpFRXSnq5KBh8DBIJ5IqGdYO7P3/roY4q8FVnIoL2y8tYXln4A4Ql8sis8dlB55E/vPLOp2Q+LSSYxkAmGmSiO+DUuGajdN9mn4/tnDHA+/Ph/vsfOH1wSDIBGNeW23ANwgQ4MwUvtvlXXtz7xzffOHIiXGS8SpAaJ5c2DU2QOpoGnVBNCBIt5jYlWPNH8AtB+x82lBEHsIe0lfCCZXUxWhynVB2rzpVWZEo88uXpGb3v6EYV/C1zQ3Wzlcs+8dp/fxU7fPKlMg4QFbazwWTjzAzsuRi2vGZefbtFZt0klAEKuWlYO3lq1Q2JsbdG1LbtJSyWYj96y+xLvZ3afWFS5aW/Mql+LlF3kFmoGJxBpbJQbByph007St2e2xrToZz5+3atpk8OH9+3JoylIgpVTFDIM1AS2ko3n5N2uwsehltVp8gi9Gtq4lSh7tSLUW5P+CMynBcrg4MSo2i8tdCWExJ+1et/hLNLHIRXqvCX+O0SdiBYKy0Qxs9OBNdJtHVgkU7fiSGTmhCh4OPumh1W1taNjEHTJsPwOtu4duOT7041KWObMKK442T9wgv441f3kP0v7/lXm1wmpGEl9wPNR1PlYYm/8ibO6STykT/w21UPv0AfgXQByd01bmcyX4ekfCEp8FJBVpZLQH1pl/FxxhGnVcr/OYl/o4Q3oWK92MD/CQSfFmQR1fUCpTu/qvZzIwtpYdyrquzQ9Nc+ik1NKzWpo8e186p/kEYdP+SWV0ibYGReU2kXSbHlb9/b99R39x0Me+xuECQybhvq+LJxrrt8Alo7pJW7vV7/2O4yV12e0vHvzRKhv4I+BxBsgyMqZ1l7nAWN0VuO6eugdRISiXt98+ViemcFpocQbBek3lbs6j2TEm+zZynstgpSm7Ztk1GrnByroF0P/B/1+nsvkRxBNlmFOVlMbwURDNfTuTT/7nK8rof2STjwtKs+cvYy8GpVmJM7GVD2R5goxlwhpru8mJ6w+RjjxdBOZrM5gUmmQABlWJJcXXp8CEosK2X50rnMP/Cr021M3qEcp5IGqnwNQPGGyw/E6oKq2Fdh7kuKWwQT+YIKmmRCRNZiirWCoFEU1LThGpivxJ+A6mNYVF4R7lDsIbgTwV0I9sOBA3QvDwKFTRv9Tf3xecrFwUMIvhpk/Pw0FmCngkburTB3f+mmBxZy1ID49yC4D8E0iD8Kd+UEnJrLxXkVnMDw8yEEh89T0AtISAckwjcqCPqt8xRULtfpyziD4JsIvg3+yfbmqDqZ7UZwNJAQLlqcILePVZib/ZiSPIrgcQRPgLWErS7lZSIkMFHWW7+P4AeXy1t/WmHuqfMU3d/0YInL/gTBzxD8HNONLYzMJP7rRfC0/Lpcgj1bYe75ixbsFwieQ/ACZDUl2CbTLMr2y0suW/DkWBLuEdO2RiTRqxWIwOvafK9L4h2N5xy4cG3Oa8wp3BjvluucuGgFvYLgdwheBwYnqCGKuvnThehmoTAO+1i/RnBYYr157tz1e4nwlwtOxAd92d5AcBLBX315LrlkQYd9ZyF5Tl2MPG8j+DuCd8vJk4eDo/cogredFWXearwXRC3xLDvy1rb1S8/xTnPVvDddj+7YTGPd8pkdr8travF1MJoidZmcaQZuwcEbcY3DWcaQYkTV7VMdfc4IsqTMvQrcEDupkfcU5gdwzJ+LKeTzKn4F8T6E5KXw8N+/5fGv1QeF8Grx1ipz8Jmb1uWirTmOT92z/1z+YU3d9pPyWQUstHr3jE1O6yfOnnm6d+ofr93U9cFzo+91WTvDQ5E9/zlzzxe//n8+COcjghcAAA==";
}
