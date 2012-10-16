package cms.www.xml;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.model.Course;
import cms.www.util.DateTimeUtil;

import cms.auth.Principal;
import cms.model.*;

/**
 * Builds an XML subtree with information on log search results Created 5 / 19 /
 * 05
 * 
 * @author Evan
 */
public class LogXMLBuilder {
  private XMLBuilder xmlBuilder;
  
  public LogXMLBuilder(final XMLBuilder builder) {
    this.xmlBuilder = builder;
  }

  /**
   * Generate an XML subtree with all info on the given Logs and their
   * associated low-level logs
   * 
   * @param p
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param logs
   *          A Collection of Log objects from which to take information
   * @return A log-results element with no properties and with children for
   *         individual log entries
   * @throws FinderException
   */
  public Element buildFullSubtree(User p, Document xml, Collection logs) {
    Element logRoot = xml.createElement(XMLBuilder.TAG_LOGSEARCH_RESULTS);
    Iterator i = logs.iterator();
    while (i.hasNext())
      logRoot.appendChild(buildLogSubtree(xml, (Log) i.next()));
    return logRoot;
  }

  /**
   * Generate an XML subtree with all info on the given Log and its associated
   * LogDetails
   * 
   * @param xml
   *          The Document on which to create this subtree
   * @param log
   *          The Log from which to take information
   * @return A log element with several properties set and child nodes for
   *         detailed logs
   * @throws FinderException
   */
  protected Element buildLogSubtree(Document xml, Log log) {
    Element logElement = xml.createElement(XMLBuilder.TAG_LOG);
    logElement.setAttribute(XMLBuilder.A_LOGID, log.toString());
    logElement.setAttribute(XMLBuilder.A_NETID, log.getActingNetID());
    if (log.getSimulatedNetID() != null)
      logElement.setAttribute(XMLBuilder.A_SIMNETID, log.getSimulatedNetID());
    InetAddress ip = log.getActingIPAddress();
    logElement.setAttribute(XMLBuilder.A_IPADDRESS, ip == null ? "(none)" : ip
        .getHostAddress());
    logElement.setAttribute(XMLBuilder.A_DATE, DateTimeUtil.formatDate(log
        .getTimestamp()));
    logElement.setAttribute(XMLBuilder.A_LOGTYPE, Log.logTypeToString(log
        .getLogType()));
    logElement.setAttribute(XMLBuilder.A_LOGNAME, log.getLogName());
    if (log.getCourse() != null)
      logElement.setAttribute(XMLBuilder.A_COURSENAME, log.getCourse().getCode());
    Iterator i = log.findAssignments().iterator();
    StringBuilder assignments = new StringBuilder();
    while (i.hasNext()) {
      Assignment assign = (Assignment) i.next();
      if (assignments.length() > 0)
        assignments.append(", ");
      assignments.append(assign.getName());
    }
    if (assignments.length() > 0) {
      logElement.setAttribute(XMLBuilder.A_ASSIGNMENT, assignments.toString());
    }
    appendReceivingNetIDSubtree(xml, logElement, log.getReceivingUsers().values());
    i = log.getDetailLogs().iterator();
    while (i.hasNext()) {
      logElement.appendChild(buildDetailedLogSubtree(xml, (LogDetail) i.next()));
    }
    return logElement;
  }

  /**
   * Generate an XML subtree with all info on the given low-level log, except
   * for a reference to the high-level log it's associated with (because we
   * assume that log is known)
   * 
   * @param xml
   *          The Document on which to create this subtree
   * @param details
   *          The LogDetail from which to take information
   * @return A log-detail element with as few properties set as possible (since
   *         we'd rather have most of the information in the high-level-log node
   */
  protected Element buildDetailedLogSubtree(Document xml, LogDetail details) {
    Element detailElement = xml.createElement(XMLBuilder.TAG_LOGDETAIL);
    detailElement.setAttribute(XMLBuilder.A_DETAILS, details.getDetail());
    if (details.getAffectedUser() != null) {
      detailElement.setAttribute(XMLBuilder.A_NETID, details.getAffectedUser().getNetID());
    }
    if (details.getAssignment() != null) {
      detailElement.setAttribute(XMLBuilder.A_ASSIGNID,   details.getAssignment().toString());
      detailElement.setAttribute(XMLBuilder.A_ASSIGNNAME, details.getAssignment().getName());
    }
    return detailElement;
  }

  protected void appendReceivingNetIDSubtree(Document xml, Element e,
      Collection netids) {
    Iterator i = netids.iterator();
    while (i.hasNext()) {
      String netid = ((User) i.next()).getNetID();
      Element xNetID = xml.createElement(XMLBuilder.TAG_RECNETID);
      xNetID.setAttribute(XMLBuilder.A_NETID, netid);
      e.appendChild(xNetID);
    }
  }

  protected void appendLogSearchCourses(User p, Document xml, Element root) {
    Element logSearchCourses =
        xml.createElement(XMLBuilder.TAG_LOGSEARCH_COURSES);
    Collection courses;
    Semester curSemester = xmlBuilder.database.getCurrentSemester();
    if (p.isCMSAdmin()) {
      courses = curSemester.getCourses();
    } else {
      courses = curSemester.findStaffAdminCourses(p);
    }
    Iterator i = courses.iterator();
    while (i.hasNext()) {
      Course c = (Course) i.next();
      Element xCourse = xml.createElement(XMLBuilder.TAG_LOGSEARCH_COURSE);
      xCourse.setAttribute(XMLBuilder.A_COURSEID,   c.toString());
      xCourse.setAttribute(XMLBuilder.A_COURSENAME, c.getName());
      xCourse.setAttribute(XMLBuilder.A_CODE,       c.getCode());
      logSearchCourses.appendChild(xCourse);
    }
    root.appendChild(logSearchCourses);
  }

  // TODO ADD AVAILABLE GROUPS NODE SEARCHING

  protected void appendLogSearchNamesTypes(Document xml, Element root,
      boolean admin) {
    // TYPES:
    Element xTypes = xml.createElement(XMLBuilder.TAG_LOGTYPES);
    Element xAdmin = xml.createElement(XMLBuilder.TAG_LOGTYPE), xCourse =
        xml.createElement(XMLBuilder.TAG_LOGTYPE), xGroup =
        xml.createElement(XMLBuilder.TAG_LOGTYPE), xGrade =
        xml.createElement(XMLBuilder.TAG_LOGTYPE), xCategory =
        xml.createElement(XMLBuilder.TAG_LOGTYPE);
    xAdmin.setAttribute(XMLBuilder.A_LOGTYPE, String.valueOf(Log.LOG_ADMIN));
    xAdmin.setAttribute(XMLBuilder.A_LOGTYPESTR, Log.ADMIN);
    xCourse.setAttribute(XMLBuilder.A_LOGTYPE, String.valueOf(Log.LOG_COURSE));
    xCourse.setAttribute(XMLBuilder.A_LOGTYPESTR, Log.COURSE);
    xGroup.setAttribute(XMLBuilder.A_LOGTYPE, String.valueOf(Log.LOG_GROUP));
    xGroup.setAttribute(XMLBuilder.A_LOGTYPESTR, Log.GROUP);
    xGrade.setAttribute(XMLBuilder.A_LOGTYPE, String.valueOf(Log.LOG_GRADE));
    xGrade.setAttribute(XMLBuilder.A_LOGTYPESTR, Log.GRADE);
    xCategory.setAttribute(XMLBuilder.A_LOGTYPE, String
        .valueOf(Log.LOG_CATEGORY));
    xCategory.setAttribute(XMLBuilder.A_LOGTYPESTR, Log.CATEGORY);
    if (admin) {
      xTypes.appendChild(xAdmin);
    }
    xTypes.appendChild(xCourse);
    xTypes.appendChild(xCategory);
    xTypes.appendChild(xGroup);
    xTypes.appendChild(xGrade);
    // NAMES:
    Element xNames = xml.createElement(XMLBuilder.TAG_LOGNAMES);
    Element xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    // Admin Types
    if (admin) {
      xName.setAttribute(XMLBuilder.A_LOGNAME, Log.ADD_CMS_ADMIN);
      xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.ADMIN);
      xNames.appendChild(xName);
      xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
      xName.setAttribute(XMLBuilder.A_LOGNAME, Log.CREATE_COURSE);
      xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.ADMIN);
      xNames.appendChild(xName);
      xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
      xName.setAttribute(XMLBuilder.A_LOGNAME, Log.CREATE_SEMESTER);
      xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.ADMIN);
      xNames.appendChild(xName);
      xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
      xName.setAttribute(XMLBuilder.A_LOGNAME, Log.EDIT_SEMESTER);
      xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.ADMIN);
      xNames.appendChild(xName);
      xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
      xName.setAttribute(XMLBuilder.A_LOGNAME, Log.REMOVE_CMS_ADMIN);
      xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.ADMIN);
      xNames.appendChild(xName);
      xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
      xName.setAttribute(XMLBuilder.A_LOGNAME, Log.SET_CUIDS);
      xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.ADMIN);
      xNames.appendChild(xName);
      xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
      xName.setAttribute(XMLBuilder.A_LOGNAME, Log.SET_CURRENT_SEMESTER);
      xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.ADMIN);
      xNames.appendChild(xName);
      xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
      xName.setAttribute(XMLBuilder.A_LOGNAME, Log.SET_USERNAMES);
      xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.ADMIN);
      xNames.appendChild(xName);
    }
    // Course Types
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.ADD_STUDENTS);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.CHANGE_GROUP_TIMESLOT);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.COMPUTE_ASSIGNMENT_STATS);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.COMPUTE_TOTAL_SCORES);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.CREATE_ANNOUNCEMENT);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.CREATE_ASSIGNMENT);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.CREATE_TIMESLOTS);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.DROP_STUDENTS);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.EDIT_ANNOUNCEMENT);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.EDIT_ASSIGNMENT);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.EDIT_COURSE_PROPS);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.EDIT_STAFF_PREFS);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.EDIT_STUDENT_PREFS);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.REMOVE_ASSIGNMENT);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.REMOVE_TIMESLOTS);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.RESTORE_ANNOUNCEMENT);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.RESTORE_ASSIGNMENT);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.SEND_EMAIL);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.UPLOAD_CLASSLIST);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.COURSE);
    xNames.appendChild(xName);
    // Category Types
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.ADDNEDIT_CONTENTS);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.CATEGORY);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.CREATE_CATEGORY);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.CATEGORY);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.EDIT_CATEGORY);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.CATEGORY);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.ORDER_CATEGORIES);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.CATEGORY);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.REMOVE_CATEGORY);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.CATEGORY);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.REMOVE_CATEGORY_ROW);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.CATEGORY);
    xNames.appendChild(xName);
    // Grade Types
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.ASSIGN_GRADER);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GRADE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.EDIT_FINAL_GRADES);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GRADE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.EDIT_GRADES);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GRADE);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.UPLOAD_GRADES_FILE);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GRADE);
    xNames.appendChild(xName);
    // Group Types
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.ACCEPT_INVITATION);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GROUP);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.CANCEL_INVITATION);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GROUP);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.CREATE_GROUP);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GROUP);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.CREATE_INVITATION);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GROUP);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.DISBAND_GROUP);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GROUP);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.GRANT_EXTENSION);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GROUP);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.LEAVE_GROUP);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GROUP);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.REJECT_INVITATION);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GROUP);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.REMOVE_EXTENSION);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GROUP);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.REQUEST_REGRADE);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GROUP);
    xNames.appendChild(xName);
    xName = xml.createElement(XMLBuilder.TAG_LOGNAME);
    xName.setAttribute(XMLBuilder.A_LOGNAME, Log.SUBMIT_FILES);
    xName.setAttribute(XMLBuilder.A_LOGTYPE, Log.GROUP);
    xNames.appendChild(xName);
    root.appendChild(xNames);
    root.appendChild(xTypes);
  }

  protected void appendLogSearchAssigns(User p, Document xml, Element root) {
    Element logSearchAssigns =
        xml.createElement(XMLBuilder.TAG_LOGSEARCH_ASSIGNS);
    Collection assigns;
    Semester curSemester = xmlBuilder.database.getCurrentSemester();
    if (p.isCMSAdmin()) {
      assigns = curSemester.findAssignments();
    } else {
      assigns = xmlBuilder.database.findAssignmentsByCourseAdmin(curSemester, p);
    }
    Iterator i = assigns.iterator();
    while (i.hasNext()) {
      Assignment a = (Assignment) i.next();
      Element xAssign = xml.createElement(XMLBuilder.TAG_LOGSEARCH_ASSIGN);
      xAssign.setAttribute(XMLBuilder.A_ASSIGNID,  a.toString());
      xAssign.setAttribute(XMLBuilder.A_NAME,      a.getCourse().getCode() + ": " + a.getName());
      xAssign.setAttribute(XMLBuilder.A_NAMESHORT, a.getName());
      xAssign.setAttribute(XMLBuilder.A_COURSEID,  a.getCourse().toString());
      logSearchAssigns.appendChild(xAssign);
    }
    root.appendChild(logSearchAssigns);
  }

  protected void appendAssignments(User p, Document xml,
      Course course) {
    Iterator i = course.getAssignments().iterator();
    Element root = (Element) xml.getFirstChild();
    while (i.hasNext()) {
      Assignment a = (Assignment) i.next();
      Element xAssign = xml.createElement(XMLBuilder.TAG_LOGSEARCH_ASSIGN);
      xAssign.setAttribute(XMLBuilder.A_ASSIGNID, a.toString());
      xAssign.setAttribute(XMLBuilder.A_ASSIGNNAME, a.getName());
      xAssign.setAttribute(XMLBuilder.A_NAMESHORT, a.getNameShort());
      root.appendChild(xAssign);
    }
  }
}
