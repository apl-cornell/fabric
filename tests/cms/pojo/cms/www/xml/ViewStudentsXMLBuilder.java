package cms.www.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.ejb.FinderException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cms.www.util.Profiler;
import cms.www.util.StringUtil;
import cms.www.util.Util;

import edu.cornell.csuglab.cms.base.*;


public class ViewStudentsXMLBuilder extends XMLBuilder {

	
	public static void buildSelectedStudentList(long courseID, Document xml, Element studentsNode, Collection selectedIDs) throws  FinderException {
		Iterator students = database.studentHome().findAllByCourseID(courseID).iterator();
		Map names = database.getFirstLastNameMap(courseID);
		Map groups = database.getGroupIDMapByCourse(courseID);
		while (students.hasNext()) {
			StudentLocal student = (StudentLocal) students.next();
			String userID = student.getUserID();
			String[] name = (String[]) names.get(userID);
			Element xStudent = xml.createElement(userID);
			Float totalScore = student.getTotalScore();
			xStudent.setAttribute(A_TOTALSCORE, (totalScore == null ? "" : StringUtil.roundToOne(String.valueOf(totalScore.floatValue()))));
			xStudent.setAttribute(A_FINALGRADE, (student.getFinalGrade() == null ? "" : student.getFinalGrade()));
			xStudent.setAttribute(A_FIRSTNAME, name[0]);
			xStudent.setAttribute(A_LASTNAME, name[1]);
			xStudent.setAttribute(A_NETID, userID);
			if (selectedIDs.contains(userID)) {
				xStudent.setAttribute(A_ENROLLED, StudentBean.ENROLLED);
				studentsNode.appendChild(xStudent);
			}
		}
	}
	
	public static void buildStudentList(long courseID, Document xml, Element studentsNode, Element assignsNode) throws  FinderException {
		Iterator students = database.studentHome().findAllByCourseID(courseID).iterator();
		Map names = database.getFirstLastNameMap(courseID);
		Map groups = database.getGroupIDMapByCourse(courseID);
		Collection assigns = database.assignmentHome().findByCourseID(courseID);
		LinkedList nonenrolled = new LinkedList();
		boolean seenAssigns = false;
		while (students.hasNext()) {
			StudentLocal student = (StudentLocal) students.next();
			String userID = student.getUserID();
			String[] name = (String[]) names.get(userID);
			String section = student.getSection();
			Element xStudent = xml.createElement(userID);
			Float totalScore = student.getTotalScore();
			xStudent.setAttribute(A_TOTALSCORE, (totalScore == null ? "" : StringUtil.roundToOne(String.valueOf(totalScore.floatValue()))));
			xStudent.setAttribute(A_FINALGRADE, (student.getFinalGrade() == null ? "" : student.getFinalGrade()));
			xStudent.setAttribute(A_FIRSTNAME, name[0]);
			xStudent.setAttribute(A_LASTNAME, name[1]);
			xStudent.setAttribute(A_NETID, userID);
			xStudent.setAttribute(A_SECTION, section);
			if (student.getStatus().equals(StudentBean.ENROLLED)) {
				xStudent.setAttribute(A_ENROLLED, StudentBean.ENROLLED);
				studentsNode.appendChild(xStudent);
			} else {
			    xStudent.setAttribute(A_ENROLLED, StudentBean.DROPPED);
				nonenrolled.addLast(xStudent);
			}
			seenAssigns = true;
		}
		Iterator i = assigns.iterator();
		// Add a node under each student for each assignment for assignment grades
		while (i.hasNext()) {
			AssignmentLocal assign = (AssignmentLocal) i.next();
			long assignID = assign.getAssignmentID();
			NodeList studs = studentsNode.getChildNodes();
			for (int j=0; j < studs.getLength(); j++) {
				Element xStudent = (Element) studs.item(j);
				String userID = xStudent.getAttribute(A_NETID);
				if (!seenAssigns) {
					Element xAssign = xml.createElement(A_ID + assignID);
					xAssign.setAttribute(A_NAMESHORT, assign.getNameShort());
					assignsNode.appendChild(xAssign);
				}
		        Element xGrade = xml.createElement(A_ID + assignID);
		        xGrade.setAttribute(A_ASSIGNID, String.valueOf(assignID));
		        xStudent.appendChild(xGrade);
				Long groupID = (Long) groups.get(userID + "_" + assignID);
				if (groupID != null) {
			        xGrade.setAttribute(A_GROUPID, groupID.toString());
				}
			}
		}
		while (nonenrolled.size() > 0) {
			Element xStudent = (Element) nonenrolled.removeFirst();
			studentsNode.appendChild(xStudent);
		}
	}
	
	public static void buildAssignmentGrades(long courseID, Element studentsNode) throws  FinderException {
		Iterator grades = database.gradeHome().findTotalsByCourseID(courseID).iterator();
		while (grades.hasNext()) {
			GradeLocal grade = (GradeLocal) grades.next();
			Element xStudent = (Element) studentsNode.getElementsByTagName(grade.getNetID()).item(0);
			if (xStudent != null) {
			    Element xGrade = (Element) xStudent.getElementsByTagName(A_ID + grade.getAssignmentID()).item(0);
			    if (xGrade != null) {
			    	xGrade.setAttribute(A_SCORE, StringUtil.roundToOne(String.valueOf(grade.getGrade())));
			    }
			}
		}
	}
	
	public static void buildRegrades(long courseID, Element studentsNode) throws  FinderException {
		Iterator regrades = database.regradeRequestHome().findByCourseID(courseID).iterator();
		Map groups = database.getGroupMembersMap(courseID);
		Map assigns = database.getAssignmentIDMap(courseID);
		while (regrades.hasNext()) {
			RegradeRequestLocal regrade = (RegradeRequestLocal) regrades.next();
			ArrayList groupmems = (ArrayList) groups.get(new Long(regrade.getGroupID()));
			if (groupmems != null) {
				for (int i=0; i < groupmems.size(); i++) {
					String netID = (String) groupmems.get(i);
					Element xStudent = (Element) studentsNode.getElementsByTagName(netID).item(0);
					if (xStudent != null) {
						long assignID = ((Long) assigns.get(new Long(regrade.getGroupID()))).longValue();
						Element xGrade = (Element) xStudent.getElementsByTagName(A_ID + assignID).item(0);
						if (xGrade != null) {
				            if (regrade.getStatus().equals(RegradeRequestBean.PENDING) || !xGrade.hasAttribute(XMLBuilder.A_REGRADE)) {
				                xGrade.setAttribute(XMLBuilder.A_REGRADE, regrade.getStatus());
				            }
						}
					}
				}
			}
		}
	}
	
	/*
	 * "overmax" refers to scores above the maximum
	 */
	public static void markOverMaxScores(long courseID, Element studentsNode) throws  FinderException {
		Iterator overgrades = database.gradeHome().findOverMaxByCourseID(courseID).iterator();
		while (overgrades.hasNext()) {
			GradeLocal grade = (GradeLocal) overgrades.next();
			Element xStudent = (Element) studentsNode.getElementsByTagName(grade.getNetID()).item(0);
			if (xStudent != null) {
				Element xGrade = (Element) xStudent.getElementsByTagName(A_ID + grade.getAssignmentID()).item(0);
				if (xGrade != null) {
					xGrade.setAttribute(A_OVERMAX, "true");
				}
			}
		}
	}
	
	public static void buildStudentsPage(long courseID, Document xml) throws  FinderException {
		Profiler.enterMethod("ViewStudentsXMLBuilder.buildStudentsPage", "CourseID: " + courseID);
		Element root = (Element) xml.getFirstChild();
		Element studentsNode = xml.createElement(TAG_STUDENTS);
		Element assignsNode = xml.createElement(TAG_STUDENTASSIGNS);
		buildStudentList(courseID, xml, studentsNode, assignsNode);
		buildAssignmentGrades(courseID, studentsNode);
		buildRegrades(courseID, studentsNode);
		markOverMaxScores(courseID, studentsNode);
		root.appendChild(studentsNode);
		root.appendChild(assignsNode);
		Profiler.exitMethod("ViewStudentsXMLBuilder.buildStudentsPage", "CourseID: " + courseID);
	}
	
}
