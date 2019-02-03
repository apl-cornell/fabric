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
                        int $backoff17 = 1;
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
                                    if ($backoff17 < 5000) $backoff17 *= 2;
                                }
                                $doBackoff18 = $backoff17 <= 32 ||
                                                 !$doBackoff18;
                            }
                            $commit11 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.lang.security.LabelUtil._Static.
                                      _Proxy.
                                      $instance.
                                      set$localStore(
                                        fabric.worker.Worker.getWorker().
                                            getLocalStore());
                                }
                                catch (final fabric.worker.
                                         RetryException $e14) {
                                    throw $e14;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e14) {
                                    throw $e14;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e14) {
                                    throw $e14;
                                }
                                catch (final Throwable $e14) {
                                    $tm16.getCurrentLog().checkRetrySignal();
                                    throw $e14;
                                }
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
                                if ($tm16.checkForStaleObjects()) {
                                    $retry12 = true;
                                    $keepReads13 = false;
                                    continue $label10;
                                }
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
                                if ($tm16.checkForStaleObjects())
                                    continue $label10;
                                $retry12 = false;
                                throw new fabric.worker.AbortException($e14);
                            }
                            finally {
                                if ($commit11) {
                                    fabric.common.TransactionID $currentTid15 =
                                      $tm16.getCurrentTid();
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
                                        if ($tm16.checkForStaleObjects()) {
                                            $retry12 = true;
                                            $keepReads13 = false;
                                            continue $label10;
                                        }
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
                                } else if ($keepReads13) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
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
    
    public static final byte[] $classHash = new byte[] { -101, -122, -106, 9,
    -119, -50, -124, 27, 0, 77, 102, -104, -114, -91, -27, 39, -38, 26, 5, 52,
    -4, 52, -29, 33, -22, -1, 22, -3, -19, 80, -64, -50 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
