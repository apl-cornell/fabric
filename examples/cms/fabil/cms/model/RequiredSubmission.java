package cms.model;

import fabric.util.*;

public class RequiredSubmission {
  private static int nextID = 1;

  public static final String ANY_TYPE = "accept any";
  public static final String MATCHING_TYPE = "tgz";

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private final Assignment assignment;
  private String     submissionName;
  private int        maxSize;
  private boolean    hidden;
  private final Set  fileTypes;
  private final int  id;
  
  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

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
    this.assignment = assign;
    setSubmissionName(name);
    setMaxSize(maxSize);
    setHidden(false);
    this.fileTypes = new HashSet();
    id = nextID++;
    
    this.assignment.requiredSubmissions.add(this);
    this.assignment.course.semester.database.requiredSubmissions.put(
        toString(), this);
  }

  public RequiredSubmission(Assignment assign) {
    this(assign, "", 0);
  }
  public boolean isHidden() {
    return getHidden();
  }
  public Collection/*String*/ getRequiredFileTypes() {
    return Collections.unmodifiableCollection(fileTypes);
  }
  public String matchFileType(String givenFileType) {
    throw new NotImplementedException();
  }
  public void addRequiredFileType(String value) {
    fileTypes.add(value);
  }
  
  public String toString() {
    return "" + id;
  
  }

}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
