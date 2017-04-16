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
   *
   * @return true iff there was a modification that needs to be processed by any
   * parents (if any) of this object.
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
    
    public static final byte[] $classHash = new byte[] { -119, 115, -72, 127,
    -50, 51, 80, 107, -96, -91, -3, 60, 2, -86, 59, -97, 94, 107, 93, -12, -41,
    -69, 11, 124, -8, -30, -86, 18, -83, -14, 103, -68 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1492364243000L;
    java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYYWwcxRWeOzu2L3FixyGGGMcJIaQKhDtFSJXAFEhOCTlyECuOf+A0PuZ2586L53Y3s7P2hSYQQCgBJFcqJiSV4vaHU2gI0FZNQQoRoa0KIYAEraBUahtVQm1F84O2UvlRSt+b3btdr8/nQMJJ+3Zu5r2Z9968972ZPXGBzHMEWVWgeYMn5R6bOcnNNJ/J9lHhMD3NqePsgN6ctqAxc+hvz+o9cRLPklaNmpZpaJTnTEeSRdn76ShNmUymBrZneneShIaCW6gzLEl858ayICtti+8pckv6i8yY/+kbUhPPDLX/rIG0DZI2w+yXVBpa2jIlK8tB0lpipTwTzgZdZ/ogWWwypvczYVBuPACMljlIOhyjaFLpCuZsZ47FR5Gxw3FtJtSalU5U3wK1hatJS4D67Z76rjR4Kms4sjdLmgoG47qzmzxIGrNkXoHTIjB2ZitWpNSMqc3YD+zzDVBTFKjGKiKNI4apS7IiKlG1ePVWYADR5hKTw1Z1qUaTQgfp8FTi1Cym+qUwzCKwzrNcWEWSrlknBaYWm2ojtMhyklwV5evzhoArodyCIpIsjbKpmWDPuiJ7FtqtC/fcOv4dc4sZJzHQWWcaR/1bQKgnIrSdFZhgpsY8wdbrs4do5+mDcUKAeWmE2eN5ee+nd6zrOfOmx3N1DZ5t+fuZJnPaVH7Re93ptTc3oBottuUYGArTLFe72ueP9JZtiPbO6ow4mKwMntn+m3v3H2efxMn8DGnSLO6WIKoWa1bJNjgTdzKTCSqZniEJZuppNZ4hzdDOGibzercVCg6TGdLIVVeTpf6DiwowBbqoGdqGWbAqbZvKYdUu24SQZnhIDJ7lhDSsh/dC+LtWkq2pYavEUnnusjEI7xQ8jAptOAV5Kwwt5QgtJVxTGsDkd0EUwcvx7N+Wd5gYZSIJatiXd7oyat8+FouBY1dols7y1IFd8iNmYx+HpNhicZ2JnMbHT2fIktNHVNQkMNIdiFbllxjsdHcUI8KyE+7GTZ++mDvnRRzK+m6TpNvTMenr6O1qRUdQqxVzKQnolAR0OhErJ9OTmedVyDQ5KreqM7XCTLfYnMqCJUplEosps65Q8mpW2OkRQBAAida1/bvuuu/gqgYIUnusETeurJK4u/IHBCMGKbj4Vr999Pfv/v0mBaQVZGkLQVA/k72haMY521TcLg702CEYA74/Hu576ukLB3YqJYDj2loLrkaahiimEL6WeOzN3R/9+U9Tv4tXFW+QpMl289zQJGmhefAJ1aQkiSqoeYYt/gJ+MXj+hw/aiB34BrxK+1myspomth11x/LZ8ERh4dQjE5P6tmPrvazvmJ6jm0y39MIHn7+dPHz+bI0ISEjLvpGzUcZDay6AJa+ZUdjuVnCbAfynAEo57fwny29Oj3xc9JZdEVExyv3ju0+cvXON9r04afBxrwbGTxfqDSsLpUIwKFEmmo0982HRVdG4F5bGdKhjwbrXr6Qnc6f3rY5jjUhA+ZIUAAZqQU908Wl421uJMFxqXpYswLimHIcqBWe+HBbWWNCj8nmRt+HgxARu3kp4OgmJu/57CEeX2Eiv8PJf8a9QdBWS69QOxLG5Bsk3FNta2JE1QRADhHKAcYhxZ/WAWbJ0o2DQPGeYXv9tu279yX+Mt3ubzaHH006QdXNPEPQv20j2nxv6T4+aJqZhCQ8SLWDz6sKSYOYNQtA9qEf54feXH3mDHgW4AFR3jAeYAmriRzUqdbsy+xZFb4uMbUDyTUkWDlNT52zA1iE1HGdmmewTRgnSfdQvk+zgxBNfJMcnPPO9s8S1M8p5WMY7T6hFFypfY8ZdU28VJbH5ry/tO/XcvgNxX+EbJWnOWxZn1FQGrZ8eCViTugExkv676StHwuw+u6fOWB+SDOBlEaoqo4V+V50EHMW8FPr9UqA2EQASu7vChijIqo1hncGRyztfJNUp1rbrGdYQGAYoCsdPcO6cFu6sM7bL0xbJQLliVruKTFQt6ammBpYB8GHB4hYcxMv19JMAA4ZJ1SHtXk89JINIvo1kCJAfXKoQuerLYNGgf1m0SGJnAUkxrPjFeSykTh2PWHXGdkcXzc0VfyHzVWlVsQtA2jIMt5U0nF+UaNpPIXxtlqQBSiE2XSSjF2noXDEfU1wxpbVi2FfH0Icu0lA13ZrAxr1IHkSyH+KT7XapVyI3IXlUtS6TOWFtD9YZe+IrWnIAyeNInoTdkpZ3LaqRIaGBmtE6juS7X1e0PlNn7MhFmh4syiIhewjJYSTfR7ixpFHYUytgG0ctQ8f2USSTX5exx+qMPXvJxk4h+RGS5wDpPGM3cAVik0iev+y2+SlZ06PcMotK6Cd1hCASe4JIzOABWrg2nIY3lTVmV47zVM1z8pId9BKSnyN5BRQco4as+ubUl/HNXKkdD7h+imRUcZ2ZHc9+oRh++aXBmQW2vYbkdSS/Cuy57JaFA/atuex5+1LsOYvkHJJ3atlTBkyrXFnxLHp1jau0/2FHS/+aTX28dd3SWa7RV8341ObLvTjZ1nLl5MCH6hJR/WiTgLtmweU8dEcJ31eabMEKhrIj4d0NvPPQbyVZUuPeDXGIL+WS9zzODyRZNJ1Tqq9e2ArzfQSI5vHhvz+owtwVkEp+dfhz1TgNTcd6NWmXK/AL5Il/XflZU8uO8+rSizeax51XHnr3pr6RHx77/Nb48d4fDI3s+veHpxbs/ewvxzte+Gfx1f8DopSg5BkVAAA=";
}
