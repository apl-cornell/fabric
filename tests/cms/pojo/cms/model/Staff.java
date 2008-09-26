package cms.model;

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
    user.staffCourses.add(this);
    user.staffIndex.put(course, this);
  }
  
  public int compareTo(Object o) {
    if (!(o instanceof Staff)) return 0;
    return user.getNetID().compareTo(((Staff) o).user.getNetID());
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
