/*
 * Created on Jun 20, 2004
 */
package cms.www;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

import javax.ejb.FinderException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadException;
import org.w3c.dom.Document;

import cms.www.util.*;
import cms.www.xml.XMLBuilder;

import edu.cornell.csuglab.cms.author.Principal;
import edu.cornell.csuglab.cms.author.UserNotFoundException;
import edu.cornell.csuglab.cms.base.*;

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

  /*
   * // XXX TEMPORARY TEST VARIABLE ILL PROBABLY FORGET TO REMOVE public static
   * boolean firstRequest = true;
   */

  public static final String defaultID = "sg252";

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
      P_GROUPSFROM = "groupsfrom", // asgn ID of assignment from which to
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
                                    // to ID of subproblem to regrade
      P_REGRADENETID = "regradenetid", // netID of staff member submitting
                                        // regrade response
      /* grading */
      P_GRADE = "grade", // used on various grading pages; a number
      P_OLDGRADE = "oldgrade", // used for conflict resolution, sends back the
                                // original value of the associated grade
      P_GRADEMESSAGE = "grademsg", // whether user has selected a "Grade" link,
                                    // but yet to select students/assignments
      /* assignment grading page */
      P_GRADEGROUP = "gradegroup_", // followed by a group ID; "checked" or not
                                    // present
      P_ASSIGNPROBNAME = "assignsubprobname", // which subproblem to grade; can
                                              // be "<All Parts>"
      P_ASSIGNGRADER = "assigngrader", // netID of grader to be assigned
      P_NETIDLIST = "groupslist", // list of netIDs to be grouped together for a
                                  // given assignment
      P_GRADESFILE = "gradesfile", // file associating netIDs with grades for a
                                    // particular assignment
      P_EXTGROUPID = "extgroupid", // ID of group to receive an extension
      /*
       * detailed grading pages (all students for one assignment, all
       * assignments for one student)
       */
      P_COMMENTTEXT = "commenttext", // prepended to group ID; text of comment
                                      // by grader
      P_COMMENTFILE = "commentfile", // prepended to group ID; file with
                                      // comment by grader
      P_REGRADERESPONSE = "regraderesponse", // prepended to group ID and
                                              // regrade request ID; boolean
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
      P_LOGSEARCH_COURSE = "logsearchcourseid", // course ID
      P_LOGSEARCH_ASGN = "logsearchassignid", // assignment ID
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
      P_TIMESLOTID = "timeslotid", // ID of slot for which to add/remove group
      // editing schedule
      P_NEWTSNAME = "newtsname", // arbitrary timeslot name; text
      P_NEWTSSTAFF = "newtsstaff", // staff netID
      P_NEWTSLOCATION = "newtslocation", // arbitrary location name; text
      P_NEWTSSTARTDATE = "newtsstartdate", // start date/time
      P_NEWTSSTARTTIME = "newtsstarttime", // ditto
      P_NEWTSSTARTAMPM = "newtsstartampm", // ditto
      P_NEWTSMULTIPLICITY = "newtsmultiplicity", // number of consecutive slots
                                                  // to create
      P_DELETETIMESLOT = "deletetimeslot_", // prepended to timeslot ID;
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
      P_NEWITEMFILEPATH = "newitemfilepath_";

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

  private HashMap debugPrincipalMap;

  /**
   * Initialize the Servlet
   * 
   * @param config
   *                Configuration info for the servlet
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    if (transactions == null) {
      transactions = new TransactionHandler();
    }
    try {
      debug = XMLBuilder.getDatabase().isDebugMode();
      if (debug) {
        debugPrincipalMap = new HashMap();
      }
      maxFileSize = XMLBuilder.getDatabase().getMaxFileSize();
    } catch (Exception e) {
      e.printStackTrace();
    }
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
  private Principal setUpPrincipal(HttpSession session,
      HttpServletRequest request, String action) throws ServletException {
    Principal p = null;
    try {
      p = (Principal) session.getAttribute(A_PRINCIPAL);
      if (debug) {
        String debugID = request.getParameter(P_DEBUGID);
        if (debugID == null) {
          if (p == null
              && (p = (Principal) debugPrincipalMap.get(request.getLocalAddr())) == null) {
            p =
                new Principal(XMLBuilder.getDatabase(), Principal.guestid,
                    request.getRemoteAddr());
          }
        } else {
          p =
              new Principal(XMLBuilder.getDatabase(), debugID, request
                  .getRemoteAddr());
          debugPrincipalMap.put(request.getLocalAddr(), p);
        }
      } else {
        String nodebugloginID = request.getHeader("remote_user"); // Authenticated
                                                                  // user
        nodebugloginID =
            nodebugloginID == null ? Principal.guestid : nodebugloginID;
        if ((action != null)
            && (p == null || !p.getPrincipalID().equals(nodebugloginID))) {
          try {
            p =
                new Principal(XMLBuilder.getDatabase(), nodebugloginID, request
                    .getRemoteAddr());
          } catch (UserNotFoundException e) {
            p =
                new Principal(XMLBuilder.getDatabase(), Principal.guestid,
                    request.getRemoteAddr());
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return p;
  }

  /**
   * Handle Get and Post requests
   * 
   * @param request
   *                The Servlet Request to handle
   * @param response
   *                The Servlet Response to return
   * @throws ServletException,
   *                 IOException
   */
  protected void processRequest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter(P_ACTION);
    response.setCharacterEncoding("iso-8859-1");
    String path = request.getPathInfo();
    if (path == null) {
      response.sendRedirect(request.getContextPath() + request.getServletPath()
          + "/");
      return;
    }
    if (path != null && !path.equals("/")) {
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
      Document xml = null;
      Principal p = null;
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
        xml = XMLBuilder.buildHomepage();
      } else {
        // Set up Principal and netID in debug mode
        p = setUpPrincipal(session, request, action);
        if (p == null) {
          buildURL = HOMEPAGE_URL;
          xml = XMLBuilder.buildHomepage();
        } else {
          String netid = p.getNetID(); // netid of appUser if in
          // staffAs_ mode, apparentID
          // of principal otherwise
          try {
            RequestHandlerInfo info =
                handleSpecificAction(action, request, response, session, p);
            if (info == null)
              throw new RuntimeException(
                  "Action handler return value should not be null!");
            buildURL = info.getBuildURL();
            xml = info.getXMLDocument();
          } catch (Exception nfe) {
            // Bad input - go to overview
            xml = XMLBuilder.buildErrorPage(p.getNetID(), action, nfe);
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
      session.setAttribute(A_PRINCIPAL, p);
      if (buildURL != null) {
        redirectTo(buildURL, request, response);
      }
    } catch (Exception e) {
      System.out.println("Error in AccessController.processRequest(): " + e);
      e.printStackTrace();
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
   * @param p
   * @param xml
   * @return A RequestHandlerInfo giving all the info needed to build output, if
   *         any. The return value may have null fields (meaning there should be
   *         no output), but WILL NOT BE NULL.
   * @see AccessController.RequestHandlerInfo (above)
   */
  private RequestHandlerInfo handleSpecificAction(String action,
      HttpServletRequest request, HttpServletResponse response,
      HttpSession session, Principal p) throws FinderException, IOException,
      RemoteException, SQLException, FileUploadException {
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
      Long semesterID = null;
      try {
        semesterID =
            new Long(Long.parseLong(request.getParameter(P_SEMESTERID)));
      } catch (NumberFormatException e) {
      }
      // Overview resets the principal to its standard view
      if (p.isInStaffAsBlankMode()) {
        p.resetToStaffMode();
      }
      buildURL = OVERVIEW_URL;
      xml = XMLBuilder.buildOverview(p, semesterID);
    }
    // Accept a group invitation
    else if (action.equals(ACT_ACCEPT)) {
      long groupID = Util.parseLong(request.getParameter(P_GROUPID));
      if (p.isStudentInCourseByGroupID(groupID)) {
        buildURL = ASSIGNMENT_URL;
        TransactionResult result = transactions.acceptInvitation(p, groupID);
        xml = XMLBuilder.refreshStudentAssignmentPage(p, groupID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Add a CMS administrator
    else if (action.equals(ACT_ADDCMSADMIN)) {
      if (p.isCMSAdmin()) {
        buildURL = CMSADMIN_URL;
        TransactionResult result =
            transactions.addCMSAdmin(p, request.getParameter(P_NETID));
        xml = XMLBuilder.buildCMSAdminPage(p);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the add-and-edit-category-contents page
    else if (action.equals(ACT_ADDNEDITCONTENTS)) {
      long categoryID = Util.parseLong(request.getParameter(P_CATID));
      if (p.isCategoryPrivByCategoryID(categoryID)) {
        buildURL = CTGCONTENTSADMIN_URL;
        xml = XMLBuilder.buildCtgContentPage(p, categoryID);
      } else buildURL = FORBIDDEN_URL;
    }
    // Add a course under the current semester
    else if (action.equals(ACT_ADDCOURSE)) {
      if (p.isCMSAdmin()) {
        String courseCode = request.getParameter(P_CODE), courseName =
            request.getParameter(P_COURSENAME);
        TransactionResult result =
            transactions.addCourse(p, courseCode, courseName);
        buildURL = CMSADMIN_URL;
        xml = XMLBuilder.buildCMSAdminPage(p);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Set a new sitewide notice
    else if (action.equals(ACT_NEWNOTICE)) {
      if (p.isCMSAdmin()) {
        String text = request.getParameter(P_NOTICETEXT);
        String author = p.getNetID();
        boolean hidden = request.getParameter(P_HIDDEN) != null;
        String date = request.getParameter(P_NOTICEEXPDATE);
        String time = request.getParameter(P_NOTICEEXPTIME);
        String ampm = request.getParameter(P_NOTICEEXPAMPM);
        Timestamp exp;
        try {
          exp = DateTimeUtil.parseDate(date, time, ampm);
        } catch (ParseException e) {
          exp = null;
        }

        TransactionResult result =
            transactions.addNotice(p, text, author, exp, hidden);
        buildURL = CMSADMIN_URL;
        xml = XMLBuilder.buildCMSAdminPage(p);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Add students to a course
    else if (action.equals(ACT_ADDSTUDENTS)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.hasStudentsPageAccess(courseID)) {
        TransactionResult result =
            transactions.addStudentsToCourse(p, courseID, request);
        buildURL = STUDENTS_URL;
        xml = XMLBuilder.buildStudentsPage(p, courseID, true);
        if (result.hasErrors()) {
          xml = XMLBuilder.addStatus(xml, result);
        } else {
          xml =
              XMLBuilder.addStatus(xml, "All students added successfully",
                  XMLBuilder.MSG_NORMAL);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // goto add student page
    else if (action.equals(ACT_ADDSTUDENTPAGE)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.hasStudentsPageAccess(courseID)) {
        xml = XMLBuilder.buildStudentsPage(p, courseID, true);
        buildURL = STUDENTS_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // Apply to groups (covers most actions on the main grading page)
    else if (action.equals(ACT_APPLYTOGROUPS)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      String gradingAction = request.getParameter(P_SUBMIT);
      gradingAction = gradingAction == null ? "" : gradingAction;
      if (gradingAction.equals(GA_GRADE)) {
        if (p.isGradesPrivByAssignmentID(assignID)) {
          AssignmentLocal assign =
              XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                  assignID);
          Collection groupIDs = null;
          if (!p.isAssignPrivByAssignmentID(assignID)
              && assign.getAssignedGraders()) {
            groupIDs =
                XMLBuilder.getDatabase().assignedToGroups(assignID,
                    p.getNetID(),
                    Util.extractGroupIDsFromMainGradingPageRequest(request));
          } else {
            groupIDs = Util.extractGroupIDsFromMainGradingPageRequest(request);
          }
          if (groupIDs.size() > 0) {
            xml = XMLBuilder.buildGradePage(p, assignID, groupIDs);
            buildURL = GRADESTUDENTS_URL;
          } else {
            xml = XMLBuilder.buildGradeAssignPage(p, assignID);
            // xml = XMLBuilder.addStatus(xml, "No groups selected",
            // XMLBuilder.MSG_NORMAL);
            buildURL = GRADEASSIGN_URL;
          }
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_FILES)) {
        List groupIDs = Util.extractGroupIDsFromMainGradingPageRequest(request);
        if (transactions.authorizeGroupFiles(p, groupIDs)) {
          if (groupIDs.size() > 0) {
            response.setContentType("application/zip");
            response.setHeader("Content-disposition", "attachment; filename=\""
                + SUBMISSIONS_ZIP_FILENAME + "\"");
            transactions.uploadGroupSubmissions(groupIDs, response
                .getOutputStream());
          }
          buildURL = null;
          xml = null;
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_EMAIL)) {
        long courseID = Util.parseLong(request.getParameter(P_COURSEID));
        if (p.isAdminPrivByCourseID(courseID)
            || p.isGradesPrivByCourseID(courseID)
            || p.isGroupsPrivByCourseID(courseID)) {
          Collection groupIDs =
              Util.extractGroupIDsFromMainGradingPageRequest(request);
          xml = XMLBuilder.buildEmailPage(p, courseID, groupIDs);
          buildURL = EMAIL_URL;
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_GRANT)
          || gradingAction.equals(GA_CHANGE)) { // grant or change an extension
        if (p.isGradesPrivByAssignmentID(assignID)) {
          long groupID = Util.parseLong(request.getParameter(P_EXTGROUPID));
          TransactionResult result =
              transactions.setExtension(p, groupID, request);
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
          buildURL = GRADEASSIGN_URL;
          if (result.hasErrors()) {
            xml = XMLBuilder.addStatus(xml, result);
          } else {
            xml =
                XMLBuilder.addStatus(xml, "Successfully granted extension",
                    XMLBuilder.MSG_NORMAL);
          }
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_GROUP)) { // group all students/groups
                                                    // whose checkboxes were
                                                    // selected
        if (p.isGroupsPrivByAssignmentID(assignID)) {
          List groupIDs =
              Util.extractGroupIDsFromMainGradingPageRequest(request);
          // TransactionHandler makes sure they haven't already been graded
          TransactionResult result =
              transactions.groupSelectedStudents(p, assignID, groupIDs);
          buildURL = GRADEASSIGN_URL;
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
          if (result.hasErrors())
            xml = XMLBuilder.addStatus(xml, result);
          else xml =
              XMLBuilder.addStatus(xml, (String) result.getValue(),
                  XMLBuilder.MSG_NORMAL);
        }
      } else if (gradingAction.equals(GA_UNGROUP)) { // ungroup all groups
                                                      // whose checkboxes were
                                                      // selected
        if (p.isGroupsPrivByAssignmentID(assignID)) {
          List groupIDs =
              Util.extractGroupIDsFromMainGradingPageRequest(request);
          // TransactionHandler makes sure they haven't already been graded
          TransactionResult result =
              transactions.ungroupSelectedStudents(p, assignID, groupIDs);
          buildURL = GRADEASSIGN_URL;
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
          if (result.hasErrors())
            xml = XMLBuilder.addStatus(xml, result);
          else xml =
              XMLBuilder.addStatus(xml, "Successfully ungrouped students",
                  XMLBuilder.MSG_NORMAL);
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_CREATEGROUP)) { // create group from
                                                          // netids in textbox
                                                          // contents
        if (p.isGroupsPrivByAssignmentID(assignID)) {
          List netids =
              StringUtil.parseNetIDList(request.getParameter(P_NETIDLIST));
          TransactionResult result =
              transactions.createGroup(p, netids, assignID);
          buildURL = GRADEASSIGN_URL;
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
          if (result.hasErrors())
            xml = XMLBuilder.addStatus(xml, result);
          else xml =
              XMLBuilder.addStatus(xml, "Successfully grouped students",
                  XMLBuilder.MSG_NORMAL);
        } else buildURL = FORBIDDEN_URL;
      } else if (gradingAction.equals(GA_ASSIGNGRADER)) { // ASSIGN GRADERS
        AssignmentLocal assign =
            XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                assignID);
        if (p.isAdminPrivByCourseID(assign.getCourseID())) {
          String subprobname = request.getParameter(P_ASSIGNPROBNAME);
          String grader = request.getParameter(P_ASSIGNGRADER);
          buildURL = GRADEASSIGN_URL;
          TransactionResult result =
              transactions.assignGrader(p, assignID, subprobname, grader,
                  request.getParameterMap());
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
          XMLBuilder.addStatus(xml, result);
        } else buildURL = FORBIDDEN_URL;
      } else {
        if (p.isGradesPrivByAssignmentID(assignID)
            || p.isGroupsPrivByAssignmentID(assignID)) {
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
          buildURL = GRADEASSIGN_URL;
        } else buildURL = FORBIDDEN_URL;
      }
    }
    // View student-side assignment page
    else if (action.equals(ACT_ASSIGN)) {
      System.out.println("Attempting to display assignment page");
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      CourseLocal course = null;
      try {
        course =
            XMLBuilder.getDatabase().courseHome().findByAssignmentID(assignID);
      } catch (Exception e) {
      }

      // Check if principal is a student in this course
      if (course != null && p.hasAssignAccess(course.getCourseID(), assignID)) {
        buildURL = ASSIGNMENT_URL;
        xml = XMLBuilder.buildStudentAssignmentPage(p, assignID);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side assignment page
    else if (action.equals(ACT_ASSIGNADMIN)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isAssignPrivByAssignmentID(assignID)) {
        buildURL = ASSIGNADMIN_URL;
        xml = XMLBuilder.buildBasicAssignmentPage(p, assignID);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side survey page
    else if (action.equals(ACT_SURVEYADMIN)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isAssignPrivByAssignmentID(assignID)) {
        buildURL = ASSIGNADMIN_URL;
        xml = XMLBuilder.buildBasicAssignmentPage(p, assignID);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side quiz page
    else if (action.equals(ACT_QUIZADMIN)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isAssignPrivByAssignmentID(assignID)) {
        buildURL = ASSIGNADMIN_URL;
        xml = XMLBuilder.buildBasicAssignmentPage(p, assignID);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side assignment LIST page
    else if (action.equals(ACT_ASSIGNLISTADMIN)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isStaffInCourseByCourseID(courseID)) {
        buildURL = ASSIGNLISTADMIN_URL;
        xml = XMLBuilder.buildCoursePage(p, courseID);
      } else buildURL = FORBIDDEN_URL;
    }
    // Cancel an unaccepted invitation
    else if (action.equals(ACT_CANCEL)) {
      long groupID = Util.parseLong(request.getParameter(P_GROUPID));
      if (p.isStudentInCourseByGroupID(groupID)) {
        String cancelNetID = (String) request.getParameter(P_NETID);
        buildURL = ASSIGNMENT_URL;
        TransactionResult result =
            transactions.cancelInvitation(p, cancelNetID, groupID);
        xml = XMLBuilder.refreshStudentAssignmentPage(p, groupID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View one of the CMS administration pages
    else if (action.equals(ACT_CMSADMIN)) {
      if (p.isInStaffAsBlankMode()) {
        p.resetToStaffMode();
      }
      if (p.isCMSAdmin()) {
        buildURL = CMSADMIN_URL;
        xml = XMLBuilder.buildCMSAdminPage(p);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the CMS admin course-edit page
    else if (action.equals(ACT_CMSADMINCOURSEPROPS)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      // if the cms admin happens to be course staff, don't shut him/her out of
      // full access
      if (p.isStaffInCourseByCourseID(courseID)) {
        buildURL = COURSEPROPS_URL;
        xml = XMLBuilder.buildCoursePropertiesPage(p, courseID);
      } else if (p.isCMSAdmin()) {
        buildURL = CMSADMINCOURSEPROPS_URL;
        xml = XMLBuilder.buildCMSAdminCoursePropsPage(p, courseID);
      } else buildURL = FORBIDDEN_URL;
    }
    // Confirm the parsing of a Final Grades file and commit the grades
    else if (action.equals(ACT_CONFIRMFINALGRADES)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isGradesPrivByCourseID(courseID)) {
        List table = (List) session.getAttribute(A_GRADESTABLE);
        if (table != null) {
          TransactionResult result =
              transactions.commitFinalGradesFile(p, courseID, table);
          xml = XMLBuilder.buildStudentsPage(p, courseID, false);
          if (result.getSuccess()) {
            xml =
                XMLBuilder.addStatus(xml, (String) result.getValue(),
                    XMLBuilder.MSG_NORMAL);
          } else {
            xml = XMLBuilder.addStatus(xml, result);
          }
        } else {
          xml = XMLBuilder.buildStudentsPage(p, courseID, false);
        }
        buildURL = STUDENTS_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // Confirm the parsing of an uploaded table of student info, and commit
    // changes
    else if (action.equals(ACT_CONFIRMSTUDENTINFO)) {
      String courseIDs = request.getParameter(P_COURSEID);
      boolean hasPermission = false;
      if (courseIDs == null) // system-wide info
      {
        if (p.isCMSAdmin()) {
          List table = (List) session.getAttribute(A_PARSEDCSVINFO);
          TransactionResult result =
              transactions.commitStudentInfo(p, table, null, false);
          xml = XMLBuilder.buildCMSAdminPage(p);
          xml = XMLBuilder.addStatus(xml, result);
          buildURL = CMSADMIN_URL;
        } else buildURL = FORBIDDEN_URL;
      } else // course-specific info
      {
        long courseID = Util.parseLong(courseIDs);
        if (p.isAdminPrivByCourseID(courseID)) {
          List table = (List) session.getAttribute(A_PARSEDCSVINFO);
          TransactionResult result =
              transactions.commitStudentInfo(p, table, new Long(courseID),
                  ((Boolean) session.getAttribute(A_ISCLASSLIST))
                      .booleanValue());
          xml = XMLBuilder.buildFinalGradesPage(p, courseID);
          xml = XMLBuilder.addStatus(xml, result);
          buildURL = FINALGRADES_URL;
        } else buildURL = FORBIDDEN_URL;
        session.removeAttribute(A_ISCLASSLIST); // we don't want to
        // apply this to the
        // next uploaded CSV
      }
    }
    // Confirm the parsing of an uploaded grades table and commit the grades
    else if (action.equals(ACT_CONFIRMTABLE)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));

      if (p.isGradesPrivByAssignmentID(assignID)) {
        List table = (List) session.getAttribute(A_GRADESTABLE);
        if (table != null) {
          TransactionResult result2 = null;
          if (request.getParameter(P_NEWNETIDS) != null) {
            CourseLocal course =
                XMLBuilder.getDatabase().courseHome().findByAssignmentID(
                    assignID);
            result2 =
                transactions.addStudentsToCourse(p, course.getCourseID(),
                    request);
          }

          TransactionResult result =
              transactions.commitGradesFile(p, assignID, table);

          buildURL = GRADEASSIGN_URL;
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
          xml = XMLBuilder.addStatus(xml, result);

          if (result2 != null) {
            if (result2.hasErrors()) {
              xml = XMLBuilder.addStatus(xml, result2);
            } else {
              xml =
                  XMLBuilder.addStatus(xml, "All students added successfully",
                      XMLBuilder.MSG_NORMAL);
            }
          }
        } else {
          buildURL = GRADEASSIGN_URL;
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // View student-side course page
    else if (action.equals(ACT_COURSE)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (request.getParameter(P_RESET) != null && p.isInStaffAsBlankMode()) {
        p.resetToStaffMode();
      }
      if (p.hasCourseAccess(courseID)) {
        buildURL = COURSE_URL;
        xml = XMLBuilder.buildCoursePage(p, courseID);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the staff member logsearch page
    else if (action.equals(ACT_COURSE_LOGSEARCH)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAdminPrivByCourseID(courseID)) {
        buildURL = COURSELOG_URL;
        xml = XMLBuilder.buildStaffLogSearchPage(p, null, courseID);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side course page
    else if (action.equals(ACT_COURSEADMIN)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      // Reset principal to default
      if (p.isInStaffAsBlankMode()) {
        p.resetToStaffMode();
      }
      if (p.isStaffInCourseByCourseID(courseID)) {
        buildURL = COURSEADMIN_URL;
        xml = XMLBuilder.buildCoursePage(p, courseID);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side course properties page
    else if (action.equals(ACT_COURSEPROPS)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAdminPrivByCourseID(courseID)) {
        buildURL = COURSEPROPS_URL;
        xml = XMLBuilder.buildCoursePropertiesPage(p, courseID);
      } else buildURL = FORBIDDEN_URL;
    }
    // Add a new semester
    else if (action.equals(ACT_CREATESEMESTER)) {
      if (p.isCMSAdmin()) {
        buildURL = CMSADMIN_URL;
        TransactionResult result =
            transactions.createSemester(p, request.getParameter(P_NAME));
        xml = XMLBuilder.buildCMSAdminPage(p);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Decline a group invitation
    else if (action.equals(ACT_DECLINE)) {
      long groupID = Util.parseLong(request.getParameter(P_GROUPID));
      if (p.isStudentInCourseByGroupID(groupID)) {
        buildURL = ASSIGNMENT_URL;
        TransactionResult result = transactions.declineInvitation(p, groupID);
        xml = XMLBuilder.refreshStudentAssignmentPage(p, groupID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Download a file
    else if (action.equals(ACT_DOWNLOAD)) {
      buildURL = FORBIDDEN_URL; // default unless valid & authorized
      try {
        int type = Integer.parseInt(request.getParameter(P_DOWNLOADTYPE));
        long id = Util.parseLong(request.getParameter(P_ID));
        if (transactions.authorizeDownload(p, id, type)) {
          sendFile(id, type, response);
          buildURL = null;
          xml = null;
        }
      } catch (IOException ex) {
        System.out.println(ex.getMessage());
      } catch (NumberFormatException e) {
      }
    } // Drop a student from a course
    else if (action.equals(ACT_DROP)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      String netID = request.getParameter(P_NETID);
      if (p.isAdminPrivByCourseID(courseID)) {
        Collection n = new ArrayList();
        n.add(netID);
        TransactionResult result = transactions.dropStudent(p, courseID, n);
        buildURL = STUDENTS_URL;
        xml = XMLBuilder.buildStudentsPage(p, courseID, false);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } // Drop multiple students from a course
    else if (action.equals(ACT_DROPSTUDENTS)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      String netIDs = request.getParameter(P_STUDENTSLIST);
      // FIXME make work for multiple students
      if (p.isAdminPrivByCourseID(courseID)) {
        List netids = StringUtil.parseNetIDList(netIDs);
        TransactionResult result =
            transactions.dropStudent(p, courseID, netids);
        buildURL = STUDENTS_URL;
        xml = XMLBuilder.buildStudentsPage(p, courseID, false);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Edit a course's textual description
    else if (action.equals(ACT_EDITCOURSEDESCRIPTION)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAdminPrivByCourseID(courseID)) {
        String newDescription = request.getParameter(P_DESCRIPTION);
        TransactionResult result =
            transactions.editCourseDescription(p, courseID, newDescription);
        buildURL = COURSEADMIN_URL;
        xml = XMLBuilder.buildCoursePage(p, courseID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the general category properties page for a category
    else if (action.equals(ACT_EDITCTG)) {
      long categoryID = Util.parseLong(request.getParameter(P_CATID));
      if (p.isCategoryPrivByCategoryID(categoryID)) {
        buildURL = CATEGORYADMIN_URL;
        xml = XMLBuilder.buildCtgContentPage(p, categoryID);
      } else buildURL = FORBIDDEN_URL;
    }
    // Change an existing sitewide notice
    else if (action.equals(ACT_EDITNOTICE)) {
      if (p.isCMSAdmin()) {
        long noticeID = Util.parseLong(request.getParameter(P_ID));
        boolean deleted = request.getParameter(P_DELNOTICE) != null;
        boolean hidden = request.getParameter(P_HIDDEN) != null;
        String text = request.getParameter(P_NOTICETEXT);

        String date = request.getParameter(P_NOTICEEXPDATE);
        String time = request.getParameter(P_NOTICEEXPTIME);
        String ampm = request.getParameter(P_NOTICEEXPAMPM);
        Timestamp exp;
        try {
          exp = DateTimeUtil.parseDate(date, time, ampm);
        } catch (ParseException e) {
          exp = null;
        }

        TransactionResult result =
            transactions.editNotice(p, noticeID, text, exp, hidden, deleted);

        buildURL = CMSADMIN_URL;
        xml = XMLBuilder.buildCMSAdminPage(p);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Edit the properties of a semester
    else if (action.equals(ACT_EDITSEMESTER)) {
      if (p.isCMSAdmin()) {
        long semID = Util.parseLong(request.getParameter(P_ID));
        boolean hidden =
            request.getParameter(P_HIDDEN).equalsIgnoreCase("true");
        buildURL = CMSADMIN_URL;
        TransactionResult result = transactions.editSemester(p, semID, hidden);
        xml = XMLBuilder.buildCMSAdminPage(p);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the course email page
    else if (action.equals(ACT_EMAIL)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAdminPrivByCourseID(courseID)) {
        buildURL = EMAIL_URL;
        xml = XMLBuilder.buildEmailPage(p, courseID);
      } else buildURL = FORBIDDEN_URL;
    }
    // Export the full final grades table: netid, name, lecture, section,
    // grade option...
    else if (action.equals(ACT_EXPORTFINALGRADES)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.hasStudentsPageAccess(courseID)) {
        buildURL = null;
        xml = null;
        CourseLocal course =
            XMLBuilder.getDatabase().courseHome().findByPrimaryKey(
                new CoursePK(courseID));
        SemesterLocal sem =
            XMLBuilder.getDatabase().semesterHome().findByPrimaryKey(
                new SemesterPK(course.getSemesterID()));
        String filename =
            course.getCode() + "_" + sem.getSemesterName()
                + "_final_grades.csv";
        filename = filename.replace(' ', '_').toLowerCase();
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=\""
            + filename + "\"");
        transactions.exportStudentInfoFinalGrades(courseID, response
            .getOutputStream());
      } else buildURL = FORBIDDEN_URL;
    }
    // Export grades table csv file for a single assignment
    else if (action.equals(ACT_EXPORTGRADESTABLE)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isGradesPrivByAssignmentID(assignID)) {
        CourseLocal course =
            XMLBuilder.getDatabase().courseHome().findByAssignmentID(assignID);
        AssignmentLocal asgn =
            XMLBuilder.getDatabase().assignmentHome().findByPrimaryKey(
                new AssignmentPK(assignID));
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename =\""
            + course.getCode().replace(' ', '_') + "_"
            + asgn.getNameShort().replace(' ', '_') + "_"
            + GRADES_TABLE_FILENAME_EXTENSION + "\"");
        transactions.exportSingleAssignmentGradesTable(p, assignID, response
            .getOutputStream());
        buildURL = null;
        xml = null;
      } else buildURL = FORBIDDEN_URL;
    }
    // Export csv-formatted table of assignment max scores and weights
    else if (action.equals(ACT_EXPORTRUBRIC)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAdminPrivByCourseID(courseID)) {
        CourseLocal course =
            XMLBuilder.getDatabase().courseHome().findByPrimaryKey(
                new CoursePK(courseID));
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=\""
            + course.getCode().replace(' ', '_') + "_"
            + RUBRIC_FILENAME_EXTENSION + "\"");
        transactions.exportGradingRubric(p, courseID, response
            .getOutputStream());
        buildURL = null;
        xml = null;
      } else buildURL = FORBIDDEN_URL;
    }
    // Export full template for any student-info upload
    else if (action.equals(ACT_EXPORTSTUDENTINFOTEMPLATE)) {
      String courseIDs = request.getParameter(P_COURSEID);
      if (courseIDs == null) // cmsadmin download
      {
        if (p.isCMSAdmin()) {
          response.setContentType("text/csv");
          response.setHeader("Content-disposition", "attachment; filename=\""
              + STUDENT_INFO_TEMPLATE_FILENAME + "\"");
          TransactionResult result =
              transactions.exportStudentInfoTemplate(null, response
                  .getOutputStream());
          if (!result.getSuccess()) {
            xml = XMLBuilder.buildCMSAdminPage(p);
            xml = XMLBuilder.addStatus(xml, result);
            buildURL = UPLOAD_URL;
          } else {
            buildURL = null;
            xml = null;
          }
        } else buildURL = FORBIDDEN_URL;
      } else // course staff download
      {
        long courseID = Util.parseLong(courseIDs);
        if (p.isStaffInCourseByCourseID(courseID)) {
          CourseLocal course =
              XMLBuilder.getDatabase().courseHome().findByPrimaryKey(
                  new CoursePK(courseID));
          response.setContentType("text/csv");
          response.setHeader("Content-disposition", "attachment; filename=\""
              + course.getCode().replace(' ', '_') + "_"
              + STUDENT_INFO_TEMPLATE_FILENAME + "\"");
          TransactionResult result =
              transactions.exportStudentInfoTemplate(new Long(courseID),
                  response.getOutputStream());
          if (!result.getSuccess()) {
            xml = XMLBuilder.buildCSVUploadPage(p, courseID);
            xml = XMLBuilder.addStatus(xml, result);
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
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isGradesPrivByCourseID(courseID)) {
        CourseLocal course =
            XMLBuilder.getDatabase().courseHome().findByPrimaryKey(
                new CoursePK(courseID));
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=\""
            + course.getCode().replace(' ', '_') + "_"
            + STUDENT_TABLE_FILENAME_EXTENSION + "\"");
        transactions.exportGradesTable(p, courseID, response.getOutputStream());
        buildURL = null;
        xml = null;
      } else buildURL = FORBIDDEN_URL;
    }
    // Submit a file for an assignment
    else if (action.equals(ACT_FILESUBMIT)) {
      long assignmentid = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isStudentInCourseByAssignmentID(assignmentid)) {
        TransactionResult result = transactions.submitFiles(p, request);
        buildURL = ASSIGNMENT_URL;
        xml = XMLBuilder.buildStudentAssignmentPage(p, assignmentid);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Submit a survey
    else if (action.equals(ACT_SURVEYSUBMIT)) {
      long assignmentid = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isStudentInCourseByAssignmentID(assignmentid)) {
        TransactionResult result = transactions.submitSurvey(p, request);

        if (result.getSuccess())
          result.setValue("Answers submitted successfully");

        AssignmentLocal assign =
            XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                assignmentid);
        buildURL = COURSE_URL;
        xml = XMLBuilder.buildCoursePage(p, assign.getCourseID());
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View list of administrative data and final grades for all students in
    // a course
    else if (action.equals(ACT_FINALGRADES)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isCMSAdmin() || p.isAdminPrivByCourseID(courseID)) {
        buildURL = FINALGRADES_URL;
        xml = XMLBuilder.buildFinalGradesPage(p, courseID);
      } else buildURL = FORBIDDEN_URL;
    }
    // Parse and error-check a Final Grades CSV File
    else if (action.equals(ACT_FINALGRADESFILE)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isGradesPrivByCourseID(courseID)) {
        TransactionResult result =
            transactions.parseFinalGradesFile(courseID, request);
        if (!result.getSuccess() && result.getValue() == null) {
          xml = XMLBuilder.buildStudentsPage(p, courseID, false);
          xml =
              XMLBuilder.addStatus(xml,
                  "Could not parse grades file; format was unrecognized",
                  XMLBuilder.MSG_ERROR);
          buildURL = STUDENTS_URL;
        } else {
          xml =
              XMLBuilder.buildConfirmPage(p, XMLBuilder.CONFIRM_FINALGRADES,
                  courseID, result);
          buildURL = CONFIRMTABLE_URL;
          if (result.getSuccess()) {
            session.setAttribute(A_GRADESTABLE, result.getValue());
          }
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // Download mini-template just for filling in final grades for a class
    else if (action.equals(ACT_FINALGRADESTEMPLATE)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAdminPrivByCourseID(courseID)) {
        CourseLocal course =
            XMLBuilder.getDatabase().courseHome().findByPrimaryKey(
                new CoursePK(courseID));
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=\""
            + course.getCode().replace(' ', '_') + "_"
            + FINALGRADES_TEMPLATE_FILENAME + "\"");
        transactions.exportFinalGradesTemplate(courseID, response
            .getOutputStream());
        buildURL = null;
        xml = null;
      } else buildURL = FORBIDDEN_URL;
    }
    // View the individual-student-grading page for a single assignment
    else if (action.equals(ACT_GRADESTUDENTS)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      AssignmentLocal assign =
          XMLBuilder.getDatabase().assignmentHome()
              .findByAssignmentID(assignID);
      if (p.isGradesPrivByAssignmentID(assignID)
          && assign.getType() != AssignmentBean.SURVEY) {
        Collection groupIDs = null;
        if (!p.isAdminPrivByCourseID(assign.getCourseID())
            && assign.getAssignedGraders()) {
          groupIDs =
              XMLBuilder.getDatabase().assignedToGroups(assignID,
                  p.getNetID(),
                  Util.extractGroupIDsFromMainGradingPageRequest(request));
        } else {
          groupIDs = Util.extractGroupIDsFromMainGradingPageRequest(request);
        }
        if (groupIDs.size() > 0) {
          xml = XMLBuilder.buildGradePage(p, assignID, groupIDs);
          buildURL = GRADESTUDENTS_URL;
        } else {
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
          // xml = XMLBuilder.addStatus(xml, "No groups selected",
          // XMLBuilder.MSG_NORMAL);
          buildURL = GRADEASSIGN_URL;
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // View assignment-grading main page
    else if (action.equals(ACT_GRADEASSIGN)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      boolean showGradeMsg = (request.getParameter(P_GRADEMESSAGE) != null);
      if (p.isGradesPrivByAssignmentID(assignID)
          || p.isGroupsPrivByAssignmentID(assignID)) {
        AssignmentLocal a =
            XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                assignID);

        // can't display a grades page for a survey
        if (a.getType() == AssignmentBean.SURVEY) {
          buildURL = FORBIDDEN_URL;
        } else {
          buildURL = GRADEASSIGN_URL;
          xml = XMLBuilder.buildGradeAssignPage(p, assignID, showGradeMsg);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // Upload group submission files
    else if (action.equals(ACT_GROUPFILES)) {
      long groupID = Util.parseLong(request.getParameter(P_GROUPID));
      GroupLocal group = null;
      try {
        group = XMLBuilder.getDatabase().groupHome().findByGroupID(groupID);
      } catch (Exception e) {
      }
      if (group != null) {
        ArrayList groupIDs = new ArrayList();
        groupIDs.add(new Long(groupID));
        if (transactions.authorizeGroupFiles(p, groupIDs)) {
          response.setContentType("application/zip");
          response.setHeader("Content-disposition", "attachment; filename=\""
              + SUBMISSIONS_ZIP_FILENAME + "\"");
          transactions.uploadGroupSubmissions(groupIDs, response
              .getOutputStream());
          buildURL = null;
          xml = null;
        } else buildURL = FORBIDDEN_URL;
      } else {
        buildURL = FORBIDDEN_URL;
      }
    }
    // Invite someone to join a group
    else if (action.equals(ACT_INVITE)) {
      String invite = request.getParameter(P_INVITE);
      long groupID = Util.parseLong(request.getParameter(P_GROUPID));
      if (p.isStudentInCourseByGroupID(groupID)) {
        // TODO Later add multi-invites; now one at a time
        buildURL = ASSIGNMENT_URL;
        TransactionResult result = transactions.inviteUser(p, invite, groupID);
        xml = XMLBuilder.refreshStudentAssignmentPage(p, groupID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Leave a group
    else if (action.equals(ACT_LEAVE)) {
      long groupID = Util.parseLong(request.getParameter(P_GROUPID));
      if (p.isStudentInCourseByGroupID(groupID)) {
        buildURL = ASSIGNMENT_URL;
        TransactionResult result = transactions.leaveGroup(p, groupID);
        xml = XMLBuilder.refreshStudentAssignmentPage(p, groupID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Post a new announcement
    else if (action.equals(ACT_NEWANNOUNCE)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isCategoryPrivByCourseID(courseID)) {
        String announce = request.getParameter(P_ANNOUNCE);
        TransactionResult result =
            transactions.postAnnouncement(p, courseID, announce);
        buildURL = COURSEADMIN_URL;
        xml = XMLBuilder.buildCoursePage(p, courseID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the assignment creation page
    else if (action.equals(ACT_NEWASSIGN)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAssignPrivByCourseID(courseID)) {
        buildURL = ASSIGNADMIN_URL;
        xml =
            XMLBuilder.buildAssignmentCreationPage(p, courseID,
                AssignmentBean.ASSIGNMENT);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the survey creation page
    else if (action.equals(ACT_NEWSURVEY)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAssignPrivByCourseID(courseID)) {
        buildURL = ASSIGNADMIN_URL;
        xml =
            XMLBuilder.buildAssignmentCreationPage(p, courseID,
                AssignmentBean.SURVEY);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the quiz creation page
    else if (action.equals(ACT_NEWQUIZ)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAssignPrivByCourseID(courseID)) {
        buildURL = ASSIGNADMIN_URL;
        xml =
            XMLBuilder.buildAssignmentCreationPage(p, courseID,
                AssignmentBean.QUIZ);
      } else buildURL = FORBIDDEN_URL;
    }
    // View the category creation page
    else if (action.equals(ACT_NEWCATEGORY)) {
      // check the privilege
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isCategoryPrivByCourseID(courseID)) {
        buildURL = CATEGORYADMIN_URL;
        xml = XMLBuilder.buildNewCategoryPage(p, courseID);
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
      long assignID = 0;
      try {
        assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      } catch (Exception e) {
      }
      CourseLocal course =
          XMLBuilder.getDatabase().courseHome().findByAssignmentID(assignID);
      if (p.isStaffInCourseByCourseID(course.getCourseID())) {
        buildURL = PRINTSCHED_URL;
        xml = XMLBuilder.buildBasicSchedulePage(p, assignID);
      } else buildURL = FORBIDDEN_URL;
    } // Call up profiler page (only in debug mode)
    else if (action.equals(ACT_PROFILER)) {
      if (debug) {
        buildURL = PROFILER_URL;
        xml = Profiler.output(100);
      } else buildURL = FORBIDDEN_URL;
    } // Reenroll a student in the course
    else if (action.equals(ACT_REENROLL)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      String netID = request.getParameter(P_NETID);
      if (p.isAdminPrivByCourseID(courseID)) {
        TransactionResult result =
            transactions.reenrollStudent(p, courseID, netID, request
                .getParameter(P_EMAILADDED) != null);
        buildURL = STUDENTS_URL;
        xml = XMLBuilder.buildStudentsPage(p, courseID, false);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } // Removes an assignment from the system
    else if (action.equals(ACT_REMOVEASSIGN)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isAssignPrivByAssignmentID(assignID)) {
        AssignmentLocal assign =
            XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                assignID);
        TransactionResult result = transactions.removeAssignment(p, assignID);
        buildURL = COURSEADMIN_URL;
        xml = XMLBuilder.buildCoursePage(p, assign.getCourseID());
        if (result.getSuccess())
          result.setValue("Assignment removed successfully");
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Remove a CMS administrator
    else if (action.equals(ACT_REMOVECMSADMIN)) {
      if (p.isCMSAdmin()) {
        buildURL = CMSADMIN_URL;
        TransactionResult result = new TransactionResult();
        if (p.getNetID().equals(request.getParameter(P_NETID)))
          result.addError("Can't remove current user");
        else result =
            transactions.removeCMSAdmin(p, request.getParameter(P_NETID));
        xml = XMLBuilder.buildCMSAdminPage(p);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Remove a group's extension
    else if (action.equals(ACT_REMOVEEXTENSION)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      long groupID = Util.parseLong(request.getParameter(P_GROUPID));
      if (p.isGradesPrivByCourseID(courseID)) {
        TransactionResult result =
            transactions.removeExtension(p, courseID, groupID);
        long assignID = -1;
        try {
          assignID =
              XMLBuilder.getDatabase().groupHome().findByGroupID(groupID)
                  .getAssignmentID();
        } catch (Exception e) {
        }
        if (assignID > 0) {
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
          buildURL = GRADEASSIGN_URL;
        } else {
          xml = XMLBuilder.buildCoursePage(p, courseID);
          buildURL = COURSEADMIN_URL;
        }
        if (result.getSuccess())
          result.setValue("Successfully removed extension");
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Remove a row of content from a category (can be done from the main
    // course page, admin side)
    else if (action.equals(ACT_REMOVEROW)) {
      long rowID = Util.parseLong(request.getParameter(P_ID));
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isCategoryPrivByCourseID(courseID)) {
        TransactionResult result = transactions.removeCtgRow(p, rowID);
        buildURL = COURSEADMIN_URL;
        xml = XMLBuilder.buildCoursePage(p, courseID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } else if (action.equals(ACT_REQUESTREGRADE)) {
      long groupID = Util.parseLong(request.getParameter(P_GROUPID));
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isStudentInCourseByAssignmentID(assignID)) {
        TransactionResult result =
            transactions.addRegradeRequest(p, groupID, request);
        buildURL = ASSIGNMENT_URL;
        xml = XMLBuilder.buildStudentAssignmentPage(p, assignID);
        if (result.getSuccess())
          result.setValue("Successfully added regrade request");
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } // Restores a previously removed announcement
    else if (action.equals(ACT_RESTOREANNOUNCE)) {
      long announceID = Util.parseLong(request.getParameter(P_ID));
      AnnouncementLocal announce =
          XMLBuilder.getDatabase().announcementHome().findByPrimaryKey(
              new AnnouncementPK(announceID));
      if (p.isCategoryPrivByCourseID(announce.getCourseID())) {
        TransactionResult result =
            transactions.restoreAnnouncement(p, announceID);
        buildURL = COURSEADMIN_URL;
        xml = XMLBuilder.buildCoursePage(p, announce.getCourseID());
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } // Restores a previously removed assignment
    else if (action.equals(ACT_RESTOREASSIGN)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isAssignPrivByAssignmentID(assignID)) {
        AssignmentLocal assign =
            XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                assignID);
        TransactionResult result = transactions.restoreAssignment(p, assignID);
        buildURL = COURSEADMIN_URL;
        xml = XMLBuilder.buildCoursePage(p, assign.getCourseID());
        if (result.getSuccess())
          result.setValue("Assignment restored successfully");
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // View staff-side schedule page
    else if (action.equals(ACT_SCHEDULE)) {
      long assignID = 0;
      try {
        assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      } catch (Exception e) {
      }
      CourseLocal course =
          XMLBuilder.getDatabase().courseHome().findByAssignmentID(assignID);
      /*
       * Since it's possible to assign any staff member to a timeslot, all staff
       * members should be able to see the schedule in case they've been
       * assigned to any slots. Previously only assignment-privileged staff
       * could view the schedule. - Evan, 5 / 25 / 06
       */
      if (p.isStaffInCourseByCourseID(course.getCourseID())) {
        buildURL = ASSIGNSCHED_URL;
        xml = XMLBuilder.buildBasicSchedulePage(p, assignID);
      } else buildURL = FORBIDDEN_URL;
    }
    // Send an email to the students and/or staff of a course
    else if (action.equals(ACT_SENDEMAIL)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAdminPrivByCourseID(courseID)) {
        TransactionResult result = transactions.sendEmail(p, courseID, request);
        xml = XMLBuilder.buildEmailPage(p, courseID);
        xml = XMLBuilder.addStatus(xml, result);
        buildURL = EMAIL_URL;
      } else buildURL = FORBIDDEN_URL;
      // Search logs as a cmsadmin
    } else if (action.equals(ACT_SEARCHLOGS_CMSADMIN)) {
      if (p.isCMSAdmin()) {
        buildURL = CMSADMIN_LOGRESULTS_URL;
        xml = XMLBuilder.buildLogSearchPage(p, request);
        XMLBuilder.appendCMSAdminLogInfo(p, xml);
      } else buildURL = FORBIDDEN_URL;
    }
    // Search logs as a course admin
    else if (action.equals(ACT_SEARCHLOGS_COURSE)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAdminPrivByCourseID(courseID)) {
        buildURL = COURSELOG_URL;
        xml = XMLBuilder.buildLogSearchPage(p, request, new Long(courseID));
        xml = XMLBuilder.buildStaffLogSearchPage(p, xml, courseID);
      } else buildURL = FORBIDDEN_URL;
    }
    // Add and/or edit content to/in a category
    else if (action.equals(ACT_SETADDNEDITCONTENTS)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      long catID = Util.parseLong(request.getParameter(P_CATID));
      if (p.isCategoryPrivByCategoryID(catID)) {
        TransactionResult result =
            transactions.addNEditCtgContents(p, catID, request);
        if (result.getSuccess()) {
          buildURL = COURSEADMIN_URL;
          xml = XMLBuilder.buildCoursePage(p, courseID);
        } else {
          buildURL = CTGCONTENTSADMIN_URL;
          xml = XMLBuilder.buildCtgContentPage(p, catID);
          xml = XMLBuilder.addStatus(xml, result);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // Edit an announcement
    else if (action.equals(ACT_SETANNOUNCE)) {
      long announceID = Util.parseLong(request.getParameter(P_ID));
      long courseID =
          XMLBuilder.getDatabase().announcementHome().findByPrimaryKey(
              new AnnouncementPK(announceID)).getCourseID();
      if (p.isCategoryPrivByCourseID(courseID)) {
        String announce = request.getParameter(P_ANNOUNCE);
        String poster = p.getNetID();
        boolean remove = request.getParameter(P_REMOVEANNOUNCE) != null;
        TransactionResult result =
            transactions.editAnnouncement(p, announceID, announce, remove);
        buildURL = COURSEADMIN_URL;
        xml = XMLBuilder.refreshCoursePage(p, announceID);
        if (result.getSuccess())
          result.setValue("Announcement edited successfully");
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Set assignment properties/make new assignment
    else if (action.equals(ACT_SETASSIGN)) {
      long assignID =
          Util.parseLong((request.getParameter(P_ASSIGNID) != null) ? request
              .getParameter(P_ASSIGNID) : "0");
      long courseID = 0;
      if (assignID != 0) { // existing assignment
        AssignmentLocal assign = null;
        try {
          assign =
              XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                  assignID);
        } catch (Exception e) {
        }
        if (assign != null) {
          courseID = assign.getCourseID();
        }
      } else { // new assignment
        courseID = Util.parseLong(request.getParameter(P_COURSEID));
      }
      if (courseID != 0 && p.isAssignPrivByCourseID(courseID)) {
        TransactionResult result =
            transactions.setAssignmentProps(p, courseID, assignID, request);
        if (result.getSuccess()) {
          xml = XMLBuilder.buildCoursePage(p, courseID);
          result.setValue("Assignment properties successfully set.");
          buildURL = COURSEADMIN_URL;
        } else {
          // xml = XMLBuilder.buildErrorAssignmentPage(p,
          // (Collection) result.getValue(), courseID, assignID);

          /*
           * if (assignID != 0) { xml = XMLBuilder.buildBasicAssignmentPage(p,
           * assignID); } else { xml = XMLBuilder.buildAssignmentCreationPage(p,
           * courseID); }
           */
          if (assignID == 0) {
            buildURL = ERROR_URL;
            xml =
                XMLBuilder.buildErrorAssignmentPage(p, (Collection) result
                    .getValue(), courseID, assignID);
          } else {
            buildURL = ASSIGNADMIN_URL;
            xml = XMLBuilder.buildBasicAssignmentPage(p, assignID);
          }
        }
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Set general properties for a category or create a new category for
    // the given course
    else if (action.equals(ACT_SETCATEGORY)) {
      long catID =
          Util.parseLong((request.getParameter(P_CATID) != null) ? request
              .getParameter(P_CATID) : "0");
      long courseID = 0;
      if (catID != 0) {
        CategoryLocal cat = null;
        try {
          cat =
              XMLBuilder.getDatabase().categoryHome().findByPrimaryKey(
                  new CategoryPK(catID));
        } catch (Exception e) {
        }
        if (cat != null) // the category ID is valid
        {
          courseID = cat.getCourseID();
        }
      } else {
        courseID = Util.parseLong(request.getParameter(P_COURSEID));
      }
      if (courseID != 0 && p.isCategoryPrivByCourseID(courseID)) {
        TransactionResult result =
            transactions.createNEditCategory(p, catID, courseID, request);
        if (result.getSuccess()) {
          buildURL = COURSEADMIN_URL;
          xml = XMLBuilder.buildCoursePage(p, courseID);
        } else {
          buildURL = CATEGORYADMIN_URL;
          if (catID == 0)
            xml = XMLBuilder.buildNewCategoryPage(p, courseID);
          else xml = XMLBuilder.buildCategoryPage(p, catID);
          xml = XMLBuilder.addStatus(xml, result);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // Set course properties
    else if (action.equals(ACT_SETCOURSEPROPS)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isAdminPrivByCourseID(courseID)) {
        TransactionResult result =
            transactions.setCourseProps(p, courseID, request.getParameterMap());
        if (p.isAdminPrivByCourseID(courseID) || !result.getSuccess()) {
          buildURL = COURSEPROPS_URL;
          xml = XMLBuilder.buildCoursePropertiesPage(p, courseID);
          if (result.getSuccess())
            result.setValue("Course properties successfully set");
          xml = XMLBuilder.addStatus(xml, result);
        } else // User may have revoked their own Admin privilege
        {
          buildURL = COURSEADMIN_URL;
          xml = XMLBuilder.buildCoursePage(p, courseID);
          if (result.getSuccess())
            result.setValue("Course properties successfully set");
          xml = XMLBuilder.addStatus(xml, result);
        }
      } else if (p.isCMSAdmin()) {
        TransactionResult result =
            transactions.setCourseProps(p, courseID, request.getParameterMap());
        buildURL = CMSADMINCOURSEPROPS_URL;
        xml = XMLBuilder.buildCMSAdminCoursePropsPage(p, courseID);
        if (result.getSuccess())
          result.setValue("Course properties successfully set");
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Set the current semester
    else if (action.equals(ACT_SETCURSEMESTER)) {
      if (p.isCMSAdmin()) {
        buildURL = CMSADMIN_URL;
        TransactionResult result =
            transactions.setCurrentSemester(p, Util.parseLong(request
                .getParameter(P_ID)));
        xml = XMLBuilder.buildCMSAdminPage(p);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Set final grades
    else if (action.equals(ACT_SETFINALGRADES)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isGradesPrivByCourseID(courseID)) {
        TransactionResult result =
            transactions.setFinalGrades(p, courseID, request);
        xml = XMLBuilder.buildStudentsPage(p, courseID, false);
        xml = XMLBuilder.addStatus(xml, result);
        buildURL = STUDENTS_URL;
      } else buildURL = FORBIDDEN_URL;
    } // Set staff member course preferences
    else if (action.equals(ACT_SETSTAFFPREFS)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isStaffInCourseByCourseID(courseID)) {
        TransactionResult result =
            transactions.setStaffPrefs(p, courseID, request);
        xml = XMLBuilder.buildStaffPrefsPage(p, courseID);
        xml = XMLBuilder.addStatus(xml, result);
        buildURL = STAFFPREFS_URL;
      } else buildURL = FORBIDDEN_URL;
    } // Set student grades from the view of all assignments for a single
    // student
    else if (action.equals(ACT_SETSTUDENTALLGRADES)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      String netID = request.getParameter(P_NETID);
      if (p.isAdminPrivByCourseID(courseID)) {
        TransactionResult result =
            transactions.addGradesComments(p, false, courseID, request);
        xml = XMLBuilder.buildGradeStudentPage(p, courseID, netID);
        buildURL = GRADEALLASSIGNS_URL;
        xml = XMLBuilder.addStatus(xml, result);
      }
    } // Set student grades
    else if (action.equals(ACT_SETSTUDENTGRADES)) {
      String s = request.getParameter(P_ASSIGNID);
      long assignID = Util.parseLong(s);
      if (p.isGradesPrivByAssignmentID(assignID)) {
        AssignmentLocal assign =
            XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                assignID);
        TransactionResult result =
            transactions.addGradesComments(p, true, assignID, request);
        List groupids = null;
        if (result.getSuccess()) {
          Object[] val = (Object[]) result.getValue();
          groupids = val != null ? (List) val[1] : null; // a List of
          // GroupIDs
          // (Longs)
          result.setValue(val[0]);
        }
        xml =
            XMLBuilder.buildGradeAssignPage(p, assignID,
                (groupids != null && groupids.size() > 0) ? (Long) groupids
                    .get(0) : null);
        buildURL = GRADEASSIGN_URL;
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    } // Set student course preferences
    else if (action.equals(ACT_SETSTUDENTPREFS)) {
      String scope = request.getParameter(P_APPLYSCOPE);
      if (scope == null) {
        // invalid data posted!
        String courseParam = request.getParameter(P_COURSEID);
        if (courseParam != null && !courseParam.equals("")) {
          long courseID = Util.parseLong(courseParam);
          if (p.isStudentInCourseByCourseID(courseID)) {
            TransactionResult result = new TransactionResult();
            result
                .addError("No scope (which courses to apply settings to?) was received from browser.");
            buildURL = STUDENTPREFS_URL;
            xml = XMLBuilder.buildStudentPrefsPage(p, courseID);
            xml = XMLBuilder.addStatus(xml, result);
          } else buildURL = FORBIDDEN_URL;
        } else buildURL = FORBIDDEN_URL;
      } else if (scope.equals(P_APPLYTHIS)) {
        long courseID = Util.parseLong(request.getParameter(P_COURSEID));
        if (p.isStudentInCourseByCourseID(courseID)) {
          TransactionResult result =
              transactions.setStudentPrefs(p, courseID, request);
          buildURL = STUDENTPREFS_URL;
          xml = XMLBuilder.buildStudentPrefsPage(p, courseID);
          xml = XMLBuilder.addStatus(xml, result);
        } else buildURL = FORBIDDEN_URL;
      } else if (scope.equals(P_APPLYALL)) {
        TransactionResult result = transactions.setAllStudentPrefs(p, request);

        String courseParam = request.getParameter(P_COURSEID);
        if (courseParam != null && !courseParam.equals("")) {
          // performed from a course's notification page
          buildURL = STUDENTPREFS_URL;
          xml =
              XMLBuilder.buildStudentPrefsPage(p, Util.parseLong(courseParam));
          xml = XMLBuilder.addStatus(xml, result);
        } else {
          // performed from main notification page
          buildURL = FORBIDDEN_URL; // TODO
          // xml = XMLBuilder.buildStudentPrefsPage(p, courseID);
          // xml = XMLBuilder.addStatus(xml, result);
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
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isStaffInCourseByCourseID(courseID)) {
        xml = XMLBuilder.buildStaffPrefsPage(p, courseID);
        buildURL = STAFFPREFS_URL;
      } else buildURL = FORBIDDEN_URL;
    } // View all assignment grades for a given student
    else if (action.equals(ACT_STUDENTALLGRADES)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      String netID = request.getParameter(P_NETID);
      if (p.isAdminPrivByCourseID(courseID)) {
        Collection assignments =
            XMLBuilder.getDatabase().assignmentHome().findByCourseID(courseID);
        if (assignments.size() > 0) {
          xml = XMLBuilder.buildGradeStudentPage(p, courseID, netID);
          buildURL = GRADEALLASSIGNS_URL;
        } else {
          xml = XMLBuilder.buildStudentsPage(p, courseID, false);
          xml =
              XMLBuilder.addStatus(xml, "No assignments to list",
                  XMLBuilder.MSG_WARNING);
          buildURL = STUDENTS_URL;
        }
      } else buildURL = FORBIDDEN_URL;
    } // View the student course preferences page
    else if (action.equals(ACT_STUDENTPREFS)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isStudentInCourseByCourseID(courseID)) {
        xml = XMLBuilder.buildStudentPrefsPage(p, courseID);
        buildURL = STUDENTPREFS_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // Sign out
    else if (action.equals(ACT_SIGNOUT)) {
      buildURL = SIGNOUT_URL;
    }
    // View student listing page for a course
    else if (action.equals(ACT_STUDENTS)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      boolean showGradeMsg = (request.getParameter(P_GRADEMESSAGE) != null);
      if (p.isAdminPrivByCourseID(courseID)) {
        buildURL = STUDENTS_URL;
        xml = XMLBuilder.buildStudentsPage(p, courseID, false, showGradeMsg);
      } else buildURL = FORBIDDEN_URL;
    }
    // Download survey CVS
    else if (action.equals(ACT_SURVEYDOWNLOAD)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isAdminPrivByAssignmentID(assignID)) {
        String filename = request.getParameter(P_FILENAME);
        response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment; filename=\""
            + filename + "\"");
        Collection surveyResultData =
            XMLBuilder.generateSurveyResultCSV(assignID);
        TransactionResult result =
            transactions.exportSurveyResult(response.getOutputStream(),
                surveyResultData);
        if (!result.getSuccess()) {
          // xml = XMLBuilder.buildCMSAdminPage(p);
          // xml = XMLBuilder.addStatus(xml, result);
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
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isAdminPrivByAssignmentID(assignID)) {
        xml = XMLBuilder.buildSurveyResultPage(p, assignID);
        buildURL = SURVEYRESULT_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // Staff member manually adding a group to a timeslot
    else if (action.equals(ACT_TIMESLOTASSIGN)) {
      long groupID = 0, assignID = 0;
      try {
        groupID = Util.parseLong(request.getParameter(P_GROUPID));
      } catch (Exception e) {
      }
      try {
        assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      } catch (Exception e) {
      }
      long courseID = 0;
      try {
        AssignmentLocal assign =
            XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                assignID);
        courseID = assign.getCourseID();
      } catch (Exception e) {
      }
    }
    // Staff member updating multiple timeslots at once
    else if (action.equals(ACT_TIMESLOTSUPDATE)) {
      long assignID = 0;
      try {
        assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      } catch (Exception e) {
      }
      long courseID = 0;
      try {
        AssignmentLocal assign =
            XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                assignID);
        courseID = assign.getCourseID();
      } catch (Exception e) {
      }
      if (p.isAssignPrivByCourseID(courseID)) {
        buildURL = ASSIGNSCHED_URL;
        java.util.Enumeration groups = request.getParameterNames();
        java.util.Collection results = new java.util.Vector();
        while (groups.hasMoreElements()) {
          String groupS = (String) groups.nextElement();
          if (!(groupS.equals(P_ASSIGNID) || groupS.equals("action"))) {
            long groupID = Util.parseLong(groupS);
            long slotID = Util.parseLong(request.getParameter(groupS));
            if (slotID > -1) {
              TransactionResult result =
                  transactions.changeGroupSlotByID(p, groupID, assignID,
                      slotID, true, false);
              results.add(result);
            }
          }
        }
        java.util.Iterator resultsI = results.iterator();
        xml = XMLBuilder.buildBasicSchedulePage(p, assignID);
        while (resultsI.hasNext()) {
          TransactionResult result = (TransactionResult) resultsI.next();
          xml = XMLBuilder.addStatus(xml, result);
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // Staff member creating a block of timeslots from the schedule page
    else if (action.equals(ACT_TIMESLOTSCREATE)) {
      long assignID = 0;
      try {
        assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      } catch (Exception e) {
      }
      long courseID = 0;
      try {
        AssignmentLocal assign =
            XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                assignID);
        courseID = assign.getCourseID();
      } catch (Exception e) {
      }
      // check for privileges
      if (courseID != 0 && p.isAssignPrivByCourseID(courseID)) {
        buildURL = ASSIGNSCHED_URL;
        // perform timeslot creation
        TransactionResult result =
            transactions.createTimeSlots(p, assignID, request);
        if (result.getSuccess())
          result.setValue("Time slot creation was successful");
        // reload the schedule page with message status
        xml = XMLBuilder.buildBasicSchedulePage(p, assignID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Staff member deleting one or more checked timeslots
    else if (action.equals(ACT_TIMESLOTSDELETE)) {
      long assignID = 0;
      try {
        assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      } catch (Exception e) {
      }
      long courseID = 0;
      try {
        AssignmentLocal assign =
            XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                assignID);
        courseID = assign.getCourseID();
      } catch (Exception e) {
      }
      if (courseID != 0 && p.isAssignPrivByCourseID(courseID)) {
        buildURL = ASSIGNSCHED_URL;
        TransactionResult result =
            transactions.deleteTimeSlots(p, assignID, request);
        if (result.getSuccess())
          result.setValue("Time slot deletion was successful");
        xml = XMLBuilder.buildBasicSchedulePage(p, assignID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Student adding a timeslot from the assignment page
    else if (action.equals(ACT_TIMESLOTSELECT)) {
      long groupID = 0, assignID = 0;
      try {
        groupID = Util.parseLong(request.getParameter(P_GROUPID));
      } catch (Exception e) {
      }
      try {
        assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      } catch (Exception e) {
      }
      GroupMemberLocal gm =
          XMLBuilder.getDatabase().groupMemberHome().findByPrimaryKey(
              new GroupMemberPK(groupID, p.getNetID()));
      if (gm != null) {
        buildURL = ASSIGNMENT_URL;
        boolean error = false;
        TransactionResult result =
            transactions.changeGroupSlot(p, groupID, assignID, request, true,
                true);
        xml = XMLBuilder.refreshStudentAssignmentPage(p, groupID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Staff member manually adding a group to a timeslot
    else if (action.equals(ACT_TIMESLOTUNASSIGN)) {
      long groupID = 0, assignID = 0;
      try {
        groupID = Util.parseLong(request.getParameter(P_GROUPID));
      } catch (Exception e) {
      }
      try {
        assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      } catch (Exception e) {
      }
      long courseID = 0;
      try {
        AssignmentLocal assign =
            XMLBuilder.getDatabase().assignmentHome().findByAssignmentID(
                assignID);
        courseID = assign.getCourseID();
      } catch (Exception e) {
      }
      if (p.isAssignPrivByCourseID(courseID)) {
        buildURL = ASSIGNSCHED_URL;
        TransactionResult result =
            transactions.changeGroupSlot(p, groupID, assignID, request, false,
                false);
        xml = XMLBuilder.buildBasicSchedulePage(p, assignID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Student removing his/her group from its timeslot on the assignment
    // page
    else if (action.equals(ACT_TIMESLOTUNSELECT)) {
      long groupID = 0, assignID = 0;
      try {
        groupID = Util.parseLong(request.getParameter(P_GROUPID));
      } catch (Exception e) {
      }
      try {
        assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      } catch (Exception e) {
      }
      GroupMemberLocal gm =
          XMLBuilder.getDatabase().groupMemberHome().findByPrimaryKey(
              new GroupMemberPK(groupID, p.getNetID()));
      if (gm != null) {
        buildURL = ASSIGNMENT_URL;
        TransactionResult result =
            transactions.changeGroupSlot(p, groupID, assignID, request, false,
                false);
        xml = XMLBuilder.refreshStudentAssignmentPage(p, groupID);
        xml = XMLBuilder.addStatus(xml, result);
      } else buildURL = FORBIDDEN_URL;
    }
    // Disband a single group (to disband multiple groups, see
    // ACT_APPLYTOGROUPS)
    else if (action.equals(ACT_UNGROUP)) {
      long groupID = Util.parseLong(request.getParameter(P_GROUPID));
      GroupLocal group = null;
      try {
        group = XMLBuilder.getDatabase().groupHome().findByGroupID(groupID);
      } catch (Exception e) {
      }
      if (group != null) {
        long assignID = group.getAssignmentID();
        if (p.isGroupsPrivByAssignmentID(assignID)) {
          // disbandGroup() checks whether they've already been graded
          TransactionResult result = transactions.disbandGroup(p, groupID);
          buildURL = GRADEASSIGN_URL;
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
          XMLBuilder.addStatus(xml, result);
        } else buildURL = FORBIDDEN_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // Upload grades
    else if (action.equals(ACT_UPLOADGRADES)) {
      long assignID = Util.parseLong(request.getParameter(P_ASSIGNID));
      if (p.isGradesPrivByAssignmentID(assignID)) {
        TransactionResult table =
            transactions.parseGradesFile(assignID, request);
        if (table.getSuccess() && table.getValue() == null) {
          buildURL = GRADEASSIGN_URL;
          xml = XMLBuilder.buildGradeAssignPage(p, assignID);
        } else {
          buildURL = CONFIRMTABLE_URL;
          xml =
              XMLBuilder.buildConfirmPage(p, XMLBuilder.CONFIRM_ASSIGNINFO,
                  assignID, table);
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
      String courseID = request.getParameter(P_COURSEID);
      boolean isClasslist = (request.getParameter(P_ISCLASSLIST) != null);
      boolean hasPermission = false;
      if (courseID == null) // assume info is system-wide, submitted by a
        // cmsadmin
        hasPermission = p.isCMSAdmin();
      else
      // assume info is course-specific
      hasPermission = p.isAdminPrivByCourseID(Util.parseLong(courseID));
      if (hasPermission) {
        TransactionResult result =
            transactions.parseCSVInfo(request,
                isClasslist ? CSVFileFormatsUtil.CLASSLIST_FORMAT : null);
        if (result.getSuccess())
          session.setAttribute(A_PARSEDCSVINFO, result.getValue());
        xml =
            XMLBuilder.buildConfirmPage(p,
                (courseID == null) ? XMLBuilder.CONFIRM_GENERAL
                    : XMLBuilder.CONFIRM_COURSEINFO, (courseID == null) ? 0
                    : Util.parseLong(courseID), result);
        xml = XMLBuilder.addStatus(xml, result);
        buildURL = CONFIRMTABLE_URL;
        session.setAttribute(A_ISCLASSLIST, new Boolean(isClasslist));
      } else buildURL = FORBIDDEN_URL;
    }
    // Enter Cornell-member perspective
    else if (action.equals(ACT_VIEWCORNELLMEM)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      CourseLocal course =
          XMLBuilder.getDatabase().courseHome().findByPrimaryKey(
              new CoursePK(courseID));
      if (p.isInStaffAsBlankMode()) {
        p.resetToStaffMode();
      }
      if (p.isAdminPrivByCourseID(courseID)) {
        if (course.getCourseCCAccess()) {
          p.setStaffAsCornellMem(courseID);
          buildURL = COURSE_URL;
          xml = XMLBuilder.buildCoursePage(p, courseID);
        } else {
          buildURL = COURSEADMIN_URL;
          xml = XMLBuilder.buildCoursePage(p, courseID);
          String error =
              "Cannot view course page as Cornell Member: that authorization level has no access to the course page<br>";
          error += "Navigate to Course Options to view/edit permissions";
          xml = XMLBuilder.addStatus(xml, error, XMLBuilder.MSG_ERROR);
        }
      } else buildURL = FORBIDDEN_URL;
      // Enter Guest perspective of course
    } else if (action.equals(ACT_VIEWGUEST)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      CourseLocal course =
          XMLBuilder.getDatabase().courseHome().findByPrimaryKey(
              new CoursePK(courseID));
      if (p.isInStaffAsBlankMode()) {
        p.resetToStaffMode();
      }
      if (p.isAdminPrivByCourseID(courseID)) {
        if (course.getCourseGuestAccess()) {
          p.setStaffAsGuest(courseID);
          buildURL = COURSE_URL;
          xml = XMLBuilder.buildCoursePage(p, courseID);
        } else {
          buildURL = COURSEADMIN_URL;
          xml = XMLBuilder.buildCoursePage(p, courseID);
          String error =
              "Cannot view course page as Guest: "
                  + "that authorization level has no access "
                  + "to the course page<br>";
          error += "Navigate to Course Options to view/edit permissions";
          xml = XMLBuilder.addStatus(xml, error, XMLBuilder.MSG_ERROR);
        }
      } else buildURL = FORBIDDEN_URL;
    } else if (action.equals(ACT_VIEWRESET)) {
      if (p.isInStaffAsBlankMode()) {
        long courseID = p.getCourseID();
        p.resetToStaffMode();
        if (p.isStaffInCourseByCourseID(courseID)) {
          p.resetToStaffMode();
        }
        if (p.isStaffInCourseByCourseID(courseID)) {
          buildURL = COURSEADMIN_URL;
          xml = XMLBuilder.buildCoursePage(p, courseID);
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
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      String studentID = request.getParameter(P_NETID);
      if (p.isInStaffAsBlankMode()) {
        p.resetToStaffMode();
      }
      if (p.isAdminPrivByCourseID(courseID)) {
        String message = null;
        message = p.setStaffAsStudent(studentID, courseID);
        if (message != null) {
          xml = (Document) session.getAttribute(A_DISPLAYDATA);
          xml = XMLBuilder.addStatus(xml, message, XMLBuilder.MSG_ERROR);
          buildURL = (String) session.getAttribute(A_URL);
        } else {
          xml = XMLBuilder.buildCoursePage(p, courseID);
          buildURL = COURSE_URL;
        }
      } else buildURL = FORBIDDEN_URL;
    }
    // View the page to upload a general CSV student info file
    else if (action.equals(ACT_VIEWUPLOAD)) {
      long courseID = Util.parseLong(request.getParameter(P_COURSEID));
      if (p.isStaffInCourseByCourseID(courseID)) {
        xml = XMLBuilder.buildCSVUploadPage(p, courseID);
        buildURL = UPLOAD_URL;
      } else buildURL = FORBIDDEN_URL;
    }
    // null or unrecognized action (possibly first logging in -
    // ACT_OVERLOGIN)
    else {
      // All Authenticated Principals can view their overview
      String semesterID = request.getParameter(P_SEMESTERID);
      Long courseID = null, semID = null;
      /*
       * For users only involved in one course, this code jumps them directly,
       * into the course page for that course.
       */
      if (semesterID == null || semesterID.equals("")) {
        courseID = XMLBuilder.getDatabase().hasSoloCourse(p.getNetID());
      } else {
        semID = new Long(Util.parseLong(semesterID));
        courseID =
            XMLBuilder.getDatabase().hasSoloCourseBySemester(p.getNetID(),
                semID.longValue());
      }
      if (courseID == null) {
        xml = XMLBuilder.buildOverview(p, semID);
        buildURL = OVERVIEW_URL;
      } else {
        xml = XMLBuilder.buildCoursePage(p, courseID.longValue());
        if (p.isStaffInCourseByCourseID(courseID.longValue())) {
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

  private void sendFile(long id, int type, HttpServletResponse response)
      throws IOException, IllegalArgumentException {
    DownloadFile file = null;
    try {
      file = transactions.getJavaFile(id, type);
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
    response.setContentType("application/download");
    response.setHeader("Content-disposition", "attachment; filename=\""
        + file.getDownloadName() + "\"");
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
      bis = new BufferedInputStream(fis);
      bos = new BufferedOutputStream(response.getOutputStream());
      byte[] buff = new byte[2048];
      int bytesread;
      while (-1 != (bytesread = bis.read(buff, 0, buff.length))) {
        bos.write(buff, 0, bytesread);
      }
    } catch (final IOException e) {
      System.out.println("IOException.");
      throw e;
    } finally {
      if (bis != null) bis.close();
      if (bos != null) bos.close();
      if (fis != null) fis.close();
    }
  }
}
