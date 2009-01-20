package jif.lang;

public interface Principal extends fabric.lang.Object {
    
    java.lang.String name();
    
    boolean delegatesTo(final jif.lang.Principal p);
    
    boolean equals(final jif.lang.Principal p);
    
    boolean isAuthorized(final fabric.lang.Object authPrf,
                         final jif.lang.Closure closure,
                         final jif.lang.Label lb, final boolean executeNow);
    
    jif.lang.ActsForProof findProofUpto(final fabric.client.Core core,
                                        final jif.lang.Principal p,
                                        final fabric.lang.Object searchState);
    
    jif.lang.ActsForProof findProofDownto(final fabric.client.Core core,
                                          final jif.lang.Principal q,
                                          final fabric.lang.Object searchState);
    
    public static class $Proxy extends fabric.lang.Object.$Proxy
      implements jif.lang.Principal
    {
        
        public native java.lang.String name();
        
        public native boolean delegatesTo(jif.lang.Principal arg1);
        
        public native boolean equals(jif.lang.Principal arg1);
        
        public native boolean isAuthorized(fabric.lang.Object arg1,
                                    jif.lang.Closure arg2, jif.lang.Label arg3,
                                    boolean arg4);
        
        public native jif.lang.ActsForProof findProofUpto(fabric.client.Core arg1,
                                                   jif.lang.Principal arg2,
                                                   fabric.lang.Object arg3);
        
        public native jif.lang.ActsForProof findProofDownto(fabric.client.Core arg1,
                                                     jif.lang.Principal arg2,
                                                     fabric.lang.Object arg3);
        
        public $Proxy(fabric.client.Core core, long onum) { super(core, onum); }
    }
}
