package cms.www.xml;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.FinderException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;

import edu.cornell.csuglab.cms.author.Principal;
import edu.cornell.csuglab.cms.base.*;

/**
 * Builds an XML subtree with information about a given student
 * 
 * Created 3 / 28 / 05
 * @author Evan
 */
public class StudentXMLBuilder extends XMLBuilder
{
	/**
	 * Generate an XML subtree with a list of the courses the given principal is a student in
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @return A list of courses, with short info, under a TAG_STUDENTCOURSES element
	 * @throws FinderException
	 */
	public Element buildCourseListSubtree(Principal p, Document xml, Long semesterID) throws FinderException {
		Element xStudentCourses = xml.createElement(TAG_STUDENTCOURSES);
		Iterator i;
		CourseLocalHome ch = XMLBuilder.database.courseHome();
		String pid = p.getPrincipalID();
		if (semesterID == null) {
			Collection c = ch.findStudentCourses(pid);
			i = c.iterator();
		} else {
			Collection c = ch.findStudentCoursesBySemester(pid, semesterID.longValue());
			i = c.iterator();
		}
		while (i.hasNext()) {
			xStudentCourses.appendChild(CourseXMLBuilder.buildShortSubtree(p, xml, (CourseLocal)i.next()));
		}
		return xStudentCourses;
	}
	
	/**
 	 * Generate an XML subtree with info on assignments posted in classes the given principal is in
 	 * @param p The Principal to generate the page for
 	 * @param xml The Document to place this element on
 	 * @return An element holding a list of assignments
 	 * @throws FinderException
 	 */
	public Element buildDueAsgnListSubtree(Principal p, Document xml, Long semesterID) throws FinderException {
		Element xAssignments = xml.createElement(TAG_ALLDUEASSIGNMENTS);
		Iterator i = semesterID == null ? 
		        database.assignmentHome().findByDateNetID(p.getPrincipalID(), new Timestamp(System.currentTimeMillis())).iterator()
		        : database.assignmentHome().findByDateNetIDSemester(p.getPrincipalID(), new Timestamp(System.currentTimeMillis()), semesterID.longValue()).iterator();
		while (i.hasNext()) {
			AssignmentLocal assignment = (AssignmentLocal)i.next();
			Element xAssignment = AssignmentXMLBuilder.buildShortSubtree(xml, assignment);
			xAssignment.setAttribute(A_DUEDATE, DateTimeUtil.formatCountdown(new Timestamp(System.currentTimeMillis()), assignment.getDueDate()));
			xAssignment.setAttribute(A_ASSIGNID, Long.toString(assignment.getAssignmentID()));
			xAssignment.setAttribute(A_STATUS, assignment.getStatus());
			CourseLocal course = database.courseHome().findByPrimaryKey(new CoursePK(assignment.getCourseID()));
			xAssignment.setAttribute(A_COURSEID, Long.toString(course.getCourseID()));
			xAssignments.appendChild(xAssignment);
		}
		return xAssignments;
	}
}
