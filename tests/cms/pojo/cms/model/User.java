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
    throw new NotImplementedException();
  }
  
  public Collection/*Course*/ findStaffCoursesBySemester(Semester semester) {
    throw new NotImplementedException();
  }
  
  public Collection/*Course*/ findStudentCourses() {
    throw new NotImplementedException();
  }
  
  public Collection/*Course*/ findStudentCoursesBySemester(Semester semester) {
    throw new NotImplementedException();
  }

  public String canonicalName() {
    if (getFirstName().length() > 0)
      return getFirstName() + " " + getLastName();
    else
      return getLastName();
  }
  
  public Collection/*Semester*/ findSemesters() {
    throw new NotImplementedException();
  }
  
  /**
   * @return the unique course for this student for the given semester, or null
   *         if the student is enrolled in more or less than one course.
   */
  public Course getSoloCourse(Semester semester) {
    throw new NotImplementedException();
  }
  public boolean hasAssignAccess(Course course, Assignment assign) {
    throw new NotImplementedException();
  }
  public boolean isInStaffAsBlankMode() {
    throw new NotImplementedException();
  }
  public boolean isAdminPrivByCourse(Course course) {
    throw new NotImplementedException();
  }
  public boolean isGroupsPrivByCourse(Course course) {
    throw new NotImplementedException();
  }
  public boolean isGradesPrivByCourse(Course course) {
    throw new NotImplementedException();
  }
  public boolean isAssignPrivByCourse(Course course) {
    throw new NotImplementedException();
  }
  public boolean isCategoryPrivByCourse(Course course) {
    throw new NotImplementedException();
  }
  public boolean isStaffInCourseByCourse(Course course) {
    throw new NotImplementedException();
  }
  public boolean isCMSAdmin() {
    throw new NotImplementedException();
  }
  public boolean isStudentInCourseByCourse(Course course) {
    throw new NotImplementedException();
  }
  public int getAuthoriznLevelByCourse(Course course) {
    throw new NotImplementedException();
  }
  public boolean isAssignPrivByAssignment(Assignment assignment) {
    throw new NotImplementedException();
  }
  public boolean isAuthenticated() {
    throw new NotImplementedException();
  }
  public boolean isGuest() {
    throw new NotImplementedException();
  }
  public boolean delegatesTo(Principal delagatee) {
    return false;
  }
  public boolean hasCourseAccess(Course course) {
    throw new NotImplementedException();
  }
  public boolean hasStudentsPageAccess(Course course) {
    throw new NotImplementedException();
  }
  public boolean isAdminPrivByAssignment(Assignment assign) {
    throw new NotImplementedException();
  }
  public boolean isCategoryPrivByCategory(Category category) {
    throw new NotImplementedException();
  }
  public boolean isGradesPrivByAssignment(Assignment assign) {
    throw new NotImplementedException();
  }
  public boolean isGroupsPrivByAssignment(Assignment assign) {
    throw new NotImplementedException();
  }
  public boolean isStudentInCourseByAssignment(Assignment assign) {
    throw new NotImplementedException();
  }
  public boolean isStudentInCourseByGroup(Group group) {
    throw new NotImplementedException();
  }
  public boolean canView(SolutionFile sol) {
    // See TransactionHandler.authorizeDownload
    throw new NotImplementedException();
  }
  /**
   * return true if this user is a staff member assigned to grade a given student.
   * @param student
   * @param assignment
   * @return
   */
  public boolean isAssignedTo(Student student, Assignment assignment) {
    throw new NotImplementedException();
  }
  public boolean isAssignedTo(Group group, Assignment assignment) {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
