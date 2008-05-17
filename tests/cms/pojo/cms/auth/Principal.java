package cms.auth;

import cms.model.Assignment;
import cms.model.Course;

public class Principal {

  public static final String AUTHOR_CORNELL_COMMUNITY = null; // TODO

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

  public String getNetID() {
    // TODO Auto-generated method stub
    return null;
  }

  public int getAuthoriznLevelByCourse(Course course) {
    // TODO Auto-generated method stub
    return 0;
  }

  public boolean isAssignPrivByAssignment(Assignment assignment) {
    // TODO Auto-generated method stub
    return false;
  }
}
