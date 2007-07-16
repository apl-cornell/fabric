package OO7;

public class Manual {
  public char[] title;
  public int    id;

  public String text;
  public Module mod;

  public Manual (int id) {
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

  public int firstLast() {
    return 0;
  }

}

 


/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

