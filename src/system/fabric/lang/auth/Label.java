package fabric.lang.auth;

import fabric.lang.Object;

/**
 * A Label is the runtime representation of a Jif label.
 */
public interface Label extends Object {
  // /**
  // * Determines whether this <= l. If this method
  // * returns true, then all delegations on which this result depends (i.e.,
  // * DelegationPairs) are added to <code>dependencies</code>. If this method
  // * returns false, then <code>dependencies</code> remains unaltered.
  // */
  // boolean relabelsTo(Label l, Set dependencies);

  Label join(Label l);

  Label meet(Label l);

  ConfPolicy confPolicy();

  IntegPolicy integPolicy();

  public static class $Proxy extends Object.$Proxy implements Label {

    public $Proxy(fabric.client.Core core, long onum) {
      super(core, onum);
    }

    public Label join(Label l) {
      return ((Label) fetch()).join(l);
    }

    public Label meet(Label l) {
      return ((Label) fetch()).meet(l);
    }

    public ConfPolicy confPolicy() {
      return ((Label) fetch()).confPolicy();
    }

    public IntegPolicy integPolicy() {
      return ((Label) fetch()).integPolicy();
    }
  }
}
