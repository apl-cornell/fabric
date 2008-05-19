package cms.model;

import java.util.Collection;

import cms.auth.Principal;

// TODO: not sure if User should extend Principal
// TODO: figure out when to use what
public class User implements Principal {

  public static final String AUTHOR_CORNELL_COMMUNITY = null;
  
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
  
  /**
   * @return the unique course for this student for the given semester, or null
   *         if the student is enrolled in more or less than one course.
   */
  public Course getSoloCourse(Semester semester) {
    // TODO Auto-generated method stub
    return null;
  }
  public boolean hasAssignAccess(Course course, Assignment assign) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isInStaffAsBlankMode() {
    // TODO
    return false;
  }
  public boolean isAdminPrivByCourse(Course course) {
    // TODO
    return false;
  }
  public boolean isGroupsPrivByCourse(Course course) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isGradesPrivByCourse(Course course) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isAssignPrivByCourse(Course course) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isCategoryPrivByCourse(Course course) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isStaffInCourseByCourse(Course course) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isCMSAdmin() {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isStudentInCourseByCourse(Course course) {
    // TODO Auto-generated method stub
    return false;
  }
  public int getAuthoriznLevelByCourse(Course course) {
    // TODO Auto-generated method stub
    return 0;
  }
  public boolean isAssignPrivByAssignment(Assignment assignment) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isAuthenticated() {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isGuest() {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean delegatesTo(Principal delagatee) {
    return false;
  }
  public boolean hasCourseAccess(Course course) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean hasStudentsPageAccess(Course course) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isAdminPrivByAssignment(Assignment assign) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isCategoryPrivByCategory(Category category) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isGradesPrivByAssignment(Assignment assign) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isGroupsPrivByAssignment(Assignment assign) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isStudentInCourseByAssignment(Assignment assign) {
    // TODO Auto-generated method stub
    return false;
  }
  public boolean isStudentInCourseByGroup(Group group) {
    // TODO Auto-generated method stub
    return false;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
