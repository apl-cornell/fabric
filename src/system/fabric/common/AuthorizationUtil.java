package fabric.common;

import jif.lang.Label;
import jif.lang.LabelUtil;
import fabric.worker.Worker;
import fabric.worker.Core;
import fabric.common.util.OidKeyHashMap;
import fabric.lang.NodePrincipal;

public class AuthorizationUtil {

  /**
   * This is the cache for authorized reads. The keys in this map are label
   * onums. The values are sets of oids of principals that are authorized to
   * read according to the label. We're not using the caches in LabelUtil
   * because the transaction management is too slow (!!).
   */
  private static final OidKeyHashMap<OidKeyHashMap<Void>> cachedReadAuthorizations =
      new OidKeyHashMap<OidKeyHashMap<Void>>();

  /**
   * This is the cache for authorizing writes. The keys in this map are label
   * onums. The values are principals that are authorized to write according to
   * the label. We're not using the caches in LabelUtil because the transaction
   * management is too slow (!!).
   */
  private static final OidKeyHashMap<OidKeyHashMap<Void>> cachedWriteAuthorizations =
      new OidKeyHashMap<OidKeyHashMap<Void>>();

  private static boolean checkAuthorizationCache(
      OidKeyHashMap<OidKeyHashMap<Void>> cache,
      NodePrincipal principal, Core core, long labelOnum) {
    OidKeyHashMap<Void> submap;
    synchronized (cache) {
      submap = cache.get(core, labelOnum);
      if (submap == null) return false;
    }

    synchronized (submap) {
      return submap.containsKey(principal);
    }
  }

  private static void cacheAuthorization(
      OidKeyHashMap<OidKeyHashMap<Void>> cache, NodePrincipal principal,
      Core core, long labelOnum) {
    OidKeyHashMap<Void> submap;
    synchronized (cache) {
      submap = cache.get(core, labelOnum);
      if (submap == null) {
        submap = new OidKeyHashMap<Void>();
        cache.put(core, labelOnum, submap);
      }
    }

    synchronized (submap) {
      submap.put(principal, null);
    }
  }

  /**
   * Determines whether the given principal is permitted to read according to
   * the label at the given oid. This is run as a subtransaction of the current
   * transaction.
   */
  public static boolean isReadPermitted(final NodePrincipal principal,
      Core core, long labelOnum) {
    // Allow the core's worker principal to do anything. We use pointer equality
    // here to avoid having to call into the worker.
    if (principal == Worker.getWorker().getPrincipal()) return true;

    if (checkAuthorizationCache(cachedReadAuthorizations, principal, core,
        labelOnum)) return true;

    // Call into the Jif label framework to perform the label check.
    final Label label = new Label._Proxy(core, labelOnum);
    boolean result = Worker.runInSubTransaction(new Worker.Code<Boolean>() {
      public Boolean run() {
        return LabelUtil._Impl.isReadableBy(label, principal);
      }
    });

    if (result) {
      cacheAuthorization(cachedReadAuthorizations, principal, core, labelOnum);
    }

    return result;
  }

  /**
   * Determines whether the given principal is permitted to write according to
   * the label at the given onum. This is run as a subtransaction of the current
   * transaction.
   */
  public static boolean isWritePermitted(final NodePrincipal principal,
      Core core, long labelOnum) {
    // Allow the core's worker principal to do anything. We use pointer equality
    // here to avoid having to call into the worker.
    if (principal == Worker.getWorker().getPrincipal()) return true;

    if (checkAuthorizationCache(cachedWriteAuthorizations, principal, core,
        labelOnum)) return true;

    // Call into the Jif label framework to perform the label check.
    final Label label = new Label._Proxy(core, labelOnum);
    boolean result = Worker.runInSubTransaction(new Worker.Code<Boolean>() {
      public Boolean run() {
        return LabelUtil._Impl.isWritableBy(label, principal);
      }
    });

    if (result) {
      cacheAuthorization(cachedWriteAuthorizations, principal, core, labelOnum);
    }
    return result;
  }
}
