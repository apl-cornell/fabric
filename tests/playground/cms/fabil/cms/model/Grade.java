package cms.model;

import java.util.Date;

public class Grade {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private final User       user;
  private final Assignment assignment;
  private final SubProblem subproblem;
  private Float      grade;
  private User       grader;
  private Date       entered;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setGrade      (final Float grade)           { this.grade      = grade;      }
  public void setGrader     (final User grader)           { this.grader     = grader;     }
  public void setEntered    (final Date entered)          { this.entered    = entered;    }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public User       getUser()       { return this.user;       }
  public Assignment getAssignment() { return this.assignment; }
  public SubProblem getSubProblem() { return this.subproblem; }
  public Float      getGrade()      { return this.grade;      }
  public User       getGrader()     { return this.grader;     }
  public Date       getEntered()    { return this.entered;    }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Grade(Assignment assign, SubProblem subProblem, Float grade, User user, User grader) {
    this.assignment = assign;
    this.subproblem = subProblem;
    this.user = user;
    
    setGrade(grade);
    setGrader(grader);
    setEntered(new Date(System.currentTimeMillis()));
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
