package sif.html;

import sif.servlet.HTMLServlet;
import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;

/** An input that allows typing in a single line of text. Appropriate
 * for most textual input. */
public class TextInput extends InputNode {
  private final int size;
  private final String initialText;

  public TextInput(Principal servletP, Label L, Label E, Input input, int size,
      String initial) {
    super(servletP, L, E, input);
    this.size = size;
    initialText = initial;
  }

  public TextInput(Principal servletP, Label L, Label E, HTMLServlet s,
      Label inputLabel, int size, String initial) {
    super(servletP, L, E, new Input(servletP, s, inputLabel));
    this.size = size;
    initialText = initial;
  }

  public TextInput(Principal servletP, Label L, Label E, String name, int size,
      String initial) {
    super(servletP, L, E, name);
    this.size = size;
    initialText = initial;
  }

  public final int getSize() {
    return size;
  }

  public final String getInitialText() {
    return initialText;
  }

  String typeName() {
    return "text";
  }

  @Override
  final void writeImpl(HTMLWriter p) {
    p.print("<input");
    p.print(" type=\"" + typeName() + "\"");
    p.print(" name=");
    p.printq(getName());
    p.print(" size=");
    p.printq(size);
    p.print(" value=");
    p.printq(initialText);
    p.print("/>");
  }

  public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
    return ((o instanceof TextInput) && InputNode.jif$Instanceof(P, l, e, o));
  }

  public static TextInput jif$cast$sif_html_TextInput(Principal P, Label l,
      Label e, Object o) {
    if (o == null) return null;
    if (jif$Instanceof(P, l, e, o)) return (TextInput) o;
    throw new ClassCastException();
  }
}
