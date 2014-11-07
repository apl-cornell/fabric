package fla.util;

import fla.principals.Principal;

/**
 * Represents a delegation from an inferior to a superior.
 */
public class DelegationPair {
  public final Principal inferior;
  public final Principal superior;

  /**
   * @param inferior the principal whose authority is being delegated
   * @param superior the principal being delegated to
   */
  public DelegationPair(Principal inferior, Principal superior) {
    this.inferior = inferior;
    this.superior = superior;
  }

  public Principal inferior() {
    return inferior;
  }

  public Principal superior() {
    return superior;
  }
}
