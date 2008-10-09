package cms.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AssignmentItem {
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private final Assignment assignment;
  private String     name;
  private boolean    hidden;
  
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
    
    this.hiddenFiles = new HashSet();
  }
  
  public AssignmentItem(Assignment assign) {
    this(assign, "");
  }
  
  public AssignmentFile getAssignmentFile() {
    return file;
  }
  
  public Collection/*AssignmentFile*/ findHiddenAssignmentFiles() {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
