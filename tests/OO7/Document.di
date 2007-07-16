package OO7;

public class Document {
  public final int id;
  public char[] title;
  public String text;

  public CompositePart part;

  public Document (int id) {
    this.id = id;
  }

  public int searchText (char c) {
    return text.indexOf(c);
  }

  public int replaceText (String oldText, String newText) {
    this.text.replaceAll(oldText,newText);

    // TODO
    return 0;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

