package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObjectSet;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.treaties.TreatySet;
import java.util.SortedSet;

/**
 * Utility to make treaties and observers exist outside of metrics for the
 * purposes of managing contention and conflicts between transactions.
 *
 * This acts as a proxy for the Metric's treaties and observers.
 * TODO: Should this still be a proxy?
 */
public interface TreatiesBox extends fabric.lang.Object {
    public fabric.metrics.Metric get$owner();
    
    public fabric.metrics.Metric set$owner(fabric.metrics.Metric val);
    
    public fabric.worker.metrics.treaties.TreatySet get$treaties();
    
    public fabric.worker.metrics.treaties.TreatySet set$treaties(
      fabric.worker.metrics.treaties.TreatySet val);
    
    public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
      fabric.metrics.Metric m);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.TreatiesBox {
        public fabric.metrics.Metric get$owner() {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).get$owner(
                                                                       );
        }
        
        public fabric.metrics.Metric set$owner(fabric.metrics.Metric val) {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).set$owner(
                                                                       val);
        }
        
        public fabric.worker.metrics.treaties.TreatySet get$treaties() {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).
              get$treaties();
        }
        
        public fabric.worker.metrics.treaties.TreatySet set$treaties(
          fabric.worker.metrics.treaties.TreatySet val) {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).
              set$treaties(val);
        }
        
        public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
          fabric.metrics.Metric arg1) {
            return ((fabric.metrics.util.TreatiesBox) fetch()).
              fabric$metrics$util$TreatiesBox$(arg1);
        }
        
        public _Proxy(TreatiesBox._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.TreatiesBox {
        public fabric.metrics.Metric get$owner() { return this.owner; }
        
        public fabric.metrics.Metric set$owner(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.owner = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.Metric owner;
        
        public fabric.worker.metrics.treaties.TreatySet get$treaties() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.treaties;
        }
        
        public fabric.worker.metrics.treaties.TreatySet set$treaties(
          fabric.worker.metrics.treaties.TreatySet val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.treaties = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public transient fabric.worker.metrics.treaties.TreatySet treaties;
        
        public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
          fabric.metrics.Metric m) {
            this.set$owner(m);
            fabric$lang$Object$();
            this.set$$associates(
                   fabric.worker.metrics.ImmutableObjectSet.emptySet().add(m));
            this.
              set$treaties(
                fabric.worker.metrics.treaties.TreatySet.
                    emptySet((fabric.metrics.util.TreatiesBox)
                               this.$getProxy()));
            return (fabric.metrics.util.TreatiesBox) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.TreatiesBox._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.owner, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.owner = (fabric.metrics.Metric)
                           $readRef(fabric.metrics.Metric._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.TreatiesBox._Impl src =
              (fabric.metrics.util.TreatiesBox._Impl) other;
            this.owner = src.owner;
            this.treaties = src.treaties;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.TreatiesBox._Static {
            public _Proxy(fabric.metrics.util.TreatiesBox._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.TreatiesBox._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  TreatiesBox.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.TreatiesBox._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.TreatiesBox._Static._Impl.class);
                $instance = (fabric.metrics.util.TreatiesBox._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.TreatiesBox._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.TreatiesBox._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -120, -62, 8, -107, 1,
    -28, 93, 58, 43, 17, 22, -101, -109, 43, 61, -92, 100, -109, -117, -108,
    -45, 78, -26, 32, 83, 60, -14, -52, -33, 36, 89, 14 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549064023000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVXaWwTRxQeO8GJQ5oTAoQQQnARpy1oVQkCLcTicDEQ4YBEEITx7jhZst5dZsfEoaWilSqQKvGjBAri+JWqKk1BaoWqVoqEqh4gUKUe6vGjwI9SUVGk0qrHj15vZtbe9SaUX7W0M+OZ9968fcf33o7eQ5NsitozOK3pUTZkETu6DqcTyS5MbaLGdWzb3bDbq0wuT5y485raGkTBJKpWsGEamoL1XsNmqCa5F+/HMYOw2LatiY6dKKxwxg3Y7mcouLMzT1GbZepDfbrJnEvGyT++KDb8yu66t8pQbQ+q1YwUw0xT4qbBSJ71oOosyaYJtdeoKlF7UL1BiJoiVMO6dgAITaMHNdhan4FZjhJ7K7FNfT8nbLBzFqHizsImV98EtWlOYSYF9euk+jmm6bGkZrOOJAplNKKr9j70HCpPokkZHfcBYVOy8BYxITG2ju8DeZUGatIMVkiBpXxAM1SGZvs5im8c2QgEwFqRJazfLF5VbmDYQA1SJR0bfbEUo5rRB6STzBzcwlDzA4UCUaWFlQHcR3oZmu6n65JHQBUWZuEsDE31kwlJ4LNmn8883rq3eeXRZ4wNRhAFQGeVKDrXvxKYWn1MW0mGUGIoRDJWL0yewE1jR4IIAfFUH7GkeefZ+6sXt16+ImlmTkCzJb2XKKxXGUnXfNoSX7C8jKtRaZm2xkOh5M2FV7uck468BdHeVJTID6OFw8tbP9px6Dy5G0RVCRRSTD2XhaiqV8yspemEricGoZgRNYHCxFDj4jyBKmCd1Awid7dkMjZhCVSui62QKf6DiTIggpuoAtaakTELawuzfrHOWwihCnhQAJ42hMpuwFyPUDDGUCrWb2ZJLK3nyCCEdwwegqnSH4O8pZqyRDGtoZhNlRjNGUwDSrkfg1CCyZZG6KYEMoXYnWY+CupY/4/YPH+busFAAAw9WzFVksY2eM2JoM4uHZJkg6mrhPYq+tGxBGocOyWiKMwj34boFXYKgOdb/Jjh5R3Oda69f6H3moxAzuuYEZJOqhl11JRe9qgJmlXz9IoCYEUBsEYD+Wj8XOINEUUhW6RbUVg1CFth6ZhlTJrNo0BAvNkUwS8Eg/MHAFQAN6oXpHY9vedIexnErTVYzl0JpBF/FrnYk4AVhtToVWoP3/nt4omDpptPDEXGpfl4Tp6m7X4zUVMhKsCgK35hG77UO3YwEuQQEwb0YxjiE6Ck1X9HSbp2FKCPW2NSEk3mNsA6PyrgVRXrp+aguyPcX8OHBhkJ3Fg+BQVqrkpZZ7/+5IfHRD0pAGytB4lThHV4kpoLqxXpW+/aHpxKgO7bk13Hjt87vFMYHijmTnRhhI9xSGYMWWzSF6/s++bmjZEvgq6zGApZubSuKXnxLvX/wC8Az9/84ZnJN/gM+Bx3UKGtCAsWv3meqxsAhA4gBarbkW1G1lS1jIbTOuGR8mfto0sv/Xi0Trpbhx1pPIoWP1yAuz+jEx26tvv3ViEmoPAC5drPJZOo1+hKXkMpHuJ65J//bNapj/FZiHzALFs7QAQMIWEPJBy4TNhiiRiX+s4e50O7tFaL2A/a4yvAOl5K3VjsiY2eaY4/eVcmfTEWuYw5EyT9duxJk2Xns78G20MfBlFFD6oTVRwbbDsGAIMw6IE6bMedzSR6pOS8tKbKAtJRzLUWfx54rvVngQs2sObUfF0lA18GDhiilhupBZ5GMMoeZ07x00aLj1PyASQWKwTLXDHO48MCaUi+XMhQWMtmc4y7XVywiEEXMAjmEvRToXj7sG6TmPlhs0w/Pj5RVKuRqzUPnia44qIzn5lArc6HqMUoNmyNGCxflB3kshscmaed+SWPbIYqmYPCBfXnO+oPmnSA0OJbFMgkag+BbwXDDLiYw7FuQucp4SU/sZ4Boaerm/iFnIIadeb5Ht08MYx4gZj1oN5H9G0jLwyfU7e8ulR2KA2l/cRaI5d988u/rkdP3ro6QXUKM9NaopP9RPfcyQvFnHFN+CbRGrrhf+vurOXxgdt98trZPhX91K9vGr26fp7ychCVFeN8XD9aytRRGt1VlEA7bXSXxHhb0ahhbqyn4JkOxvzJmd/zBpMbgnP5sLk0Viodlned+W2/P1zUCbheXS2k7v4PWNrDhx0MtcngijhRFeHeiXj6gIiMIIYmezY5VM6coG9xumol/gEZub1x8dQH9CzTx33nOHwXztVWTju37StRgosdcxgqXCan615I8axDFiUZTbxUWAKMJSZAs8YJmhyGyvkkjKJISvgAqymlZOKTg6+8dANQ/CQd/6dbRQSRQyFfGxxZHESjEkQLmVnaKAmhzTnKP/9Gf5n2R6iy+5Yotby5PfJ+5fHAd7tWLKpvOn1s0aoR9dhLw59v/r4ttfLn6zcjO2r+BXrpBG2WDgAA";
}
