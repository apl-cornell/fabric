/*
 * Created on Jun 20, 2004
 */
package cms.www;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.text.ParseException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.util.Streams;
import org.w3c.dom.Document;

import cms.www.util.*;
import cms.www.xml.XMLBuilder;

import cms.model.*;

/**
 * @author Jon, Ray, Evan
 */

public class AccessController extends HttpServlet {
  /*
   * AccessController is the servlet. It serves two purposes: 1) It handles all
   * incoming HTTP Requests, by matching the action parameter in the request
   * against a list of predefined actions (see ProcessRequest). 2) It determines
   * permissions using Principal. Before performing any requested action, the
   * current Principal is checked to see if that action is allowed. Exception is
   * "overview", the default, since any user can access his overview page.
   */

  // Loaded from tSystemProperties during system startup
  public static boolean debug;
  public static int maxFileSize;

  /* URLs of all main JSPs in the system (others are included JSP fragments) */
  public static final String ASSIGNADMIN_URL = "/staff/assignment/assignment.jsp", // assignment
                                                                                    // creation/edit
                                                                                    // page
      ASSIGNLISTADMIN_URL = "/staff/assignment/assignmentlist.jsp", // staff
                                                                    // view of
                                                                    // just the
                                                                    // assignment
                                                                    // list
      ASSIGNMENT_URL = "/student/assignment.jsp", // student assignment view,
                                                  // allowing to select a
                                                  // timeslot or submit a file
      ASSIGNSCHED_URL = "/staff/assignment/schedule.jsp", // schedule edit page
                                                          // (create/remove/assign
                                                          // timeslots)
      CATEGORYADMIN_URL = "/staff/category/categorytemplate.jsp", // category
                                                                  // template
                                                                  // edit page;
                                                                  // can also
                                                                  // allow
                                                                  // editing of
                                                                  // category
                                                                  // order for a
                                                                  // course
      CMSADMIN_URL = "/cmsadmin/cmsadmin.jsp", // main cms admin page
      CMSADMIN_LOGRESULTS_URL = "/cmsadmin/cmsadmin-logsearchresults.jsp", // cms
                                                                            // admin
                                                                            // log
                                                                            // search
                                                                            // results
                                                                            // page
                                                                            // for
                                                                            // a
                                                                            // given
                                                                            // course
      COURSE_URL = "/student/course.jsp", // student view of main course page
      COURSEADMIN_URL = "/staff/course.jsp", // staff view of main course page
      COURSEPROPS_URL = "/staff/courseprops.jsp", // the course-admin view,
                                                  // showing all course
                                                  // properties
      CMSADMINCOURSEPROPS_URL = "/cmsadmin/cmsadmincourseprops.jsp", // those
                                                                      // course
                                                                      // properties
                                                                      // that a
                                                                      // cms
                                                                      // admin
                                                                      // is
                                                                      // allowed
                                                                      // to edit
      COURSELOG_URL = "/staff/log.jsp", // course staff log search and results
                                        // page
      CTGCONTENTSADMIN_URL = "/staff/category/categorycontents-addNedit.jsp", // add
                                                                              // and/or
                                                                              // edit
                                                                              // contents
                                                                              // for
                                                                              // a
                                                                              // given
                                                                              // category
      EDITANNOUNCE_URL = "/staff/announcement.jsp", // announcement edit page,
                                                    // showing previous versions
      ERROR_URL = "/errorpage.jsp", FORBIDDEN_URL = "/forbidden.jsp", // "Your
                                                                      // attempted
                                                                      // action
                                                                      // is
                                                                      // forbidden"
      FINALGRADES_URL = "/staff/grading/finalgrades.jsp", // list of students,
                                                          // their
                                                          // administrative data
                                                          // and final grades,
                                                          // with an option to
                                                          // export the table
      GRADEALLASSIGNS_URL = "/staff/grading/gradeallassignments.jsp", // a
                                                                      // single
                                                                      // student's
                                                                      // grades
                                                                      // for all
                                                                      // assignments
                                                                      // in a
                                                                      // given
                                                                      // course
      GRADEASSIGN_URL = "/staff/grading/gradeassign.jsp", // main grading page,
                                                          // listing students
                                                          // and grades for a
                                                          // single assignment
      GRADESTUDENTS_URL = "/staff/grading/gradestudents.jsp", // detailed
                                                              // grading page,
                                                              // with boxes for
                                                              // one or more
                                                              // groups for a
                                                              // given
                                                              // assignment
      STUDENTPREFS_URL = "/student/notifications.jsp", // student preferences
                                                        // page
      STAFFPREFS_URL = "/staff/notifications.jsp", // staff preferences page
      HOMEPAGE_URL = "/homepage.jsp", // cms welcome and login page
      OVERVIEW_URL = "/overall.jsp", // main view for any user
      PRINTSCHED_URL = "/staff/assignment/schedule-print.jsp", // printer-friendly
                                                                // list of
                                                                // timeslots for
                                                                // a given
                                                                // assignment
      PROFILER_URL = "/profiler.jsp", // results of last several requests from
                                      // Jon's profiling code
      SIGNOUT_URL = "/signout.jsp", // goodbye, fly with CMS again
      STUDENTS_URL = "/staff/students.jsp", // student table, listing names and
                                            // grades, with an option to export
                                            // the table
      SURVEYRESULT_URL = "/staff/assignment/survey-result.jsp", // display
                                                                // result of a
                                                                // survey
      CONFIRMTABLE_URL = "/staff/grading/confirmtable.jsp", // course admin view
                                                            // to confirm
                                                            // correct parsing
                                                            // of an uploaded
                                                            // class list, CUID
                                                            // map file or final
                                                            // grades file
      EMAIL_URL = "/staff/email.jsp", // view e-mail archives for a course
                                      // and/or send a new message
      UPLOAD_URL = "/staff/upload_csvinfo.jsp"; // view page to upload

  // general CSV file

  /* Action names */
  public static final String
  // Log-in/-out actions
      ACT_OVER = "overview", // View cms overview page, and leave view-as mode
                              // if in it
      ACT_OVERLOGIN = "loginview", // If user is only involved with one course,
                                    // go to its main page; else go to overview
      ACT_SIGNOUT = "signout", // Log out of cms
      // CMS admin actions
      ACT_CMSADMIN = "cmsadmin", // View cmsadmin page
      ACT_ADDCMSADMIN = "addcmsadmin", // Add to list of cms admins
      ACT_REMOVECMSADMIN = "removecmsadmin", // Remove from list of cmsadmins
      ACT_CREATESEMESTER = "createsemester", // Create new semester from
                                              // drop-down list
      ACT_SETCURSEMESTER = "setcursemester", // Change default ("current")
                                              // system semester
      ACT_EDITSEMESTER = "editsemester", // Edit the properties of a semester
                                          // (currently, can hide/unhide)
      ACT_ADDCOURSE = "addcourse", // Create course in current semester
      ACT_CMSADMINCOURSEPROPS = "cmsadmincourseprops", // View cmsadmin
                                                        // course-properties
                                                        // page (can set course
                                                        // name, staff
                                                        // permissions)
      ACT_FINALGRADES = "finalgrades", // View administrative data and final
                                        // grades for all students in a given
                                        // course
      ACT_EDITNOTICE = "editnotice", // Edit/delete a sitewide notice
      ACT_NEWNOTICE = "newnotice", // Add a sitewide notice
      // Student actions
      ACT_ASSIGN = "assignment", // View student-side assignment page
      ACT_SURVEY = "survey", // View student-side assignment page
      ACT_QUIZ = "quiz", // View student-side assignment page
      ACT_COURSE = "course", // View the student-side course page
      ACT_FILESUBMIT = "filesubmit", // Submit one or more files for a given
                                      // assignment
      ACT_SURVEYSUBMIT = "surveysubmit", // Submit a survey
      ACT_SURVEYRESULT = "surveyresult", // Display survey result
      ACT_SURVEYDOWNLOAD = "surveydownload", // Download survey result
      ACT_DOWNLOAD = "download", // Download any one file from the server (the
                                  // file type is specified as a parameter)
      ACT_REQUESTREGRADE = "requestregrade", // Request a regrade for a certain
                                              // assignment or one or more
                                              // subproblems within a single
                                              // assignment
      // Group actions
      ACT_ACCEPT = "accept", // Accept an invitation to join a group
      ACT_DECLINE = "decline", // Decline an invitation to join a group
      ACT_INVITE = "invite", // Invite someone to your group
      ACT_CANCEL = "cancel", // Cancel an invitation
      ACT_LEAVE = "leave", // Leave your current group
      // Course staff actions
      ACT_ADDSTUDENTPAGE = "addstudentpage", // View add-student page
                                              // (currently same as student-list
                                              // page)
      ACT_ADDSTUDENTS = "addstudents", // Add one or more students
      ACT_DROP = "drop", // Drop a single student
      ACT_DROPSTUDENTS = "dropstudents", // Drop multiple students
      ACT_REENROLL = "reenroll", // Reenroll one student in a given course
      ACT_COURSEADMIN = "courseadmin", // View staff-side main course page
      ACT_COURSEPROPS = "courseprops", // View course properties page
      ACT_EDITCOURSEDESCRIPTION = "editcoursedescription", // Edit course
                                                            // description
      ACT_RESTOREANNOUNCE = "restoreannounce", // Restore removed announcement
      ACT_ASSIGNADMIN = "assignadmin", // View assignment edit page
      ACT_ASSIGNLISTADMIN = "assignlist", // View staff-side list of assignments
                                          // (same format as on main course
                                          // page)
      ACT_NEWASSIGN = "newassign", // View assignment creation page (edit page
                                    // without info filled in)
      ACT_NEWSURVEY = "newsurvey", // View survey creation page (edit page
                                    // without info filled in)
      ACT_SURVEYADMIN = "surveyadmin", // View survey edit page
      ACT_SURVEYLISTADMIN = "assignlist", // View staff-side list of surveys
      ACT_NEWQUIZ = "newquiz", // View quiz creation page (edit page without
                                // info filled in)
      ACT_QUIZADMIN = "quizadmin", // View survey edit page
      ACT_QUIZLISTADMIN = "assinglist", // View staff-side list of surveys
      ACT_STUDENTS = "students", // View student-list page
      ACT_SETCOURSEPROPS = "setcourseprops", // Set course properties, either
                                              // as course staff or as cmsadmin
      ACT_NEWANNOUNCE = "newannounce", // Post a new announcement
      ACT_SETANNOUNCE = "setannounce", // Edit an announcement
      ACT_SETASSIGN = "setassign", // Create/edit assignment
      ACT_REMOVEASSIGN = "removeassign", // Remove an assignment
      ACT_RESTOREASSIGN = "restoreassign", // Restore an assignment
      ACT_UPLOADSTUDENTINFO = "uploadstudentinfo", // Upload general csv-format
                                                    // student information
                                                    // (course admin or
                                                    // cmsadmin)
      ACT_CONFIRMSTUDENTINFO = "confirmstudentinfo", // View page to confirm
                                                      // upload of student info
                                                      // (course admin or
                                                      // cmsadmin)
      ACT_EXPORTSTUDENTINFOTEMPLATE = "exportcsvtemplate", // Export a full
                                                            // template with all
                                                            // columns we'll
                                                            // accept back on an
                                                            // upload
      ACT_VIEWUPLOAD = "viewuploadpage", // View the general-CSV upload page
      // Enter different view modes (ie levels of privilege)
      ACT_VIEWRESET = "resetView", // Reset view-as mode to none
      ACT_VIEWSTUDENT = "viewStud", // Masquerade as a student
      ACT_VIEWCORNELLMEM = "viewCUmemb", // Masquerade as an Upstanding Member
                                          // of the Cornell Community
      ACT_VIEWGUEST = "viewGuest", // Masquerade as an extraCornellian guest
      // Grading actions
      ACT_SETFINALGRADES = "setfinalgrades", // Set final grades for one or
                                              // more students in a given course
                                              // by means of the html form on
                                              // the grading page (NOT an
                                              // uploaded file)
      ACT_FINALGRADESTEMPLATE = "finalgradestemplate", // Download (FROM
                                                        // server) template with
                                                        // NetID and final grade
                                                        // columns for a given
                                                        // course
      ACT_FINALGRADESFILE = "finalgradesfile", // Upload (TO server) a CSV file
                                                // with NetIDs and final grades
                                                // for a given course
      ACT_CONFIRMFINALGRADES = "confirmfinalgrades", // After upload of a final
                                                      // grades file, a staff
                                                      // with grades privilege
                                                      // must confirm the grades
                                                      // before the database is
                                                      // updated
      ACT_EXPORTTABLE = "exporttable", // Download (FROM server) student table
                                        // as a CSV file
      ACT_EXPORTRUBRIC = "exportrubric", // Download (FROM server) table of
                                          // assignment max scores and weights
      ACT_EXPORTGRADESTABLE = "exportgradestable", // Download (FROM server)
                                                    // table of NetIDs and
                                                    // grades for a given
                                                    // assignment as a CSV file
      ACT_EXPORTFINALGRADES = "exportfinalgrades", // Download (FROM server)
                                                    // table of NetIDs and final
                                                    // grades for a given course
                                                    // as a CSV file
      ACT_APPLYTOGROUPS = "applytogroups", // One of several grading page
                                            // actions: grade one or more groups
                                            // on a separate page; download
                                            // submitted files for one or more
                                            // groups; grant or change an
                                            // extension; assign grader(s) to
                                            // group(s)
      ACT_GRADEASSIGN = "gradeassign", // View grading page for all students in
                                        // a given course for one assignment
      ACT_GRADESTUDENTS = "gradestudents", // View grading page for one student
                                            // for one assignment
      ACT_SETSTUDENTGRADES = "setstudentgrades", // Set grades/comments for any
                                                  // number of groups for a
                                                  // given assignment
      ACT_SETSTUDENTALLGRADES = "setstudentallgrades", // Set all grades for a
                                                        // given student in a
                                                        // given course
      ACT_STUDENTALLGRADES = "studentallgrades", // View all grades for a given
                                                  // student in a given course
      ACT_UNGROUP = "ungroup", // Remove all students in one specific group to
                                // singlet groups for a given assignment
      ACT_UPLOADGRADES = "uploadgrades", // Upload (TO server) grades table CSV
                                          // file for a single assignment
      ACT_CONFIRMTABLE = "confirmtable", // After upload of a grades table
                                          // file, a staff with grades privilege
                                          // must confirm the grades before the
                                          // database is updated
      ACT_GROUPFILES = "groupfiles", // Download (FROM server) most recent
                                      // submissions for a given group for a
                                      // given assignment
      ACT_REMOVEEXTENSION = "removeextension", // Remove an extension for a
                                                // given group for a given
                                                // assignment
      // Category admin
      ACT_NEWCATEGORY = "newcategory", // View the category creation/reorder
                                        // page for a course
      ACT_EDITCTG = "editcategory", // View the template/general properties edit
                                    // page for a category
      ACT_REMOVEROW = "remvctgrow", // Remove one category row (can be done from
                                    // the main course page)
      ACT_ADDNEDITCONTENTS = "addNeditctnt", // View the add/edit contents page
                                              // for a category
      ACT_SETCATEGORY = "setctg", // Submit either the template edit page for an
                                  // existing category or the page to create
                                  // and/or reorder categories for a course
      ACT_SETADDNEDITCONTENTS = "setaddNeditctnt", // Submit the add/edit
                                                    // contents page for a
                                                    // category
      // Log searching
      ACT_SEARCHLOGS_CMSADMIN = "searchlogs_cmsadmin", // Search as a cms admin
      ACT_SEARCHLOGS_COURSE = "searchlogs_course", // Search as a course admin
      ACT_COURSE_LOGSEARCH = "courselogsearch", // View the course-admin
                                                // log-search page
      // E-mail actions
      ACT_EMAIL = "email", // View course e-mail page
      ACT_SENDEMAIL = "sendemail", // Send e-mail as a course staff
      // User preferences
      ACT_STAFFPREFS = "staffprefs", // View the staff preferences page
                                      // (currently only notification options)
      ACT_STUDENTPREFS = "studentprefs", // View the student preferences page
                                          // (currently only notification
                                          // options)
      ACT_SETSTUDENTPREFS = "setstudentprefs", // Set student preferences
      ACT_SETSTAFFPREFS = "setstaffprefs", // Set staff preferences
      // Scheduling actions
      ACT_SCHEDULE = "schedule", // A staff member accesses the staff schedule
                                  // page
      ACT_TIMESLOTASSIGN = "timeslotassign", // A group is added to a timeslot
                                              // by a staff member
      ACT_TIMESLOTUNASSIGN = "timeslotunassign", // A group is removed from a
                                                  // timeslot by a staff member
      ACT_TIMESLOTSELECT = "timeslotselect", // A group member adds his/her
                                              // group to a timeslot
      ACT_TIMESLOTUNSELECT = "timeslotunselect", // A group member removes
                                                  // his/her group from a
                                                  // timeslot
      ACT_TIMESLOTSCREATE = "timeslotscreate", // A set of consecutive
                                                // timeslots is created
      ACT_TIMESLOTSDELETE = "timeslotsdelete", // A set of consecutive
                                                // timeslots is deleted
      ACT_TIMESLOTSUPDATE = "timeslotsupdate", // ...updated
      ACT_PRINTSCHEDULE = "printschedule", // A staff member requests a
                                            // printable version of the schedule
      // Profiler actions
      ACT_PROFILER = "profiler"; // Call up profiler output page

  /* Request parameters */
  public static final String
  /* multipurpose */
  P_ACTION = "action", // action string (see just above)
      P_ID = "id", //
      P_NETID = "netid", // a string
      P_COURSEID = "courseid", // a number
      P_CATID = "categoryid", // a number
      P_GROUPID = "groupid", // a number
      P_ASSIGNID = "assignid", // a number
      P_NAME = "name", // used for various things
      P_DOWNLOADTYPE = "downloadtype", // used in assignment and category
                                        // displays: one of the T_*FILE
                                        // constants in XMLBuilder
      P_DEBUGID = "debugid", // netID of user to switch to; used in debug mode
      P_RESET = "reset", // when going to a main course page, if P_RESET shows
                          // up, masquerading ends
      P_SUBMIT = "submit", // used on several grading pages for submit buttons
      P_CHARACTERSLEFT = "charactersleft", // the number of characters left
                                            // that can be input into a textarea
      /* cms overview page */
      P_SEMESTERID = "semesterid", // choose a semester to look at
      /* cmsadmin pages */
      P_HIDDEN = "hidden", // for semesters
      P_FIRSTNAME = "firstname", // used in cms admin adding user names where
                                  // we couldn't get them from the LDAP server
      P_LASTNAME = "lastname", // ditto
      P_CODE = "code", // used in cmsadmin and courseprops pages, for a course
      P_COURSENAME = "coursename", // used in cmsadmin pages
      P_NOTICETEXT = "noticetext", // for notices
      P_DELNOTICE = "delnotice", // to delete a notice
      P_NOTICEEXPDATE = "noticeexpdate", // notice expiration date
      P_NOTICEEXPTIME = "noticeexptime",
      P_NOTICEEXPAMPM = "noticeexpAMPM",
      /* main course page */
      P_CTGPOSITION = "ctgpos", // order of category in list of all ctgs for a
                                // course
      P_REMOVEANNOUNCE = "removeannounce", //
      /* course-admin courseprops page */
      P_DISPLAYEDCODE = "displayedcode", // the code displayed for a course
                                          // (may be, eg, CS211 where real
                                          // course code is COM S 211)
      P_DESCRIPTION = "description", // course or assignment description string
      P_FREEZECOURSE = "freezecourse", // used in courseprops page
      P_FINALGRADES = "showfinalgrades", // used in courseprops page
      P_HASSECTION = "hassection", // used in courseprops page
      P_SHOWTOTALSCORES = "showtotalscores", // used in courseprops page
      P_SHOWASSIGNWEIGHTS = "showassignweights", // used in courseprops pages
      P_SHOWGRADERID = "showgradernetid", // used in courseprops pages
      // guest permissions
      P_COURSEGUESTACCESS = "courseGA", // "checked" or not in request
      P_ASSIGNGUESTACCESS = "assignGA", // "checked" or not in request
      P_ANNOUNCEGUESTACCESS = "announceGA", // "checked" or not in request
      P_SOLUTIONGUESTACCESS = "solutionGA", // "checked" or not in request
      // Cornell community permissions
      P_COURSECCACCESS = "courseCC", // "checked" or not in request
      P_ASSIGNCCACCESS = "assignCC", // "checked" or not in request
      P_ANNOUNCECCACCESS = "announceCC", // "checked" or not in request
      P_SOLUTIONCCACCESS = "solutionCC", // "checked" or not in request
      // suffixes appended to netIDs for staff permissions
      P_REMOVE = "_remove", // remove staff
      P_ISADMIN = "_admin", // set permissions
      P_ISGROUPS = "_groups", // (each time through the courseprops page,
      P_ISGRADES = "_grades", // the permissions submitted as parameters
      P_ISASSIGN = "_assign", // are given; take away a permission by not
      P_ISCATEGORY = "_category", // submitting the corresponding parameter)
      // prefixes for new staff members
      P_NEWNETID = "newnetid_", // each of these is followed by a netID
      P_NEWADMIN = "newadmin_", //
      P_NEWGROUPS = "newgroups_", //
      P_NEWGRADES = "newgrades_", //
      P_NEWASSIGN = "newassign_", //
      P_NEWCATEGORY = "newcategory_", //
      /* announcement edit page */
      P_ANNOUNCE = "announce", // used in announcement edit page for
                                // announcement text
      P_EDIT = "edit", // P_TRUE or P_FALSE for whether a change happened (is
                        // hardcoded in header.js, Oct 2007)
      P_TRUE = "true", // only used as a value for P_EDIT (is hardcoded in
                        // header.js, Oct 2007)
      P_FALSE = "false", // ditto
      /* assignment edit page */
      P_NAMESHORT = "nameshort", // used in assignment edit page for short name
                                  // text
      P_GRACEPERIOD = "graceperiod", // used in assignment edit page for number
                                      // of minutes
      P_LATEALLOWED = "lateallowed", // used in assignment edit page; value is
                                      // ZERO or ONE
      P_STATUS = "status", // used in assignment edit page:
                            // AssignmentBean.HIDDEN | OPEN | CLOSED | GRADED
      // Assignment due date
      P_DUEDATE = "duedate", // used in assignment edit page and main grading
                              // page
      P_DUETIME = "duetime", // ditto
      P_DUEAMPM = "dueampm", // ditto Assignment late deadline
      P_LATEDATE = "latedate", // used in assignment edit page
      P_LATETIME = "latetime", // ditto
      P_LATEAMPM = "lateampm", // ditto Assignment regrade date
      P_REGRADEDATE = "regradesdate", // used in assignment edit page
      P_REGRADETIME = "regradestime", // ditto
      P_REGRADEAMPM = "regradesampm", // ditto
      P_REGRADES = "regrades", // whether online regrades are allowed: ZERO or
                                // ONE
      P_GROUPS = "groups", // group option: no groups; staff-set groups;
                            // student-set groups
      P_GROUPSFROM = "groupsfrom", // asgn  of assignment from which to
                                    // migrate groups
      P_GROUPSBYTA = "groupsbyta", // whether staff can grade groups not
                                    // assigned to them
      P_GROUPSMAX = "groupsmax", // max size of student-formed groups
      P_GROUPSMIN = "groupsmin", // min size of student-formed groups
      P_SUBMITTEDFILE = "submittedfile", // download a submitted file for a
                                          // given group and subproblem
      P_TOTALSCORE = "totalscore", // max score for an assignment
      P_WEIGHT = "weight", // weight given to an assignment
      P_SHOWSTATS = "showstats", // assignment edit page: whether to show stats
                                  // to students once graded
      P_SHOWSOLUTION = "showsolution", // assignment edit page: whether to make
                                        // solution available to all students
      // prefixes for new required file submissions
      P_NEWREQFILENAME = "newreqfilename_", //
      P_NEWREQFILETYPE = "newreqfiletype_", //
      P_NEWREQSIZE = "newreqsize_", // prefixes for modifying old required file
                                    // submissions
      P_REQFILENAME = "reqfilename_", //
      P_REQFILETYPE = "reqfiletype_", //
      P_REQSIZE = "reqsize_", // deleting and restoring required files
      P_REMOVEREQ = "removereq_", //
      P_RESTOREREQ = "restorereq_", // new assignment files
      P_NEWITEMNAME = "newitemname_", //
      P_NEWITEMFILE = "newitemfile_", // modifying old assignment files
      P_ITEMNAME = "itemname_", //
      P_ITEMFILE = "itemfile_", // A file for this assignment item
      // deleting and restoring assignment files
      P_REMOVEITEM = "removeitem_", //
      P_RESTOREITEM = "restoreitem_", // format: restoreitemfile_ITEMID_FILEID
      P_RESTOREITEMFILE = "restoreitemfile_", // new subproblems
      P_NEWSUBPROBNAME = "newprobname_", //
      P_NEWSUBPROBSCORE = "newprobscore_", // old subproblems
      P_SUBPROBNAME = "probname_", //
      P_SUBPROBSCORE = "probscore_", //
      P_RESTORESUBPROB = "restoreprob_", //
      P_REMOVESUBPROB = "removeprob_", // new questions
      P_REMOVECHOICE = "removechoice_",
      P_NEWSUBPROBTYPE = "newprobtype_",
      P_NEWSUBPROBORDER = "newproborder_",
      P_NEWCHOICE = "newchoice_", //
      P_NEWCORRECTCHOICE = "newcorrectchoice_", // old questions
      P_SUBPROBTYPE = "probtype_",
      P_SUBPROBORDER = "proborder_",
      P_CHOICE = "choice_", //
      P_CORRECTCHOICE = "correctchoice_", // survey creation
      P_ASSIGNMENTTYPE = "assignmenttype",
      P_FILENAME = "filename",
      // solution files
      P_SOLFILE = "solfile", //
      P_REMOVESOL = "removesol", //
      P_RESTORESOL = "restoresol", // schedule
      P_USESCHEDULE = "useschedule", // whether enabled; "checked" or not
                                      // present
      P_TSMAXGROUPS = "tsmaxgroups", // max groups per time per staff member
      P_TSDURATIONSTR = "tsdurationstr", // timeslot duration in hh:mm:ss
                                          // format
      P_SCHEDULE_LOCKDATE = "tslockdate", // deadline for students to change
                                          // their assigned slots
      P_SCHEDULE_LOCKTIME = "tslocktime", // ditto
      P_SCHEDULE_LOCKAMPM = "tslockampm", // ditto
      /* student table */
      P_FINALGRADE = "finalgrade_", // prepended to a netID, used to set that
                                    // student's final grade
      // A list of students and its submit button
      P_STUDENTSLIST = "studentslist", // a list of netIDs to be
                                        // enrolled/dropped
      P_ADDSTUDENTSLIST = "addstudentslist", // the submit button for that list
                                              // of netIDs
      // A file of new students and its submit button
      P_STUDENTSFILE = "studentsfile", // a file of netIDs to be enrolled
      P_ADDSTUDENTSFILE = "addstudentsfile", // the submit button for that file
      // E-mail notifications for added students
      P_EMAILADDED = "emailadded", // checkbox for whether to e-mail (both on
                                    // add/drop and grade table)
      /* confirm-info-upload page */
      P_UPLOADEDCSV = "uploadedcsvfile", // actual file to upload
      P_ISCLASSLIST = "isclasslist", // included to require classlist file
                                      // format
      /* category edit-template, reordering and creation page */
      // editing category template
      P_CTGNAME = "ctgname", //
      P_COLSORTBY = "colsortby", // column id to sort on
      P_ORDER = "order", // whether to sort ASCENDING or DESCENDING
      P_NUMSHOWITEMS = "numshowitems", // default number of rows to display
      P_AUTHORZN = "authorzn", // who can see the category: one of the
                                // Principal.AUTHOR_* constants
      // editing a column
      P_COLNAME = "colname", //
      P_COLPOSITION = "colpos", //
      P_COLHIDDEN = "colhidden", // nb hidden and removed columns are NOT the
                                  // same
      P_REMOVECOL = "removecol", // ditto
      P_RESTORECOL = "restorecol", // applies to *removed* columns
      // adding a new category
      P_NEWCTGNAME = "newctgname", //
      P_NEWCTGPOSITION = "newctgpos", // reordering existing categories
      P_REMOVECTG = "removectg", //
      P_RESTORECTG = "restorectg", // adding a new column
      P_NEWCOLNAME = "newcolname", //
      P_NEWCOLTYPE = "newcoltype", //
      P_NEWCOLHIDDEN = "newcolhidden", //
      P_NEWCOLPOSITION = "newcolpos", // creating a new category
      /* category add-'n'-edit-contents page */
      P_REMOVEROW = "removerow", //
      P_RESTOREROW = "restorerow", //
      P_REMOVEFILE = "removefile", // only used wrt category contents
      /*
       * this next block of parameters shouldn't contain any underscores
       * (they're used to parse request strings, which use underscores as
       * delimiters)
       */
      P_PREFIX_NEW_CONTENT = "new", // prefix for all parameter values that
                                    // denote content addsedit existing category
                                    // content
      P_CONTENT_TEXT = "ctnttext", //
      P_CONTENT_DATE = "ctntdate", //
      P_CONTENT_NUMBER = "ctntnumber", //
      P_CONTENT_FILE = "ctntfile", // add a file to an existing (possibly
                                    // empty) content
      P_CONTENT_FILELABEL = "ctntfilelabel", // edit existing label or add
                                              // label to content in existing
                                              // row
      P_CONTENT_URLADDRESS = "ctnturladdr", //
      P_CONTENT_URLLABEL = "ctnturllabel", // add new category content
      P_NEW_CONTENT_TEXT = "newctnttext", //
      P_NEW_CONTENT_URLADDRESS = "newctnturladdr", //
      P_NEW_CONTENT_URLLABEL = "newctnturllabel", //
      P_NEW_CONTENT_DATE = "newctntdate", //
      P_NEW_CONTENT_NUMBER = "newctntnumber", //
      P_NEW_CONTENT_FILE = "newctntfile", //
      P_NEW_CONTENT_FILELABEL = "newctntfilelabel", // add label to cell in row
                                                    // to be created
      /* end non-underscorable block */
      /* student assignment view */
      P_INVITE = "invite", // netID of receiver of group invitation
      P_REGRADEREQUEST = "regraderequest", // on both student and staff views;
                                            // text of request
      P_REGRADEWHOLE = "regradewhole", // on both student and staff views; if
                                        // present, regrade all subproblems
      P_REGRADESUB = "regradesub", // on both student and staff views; prepend
                                    // to  of subproblem to regrade
      P_REGRADENETID = "regradenetid", // netID of staff member submitting
                                        // regrade response
      /* grading */
      P_GRADE = "grade", // used on various grading pages; a number
      P_OLDGRADE = "oldgrade", // used for conflict resolution, sends back the
                                // original value of the associated grade
      P_GRADEMESSAGE = "grademsg", // whether user has selected a "Grade" link,
                                    // but yet to select students/assignments
      /* assignment grading page */
      P_GRADEGROUP = "gradegroup_", // followed by a group ; "checked" or not
                                    // present
      P_ASSIGNPROBNAME = "assignsubprobname", // which subproblem to grade; can
                                              // be "<All Parts>"
      P_ASSIGNGRADER = "assigngrader", // netID of grader to be assigned
      P_NETIDLIST = "groupslist", // list of netIDs to be grouped together for a
                                  // given assignment
      P_GRADESFILE = "gradesfile", // file associating netIDs with grades for a
                                    // particular assignment
      P_EXTGROUPID = "extgroupid", //  of group to receive an extension
      /*
       * detailed grading pages (all students for one assignment, all
       * assignments for one student)
       */
      P_COMMENTTEXT = "commenttext", // prepended to group ; text of comment
                                      // by grader
      P_COMMENTFILE = "commentfile", // prepended to group ; file with
                                      // comment by grader
      P_REGRADERESPONSE = "regraderesponse", // prepended to group  and
                                              // regrade request ; boolean
      P_REMOVECOMMENT = "removecomment", //
      /* log search pages (course admin, cms admin) */
      P_LOGSEARCH_NETID = "actingnetid", // netID
      P_LOGSEARCH_RECNETID = "receivingnetid", // netID
      P_LOGSEARCH_SIMNETID = "simulatednetid", // netID
      P_LOGSEARCH_IP = "actingipaddress", // text
      P_LOGSEARCH_START = "starttime", // date
      P_LOGSEARCH_END = "endtime", // date
      P_LOGSEARCH_TYPE = "logtype", // log type, integer (see LogBean)
      P_LOGSEARCH_NAME = "logname", // log subtype, text
      P_LOGSEARCH_COURSE = "logsearchcourseid", // course 
      P_LOGSEARCH_ASGN = "logsearchassignid", // assignment 
      /* e-mail */
      P_EMAIL_NETIDS = "emailnetids",
      P_EMAIL_SUBJECT = "emailsubject", // text
      P_EMAIL_BODY = "emailbody", // text
      P_EMAIL_RECIPIENTS = "emailrecipients", // one of "all", "staff",
                                              // "students"
      /* user preferences (staff, student) */
      // e-mail notification options
      P_PREF_NEWASSIGN = "prefnewassign", // for staff and students
      P_PREF_DATECHANGE = "prefdatechange", // for staff and students
      P_PREF_FINALGRADES = "preffinalgrades", // for staff and students; when
                                              // final grades released
      P_PREF_ASSIGNEDTO = "prefassignedto", // for staff only
      P_PREF_REGRADEREQUEST = "prefregraderequest", // for staff only
      P_PREF_INVITATION = "prefinvitation", // for students only
      P_PREF_GRADERELEASE = "prefgraderelease", // for students only
      P_PREF_GRADECHANGE = "prefgradechange", // for students only
      P_PREF_FILESUBMIT = "preffilesubmit", // for students only; sent to group
                                            // members
      P_PREF_TIMESLOTCHANGE = "preftimeslotchange", // for students only
      P_APPLYSCOPE = "applyscope", // Whether to apply settings to all courses
                                    // & make default
      P_APPLYTHIS = "applythis",
      P_APPLYALL = "applyall",
      P_APPLYDEFAULT = "appldef",
      /* Assignment schedule edit page */
      // assigning groups
      P_TIMESLOTID = "timeslotid", //  of slot for which to add/remove group
      // editing schedule
      P_NEWTSNAME = "newtsname", // arbitrary timeslot name; text
      P_NEWTSSTAFF = "newtsstaff", // staff netID
      P_NEWTSLOCATION = "newtslocation", // arbitrary location name; text
      P_NEWTSSTARTDATE = "newtsstartdate", // start date/time
      P_NEWTSSTARTTIME = "newtsstarttime", // ditto
      P_NEWTSSTARTAMPM = "newtsstartampm", // ditto
      P_NEWTSMULTIPLICITY = "newtsmultiplicity", // number of consecutive slots
                                                  // to create
      P_DELETETIMESLOT = "deletetimeslot_", // prepended to timeslot ;
                                            // "checked" or not present
      P_NEWNETIDS = "newnetids", // adding new net ids from a file Duplicate
                                  // hidden fields for the assignment editing
                                  // page, for reloading on error
      // Hidden parameters for saving and reloading data on the assignment
      // editing page in the case of errors
      P_ITEMID = "itemid_",
      P_ITEMFILENAME = "itemfilename_",
      P_HIDDENITEMNAME = "hiddenitemname_",
      P_HIDDENITEMDATE = "hiddenitemdate_",
      P_HIDDENITEMFILEID = "hiddenitemfileid_",
      P_HIDDENITEMFILEDATE = "hiddenitemfiledate_",
      P_HIDDENITEMFILENAME = "hiddenitemfilename_",
      P_HIDDENSOLID = "hiddensolid_",
      P_HIDDENSOLNAME = "hiddensolname",
      P_HIDDENPROBNAME = "hiddenprobname",
      P_HIDDENPROBSCORE = "hiddenprobscore",
      P_SOLFILEID = "solfileid_",
      P_SOLFILEPATH = "solfilepath_",
      P_SOLFILENAME = "solfilename_",
      P_HIDDENREQNAME = "hiddenreqname_",
      P_HIDDENREQTYPE = "hiddenreqtype_",
      P_HIDDENREQSIZE = "hiddenreqsize_", P_HIDDENITEMFILE = "hiddenitemfile_", // The
                                                                                // local
                                                                                // path
                                                                                // of
                                                                                // the
                                                                                // file,
                                                                                // for
                                                                                // refilling
                                                                                // in
                                                                                // the
                                                                                // form
                                                                                // after
                                                                                // error
      P_NEWITEMFILEPATH = "newitemfilepath_",
      P_ASCENDING       = "ascending";

  // Session attributes
  public static final String A_TIME = "time", // time taken to render the page;
                                              // used in footer.jsp when in
                                              // debug mode
      A_DEBUG = "debug", //
      A_PRINCIPAL = "principal", //
      A_DISPLAYDATA = "displayData", // concerning file upload
      A_GRADESTABLE = "gradestable", // referenced only in this file
      A_PARSEDCSVINFO = "csvtable", // referenced only in this file
      A_ISCLASSLIST = "isclasslist", // set when we're confirming a classlist
                                      // upload
      A_URL = "url", // The URL of the previously loaded page (used by
                      // viewAs.jsp)
      A_COOKIES = "cookies"; // a Cookie[], or null if no cookies

  // Misc
  public static final String ZERO = "0", ONE = "1", TWO = "2", TIME = "time", // name
                                                                              // of
                                                                              // session
                                                                              // attribute
                                                                              // used
                                                                              // to
                                                                              // store
                                                                              // system
                                                                              // time
                                                                              // before
                                                                              // each
                                                                              // request
                                                                              // is
                                                                              // processed
      ADDSTUDENTS = "Add students", //
      /*
       * Actions available from grading page (all are part of the same form)
       * these are also the labels used for the various submit buttons on that
       * page
       */
      GA_GRADE = "Grade", // go to group-specific grading page
      GA_FILES = "Files", // download all files for a group
      GA_EMAIL = "Email", GA_GRANT = "Grant", // grant an extension
      GA_CHANGE = "Change", // change an extension
      GA_GROUP = "Group", // group selected students
      GA_UNGROUP = "Ungroup", // put all selected students into individual
                              // groups
      GA_CREATEGROUP = "Create", // group the netids specified in a textbox
      GA_ASSIGNGRADER = "Assign Grader", // assign groups to a grader
      /* filenames for various files we upload/download via streams */
      SUBMISSIONS_ZIP_FILENAME = "submissions.zip", // most recent submissions
                                                    // for a given group for a
                                                    // given assignment
      GRADES_TABLE_FILENAME_EXTENSION = "grades_table.csv", // appended to
                                                            // course code,
                                                            // semester and
                                                            // assignment name
                                                            // for table of
                                                            // grades for that
                                                            // assignment
      FINAL_GRADES_TABLE_FILENAME_EXTENSION = "final_grades.csv", // appended to
                                                                  // course code
                                                                  // and
                                                                  // semester
                                                                  // for table
                                                                  // of final
                                                                  // grades
      STUDENT_TABLE_FILENAME_EXTENSION = "student_table.csv", // appended to
                                                              // course code for
                                                              // table of
                                                              // students and
                                                              // grades so far
                                                              // for that course
      RUBRIC_FILENAME_EXTENSION = "rubric.csv", // appended to course code for
                                                // grading rubric
      STUDENT_INFO_TEMPLATE_FILENAME = "student_template.csv", // possibly
                                                                // appended to
                                                                // course code
                                                                // for general
                                                                // student info
                                                                // table
      FINALGRADES_TEMPLATE_FILENAME = "final_grades.csv"; // appended to course
                                                          // code for
                                                          // netid/final grade
                                                          // file

  public static final int TEXTAREA_MAX_LENGTH = 8000; // Maximum size for a text
                                                      // area
  private TransactionHandler transactions;

  /*
   * Hold the info necessary to have a helper function do the transactions and
   * XMLbuilding so that processRequest() itself doesn't have to be insanely
   * long and essentially have two levels of logic in one function.
   */
  private class RequestHandlerInfo {
    private String buildURL; // the URL to which to output, or null if none

    private Document xmlDoc; // the info to send to a JSP, or null if no

    // output is to be created

    public RequestHandlerInfo(String url, Document doc) {
      this.buildURL = url;
      this.xmlDoc = doc;
    }

    // null means no output
    public String getBuildURL() {
      return this.buildURL;
    }

    // null means don't build
    public Document getXMLDocument() {
      return this.xmlDoc;
    }
  }

  private HashMap    debugPrincipalMap;
  private XMLBuilder xmlBuilder;

  /**
   * Initialize the Servlet
   * 
   * @param config
   *                Configuration info for the servlet
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    
    // TODO: fetch CMS root
    CMSRoot database = new CMSRoot();
    
    // add test data
    cms.controller.test.CreateDB.create(database);
    
    if (xmlBuilder == null) {
      try {
        xmlBuilder = new XMLBuilder(database);
      } catch (ParserConfigurationException e) {
        throw new ServletException(e);
      }
    }
    if (transactions == null) {
      transactions = new TransactionHandler(database);
    }

    debug = xmlBuilder.getDatabase().getDebugMode();
    if (xmlBuilder.getDatabase().getDebugMode())
      debugPrincipalMap = new HashMap();
    maxFileSize = xmlBuilder.getDatabase().getMaxFileSize();
  }

  /**
   * Destroy this servlet for shutdown
   */
  public void destroy() {
    super.destroy();
  }

  /**
   * Handle get requests
   * 
   * @param request
   *                The Servlet Request to handle
   * @param response
   *                The Servlet Response to return
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handle post requests
   * 
   * @param request
   *                The Servlet Request to handle
   * @param response
   *                The Servlet Response to return
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Set up a Principal based on request info, including the action.
   * 
   * @param session
   * @param request
   * @param action
   * @return A Principal that's null when action is null, else initialized
   * @throws ServletException
   */
  private User setUpPrincipal(HttpSession session,
      HttpServletRequest request, boolean action) throws ServletException {
    // there are many possible sources for the netid:
    // - DEBUGID request parameter
    // - stored by ip address
    // - A_PRINCIPAL session variable
    // - remote_user header
    // - Guest
    // - null
    User result;
    
    if (debug && request.getParameter(P_DEBUGID) != null) {
      result = getUser(request.getParameter(P_DEBUGID));
      debugPrincipalMap.put(request.getLocalAddr(), result);
    }

    else if (debug && session.getAttribute(A_PRINCIPAL) != null)
      result = (User) session.getAttribute(A_PRINCIPAL);
      
    else if (debug)
      result = (User) debugPrincipalMap.get(request.getLocalAddr());
    
    else if (action && request.getHeader("remote_user") != null) 
      result = getUser(request.getHeader("remote_user"));
    
    else
      result = (User) session.getAttribute(A_PRINCIPAL);
    
    if (action && result == null)
      result = xmlBuilder.getDatabase().getGuestUser();
    
    return result;
  }

  /**
   * Handle Get and Post requests
   * 
   * @param request
   *          The Servlet Request to handle
   * @param response
   *          The Servlet Response to return
   * @throws ServletException,
   *           IOException
   */
  protected void processRequest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter(P_ACTION);
    response.setCharacterEncoding("iso-8859-1");
    String path = request.getPathInfo();
    System.out.println("processRequest");
    if (path == null) {
      response.sendRedirect(request.getContextPath() + request.getServletPath()
          + "/");
      return;
    }
    if (!path.equals("/")) {
      if (path.endsWith("/")
          || (!path.endsWith(".js.jsp") && path.endsWith(".jsp"))) {
        response.sendRedirect(request.getContextPath()
            + request.getServletPath() + "/");
        // redirectTo(FORBIDDEN_URL, request, response);
        // response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return;
      } else {
        redirectTo(path, request, response);
        return;
      }
    }
  
    Transactions.refreshAddressFromRequest(request);
    
    String buildURL = "";
    HttpSession session = request.getSession(true);
    session.setAttribute(TIME, new Long(System.currentTimeMillis()));
    /*
     * Course data for all of a user's courses are cached in the session and can
     * be retrieved for fast reference
     */
    try {
      // Check for a requested action
      System.out.println("The current action is: " + action);
      // debug parameter values
      Map reqmap = request.getParameterMap();
      Iterator iter = reqmap.keySet().iterator();
      while (iter.hasNext()) {
        String key = (String) iter.next();
        String value = ((String[]) reqmap.get(key))[0];
        System.out.println("reqparam: " + key + "=" + value);
      }
      session.setAttribute(A_DEBUG, new Boolean(debug));
      session.setAttribute(A_COOKIES, request.getCookies());
      Document xml  = null;
      User     user = null;
      
      /*
       * no action: go to cms home page, which tells you to either go guest or
       * sign in (if action is null, there won't be a principal, so don't bother
       * setting one up) FIXME After I synched on 5/17/05, I found that when I
       * tried to run, the action was null and so getUserID() (just below) was
       * being called on the null Principal that was returned by
       * setUpPrincipal() (above). I "fixed" this, assuming it was a bug, and
       * the system seems to be working now. Am I missing something, or was
       * non-working code committed? - Evan
       */
      if (action == null) {
        buildURL = HOMEPAGE_URL;
        xml = xmlBuilder.buildHomepage();
      } else {
        // Set up Principal and netID in debug mode
        user = setUpPrincipal(session, request, action != null);
        if (user == null) {
          buildURL = HOMEPAGE_URL;
          xml = xmlBuilder.buildHomepage();
        } else {
          // staffAs_ mode, apparentID
          // of principal otherwise
          try {
            RequestHandlerInfo info =
                handleSpecificAction(action, request, response, session, user);
            if (info == null)
              throw new RuntimeException(
                  "Action handler return value should not be null!");
            buildURL = info.getBuildURL();
            xml = info.getXMLDocument();
          } catch (Exception nfe) {
            // Bad input - go to overview
            xml = xmlBuilder.buildErrorPage(user.getNetID(), action, nfe);
            buildURL = ERROR_URL;
            nfe.printStackTrace();
          }
        }
      }
      session.setAttribute(A_DISPLAYDATA, xml);
      /*
       * FIXME is it ok to set the principal to be null on the line below, or
       * will this cause problems in JSPs? The principal is null when the action
       * is null; see my fixme just above this. - Evan
       */
      session.setAttribute(A_PRINCIPAL, user);

      if (buildURL != null) {

        redirectTo(buildURL, request, response);
      }
    } catch (ServletException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Error in AccessController.processRequest(): " + e);
      e.printStackTrace();
      throw new ServletException(e);
    }
  }

  private void redirectTo(String url, HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
    RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
    rd.forward(request, response);
  }

  /**
   * Auxiliary to processRequest(): do the lower-level action handling logic, to
   * keep processRequest() at a sane length. Go through transactions where
   * necessary and/or produce XML output proper for the given action.
   * 
   * @param action
   * @param request
   * @param response
   * @param session
   * @param user
   * @param xml
   * @return A RequestHandlerInfo giving all the info needed to build output, if
   *         any. The return value may have null fields (meaning there should be
   *         no output), but WILL NOT BE NULL.
   * @see AccessController.RequestHandlerInfo (above)
   */
  private RequestHandlerInfo handleSpecificAction(String action,
      HttpServletRequest request, HttpServletResponse response,
      HttpSession session, User user) throws IOException, FileUploadException {
    // the values to be filled in and returned in some form
    String buildURL = null;
    Document xml = null;
    if (debug) Profiler.beginAction(action);
    /**
     * ******** actions checked for below should be in alphabetical order
     * *********
     */
    // overview page
    if (action.equals(ACT_OVER)) {
      Semester semester = getSemester(request.getParameter(P_SEMESTERID));
      
      // Overview resets the principal to its standard view
      if (user.isInStaffAsBlankMode()) {
        resetToStaff(request, user);
      }
      buildURL = OVERVIEW_URL;
      xml = xmlBuilder.buildOverview(user, semester);
    }
    // Accept a group invitation
    else if (action.equals(ACT_ACCEPT)) {
      Group group = getGroup(request.getParameter(P_GROUPID));
      if (user.isStudentInCourseByGroup(group)) {
        buildURL = ASSIGNMENT_URL;
        TransactionResult result = transactions.acceptInvitation(user, group);
        xml = xmlBuilder.refreshStudentAssignmentPage(user, group);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Add a CMS administrator
    else if (action.equals(ACT_ADDCMSADMIN)) {
      if (user.isCMSAdmin()) {
        buildURL = CMSADMIN_URL;
        TransactionResult result =
            transactions.addCMSAdmin(user, getUser(request.getParameter(P_NETID)));
        xml = xmlBuilder.buildCMSAdminPage(user);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the add-and-edit-category-contents page
    else if (action.equals(ACT_ADDNEDITCONTENTS)) {
      Category category = getCategory(request.getParameter(P_CATID));
      if (user.isCategoryPrivByCategory(category)) {
        buildURL = CTGCONTENTSADMIN_URL;
        xml = xmlBuilder.buildCtgContentPage(user, category);
      } else buildURL = FORBIDDEN_URL;
    }
    // Add a course under the current semester
    else if (action.equals(ACT_ADDCOURSE)) {
      if (user.isCMSAdmin()) {
        String courseCode = request.getParameter(P_CODE), courseName =
            request.getParameter(P_COURSENAME);
        TransactionResult result =
            transactions.addCourse(user, courseCode, courseName);
        buildURL = CMSADMIN_URL;
        xml = xmlBuilder.buildCMSAdminPage(user);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Set a new sitewide notice
    else if (action.equals(ACT_NEWNOTICE)) {
      if (user.isCMSAdmin()) {
        String text    = request.getParameter(P_NOTICETEXT);
        User   author  = user;
        boolean hidden = request.getParameter(P_HIDDEN) != null;
        String date    = request.getParameter(P_NOTICEEXPDATE);
        String time    = request.getParameter(P_NOTICEEXPTIME);
        String ampm    = request.getParameter(P_NOTICEEXPAMPM);
        Date exp;
        try {
          exp = DateTimeUtil.parseDate(date, time, ampm);
        } catch (ParseException e) {
          exp = null;
        }

        TransactionResult result =
            transactions.addNotice(user, text, author, exp, hidden);
        buildURL = CMSADMIN_URL;
        xml = xmlBuilder.buildCMSAdminPage(user);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Add students to a course
    else if (action.equals(ACT_ADDSTUDENTS)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.hasStudentsPageAccess(course)) {
        TransactionResult result =
            transactions.addStudentsToCourse(user, course, request);
        buildURL = STUDENTS_URL;
        xml = xmlBuilder.buildStudentsPage(user, course, true);
        if (result.hasErrors()) {
          xml = xmlBuilder.addStatus(xml, result);
        } else {
          xml =
              xmlBuilder.addStatus(xml, "All students added successfully",
                  xmlBuilder.MSG_NORMAL);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // goto add student page
    else if (action.equals(ACT_ADDSTUDENTPAGE)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.hasStudentsPageAccess(course)) {
        xml = xmlBuilder.buildStudentsPage(user, course, true);
        buildURL = STUDENTS_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // Apply to groups (covers most actions on the main grading page)
    else if (action.equals(ACT_APPLYTOGROUPS)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      String gradingAction = request.getParameter(P_SUBMIT);
      gradingAction = gradingAction == null ? "" : gradingAction;
      if (gradingAction.equals(GA_GRADE)) {
        if (user.isGradesPrivByAssignment(assign)) {
          Collection groups = null;
          if (!user.isAssignPrivByAssignment(assign) && assign.getAssignedGraders()) {
            groups =
                xmlBuilder.getDatabase().assignedToGroups(assign,
                    user,
                    extractGroupsFromMainGradingPageRequest(request));
          } else {
            groups = extractGroupsFromMainGradingPageRequest(request);
          }
          if (groups.size() > 0) {
            xml = xmlBuilder.buildGradePage(user, assign, groups);
            buildURL = GRADESTUDENTS_URL;
          } else {
            xml = xmlBuilder.buildGradeAssignPage(user, assign);
            // xml = xmlBuilder.addStatus(xml, "No groups selected",
            // xmlBuilder.MSG_NORMAL);
            buildURL = GRADEASSIGN_URL;
          }
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_FILES)) {
        List groups = extractGroupsFromMainGradingPageRequest(request);
        if (transactions.authorizeGroupFiles(user, groups)) {
          if (groups.size() > 0) {
            response.setContentType("application/zip");
            response.setHeader("Content-disposition", "attachment; filename=\""
                + SUBMISSIONS_ZIP_FILENAME + "\"");
            transactions.uploadGroupSubmissions(groups, response
                .getOutputStream());
          }
          buildURL = null;
          xml = null;
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_EMAIL)) {
        Course course = getCourse(request.getParameter(P_COURSEID));
        if (user.isAdminPrivByCourse(course)
            || user.isGradesPrivByCourse(course)
            || user.isGroupsPrivByCourse(course)) {
          Collection groups =
              extractGroupsFromMainGradingPageRequest(request);
          xml = xmlBuilder.buildEmailPage(user, course, groups);
          buildURL = EMAIL_URL;
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_GRANT) || gradingAction.equals(GA_CHANGE)) {
        // grant or change an extension
        if (user.isGradesPrivByAssignment(assign)) {
          Group group = getGroup(request.getParameter(P_EXTGROUPID));
          TransactionResult result =
              transactions.setExtension(user, group, request);
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
          buildURL = GRADEASSIGN_URL;
          if (result.hasErrors()) {
            xml = xmlBuilder.addStatus(xml, result);
          } else {
            xml =
                xmlBuilder.addStatus(xml, "Successfully granted extension",
                    xmlBuilder.MSG_NORMAL);
          }
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_GROUP)) { // group all students/groups
                                                    // whose checkboxes were
                                                    // selected
        if (user.isGroupsPrivByAssignment(assign)) {
          List groups = extractGroupsFromMainGradingPageRequest(request);
          // TransactionHandler makes sure they haven't already been graded
          TransactionResult result =
              transactions.groupSelectedStudents(user, assign, groups);
          buildURL = GRADEASSIGN_URL;
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
          if (result.hasErrors())
            xml = xmlBuilder.addStatus(xml, result);
          else xml =
              xmlBuilder.addStatus(xml, (String) result.getValue(),
                  xmlBuilder.MSG_NORMAL);
        }
      } else if (gradingAction.equals(GA_UNGROUP)) { // ungroup all groups
                                                      // whose checkboxes were
                                                      // selected
        if (user.isGroupsPrivByAssignment(assign)) {
          List groups = extractGroupsFromMainGradingPageRequest(request);
          // TransactionHandler makes sure they haven't already been graded
          TransactionResult result =
              transactions.ungroupSelectedStudents(user, assign, groups);
          buildURL = GRADEASSIGN_URL;
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
          if (result.hasErrors())
            xml = xmlBuilder.addStatus(xml, result);
          else xml =
              xmlBuilder.addStatus(xml, "Successfully ungrouped students",
                  xmlBuilder.MSG_NORMAL);
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_CREATEGROUP)) { // create group from
                                                          // netids in textbox
                                                          // contents
        if (user.isGroupsPrivByAssignment(assign)) {
          List netids = StringUtil.parseNetIDList(request.getParameter(P_NETIDLIST));
          TransactionResult result =
              transactions.createGroup(user, netids, assign);
          buildURL = GRADEASSIGN_URL;
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
          if (result.hasErrors())
            xml = xmlBuilder.addStatus(xml, result);
          else xml =
              xmlBuilder.addStatus(xml, "Successfully grouped students",
                  xmlBuilder.MSG_NORMAL);
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_ASSIGNGRADER)) { // ASSIGN GRADERS
        if (user.isAdminPrivByCourse(assign.getCourse())) {
          SubProblem subproblem = getSubProblem(request.getParameter(P_ASSIGNPROBNAME));
          User grader = getUser(request.getParameter(P_ASSIGNGRADER));
          buildURL = GRADEASSIGN_URL;
          TransactionResult result =
              transactions.assignGrader(user, assign, subproblem, grader,
                  request.getParameterMap());
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
          xmlBuilder.addStatus(xml, result);
        } else buildURL = FORBIDDEN_URL;
      } else {
        if (user.isGradesPrivByAssignment(assign)
            || user.isGroupsPrivByAssignment(assign)) {
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
          buildURL = GRADEASSIGN_URL;
        } else buildURL = FORBIDDEN_URL;
      }
    }
    // View student-side assignment page
    else if (action.equals(ACT_ASSIGN)) {
      System.out.println("Attempting to display assignment page");
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      Course course = assign.getCourse();
      
      // Check if principal is a student in this course
      if (course != null && user.hasAssignAccess(course, assign)) {
        buildURL = ASSIGNMENT_URL;
        xml = xmlBuilder.buildStudentAssignmentPage(user, assign);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side assignment page
    else if (action.equals(ACT_ASSIGNADMIN)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isAssignPrivByAssignment(assign)) {
        buildURL = ASSIGNADMIN_URL;
        xml = xmlBuilder.buildBasicAssignmentPage(user, assign);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side survey page
    else if (action.equals(ACT_SURVEYADMIN)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isAssignPrivByAssignment(assign)) {
        buildURL = ASSIGNADMIN_URL;
        xml = xmlBuilder.buildBasicAssignmentPage(user, assign);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side quiz page
    else if (action.equals(ACT_QUIZADMIN)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isAssignPrivByAssignment(assign)) {
        buildURL = ASSIGNADMIN_URL;
        xml = xmlBuilder.buildBasicAssignmentPage(user, assign);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side assignment LIST page
    else if (action.equals(ACT_ASSIGNLISTADMIN)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isStaffInCourseByCourse(course)) {
        buildURL = ASSIGNLISTADMIN_URL;
        xml = xmlBuilder.buildCoursePage(user, course);
      } else buildURL = FORBIDDEN_URL;
    }
    // Cancel an unaccepted invitation
    else if (action.equals(ACT_CANCEL)) {
      Group group = getGroup(request.getParameter(P_GROUPID));
      if (user.isStudentInCourseByGroup(group)) {
        User cancel = getUser(request.getParameter(P_NETID));
        buildURL = ASSIGNMENT_URL;
        TransactionResult result =
            transactions.cancelInvitation(user, cancel, group);
        xml = xmlBuilder.refreshStudentAssignmentPage(user, group);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View one of the CMS administration pages
    else if (action.equals(ACT_CMSADMIN)) {
      if (user.isInStaffAsBlankMode()) {
        resetToStaff(request, user);
      }
      if (user.isCMSAdmin()) {
        buildURL = CMSADMIN_URL;
        xml = xmlBuilder.buildCMSAdminPage(user);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the CMS admin course-edit page
    else if (action.equals(ACT_CMSADMINCOURSEPROPS)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      // if the cms admin happens to be course staff, don't shut him/her out of
      // full access
      if (user.isStaffInCourseByCourse(course)) {
        buildURL = COURSEPROPS_URL;
        xml = xmlBuilder.buildCoursePropertiesPage(user, course);
      } else if (user.isCMSAdmin()) {
        buildURL = CMSADMINCOURSEPROPS_URL;
        xml = xmlBuilder.buildCMSAdminCoursePropsPage(user, course);
      } else buildURL = FORBIDDEN_URL;
    }
    // Confirm the parsing of a Final Grades file and commit the grades
    else if (action.equals(ACT_CONFIRMFINALGRADES)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isGradesPrivByCourse(course)) {
        List table = (List) session.getAttribute(A_GRADESTABLE);
        if (table != null) {
          TransactionResult result =
              transactions.commitFinalGradesFile(user, course, table);
          xml = xmlBuilder.buildStudentsPage(user, course, false);
          if (result.getSuccess()) {
            xml =
                xmlBuilder.addStatus(xml, (String) result.getValue(),
                    xmlBuilder.MSG_NORMAL);
          } else {
            xml = xmlBuilder.addStatus(xml, result);
          }
        } else {
          xml = xmlBuilder.buildStudentsPage(user, course, false);
        }
        buildURL = STUDENTS_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // Confirm the parsing of an uploaded table of student info, and commit
    // changes
    else if (action.equals(ACT_CONFIRMSTUDENTINFO)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      boolean hasPermission = false;
      if (course == null) // system-wide info
      {
        if (user.isCMSAdmin()) {
          List table = (List) session.getAttribute(A_PARSEDCSVINFO);
          TransactionResult result =
              transactions.commitStudentInfo(user, table, null, false);
          xml = xmlBuilder.buildCMSAdminPage(user);
          xml = xmlBuilder.addStatus(xml, result);
          buildURL = CMSADMIN_URL;
        } else buildURL = FORBIDDEN_URL;
      } else // course-specific info
      {
        if (user.isAdminPrivByCourse(course)) {
          List table = (List) session.getAttribute(A_PARSEDCSVINFO);
          TransactionResult result =
              transactions.commitStudentInfo(user, table, course,
                  ((Boolean) session.getAttribute(A_ISCLASSLIST))
                      .booleanValue());
          xml = xmlBuilder.buildFinalGradesPage(user, course);
          xml = xmlBuilder.addStatus(xml, result);
          buildURL = FINALGRADES_URL;
        } else buildURL = FORBIDDEN_URL;
        session.removeAttribute(A_ISCLASSLIST); // we don't want to
        // apply this to the
        // next uploaded CSV
      }
    }
    // Confirm the parsing of an uploaded grades table and commit the grades
    else if (action.equals(ACT_CONFIRMTABLE)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));

      if (user.isGradesPrivByAssignment(assign)) {
        List table = (List) session.getAttribute(A_GRADESTABLE);
        if (table != null) {
          TransactionResult result2 = null;
          if (request.getParameter(P_NEWNETIDS) != null) {
            Course course = assign.getCourse();
            result2 = transactions.addStudentsToCourse(user, course, request);
          }

          TransactionResult result =
              transactions.commitGradesFile(user, assign, table);

          buildURL = GRADEASSIGN_URL;
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
          xml = xmlBuilder.addStatus(xml, result);

          if (result2 != null) {
            if (result2.hasErrors()) {
              xml = xmlBuilder.addStatus(xml, result2);
            } else {
              xml =
                  xmlBuilder.addStatus(xml, "All students added successfully",
                      xmlBuilder.MSG_NORMAL);
            }
          }
        } else {
          buildURL = GRADEASSIGN_URL;
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // View student-side course page
    else if (action.equals(ACT_COURSE)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (request.getParameter(P_RESET) != null && user.isInStaffAsBlankMode()) {
        resetToStaff(request, user);
      }
      if (user.hasCourseAccess(course)) {
        buildURL = COURSE_URL;
        xml = xmlBuilder.buildCoursePage(user, course);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the staff member logsearch page
    else if (action.equals(ACT_COURSE_LOGSEARCH)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAdminPrivByCourse(course)) {
        buildURL = COURSELOG_URL;
        xml = xmlBuilder.buildStaffLogSearchPage(user, null, course);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side course page
    else if (action.equals(ACT_COURSEADMIN)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      // Reset principal to default
      if (user.isInStaffAsBlankMode()) {
        resetToStaff(request, user);
      }
      if (user.isStaffInCourseByCourse(course)) {
        buildURL = COURSEADMIN_URL;
        xml = xmlBuilder.buildCoursePage(user, course);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side course properties page
    else if (action.equals(ACT_COURSEPROPS)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAdminPrivByCourse(course)) {
        buildURL = COURSEPROPS_URL;
        xml = xmlBuilder.buildCoursePropertiesPage(user, course);
      } else buildURL = FORBIDDEN_URL;
    }
    // Add a new semester
    else if (action.equals(ACT_CREATESEMESTER)) {
      if (user.isCMSAdmin()) {
        buildURL = CMSADMIN_URL;
        TransactionResult result =
            transactions.createSemester(user, request.getParameter(P_NAME));
        xml = xmlBuilder.buildCMSAdminPage(user);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Decline a group invitation
    else if (action.equals(ACT_DECLINE)) {
      Group group = getGroup(request.getParameter(P_GROUPID));
      if (user.isStudentInCourseByGroup(group)) {
        buildURL = ASSIGNMENT_URL;
        TransactionResult result = transactions.declineInvitation(user, group);
        xml = xmlBuilder.refreshStudentAssignmentPage(user, group);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Download a file
    else if (action.equals(ACT_DOWNLOAD)) {
      buildURL = FORBIDDEN_URL; // default unless valid & authorized
      try {
        int  type = Integer.parseInt(request.getParameter(P_DOWNLOADTYPE));
        String id = request.getParameter(P_ID);
        CMSRoot database = xmlBuilder.getDatabase();
        FileEntry entry = null;
        switch (type) {
        case XMLBuilder.T_SOLFILE:
          entry = database.getSolutionFile(id);
          break;
        case XMLBuilder.T_ITEMFILE:
          entry = database.getAssignmentItem(id).getAssignmentFile();
          break;
        case XMLBuilder.T_FILEFILE:
          entry = database.getAssignmentFile(id);
          break;
        case XMLBuilder.T_GROUPFILE:
          entry = database.getSubmittedFile(id);
          break;
        case XMLBuilder.T_CATFILE:
          entry = database.getCategoryContentsFileEntry(id);
          break;
        case XMLBuilder.T_COMMENTFILE:
          entry = database.getCommentFile(id);
          break;
        }
        if (entry.isFileAuthorized(user)) {
          sendFile(entry.getFile(), response);
          buildURL = null;
          xml = null;
        }
      } catch (IOException ex) {
        System.out.println(ex.getMessage());
      } catch (NumberFormatException e) {
      }
    } // Drop a student from a course
    else if (action.equals(ACT_DROP)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      String netID = request.getParameter(P_NETID);
      if (user.isAdminPrivByCourse(course)) {
        Collection n = new ArrayList();
        n.add(netID);
        TransactionResult result = transactions.dropStudent(user, course, n);
        buildURL = STUDENTS_URL;
        xml = xmlBuilder.buildStudentsPage(user, course, false);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } // Drop multiple students from a course
    else if (action.equals(ACT_DROPSTUDENTS)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      String netIDs = request.getParameter(P_STUDENTSLIST);
      // FIXME make work for multiple students
      if (user.isAdminPrivByCourse(course)) {
        List netids = StringUtil.parseNetIDList(netIDs);
        TransactionResult result =
            transactions.dropStudent(user, course, netids);
        buildURL = STUDENTS_URL;
        xml = xmlBuilder.buildStudentsPage(user, course, false);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Edit a course's textual description
    else if (action.equals(ACT_EDITCOURSEDESCRIPTION)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAdminPrivByCourse(course)) {
        String newDescription = request.getParameter(P_DESCRIPTION);
        TransactionResult result =
            transactions.editCourseDescription(user, course, newDescription);
        buildURL = COURSEADMIN_URL;
        xml = xmlBuilder.buildCoursePage(user, course);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the general category properties page for a category
    else if (action.equals(ACT_EDITCTG)) {
      Category category = getCategory(request.getParameter(P_CATID));
      if (user.isCategoryPrivByCategory(category)) {
        buildURL = CATEGORYADMIN_URL;
        xml = xmlBuilder.buildCtgContentPage(user, category);
      } else buildURL = FORBIDDEN_URL;
    }
    // Change an existing sitewide notice
    else if (action.equals(ACT_EDITNOTICE)) {
      if (user.isCMSAdmin()) {
        SiteNotice notice = getNotice(request.getParameter(P_ID));
        boolean deleted = request.getParameter(P_DELNOTICE) != null;
        boolean hidden = request.getParameter(P_HIDDEN) != null;
        String text = request.getParameter(P_NOTICETEXT);

        String date = request.getParameter(P_NOTICEEXPDATE);
        String time = request.getParameter(P_NOTICEEXPTIME);
        String ampm = request.getParameter(P_NOTICEEXPAMPM);
        Date exp;
        try {
          exp = DateTimeUtil.parseDate(date, time, ampm);
        } catch (ParseException e) {
          exp = null;
        }

        TransactionResult result =
            transactions.editNotice(user, notice, text, exp, hidden, deleted);

        buildURL = CMSADMIN_URL;
        xml = xmlBuilder.buildCMSAdminPage(user);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Edit the properties of a semester
    else if (action.equals(ACT_EDITSEMESTER)) {
      if (user.isCMSAdmin()) {
        Semester semester = getSemester(request.getParameter(P_ID));
        boolean hidden = request.getParameter(P_HIDDEN).equalsIgnoreCase("true");
        buildURL = CMSADMIN_URL;
        TransactionResult result = transactions.editSemester(user, semester, hidden);
        xml = xmlBuilder.buildCMSAdminPage(user);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the course email page
    else if (action.equals(ACT_EMAIL)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAdminPrivByCourse(course)) {
        buildURL = EMAIL_URL;
        xml = xmlBuilder.buildEmailPage(user, course);
      } else buildURL = FORBIDDEN_URL;
    }
    // Export the full final grades table: netid, name, lecture, section,
    // grade option...
    else if (action.equals(ACT_EXPORTFINALGRADES)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.hasStudentsPageAccess(course)) {
        buildURL = null;
        xml = null;
        Semester sem = course.getSemester();
        String filename =
            course.getCode() + "_" + sem.getName() + "_final_grades.csv";
        filename = filename.replace(' ', '_').toLowerCase();
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=\""
            + filename + "\"");
        transactions.exportStudentInfoFinalGrades(course, response
            .getOutputStream());
      } else buildURL = FORBIDDEN_URL;
    }
    // Export grades table csv file for a single assignment
    else if (action.equals(ACT_EXPORTGRADESTABLE)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isGradesPrivByAssignment(assign)) {
        Course course = assign.getCourse();
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename =\""
            + course.getCode().replace(' ', '_') + "_"
            + assign.getNameShort().replace(' ', '_') + "_"
            + GRADES_TABLE_FILENAME_EXTENSION + "\"");
        transactions.exportSingleAssignmentGradesTable(user, assign, response
            .getOutputStream());
        buildURL = null;
        xml = null;
      } else buildURL = FORBIDDEN_URL;
    }
    // Export csv-formatted table of assignment max scores and weights
    else if (action.equals(ACT_EXPORTRUBRIC)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAdminPrivByCourse(course)) {
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=\""
            + course.getCode().replace(' ', '_') + "_"
            + RUBRIC_FILENAME_EXTENSION + "\"");
        transactions.exportGradingRubric(user, course, response.getOutputStream());
        buildURL = null;
        xml = null;
      } else buildURL = FORBIDDEN_URL;
    }
    // Export full template for any student-info upload
    else if (action.equals(ACT_EXPORTSTUDENTINFOTEMPLATE)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (course == null) // cmsadmin download
      {
        if (user.isCMSAdmin()) {
          response.setContentType("text/csv");
          response.setHeader("Content-disposition", "attachment; filename=\""
              + STUDENT_INFO_TEMPLATE_FILENAME + "\"");
          TransactionResult result =
              transactions.exportStudentInfoTemplate(null, response
                  .getOutputStream());
          if (!result.getSuccess()) {
            xml = xmlBuilder.buildCMSAdminPage(user);
            xml = xmlBuilder.addStatus(xml, result);
            buildURL = UPLOAD_URL;
          } else {
            buildURL = null;
            xml = null;
          }
        } else buildURL = FORBIDDEN_URL;
      } else // course staff download
      {
        if (user.isStaffInCourseByCourse(course)) {
          response.setContentType("text/csv");
          response.setHeader("Content-disposition", "attachment; filename=\""
              + course.getCode().replace(' ', '_') + "_"
              + STUDENT_INFO_TEMPLATE_FILENAME + "\"");
          TransactionResult result =
              transactions.exportStudentInfoTemplate(course, response.getOutputStream());
          if (!result.getSuccess()) {
            xml = xmlBuilder.buildCSVUploadPage(user, course);
            xml = xmlBuilder.addStatus(xml, result);
            buildURL = UPLOAD_URL;
          } else {
            buildURL = null;
            xml = null;
          }
        } else buildURL = FORBIDDEN_URL;
      }
    }
    // Export students table w/grades as csv file
    else if (action.equals(ACT_EXPORTTABLE)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isGradesPrivByCourse(course)) {
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=\""
            + course.getCode().replace(' ', '_') + "_"
            + STUDENT_TABLE_FILENAME_EXTENSION + "\"");
        transactions.exportGradesTable(xmlBuilder, user, course, response.getOutputStream());
        buildURL = null;
        xml = null;
      } else buildURL = FORBIDDEN_URL;
    }
    // Submit a file for an assignment
    else if (action.equals(ACT_FILESUBMIT)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isStudentInCourseByAssignment(assign)) {
        TransactionResult result = transactions.submitFiles(user, request);
        buildURL = ASSIGNMENT_URL;
        xml = xmlBuilder.buildStudentAssignmentPage(user, assign);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Submit a survey
    else if (action.equals(ACT_SURVEYSUBMIT)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isStudentInCourseByAssignment(assign)) {
        TransactionResult result = transactions.submitSurvey(user, request);

        if (result.getSuccess())
          result.setValue("Answers submitted successfully");

        buildURL = COURSE_URL;
        xml = xmlBuilder.buildCoursePage(user, assign.getCourse());
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View list of administrative data and final grades for all students in
    // a course
    else if (action.equals(ACT_FINALGRADES)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isCMSAdmin() || user.isAdminPrivByCourse(course)) {
        buildURL = FINALGRADES_URL;
        xml = xmlBuilder.buildFinalGradesPage(user, course);
      } else buildURL = FORBIDDEN_URL;
    }
    // Parse and error-check a Final Grades CSV File
    else if (action.equals(ACT_FINALGRADESFILE)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isGradesPrivByCourse(course)) {
        TransactionResult result =
            transactions.parseFinalGradesFile(course, request);
        if (!result.getSuccess() && result.getValue() == null) {
          xml = xmlBuilder.buildStudentsPage(user, course, false);
          xml =
              xmlBuilder.addStatus(xml,
                  "Could not parse grades file; format was unrecognized",
                  xmlBuilder.MSG_ERROR);
          buildURL = STUDENTS_URL;
        } else {
          xml =
              xmlBuilder.buildConfirmPage(user, xmlBuilder.CONFIRM_FINALGRADES,
                  course, result);
          buildURL = CONFIRMTABLE_URL;
          if (result.getSuccess()) {
            session.setAttribute(A_GRADESTABLE, result.getValue());
          }
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // Download mini-template just for filling in final grades for a class
    else if (action.equals(ACT_FINALGRADESTEMPLATE)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAdminPrivByCourse(course)) {
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=\""
            + course.getCode().replace(' ', '_') + "_"
            + FINALGRADES_TEMPLATE_FILENAME + "\"");
        transactions.exportFinalGradesTemplate(course, response.getOutputStream());
        buildURL = null;
        xml = null;
      } else buildURL = FORBIDDEN_URL;
    }
    // View the individual-student-grading page for a single assignment
    else if (action.equals(ACT_GRADESTUDENTS)) {
      Assignment assign = xmlBuilder.getDatabase().getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isGradesPrivByAssignment(assign) && assign.getType() != Assignment.SURVEY) {
        Collection groups = null;
        if (!user.isAdminPrivByCourse(assign.getCourse())
            && assign.getAssignedGraders()) {
          groups =
              xmlBuilder.getDatabase().assignedToGroups(assign,
                  user,
                  extractGroupsFromMainGradingPageRequest(request));
        } else {
          groups = extractGroupsFromMainGradingPageRequest(request);
        }
        if (groups.size() > 0) {
          xml = xmlBuilder.buildGradePage(user, assign, groups);
          buildURL = GRADESTUDENTS_URL;
        } else {
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
          // xml = xmlBuilder.addStatus(xml, "No groups selected",
          // xmlBuilder.MSG_NORMAL);
          buildURL = GRADEASSIGN_URL;
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // View assignment-grading main page
    else if (action.equals(ACT_GRADEASSIGN)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      boolean showGradeMsg = (request.getParameter(P_GRADEMESSAGE) != null);
      if (user.isGradesPrivByAssignment(assign)
          || user.isGroupsPrivByAssignment(assign)) {
        
        // can't display a grades page for a survey
        if (assign.getType() == Assignment.SURVEY) {
          buildURL = FORBIDDEN_URL;
        } else {
          buildURL = GRADEASSIGN_URL;
          xml = xmlBuilder.buildGradeAssignPage(user, assign, showGradeMsg);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // Upload group submission files
    else if (action.equals(ACT_GROUPFILES)) {
      Group group = getGroup(request.getParameter(P_GROUPID));
      if (group != null) {
        Collection groups = Collections.singleton(group);
        if (transactions.authorizeGroupFiles(user, groups)) {
          response.setContentType("application/zip");
          response.setHeader("Content-disposition", "attachment; filename=\""
              + SUBMISSIONS_ZIP_FILENAME + "\"");
          transactions.uploadGroupSubmissions(groups, response.getOutputStream());
          buildURL = null;
          xml = null;
        } else buildURL = FORBIDDEN_URL;
      } else {
        buildURL = FORBIDDEN_URL;
      }
    }
    // Invite someone to join a group
    else if (action.equals(ACT_INVITE)) {
      User invite = getUser(request.getParameter(P_INVITE));
      Group group = getGroup(request.getParameter(P_GROUPID));
      if (user.isStudentInCourseByGroup(group)) {
        // TODO Later add multi-invites; now one at a time
        buildURL = ASSIGNMENT_URL;
        TransactionResult result = transactions.inviteUser(user, invite, group);
        xml = xmlBuilder.refreshStudentAssignmentPage(user, group);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Leave a group
    else if (action.equals(ACT_LEAVE)) {
      Group group = getGroup(request.getParameter(P_GROUPID));
      if (user.isStudentInCourseByGroup(group)) {
        buildURL = ASSIGNMENT_URL;
        TransactionResult result = transactions.leaveGroup(user, group);
        xml = xmlBuilder.refreshStudentAssignmentPage(user, group);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Post a new announcement
    else if (action.equals(ACT_NEWANNOUNCE)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isCategoryPrivByCourse(course)) {
        String announce = request.getParameter(P_ANNOUNCE);
        TransactionResult result =
            transactions.postAnnouncement(user, course, announce);
        buildURL = COURSEADMIN_URL;
        xml = xmlBuilder.buildCoursePage(user, course);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the assignment creation page
    else if (action.equals(ACT_NEWASSIGN)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAssignPrivByCourse(course)) {
        buildURL = ASSIGNADMIN_URL;
        xml =
            xmlBuilder.buildAssignmentCreationPage(user, course, Assignment.ASSIGNMENT);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the survey creation page
    else if (action.equals(ACT_NEWSURVEY)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAssignPrivByCourse(course)) {
        buildURL = ASSIGNADMIN_URL;
        xml =
            xmlBuilder.buildAssignmentCreationPage(user, course,
                Assignment.SURVEY);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the quiz creation page
    else if (action.equals(ACT_NEWQUIZ)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAssignPrivByCourse(course)) {
        buildURL = ASSIGNADMIN_URL;
        xml =
            xmlBuilder.buildAssignmentCreationPage(user, course,
                Assignment.QUIZ);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the category creation page
    else if (action.equals(ACT_NEWCATEGORY)) {
      // check the privilege
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isCategoryPrivByCourse(course)) {
        buildURL = CATEGORYADMIN_URL;
        xml = xmlBuilder.buildNewCategoryPage(user, course);
      } else buildURL = FORBIDDEN_URL;
      // Staff member accessing the printable schedule page
      /*
       * Since it's possible to assign any staff member to a timeslot, all staff
       * members should be able to see the schedule in case they've been
       * assigned to any slots. Previously only assignment-privileged staff
       * could view the schedule. - Evan, 5 / 25 / 06
       */
    } else if (action.equals(ACT_PRINTSCHEDULE)) {
      System.out.println("Attempting to display printable schedule page");
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      Course course = assign.getCourse();
      if (user.isStaffInCourseByCourse(course)) {
        buildURL = PRINTSCHED_URL;
        xml = xmlBuilder.buildBasicSchedulePage(user, assign);
      } else buildURL = FORBIDDEN_URL;
    } // Call up profiler page (only in debug mode)
    else if (action.equals(ACT_PROFILER)) {
      if (debug) {
        buildURL = PROFILER_URL;
        xml = Profiler.output(100);
      } else buildURL = FORBIDDEN_URL;
    } // Reenroll a student in the course
    else if (action.equals(ACT_REENROLL)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      User  student = getUser(request.getParameter(P_NETID));
      if (user.isAdminPrivByCourse(course)) {
        TransactionResult result =
            transactions.reenrollStudent(user, course, student, request
                .getParameter(P_EMAILADDED) != null);
        buildURL = STUDENTS_URL;
        xml = xmlBuilder.buildStudentsPage(user, course, false);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } // Removes an assignment from the system
    else if (action.equals(ACT_REMOVEASSIGN)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isAssignPrivByAssignment(assign)) {
        TransactionResult result = transactions.removeAssignment(user, assign);
        buildURL = COURSEADMIN_URL;
        xml = xmlBuilder.buildCoursePage(user, assign.getCourse());
        if (result.getSuccess())
          result.setValue("Assignment removed successfully");
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Remove a CMS administrator
    else if (action.equals(ACT_REMOVECMSADMIN)) {
      if (user.isCMSAdmin()) {
        buildURL = CMSADMIN_URL;
        TransactionResult result = new TransactionResult();
        if (user.getNetID().equals(request.getParameter(P_NETID)))
          result.addError("Can't remove current user");
        else result =
            transactions.removeCMSAdmin(user, getUser(request.getParameter(P_NETID)));
        xml = xmlBuilder.buildCMSAdminPage(user);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Remove a group's extension
    else if (action.equals(ACT_REMOVEEXTENSION)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      Group  group  = getGroup(request.getParameter(P_GROUPID));
      if (user.isGradesPrivByCourse(course)) {
        TransactionResult result =
            transactions.removeExtension(user, course, group);
        Assignment assign = group.getAssignment();
        if (assign != null) {
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
          buildURL = GRADEASSIGN_URL;
        } else {
          xml = xmlBuilder.buildCoursePage(user, course);
          buildURL = COURSEADMIN_URL;
        }
        if (result.getSuccess())
          result.setValue("Successfully removed extension");
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Remove a row of content from a category (can be done from the main
    // course page, admin side)
    else if (action.equals(ACT_REMOVEROW)) {
      CategoryRow row = getCategoryRow(request.getParameter(P_ID));
      Course   course = getCourse(request.getParameter(P_COURSEID));
      if (user.isCategoryPrivByCourse(course)) {
        TransactionResult result = transactions.removeCtgRow(user, row);
        buildURL = COURSEADMIN_URL;
        xml = xmlBuilder.buildCoursePage(user, course);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } else if (action.equals(ACT_REQUESTREGRADE)) {
      Group group = getGroup(request.getParameter(P_GROUPID));
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isStudentInCourseByAssignment(assign)) {
        TransactionResult result =
            transactions.addRegradeRequest(user, group, request);
        buildURL = ASSIGNMENT_URL;
        xml = xmlBuilder.buildStudentAssignmentPage(user, assign);
        if (result.getSuccess())
          result.setValue("Successfully added regrade request");
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } // Restores a previously removed announcement
    else if (action.equals(ACT_RESTOREANNOUNCE)) {
      Announcement announce = getAnnouncement(request.getParameter(P_ID));
      if (user.isCategoryPrivByCourse(announce.getCourse())) {
        TransactionResult result =
            transactions.restoreAnnouncement(user, announce);
        buildURL = COURSEADMIN_URL;
        xml = xmlBuilder.buildCoursePage(user, announce.getCourse());
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } // Restores a previously removed assignment
    else if (action.equals(ACT_RESTOREASSIGN)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isAssignPrivByAssignment(assign)) {
        TransactionResult result = transactions.restoreAssignment(user, assign);
        buildURL = COURSEADMIN_URL;
        xml = xmlBuilder.buildCoursePage(user, assign.getCourse());
        if (result.getSuccess())
          result.setValue("Assignment restored successfully");
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side schedule page
    else if (action.equals(ACT_SCHEDULE)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      Course     course = assign == null ? null : assign.getCourse();
      /*
       * Since it's possible to assign any staff member to a timeslot, all staff
       * members should be able to see the schedule in case they've been
       * assigned to any slots. Previously only assignment-privileged staff
       * could view the schedule. - Evan, 5 / 25 / 06
       */
      if (user.isStaffInCourseByCourse(course)) {
        buildURL = ASSIGNSCHED_URL;
        xml = xmlBuilder.buildBasicSchedulePage(user, assign);
      } else buildURL = FORBIDDEN_URL;
    }
    // Send an email to the students and/or staff of a course
    else if (action.equals(ACT_SENDEMAIL)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAdminPrivByCourse(course)) {
        TransactionResult result = transactions.sendEmail(user, course, request);
        xml = xmlBuilder.buildEmailPage(user, course);
        xml = xmlBuilder.addStatus(xml, result);
        buildURL = EMAIL_URL;
      } else buildURL = FORBIDDEN_URL;
      // Search logs as a cmsadmin
    } else if (action.equals(ACT_SEARCHLOGS_CMSADMIN)) {
      if (user.isCMSAdmin()) {
        buildURL = CMSADMIN_LOGRESULTS_URL;
        xml = xmlBuilder.buildLogSearchPage(user, request);
        xmlBuilder.appendCMSAdminLogInfo(user, xml);
      } else buildURL = FORBIDDEN_URL;
    }
    // Search logs as a course admin
    else if (action.equals(ACT_SEARCHLOGS_COURSE)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAdminPrivByCourse(course)) {
        buildURL = COURSELOG_URL;
        xml = xmlBuilder.buildLogSearchPage(user, request, course);
        xml = xmlBuilder.buildStaffLogSearchPage(user, xml, course);
      } else buildURL = FORBIDDEN_URL;
    }
    // Add and/or edit content to/in a category
    else if (action.equals(ACT_SETADDNEDITCONTENTS)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      Category cat  = getCategory(request.getParameter(P_CATID));
      if (user.isCategoryPrivByCategory(cat)) {
        TransactionResult result =
            transactions.addNEditCtgContents(user, cat, request);
        if (result.getSuccess()) {
          buildURL = COURSEADMIN_URL;
          xml = xmlBuilder.buildCoursePage(user, course);
        } else {
          buildURL = CTGCONTENTSADMIN_URL;
          xml = xmlBuilder.buildCtgContentPage(user, cat);
          xml = xmlBuilder.addStatus(xml, result);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // Edit an announcement
    else if (action.equals(ACT_SETANNOUNCE)) {
      Announcement announce = getAnnouncement(request.getParameter(P_ID));
      Course course = announce.getCourse();
      if (user.isCategoryPrivByCourse(course)) {
        String newText = request.getParameter(P_ANNOUNCE);
        String poster  = user.getNetID();
        boolean remove = request.getParameter(P_REMOVEANNOUNCE) != null;
        TransactionResult result =
            transactions.editAnnouncement(user, announce, newText, remove);
        buildURL = COURSEADMIN_URL;
        xml = xmlBuilder.refreshCoursePage(user, announce);
        if (result.getSuccess())
          result.setValue("Announcement edited successfully");
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Set assignment properties/make new assignment
    else if (action.equals(ACT_SETASSIGN)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      Assignment assign = course.getAssignment(request.getParameter(P_ASSIGNID));
      if (course != null && user.isAssignPrivByCourse(course)) {
        TransactionResult result =
            transactions.setAssignmentProps(user, course, assign, request);
        if (result.getSuccess()) {
          xml = xmlBuilder.buildCoursePage(user, course);
          result.setValue("Assignment properties successfully set.");
          buildURL = COURSEADMIN_URL;
        } else {
          // xml = xmlBuilder.buildErrorAssignmentPage(p,
          // (Collection) result.getValue(), course, assign);

          /*
           * if (assign != 0) { xml = xmlBuilder.buildBasicAssignmentPage(p,
           * assign); } else { xml = xmlBuilder.buildAssignmentCreationPage(p,
           * course); }
           */
          if (assign == null) {
            // XXX Why don't we have a real error page for this?  --MJL
//            xml =
//                xmlBuilder.buildErrorAssignmentPage(user, (Map) result
//                    .getValue(), course, assign);
            String message = "Assignment creation error page not implemented.<br>";
            for (Iterator it = result.getErrors().iterator(); it.hasNext();) {
              TransactionError error = (TransactionError) it.next();
              message += error.getMessage()+"<br>";
            }
            
            xml =
                xmlBuilder.buildErrorPage(user.getNetID(), action,
                    new NotImplementedException(message));
            buildURL = ERROR_URL;
          } else {
            buildURL = ASSIGNADMIN_URL;
            xml = xmlBuilder.buildBasicAssignmentPage(user, assign);
          }
        }
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Set general properties for a category or create a new category for
    // the given course
    else if (action.equals(ACT_SETCATEGORY)) {
      Category cat    = getCategory(request.getParameter(P_CATID));
      Course   course = null;
      if (cat != null)
        course = cat.getCourse();
      else
        course = getCourse(request.getParameter(P_COURSEID));
      
      if (course != null && user.isCategoryPrivByCourse(course)) {
        TransactionResult result =
            transactions.createNEditCategory(user, course, request);
        if (result.getSuccess()) {
          buildURL = COURSEADMIN_URL;
          xml = xmlBuilder.buildCoursePage(user, course);
        } else {
          buildURL = CATEGORYADMIN_URL;
          if (cat == null)
            xml = xmlBuilder.buildNewCategoryPage(user, course);
          else xml = xmlBuilder.buildCategoryPage(user, cat);
          xml = xmlBuilder.addStatus(xml, result);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // Set course properties
    else if (action.equals(ACT_SETCOURSEPROPS)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isAdminPrivByCourse(course)) {
        TransactionResult result =
            transactions.setCourseProps(user, course, request);
        if (user.isAdminPrivByCourse(course) || !result.getSuccess()) {
          buildURL = COURSEPROPS_URL;
          xml = xmlBuilder.buildCoursePropertiesPage(user, course);
          if (result.getSuccess())
            result.setValue("Course properties successfully set");
          xml = xmlBuilder.addStatus(xml, result);
        } else // User may have revoked their own Admin privilege
        {
          buildURL = COURSEADMIN_URL;
          xml = xmlBuilder.buildCoursePage(user, course);
          if (result.getSuccess())
            result.setValue("Course properties successfully set");
          xml = xmlBuilder.addStatus(xml, result);
        }
      } else if (user.isCMSAdmin()) {
        TransactionResult result =
            transactions.setCourseProps(user, course, request);
        buildURL = CMSADMINCOURSEPROPS_URL;
        xml = xmlBuilder.buildCMSAdminCoursePropsPage(user, course);
        if (result.getSuccess())
          result.setValue("Course properties successfully set");
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Set the current semester
    else if (action.equals(ACT_SETCURSEMESTER)) {
      if (user.isCMSAdmin()) {
        buildURL = CMSADMIN_URL;
        Semester semester = getSemester(request.getParameter(P_ID));
        TransactionResult result = transactions.setCurrentSemester(user, semester);
        xml = xmlBuilder.buildCMSAdminPage(user);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Set final grades
    else if (action.equals(ACT_SETFINALGRADES)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isGradesPrivByCourse(course)) {
        TransactionResult result =
            transactions.setFinalGrades(user, course, request);
        xml = xmlBuilder.buildStudentsPage(user, course, false);
        xml = xmlBuilder.addStatus(xml, result);
        buildURL = STUDENTS_URL;
      } else buildURL = FORBIDDEN_URL;
    } // Set staff member course preferences
    else if (action.equals(ACT_SETSTAFFPREFS)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isStaffInCourseByCourse(course)) {
        TransactionResult result =
            transactions.setStaffPrefs(user, course, request);
        xml = xmlBuilder.buildStaffPrefsPage(user, course);
        xml = xmlBuilder.addStatus(xml, result);
        buildURL = STAFFPREFS_URL;
      } else buildURL = FORBIDDEN_URL;
    } // Set student grades from the view of all assignments for a single
    // student
    else if (action.equals(ACT_SETSTUDENTALLGRADES)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      User  student = getUser(request.getParameter(P_NETID));
      if (user.isAdminPrivByCourse(course)) {
        TransactionResult result =
            transactions.addGradesComments(user, false, course, request);
        xml = xmlBuilder.buildGradeStudentPage(user, course, student);
        buildURL = GRADEALLASSIGNS_URL;
        xml = xmlBuilder.addStatus(xml, result);
      }
    } // Set student grades
    else if (action.equals(ACT_SETSTUDENTGRADES)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isGradesPrivByAssignment(assign)) {
        TransactionResult result =
            transactions.addGradesComments(user, true, assign, request);
        List groupids = null;
        if (result.getSuccess()) {
          Object[] val = (Object[]) result.getValue();
          groupids = val != null ? (List) val[1] : null; // a List of Groups
          result.setValue(val[0]);
        }
        xml = xmlBuilder.buildGradeAssignPage(user, assign,
            (groupids != null && groupids.size() > 0) ? (Group) groupids.get(0)
                                                      : null);
        buildURL = GRADEASSIGN_URL;
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } // Set student course preferences
    else if (action.equals(ACT_SETSTUDENTPREFS)) {
      String scope = request.getParameter(P_APPLYSCOPE);
      if (scope == null) {
        // invalid data posted!
        Course course = getCourse(request.getParameter(P_COURSEID));
        if (course != null) {
          if (user.isStudentInCourseByCourse(course)) {
            TransactionResult result = new TransactionResult();
            result.addError("No scope (which courses to apply settings to?) was received from browser.");
            buildURL = STUDENTPREFS_URL;
            xml = xmlBuilder.buildStudentPrefsPage(user, course);
            xml = xmlBuilder.addStatus(xml, result);
          } else buildURL = FORBIDDEN_URL;
        } else buildURL = FORBIDDEN_URL;
      } else if (scope.equals(P_APPLYTHIS)) {
        Course course = getCourse(request.getParameter(P_COURSEID));
        if (user.isStudentInCourseByCourse(course)) {
          TransactionResult result =
              transactions.setStudentPrefs(user, course, request);
          buildURL = STUDENTPREFS_URL;
          xml = xmlBuilder.buildStudentPrefsPage(user, course);
          xml = xmlBuilder.addStatus(xml, result);
        } else buildURL = FORBIDDEN_URL;
      } else if (scope.equals(P_APPLYALL)) {
        TransactionResult result = transactions.setAllStudentPrefs(user, request);

        Course course = getCourse(request.getParameter(P_COURSEID));
        if (course != null) {
          // performed from a course's notification page
          buildURL = STUDENTPREFS_URL;
          xml = xmlBuilder.buildStudentPrefsPage(user, course);
          xml = xmlBuilder.addStatus(xml, result);
        } else {
          // performed from main notification page
          buildURL = FORBIDDEN_URL; // TODO
          // xml = xmlBuilder.buildStudentPrefsPage(p, course);
          // xml = xmlBuilder.addStatus(xml, result);
          // incomplete!
        }
      } else if (scope.equals(P_APPLYDEFAULT)) {
        // transactions.setDefStudentPrefs(p, request);
        buildURL = FORBIDDEN_URL; // unfinished
      } else {
        buildURL = FORBIDDEN_URL; // unfinished
      }
    }
    // View the staff course preferences page
    else if (action.equals(ACT_STAFFPREFS)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isStaffInCourseByCourse(course)) {
        xml = xmlBuilder.buildStaffPrefsPage(user, course);
        buildURL = STAFFPREFS_URL;
      } else buildURL = FORBIDDEN_URL;
    } // View all assignment grades for a given student
    else if (action.equals(ACT_STUDENTALLGRADES)) {
      Course course  = getCourse(request.getParameter(P_COURSEID));
      User   student = getUser(request.getParameter(P_NETID));
      if (user.isAdminPrivByCourse(course)) {
        Collection assignments = course.getAssignments();
        if (assignments.size() > 0) {
          xml = xmlBuilder.buildGradeStudentPage(user, course, student);
          buildURL = GRADEALLASSIGNS_URL;
        } else {
          xml = xmlBuilder.buildStudentsPage(user, course, false);
          xml = xmlBuilder.addStatus(xml, "No assignments to list", xmlBuilder.MSG_WARNING);
          buildURL = STUDENTS_URL;
        }
      } else buildURL = FORBIDDEN_URL;
    } // View the student course preferences page
    else if (action.equals(ACT_STUDENTPREFS)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isStudentInCourseByCourse(course)) {
        xml = xmlBuilder.buildStudentPrefsPage(user, course);
        buildURL = STUDENTPREFS_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // Sign out
    else if (action.equals(ACT_SIGNOUT)) {
      buildURL = SIGNOUT_URL;
    }
    // View student listing page for a course
    else if (action.equals(ACT_STUDENTS)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      boolean showGradeMsg = (request.getParameter(P_GRADEMESSAGE) != null);
      if (user.isAdminPrivByCourse(course)) {
        buildURL = STUDENTS_URL;
        xml = xmlBuilder.buildStudentsPage(user, course, false, showGradeMsg);
      } else buildURL = FORBIDDEN_URL;
    }
    // Download survey CVS
    else if (action.equals(ACT_SURVEYDOWNLOAD)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isAdminPrivByAssignment(assign)) {
        String filename = request.getParameter(P_FILENAME);
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=\""
            + filename + "\"");
        Collection surveyResultData =
            xmlBuilder.generateSurveyResultCSV(assign);
        TransactionResult result =
            transactions.exportSurveyResult(response.getOutputStream(),
                surveyResultData);
        if (!result.getSuccess()) {
          // xml = xmlBuilder.buildCMSAdminPage(p);
          // xml = xmlBuilder.addStatus(xml, result);
          // buildURL = UPLOAD_URL;
        } else {
          // buildURL = null;
          // xml = null;
        }
        buildURL = null;
        xml = null;
      } else buildURL = FORBIDDEN_URL;
    }
    // Display result of a survey
    else if (action.equals(ACT_SURVEYRESULT)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isAdminPrivByAssignment(assign)) {
        xml = xmlBuilder.buildSurveyResultPage(user, assign);
        buildURL = SURVEYRESULT_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // Staff member manually adding a group to a timeslot
    else if (action.equals(ACT_TIMESLOTASSIGN)) {
      Group      group  = getGroup(request.getParameter(P_GROUPID));
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      Course     course = assign.getCourse();
      // TODO: should there be more here?
    }
    // Staff member updating multiple timeslots at once
    else if (action.equals(ACT_TIMESLOTSUPDATE)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      Course     course = assign.getCourse();
      if (user.isAssignPrivByCourse(course)) {
        buildURL = ASSIGNSCHED_URL;
        java.util.Collection results = new java.util.Vector();
        Iterator entries = request.getParameterMap().entrySet().iterator();
        while (entries.hasNext()) {
          // entry.key   is a String representing the group
          // entry.value is a String representing the timeslot
          Map.Entry entry = (Map.Entry) entries.next();
          
          if (!(entry.getKey().equals(P_ASSIGNID) || entry.getKey().equals("action"))) {
            Group    group = getGroup((String) entry.getKey());
            TimeSlot slot  = getTimeSlot((String) entry.getValue());
            if (slot != null) {
              TransactionResult result =
                  transactions.changeGroupSlotBy(user, group, assign, slot, true, false);
              results.add(result);
            }
          }
        }
        java.util.Iterator resultsI = results.iterator();
        xml = xmlBuilder.buildBasicSchedulePage(user, assign);
        while (resultsI.hasNext()) {
          TransactionResult result = (TransactionResult) resultsI.next();
          xml = xmlBuilder.addStatus(xml, result);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // Staff member creating a block of timeslots from the schedule page
    else if (action.equals(ACT_TIMESLOTSCREATE)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      Course     course = assign == null ? null : assign.getCourse();
      // check for privileges
      if (course != null && user.isAssignPrivByCourse(course)) {
        buildURL = ASSIGNSCHED_URL;
        // perform timeslot creation
        TransactionResult result =
            transactions.createTimeSlots(user, assign, request);
        if (result.getSuccess())
          result.setValue("Time slot creation was successful");
        // reload the schedule page with message status
        xml = xmlBuilder.buildBasicSchedulePage(user, assign);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Staff member deleting one or more checked timeslots
    else if (action.equals(ACT_TIMESLOTSDELETE)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      Course     course = assign == null ? null : assign.getCourse();
      if (course != null && user.isAssignPrivByCourse(course)) {
        buildURL = ASSIGNSCHED_URL;
        TransactionResult result =
            transactions.deleteTimeSlots(user, assign, request);
        if (result.getSuccess())
          result.setValue("Time slot deletion was successful");
        xml = xmlBuilder.buildBasicSchedulePage(user, assign);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Student adding a timeslot from the assignment page
    else if (action.equals(ACT_TIMESLOTSELECT)) {
      Group       group  = getGroup(request.getParameter(P_GROUPID));
      Assignment  assign = getAssignment(request.getParameter(P_ASSIGNID));
      GroupMember gm = group.findGroupMember(user);
      
      if (gm != null) {
        buildURL = ASSIGNMENT_URL;
        boolean error = false;
        TransactionResult result =
            transactions.changeGroupSlot(user, group, assign, request, true, true);
        xml = xmlBuilder.refreshStudentAssignmentPage(user, group);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Staff member manually adding a group to a timeslot
    else if (action.equals(ACT_TIMESLOTUNASSIGN)) {
      Group       group  = getGroup(request.getParameter(P_GROUPID));
      Assignment  assign = getAssignment(request.getParameter(P_ASSIGNID));
      Course     course = assign == null ? null : assign.getCourse();
      if (user.isAssignPrivByCourse(course)) {
        buildURL = ASSIGNSCHED_URL;
        TransactionResult result =
            transactions.changeGroupSlot(user, group, assign, request, false, false);
        xml = xmlBuilder.buildBasicSchedulePage(user, assign);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Student removing his/her group from its timeslot on the assignment
    // page
    else if (action.equals(ACT_TIMESLOTUNSELECT)) {
      Group       group  = getGroup(request.getParameter(P_GROUPID));
      Assignment  assign = getAssignment(request.getParameter(P_ASSIGNID));
      GroupMember gm = group.findGroupMember(user);
      
      if (gm != null) {
        buildURL = ASSIGNMENT_URL;
        TransactionResult result =
            transactions.changeGroupSlot(user, group, assign, request, false, false);
        xml = xmlBuilder.refreshStudentAssignmentPage(user, group);
        xml = xmlBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Disband a single group (to disband multiple groups, see
    // ACT_APPLYTOGROUPS)
    else if (action.equals(ACT_UNGROUP)) {
      Group group = getGroup(request.getParameter(P_GROUPID));
      
      if (group != null) {
        Assignment assign = group.getAssignment();
        if (user.isGroupsPrivByAssignment(assign)) {
          // disbandGroup() checks whether they've already been graded
          TransactionResult result = transactions.disbandGroup(user, group);
          buildURL = GRADEASSIGN_URL;
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
          xmlBuilder.addStatus(xml, result);
        } else buildURL = FORBIDDEN_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // Upload grades
    else if (action.equals(ACT_UPLOADGRADES)) {
      Assignment assign = getAssignment(request.getParameter(P_ASSIGNID));
      if (user.isGradesPrivByAssignment(assign)) {
        TransactionResult table = transactions.parseGradesFile(assign, request);
        if (table.getSuccess() && table.getValue() == null) {
          buildURL = GRADEASSIGN_URL;
          xml = xmlBuilder.buildGradeAssignPage(user, assign);
        } else {
          buildURL = CONFIRMTABLE_URL;
          xml =
              xmlBuilder.buildConfirmPage(user, XMLBuilder.CONFIRM_ASSIGNINFO,
                  assign, table);
          // if (table.getSuccess()) {
          session.setAttribute(A_GRADESTABLE, table.getValue());
          // }
        }
      } else {
        buildURL = FORBIDDEN_URL;
      }
    }
    // upload CSV-format information of any kind about students, possibly
    // only for a particular course
    else if (action.equals(ACT_UPLOADSTUDENTINFO)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      boolean isClasslist = (request.getParameter(P_ISCLASSLIST) != null);
      boolean hasPermission = false;
      if (course == null) // assume info is system-wide, submitted by a
        // cmsadmin
        hasPermission = user.isCMSAdmin();
      else
      // assume info is course-specific
      hasPermission = user.isAdminPrivByCourse(course);
      if (hasPermission) {
        TransactionResult result =
            transactions.parseCSVInfo(request,
                isClasslist ? CSVFileFormatsUtil.CLASSLIST_FORMAT : null);
        if (result.getSuccess())
          session.setAttribute(A_PARSEDCSVINFO, result.getValue());
        xml = xmlBuilder.buildConfirmPage(
            user,
            (course == null) ? xmlBuilder.CONFIRM_GENERAL
                             : xmlBuilder.CONFIRM_COURSEINFO,
            course,
            result);
        xml = xmlBuilder.addStatus(xml, result);
        buildURL = CONFIRMTABLE_URL;
        session.setAttribute(A_ISCLASSLIST, new Boolean(isClasslist));
      } else buildURL = FORBIDDEN_URL;
    }
    // Enter Cornell-member perspective
    else if (action.equals(ACT_VIEWCORNELLMEM)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isInStaffAsBlankMode()) {
        resetToStaff(request, user);
      }
      if (user.isAdminPrivByCourse(course)) {
        if (course.getCourseCCAccess()) {
          assumeMemberRole(request, user, course);
          buildURL = COURSE_URL;
          xml = xmlBuilder.buildCoursePage(user, course);
        } else {
          buildURL = COURSEADMIN_URL;
          xml = xmlBuilder.buildCoursePage(user, course);
          String error =
              "Cannot view course page as Cornell Member: that authorization level has no access to the course page<br>";
          error += "Navigate to Course Options to view/edit permissions";
          xml = xmlBuilder.addStatus(xml, error, xmlBuilder.MSG_ERROR);
        }
      } else buildURL = FORBIDDEN_URL;
      // Enter Guest perspective of course
    } else if (action.equals(ACT_VIEWGUEST)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isInStaffAsBlankMode()) {
        resetToStaff(request, user);
      }
      if (user.isAdminPrivByCourse(course)) {
        if (course.getCourseGuestAccess()) {
          assumeGuestRole(request, user, course);
          buildURL = COURSE_URL;
          xml = xmlBuilder.buildCoursePage(user, course);
        } else {
          buildURL = COURSEADMIN_URL;
          xml = xmlBuilder.buildCoursePage(user, course);
          String error =
              "Cannot view course page as Guest: "
                  + "that authorization level has no access "
                  + "to the course page<br>";
          error += "Navigate to Course Options to view/edit permissions";
          xml = xmlBuilder.addStatus(xml, error, xmlBuilder.MSG_ERROR);
        }
      } else buildURL = FORBIDDEN_URL;
    } else if (action.equals(ACT_VIEWRESET)) {
      if (user.isInStaffAsBlankMode()) {
        Course course = getAssumedCourse(request, user);
        resetToStaff(request, user);
        if (user.isStaffInCourseByCourse(course)) {
          buildURL = COURSEADMIN_URL;
          xml = xmlBuilder.buildCoursePage(user, course);
        } else {
          // This should only happen if the staff member is removed
          // from staff while in the "view as" mode, or if the view
          // reset is attempted when it is unnecessary.
          // XXX In the latter case, should just go to the course admin
          // XXX page after checking that the staff member is in the
          // XXX course.
          buildURL = FORBIDDEN_URL;
        }
      }
    }
    // Enter Student perspective of course
    else if (action.equals(ACT_VIEWSTUDENT)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      String studentID = request.getParameter(P_NETID);
      if (user.isInStaffAsBlankMode()) {
        resetToStaff(request, user);
      }
      if (user.isAdminPrivByCourse(course)) {
        user = assumeStudentRole(request, user, getUser(studentID), course);
        if (user == null) {
          xml = (Document) session.getAttribute(A_DISPLAYDATA);
          xml = xmlBuilder.addStatus(xml, "failed to change role", xmlBuilder.MSG_ERROR);
          buildURL = (String) session.getAttribute(A_URL);
        } else {
          xml = xmlBuilder.buildCoursePage(user, course);
          buildURL = COURSE_URL;
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // View the page to upload a general CSV student info file
    else if (action.equals(ACT_VIEWUPLOAD)) {
      Course course = getCourse(request.getParameter(P_COURSEID));
      if (user.isStaffInCourseByCourse(course)) {
        xml = xmlBuilder.buildCSVUploadPage(user, course);
        buildURL = UPLOAD_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // null or unrecognized action (possibly first logging in -
    // ACT_OVERLOGIN)
    else {
      // All Authenticated Principals can view their overview
      Semester semester = getSemester(request.getParameter(P_SEMESTERID));
      Course   course   = null;
      
      /*
       * For users only involved in one course, this code jumps them directly,
       * into the course page for that course.
       */
      if (semester == null)
        semester = xmlBuilder.getDatabase().getCurrentSemester();
      course = user.getSoloCourse(semester);
      
      if (course == null) {
        xml = xmlBuilder.buildOverview(user, semester);
        buildURL = OVERVIEW_URL;
      } else {
        xml = xmlBuilder.buildCoursePage(user, course);
        if (user.isStaffInCourseByCourse(course)) {
          buildURL = COURSEADMIN_URL;
        } else {
          buildURL = COURSE_URL;
        }
      }
    }
    boolean xhfg = true;
    if (debug) Profiler.endAction(action);
    return new RequestHandlerInfo(buildURL, xml);
  }

  private Course getAssumedCourse(HttpServletRequest request, User user) {
    // TODO Auto-generated method stub
    return null;
  }

  private User assumeStudentRole(ServletRequest request, User staff, User assumed, Course course) {
    // TODO Auto-generated method stub
    return staff;
  }
  
  private User assumeGuestRole(ServletRequest request, User staff, Course course) {
    // TODO
    return staff;
  }
  
  private User assumeMemberRole(ServletRequest request, User staff, Course course) {
    // TODO
    return staff;
  }
  
  private void resetToStaff(ServletRequest request, User user) {
    // TODO Auto-generated method stub
  }

  private SubProblem getSubProblem(String subProblemID) {
    return xmlBuilder.getDatabase().getSubProblem(subProblemID);
  }

  private TimeSlot getTimeSlot(String slotID) {
    return xmlBuilder.getDatabase().getTimeSlot(slotID);
  }

  private CategoryRow getCategoryRow(String rowID) {
    return xmlBuilder.getDatabase().getCategoryRow(rowID);
  }

  private SiteNotice getNotice(String noticeID) {
    return xmlBuilder.getDatabase().getSiteNotice(noticeID);
  }

  private Category getCategory(String catID) {
    return xmlBuilder.getDatabase().getCategory(catID);
  }

  private Announcement getAnnouncement(String announceID) {
    return xmlBuilder.getDatabase().getAnnouncement(announceID);
  }

  private User getUser(String netID) {
    return xmlBuilder.getDatabase().getUser(netID);
  }

  private Course getCourse(String courseID) {
    return xmlBuilder.getDatabase().getCourse(courseID);
  }

  private Semester getSemester(String semesterID) {
    return xmlBuilder.getDatabase().getSemester(semesterID);
  }

  private Group getGroup(String groupID) {
    return xmlBuilder.getDatabase().getGroup(groupID);
  }

  private Assignment getAssignment(String assignID) {
    return xmlBuilder.getDatabase().getAssignment(assignID);
  }

  /**
   * Parse the request parameters to get all selected netIDs
   * (the main grading page is the one with a checkbox next to each group for a given assignment)
   * @param request
   * @return A List of Groups
   */
  private List extractGroupsFromMainGradingPageRequest(HttpServletRequest request) {
    List result = new ArrayList();
    Iterator i = request.getParameterMap().keySet().iterator();
    while(i.hasNext()) {
      String param = ((String)i.next()).trim();
      if (param.startsWith(P_GRADEGROUP)) {
        Group group = getGroup(param.split(P_GRADEGROUP)[1]);
        if (group != null)
          result.add(group);
      }
    }
    
    Group group = getGroup(request.getParameter(P_GROUPID));
    if (group != null) {
      result.add(group);
    }
    return result;
  }
  
  private void sendFile(FileData file, HttpServletResponse response)
      throws IOException, IllegalArgumentException {
    response.setContentType("application/download");
    response.setHeader("Content-disposition", "attachment; filename=\""
        + file.getName() + "\"");
    Streams.copy(file.read(), new BufferedOutputStream(response.getOutputStream()), true);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
