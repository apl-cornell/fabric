package cms.model;

import java.util.Collection;
import java.util.Date;

/**
 * A set of responses to a survey.
 */
public class AnswerSet {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Assignment assignment;
  private Group      group;
  private Group      originalGroup;
  private User       user;
  private Date       submissionDate;
  private int        lateSubmission;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setAssignment     (final Assignment assignment)     { this.assignment     = assignment;     }
  public void setGroup          (final Group group)               { this.group          = group;          }
  public void setOriginalGroup  (final Group originalGroup)       { this.originalGroup  = originalGroup;  }
  public void setUser           (final User user)                 { this.user           = user;           }
  public void setSubmissionDate (final Date submissionDate)       { this.submissionDate = submissionDate; }
  public void setLateSubmission (final int lateSubmission)        { this.lateSubmission = lateSubmission; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Assignment getAssignment()     { return this.assignment;     }
  public Group      getGroup()          { return this.group;          }
  public Group      getOriginalGroup()  { return this.originalGroup;  }
  public User       getUser()           { return this.user;           }
  public Date       getSubmissionDate() { return this.submissionDate; }
  public int        getLateSubmission() { return this.lateSubmission; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public AnswerSet(Assignment assignment, Group group, Group originalGroup, User user, Date submitted) {
    setAssignment(assignment);
    setGroup(group);
    setOriginalGroup(originalGroup);
    setUser(user);
    setSubmissionDate(submitted);
  }

  public Collection/*Answer*/ getAnswers() {
    throw new NotImplementedException();
  }

  public Answer getAnswer(SubProblem subProblem) {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
