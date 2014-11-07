package fabric.ast;

import polyglot.ast.Branch;

public interface FabricBranch extends Branch {
  public static final Kind ABORT = new Kind("abort");
  public static final Kind RETRY = new Kind("retry");
}
