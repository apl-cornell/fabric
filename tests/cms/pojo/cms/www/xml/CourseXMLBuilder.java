package cms.www.xml;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.FinderException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.Profiler;
import cms.www.util.StringUtil;


import edu.cornell.csuglab.cms.author.Principal;
import edu.cornell.csuglab.cms.base.*;
import edu.cornell.csuglab.cms.util.category.CoursewideCategoryDataProvider;

/**
 * Builds an XML subtree describing a course in as much detail as desired
 * 
 * Created 3 / 26 / 05
 * @author Evan
 */
public class CourseXMLBuilder extends XMLBuilder
{
	/**
	 * Generate an XML subtree with all known info about the given Course that relates to the given Principal
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @param course The course from which to generate the branch
	 * @return The course element of the tree, with general properties set and several subtrees
	 * @throws FinderException
	 * @deprecated This method is very slow and should usually be avoided
	 */
	public static Element buildFullSubtree(Principal p, Document xml, CourseLocal course) throws FinderException
	{
	    Profiler.enterMethod("CourseXMLBuilder.buildFullSubtree","CourseID: " + course.getCourseID());
		boolean isStaff = p.isStaffInCourseByCourseID(course.getCourseID());
		Element xCourse = buildGeneralSubtree(p, xml, course);
		xCourse.appendChild(AnnouncementXMLBuilder.buildAnnouncementsSubtree(p, xml, course));
		AnnouncementXMLBuilder.appendHiddenAnnouncements(xml, course.getCourseID());
		xCourse.appendChild(buildAssignmentsSubtree(p, xml, course));
		xCourse.appendChild(buildSurveySubtree(p, xml, course.getCourseID()));
		xCourse.appendChild(buildHiddenAssignsSubtree(xml, course.getCourseID()));
		xCourse.appendChild(buildAssignmentFilesSubtree(p, xml, course));
		xCourse.appendChild(buildCategoriesSubtree(p, xml, course));
		if(isStaff) xCourse.appendChild(buildStaffListSubtree(p, xml, course));
	    Profiler.exitMethod("CourseXMLBuilder.buildFullSubtree","CourseID: " + course.getCourseID());
		return xCourse;
	}
	
	public static Element buildSurveySubtree(Principal p, Document xml, long courseID) throws FinderException {
		Collection surveys = new ArrayList();
		Iterator i = database.assignmentHome().findByCourseID(courseID).iterator();
		while (i.hasNext()) {
			AssignmentLocal assignment = (AssignmentLocal) i.next();
			boolean isOpen = assignment.getStatus().equals(AssignmentBean.OPEN);
			boolean isSurvey = assignment.getType() == AssignmentBean.SURVEY;
			if (isSurvey && (isOpen || p.isStaffInCourseByCourseID(courseID)))
				surveys.add(assignment);
		}
		return AssignmentXMLBuilder.buildSurveySubtree(xml, surveys);
	}
	
	/**
	 * Sets the privileges of a course element of the XML tree
	 * @param p User principal making this request
	 * @param xCourse Element of the tree representing the course
	 * @param course CourseData for the course
	 */
	private static void setCoursePrivileges(Principal p, Element xCourse, CourseLocal course) throws FinderException {
	    long courseID= Long.parseLong(xCourse.getAttribute(A_COURSEID));
	    //the given principal's permissions for this course
	    if (!p.isInStaffAsBlankMode()) {
			if(p.isAdminPrivByCourseID(courseID)) xCourse.setAttribute(A_ISADMIN, "true");
			if(p.isGroupsPrivByCourseID(courseID)) xCourse.setAttribute(A_ISGROUPS, "true");
			if(p.isGradesPrivByCourseID(courseID)) xCourse.setAttribute(A_ISGRADES, "true");
			if(p.isAssignPrivByCourseID(courseID)) xCourse.setAttribute(A_ISASSIGN, "true");
			if(p.isCategoryPrivByCourseID(courseID)) xCourse.setAttribute(A_ISCATEGORY, "true");
	    } else {
	        xCourse.setAttribute(A_ISVIEWAS, "true");
	    }
	    StudentLocal student = null;
	    try {
	    	student = database.studentHome().findByPrimaryKey(new StudentPK(courseID, p.getUserID()));
	    	student = student.getStatus().equals(StudentBean.ENROLLED) ? student : null;
	    } catch (Exception e) {}
	    if (student != null) {
	        xCourse.setAttribute(A_ISSTUDENT, "true");
	        xCourse.setAttribute(A_TOTALSCORE, StringUtil.roundToOne(String.valueOf(student.getTotalScore())));
	    }
	    /*
	    int authLevel = p.getAuthoriznLevelByCourseID(courseID);
	    if (authLevel < Principal.AUTHOR_STUDENT || p.isInStaffAsCornellMemMode() || p.isInStaffAsGuestMode()) {
	        if (course.isAnnounceGuestAccess()) {
	            xCourse.setAttribute(A_CANSEEANNOUNCE, "true");
	        }
	        if (course.isAssignGuestAccess()) {
	            xCourse.setAttribute(A_CANSEEASSIGNS, "true");
	        }
	    } else {
	        xCourse.setAttribute(A_CANSEEANNOUNCE, "true");
	        xCourse.setAttribute(A_CANSEEASSIGNS, "true");
	    }*/
	}
	
	/**
	 * Generate an XML subtree with a bit of general info about the given Course
	 * Used primarily for the navigation bar
	 * @param xml The Document to place this element on
	 * @param course The course from which to generate the branch
	 * @return The course element of the tree, with some general properties set
	 * @throws FinderException
	 */
	public static Element buildShortSubtree(Principal p, Document xml, CourseLocal course) throws FinderException
	{
		Element xCourse = xml.createElement(TAG_COURSE);
		xCourse.setAttribute(A_COURSEID, Long.toString(course.getCourseID()));
		xCourse.setAttribute(A_CODE, course.getCode());
		xCourse.setAttribute(A_DISPLAYEDCODE, course.getDisplayedCode());
		xCourse.setAttribute(A_COURSENAME, course.getName());
		xCourse.setAttribute(A_COURSEFROZEN, Boolean.toString(course.getFreezeCourse()));
		return xCourse;
	}
	
	/**
	 * Generates an XML subtree with the general information for listing course in homepage
	 * @param xml
	 * @param course
	 * @return
	 * @throws FinderException
	 */
	public static Element buildHomepageSubtree(Document xml, CourseData course) throws FinderException{
		Element xCourse = xml.createElement(TAG_COURSE);
		xCourse.setAttribute(A_COURSEID, Long.toString(course.getCourseID()));
		xCourse.setAttribute(A_CODE, course.getCode());
		xCourse.setAttribute(A_COURSENAME, course.getName());
		return xCourse;
	}
	
	
	/**
	 * Fill in an element with the basic properties of a course, including
	 * its semester
	 * @param xml
	 * @param course
	 * @return
	 * @throws FinderException
	 */
	public static Element buildBasicPropNode(Principal p, Document xml, CourseLocal course) throws FinderException {
	    long courseID = course.getCourseID();
		//general course data
		Element xCourse = xml.createElement(TAG_COURSE);
		xCourse.setAttribute(A_COURSEID, Long.toString(courseID));
		xCourse.setAttribute(A_CODE, course.getCode());
		xCourse.setAttribute(A_DISPLAYEDCODE, course.getDisplayedCode());
		xCourse.setAttribute(A_COURSENAME, course.getName());
		SemesterLocal sem = database.semesterHome().findByPrimaryKey(new SemesterPK(course.getSemesterID()));
		xCourse.setAttribute(A_SEMESTER, sem.getSemesterName());
		
		if (course.getHasSection())
			xCourse.setAttribute(A_HASSECTION, "true");
		
		setCoursePrivileges(p, xCourse, course);
		return xCourse;
	}
	
	public static void addTotalScoreStats(CourseLocal course, Element xCourse) {
		//Float score = course.getMaxTotalScore();
		
		Float totalScore = course.getMaxTotalScore();
		Float highTotalScore = course.getHighTotalScore();
		Float meanTotalScore = course.getMeanTotalScore();
		Float medianTotalScore = course.getMedianTotalScore();
		Float stDev = course.getStDevTotalScore();
		
		if (totalScore == null || totalScore.toString().equals("NaN"))
			xCourse.setAttribute(A_MAXTOTALSCORE, "0.0");
		else xCourse.setAttribute(A_MAXTOTALSCORE, StringUtil.roundToOne(totalScore.toString()));
		
		if (highTotalScore == null || highTotalScore.toString().equals("NaN"))
			xCourse.setAttribute(A_HIGHTOTALSCORE, "0.0");
		else xCourse.setAttribute(A_HIGHTOTALSCORE, StringUtil.roundToOne(highTotalScore.toString()));
		
		if (meanTotalScore == null || meanTotalScore.toString().equals("NaN"))
			xCourse.setAttribute(A_MEANTOTALSCORE, "0.0");
		else xCourse.setAttribute(A_MEANTOTALSCORE, StringUtil.roundToOne(meanTotalScore.toString()));
		
		if (medianTotalScore == null || medianTotalScore.toString().equals("NaN"))
			xCourse.setAttribute(A_MEDIANTOTALSCORE, "0.0");
		else xCourse.setAttribute(A_MEDIANTOTALSCORE, StringUtil.roundToOne(medianTotalScore.toString()));
		
		if (stDev == null || stDev.toString().equals("NaN"))
			xCourse.setAttribute(A_STDEVTOTALSCORE, "0.0");
		else xCourse.setAttribute(A_STDEVTOTALSCORE, StringUtil.roundToOne(stDev.toString()));
		
		//xCourse.setAttribute(A_HIGHTOTALSCORE, score == null ? "0.0" : StringUtil.roundToOne(score.toString()));
		//xCourse.setAttribute(A_MEANTOTALSCORE, score == null ? "0.0" : StringUtil.roundToOne(score.toString()));
		//xCourse.setAttribute(A_MEDIANTOTALSCORE, score == null ? "0.0" : StringUtil.roundToOne(score.toString()));
		//xCourse.setAttribute(A_STDEVTOTALSCORE, score == null ? "0.0" : StringUtil.roundToOne(score.toString()));		
	}
	
	public static void addCourseProperties(CourseLocal course, Element xCourse) {
		xCourse.setAttribute(A_COURSEFROZEN, Boolean.toString(course.getFreezeCourse()));
		if (course.getShowFinalGrade()) {
		    xCourse.setAttribute(A_SHOWFINALGRADES, "true");
		}
		if (course.getShowTotalScores()) {
			xCourse.setAttribute(A_SHOWTOTALSCORES, "true");
		}
		if (course.getShowAssignWeights()) {
			xCourse.setAttribute(A_SHOWASSIGNWEIGHTS, "true");
		}
		if (course.getHasSection()) {
			xCourse.setAttribute(A_HASSECTION, "true");
		} 
	}
	
	public static void addAccessInfo(CourseLocal course, Element xCourse) {
		if (course.getCourseGuestAccess()) {
		    xCourse.setAttribute(A_COURSEGUESTACCESS, "true");
		}
		if (course.getCourseCCAccess()) {
		    xCourse.setAttribute(A_COURSECCACCESS, "true");
		}
		if (course.getAssignGuestAccess()) {
			xCourse.setAttribute(A_ASSIGNGUESTACCESS, "true");		    
		}
		if (course.getAssignCCAccess()) {
		    xCourse.setAttribute(A_ASSIGNCCACCESS, "true");
		}
		if (course.getAnnounceGuestAccess()) {
		    xCourse.setAttribute(A_ANNOUNCEGUESTACCESS, "true");
		}
		if (course.getAnnounceCCAccess()) {
		    xCourse.setAttribute(A_ANNOUNCECCACCESS, "true");
		}
		if (course.getSolutionGuestAccess()) {
		    xCourse.setAttribute(A_SOLUTIONGUESTACCESS, "true");
		}
		if (course.getSolutionCCAccess()) {
		    xCourse.setAttribute(A_SOLUTIONCCACCESS, "true");
		}
	}
	
	/**
	 * Generate an XML subtree with general info about the given Course
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @param course The course from which to generate the branch
	 * @return The course element of the tree, with general properties set
	 * @throws FinderException
	 */
	public static Element buildGeneralSubtree(Principal p, Document xml, CourseLocal course) throws FinderException {
	    Profiler.enterMethod("CourseXMLBuilder.buildGeneralSubtree","CourseID: " + course.getCourseID());
		StudentLocal student = null;
		long courseID = course.getCourseID();
		Element xCourse = buildBasicPropNode(p, xml, course);
		Profiler.enterMethod("StudentBean.findByPrimaryKey", "");
		try {
		    student = database.studentHome().findByPrimaryKey(new StudentPK(course.getCourseID(), p.getUserID()));
		    if (!student.getStatus().equals(StudentBean.ENROLLED)) student = null;
		} catch (Exception e) {}
		Profiler.exitMethod("StudentBean.findByPrimaryKey", "");
		if (student != null && student.getFinalGrade() != null) {
		    xCourse.setAttribute(A_FINALGRADE, student.getFinalGrade());
		}
		Element xDescription = xml.createElement(TAG_DESCRIPTION);
		xDescription.appendChild(xml.createTextNode(course.getDescription()));
		xCourse.appendChild(xDescription);
		//behavioral flags
		xCourse.setAttribute(A_SHOWGRADERID, Boolean.toString(course.getShowGraderNetID()));
		addCourseProperties(course, xCourse);
		addAccessInfo(course, xCourse);
		addTotalScoreStats(course, xCourse);
		// This info is rarely used, and only when the user is CMS Admin 
		if (p.isCMSAdmin()) {
			Collection enrolledStudents = database.studentHome().findByCourseIDSortByLastName(courseID);
			xCourse.setAttribute(A_ENROLLMENT, Integer.toString(enrolledStudents.size()));
		}
	    Profiler.exitMethod("CourseXMLBuilder.buildGeneralSubtree","CourseID: " + course.getCourseID());
	    return xCourse;
	}
	
	/**
	 * Generate an XML subtree with info on staff in the given Course
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @param course The course from which to generate the branch
	 * @return An XML element with a staff list for the given course
	 * @throws FinderException
	 */
	public static Element buildStaffListSubtree(Principal p, Document xml, CourseLocal course) throws FinderException {
	    Element xStaff= xml.createElement(TAG_STAFF);
		Iterator i= database.staffHome().findByCourseID(course.getCourseID()).iterator();
		while (i.hasNext()) {
			StaffLocal sd= (StaffLocal)i.next();
			Element xItem= xml.createElement(TAG_ITEM);
			String netID= sd.getNetID();
			xItem.setAttribute(A_NETID, netID);
			UserLocal u= database.userHome().findByUserID(netID);
			xItem.setAttribute(A_NAME, u.getFirstName() + " " + u.getLastName());
			if(sd.getAdminPriv()) xItem.setAttribute(A_ISADMIN, "");
			if(sd.getGroupsPriv()) xItem.setAttribute(A_ISGROUPS, "");
			if(sd.getGradesPriv()) xItem.setAttribute(A_ISGRADES, "");
			if(sd.getAssignmentsPriv()) xItem.setAttribute(A_ISASSIGN, "");
			if(sd.getCategoryPriv()) xItem.setAttribute(A_ISCATEGORY, "");
			xStaff.appendChild(xItem);
		}
		return xStaff;
	}
	
	
	/**
	 * Generate an XML subtree with a list of assignments for the given Course
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @param course The course from which to generate the branch
	 * @return An XML element with assignment info for the given course
	 * @throws FinderException
	 */
	public static Element buildAssignmentsSubtree(Principal p, Document xml, CourseLocal course) throws FinderException {
		Profiler.enterMethod("CourseXMLBuilder.buildAssignmentsSubtree", "");
		long courseID = course.getCourseID();
		boolean isstaff= p.isStaffInCourseByCourseID(courseID);
		boolean isstudent = p.isStudentInCourseByCourseID(courseID);
		Element xAssignments = xml.createElement(TAG_ASSIGNMENTS);
		
		Iterator i = database.assignmentHome().findByCourseID(courseID).iterator();
		Collection surveys = new ArrayList();
		Map gradeMap = database.getGradeMap(p.getUserID(), courseID);
		int count = 1;
		while (i.hasNext()) {
			AssignmentLocal assignment = (AssignmentLocal)i.next();
			boolean show= !AssignmentBean.HIDDEN.equals(assignment.getStatus());
			if(assignment.getType() != AssignmentBean.SURVEY && (isstaff || show)) {
				xAssignments.appendChild(
					AssignmentXMLBuilder.buildGeneralSubtree(xml, assignment,gradeMap));
			}
		}
		
		// xAssignments.appendChild(AssignmentXMLBuilder.buildSurveySubtree(xml, surveys));
		
		
		Profiler.exitMethod("CourseXMLBuilder.buildAssignmentsSubtree", "");
		return xAssignments;
	}
	
	public static Element buildHiddenAssignsSubtree(Document xml, long courseID) throws FinderException {
	    Element xHiddenAssigns = xml.createElement(XMLBuilder.TAG_HIDDENASSIGNMENTS);
	    Iterator i = database.assignmentHome().findHiddenByCourseID(courseID).iterator();
        while (i.hasNext()) {
	        AssignmentLocal assign = (AssignmentLocal) i.next();
	        Element xHiddenAssign = xml.createElement(XMLBuilder.TAG_HIDDENASSIGNMENT);
	        xHiddenAssign.setAttribute(XMLBuilder.A_ASSIGNID, String.valueOf(assign.getAssignmentID()));
	        xHiddenAssign.setAttribute(XMLBuilder.A_ASSIGNNAME, assign.getName());
	        xHiddenAssign.setAttribute(XMLBuilder.A_NAMESHORT, assign.getNameShort());
	        xHiddenAssigns.appendChild(xHiddenAssign);
        }
	    return xHiddenAssigns;
	}
	
	/**
	 * Generate an XML subtree with info on all visible and hidden categories in the given Course
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @param course The course from which to generate the branch
	 * @return A TAG_CATEGORIES element with category data for the given course, in the form of child nodes
	 * @throws FinderException
	 */
	public static Element buildCategoriesSubtree(Principal p, Document xml, CourseLocal course) throws FinderException {
		Element xCategories = xml.createElement(TAG_CATEGORIES);
		long current = System.currentTimeMillis();
		CoursewideCategoryDataProvider courseCategoryProvider = 
			new CoursewideCategoryDataProvider(course.getCtgColumns(p), course.getCtgRows(p), 
															course.getCtgContents(p), course.getCtgFiles(p));
		Collection ctgs = course.getCategories(p);
		Iterator catIter = course.getCategories(p).iterator();
		while(catIter.hasNext())
		{
			CategoryData ctg = (CategoryData)catIter.next();
			courseCategoryProvider.setCategoryID(ctg.getCategoryID());
			Element xCategory = CategoryXMLBuilder.buildShortSubtree(xml, ctg);
			//build and append column and row data
			Element xColumns = CategoryXMLBuilder.buildNonremovedColumnListsSubtree(xml, courseCategoryProvider);
			Element xVisibleRows = xml.createElement(TAG_VISIBLEROWS),
				xHiddenRows = xml.createElement(TAG_HIDDENROWS);
			CategoryXMLBuilder.buildRowListSubtrees(xml, courseCategoryProvider, xVisibleRows, xHiddenRows);
			//we only want nonhidden data
			xCategory.appendChild(xColumns);
			xCategory.appendChild(xVisibleRows);
			xCategories.appendChild(xCategory);
		}
		System.out.println("Time to Build: " + Long.toString(System.currentTimeMillis() - current) );
		return xCategories;
	}
	
	/**
	 * Generate an XML subtree with info on files provided for assignments in the given Course
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @param course The course from which to generate the branch
	 * @return An XML element with assignment file info for the given course
	 * @throws FinderException
	 */
	public static Element buildAssignmentFilesSubtree(Principal p, Document xml, CourseLocal course) throws FinderException
	{
		Element xItems= xml.createElement(TAG_ITEMS);
		Iterator i= course.getAllAssignmentFiles().iterator();
		while (i.hasNext()) {
			Element xFile= xml.createElement(TAG_ITEM);
			xItems.appendChild(xFile);
			AssignmentFileData data= (AssignmentFileData)i.next();
			xFile.setAttribute(A_NAME, data.getFileName());
			//TODO Fix after AssignmentFiles are implemented fully
			//xFile.setAttribute(A_DATE, Util.formatDate(data.getDate(), Util.SMALL));
			//xFile.setAttribute(A_ID, Long.toString(data.getAssignmentFileID()));
		}
		return xItems;
	}
	
}
