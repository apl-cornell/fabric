package sif.html;

import sif.servlet.HTMLServlet;
import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;

/**
 * @author andru
 *
 * A TextArea is a multi-line text input with a fixed
 * number of rows and columns.
 */
public final class TextArea extends InputNode {
  private final int rows;
  private final int cols;
  private final String initial_text;

  public TextArea(Principal servletP, Label L, Label E, Input input, int rows,
      int cols, String initial) {
    super(servletP, L, E, input);
    this.rows = rows;
    this.cols = cols;
    initial_text = initial;
  }

  public TextArea(Principal servletP, Label L, Label E, HTMLServlet s,
      Label inputLabel, int rows, int cols, String initial) {
    super(servletP, L, E, new Input(servletP, s, inputLabel));
    this.rows = rows;
    this.cols = cols;
    initial_text = initial;
  }

  public TextArea(Principal servletP, Label L, Label E, String name, int rows,
      int cols, String initial) {
    super(servletP, L, E, name);
    this.rows = rows;
    this.cols = cols;
    initial_text = initial;
  }

  @Override
  void writeImpl(HTMLWriter p) {
    p.print("<textarea");
    p.print(" name=");
    p.printq(getName());
    p.print(" rows=");
    p.printq(rows);
    p.print(" cols=");
    p.printq(cols);
    p.print(">");
    p.noindent(true);
    p.escape(initial_text);
    p.noindent(false);
    // what goes here in a textarea?
    p.print("</textarea>");
  }

  public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
    return ((o instanceof TextArea) && InputNode.jif$Instanceof(P, l, e, o));
  }

  public static TextArea jif$cast$sif_html_TextArea(Principal P, Label l,
      Label e, Object o) {
    if (o == null) return null;
    if (jif$Instanceof(P, l, e, o)) return (TextArea) o;
    throw new ClassCastException();
  }
}
