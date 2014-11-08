package fla.util;

import fla.principals.Principal;

public abstract class ActsForProof {
  public final Principal superior;
  public final Principal inferior;

  ActsForProof(Principal superior, Principal inferior) {
    this.superior = superior;
    this.inferior = inferior;
  }
}
