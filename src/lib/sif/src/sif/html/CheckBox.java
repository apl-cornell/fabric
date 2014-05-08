package sif.html;

import sif.servlet.HTMLRequest;
import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;

public final class CheckBox extends InputNode {
  private final boolean checked;

  public CheckBox(Principal servletP, Label L, Label E, Input input,
      boolean checked_) {
    super(servletP, L, E, input);
    checked = checked_;
  }

  public CheckBox(Principal servletP, Label L, Label E, String name,
      boolean checked_) {
    super(servletP, L, E, name);
    checked = checked_;
  }

  @Override
  void writeImpl(HTMLWriter p) {
    p.print("<input type=checkbox");
    p.print(" name=");
    p.printq(getName());
    p.print(" value=yes");
    if (checked) p.print(" checked");
    p.print(" />");
  }

  /** 
   * @param r
   * @return Whether this checkbox is checked in client request r.
   */
  public boolean isChecked(HTMLRequest r) {
    if (r.getParam(input) == null) return false;
    return r.getParam(input).equals("yes");
  }

  public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
    return ((o instanceof CheckBox) && InputNode.jif$Instanceof(P, l, e, o));
  }

  public static CheckBox jif$cast$sif_html_CheckBox(Principal P, Label l,
      Label e, Object o) {
    if (o == null) return null;
    if (jif$Instanceof(P, l, e, o)) return (CheckBox) o;
    throw new ClassCastException();
  }
}
