package cms.www.xml;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;

import cms.auth.Principal;
import cms.model.*;

/**
 * Builds an XML subtree with information about a given group (which exists only
 * for a specific assignment) Created 3 / 29 / 05
 * 
 * @author Evan
 */
public class GroupXMLBuilder {
  private XMLBuilder xmlBuilder;
  
  public GroupXMLBuilder(final XMLBuilder builder) {
    this.xmlBuilder = builder;
  }

  /**
   * Generate an XML subtree with all known info on the given Group
   * 
   * @param user
   *          The Principal to generate the page for. We assume p is a member of
   *          the group or has staff privileges for the course the group is in
   * @param xml
   *          The Document to place this element on
   * @param group
   *          The group from which to take information          
   * @return A group tag with general attributes set and several child nodes
   * @throws FinderException
   */
  public Element buildFullSubtree(User user, Document xml, Group group, Assignment assignment) {
    Profiler.enterMethod("GroupXMLBuilder.buildFullSubtree", "GroupID: " + group.toString());
    Element xGroup = xml.createElement(XMLBuilder.TAG_GROUP);
    xGroup.setAttribute(XMLBuilder.A_ID, group.toString());
    Date extension = group.getExtension();
    if (extension != null) {
      xGroup.setAttribute(XMLBuilder.A_EXTENSION, DateTimeUtil.DATE_TIME_AMPM
          .format(extension));
      xGroup.setAttribute(XMLBuilder.A_EXTDATE, DateTimeUtil.DATE
          .format(extension));
      xGroup.setAttribute(XMLBuilder.A_EXTTIME, DateTimeUtil.TIME
          .format(extension));
      xGroup.setAttribute(XMLBuilder.A_EXTAMPM, DateTimeUtil.AMPM
          .format(extension));
      Date now = new Date(System.currentTimeMillis()
                         - 60000 * assignment.getGracePeriod());
      if (now.after(extension)) {
        xGroup.setAttribute(XMLBuilder.A_PASTEXTENSION, "true");
      }
    }
    xGroup.appendChild(buildMembersSubtree(xml, group));
    xGroup.appendChild(buildInvitationsSubtree(xml, group));
    xGroup.appendChild(buildGroupsInvitedToSubtree(user, xml, group.getAssignment()));
    xGroup.appendChild(buildSubmissionsSubtree(xml, group));
    if (group.getTimeSlot() != null) {
      xGroup.setAttribute(XMLBuilder.A_ISSCHEDULED, "1");
      xGroup.appendChild(xmlBuilder.scheduleXMLBuilder.buildTimeSlotSubtree(xml,
          assignment, group.getTimeSlot(), user, XMLBuilder.TAG_TIMESLOT, true));
    }
    Profiler.exitMethod("GroupXMLBuilder.buildFullSubtree", "GroupID: " + group.toString());
    return xGroup;
  }

  /**
   * Generate an XML subtree with a list of members of the given Group
   * 
   * @param xml
   *          The Document to place this element on
   * @param group
   *          The group from which to take information
   * @return A
   * @throws FinderException,
   *           IllegalArgumentException
   */
  public Element buildMembersSubtree(Document xml, Group group) throws IllegalArgumentException {
    Element xMembers = xml.createElement(XMLBuilder.TAG_MEMBERS);
    Iterator i = group.getMembers().iterator();

    while (i.hasNext()) {
      GroupMember member = (GroupMember) i.next();
      if (member.getStatus() != GroupMember.ACTIVE)
        continue;
      User user = member.getStudent().getUser();
      
      Element xMember = xml.createElement(XMLBuilder.TAG_MEMBER);
      xMember.setAttribute(XMLBuilder.A_NETID, user.getNetID());
      StringBuilder name = new StringBuilder(user.getFirstName());
      if (name.length() != 0)
        name.append(" ");
      name.append(user.getLastName());
      
      xMember.setAttribute(XMLBuilder.A_NAME, name.toString());
      xMembers.appendChild(xMember);
    }
    return xMembers;
  }

  /**
   * Generate an XML subtree with a list of people invited to join the given
   * Group
   * 
   * @param xml
   *          The Document to place this element on
   * @param group
   *          The group from which to take information
   * @return A XMLBuilder.TAG_INVITATIONS element with child nodes
   * @throws FinderException
   */
  public Element buildInvitationsSubtree(Document xml, Group group) {
    Element xInvitations = xml.createElement(XMLBuilder.TAG_INVITATIONS);
    Iterator i = group.getMembers().iterator();
    while (i.hasNext()) {
      GroupMember member = (GroupMember) i.next();
      if (member.getStatus() != GroupMember.INVITED)
        continue;
      User user = member.getStudent().getUser();
      
      Element xInvited = xml.createElement(XMLBuilder.TAG_INVITATION);
      xInvited.setAttribute(XMLBuilder.A_NETID, user.getNetID());
      StringBuilder name = new StringBuilder(user.getFirstName());
      if (name.length() > 0)
        name.append(" ");
      name.append(user.getLastName());
      xInvited.setAttribute(XMLBuilder.A_NAME, name.toString());
      xInvitations.appendChild(xInvited);
    }
    return xInvitations;
  }

  /**
   * Generate an XML subtree with a list of groups the given principal has been
   * invited to join
   * 
   * @param user
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param assignmentID
   *          Used to get some group information
   * @return A XMLBuilder.TAG_GROUPSINVITEDTO element with child nodes
   * @throws FinderException
   */
  public Element buildGroupsInvitedToSubtree(User user, Document xml, Assignment assignment) {
    Element xInvitedGroups = xml.createElement(XMLBuilder.TAG_GROUPSINVITEDTO);
    
    Iterator i = assignment.findInvitations(user).iterator();
    while (i.hasNext()) {
      GroupMember invite = (GroupMember) i.next();
      
      Iterator members = invite.getGroup().getMembers().iterator();
      while (members.hasNext()) {
        GroupMember inviter = (GroupMember) members.next();
        if (inviter.getStatus() != GroupMember.ACTIVE)
          continue;

        Element xInvite = (Element) xInvitedGroups.getElementsByTagNameNS(
            XMLBuilder.TAG_INVITATION + inviter.getGroup().toString(),
            XMLBuilder.TAG_INVITATION).item(0);
        if (xInvite == null) {
          xInvite = xml.createElementNS(
              XMLBuilder.TAG_INVITATION + inviter.getGroup().toString(),
              XMLBuilder.TAG_INVITATION);
          xInvite.setAttribute(XMLBuilder.A_ID, inviter.getGroup().toString());
          xInvitedGroups.appendChild(xInvite);
        }
        Element xUser = xml.createElement(XMLBuilder.TAG_ITEM);
        StringBuilder name = new StringBuilder(inviter.getStudent().getUser().getFirstName());
        if (name.length() > 0)
          name.append(" ");
        name.append(inviter.getStudent().getUser().getLastName());
        xUser.setAttribute(XMLBuilder.A_NAME, name.toString());
        xUser.setAttribute(XMLBuilder.A_ID, inviter.getStudent().getUser().getNetID());
        xInvite.appendChild(xUser);
      }
    }
    return xInvitedGroups;
  }

  /**
   * Generate an XML subtree with a list of submissions by the given Group
   * 
   * @param xml
   *          The Document to place this element on
   * @param group
   *          The group from which to take information
   * @return A XMLBuilder.TAG_SUBMITTED element with child nodes
   * @throws FinderException
   */
  public Element buildSubmissionsSubtree(Document xml, Group group) {
    Element xSubmissions = xml.createElement(XMLBuilder.TAG_SUBMITTED);
    Iterator i = group.getSubmittedFiles().iterator();
    while (i.hasNext()) {
      Element xFile = xml.createElement(XMLBuilder.TAG_FILE);
      SubmittedFile file = (SubmittedFile) i.next();
      String subName = file.getSubmission().getSubmissionName();
      xFile.setAttribute(XMLBuilder.A_NAME,
                         file.getSubmission().getSubmissionName());
      xFile.setAttribute(XMLBuilder.A_DATE,
                         DateTimeUtil.DATE_TIME_AMPM.format(file.getFileDate()));
      xFile.setAttribute(XMLBuilder.A_SIZE,
                         file.getFile().formatFileSize());
      xFile.setAttribute(XMLBuilder.A_MD5,  file.getFile().getMD5());
      xFile.setAttribute(XMLBuilder.A_USER, file.getUser().getNetID());
      xFile.setAttribute(XMLBuilder.A_SUBMITTEDFILEID, file.toString());
      xSubmissions.appendChild(xFile);
    }
    return xSubmissions;
  }

  public void addGroupMemberNames(Document xml, Course course) {
    Element xRoot = (Element) xml.getFirstChild();
    // TODO: this method is kind of horrible... [mdg]
    Map/*String (netID), User*/ userMap = new HashMap();
    
    Iterator students = course.getStudents().iterator();
    while (students.hasNext()) {
      User user = ((Student) students.next()).getUser();
      userMap.put(user.getNetID(), user);
    }
    
    NodeList groups = xRoot.getElementsByTagName(XMLBuilder.TAG_GROUP);
    for (int i = 0; i < groups.getLength(); i++) {
      Element xGroup = (Element) groups.item(i);
      NodeList members = xGroup.getElementsByTagName(XMLBuilder.TAG_MEMBER);
      for (int j = 0; j < members.getLength(); j++) {
        Element xMember = (Element) members.item(j);
        String netID = xMember.getAttribute(XMLBuilder.A_NETID);
        User   user  = (User) userMap.get(netID);
        if (user != null) {
          xMember.setAttribute(XMLBuilder.A_FIRSTNAME, user.getFirstName());
          xMember.setAttribute(XMLBuilder.A_LASTNAME,  user.getLastName());
        }
      }
    }
  }

}
