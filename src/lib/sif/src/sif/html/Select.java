package sif.html;

import sif.servlet.HTMLServlet;
import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;

/**
 * @author andru
 *
 * A <select> input appliance.
 */
public final class Select extends InputNode {
  private final String[] options;
  private final int selected;

  public Select(Principal servletP, Label L, Label E, Input input,
      int selected_, String[] options_) {
    super(servletP, L, E, input);
    options = options_;
    selected = selected_;
  }

  public Select(Principal servletP, Label L, Label E, HTMLServlet s,
      Label inputLabel, int selected_, String[] options_) {
    super(servletP, L, E, new Input(servletP, s, inputLabel));
    options = options_;
    selected = selected_;
  }

  public Select(Principal servletP, Label L, Label E, String name,
      int selected_, String[] options_) {
    super(servletP, L, E, name);
    options = options_;
    selected = selected_;
  }

  @Override
  void writeImpl(HTMLWriter p) {
    // TODO Auto-generated method stub
    p.begin(2);
    p.print("<select name=");
    p.printq(getName());
    p.print(">");
    for (int i = 0; i < options.length; i++) {
      //p.breakLine();
      if (i == selected)
        p.print("<option selected>");
      else p.print("<option>");
      p.escape(options[i]);
      p.print("</option>");
    }
    p.end();
    //p.breakLine();
    p.print("</select>");
  }

  public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
    return ((o instanceof Select) && InputNode.jif$Instanceof(P, l, e, o));
  }

  public static Select jif$cast$sif_html_Select(Principal P, Label l, Label e,
      Object o) {
    if (o == null) return null;
    if (jif$Instanceof(P, l, e, o)) return (Select) o;
    throw new ClassCastException();
  }

}
