package fabric.lang.security;

public interface ConfPolicy
  extends fabric.lang.security.Policy, fabric.lang.Object
{
    
    fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p,
                                         java.util.Set dependencies);
    
    fabric.lang.security.ConfPolicy join(fabric.lang.security.ConfPolicy p,
                                         java.util.Set dependencies);
    
    fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p,
                                         java.util.Set dependencies);
    
    fabric.lang.security.ConfPolicy meet(fabric.lang.security.ConfPolicy p,
                                         java.util.Set dependencies);
    
    fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p);
    
    fabric.lang.security.ConfPolicy join(fabric.lang.security.ConfPolicy p);
    
    fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p);
    
    fabric.lang.security.ConfPolicy meet(fabric.lang.security.ConfPolicy p);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.ConfPolicy
    {
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3);
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.lang.security.ConfPolicy arg1, java.util.Set arg2);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.lang.security.ConfPolicy arg1, java.util.Set arg2);
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2);
        
        native public fabric.lang.security.ConfPolicy join(
          fabric.lang.security.ConfPolicy arg1);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2);
        
        native public fabric.lang.security.ConfPolicy meet(
          fabric.lang.security.ConfPolicy arg1);
        
        native public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                         java.util.Set arg2);
        
        native public void clobberThisPlaceholders(Principal replacement);
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
