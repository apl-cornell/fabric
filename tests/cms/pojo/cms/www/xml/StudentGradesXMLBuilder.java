package cms.www.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.FinderException;

import org.w3c.dom.*;

import cms.www.util.Profiler;
import cms.www.util.StringUtil;
import edu.cornell.csuglab.cms.author.Principal;
import edu.cornell.csuglab.cms.base.*;

/**
 * @author jfg32
 */
public class StudentGradesXMLBuilder extends XMLBuilder {

    public static void buildStudentGradesTree(Principal p, Document xml, String netID, long courseID) throws FinderException {
		Profiler.enterMethod("StudentGradesXMLBuilder.buildStudentGradesTree", "CourseID: " + courseID);
        Element root = (Element) xml.getFirstChild();
        Element xUser = xml.createElement(TAG_GRADESTUDENT);
        xUser.setAttribute(A_NETID, netID);
        UserLocal user = database.userHome().findByUserID(netID);
        xUser.setAttribute(A_FIRSTNAME, user.getFirstName());
        xUser.setAttribute(A_LASTNAME, user.getLastName());
        root.appendChild(xUser);
        List groupIDs = addGroups(netID, xml, courseID);
        Map subNames = database.getSubmissionNameMapByCourse(courseID);
        addGrades(p, xml, netID, courseID);
        GradingXMLBuilder.addSubmittedFiles(xml, groupIDs, subNames);
        Map groupidsMap = database.getCommentFileGroupIDMapByCourse(courseID);
        Map requestIDs = database.getCommentFileRequestIDMapByCourse(courseID);
        Map subProbNames = database.getSubProblemNameMapByCourse(courseID);
        GradingXMLBuilder.addRegradeInfo(xml,groupIDs, subProbNames, requestIDs, groupidsMap);
        GradingXMLBuilder.addGradeLogs(xml, courseID, groupIDs);
		Profiler.exitMethod("StudentGradesXMLBuilder.buildStudentGradesTree", "CourseID: " + courseID);
    }
    
    public static void addAssignments(Document xml, long courseID, Map assignToGroup) throws FinderException {
        Profiler.enterMethod("StudentGradesXMLBuilder.addAssignments", "");
    	Element root = (Element) xml.getFirstChild();
        Collection assigns = database.assignmentHome().findByCourseID(courseID);
        long[] assignIDs = new long[assigns.size()];
        Iterator i = assigns.iterator();
        int count = 0;
        Collection sps = database.subProblemHome().findByAssignmentIDs(assignIDs);
        while (i.hasNext())  {
            AssignmentLocal assign = (AssignmentLocal) i.next();
            
            if (assign.getType() != AssignmentBean.SURVEY) {
            	assignIDs[count++] = assign.getAssignmentID();
            	Long groupID = (Long)assignToGroup.get(new Long(assign.getAssignmentID()));
            	Element xGroup = (Element) root.getElementsByTagNameNS(TAG_GROUP + (groupID == null ? "00" : groupID.toString()), TAG_GROUP).item(0);
            	if (xGroup != null) {
            		xGroup.setAttribute(A_ASSIGNNAME, assign.getName());
            		xGroup.setAttribute(A_NAMESHORT, assign.getNameShort());
            		xGroup.setAttribute(A_MAXSCORE, StringUtil.trimTrailingZero(String.valueOf(assign.getMaxScore())));
            		Element xTotalProb = xml.createElement(TAG_TOTALPROBLEM);
            		xTotalProb.setAttribute(A_SUBPROBID, "0");
            		xTotalProb.setAttribute(A_SUBPROBNAME, "Total");
            		xTotalProb.setAttribute(A_MAXSCORE, StringUtil.trimTrailingZero(String.valueOf(assign.getMaxScore())));
            		xGroup.appendChild(xTotalProb);
            	}
            }
            
        }
        Iterator subProbs = database.subProblemHome().findByAssignmentIDs(assignIDs).iterator();
        while (subProbs.hasNext()) {
            SubProblemLocal subProb = (SubProblemLocal) subProbs.next();
            Long groupID = (Long) assignToGroup.get(new Long(subProb.getAssignmentID()));
            Element xGroup = (Element) root.getElementsByTagNameNS(TAG_GROUP + (groupID == null ? "00" : groupID.toString()), TAG_GROUP).item(0);
            if (xGroup != null) {
                Element xSubProb = xml.createElementNS(TAG_SUBPROBLEM + subProb.getSubProblemID(), TAG_SUBPROBLEM);
                xSubProb.setAttribute(A_SUBPROBID, String.valueOf(subProb.getSubProblemID()));
                xSubProb.setAttribute(A_SUBPROBNAME, subProb.getSubProblemName());
                xSubProb.setAttribute(A_MAXSCORE, StringUtil.trimTrailingZero(String.valueOf(subProb.getMaxScore())));
                xGroup.appendChild(xSubProb);
            }
        }
        Profiler.exitMethod("StudentGradesXMLBuilder.addAssignments", "");
    }
    
    /**
     * 
     * @param netid
     * @param xml
     * @param courseID
     * @return A List of Longs (group IDs)
     * @throws FinderException
     */
    public static List addGroups(String netid, Document xml, long courseID) throws FinderException {
    	Profiler.enterMethod("StudentGradesXMLBuilder.addGroups", "");
        Element root = (Element) xml.getFirstChild();
        Collection gr = database.groupHome().findByNetIDCourseID(netid, courseID);
        Iterator groups = gr.iterator();
        List groupIDs = new ArrayList();
        Iterator members = database.groupMemberHome().findByNetIDCourseID(netid, courseID).iterator();
        Map assignToGroup = new HashMap();
        int count = 0;
        while (groups.hasNext()) {
            GroupLocal g = (GroupLocal) groups.next();
            AssignmentLocal assign = database.assignmentHome().findByAssignmentID(g.getAssignmentID());
            
            if (assign.getType() != AssignmentBean.SURVEY) {
            	groupIDs.add(new Long(g.getGroupID()));
            	Element xGroup = xml.createElementNS(TAG_GROUP + g.getGroupID(), TAG_GROUP);
            	xGroup.setAttribute(A_GROUPID, String.valueOf(g.getGroupID()));
            	xGroup.setAttribute(A_ASSIGNID, String.valueOf(g.getAssignmentID()));
            	assignToGroup.put(new Long(g.getAssignmentID()), new Long(g.getGroupID()));
            	root.appendChild(xGroup);
            }
        }
        while (members.hasNext()) {
            GroupMemberLocal m = (GroupMemberLocal) members.next();
            Element xGroup = (Element) root.getElementsByTagNameNS(TAG_GROUP + m.getGroupID(), TAG_GROUP).item(0);
            if (xGroup != null) {
                Element xMember = xml.createElementNS(TAG_MEMBER + m.getNetID(), TAG_MEMBER);
                xMember.setAttribute(A_NETID, m.getNetID());
                xGroup.appendChild(xMember);
            }
        }
        GroupXMLBuilder.addGroupMemberNames(xml, courseID);
        addAssignments(xml, courseID, assignToGroup);
        addSubmissions(xml, courseID, assignToGroup);
        Profiler.exitMethod("StudentGradesXMLBuilder.addGroups", "");
        return groupIDs;
    }
    
    public static void addGrades(Principal p, Document xml, String netid, long courseID) throws FinderException {
        Profiler.enterMethod("StudentGradesXMLBuilder.addGrades", "");
    	Element root = (Element) xml.getFirstChild();
        Iterator grades = database.gradeHome().findRecentByNetIDCourseID(netid, courseID, p.isAdminPrivByCourseID(courseID), p.getUserID()).iterator();
        Map groups = database.getGroupIDMap(netid);
        while (grades.hasNext()) {
            GradeLocal grade = (GradeLocal) grades.next();
            long assignID = grade.getAssignmentID();
            long groupID = ((Long) groups.get(new Long(grade.getAssignmentID()))).longValue();
            Element xGroup = (Element) root.getElementsByTagNameNS(TAG_GROUP + groupID, TAG_GROUP).item(0);
            if (xGroup != null) {
                Element xMember = (Element) xGroup.getElementsByTagNameNS(TAG_MEMBER + grade.getNetID(), TAG_MEMBER).item(0);
                if (xMember != null) {
                    Element xGrade = xml.createElementNS(TAG_GRADE + grade.getSubProblemID(), TAG_GRADE);
                    xGrade.setAttribute(A_SCORE, StringUtil.trimTrailingZero(String.valueOf(grade.getGrade())));
                    xGrade.setAttribute(A_SUBPROBID, String.valueOf(grade.getSubProblemID()));
                    xMember.appendChild(xGrade);
                }
            }
        }
        Profiler.exitMethod("StudentGradesXMLBuilder.addGrades", "");
    }
    
    /**
     * find the element in xml corresponding to each group this student has been in for the given course,
     * and for each group, add one child node for each of its submissions
     * 
     * @param assignToGroup A map from assignment IDs in the given course to this student's group ID
     * for that assignment
     */
    public static void addSubmissions(Document xml, long courseID, Map assignToGroup) throws FinderException {
        Profiler.enterMethod("StudentGradesXMLBuilder.addSubmissions", "");
    	Element root = (Element) xml.getFirstChild();
        Iterator submissions = database.requiredSubmissionHome().findByCourseID(courseID).iterator();
        while (submissions.hasNext()) {
            RequiredSubmissionLocal submission = (RequiredSubmissionLocal) submissions.next();
            long groupID = ((Long) assignToGroup.get(new Long(submission.getAssignmentID()))).longValue();
            Element xGroup = (Element) root.getElementsByTagNameNS(TAG_GROUP + groupID, TAG_GROUP).item(0);
            if (xGroup != null) {
                Element xSubmission = xml.createElement(TAG_SUBMISSION);
                xSubmission.setAttribute(A_ID, String.valueOf(submission.getSubmissionID()));
                xSubmission.setAttribute(A_NAME, submission.getSubmissionName());
                xSubmission.setAttribute(A_GROUPID, String.valueOf(groupID));
                xSubmission.setAttribute(A_ASSIGNID, String.valueOf(submission.getAssignmentID()));
                xGroup.appendChild(xSubmission);
            }
        }
        Profiler.exitMethod("StudentGradesXMLBuilder.addSubmissions", "");
     }
    
}
