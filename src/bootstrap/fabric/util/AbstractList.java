package fabric.util;

public interface AbstractList
  extends fabric.util.List, fabric.util.AbstractCollection
{
    public boolean add(fabric.lang.Object o);
    public fabric.lang.Object $initLabels();
        
    abstract public static class _Impl
    extends fabric.util.AbstractCollection._Impl
      implements fabric.util.AbstractList
    {
        protected _Impl(fabric.worker.Store $location) { super($location); }
        native public boolean add(fabric.lang.Object o);
    	native public fabric.lang.Object $initLabels();
    }
    public static class _Proxy extends fabric.util.AbstractCollection._Proxy
      implements fabric.util.AbstractList
    {
    	native public boolean add(fabric.lang.Object o);
    	native public fabric.lang.Object $initLabels();
    	public _Proxy(AbstractList._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
}
