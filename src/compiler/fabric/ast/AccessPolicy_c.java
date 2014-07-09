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
package fabric.ast;

import java.util.List;

import jif.ast.LabelNode;
import jif.types.label.ConfPolicy;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.ast.Term_c;
import polyglot.types.MemberInstance;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabric.types.AccessPolicyInstance;
import fabric.types.FabricParsedClassType;
import fabric.types.FabricTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class AccessPolicy_c extends Term_c implements AccessPolicy {
  protected LabelNode policy;
  protected AccessPolicyInstance accessPolicyInstance;

  @Deprecated
  public AccessPolicy_c(Position pos, LabelNode policy) {
    this(pos, policy, null);
  }

  public AccessPolicy_c(Position pos, LabelNode policy, Ext ext) {
    super(pos, ext);
    this.policy = policy;
  }

  @Override
  public LabelNode policy() {
    return policy;
  }

  @Override
  public AccessPolicy policy(LabelNode policy) {
    return policy(this, policy);
  }

  protected <N extends AccessPolicy_c> N policy(N n, LabelNode policy) {
    if (n.policy == policy) return n;
    n = copyIfNeeded(n);
    n.policy = policy;
    return n;
  }

  @Override
  public MemberInstance memberInstance() {
    return accessPolicyInstance;
  }

  @Override
  public AccessPolicyInstance accessPolicyInstance() {
    return accessPolicyInstance;
  }

  public AccessPolicy accessPolicyInstance(AccessPolicyInstance api) {
    if (api == this.accessPolicyInstance) return this;
    AccessPolicy_c n = (AccessPolicy_c) copy();
    n.accessPolicyInstance = api;
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    LabelNode policy = visitChild(this.policy, v);
    return reconstruct(this, policy);
  }

  protected <N extends AccessPolicy_c> N reconstruct(N n, LabelNode policy) {
    n = policy(n, policy);
    return n;
  }

  @Override
  public Term firstChild() {
    return null;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    return succs;
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) tc.typeSystem();

    FabricParsedClassType ct =
        (FabricParsedClassType) tc.context().currentClass();

    if (ct == null) {
      return this;
    }

    if (accessPolicyInstance == null || !accessPolicyInstance.isCanonical()) {
      ConfPolicy pol = ts.confProjection(policy.label());
      AccessPolicyInstance api = ts.accessPolicyInstance(position(), ct, pol);
      ct.setAccessPolicy(pol);
      return accessPolicyInstance(api);
    } else return this;
  }
}
