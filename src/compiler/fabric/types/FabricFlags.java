package fabric.types;

import polyglot.types.Flags;

public class FabricFlags extends Flags {
  public static final Flags ATOMIC = createFlag("atomic", FINAL);
  public static final Flags NONFABRIC = createFlag("nonfabric", FINAL);
}
