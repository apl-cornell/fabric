package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.MetricContract;

/**
 * A policy for enforcing a {@link MetricContract}. This class is responsible
 * for ensuring a {@link MetricContract} (and the API implementation) is
 * monitoring evidence of the {@link MetricContract}'s validity and updating the
 * expiration time correctly. It effectively acts as a bundle of the currently
 * monitored information for enforcing a {@link MetricContract}.
 */
public interface EnforcementPolicy extends fabric.lang.Object {
    /**
   * @return the exipration time of this {@link EnforcementPolicy}.
   */
    public abstract long expiry();
    
    /**
   * Update book-keeping to use this {@link EnforcementPolicy} for the given
   * {@link MetricContract}. This will add the given {@link MetricContract} as
   * an {@link metrics.util.Observer Observer} of the necessary
   * {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link MetricContract} to apply this policy to.
   */
    public abstract void apply(fabric.metrics.contracts.MetricContract mc);
    
    /**
   * Update book-keeping to stop using this {@link EnforcementPolicy} for the
   * given {@link MetricContract}. This will remove the given
   * {@link MetricContract} as an {@link metrics.util.Observer Observer} of
   * the necessary {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link MetricContract} to stop applying this policy to.
   */
    public abstract void unapply(fabric.metrics.contracts.MetricContract mc);
    
    /**
   * Activate this policy, activating witnesses and setting the expiry.
   */
    public abstract void activate();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.enforcement.EnforcementPolicy {
        public long expiry() {
            return ((fabric.metrics.contracts.enforcement.EnforcementPolicy)
                      fetch()).expiry();
        }
        
        public void apply(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              apply(arg1);
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              unapply(arg1);
        }
        
        public void activate() {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              activate();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -102, 14, -6, -10, 118,
    -27, -31, -52, 61, -112, -11, -124, 82, -36, -27, -53, 23, -41, -22, -28, 1,
    45, 46, -94, 65, -107, 93, 54, 5, -72, 102, 106 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1518538116000L;
    java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAL1YDWwcxRWeO/9eYvwX8mcSOwkmUWhypwgpEri0dU4OObgQy05UkZSYub05e5O53c3srH0OpKItNBFIEQWHJpVIgaalQAgtUtQfaimqKISGVoLS0qr8BLUWtGlaoao/qtrS92b3btfr89khpiftu9mZN/Pe9+b9zOzJi6TGFmRVjmZ0HpejFrPjm2kmle6lwmbZJKe2vR16B7T51amH338i2x4l0TRp0KhhGrpG+YBhS9KY3kOHacJgMrGjL9W1i8Q0nLiF2kOSRHdtKgiywjL56CA3pSdkyvpHPpEY++ru5ueqSNNO0qQb/ZJKXUuahmQFuZM05Fk+w4Tdnc2y7E7SYjCW7WdCp1zfD4ymsZO02vqgQaUjmN3HbJMPI2Or7VhMKJnFTlTfBLWFo0lTgPrNrvqO1HkirduyK01qczrjWXsf+TypTpOaHKeDwLgoXUSRUCsmNmM/sM/TQU2RoxorTqneqxtZSTrCM0qIO28BBphal2dyyCyJqjYodJBWVyVOjcFEvxS6MQisNaYDUiRpm3ZRYKq3qLaXDrIBSZaE+XrdIeCKKbPgFEkWhtnUSrBnbaE9C+zWxVs/efhOY4sRJRHQOcs0jvrXw6T20KQ+lmOCGRpzJzZcm36YLho/FCUEmBeGmF2e7931wWfWtZ856/JcVYZnW2YP0+SAdiLT+Oqy5Nrrq1CNesu0dXSFScjVrvZ6I10FC7x9UWlFHIwXB8/0vXjb3U+xC1EyL0VqNZM7efCqFs3MWzpn4iZmMEEly6ZIjBnZpBpPkTpop3WDub3bcjmbyRSp5qqr1lTvYKIcLIEmqoO2buTMYtuicki1CxYhpA4eEoHnOmgDfNIAr+9JwhJDZp4lMtxhI+DeCXgYFdpQAuJW6FrCFlpCOIbUgcnrAi+CPzsBri4F1aSdYCBWaCzPDJno8du9Jte10TgoaP2/BBUQcfNIJAKb0aGZWZahNuys52WbejkE0haTZ5kY0Pjh8RRZMH5MeVoMo8MGD1e2jIB3LAvnleDcMWdTzwenBs65XopzPVNLstHVPu5pHy9pHw9oH5+iPSjcgJEZh1wXh1x3MlKIJ4+nnlYOWGurSC3JaAAZN1icSlgkXyCRiAJ8pZqvPA/8Zi/kI0g5DWv7b7/5jkOrqsDlrZFqdIOCSgnLii8wMQRVJZ8b+61Hfv3zP1yn0nIxTzUFElo/k12B2MA1m1QUtPh6bBeMAd9bR3sfOnLx4C6lBHBcXU5gJ9IkxASFYDDFvWf3/eadt0+8Hi0pXiVJreVkwFqS1NOMrcwqSayUIl1gLR/CLwLPf/FBjNiB/5D9kl7MrSgFnWWFzbF8uuykMuuJL44dz2775gY3h7ROjvgew8k/86v/vBI/ev7lMr4Rk6a1nrNhxgMyrwCRK6eUya0qeaegmlBIcQPa+QvLr0/unRh0xXaEVAxzP7n15Ms3rdYejJIqL4uWqRiTJ3UFlYXCIxgUPANhY888ELoqHBHC1FgWqqIv99oV9PTA+IHOKFacGLo+hXQFlaU9LHxS9u4qehiKqkmT+ejXlONQsXzNk0PCHPF7VKQ3uhsORmzFzeuAB16ijvev8t8CC+mVbmZQ/B2KrkJyjdqBKDZXI1mj2NbCjqz2nRgSMoeiAD5ud+4w8mZWz+k0wxmG17+brtlw+k+Hm93N5tDjaifIupkX8PuXbiJ3n9v9j3a1TETDA4EfaD6bW2UW+Ct3C0FHUY/CF15bfuwl+gikC6gRtr6fqbRPPK9GpT6tYN+g6KdCY91INkJ8wTRdYDIKV9teoechzoe9assOjd33YfzwmIvbPZJcPeVUEJzjHkuUtCuUkTHUVlaSomZsfu/ZA89/+8DBqKfpeglmNg3XVTZM3v8EPMsIqV7p/Vd/5P2fbKmI4oqo94WSrJk2w29VPUnvHdnb1IK3VjB9H5KUJDXUsvioYkl69sG/zYB32NSz5fBugGcNITXvev8/nlu8+LpNMXyugv67kXxWkjrHUAjwdXs5bXFX1hFSe5v3f/McaRtUhlUYU+XzDiwdEEvDkPqnaKrqRPnCscg/NbtHxLi6iFhWJc2rfM0htOAGAY49IwSrwphwtUWyp1B0x2aVDlC1uKuaGlgK1QZPCdyEu1Shkn7gejndoOqcbbjqIVEHyX1IoO7XDzKpymBRaJMv1O9fGj6ZYOedSO4KKj47iwXUqWCReyqMfTksVM7kYAH4X0JyL5KDAH8ILpxJOE6WC88qOH9g8z4k988S6CWEoFQMD1YAOjZLoGq51T7GryB5CMkRTP37HMrtcgjrMqbJGVVHvKNIjs0RyiCIRyuMPf4RAX4dyWNIvgGbKE33wlsmcAIDZZ34W0ie+Lic+NkKY9+dJXRf6P6QJ59C8h0kz2EWMqWec5M0ktOq9XEB+2GFsR9dNrAfIHkeyTgkOxdYN+clbGfmHFsgKnsUw08qnxTafQ9L4SVFOBbcOHoKGrOKV6Zhtc5Ll22MF5C8iOSncGIYobos2eFnl2KHmUI26nP1ILlfcb02vaHOKobXLzkX7/exvYrkF0h+6eOZc2RB53xzJjxvXw6e3yJ5C8k75fAUJGmZ8n0AD/5Xlfmi4X2T05IvsBMTt6xbOM3XjCVTvpJ6804db6pffHzHG+rGVvreFoOLfc7hPHAhDF4Oay3BcroCFHMvYu45aEKSztl8/pBkfuBN2ex37grvS7JkuhWke6NU7eCcC5I0Tp4j1cdPbAX5/gzpz+XDt7+ok2dbiBQjt9VbsMyxanJ1UCu3OQK/Rp/86+J/1tZvP68+WcDmr/ha47/+Pjzx7is3PvC3e/renDi3+I0//j6yPv5495HbN9Z8P7fnf82b2oglFwAA";
}
