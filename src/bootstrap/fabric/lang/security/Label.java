package fabric.lang.security;

public interface Label extends fabric.lang.Object {
    
    boolean relabelsTo(fabric.lang.security.Label l, java.util.Set s);
    
    fabric.lang.security.Label join(fabric.worker.Store store,
                                    fabric.lang.security.Label l);
    
    fabric.lang.security.Label join(fabric.lang.security.Label l);
    
    fabric.lang.security.Label meet(fabric.worker.Store store,
                                    fabric.lang.security.Label l);
    
    fabric.lang.security.Label meet(fabric.lang.security.Label l);
    
    fabric.lang.security.ConfPolicy confPolicy();
    
    fabric.lang.security.IntegPolicy integPolicy();
    
    fabric.lang.security.SecretKeyObject keyObject();
    
    void clobberThisPlaceholders(Principal replacement);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.Label
    {
        
        native public boolean relabelsTo(fabric.lang.security.Label arg1,
                                         java.util.Set arg2);
        
        native public fabric.lang.security.Label join(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2);
        
        native public fabric.lang.security.Label join(
          fabric.lang.security.Label arg1);
        
        native public fabric.lang.security.Label meet(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2);
        
        native public fabric.lang.security.Label meet(
          fabric.lang.security.Label arg1);
        
        native public fabric.lang.security.ConfPolicy confPolicy();
        
        native public fabric.lang.security.IntegPolicy integPolicy();
        
        native public fabric.lang.security.SecretKeyObject keyObject();
        
        native public void clobberThisPlaceholders(Principal replacement);
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
