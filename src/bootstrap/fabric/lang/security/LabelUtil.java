package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.HashSet;
import fabric.common.exceptions.InternalError;
import fabric.common.util.Pair;
import fabric.common.util.Triple;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import fabric.lang.security.PrincipalUtil;
import fabric.lang.security.SecurityCache;
import fabric.util.*;

/**
 * A Label is the runtime representation of a Jif label. A Label consists of a
 * set of components, each of which is a fabric.lang.security.Policy. This code
 * is mostly copied from Jif.
 */
public interface LabelUtil extends fabric.lang.Object {
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.LabelUtil {
        public static fabric.lang.security.Label noComponents() {
            return fabric.lang.security.LabelUtil._Impl.noComponents();
        }
        
        public static fabric.lang.security.Label noComponents(
          fabric.worker.Store arg1) {
            return fabric.lang.security.LabelUtil._Impl.noComponents(arg1);
        }
        
        public static fabric.lang.security.ConfPolicy bottomConf() {
            return fabric.lang.security.LabelUtil._Impl.bottomConf();
        }
        
        public static fabric.lang.security.IntegPolicy bottomInteg() {
            return fabric.lang.security.LabelUtil._Impl.bottomInteg();
        }
        
        public static fabric.lang.security.ConfPolicy topConf() {
            return fabric.lang.security.LabelUtil._Impl.topConf();
        }
        
        public static fabric.lang.security.IntegPolicy topInteg() {
            return fabric.lang.security.LabelUtil._Impl.topInteg();
        }
        
        public static fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3) {
            return fabric.lang.security.LabelUtil._Impl.readerPolicy(arg1, arg2,
                                                                     arg3);
        }
        
        public static fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.util.Collection arg3) {
            return fabric.lang.security.LabelUtil._Impl.readerPolicy(arg1, arg2,
                                                                     arg3);
        }
        
        public static fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Principal arg3,
          fabric.lang.arrays.ObjectArray arg4) {
            return fabric.lang.security.LabelUtil._Impl.readerPolicy(arg1, arg2,
                                                                     arg3,
                                                                     arg4);
        }
        
        public static fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.PrincipalSet arg3) {
            return fabric.lang.security.LabelUtil._Impl.readerPolicy(arg1, arg2,
                                                                     arg3);
        }
        
        public static fabric.lang.security.Label readerPolicyLabel(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            return fabric.lang.security.LabelUtil._Impl.readerPolicyLabel(arg1,
                                                                          arg2);
        }
        
        public static fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3) {
            return fabric.lang.security.LabelUtil._Impl.readerPolicyLabel(arg1,
                                                                          arg2,
                                                                          arg3);
        }
        
        public static fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.util.Collection arg3) {
            return fabric.lang.security.LabelUtil._Impl.readerPolicyLabel(arg1,
                                                                          arg2,
                                                                          arg3);
        }
        
        public static fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Principal arg3,
          fabric.lang.arrays.ObjectArray arg4) {
            return fabric.lang.security.LabelUtil._Impl.readerPolicyLabel(arg1,
                                                                          arg2,
                                                                          arg3,
                                                                          arg4);
        }
        
        public static fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.PrincipalSet arg3) {
            return fabric.lang.security.LabelUtil._Impl.readerPolicyLabel(arg1,
                                                                          arg2,
                                                                          arg3);
        }
        
        public static fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3) {
            return fabric.lang.security.LabelUtil._Impl.writerPolicy(arg1, arg2,
                                                                     arg3);
        }
        
        public static fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.util.Collection arg3) {
            return fabric.lang.security.LabelUtil._Impl.writerPolicy(arg1, arg2,
                                                                     arg3);
        }
        
        public static fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3) {
            return fabric.lang.security.LabelUtil._Impl.writerPolicyLabel(arg1,
                                                                          arg2,
                                                                          arg3);
        }
        
        public static fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.util.Collection arg3) {
            return fabric.lang.security.LabelUtil._Impl.writerPolicyLabel(arg1,
                                                                          arg2,
                                                                          arg3);
        }
        
        public static fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Principal arg3,
          fabric.lang.arrays.ObjectArray arg4) {
            return fabric.lang.security.LabelUtil._Impl.writerPolicyLabel(arg1,
                                                                          arg2,
                                                                          arg3,
                                                                          arg4);
        }
        
        public static fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Principal arg3,
          fabric.lang.arrays.ObjectArray arg4) {
            return fabric.lang.security.LabelUtil._Impl.writerPolicy(arg1, arg2,
                                                                     arg3,
                                                                     arg4);
        }
        
        public static fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.PrincipalSet arg3) {
            return fabric.lang.security.LabelUtil._Impl.writerPolicy(arg1, arg2,
                                                                     arg3);
        }
        
        public static fabric.lang.security.Label toLabel(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.IntegPolicy arg3) {
            return fabric.lang.security.LabelUtil._Impl.toLabel(arg1, arg2,
                                                                arg3);
        }
        
        public static fabric.lang.security.Label toLabel(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2) {
            return fabric.lang.security.LabelUtil._Impl.toLabel(arg1, arg2);
        }
        
        public static fabric.lang.security.Label toLabel(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2) {
            return fabric.lang.security.LabelUtil._Impl.toLabel(arg1, arg2);
        }
        
        public static fabric.lang.security.Label liftToLabel(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2) {
            return fabric.lang.security.LabelUtil._Impl.liftToLabel(arg1, arg2);
        }
        
        public static fabric.lang.security.Label liftToLabel(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2) {
            return fabric.lang.security.LabelUtil._Impl.liftToLabel(arg1, arg2);
        }
        
        public static fabric.lang.security.Label join(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Label arg3) {
            return fabric.lang.security.LabelUtil._Impl.join(arg1, arg2, arg3);
        }
        
        public static fabric.lang.security.Label join(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Label arg3, boolean arg4) {
            return fabric.lang.security.LabelUtil._Impl.join(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public static fabric.lang.security.Label meet(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Label arg3) {
            return fabric.lang.security.LabelUtil._Impl.meet(arg1, arg2, arg3);
        }
        
        public static fabric.lang.security.Label meet(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Label arg3, boolean arg4) {
            return fabric.lang.security.LabelUtil._Impl.meet(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public static fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3) {
            return fabric.lang.security.LabelUtil._Impl.join(arg1, arg2, arg3);
        }
        
        public static fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, boolean arg4) {
            return fabric.lang.security.LabelUtil._Impl.join(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public static fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, java.util.Set arg4) {
            return fabric.lang.security.LabelUtil._Impl.join(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public static fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, java.util.Set arg4,
          boolean arg5) {
            return fabric.lang.security.LabelUtil._Impl.join(arg1, arg2, arg3,
                                                             arg4, arg5);
        }
        
        public static fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3) {
            return fabric.lang.security.LabelUtil._Impl.join(arg1, arg2, arg3);
        }
        
        public static fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, boolean arg4) {
            return fabric.lang.security.LabelUtil._Impl.join(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public static fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, java.util.Set arg4) {
            return fabric.lang.security.LabelUtil._Impl.join(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public static fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, java.util.Set arg4,
          boolean arg5) {
            return fabric.lang.security.LabelUtil._Impl.join(arg1, arg2, arg3,
                                                             arg4, arg5);
        }
        
        public static fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3) {
            return fabric.lang.security.LabelUtil._Impl.meet(arg1, arg2, arg3);
        }
        
        public static fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, boolean arg4) {
            return fabric.lang.security.LabelUtil._Impl.meet(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public static fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, java.util.Set arg4) {
            return fabric.lang.security.LabelUtil._Impl.meet(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public static fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, java.util.Set arg4,
          boolean arg5) {
            return fabric.lang.security.LabelUtil._Impl.meet(arg1, arg2, arg3,
                                                             arg4, arg5);
        }
        
        public static fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3) {
            return fabric.lang.security.LabelUtil._Impl.meet(arg1, arg2, arg3);
        }
        
        public static fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, boolean arg4) {
            return fabric.lang.security.LabelUtil._Impl.meet(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public static fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, java.util.Set arg4) {
            return fabric.lang.security.LabelUtil._Impl.meet(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public static fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, java.util.Set arg4,
          boolean arg5) {
            return fabric.lang.security.LabelUtil._Impl.meet(arg1, arg2, arg3,
                                                             arg4, arg5);
        }
        
        public static boolean equivalentTo(fabric.lang.security.Label arg1,
                                           fabric.lang.security.Label arg2) {
            return fabric.lang.security.LabelUtil._Impl.equivalentTo(arg1,
                                                                     arg2);
        }
        
        public static boolean isReadableBy(
          fabric.lang.security.Label arg1,
          fabric.lang.security.Principal arg2) {
            return fabric.lang.security.LabelUtil._Impl.isReadableBy(arg1,
                                                                     arg2);
        }
        
        public static boolean isWritableBy(
          fabric.lang.security.Label arg1,
          fabric.lang.security.Principal arg2) {
            return fabric.lang.security.LabelUtil._Impl.isWritableBy(arg1,
                                                                     arg2);
        }
        
        public static boolean relabelsTo(fabric.lang.security.Label arg1,
                                         fabric.lang.security.Label arg2) {
            return fabric.lang.security.LabelUtil._Impl.relabelsTo(arg1, arg2);
        }
        
        public static boolean acts_for(fabric.lang.security.Label arg1,
                                       fabric.lang.security.Principal arg2) {
            return fabric.lang.security.LabelUtil._Impl.acts_for(arg1, arg2);
        }
        
        public static boolean actsFor(fabric.lang.security.Label arg1,
                                      fabric.lang.security.Principal arg2) {
            return fabric.lang.security.LabelUtil._Impl.actsFor(arg1, arg2);
        }
        
        public static boolean relabelsTo(fabric.lang.security.Policy arg1,
                                         fabric.lang.security.Policy arg2) {
            return fabric.lang.security.LabelUtil._Impl.relabelsTo(arg1, arg2);
        }
        
        public static boolean relabelsTo(fabric.lang.security.Policy arg1,
                                         fabric.lang.security.Policy arg2,
                                         java.util.Set arg3) {
            return fabric.lang.security.LabelUtil._Impl.relabelsTo(arg1, arg2,
                                                                   arg3);
        }
        
        public static java.lang.String stringValue(
          fabric.lang.security.Label arg1) {
            return fabric.lang.security.LabelUtil._Impl.stringValue(arg1);
        }
        
        public static java.lang.String toString(
          fabric.lang.security.Label arg1) {
            return fabric.lang.security.LabelUtil._Impl.toString(arg1);
        }
        
        public static int hashCode(fabric.lang.security.Label arg1) {
            return fabric.lang.security.LabelUtil._Impl.hashCode(arg1);
        }
        
        public static void notifyNewDelegation(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            fabric.lang.security.LabelUtil._Impl.notifyNewDelegation(arg1,
                                                                     arg2);
        }
        
        public static fabric.lang.Object accessCheck(
          fabric.lang.security.Label arg1, fabric.lang.Object arg2) {
            return fabric.lang.security.LabelUtil._Impl.accessCheck(arg1, arg2);
        }
        
        public _Proxy(LabelUtil._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.LabelUtil {
        private fabric.lang.security.LabelUtil fabric$lang$security$LabelUtil$(
          ) {
            fabric$lang$Object$();
            return (fabric.lang.security.LabelUtil) this.$getProxy();
        }
        
        public static fabric.lang.security.Label noComponents() {
            return fabric.lang.security.LabelUtil._Static._Proxy.$instance.
              get$localStore().getEmptyLabel();
        }
        
        public static fabric.lang.security.Label noComponents(
          fabric.worker.Store store) {
            return fabric.lang.security.LabelUtil._Impl.noComponents();
        }
        
        public static fabric.lang.security.ConfPolicy bottomConf() {
            return fabric.lang.security.LabelUtil._Static._Proxy.$instance.
              get$localStore().getBottomConfidPolicy();
        }
        
        public static fabric.lang.security.IntegPolicy bottomInteg() {
            return fabric.lang.security.LabelUtil._Static._Proxy.$instance.
              get$localStore().getBottomIntegPolicy();
        }
        
        public static fabric.lang.security.ConfPolicy topConf() {
            return fabric.lang.security.LabelUtil._Static._Proxy.$instance.
              get$localStore().getTopConfidPolicy();
        }
        
        public static fabric.lang.security.IntegPolicy topInteg() {
            return fabric.lang.security.LabelUtil._Static._Proxy.$instance.
              get$localStore().getTopIntegPolicy();
        }
        
        public static fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.Principal reader) {
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            fabric.common.util.Triple triple =
              new fabric.common.util.Triple(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(owner),
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(reader), store);
            fabric.lang.security.ConfPolicy result =
              cache.getReaderPolicy(triple);
            if (!fabric.lang.Object._Proxy.idEquals(result, null))
                return result;
            result =
              (fabric.lang.security.ReaderPolicy)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.lang.security.ReaderPolicy)
                     new fabric.lang.security.ReaderPolicy._Impl(store).
                     $getProxy()).fabric$lang$security$ReaderPolicy$(owner,
                                                                     reader));
            cache.putReaderPolicy(triple, result);
            return result;
        }
        
        public static fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.util.Collection readers) {
            return fabric.lang.security.LabelUtil._Impl.
              readerPolicy(
                store,
                owner,
                fabric.lang.security.PrincipalUtil._Impl.disjunction(store,
                                                                     readers));
        }
        
        /**
   * See the Jif signature for the explanation of lbl.
   */
        public static fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store store, fabric.lang.security.Label lbl,
          fabric.lang.security.Principal owner,
          fabric.lang.arrays.ObjectArray readers) {
            if (fabric.lang.Object._Proxy.idEquals(readers, null))
                return fabric.lang.security.LabelUtil._Impl.
                  readerPolicy(
                    store,
                    owner,
                    fabric.util.Collections._Static._Proxy.$instance.
                        get$EMPTY_SET());
            return fabric.lang.security.LabelUtil._Impl.
              readerPolicy(store, owner,
                           fabric.util.Arrays._Impl.asList(readers));
        }
        
        public static fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.PrincipalSet writers) {
            return fabric.lang.security.LabelUtil._Impl.readerPolicy(
                                                          store, owner,
                                                          writers.getSet());
        }
        
        public static fabric.lang.security.Label readerPolicyLabel(
          fabric.lang.security.Principal owner,
          fabric.lang.security.Principal reader) {
            return fabric.lang.security.LabelUtil._Impl.
              toLabel(
                fabric.lang.security.LabelUtil._Static._Proxy.$instance.
                    get$localStore(),
                fabric.lang.security.LabelUtil._Impl.
                    readerPolicy(
                      fabric.lang.security.LabelUtil._Static._Proxy.$instance.
                          get$localStore(),
                      owner,
                      reader));
        }
        
        public static fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.Principal reader) {
            return fabric.lang.security.LabelUtil._Impl.
              toLabel(
                store,
                fabric.lang.security.LabelUtil._Impl.readerPolicy(store, owner,
                                                                  reader));
        }
        
        public static fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.util.Collection readers) {
            fabric.lang.security.Label
              l =
              fabric.lang.security.LabelUtil._Impl.
              toLabel(
                store,
                fabric.lang.security.LabelUtil._Impl.
                    readerPolicy(
                      store,
                      owner,
                      fabric.lang.security.PrincipalUtil._Impl.disjunction(
                                                                 store,
                                                                 readers)));
            return l;
        }
        
        /**
   * See the Jif signature for the explanation of lbl.
   */
        public static fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Label lbl,
          fabric.lang.security.Principal owner,
          fabric.lang.arrays.ObjectArray readers) {
            if (fabric.lang.Object._Proxy.idEquals(readers, null))
                return fabric.lang.security.LabelUtil._Impl.
                  readerPolicyLabel(
                    store,
                    owner,
                    fabric.util.Collections._Static._Proxy.$instance.
                        get$EMPTY_SET());
            return fabric.lang.security.LabelUtil._Impl.
              readerPolicyLabel(store, owner,
                                fabric.util.Arrays._Impl.asList(readers));
        }
        
        public static fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.PrincipalSet readers) {
            return fabric.lang.security.LabelUtil._Impl.
              readerPolicyLabel(
                store,
                owner,
                fabric.lang.security.PrincipalUtil._Impl.disjunction(
                                                           store,
                                                           readers.getSet()));
        }
        
        public static fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.Principal writer) {
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            fabric.common.util.Triple triple =
              new fabric.common.util.Triple(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(owner),
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(writer), store);
            fabric.lang.security.IntegPolicy result =
              cache.getWriterPolicy(triple);
            if (!fabric.lang.Object._Proxy.idEquals(result, null))
                return result;
            result =
              (fabric.lang.security.WriterPolicy)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.lang.security.WriterPolicy)
                     new fabric.lang.security.WriterPolicy._Impl(store).
                     $getProxy()).fabric$lang$security$WriterPolicy$(owner,
                                                                     writer));
            cache.putWriterPolicy(triple, result);
            return result;
        }
        
        public static fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.util.Collection writers) {
            return fabric.lang.security.LabelUtil._Impl.
              writerPolicy(
                store,
                owner,
                fabric.lang.security.PrincipalUtil._Impl.disjunction(store,
                                                                     writers));
        }
        
        public static fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.Principal writer) {
            return fabric.lang.security.LabelUtil._Impl.
              toLabel(
                store,
                fabric.lang.security.LabelUtil._Impl.writerPolicy(store, owner,
                                                                  writer));
        }
        
        public static fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.util.Collection writers) {
            return fabric.lang.security.LabelUtil._Impl.
              toLabel(
                store,
                fabric.lang.security.LabelUtil._Impl.
                    writerPolicy(
                      store,
                      owner,
                      fabric.lang.security.PrincipalUtil._Impl.disjunction(
                                                                 store,
                                                                 writers)));
        }
        
        /**
   * See the Jif signature for the explanation of lbl.
   */
        public static fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Label lbl,
          fabric.lang.security.Principal owner,
          fabric.lang.arrays.ObjectArray writers) {
            if (fabric.lang.Object._Proxy.idEquals(writers, null))
                return fabric.lang.security.LabelUtil._Impl.
                  writerPolicyLabel(
                    store,
                    owner,
                    fabric.util.Collections._Static._Proxy.$instance.
                        get$EMPTY_SET());
            return fabric.lang.security.LabelUtil._Impl.
              writerPolicyLabel(store, owner,
                                fabric.util.Arrays._Impl.asList(writers));
        }
        
        /**
   * See the Jif signature for the explanation of lbl.
   */
        public static fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store store, fabric.lang.security.Label lbl,
          fabric.lang.security.Principal owner,
          fabric.lang.arrays.ObjectArray writers) {
            if (fabric.lang.Object._Proxy.idEquals(writers, null))
                return fabric.lang.security.LabelUtil._Impl.
                  writerPolicy(
                    store,
                    owner,
                    fabric.util.Collections._Static._Proxy.$instance.
                        get$EMPTY_SET());
            return fabric.lang.security.LabelUtil._Impl.
              writerPolicy(store, owner,
                           fabric.util.Arrays._Impl.asList(writers));
        }
        
        public static fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.PrincipalSet writers) {
            return fabric.lang.security.LabelUtil._Impl.writerPolicy(
                                                          store, owner,
                                                          writers.getSet());
        }
        
        public static fabric.lang.security.Label toLabel(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy cPolicy,
          fabric.lang.security.IntegPolicy iPolicy) {
            if (fabric.lang.Object._Proxy.idEquals(cPolicy, null) ||
                  fabric.lang.Object._Proxy.idEquals(iPolicy, null))
                throw new java.lang.NullPointerException();
            fabric.common.util.Triple triple =
              new fabric.common.util.Triple(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(cPolicy),
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(iPolicy), store);
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            fabric.lang.security.Label result = cache.getLabel(triple);
            if (!fabric.lang.Object._Proxy.idEquals(result, null))
                return result;
            result =
              (fabric.lang.security.Label)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.lang.security.PairLabel)
                     new fabric.lang.security.PairLabel._Impl(store).$getProxy(
                                                                       )).
                      fabric$lang$security$PairLabel$(cPolicy, iPolicy));
            cache.putLabel(triple, result);
            return result;
        }
        
        public static fabric.lang.security.Label toLabel(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy policy) {
            return fabric.lang.security.LabelUtil._Impl.
              toLabel(store, policy,
                      fabric.lang.security.LabelUtil._Impl.topInteg());
        }
        
        public static fabric.lang.security.Label toLabel(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy policy) {
            return fabric.lang.security.LabelUtil._Impl.
              toLabel(store, fabric.lang.security.LabelUtil._Impl.bottomConf(),
                      policy);
        }
        
        public static fabric.lang.security.Label liftToLabel(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy policy) {
            return fabric.lang.security.LabelUtil._Impl.
              toLabel(store, policy,
                      fabric.lang.security.LabelUtil._Impl.bottomInteg());
        }
        
        public static fabric.lang.security.Label liftToLabel(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy policy) {
            return fabric.lang.security.LabelUtil._Impl.
              toLabel(store, fabric.lang.security.LabelUtil._Impl.bottomConf(),
                      policy);
        }
        
        public static fabric.lang.security.Label join(
          fabric.worker.Store store, fabric.lang.security.Label l1,
          fabric.lang.security.Label l2) {
            return fabric.lang.security.LabelUtil._Impl.join(store, l1, l2,
                                                             true);
        }
        
        public static fabric.lang.security.Label join(
          fabric.worker.Store store, fabric.lang.security.Label l1,
          fabric.lang.security.Label l2, boolean simplify) {
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            if (fabric.lang.Object._Proxy.idEquals(l1, null)) return l2;
            if (fabric.lang.Object._Proxy.idEquals(l2, null)) return l1;
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(l1)) instanceof fabric.lang.security.PairLabel &&
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(l2)) instanceof fabric.lang.security.PairLabel) {
                fabric.common.util.Triple triple =
                  fabric.lang.security.SecurityCache.canonicalize(l1, l2,
                                                                  store);
                fabric.lang.security.Label result = cache.getLabelJoin(triple);
                if (fabric.lang.Object._Proxy.idEquals(result, null)) {
                    fabric.lang.security.PairLabel pl1 =
                      (fabric.lang.security.PairLabel)
                        fabric.lang.Object._Proxy.$getProxy(l1);
                    fabric.lang.security.PairLabel pl2 =
                      (fabric.lang.security.PairLabel)
                        fabric.lang.Object._Proxy.$getProxy(l2);
                    java.util.Set dependencies = new java.util.HashSet();
                    result =
                      (fabric.lang.security.PairLabel)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          ((fabric.lang.security.PairLabel)
                             new fabric.lang.security.PairLabel._Impl(store).
                             $getProxy()).fabric$lang$security$PairLabel$(
                                            pl1.confPolicy().join(
                                                               store,
                                                               pl2.confPolicy(),
                                                               dependencies,
                                                               simplify),
                                            pl1.integPolicy().join(
                                                                store,
                                                                pl2.integPolicy(
                                                                      ),
                                                                dependencies,
                                                                simplify)));
                    cache.putLabelJoin(triple, result, dependencies);
                }
                return result;
            }
            return null;
        }
        
        public static fabric.lang.security.Label meet(
          fabric.worker.Store store, fabric.lang.security.Label l1,
          fabric.lang.security.Label l2) {
            return fabric.lang.security.LabelUtil._Impl.meet(store, l1, l2,
                                                             true);
        }
        
        public static fabric.lang.security.Label meet(
          fabric.worker.Store store, fabric.lang.security.Label l1,
          fabric.lang.security.Label l2, boolean simplify) {
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            if (fabric.lang.Object._Proxy.idEquals(l1, null)) return l2;
            if (fabric.lang.Object._Proxy.idEquals(l2, null)) return l1;
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(l1)) instanceof fabric.lang.security.PairLabel &&
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(l2)) instanceof fabric.lang.security.PairLabel) {
                fabric.common.util.Triple triple =
                  fabric.lang.security.SecurityCache.canonicalize(l1, l2,
                                                                  store);
                fabric.lang.security.Label result = cache.getLabelMeet(triple);
                if (fabric.lang.Object._Proxy.idEquals(result, null)) {
                    fabric.lang.security.PairLabel pl1 =
                      (fabric.lang.security.PairLabel)
                        fabric.lang.Object._Proxy.$getProxy(l1);
                    fabric.lang.security.PairLabel pl2 =
                      (fabric.lang.security.PairLabel)
                        fabric.lang.Object._Proxy.$getProxy(l2);
                    java.util.Set dependencies = new java.util.HashSet();
                    result =
                      (fabric.lang.security.PairLabel)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          ((fabric.lang.security.PairLabel)
                             new fabric.lang.security.PairLabel._Impl(store).
                             $getProxy()).fabric$lang$security$PairLabel$(
                                            pl1.confPolicy().meet(
                                                               store,
                                                               pl2.confPolicy(),
                                                               dependencies,
                                                               simplify),
                                            pl1.integPolicy().meet(
                                                                store,
                                                                pl2.integPolicy(
                                                                      ),
                                                                dependencies,
                                                                simplify)));
                    cache.putLabelMeet(triple, result, dependencies);
                }
                return result;
            }
            return null;
        }
        
        public static fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2) {
            return fabric.lang.security.LabelUtil._Impl.join(
                                                          store,
                                                          p1,
                                                          p2,
                                                          new java.util.HashSet(
                                                            ), true);
        }
        
        public static fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.join(
                                                          store,
                                                          p1,
                                                          p2,
                                                          new java.util.HashSet(
                                                            ), true);
        }
        
        public static fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, java.util.Set s) {
            return fabric.lang.security.LabelUtil._Impl.join(store, p1, p2, s,
                                                             true);
        }
        
        public static fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, java.util.Set s,
          boolean simplify) {
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            fabric.common.util.Triple triple =
              fabric.lang.security.SecurityCache.canonicalize(p1, p2, store);
            fabric.common.util.Pair cacheEntry = cache.getPolicyJoin(triple);
            if (!fabric.lang.Object._Proxy.idEquals(cacheEntry, null)) {
                s.addAll((java.util.Set) cacheEntry.second);
                return (fabric.lang.security.ConfPolicy)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           fabric.lang.WrappedJavaInlineable.
                               $wrap(cacheEntry.first));
            }
            fabric.util.Set
              comps =
              (fabric.util.LinkedHashSet)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedHashSet)
                     new fabric.util.LinkedHashSet._Impl(store).$getProxy()).
                      fabric$util$LinkedHashSet$());
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p1)) instanceof fabric.lang.security.JoinConfPolicy) {
                comps.addAll(
                        ((fabric.lang.security.JoinConfPolicy)
                           fabric.lang.Object._Proxy.$getProxy(
                                                       p1)).joinComponents());
            }
            else {
                comps.add(p1);
            }
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p2)) instanceof fabric.lang.security.JoinConfPolicy) {
                comps.addAll(
                        ((fabric.lang.security.JoinConfPolicy)
                           fabric.lang.Object._Proxy.$getProxy(
                                                       p2)).joinComponents());
            }
            else {
                comps.add(p2);
            }
            java.util.Set deps = new java.util.HashSet();
            if (simplify)
                comps = fabric.lang.security.LabelUtil._Impl.simplifyJoin(store,
                                                                          comps,
                                                                          deps);
            fabric.lang.security.ConfPolicy result;
            if (comps.size() == 1) {
                result =
                  (fabric.lang.security.ConfPolicy)
                    fabric.lang.Object._Proxy.$getProxy(
                                                comps.iterator().next());
            }
            else {
                result =
                  ((fabric.lang.security.JoinConfPolicy)
                     new fabric.lang.security.JoinConfPolicy._Impl(store).
                     $getProxy()).fabric$lang$security$JoinConfPolicy$(comps);
            }
            cache.putPolicyJoin(triple, result, deps);
            s.addAll(deps);
            return result;
        }
        
        public static fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2) {
            return fabric.lang.security.LabelUtil._Impl.join(
                                                          store,
                                                          p1,
                                                          p2,
                                                          new java.util.HashSet(
                                                            ), true);
        }
        
        public static fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.join(
                                                          store,
                                                          p1,
                                                          p2,
                                                          new java.util.HashSet(
                                                            ), simplify);
        }
        
        public static fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, java.util.Set s) {
            return fabric.lang.security.LabelUtil._Impl.join(store, p1, p2, s,
                                                             true);
        }
        
        public static fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, java.util.Set s,
          boolean simplify) {
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            fabric.common.util.Triple triple =
              fabric.lang.security.SecurityCache.canonicalize(p1, p2, store);
            fabric.common.util.Pair cacheEntry = cache.getPolicyJoin(triple);
            if (!fabric.lang.Object._Proxy.idEquals(cacheEntry, null)) {
                s.addAll((java.util.Set) cacheEntry.second);
                return (fabric.lang.security.IntegPolicy)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           fabric.lang.WrappedJavaInlineable.
                               $wrap(cacheEntry.first));
            }
            fabric.util.Set
              comps =
              (fabric.util.LinkedHashSet)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedHashSet)
                     new fabric.util.LinkedHashSet._Impl(store).$getProxy()).
                      fabric$util$LinkedHashSet$());
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p1)) instanceof fabric.lang.security.JoinIntegPolicy) {
                comps.addAll(
                        ((fabric.lang.security.JoinIntegPolicy)
                           fabric.lang.Object._Proxy.$getProxy(
                                                       p1)).joinComponents());
            }
            else {
                comps.add(p1);
            }
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p2)) instanceof fabric.lang.security.JoinIntegPolicy) {
                comps.addAll(
                        ((fabric.lang.security.JoinIntegPolicy)
                           fabric.lang.Object._Proxy.$getProxy(
                                                       p2)).joinComponents());
            }
            else {
                comps.add(p2);
            }
            java.util.Set deps = new java.util.HashSet();
            if (simplify)
                comps = fabric.lang.security.LabelUtil._Impl.simplifyJoin(store,
                                                                          comps,
                                                                          deps);
            fabric.lang.security.IntegPolicy result;
            if (comps.size() == 1) {
                result =
                  (fabric.lang.security.IntegPolicy)
                    fabric.lang.Object._Proxy.$getProxy(
                                                comps.iterator().next());
            }
            else {
                result =
                  (fabric.lang.security.JoinIntegPolicy)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      ((fabric.lang.security.JoinIntegPolicy)
                         new fabric.lang.security.JoinIntegPolicy._Impl(store).
                         $getProxy()).fabric$lang$security$JoinIntegPolicy$(
                                        comps));
            }
            cache.putPolicyJoin(triple, result, deps);
            s.addAll(deps);
            return result;
        }
        
        public static fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2) {
            return fabric.lang.security.LabelUtil._Impl.meet(
                                                          store,
                                                          p1,
                                                          p2,
                                                          new java.util.HashSet(
                                                            ), true);
        }
        
        public static fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.meet(
                                                          store,
                                                          p1,
                                                          p2,
                                                          new java.util.HashSet(
                                                            ), simplify);
        }
        
        public static fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, java.util.Set s) {
            return fabric.lang.security.LabelUtil._Impl.meet(store, p1, p2, s,
                                                             true);
        }
        
        public static fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, java.util.Set s,
          boolean simplify) {
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            fabric.common.util.Triple triple =
              fabric.lang.security.SecurityCache.canonicalize(p1, p2, store);
            fabric.common.util.Pair cacheEntry = cache.getPolicyMeet(triple);
            if (!fabric.lang.Object._Proxy.idEquals(cacheEntry, null)) {
                s.addAll((java.util.Set) cacheEntry.second);
                return (fabric.lang.security.ConfPolicy)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           fabric.lang.WrappedJavaInlineable.
                               $wrap(cacheEntry.first));
            }
            fabric.util.Set
              comps =
              (fabric.util.LinkedHashSet)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedHashSet)
                     new fabric.util.LinkedHashSet._Impl(store).$getProxy()).
                      fabric$util$LinkedHashSet$());
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p1)) instanceof fabric.lang.security.MeetConfPolicy) {
                comps.addAll(
                        ((fabric.lang.security.MeetConfPolicy)
                           fabric.lang.Object._Proxy.$getProxy(
                                                       p1)).meetComponents());
            }
            else {
                comps.add(p1);
            }
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p2)) instanceof fabric.lang.security.MeetConfPolicy) {
                comps.addAll(
                        ((fabric.lang.security.MeetConfPolicy)
                           fabric.lang.Object._Proxy.$getProxy(
                                                       p2)).meetComponents());
            }
            else {
                comps.add(p2);
            }
            java.util.Set deps = new java.util.HashSet();
            if (simplify)
                comps = fabric.lang.security.LabelUtil._Impl.simplifyMeet(store,
                                                                          comps,
                                                                          deps);
            fabric.lang.security.ConfPolicy result;
            if (comps.size() == 1) {
                result =
                  (fabric.lang.security.ConfPolicy)
                    fabric.lang.Object._Proxy.$getProxy(
                                                comps.iterator().next());
            }
            else {
                result =
                  (fabric.lang.security.MeetConfPolicy)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      ((fabric.lang.security.MeetConfPolicy)
                         new fabric.lang.security.MeetConfPolicy._Impl(store).
                         $getProxy()).fabric$lang$security$MeetConfPolicy$(
                                        comps));
            }
            cache.putPolicyMeet(triple, result, deps);
            s.addAll(deps);
            return result;
        }
        
        public static fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2) {
            return fabric.lang.security.LabelUtil._Impl.meet(
                                                          store,
                                                          p1,
                                                          p2,
                                                          new java.util.HashSet(
                                                            ));
        }
        
        public static fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, boolean simplify) {
            return fabric.lang.security.LabelUtil._Impl.meet(
                                                          store,
                                                          p1,
                                                          p2,
                                                          new java.util.HashSet(
                                                            ), simplify);
        }
        
        public static fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, java.util.Set s) {
            return fabric.lang.security.LabelUtil._Impl.meet(store, p1, p2, s,
                                                             true);
        }
        
        public static fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, java.util.Set s,
          boolean simplify) {
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            fabric.common.util.Triple triple =
              fabric.lang.security.SecurityCache.canonicalize(p1, p2, store);
            fabric.common.util.Pair cacheEntry = cache.getPolicyMeet(triple);
            if (!fabric.lang.Object._Proxy.idEquals(cacheEntry, null)) {
                s.addAll((java.util.Set) cacheEntry.second);
                return (fabric.lang.security.IntegPolicy)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           fabric.lang.WrappedJavaInlineable.
                               $wrap(cacheEntry.first));
            }
            fabric.util.Set
              comps =
              (fabric.util.LinkedHashSet)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedHashSet)
                     new fabric.util.LinkedHashSet._Impl(store).$getProxy()).
                      fabric$util$LinkedHashSet$());
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p1)) instanceof fabric.lang.security.MeetIntegPolicy) {
                comps.addAll(
                        ((fabric.lang.security.MeetIntegPolicy)
                           fabric.lang.Object._Proxy.$getProxy(
                                                       p1)).meetComponents());
            }
            else {
                comps.add(p1);
            }
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p2)) instanceof fabric.lang.security.MeetIntegPolicy) {
                comps.addAll(
                        ((fabric.lang.security.MeetIntegPolicy)
                           fabric.lang.Object._Proxy.$getProxy(
                                                       p2)).meetComponents());
            }
            else {
                comps.add(p2);
            }
            java.util.Set deps = new java.util.HashSet();
            if (simplify)
                comps = fabric.lang.security.LabelUtil._Impl.simplifyMeet(store,
                                                                          comps,
                                                                          deps);
            fabric.lang.security.IntegPolicy result;
            if (comps.size() == 1) {
                result =
                  (fabric.lang.security.IntegPolicy)
                    fabric.lang.Object._Proxy.$getProxy(
                                                comps.iterator().next());
            }
            else {
                result =
                  (fabric.lang.security.MeetIntegPolicy)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      ((fabric.lang.security.MeetIntegPolicy)
                         new fabric.lang.security.MeetIntegPolicy._Impl(store).
                         $getProxy()).fabric$lang$security$MeetIntegPolicy$(
                                        comps));
            }
            cache.putPolicyMeet(triple, result, deps);
            s.addAll(deps);
            return result;
        }
        
        public static boolean equivalentTo(fabric.lang.security.Label l1,
                                           fabric.lang.security.Label l2) {
            if (fabric.lang.Object._Proxy.idEquals(l1, l2) ||
                  !fabric.lang.Object._Proxy.idEquals(l1, null) &&
                  l1.equals(l2))
                return true;
            return fabric.lang.security.LabelUtil._Impl.relabelsTo(l1, l2) &&
              fabric.lang.security.LabelUtil._Impl.relabelsTo(l2, l1);
        }
        
        public static boolean isReadableBy(fabric.lang.security.Label lbl,
                                           fabric.lang.security.Principal p) {
            fabric.lang.security.Label
              L =
              fabric.lang.security.LabelUtil._Impl.
              toLabel(
                fabric.lang.security.LabelUtil._Static._Proxy.$instance.
                    get$localStore(),
                fabric.lang.security.PrincipalUtil._Impl.
                    readableByPrinPolicy(
                      fabric.lang.security.LabelUtil._Static._Proxy.$instance.
                          get$localStore(),
                      p));
            return fabric.lang.security.LabelUtil._Impl.relabelsTo(lbl, L);
        }
        
        public static boolean isWritableBy(fabric.lang.security.Label lbl,
                                           fabric.lang.security.Principal p) {
            fabric.lang.security.Label
              L =
              fabric.lang.security.LabelUtil._Impl.
              toLabel(
                fabric.lang.security.LabelUtil._Static._Proxy.$instance.
                    get$localStore(),
                fabric.lang.security.PrincipalUtil._Impl.
                    writableByPrinPolicy(
                      fabric.lang.security.LabelUtil._Static._Proxy.$instance.
                          get$localStore(),
                      p));
            return fabric.lang.security.LabelUtil._Impl.relabelsTo(L, lbl);
        }
        
        /**
   * @return true iff from <= to in the information-flow ordering.
   */
        public static boolean relabelsTo(fabric.lang.security.Label from,
                                         fabric.lang.security.Label to) {
            if (fabric.lang.Object._Proxy.idEquals(from, null) ||
                  fabric.lang.Object._Proxy.idEquals(to, null))
                return false;
            if (fabric.lang.Object._Proxy.idEquals(from, to) || from.equals(to))
                return true;
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            fabric.common.util.Pair pair =
              new fabric.common.util.Pair(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(from),
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(to));
            if (cache.containsTrueLabelRelabel(pair)) return true;
            if (cache.containsFalseLabelRelabel(pair)) return false;
            java.util.Set dependencies = new java.util.HashSet();
            boolean result = !fabric.lang.Object._Proxy.idEquals(from, null) &&
              from.relabelsTo(to, dependencies);
            if (!result) {
                cache.addFalseLabelRelabel(pair);
            } else {
                cache.addTrueLabelRelabel(pair, dependencies);
            }
            return result;
        }
        
        public static boolean acts_for(fabric.lang.security.Label actor,
                                       fabric.lang.security.Principal granter) {
            return fabric.lang.security.LabelUtil._Impl.actsFor(actor, granter);
        }
        
        public static boolean actsFor(fabric.lang.security.Label actor,
                                      fabric.lang.security.Principal granter) {
            fabric.lang.security.Label
              L =
              fabric.lang.security.LabelUtil._Impl.
              toLabel(
                fabric.lang.security.LabelUtil._Static._Proxy.$instance.
                    get$localStore(),
                fabric.lang.security.LabelUtil._Impl.topConf(),
                fabric.lang.security.LabelUtil._Impl.
                    writerPolicy(
                      fabric.lang.security.LabelUtil._Static._Proxy.$instance.
                          get$localStore(),
                      granter,
                      granter));
            return fabric.lang.security.LabelUtil._Impl.relabelsTo(actor, L);
        }
        
        public static boolean relabelsTo(fabric.lang.security.Policy from,
                                         fabric.lang.security.Policy to) {
            return fabric.lang.security.LabelUtil._Impl.relabelsTo(
                                                          from,
                                                          to,
                                                          new java.util.HashSet(
                                                            ));
        }
        
        public static boolean relabelsTo(fabric.lang.security.Policy from,
                                         fabric.lang.security.Policy to,
                                         java.util.Set s) {
            if (fabric.lang.Object._Proxy.idEquals(from, null) ||
                  fabric.lang.Object._Proxy.idEquals(to, null))
                return false;
            if (fabric.lang.Object._Proxy.idEquals(from, to) || from.equals(to))
                return true;
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            fabric.common.util.Pair pair =
              new fabric.common.util.Pair(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(from),
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(to));
            if (cache.containsTruePolicyRelabel(pair)) {
                s.addAll(cache.getTruePolicyRelabels(pair));
                return true;
            }
            if (cache.containsFalsePolicyRelabel(pair)) return false;
            java.util.Set dependencies = new java.util.HashSet();
            boolean result = from.relabelsTo(to, dependencies);
            if (!result) {
                cache.addFalsePolicyRelabel(pair);
            } else {
                cache.putTruePolicyRelabels(pair, dependencies);
                s.addAll(dependencies);
            }
            return result;
        }
        
        public static java.lang.String stringValue(
          fabric.lang.security.Label lb) {
            if (fabric.lang.Object._Proxy.idEquals(lb, null)) return "<null>";
            return lb.toString();
        }
        
        public static java.lang.String toString(fabric.lang.security.Label lb) {
            return fabric.lang.security.LabelUtil._Impl.stringValue(lb);
        }
        
        public static int hashCode(fabric.lang.security.Label lb) {
            if (fabric.lang.Object._Proxy.idEquals(lb, null)) return 0;
            return lb.hashCode();
        }
        
        private static fabric.util.Set simplifyJoin(
          fabric.worker.Store store, fabric.util.Set policies,
          java.util.Set dependencies) {
            fabric.util.Set
              needed =
              (fabric.util.LinkedHashSet)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedHashSet)
                     new fabric.util.LinkedHashSet._Impl(store).$getProxy()).
                      fabric$util$LinkedHashSet$());
            for (fabric.util.Iterator i = policies.iterator(); i.hasNext(); ) {
                fabric.lang.security.Policy ci =
                  (fabric.lang.security.Policy)
                    fabric.lang.Object._Proxy.$getProxy(i.next());
                boolean subsumed = fabric.lang.Object._Proxy.idEquals(ci, null);
                for (fabric.util.Iterator j = needed.iterator();
                     !subsumed && j.hasNext(); ) {
                    fabric.lang.security.Policy cj =
                      (fabric.lang.security.Policy)
                        fabric.lang.Object._Proxy.$getProxy(j.next());
                    if (fabric.lang.security.LabelUtil._Impl.relabelsTo(
                                                               ci, cj,
                                                               dependencies)) {
                        subsumed = true;
                        break;
                    }
                    if (fabric.lang.security.LabelUtil._Impl.relabelsTo(
                                                               cj, ci,
                                                               dependencies)) {
                        j.remove();
                    }
                }
                if (!subsumed) needed.add(ci);
            }
            return needed;
        }
        
        private static fabric.util.Set simplifyMeet(
          fabric.worker.Store store, fabric.util.Set policies,
          java.util.Set dependencies) {
            fabric.util.Set
              needed =
              (fabric.util.LinkedHashSet)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedHashSet)
                     new fabric.util.LinkedHashSet._Impl(store).$getProxy()).
                      fabric$util$LinkedHashSet$());
            for (fabric.util.Iterator i = policies.iterator(); i.hasNext(); ) {
                fabric.lang.security.Policy ci =
                  (fabric.lang.security.Policy)
                    fabric.lang.Object._Proxy.$getProxy(i.next());
                boolean subsumed = fabric.lang.Object._Proxy.idEquals(ci, null);
                for (fabric.util.Iterator j = needed.iterator();
                     !subsumed && j.hasNext(); ) {
                    fabric.lang.security.Policy cj =
                      (fabric.lang.security.Policy)
                        fabric.lang.Object._Proxy.$getProxy(j.next());
                    if (fabric.lang.security.LabelUtil._Impl.relabelsTo(
                                                               cj, ci,
                                                               dependencies)) {
                        subsumed = true;
                        break;
                    }
                    if (fabric.lang.security.LabelUtil._Impl.relabelsTo(
                                                               ci, cj,
                                                               dependencies)) {
                        j.remove();
                    }
                }
                if (!subsumed) needed.add(ci);
            }
            return needed;
        }
        
        public static void notifyNewDelegation(
          fabric.lang.security.Principal granter,
          fabric.lang.security.Principal superior) {
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            cache.clearFalseLabelRelabels();
            cache.clearFalsePolicyRelabels();
        }
        
        /**
   * Throws an exception if o's store is not trusted to enforce accessLabel.
   * @param accessLabel
   * @param o
   */
        public static fabric.lang.Object accessCheck(
          fabric.lang.security.Label accessLabel, fabric.lang.Object o) {
            fabric.lang.security.Label storeLabel =
              fabric.lang.security.LabelUtil._Impl.
              readerPolicyLabel(
                fabric.lang.security.PrincipalUtil._Impl.topPrincipal(),
                o.fetch().$getStore().getPrincipal());
            if (!fabric.lang.security.LabelUtil._Impl.relabelsTo(accessLabel,
                                                                 storeLabel))
                throw new fabric.common.exceptions.InternalError(
                        "Illegal access to " + o.$getStore() +
                          ": access label is " + accessLabel.toString()); else
                return o;
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.LabelUtil) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.LabelUtil._Proxy(this);
        }
        
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
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public fabric.worker.LocalStore get$localStore();
        
        public fabric.worker.LocalStore set$localStore(
          fabric.worker.LocalStore val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.LabelUtil._Static {
            public fabric.worker.LocalStore get$localStore() {
                return ((fabric.lang.security.LabelUtil._Static._Impl) fetch()).
                  get$localStore();
            }
            
            public fabric.worker.LocalStore set$localStore(
              fabric.worker.LocalStore val) {
                return ((fabric.lang.security.LabelUtil._Static._Impl) fetch()).
                  set$localStore(val);
            }
            
            public _Proxy(fabric.lang.security.LabelUtil._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.LabelUtil._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  LabelUtil.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.LabelUtil._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.LabelUtil._Static._Impl.class);
                $instance = (fabric.lang.security.LabelUtil._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.LabelUtil._Static {
            public fabric.worker.LocalStore get$localStore() {
                return this.localStore;
            }
            
            public fabric.worker.LocalStore set$localStore(
              fabric.worker.LocalStore val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.localStore = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public fabric.worker.LocalStore localStore;
            
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
                return new fabric.lang.security.LabelUtil._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm16 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled19 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff17 = 1;
                        boolean $doBackoff18 = true;
                        boolean $retry12 = true;
                        boolean $keepReads13 = false;
                        $label10: for (boolean $commit11 = false; !$commit11;
                                       ) {
                            if ($backoffEnabled19) {
                                if ($doBackoff18) {
                                    if ($backoff17 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff17));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e14) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff17 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff17 =
                                          java.lang.Math.
                                            min(
                                              $backoff17 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff18 = $backoff17 <= 32 ||
                                                 !$doBackoff18;
                            }
                            $commit11 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.lang.security.LabelUtil._Static._Proxy.
                                  $instance.
                                  set$localStore(
                                    fabric.worker.Worker.getWorker().
                                        getLocalStore());
                            }
                            catch (final fabric.worker.RetryException $e14) {
                                $commit11 = false;
                                continue $label10;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e14) {
                                $commit11 = false;
                                $retry12 = false;
                                $keepReads13 = $e14.keepReads;
                                fabric.common.TransactionID $currentTid15 =
                                  $tm16.getCurrentTid();
                                if ($e14.tid == null ||
                                      !$e14.tid.isDescendantOf($currentTid15)) {
                                    throw $e14;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e14);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e14) {
                                $commit11 = false;
                                fabric.common.TransactionID $currentTid15 =
                                  $tm16.getCurrentTid();
                                if ($e14.tid.isDescendantOf($currentTid15))
                                    continue $label10;
                                if ($currentTid15.parent != null) {
                                    $retry12 = false;
                                    throw $e14;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e14) {
                                $commit11 = false;
                                $retry12 = false;
                                if ($tm16.inNestedTxn()) {
                                    $keepReads13 = true;
                                }
                                throw $e14;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid15 =
                                  $tm16.getCurrentTid();
                                if ($commit11) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e14) {
                                        $commit11 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e14) {
                                        $commit11 = false;
                                        $retry12 = false;
                                        $keepReads13 = $e14.keepReads;
                                        if ($e14.tid ==
                                              null ||
                                              !$e14.tid.isDescendantOf(
                                                          $currentTid15))
                                            throw $e14;
                                        throw new fabric.worker.
                                                UserAbortException($e14);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e14) {
                                        $commit11 = false;
                                        $currentTid15 = $tm16.getCurrentTid();
                                        if ($currentTid15 != null) {
                                            if ($e14.tid.equals(
                                                           $currentTid15) ||
                                                  !$e14.tid.isDescendantOf(
                                                              $currentTid15)) {
                                                throw $e14;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm16.inNestedTxn() &&
                                          $tm16.checkForStaleObjects()) {
                                        $retry12 = true;
                                        $keepReads13 = false;
                                    }
                                    if ($keepReads13) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e14) {
                                            $currentTid15 = $tm16.getCurrentTid();
                                            if ($currentTid15 != null &&
                                                  ($e14.tid.equals($currentTid15) || !$e14.tid.isDescendantOf($currentTid15))) {
                                                throw $e14;
                                            } else {
                                                $retry12 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit11) {
                                    {  }
                                    if ($retry12) { continue $label10; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -17, 117, -75, 13, 51,
    85, 56, 90, -49, 82, -26, 15, 9, 101, -27, 84, -64, 64, -12, -94, 58, -77,
    68, -62, 58, -50, -18, -72, 87, -118, -62, -26 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
