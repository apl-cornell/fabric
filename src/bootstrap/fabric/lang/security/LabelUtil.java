package fabric.lang.security;

import fabric.common.VersionWarranty;

public interface LabelUtil extends fabric.lang.Object {

  public boolean acts_for(fabric.lang.security.Label actor,
      fabric.lang.security.Principal granter);

  public boolean actsFor(fabric.lang.security.Label actor,
      fabric.lang.security.Principal granter);

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.LabelUtil {

    native public static fabric.lang.security.Label noComponents();

    native public static fabric.lang.security.Label noComponents(
        fabric.worker.Store arg1);

    native public static fabric.lang.security.ConfPolicy bottomConf();

    native public static fabric.lang.security.IntegPolicy bottomInteg();

    native public static fabric.lang.security.ConfPolicy topConf();

    native public static fabric.lang.security.IntegPolicy topInteg();

    native public static fabric.lang.security.ConfPolicy bottomConf(
        fabric.worker.Store arg1);

    native public static fabric.lang.security.ConfPolicy topConf(
        fabric.worker.Store arg1);

    native public static fabric.lang.security.IntegPolicy topInteg(
        fabric.worker.Store arg1);

    native public static fabric.lang.security.IntegPolicy bottomInteg(
        fabric.worker.Store arg1);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.util.Collection arg3);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.lang.security.Principal arg1, fabric.util.Collection arg2);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Label arg2,
        fabric.lang.security.Principal arg3, fabric.lang.arrays.ObjectArray arg4);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.lang.security.Label arg1, fabric.lang.security.Principal arg2,
        fabric.lang.arrays.ObjectArray arg3);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.PrincipalSet arg3);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.lang.security.Principal arg1,
        fabric.lang.security.PrincipalSet arg2);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.util.Collection arg3);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.lang.security.Principal arg1, fabric.util.Collection arg2);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.worker.Store arg1, fabric.lang.security.Label arg2,
        fabric.lang.security.Principal arg3, fabric.lang.arrays.ObjectArray arg4);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.lang.security.Label arg1, fabric.lang.security.Principal arg2,
        fabric.lang.arrays.ObjectArray arg3);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.PrincipalSet arg3);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.lang.security.Principal arg1,
        fabric.lang.security.PrincipalSet arg2);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.util.Collection arg3);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.lang.security.Principal arg1, fabric.util.Collection arg2);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.Principal arg3);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.util.Collection arg3);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.lang.security.Principal arg1, fabric.util.Collection arg2);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.worker.Store arg1, fabric.lang.security.Label arg2,
        fabric.lang.security.Principal arg3, fabric.lang.arrays.ObjectArray arg4);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.lang.security.Label arg1, fabric.lang.security.Principal arg2,
        fabric.lang.arrays.ObjectArray arg3);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Label arg2,
        fabric.lang.security.Principal arg3, fabric.lang.arrays.ObjectArray arg4);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.lang.security.Label arg1, fabric.lang.security.Principal arg2,
        fabric.lang.arrays.ObjectArray arg3);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
        fabric.lang.security.PrincipalSet arg3);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.lang.security.Principal arg1,
        fabric.lang.security.PrincipalSet arg2);

    native public static fabric.lang.security.Label toLabel(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
        fabric.lang.security.IntegPolicy arg3);

    native public static fabric.lang.security.Label toLabel(
        fabric.lang.security.ConfPolicy arg1,
        fabric.lang.security.IntegPolicy arg2);

    native public static fabric.lang.security.Label toLabel(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2);

    native public static fabric.lang.security.Label toLabel(
        fabric.lang.security.ConfPolicy arg1);

    native public static fabric.lang.security.Label toLabel(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2);

    native public static fabric.lang.security.Label toLabel(
        fabric.lang.security.IntegPolicy arg1);

    native public static fabric.lang.security.Label liftToLabel(
        fabric.lang.security.ConfPolicy arg1);

    native public static fabric.lang.security.Label liftToLabel(
        fabric.lang.security.IntegPolicy arg1);

    native public static fabric.lang.security.Label join(
        fabric.worker.Store arg1, fabric.lang.security.Label arg2,
        fabric.lang.security.Label arg3);

    native public static fabric.lang.security.Label join(
        fabric.lang.security.Label arg1, fabric.lang.security.Label arg2);

    native public static fabric.lang.security.Label meetLbl(
        fabric.worker.Store arg1, fabric.lang.security.Label arg2,
        fabric.lang.security.Label arg3);

    native public static fabric.lang.security.Label meetLbl(
        fabric.lang.security.Label arg1, fabric.lang.security.Label arg2);

    native public static fabric.lang.security.Label meet(
        fabric.worker.Store arg1, fabric.lang.security.Label arg2,
        fabric.lang.security.Label arg3);

    native public static fabric.lang.security.Label meet(
        fabric.lang.security.Label arg1, fabric.lang.security.Label arg2);

    native public static fabric.lang.security.ConfPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
        fabric.lang.security.ConfPolicy arg3);

    native public static fabric.lang.security.ConfPolicy join(
        fabric.lang.security.ConfPolicy arg1,
        fabric.lang.security.ConfPolicy arg2);

    native public static fabric.lang.security.ConfPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
        fabric.lang.security.ConfPolicy arg3, java.util.Set arg4);

    native public static fabric.lang.security.ConfPolicy join(
        fabric.lang.security.ConfPolicy arg1,
        fabric.lang.security.ConfPolicy arg2, java.util.Set arg3);

    native public static fabric.lang.security.IntegPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        fabric.lang.security.IntegPolicy arg3);

    native public static fabric.lang.security.IntegPolicy join(
        fabric.lang.security.IntegPolicy arg1,
        fabric.lang.security.IntegPolicy arg2);

    native public static fabric.lang.security.IntegPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        fabric.lang.security.IntegPolicy arg3, java.util.Set arg4);

    native public static fabric.lang.security.IntegPolicy join(
        fabric.lang.security.IntegPolicy arg1,
        fabric.lang.security.IntegPolicy arg2, java.util.Set arg3);

    native public static fabric.lang.security.ConfPolicy meetPol(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
        fabric.lang.security.ConfPolicy arg3);

    native public static fabric.lang.security.ConfPolicy meetPol(
        fabric.lang.security.ConfPolicy arg1,
        fabric.lang.security.ConfPolicy arg2);

    native public static fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
        fabric.lang.security.ConfPolicy arg3, java.util.Set arg4);

    native public static fabric.lang.security.ConfPolicy meet(
        fabric.lang.security.ConfPolicy arg1,
        fabric.lang.security.ConfPolicy arg2, java.util.Set arg3);

    native public static fabric.lang.security.IntegPolicy meetPol(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        fabric.lang.security.IntegPolicy arg3);

    native public static fabric.lang.security.IntegPolicy meetPol(
        fabric.lang.security.IntegPolicy arg1,
        fabric.lang.security.IntegPolicy arg2);

    native public static fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
        fabric.lang.security.IntegPolicy arg3, java.util.Set arg4);

    native public static fabric.lang.security.IntegPolicy meet(
        fabric.lang.security.IntegPolicy arg1,
        fabric.lang.security.IntegPolicy arg2, java.util.Set arg3);

    native public static boolean equivalentTo(fabric.lang.security.Label arg1,
        fabric.lang.security.Label arg2);

    native public static boolean isReadableBy(fabric.lang.security.Label arg1,
        fabric.lang.security.Principal arg2);

    native public static boolean isWritableBy(fabric.lang.security.Label arg1,
        fabric.lang.security.Principal arg2);

    native public static boolean relabelsTo(fabric.lang.security.Label arg1,
        fabric.lang.security.Label arg2);

    @Override
    native public boolean acts_for(fabric.lang.security.Label arg1,
        fabric.lang.security.Principal arg2);

    @Override
    native public boolean actsFor(fabric.lang.security.Label arg1,
        fabric.lang.security.Principal arg2);

    native public static boolean relabelsTo(fabric.lang.security.Policy arg1,
        fabric.lang.security.Policy arg2);

    native public static boolean relabelsTo(fabric.lang.security.Policy arg1,
        fabric.lang.security.Policy arg2, java.util.Set arg3);

    native public static java.lang.String stringValue(
        fabric.lang.security.Label arg1);

    native public static java.lang.String toString(
        fabric.lang.security.Label arg1);

    native public static int hashCode(fabric.lang.security.Label arg1);

    native public static void notifyNewDelegation(
        fabric.lang.security.Principal arg1, fabric.lang.security.Principal arg2);

    public _Proxy(LabelUtil._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  final public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.lang.security.LabelUtil {

    private _Impl(fabric.worker.Store $location) {
      super($location);
    }

    native public static fabric.lang.security.Label noComponents();

    native public static fabric.lang.security.Label noComponents(
        fabric.worker.Store store);

    native public static fabric.lang.security.ConfPolicy bottomConf();

    native public static fabric.lang.security.IntegPolicy bottomInteg();

    native public static fabric.lang.security.ConfPolicy topConf();

    native public static fabric.lang.security.IntegPolicy topInteg();

    native public static fabric.lang.security.ConfPolicy bottomConf(
        fabric.worker.Store store);

    native public static fabric.lang.security.ConfPolicy topConf(
        fabric.worker.Store store);

    native public static fabric.lang.security.IntegPolicy topInteg(
        fabric.worker.Store store);

    native public static fabric.lang.security.IntegPolicy bottomInteg(
        fabric.worker.Store store);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.worker.Store store, fabric.lang.security.Principal owner,
        fabric.lang.security.Principal reader);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.lang.security.Principal owner,
        fabric.lang.security.Principal reader);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.worker.Store store, fabric.lang.security.Principal owner,
        fabric.util.Collection readers);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.lang.security.Principal owner, fabric.util.Collection readers);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.worker.Store store, fabric.lang.security.Label lbl,
        fabric.lang.security.Principal owner,
        fabric.lang.arrays.ObjectArray readers);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.lang.security.Label lbl, fabric.lang.security.Principal owner,
        fabric.lang.arrays.ObjectArray readers);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.worker.Store store, fabric.lang.security.Principal owner,
        fabric.lang.security.PrincipalSet writers);

    native public static fabric.lang.security.ConfPolicy readerPolicy(
        fabric.lang.security.Principal owner,
        fabric.lang.security.PrincipalSet writers);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.worker.Store store, fabric.lang.security.Principal owner,
        fabric.lang.security.Principal reader);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.lang.security.Principal owner,
        fabric.lang.security.Principal reader);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.worker.Store store, fabric.lang.security.Principal owner,
        fabric.util.Collection readers);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.lang.security.Principal owner, fabric.util.Collection readers);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.worker.Store store, fabric.lang.security.Label lbl,
        fabric.lang.security.Principal owner,
        fabric.lang.arrays.ObjectArray readers);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.lang.security.Label lbl, fabric.lang.security.Principal owner,
        fabric.lang.arrays.ObjectArray readers);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.worker.Store store, fabric.lang.security.Principal owner,
        fabric.lang.security.PrincipalSet readers);

    native public static fabric.lang.security.Label readerPolicyLabel(
        fabric.lang.security.Principal owner,
        fabric.lang.security.PrincipalSet readers);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.worker.Store store, fabric.lang.security.Principal owner,
        fabric.lang.security.Principal writer);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.lang.security.Principal owner,
        fabric.lang.security.Principal writer);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.worker.Store store, fabric.lang.security.Principal owner,
        fabric.util.Collection writers);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.lang.security.Principal owner, fabric.util.Collection writers);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.worker.Store store, fabric.lang.security.Principal owner,
        fabric.lang.security.Principal writer);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.lang.security.Principal owner,
        fabric.lang.security.Principal writer);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.worker.Store store, fabric.lang.security.Principal owner,
        fabric.util.Collection writers);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.lang.security.Principal owner, fabric.util.Collection writers);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.worker.Store store, fabric.lang.security.Label lbl,
        fabric.lang.security.Principal owner,
        fabric.lang.arrays.ObjectArray writers);

    native public static fabric.lang.security.Label writerPolicyLabel(
        fabric.lang.security.Label lbl, fabric.lang.security.Principal owner,
        fabric.lang.arrays.ObjectArray writers);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.worker.Store store, fabric.lang.security.Label lbl,
        fabric.lang.security.Principal owner,
        fabric.lang.arrays.ObjectArray writers);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.lang.security.Label lbl, fabric.lang.security.Principal owner,
        fabric.lang.arrays.ObjectArray writers);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.worker.Store store, fabric.lang.security.Principal owner,
        fabric.lang.security.PrincipalSet writers);

    native public static fabric.lang.security.IntegPolicy writerPolicy(
        fabric.lang.security.Principal owner,
        fabric.lang.security.PrincipalSet writers);

    native public static fabric.lang.security.Label toLabel(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy cPolicy,
        fabric.lang.security.IntegPolicy iPolicy);

    native public static fabric.lang.security.Label toLabel(
        fabric.lang.security.ConfPolicy cPolicy,
        fabric.lang.security.IntegPolicy iPolicy);

    native public static fabric.lang.security.Label toLabel(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy policy);

    native public static fabric.lang.security.Label toLabel(
        fabric.lang.security.ConfPolicy policy);

    native public static fabric.lang.security.Label toLabel(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy policy);

    native public static fabric.lang.security.Label toLabel(
        fabric.lang.security.IntegPolicy policy);

    native public static fabric.lang.security.Label liftToLabel(
        fabric.lang.security.ConfPolicy policy);

    native private static fabric.lang.security.Label liftToLabel(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy policy);

    native public static fabric.lang.security.Label liftToLabel(
        fabric.lang.security.IntegPolicy policy);

    native public static fabric.lang.security.Label join(
        fabric.worker.Store store, fabric.lang.security.Label l1,
        fabric.lang.security.Label l2);

    native public static fabric.lang.security.Label join(
        fabric.lang.security.Label l1, fabric.lang.security.Label l2);

    native public static fabric.lang.security.Label meetLbl(
        fabric.worker.Store store, fabric.lang.security.Label l1,
        fabric.lang.security.Label l2);

    native public static fabric.lang.security.Label meetLbl(
        fabric.lang.security.Label l1, fabric.lang.security.Label l2);

    native public static fabric.lang.security.Label meet(
        fabric.worker.Store store, fabric.lang.security.Label l1,
        fabric.lang.security.Label l2);

    native public static fabric.lang.security.Label meet(
        fabric.lang.security.Label l1, fabric.lang.security.Label l2);

    native public static fabric.lang.security.ConfPolicy join(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
        fabric.lang.security.ConfPolicy p2);

    native public static fabric.lang.security.ConfPolicy join(
        fabric.lang.security.ConfPolicy p1, fabric.lang.security.ConfPolicy p2);

    native public static fabric.lang.security.ConfPolicy join(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
        fabric.lang.security.ConfPolicy p2, java.util.Set s);

    native public static fabric.lang.security.ConfPolicy join(
        fabric.lang.security.ConfPolicy p1, fabric.lang.security.ConfPolicy p2,
        java.util.Set s);

    native public static fabric.lang.security.IntegPolicy join(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
        fabric.lang.security.IntegPolicy p2);

    native public static fabric.lang.security.IntegPolicy join(
        fabric.lang.security.IntegPolicy p1, fabric.lang.security.IntegPolicy p2);

    native public static fabric.lang.security.IntegPolicy join(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
        fabric.lang.security.IntegPolicy p2, java.util.Set s);

    native public static fabric.lang.security.IntegPolicy join(
        fabric.lang.security.IntegPolicy p1,
        fabric.lang.security.IntegPolicy p2, java.util.Set s);

    native public static fabric.lang.security.ConfPolicy meetPol(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
        fabric.lang.security.ConfPolicy p2);

    native public static fabric.lang.security.ConfPolicy meetPol(
        fabric.lang.security.ConfPolicy p1, fabric.lang.security.ConfPolicy p2);

    native public static fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
        fabric.lang.security.ConfPolicy p2, java.util.Set s);

    native public static fabric.lang.security.ConfPolicy meet(
        fabric.lang.security.ConfPolicy p1, fabric.lang.security.ConfPolicy p2,
        java.util.Set s);

    native public static fabric.lang.security.IntegPolicy meetPol(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
        fabric.lang.security.IntegPolicy p2);

    native public static fabric.lang.security.IntegPolicy meetPol(
        fabric.lang.security.IntegPolicy p1, fabric.lang.security.IntegPolicy p2);

    native public static fabric.lang.security.IntegPolicy meet(
        fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
        fabric.lang.security.IntegPolicy p2, java.util.Set s);

    native public static fabric.lang.security.IntegPolicy meet(
        fabric.lang.security.IntegPolicy p1,
        fabric.lang.security.IntegPolicy p2, java.util.Set s);

    native public static boolean equivalentTo(fabric.lang.security.Label l1,
        fabric.lang.security.Label l2);

    native public static boolean isReadableBy(fabric.lang.security.Label lbl,
        fabric.lang.security.Principal p);

    native public static boolean isWritableBy(fabric.lang.security.Label lbl,
        fabric.lang.security.Principal p);

    native public static boolean relabelsTo(fabric.lang.security.Label from,
        fabric.lang.security.Label to);

    @Override
    native public boolean acts_for(fabric.lang.security.Label actor,
        fabric.lang.security.Principal granter);

    @Override
    native public boolean actsFor(fabric.lang.security.Label actor,
        fabric.lang.security.Principal granter);

    native public static boolean relabelsTo(fabric.lang.security.Policy from,
        fabric.lang.security.Policy to);

    native public static boolean relabelsTo(fabric.lang.security.Policy from,
        fabric.lang.security.Policy to, java.util.Set s);

    native public static java.lang.String stringValue(
        fabric.lang.security.Label lb);

    native public static java.lang.String toString(fabric.lang.security.Label lb);

    native public static int hashCode(fabric.lang.security.Label lb);

    native private static fabric.util.Set simplifyJoin(
        fabric.worker.Store store, fabric.util.Set policies,
        java.util.Set dependencies);

    native private static fabric.util.Set simplifyMeet(
        fabric.worker.Store store, fabric.util.Set policies,
        java.util.Set dependencies);

    native public static void notifyNewDelegation(
        fabric.lang.security.Principal granter,
        fabric.lang.security.Principal superior);

    @Override
    native protected fabric.lang.Object._Proxy $makeProxy();

    @Override
    native public void $serialize(java.io.ObjectOutput out,
        java.util.List refTypes, java.util.List intraStoreRefs,
        java.util.List interStoreRefs) throws java.io.IOException;

    public _Impl(fabric.worker.Store store, long onum, int version,
        VersionWarranty warranty, long label, long accessLabel,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, warranty, label, accessLabel, in, refTypes,
          intraStoreRefs, interStoreRefs);
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {

    public fabric.worker.LocalStore get$localStore();

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.lang.security.LabelUtil._Static {

      @Override
      native public fabric.worker.LocalStore get$localStore();

      public _Proxy(fabric.lang.security.LabelUtil._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.lang.security.LabelUtil._Static {

      @Override
      native public fabric.worker.LocalStore get$localStore();

      public _Impl(fabric.worker.Store store)
          throws fabric.net.UnreachableNodeException {
        super(store);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      native private void $init();
    }

  }

}
