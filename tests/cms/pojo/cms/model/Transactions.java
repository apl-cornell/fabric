package cms.model;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import cms.www.AccessController;
import cms.www.TransactionError;
import cms.www.TransactionResult;
import cms.www.util.Emailer;
import cms.www.util.Util;

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
  
  public TransactionResult setAllCourseProperties(User p, Course course,
      HttpServletRequest request, TransactionResult result) {
    // XXX This method was moved out of transaction handler because it itself is
    // a transaction.  -- MJL
    
    Log log = startLog(p);
    log.setCourse(course);
    log.setLogName(Log.EDIT_COURSE_PROPS);
    log.setLogType(Log.LOG_COURSE);
    
    Map map = request.getParameterMap();
    
    // Set course general properties.
    String name = request.getParameter(AccessController.P_NAME);
    if (!Util.equalNull(course.getName(), name)) {
      new LogDetail(log, "Course '" + course.getName() + "' renamed to '" + name + "'");
      course.setName(name);
    }
    
    if (p.isCMSAdmin()) {
      String code = request.getParameter(AccessController.P_CODE);
      if (!Util.equalNull(course.getCode(), code)) {
        new LogDetail(log, "Course Code changed to: " + code);
        course.setCode(code);
      }
    }

    String displayedCode = request
        .getParameter(AccessController.P_DISPLAYEDCODE);
    if (!Util.equalNull(course.getDisplayedCode(), displayedCode)) {
      new LogDetail(log, "Displayed Course Code changed to: " + displayedCode);
      course.setDisplayedCode(displayedCode);
    }
    
    boolean hasSection = map.containsKey(AccessController.P_HASSECTION);
    if (course.getHasSection() != hasSection) {
      new LogDetail(log, "Section is now "
          + (hasSection ? "enabled" : "disabled"));
      course.setHasSection(hasSection);
    }
    
    boolean showGraderNetID = map.containsKey(AccessController.P_SHOWGRADERID);
    if (course.getShowGraderNetID() != showGraderNetID) {
      new LogDetail(log, "Grader NetIDs are now "
          + (showGraderNetID ? "shown to" : "hidden from") + " students");
      course.setShowGraderNetID(showGraderNetID);
    }
    
    boolean freezeCourse = map.containsKey(AccessController.P_FREEZECOURSE);
    if (course.getFreezeCourse() != freezeCourse) {
      new LogDetail(log, "Course is now " + (freezeCourse ? "" : "un")
          + "frozen");
      course.setFreezeCourse(freezeCourse);
    }
    
    boolean showFinalGrade = map.containsKey(AccessController.P_FINALGRADES);
    if (course.getShowFinalGrade() != showFinalGrade) {
      new LogDetail(log, "Final grades are now "
          + (showFinalGrade ? "shown to" : "hidden from") + " students");
      course.setShowFinalGrade(showFinalGrade);
    }
    
    boolean showTotalScores =
        map.containsKey(AccessController.P_SHOWTOTALSCORES);
    if (course.getShowTotalScores() != showTotalScores) {
      new LogDetail(log, "Total scores are now "
          + (showTotalScores ? "shown to" : "hidden from") + " students");
      course.setShowTotalScores(showTotalScores);
    }
    
    boolean showAssignWeights =
        map.containsKey(AccessController.P_SHOWASSIGNWEIGHTS);
    if (course.getShowAssignWeights() != showAssignWeights) {
      new LogDetail(log, "Assignment weights are now "
          + (showAssignWeights ? "shown to" : "hidden from") + " students");
      course.setShowAssignWeights(showAssignWeights);
    }
    
    boolean announceGuestAccess =
        map
        .containsKey(AccessController.P_ANNOUNCEGUESTACCESS);
    if (course.getAnnounceGuestAccess() != announceGuestAccess) {
      new LogDetail(log, "Guest access to announcements is now "
          + (announceGuestAccess ? "" : "dis") + "allowed");
      course.setAnnounceGuestAccess(announceGuestAccess);
    }
    
    boolean assignGuestAccess =
        map.containsKey(AccessController.P_ASSIGNGUESTACCESS);
    if (course.getAssignGuestAccess() != assignGuestAccess) {
      new LogDetail(log, "Guest access to assignments is now "
          + (assignGuestAccess ? "" : "dis") + "allowed");
      course.setAssignGuestAccess(assignGuestAccess);
    }
    
    boolean solutionGuestAccess =
        map.containsKey(AccessController.P_SOLUTIONGUESTACCESS);
    if (course.getSolutionGuestAccess() != solutionGuestAccess) {
      new LogDetail(log, "Guest access to solution files is now "
          + (solutionGuestAccess ? "" : "dis") + "allowed");
      course.setSolutionGuestAccess(solutionGuestAccess);
    }
    
    boolean courseGuestAccess =
        map.containsKey(AccessController.P_COURSEGUESTACCESS);
    if (course.getCourseGuestAccess() != courseGuestAccess) {
      new LogDetail(log, "Guest access to course is now "
          + (courseGuestAccess ? "" : "dis") + "allowed");
      course.setCourseGuestAccess(courseGuestAccess);
    }
    
    boolean announceCCAccess =
        map
        .containsKey(AccessController.P_ANNOUNCECCACCESS);
    if (course.getAnnounceCCAccess() != announceCCAccess) {
      new LogDetail(log, "Cornell community access to announcements is now "
          + (announceCCAccess ? "" : "dis") + "allowed");
      course.setAnnounceCCAccess(announceCCAccess);
    }
    
    boolean assignCCAccess =
        map.containsKey(AccessController.P_ASSIGNCCACCESS);
    if (course.getAssignCCAccess() != assignCCAccess) {
      new LogDetail(log, "Cornell community access to assignments is now "
          + (assignCCAccess ? "" : "dis") + "allowed");
      course.setAssignCCAccess(assignCCAccess);
    }
    
    boolean solutionCCAccess =
        map.containsKey(AccessController.P_SOLUTIONCCACCESS);
    if (course.getSolutionCCAccess() != solutionCCAccess) {
      new LogDetail(log, "Cornell community access to solution files is now "
          + (solutionCCAccess ? "" : "dis") + "allowed");
      course.setSolutionCCAccess(solutionCCAccess);
    }
    
    boolean courseCCAccess =
        map.containsKey(AccessController.P_COURSECCACCESS);
    if (course.getCourseCCAccess() != courseCCAccess) {
      new LogDetail(log, "Cornell community access to course is now "
          + (courseCCAccess ? "" : "dis") + "allowed");
      course.setCourseCCAccess(courseCCAccess);
    }
    
    boolean hasAdmin = false;

    // Remove current staff members and set remaining staff member permissions.
    Iterator iter = course.getStaff().iterator();
    while (iter.hasNext()) {
      Staff staff = ((Staff) iter.next());
      if (map.containsKey(staff.getUser().getNetID()
          + AccessController.P_REMOVE)) {
        if (staff.getUser().equals(p)) {
          result.addError("Cannot remove yourself as a staff member");
          continue;
        }
        
        // Remove the staff member.
        staff.setStatus(log, Staff.INACTIVE);
        
        continue;
      }
      
      // set staff permissions
      String netID = staff.getUser().getNetID();
      staff.setAdminPriv(log, map.containsKey(netID + AccessController.P_ISADMIN));
      staff.setAssignmentsPriv(log, map.containsKey(netID
          + AccessController.P_ISASSIGN));
      staff.setGroupsPriv(log, map.containsKey(netID + AccessController.P_ISGROUPS));
      staff.setGradesPriv(log, map.containsKey(netID + AccessController.P_ISGRADES));
      staff.setCategoryPriv(log, map.containsKey(netID
          + AccessController.P_ISCATEGORY));

      hasAdmin = hasAdmin || staff.getAdminPriv();
    }
    
    // add new staff
    int i = 0;
    for (String newnetid = request.getParameter(AccessController.P_NEWNETID + i);
        newnetid != null;
        newnetid = request.getParameter(AccessController.P_NEWNETID + (++i))) {
      User newUser = database.getUser(newnetid);
      if (newUser.studentIndex.containsKey(course)) {
        result.addError("Could not add " + newnetid
            + ": Already enrolled as a student");
        
        continue;
      }
      
      Staff newStaff = (Staff) newUser.staffIndex.get(course);
      if (newStaff == null) {
        newStaff = new Staff(log, newUser, course);
      } else {
        newStaff.setStatus(log, Staff.ACTIVE);
      }

      newStaff.setAdminPriv(log, map.containsKey(AccessController.P_NEWADMIN
          + i));
      newStaff.setAssignmentsPriv(log, map
          .containsKey(AccessController.P_NEWASSIGN + i));
      newStaff.setGroupsPriv(log, map.containsKey(AccessController.P_NEWGROUPS
          + i));
      newStaff.setGradesPriv(log, map.containsKey(AccessController.P_NEWGRADES
          + i));
      newStaff.setCategoryPriv(log, map
          .containsKey(AccessController.P_NEWCATEGORY + i));

      hasAdmin = hasAdmin || newStaff.getAdminPriv();
    }

    if (!hasAdmin) {
      result
          .addError("Must have at least one staff member with admin privilege");
    }

    if (result.hasErrors()) {
      // Abort.
      return result;
    }

    return result;
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
