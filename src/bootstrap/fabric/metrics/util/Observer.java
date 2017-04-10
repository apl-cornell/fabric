package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.Observer {
        public void handleUpdates() {
            ((fabric.metrics.util.Observer) fetch()).handleUpdates();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 79, 61, 1, -46, 17,
    -47, 49, 101, -44, -78, 127, 52, 83, 78, 63, 70, -51, 84, 62, 45, 123, 12,
    64, -93, 120, 12, 99, -14, -87, -99, 24, -12 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1491836575000L;
    java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYfWwcxRWfOzu2z5j4IyQkIXFCCEGB5E4RqBK4fCSnmBw5Ytd2kHAAd2537rzx3O5mds4+AymBChLxh/moQ0nbRJUwbQPhQ4gQ1CqQVogGBahAqC1/0OYfxGf+gFYqf9DS92b2bs/r8yWQcNK+3Zt5b957M+/93ts9dIrM8QRZkaUZi8fluMu8eDfNpNK9VHjMTHLqeQMwOmScV5967OPfmp1REk2TFoPajm0ZlA/ZniRz09vpKE3YTCa29qW6tpGYgYKbqDcsSXTbhqIgy12Hj+e4I30lM9bfe0Vi8ud3tL1QR1oHSatl90sqLSPp2JIV5SBpybN8hglvvWkyc5C024yZ/UxYlFt3AqNjD5IOz8rZVBYE8/qY5/BRZOzwCi4TSmdpEM13wGxRMKQjwPw2bX5BWjyRtjzZlSYNWYtx09tBfkLq02ROltMcMC5Il7xIqBUT3TgO7M0WmCmy1GAlkfoRyzYlWRaWKHu8cjMwgGhjnslhp6yq3qYwQDq0SZzauUS/FJadA9Y5TgG0SLJ41kWBqcmlxgjNsSFJFob5evUUcMXUtqCIJPPDbGolOLPFoTOrOK1TW344cZe9yY6SCNhsMoOj/U0g1BkS6mNZJphtMC3Ycnn6Mbrg6J4oIcA8P8SseY7c/cUNazqPHdc8F1Xh6clsZ4YcMqYyc99Zklx9dR2a0eQ6noWhMM1zdaq9/kxX0YVoX1BeESfjpcljfa/fuusp9lmUNKdIg+HwQh6iqt1w8q7FmbiR2UxQycwUiTHbTKr5FGmE57RlMz3ak816TKZIPVdDDY76D1uUhSVwixrh2bKzTunZpXJYPRddQkgjXCQCVxvcfg/3GFyfSrI5MezkWSLDC2wMwjsBF6PCGE5A3grLSHjCSIiCLS1g8ocgiuDmaf97Mh4To0zEwQz33C5XROvbxiIR2NhlhmOyDPXglPyI2dDLISk2OdxkYsjgE0dTZN7RfSpqYhjpHkSr2pcInPSSMEZUyk4WNmz84tmhEzriUNbfNkmWaBvjvo36VEs2glktmEtxQKc4oNOhSDGePJB6WoVMg6dyq7xSC6x0jcupzDoiXySRiHLrAiWvVoWTHgEEAZBoWd1/+00/3rOiDoLUHavHgyuqJF5S+gOCIYcUXFzb7+7/+9ufXKmAtIQsrRUQ1M9kV0U045qtKm7bAzsGBGPA98HjvT/be2r3NmUEcFxSTeFKpEmIYgrh64j7j+94/5//mHovWja8TpIGt5DhliFJE83AnlBDShIrg5p2rP0b+EXg+h9e6CMO4B3wKulnyfJymrhueDuWzoYnCgun7ps8YPY8uU5nfcf0HN1oF/LP/PW/b8YfP/lGlQiIScddy9ko4xU6m0HlxTMK280KblOA/xRAacg4+dnSq5MjH+a02mUhE8PcB28+9MaNq4xHo6TOx70qGD9dqKvSWCgVgkGJstFtHGkGpSvCcS8cg5lQxwK9ly+nh4eO7lwZxRoRg/IlKQAM1ILOsPJpeNtVijBUNSdNzsO4phynSgWnWQ4LZywYUfk8Vx+4D0BkKVytgEhH/PsTODvPRXqBzn/Fv0zRFUguVScQxcdVSC5TbKvhRFYFQQwQygHGIca9lVvtvGNaWYtmOMP0+rr10nWHP59o04fNYURbJ8ia0y8QjC/aQHaduOM/nWqZiIElPEi0gE3XhXnByuuFoONoR/Hed5fu+zPdD3ABqO5ZdzIF1MSPajTqeuX2NYpeF5pbj+QHkpw/TG2Ts62uCanheTPLZK+w8pDuo36ZZHsmH/wmPjGp3de9xCUzynmljO4nlNLz1V5jxl1cS4uS6P7ouZ1/+N3O3VHf4LWS1I86lu5H1gV5T6rn/YKgTdE1Oa46P9etFRZ1QVgA8kDLBgb58TH7TvbWmOvT1iK5SWPVfOjq1GmiaXFtmppYBGCBIM8daF6LteyTkDqWTVVjs0Wbh6QHyY+Q9ANa5phUKFZS2hooDcYXhQsLDm5Dclul4We2YxXm1NgRs8ZcNqx04HTZW+G+gUQhVw7cH4YOPwk1X4km/bDDW7ckdVA+8HE7kpEzdHQmYkz3I6K4IspqxSBqOCrP0FG13KrAxx1IFFMB4pPtKFDuVfOwMeM4nFFVoZX0+DnystKJXTXm7vuODt6D5F4kP4VDlI5+w6iSOBUTVYP4ASS7v68gfqjG3CNn6HqgdDAUyRNIHkbyKKKQI63sOP7biGSvevq+HPtFjblfnbVj+5D8Esl+ADvt2HrOy779+pz75mdltRyp546dU0JP1hCCqOsMoi6FfacouNBEbiwazC11wbeodZ466w2aQnIQyTNg4Bi1ZHlvnv82e3O6NI4GXL9BMqK4Ds8OaU8rhiPfGp8HA99eRPISkpcDf865Z5UB++rp/Pnj2fjzCpJjSP5UzZ8i4FfpTQ9buIuqvIH630OM5Gts6sPNa+bP8va5cMYXKl/u2QOtTRce2Po31XuXv3XE4BUtW+C8orWvbPMbXMGylvIjpltq3RIdl2RelddViEO8qS15XXOekGTudE6pPhbhUyXf24Bemg///UV15YsDUsqvDn+tKg3RdFxXiy4uCPxwd+hfF37V0DRwUr0rwhEt77k28m77O+vYey/cc1X/luu73xq4bu1dLTc8UWwxvjy4f+G//w+ZSDPxUBQAAA==";
}
