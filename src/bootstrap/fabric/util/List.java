package fabric.util;

public interface List extends fabric.util.Collection, fabric.lang.Object {
    public boolean add(fabric.lang.Object o);
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.List
    {
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    	public native boolean add(fabric.lang.Object o);
  		public native Iterator iterator();
  		public native int size();
    }
}
