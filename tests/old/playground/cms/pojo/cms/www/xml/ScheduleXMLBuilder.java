/**
 * Created on Mar 15, 2006
 * 
 * @author Matt Thomas, evan
 */
package cms.www.xml;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.DateTimeUtil;
import cms.www.util.Util;

import cms.auth.Principal;
import cms.model.*;

/**
 * Builds a subtree containing information about a course schedule. This subtree
 * will be placed into the standard assignment subtree.
 */
public class ScheduleXMLBuilder {
  public Long zero = new Long(0);
  private XMLBuilder xmlBuilder;
  
  public ScheduleXMLBuilder(final XMLBuilder builder) {
    this.xmlBuilder = builder;
  }

  public Element buildScheduleSubtree(User p, Document xml, Assignment assignment) {
    if (!assignment.getScheduled()) {
      // return a blank subtree
      Element xSchedule = xml.createElement(XMLBuilder.TAG_SCHEDULE);
      return xSchedule;
    }
    Element xSchedule = xml.createElement(XMLBuilder.TAG_SCHEDULE);
    // Make sure there are enough slots for all the groups
    int nGroups = 0;
    ArrayList observedGroups = new ArrayList();
    Iterator i = assignment.getGroups().iterator();
    while (i.hasNext()) {
      // TODO: this is kinda bad... [mdg]
      Group gr = (Group) i.next();
      if (!observedGroups.contains(gr)) {
        observedGroups.add(gr);
      }
    }
    nGroups = observedGroups.size();
    Collection slots = assignment.getTimeSlots();
    Integer maxGroupsObj = assignment.getGroupLimit();
    int maxGroups = 0;
    if (maxGroupsObj != null) maxGroups = maxGroupsObj.intValue();
    if (p.isAssignPrivByAssignment(assignment)
        && nGroups > (maxGroups * slots.size())) {
      xmlBuilder.addStatus(xml,
          "There are not enough time slots for all student groups",
          XMLBuilder.MSG_WARNING);
    }
    // Process the timeslot duration into a presentable string in h:mm:ss format
    Long durationObj = assignment.getDuration();
    long duration = 0;
    if (durationObj != null) {
      duration = durationObj.longValue();
    }
    String durationString = String.valueOf(duration);
    xSchedule.setAttribute(XMLBuilder.A_TSDURATIONSTR, durationString);
    xSchedule.setAttribute(XMLBuilder.A_TSMAXGROUPS, Integer
        .toString(maxGroups));
    // staff-side lock-date info
    Date lockTime = assignment.getTimeslotLockTime();
    if (lockTime == null) { // no deadline
      xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKDATE, "");
      xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKTIME, "");
      xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKAMPM, "");
    } else {
      xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKDATE,
                             DateTimeUtil.DATE.format(lockTime));
      xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKTIME,
                             DateTimeUtil.TIME.format(lockTime));
      xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKAMPM,
                             DateTimeUtil.AMPM.format(lockTime));
    }
    // student-side lock-date info
    if (lockTime != null && lockTime.before(new Date()))
      xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKED, "");
    // Iterate and add a node relating to each timeslot currently in the
    // schedule
    i = assignment.getTimeSlots().iterator();
    while (i.hasNext()) {
      TimeSlot ts = (TimeSlot) i.next();
      xSchedule.appendChild(buildTimeSlotSubtree(xml, assignment, ts, p,
          XMLBuilder.TAG_TIMESLOT, true));
    }
    i = assignment.findConflictingTimeSlots().iterator();
    while (i.hasNext()) {
      TimeSlot ts = (TimeSlot) i.next();
      Element xTimeSlot = (Element) XMLUtil.getChildrenByTagNameAndAttributeValue(
          xSchedule,
          XMLBuilder.TAG_TIMESLOT, XMLBuilder.A_TSID, ts.toString()).item(0);
      if (xTimeSlot != null) {
        xTimeSlot.setAttribute(XMLBuilder.A_TSCONFLICTING, "true");
      }
    }
    // build a subtree containing all unscheduled groups, for use in generating
    // schedule printouts
    Element xUnscheduled = xml.createElement(XMLBuilder.TAG_UNSCHEDULEDGROUPS);
    Iterator it = assignment.getGroups().iterator();
    observedGroups = new java.util.ArrayList();
    while (it.hasNext()) {
      // Group gr = XMLBuilder.database.groupHome().findByPrimaryKey((GroupPK)
      // it.next());
      Group    gr   = (Group) it.next();
      TimeSlot slot = gr.getTimeSlot();
      if (gr != null &&
          !observedGroups.contains(gr) &&
          (slot == null)) {
        Element xGroup = buildShortGroupSubtree(xml, gr, assignment, true);
        xUnscheduled.appendChild(xGroup);
        observedGroups.add(gr);
      }
    }
    xSchedule.appendChild(xUnscheduled);
    return xSchedule;
  }

  // Subtree of the 'schedule' tree to describe a particular timeslot
  public Element buildTimeSlotSubtree(Document xml, Assignment assignment,
      TimeSlot ts, User p, String tag, boolean showMembers) {
    long duration = 0;
    if (assignment.getDuration() != null) {
      duration = assignment.getDuration().longValue();
    }
    Course course = assignment.getCourse();
    Element xTimeSlot = xml.createElement(tag);
    xTimeSlot.setAttribute(XMLBuilder.A_TSID, ts.toString());
    String name = ts.getName();
    xTimeSlot.setAttribute(XMLBuilder.A_TSNAME, (name == null) ? "" : name);
    xTimeSlot.setAttribute(XMLBuilder.A_TSSTAFF, ts.getStaff().getNetID());
    String location = ts.getLocation();
    xTimeSlot.setAttribute(XMLBuilder.A_TSLOCATION, (location == null) ? "" : location);
    // staff member's netid is converted into a readable name
    User staffer = ts.getStaff();
    String staffName = staffer.getFirstName() + " " + staffer.getLastName();
    // determination is made as to whether the current user has the proper
    // priveleges to alter
    // the given timeslot... this applies ONLY TO STAFF MEMBERS and will default
    // to false
    // for other groups; also, if the assignment subtree was created without a
    // principal being
    // passed in, then editRights will always read 'false'
    String editRights = "false";
    if (p != null) {
      if (p.isAdminPrivByCourse(course))
        editRights = "true";
      else if (p.isAssignPrivByCourse(course))
        editRights = "true";
      else if (p.equals(staffer)) editRights = "true";
    }
    xTimeSlot.setAttribute(XMLBuilder.A_TSEDITRIGHTS, editRights);
    xTimeSlot.setAttribute(XMLBuilder.A_TSSTAFFNAME,  staffName);
    xTimeSlot.setAttribute(XMLBuilder.A_TSPOPULATION, Integer.toString(ts.getPopulation()));
    // convert timeslot's timestamp and duration into readable start and end
    // times
    Date tsStart = ts.getStartTime();
    long startTime = tsStart.getTime();
    Date tsEnd =
        new Date(startTime + (duration * 1000 * 60 /* ms -> min */));
    xTimeSlot.setAttribute(XMLBuilder.A_TSSTARTDATE, DateTimeUtil.DATE
        .format(tsStart));
    xTimeSlot.setAttribute(XMLBuilder.A_TSSTARTTIME, DateTimeUtil.TIME
        .format(tsStart));
    xTimeSlot.setAttribute(XMLBuilder.A_TSSTARTAMPM, DateTimeUtil.AMPM
        .format(tsStart));
    xTimeSlot.setAttribute(XMLBuilder.A_TSENDDATE, DateTimeUtil.DATE
        .format(tsEnd));
    xTimeSlot.setAttribute(XMLBuilder.A_TSENDTIME, DateTimeUtil.TIME
        .format(tsEnd));
    xTimeSlot.setAttribute(XMLBuilder.A_TSENDAMPM, DateTimeUtil.AMPM
        .format(tsEnd));
    // Add short subtrees for every group in the timeslot
    Iterator i = ts.findGroups().iterator();
    Collection observedGroups = new java.util.ArrayList();
    while (i.hasNext()) {
      Group gr = (Group) i.next();
      if (!observedGroups.contains(gr)) {
        Element xGroup =
            buildShortGroupSubtree(xml, gr, assignment, showMembers);
        xTimeSlot.appendChild(xGroup);
        observedGroups.add(gr);
      }
    }
    return xTimeSlot;
  }

  // small group subtree to describe a constituent group of a timeslot (or of
  // the unscheduled-groups set)
  public Element buildShortGroupSubtree(Document xml, Group group,
      Assignment assignment, boolean showMembers) {
    Element xGroup = xml.createElement(XMLBuilder.TAG_GROUP);
    xGroup.setAttribute(XMLBuilder.A_ID, group.toString());
    // standard 'members' subtree used in standard group trees
    if (showMembers)
      xGroup.appendChild(xmlBuilder.groupXMLBuilder.buildMembersSubtree(xml, group));
    return xGroup;
  }
}
