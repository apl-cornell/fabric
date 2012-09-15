package cms.www.xml;

import fabric.util.Collection;
import fabric.util.Iterator;
import fabric.util.LinkedList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import cms.www.util.Profiler;
import cms.www.util.StringUtil;
import cms.model.*;

public class ViewStudentsXMLBuilder {
  
  XMLBuilder xmlBuilder;
  
  public ViewStudentsXMLBuilder(XMLBuilder builder) {
    this.xmlBuilder = builder;
  }
  
  public void buildSelectedStudentList(Course course, Document xml,
      Element studentsNode, Collection/*User*/ selectedUsers) {
    Iterator students = course.getStudents().iterator();
    while (students.hasNext()) {
      Student student = (Student) students.next();
      User    user    = student.getUser();
      
      Element xStudent = xml.createElement(user.getNetID());
      Float totalScore = student.getTotalScore();
      xStudent.setAttribute(XMLBuilder.A_TOTALSCORE, (totalScore == null ? "" : StringUtil.roundToOne(String.valueOf(totalScore.floatValue()))));
      xStudent.setAttribute(XMLBuilder.A_FINALGRADE, (student.getFinalGrade() == null ? "" : student.getFinalGrade()));
      xStudent.setAttribute(XMLBuilder.A_FIRSTNAME, user.getFirstName());
      xStudent.setAttribute(XMLBuilder.A_LASTNAME, user.getLastName());
      xStudent.setAttribute(XMLBuilder.A_NETID, user.getNetID());
      if (selectedUsers.contains(user)) {
        xStudent.setAttribute(XMLBuilder.A_ENROLLED, Student.ENROLLED);
        studentsNode.appendChild(xStudent);
      }
    }
  }

  public void buildStudentList(Course course, Document xml, Element studentsNode) {
    Iterator students = course.getStudents().iterator();
    java.util.LinkedList nonenrolled = new java.util.LinkedList();
    while (students.hasNext()) {
      Student student = (Student) students.next();
      User    user    = student.getUser();
      
      String section = student.getSection();
      Element xStudent = xml.createElement(user.getNetID());
      Float totalScore = student.getTotalScore();
      xStudent.setAttribute(XMLBuilder.A_TOTALSCORE, (totalScore == null ? "" : StringUtil
          .roundToOne(String.valueOf(totalScore.floatValue()))));
      xStudent.setAttribute(XMLBuilder.A_FINALGRADE, (student.getFinalGrade() == null ? ""
          : student.getFinalGrade()));
      xStudent.setAttribute(XMLBuilder.A_FIRSTNAME, user.getFirstName());
      xStudent.setAttribute(XMLBuilder.A_LASTNAME, user.getLastName());
      xStudent.setAttribute(XMLBuilder.A_NETID, user.getNetID());
      xStudent.setAttribute(XMLBuilder.A_SECTION, section);
      if (student.getStatus().equals(Student.ENROLLED)) {
        xStudent.setAttribute(XMLBuilder.A_ENROLLED, Student.ENROLLED);
        studentsNode.appendChild(xStudent);
      } else {
        // save for later
        xStudent.setAttribute(XMLBuilder.A_ENROLLED, Student.DROPPED);
        nonenrolled.addLast(xStudent);
        continue;
      }

      Iterator assigns = course.getAssignments().iterator();
      while (assigns.hasNext()) {
        Assignment assign = (Assignment) assigns.next();
        
        Element xGrade = xml.createElement(XMLBuilder.A_ID + assign.toString());
        xGrade.setAttribute(XMLBuilder.A_ASSIGNID, assign.toString());
        
        Group group = assign.findGroup(user);
        if (group != null)
          xGrade.setAttribute(XMLBuilder.A_GROUPID, group.toString());
        
        Grade grade = assign.findGrade(student);
        if (grade != null)
          xGrade.setAttribute(XMLBuilder.A_SCORE,
                              StringUtil.roundToOne(String.valueOf(grade.getGrade())));
        
        if (grade != null && grade.getGrade() != null &&
            grade.getGrade().floatValue() > assign.getMaxScore())
          xGrade.setAttribute(XMLBuilder.A_OVERMAX, "true");
        
        Iterator regrades = assign.findRegradeRequests(group).iterator();
        RegradeRequest regrade = null;
        while (regrades.hasNext()) {
          RegradeRequest next = (RegradeRequest) regrades.next();
          if (regrade == null || next.getStatus().equals(RegradeRequest.PENDING))
            regrade = next;
        }
        if (regrade != null)
          xGrade.setAttribute(XMLBuilder.A_REGRADE, regrade.getStatus());

        xStudent.appendChild(xGrade);
      }
    }
    
    // add on all of the non-enrolled students
    java.util.Iterator i = nonenrolled.iterator();
    while (i.hasNext())
      studentsNode.appendChild((Element) i.next());
  }
  
  public void buildAssignmentList(Course course, Document xml, Element assignsNode) {
    Iterator assigns = course.getAssignments().iterator();
    while (assigns.hasNext()) {
      Assignment assign = (Assignment) assigns.next();
      Element xAssign = xml.createElement(XMLBuilder.A_ID + assign.toString());
      xAssign.setAttribute(XMLBuilder.A_NAMESHORT, assign.getNameShort());
      assignsNode.appendChild(xAssign);
    }
  }

  public void buildStudentsPage(Course course, Document xml) {
    Profiler.enterMethod("ViewStudentsXMLBuilder.buildStudentsPage",
        "CourseID: " + course.toString());
    Element root = (Element) xml.getFirstChild();
    Element studentsNode = xml.createElement(XMLBuilder.TAG_STUDENTS);
    Element assignsNode = xml.createElement(XMLBuilder.TAG_STUDENTASSIGNS);
    buildStudentList(course, xml, studentsNode);
    buildAssignmentList(course, xml, assignsNode);
    root.appendChild(studentsNode);
    root.appendChild(assignsNode);
    Profiler.exitMethod("ViewStudentsXMLBuilder.buildStudentsPage",
        "CourseID: " + course.toString());
  }

}
