package cms.model;

import java.util.*;

import cms.www.TransactionError;
import cms.www.TransactionResult;
import cms.www.util.Emailer;

public class Transactions {
  private CMSRoot database;

  public Transactions(CMSRoot database) {
    this.database = database;
  }

  public boolean acceptInvitation(User user, Group group) {
    throw new NotImplementedException();
  }

  public boolean addCMSAdmin(User p, User newAdmin) {
    List users = new ArrayList(1);
    users.add(newAdmin);
    
    Log log = startLog(p);
    log.setLogName(Log.ADD_CMS_ADMIN);
    log.setLogType(Log.LOG_ADMIN);
    log.addReceivingUsers(users);
    new LogDetail(log, newAdmin.getNetID() + " was added as a CMS admin");
    
    newAdmin.setCMSAdmin(true);
    
    return true;
  }

  public boolean createCourse(User p, String courseCode, String courseName) {
    Course course =
        new Course(database, database.getCurrentSemester(), courseName, "",
            courseCode);
    
    Log log = startLog(p);
    log.setCourse(course);
    log.setLogName(Log.CREATE_COURSE);
    log.setLogType(Log.LOG_ADMIN);
    new LogDetail(log, "Created '" + courseCode + ": " + courseName + "'");
    
    return true;
  }

  public boolean addSiteNotice(User p, String text, User author, Date exp, boolean hidden) {
    new SiteNotice(database, p, text, exp, hidden);
    return true;
  }

  public boolean editSiteNotice(User p, SiteNotice notice, String text, Date exp, boolean hidden, boolean deleted) {
    notice.setText(text);
    notice.setExpireDate(exp);
    notice.setHidden(hidden);
    notice.setDeleted(deleted);
    return true;
  }

  public boolean addRegradeRequest(User p, Group group, Collection subProblems, String requestText) {
    throw new NotImplementedException();
  }

  public boolean assignGrader(User p, Assignment assign, SubProblem subProblem, User grader, Collection groups) {
    throw new NotImplementedException();
  }

  public boolean cancelInvitation(User p, User canceled, Group group) {
    throw new NotImplementedException();
  }

  public boolean changeGroupSlot(User p, Group group, Assignment assign, TimeSlot slotNum, boolean addGroup) {
    throw new NotImplementedException();
  }

  public TransactionResult commitFinalGradesFile(User p, Course course, List table) {
    throw new NotImplementedException();
  }

  public TransactionResult addStudentsToCourse(User pr, Vector netids, Course course, boolean emailOn) {
    throw new NotImplementedException();
  }

  public boolean inviteUser(User inviter, User invited, Group group) {
    throw new NotImplementedException();
  }

  public boolean leaveGroup(User p, Group group) {
    throw new NotImplementedException();
  }

  public boolean createCategory(User p, Category category) {
    throw new NotImplementedException();
  }

  public boolean updateCategory(User p, Category category) {
    throw new NotImplementedException();
  }

  public boolean postAnnouncement(User p, Course course, String announce) {
    throw new NotImplementedException();
  }

  public TransactionResult removeAssignment(User p, Assignment assignment) {
    throw new NotImplementedException();
  }

  public boolean removeCategory(User p, Category category) {
    throw new NotImplementedException();
  }

  public boolean removeCMSAdmin(User p, User toRemove) {
    List users = new ArrayList(1);
    users.add(toRemove);
    
    Log log = startLog(p);
    log.setLogName(Log.REMOVE_CMS_ADMIN);
    log.setLogType(Log.LOG_ADMIN);
    log.addReceivingUsers(users);
    new LogDetail(log, "Remove " + toRemove.getNetID() + " from the CMS admin list");
    
    toRemove.setCMSAdmin(false);
    
    return true;
  }

  public boolean removeCtgRow(User p, CategoryRow row) {
    throw new NotImplementedException();
  }

  public TransactionResult removeExtension(User p, Group group) {
    throw new NotImplementedException();
  }

  public TransactionResult restoreAnnouncement(User p, Announcement announce) {
    throw new NotImplementedException();
  }

  public TransactionResult restoreAssignment(User p, Assignment assignment) {
    throw new NotImplementedException();
  }

  public TransactionResult sendEmail(User p, Course course, Emailer email) {
    throw new NotImplementedException();
  }

  public boolean commitGradesFile(User p, Assignment assignment, List table) {
    throw new NotImplementedException();
  }

  public boolean commitStudentInfo(User p, List table, Course course, boolean isClasslist) {
    throw new NotImplementedException();
  }

  public boolean createGroup(User p, List netids, Assignment assignment) {
    throw new NotImplementedException();
  }

  public boolean createSemester(User p, String semesterName) {
    Log log = startLog(p);
    log.setLogName(Log.CREATE_SEMESTER);
    log.setLogType(Log.LOG_ADMIN);
    new LogDetail(log, "Created semester: " + semesterName);
    
    new Semester(database, semesterName);
    
    return true;
  }

  public boolean declineInvitation(User p, Group group) {
    throw new NotImplementedException();
  }

  public boolean disbandGroup(User p, Group group) {
    throw new NotImplementedException();
  }

  public boolean dropStudents(User p, Collection users, Course course) {
    throw new NotImplementedException();
  }

  public void editAnnouncement(User p, Announcement annt, String newText, boolean remove) {
    throw new NotImplementedException();
  }

  public void editSemester(User p, Semester semester, boolean hidden) {
    boolean hiddenChange = semester.getHidden() != hidden;
    semester.setHidden(hidden);
    
    Log log = startLog(p);
    log.setLogName(Log.EDIT_SEMESTER);
    log.setLogType(Log.LOG_ADMIN);
    
    if (hiddenChange) {
      new LogDetail(log, "Semester " + semester.getName() + " was "
          + (hidden ? "" : "un") + "hidden.");
    }
  }

  public boolean setCurrentSemester(User p, Semester sem) {
    Semester oldSem = database.getCurrentSemester();
    if (oldSem != sem) {
      database.setCurrentSemester(sem);
      Log log = startLog(p);
      log.setLogName(Log.SET_CURRENT_SEMESTER);
      log.setLogType(Log.LOG_ADMIN);
      new LogDetail(log, "Current semester changed from " + oldSem.getName()
          + " to " + sem.getName());
    }
    
    return true;
  }

  public TransactionResult setExtension(User p, Group group, Date extension) {
    throw new NotImplementedException();
  }

  public boolean setFinalGrades(User p, Course course, Collection grades) {
    throw new NotImplementedException();
  }

  public boolean mergeGroups(User p, List groups, Assignment assignment) {
    throw new NotImplementedException();
  }

  public boolean disbandGroups(User p, List groups, Assignment assignment) {
    throw new NotImplementedException();
  }

  public void removeTimeSlots(User p, Assignment assignment, ArrayList toDelete) {
    throw new NotImplementedException();
  }

  public boolean createTimeSlots(User p, TimeSlot tsd, int multiplicity) {
    throw new NotImplementedException();
  }

  public TransactionResult editCourseDescription(User p, Course course, String newDescription) {
    throw new NotImplementedException();
  }

  public TransactionError submitFiles(User p, Assignment assignment, Collection files) {
    throw new NotImplementedException();
  }

  public TransactionError submitSurvey(User p, String assignmentid, List answers) {
    throw new NotImplementedException();
  }

  public TransactionResult setStaffPrefs(User p, Course course, Staff data) {
    // TODO: I think this is right...the TransactionManager does the updates.
    return new TransactionResult();
  }

  public TransactionResult addAllAssignsGrades(User p, Object data, Object data2) {
    throw new NotImplementedException();
  }

  public void computeAssignmentStats(User p, Assignment assign, Object object) {
    throw new NotImplementedException();
  }

  public void computeTotalScores(User p, Object data, Object object) {
    throw new NotImplementedException();
  }
  
  private Log startLog(User p) {
    Log log = new Log(database);
    log.setActingNetID(p.getNetID());
    if (p.isInStaffAsBlankMode()) {
//      log.setSimulatedNetID(p.getUserID());  // XXX
    }
    return log;
  }

}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
