package util;

import jif.runtime.Runtime;
import jif.lang.Principal;
import jif.lang.Label;

import java.io.PrintStream;

public final class System {
  public static final Principal user = Runtime.getRuntime(null).user(null);

  public static PrintStream stdout(Label lbl) {
    // this is safe because the where clause gaurantees that information can
    // flow from the label to the user.

    return java.lang.System.out;
  }
}


/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
