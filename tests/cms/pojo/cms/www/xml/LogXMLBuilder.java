package cms.www.xml;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.FinderException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.DateTimeUtil;

import edu.cornell.csuglab.cms.author.Principal;
import edu.cornell.csuglab.cms.base.*;
import edu.cornell.csuglab.cms.log.LogDetail;

/**
 * Builds an XML subtree with information on log search results
 * 
 * Created 5 / 19 / 05
 * @author Evan
 */
public class LogXMLBuilder extends XMLBuilder
{
	/**
	 * Generate an XML subtree with all info on the given Logs and their associated low-level logs
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @param logs A Collection of Log objects from which to take information
	 * @return A log-results element with no properties and with children for individual log entries
	 * @throws FinderException
	 */
	public static Element buildFullSubtree(Principal p, Document xml, Collection logs) throws FinderException
	{
		Element logRoot = xml.createElement(TAG_LOGSEARCH_RESULTS);
		Iterator i = logs.iterator();
		Map courseCodes = database.getCIDCourseCodeMap();
		Map assignNames = database.getAssignmentNameMap();
		while(i.hasNext())
		{
			logRoot.appendChild(buildLogSubtree(xml, (LogLocal)i.next(), courseCodes, assignNames));
		}
		return logRoot;
	}
	
	/**
	 * Generate an XML subtree with all info on the given Log and its associated LogDetails
	 * @param xml The Document on which to create this subtree
	 * @param log The Log from which to take information
	 * @return A log element with several properties set and child nodes for detailed logs
	 * @throws FinderException
	 */
	protected static Element buildLogSubtree(Document xml, LogLocal log, Map codes, Map assigns) throws FinderException {
		Element logElement = xml.createElement(TAG_LOG);
		logElement.setAttribute(A_LOGID, String.valueOf(log.getLogID()));
		logElement.setAttribute(A_NETID, log.getActingNetID());
		if (log.getSimulatedNetID() != null)
		    logElement.setAttribute(A_SIMNETID, log.getSimulatedNetID());
		logElement.setAttribute(A_IPADDRESS, log.getActingIPAddress().getHostAddress());
		logElement.setAttribute(A_DATE, DateTimeUtil.formatDate(log.getTimestamp()));
		logElement.setAttribute(A_LOGTYPE, LogBean.logTypeToString(log.getLogType()));
		logElement.setAttribute(A_LOGNAME, log.getLogName());
		if (log.getCourseID() != null) 
		    logElement.setAttribute(A_COURSENAME, (String) codes.get(log.getCourseID()));
		Iterator i = log.getAssignmentIDs().iterator();
		String assignments = "";
		while (i.hasNext()){
		    Long aid = (Long) i.next();
		    String aname = (String) assigns.get(aid);
		    assignments += (assignments.equals("") ? aname : ", " + aname);
		}
		if (!assignments.equals("")) {
		    logElement.setAttribute(A_ASSIGNMENT, assignments);
		}
		appendReceivingNetIDSubtree(xml, logElement, log.getReceivingNetIDs());
		i = log.getDetailLogs().iterator();
		while(i.hasNext()) {
			logElement.appendChild(buildDetailedLogSubtree(xml, (LogDetail)i.next(), assigns));
		}
		return logElement;
	}
	
	/**
	 * Generate an XML subtree with all info on the given low-level log, except for a reference
	 * to the high-level log it's associated with (because we assume that log is known)
	 * @param xml The Document on which to create this subtree
	 * @param details The LogDetail from which to take information
	 * @return A log-detail element with as few properties set as possible
	 * (since we'd rather have most of the information in the high-level-log node
	 */
	protected static Element buildDetailedLogSubtree(Document xml, LogDetail details, Map assigns) {
		Element detailElement = xml.createElement(TAG_LOGDETAIL);
		detailElement.setAttribute(A_DETAILS, details.getDetailString());
		if (details.getNetID() != null) {
		    detailElement.setAttribute(A_NETID, details.getNetID());
		}
		if (details.getAssignmentID() != null) {
		   detailElement.setAttribute(A_ASSIGNID, details.getAssignmentID().toString());
		   detailElement.setAttribute(A_ASSIGNNAME, (String) assigns.get(details.getAssignmentID()));
		}
		return detailElement;
	}
	
	protected static void appendReceivingNetIDSubtree(Document xml, Element e, Collection netids) {
	    Iterator i = netids.iterator();
	    while (i.hasNext()) {
	        String netid = (String) i.next();
	        Element xNetID = xml.createElement(TAG_RECNETID);
	        xNetID.setAttribute(A_NETID, netid);
	        e.appendChild(xNetID);
	    }
	}
	
	protected static void appendLogSearchCourses(Principal p, Document xml, Element root) throws FinderException, FinderException {
	    Element logSearchCourses = xml.createElement(TAG_LOGSEARCH_COURSES);
	    Collection courses;
	    SemesterPK curSemester = database.findCurrentSemester();
	    if (p.isCMSAdmin()) {
	        courses = database.courseHome().findBySemesterID(curSemester.getSemesterID());
	    } else {
	        courses = database.courseHome().findStaffAdminCourses(curSemester.getSemesterID(), p.getUserID());
	    }
	    Iterator i = courses.iterator();
	    while (i.hasNext()) {
	        CourseLocal c = (CourseLocal) i.next();
	        Element xCourse = xml.createElement(TAG_LOGSEARCH_COURSE);
	        xCourse.setAttribute(A_COURSEID, String.valueOf(c.getCourseID()));
	        xCourse.setAttribute(A_COURSENAME, c.getName());
	        xCourse.setAttribute(A_CODE, c.getCode());
	        logSearchCourses.appendChild(xCourse);
	    }
	    root.appendChild(logSearchCourses);
	}
	
	//TODO ADD AVAILABLE GROUPS NODE SEARCHING
	
	protected static void appendLogSearchNamesTypes(Document xml, Element root, boolean admin) {
	    // TYPES:
	    Element xTypes = xml.createElement(TAG_LOGTYPES);
	    Element xAdmin = xml.createElement(TAG_LOGTYPE),
	    		xCourse = xml.createElement(TAG_LOGTYPE),
	    		xGroup = xml.createElement(TAG_LOGTYPE),
	    		xGrade = xml.createElement(TAG_LOGTYPE),
	    		xCategory = xml.createElement(TAG_LOGTYPE);
	    xAdmin.setAttribute(A_LOGTYPE, String.valueOf(LogBean.LOG_ADMIN));
	    xAdmin.setAttribute(A_LOGTYPESTR, LogBean.ADMIN);
	    xCourse.setAttribute(A_LOGTYPE, String.valueOf(LogBean.LOG_COURSE));
	    xCourse.setAttribute(A_LOGTYPESTR, LogBean.COURSE);
	    xGroup.setAttribute(A_LOGTYPE, String.valueOf(LogBean.LOG_GROUP));
	    xGroup.setAttribute(A_LOGTYPESTR, LogBean.GROUP);
	    xGrade.setAttribute(A_LOGTYPE, String.valueOf(LogBean.LOG_GRADE));
	    xGrade.setAttribute(A_LOGTYPESTR, LogBean.GRADE);
	    xCategory.setAttribute(A_LOGTYPE, String.valueOf(LogBean.LOG_CATEGORY));
	    xCategory.setAttribute(A_LOGTYPESTR, LogBean.CATEGORY);
	    if (admin) {
	        xTypes.appendChild(xAdmin);
	    }
	    xTypes.appendChild(xCourse);
	    xTypes.appendChild(xCategory);
	    xTypes.appendChild(xGroup);
	    xTypes.appendChild(xGrade);
	    // NAMES:
	    Element xNames = xml.createElement(TAG_LOGNAMES);
	    Element xName = xml.createElement(TAG_LOGNAME);
	    // Admin Types
	    if (admin) {
		    xName.setAttribute(A_LOGNAME, LogBean.ADD_CMS_ADMIN);
		    xName.setAttribute(A_LOGTYPE, LogBean.ADMIN);
		    xNames.appendChild(xName);
		    xName = xml.createElement(TAG_LOGNAME);
		    xName.setAttribute(A_LOGNAME, LogBean.CREATE_COURSE);
		    xName.setAttribute(A_LOGTYPE, LogBean.ADMIN);
		    xNames.appendChild(xName);
		    xName = xml.createElement(TAG_LOGNAME);
		    xName.setAttribute(A_LOGNAME, LogBean.CREATE_SEMESTER);
		    xName.setAttribute(A_LOGTYPE, LogBean.ADMIN);
		    xNames.appendChild(xName);
		    xName = xml.createElement(TAG_LOGNAME);
		    xName.setAttribute(A_LOGNAME, LogBean.EDIT_SEMESTER);
		    xName.setAttribute(A_LOGTYPE, LogBean.ADMIN);
		    xNames.appendChild(xName);
		    xName = xml.createElement(TAG_LOGNAME);
		    xName.setAttribute(A_LOGNAME, LogBean.REMOVE_CMS_ADMIN);
		    xName.setAttribute(A_LOGTYPE, LogBean.ADMIN);
		    xNames.appendChild(xName);
		    xName = xml.createElement(TAG_LOGNAME);
		    xName.setAttribute(A_LOGNAME, LogBean.SET_CUIDS);
		    xName.setAttribute(A_LOGTYPE, LogBean.ADMIN);
		    xNames.appendChild(xName);
		    xName = xml.createElement(TAG_LOGNAME);
		    xName.setAttribute(A_LOGNAME, LogBean.SET_CURRENT_SEMESTER);
		    xName.setAttribute(A_LOGTYPE, LogBean.ADMIN);
		    xNames.appendChild(xName);
		    xName = xml.createElement(TAG_LOGNAME);
		    xName.setAttribute(A_LOGNAME, LogBean.SET_USERNAMES);
		    xName.setAttribute(A_LOGTYPE, LogBean.ADMIN);
		    xNames.appendChild(xName);
	    }
	    // Course Types
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.ADD_STUDENTS);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.CHANGE_GROUP_TIMESLOT);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.COMPUTE_ASSIGNMENT_STATS);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.COMPUTE_TOTAL_SCORES);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.CREATE_ANNOUNCEMENT);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.CREATE_ASSIGNMENT);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.CREATE_TIMESLOTS);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.DROP_STUDENTS);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.EDIT_ANNOUNCEMENT);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.EDIT_ASSIGNMENT);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.EDIT_COURSE_PROPS);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.EDIT_STAFF_PREFS);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.EDIT_STUDENT_PREFS);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.REMOVE_ASSIGNMENT);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);	    
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.REMOVE_TIMESLOTS);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.RESTORE_ANNOUNCEMENT);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.RESTORE_ASSIGNMENT);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.SEND_EMAIL);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.UPLOAD_CLASSLIST);
	    xName.setAttribute(A_LOGTYPE, LogBean.COURSE);
	    xNames.appendChild(xName);
	    // Category Types
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.ADDNEDIT_CONTENTS);
	    xName.setAttribute(A_LOGTYPE, LogBean.CATEGORY);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.CREATE_CATEGORY);
	    xName.setAttribute(A_LOGTYPE, LogBean.CATEGORY);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.EDIT_CATEGORY);
	    xName.setAttribute(A_LOGTYPE, LogBean.CATEGORY);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.ORDER_CATEGORIES);
	    xName.setAttribute(A_LOGTYPE, LogBean.CATEGORY);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.REMOVE_CATEGORY);
	    xName.setAttribute(A_LOGTYPE, LogBean.CATEGORY);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.REMOVE_CATEGORY_ROW);
	    xName.setAttribute(A_LOGTYPE, LogBean.CATEGORY);
	    xNames.appendChild(xName);
	    // Grade Types
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.ASSIGN_GRADER);
	    xName.setAttribute(A_LOGTYPE, LogBean.GRADE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.EDIT_FINAL_GRADES);
	    xName.setAttribute(A_LOGTYPE, LogBean.GRADE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.EDIT_GRADES);
	    xName.setAttribute(A_LOGTYPE, LogBean.GRADE);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.UPLOAD_GRADES_FILE);
	    xName.setAttribute(A_LOGTYPE, LogBean.GRADE);
	    xNames.appendChild(xName);
	    // Group Types
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.ACCEPT_INVITATION);
	    xName.setAttribute(A_LOGTYPE, LogBean.GROUP);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.CANCEL_INVITATION);
	    xName.setAttribute(A_LOGTYPE, LogBean.GROUP);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.CREATE_GROUP);
	    xName.setAttribute(A_LOGTYPE, LogBean.GROUP);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.CREATE_INVITATION);
	    xName.setAttribute(A_LOGTYPE, LogBean.GROUP);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.DISBAND_GROUP);
	    xName.setAttribute(A_LOGTYPE, LogBean.GROUP);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.GRANT_EXTENSION);
	    xName.setAttribute(A_LOGTYPE, LogBean.GROUP);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.LEAVE_GROUP);
	    xName.setAttribute(A_LOGTYPE, LogBean.GROUP);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.REJECT_INVITATION);
	    xName.setAttribute(A_LOGTYPE, LogBean.GROUP);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.REMOVE_EXTENSION);
	    xName.setAttribute(A_LOGTYPE, LogBean.GROUP);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.REQUEST_REGRADE);
	    xName.setAttribute(A_LOGTYPE, LogBean.GROUP);
	    xNames.appendChild(xName);
	    xName = xml.createElement(TAG_LOGNAME);
	    xName.setAttribute(A_LOGNAME, LogBean.SUBMIT_FILES);
	    xName.setAttribute(A_LOGTYPE, LogBean.GROUP);
	    xNames.appendChild(xName);
	    root.appendChild(xNames);
	    root.appendChild(xTypes);
	}
	
	protected static void appendLogSearchAssigns(Principal p, Document xml, Element root) throws FinderException {
	    Element logSearchAssigns = xml.createElement(TAG_LOGSEARCH_ASSIGNS);
	    Collection assigns;
	    SemesterPK curSemester = database.findCurrentSemester();
	    Map codes = database.getCourseCodeMap();
	    if (p.isCMSAdmin()) {
	        assigns = database.assignmentHome().findBySemesterID(curSemester.getSemesterID());
	    } else {
	        assigns = database.assignmentHome().findByCourseAdmin(curSemester.getSemesterID(), p.getUserID());
	    }
	    Iterator i = assigns.iterator();
	    while (i.hasNext()) {
	        AssignmentLocal a = (AssignmentLocal) i.next();
	        Element xAssign = xml.createElement(TAG_LOGSEARCH_ASSIGN);
	        String courseCode = ((String)codes.get(new Long(a.getAssignmentID())));
	        xAssign.setAttribute(A_ASSIGNID, String.valueOf(a.getAssignmentID()));
	        xAssign.setAttribute(A_NAME, courseCode + ": " + a.getName());
	        xAssign.setAttribute(A_NAMESHORT, a.getName());
	        xAssign.setAttribute(A_COURSEID, String.valueOf(a.getCourseID()));
	        logSearchAssigns.appendChild(xAssign);
	    }
	    root.appendChild(logSearchAssigns);
	}
	
	protected static void appendAssignments(Principal p, Document xml, long courseID) throws FinderException {
	    Collection assigns = database.assignmentHome().findByCourseID(courseID);
	    Iterator i = assigns.iterator();
	    Element root = (Element) xml.getFirstChild();
	    while (i.hasNext()) {
	        AssignmentLocal a = (AssignmentLocal) i.next();
	        Element xAssign = xml.createElement(TAG_LOGSEARCH_ASSIGN);
	        xAssign.setAttribute(A_ASSIGNID, String.valueOf(a.getAssignmentID()));
	        xAssign.setAttribute(A_ASSIGNNAME, a.getName());
	        xAssign.setAttribute(A_NAMESHORT, a.getNameShort());
	        root.appendChild(xAssign);
	    }
	}
}