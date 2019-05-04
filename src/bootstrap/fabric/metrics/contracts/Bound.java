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
 * and enforced by a treaty of
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
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
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
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { 37, -27, 77, 79, -123,
    -56, -93, -54, -6, 5, 25, -47, 27, -22, 124, 111, -80, 34, -9, 102, -37,
    119, 105, 28, -43, 21, 95, -5, 94, 12, -40, 27 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556640403000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUYbWwcR3XufD5/xLEdJ04T23Ecx42UrztSkFAxpYlPcXPJuXHtOAgHYub25uyN93a3s3P2OW2gBVUJIEVAnbRViflBqtLiJqhqyg8UqT/4SClCtOIjFYLmT6AQIqgQFPFV3pvZu93bs68KCiftzOzMe2/e93t7izdJrcNJb5amdSMm5mzmxAZpOpkaptxhmYRBHecQ7E5oKyLJs28/m+kOk3CKNGnUtExdo8aE6QjSnDpGZ2jcZCI+NpLsP0IaNETcR50pQcJHBgqc9NiWMTdpWMK9pIL+me3x+SeOtr5YQ1rGSYtujgoqdC1hmYIVxDhpyrFcmnFnTybDMuNklclYZpRxnRr6cQC0zHHS5uiTJhV5zpwR5ljGDAK2OXmbcXlncRPZt4BtnteExYH9VsV+XuhGPKU7oj9FolmdGRnnQfIZEkmR2qxBJwFwbaooRVxSjA/iPoA36sAmz1KNFVEi07qZEWRjEKMkcd8BAADUuhwTU1bpqohJYYO0KZYMak7GRwXXzUkArbXycIsgHcsSBaB6m2rTdJJNCLIuCDesjgCqQaoFUQRpD4JJSmCzjoDNfNa6ef9HTz9k7jPDJAQ8Z5hmIP/1gNQdQBphWcaZqTGF2LQtdZauvXwqTAgAtweAFcx3Hn5n947uV64omM4lYA6mjzFNTGjn082vdyW23l2DbNTblqOjK5RJLq067J70F2zw9rUlingYKx6+MvKDTzzyPLsRJo1JEtUsI58Dr1qlWTlbNxi/j5mMU8EySdLAzExCnidJHaxTusnU7sFs1mEiSSKG3Ipa8h1UlAUSqKI6WOtm1iqubSqm5LpgE0Lq4CEheHbDuhHmJnhdIchIfMrKsXjayLNZcO84PIxybSoOcct1badm2XNxh2txnjeFDpBqPw6uBJMTB38XnGrCiQ9YeTMTA27s/wvVAsrSOhsKgZo3alaGpakDNnP9Z2DYgBDZZxkZxic04/TlJFl9+SnpQw3o9w74rtRSCOzeFcwYftz5/MDedy5MvKb8D3FdJQrSrbiMuVzGSlzGJJfAWBPGVgyyVQyy1WKoEEssJL8lXSjqyFgr0WoCWh+xDSqyFs8VSCgkBVsj8aXvgOWnIaNA0mjaOvqp/Z8+1VsDTmvPRtCOANoXDCEv8SRhRSEuJrSWk2//7eLZE5YXTIL0VcR4JSbGaG9QS9zSWAZyoEd+Ww+9NHH5RF8Y80sDqoOCc0Ie6Q7eURar/cW8h9qoTZEVqANq4FExWTWKKW7NejvS+s04tClHQGUFGJQp855R+9zVn/z+g7KYFLNriy8NjzLR74toJNYiY3eVp/tDnDGA+/WTw4+fuXnyiFQ8QGxe6sI+HBMQyRRC2OKPXXnwzbd+c/5nYc9YgkTtfNrQtYKUZdV78AvB8x98MCxxA2dIzgk3JfSUcoKNN2/xeIPsYECGAtadvjEzZ2X0rE7TBkNP+VfLnbsu/fF0qzK3ATtKeZzseH8C3v76AfLIa0ff7ZZkQhpWJ09/HphKeas9yns4p3PIR+HRNzY89UN6DjwfEpajH2cyBxGpDyINeJfUxU457gqcfQiHXqWtLndfvmyW4xYctird4nKbq1fi/qJuditmuQierrZxXFNOk5MNyxUiWUTPf25+IXPwmV2qXLSVJ/e9Zj73wi/+/ePYk9deXSJZNAjL3mmwGWb47ozClZsqOqIhWae9sLp2Y8Pdienrk+rajQEWg9DPDS2+et8W7athUlOK8YrmoByp388sBBtn0NuYKDbuNEoj9JSU2orK2gtPCyjz8+58v0+pbkQuaaGwtJDA/Iddl2eqMFGUJLUhd94dNJXnIGFFzqnsA4a5noNYnnH7AHZq/ovvxU7PK5uoZmlzRb/ix1ENk5RiJQ7b0TM2VbtFYgz+7uKJ737zxMmw67b9IGXGgkiXWkzJvWQVN38AhwFBVmicQaQXa0in3z/2Q3DJsFKyHYUk9dO5P51VnhHsonyAf15868YbKzdckJk5gqVSWjbYflZ2l2VNo2SzyZZTqiRDqFSDgpYYRNqeZ47HF7/WkfjYDVWGS+UB6Wxaogwfpr7Kddfzub+Ge6PfD5O6cdIqu2pqisMUOgrIzOPAuZNwN1NkZdl5eY+rGrr+Umh0BV3Bd22wMPmDJCLKwqPZLoSIdNQj1R2/NqubVHUv28FBDGZOiikJvN/1NZyGBKkBW+ByrFDh8/jeLtxUi1JBnrJMhllbnq2HdIP9hGHBd1OhCK6aCd2Klb5mXNfMFipsie/3KkOPKXlw+LDksIoLm1XOpHKmQQEa8lpkqtWTQVnGx9C95SmnXT1hJLwGmPztLaUcHAaXSDdI6bo7X10+3dRIUjXS8z33D5gsApJNSlaOV1HEZ3EQ5XHuKTog9Gp4NsHFL8HcA/Ox2yI0UtLd+ZPLCx2RpCKe0Dg8JAd598kqUn4Bh0fB3DMYg57KAvLtgWcr3HHKnT9+W+RDSofdOXkLRvVE+0oV0R7H4UvvK9oIPIBay935A7dFNKQUd+feWzSdT76nq8h3DoczAppunmd7C7bO5yTiUkIegOceaGTedOeF2yIkUjrnzl/+34V8poqQz+LwdYhZwRyxVDDXpS3LYNQsgKFljGJ327nEl6b7L4iW+B47f/3AjvZlvjLXVfwv5eJdWGipv2Nh7JeqNhf/4WiAj5Js3jD8Jce3jtqcZXUpSUOxAOF0UZB1y32WClV05VpK+YLCeVGQ5nIcIes+rvxwl6BeKTh8e1mapMMbiim9zaXlS+rFqlT+lSuJduQ5/nG3+Jc7/h6tP3RNfidhZrrz+tDBx65840f/qF3/eucfHra+3ftu9lezetfP2yf+ebTpaud/AbYPckRQFAAA";
}
