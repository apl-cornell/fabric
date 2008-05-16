package cms.model;

import cms.auth.Principal;

public class GroupAssignedTo {

  public static String UNASSIGNED = "<unassigned>";
  public static String ALLPARTS = "<All Parts>";
  
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Group      group;
  private SubProblem subProblem;
  private User       user;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setGroup      (final Group group)           { this.group      = group;      }
  public void setSubProblem (final SubProblem subProblem) { this.subProblem = subProblem; }
  public void setUser       (final User user)             { this.user       = user;       }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Group      getGroup()      { return this.group;      }
  public SubProblem getSubProblem() { return this.subProblem; }
  public User       getUser()       { return this.user;       }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public GroupAssignedTo(Group group, User user, SubProblem subProblem) {
    setGroup(group);
    setUser(user);
    setSubProblem(subProblem);
  }
  
  public static boolean isAssignedTo(SubProblem subProblem, Group group, User user) {
    // TODO Auto-generated method stub
    return false;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
