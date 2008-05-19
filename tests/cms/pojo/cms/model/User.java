package cms.model;

import java.util.Collection;

import cms.auth.Principal;

// TODO: not sure if User should extend Principal
// TODO: figure out when to use what
public class User extends Principal {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private String userID;
  private String firstName;
  private String lastName;
  private String CUID;
  private String college;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setNetID     (final String userID)    { this.userID    = userID;    }
  public void setFirstName (final String firstName) { this.firstName = firstName; }
  public void setLastName  (final String lastName)  { this.lastName  = lastName;  }
  public void setCUID      (final String CUID)      { this.CUID      = CUID;      }
  public void setCollege   (final String college)   { this.college   = college;   }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public String getNetID()     { return this.userID;    }
  public String getFirstName() { return this.firstName; }
  public String getLastName()  { return this.lastName;  }
  public String getCUID()      { return this.CUID;      }
  public String getCollege()   { return this.college;   }
  
  
  //////////////////////////////////////////////////////////////////////////////
  // public methods                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Collection/*Course*/ findStaffCourses() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public Collection/*Course*/ findStaffCoursesBySemester(Semester semester) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public Collection/*Course*/ findStudentCourses() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public Collection/*Course*/ findStudentCoursesBySemester(Semester semester) {
    // TODO Auto-generated method stub
    return null;
  }

  public String canonicalName() {
    if (getFirstName().length() > 0)
      return getFirstName() + " " + getLastName();
    else
      return getLastName();
  }
  public Collection/*Semester*/ findSemesters() {
    // TODO Auto-generated method stub
    return null;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
