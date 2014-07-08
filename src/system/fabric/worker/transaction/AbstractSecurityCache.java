/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
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
