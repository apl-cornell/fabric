package cms.model;

import cms.www.util.Util;

public class Staff implements Comparable {

  //possible statuses
  public static String ACTIVE = "Active", INACTIVE = "Inactive";
  
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Course  course;
  private User    user;
  private String  status;
  private boolean adminPriv;
  private boolean groupsPriv;
  private boolean gradesPriv;
  private boolean assignmentsPriv;
  private boolean categoryPriv;
  private boolean emailNewAssign;
  private boolean emailDueDate;
  private boolean emailAssignedTo;
  private boolean emailFinalGrade;
  private boolean emailRequest;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setCourse          (final Course course)           { this.course          = course;          }
  public void setUser            (final User user)               { this.user            = user;            }
  public void setStatus          (final String status)           { this.status          = status;          }
  public void setAdminPriv       (final boolean adminPriv)       { this.adminPriv       = adminPriv;       }
  public void setGroupsPriv      (final boolean groupsPriv)      { this.groupsPriv      = groupsPriv;      }
  public void setGradesPriv      (final boolean gradesPriv)      { this.gradesPriv      = gradesPriv;      }
  public void setAssignmentsPriv (final boolean assignmentsPriv) { this.assignmentsPriv = assignmentsPriv; }
  public void setCategoryPriv    (final boolean categoryPriv)    { this.categoryPriv    = categoryPriv;    }
  public void setEmailNewAssign  (final boolean emailNewAssign)  { this.emailNewAssign  = emailNewAssign;  }
  public void setEmailDueDate    (final boolean emailDueDate)    { this.emailDueDate    = emailDueDate;    }
  public void setEmailAssignedTo (final boolean emailAssignedTo) { this.emailAssignedTo = emailAssignedTo; }
  public void setEmailFinalGrade (final boolean emailFinalGrade) { this.emailFinalGrade = emailFinalGrade; }
  public void setEmailRequest    (final boolean emailRequest)    { this.emailRequest    = emailRequest;    }
  
  public void setStatus(Log log, String status) {
    if (this.status.equals(status)) return;
    
    LogDetail detail =
        new LogDetail(log, user.getNetID() + " was "
            + (status.equals(ACTIVE) ? "restored" : "removed")
            + " as a staff member", user);
    
    setStatus(status);
  }
  
  public void setAdminPriv(Log log, boolean adminPriv) {
    if (this.adminPriv == adminPriv) return;
    logPermChange(log, "Full Admin", adminPriv);
    setAdminPriv(adminPriv);
  }
  
  public void setGroupsPriv(Log log, boolean groupsPriv) {
    if (this.groupsPriv == groupsPriv) return;
    logPermChange(log, "Groups", groupsPriv);
    setGroupsPriv(groupsPriv);
  }
  
  public void setGradesPriv(Log log, boolean gradesPriv) {
    if (this.gradesPriv == gradesPriv) return;
    logPermChange(log, "Grades", gradesPriv);
    setGradesPriv(gradesPriv);
  }
  
  public void setAssignmentsPriv(Log log, boolean assignmentsPriv) {
    if (this.assignmentsPriv == assignmentsPriv) return;
    logPermChange(log, "Assignments", assignmentsPriv);
    setAssignmentsPriv(assignmentsPriv);
  }
  
  public void setCategoryPriv(Log log, boolean categoryPriv) {
    if (this.categoryPriv == categoryPriv) return;
    logPermChange(log, "Content", categoryPriv);
    setCategoryPriv(categoryPriv);
  }
  
  private void logPermChange(Log log, String desc, boolean status) {
    new LogDetail(log, desc + " privilege was "
        + (status ? "granted to " : "revoked from ") + user.getNetID(), user);
  }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Course  getCourse()          { return this.course;          }
  public User    getUser()            { return this.user;            }
  public String  getStatus()          { return this.status;          }
  public boolean getAdminPriv()       { return this.adminPriv;       }
  public boolean getGroupsPriv()      { return this.groupsPriv;      }
  public boolean getGradesPriv()      { return this.gradesPriv;      }
  public boolean getAssignmentsPriv() { return this.assignmentsPriv; }
  public boolean getCategoryPriv()    { return this.categoryPriv;    }
  public boolean getEmailNewAssign()  { return this.emailNewAssign;  }
  public boolean getEmailDueDate()    { return this.emailDueDate;    }
  public boolean getEmailAssignedTo() { return this.emailAssignedTo; }
  public boolean getEmailFinalGrade() { return this.emailFinalGrade; }
  public boolean getEmailRequest()    { return this.emailRequest;    }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Staff(User user, Course course) {
    setUser(user);
    setCourse(course);
    setStatus(ACTIVE);
    setAdminPriv(false);
    setAssignmentsPriv(false);
    setCategoryPriv(false);
    setEmailAssignedTo(false);
    setEmailDueDate(false);
    setEmailFinalGrade(false);
    setEmailNewAssign(false);
    setEmailRequest(false);
    setGradesPriv(false);
    setGroupsPriv(false);
    
    course.staff.put(user, this);
    user.staffIndex.put(course, this);
  }
  
  public Staff(Log log, User user, Course course) {
    this(user, course);
    new LogDetail(log, user.getNetID() + " was added as a staff member", user);
  }
  
  public int compareTo(java.lang.Object o) {
    if (!(o instanceof Staff)) return 0;
    return user.getNetID().compareTo(((Staff) o).user.getNetID());
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
