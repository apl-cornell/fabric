package cms.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import cms.www.TransactionResult;
import cms.www.util.Emailer;

public class Transactions {
  public Transactions(CMSRoot database) {
    // TODO
  }

  public boolean acceptInvitation(User user, Group group) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean addCMSAdmin(User p, User admin) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean createCourse(User p, String courseCode, String courseName) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean addSiteNotice(User p, String text, User author, Date exp, boolean hidden) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean editSiteNotice(User p, SiteNotice id, String text, Date exp, boolean hidden, boolean deleted) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean addRegradeRequest(User p, Group group, Collection subProblems, String requestText) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean assignGrader(User p, Assignment assign, SubProblem subProblem, User grader, Collection groups) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean cancelInvitation(User p, User canceled, Group group) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean changeGroupSlot(User p, Group group, Assignment assign, TimeSlot slotNum, boolean addGroup) {
    // TODO Auto-generated method stub
    return false;
  }

  public TransactionResult commitFinalGradesFile(User p, Course course, List table) {
    // TODO Auto-generated method stub
    return null;
  }

  public TransactionResult addStudentsToCourse(User pr, Vector netids, Course course, boolean emailOn) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean inviteUser(User inviter, User invited, Group group) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean leaveGroup(User p, Group group) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean createCategory(User p, Category category) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean updateCategory(User p, Category category) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean postAnnouncement(User p, Course course, String announce) {
    // TODO Auto-generated method stub
    return false;
  }

  public TransactionResult removeAssignment(User p, Assignment assignment) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean removeCategory(User p, Category category) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean removeCMSAdmin(User p) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean removeCtgRow(User p, CategoryRow row) {
    // TODO Auto-generated method stub
    return false;
  }

  public TransactionResult removeExtension(User p, Group group) {
    // TODO Auto-generated method stub
    return null;
  }

  public TransactionResult restoreAnnouncement(User p, Announcement announce) {
    // TODO Auto-generated method stub
    return null;
  }

  public TransactionResult restoreAssignment(User p, Assignment assignment) {
    // TODO Auto-generated method stub
    return null;
  }

  public TransactionResult sendEmail(User p, Course course, Emailer email) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean commitGradesFile(User p, Assignment assignment, List table) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean commitStudentInfo(User p, List table, Course course, boolean isClasslist) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean createGroup(User p, List netids, Assignment assignment) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean createSemester(User p, String semesterName) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean declineInvitation(User p, Group group) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean disbandGroup(User p, Group group) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean dropStudents(User p, Collection users, Course course) {
    // TODO Auto-generated method stub
    return false;
  }

  public void editAnnouncement(User p, Announcement annt, String newText, boolean remove) {
    // TODO Auto-generated method stub
    
  }

  public void editSemester(User p, Semester semester, boolean hidden) {
    // TODO Auto-generated method stub
    
  }

  public boolean setCurrentSemester(User p, Semester sem) {
    // TODO Auto-generated method stub
    return false;
  }

  public TransactionResult setExtension(User p, Group group, Date extension) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean setFinalGrades(User p, Course course, Collection grades) {
    // TODO Auto-generated method stub
    return false;
  }
}
