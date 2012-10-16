package cms.www.xml;

import fabric.util.*;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;

import cms.auth.Principal;
import cms.model.*;

/**
 * Builds an XML subtree describing a course in as much detail as desired
 * Created 3 / 28 / 05
 * 
 * @author Evan
 */
public class AnnouncementXMLBuilder {

  public AnnouncementXMLBuilder(XMLBuilder builder) {
  }

  /**
   * Generate an XML subtree with a list of announcements for the given Course
   * 
   * @param p
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param course
   *          The course from which to generate the branch
   * @return An XML element with announcement info for the given course
   * @throws FinderException
   */
  public Element buildAnnouncementsSubtree(User p, Document xml, Course course) {
    Profiler.enterMethod("AnnouncementXMLBuilder.buildAnnouncementSubtree",
        "CourseID: " + course.toString());
    Element xAnnouncements = xml.createElement(XMLBuilder.TAG_COURSEANNOUNCEMENTS);
    Iterator i = course.getAnnouncements().iterator();
    
    final long SECS_PER_DAY = 86400;
    Date current = new Date();
    Date weekAgo = new Date(current.getTime() - 7 * SECS_PER_DAY * 1000);
    int numRecentAnnounce = 0;
    int numTotalAnnounce = 0;
    
    while (i.hasNext()) {
      Announcement announcement = (Announcement) i.next();
      if (announcement.getPosted().compareTo(weekAgo) > 0)
        numRecentAnnounce++;
      Element xAnnouncement = buildGeneralSubtree(xml, announcement);
      xAnnouncement.appendChild(buildHistorySubtree(xml, announcement));
      xAnnouncements.appendChild(xAnnouncement);
      numTotalAnnounce++;
    }
    xAnnouncements.setAttribute(XMLBuilder.A_NUMRECENTANNOUNCE, ""
        + numRecentAnnounce);
    xAnnouncements.setAttribute(XMLBuilder.A_NUMTOTALANNOUNCE, ""
        + numTotalAnnounce);
    Profiler.exitMethod("AnnouncementXMLBuilder.buildAnnouncementSubtree",
        "CourseID: " + course.toString());
    return xAnnouncements;
  }

  public void appendHiddenAnnouncements(Document xml, Course course) {
    Element root = (Element) xml.getFirstChild();
    Iterator as = course.findHiddenAnnouncements().iterator();
        
    while (as.hasNext()) {
      Announcement a = (Announcement) as.next();
      Element xAnnounce = xml.createElement(XMLBuilder.TAG_HIDDENANNOUNCE);
      xAnnounce.setAttribute(XMLBuilder.A_ID, a.toString());
      xAnnounce.setAttribute(XMLBuilder.A_POSTER, a.getAuthor().getNetID());
      xAnnounce.setAttribute(XMLBuilder.A_TEXT, a.getText());
      xAnnounce.setAttribute(XMLBuilder.A_DATE, DateTimeUtil.MONTH_DAY_TIME.format(a.getPosted()));
      root.appendChild(xAnnounce);
    }
  }

  /**
   * Generate an XML subtree with general info about the given Announcement
   * 
   * @param xml
   *          The Document to place this element on
   * @param announcement
   *          The announcement from which to generate the branch
   * @param formatForWeb
   *          Whether or not to escape HTML tags from the text
   * @return The announcement element of the tree, with general properties set
   * @throws FinderException
   */
  public Element buildGeneralSubtree(Document xml, Announcement announcement) {
    Element xAnnouncement = xml.createElement(XMLBuilder.TAG_ANNOUNCEMENT);
    xAnnouncement.setAttribute(XMLBuilder.A_ID, announcement.toString());
    String name   = announcement.getAuthor().canonicalName();
    String poster = name.length() > 0 ?
        announcement.getAuthor().getNetID() :
        name + " (" + announcement.getAuthor().getNetID() + ")";
    xAnnouncement.setAttribute(XMLBuilder.A_POSTER, poster);
    xAnnouncement.setAttribute(XMLBuilder.A_DATE, DateTimeUtil.MONTH_DAY_TIME
        .format(announcement.getPosted()));
    String text = announcement.getText();
    // if (formatForWeb) text= Util.formatWebString(text);
    xAnnouncement.setAttribute(XMLBuilder.A_TEXT, text);
    xAnnouncement.setAttribute(XMLBuilder.A_OLDTEXT, announcement.getEditInfo());
    return xAnnouncement;
  }

  public Element buildHistorySubtree(Document xml, Announcement announcement) {
    Element xOldAnnouncement, xHistory =
        xml.createElement(XMLBuilder.TAG_ANNOUNCEMENTHISTORY);
    Collection c = announcement.getAnnouncementHistory();
    Iterator i = c.iterator();
    String text;
    while (i.hasNext()) {
      OldAnnouncement oldAnnounce = (OldAnnouncement) i.next();
      text = oldAnnounce.getText();
      // if (formatForWeb)text = Util.formatWebString(text);
      xOldAnnouncement = xml.createElement(XMLBuilder.TAG_ANNOUNCEMENT);
      xOldAnnouncement.setAttribute(XMLBuilder.A_ID, oldAnnounce.toString());
      xOldAnnouncement.setAttribute(XMLBuilder.A_TEXT, text);
      xHistory.appendChild(xOldAnnouncement);
    }
    return xHistory;
  }
}
