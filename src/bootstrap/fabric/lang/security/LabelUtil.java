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
    
    public static final byte[] $classHash = new byte[] { 112, -79, 27, -110, 36,
    -124, 32, -55, -84, -34, 103, -7, 2, 121, -61, 45, -74, 81, 35, -33, -40,
    35, 81, -52, 3, 38, 110, 20, 2, -24, -57, -118 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
