package cms.www.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.w3c.dom.*;

import cms.www.util.Profiler;
import cms.www.util.StringUtil;
import cms.auth.Principal;
import cms.model.*;

/**
 * @author jfg32
 */
public class StudentGradesXMLBuilder {

  private XMLBuilder xmlBuilder;
  
  public StudentGradesXMLBuilder(XMLBuilder builder) {
    this.xmlBuilder = builder;
  }

  public void buildStudentGradesTree(User p, Document xml, User user,
      Course course) {
    Profiler.enterMethod("StudentGradesXMLBuilder.buildStudentGradesTree",
        "CourseID: " + course.toString());
    Element root = (Element) xml.getFirstChild();
    Element xUser = xml.createElement(XMLBuilder.TAG_GRADESTUDENT);
    xUser.setAttribute(XMLBuilder.A_NETID, user.getNetID());
    xUser.setAttribute(XMLBuilder.A_FIRSTNAME, user.getFirstName());
    xUser.setAttribute(XMLBuilder.A_LASTNAME, user.getLastName());
    root.appendChild(xUser);

    List groups = addGroups(user, xml, course);
    addGrades(p, xml, user, course);
    xmlBuilder.gradingXMLBuilder.addSubmittedFiles(xml, groups);
    xmlBuilder.gradingXMLBuilder.addRegradeInfo(xml, groups);
    xmlBuilder.gradingXMLBuilder.addGradeLogs(xml, course, groups);
    Profiler.exitMethod("StudentGradesXMLBuilder.buildStudentGradesTree",
        "CourseID: " + course.toString());
  }

  public void addAssignments(Document xml, Course course, Map assignToGroup) {
    Profiler.enterMethod("StudentGradesXMLBuilder.addAssignments", "");
    Element root = (Element) xml.getFirstChild();
    Collection assigns = course.getAssignments();
    Iterator i = assigns.iterator();
    while (i.hasNext()) {
      Assignment assign = (Assignment) i.next();

      if (assign.getType() != Assignment.SURVEY) {
        Group group = (Group) assignToGroup.get(assign);
        Element xGroup =
            (Element) root.getElementsByTagNameNS(
                XMLBuilder.TAG_GROUP
                    + (group == null ? "00" : group.toString()),
                XMLBuilder.TAG_GROUP).item(0);
        if (xGroup != null) {
          xGroup.setAttribute(XMLBuilder.A_ASSIGNNAME, assign.getName());
          xGroup.setAttribute(XMLBuilder.A_NAMESHORT, assign.getNameShort());
          xGroup.setAttribute(XMLBuilder.A_MAXSCORE, StringUtil
              .trimTrailingZero(String.valueOf(assign.getMaxScore())));
          Element xTotalProb = xml.createElement(XMLBuilder.TAG_TOTALPROBLEM);
          xTotalProb.setAttribute(XMLBuilder.A_SUBPROBID, "0");
          xTotalProb.setAttribute(XMLBuilder.A_SUBPROBNAME, "Total");
          xTotalProb.setAttribute(XMLBuilder.A_MAXSCORE, StringUtil
              .trimTrailingZero(String.valueOf(assign.getMaxScore())));
          
          Iterator subProbs = assign.getSubProblems().iterator();
          while (subProbs.hasNext()) {
            SubProblem subProb = (SubProblem) subProbs.next();
            Element xSubProb = xml.createElementNS(
                XMLBuilder.TAG_SUBPROBLEM + subProb.toString(),
                XMLBuilder.TAG_SUBPROBLEM);
            xSubProb.setAttribute(XMLBuilder.A_SUBPROBID,
                                  subProb.toString());
            xSubProb.setAttribute(XMLBuilder.A_SUBPROBNAME,
                                  subProb.getName());
            xSubProb.setAttribute(XMLBuilder.A_MAXSCORE,
                                  StringUtil.trimTrailingZero(String.valueOf(subProb.getMaxScore())));
            xGroup.appendChild(xSubProb);

          }
          
          xGroup.appendChild(xTotalProb);
        }
      }

    }

    Profiler.exitMethod("StudentGradesXMLBuilder.addAssignments", "");
  }

  /**
   * @return A List of Groups
   */
  public List/*Group*/ addGroups(User user, Document xml, Course course) {
    Profiler.enterMethod("StudentGradesXMLBuilder.addGroups", "");
    Element root = (Element) xml.getFirstChild();
    Iterator groups = course.findGroupsByUser(user).iterator();
    List result = new ArrayList();
    
    Iterator members = course.findGroupMembersByUser(user).iterator();
    Map assignToGroup = new HashMap();
    int count = 0;
    while (groups.hasNext()) {
      Group g = (Group) groups.next();
      Assignment assign = g.getAssignment();
          
      if (assign.getType() != Assignment.SURVEY) {
        result.add(g);
        Element xGroup =
            xml.createElementNS(XMLBuilder.TAG_GROUP + g.toString(),
                                XMLBuilder.TAG_GROUP);
        xGroup.setAttribute(XMLBuilder.A_GROUPID,  g.toString());
        xGroup.setAttribute(XMLBuilder.A_ASSIGNID, g.getAssignment().toString());

        assignToGroup.put(g.getAssignment(), g);
        root.appendChild(xGroup);
      }
    }
    while (members.hasNext()) {
      GroupMember m = (GroupMember) members.next();
      Element xGroup =
          (Element) root.getElementsByTagNameNS(
              XMLBuilder.TAG_GROUP + m.getGroup().toString(), XMLBuilder.TAG_GROUP)
              .item(0);
      if (xGroup != null) {
        Element xMember =
            xml.createElementNS(XMLBuilder.TAG_MEMBER + m.getStudent().getUser().getNetID(),
                XMLBuilder.TAG_MEMBER);
        xMember.setAttribute(XMLBuilder.A_NETID, m.getStudent().getUser().getNetID());
        xGroup.appendChild(xMember);
      }
    }
    xmlBuilder.groupXMLBuilder.addGroupMemberNames(xml, course);
    addAssignments(xml, course, assignToGroup);
    addSubmissions(xml, course, assignToGroup);
    Profiler.exitMethod("StudentGradesXMLBuilder.addGroups", "");
    return result;
  }

  public void addGrades(User p, Document xml, User user, Course course) {
    Profiler.enterMethod("StudentGradesXMLBuilder.addGrades", "");
    Element root = (Element) xml.getFirstChild();
    Iterator grades = course.findRecentGradesByUser(user, p.isAdminPrivByCourse(course), p).iterator();
    while (grades.hasNext()) {
      Grade grade = (Grade) grades.next();
      Assignment assign = grade.getAssignment();
      Group group = assign.findGroup(user);
      Element xGroup = (Element) root.getElementsByTagNameNS(XMLBuilder.TAG_GROUP + group.toString(),
              XMLBuilder.TAG_GROUP).item(0);
      if (xGroup != null) {
        Element xMember =
            (Element) xGroup
                .getElementsByTagNameNS(
                    XMLBuilder.TAG_MEMBER + grade.getUser().getNetID(),
                    XMLBuilder.TAG_MEMBER).item(0);
        if (xMember != null) {
          Element xGrade = xml.createElementNS(
              XMLBuilder.TAG_GRADE + 
                (grade.getSubProblem() == null ? "null" : 
                  grade.getSubProblem().toString()),
              XMLBuilder.TAG_GRADE);
          xGrade.setAttribute(XMLBuilder.A_SCORE, 
                              StringUtil.trimTrailingZero(String.valueOf(grade.getGrade())));
          xGrade.setAttribute(XMLBuilder.A_SUBPROBID,
              grade.getSubProblem() == null ? "null" : 
                grade.getSubProblem().toString());
          xMember.appendChild(xGrade);
        }
      }
    }
    Profiler.exitMethod("StudentGradesXMLBuilder.addGrades", "");
  }

  /**
   * find the element in xml corresponding to each group this student has been
   * in for the given course, and for each group, add one child node for each of
   * its submissions
   * 
   * @param assignToGroup
   *          A map from assignment IDs in the given course to this student's
   *          group ID for that assignment
   */
  public void addSubmissions(Document xml, Course course, Map assignToGroup) {
    Profiler.enterMethod("StudentGradesXMLBuilder.addSubmissions", "");
    Element root = (Element) xml.getFirstChild();
    Iterator submissions = course.getRequiredSubmissions().iterator();
    while (submissions.hasNext()) {
      RequiredSubmission submission = (RequiredSubmission) submissions.next();
      Group group = (Group) assignToGroup.get(submission.getAssignment());
      Element xGroup = (Element) root.getElementsByTagNameNS(
          XMLBuilder.TAG_GROUP + group.toString(),
          XMLBuilder.TAG_GROUP).item(0);
      if (xGroup != null) {
        Element xSubmission = xml.createElement(XMLBuilder.TAG_SUBMISSION);
        xSubmission.setAttribute(XMLBuilder.A_ID,       submission.toString());
        xSubmission.setAttribute(XMLBuilder.A_NAME,     submission.getSubmissionName());
        xSubmission.setAttribute(XMLBuilder.A_GROUPID,  group.toString());
        xSubmission.setAttribute(XMLBuilder.A_ASSIGNID, submission.getAssignment().toString());
        xGroup.appendChild(xSubmission);
      }
    }
    Profiler.exitMethod("StudentGradesXMLBuilder.addSubmissions", "");
  }

}
