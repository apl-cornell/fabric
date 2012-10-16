package cms.www.xml;

import fabric.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.model.*;

/**
 * Builds an XML subtree with information about a given user with staff
 * privileges for at least one course Created 3 / 28 / 05
 * 
 * @author Evan
 */
public class StaffXMLBuilder {
  private CourseXMLBuilder courseXMLBuilder;

  /**
   * Generate an XML subtree with a list of the courses the given principal is
   * staff for
   * 
   * @param p
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @return A list of courses, with short info, under a TAG_STAFFCOURSES
   *         element
   * @throws RemoteException
   */
  public Element buildCourseListSubtree(User user, Document xml, Semester semester) {
    Element xStaffCourses = xml.createElement(XMLBuilder.TAG_STAFFCOURSES);
    Iterator i = semester == null ?
        user.findStaffCourses().iterator() :
        user.findStaffCoursesBySemester(semester).iterator();
    while (i.hasNext()) {
      xStaffCourses.appendChild(courseXMLBuilder.buildShortSubtree(user, xml,
          (Course) i.next()));
    }
    return xStaffCourses;
  }

  public StaffXMLBuilder(XMLBuilder builder) {
    this.courseXMLBuilder = builder.courseXMLBuilder;
  }
}
