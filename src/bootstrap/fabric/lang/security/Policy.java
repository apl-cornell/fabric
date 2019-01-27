package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Set;

/**
 * A Policy is a component of a label, and is either an integrity policy or a
 * confidentiality policy. This code is mostly copied from Jif.
 */
public interface Policy extends fabric.lang.Object {
    /**
   * Does this policy relabel to policy p? If this method returns true, then all
   * delegations that this result depend upon (i.e., DelegationPairs) should be
   * added to the set s. If this method returns false, then the set is not
   * altered at all.
   * 
   * @param p
   * @param dependencies
   * @return
   */
    boolean relabelsTo(fabric.lang.security.Policy p, java.util.Set s);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.Policy {
        public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.Policy) fetch()).relabelsTo(arg1,
                                                                      arg2);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -45, -90, -39, 90, -30,
    13, -38, -29, 108, -13, -109, -4, 113, -96, -103, -85, 22, -92, 40, -127,
    18, 8, -6, -30, -14, -122, -48, 9, 25, 85, -20, 69 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYfWwcRxWfOzu2z3Vix4nd5stJ0ySQktwpQkJqXT7ik5Nce20sf/DhtDFzu3PnbeZ2N7Oz9jmQklRqGyFhPuqURhCDkGkLcVupUikfihSggpQCaikCigRERRWgNn+U/gFChfa9mb3b9fl8SUl60r6bm3lv5r037/3e25u/SJZ5gmzO05zFk3LKZV5yD81lsgNUeMxMc+p5wzA7ZlzTmHno74+aPXESz5I2g9qObRmUj9meJCuyd9MJmrKZTI0MZnoPkISBgvuoNy5J/EBfSZBNrsOnCtyRwSGL9j/5gdTMVw92PNVA2kdJu2UPSSotI+3YkpXkKGkrsmKOCW+3aTJzlKy0GTOHmLAot44Ao2OPkk7PKthU+oJ5g8xz+AQydnq+y4Q6szyJ6jugtvAN6QhQv0Or70uLp7KWJ3uzpClvMW56h8k9pDFLluU5LQBjd7ZsRUrtmNqD88DeaoGaIk8NVhZpPGTZpiQbqyUqFm+5DRhAtLnI5LhTOarRpjBBOrVKnNqF1JAUll0A1mWOD6dIsnbJTYGpxaXGIVpgY5JcV803oJeAK6HcgiKSdFWzqZ3gztZW3Vnkti7eccv0Z+x9dpzEQGeTGRz1bwGhniqhQZZngtkG04JtN2Yfot1nT8QJAeauKmbN88xn3/jYjp5z5zXPuho8+3N3M0OOGXO5FS+uT2+/qQHVaHEdz8JQWGC5utWBYKW35EK0d1d2xMVkefHc4M8+dey77LU4ac2QJsPhfhGiaqXhFF2LM7GX2UxQycwMSTDbTKv1DGmGcdaymZ7dn897TGZII1dTTY76DS7KwxboomYYW3beKY9dKsfVuOQSQprhITF4ugiJj8B3Ap6XJNmfGneKLJXjPpuE8E7Bw6gwxlOQt8IydhqOO5XyhJESvi0t4NTzOn48ZvjCklPgBW4ZU0lQxb36W5bQio7JWAwcvNFwTJajHtxWEDl9AxySY5/DTSbGDD59NkNWnT2loieBEe9B1Cr/xODG11djRVR2xu/rf+OJsed15KFs4D5J1mkVk6hisqxiUqsIWrVhSiUBpJIAUvOxUjI9mzmjIqfJUylW2agNNrrZ5VTmHVEskVhMWbVayauQgQs/BEACWNG2feiuWz99YnMDxKo72Yj3V1K5vL78AwSr7FGo8eEh9/Qffv2PDyo8LQNMewSJhpjsjQQ17tmuwndlqMewYAz4/vTwwIMnLz5wQCkBHDfUOnAL0jQEM4UodsR95w+//Jc/z/02XlG8QZIm18+BtyRpoTnwCTWkJIkKtmnDVr4Nnxg8/8MHbcQJ/AbYSgfJsqmSLa5b7Y4NS8GKgsS5e2dmzf3f3qWTv3NhqvbbfvHx3/33l8mHLzxXIwAS0nF3cjbBeOTMVjjy+kX17XaFuhkoAxSwacy48NqGm9KHXi3oYzdWqVjN/Z3b55/bu834Spw0BPBXA+oXCvVGlYWKIRhUKhvNxplWOHRzddgLx2AmlLPw3Bs30afHzh7dEsdSkYAqJingDJSEnurDF8BubznC8KhlWXINxjXluFSuO61yXDiT4YxK5xX6wsGJcby8rfCshvFg8J3C1VUu0tU6/RX/RkU3I9mqbiCOw21I3qfYtsONbAuDGJCUA5pDjHtbRuyiY1p5i+Y4w/R6q33rrqdfn+7Ql81hRmsnyI5LbxDOr+kjx54/+K8etU3MwEoeJlrIpsvDqnDn3ULQKdSjdPw3G079nJ4GuABw96wjTOF1PLRP294lyfJQHFJTza6B0ERI4Q50TCXlg4+qhZsV/Qg6N8gP/N2P5EMSY4TTHATzsOMtLqwDwioCMkwEhZWdmPn828npGe0p3X3csKgBiMroDkSduFxdCybn9fVOURJ7/vbk0R89dvSBeKDtTkmac47DGbWVNbtClCC1UaI77G10IU+qdtF16wVRQxhEgFPQ54FOQTQt7cnhOmsf19oiuaNUvrwOdXmqgGjVcL6vnkYSUsuyqep/BrVCSIaQjCD5BKBpgUmFcuVj2sNjwvk11YUHJ+9CcjCq6uX5KKJOHR/k66yNVx/6yUtld8R8BWqqoFpg/ji8CKShJVCie4NYw69bJWmA8oJD5cDiZRq6GFEW2hFTXDGltWKQdQyduExD1XbbQhvVuo9kEiKSHfapLjsZJEfU6CqZE9X2c3XWjv+fltyD5BiSe+G2pKPfOGrkRGShZrTeh+T+9ypap+usfekyTQ8PvbMqZL+A5ItIvowA40grP1UrYBsnHMvE8QySk++VsV+vszZ7xcZ+DclpJN+A2qSN3c1VDp5E8q2rbluQkjU9yh27oIQerSMEkdgTRmIGm1Lhu9Bh9pcM5pZb5D61z/wVO+gRJGeQPAkKTlJLVnzz1LvxzaVSO9JBPIZE59EzS+PZ44rhB+8anO8Mbfseku8j+WFoz1W3LBqwP76UPT+9EnvOIfkJkmdr2VOCdNYvgdjdravxbhr8Y2Kkn2Vzr962o2uJ99LrFv2HFcg9Mdvecu3syO9VW175NyQBb295n/NI1x99A2hyBctbyoqE7rZ1//MLSVbXepEFZC4PlVfOa/ZfSWjnQ3bMJhpAdMDxAtivOfDXi6pfXxuScnJ1Rg/VzU9toFebrvUF/rM3/+a1/25qGb6g3iLhfja99MjLo68s/+Nf+ZsPvnX4m6fOdM+9/3hny39e+ef9LyTWjLze/w6kO8R6cRQAAA==";
}
