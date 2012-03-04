package fabric.util;

public interface AbstractCollection
  extends fabric.util.Collection, fabric.lang.Object
{
    public boolean add(fabric.lang.Object o);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.AbstractCollection
    {
        public _Proxy(AbstractCollection._Impl impl) { super(impl); }
    	public native boolean add(fabric.lang.Object o);
  		public native Iterator iterator();
  		public native int size();
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    abstract public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.AbstractCollection
    {
        protected _Impl(fabric.worker.Store $location) { super($location); }
    	public native boolean add(fabric.lang.Object o);
  		public native Iterator iterator();
  		public native int size();
	}
}
