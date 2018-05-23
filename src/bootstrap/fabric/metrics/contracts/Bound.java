package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.worker.Store;

/**
 * A linear time-varying bound that can be applied to a {@link Metric}'s value
 * and enforced by a {@link MetricContract} of
 * <code>&gt;= r * (t - startTime) + b</code>.
 */
public interface Bound extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.Bound {
        public static double[] createBound(double arg1, double arg2) {
            return fabric.metrics.contracts.Bound._Impl.createBound(arg1, arg2);
        }
        
        public static double[] createBound(double arg1, double arg2,
                                           long arg3) {
            return fabric.metrics.contracts.Bound._Impl.createBound(arg1, arg2,
                                                                    arg3);
        }
        
        public static double value(double arg1, double arg2, long arg3,
                                   long arg4) {
            return fabric.metrics.contracts.Bound._Impl.value(arg1, arg2, arg3,
                                                              arg4);
        }
        
        public static double value(double arg1, double arg2, long arg3) {
            return fabric.metrics.contracts.Bound._Impl.value(arg1, arg2, arg3);
        }
        
        public static long trueExpiry(double arg1, double arg2, double arg3,
                                      long arg4) {
            return fabric.metrics.contracts.Bound._Impl.trueExpiry(arg1, arg2,
                                                                   arg3, arg4);
        }
        
        public static boolean test(double arg1, double arg2, double arg3,
                                   long arg4) {
            return fabric.metrics.contracts.Bound._Impl.test(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public _Proxy(Bound._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.Bound {
        /** Create a normalized bound. */
        public static double[] createBound(double rate, double base) {
            return fabric.metrics.contracts.Bound._Impl.createBound(rate, base,
                                                                    0);
        }
        
        /** Create a normalized bound. */
        public static double[] createBound(double rate, double base,
                                           long startTime) {
            if (java.lang.Double.isNaN(rate) || java.lang.Double.isNaN(base)) {
                throw new java.lang.RuntimeException("This shouldn\'t happen");
            }
            return new double[] { rate, base - rate * startTime };
        }
        
        /**
   * Compute a value given the rate, base, starting time (for base), and
   * current time.
   */
        public static double value(double rate, double base, long startTime,
                                   long curTime) {
            long dt = curTime - startTime;
            return rate * dt + base;
        }
        
        /**
   * Compute a value given the rate, base, and current time.
   */
        public static double value(double rate, double base, long time) {
            return fabric.metrics.contracts.Bound._Impl.value(rate, base, 0,
                                                              time);
        }
        
        /**
   * Compute the time at which this bound will expire given the current value
   * and time.
   */
        public static long trueExpiry(double rate, double base, double value,
                                      long time) {
            if (rate > 0) {
                return (long)
                         (time +
                            (value -
                               fabric.metrics.contracts.Bound._Impl.value(
                                                                      rate,
                                                                      base,
                                                                      time)) /
                            rate);
            } else if (value <
                         fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                                    time)) {
                return 0;
            }
            return java.lang.Long.MAX_VALUE;
        }
        
        /**
   * Determine if the given value x is above the bound at the given time.
   */
        public static boolean test(double rate, double base, double x,
                                   long time) {
            return x >=
              fabric.metrics.contracts.Bound._Impl.value(rate, base, time);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.Bound._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.Bound._Static {
            public _Proxy(fabric.metrics.contracts.Bound._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.Bound._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  Bound.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.contracts.Bound._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.Bound._Static._Impl.class);
                $instance = (fabric.metrics.contracts.Bound._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.Bound._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Bound._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -91, -99, -25, -119,
    110, -94, 108, -16, -48, 124, 2, -110, 33, 49, 116, 28, -95, 29, -15, -63,
    -122, 86, -116, -20, -68, 58, -97, 19, 73, 56, -45, -20 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526753776000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0YbWwUx3XufD7bYGxjY0KMbWzjIvGRO0ErtYmbAj7hcHAU18ZIMQru3O7ceeO93c3snDmT0CaRWkhaITUYStRCkgqUNjUgRaL90SKlUtomSlWladWPH234E4UIUIOqpP3RNn1vdu92b+98EZVP2pnZmffevO/39hZuk3qbk4EMTWt6TMxZzI6N0HQyNUq5zdSETm37AOxOKcsjyTM3XlZ7wyScIs0KNUxDU6g+ZdiCtKQepbM0bjARnxhLDh0iTQoi7qb2tCDhQ8MFTvosU5/L6qZwL6mgf3pzfP67h9terSOtk6RVM8YFFZqSMA3BCmKSNOdYLs24vVNVmTpJVhqMqeOMa1TXjgKgaUySdlvLGlTkObPHmG3qswjYbuctxuWdxU1k3wS2eV4RJgf22xz280LT4ynNFkMpEs1oTFftx8jXSCRF6jM6zQLg6lRRirikGB/BfQBfpgGbPEMVVkSJzGiGKsi6IEZJ4sG9AACoDTkmps3SVRGDwgZpd1jSqZGNjwuuGVkArTfzcIsgXYsSBaBGiyozNMumBFkThBt1jgCqSaoFUQTpDIJJSmCzroDNfNa6/eUvnnzc2G2ESQh4VpmiI/+NgNQbQBpjGcaZoTAHsXlT6gxdfe1EmBAA7gwAOzA/feLOji29r73hwKytArM//ShTxJRyId3yu+7ExvvrkI1Gy7Q1dIUyyaVVR92ToYIF3r66RBEPY8XD18Z+9fCTr7CbYbIsSaKKqedz4FUrFTNnaTrjDzGDcSqYmiRNzFAT8jxJGmCd0gzm7O7PZGwmkiSiy62oKd9BRRkggSpqgLVmZMzi2qJiWq4LFiGkAR4SgmcHrLtgbobXtYLsi0+bORZP63l2BNw7Dg+jXJmOQ9xyTYnbXInzvCE0AHK3wItgsuPg6oJTRdjxYTNvqDFgxFpqggWUoO1IKATKXaeYKktTGyzles3wqA6BsdvUVcanFP3ktSTpuPa89Jwm9HYbPFbqJgTW7g7mCT/ufH54153LU285Xoe4ruoE6XW4jLlcxkpcxiSXwFgzRlQMclQMctRCqBBLnE/+WDpO1JYRVqLVDLQesHQqMibPFUgoJAVbJfGlx4C9ZyCPQKpo3jj+yJ6vnhioA1e1jkTQegA6GAwcL90kYUUhGqaU1uM3Pr5y5pjphZAggxWRXYmJkTkQ1BI3FaZC5vPIb+qjV6euHRsMY1ZpQnVQcEnIHr3BO8oidKiY7VAb9SmyHHVAdTwqpqhlYpqbR7wdaf0WHNodR0BlBRiUifLBcevcn3/7wWdlCSnm1FZf8h1nYsgXx0isVUbsSk/3BzhjAPfXs6OnTt8+fkgqHiDWV7twEMcExC+FwDX5N9547C/v/u3CH8KesQSJWvm0rikFKcvKT+AXgue/+GAw4gbOkJITbiLoK2UCC2/e4PEGOUGHvASs24MTRs5UtYxG0zpDT/l362e2Xr11ss0xtw47jvI42fLpBLz9e4fJk28d/mevJBNSsCZ5+vPAnETX4VHeyTmdQz4KT73T8/yv6TnwfEhTtnaUycxDpD6INOA2qYv75Lg1cPY5HAYcbXW7+/JlvRw34LDR0S0uN7l6Je4v6ua0Ym5bhacdFo6rymly0rNY+ZGl88LT8+fV/Re3OkWivTyl7zLyuUt//M9vYmevv1klWTQJ07pPZ7NM990ZhSv7K/qgfbI6e2F1/WbP/YmZ97LOtesCLAahf7Rv4c2HNijPhUldKcYrWoJypCE/sxBsnEFHY6DYuLNMGqGvpNQ2VNYueFpBmafc+bBPqW5EVrVQWFpIYP7DXsszVZg4lCS1R9x5NGgqz0HCDjm7svqPci0HsTzrVn92Yv7ZT2In5x2bOC3S+oouxY/jtElSihU4bEbP6K91i8QYef/KsZ/98NjxsOu2QyClakKkSy2m5F6yhpt/BYdhQZYrnEGkF2vIWr9/7IHgkmHlyHYYktTbc38/43hGsHfyAX648O7Nd1b0XJaZOYKlUlo22HRW9pRlraJks9mSU6okQ6hUg4KWGEHanmdOxhe+35X40k2nDJfKA9Lpr1KGD1Jf5dr2Su6j8ED0l2HSMEnaZC9NDXGQQjMBmXkSOLcT7maKrCg7L+9snTZuqBQa3UFX8F0bLEz+IImIsvBosQohIh31UG3Hr89oBnW6l83gIDozsmJaAu9xfQ2nfYLUgS1wOVGo8Hl87xRuqkWpIE+ZBsOsLc/uhXSD/YRuwtdSoQjuNBOaGSt9w7iumSlU2BLftzuGnnDkweHzksMaLmzUOJPKmQEFKMhrkak2TwbHMj6GtpennE7nCe+EeRUw+fFdpRwcRqqkG6T0kTvfWDzd1ElSddLzPfcPmCwCkmUlK0drKOLrOIjyOPcUHRC6A55+uPh1mPtgProkQiOlOXfWFxc6IklFPKFxeFwO8u7jNaR8BoenwNyzGIOeygLyoTE3wh1n3Tm7JPIhpYw7P3wXRvVE+04N0U7h8K1PFW0MHkCtf9qddyyJaEhpuztvu0vT+eT7Xg35zuFwWkDTzfNsV8HS+JxErCbkXngehEbmA3e+vCRCIqVL7vzi/y/kxRpCvozDCxCzgtmiWjA3pE1TZ9QogKFljGJ3u7bKl6b734eSeJ1deG/vls5FvjLXVPwb5eJdPt/aeM/5iT85tbn4v0YTfJRk8rruLzm+ddTiLKNJSZqKBQinK4KsWeyzVDhFV66llJccnFcFaSnHEbLu48oPdxXqlQOHbz+RJunyhmJKb3dp+ZJ6sSqVf+VKol15jn/XLfzjnn9FGw9cl99JmJkunnv/GeMH+odvPxF+rn+r6H6p584vvnnw27d+/sALHckv/P7W/wC4k5MQRhQAAA==";
}
