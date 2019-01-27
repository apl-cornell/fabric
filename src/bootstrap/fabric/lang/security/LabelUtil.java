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
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e14) {
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
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e14) {
                                $commit11 = false;
                                if ($tm16.checkForStaleObjects())
                                    continue $label10;
                                fabric.common.TransactionID $currentTid15 =
                                  $tm16.getCurrentTid();
                                if ($e14.tid.isDescendantOf($currentTid15)) {
                                    $retry12 = true;
                                }
                                else if ($currentTid15.parent != null) {
                                    $retry12 = false;
                                    throw $e14;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
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
    
    public static final byte[] $classHash = new byte[] { 44, -121, -66, 6, -102,
    81, -110, -52, 76, 38, -48, -77, -93, 47, -42, 7, -114, 12, -58, -25, -78,
    41, -40, -57, -82, -112, -114, -56, 2, -17, -10, 40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVcC3gUVZY+XXlDIJAAQnhDRIGQjPiEoAuJIIHmIe/HaKxUVycFlaqmukKCOziiozg6soyCoCu4MyIIi+K6g84MMuPMOPgaXfRzHXc/R91dWR/o+nacUZc9597bj3Sqb6rz1eb76vyVqntPnf/cc899dHUf/hAK4g6Mi6rNhlnjborp8Zo5anNjeLHqxPVIg6nG48vwapPWN7/xrncPREYpoIShVFMt2zI01Wyy4i70D69TN6q1lu7WLl/SWLcWSjSqOFeNt7qgrK3vdGBMzDY3tZi2Kx7STf/OybU7dl094NE8KFsDZYa11FVdQ2uwLVfvdNdAaZve1qw78VmRiB5ZAwMtXY8s1R1DNY1rsaBtrYHyuNFiqW67o8eX6HHb3EgFy+PtMd1hz0xcJPNtNNtp11zbQfMHcPPbXcOsDRtxty4MhVFDNyPxDXAd5IehIGqqLVhwSDjBopZprJ1D17F4HwPNdKKqpieq5K83rIgLozNrJBlXzccCWLWoTXdb7eSj8i0VL0A5N8lUrZbapa5jWC1YtMBux6e4UJlVKRYqjqnaerVFb3JhaGa5xfwWliphbqEqLgzOLMY0YZtVZrRZWmt9uHDGtr+15loKhNDmiK6ZZH8xVhqVUWmJHtUd3dJ0XrF0UvgudcjxWxQALDw4ozAv8/j3PplZPerJZ3iZ4R5lFjWv0zW3SdvX3P+lEQ0Tp+WRGcUxO25QKHRhzlp1sbhT1xnDaB+S1Eg3axI3n1xyYvX1h/TTCvRphELNNtvbMKoGanZbzDB15wrd0h3V1SONUKJbkQZ2vxGK8DxsWDq/uigajetuI+Sb7FKhzf5HF0VRBbmoCM8NK2onzmOq28rOO2MA0A8PCAEoNwOs3oPng/B8lQtLalvtNr222WzXOzC8a/HQVUdrrcV+6xjaFM2ObaqNO1qt0265Bpbk13n8xHWt3THcTbVhtVk3lxNltCb2/6K1k7gM6AiF0M2jNTuiN6txbDMRP/WLTewic20zojtNmrnteCNUHL+bxVAJxX0cY5d5KYTtPiIzY6TX3dFeP/uTh5ue5/FHdYUTXRjFrawhK2sSVtYkrUTDSqlv1WC2qsFsdTjUWdOwt/EfWQgVxllfS+oqRV3TY6bqRm2nrRNCIUZsEKvPYgdbfj1mFEwapROXXjXvmlvG5WHQxjryqR2xaFVmF0olnkY8U7FfNGllW9/98shdm+1UZ3Khqlsf716T+ui4TC85tqZHMAem1E8aox5tOr65SqH8UoKpz1UxODGPjMp8Rpe+WpfIe+SNgjD0JR+oJt1KJKs+bqtjd6SusNbvT6KcBwI5K8NAljIvXRrb89qL753PBpNEdi1LS8NLdbcurUeTsjLWdwemfL/M0XUs96fdi+/c+eHWtczxWGK81wOrSDZgT1axC9vOTc9s+Lc339j3ipJqLBcKY+3NpqF1Mi4Dz+BfCI//pYO6JV0gxOTcIFLCmGROiNGTJ6Rsw+xgYoZC0+NVy602O2JEDbXZ1ClSvik7+7yjH2wbwJvbxCvceQ5U96wgdX1YPVz//NV/HsXUhDQanVL+SxXjKa8ipXmW46ibyI7OLS+PvPtpdQ9GPiasuHGtznIQMH8Aa8CpzBdTmDwv494FJMZxb41IBnxm+p9D42gqFtfUHr63suGy07zPJ2ORdIz16PMr1LRuMvVQ2xfKuMLfK1C0BgawIVy13BUqpi8MgzU4CMcbxMUw9Otyv+uAykePumRfG5HZD9Iem9kLUrkGz6k0nffhgc8DBx0xhJx0JR6VAHkTOCqn6W5FjOSgzhCwk+msyngmJ5CYyByZR6eTXEpHNAlyocRoa2t3qf3Zkya70Me0cfa1FGOZ973BOOiJxNdhO+t1pybctcAw1ELZjNXjvbPT++mKeHpB1LCwaJIV+ysVg9JKgfPTWKWFAnRiLIzMNn9gc599N+zYG1n0wHl8lC/vOibPttrbHnr12z/U7H7rWY8cX+LasSmmvlE30545Ex85tttEdgGbXqWi6K3TI6c1rD/Vwh87OsPEzNIHFxx+9ooJ2h0K5CXDpducrmuluq5B0sfRcUpqLesSKmOSTu1DzroQj8HozPsEuumhwhOpZ0th0iqKOcZGzECpZlJIY4nQFBdoZjaTd59eLbm3lsQSpM/DrIq6UlVifK1Kjq9VKXsXJW0aQFqq8BgB5EiOl/hkyeORxGUZLMuEposFTvLHUpPcY410FY78lk1Z3rZ0y40nelhl9qkFlaj0Yl2Lxxi07bDA2wNhTZp+JPD72VmHRF4W9ld0zRBdkkPXqQ6zzJb4qZ2EkeEnutbi5YRzePPnfSPwVCBOIE1vC/yjv6bfLLnH/NiBqbXZdl27DceSaMJxoz0bnkostnHKsClr60/E41yA/KsFzg6EOGm6XODF/oj/UHLvNhI3utCXE2/EtWxLgvkYT+asSA/UqbtXo4FPCHwwEOqk6YDAe/xR3ym5t4vE32EaxQGFmpP+3eLFhkbx7wAUDBEIgbBBTflnBH7qj819kns/IXG3C8XIhjUR/X+7F52zgI06BfsRL0BcHQgd0rRKYLeJQcrkvNQkJ5aIM+9V22JcjWlGTDW9sxRdPMTkQYlXjpC4H+s6uoqLx1TYejY09dhZAEXHEGcifjcQz5CmtQIX+vMMI5dwzxDhnoy1Aet8zKrHJQ44RuKf/DpgMh7zAIr/BbER0e9kRO4A0hQXGM3ugHymKj/pgBbuBZxLZkzs0BlsIcN3jF488NWw41XvfcUndZn7VmkFPz785umX+418mK2F82lzgh7QJ3PDr/t+XpdtOuaG0iRJ6kw0EjO3PSXwWKbbDnmOyheReJrWAxn/0slzPXn6isRyoNDUrRa31WMBhl2oDdfQG8X+m37LjlvP1GzbwSfVfJNyfLd9wvQ6fKOSPagfexpN7cfKnsJqzHnnyOZjD27eqoggnO9CHnqVTk9kmc0yWpwRiRdInGQVOpO+U7gHkjMatq5lSaPBxEkILZHoVkOiAN+rMeya5GZxokSnZ3ss4g3g00xJp3tLcu8/SbyOKyyNbE6YOiDFha9QuZmsxu8k2t4hcdxv9x4ObGla8iriYsSbAunepOkHAjt6kd/GytM/ru9Tqe4jiS8+I/GeX19gP4XluAbTBE4MxBek6VyBw7P7QkmpOsQEe+LXEnLfkvjChYHp5JKrD8+JN6WmNQB9cQ0Iq9GeZwJhSJqeFvjLHFo7STNUkJ1mqIgunsmJJg3aTUjzBUScave1AqFJmtoEXpMjzZ8zKmUSmgNJlOREk4bmdQD9yhENgNLHA6FJmh4TeDA7Te+hmcQJxmeYhCt1g1BFTlwpT20A6D8eMYacXw6EK2l6SeBTOTbpx4zKeAnNs0mMzIkmTZc3Is2PBB4IhCZp2i/w7l510CkSmrUkzsEk24FZOj3Jei41qG/eADCwEHELXtsfCEPS9IDA3b3qmxdLGE4jcZ5fhrQXejMy/IVAJxCGpGmDwEiv2nCWhGEDiToM1XSGPSfZ2wDKyahbEcsDoUmaBgrM71VDzpfQXEBidk40K/HYAVCxE/FOxOmB0CRN0wRWZ6fZU5JdLuG6ksSinLnuBRj0OeIexN2BcCVNuwTe2nuuTRKuKonVfjsojSWYLAbT9uM+xEOB0CRNBwXem2Pk8rHEkDBcT0Lzy3ApHmjLkDsELgqEIWlaKHCWf4ZbmJmMRVzCkLaQQ21s700epZfi8TjA0N8LDCZKSdMugZIoTZumc3LMdsk+coj2kUMdPnhdhscxgGEzBPYPhBdp6icQ/PPi7bVVwuuHJLb44DULj99gcjmL47D/DoQXaTol8LVc2+vHEl53krjNhb6mEXWX9cCtHg+Mw8pdAvVAuJGmiMCVubbZ30u47SGx0ye3OjyexWxZxrHy7UC4kab/Eviq/yTCRoMWRuIBCUGaL4fucyF/nW1YWZktwAPXhsN/JDCY9EiaFgqUpEfPcY6b+SKJyVz1S9izmm3b1FWLMTsiYf0zEgd7Yk3t+Q7A6BsFrgqENWlaKXBer9rzCQmzX5F4DJm16XzPJ2t7nuZ7roSjfxcIM9L0W4FHc21PsvsRRuGEhB7tv4Se7InePIBQAUBVPsfxzwVBj2l6VuAT/huOjeY8i56UMKP1eei5tJD03HH7Lj6/BJkdFHhVIMxIU0LzouzMMhtuS0KIhvt3Cb3XSbzSE70YGoFj79kRgfmB0CNNeRyrvsyVHrs92IV+qTfAxG5qAyP2toT0uyTekJCuoKI4PQuVA0zA3hjCi2f7/qDXhZKYY7u65uoRL+7lQuEBgbuycy9gGgs8mvZUqn0/kVD9jMT7PbUv5p3QRQCTZwssDqR9SVMRx0l/9d8x2ZskfAog2TUO0a5x6Is0Zp4LiCZ8/nSA6gKOkx8LhBlpOirwwezMMiP39oTgDadIdosV2i0OnZHQo1ehwEQjcLpd/bJA2ye9kBezYqHEEtjaG2anmPWSDWKFNoiVkp6YXYdG4KR0yg0C+2VhRspKu/OgKqUCC/33rzQeqWYaKiFTSaKipyikga8FYOpxgTcGEoWk6QaB7f77V2rgU8ZJmNELb8qItCE968C3HuD8TQKnBMKMNFULHO0/CjMGPqVaQq+GxISe6NHAZwNcsIDj+R8GQo80fSDwzd7Q453sIgm9S0h8R0IvOcS5ABeW0MfESPPHOdAL/Y8XvXKhabvA6/33Pa+xTZkp4VhPYnpPTUhNtxtg+nSO074NpAlJ0zcCP/Lf91JjmzJPwixM4vI0ZlnHtnuR2acC7w+EGWn6qcCd/oMzc2xbJqG3gsRCCb3k2PYPuNr7tcBsbyxt8B4BqMpagct7w4N3sqskPJpIrOqJB41k+wBmxDjWfZ0bD6ryV4Gf++9NniNZVEKGRntF7SnmLkFLngeY9QuBuWSM7DFHmrYLvDE7x7R9otSyW4lJSNFHVso6F0r1De3GRtXULXeZzfzhRW4GmnASoH6jwHmBkCNNjQJn+CfHPutSrpWQ+x4JF8kZ8SW6GqE3gOo3Scn9K0DDhQJLAiFHmoo51n+dK7kfSMjdTOI6Rm6lY7g9kKOw/BPA5ZcKHBwIOdI0SGBRrmG5TUJuO4lbXPoShUlbl3FJUM5EAz4GuKKS45z3A6FGmt4T+Hqu7bZbQo1eXlbucKFY1dx4U9R2shL7G3z850jsNoGBvJHKNK0VKHkj1ZvYTyXE9pG414UiIjZHwosmGn8BmBsWOC4QXqRprMBBPfJi/w92Ybj3+2epN9yJ1EOM32EJ90dI7PcXr9egkd8CNBZynBvMips0HRUoWXGn5lvKQwkhBvKfS/jRy17Ko/744bpEwaQQXimwLgh+TNN0gVOy8wulVu88y/xWwopeBFKecKEv//Yv+8ZgIjDSXsrk38NnN7q9ie7lgIlo5iicV48SWBCIA0hTPsfwn3NwgGTvVqG9W+U59r2BpYkvQCsnvDhNwCefgxYcFxjI60pM036BkteVunGSbNgqlKyVV5BTqxpvbbAj7IO6kxmc6ICL8MlTARb+ByJqWZjTR3orvDgNEJoiAlf46Yip70KUpb/sn3j1NdVBJTu2Cu3YKm9gdMaNtphpRDfN49styqlszDH7LqGuOh/gykcCYU6ajgj8iT/mzMIUw48lDOlrMsr7aQwX8Gl4N4ZsTTEX7dABlk/luOyzLAw3eK4pWJVPBX6QnYrnq7zKXyQsaAqofO5ChWW7yGGh3nG5buot7JdMWIWMTwbzN9pGJMvkQHEAVrwgcG8gHZI07RG43Rfv5BfGytNHUv4eu3fKJOvyCrP7KK+UBGBaVjVNj8cbWnWNXorJK/Jq6JFox2aAVfcIvDl7Q8Mb3RuaqtwkUPJ9ynTzKiT3aFqd1x9NrzIsw2WfuseZ6Z0ulCS/KktfzB/u8SMZ4gdctIan9H2n5lcPzvIDGUO7/aSOqPfw3rLis/Yu/yP/kkvix1lKwlAcbTfN9C+wp50Xxhw9ajBXlTDZP8aoVLowyGtyhJk1ccqoDeXFRyLptOIYuQTpJca4UMhL0H9jY4nslhLoJnba7tCPBx3+7KyvCouXvcV+qwEdPKZ6668K77nyjj+EJ5z85/trXy3aVnrinUcnvvb0w9u3PaN89OW5/weT/DiJ1EgAAA==";
}
