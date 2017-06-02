package bolt.ast;

import bolt.types.BoltContext;
import bolt.types.BoltContext.FinalInitKind;
import polyglot.types.Context;
import polyglot.util.SerialVersionUID;

/**
 * An extension node for constructor prologues.
 */
public class BoltPrologueExt extends BoltTermExt {

  private static final long serialVersionUID = SerialVersionUID.generate();

  @Override
  public Context enterScope(Context c) {
    return ((BoltContext) c).pushFinalInitializer(FinalInitKind.INLINE);
  }

}
