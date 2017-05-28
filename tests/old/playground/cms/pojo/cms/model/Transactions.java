package cms.model;

import java.util.*;

import java.net.ConnectException;

import javax.servlet.http.HttpServletRequest;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import cms.www.AccessController;
import cms.www.TransactionError;
import cms.www.TransactionResult;
import cms.www.util.Emailer;
import cms.www.util.Util;
import cms.www.util.Profiler;

public class Transactions {
  private CMSRoot database;
  private Properties env = null;

  // This field is used for sending links in emails to users,
  // and telling clients which hostname to use in cross-site nav/overview
  // (links from there to here)
  // Example: "https://cms.csuglab.cornell.edu/web/auth/"
  private static String cmsServerAddress = "";

  public Transactions(CMSRoot database) {
    this.database = database;
    env = new Properties();
    env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
    env.put("java.naming.provider.url", "ldap://directory.cornell.edu");
  }
  
  public static String getCMSServerAddress() {
    return cmsServerAddress;
  }
  
  public static void refreshAddressFromRequest(HttpServletRequest req) {
    String address = req.getServerName();
    
    // prefer this to not be an IP address, but an IP is better than nothing
    if (!address.matches("[0-9\\.]*") || cmsServerAddress.equals(""))
            cmsServerAddress = req.getRequestURL().toString(); // contains protocol and path, but not query string
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

  public TransactionResult addStudentsToCourse(User p, Vector netIDs, Course course, boolean sendEmail) {
    Profiler.enterMethod("Transactions.addStudentsToCourse", "CourseID: " + course);
    TransactionResult result = new TransactionResult();
    try {
      /*
       * allAssigns holds all assignment for the given course, so we can add new students
       * to groups for all of them
       */
      Collection allAssigns = course.getAssignments();
      Map submissionCounts = new HashMap();
      for (Iterator i = allAssigns.iterator(); i.hasNext(); ) {
        Assignment a = (Assignment) i.next();
        submissionCounts.put(a, new Integer(a.getNumassignedfiles()));
      }
      Log log = startLog(p);
      log.setCourse(course);
      log.setLogName(Log.ADD_STUDENTS);
      log.setLogType(Log.LOG_COURSE);
      result = ensureUserExistence(netIDs, log);
      HashSet staffMems = new HashSet();
      Iterator classstaff = course.getStaff().iterator();
      while (classstaff.hasNext()) {
        Staff staff = (Staff) classstaff.next();
        staffMems.add(staff.getUser().getNetID());
      }
      //TODO: Look into why on the production version of CMS, this just returns null
      Collection graded = new Vector();
      for (int j=0; j < netIDs.size(); j++) {
        String netID = (String)netIDs.get(j);
        User studentUser = database.getUser(netID);
        if (staffMems.contains(netID)) {
            result.addError("Could not add '" + netID + "': " +
                        "Already a staff member for this course");
            continue;
        }
        Student student = null;
        try {
          student = course.getStudent(studentUser);
        } catch (Exception e) {}
        if (student == null) {
          student = new Student(course, studentUser);
          LogDetail l = new LogDetail(log, "Added " + netID + " as a student",
              studentUser);
          // Place new student in a group for all assignments
          Iterator i= allAssigns.iterator();
          while (i.hasNext()) {
            Assignment assign = (Assignment)i.next();
            Group g= new Group(assign, 
                ((Integer) submissionCounts.get(assign)).intValue());
            new GroupMember(g, student, GroupMember.ACTIVE);
          }
        
          if (sendEmail) sendAddedToCourseEmail(netID, course, log);
        
        } else {
          boolean isAlreadyEnrolled = student.getStatus().equals(Student.ENROLLED);
          if (isAlreadyEnrolled){
            result.addWarning(netID + " was not added because he is already " +
                        "enrolled in this course.");
          }
          else {
            new LogDetail(log, netID + " was reenrolled as a student", studentUser);
            student.setStatus(Student.ENROLLED);
            //Place student in a group for all assignments (s)he doesn't have records for
            Vector assignIDs = new Vector();
            
            throw new NotImplementedException("Not yet completed");
            
            /*
            Collection groupedAssigns = course.findGrouplessAssignments(courseID, netID);
            assignIDs = (Vector) allAssignIDs.clone();
            for (Iterator i= groupedAssigns.iterator(); i.hasNext(); ) {
                    assignIDs.remove(new Long(((AssignmentLocal) i.next()).getAssignmentID()));
            }
            for (int i=0; i < assignIDs.size(); i++) {
                Long assignID = (Long) assignIDs.get(i);
                GroupLocal g= database.groupHome().create(assignID.longValue(), ((Integer) submissionCounts.get(assignID)).intValue());
                database.groupMemberHome().create(g.getGroupID(), netID, GroupMemberBean.ACTIVE);
            }
            
            // check whether adding this student makes any group size exceedds limit
            Iterator groups = database.groupHome().findByNetIDCourseID(netID, courseID).iterator();
            while (groups.hasNext()) {
                    GroupLocal group = (GroupLocal)groups.next();
                    AssignmentLocal assign = database.assignmentHome().findByAssignmentID(group.getAssignmentID());
                    int currentGroupSize = TransactionHandler.getActiveGroupSize(group);
                    int maxGroupSize = assign.getGroupSizeMax();
                    if (currentGroupSize > maxGroupSize && maxGroupSize > 1)
                            result.addWarning("Adding student " + netID + " causes a group size in "
                                                                    + assign.getName() + " to overflow.");
                    
            }
            
            if (sendEmail) sendAddedToCourseEmail(netID, course, log);
            */
          }
        }
      }
      // Recompute assignment stats for any assignments which require it
      Iterator i = graded.iterator();
      while (i.hasNext()) {
        Assignment assign = (Assignment)i.next();
        computeAssignmentStats(p, assign, log);
        appendAssignment(log, assign);
      }
      computeTotalScores(p, course, log);
      Profiler.exitMethod("Transactions.addStudentsToCourse", "CourseID: " + 
          course);
      return result;
    }
    catch(Exception e) {
      e.printStackTrace();
      result.setException(e);
      result.getErrors().clear();
      result.addError("An unexpected error occurred, could not add students");
    }
    Profiler.exitMethod("Transactions.addStudentsToCourse", "CourseID: " +  course);
    return result;
  }

  /**
   * Make sure all users whose NetIDs are given exist in the CMS database. Do this
   * by querying both the database and the LDAP server if necessary.  Remove
   * any NetIDs from the input vector which cannot be added as a user (due to errors).
   * 
   * This function is not a transaction; it's called from within other functions
   * that are. That's why it doesn't roll back on an exception.
   * @param netIDs A Vector of Strings
   * @return A TransactionResult describing the result of the transaction
   *  
   */
  protected TransactionResult ensureUserExistence(Vector netIDs, Log log) {
    TransactionResult result = new TransactionResult();
    Collection LDAPInput = new ArrayList();
    Hashtable existingUsers = new Hashtable();
    try {
      for (int i = 0; i < netIDs.size(); i++) {
        String netID = (String)netIDs.get(i);
        User user = null;
        try {
          user = database.getUser(netID);
        } catch (Exception e) {}
        if (user == null) LDAPInput.add(netID);
        else if (user.getFirstName() == null || user.getLastName() == null || 
            user.getFirstName().equals("") || user.getLastName().equals("")) {
          LDAPInput.add(netID);
          existingUsers.put(netID, user);
        }
      }
      if (!LDAPInput.isEmpty()) {
        String[][] LDAPNames = null;
        try {
          result = getLDAPNames(LDAPInput);
          LDAPNames = (String[][]) result.getValue();
        } catch(NamingException e) {
          String names = "";
          netIDs.removeAll(LDAPInput);
          result.addError("Could not add " + 
              Util.listElements(LDAPInput) + ": " +
                        "Could not connect to LDAP");
          return result;
        }
        for (int i=0; i < LDAPNames.length; i++) {
          String netid = LDAPNames[i][0];
          String firstName = LDAPNames[i][1];
          String lastName = LDAPNames[i][2];
          if (firstName == null || lastName == null) {
              netIDs.remove(netid);
              continue;
          }
          if (existingUsers.containsKey(netid)) {
            User user = (User) existingUsers.get(netid);
            if ((user.getFirstName() == null || 
                user.getFirstName().equals("")) && 
                !firstName.equals(""))
                    user.setFirstName(firstName);
            if ((user.getLastName() == null || 
                user.getLastName().equals("")) && 
                !lastName.equals(""))
                    user.setLastName(lastName);
          } else {
            try {
              new User(database, netid, firstName, lastName, "TBD", "TBD");
              appendDetail(log, "Created new user (" + lastName + ", " +  
                  firstName + ") as " + netid);
            } catch(Exception e) {
              result.addError("Could not add " + netid + ": Unknown create error");
              netIDs.remove(netid);
            }
          }
        }
      }
    } catch(Exception e) {
        e.printStackTrace();
            result.setException(e);
            result.addError("Could not add users due to an unexpected error");
            netIDs = new Vector();
    }
    return result;
  }
  
  /* functions to add details to a log */
  
  private void appendAssignment(Log log, Assignment assignment) {
    LogDetail d = new LogDetail(log);
    d.setAssignment(assignment);
  }
  
  public static void appendDetail(Log log, String detail) {
    new LogDetail(log, detail);
  }
  
  public static void appendDetail(Log log, String detail, User affected) {
    new LogDetail(log, detail, affected);
  }
        
  /**
   * Get and increment the course-specific file counter
   * @ejb.interface-method view-type="local"
   * @ejb.transaction type="Required"
   */
  public long getCourseFileCounter(Course course) {
    throw new NotImplementedException();
  }
  
  /**
   * Get and increment the group-specific file counter
   * @param groupID The GroupID of the group
   * @return 
   * @ejb.interface-method view-type="local"
   * @ejb.transaction type="Required"
   */
  public int getGroupFileCounter(long groupID) {
    throw new NotImplementedException();
  }
  
  /**
   * Auxiliary to ensureUserExistence():
   * Query the LDAP server to find the first and last name associated with the given netID
   * @param netids        The NetIDs of the users to find information on
   * @return      Returns a TransactionResult containing any errors which occurred,
   *      and has as it's value a two dimensional string array containing:
   *                              result[i][0] = the ith NetID in netids (This entry is null if an error occurred at this row)
   *                              result[i][1] = the first name given by LDAP for the ith netid
   *                              result[i][2] = the last name given by LDAP for the ith netid
   *                              First and last name are in the form 
   *                                      [Capital letter][all lowercase letters]
   */
  public TransactionResult getLDAPNames(Collection netids) throws NamingException {
    TransactionResult result = new TransactionResult();
    String[][] value = new String[netids.size()][3];
    DirContext ctx = new InitialDirContext(env);
    Iterator i = netids.iterator();
    int count = 0;
    while (i.hasNext()) {
      value[count][0] = (String) i.next();
      try {
        Attributes attrs = ctx.getAttributes("uid=" + value[count][0] + ", ou=People, " + 
        "o=Cornell University, c=US");
        if (attrs.getAll() != null) {
            try {
                value[count][1] = (String)attrs.get("givenName").get();
            } catch (NullPointerException e) {}
            try {
                value[count][2] = (String)attrs.get("sn").get();
            } catch (NullPointerException e) {}
        }
        if (value[count][1] == null) value[count][1] = "";
        if (value[count][2] == null) value[count][2] = "";
        
      } catch (NameNotFoundException e) {
              result.addError("Could not add '" + value[count][0] + "': " +
                        "Not a registered Cornell NetID");
              value[count][1] = null;
              value[count][2] = null;
      }
      // Correct capitalization:
      for (int j= 1; j != 3; j++) {
        if (value[count][j] == null) continue;
        int length= value[count][j].length();
        if (length > 0) 
            value[count][j]= Character.toUpperCase(value[count][j].charAt(0)) + 
                    value[count][j].substring(1, length).toLowerCase();
      }
      count++;
    }
    ctx.close();
    result.setValue(value);
    return result;
  }

  public boolean inviteUser(User inviter, User invited, Group group) {
    boolean success = false;
    Assignment assignment = group.getAssignment();
    GroupMember member = null;
    Student student = assignment.getCourse().getStudent(invited);
    if (student == null) 
      return false; //Not a student in the class
    
    try {
      Collection groupMembers = group.getMembers();
      Iterator i = groupMembers.iterator();
      SortedSet groupMembersStr = new TreeSet();
      while (i.hasNext()) {
        GroupMember m = (GroupMember) i.next();
        groupMembersStr.add(m);
        if (m.getStudent().getUser().getNetID() == invited.getNetID())
          member = m;
      }
      boolean noInvite = false;
      if (member == null) {
        member = new GroupMember(group, student, "Invited");
      }
      else if (!member.getStatus().equalsIgnoreCase("active")) {
        member.setStatus("Invited");
      } else {
        noInvite = true;
      }
      if (!noInvite) {
        Log log = startLog(inviter);
        log.setCourse(group.getAssignment().getCourse());
        log.setLogName(Log.CREATE_INVITATION);
        log.setLogType(Log.LOG_GROUP);
        if (student.getEmailGroup()) {
            sendInvitationEmail(invited, groupMembersStr, assignment, log);
        }
        appendAssignment(log, assignment);
        new LogDetail(log, invited.getNetID());
        appendDetail(log, invited + " was invited to join the group of " + Util.listElements(groupMembersStr) +  " for '" + assignment.getName() + "'");
      }
      success = true;
    }
    catch (Exception e) {
      success = false;
    }
    return success;
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
    TransactionResult result = new TransactionResult();
    try {
        assignment.setHidden(true);
        Log log = startLog(p);
        log.setCourse(assignment.getCourse());
        appendAssignment(log, assignment);
        log.setLogName(Log.REMOVE_ASSIGNMENT);
        log.setLogType(Log.LOG_COURSE);
        appendDetail(log, "'" + assignment.getName() + 
            "' was removed from the course");
        computeTotalScores(p, assignment.getCourse(), log);
    } catch (Exception e) {
        e.printStackTrace();
                result.setException(e);
        result.addError("Unexpected error, course not remove assignment");
    }
    return result;
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
  
  public void sendAddedToCourseEmail(String netID, Course course, Log log) {
    try {
      Emailer email = new Emailer();
      email.setFrom("CMS _do not reply_ " + "<cms-devnull@csuglab.cornell.edu>");
      email.setSubject("[" + course.getCode() + "]" + " - You Have Been Added to " + course.getName());
      String msg = "You have been added to the CMS roster for the course " + course.getName() + ".\n\n" +
                      "By default, you will not receive any further e-mails about this course.  To change preferences about e-mail notifications, such as when grades are released or groups are changed, navigate to the following page:\n" +
                      "   " + getCMSServerAddress() + "?" + AccessController.P_ACTION + "=" + AccessController.ACT_STUDENTPREFS + "&" + AccessController.P_COURSEID + "=" + course +
                      "\n\n\n" +
                      "-----------------------------------\n" +
                      "This message was auto-generated by Cornell CMS.  Do not reply to this message directly.  ";
      
      email.setMessage(msg);
      email.addTo(netID + "@cornell.edu");
      if (!email.sendEmail()) throw new ConnectException("");
      appendDetail(log, "Sent Added-to-Course Email to " + netID);
    } catch (ConnectException e) {
      appendDetail(log, "Failed to create a connection for sending email.  " +
                "Could not send Added-to-Course email to " + netID + ".");              
    }
  }

  public void sendInvitationEmail(User invited, Collection groupMems, 
    Assignment assign, Log log) {
    Course course = assign.getCourse();
    try {
      Emailer email = new Emailer();
      email.setFrom("CMS _do not reply_ " + "<cms-devnull@csuglab.cornell.edu>");
      email.setSubject("[" + course.getCode() + "]" + " - You Received an Invitation for " + assign.getName());
      String msg = "You have been invited to join the group of " + Util.listElements(groupMems) + " for " + assign.getName() + ".\n\n";
      msg += "To view this assignment, navigate to the following page:\n";
      msg += "   " + getCMSServerAddress() + "?" + AccessController.P_ACTION + "=" + AccessController.ACT_ASSIGN + "&" + AccessController.P_ASSIGNID + "=" + assign;
      email.setMessage(Emailer.appendEmailFooter(msg, course.getCode()));
      email.addTo(invited + "@cornell.edu");
      if (!email.sendEmail()) throw new ConnectException("");
        appendDetail(log, "Sent New Invitation Email to " + invited);
      } catch (ConnectException e) {
        appendDetail(log, "Failed to create a connection for sending email.  Could not send automatic New Invitation email to " + invited + ".");           
      }
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

  public boolean dropStudents(User p, Collection netIDs, Course course) {
    try {
      Iterator i = netIDs.iterator();
      Log log = startLog(p);
      log.setCourse(course);
      log.setLogName(Log.DROP_STUDENTS);
      log.setLogType(Log.LOG_COURSE);
      Collection graded = course.getAssignments();
      SortedSet affectedNetIDs = new TreeSet();
      while (i.hasNext()) {
        String netID = (String) i.next();
        Student student = course.getStudent(database.getUser(netID));
        if (student == null) {
            return false;
        } else {
            student.setStatus(Student.DROPPED);
            appendDetail(log, netID + " was dropped from the course");
                    affectedNetIDs.add(netID);
        }
      }
      i = graded.iterator();
      while (i.hasNext()) {
        Assignment assign = (Assignment) i.next();
        computeAssignmentStats(p, assign, log);
        appendAssignment(log, assign);
      }
      computeTotalScores(p, course, log);
      return true;
    } catch (Exception e) {
      return false;
    }
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
    try {
      for (Iterator i = grades.iterator(); i.hasNext();) {
        String[] gradePair = (String[])i.next();
        User u = database.getUser(gradePair[0]);
        String grade = gradePair[1];
        
        Student s = course.getStudent(u);
        s.setFinalGrade(grade);
      }
      return true;
    } catch(Exception ex) {
      return false;
    }
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

  public void computeAssignmentStats(User p, Assignment assignment, Log log) {
    Profiler.enterMethod("Transactions.computeAssignmentStats", "AssignmentID: " + assignment);
    try {
    if (log == null) {
      log = startLog(p);
      log.setLogName(Log.COMPUTE_ASSIGNMENT_STATS);
      log.setLogType(Log.LOG_COURSE);
      log.setCourse(assignment.getCourse());
      appendAssignment(log, assignment);
    }
    // New grade entries made here are attributed to the system
    String grader = "CMS";
    Collection subProblems = assignment.getSubProblems();
    boolean hasSubProblems = subProblems.size() > 0;
    Collection students = assignment.getCourse().getStudents();
    Collection groups = assignment.getGroups();
    /* Key: NetID -> Value: [TotalGrade, PreviousGrade]
     * String -> Float[2] */
    Hashtable studentSums = new Hashtable();
    /* Key: "<GroupID>_<SubProblemID>"
     * Value: [Total (Float), EntryNum (Integer), Averaged (Boolean), LastEntry (Float)]
     * String -> Object[4] */
    Hashtable groupSums = new Hashtable();
    Iterator i;
    
    Map groupsMap = new HashMap();
    i = groups.iterator();
    while(i.hasNext()) {
      Group g = (Group)i.next();
      for (Iterator mi = g.getMembers().iterator(); mi.hasNext();)
        groupsMap.put(((GroupMember)mi.next()).getStudent().getUser(), g);
    }
    
    
    // Find all grades for students in this assignment and add them to the individual sums
    Collection grades = assignment.getGrades();
    if (grades.size() == 0) {
      Profiler.exitMethod("Transactions.computeAssignmentStats", "Assignment: " 
          + assignment + " - (early exit)");
      return;
    }
    i = grades.iterator();
    while (i.hasNext()) {
      Grade grade = (Grade) i.next();
      float score = grade.getGrade().floatValue();
      // [sum of current scores (null if no scores), old total score (null if none)]
      Float[] sum = (Float[]) studentSums.get(grade.getUser());
      if (!(grade.getSubProblem() == null && hasSubProblems)) {
        if (sum != null) {
          sum[0] = sum[0] == null ? new Float(score) : 
            new Float(sum[0].floatValue() + score);
        } else {
          sum = new Float[2];
          sum[0] = new Float(score);
          sum[1] = null;
        }
      } else {
        if (sum != null) {
            sum[1] = new Float(score);
        } else {
            sum = new Float[2];
            sum[0] = null;
            sum[1] = new Float(score);
        }
      }
      studentSums.put(grade.getUser(), sum);
      if (students.contains(assignment.getCourse().getStudent(grade.getUser())) 
          && grade.getSubProblem() != null) {
          Group group = (Group)groupsMap.get(grade.getUser());
          Object[] groupSum = (Object[]) groupSums.get(group.toString() + "_" + 
              grade.getSubProblem().toString());
          if (groupSum != null) {
            Float total = (Float) groupSum[0];
            Integer count = (Integer) groupSum[1];
            Boolean averaged = (Boolean) groupSum[2];
            Float last = (Float) groupSum[3];
            groupSum[0] = new Float(total.floatValue() + score);
            groupSum[1] = new Integer(count.intValue() + 1);
            if (!averaged.booleanValue() && !last.equals(new Float(score))) {
                    groupSum[2] = new Boolean(true);
            }
          } else {
            groupSum = new Object[4];
            groupSum[0] = new Float(score);
            groupSum[1] = new Integer(1);
            groupSum[2] = new Boolean(false);
            groupSum[3] = new Float(score);
          }
          groupSums.put(group.toString() + "_" + grade.getSubProblem().toString(), 
              groupSum);
      }
    }
    // Enter newly summed total grades into the database and compute the mean score
    i = students.iterator();
    float total = 0;
    ArrayList scores = new ArrayList();
    /* This loop finds a total of all assignment grades (for getting the mean), 
     *  and creates a grade entry for the student's total assignment grade 
     *  if necessary. */
    while (i.hasNext()) {
      Student student = (Student) i.next();
      Float[] sums = (Float[]) studentSums.get(student.getUser());
      if (sums != null) {
        Group g = (Group)groupsMap.get(student.getUser());
        if (sums[0] != null) {
          // Insert recently calculated sums into the group grade sums
          Object[] groupSum = (Object[]) groupSums.get(g.toString() + "_0");
          if (groupSum != null) {
            Float sumtotal = (Float) groupSum[0];
            Integer count = (Integer) groupSum[1];
            Boolean averaged = (Boolean) groupSum[2];
            Float last = (Float) groupSum[3];
            groupSum[0] = new Float(sumtotal.floatValue() + sums[0].floatValue());
            groupSum[1] = new Integer(count.intValue() + 1);
            if (!averaged.booleanValue() && !last.equals(new Float(sums[0].floatValue()))) {
                    groupSum[2] = new Boolean(true);
            }
          } else {
            groupSum = new Object[4];
            groupSum[0] = new Float(sums[0].floatValue());
            groupSum[1] = new Integer(1);
            groupSum[2] = new Boolean(false);
            groupSum[3] = new Float(sums[0].floatValue());
          }
          groupSums.put(g.toString() + "_0", groupSum);
          scores.add(new Float(sums[0].floatValue()));
          total += sums[0].floatValue();
        }
        if (hasSubProblems && (sums[0] == null || sums[1] == null || !sums[0].equals(sums[1]))) {
          assignment.addGrade(g, null, sums[0], student.getUser(), p);
          float diff = ((sums[0] == null || sums[1] == null) ? 0 : sums[0].floatValue() - sums[1].floatValue());
          LogDetail d = new LogDetail(log, "Grade for " + student.getUser().getNetID() 
              + " on " + assignment.getName() + 
                  (sums[0] == null ? " erased" : " calculated to be " + 
                      sums[0].floatValue() + (sums[1] == null ? "" : (" (" + 
                          (diff > 0 ? "+" : "") + diff + ")"))),
                          student.getUser(), assignment);
        }
      }
    }
    Float mean, median, max = null, stdev;
    if (scores.size() > 0) {
      mean = new Float(total / (float) scores.size());
      float sstotal = 0;
      i = scores.iterator();
      float val;
      /* This loop calculates the sum of squared differences using the previously
       * computed mean value (for finding the standard deviation) */
      while (i.hasNext()) {
              Float score = (Float) i.next();
              if (max == null || score.floatValue() > max.floatValue()) {
                      max = score;
              }
              val = (score.floatValue() - mean.floatValue());
              sstotal += (val * val);
      }
      stdev = new Float((float) Math.sqrt(sstotal / scores.size()));
      // Find median
      median = new Float(0);
      Object[] sortedScores = scores.toArray();
      Arrays.sort(sortedScores);
      if (sortedScores.length % 2 == 0) {
              median = new Float(sortedScores.length == 0 ? 0 : (((Float) sortedScores[sortedScores.length / 2 - 1]).floatValue() 
                              + ((Float) sortedScores[sortedScores.length / 2]).floatValue()) / 2.0f);
      } else /* Odd */ {
              median = new Float(((Float) sortedScores[(sortedScores.length - 1) / 2]).floatValue());
      }
    } else {
      mean = null;
      median = null;
      stdev = null;
    }
    /* XXX TODO: GROUP GRADES
    // First we modify all the previously EXISTING group grades
    i = assignment.getgr
    while (i.hasNext()) {
            GroupGradeLocal groupGrade = (GroupGradeLocal) i.next();
            String key = groupGrade.getGroupID() + "_" + groupGrade.getSubProblemID();
            Object[] groupSum = (Object[]) groupSums.get(key);
            if (groupSum != null) {
                    Float sum = (Float) groupSum[0];
                    Integer count = (Integer) groupSum[1];
                    Boolean averaged = (Boolean) groupSum[2];
                    groupGrade.setScore(sum.floatValue() / count.floatValue());
                    groupGrade.setIsAveraged(averaged.booleanValue());
                    groupSums.remove(key);
            } else {
                    ggHome.remove(groupGrade.getPrimaryKey());
            }
    }
    // Then for any left over (NON-EXISTING) group grades, we create new database entries
    Enumeration e = groupSums.keys();
    while (e.hasMoreElements()) {
            String k = (String) e.nextElement();
            String[] ks = k.split("_");
            long groupID = Long.parseLong(ks[0]);
            long subProblemID = Long.parseLong(ks[1]);
            Object[] groupSum = (Object[]) groupSums.get(k);
            Float sum = (Float) groupSum[0];
            Integer count = (Integer) groupSum[1];
            Boolean averaged = (Boolean) groupSum[2];
            ggHome.create(groupID, sum.floatValue() / count.floatValue(),
                            averaged.booleanValue(), subProblemID);
    }
    */
    assignment.setMax(max);
    assignment.setMean(mean);
    assignment.setStdDev(stdev);
    assignment.setMedian(median);
    Profiler.exitMethod("Transactions.computeAssignmentStats", "Assignment: " + 
        assignment);
    } catch(Exception x) {
      x.printStackTrace();
      Profiler.exitMethod("Transactions.computeAssignmentStats", "Assignment: " 
          + assignment + " - (crash)");
    }
  }

  public void computeTotalScores(User p, Course course, Log log) {
    Profiler.enterMethod("Transactions.computeTotalScores", "CourseID: " + 
        course);
    try {
      boolean commitLog = false;
      if (log == null) {
        log = startLog(p);
        commitLog = true;
        log.setLogName(Log.COMPUTE_TOTAL_SCORES);
        log.setLogType(Log.LOG_COURSE);
        log.setCourse(course);
      }
      Collection as = course.getAssignments();
      Collection ss = course.getStudents();
      Hashtable assignWeights = new Hashtable(), maxScores = new Hashtable();
      Iterator grades = null;
      Iterator assigns = as.iterator();
      Iterator students = ss.iterator();
      Hashtable totalScores = new Hashtable();
      float maxTotalScore = 0;
      while (assigns.hasNext()) {
        Assignment assign = (Assignment) assigns.next();
        assignWeights.put(assign, new Float(assign.getWeight()));
        maxScores.put(assign, new Float(assign.getMaxScore()));
        maxTotalScore += assign.getWeight();
        
        grades = assign.getGrades().iterator();
        while (grades.hasNext()) {
          Grade grade = (Grade) grades.next();
          if (grade.getSubProblem() == null && assign.hasSubProblems()) continue;
          Float score = (Float) totalScores.get(grade.getUser());
          Float w = (Float) assignWeights.get(grade.getAssignment());
          Float m = (Float) maxScores.get(grade.getAssignment());
          if (w == null || m == null) continue;
          if (score == null) {
              score = new Float((grade.getGrade().floatValue() / m.floatValue()) * w.floatValue());
          } else {
              score = new Float((grade.getGrade().floatValue() / m.floatValue()) * w.floatValue() + score.floatValue());
          }
          totalScores.put(grade.getUser(), score);
        }
      }
     
      float highTotalScore = 0;
      float[] scoresArray = new float[ss.size()];
      int i = 0;
      while (students.hasNext()) {
        Student student = (Student) students.next();
        Float score = (Float) totalScores.get(student.getUser());
        if (score != null) {
          if (score.floatValue() > highTotalScore) {
                  highTotalScore = score.floatValue();
          }
          scoresArray[i++] = score.floatValue();
          if (!Util.equalNull(student.getTotalScore(), score)) {
            boolean isnew = student.getTotalScore() == null;
            float diff = isnew ? 0 : score.floatValue() - student.getTotalScore().floatValue();
            student.setTotalScore(score);
            appendDetail(log, "Total grade for " + student.getUser().getNetID()
                + (isnew ? " " : " re") + "calculated to be " + score.floatValue() +
                    (isnew ? "" : ((diff > 0 ? " (+" : " (") + diff + ")")));
          }
        } else {
          if (student.getTotalScore() != null) {
                  student.setTotalScore(null); // score null, so total score should be null
          }
        }
      }
      float[] sortedScores;
      if (i < ss.size()) {
        sortedScores = new float[i];
        System.arraycopy(scoresArray, 0, sortedScores, 0, i);
        Arrays.sort(sortedScores);
      } else {
        Arrays.sort(scoresArray);
        sortedScores = scoresArray;
      }
      float meanTotalScore = 0;
      for (int j = 0; j < sortedScores.length; j++) {
        meanTotalScore += sortedScores[j];
      }
      meanTotalScore /= sortedScores.length;
      float stDevTotalScore = 0;
      for (int j = 0; j < sortedScores.length; j++) {
        float diff = sortedScores[j] - meanTotalScore;
        stDevTotalScore += diff * diff;
      }
      stDevTotalScore = (float) Math.sqrt(stDevTotalScore / sortedScores.length);
      float medianTotalScore;
      if (sortedScores.length % 2 == 0) {
              medianTotalScore = sortedScores.length == 0 ? 0 : ((sortedScores[sortedScores.length / 2 - 1]
                              + sortedScores[sortedScores.length / 2]) / 2.0f);
      } else /* Odd */ {
              medianTotalScore = sortedScores[(sortedScores.length - 1) / 2];
      }
      if (as.size() == 0) course.setMaxTotalScore(null);
      else course.setMaxTotalScore(new Float(maxTotalScore));
      if (as.size() == 0 || ss.size() == 0) {
              course.setHighTotalScore(null);
              course.setMeanTotalScore(null);
              course.setMedianTotalScore(null);
              course.setStDevTotalScore(null);
      } else {
              course.setHighTotalScore(new Float(highTotalScore));
              course.setMeanTotalScore(new Float(meanTotalScore));
              course.setMedianTotalScore(new Float(medianTotalScore));
              course.setStDevTotalScore(new Float(stDevTotalScore));
      }
      Profiler.exitMethod("Transactions.computeTotalScores", "CourseID: " 
          + course);
    } catch (Exception e) {
        e.printStackTrace();
        Profiler.exitMethod("Transactions.computeTotalScores", "CourseID: " 
            + course + " - (crash)");
    }
  }
  
  public Log startLog(User p) {
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
