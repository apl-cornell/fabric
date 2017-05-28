package cms.model;

import java.net.InetAddress;
import java.util.*;

/**
 * A Log is a high-level log in a two-level logging system. Log objects handle
 * logs that represent entire transaction action, e.g. "Assignment created for
 * course Foo".  LogDetail objects handle low-level logs that represent
 * individual database changes, such as "Assignment status set to Hidden". Both
 * are used for every transaction for which they make sense.
 *
 * Each Log consists of an entry in the tLogs table with the basic log information.
 * Each Log is also associated with a collection of LogDetails in the
 * tLogDetails table which can contain Details which describe exactly what
 * changes happend during the transaction, NetIDs which have been affected by
 * this log's action, and AssignmentIDs which this action affected.
 *
 * To create a log entry (In TransactionsBean):
 *  1. A new log is created from a LogData object.  A new LogData object
 *     should be created using the startLog(Principal p) method in TransactionsBean,
 *     which will add information from the Principal to the LogData.
 *  2. LogDetails are added to the LogData as necessary using the methods
 *     appendDetail, appendAssignment, appendReceiver in TransactionsBean.
 *     Do not use the recNetIDs or assignmentIDs collections in the LogData to
 *     add receiving NetIDs or AssignmentIDs to the log.  Instead use appendReceiver
 *     or appendAssignment.
 *  3. Upon being loaded, LogDetails with null detail strings will not be added
 *     as LogDetails, but will instead be interpreted as a receiving NetID or an
 *     AssignmentID and added to the proper set.
 *
 * Notes:
 *  - Any LogID entries in the LogData used for creation will be ignored
 *    (The LogID is generated when the log is entered into the database)
 *  - Logs are read-only.  None of the setters are hooked up to make changes
 *    to the database, so create-time is the only time to affect the log entry.
 *
 * @see LogDetail
 * @see LogSearchParams
 **/
public class Log {

  //legal high-level log types (| these to combine when searching)
  public static final int
    LOG_ADMIN  = 1,
    LOG_COURSE = 2,
    LOG_GROUP  = 4,
    LOG_GRADE  = 8,
    LOG_ALL    = 31,
    //this one is meant for use only by finder functions in LogDAOImpl
    MAX_INDIVIDUAL_LOG_TYPE = 16;

  public static final String
    // LOG TYPES (string format)
    ADMIN = "Admin",
    COURSE = "Course",
    GROUP = "Group",
    GRADE = "Grade",
    CATEGORY = "Content",
    // LEGAL LOG NAMES (corresponding to database transactions)
    // Admin Type
    ADD_CMS_ADMIN = "Added CMS Admin",
    CREATE_COURSE = "Created New Course",
    CREATE_SEMESTER = "Created New Semester",
    EDIT_SEMESTER = "Edited Semester",
    REMOVE_CMS_ADMIN = "Removed CMS Admin",
    SET_CUIDS = "Set User CUIDs",
    SET_CURRENT_SEMESTER = "Set Current Semester",
    SET_USERNAMES = "Set User Names",
    UPLOAD_STUDENT_INFO = "Uploaded Student Information", //can also be of type COURSE
    // Course Type
    ADD_STUDENTS = "Added Students",
    CHANGE_GROUP_TIMESLOT = "Changed Group Timeslot",
    COMPUTE_ASSIGNMENT_STATS = "Computed Assignment Stats",
    COMPUTE_TOTAL_SCORES = "Computed Total Scores",
    CREATE_ANNOUNCEMENT = "Posted New Announcement",
    CREATE_ASSIGNMENT = "Created New Assignment",
    CREATE_TIMESLOTS = "Created New Timeslots",
    DROP_STUDENTS = "Dropped Students",
    EDIT_ANNOUNCEMENT = "Edited Announcement",
    EDIT_ASSIGNMENT = "Edited Assignment",
    EDIT_COURSE_PROPS = "Edited Course Properties",
    EDIT_STAFF_PREFS = "Edited Staff Preferences",
    EDIT_STUDENT_PREFS = "Edited Student Preferences",
    REMOVE_ASSIGNMENT = "Removed Assignment",
    REMOVE_TIMESLOTS = "Removed Timeslots",
    RESTORE_ANNOUNCEMENT = "Restored Announcement",
    RESTORE_ASSIGNMENT = "Restored Assignment",
    SEND_EMAIL = "Sent Course Email",
    UPLOAD_CLASSLIST = "Uploaded Classlist",
    // Category Type
    CREATE_CATEGORY = "Create New Content",
    EDIT_CATEGORY = "Edited Content",
    ADDNEDIT_CONTENTS = "Added/Edited Content Data",
    ORDER_CATEGORIES = "Reordered Content",
    REMOVE_CATEGORY = "Removed Content",
    REMOVE_CATEGORY_ROW = "Removed Content Row",
    // Grade Type
    ASSIGN_GRADER = "Assigned Grader",
    DOWNLOAD_ASSIGNMENT_GRADES = "Downloaded Assignment Grades",
    EDIT_GRADES = "Edited Grades/Comments",
    EDIT_FINAL_GRADES = "Edited Final Grades",
    UPLOAD_FINAL_GRADES = "Uploaded Final Grades File",
    UPLOAD_GRADES_FILE = "Uploaded Grades File",
    // Group Type
    ACCEPT_INVITATION = "Accepted Group Invite",
    CANCEL_INVITATION = "Canceled Group Invite",
    CREATE_GROUP = "Created New Group",
    CREATE_INVITATION = "Invited to Join Group",
    DISBAND_GROUP = "Disband Group",
    GRANT_EXTENSION = "Granted Extension",
    LEAVE_GROUP = "Left Group",
    REJECT_INVITATION = "Rejected Group Invite",
    REMOVE_EXTENSION = "Removed Extension",
    REQUEST_REGRADE = "Requested Regrade",
    SUBMIT_FILES = "Submitted Files",
    SUBMIT_SURVEY = "Submitted Survey"
    ;

  public static final long LOG_CATEGORY = 16;

  public static String logTypeToString(int type) {
    switch(type) {
      case LOG_ADMIN:  return ADMIN;
      case LOG_COURSE: return COURSE;
      case LOG_GROUP:  return GROUP;
      case LOG_GRADE:  return GRADE;
      default:         return CATEGORY;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private String      actingNetID;
  private String      simulatedNetID;
  private SortedMap/*String, User*/ receivingUsers;  // NetID -> User
  private InetAddress actingIPAddress;
  private Date        time;
  private String      logName;
  private int         logType;
  private Course      course;
  private Collection/*Assignment*/ assignments;
  
  final List/*String*/ detailLogs;  // Managed by LogDetail.

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setActingNetID     (final String actingNetID)          { this.actingNetID     = actingNetID;     }
  public void setSimulatedNetID  (final String simulatedNetID)       { this.simulatedNetID  = simulatedNetID;  }
  public void setActingIPAddress (final InetAddress actingIPAddress) { this.actingIPAddress = actingIPAddress; }
  public void setTimestamp       (final Date time)                   { this.time            = time;            }
  public void setLogName         (final String logName)              { this.logName         = logName;         }
  public void setLogType         (final int logType)                 { this.logType         = logType;         }
  public void setCourse          (final Course course)               { this.course          = course;          }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public String      getActingNetID()     { return this.actingNetID;     }
  public String      getSimulatedNetID()  { return this.simulatedNetID;  }
  public InetAddress getActingIPAddress() { return this.actingIPAddress; }
  public Date        getTimestamp()       { return this.time;            }
  public String      getLogName()         { return this.logName;         }
  public int         getLogType()         { return this.logType;         }
  public Course      getCourse()          { return this.course;          }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Log(CMSRoot database) {
    database.logs.add(this);
    detailLogs = new ArrayList();
    time = new Date();
    assignments = new HashSet();
  }
  public Collection/*Assignments*/ findAssignments() {
    return Collections.unmodifiableCollection(assignments);
  }
  public Collection/*LogDetail*/ getDetailLogs() {
    return Collections.unmodifiableCollection(detailLogs);
  }
  public Map/*String, User*/ getReceivingUsers() {
    return receivingUsers == null ? Collections.EMPTY_MAP : Collections
        .unmodifiableMap(receivingUsers);
  }
  
  public void addReceivingUsers(Collection users) {
    if (users == null) return;
    
    if (receivingUsers == null) receivingUsers = new TreeMap();

    for (Iterator it = users.iterator(); it.hasNext();) {
      User user = (User) it.next();
      receivingUsers.put(user.getNetID(), user);
    }
  }

  public void addReceivingUser(User user) {
    if (receivingUsers == null) receivingUsers = new TreeMap();
    receivingUsers.put(user.getNetID(), user);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
