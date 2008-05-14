package fabric.client.transaction;

import fabric.common.FabricRuntimeException;
import fabric.lang.Object.$Impl;
import fabric.lang.Object.$Proxy;

public final class VersionConflictException extends
    FabricRuntimeException {
  public $Proxy reference;

  public VersionConflictException($Impl obj) {
    this(obj.$getProxy());
  }

  public VersionConflictException($Proxy reference) {
    this.reference = reference;
  }
}