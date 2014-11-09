package fla.util;

import fla.principals.Principal;

public abstract class ActsForProof<Superior extends Principal, Inferior extends Principal> {
  public final Superior superior;
  public final Inferior inferior;

  ActsForProof(Superior superior, Inferior inferior) {
    this.superior = superior;
    this.inferior = inferior;
  }
}
