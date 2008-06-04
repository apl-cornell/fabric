package cms.model;

import java.util.Collection;

public class RequiredSubmission {

  public static final String ANY_TYPE = "accept any";
  public static final String MATCHING_TYPE = "tgz";

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Assignment assignment;
  private String     submissionName;
  private int        maxSize;
  private boolean    hidden;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setAssignment     (final Assignment assignment)     { this.assignment     = assignment;     }
  public void setSubmissionName (final String submissionName)     { this.submissionName = submissionName; }
  public void setMaxSize        (final int maxSize)               { this.maxSize        = maxSize;        }
  public void setHidden         (final boolean hidden)            { this.hidden         = hidden;         }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Assignment getAssignment()     { return this.assignment;     }
  public String     getSubmissionName() { return this.submissionName; }
  public int        getMaxSize()        { return this.maxSize;        }
  public boolean    getHidden()         { return this.hidden;         }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public RequiredSubmission(Assignment assign, String name, int maxSize) {
    setAssignment(assign);
    setSubmissionName(name);
    setMaxSize(maxSize);
    setHidden(false);
  }

  public RequiredSubmission(Assignment assign) {
    // TODO Auto-generated constructor stub
  }
  public boolean isHidden() {
    // TODO Auto-generated method stub
    return false;
  }
  public Collection/*String*/ getRequiredFileTypes() {
    // TODO Auto-generated method stub
    return null;
  }
  public String matchFileType(String givenFileType) {
    // TODO Auto-generated method stub
    return null;
  }
  public void addRequiredFileType(String value) {
    // TODO Auto-generated method stub
    
  }

}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
