package sif.html;

import sif.servlet.HTMLServlet;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;
import fabric.lang.security.PrincipalUtil;

/**
 * @author andru
 *
 * An Input node is one that accepts user input. It
 * must occur within a form. There are several kinds
 * of Input nodes. Every input is associated with a
 * servlet. Input nodes should result in HTML decorations
 * that describe confidentiality requirements.
 * XXX Currently this last is not implemented.
 */
public abstract class InputNode extends Node {
  // Invariant: exactly one of input and name is null.
  // For off-site forms, input must be null.
  // For forms that submit back to the servlet, name must be null.
  public final Input input;
  public final String name;

  private final Principal servletP;

  InputNode(Principal servletP, Label L, Label E, Input i) {
    super(L, E);
    this.servletP = servletP;
    input = i;
    name = null;
  }

  InputNode(Principal servletP, Label L, Label E, HTMLServlet s,
      Label inputLabel) {
    this(servletP, L, E, new Input(servletP, s, inputLabel));
  }

  InputNode(Principal servletP, Label L, Label E, String n) {
    super(L, E);
    this.servletP = servletP;
    input = null;
    name = n;
  }

  public final String getName() {
    if (name == null) return input.getName();
    return name;
  }

  public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
    if ((o instanceof InputNode) && Node.jif$Instanceof(l, e, o)) {
      InputNode that = (InputNode) o;
      return PrincipalUtil._Impl.equivalentTo(that.servletP, P);
    }
    return false;
  }

  public static InputNode jif$cast$sif_html_InputNode(Principal P, Label l,
      Label e, Object o) {
    if (o == null) return null;
    if (jif$Instanceof(P, l, e, o)) return (InputNode) o;
    throw new ClassCastException();
  }

}
