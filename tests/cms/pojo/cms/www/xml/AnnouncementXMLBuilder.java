package cms.www.xml;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.FinderException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;

import edu.cornell.csuglab.cms.author.Principal;
import edu.cornell.csuglab.cms.base.*;

/**
 * Builds an XML subtree describing a course in as much detail as desired
 * 
 * Created 3 / 28 / 05
 * @author Evan
 */
public class AnnouncementXMLBuilder extends XMLBuilder
{
	
	/**
	 * Generate an XML subtree with a list of announcements for the given Course
	 * @param p The Principal to generate the page for
	 * @param xml The Document to place this element on
	 * @param course The course from which to generate the branch
	 * @return An XML element with announcement info for the given course
	 * @throws FinderException
	 */
	public static Element buildAnnouncementsSubtree(Principal p, Document xml, CourseLocal course) throws FinderException {
		Profiler.enterMethod("AnnouncementXMLBuilder.buildAnnouncementSubtree", "CourseID: " + course.getCourseID());
		Element xAnnouncements = xml.createElement(TAG_COURSEANNOUNCEMENTS);
		Iterator i = database.announcementHome().findByCourseID(course.getCourseID()).iterator();
		final long SECS_PER_DAY = 86400;
		Timestamp current = new Timestamp(System.currentTimeMillis());
		Timestamp weekAgo = new Timestamp(current.getTime() - 7 * SECS_PER_DAY * 1000);
		int numRecentAnnounce = 0;
		int numTotalAnnounce = 0;
		Map staffNames = database.getStaffNameMap(course.getCourseID());
		while (i.hasNext())
		{
			AnnouncementLocal announcement = (AnnouncementLocal)i.next();
			AnnouncementData announceData = announcement.getAnnouncementData();
			if(announceData.getPostedDate().compareTo(weekAgo) > 0) numRecentAnnounce ++;
			Element xAnnouncement = buildGeneralSubtree(xml, announceData, staffNames);
			xAnnouncement.appendChild(buildHistorySubtree(xml, announcement));
			xAnnouncements.appendChild(xAnnouncement);
			numTotalAnnounce ++;
		}
		xAnnouncements.setAttribute(XMLBuilder.A_NUMRECENTANNOUNCE, ""+numRecentAnnounce);
		xAnnouncements.setAttribute(XMLBuilder.A_NUMTOTALANNOUNCE, ""+numTotalAnnounce);
		Profiler.exitMethod("AnnouncementXMLBuilder.buildAnnouncementSubtree", "CourseID: " + course.getCourseID());
		return xAnnouncements;
	}
	
	public static void appendHiddenAnnouncements(Document xml, long courseID) throws FinderException {
	    Element root = (Element) xml.getFirstChild();
	    Iterator as = database.announcementHome().findHiddenByCourseID(courseID).iterator();
	    while (as.hasNext()) {
	        AnnouncementLocal a = (AnnouncementLocal) as.next();
	        Element xAnnounce = xml.createElement(TAG_HIDDENANNOUNCE);
	        xAnnounce.setAttribute(A_ID, String.valueOf(a.getAnnouncementID()));
	        xAnnounce.setAttribute(A_POSTER, a.getAuthor());
	        xAnnounce.setAttribute(A_TEXT, a.getText());
	        xAnnounce.setAttribute(A_DATE, DateTimeUtil.MONTH_DAY_TIME.format(a.getPostedDate()));
	        root.appendChild(xAnnounce);
	    }
	}
	
	/**
	 * Generate an XML subtree with general info about the given Announcement
	 * @param xml The Document to place this element on
	 * @param announcement The announcement from which to generate the branch
	 * @param formatForWeb Whether or not to escape HTML tags from the text
	 * @return The announcement element of the tree, with general properties set
	 * @throws FinderException
	 */
	public static Element buildGeneralSubtree(Document xml, AnnouncementData announcement, Map staffNames) throws FinderException
	{
		Element xAnnouncement = xml.createElement(TAG_ANNOUNCEMENT);
		xAnnouncement.setAttribute(A_ID, Long.toString(announcement.getAnnouncementID()));
		String name = (String) staffNames.get(announcement.getAuthor());
		String poster = name == null ? announcement.getAuthor() :
		    	name + " (" + announcement.getAuthor() + ")";
		xAnnouncement.setAttribute(A_POSTER, poster);
		xAnnouncement.setAttribute(A_DATE, DateTimeUtil.MONTH_DAY_TIME.format(announcement.getPostedDate()));
		String text= announcement.getText();
		//if(formatForWeb) text= Util.formatWebString(text);
		xAnnouncement.setAttribute(A_TEXT, text);
		xAnnouncement.setAttribute(A_OLDTEXT, announcement.getEditInfo());
		return xAnnouncement;
	}
	
	public static Element buildHistorySubtree(Document xml, AnnouncementLocal announcement) throws FinderException{
		Element xOldAnnouncement, xHistory = xml.createElement(TAG_ANNOUNCEMENTHISTORY);
		Collection c = announcement.getAnnouncementHistory();
		Iterator i = c.iterator();
		String text;
		while(i.hasNext()){
			OldAnnouncementData oldAnnounce = (OldAnnouncementData)i.next();
			text = oldAnnounce.getText();
			//if(formatForWeb)text = Util.formatWebString(text);
			xOldAnnouncement = xml.createElement(TAG_ANNOUNCEMENT);
			xOldAnnouncement.setAttribute(A_ID, Long.toString(oldAnnounce.getOldAnnouncementID()));
			xOldAnnouncement.setAttribute(A_TEXT, text);
			xHistory.appendChild(xOldAnnouncement);
		}
		return xHistory;
	}
}
