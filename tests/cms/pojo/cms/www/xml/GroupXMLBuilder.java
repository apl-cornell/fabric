package cms.www.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cms.www.util.DateTimeUtil;
import cms.www.util.FileUtil;
import cms.www.util.Profiler;

import cms.auth.Principal;
import cms.model.*;

/**
 * Builds an XML subtree with information about a given group 
 * (which exists only for a specific assignment) 
 * 
 * Created 3 / 29 / 05
 * @author Evan
 */
public class GroupXMLBuilder extends XMLBuilder
{
	/**
	 * Generate an XML subtree with all known info on the given Group
	 * @param p The Principal to generate the page for. We assume p is a member of the group
	 * or has staff privileges for the course the group is in
	 * @param xml The Document to place this element on
	 * @param group The group from which to take information
	 * @return A group tag with general attributes set and several child nodes
	 * @throws FinderException
	 */
	public Element buildFullSubtree(Principal p, Document xml, GroupLocal group, AssignmentLocal assignment) throws FinderException
	{
		Profiler.enterMethod("GroupXMLBuilder.buildFullSubtree", "GroupID: " + group.getGroupID());
		Element xGroup = xml.createElement(TAG_GROUP);
		xGroup.setAttribute(A_ID, Long.toString(group.getGroupID()));
		Timestamp extension = group.getExtension();
		if (extension != null) {
		    xGroup.setAttribute(A_EXTENSION, DateTimeUtil.DATE_TIME_AMPM.format(extension));
		    xGroup.setAttribute(A_EXTDATE, DateTimeUtil.DATE.format(extension));
		    xGroup.setAttribute(A_EXTTIME, DateTimeUtil.TIME.format(extension));
		    xGroup.setAttribute(A_EXTAMPM, DateTimeUtil.AMPM.format(extension));
		    Timestamp now = new Timestamp(System.currentTimeMillis() - 60000 * assignment.getGracePeriod());
		    if (now.after(extension)) {
		        xGroup.setAttribute(A_PASTEXTENSION, "true");
		    }
		}
		xGroup.appendChild(buildMembersSubtree(xml, group));
		xGroup.appendChild(buildInvitationsSubtree(xml, group));
		xGroup.appendChild(buildGroupsInvitedToSubtree(p, xml, group.getAssignmentID()));
		xGroup.appendChild(buildSubmissionsSubtree(xml, group));
		if (group.getTimeSlotID() != null && group.getTimeSlotID().longValue() != 0){
			xGroup.setAttribute(A_ISSCHEDULED, "1");
			xGroup.appendChild(ScheduleXMLBuilder.buildTimeSlotSubtree(xml, assignment,
					database.timeSlotHome().findByTimeSlotID(group.getTimeSlotID().longValue()), p, XMLBuilder.TAG_TIMESLOT, true));
		}
		Profiler.exitMethod("GroupXMLBuilder.buildFullSubtree", "GroupID: " + group.getGroupID());
		return xGroup;
	}
	
	/**
	 * Generate an XML subtree with a list of members of the given Group
	 * @param xml The Document to place this element on
	 * @param group The group from which to take information
	 * @return A 
	 * @throws FinderException, IllegalArgumentException
	 */
	public Element buildMembersSubtree(Document xml, GroupLocal group) throws FinderException, IllegalArgumentException {
	    Element xMembers = xml.createElement(TAG_MEMBERS);
		Iterator i = database.userHome().findByGroupIDStatus(group.getGroupID(), GroupMemberBean.ACTIVE).iterator();
		while (i.hasNext()) {
			Element xMember = xml.createElement(TAG_MEMBER);
			UserLocal member = (UserLocal) i.next();
			xMember.setAttribute(A_NETID, member.getNetID());
			String name = member.getFirstName();
			name += name.length() == 0 ? member.getLastName() : " " + member.getLastName();
			xMember.setAttribute(A_NAME, name);
			xMembers.appendChild(xMember);
		}
		return xMembers;
	}
	
	/**
	 * Generate an XML subtree with a list of people invited to join the given Group
	 * @param xml The Document to place this element on
	 * @param group The group from which to take information
	 * @return A TAG_INVITATIONS element with child nodes
	 * @throws FinderException
	 */
	public Element buildInvitationsSubtree(Document xml, GroupLocal group) throws FinderException {
		Element xInvitations = xml.createElement(TAG_INVITATIONS);
		Iterator i = database.userHome().findByGroupIDStatus(group.getGroupID(), GroupMemberBean.INVITED).iterator();
		while (i.hasNext()) {
			UserLocal user = (UserLocal)i.next();
			Element xInvited = xml.createElement(TAG_INVITATION);
			xInvited.setAttribute(A_NETID, user.getNetID());
			String name = user.getFirstName();
			name += name.length() == 0 ? user.getLastName() : " " + user.getLastName();
			xInvited.setAttribute(A_NAME, name);
			xInvitations.appendChild(xInvited);
		}
		return xInvitations;
	}
	
	/**
	 * Generate an XML subtree with a list of groups the given principal has been invited to join
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @param assignmentID Used to get some group information
	 * @return A TAG_GROUPSINVITEDTO element with child nodes
	 * @throws FinderException
	 */
	public Element buildGroupsInvitedToSubtree(Principal p, Document xml, long assignmentID) throws FinderException {
		Element xInvitedGroups = xml.createElement(TAG_GROUPSINVITEDTO);
		AssignmentLocal assignment = database.assignmentHome().findByAssignmentID(assignmentID);
		Map names = database.getNameMap(assignment.getCourseID());
		Iterator i = database.groupMemberHome().findInvitersByNetIDAssignmentID(p.getNetID(), assignmentID).iterator();
		while (i.hasNext()) {
			GroupMemberLocal inviter = (GroupMemberLocal) i.next();
			Element xInvite = (Element) xInvitedGroups.getElementsByTagNameNS(TAG_INVITATION + inviter.getGroupID(), TAG_INVITATION).item(0);
			if (xInvite == null) {
			    xInvite =  xml.createElementNS(TAG_INVITATION + inviter.getGroupID(), TAG_INVITATION);
			    xInvite.setAttribute(A_ID, Long.toString(inviter.getGroupID()));
			    xInvitedGroups.appendChild(xInvite);
			}
			Element xUser = xml.createElement(TAG_ITEM);
			xUser.setAttribute(A_NAME, (String) names.get(inviter.getNetID()));
			xUser.setAttribute(A_ID, inviter.getNetID());
			xInvite.appendChild(xUser);
		}
		return xInvitedGroups;
	}
	
	/**
	 * Generate an XML subtree with a list of submissions by the given Group
	 * @param xml The Document to place this element on
	 * @param group The group from which to take information
	 * @return A TAG_SUBMITTED element with child nodes
	 * @throws FinderException
	 */
	public Element buildSubmissionsSubtree(Document xml, GroupLocal group) throws FinderException {
		Element xSubmissions = xml.createElement(TAG_SUBMITTED);
		Iterator i = database.submittedFileHome().findByGroupID(group.getGroupID()).iterator();
		Map submissions = database.getSubmissionNameMap(group.getAssignmentID());
		while (i.hasNext()) {
			Element xFile = xml.createElement(TAG_FILE);
			SubmittedFileLocal file = (SubmittedFileLocal)i.next();
			String subName = (String) submissions.get(new Long(file.getSubmissionID()));
			xFile.setAttribute(A_NAME, subName);
			xFile.setAttribute(A_DATE, DateTimeUtil.DATE_TIME_AMPM.format(file.getFileDate()));
			xFile.setAttribute(A_SIZE, FileUtil.formatFileSize(file.getFileSize()));
			xFile.setAttribute(A_MD5, file.getMD5());
			xFile.setAttribute(A_USER, file.getNetID());
			xFile.setAttribute(A_SUBMITTEDFILEID, Long.toString(file.getSubmittedFileID()));
			xSubmissions.appendChild(xFile);
		}
		return xSubmissions;
	}
	
	public void addGroupMemberNames(Document xml, Course course) {
		Element xRoot = (Element) xml.getFirstChild();
		Iterator users = database.userHome().findByCourseID(courseID).iterator();
		HashMap names = new HashMap();
		while (users.hasNext()) {
			UserLocal user = (UserLocal) users.next();
			String[] name = new String[] {user.getFirstName(), user.getLastName()};
			names.put(user.getNetID(), name);
		}
		NodeList groups = xRoot.getElementsByTagName(XMLBuilder.TAG_GROUP);
		for (int i=0; i < groups.getLength(); i++) {
			Element xGroup = (Element) groups.item(i);
			NodeList members = xGroup.getElementsByTagName(XMLBuilder.TAG_MEMBER);
			for (int j=0; j < members.getLength(); j++) {
				Element xMember = (Element) members.item(j);
				String netID = xMember.getAttribute(XMLBuilder.A_NETID);
				String[] name = (String[]) names.get(netID);
				if (name != null) {
					xMember.setAttribute(XMLBuilder.A_FIRSTNAME, name[0]);
					xMember.setAttribute(XMLBuilder.A_LASTNAME, name[1]);
				}
			}
		}
	}
	
}
