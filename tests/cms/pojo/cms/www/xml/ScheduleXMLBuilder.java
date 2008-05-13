/**
 * Created on Mar 15, 2006
 *
 * @author Matt Thomas, evan
 */
package cms.www.xml;

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
 * Builds a subtree containing information about a course schedule.
 * This subtree will be placed into the standard assignment subtree. 
 */
public class ScheduleXMLBuilder {
	public Long zero = new Long(0);

	public Element buildScheduleSubtree(Principal p, Document xml, Assignment assignment) {
		if (!assignment.getScheduled()){
			// return a blank subtree
			Element xSchedule = xml.createElement(XMLBuilder.TAG_SCHEDULE);
			return xSchedule;
		}		
		Element xSchedule = xml.createElement(XMLBuilder.TAG_SCHEDULE);
		long assignID = assignment.getAssignmentID();
		// Make sure there are enough slots for all the groups
		int nGroups = 0;
		java.util.ArrayList observedGroups = new java.util.ArrayList();
		Iterator i = XMLBuilder.getDatabase().groupHome().findByAssignmentID(assignment.getAssignmentID()).iterator();
		while (i.hasNext()){
			GroupLocal gr = (GroupLocal)i.next();
			long gr_id = gr.getGroupID();
			Long gr_id_obj = new Long(gr_id);
			if (!observedGroups.contains(gr_id_obj)){
				observedGroups.add(gr_id_obj);
			}
		}
			nGroups = observedGroups.size();
		Collection slots = database.timeSlotHome().findByAssignmentID(assignID);
		Integer maxGroupsObj = assignment.getGroupLimit();
		int maxGroups = 0;
		if (maxGroupsObj!=null) maxGroups = maxGroupsObj.intValue();
		if(p.isAssignPrivByAssignmentID(assignID) && nGroups > (maxGroups * slots.size()))
		{
			addStatus(xml, "There are not enough time slots for all student groups", MSG_WARNING);
		}
		// Process the timeslot duration into a presentable string in h:mm:ss format
		Long durationObj = assignment.getDuration();
		long duration = 0;
		if (durationObj!=null) { duration = durationObj.longValue(); }
		String durationString = String.valueOf(duration);
		xSchedule.setAttribute(XMLBuilder.A_TSDURATIONSTR, durationString);
		xSchedule.setAttribute(XMLBuilder.A_TSMAXGROUPS, Integer.toString(maxGroups));
		//staff-side lock-date info
		Timestamp lockTime = assignment.getTimeslotLockTime();
		if(lockTime == null) { //no deadline
			xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKDATE, "");
			xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKTIME, "");
			xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKAMPM, "");
		} else {
			xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKDATE, DateTimeUtil.DATE.format(lockTime));
			xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKTIME, DateTimeUtil.TIME.format(lockTime));
			xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKAMPM, DateTimeUtil.AMPM.format(lockTime));
		}
		//student-side lock-date info
		if(lockTime != null && lockTime.before(new Timestamp(System.currentTimeMillis())))
			xSchedule.setAttribute(XMLBuilder.A_SCHEDULE_LOCKED, "");
		// Iterate and add a node relating to each timeslot currently in the schedule
		i = database.timeSlotHome().findByAssignmentID(assignment.getAssignmentID()).iterator();
		while (i.hasNext()) {
			TimeSlotLocal ts = (TimeSlotLocal)i.next();
			xSchedule.appendChild(buildTimeSlotSubtree(xml,assignment,ts,
					p,TAG_TIMESLOT, true));
		}
		i = database.timeSlotHome().findConflictingByAssignmentID(assignment.getAssignmentID()).iterator();
		while (i.hasNext()) {
			TimeSlotLocal ts = (TimeSlotLocal)i.next();
			Element xTimeSlot = (Element) XMLUtil.getChildrenByTagNameAndAttributeValue(xSchedule, TAG_TIMESLOT, A_TSID, "" + ts.getTimeSlotID()).item(0);
			if (xTimeSlot != null) {
				xTimeSlot.setAttribute(A_TSCONFLICTING, "true");
			}
		}
		// build a subtree containing all unscheduled groups, for use in generating schedule printouts
		String tag = XMLBuilder.TAG_UNSCHEDULEDGROUPS;
		Element xUnscheduled = xml.createElement(tag);
		Iterator it = null;
		it = XMLBuilder.database.groupHome().findByAssignmentID(assignment.getAssignmentID()).iterator();
		observedGroups = new java.util.ArrayList();
		while (it.hasNext()){
			//Group gr = XMLBuilder.database.groupHome().findByPrimaryKey((GroupPK) it.next());
			GroupLocal gr = (GroupLocal)it.next();
			Long groupid = new Long(gr.getGroupID());
			Long tsid = gr.getTimeSlotID();
			if ((!observedGroups.contains(groupid)) && (groupid!=null) &&
					((tsid == null) || (tsid.longValue() == 0))){
				Element xGroup = buildShortGroupSubtree(xml,gr,assignment,true);
				xUnscheduled.appendChild(xGroup);
				observedGroups.add(groupid);
			}
		}
		xSchedule.appendChild(xUnscheduled);
		return xSchedule;
	}

	// Subtree of the 'schedule' tree to describe a particular timeslot
	public Element buildTimeSlotSubtree(Document xml, AssignmentLocal assignment,
												TimeSlotLocal ts, Principal p, String tag, boolean showMembers) throws FinderException
	{
		long duration = 0;
		if (assignment.getDuration() != null){
			duration = assignment.getDuration().longValue();
		}
		long courseid = assignment.getCourseID();
		Element xTimeSlot = xml.createElement(tag);
		long tsid = ts.getTimeSlotID();
		xTimeSlot.setAttribute(XMLBuilder.A_TSID, "" + tsid);
		String name = ts.getName();
		xTimeSlot.setAttribute(XMLBuilder.A_TSNAME, (name==null) ? "" : name );
		String staffid = ts.getStaff();
		xTimeSlot.setAttribute(XMLBuilder.A_TSSTAFF, staffid);
		String location = ts.getLocation();
		xTimeSlot.setAttribute(XMLBuilder.A_TSLOCATION, (location==null) ? "" : location );
		// staff member's netid is converted into a readable name
		String staffName = "";
		UserLocal staffer = XMLBuilder.database.userHome().findByUserID(staffid);
		staffName = staffer.getFirstName() + " " + staffer.getLastName();
		// determination is made as to whether the current user has the proper priveleges to alter
		// the given timeslot... this applies ONLY TO STAFF MEMBERS and will default to false
		// for other groups; also, if the assignment subtree was created without a principal being
		// passed in, then editRights will always read 'false'
		String editRights = "false";
		if(p != null)
		{
			if(p.isAdminPrivByCourseID(courseid)) editRights = "true";
			else if(p.isAssignPrivByCourseID(courseid)) editRights = "true";
			else if(p.getNetID().equals(staffid)) editRights = "true";
		}
		xTimeSlot.setAttribute(XMLBuilder.A_TSEDITRIGHTS, editRights);
		xTimeSlot.setAttribute(XMLBuilder.A_TSSTAFFNAME, staffName);
		xTimeSlot.setAttribute(XMLBuilder.A_TSPOPULATION, Integer.toString(ts.getPopulation()));
		// convert timeslot's timestamp and duration into readable start and end times
		Timestamp tsStart = ts.getStartTime();
		long startTime = tsStart.getTime();
		Timestamp tsEnd = new Timestamp(startTime + (duration * 1000 * 60 /* ms -> min */));
		xTimeSlot.setAttribute(XMLBuilder.A_TSSTARTDATE, DateTimeUtil.DATE.format(tsStart));
		xTimeSlot.setAttribute(XMLBuilder.A_TSSTARTTIME, DateTimeUtil.TIME.format(tsStart));
		xTimeSlot.setAttribute(XMLBuilder.A_TSSTARTAMPM, DateTimeUtil.AMPM.format(tsStart));
		xTimeSlot.setAttribute(XMLBuilder.A_TSENDDATE, DateTimeUtil.DATE.format(tsEnd));
		xTimeSlot.setAttribute(XMLBuilder.A_TSENDTIME, DateTimeUtil.TIME.format(tsEnd));
		xTimeSlot.setAttribute(XMLBuilder.A_TSENDAMPM, DateTimeUtil.AMPM.format(tsEnd));
		// Add short subtrees for every group in the timeslot
		GroupLocalHome gh;
		gh = database.groupHome();
		Collection c= gh.findByTimeSlotID(ts.getTimeSlotID());
		Iterator i = c.iterator();
		Collection observedGroups = new java.util.ArrayList();
		while (i.hasNext()){
			GroupLocal gr = (GroupLocal)i.next();
			Long id = new Long(gr.getGroupID());
			if (!observedGroups.contains(id)){
				Element xGroup = buildShortGroupSubtree(xml,gr,assignment,showMembers);
				xTimeSlot.appendChild(xGroup);
				observedGroups.add(id);
			}
		}
		return xTimeSlot;
	}
	
	// small group subtree to describe a constituent group of a timeslot (or of the unscheduled-groups set)
	public Element buildShortGroupSubtree(Document xml, GroupLocal group, AssignmentLocal assignment, boolean showMembers) throws FinderException {
		Element xGroup = xml.createElement(XMLBuilder.TAG_GROUP);
		xGroup.setAttribute(XMLBuilder.A_ID, Long.toString(group.getGroupID()));
		// standard 'members' subtree used in standard group trees
		if (showMembers) xGroup.appendChild(GroupXMLBuilder.buildMembersSubtree(xml, group));
		return xGroup;
	}
}
