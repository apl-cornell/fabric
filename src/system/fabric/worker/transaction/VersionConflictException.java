package fabric.worker.transaction;

import fabric.common.exceptions.FabricRuntimeException;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;

public final class VersionConflictException extends FabricRuntimeException {
  public _Proxy reference;

  public VersionConflictException(_Impl obj) {
    this(obj.$getProxy());
  }

  public VersionConflictException(_Proxy reference) {
    this.reference = reference;
  }
}
