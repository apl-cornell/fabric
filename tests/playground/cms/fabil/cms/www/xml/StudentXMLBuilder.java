package cms.www.xml;

import fabric.util.*;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.DateTimeUtil;
import cms.auth.Principal;
import cms.model.*;

/**
 * Builds an XML subtree with information about a given student Created 3 / 28 /
 * 05
 * 
 * @author Evan
 */
public class StudentXMLBuilder {
  private XMLBuilder xmlBuilder;

  public StudentXMLBuilder(XMLBuilder builder) {
    this.xmlBuilder = builder;
  }

  /**
   * Generate an XML subtree with a list of the courses the given principal is a
   * student in
   * 
   * @param p
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @return A list of courses, with short info, under a TAG_STUDENTCOURSES
   *         element
   * @throws FinderException
   */
  public Element buildCourseListSubtree(User user, Document xml, Semester semester) {
    Element xStudentCourses = xml.createElement(XMLBuilder.TAG_STUDENTCOURSES);
    
    Iterator i = semester == null ?
        user.findStudentCourses().iterator() :
        user.findStudentCoursesBySemester(semester).iterator();
    while (i.hasNext()) {
      xStudentCourses.appendChild(xmlBuilder.courseXMLBuilder.buildShortSubtree(user, xml,
          (Course) i.next()));
    }
    return xStudentCourses;
  }

  /**
   * Generate an XML subtree with info on assignments posted in classes the
   * given principal is in
   * 
   * @param p
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @return An element holding a list of assignments
   * @throws FinderException
   */
  public Element buildDueAsgnListSubtree(User p, Document xml, Semester semester) {
    Element xAssignments = xml.createElement(XMLBuilder.TAG_ALLDUEASSIGNMENTS);
    Collection c = semester == null ?
        p.findAssignmentsByDate(new Date())
        : p.findAssignmentsByDateAndSemester(new Date(), semester);

    for (Iterator i = c.iterator(); i.hasNext();) {
      Assignment assignment = (Assignment) i.next();
      Element xAssignment =
          xmlBuilder.assignmentXMLBuilder.buildShortSubtree(xml, assignment);
      xAssignment.setAttribute(XMLBuilder.A_DUEDATE,
          DateTimeUtil.formatCountdown(new Date(), assignment.getDueDate()));
      xAssignment.setAttribute(XMLBuilder.A_ASSIGNID, assignment.toString());
      xAssignment.setAttribute(XMLBuilder.A_STATUS, assignment.getStatus());
      xAssignment.setAttribute(XMLBuilder.A_COURSEID,
          assignment.getCourse().toString());
      xAssignments.appendChild(xAssignment);
    }
    return xAssignments;
  }
}
