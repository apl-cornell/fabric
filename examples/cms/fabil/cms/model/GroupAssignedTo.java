package cms.model;

import cms.auth.Principal;

/**
 * Represents the assignment of a group/subproblem pair to a grader.
 */
public class GroupAssignedTo {

  public static String UNASSIGNED = "<unassigned>";
  public static String ALLPARTS = "<All Parts>";
  
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Group      group;
  private SubProblem subProblem;
  private User       grader;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setGroup      (final Group group)           { this.group      = group;      }
  public void setSubProblem (final SubProblem subProblem) { this.subProblem = subProblem; }
  public void setGrader     (final User user)             { this.grader     = user;       }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Group      getGroup()      { return this.group;      }
  public SubProblem getSubProblem() { return this.subProblem; }
  public User       getGrader()     { return this.grader;     }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public GroupAssignedTo(Group group, User grader, SubProblem subProblem) {
    setGroup(group);
    setGrader(grader);
    setSubProblem(subProblem);
    
    subProblem.assignedTo.put(group, grader);
  }
  
  public static boolean isAssignedTo(SubProblem subProblem, Group group, User user) {
    return subProblem.assignedTo.get(group) == user;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
