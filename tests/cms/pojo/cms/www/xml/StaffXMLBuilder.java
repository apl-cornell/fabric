package cms.www.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.FinderException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.Profiler;

import edu.cornell.csuglab.cms.author.Principal;
import edu.cornell.csuglab.cms.base.*;

/**
 * Builds an XML subtree with information about a given user with staff privileges for at least one course
 * 
 * Created 3 / 28 / 05
 * @author Evan
 */
public class StaffXMLBuilder extends XMLBuilder
{
	/**
	 * Generate an XML subtree with a list of the courses the given principal is staff for
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @return A list of courses, with short info, under a TAG_STAFFCOURSES element
	 * @throws RemoteException
	 */
	public Element buildCourseListSubtree(Principal p, Document xml, Long semesterID) throws FinderException {
		Element xStaffCourses= xml.createElement(TAG_STAFFCOURSES);
		Iterator i = semesterID == null ? database.courseHome().findStaffCourses(p.getPrincipalID()).iterator()
				: database.courseHome().findStaffCoursesBySemester(p.getPrincipalID(), semesterID.longValue()).iterator();
		while (i.hasNext()) {
			xStaffCourses.appendChild(CourseXMLBuilder.buildShortSubtree(p, xml, (CourseLocal)i.next()));
		}
		return xStaffCourses;
	}
	
}
