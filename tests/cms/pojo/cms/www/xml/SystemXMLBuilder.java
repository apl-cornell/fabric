package cms.www.xml;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.ejb.FinderException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.Profiler;
import cms.www.util.Util;

import edu.cornell.csuglab.cms.base.*;

/**
 * Builds an XML subtree with requested information on CMS systemwide settings
 * or on usage statistics for the current semester
 * 
 * Created 3 / 27 / 05
 * @author Evan
 */
public class SystemXMLBuilder extends XMLBuilder
{
	/**
	 * Generate an XML subtree with a list of legal file types for upload/download
	 * @param xml The Document to place this element on
	 * @return A TAG_FILETYPES element with child nodes
	 * @throws FinderException
	 */
	public static Element buildFiletypeListSubtree(Document xml) throws FinderException
	{
		Element xFileTypes= xml.createElement(TAG_FILETYPES);
		Collection fileTypes = database.getFileTypes();
		Iterator i = fileTypes.iterator();
		while (i.hasNext()) {
			Element xItem= xml.createElement(TAG_ITEM);
			xItem.setAttribute(A_TYPE, (String) i.next());
			xFileTypes.appendChild(xItem);
		}
		return xFileTypes;
	}
	
	/**
	 * Generate an XML subtree with a list of NetIDs to use to debug CMS
	 * @param xml The Document to place this element on
	 * @return A TAG_DEBUGIDS element with child nodes
	 * @throws FinderException
	 */
	public static Element buildDebugNetIDListSubtree(Document xml) throws FinderException {
		Profiler.enterMethod("SystemXMLBuilder.buildDebugNetIDListSubtree","");
		Element xDebugids = xml.createElement(TAG_DEBUGIDS);
		Element xItem = xml.createElement(TAG_ITEM);
		xItem.setAttribute(A_DEBUGID, "da10");
		xDebugids.appendChild(xItem);
		xItem = xml.createElement(TAG_ITEM);
		xItem.setAttribute(A_DEBUGID, "jfg32");
		xDebugids.appendChild(xItem);
		xItem = xml.createElement(TAG_ITEM);
		xItem.setAttribute(A_DEBUGID, "osb5");
		xDebugids.appendChild(xItem);
		xItem = xml.createElement(TAG_ITEM);
		xItem.setAttribute(A_DEBUGID, "kaa32");
		xDebugids.appendChild(xItem);
		xItem = xml.createElement(TAG_ITEM);
		xItem.setAttribute(A_DEBUGID, "sg252");
		xDebugids.appendChild(xItem);
		/*Collection users = database.userHome().findAll();
	    //users = database.getAllUsers();
	    Iterator i = users.iterator();
	    while (i.hasNext()) {
	    	UserLocal user = (UserLocal) i.next();
			Element xItem = xml.createElement(TAG_ITEM);
			xItem.setAttribute(A_DEBUGID, user.getUserID());
			xDebugids.appendChild(xItem);
	    }*/
		Profiler.exitMethod("SystemXMLBuilder.buildDebugNetIDListSubtree","");
		return xDebugids;
	}
	
	/**
	 * Generate an XML subtree with system usage statistics for the current semester
	 * @param xml The Document on which to place this element
	 * @return A TAG_SYSTEMDATA element with properties set
	 * @throws FinderException
	 */
	public static Element buildSystemDataSubtree(Document xml) throws FinderException {
		Element xSysData = xml.createElement(TAG_SYSTEMDATA);
		//number of courses with students enrolled in them
		SemesterPK sem = database.findCurrentSemester();
		Collection courses = database.courseHome().findBySemesterID(sem.getSemesterID());
		int numCourses = 0;
		Iterator iter = courses.iterator();
		HashSet students = new HashSet(); //holds netIDs (Strings)
		while(iter.hasNext())
		{
			CourseLocal course = (CourseLocal)iter.next();
			Collection crsStuds = course.getActiveStudents();
			if(crsStuds.size() > 0) numCourses++;
			Iterator iter2 = crsStuds.iterator();
			while(iter2.hasNext()) //add netIDs; sets contain no duplicates, so dups will be filtered out
				students.add(((StudentData)iter2.next()).getUserID());
		}
		//number of courses for the currently selected semester
		xSysData.setAttribute(A_COURSES, Integer.toString(numCourses));
		//number of students active in one or more courses
		xSysData.setAttribute(A_ENROLLMENT, Integer.toString(students.size()));
		//number of users of all kinds in the system
		Collection users = database.userHome().findAll();
		xSysData.setAttribute(A_USERS, Integer.toString(users.size()));
		return xSysData;
	}
}
