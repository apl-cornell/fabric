package generated;

import diaspora.client.Core;
import diaspora.client.DObject;
import diaspora.client.DRef;
import diaspora.client.DArray;
import diaspora.client.UnreachableCoreException;

public class ArrayTest extends DObject {

  // parameter ints ////////////////////////////////////////////////////////////
  private DRef ints_ref;

  public DArray<Integer> get_ints() {
    if (ints_ref == null) return null;

    DArray<Integer> result = (DArray<Integer>) ints_ref.fetch();
    ints_ref = result.this_ref;

    return result;
  }

  public void set_ints(DArray<Integer> new_ints) {
    if (new_ints == null)
      ints_ref = null;
    else ints_ref = new_ints.this_ref;
  }

  // parameter obs /////////////////////////////////////////////////////////////
  private DRef obs_ref;

  public DArray<DRef> get_obs() {
    if (obs_ref == null) return null;

    DArray<DRef> result = (DArray<DRef>) obs_ref.fetch();
    obs_ref = result.this_ref;

    return result;
  }

  public void set_obs(DArray<DRef> new_obs) {
    if (new_obs == null)
      obs_ref = null;
    else obs_ref = new_obs.this_ref;
  }

  // constructors //////////////////////////////////////////////////////////////

  public ArrayTest(Core core) throws UnreachableCoreException {
    super(core);

    set_ints (new DArray<Integer>(getCore(), 3));
    for (int i = 0; i < get_ints().get_length(); i++)
      get_ints().set(i, i);

    set_obs (new DArray<DRef>(getCore(), 1));
    get_obs().set(1, this_ref);
  }

  public static void main(String[] args) {
    runTests();
    // atomic {
    runTests();
    // }
  }

  public static void runTests() {
    ArrayTest a1 = null;
    ArrayTest a2 = null;
    // atomic {
    {
    Core core = null;
    a1 = new ArrayTest(core);
    a2 = new ArrayTest(core);
    a2.set_ints( new DArray<Integer>(core, new Integer[] {5, 4, 3, 2, 1}) );
    }
    // }

    // atomic {
    {
    DArray<Integer> temp = a1.get_ints();
    a1.set_ints(a2.get_ints());
    a2.set_ints(temp);
    }
    // }

    // atomic {
    {
    DArray<DRef> temp = a1.get_obs();
    a1.set_obs(a2.get_obs());
    a2.set_obs(temp);
    }
    // }

    // atomic {
    {
    a1.get_obs().set(0, a1.this_ref);
    }
    // }

    // atomic {
    {
    a1.get_obs().set(0, ((ArrayTest) ((ArrayTest) a1.get_obs().get(0).fetch()).get_obs().get(0).fetch()).this_ref);
    }
    // }
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0
*/

