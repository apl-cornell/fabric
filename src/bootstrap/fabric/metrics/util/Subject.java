package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collection;
import fabric.util.List;

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
    public fabric.util.List getObservers();
    
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
        
        public fabric.util.List getObservers() {
            return ((fabric.metrics.util.Subject) fetch()).getObservers();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -29, -30, 123, -119,
    58, -120, 110, -8, -17, 17, 29, 74, -27, -65, 78, 40, -51, -22, 42, 26, -34,
    -127, 22, 31, 34, 81, -68, 91, -67, 123, 79, 60 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1520977993000L;
    java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVYe2wUxxmfOx+2z7j4QXCCwTZxgARK7ooqVQpO2+ATj4NL7NrwR7DAmdudszfs7S6zc/YZhwQqUWj/cNrU0EQKbqUQ5UWJVClNX1bTNm1BeUhJX6nUENSKJiGlbRSpyR9p0++b2btdn48zBDhpv9mb+Wbm933zvWZPXCDzXE46MzRtmDEx5jA3tpGmk6leyl2mJ0zqutugd1CbH0kefecJvT1MwilSr1HLtgyNmoOWK8iC1L10hMYtJuLb+5JdAySq4cTN1B0WJDzQnedkmWObY0OmLbxNZq1/5LPxye/savxBFWnYQRoMq19QYWgJ2xIsL3aQ+izLphl31+s603eQJosxvZ9xg5rGXmC0rR2k2TWGLCpynLl9zLXNEWRsdnMO43LPQifCtwE2z2nC5gC/UcHPCcOMpwxXdKVIdcZgpu7uIfeTSIrMy5h0CBhbUgUp4nLF+EbsB/Y6A2DyDNVYYUpkt2HpgnSUzihKvHwrMMDUmiwTw3Zxq4hFoYM0K0gmtYbi/YIb1hCwzrNzsIsgrRddFJhqHartpkNsUJAbSvl61RBwRaVacIogi0rZ5EpwZq0lZxY4rQt33T4xbm22wiQEmHWmmYi/Fia1l0zqYxnGmaUxNbF+deoobZk+HCYEmBeVMCue5+97/4417S+cUjxLyvD0pO9lmhjUjqcXvLY0seq2KoRR69iugaYwQ3J5qr3eSFfeAWtvKa6Ig7HC4At9v7l7/9PsvTCpS5JqzTZzWbCqJs3OOobJ+CZmMU4F05Mkyiw9IceTpAbeU4bFVG9PJuMykSQRU3ZV2/I/qCgDS6CKauDdsDJ24d2hYli+5x1CSA08JATP5wiJXIC2Hv6OC7IlPmxnWTxt5tgomHccHka5NhwHv+WGFne5Fuc5SxjA5HWBFUHjKvn7c1JhMUDhXNXV8oi9cTQUArV2aLbO0tSFM/LspbvXBJfYbJs644OaOTGdJAunH5E2E0U7d8FWpVZCcM5LSyNEcO5krnvD+ycHX1L2hnM9pQmyREGMeRDVmXoQAVU9OlIMQlMMQtOJUD6WmEo+I+2l2pWOVVyoHhZa55hUZGyezZNQSEp1nZwvF4Vj3g3hAyJE/ar+nVvuOdxZBRbqjEbw1PLSg5cW/sDEEnlkrPhiv3PsjVff/byMooWw0hCIP/1MdAVMGddskEbb5OPYxhkDvjcf7v32kQuHBiQI4Lip3IbLkSbAhCnYrs0Pntrz57fOHP99uAi8SpBqJ5c2DU2QWpoGnVBNCBItRjQlWNMn8AvB8z98UEbswBaCVcJzkWVFH3GcUnW0XSyYyEB4/KuTU3rP42uVyzfPdNANVi77/T/+9+XYw2dPlzGAqLCdW002wszAngtgyxtnZbU7ZaxNQvCnEJEGtbPvtd2W2H1uSG3bUQKxlPupO0+c3rRSeyhMqrygVybAz5zUFQQLeYIzyE8Wio09dbBpZ6nZc1tjOiQxf9/Vy+hzg9P7locxQUQhdwkK0QUSQXvp5jOCbVfBwnCreSkyH+2amjhUyDZ1Ypjbo36PdOcF6sBBiVE8vBXwtBASftRr9+PoQgfpdcr9JX+HpJ1IVsgTCOPrSiQ3S7ZVcCIrfSOG+GmCh4KNu8u3W1lbNzIGTZsM3evjhhVrn/vHRKM6bBN6FDpO1sy9gN+/uJvsf2nXh+1ymZCG+dt3NJ9NJYWF/srrOadjiCN/4PW2R35Lj0G4gJDuGnuZjNIhKV9ICrxIkKXlAlBP2mV8hHHkaZXyf1nyr5P0S6hYzzfwfwLJFwSZT3W9MNOdnUt7uZGFsDDi5VJ2ePIbn8QmJpWaVMFx06ycH5yjig655WfkmaBn3lhpFzlj49vP7vvpk/sOhT24twoSGbENVbSsnWkut8DTDmGl12vXfmpzmakuT+n49w7J0FtBn31ItkJhylnWHmHBw9hUDvTNys6rPvbaM9cK9N0VQA8g2SZIna3g6t1jkm+Dd1LYbBakJm3bJqNWOTna4FkNpQP32l1XSY4gTFZhTCbTe0AEw/V0rmPPznJYAR+JA8bzXnv6GmC1KozJnQxI+0NMFH2u4NONnk97eV8FoNagGDL9lc+HLX7trgrVmLwOOU4lsap8sSAjwz0GHHBO+cYrjO1TaJHk8kW5ZJRDaDEFTQ4shiSKxY9pw40uXwmfgJRiWFRW+2MKHpK9SO5Dcj9UEaBQmd0Lmzb4m/r9i0sLLuz8GpJDQeCXprEAnAoaebDC2LdKN31gLusLiD+B5JtIHgLxh+Ham4BSuJzzVkFZha9HkBy9REEvI8o8IBmOVRD0u5coqFxupS/jo0imkHwP7JPtyVFVbu1E8ljAy69YnCDaJyuMPf0pJXkCyVNInoHTEra6X5fxkMBAWWs9ieTZa2Wtz1cY+/Eliu5verDEZH+I5EdIfoLhxhZGZgz/bUIyLd+ulWC/rDD24hUL9gskv0Lya4hqSrD1plmU7dRVly1YDpa4e8S0rSE56ZUKk8Dq2n2rS+LFi+ccuEVtyGvMKVwDD8h1Xr9iBb2M5DUkfwCAo9QQRd28cTm6mcuNwz7Xq0iOSq43Lx67ficZ3rrsQHzQl+0vSGTxdtaX56pLFjTYt+eS590rkefvSN5Bcr6cPHmoBr0vHXiFWVLmA4z3MVBLvMiOn9u6ZtFFPr7cMOvzrDfv5FRD7fVT2/8k757FD33RFKnN5EwzcLUNXnOrHc4yhhQjqq6UqvT5tyALy1yWwAyxkRr5p+L8AGr3mZxCfinFtyDffyB4KT7896Gs6Vp9UnCvZm+tMoXPzLAuF23NcfxqfeKD6z+qrt12Vn4rgRNa9re/jn993WHro381tW059/O7bnnl/OrWMwdaOjq/8rOB6fGe2/8P1+ehCk0XAAA=";
}
