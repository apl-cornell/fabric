package fabil.types;

import polyglot.types.Flags;

public class FabILFlags extends Flags {
  public static final Flags ATOMIC = createFlag("atomic", null);
  public static final Flags NONFABRIC = createFlag("nonfabric", null);
  public static final Flags IMMUTABLE = createFlag("immutable", null);
}
