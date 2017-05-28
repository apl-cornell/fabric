package cms.www.xml;

import fabric.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.Profiler;

import cms.model.*;

/**
 * Builds an XML subtree with requested information on CMS systemwide settings
 * or on usage statistics for the current semester Created 3 / 27 / 05
 * 
 * @author Evan
 */
public class SystemXMLBuilder {
  private XMLBuilder xmlBuilder;

  public SystemXMLBuilder(XMLBuilder builder) {
    this.xmlBuilder = builder;
  }

  /**
   * Generate an XML subtree with a list of legal file types for upload/download
   * 
   * @param xml
   *          The Document to place this element on
   * @return A XMLBuilder.TAG_FILETYPES element with child nodes
   * @throws FinderException
   */
  public Element buildFiletypeListSubtree(Document xml) {
    Element xFileTypes = xml.createElement(XMLBuilder.TAG_FILETYPES);
    Collection fileTypes = xmlBuilder.database.getFileTypes();
    Iterator i = fileTypes.iterator();
    while (i.hasNext()) {
      Element xItem = xml.createElement(XMLBuilder.TAG_ITEM);
      xItem.setAttribute(XMLBuilder.A_TYPE, (String) i.next());
      xFileTypes.appendChild(xItem);
    }
    return xFileTypes;
  }

  /**
   * Generate an XML subtree with a list of NetIDs to use to debug CMS
   * 
   * @param xml
   *          The Document to place this element on
   * @return A XMLBuilder.TAG_DEBUGIDS element with child nodes
   * @throws FinderException
   */
  public Element buildDebugNetIDListSubtree(Document xml) {
    Profiler.enterMethod("SystemXMLBuilder.buildDebugNetIDListSubtree", "");
    Element xDebugids = xml.createElement(XMLBuilder.TAG_DEBUGIDS);
    Element xItem = xml.createElement(XMLBuilder.TAG_ITEM);
    xItem.setAttribute(XMLBuilder.A_DEBUGID, "acm22");
    xDebugids.appendChild(xItem);
    xItem = xml.createElement(XMLBuilder.TAG_ITEM);
    xItem.setAttribute(XMLBuilder.A_DEBUGID, "kv48");
    xDebugids.appendChild(xItem);
    xItem = xml.createElement(XMLBuilder.TAG_ITEM);
    xItem.setAttribute(XMLBuilder.A_DEBUGID, "mdg39");
    xDebugids.appendChild(xItem);
    xItem = xml.createElement(XMLBuilder.TAG_ITEM);
    xItem.setAttribute(XMLBuilder.A_DEBUGID, "ml103");
    xDebugids.appendChild(xItem);
    xItem = xml.createElement(XMLBuilder.TAG_ITEM);
    xItem.setAttribute(XMLBuilder.A_DEBUGID, "xq24");
    xDebugids.appendChild(xItem);
    /*
     * Collection users = database.userHome().findAll(); //users =
     * database.getAllUsers(); Iterator i = users.iterator(); while
     * (i.hasNext()) { UserLocal user = (UserLocal) i.next(); Element xItem =
     * xml.createElement(XMLBuilder.TAG_ITEM); xItem.setAttribute(XMLBuilder.A_DEBUGID,
     * user.getUserID()); xDebugids.appendChild(xItem); }
     */
    Profiler.exitMethod("SystemXMLBuilder.buildDebugNetIDListSubtree", "");
    return xDebugids;
  }

  /**
   * Generate an XML subtree with system usage statistics for the current
   * semester
   * 
   * @param xml
   *          The Document on which to place this element
   * @return A XMLBuilder.TAG_SYSTEMDATA element with properties set
   * @throws FinderException
   */
  public Element buildSystemDataSubtree(Document xml) {
    Element xSysData = xml.createElement(XMLBuilder.TAG_SYSTEMDATA);
    // number of courses with students enrolled in them
    Semester sem = xmlBuilder.database.getCurrentSemester();
    Collection courses = sem.getCourses();
    int numCourses = 0;
    Iterator iter = courses.iterator();
    HashSet students = new HashSet(); // holds netIDs (Strings)
    while (iter.hasNext()) {
      Course course = (Course) iter.next();
      Collection crsStuds = course.findActiveStudents();
      if (crsStuds.size() > 0) numCourses++;
      Iterator iter2 = crsStuds.iterator();
      while (iter2.hasNext())
        // add netIDs; sets contain no duplicates, so dups will be filtered out
        students.add(((Student) iter2.next()).getUser().getNetID());
    }
    // number of courses for the currently selected semester
    xSysData.setAttribute(XMLBuilder.A_COURSES, Integer.toString(numCourses));
    // number of students active in one or more courses
    xSysData.setAttribute(XMLBuilder.A_ENROLLMENT, Integer.toString(students.size()));
    // number of users of all kinds in the system
    Collection users = xmlBuilder.database.getAllUsers();
    xSysData.setAttribute(XMLBuilder.A_USERS, Integer.toString(users.size()));
    return xSysData;
  }
}
