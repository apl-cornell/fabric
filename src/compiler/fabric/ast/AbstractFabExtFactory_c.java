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
package fabric.ast;

import jif.ast.AbstractJifExtFactory_c;
import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;

/**
 * This class serves the same purpose as {@link AbstractJifExtFactory_c} and its
 * parent class {@link polyglot.ast.AbstractExtFactory_c}: it provides a default
 * implementation of the factory method for each fabric extension class that
 * delegates to the factory method for its parent class's extension.
 * 
 * @author mdgeorge
 */
public class AbstractFabExtFactory_c extends AbstractJifExtFactory_c implements
    FabricExtFactory {

  public AbstractFabExtFactory_c() {
    super();
  }

  public AbstractFabExtFactory_c(ExtFactory next) {
    super(next);
  }

  @Override
  public final Ext extAtomic() {
    Ext e = extAtomicImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extAtomic();
      e = composeExts(e, e2);
    }
    return postExtAtomic(e);
  }

  protected Ext extAtomicImpl() {
    return extBlockImpl();
  }

  protected Ext postExtAtomic(Ext e) {
    return postExtBlock(e);
  }

  @Override
  public final Ext extAbortStmt() {
    Ext e = extAbortStmtImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extAbortStmt();
      e = composeExts(e, e2);
    }
    return postExtAbortStmt(e);
  }

  protected Ext extAbortStmtImpl() {
    return extStmtImpl();
  }

  protected Ext postExtAbortStmt(Ext e) {
    return postExtStmt(e);
  }

  @Override
  public final Ext extRetryStmt() {
    Ext e = extRetryStmtImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extRetryStmt();
      e = composeExts(e, e2);
    }
    return postExtRetryStmt(e);
  }

  protected Ext extRetryStmtImpl() {
    return extStmtImpl();
  }

  protected Ext postExtRetryStmt(Ext e) {
    return postExtStmt(e);
  }

  @Override
  public final Ext extWorker() {
    Ext e = extWorkerImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extWorker();
      e = composeExts(e, e2);
    }
    return postExtWorker(e);
  }

  protected Ext extWorkerImpl() {
    return extExprImpl();
  }

  protected Ext postExtWorker(Ext e) {
    return postExtExpr(e);
  }

  @Override
  public final Ext extNewFabricArray() {
    Ext e = extNewFabricArrayImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extNewFabricArray();
      e = composeExts(e, e2);
    }
    return postExtNewFabricArray(e);
  }

  protected Ext extNewFabricArrayImpl() {
    return extNewArrayImpl();
  }

  protected Ext postExtNewFabricArray(Ext e) {
    return postExtNewArray(e);
  }

  @Override
  public final Ext extAmbNewFabricArray() {
    Ext e = extAmbNewFabricArrayImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extAmbNewFabricArray();
      e = composeExts(e, e2);
    }

    return postExtNewFabricArray(e);
  }

  protected Ext extAmbNewFabricArrayImpl() {
    return extAmbNewArrayImpl();
  }

  protected Ext postExtAmbNewFabricArrayImpl(Ext e) {
    return postExtAmbNewArray(e);
  }

  @Override
  public final Ext extRemoteWorkerGetter() {
    Ext e = extRemoteWorkerGetterImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extRemoteWorkerGetter();
      e = composeExts(e, e2);
    }
    return postExtRemoteWorkerGetter(e);
  }

  protected Ext extRemoteWorkerGetterImpl() {
    return extExprImpl();
  }

  protected Ext postExtRemoteWorkerGetter(Ext e) {
    return postExtExpr(e);
  }

  @Override
  public final Ext extFabricArrayInit() {
    Ext e = extFabricArrayInitImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extFabricArrayInit();
      e = composeExts(e, e2);
    }
    return postExtFabricArrayInit(e);
  }

  protected Ext extFabricArrayInitImpl() {
    return extArrayInitImpl();
  }

  protected Ext postExtFabricArrayInit(Ext e) {
    return postExtArrayInit(e);
  }

  @Override
  public Ext extFabricArrayTypeNode() {
    Ext e = extFabricArrayTypeNodeImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extFabricArrayTypeNode();
      e = composeExts(e, e2);
    }
    return postExtFabricArrayTypeNode(e);
  }

  protected Ext extFabricArrayTypeNodeImpl() {
    return extArrayTypeNodeImpl();
  }

  protected Ext postExtFabricArrayTypeNode(Ext e) {
    return postExtArrayTypeNode(e);
  }

  @Override
  public Ext extCodebaseNode() {
    Ext e = extCodebaseNodeImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extCodebaseNode();
      e = composeExts(e, e2);
    }
    return e;
  }

  protected Ext extCodebaseNodeImpl() {
    return extNode();
  }

  @Override
  public Ext extCodebaseDecl() {
    Ext e = extCodebaseDeclImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extCodebaseDecl();
      e = composeExts(e, e2);
    }
    return e;
  }

  protected Ext extCodebaseDeclImpl() {
    return extNode();
  }

  @Override
  public final Ext extStore() {
    Ext e = extStoreImpl();
    if (nextExtFactory() != null
        && nextExtFactory() instanceof FabricExtFactory) {
      FabricExtFactory nextFac = (FabricExtFactory) nextExtFactory();
      Ext e2 = nextFac.extStore();
      e = composeExts(e, e2);
    }
    return postExtStore(e);
  }

  protected Ext extStoreImpl() {
    return extExprImpl();
  }

  protected Ext postExtStore(Ext e) {
    return postExtExpr(e);
  }

}
