public interface Equals03 extends fabric.lang.Object {
    
    public int get$a();
    
    public int set$a(int val);
    
    public int postInc$a();
    
    public int postDec$a();
    
    public int get$b();
    
    public int set$b(int val);
    
    public int postInc$b();
    
    public int postDec$b();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements Equals03
    {
        
        public int get$a() { return ((Equals03._Impl) fetch()).get$a(); }
        
        public int set$a(int val) {
            return ((Equals03._Impl) fetch()).set$a(val);
        }
        
        public int postInc$a() {
            return ((Equals03._Impl) fetch()).postInc$a();
        }
        
        public int postDec$a() {
            return ((Equals03._Impl) fetch()).postDec$a();
        }
        
        public int get$b() { return ((Equals03._Impl) fetch()).get$b(); }
        
        public int set$b(int val) {
            return ((Equals03._Impl) fetch()).set$b(val);
        }
        
        public int postInc$b() {
            return ((Equals03._Impl) fetch()).postInc$b();
        }
        
        public int postDec$b() {
            return ((Equals03._Impl) fetch()).postDec$b();
        }
        
        public static void main(fabric.lang.arrays.ObjectArray arg1) {
            Equals03._Impl.main(arg1);
        }
        
        public _Proxy(Equals03._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements Equals03
    {
        
        public int get$a() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(
              this);
            return this.a;
        }
        
        public int set$a(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.a = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$a() {
            int tmp = this.get$a();
            this.set$a((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$a() {
            int tmp = this.get$a();
            this.set$a((int) (tmp - 1));
            return tmp;
        }
        
        int a;
        
        public int get$b() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(
              this);
            return this.b;
        }
        
        public int set$b(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.b = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$b() {
            int tmp = this.get$b();
            this.set$b((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$b() {
            int tmp = this.get$b();
            this.set$b((int) (tmp - 1));
            return tmp;
        }
        
        int b;
        
        public _Impl(fabric.worker.Store $location,
                     fabric.lang.security.Label $label) {
            super($location, $label);
            this.set$a(859);
            this.set$b(859);
            java.lang.System.out.println(
              fabric.lang.Object._Proxy.idEquals(this.get$a(), this.get$b()));
        }
        
        public static void main(fabric.lang.arrays.ObjectArray args) {
            new Equals03._Impl(
              Equals03._Static._Proxy.$instance.$getStore(),
              Equals03._Static._Proxy.$instance.get$label()).$getProxy();
        }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new Equals03._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeInt(this.a);
            out.writeInt(this.b);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, in, refTypes,
                  intraStoreRefs);
            this.a = in.readInt();
            this.b = in.readInt();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            Equals03._Impl src = (Equals03._Impl) other;
            this.a = src.a;
            this.b = src.b;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements Equals03._Static
        {
            
            public _Proxy(Equals03._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            final public static Equals03._Static $instance;
            
            static {
                Equals03._Static._Impl impl =
                  (Equals03._Static._Impl)
                    fabric.lang.Object._Static._Proxy.$makeStaticInstance(
                      Equals03._Static._Impl.class);
                $instance = (Equals03._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements Equals03._Static
        {
            
            public _Impl(fabric.worker.Store store,
                         fabric.lang.security.Label label)
                  throws fabric.net.UnreachableNodeException {
                super(store, label);
            }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new Equals03._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    final public static java.lang.String jlc$CompilerVersion$fabil = "0.1.0";
    final public static long jlc$SourceLastModified$fabil = 1278859727000L;
    final public static java.lang.String jlc$ClassType$fabil =
      ("H4sIAAAAAAAAAIVWa2xURRSevbvddtvFPqA8Sl9gCSBhS0KCmhKxaWisLOnS" +
       "8pBFsszeO7u9MHvv\n5d7Z7bYo0WgESTRB0Wii+IeExPDDQKKJ7wg+otEYfs" +
       "AvSAjGRxQSfhgbo9EzM/fu4+4WNrmTszPn\nnDlzznce526hBsdGKzM4rdMY" +
       "m7GIExvF6bF4AtsO0UYodpydsJtSlSeXvLLl+b+/UBAq2qjfMulM\nlprMla" +
       "lhP/Jh88bOfZdmg6g1iVp1Y5JhpqsjpsFIkSVRNEdyaWI7w5pGtCRqNwjRJo" +
       "mtY6rPAqNp\nJFGHo2cNzPI2cSaIY9ICZ+xw8haxxZ3eZhxFVdNwmJ1XmWk7" +
       "DLXFD+ICHswznQ7GdYcNxVE4oxOq\nOYfRUaTEUUOG4iwwLo57rxgUGgdH+T" +
       "6wN+tgpp3BKvFEQod0Q2Oozy9RevHANmAA0cYcYVNm6aqQ\ngWEDdUiTKDay" +
       "g5PM1o0ssDaYebiFoa55lQJTk4XVQzhLUgwt9fMl5BFwRYRbuAhDnX42oQli" +
       "1uWL\nWUW0xsPRf08k/upXUABs1ohKuf1hEOr1CU2QDLGJoRIpOJePnRrbm+" +
       "+WqOj0MUue4VUf7Ir/+lmf\n5Fleh2c8fZCoLKX+s6m75/LwT5EgN6PJMh2d" +
       "Q6Hq5SKqCfdkqGgBeBeXNPLDmHf4+cRXe595l/yu\noMgYCqsmzeeMMRQhhj" +
       "bi0o1Ax3WDyN3xTMYhbAyFqNgKm+I/uCOjU8Ld0QC0bmRMj7YwmxJ00UII\n" +
       "NcIXgK8NyZ/CF4aiWw/nMXU2bIyBGouztxf5et90IAC2d/vziALoHjOpRuyU" +
       "evbmt09t3fbicRkV\njiT3OoaaPLUoEBCKllQ7gXtV4+D/4/xQ28vrnfcVFE" +
       "yiiJ7L5RlOUwJJgyk1p4mWYgI17RUIFcAA\nVEXTADDAaoqCIgFoeGkBqoUf" +
       "SOX0GwMKAzouH/3vx9up6Qs85jxGi7h2aRp4/JC0Lbp2cv/jB46v\nDHKm6R" +
       "B3ILAO3Ft7Sr19YvuFK99dW1MGLEMDNXlUK8nzwG9+wjZVokGdKas/s6w1uA" +
       "ftPqmgECQX\nlBeGARSQq73+O6ryYcirLdxZwThqyZh2DlN+5BWEZjZlm9Pl" +
       "HQGGqKBbwQER+MAdqMVFEUcVEqip\nxA53qO8RonTNPRfecPXjli+FV7wq11" +
       "pRDicJkznTXo7HTpsQ2L/2RuLV124d2yeC4UaDobCVT1Nd\nLQoDOwMQ/IV1" +
       "8je2dNGp19e+ddWL9sKy9mHbxjM82MVnL/e8+TV+G3IbcszRZ4lIGyRuQt4F" +
       "fH1A\n0OsqDvn/fpeFA9CfNaO8unvRy6WP/HnxdHO/NIbLdAk1ilNbzaoEU+" +
       "rsp7tOz33Prgv/lcPOdfQV\na6/djSsQ+dCVQnv4vXdyCmpMojbRkbDBdmOa" +
       "595NQk9xRtzNOFpQdV7dH2QxHCrButsPuYpr/YAr\nFwmgOTenm3wY45WpCb" +
       "6Qi7GQH2MBJIgHhcRKsa6y3FAwFMB1HJmw9RxU3YLbFk72nvn5ws2JRTI9\n" +
       "Ze+8v6Z9VcrI/iksbbGKcMOKu90guC+tW3Hu6MT1tOwrHdUFcKuRz/0yc5Gs" +
       "3vzSjTr1MwgdXqYS\nXzfW+KbB9U3DPL4Z4cvD4Iw0Jx4RqoTxAy5Ky0Du8h" +
       "Buo575OrB4z7En7kRfwJf2Ky7iH2Uowkxr\nPSUFQsvg9yvZLgYODxGte/pu" +
       "jG46+7Qf/byy9t1VMqW2F5bvCE7p3ygCShJ9NRNPtdBQNeaabQID\nm7GzCn" +
       "m9Je/y3rgGvgWudxfUr241sFM4vRqqkSOmyPrFIlB6Z49vmrV1VZQhic0fzs" +
       "4t+2TgtzkJ\nG/9QVMF4PjhwR/lo8YBoAqE0duSb/NNk7bBYNQOKNzWXXNAF" +
       "X//dXCA4O2CKFTWUV4SYnBhrHiqQ\ny5d9HJW+v5w4UMeTDAqGbmAx362GbA" +
       "5TYmTlHCNQnKyVCUhevmplvmLJHsUtr67hC8uGj1DTIHzc\n8M7kGKCbsdKs" +
       "D4fFui8bl08Rd1Uk1jwd4p7tIwcPV7k5dfwrK27x3kp2MBTKQUsQh5stKbAF" +
       "Ngum\nrhUrpjLL8q7pyAj8VV30P6MLsVN1DQAA");
}
