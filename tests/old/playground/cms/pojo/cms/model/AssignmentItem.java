package cms.model;

import java.util.Collections;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AssignmentItem {
  private static int nextID = 1;
  
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private final Assignment assignment;
  private String     name;
  private boolean    hidden;
  private final int  id;
  
  //////////////////////////////////////////////////////////////////////////////
  // protected members                                                        //
  //////////////////////////////////////////////////////////////////////////////
  
  final Set hiddenFiles;
  AssignmentFile file;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setItemName   (final String itemName)       { this.name   = itemName;   }
  public void setHidden     (final boolean hidden)        { this.hidden     = hidden;     }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Assignment getAssignment() { return this.assignment; }
  public String     getItemName()   { return this.name;   }
  public boolean    getHidden()     { return this.hidden;     }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public AssignmentItem(Assignment assign, String name) {
    this.assignment = assign;
    setItemName(name);
    setHidden(false);
    id = nextID++;
    
    this.hiddenFiles = new HashSet();
    this.assignment.items.add(this);
    this.assignment.course.semester.database.assignmentItems.put(toString(), this);
  }
  
  public AssignmentItem(Assignment assign) {
    this(assign, "");
  }
  
  public AssignmentFile getAssignmentFile() {
    return file;
  }
  
  public Collection/*AssignmentFile*/ findHiddenAssignmentFiles() {
    return Collections.unmodifiableCollection(hiddenFiles);
  }
  
  public String toString() {
    return "" + id;
  }
  
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
