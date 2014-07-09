/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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
package fabric.types;

import jif.types.label.ConfPolicy;
import polyglot.types.Flags;
import polyglot.types.ReferenceType;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.Position;

/**
 * 
 */
public class AccessPolicyInstance_c implements AccessPolicyInstance {

  protected ConfPolicy policy;
  protected ReferenceType container;
  protected Position pos;

  public AccessPolicyInstance_c(Position pos, ReferenceType ct,
      ConfPolicy policy) {
    this.container = ct;
    this.pos = pos;
    this.policy = policy;
  }

  @Override
  public Flags flags() {
    return Flags.NONE;
  }

  @Override
  public void setFlags(Flags flags) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ReferenceType container() {
    return container;
  }

  @Override
  public void setContainer(ReferenceType container) {
    this.container = container;
  }

  @Override
  public boolean isCanonical() {
    return container.isCanonical();
  }

  @Override
  public TypeSystem typeSystem() {
    return container.typeSystem();
  }

  @Override
  public Position position() {
    return pos;
  }

  @Override
  public ConfPolicy policy() {
    return policy;
  }

  @Override
  public boolean equalsImpl(TypeObject t) {
    if (t == this) return true;

    if (t instanceof AccessPolicyInstance) {
      AccessPolicyInstance api = (AccessPolicyInstance) t;
      return api.container().equals(container) && api.policy().equals(policy);
    }
    return false;
  }

  @Override
  public AccessPolicyInstance copy() {
    return new AccessPolicyInstance_c(pos, container, policy);
  }
}
