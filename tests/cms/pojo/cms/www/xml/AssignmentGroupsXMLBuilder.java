package cms.www.xml;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.FinderException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;
import cms.www.util.StringUtil;

import edu.cornell.csuglab.cms.author.Principal;
import edu.cornell.csuglab.cms.base.*;


/*
 * This class is responsible for building the XML document used to render the
 * staff Groups view for any assignment.  Information on this page may include
 * a full listing of groups and their members, current grading assignments,
 * any available grades, and information about extensions.
 */
public class AssignmentGroupsXMLBuilder extends XMLBuilder
{
	/**
	 * Build the complete XML output
	 */
	public static void buildGroupGradingPage(Principal p, long assignmentID, Document xml) throws FinderException {
		Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildGroupGradingPage", "");
		Element root = (Element) xml.getFirstChild();
		Element groupsNode = xml.createElement(TAG_GROUPS);
		Element subProbsNode = xml.createElement(TAG_SUBPROBS);
		AssignmentLocal assign = database.assignmentHome().findByAssignmentID(assignmentID);
		long courseID = assign.getCourseID();
		boolean adminPriv = false, gradePriv = false, groupsPriv = false, assignedGraders = false;
		adminPriv = p.isAdminPrivByCourseID(courseID);
		gradePriv = p.isGradesPrivByCourseID(courseID);
		groupsPriv = p.isGroupsPrivByCourseID(courseID);
		assignedGraders = assign.getAssignedGraders();
		Profiler.enterMethod("RootBean.getStaffFirstLastNameMap", "");
		Map staffMap = database.getStaffFirstLastNameMap(assign.getCourseID());
		Profiler.exitMethod("RootBean.getStaffFirstLastNameMap", "");
		// Can this user view all entries on this page, or should they be restricted?
		boolean fullAccess = adminPriv || groupsPriv || (!assignedGraders && gradePriv);
		buildGroups(p, assign, fullAccess, xml, groupsNode);
		Map subProbScores = buildSubproblems(assignmentID, xml, subProbsNode);
		buildAssignedGraders(p, assign, xml, groupsNode, staffMap);
		buildGroupGrades(p, assignmentID, xml, groupsNode, adminPriv, subProbScores);
		buildRegrades(assignmentID, xml, groupsNode);
		buildStaffGraders(courseID, xml, staffMap);
		root.appendChild(groupsNode);
		root.appendChild(subProbsNode);
		Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildGroupGradingPage", "");
	}

	/**
	 * Add nodes to the XML document for each group with active members and
	 * subnodes under each group for the different group members
	 * @param fullaccess Whether the principal is allowed to see all entries
	 * (basically, whether the principal has at least groups privilege)
	 */
	public static void buildGroups(Principal p, AssignmentLocal assign, boolean fullAccess, Document xml, Element groupsNode) throws FinderException {
		Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildGroups", "");
		int submissions = 0, partial = 0, complete = 0;
		long assignmentID = assign.getAssignmentID();
		int numOfAssignedFiles = assign.getNumOfAssignedFiles();
		Iterator groupmems = null;
		if (fullAccess) {
			groupmems = database.groupMemberHome().findActiveByAssignmentID(assignmentID).iterator();
		} else {
			groupmems = database.groupMemberHome().findAssignedActiveByAssignmentID(p.getUserID(), assignmentID).iterator();
		}
		Set lateGroups = database.getLateGroups(assignmentID);
		Map groupsMap = database.getGroupsMap(assignmentID);
		Map nameMap = database.getFirstLastNameMap(assign.getCourseID());
		while (groupmems.hasNext()) {
			GroupMemberLocal groupmem = (GroupMemberLocal) groupmems.next();
			GroupLocal group = (GroupLocal) groupsMap.get(new Long(groupmem.getGroupID()));
			String[] name = (String[]) nameMap.get(groupmem.getNetID());
			String netid = groupmem.getNetID();
			Element xGroup = (Element) groupsNode.getElementsByTagNameNS(TAG_GROUP + groupmem.getGroupID(), TAG_GROUP).item(0);
			if (xGroup == null) {
				long groupID = groupmem.getGroupID();
				// Count submissions only the first time we see a group
				if (numOfAssignedFiles == 0 || group.getRemainingSubmissions() < numOfAssignedFiles) {
					submissions++;
					if (group.getRemainingSubmissions() == 0) {
						complete++;
					} else {
						partial++;
					}
				}
				xGroup = xml.createElementNS(XMLBuilder.TAG_GROUP + groupID, XMLBuilder.TAG_GROUP);
				xGroup.setAttribute(XMLBuilder.A_GROUPID, String.valueOf(groupID));
				xGroup.setAttribute(XMLBuilder.A_REMAININGSUBS, String.valueOf(group.getRemainingSubmissions()));
				if (group.getExtension() != null) {
				    xGroup.setAttribute(XMLBuilder.A_EXTENSION, DateTimeUtil.DATE.format(group.getExtension()));
				    xGroup.setAttribute(XMLBuilder.A_EXTTIME, DateTimeUtil.TIME.format(group.getExtension()));
				    xGroup.setAttribute(XMLBuilder.A_EXTVAL, String.valueOf(group.getExtension().getTime()));
				}
				groupsNode.appendChild(xGroup);
				if (lateGroups.contains(new Long(groupID))) {
					xGroup.setAttribute(XMLBuilder.A_LATESUBMISSION, "true");
				}
			}
			StudentLocal student = database.studentHome().findByGroupIDNetID(group.getGroupID(), netid);
			String section = student.getSection();
			
			Element xGroupMember = xml.createElement(XMLBuilder.TAG_MEMBER);
			xGroupMember.setAttribute(XMLBuilder.A_NETID, netid);
			xGroupMember.setAttribute(XMLBuilder.A_FIRSTNAME, name[0]);
			xGroupMember.setAttribute(XMLBuilder.A_LASTNAME, name[1]);
			xGroupMember.setAttribute(XMLBuilder.A_SECTION, section);
			xGroup.appendChild(xGroupMember);
		}
		// Add submissions counts to the group node
		groupsNode.setAttribute(XMLBuilder.A_SUBMISSIONCOUNT, String.valueOf(submissions));
		groupsNode.setAttribute(XMLBuilder.A_PARTIAL, String.valueOf(partial));
		groupsNode.setAttribute(XMLBuilder.A_COMPLETE, String.valueOf(complete));
		Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildGroups", "");
	}
	
	/**
	 * Add information about the non-hidden subproblems for this assignment to 
	 * the given subProbsNode element.  
	 * Returns a mapping from SubProblemID (Long) -> MaxProblemScore (Float).
	 */
	public static Map buildSubproblems(long assignmentID, Document xml, Element subProbsNode) throws  FinderException {
		Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildSubproblems", "");
		HashMap result = new HashMap();
		
		// sort the subproblems by their order
		//TreeMap orderToSubProblem = new TreeMap();
		Iterator subProbs = database.subProblemHome().findByAssignmentID(assignmentID).iterator();
		/*while(subProbs.hasNext()) {
			SubProblemLocal sp = (SubProblemLocal) subProbs.next();
			orderToSubProblem.put(new Integer(sp.getOrder()), sp);
		}
		
		subProbs = orderToSubProblem.keySet().iterator();*/
		while (subProbs.hasNext()) {
			SubProblemLocal subProb = (SubProblemLocal) subProbs.next();
			result.put(new Long(subProb.getSubProblemID()), new Float(subProb.getMaxScore()));
			Element xSubProb = xml.createElement(TAG_SUBPROBLEM);
			xSubProb.setAttribute(XMLBuilder.A_SUBPROBID, String.valueOf(subProb.getSubProblemID()));
			xSubProb.setAttribute(XMLBuilder.A_NAME, subProb.getSubProblemName());
			xSubProb.setAttribute(XMLBuilder.A_SCORE, StringUtil.roundToOne(String.valueOf(subProb.getMaxScore())));
			xSubProb.setAttribute(XMLBuilder.A_ORDER, Integer.toString(subProb.getOrder()));
			xSubProb.setAttribute(XMLBuilder.A_HIDDEN, Boolean.toString(subProb.getHidden()));
			subProbsNode.appendChild(xSubProb);
		}
		Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildSubproblems", "");
		return result;
	}
	
	public static void buildAssignedGraders(Principal p, AssignmentLocal assign, Document xml, Element groupsNode, Map staffMap) throws  FinderException {
		Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildAssignedGraders", "");
		Iterator assignedTos = database.groupAssignedToHome().findByAssignmentID(assign.getAssignmentID()).iterator();
		while (assignedTos.hasNext()) {
			GroupAssignedToLocal assignedTo = (GroupAssignedToLocal) assignedTos.next();
			Element xGroup = (Element) groupsNode.getElementsByTagNameNS(TAG_GROUP + assignedTo.getGroupID(), TAG_GROUP).item(0);
			if (xGroup != null && assignedTo.getNetID() != null) {
				Element xAssignedTo = xml.createElementNS(TAG_ASSIGNEDTO + assignedTo.getSubProblemID(), TAG_ASSIGNEDTO);
			    String[] name = (String[]) staffMap.get(assignedTo.getNetID());
			    // Set this attribute if the Principal is assigned to grade this group for any subproblem
				if (p.getUserID().equals(assignedTo.getNetID())) {
			        xGroup.setAttribute(A_CANGRADE, "true");
			    }
				xAssignedTo.setAttribute(A_SUBPROBID, String.valueOf(assignedTo.getSubProblemID()));
				xAssignedTo.setAttribute(A_NETID, assignedTo.getNetID());
				xAssignedTo.setAttribute(A_FIRSTNAME, name[0]);
				xAssignedTo.setAttribute(A_LASTNAME, name[1]);
				xGroup.appendChild(xAssignedTo);
			}
		}
		Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildAssignedGraders", "");
	}
	
	public static void buildGroupGrades(Principal p, long assignmentID, Document xml, Element groupsNode, boolean adminPriv, Map subProbScores) throws  FinderException {
		Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildGroupGrades", "");
		Collection grades = database.groupGradeHome().findByGraderAssignmentID(p.getUserID(), assignmentID, adminPriv, subProbScores.size());
		Iterator groupGrades = grades.iterator();
		AssignmentLocal assignment = database.assignmentHome().findByPrimaryKey(new AssignmentPK(assignmentID));
		Collection overMaxScore = new ArrayList();
		while (groupGrades.hasNext()) {
			GroupGradeLocal groupGrade = (GroupGradeLocal) groupGrades.next();
			Element xGroup = (Element) groupsNode.getElementsByTagNameNS(TAG_GROUP + groupGrade.getGroupID(), TAG_GROUP).item(0);
			if (xGroup != null) {
				Element xGrade = xml.createElementNS(TAG_GRADE + groupGrade.getSubProblemID(), TAG_GRADE);
				xGrade.setAttribute(A_SUBPROBID, String.valueOf(groupGrade.getSubProblemID()));
				xGrade.setAttribute(A_SCORE, StringUtil.roundToOne(String.valueOf(groupGrade.getScore())));
				if (groupGrade.getAveraged()) {
					xGrade.setAttribute(A_ISAVERAGE, "true");
				}
				if (groupGrade.getSubProblemID() != 0) //check max subproblem grade
				{
					long subID = groupGrade.getSubProblemID();
					float groupScore = groupGrade.getScore();
				    if (((Float) subProbScores.get(new Long(subID))).floatValue() < groupScore) {
				        xGrade.setAttribute(A_OVERMAX, "true");
				        overMaxScore.add(new Long(groupGrade.getGroupID()));
				    }
				}
				else //check max assignment grade
				{
					if(groupGrade.getScore() > assignment.getMaxScore())
						xGrade.setAttribute(A_OVERMAX, "true");
				}
				xGroup.appendChild(xGrade);
			}
		}
		// Flag any groups' total scores which had over max score on at least one subproblem
		Iterator i = overMaxScore.iterator();
		while (i.hasNext()) {
		    long groupID = ((Long) i.next()).longValue();
		    Element xGroup = (Element) groupsNode.getElementsByTagNameNS(TAG_GROUP + groupID, TAG_GROUP).item(0);
		    if (xGroup != null) {
		        Element xGrade = (Element) xGroup.getElementsByTagNameNS(TAG_GRADE + 0, TAG_GRADE).item(0);
		        if (xGrade != null) {
		            xGrade.setAttribute(A_OVERMAX, "true");
		        }
		    }
		}
		Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildGroupGrades", "");
	}
	
	public static void buildRegrades(long assignmentID, Document xml, Element groupsNode) throws FinderException {
		Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildRegrades", "");
		Iterator regrades = database.regradeRequestHome().findByAssignmentID(assignmentID).iterator();
		while (regrades.hasNext()) {
			RegradeRequestLocal regrade = (RegradeRequestLocal) regrades.next();
			Element xGroup = (Element) groupsNode.getElementsByTagNameNS(TAG_GROUP + regrade.getGroupID(), TAG_GROUP).item(0);
			Element xRegrade = (Element) (xGroup == null ? null : xGroup.getElementsByTagName(TAG_REGRADE).item(0));
			if (xGroup != null && xRegrade == null) {
				xRegrade = xml.createElement(TAG_REGRADE);
				xRegrade.setAttribute(A_STATUS, regrade.getStatus());
				xGroup.appendChild(xRegrade);
			}
		}
		Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildRegrades","");;
	}
	
	public static void buildStaffGraders(long courseID, Document xml, Map staffMap) throws FinderException {
		Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildStaffGraders", "");
		Iterator staff = database.staffHome().findByCourseID(courseID).iterator();
		Element root = (Element) xml.getFirstChild();
		while (staff.hasNext()) {
			StaffLocal staffmem = (StaffLocal) staff.next();
			if (staffmem.getAdminPriv() || staffmem.getGradesPriv()) {
				Element xStaff = xml.createElement(TAG_GRADER);
				String[] name = (String[]) staffMap.get(staffmem.getNetID());
			    xStaff.setAttribute(XMLBuilder.A_NETID, staffmem.getNetID());
			    xStaff.setAttribute(XMLBuilder.A_FIRSTNAME, name[0]);
			    xStaff.setAttribute(XMLBuilder.A_LASTNAME, name[1]);
			    root.appendChild(xStaff);
			}
		}
		Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildStaffGraders", "");
	}
}
