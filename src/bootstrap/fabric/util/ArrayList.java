package fabric.util;

public interface ArrayList
  extends fabric.util.List, fabric.util.RandomAccess, fabric.util.AbstractList
{
    public boolean add(fabric.lang.Object o);
    public fabric.lang.Object $initLabels();
    public static class _Proxy extends fabric.util.AbstractList._Proxy
      implements fabric.util.ArrayList
    {
        
        public _Proxy(ArrayList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    	public native fabric.lang.Object $initLabels();
        public native boolean add(fabric.lang.Object o);
    }
    
    public static class _Impl extends fabric.util.AbstractList._Impl
      implements fabric.util.ArrayList
    {
    	public native fabric.lang.Object $initLabels();
        public native boolean add(fabric.lang.Object o);
        
        public _Impl(fabric.worker.Store $location, int capacity) {
            super($location);
        }
        
        public _Impl(fabric.worker.Store $location) {
            this($location,0);
        }
        
        public _Impl(fabric.worker.Store $location, fabric.util.Collection c) {
            this($location, (int) (c.size() * 1.1F));
        }
        
    }
}
