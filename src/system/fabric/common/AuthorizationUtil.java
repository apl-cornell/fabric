package fabric.common;

import fabric.common.util.OidKeyHashMap;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;
import fabric.lang.security.Principal;
import fabric.worker.Store;
import fabric.worker.Worker;

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

  /**
   * Return true if cache[label][<code>principal</code>] exists, where label is
   * given by the Oid (<code>store</code>,<code>labelOnum</code>).
   */
  private static boolean checkAuthorizationCache(
      OidKeyHashMap<OidKeyHashMap<Void>> cache, Principal principal,
      Store store, long labelOnum) {
    OidKeyHashMap<Void> submap;
    synchronized (cache) {
      submap = cache.get(store, labelOnum);
      if (submap == null) return false;
    }

    synchronized (submap) {
      return submap.containsKey(principal);
    }
  }

  private static void cacheAuthorization(
      OidKeyHashMap<OidKeyHashMap<Void>> cache, Principal principal,
      Store store, long labelOnum) {
    OidKeyHashMap<Void> submap;
    synchronized (cache) {
      submap = cache.get(store, labelOnum);
      if (submap == null) {
        submap = new OidKeyHashMap<Void>();
        cache.put(store, labelOnum, submap);
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
  public static boolean isReadPermitted(final Principal principal, Store store,
      long labelOnum) {
    // Allow the store's worker principal to do anything. We use pointer
    // equality here to avoid having to call into the worker.
    if (principal == Worker.getWorker().getPrincipal()) return true;

    if (checkAuthorizationCache(cachedReadAuthorizations, principal, store,
        labelOnum)) return true;

    // Call into the Jif label framework to perform the label check.
    final Label label = new Label._Proxy(store, labelOnum);
    boolean result = Worker.runInSubTransaction(new Worker.Code<Boolean>() {
      @Override
      public Boolean run() {
        return LabelUtil._Impl.isReadableBy(label, principal);
      }
    });

    if (result) {
      cacheAuthorization(cachedReadAuthorizations, principal, store, labelOnum);
    }

    return result;
  }

  /**
   * Determines whether the given principal is permitted to write according to
   * the label at the given onum. This is run as a subtransaction of the current
   * transaction.
   */
  public static boolean isWritePermitted(final Principal principal,
      Store store, long labelOnum) {
    // Allow the store's worker principal to do anything. We use pointer
    // equality here to avoid having to call into the worker.
    if (principal == Worker.getWorker().getPrincipal()) return true;

    if (checkAuthorizationCache(cachedWriteAuthorizations, principal, store,
        labelOnum)) return true;

    // Call into the Jif label framework to perform the label check.
    final Label label = new Label._Proxy(store, labelOnum);
    boolean result = Worker.runInSubTransaction(new Worker.Code<Boolean>() {
      @Override
      public Boolean run() {
        return LabelUtil._Impl.isWritableBy(label, principal);
      }
    });

    if (result) {
      cacheAuthorization(cachedWriteAuthorizations, principal, store, labelOnum);
    }
    return result;
  }
}
