package fabric.worker.transaction;

import fabric.lang.security.SecurityCache;

/**
 * A cache of acts-for relationships. This provides only the hooks needed for
 * the transaction manager to do its thing. All other functionality is provided
 * in fabric.lang.security.AuthorizationCache. Note that the methods implemented
 * here should all be final and package-protected. Any abstract methods should
 * be protected.
 */
public abstract class AbstractSecurityCache {
  /**
   * Resets the state of this cache back to how it was when it was created.
   */
  protected abstract void reset();

  /**
   * Sets the state of this cache to match that of the given cache. Assumes that
   * the given cache will no longer be used.
   */
  protected abstract void set(SecurityCache cache);
}
