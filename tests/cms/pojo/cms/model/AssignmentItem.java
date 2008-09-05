package cms.model;

import java.util.Collection;

public class AssignmentItem {
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Assignment assignment;
  private String     itemName;
  private boolean    hidden;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setAssignment (final Assignment assignment) { this.assignment = assignment; }
  public void setItemName   (final String itemName)       { this.itemName   = itemName;   }
  public void setHidden     (final boolean hidden)        { this.hidden     = hidden;     }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Assignment getAssignment() { return this.assignment; }
  public String     getItemName()   { return this.itemName;   }
  public boolean    getHidden()     { return this.hidden;     }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  AssignmentItem(Assignment assign, String name) {
    setAssignment(assignment);
    setItemName(name);
    setHidden(false);
  }
  
  public AssignmentItem(Assignment assign) {
    throw new NotImplementedException();
  }
  // Note: I think it may be the case that an assignment item has a unique
  // non-hidden file and then a collection of hidden files, but I'm not sure.
  public AssignmentFile getAssignmentFile() {
    throw new NotImplementedException();
  }
  public Collection/*AssignmentFile*/ findHiddenAssignmentFiles() {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
