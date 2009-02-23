package fabric.common;

import java.util.HashMap;
import java.util.Map;

import jif.lang.Label;
import jif.lang.LabelUtil;
import fabric.client.Client;
import fabric.client.Core;
import fabric.lang.Principal;

public class AuthorizationUtil {

  /**
   * This is the cache for authorizing reads. The keys in this map are label
   * onums and principals. The values are booleans that specify whether the
   * principal is authorized to read according to the label. We're not using the
   * caches in LabelUtil because the transaction management is too slow (!!).
   */
  private static final OidKeyHashMap<Map<Principal, Boolean>> cachedReadAuthorizations =
      new OidKeyHashMap<Map<Principal, Boolean>>();

  private static Boolean checkAuthorizationCache(
      OidKeyHashMap<Map<Principal, Boolean>> cache, Principal principal,
      Core core, long labelOnum) {
    Map<Principal, Boolean> submap;
    synchronized (cache) {
      submap = cache.get(core, labelOnum);
      if (submap == null) return null;
    }

    synchronized (submap) {
      return submap.get(principal);
    }
  }

  private static void cacheAuthorization(
      OidKeyHashMap<Map<Principal, Boolean>> cache, Principal principal,
      Core core, long labelOnum, Boolean result) {
    Map<Principal, Boolean> submap;
    synchronized (cache) {
      submap = cache.get(core, labelOnum);
      if (submap == null) {
        submap = new HashMap<Principal, Boolean>();
        cache.put(core, labelOnum, submap);
      }
    }

    synchronized (submap) {
      submap.put(principal, result);
    }
  }

  /**
   * Determines whether the given principal is permitted to read according to
   * the label at the given oid.
   */
  public static boolean isReadPermitted(final Principal principal, Core core,
      long labelOnum) {
    // Allow the core's client principal to do anything. We use pointer equality
    // here to avoid having to call into the client.
    if (principal == Client.getClient().getPrincipal()) return true;

    Boolean result =
        checkAuthorizationCache(cachedReadAuthorizations, principal, core,
            labelOnum);
    if (result != null) return result;

    // Call into the Jif label framework to perform the label check.
    final Label label = new Label.$Proxy(core, labelOnum);
    result = Client.runInTransaction(new Client.Code<Boolean>() {
      public Boolean run() {
        return LabelUtil.$Impl.isReadableBy(label, principal);
      }
    });

    cacheAuthorization(cachedReadAuthorizations, principal, core, labelOnum,
        result);

    return result;
  }

  /**
   * Determines whether the given principal is permitted to write according to
   * the label at the given onum.
   */
  public static boolean isWritePermitted(final Principal principal, Core core,
      long labelOnum) {
    // Allow the core's client principal to do anything. We use pointer equality
    // here to avoid having to call into the client.
    if (principal == Client.getClient().getPrincipal()) return true;

    // Call into the Jif label framework to perform the label check.
    final Label label = new Label.$Proxy(core, labelOnum);
    return Client.runInTransaction(new Client.Code<Boolean>() {
      public Boolean run() {
        return LabelUtil.$Impl.isWritableBy(label, principal);
      }
    });
  }
}
