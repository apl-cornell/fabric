/*
 * Created on Jul 16, 2004
 */

package cms.www.xml;

import java.net.ConnectException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cms.www.AccessController;
import cms.www.TransactionError;
import cms.www.TransactionResult;
import cms.www.util.DateTimeUtil;
import cms.www.util.Emailer;
import cms.www.util.Profiler;
import cms.www.util.StringUtil;

// import com.Ostermiller.util.CSVPrinter;
// import com.sun.rsasign.u;

import cms.model.*;

/**
 * Methods for building the xml tree
 * 
 * @author rd94
 */
@SuppressWarnings("unchecked")
public class XMLBuilder {
  // XML Attributes
  public final static String
      A_CODE = "code",
      A_COUNT = "count",
      A_DISPLAYEDCODE = "displayedcode",
      A_COURSEID = "courseid",
      A_ASSIGNID = "assignid",
      A_ID = "id",
      A_NAME = "name",
      A_FIRSTNAME = "firstname",
      A_LASTNAME = "lastname",
      A_COLLEGE = "college",
      A_COURSENAME = "coursename",
      A_NAMESHORT = "nameshort",
      A_ENROLLMENT = "enrollment",
      A_SEMESTER = "semester",
      A_SIZE = "size",
      A_USERS = "users",
      A_COURSES = "courses",
      A_SHOWFINALGRADES = "showfinalgrades",
      A_SHOWTOTALSCORES = "showtotalscores",
      A_SHOWASSIGNWEIGHTS = "showassignweights",
      A_HASSECTION = "hassection",
      A_SHOWGRADERID = "showgradernetid",
      A_POSTER = "poster",
      A_DATE = "date",
      A_EXTENSION = "extension",
      A_EXTVAL = "extval",
      A_PASTEXTENSION = "pastextension",
      A_EXTDATE = "extdate",
      A_EXTTIME = "exttime",
      A_EXTAMPM = "extampm",
      A_ASSIGNTYPE = "assigntype",
      A_EXPIRED = "expired",
      // Due fields
      A_DUEDATE = "duedate",
      A_DUETIME = "duetime",
      A_DESCRIPTION = "description",
      A_DUEAMPM = "dueampm",
      A_DUECOUNTDOWN = "duecountdown",
      A_PASTDUE = "pastdue",
      // Late fields
      A_LATEDATE = "latedate",
      A_LATETIME = "latetime",
      A_LATEAMPM = "lateampm",
      A_LATEALLOWED = "lateallowed",
      A_LATEFULLDATE = "latefulldate",
      A_PASTLATE = "pastlate",
      // Regrade fields
      A_REGRADEDATE = "regradedate",
      A_REGRADETIME = "regradetime",
      A_REGRADEAMPM = "regradeampm",
      A_PASTREGRADE = "pastregrade",
      A_TEXT = "text",
      A_ACTIVITY = "activity",
      A_STATUS = "status",
      A_MAXGROUP = "maxgroup",
      A_MINGROUP = "mingroup",
      A_STATMEAN = "statmean",
      A_STATMAX = "statmax",
      A_STATMEDIAN = "statmedian",
      A_STATDEV = "statdev",
      A_WEIGHT = "weight",
      A_TOTALSCORE = "totalscore",
      A_FINALGRADE = "finalgrade",
      A_NETID = "netid",
      A_MEMBERS = "members",
      A_DETAILS = "details",
      A_URL = "url",
      A_TYPE = "type",
      A_FILE = "file",
      A_NUMBER = "number",
      A_TYPELIST = "typelist",
      A_ISNEW = "isnew",
      A_MD5 = "md5",
      A_FILENAME = "filename",
      A_ASSIGNMENT = "assignment",
      A_GRACEPERIOD = "graceperiod",
      A_MAXTOTALSCORE = "maxtotalscore",
      A_HIGHTOTALSCORE = "hightotalscore",
      A_MEANTOTALSCORE = "meantotalscore",
      A_MEDIANTOTALSCORE = "mediantotalscore",
      A_STDEVTOTALSCORE = "stdevtotalscore",
      A_ASSIGNEDGRADERS = "assignedgraders",
      A_ASSIGNEDGROUPS = "assignedgroups",
      A_STUDENTREGRADES = "studentregrades",
      A_SHOWADDDROP = "showadddrop",
      A_SHOWSTATS = "showstats",
      A_SHOWSOLUTION = "showsolution",
      A_ISERROR = "iserror",
      A_ISWARNING = "iswarning",
      A_USER = "user",
      A_ISADMIN = "isadmin",
      A_ISGROUPS = "isgroups",
      A_ISGRADES = "isgrades",
      A_ISASSIGN = "isassignments",
      A_ISCATEGORY = "iscategory",
      A_ISVIEWAS = "isviewas",
      A_EXAMPLEDATE = "exampledate",
      A_HIDDEN = "hidden",
      A_DATA = "data",
      A_NULL = "null",
      A_SCORE = "score",
      A_OLDTEXT = "oldtext",
      // misc
      A_SUBMISSIONID = "submissionid",
      // for categories
      A_ORDER = "order",
      A_POSITION = "position",
      A_SORTBYID = "sortbyid",
      A_NUMOFCOLS = "numcols",
      A_NUMSHOW = "numshow",
      A_NUMOFDATES = "numdates",
      A_AUTHORZN = "authorzn",
      A_NUMOFCTGS = "numctgs",
      A_ISANNOUNCEMENTS = "isAnnouncements",
      A_CONTENTID = "ctntID",
      A_NUMRECENTANNOUNCE = "numRannounce",
      A_NUMTOTALANNOUNCE = "numTannounce",
      A_LINKNAME = "linkName",
      A_REMOVED = "removed", // used for removed columns (which are NOT the
                              // same as hidden columns)
      // course access attributes
      A_ISCCMEMBER = "isccmember",
      A_ISSTUDENT = "isstudent",
      A_COURSEGUESTACCESS = "courseGA",
      A_ASSIGNGUESTACCESS = "assignGA",
      A_ANNOUNCEGUESTACCESS = "announceGA",
      A_SOLUTIONGUESTACCESS = "solutionGA",
      A_COURSECCACCESS = "courseCC",
      A_ASSIGNCCACCESS = "assignCC",
      A_ANNOUNCECCACCESS = "announceCC",
      A_SOLUTIONCCACCESS = "solutionCC",
      // Debug attributes
      A_DEBUGID = "debugid",
      A_STUDENTNETID = "studentnetid",
      // Grading Page Attributes
      A_GROUPID = "groupid",
      A_SUBMITTEDFILEID = "submittedfileid",
      A_GRADER = "grader",
      A_CANGRADE = "cangrade",
      A_SUBPROBID = "subprobid",
      A_SUBPROBNAME = "subprobname",
      A_REQUESTID = "requestid",
      A_COMMENTID = "commentid",
      A_COMMENTFILEID = "commentfileid",
      A_COMMENT = "comment",
      A_ISAVERAGE = "isaverage",
      A_OVERMAX = "overmax",
      A_REGRADE = "regrade",
      A_REMAININGSUBS = "remainingsubs",
      A_ENROLLED = "enrolled",
      A_COURSEFROZEN = "coursefrozen",
      A_LATESUBMISSION = "latesubmission",
      A_SUBMISSIONCOUNT = "submissioncount",
      A_PARTIAL = "partial",
      A_COMPLETE = "complete",
      A_ERROR = "error",
      A_MAXSCORE = "maxscore",
      A_ASSIGNNAME = "assignname",
      A_JUMPTOGROUP = "jumptogroup",
      A_GRADEMSG = "grademsg",
      // Logging attributes
      A_ACTINGNETID = "actingnetid",
      A_IPADDRESS = "ipaddress",
      A_LOGNAME = "logname",
      A_LOGTYPE = "logtype",
      A_LOGTYPESTR = "logtypestr",
      A_RECNETID = "recnetid",
      A_SIMNETID = "simnetid",
      A_LOGID = "logid",
      A_INITIALSEARCH = "initialsearch",
      // Email attributes
      A_SENDER = "sender",
      A_MESSAGE = "message",
      A_SUBJECT = "subject",
      A_RECIPIENT = "recipient",
      // Used for the file upload confirmation page
      A_CONFIRMTYPE = "confirmtype",
      A_COLSPAN = "colspan",
      A_SHOWNAV = "shownav",
      // User Preferences
      A_PREF_NEWASSIGN = "prefnewassign",
      A_PREF_DATECHANGE = "prefdatechange",
      A_PREF_INVITATION = "prefinvitation",
      A_PREF_GRADERELEASE = "prefgraderelease",
      A_PREF_GRADECHANGE = "prefgradechange",
      A_PREF_FILESUBMIT = "preffilesubmit",
      A_PREF_FINALGRADES = "preffinalgrades",
      A_PREF_ASSIGNEDTO = "prefassignedto",
      A_PREF_NEWREQUEST = "prefnewrequest",
      // Student info
      A_CUID = "cuid",
      A_LECTURE = "lecture",
      A_LAB = "lab",
      A_SECTION = "section",
      A_CREDITS = "credits",
      A_GRADEOPTION = "gradeoption",
      A_CUIDCOUNT = "cuidcount",
      /* SCHEDULER ATTRIBUTES */
      // Assignment attribute:
      A_USESCHEDULE = "useschedule",
      // Schedule attributes:
      A_TSDURATIONSTR = "tsdurationstr",
      A_TSMAXGROUPS = "tsmaxgroups",
      A_SCHEDULE_LOCKDATE = "tslockdate", // for the staff side
      A_SCHEDULE_LOCKTIME = "tslocktime", // for the staff side
      A_SCHEDULE_LOCKAMPM = "tslockampm", // for the staff side
      A_SCHEDULE_LOCKED = "tslocked", // for the student side
      // Survey attributes
      A_ANSWER = "answer",
      A_ANSWERTEXT = "answertext",
      A_CORRECTANSWER = "correctanswer",
      A_LETTER = "letter",
      // Timeslot attributes:
      A_TSID = "tsid",
      A_TSNAME = "tsname",
      A_TSSTAFF = "tsstaff",
      A_TSLOCATION = "tslocation",
      A_TSSTAFFNAME = "tsstaffname",
      A_TSPOPULATION = "tspopulation",
      A_TSSTARTDATE = "tsstartdate",
      A_TSSTARTTIME = "tsstarttime",
      A_TSSTARTAMPM = "tsstartampm",
      A_TSENDDATE = "tsenddate",
      A_TSENDTIME = "tsendtime",
      A_TSENDAMPM = "tsendampm",
      A_TSEDITRIGHTS = "tseditrights",
      A_TSCONFLICTING = "tsconflicting",
      // Group attribute:
      A_ISSCHEDULED = "isscheduled",
      // Student preference:
      A_PREF_TimeslotCHANGE = "prefTimeslotchange",

      // Profiler Attributes
      A_METHODNAME = "methodname",
      A_CONTEXT = "context",
      A_TIMEPERIOD = "timeperiod",
      A_TIME = "time",
      A_PERCENT = "percent",
      // Error output
      A_ACTION = "erroraction",
      A_ERR_MSG = "errormsg",
      A_ERR_NAME = "errorname",
      A_ERR_CLASS = "errorclass",
      A_ERR_METHOD = "errormethod",
      A_ERR_LINE = "errorline",
      A_ERR_FILE = "errorfile",

      // Attributes for reloading assignment editing page after error
      // TODO delete this
      A_GROUPSVAL = "groupsval", A_RESTORED = "restored",
      A_NEWREQNAME = "newreqname", A_NEWREQTYPE = "newreqtype",
      A_NEWREQSIZE = "newreqsize", A_NEWITEMFILEPATH = "newitemfilepath",
      A_SOLFILEPATH = "solfilepath", A_REMOVESOL = "removesol",
      A_REPLACEITEMFILE = "replaceitemfile",
      A_RESTOREITEMFILE = "restoreitemfile", A_REMOVEITEM = "removeitem",
      A_RESTOREITEM = "restoreitem";

  // XML Tags
  public final static String
      TAG_ROOT = "root",
      TAG_PRINCIPAL = "principal",
      TAG_COURSESTUDENTS = "coursestudents",
      // CMS administration
      TAG_CMSADMINLIST = "cmsAdminList",
      TAG_CMSADMIN = "cmsAdmin",
      TAG_SYSTEMDATA = "system",
      TAG_SEMESTERS = "semesters",
      TAG_CURSEMESTER = "curSemester",
      TAG_SEMESTER = "semester",
      TAG_ALLCOURSES = "allCourses",
      TAG_OPENASSIGNMENTS = "openAssignments",
      TAG_NAMELESSUSER = "namelessuser",
      TAG_NOCUID = "nocuid",
      TAG_CURSITENOTICES = "curSiteNotices",
      TAG_DELSITENOTICES = "delSiteNotices",
      TAG_SITENOTICE = "siteNotice",
      // assignments
      TAG_ASSIGNMENT = "assignment",
      TAG_ASSIGNMENTS = "assignments",
      TAG_HIDDENASSIGNMENTS = "hiddenassignments",
      TAG_HIDDENASSIGNMENT = "hiddenassignment",
      TAG_STUDENTASSIGNS = "studentassigns",
      TAG_ALLDUEASSIGNMENTS = "allDueAssignments",
      TAG_ALLANNOUNCEMENTS = "allAnnouncements",
      TAG_ALLEVENTS = "allEvents",
      TAG_ANNOUNCEMENT = "announcement",
      TAG_HIDDENANNOUNCE = "hiddenannounce",
      TAG_ANNOUNCEMENTHISTORY = "announceHistory",
      TAG_CLARIFICATIONS = "clarifications",
      // For Assignment Items
      TAG_ITEMS = "items",
      TAG_ITEM = "item",
      TAG_FILE = "file",
      TAG_HIDDENITEM = "hiddenitem",
      TAG_HIDDENFILE = "hiddenfile",
      TAG_FILETYPES = "fileTypes",
      TAG_ENTRY = "entry",
      TAG_FORMAT = "format",
      TAG_GROUP = "group",
      TAG_SURVEYRESULT = "surveyresult",
      TAG_SURVEY = "survey",
      TAG_SURVEYS = "surveys",
      TAG_SUBPROBLEM = "subproblem",
      TAG_TOTALPROBLEM = "totalproblem",
      TAG_GROUPS = "groups",
      TAG_SUBPROBS = "subprobs",
      TAG_HIDDENSUBPROB = "hiddensubprob",
      TAG_SUBMISSIONS = "submissions",
      TAG_SUBMITTED = "submitted",
      TAG_SUBMISSION = "submission",
      TAG_SOLUTIONS = "solutions",
      TAG_SOLFILE = "solfile",
      TAG_HIDDENSOLFILE = "hiddensolfile",
      TAG_COMMENTS = "comments",
      TAG_NOTES = "notes",
      TAG_DESCRIPTION = "description",
      TAG_COURSE = "course",
      TAG_GUESTCOURSE = "guestCourse",
      TAG_STUDENTCOURSES = "studentCourses",
      TAG_STAFFCOURSES = "staffCourses",
      TAG_COURSEANNOUNCEMENTS = "courseAnnouncements",
      TAG_MEMBER = "member",
      TAG_MEMBERS = "members",
      TAG_INVITATION = "invitation",
      TAG_INVITATIONS = "invitations",
      TAG_GROUPSINVITEDTO = "groupsInvitedTo",
      TAG_STATUS = "status",
      TAG_STAFF = "staff",
      TAG_STUDENTS = "students",
      TAG_STUDENT = "student",
      // debug tags
      TAG_DEBUGIDS = "debugids",
      // category tags
      TAG_CATEGORIES = "categories",
      TAG_CATEGORY = "category",
      TAG_CTGCONTENTS = "ctgContents",
      TAG_COLUMN = "ctgColumn",
      TAG_CTGROW = "ctgRow",
      TAG_CONTENT = "content",
      TAG_FILECOLN = "filecoln",
      TAG_CTGFILE = "ctgfile",
      TAG_DATATYPE = "datatype",
      TAG_VISIBLEFILES = "visibleFiles",
      TAG_HIDDENFILES = "hiddenFiles",
      TAG_COLUMNS = "columns", // non-removed columns go under this category
                                // subtree and have A_HIDDEN set
      TAG_REMOVEDCOLUMNS = "removedColumns", // removed columns go under this
                                              // category subtree
      TAG_VISIBLEROWS = "visibleRow",
      TAG_HIDDENROWS = "hiddenRow",
      TAG_VISIBLECTG = "visibleCtg",
      TAG_HIDDENCTG = "hiddenCtg",
      TAG_SHOWCATEGORY = "showcategory",
      // For Grade Assignment
      TAG_STUDENTSTOGRADE = "studentstograde",
      TAG_ELEMENT = "element",
      TAG_STUDENTTOGRADE = "studenttograde",
      TAG_GRADESTUDENT = "gradestudent",
      TAG_ASSIGNEDTO = "assignedto",
      TAG_GRADE = "grade",
      TAG_GRADELOG = "gradelog",
      TAG_REGRADE = "regrade",
      TAG_GRADER = "grader",
      TAG_OLDFILE = "oldfile",
      TAG_COMMENTFILE = "commentfile",
      TAG_COMMENT = "comment",
      TAG_RESPONSE = "response",
      TAG_ERROR = "error",
      TAG_ROW = "row",
      // Logging
      TAG_LOG = "log",
      TAG_LOGNAMES = "lognames",
      TAG_LOGTYPES = "logtypes",
      TAG_LOGNAME = "logname",
      TAG_LOGTYPE = "logtype",
      TAG_LOGSEARCH_COURSES = "logsearchcourses",
      TAG_LOGSEARCH_COURSE = "logsearchcourse",
      TAG_LOGSEARCH_ASSIGNS = "logsearchassigns",
      TAG_LOGSEARCH_ASSIGN = "logsearchassign",
      TAG_LOGSEARCH_RESULTS = "logsearchresults",
      TAG_RECNETID = "recnetid",
      TAG_LOGDETAIL = "logdetail",
      // Email Page
      TAG_LATESTEMAIL = "latestemail",
      TAG_EMAIL = "email",
      // User Preferences
      TAG_PREFS = "prefs",
      // Sheduler Tags
      TAG_SCHEDULE = "schedule",
      TAG_TIMESLOT = "Timeslot",
      TAG_UNSCHEDULEDGROUPS = "unscheduledgroups",
      // Profiler Tags
      TAG_REQUESTPROFILE = "requestprofile",
      TAG_METHODCALL = "methodcall",
      // Error tag
      TAG_ERROR_LINE = "errorline",
      TAG_NEWITEM = "newitem",
      TAG_NEWPROB = "newprob",
      // Surveys
      TAG_QUESTIONS = "questions", TAG_ANSWERS = "answers",
      TAG_ANSWER = "answer", TAG_CHOICES = "choices",
      TAG_HIDDENCHOICE = "hiddenchoice", TAG_CHOICE = "choice";

  // Downloadable file types
  public final static int
      T_SOLFILE = 0, // Solution
      T_ITEMFILE = 1, // Assignment Item
      T_FILEFILE = 2, // Assignment File
      T_GROUPFILE = 3, // Uploaded group submission
      T_CATFILE = 4, // File posted in a category
      T_COMMENTFILE = 5; // Comment File

  // various types of uploaded info whose contents need to be confirmed by some
  // admin
  public final static int
      CONFIRM_ASSIGNINFO = 1,  // info specific to an assignment
      CONFIRM_COURSEINFO = 2,  // info specific to a course
      CONFIRM_GENERAL = 3,     // systemwide info
      CONFIRM_FINALGRADES = 4; // special case

  // status message types
  public final static int MSG_NORMAL = 0, MSG_WARNING = 1, MSG_ERROR = 2;

  // Misc
  protected boolean debug = true; // debug mode
  // TODO: set up database
  protected CMSRoot         database;
  protected DocumentBuilder db;

  CourseXMLBuilder           courseXMLBuilder;
  AssignmentXMLBuilder       assignmentXMLBuilder;
  SystemXMLBuilder           systemXMLBuilder;
  StudentXMLBuilder          studentXMLBuilder;
  LogXMLBuilder              logXMLBuilder;
  ViewStudentsXMLBuilder     viewStudentsXMLBuilder;
  ScheduleXMLBuilder         scheduleXMLBuilder;
  GradingXMLBuilder          gradingXMLBuilder;
  GroupXMLBuilder            groupXMLBuilder;
  AnnouncementXMLBuilder     announcementXMLBuilder;
  CategoryXMLBuilder         categoryXMLBuilder;
  AssignmentGroupsXMLBuilder assignmentGroupsXMLBuilder;
  StaffXMLBuilder            staffXMLBuilder;
  StudentGradesXMLBuilder    studentGradesXMLBuilder;

  public XMLBuilder(CMSRoot database) throws ParserConfigurationException {
    this.database = database;

    this.courseXMLBuilder           = new CourseXMLBuilder          (this);
    this.assignmentXMLBuilder       = new AssignmentXMLBuilder      (this);
    this.systemXMLBuilder           = new SystemXMLBuilder          (this);
    this.studentXMLBuilder          = new StudentXMLBuilder         (this);
    this.logXMLBuilder              = new LogXMLBuilder             (this);
    this.viewStudentsXMLBuilder     = new ViewStudentsXMLBuilder    (this);
    this.scheduleXMLBuilder         = new ScheduleXMLBuilder        (this);
    this.gradingXMLBuilder          = new GradingXMLBuilder         (this);
    this.groupXMLBuilder            = new GroupXMLBuilder           (this);
    this.announcementXMLBuilder     = new AnnouncementXMLBuilder    (this);
    this.categoryXMLBuilder         = new CategoryXMLBuilder        (this);
    this.assignmentGroupsXMLBuilder = new AssignmentGroupsXMLBuilder(this);
    this.staffXMLBuilder            = new StaffXMLBuilder           (this);
    this.studentGradesXMLBuilder    = new StudentGradesXMLBuilder   (this);

    db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
  }
  
  public Document buildHomepage() throws Exception {
    Profiler.enterMethod("XMLBuilder.buildHomepage", "");
    Document xml = db.newDocument();
    Element root = xml.createElement(TAG_ROOT);
    xml.appendChild(root);
    Element courseList = xml.createElement(TAG_ALLCOURSES);
    root.appendChild(courseList);
    Collection courses = database.getCurrentSemester().getCourses();
    Iterator i = courses.iterator();
    while (i.hasNext()) {
      Course course = (Course) i.next();
      if (course.getCourseGuestAccess()) {
        Element xCourse = courseXMLBuilder.buildHomepageSubtree(xml, course);
        courseList.appendChild(xCourse);
      }
    }
    Profiler.exitMethod("XMLBuilder.buildHomepage", "");
    return xml;
  }

  /**
   * Staff can upload csv files for a course or download a template from the CSV
   * upload page for that course
   * 
   * @param user
   * @param courseID
   * @return
   * @throws FinderException
   */
  public Document buildCSVUploadPage(User user, Course course) {
    // all we really need to know is what course we're in
    return buildStaffNavbar(user, course);
  }

  public Document buildEmailPage(User user, Course course) {
    return buildEmailPage(user, course, null);
  }

  public Document buildEmailPage(User user, Course course,
      Collection/*Group*/ groups) {
    Profiler.enterMethod("XMLBuilder.buildEmailPage", "CourseID: " + course);
    Document xml = buildStaffNavbar(user, course);
    Element root = (Element) xml.getFirstChild();
    Element studentsNode = xml.createElement(TAG_STUDENTS);
    Iterator emails = course.getEmails().iterator();
    Collection selectedIDs = new Vector();
    if (groups != null) {
      Iterator groupsIter = groups.iterator();
      while (groupsIter.hasNext()) {
        Group    group   = (Group) groupsIter.next();
        Iterator members = group.getMembers().iterator();

        while (members.hasNext()) {
          GroupMember member = (GroupMember) members.next();
          selectedIDs.add(member.getStudent().getUser().getNetID());
        }
      }
    }

    while (emails.hasNext()) {
      Email email = (Email) emails.next();
      Element xEmail = null;
      xEmail = xml.createElement(TAG_EMAIL);
      
      StringBuilder sender = new StringBuilder(email.getSender().getFirstName());
      if (sender.length() > 0)
        sender.append(" ");
      sender.append(email.getSender().getLastName());
      if (sender.length() > 0)
        sender.append(" ");
      sender.append(email.getSender().getNetID());
      
      xEmail.setAttribute(A_SENDER,  sender.toString());
      xEmail.setAttribute(A_SUBJECT, email.getSubject());
      xEmail.setAttribute(A_MESSAGE, email.getMessage());
      xEmail.setAttribute(A_DATE,
                          DateTimeUtil.formatDate(email.getDateSent()));
      xEmail.setAttribute(A_RECIPIENT,
                          String.valueOf(email.getRecipient()));
      xEmail.setAttribute(A_ID, email.toString());
      root.appendChild(xEmail);
    }
    if (selectedIDs.size() > 0) {
      viewStudentsXMLBuilder.buildSelectedStudentList(course, xml,
          studentsNode, selectedIDs);
      root.appendChild(studentsNode);
    }
    Profiler.exitMethod("XMLBuilder.buildEmailPage", "CourseID: " + course.toString());
    return xml;
  }

  public Document buildErrorPage(String netID, String action, Exception e) {
    Document xml = db.newDocument();
    Element root = xml.createElement(TAG_ROOT);
    xml.appendChild(root);
    root.setAttribute(A_ACTION, action);
    Emailer email = null;
    try {
      email = new Emailer();
      email.setFrom(netID + "@cornell.edu");
      email.setReplyTo(Emailer.DEBUG_EMAIL);
      email.setRecipient(Emailer.DEBUG_EMAIL);
      email.setSubject("CMS Error Report");
    } catch (ConnectException x) {
      x.printStackTrace();
    }
    String body =
        "Action: " + action + "\nError: " + e.getClass().getName()
            + "\nMessage: " + e.getMessage() + "\n\n";
    body = appendStackTrace(xml, root, e, body);
    if (email != null && !AccessController.debug) {
      email.setMessage(body);
      email.sendEmail(Emailer.DEBUG_EMAIL);
    }
    return xml;
  }

  /**
   * Adds exception information to an XML element root Returns the stack trace
   * printed out as a String appended to body
   */
  public String appendStackTrace(Document xml, Element root,
      Exception e, String body) {
    root.setAttribute(A_ERR_NAME, e.getClass().getName());
    root.setAttribute(A_ERR_MSG, e.getMessage());
    StackTraceElement[] s = e.getStackTrace();
    for (int i = 0; i < s.length; i++) {
      Element xErrorLine = xml.createElement(TAG_ERROR_LINE);
      xErrorLine.setAttribute(A_ERR_CLASS, s[i].getClassName());
      xErrorLine.setAttribute(A_ERR_FILE, s[i].getFileName());
      xErrorLine.setAttribute(A_ERR_METHOD, s[i].getMethodName());
      xErrorLine.setAttribute(A_ERR_LINE, String.valueOf(s[i].getLineNumber()));
      root.appendChild(xErrorLine);
      if (body != null) {
        body +=
            "   " + s[i].getClassName() + "." + s[i].getMethodName() + " ("
                + s[i].getFileName() + ":" + s[i].getLineNumber() + ")\n";
      }
    }
    return body;
  }

  public Document buildFinalGradesPage(User requester, Course course) {
    Profiler.enterMethod("XMLBuilder.buildFinalGradesPage", "CourseID: "
        + course);
    Document xml = buildStaffNavbar(requester, course);
    Element root = (Element) xml.getFirstChild();
    if (requester.isAdminPrivByCourse(course))
      root.setAttribute(A_ISADMIN, "true");
    List students = new ArrayList(course.getStudents());
    Collections.sort(students, Student.LAST_NAME_COMPARATOR);
    
    Iterator studentsIter = students.iterator();
    while (studentsIter.hasNext()) {
      Student student = (Student) studentsIter.next();
      User    user    = student.getUser();
      
      Element xUser =
        xml.createElementNS(TAG_STUDENT + user.getNetID(), TAG_STUDENT);
      xUser.setAttribute(A_NETID, user.getNetID());
      xUser.setAttribute(A_CUID, user.getCUID() == null ? "" : user.getCUID()
          .toString());
      xUser.setAttribute(A_COLLEGE, user.getCollege());
      xUser.setAttribute(A_FIRSTNAME, user.getFirstName());
      xUser.setAttribute(A_LASTNAME, user.getLastName());
      root.appendChild(xUser);
      if (xUser != null) {
        xUser.setAttribute(A_LECTURE, student.getLecture() == null ? ""
            : student.getLecture().toString());
        xUser.setAttribute(A_LAB, student.getLab() == null ? "" : student
            .getLab().toString());
        xUser.setAttribute(A_SECTION, student.getSection() == null ? ""
            : student.getSection().toString());
        xUser.setAttribute(A_CREDITS, student.getCredits() == null ? ""
            : student.getCredits().toString());
        xUser.setAttribute(A_GRADEOPTION, student.getGradeOption() == null ? ""
            : student.getGradeOption());
        xUser.setAttribute(A_FINALGRADE, student.getFinalGrade() == null ? ""
            : student.getFinalGrade());
      }
    }
    Profiler.exitMethod("XMLBuilder.buildFinalGradesPage", "CourseID: "
        + course);
    return xml;
  }

  /**
   * Creates the xml tree for the entire cms admin page group: includes all the
   * info an admin might want for all admin functions.
   * 
   * @param user
   *          The User object representing the current user
   */
  public Document buildCMSAdminPage(User user) {
    Profiler.enterMethod("XMLBuilder.buildCMSAdminPage", "");
    Document xml = db.newDocument();
    Element root = xml.createElement(TAG_ROOT);
    xml.appendChild(root);
    // systemwide usage stats for current semester
    root.appendChild(systemXMLBuilder.buildSystemDataSubtree(xml));
    Iterator i;
    // add list of current cms admins
    Element adminList = xml.createElement(TAG_CMSADMINLIST);
    Collection admins = database.findAllAdmins();
    i = admins.iterator();
    while (i.hasNext()) {
      Element adminNode = xml.createElement(TAG_CMSADMIN);
      User admin = (User) i.next();
      adminNode.setAttribute(A_NETID, admin.getNetID());
      adminNode.setAttribute(A_NAME,  admin.getFirstName() + " " + admin.getLastName());
      adminList.appendChild(adminNode);
    }
    root.appendChild(adminList);
    // add current semester and list of current courses and their
    // enrollment statistics
    Element semesterList = xml.createElement(TAG_SEMESTERS);
    Semester curSemester = database.getCurrentSemester();
    Collection sems      = database.getAllSemesters();
    i = sems.iterator();
    while (i.hasNext()) {
      Semester sem = (Semester) i.next();
      Element semester;
      if (sem == curSemester)
        semester = xml.createElement(TAG_CURSEMESTER);
      else semester = xml.createElement(TAG_SEMESTER);
      semester.setAttribute(A_ID,     sem.toString());
      semester.setAttribute(A_NAME,   sem.getName());
      semester.setAttribute(A_HIDDEN, Boolean.toString(sem.getHidden()));
      semesterList.appendChild(semester);
    }
    root.appendChild(semesterList);
    // list current-semester courses
    Element courseList = xml.createElement(TAG_ALLCOURSES);
    i = curSemester.getCourses().iterator();
    while (i.hasNext()) {
      Course c = (Course) i.next();
      Element xCourse = courseXMLBuilder.buildShortSubtree(user, xml, c);
      xCourse.setAttribute(A_ENROLLMENT, Integer.toString(c.findActiveStudents().size()));
      courseList.appendChild(xCourse);
    }
    root.appendChild(courseList);
    // add open assignments for all courses (so admins know when not to
    // update the server)
    Element assignmentList = xml.createElement(TAG_OPENASSIGNMENTS);
    // look for all assignments due within an arbitrary short period
    final long MILLISECS_PER_HOUR = 3600000;
    Date checkDeadline = new Date(System.currentTimeMillis() + 48 * MILLISECS_PER_HOUR);
    Collection openAssignments = database.findOpenAssignmentsByDeadline(checkDeadline);
    i = openAssignments.iterator();
    while (i.hasNext()) // assume the Collection returned assignments in
    // order by deadline
    {
      Assignment asgn = (Assignment) i.next();
      assignmentList.appendChild(assignmentXMLBuilder.buildShortSubtree(xml,
          asgn));
    }
    // Append searchable courses/assignments
    appendCMSAdminLogInfo(user, xml);
    root.appendChild(assignmentList);
    Iterator emptyNames = null;
    emptyNames = database.findMissingNameUsers().iterator();
    while (emptyNames.hasNext()) {
      User noname = (User) emptyNames.next();
      Element xNoName = xml.createElement(TAG_NAMELESSUSER);
      xNoName.setAttribute(A_NETID, noname.getNetID());
      xNoName.setAttribute(A_FIRSTNAME, noname.getFirstName());
      xNoName.setAttribute(A_LASTNAME, noname.getLastName());
      root.appendChild(xNoName);
    }
    Iterator emptyCUIDs = database.findActiveStudentsWithoutCUID().iterator();
    root.setAttribute(XMLBuilder.A_CUIDCOUNT, String.valueOf(database.getCUIDCount()));
    if (emptyCUIDs != null) {
      while (emptyCUIDs.hasNext()) {
        User nocuid = (User) emptyCUIDs.next();
        Element xNoCUID = xml.createElement(TAG_NOCUID);
        xNoCUID.setAttribute(A_NETID, nocuid.getNetID());
        xNoCUID.setAttribute(A_FIRSTNAME, nocuid.getFirstName());
        xNoCUID.setAttribute(A_LASTNAME, nocuid.getLastName());
        root.appendChild(xNoCUID);
      }
    }
    root.appendChild(getLivingNoticeElement(xml));
    root.appendChild(getDeletedNoticeElement(xml));

    Profiler.exitMethod("XMLBuilder.buildCMSAdminPage", "");
    return xml;
  }

  /**
   * Puts all the current (publicly viewable) notices into an element and
   * returns it
   * 
   * @param xml
   *          The Document object
   * @return An element containing all the publicly viewable notices
   * @throws FinderException
   */
  public Element getViewableNoticeElement(Document xml) {
    Element curNoticeList = xml.createElement(TAG_CURSITENOTICES);
    Iterator curNoticeIter = database.findCurrentSiteNoticeShowing().iterator();

    translateChildNotices(xml, curNoticeList, curNoticeIter);
    return curNoticeList;
  }

  /**
   * Puts all the admin-viewable and editable (non-deleted) notices into an
   * element and returns it
   * 
   * @param xml
   *          The Document object
   * @return An element containing all the editable notices
   * @throws FinderException
   */
  public Element getLivingNoticeElement(Document xml) {
    Element curNoticeList = xml.createElement(TAG_CURSITENOTICES);
    Iterator curNoticeIter = database.findAllLivingSiteNotices().iterator();

    translateChildNotices(xml, curNoticeList, curNoticeIter);
    return curNoticeList;
  }

  /**
   * Puts all the deleted notices into an element and returns it
   * 
   * @param xml
   *          The Document object
   * @return An element containing all the deleted notices
   * @throws FinderException
   */
  public Element getDeletedNoticeElement(Document xml) {
    Element curNoticeList = xml.createElement(TAG_DELSITENOTICES);
    Iterator curNoticeIter = database.findDeletedSiteNotices().iterator();

    translateChildNotices(xml, curNoticeList, curNoticeIter);
    return curNoticeList;
  }

  private void translateChildNotices(Document xml,
      Element curNoticeList, Iterator curNoticeIter) {
    while (curNoticeIter.hasNext()) {
      SiteNotice sn = (SiteNotice) curNoticeIter.next();
      Element notice = xml.createElement(TAG_SITENOTICE);

      notice.setAttribute(A_ID,     sn.toString());
      notice.setAttribute(A_POSTER, sn.getAuthor().getNetID());
      notice.setAttribute(A_TEXT,   sn.getText());
      Date postDate = sn.getPostedDate();
      notice.setAttribute(A_DATE,   DateTimeUtil.DATE.format(postDate));

      Date expDate = sn.getExpireDate();
      Date now     = new Date();

      notice.setAttribute(A_DUEDATE, expDate == null ? "" : DateTimeUtil.DATE.format(expDate));
      notice.setAttribute(A_DUETIME, expDate == null ? "" : DateTimeUtil.TIME.format(expDate));
      notice.setAttribute(A_DUEAMPM, expDate == null ? "" : DateTimeUtil.AMPM.format(expDate));
      notice.setAttribute(A_EXPIRED, expDate != null
          && now.compareTo(expDate) > 0 ? "true" : "false");

      notice.setAttribute(A_HIDDEN, sn.getHidden() ? "true" : "false");
      curNoticeList.appendChild(notice);
    }
  }

  public void appendCMSAdminLogInfo(User user, Document xml) {
    Element root = (Element) xml.getElementsByTagName(TAG_ROOT).item(0);
    logXMLBuilder.appendLogSearchCourses(user, xml, root);
    logXMLBuilder.appendLogSearchAssigns(user, xml, root);
    logXMLBuilder.appendLogSearchNamesTypes(xml, root, true);
  }

  /**
   * Creates the xml tree for the cms admin course-edit page
   * 
   * @param user
   *          The User object representing the current user
   * @return A new XML document
   * @throws RemoteException
   */
  public Document buildCMSAdminCoursePropsPage(User user, Course course) {
    Document xml = buildPageHeader(user);
    Element root = (Element) xml.getElementsByTagName(TAG_ROOT).item(0);
    Element xCourse = courseXMLBuilder.buildGeneralSubtree(user, xml, course);
    xCourse.appendChild(courseXMLBuilder.buildStaffListSubtree(user, xml, course));
    root.appendChild(xCourse);
    return xml;
  }

  public Document buildLogSearchPage(User user, HttpServletRequest req)
      throws FileUploadException {
    return buildLogSearchPage(user, req, null);
  }

  /**
   * Creates the xml tree for viewing searched logs as a CMS admin
   * 
   * @param user
   *          The User object representing the current user
   * @param returnedLogs
   *          A Collection of Log objects representing search results
   * @return
   * @throws RemoteException
   */
  public Document buildLogSearchPage(User user,
      HttpServletRequest request, Course course) throws FileUploadException {
    LogSearchParams params = new LogSearchParams();
    ServletFileUpload upload = new ServletFileUpload();
    upload.setFileItemFactory(new DiskFileItemFactory());
    Iterator i = upload.parseRequest(request).iterator();
    while (i.hasNext()) {
      FileItem param = (FileItem) i.next();
      String fieldName = param.getFieldName();
      if (fieldName.equals(AccessController.P_LOGSEARCH_NETID)) {
        params.setActingNetID(param.getString());
      } else if (fieldName.equals(AccessController.P_LOGSEARCH_RECNETID)) {
        params.setReceivingNetID(param.getString());
      } else if (fieldName.equals(AccessController.P_LOGSEARCH_SIMNETID)) {
        params.setSimulatedNetID(param.getString());
      } else if (fieldName.equals(AccessController.P_LOGSEARCH_IP)) {
        params.setActingIPAddress(param.getString());
      } else if (fieldName.equals(AccessController.P_LOGSEARCH_START)) {
        String dateStart = param.getString();
        try {
          params.setStartTime(DateTimeUtil.parseDate(dateStart,
              DateTimeUtil.DATE));
        } catch (ParseException x) {
          if (!dateStart.equals("")) {
            x.printStackTrace();
          }
          // Warning about invalid date format
        }
      } else if (fieldName.equals(AccessController.P_LOGSEARCH_END)) {
        String dateEnd = param.getString();
        try {
          Timestamp endtime =
              DateTimeUtil.parseDate(dateEnd, DateTimeUtil.DATE);
          // Change to be until the end of the day
          endtime.setTime(endtime.getTime() + (DateTimeUtil.SECS_PER_DAY - 1)
              * 1000);
          params.setEndTime(endtime);
        } catch (ParseException x) {
          if (!dateEnd.equals("")) {
            x.printStackTrace();
          }
          // Warning about invalid date format
        }
      } else if (fieldName.equals(AccessController.P_LOGSEARCH_TYPE)) {
        String type = param.getString();
        try {
          params.addLogType(Long.parseLong(type));
        } catch (NumberFormatException e) {
        }
      } else if (fieldName.equals(AccessController.P_LOGSEARCH_NAME)) {
        params.addLogName(param.getString());
      } else if (fieldName.equals(AccessController.P_LOGSEARCH_COURSE)) {
        String courseToSearch = param.getString();
        try {
          params.setCourseID(courseToSearch.equals("0") ? null : new Long(Long
              .parseLong(courseToSearch)));
        } catch (NumberFormatException e) {
        }
      } else if (fieldName.equals(AccessController.P_LOGSEARCH_ASGN)) {
        String assign = param.getString();
        try {
          params.setAssignmentID(assign.equals("0") ? null : new Long(Long
              .parseLong(assign)));
        } catch (NumberFormatException e) {
        }
      }
    }
    Document xml =
        course == null ? buildPageHeader(user) : buildCoursePage(user, course);
    Collection returnedLogs = database.findLogs(params);
    Element root = (Element) xml.getElementsByTagName(TAG_ROOT).item(0);
    root.appendChild(logXMLBuilder.buildFullSubtree(user, xml, returnedLogs));
    return xml;
  }

  public Document buildStaffLogSearchPage(User user, Document xml, Course course) {
    Document doc = null;
    if (xml == null) {
      doc = buildStaffNavbar(user, course);
    } else {
      doc = xml;
    }
    Element root = (Element) doc.getFirstChild();
    if (xml == null) {
      root.setAttribute(A_INITIALSEARCH, "true");
    }

    root.appendChild(courseXMLBuilder.buildGeneralSubtree(user, doc, course));
    Element xCourse = doc.createElement(TAG_LOGSEARCH_COURSE);
    xCourse.setAttribute(A_COURSEID,      course.toString());
    xCourse.setAttribute(A_COURSENAME,    course.getName());
    xCourse.setAttribute(A_CODE,          course.getCode());
    xCourse.setAttribute(A_DISPLAYEDCODE, course.getDisplayedCode());
    xCourse.setAttribute(A_COURSEFROZEN,
                         String.valueOf(course.getFreezeCourse()));
    root.appendChild(xCourse);
    logXMLBuilder.appendAssignments(user, doc, course);
    logXMLBuilder.appendLogSearchNamesTypes(doc, root, false);
    return doc;
  }

  protected Document buildPageHeader(User user) {
    return buildPageHeader(user, null);
  }

  /**
   * Creates an initial xml document with page header information loaded. This
   * will most likely just consist of a list of NetIDs for debug and courses the
   * principal is a student or staff in.
   * 
   * @param user
   *          The User object representing the current user
   * @param semesterID
   *          the SemesterID of the semester to build a header for (null will
   *          automatically use current semester)
   * @return A new XML document
   * @throws RemoteException
   */
  protected Document buildPageHeader(User user, Semester semester) {
    Profiler.enterMethod("XMLBuilder.buildPageHeader", "");
    Document xml = db.newDocument();
    Element root = xml.createElement(TAG_ROOT);
    if (debug)
      root.appendChild(systemXMLBuilder.buildDebugNetIDListSubtree(xml));
    root.appendChild(studentXMLBuilder.buildCourseListSubtree(user, xml, semester));
    root.appendChild(staffXMLBuilder.buildCourseListSubtree(user, xml, semester));
    xml.appendChild(root);
    Element princip = xml.createElement(TAG_PRINCIPAL);
    if (!user.isGuest()) {
      princip.setAttribute(A_FIRSTNAME, user.getFirstName());
      princip.setAttribute(A_LASTNAME,  user.getLastName());
      princip.setAttribute(A_NETID,     user.getNetID());
      if (user.isAuthenticated()) {
        princip.setAttribute(A_ISCCMEMBER, "true");
      }
      root.appendChild(princip);
    }
    /*
     * Extremely unnecessary amount of work
     * Profiler.enterMethod("XMLBuilder.buildPageHeader3","GetStudentsQuery");
     * Iterator students =
     * database.studentHome().findByStaff(user.getPrincipalID()) .iterator();
     * Profiler.exitMethod("XMLBuilder.buildPageHeader3","GetStudentsQuery");
     * Profiler.enterMethod("XMLBuilder.buildPageHeader4","GetStudentsIterate");
     * while (students.hasNext()) { Student student = (Student)
     * students.next(); long courseID = student.getCourseID(); Element xCourse =
     * (Element) root.getElementsByTagNameNS( TAG_COURSESTUDENTS + courseID,
     * TAG_COURSESTUDENTS).item(0); if (xCourse == null) { xCourse =
     * xml.createElementNS(TAG_COURSESTUDENTS + courseID, TAG_COURSESTUDENTS);
     * xCourse.setAttribute(A_COURSEID, String.valueOf(courseID));
     * root.appendChild(xCourse); } Element xStudent =
     * xml.createElement(TAG_STUDENT); xStudent.setAttribute(A_NETID,
     * student.getUserID()); xCourse.appendChild(xStudent); }
     * Profiler.exitMethod("XMLBuilder.buildPageHeader4","GetStudentsIterate");
     */
    // do we need this?
    root.setAttribute(A_EXAMPLEDATE, "April 16, 2006 or 4/16/06 or 04-16-2006");
    Profiler.exitMethod("XMLBuilder.buildPageHeader", "");
    return xml;
  }

  /**
   * Adds a "status" Element as a child of the root Element with the "text"
   * attribute set to the detail message. If status string is null or empty,
   * does not change the tree. Note: this function is meant to be called from
   * AccessController.processRequest().
   * 
   * @param xml
   *          Document to add the status Element to
   * @param message
   *          Detail message
   * @param msgType
   *          MSG_NORMAL | MSG_WARNING | MSG_ERROR
   * @return Document with status appended
   */
  public Document addStatus(Document xml, String message, int msgType) {
    if (message == null || xml == null || message.equals("")) return xml;
    Element root = (Element) xml.getFirstChild();
    Element xStatus = xml.createElement(TAG_STATUS);
    if (msgType == MSG_ERROR)
      xStatus.setAttribute(A_ISERROR, "");
    else if (msgType == MSG_WARNING) xStatus.setAttribute(A_ISWARNING, "");
    xStatus.setAttribute(A_TEXT, message);
    root.appendChild(xStatus);
    return xml;
  }

  /**
   * If result's value is a String and the transaction was successful, that
   * string will be added as a success message; regardless, all errors and
   * warnings will be added
   * 
   * @param xml
   * @param result
   * @return
   */
  public Document addStatus(Document xml, TransactionResult result) {
    Iterator i = result.getErrors().iterator();
    Element root = (Element) xml.getFirstChild();
    Exception e;
    if ((e = result.getException()) != null) {
      appendStackTrace(xml, root, e, null);
    }
    while (i.hasNext()) {
      TransactionError error = (TransactionError) i.next();
      xml = addStatus(xml, error.getError(), MSG_ERROR);
    }
    i = result.getWarnings().iterator();
    while (i.hasNext()) {
      xml = addStatus(xml, (String) i.next(), MSG_WARNING);
    }
    if (result.getSuccess() && result.getValue() instanceof String)
      xml = addStatus(xml, (String) result.getValue(), MSG_NORMAL);
    return xml;
  }

  public Document buildOverview(User user) {
    return buildOverview(user, null);
  }

  /**
   * Creates an xml document loaded with display data for building a student
   * overview jsp page for the user
   * 
   * @param user
   *          The User who is the student we're building the page for
   * @param semesterID
   *          The semester to build the overview page for (null will
   *          automatically use the current semester)
   * @return Document an xml document with display data tags for the jsp page
   */
  public Document buildOverview(User user, Semester sem) {
    Profiler.enterMethod("XMLBuilder.buildOverview", "SemesterID: " + sem);
    boolean hasSem = sem != null;
    Document xml = hasSem ? buildPageHeader(user, sem) : buildPageHeader(user);
    Element root = (Element) xml.getElementsByTagName(TAG_ROOT).item(0);

    // get sitewide notices
    if (user.isCMSAdmin())
      root.appendChild(getLivingNoticeElement(xml));
    else
      root.appendChild(getViewableNoticeElement(xml));

    Iterator i;
    // Fill in due assignment information
    root.appendChild(studentXMLBuilder.buildDueAsgnListSubtree(user, xml, sem));
    // Fill in recent announcement information
    Date weekago = new Date(
        System.currentTimeMillis() - DateTimeUtil.SECS_PER_WEEK * 1000);
    Collection announcements =
        hasSem ? user.findAnnouncementsByDateAndSemester(weekago, sem)
               : user.findAnnouncementsByDate(weekago);
    i = announcements.iterator();
    Element xAnnouncements = xml.createElement(TAG_ALLANNOUNCEMENTS);
    root.appendChild(xAnnouncements);
    while (i.hasNext()) {
      Announcement announcement = (Announcement) i.next();
      Element xCourse = null;
      /*
       * We create course nodes, and then append the announcements to the proper
       * course, so we have to find the corresponding course element if it has
       * already been created
       */
      NodeList currentNodes = xAnnouncements.getChildNodes();
      for (int j = 0; j < currentNodes.getLength(); j++) {
        Element c = (Element) currentNodes.item(j);
        // TODO: this is pretty bad [mdg]
        if (c.getAttribute(A_COURSEID).equals(announcement.getCourse().toString())) {
          xCourse = c;
          break;
        }
      }
      if (null == xCourse) {
        // if none, create it
        xCourse = xml.createElement(TAG_COURSE);
        xAnnouncements.appendChild(xCourse);
        xCourse.setAttribute(A_COURSEID,   announcement.getCourse().toString());
        xCourse.setAttribute(A_COURSENAME, announcement.getCourse().getCode());
      }
      // add this announcement
      xCourse.appendChild(announcementXMLBuilder.buildGeneralSubtree(xml, announcement));
    }
    // Append viewable courses
    Iterator guestCourses = null;
    if (sem == null) {
      if (user.isAuthenticated())
        guestCourses = database.findCCAccessCourses().iterator(); 
      else
        guestCourses = database.findGuestAccessCourses().iterator();
    } else {
      if (user.isAuthenticated())
        guestCourses = sem.findCCAccessCourses().iterator();
      else
        guestCourses = sem.findGuestAccessCourses().iterator();
    }
    if (guestCourses != null) {
      while (guestCourses.hasNext()) {
        Course course = (Course) guestCourses.next();
        Element xGuestCourse = xml.createElement(TAG_GUESTCOURSE);
        xGuestCourse.setAttribute(A_COURSEID,   course.toString());
        xGuestCourse.setAttribute(A_COURSENAME, course.getName());
        xGuestCourse.setAttribute(A_CODE,       course.getCode());
        root.appendChild(xGuestCourse);
      }
    }
    // Append past semester information if available
    Iterator semesters = user.findSemesters().iterator();
    while (semesters.hasNext()) {
      Semester semester = (Semester) semesters.next();
      Element xSemester = xml.createElement(TAG_SEMESTER);
      xSemester.setAttribute(A_ID,   semester.toString());
      xSemester.setAttribute(A_NAME, semester.getName());
      root.appendChild(xSemester);
    }
    Profiler.exitMethod("XMLBuilder.buildOverview", "SemesterID: " + sem);
    return xml;
  }

  /**
   * Add all category short subtrees for the given course to xml
   * 
   * @param user
   * @param xml
   * @param course
   * @throws RemoteException
   */
  private void buildAllCategories(User user, Document xml,
      Course course) {
    Profiler.enterMethod("XMLBuilder.buildAllCategories", "CourseID: " + course.toString());
    Element root = (Element) xml.getFirstChild();
    Element xVisibleCtg = xml.createElement(TAG_VISIBLECTG);
    Element xHiddenCtg = xml.createElement(TAG_HIDDENCTG);
    root.appendChild(xVisibleCtg);
    root.appendChild(xHiddenCtg);
    Collection c = course.getCategories(user);
    Iterator i = c.iterator();
    int numOfCtg = 0;
    while (i.hasNext()) {
      Category ctg = (Category) i.next();
      if (user.getAuthoriznLevelByCourse(course) < ctg.getAuthLevel())
        continue;
      Element xCategory = categoryXMLBuilder.buildShortSubtree(xml, ctg);
      if (ctg.getHidden())
        xHiddenCtg.appendChild(xCategory);
      else {
        xVisibleCtg.appendChild(xCategory);
        numOfCtg++;
      }
    }
    xVisibleCtg.setAttribute(XMLBuilder.A_NUMOFCTGS, numOfCtg + "");
    Profiler.exitMethod("XMLBuilder.buildAllCategories", "CourseID: " + course.toString());
  }

  public Document buildStudentPrefsPage(User user, Course course) {
    Document xml = buildPageHeader(user);
    Element root = (Element) xml.getFirstChild();
    Element xCourse = courseXMLBuilder.buildGeneralSubtree(user, xml, course);
    xCourse.appendChild(courseXMLBuilder.buildAssignmentsSubtree(user, xml, course));
    root.appendChild(xCourse);
    Student student = course.getStudent(user);
    root.appendChild(getStudentPrefsElement(xml, student));
    return xml;
  }

  // for use later, to create "defaults for new courses" prefs page
  // public Document buildStudentPrefsPage(User user)
  // throws FinderException {
  // Document xml = buildPageHeader(user);
  // Element root = (Element) xml.getFirstChild();
  // Student student = database.studentHome().findByPrimaryKey(new
  // Student(-1, user.getUserID()));
  // root.appendChild(getStudentPrefsElement(xml, student));
  // return xml;
  // }

  private Element getStudentPrefsElement(Document xml,
      Student student) {
    Element xPrefs = xml.createElement(TAG_PREFS);
    if (student != null) {
      if (student.getEmailNewAssignment())
        xPrefs.setAttribute(A_PREF_NEWASSIGN, "true");
      if (student.getEmailDueDate())
        xPrefs.setAttribute(A_PREF_DATECHANGE, "true");
      if (student.getEmailGroup())
        xPrefs.setAttribute(A_PREF_INVITATION, "true");
      if (student.getEmailNewGrade())
        xPrefs.setAttribute(A_PREF_GRADERELEASE, "true");
      if (student.getEmailRegrade())
        xPrefs.setAttribute(A_PREF_GRADECHANGE, "true");
      if (student.getEmailFile())
        xPrefs.setAttribute(A_PREF_FILESUBMIT, "true");
      if (student.getEmailFinalGrade())
        xPrefs.setAttribute(A_PREF_FINALGRADES, "true");
      if (student.getEmailTimeSlot())
        xPrefs.setAttribute(A_PREF_TimeslotCHANGE, "true");
    }
    return xPrefs;
  }

  public Document buildStaffPrefsPage(User user, Course course) {
    Document xml = buildStaffNavbar(user, course);
    Element root = (Element) xml.getFirstChild();
    root.appendChild(courseXMLBuilder.buildGeneralSubtree(user, xml, course));
    Staff staff = course.getStaff(user);
    Element xPrefs = xml.createElement(TAG_PREFS);
    if (staff != null) {
      if (staff.getEmailNewAssign())
        xPrefs.setAttribute(A_PREF_NEWASSIGN, "true");
      if (staff.getEmailDueDate())
        xPrefs.setAttribute(A_PREF_DATECHANGE, "true");
      if (staff.getEmailAssignedTo())
        xPrefs.setAttribute(A_PREF_ASSIGNEDTO, "true");
      if (staff.getEmailFinalGrade())
        xPrefs.setAttribute(A_PREF_FINALGRADES, "true");
      if (staff.getEmailRequest())
        xPrefs.setAttribute(A_PREF_NEWREQUEST, "true");
    }
    root.appendChild(xPrefs);
    return xml;
  }

  /**
   * Builds the view for creating a new category
   * 
   * @param user
   * @param courseid
   * @return
   * @throws RemoteException
   */
  public Document buildNewCategoryPage(User user, Course course) {
    Document xml = buildStaffNavbar(user, course);
    Element root = (Element) xml.getFirstChild();
    Element xCategory = categoryXMLBuilder.buildDatatypesSubtree(xml);
    xCategory.setAttribute(A_ID,       "0");
    xCategory.setAttribute(A_ORDER,    "1");
    xCategory.setAttribute(A_AUTHORZN, ""+User.AUTHORIZATION_LEVEL_CORNELL_COMMUNITY);
    root.appendChild(xCategory);
    buildAllCategories(user, xml, course);
    return xml;
  }

  /**
   * Builds the view for editing an existing category
   * 
   * @param user
   * @param categoryID
   * @param columnList
   * @return
   * @throws RemoteException
   */
  public Document buildCategoryPage(User user, Category cat) {
    Document xml = buildPageHeader(user);
    Element root = (Element) xml.getFirstChild();
    Course course = cat.getCourse();
    root.appendChild(courseXMLBuilder.buildFullSubtree(user, xml, course));
    root.appendChild(categoryXMLBuilder.buildGeneralSubtree(user, xml, cat));
    return xml;
  }

  /**
   * Builds the full xml for the course containing the category and the full xml
   * for the category itself. This is used for editing the category layout as
   * well as for adding and editing contents.
   * 
   * @param user
   * @param categoryID
   * @return
   * @throws RemoteException
   */
  public Document buildCtgContentPage(User user, Category cat) {
    Document xml = buildPageHeader(user);
    Element root = (Element) xml.getFirstChild();
    Course course = cat.getCourse();
    root.appendChild(courseXMLBuilder.buildFullSubtree(user, xml, course));
    root.appendChild(categoryXMLBuilder.buildFullSubtree(user, xml, cat));
    return xml;
  }

  /**
   * Creates an xml document loaded with display data for a course view (student
   * or staff) page
   * 
   * @param user
   *          The User who is the student we're building the page for
   * @param courseid
   * @returnDocument an xml document with display data tags for the jsp page
   * @throws RemoteException
   */
  public Document buildCoursePage(User user, Course course) {
    Document xml = buildPageHeader(user);
    Element root = (Element) xml.getElementsByTagName(TAG_ROOT).item(0);
    root.appendChild(courseXMLBuilder.buildFullSubtree(user, xml, course));
    return xml;
  }

  public Document buildStaffNavbar(User user, Course course) {
    return buildStaffNavbar(user, course, false);
  }

  /**
   * Builds a XML skeleton with enough only enough information to fill in the
   * navigation bars for a staff member
   * 
   * @param user
   *          The principal loading the page
   * @param courseID
   *          The ID of the course whose navbars are being loaded
   * @param addStats
   *          Add statistics for each assignment to subtrees
   */
  public Document buildStaffNavbar(User user, Course course, boolean addStats) {
    Document xml = buildPageHeader(user);
    Element root = (Element) xml.getFirstChild();
    Element xCourse = courseXMLBuilder.buildBasicPropNode(user, xml, course);
    root.appendChild(xCourse);
    Iterator assigns = course.getAssignments().iterator();
    Element xAssigns = xml.createElement(TAG_ASSIGNMENTS);
    xCourse.appendChild(xAssigns);
    while (assigns.hasNext()) {
      Assignment assign = (Assignment) assigns.next();

      if (assign.getType() != Assignment.SURVEY) {
        Element xAssign = xml.createElementNS(
            TAG_ASSIGNMENT + assign.toString(),
            TAG_ASSIGNMENT);
        xAssigns.appendChild(xAssign);
        assignmentXMLBuilder.addBasicInfo(xml, xAssign, assign);
        if (addStats)
          assignmentXMLBuilder.addStatSubtree(xml, xAssign, assign);
      }

    }

    // generate the surveys node
    xCourse.appendChild(courseXMLBuilder.buildSurveySubtree(user, xml, course));

    if (user.isCategoryPrivByCourse(course)) {
      Element xCategories = xml.createElement(TAG_CATEGORIES);
      xCourse.appendChild(xCategories);
      Iterator cats = course.getCategories(user).iterator();
      while (cats.hasNext()) {
        Category cat = (Category) cats.next();
        if (user.getAuthoriznLevelByCourse(course) < cat.getAuthLevel())
          continue;
        Element xCategory = xml.createElement(TAG_CATEGORY);
        xCategories.appendChild(xCategory);
        xCategory.setAttribute(A_ID,   cat.toString());
        xCategory.setAttribute(A_NAME, cat.getCategoryName());
      }
    }
    return xml;
  }

  public Document refreshCoursePage(User u, Announcement announce) {
    return buildCoursePage(u, announce.getCourse());
  }

  /**
   * Looks up an assignment ID given the groupID, and then uses that ID to build
   * the proper assignment page. This method is used after updating group
   * information to reload the assignmetn page.
   * 
   * @param user
   *          User looking up this information
   * @param groupID
   *          The groupID of the group just updated
   * @return XML Document containg the requested information
   */
  public Document refreshStudentAssignmentPage(User user, Group group) {
    return buildStudentAssignmentPage(user, group.getAssignment());
  }

  /**
   * Builds an XML Document for the assignment properties page for creation of a
   * new assignment. All data is blank or default. The assignment element will
   * have an ID attribute of 0.
   * 
   * @param user
   *          User accessing this page
   * @param courseID
   *          ID of the course the assignment is being created in
   * @return XML Document
   * @throws RemoteException
   *           if the db connection fails
   */
  public Document buildAssignmentCreationPage(User user, Course course, int assignType) {
    Document xml = buildStaffNavbar(user, course);
    Element root = (Element) xml.getFirstChild();
    Element xAssignment = xml.createElement(TAG_ASSIGNMENT);
    xAssignment.setAttribute(A_MAXGROUP, "1");
    xAssignment.setAttribute(A_MINGROUP, "1");
    xAssignment.setAttribute(A_ASSIGNID, "0");
    xAssignment.setAttribute(A_STATUS, Assignment.HIDDEN);
    xAssignment.setAttribute(A_DUEDATE, DateTimeUtil.DATE.format(new Timestamp(
        System.currentTimeMillis() + 1000 * DateTimeUtil.SECS_PER_WEEK)));
    xAssignment.setAttribute(A_DUETIME, "11:59");
    xAssignment.setAttribute(A_DUEAMPM, "PM");
    if (assignType == Assignment.SURVEY) {
      xAssignment.setAttribute(A_TOTALSCORE, "0");
    } else {
      xAssignment.setAttribute(A_TOTALSCORE, "100");
    }
    xAssignment.setAttribute(A_WEIGHT, "1");
    xAssignment.setAttribute(A_SHOWSTATS, "true");
    xAssignment.setAttribute(A_ASSIGNTYPE, Integer.toString(assignType));
    // don't set A_USESCHEDULE; if it's set at all, scheduling is enabled
    Element xAsgnDescription = xml.createElement(TAG_DESCRIPTION);
    xAsgnDescription.appendChild(xml.createTextNode(""));
    xAssignment.appendChild(xAsgnDescription);
    xAssignment.appendChild(xml.createElement(TAG_CLARIFICATIONS));
    xAssignment.appendChild(xml.createElement(TAG_ITEMS));
    xAssignment.appendChild(xml.createElement(TAG_SUBMISSIONS));
    xAssignment.appendChild(xml.createElement(TAG_DESCRIPTION));
    xAssignment.appendChild(xml.createElement(TAG_SCHEDULE));
    root.appendChild(xAssignment);
    // Set known system file types
    root.appendChild(systemXMLBuilder.buildFiletypeListSubtree(xml));
    return xml;
  }

  /**
   * Builds the basic assignment information XML Tree, with no user-specific
   * data. Returns same as buildEmptyAssignmentView(), but with assignment data
   * filled in
   * 
   * @param user
   *          User accessing this page
   * @param assignID
   *          ID if the assignment to get info about
   * @return XML Document with assignment data
   * @throws RemoteException
   *           If the db connection fails
   */
  public Document buildBasicAssignmentPage(User user, Assignment assignment) {
    Course course = assignment.getCourse();
    // Build template tree
    Document xml = buildStaffNavbar(user, course);
    Element root = (Element) xml.getFirstChild();
    root.appendChild(systemXMLBuilder.buildFiletypeListSubtree(xml));
    root.appendChild(assignmentXMLBuilder.buildFullSubtree(user, xml, assignment, null));
    return xml;
  }

  /*
   * Builds an XML tree with information about an assignment, appropriate for
   * placement in a scheduling page.
   */
  public Document buildBasicSchedulePage(User user, Assignment assign) {
    Document xml = buildBasicAssignmentPage(user, assign);
    Course course = assign.getCourse();
    Element root = (Element) xml.getFirstChild();
    Element xCourse =
        (Element) XMLUtil.getChildrenByTagName(root, TAG_COURSE).item(0);
    xCourse.appendChild(courseXMLBuilder.buildStaffListSubtree(user, xml, course));
    root.appendChild(xCourse);
    return xml;
  }

  private Element buildCommentsSubtree(User user, Document xml, Group group, Assignment assignment) {
    String COMMENT         = "Grading Comment",
           REGRADE_REQUEST = "Regrade Request",
           REGRADE_REPLY   = "Regrade Response";
    Element comments = xml.createElement(XMLBuilder.TAG_COMMENTS);
    Collection cComment = group.getComments();
    Collection cRegrade = group.getRegradeRequests();
    Iterator iComment = cComment.iterator();
    Iterator iRegrade = cRegrade.iterator();
    HashSet regradeCommentIDs = new HashSet(); // collection of comment ID
                                               // that are regrade replies.
    // Merge regular grading comments and grading request under one history
    // in descending order
    Comment currentComment =
        iComment.hasNext() ? (Comment) iComment.next() : null;
    RegradeRequest currentRegrade =
        iRegrade.hasNext() ? (RegradeRequest) iRegrade.next() : null;
    Element comment = null;
    while (!(currentComment == null && currentRegrade == null)) {
      if (currentComment != null
          && (currentRegrade == null || currentComment.getDateEntered()
              .compareTo(currentRegrade.getDateEntered()) <= 0)) {
        comment = xml.createElement(XMLBuilder.TAG_COMMENT);
        if (regradeCommentIDs.contains(currentComment))
          comment.setAttribute(XMLBuilder.A_TYPE, REGRADE_REPLY);
        else comment.setAttribute(XMLBuilder.A_TYPE, COMMENT);
        comment.setAttribute(XMLBuilder.A_USER, currentComment.getUser().getNetID());
        comment.setAttribute(XMLBuilder.A_DATE, DateTimeUtil.DATE_TIME_AMPM
            .format(currentComment.getDateEntered()));
        comment.setAttribute(XMLBuilder.A_TEXT, currentComment.getComment());
        CommentFile commentFile = currentComment.getCommentFile();
        if (commentFile != null) {
          comment.setAttribute(XMLBuilder.A_COMMENTFILEID, commentFile.toString());
          comment.setAttribute(XMLBuilder.A_FILENAME,      commentFile.getFile().getName());
        }
        currentComment =
            iComment.hasNext() ? (Comment) iComment.next() : null;
      } else {
        comment = xml.createElement(XMLBuilder.TAG_COMMENT);
        comment.setAttribute(XMLBuilder.A_TYPE, REGRADE_REQUEST);
        comment.setAttribute(XMLBuilder.A_USER,
            currentRegrade.getUser().getNetID());
        comment.setAttribute(XMLBuilder.A_DATE,
            DateTimeUtil.DATE_TIME_AMPM.format(currentRegrade.getDateEntered()));
        comment.setAttribute(XMLBuilder.A_TEXT,
            StringUtil.formatNoHTMLString(currentRegrade.getRequest()));
        regradeCommentIDs.add(currentRegrade);
        SubProblem subProblem = currentRegrade.getSubProblem();
        if (subProblem != null) {
          // there is a subProblem associated with regrade, get the name
          comment.setAttribute(XMLBuilder.A_SUBPROBNAME, subProblem.getName());
        }
        currentRegrade = iRegrade.hasNext() ? (RegradeRequest) iRegrade.next()
                                            : null;
      }
      comments.appendChild(comment);
    }
    return comments;
  }

  public Document buildSurveyResultPage(User user, Assignment assignment) {
    // Build template tree
    Document xml = buildStaffNavbar(user, assignment.getCourse());
    Element root = (Element) xml.getFirstChild();
    root.appendChild(assignmentXMLBuilder.buildSurveyResultSubtree(xml, assignment));
    return xml;
  }

  private Collection shuffleCollectionElements(Collection elements) {
    int size = elements.size();
    int slot = size - 1;
    Random random = new Random();
    Object[] elems = elements.toArray();
    for (int i = 0; i < size; i++) {
      if (slot < 1) break;
      int target = random.nextInt(slot);
      Object temp = elems[slot];
      elems[slot] = elems[target];
      elems[target] = temp;
      slot--;
    }
    ArrayList al = new ArrayList();
    for (int i = 0; i < size; i++) {
      al.add(elems[i]);
    }
    return al;
  }

  // find the answer to subID in an answerSet and return the answer text
  private String findAnswer(SubProblem subProblem, AnswerSet answerSet) {
    Answer answer = answerSet.getAnswer(subProblem);
    if (answer == null)
      return "";
    
    String text   = answer.getText();
    Choice choice = subProblem.getChoice(text); 
    if (choice != null)
      return choice.getLetter() + ". " + choice.getText();
    else
      return text;
  }

  public Collection generateSurveyResultCSV(Assignment assignment) {
    ArrayList csv = new ArrayList();
    Collection answerSets = shuffleCollectionElements(assignment.getAnswerSets());
    String[] headerRow = new String[1];

    // adding the assignment name and number of submissions as header of this
    // csv file
    headerRow[0] = assignment.getName();
    csv.add(headerRow);

    Collection subproblems = assignment.getSubProblems();
    
    // adding the subproblems name to the header row
    int numEntries = subproblems.size();
    Iterator i = subproblems.iterator();
    String[] firstRow = new String[numEntries];
    TreeMap sidToTypeMap = new TreeMap();
    TreeMap sidToIndexMap = new TreeMap();

    // adding the subproblems name to the header row
    for (int count = 0; count < numEntries; count++) {
      SubProblem subproblem = (SubProblem) i.next();
      int type = subproblem.getType();
      sidToTypeMap.put(subproblem, new Integer(type));
      sidToIndexMap.put(subproblem, new Integer(count));
      firstRow[count] = subproblem.getName();
    }
    csv.add(firstRow);

    // adding all the answer sets
    Iterator j = answerSets.iterator();
    while (j.hasNext()) {
      AnswerSet answerSet = (AnswerSet) j.next();
      String[] row = new String[numEntries];

      // find the answer to each recorded subproblem in this answerSet
      Iterator subIterator = sidToTypeMap.keySet().iterator();
      while (subIterator.hasNext()) {
        SubProblem sp = (SubProblem) subIterator.next();
        Integer index = (Integer) sidToIndexMap.get(sp);
        String text = findAnswer(sp, answerSet);
        row[index.intValue()] = text;
      }

      csv.add(row);
    }
    return csv;
  }

  /**
   * Creates an XML document for the Student Assignment View page, containing
   * user-specific information.
   */
  public Document buildStudentAssignmentPage(User user, Assignment assignment) {
    // Build template tree
    Document xml = buildStaffNavbar(user, assignment.getCourse());
    Element root = (Element) xml.getFirstChild();

    Map answerMap = null;
    // Get answer data if this is a quiz or survey
    int assignType = assignment.getType();
    if (assignType == Assignment.QUIZ
        || assignType == Assignment.SURVEY) {
      Group group = assignment.findGroup(user);
      AnswerSet answerSet = assignment.findMostRecentAnswerSet(group);
      if (answerSet != null) {
        Iterator i = answerSet.getAnswers().iterator();
        answerMap = new HashMap();

        while (i.hasNext()) {
          Answer answer = (Answer) i.next();
          answerMap.put(answer.getSubProblem(), answer.getText());
        }
      }
    }

    // TODO don't need the full course tree
    // root.appendChild(CourseXMLBuilder.buildFullSubtree(user, xml,
    // database.courseHome().findByPrimaryKey(new Course(courseID))));
    root.appendChild(systemXMLBuilder.buildFiletypeListSubtree(xml));
    Element xAssignment =
        assignmentXMLBuilder.buildFullSubtree(user, xml, assignment, answerMap);
    root.appendChild(xAssignment);
    // Generate group branch
    Group group = null;
    if (user.isStudentInCourseByCourse(assignment.getCourse())) {
      group = assignment.findGroup(user);
    }
    if (group != null) {
      xAssignment.appendChild(groupXMLBuilder.buildFullSubtree(user, xml, group, assignment));
      xAssignment.appendChild(buildCommentsSubtree(user, xml, group, assignment));
    }
    // Generate grade
    Student student = assignment.getCourse().getStudent(user);
    Grade grade = assignment.findMostRecentGrade(student);
    if (grade != null && grade.getGrade() != null) {
      xAssignment.setAttribute(A_SCORE, grade.getGrade().toString());
    }
    return xml;
  }

  public Document buildGradeAssignPage(User user, Assignment assign) {
    return buildGradeAssignPage(user, assign, null, false);
  }

  public Document buildGradeAssignPage(User user, Assignment assign, boolean showGradeMsg) {
    return buildGradeAssignPage(user, assign, null, showGradeMsg);
  }

  public Document buildGradeAssignPage(User user, Assignment assign, Group group) {
    return buildGradeAssignPage(user, assign, group, false);
  }

  /**
   * Builds the grade students page for the assignment Returns
   * 
   * @param user
   *          User accessing this page
   * @param assignID
   *          ID if the assignment to get info about
   * @param groupID
   *          ???
   * @param showGradeMsg
   *          Whether the user selected "Grade" (if so, we should ask the user
   *          to select a group(s))
   * @return XML Document with grade data
   * @throws RemoteException
   *           If the db connection fails
   */
  public Document buildGradeAssignPage(User user, Assignment assign,
      Group group, boolean showGradeMsg) {
    Profiler.enterMethod("XMLBuilder.buildGradeAssignPage", "");
    Document xml = buildStaffNavbar(user, assign.getCourse());
    Element xRoot = (Element) xml.getFirstChild();
    if (showGradeMsg) xRoot.setAttribute(A_GRADEMSG, "true");
    if (group != null) {
      xRoot.setAttribute(A_JUMPTOGROUP, "cg" + group.toString());
    }
    xRoot.appendChild(assignmentXMLBuilder.buildGeneralSubtree(xml, assign, null));
    assignmentGroupsXMLBuilder.buildGroupGradingPage(user, assign, xml);
    Profiler.exitMethod("XMLBuilder.buildGradeAssignPage", "assignid=" + assign.toString());
    return xml;
  }

  /**
   * Builds the grade page for one student for the assignment Returns
   * 
   * @param user
   *          User accessing this page
   * @param assignID
   *          ID of the assignment to get info about
   * @param groupIDs
   *          List of all group IDs to get information about
   * @return XML Document with grade data
   * @throws RemoteException
   *           If the db connection fails
   */
  public Document buildGradePage(User user, Assignment assign, Collection groups) {
    Profiler.enterMethod("XMLBuilder.buildGradePage", "");
    Document xml = buildStaffNavbar(user, assign.getCourse());
    Element root = (Element) xml.getFirstChild();
    root.setAttribute(A_ASSIGNID, assign.toString());
    // Element xAssign = AssignmentXMLBuilder.buildGeneralSubtree(xml, assign,
    // null);
    // root.appendChild(xAssign);
    // Course course = database.courseHome().findByPrimaryKey(new
    // Course(assign.getCourseID()));
    // Element xCourse = CourseXMLBuilder.buildFullSubtree(user, xml, course);
    // root.appendChild(xCourse);
    gradingXMLBuilder.buildGradingSubtree(user, xml, assign, groups);
    Profiler.exitMethod("XMLBuilder.buildGradePage", "");
    return xml;
  }

  public Document buildGradeStudentPage(User user, Course course, User netID) {
    Document xml = null;
    xml = buildStaffNavbar(user, course);
    /*
     * Element root = (Element) xml.getFirstChild(); Course course =
     * database.courseHome().findByPrimaryKey(new Course(courseID)); Element
     * xCourse = CourseXMLBuilder.buildFullSubtree(user, xml, course);
     * root.appendChild(xCourse);
     */
    studentGradesXMLBuilder.buildStudentGradesTree(user, xml, netID, course);
    return xml;
  }

  /**
   * Generates an XML Document for the course properties page.
   * 
   * @param user
   *          The principal accessing this page (for header)
   * @param courseID
   *          The course whose properties to retrieve
   * @return The XML Document
   * @throws RemoteException
   *           If the db connection fails
   */
  public Document buildCoursePropertiesPage(User user, Course course) {
    Document xml = buildStaffNavbar(user, course);
    Element root = (Element) xml.getFirstChild();

    Element xCourse =
        (Element) XMLUtil.getChildrenByTagName(root, TAG_COURSE).item(0);
    courseXMLBuilder.addAccessInfo(course, xCourse);
    courseXMLBuilder.addCourseProperties(course, xCourse);
    xCourse.appendChild(courseXMLBuilder.buildStaffListSubtree(user, xml, course));
    return xml;
  }

  public Document buildStudentsPage(User user, Course course, boolean showAddDrop) {
    return buildStudentsPage(user, course, showAddDrop, false);
  }

  /**
   * Retrieve XML data for the students page
   * 
   * @param user
   *          User requesting this information
   * @param courseID
   *          ID of the course for which to get the list of enrolled students
   * @param showAddDrop
   *          Whether to show the Add/Drop section by default
   * @param showGradeMsg
   *          Whether or not to ask the user to select a student to grade
   * @return XML Document with students data
   * @throws RemoteException
   *           If the db connection fails
   */
  public Document buildStudentsPage(User user, Course course,
      boolean showAddDrop, boolean showGradeMsg) {
    Document xml = buildStaffNavbar(user, course, true);
    Element xRoot = (Element) xml.getFirstChild();
    Element xCourse = XMLUtil.getFirstChildByTagName(xRoot, TAG_COURSE);
    courseXMLBuilder.addTotalScoreStats(course, xCourse);
    courseXMLBuilder.addCourseProperties(course, xCourse);
    if (showAddDrop) xRoot.setAttribute(A_SHOWADDDROP, "true");
    if (showGradeMsg) xRoot.setAttribute(A_GRADEMSG, "true");
    viewStudentsXMLBuilder.buildStudentsPage(course, xml);
    return xml;
  }

  /**
   * The data value in result should be a List of String[]s, each representing
   * the values read from one row of a CSV file in the appropriate format. The
   * header row of the file should be the first entry in the List.
   * 
   * @param user
   * @param confirm_type
   *          XMLBuilder.CONFIRM_SOMETHINGOROTHER; ASSIGNINFO is for
   *          assignment-specific info and COURSEINFO is for course-specific
   *          info that doesn't fit into another confirm type
   * @param ID
   *          If confirm_type is ASSIGNINFO, an asgn ID; if confirm_type is
   *          COURSEINFO or FINALGRADES, a course ID; else ID isn't used, so
   *          doesn't matter
   * @param result
   * @throws FinderException,
   *           IllegalArgumentException
   */
  public Document buildConfirmPage(User user, int confirm_type,
      Object data, TransactionResult result) throws IllegalArgumentException {
    Document xml = buildPageHeader(user);
    Element root = (Element) xml.getFirstChild();
    root.setAttribute(A_CONFIRMTYPE, String.valueOf(confirm_type));
    if (confirm_type == CONFIRM_ASSIGNINFO) //assignment-specific operations
    {
      Assignment assign = (Assignment) data;
      Course course     = assign.getCourse();
      Element xCourse   = courseXMLBuilder.buildFullSubtree(user, xml, course);
      root.appendChild(xCourse);
      root.setAttribute(A_ASSIGNID, assign.toString());
    }
    else if (confirm_type == CONFIRM_COURSEINFO || confirm_type == CONFIRM_FINALGRADES) //course-specific operations
    {
      Course course = (Course) data;
      Element xCourse = courseXMLBuilder.buildFullSubtree(user, xml, course);
      root.appendChild(xCourse);
      root.setAttribute(A_COURSEID, course.toString());
    }

    List table = (List) result.getValue();
    if (table != null) for (int i = 0; i < table.size(); i++) {
      Element xRow = xml.createElementNS(TAG_ROW + i, TAG_ROW);
      root.appendChild(xRow);
      String[] row = (String[]) table.get(i);
      if (row[0] == null) // Error in row flag, just print line
      {
        Element xElement = xml.createElement(TAG_ELEMENT);
        xElement.setAttribute(A_DATA, row[1]);
        xElement.setAttribute(A_COLSPAN, String.valueOf(row.length));
        xRow.appendChild(xElement);
      } else {
        for (int j = 0; j < row.length; j++) {
          Element xElement = xml.createElement(TAG_ELEMENT);
          xElement.setAttribute(A_DATA, row[j]);
          xRow.appendChild(xElement);
        }
      }
    }

    if (result.hasErrors()) root.setAttribute(A_ERROR, "true");

    /*
     * The errors need to be in this specific format for this page, because they
     * are specific to each row, so the error XML written in addStatus is not
     * sufficient.
     */
    if (result.hasErrors()) {
      String message = "";
      Iterator i = result.getErrors().iterator();
      while (i.hasNext()) {
        TransactionError err = (TransactionError) i.next();
        if (err.getLocation() != 0) {
          // error locations start at 1, and here we're renumbering, so offset
          // by -1
          Element xRow =
              (Element) root.getElementsByTagNameNS(
                  TAG_ROW + (err.getLocation() - 1), TAG_ROW).item(0);
          if (xRow != null) {
            Element xError = xml.createElement(TAG_ERROR);
            xError.setAttribute(XMLBuilder.A_DATA, err.getError());
            xRow.appendChild(xError);
          }
        } else {
          message +=
              message.equals("") ? err.getError() : "<br>" + err.getError();
        }
      }
      root.setAttribute(A_ERROR, "true");
      xml = addStatus(xml, message, MSG_ERROR);
    }
    return xml;
  }

  /**
   * Reload values from submitted form into the assignment page. When an error
   * occurs in setting assignment properties, this will allow users to continue
   * with the form values they submitted instead of having to retype everything.
   */
  public Document buildErrorAssignmentPage(User user,
      Map params, Course course, Assignment assign) {
    Document xml = buildStaffNavbar(user, course);
    Map parameterMap = new HashMap();
    Vector oldSubIDs = new Vector(), hiddenOldSubIDs = new Vector();
    Vector newSubIDs = new Vector(), oldItemIDs = new Vector();
    Vector hiddenItemIDs = new Vector(), newItemIDs = new Vector();
    Vector probIDs = new Vector(), newProbIDs = new Vector();
    Vector hiddenProbIDs = new Vector();
    Map hiddenFileIDs = new HashMap();
    for (Iterator i = params.entrySet().iterator(); i.hasNext();) {
      Entry item = (Entry) i.next();
        String key = (String) item.getKey();
        if (key.startsWith(AccessController.P_REQFILETYPE)
            || key.startsWith(AccessController.P_HIDDENREQTYPE)
            || key.startsWith(AccessController.P_NEWREQFILETYPE)
            || key.startsWith(AccessController.P_HIDDENITEMFILEID)
            || key.startsWith(AccessController.P_HIDDENSOLID)) {
          // There is potentially more than one of these, so it maps to a Vector
          Vector v = (Vector) parameterMap.get(key);
          if (v == null) {
            (v = new Vector()).add(item.getValue());
            parameterMap.put(key, v);
          } else {
            v.add(item.getValue());
          }
        } else {
          parameterMap.put(key, item.getValue());
        }
        // Save the ID so we know to write a tag for it later
        if (key.startsWith(AccessController.P_REQFILENAME)) {
          oldSubIDs.add(key.substring(AccessController.P_REQFILENAME.length()));
        } else if (key.startsWith(AccessController.P_HIDDENREQNAME)) {
          hiddenOldSubIDs.add(key.substring(AccessController.P_HIDDENREQNAME
              .length()));
        } else if (key.startsWith(AccessController.P_NEWREQFILENAME)) {
          newSubIDs.add(key.substring(AccessController.P_NEWREQFILENAME
              .length()));
        } else if (key.startsWith(AccessController.P_ITEMNAME)) {
          oldItemIDs.add(key.substring(AccessController.P_ITEMNAME.length()));
        } else if (key.startsWith(AccessController.P_HIDDENITEMNAME)) {
          hiddenItemIDs.add(key.substring(AccessController.P_HIDDENITEMNAME
              .length()));
        } else if (key.startsWith(AccessController.P_NEWITEMNAME)) {
          newItemIDs
              .add(key.substring(AccessController.P_NEWITEMNAME.length()));
        } else if (key.startsWith(AccessController.P_SUBPROBNAME)) {
          probIDs.add(key.substring(AccessController.P_SUBPROBNAME.length()));
        } else if (key.startsWith(AccessController.P_NEWSUBPROBNAME)) {
          newProbIDs.add(key.substring(AccessController.P_NEWSUBPROBNAME
              .length()));
        } else if (key.startsWith(AccessController.P_HIDDENPROBNAME)) {
          hiddenProbIDs.add(key.substring(AccessController.P_HIDDENPROBNAME
              .length()));
        } else if (key.startsWith(AccessController.P_HIDDENITEMFILEID)) {
          String itemID =
              key.substring(AccessController.P_HIDDENITEMFILEID.length());
          String hiddenFileID = (String) item.getValue();
          Vector hs = (Vector) hiddenFileIDs.get(itemID);
          if (hs == null) {
            (hs = new Vector()).add(hiddenFileID);
            hiddenFileIDs.put(itemID, hs);
          } else {
            hs.add(hiddenFileID);
          }
        }
      
    }
    Element root = (Element) xml.getFirstChild();
    Element xAssignment = xml.createElement(TAG_ASSIGNMENT);
    root.appendChild(xAssignment);
    xAssignment.setAttribute(A_ASSIGNID, (String) parameterMap
        .get(AccessController.P_ASSIGNID));
    xAssignment.setAttribute(A_NAME, (String) parameterMap
        .get(AccessController.P_NAME));
    xAssignment.setAttribute(A_NAMESHORT, (String) parameterMap
        .get(AccessController.P_NAMESHORT));
    xAssignment.setAttribute(A_STATUS, (String) parameterMap
        .get(AccessController.P_STATUS));
    xAssignment.setAttribute(A_TOTALSCORE, (String) parameterMap
        .get(AccessController.P_TOTALSCORE));
    xAssignment.setAttribute(A_WEIGHT, (String) parameterMap
        .get(AccessController.P_WEIGHT));
    xAssignment.setAttribute(A_DUEDATE, (String) parameterMap
        .get(AccessController.P_DUEDATE));
    xAssignment.setAttribute(A_DUETIME, (String) parameterMap
        .get(AccessController.P_DUETIME));
    xAssignment.setAttribute(A_DUEAMPM, (String) parameterMap
        .get(AccessController.P_DUEAMPM));
    xAssignment.setAttribute(A_GRACEPERIOD, (String) parameterMap
        .get(AccessController.P_GRACEPERIOD));
    if (AccessController.ONE.equals(parameterMap.get(AccessController.P_LATEALLOWED)))
      xAssignment.setAttribute(A_LATEALLOWED, "true");
    xAssignment.setAttribute(A_LATEDATE, (String) parameterMap
        .get(AccessController.P_LATEDATE));
    xAssignment.setAttribute(A_LATETIME, (String) parameterMap
        .get(AccessController.P_LATETIME));
    xAssignment.setAttribute(A_LATEAMPM, (String) parameterMap
        .get(AccessController.P_LATEAMPM));
    if (parameterMap.containsKey(AccessController.P_SHOWSTATS))
      xAssignment.setAttribute(A_SHOWSTATS, "true");
    if (parameterMap.containsKey(AccessController.P_SHOWSOLUTION))
      xAssignment.setAttribute(A_SHOWSOLUTION, "true");
    if (AccessController.ONE.equals(parameterMap.get(AccessController.P_REGRADES)))
      xAssignment.setAttribute(A_STUDENTREGRADES, "true");
    xAssignment.setAttribute(A_REGRADEDATE, (String) parameterMap
        .get(AccessController.P_REGRADEDATE));
    xAssignment.setAttribute(A_REGRADETIME, (String) parameterMap
        .get(AccessController.P_REGRADETIME));
    xAssignment.setAttribute(A_REGRADEAMPM, (String) parameterMap
        .get(AccessController.P_REGRADEAMPM));
    if (AccessController.ONE.equals(parameterMap.get(AccessController.P_GROUPSBYTA)))
      xAssignment.setAttribute(A_ASSIGNEDGRADERS, "true");
    // Add description node
    Element xDescription = xml.createElement(TAG_DESCRIPTION);
    String desc = (String) parameterMap.get(AccessController.P_DESCRIPTION);
    xDescription.appendChild(xml.createTextNode(desc == null ? "" : desc));
    xAssignment.appendChild(xDescription);
    xAssignment.setAttribute(A_GROUPSVAL, (String) parameterMap
        .get(AccessController.P_GROUPS));
    xAssignment.setAttribute(A_MAXGROUP, (String) parameterMap
        .get(AccessController.P_GROUPSMAX));
    xAssignment.setAttribute(A_MINGROUP, (String) parameterMap
        .get(AccessController.P_GROUPSMIN));
    Element xSubmissions = xml.createElement(TAG_SUBMISSIONS);
    // Re-output properties of already existing required submissions
    for (int j = 0; j < oldSubIDs.size(); j++) {
      String ID = (String) oldSubIDs.get(j);
      Element xItem = xml.createElement(TAG_ITEM);
      xItem.setAttribute(A_ID, ID);
      xItem.setAttribute(A_NAME, (String) parameterMap
          .get(AccessController.P_REQFILENAME + ID));
      Vector v = (Vector) parameterMap.get(AccessController.P_REQFILETYPE + ID);
      int bound = v == null ? -1 : v.size();
      for (int k = 0; k < bound; k++) {
        Element xFormat = xml.createElement(TAG_FORMAT);
        xFormat.setAttribute(A_TYPE, (String) v.get(k));
        xItem.appendChild(xFormat);
      }
      xItem.setAttribute(A_SIZE, (String) parameterMap
          .get(AccessController.P_REQSIZE + ID));
      if (parameterMap.containsKey(AccessController.P_REMOVEREQ + ID))
        xItem.setAttribute(A_REMOVED, "true");
      xSubmissions.appendChild(xItem);
    }
    // Re-output properties of hidden already existing required submissions
    for (int j = 0; j < hiddenOldSubIDs.size(); j++) {
      String ID = (String) hiddenOldSubIDs.get(j);
      Element xItem = xml.createElement(TAG_HIDDENITEM);
      xItem.setAttribute(A_ID, ID);
      xItem.setAttribute(A_NAME, (String) parameterMap
          .get(AccessController.P_HIDDENREQNAME + ID));
      xItem.setAttribute(A_SIZE, (String) parameterMap
          .get(AccessController.P_HIDDENREQSIZE + ID));
      Vector v =
          (Vector) parameterMap.get(AccessController.P_HIDDENREQTYPE + ID);
      int bound = v == null ? -1 : v.size();
      for (int k = 0; k < bound; k++) {
        Element xFormat = xml.createElement(TAG_FORMAT);
        xFormat.setAttribute(A_TYPE, (String) v.get(k));
        xItem.appendChild(xFormat);
      }
      if (parameterMap.containsKey(AccessController.P_RESTOREREQ))
        xItem.setAttribute(A_RESTORED, "true");
      xSubmissions.appendChild(xItem);
    }
    // Re-output properties of newly added required submissions
    for (int j = 0; j < newSubIDs.size(); j++) {
      String ID = (String) newSubIDs.get(j);
      Element xItem = xml.createElement(TAG_NEWITEM);
      xItem.setAttribute(A_NAME, (String) parameterMap
          .get(AccessController.P_NEWREQFILENAME + ID));
      xItem.setAttribute(A_SIZE, (String) parameterMap
          .get(AccessController.P_NEWREQSIZE + ID));
      Vector v =
          (Vector) parameterMap.get(AccessController.P_NEWREQFILETYPE + ID);
      int bound = v == null ? -1 : v.size();
      for (int k = 0; k < bound; k++) {
        Element xFormat = xml.createElement(TAG_FORMAT);
        xFormat.setAttribute(A_TYPE, (String) v.get(k));
        xItem.appendChild(xFormat);
      }
      xSubmissions.appendChild(xItem);
    }
    xAssignment.appendChild(xSubmissions);
    // Re-output properties of already existing assignment items
    Element xItems = xml.createElement(TAG_ITEMS);
    for (int j = 0; j < oldItemIDs.size(); j++) {
      String ID = (String) oldItemIDs.get(j);
      Element xItem = xml.createElement(TAG_ITEM);
      xItem.setAttribute(A_NAME, (String) parameterMap
          .get(AccessController.P_ITEMNAME + ID));
      xItem.setAttribute(A_ID, ID);
      String filepath =
          (String) parameterMap.get(AccessController.P_HIDDENITEMFILE + ID);
      xItem.setAttribute(A_REPLACEITEMFILE, filepath == null ? "" : filepath);
      if (parameterMap.containsKey(AccessController.P_REMOVEITEM + ID))
        xItem.setAttribute(A_REMOVEITEM, "true");
      Element xFile = xml.createElement(TAG_FILE);
      xFile.setAttribute(A_NAME, (String) parameterMap
          .get(AccessController.P_ITEMFILENAME + ID));
      xItem.appendChild(xFile);
      Vector hFileIDs = (Vector) hiddenFileIDs.get(ID);
      int bound = hFileIDs == null ? -1 : hFileIDs.size();
      for (int k = 0; k < bound; k++) {
        String hFileID = (String) hFileIDs.get(k);
        Element xHiddenFile = xml.createElement(TAG_HIDDENFILE);
        xHiddenFile.setAttribute(A_ID, hFileID);
        xHiddenFile.setAttribute(A_DATE, (String) parameterMap
            .get(AccessController.P_HIDDENITEMFILEDATE + ID + "_" + hFileID));
        xHiddenFile.setAttribute(A_NAME, (String) parameterMap
            .get(AccessController.P_HIDDENITEMFILENAME + ID + "_" + hFileID));
        if (parameterMap.containsKey(AccessController.P_RESTOREITEMFILE + ID
            + "_" + hFileID))
          xHiddenFile.setAttribute(A_RESTOREITEMFILE, "true");
        xItem.appendChild(xHiddenFile);
      }
      xItems.appendChild(xItem);
    }
    for (int j = 0; j < hiddenItemIDs.size(); j++) {
      String ID = (String) hiddenItemIDs.get(j);
      Element xHiddenItem = xml.createElement(TAG_HIDDENITEM);
      xHiddenItem.setAttribute(A_ID, ID);
      xHiddenItem.setAttribute(A_NAME, (String) parameterMap
          .get(AccessController.P_HIDDENITEMNAME + ID));
      xHiddenItem.setAttribute(A_DATE, (String) parameterMap
          .get(AccessController.P_HIDDENITEMDATE + ID));
      if (parameterMap.containsKey(AccessController.P_RESTOREITEM + ID))
        xHiddenItem.setAttribute(A_RESTOREITEM, "true");
      xItems.appendChild(xHiddenItem);
    }
    for (int j = 0; j < newItemIDs.size(); j++) {
      String ID = (String) newItemIDs.get(j);
      Element xNewItem = xml.createElement(TAG_NEWITEM);
      xNewItem.setAttribute(A_ID, ID);
      xNewItem.setAttribute(A_NAME, (String) parameterMap
          .get(AccessController.P_NEWITEMNAME + ID));
      xNewItem.setAttribute(A_NEWITEMFILEPATH, (String) parameterMap
          .get(AccessController.P_NEWITEMFILE + ID));
      xItems.appendChild(xNewItem);
    }
    xAssignment.appendChild(xItems);
    if (parameterMap.containsKey(AccessController.P_SOLFILEID)) {
      Element xSol = xml.createElement(XMLBuilder.TAG_SOLFILE);
      String ID = (String) parameterMap.get(AccessController.P_SOLFILEID);
      xSol.setAttribute(A_ID, ID);
      xSol.setAttribute(A_FILENAME, (String) parameterMap
          .get(AccessController.P_SOLFILENAME));
      if (parameterMap.containsKey(AccessController.P_REMOVESOL))
        xSol.setAttribute(A_REMOVESOL, "true");
      xAssignment.appendChild(xSol);
    }
    xAssignment.setAttribute(A_SOLFILEPATH, (String) parameterMap
        .get(AccessController.P_SOLFILEPATH));
    Vector hiddenSolIDs =
        (Vector) parameterMap.get(AccessController.P_HIDDENSOLID);
    hiddenSolIDs = hiddenSolIDs == null ? new Vector() : hiddenSolIDs;
    for (int j = 0; j < hiddenSolIDs.size(); j++) {
      Element xHiddenSol = xml.createElement(TAG_HIDDENSOLFILE);
      String ID = (String) hiddenSolIDs.get(j);
      xHiddenSol.setAttribute(A_ID, ID);
      xHiddenSol.setAttribute(A_FILENAME, (String) parameterMap
          .get(AccessController.P_HIDDENSOLNAME + ID));
      if (parameterMap.containsKey(AccessController.P_RESTORESOL + ID))
        xHiddenSol.setAttribute(A_RESTORED, "true");
      xAssignment.appendChild(xHiddenSol);
    }
    for (int j = 0; j < probIDs.size(); j++) {
      Element xProb = xml.createElement(TAG_SUBPROBLEM);
      String ID = (String) probIDs.get(j);
      xProb.setAttribute(A_ID, ID);
      xProb.setAttribute(A_NAME, (String) parameterMap
          .get(AccessController.P_SUBPROBNAME + ID));
      xProb.setAttribute(A_TOTALSCORE, (String) parameterMap
          .get(AccessController.P_SUBPROBSCORE + ID));
      if (parameterMap.containsKey(AccessController.P_REMOVESUBPROB + ID))
        xProb.setAttribute(A_REMOVED, "true");
      xAssignment.appendChild(xProb);
    }
    for (int j = 0; j < newProbIDs.size(); j++) {
      Element xProb = xml.createElement(TAG_NEWPROB);
      String ID = (String) newProbIDs.get(j);
      xProb.setAttribute(A_SUBPROBNAME, (String) parameterMap
          .get(AccessController.P_NEWSUBPROBNAME + ID));
      xProb.setAttribute(A_SCORE, (String) parameterMap
          .get(AccessController.P_NEWSUBPROBSCORE + ID));
      xAssignment.appendChild(xProb);
    }
    for (int j = 0; j < hiddenProbIDs.size(); j++) {
      Element xProb = xml.createElement(TAG_HIDDENSUBPROB);
      String ID = (String) hiddenProbIDs.get(j);
      xProb.setAttribute(A_ID, ID);
      xProb.setAttribute(A_NAME, (String) parameterMap
          .get(AccessController.P_HIDDENPROBNAME + ID));
      xProb.setAttribute(A_TOTALSCORE, (String) parameterMap
          .get(AccessController.P_HIDDENPROBSCORE + ID));
      if (parameterMap.containsKey(AccessController.P_RESTORESUBPROB + ID))
        xProb.setAttribute(A_RESTORED, "true");
      xAssignment.appendChild(xProb);
    }
    if (parameterMap.containsKey(AccessController.P_USESCHEDULE))
      xAssignment.setAttribute(A_USESCHEDULE, "true");
    Element xSchedule = xml.createElement(TAG_SCHEDULE);
    xSchedule.setAttribute(A_TSDURATIONSTR, (String) parameterMap
        .get(AccessController.P_TSDURATIONSTR));
    xSchedule.setAttribute(A_TSMAXGROUPS, (String) parameterMap
        .get(AccessController.P_TSMAXGROUPS));
    xSchedule.setAttribute(A_SCHEDULE_LOCKDATE, (String) parameterMap
        .get(AccessController.P_SCHEDULE_LOCKDATE));
    xSchedule.setAttribute(A_SCHEDULE_LOCKTIME, (String) parameterMap
        .get(AccessController.P_SCHEDULE_LOCKTIME));
    xSchedule.setAttribute(A_SCHEDULE_LOCKAMPM, (String) parameterMap
        .get(AccessController.P_SCHEDULE_LOCKAMPM));
    xAssignment.appendChild(xSchedule);
    // Set known system file types
    root.appendChild(systemXMLBuilder.buildFiletypeListSubtree(xml));
    return xml;
  }

  /* Miscellaneous Access Methods */

  /**
   * Returns the root session bean
   * 
   * @return The root session bean
   */
  public CMSRoot getbase() {
    return database;
  }

  public CMSRoot getDatabase() {
    return database;
  }

}
