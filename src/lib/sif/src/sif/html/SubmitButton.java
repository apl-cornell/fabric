package sif.html;

import sif.servlet.HTMLServlet;
import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;

/**
 * @author andru
 *
 * A button that submits a form.
 */
public final class SubmitButton extends InputNode {
  private final String value;

  public SubmitButton(Principal servletP, Label L, Label E, Input input,
      String val) {
    super(servletP, L, E, input);
    value = val;
  }

  public SubmitButton(Principal servletP, Label L, Label E, HTMLServlet s,
      Label inputLabel, String val) {
    super(servletP, L, E, new Input(servletP, s, inputLabel));
    value = val;
  }

  public SubmitButton(Principal servletP, Label L, Label E, String name,
      String val) {
    super(servletP, L, E, name);
    value = val;
  }

  @Override
  void writeImpl(HTMLWriter p) {
    p.print("<input ");
    p.begin();
    p.print("type=submit class=submit value=");
    p.printq(value);
    p.print(" name=");
    p.printq(getName());
    p.end();
    p.print(" />");
  }

  public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
    return ((o instanceof SubmitButton) && InputNode.jif$Instanceof(P, l, e, o));
  }

  public static SubmitButton jif$cast$sif_html_SubmitButton(Principal P,
      Label l, Label e, Object o) {
    if (o == null) return null;
    if (jif$Instanceof(P, l, e, o)) return (SubmitButton) o;
    throw new ClassCastException();
  }

}
