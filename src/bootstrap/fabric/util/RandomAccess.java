package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * Marker interface used to inform <code>List</code> implementations that
 * they support fast (usually constant time) random access. This allows
 * generic list algorithms to tailor their behavior based on the list
 * type.
 * <p>
 *
 * For example, some sorts are n*log(n) on an array, but decay to quadratic
 * time on a linked list.  As a rule of thumb, this interface should be
 * used is this loop:<br>
 * <code>for (int i = 0, n = list.size(); i &lt; n; i++) list.get(i);</code>
 * <br>runs faster than this loop:<br>
 * <code>for (Iterator i = list.iterator(store); i.hasNext(); ) i.next();</code>
 *
 * @author Eric Blake (ebb9@email.byu.edu)
 * @see List
 * @since 1.4
 * @status updated to 1.4
 */
public interface RandomAccess extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.RandomAccess {
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 125, -102, -25, 5,
    -113, 121, -99, 43, 32, 5, -40, -102, -55, 101, -32, 10, 57, 3, -18, 27,
    -56, 67, 36, 25, -123, -57, -62, 23, 87, 111, -36, 122 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYa2xcxRWeXT/XmDgxjYPzcEwwQYFkV1F/VKmpwN7msWRDLDshkNC4s/fOri+ZvXNz76y9DgTxSgn5kRYwCY8mKsiIlxMkpPAQskAUQSIQUitEqdRH/lCKIJVQf8CPtvScmd2919ebTSDB0p69njnvOeebc3fqDGnwXLIsSzMWj8txh3nxdTSTSg9Q12NmklPP2wKrw8Yl9alD/3zW7IqSaJq0GtQWtmVQPmx7ksxJ305HacJmMrF1MNW7g8QMFNxAvRFJojv6iy7pdgQfz3EhS0Zm6X/02sTE4Z1zX64jbdtJm2UPSSotIylsyYpyO2nNs3yGuV6faTJzO5lnM2YOMdei3NoDjMLeTto9K2dTWXCZN8g8wUeRsd0rOMxVNsuL6L4At92CIYUL7s/V7hekxRNpy5O9adKYtRg3vd3kLlKfJg1ZTnPA2JEuR5FQGhPrcB3YWyxw081Sg5VF6ndZtinJ0rBEJeKejcAAok15JkdExVS9TWGBtGuXOLVziSHpWnYOWBtEAaxIsvCsSoGp2aHGLppjw5JcHuYb0FvAFVNpQRFJ5ofZlCY4s4WhMwuc1pmbrjt4h73BjpII+Gwyg6P/zSDUFRIaZFnmMttgWrD1mvQh2jG9P0oIMM8PMWueV+/86oaVXW+d1DyLqvBsztzODDlsTGbm/GFxcsWaOnSj2RGehaUwI3J1qgOlnd6iA9XeUdGIm/Hy5luD79569wvsiyhpSZFGQ/BCHqpqniHyjsWZu57ZzKWSmSkSY7aZVPsp0gTPactmenVzNusxmSL1XC01CvU/pCgLKjBFTfBs2VlRfnaoHFHPRYcQ0gQfEoFPJyF1S+C7g5DoPyRJJ0ZEniUyvMDGoLwT8GHUNUYS0LeuZawyhDOe8Fwj4RZsaQGnXtfBD1LbFPk+A4rTi4MfzkXWV0T/545FIpDapYYwWYZ6cE6lmukf4NAWGwQ3mTts8IPTKXLZ9OOqbmJY6x7Uq8pMBM56cRglgrIThf61Xx0ffl/XHMqWEgfnrf3T5xn0D1xqxU6KAzbFAZumIsV48mjqRVUwjZ7qrIqWVtDyU4dTmRVuvkgiERXSj5S80gznvAvwAyCidcXQL2785f5ldVCizlg9HltRtfDi8j8gGApGgcXPhpwjn3z4+Y8VjJZxpS0AQENM9gZqGXW2qaqd5/uxxWUM+P762MAjj555YIdyAjiurGawB2kSaphC8Qp338ndf/773yY/ilYcr5Ok0SlkuGVI0kwzkBNqSEliFUjTgc37Fv4i8PkffjBGXMBvQKtkqUe6K03iOOF0LDkbmigknLx34qi5+ZnVuufbZ3boWruQP/bxfz+IP3b6VJXTj0nhrOJslPGAzRiYvGLWtbZJgW0K0J8CJA0bp79Ysia569OcNrs05GKY+/lNU6fWLzcejpK6EupVQfiZQr1BZ+GicBlcUDaGjSstYHRZuOZdYTATbjHf7jXd9MTw9N6eKN4QMbi8JAV4gZugK2x8Btr2lisMTTWkySVY15TjVvm6aZEjrhjzV1Qvz/EPnFQ/8A7/dtJQHFcXvuNoLFDySxVdhuQqXWj4uBzJ1VBycFNbo0zxr4CjWu5XNyArB5VQ/F7PVjsvTCtr0Qxn2Hf/abtq9YkvD87VVcBhRbvtkpXnVuCvd/aTu9/f+XWXUhMx8Gb3O9Bn09fFZb7mPtel4+hH8Z4/Lnn8PXoEcATA3rP2MIXfpFTu6FSfiv86RW8I7SXV6mokP9HtNR/GEGUHkxrXSVUbnVDfiEtcwLRVrJVZCadt2VTdxL06sUiuR9KP5OfQ4DkmVeOVjbb5Rv31zjAW4mIayaag4+d31gF3amRka429bWGja6sbjVaMBsLfguRmJLdA+CMwkibhivJmDyoDrpW3sCb1EML2Txz4Nn5wQleanuaunDVQBWX0RKcsX6rMI+pdUcuKklj32Ut733hu7wPRUrSrJKkD7MXHHUh2nmfK/eivrprtiOKKqPwphmyNlI+cZ8qVuuV+thWqqRvVgh5nuwuU65K6rZQR/ILRtCkjBGdUXW+qYPMXKcpgELLG3uj3DFDtF5CMQTlJoYfzKi0c2KjaTnuQ3PFDtdN9Nfb2nWfovtGNoZ66F8n9SH6FSC6klR2vdsr1o8Iy8flBJAd+qGAfqrH3yAUH+xskDyOZACjWwfZxVbQHkBy+6LGVOrVqRrmwc0rotzWEoBK7/EpM4SDnFhyYytYWDeaUx8r1Ss9TF5ygJ5H8DskkODhGLVnJzbPfJTfnau2oz3UEyU7FNXV2mHtaMRz/zrfHRj+2F5EcQ/KSH89FjyxYsK+cK57XLiSeE0heRfJ6tXiKgFTB1yccfxZVeaUr/cRgJN9hk59uXDn/LK9zl8/60ackd/xoW/OCo1v/pAbays8HMXjvyRY4D8zLwdm50XFZ1lKxxPScqsfNNyVMtv77H9Qgfql0TGuOtwGlNAf+93sH6UJFyu3SXlJQZfqaCd1K38KCiz9rTf17wTeNzVtOq3cpyHj33ic+a/j1+JFruxs+eeIUO92ypu5fi04mezr3vff2gm3iL3v+D2qMqCtuEwAA";
}
