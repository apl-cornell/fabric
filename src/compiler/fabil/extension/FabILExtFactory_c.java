/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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
package fabil.extension;

import polyglot.ast.AbstractExtFactory_c;
import polyglot.ast.Ext;

/**
 * Factory for FabIL extension nodes.
 */
public class FabILExtFactory_c extends AbstractExtFactory_c implements
    FabILExtFactory {

  @Override
  public Ext extFabricArrayTypeNode() {
    Ext e = extFabricArrayTypeNodeImpl();

    FabILExtFactory nextExtFactory = (FabILExtFactory) nextExtFactory();
    if (nextExtFactory != null) {
      Ext e2 = nextExtFactory.extFabricArrayTypeNode();
      e = composeExts(e, e2);
    }

    return postExtFabricArrayTypeNode(e);
  }

  protected Ext extFabricArrayTypeNodeImpl() {
    return extArrayTypeNodeImpl();
  }

  protected Ext postExtFabricArrayTypeNode(Ext ext) {
    return postExtArrayTypeNode(ext);
  }

  /** Factory method for Atomic objects */
  @Override
  public final Ext extAtomic() {
    Ext e = extAtomicImpl();

    FabILExtFactory nextExtFactory = (FabILExtFactory) nextExtFactory();
    if (nextExtFactory != null) {
      Ext e2 = nextExtFactory.extAtomic();
      e = composeExts(e, e2);
    }
    return postExtAtomic(e);
  }

  protected Ext extAtomicImpl() {
    return new AtomicExt_c();
  }

  protected Ext postExtAtomic(Ext ext) {
    return postExtBlock(ext);
  }

  @Override
  public final Ext extAbort() {
    Ext e = extAbortImpl();

    FabILExtFactory nextExtFactory = (FabILExtFactory) nextExtFactory();
    if (nextExtFactory != null) {
      Ext e2 = nextExtFactory.extAbort();
      e = composeExts(e, e2);
    }
    return postExtAbort(e);
  }

  protected Ext extAbortImpl() {
    return new AbortExt_c();
  }

  protected Ext postExtAbort(Ext ext) {
    return postExtStmt(ext);
  }

  @Override
  public final Ext extRetry() {
    Ext e = extRetryImpl();

    FabILExtFactory nextExtFactory = (FabILExtFactory) nextExtFactory();
    if (nextExtFactory != null) {
      Ext e2 = nextExtFactory.extRetry();
      e = composeExts(e, e2);
    }
    return postExtRetry(e);
  }

  protected Ext extRetryImpl() {
    return new RetryExt_c();
  }

  protected Ext postExtRetry(Ext ext) {
    return postExtStmt(ext);
  }

  @Override
  protected Ext extArrayAccessAssignImpl() {
    return new ArrayAccessAssignExt_c();
  }

  @Override
  protected Ext extArrayAccessImpl() {
    return new ArrayAccessExt_c();
  }

  @Override
  public final Ext extFabricArrayInit() {
    Ext e = extFabricArrayInitImpl();

    FabILExtFactory nextExtFactory = (FabILExtFactory) nextExtFactory();
    if (nextExtFactory != null) {
      Ext e2 = nextExtFactory.extFabricArrayInit();
      e = composeExts(e, e2);
    }

    return postExtFabricArrayInit(e);
  }

  protected Ext extFabricArrayInitImpl() {
    return new FabricArrayInitExt_c();
  }

  protected Ext postExtFabricArrayInit(Ext ext) {
    return postExtArrayInit(ext);
  }

  @Override
  protected Ext extBinaryImpl() {
    return new BinaryExt_c();
  }

  @Override
  protected Ext extCallImpl() {
    return new CallExt_c();
  }

  @Override
  protected Ext extCastImpl() {
    return new CastExt_c();
  }

  @Override
  protected Ext extClassBodyImpl() {
    return new ClassBodyExt_c();
  }

  @Override
  protected Ext extClassDeclImpl() {
    return new ClassDeclExt_c();
  }

  @Override
  protected Ext extConstructorDeclImpl() {
    return new ConstructorDeclExt_c();
  }

  @Override
  protected Ext extConstructorCallImpl() {
    return new ConstructorCallExt_c();
  }

  @Override
  protected Ext extEvalImpl() {
    return new EvalExt_c();
  }

  @Override
  protected Ext extExprImpl() {
    return new ExprExt_c();
  }

  @Override
  protected Ext extFieldAssignImpl() {
    return new FieldAssignExt_c();
  }

  @Override
  protected Ext extFieldDeclImpl() {
    return new FieldDeclExt_c();
  }

  @Override
  protected Ext extFieldImpl() {
    return new FieldExt_c();
  }

  @Override
  protected Ext extInitializerImpl() {
    return new InitializerExt_c();
  }

  @Override
  protected Ext extInstanceofImpl() {
    return new InstanceofExt_c();
  }

  @Override
  protected Ext extMethodDeclImpl() {
    return new MethodDeclExt_c();
  }

  @Override
  protected Ext extNewImpl() {
    return new NewExt_c();
  }

  @Override
  public Ext extNewFabricArray() {
    Ext e = extNewFabricArrayImpl();

    FabILExtFactory nextExtFactory = (FabILExtFactory) nextExtFactory();
    if (nextExtFactory != null) {
      Ext e2 = nextExtFactory.extNewFabricArray();
      e = composeExts(e, e2);
    }

    return postExtNewFabricArray(e);
  }

  protected Ext extNewFabricArrayImpl() {
    return new NewFabricArrayExt_c();
  }

  protected Ext postExtNewFabricArray(Ext e) {
    return postExtNewArray(e);
  }

  @Override
  protected Ext extNodeImpl() {
    return new FabILExt_c();
  }

  @Override
  protected Ext extPackageNodeImpl() {
    return new PackageNodeExt_c();
  }

  @Override
  protected Ext extSpecialImpl() {
    return new SpecialExt_c();
  }

  @Override
  protected Ext extTypeNodeImpl() {
    return new TypeNodeExt_c();
  }

  @Override
  protected Ext extUnaryImpl() {
    return new UnaryExt_c();
  }

  @Override
  public final Ext extProviderLabel() {
    Ext e = extProviderLabelImpl();

    FabILExtFactory nextExtFactory = (FabILExtFactory) nextExtFactory();
    if (nextExtFactory != null) {
      Ext e2 = nextExtFactory.extProviderLabel();
      e = composeExts(e, e2);
    }
    return postExtProviderLabel(e);
  }

  protected Ext extProviderLabelImpl() {
    return extExprImpl();
  }

  protected Ext postExtProviderLabel(Ext ext) {
    return postExtExpr(ext);
  }

  @Override
  public Ext extCodebaseNode() {
    return extNode();
  }

  @Override
  public Ext extCodebaseDecl() {
    return extNode();
  }

  @Override
  public Ext extCodebaseTypeNode() {
    return extCanonicalTypeNode();
  }
}
