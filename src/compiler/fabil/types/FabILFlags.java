package fabil.types;

import polyglot.types.Flags;

public class FabILFlags extends Flags {
  public static final Flags ATOMIC = createFlag("atomic", FINAL);
  public static final Flags MEMOIZED = createFlag("memoized", FINAL);
  public static final Flags NONFABRIC = createFlag("nonfabric", FINAL);
}
