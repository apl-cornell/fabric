package webapp;

import java.util.List;
import java.util.LinkedList;

public class Action {
  public static final int ReadManyPosts = 0, 
      NewPost = 1,  
      NumOccurrencesComment = 2, 
      MassUpdateTitles = 3;
  
  public static List values() {
    List l = new LinkedList();
    l.add("NewPost");
    l.add("NumOccurrencesComment");
    l.add("MassUpdateTitles");
    return l;
  }
}
