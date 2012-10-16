package cms.www.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;
import cms.www.util.StringUtil;

import cms.auth.Principal;
import cms.www.xml.XMLBuilder;
import cms.model.*;

/*
 * This class is responsible for building the XML document used to render the
 * staff Groups view for any assignment. Information on this page may include a
 * full listing of groups and their members, current grading assignments, any
 * available grades, and information about extensions.
 */
public class AssignmentGroupsXMLBuilder {
  public AssignmentGroupsXMLBuilder(XMLBuilder builder) {
  }

  /**
   * Build the complete XML output
   */
  public void buildGroupGradingPage(User user, Assignment assign,
      Document xml) {
    Profiler
        .enterMethod("AssignmentGroupsXMLBuilder.buildGroupGradingPage", "");
    Element root = (Element) xml.getFirstChild();
    Element groupsNode = xml.createElement(XMLBuilder.TAG_GROUPS);
    Element subProbsNode = xml.createElement(XMLBuilder.TAG_SUBPROBS);
    Course course = assign.getCourse();
    boolean adminPriv = false, gradePriv = false, groupsPriv = false, assignedGraders =
        false;
    adminPriv = user.isAdminPrivByCourse(course);
    gradePriv = user.isGradesPrivByCourse(course);
    groupsPriv = user.isGroupsPrivByCourse(course);
    assignedGraders = assign.getAssignedGraders();
    // Can this user view all entries on this page, or should they be
    // restricted?
    boolean fullAccess =
        adminPriv || groupsPriv || (!assignedGraders && gradePriv);
    buildGroups(user, assign, fullAccess, xml, groupsNode);
    Map subProbScores = buildSubproblems(assign, xml, subProbsNode);
    buildAssignedGraders(user, assign, xml, groupsNode);
    //buildGroupGrades(user, assign, xml, groupsNode, adminPriv, subProbScores);
    buildRegrades(assign, xml, groupsNode);
    buildStaffGraders(course, xml);
    root.appendChild(groupsNode);
    root.appendChild(subProbsNode);
    Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildGroupGradingPage", "");
  }

  /**
   * Add nodes to the XML document for each group with active members and
   * subnodes under each group for the different group members
   * 
   * @param fullaccess
   *          Whether the principal is allowed to see all entries (basically,
   *          whether the principal has at least groups privilege)
   */
  public void buildGroups(User user, Assignment assignment,
      boolean fullAccess, Document xml, Element groupsNode) {
    Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildGroups", "");
    int submissions = 0, partial = 0, complete = 0;
    int numOfAssignedFiles = assignment.getNumassignedfiles();
    Iterator groupmems = null;
    if (fullAccess)
      groupmems = assignment.findActiveGroupMembers().iterator();
    else
      groupmems = assignment.findActiveAssignedGroupMembers(user).iterator();
    Set lateGroups = assignment.findLateGroups();
    while (groupmems.hasNext()) {
      GroupMember groupmem = (GroupMember) groupmems.next();
      Group group = groupmem.getGroup();

      Element xGroup =
          (Element) groupsNode.getElementsByTagNameNS(
              XMLBuilder.TAG_GROUP + group.toString(),
              XMLBuilder.TAG_GROUP).item(0);
      if (xGroup == null) {
        // Count submissions only the first time we see a group
        if (numOfAssignedFiles == 0
            || group.getRemainingSubmissions() < numOfAssignedFiles) {
          submissions++;
          if (group.getRemainingSubmissions() == 0) {
            complete++;
          } else {
            partial++;
          }
        }
        xGroup = xml.createElementNS(XMLBuilder.TAG_GROUP + group.toString(),
                                     XMLBuilder.TAG_GROUP);
        xGroup.setAttribute(XMLBuilder.A_GROUPID, group.toString());
        xGroup.setAttribute(XMLBuilder.A_REMAININGSUBS,
                            String.valueOf(group.getRemainingSubmissions()));
        if (group.getExtension() != null) {
          xGroup.setAttribute(XMLBuilder.A_EXTENSION,
                              DateTimeUtil.DATE.format(group.getExtension()));
          xGroup.setAttribute(XMLBuilder.A_EXTTIME,
                              DateTimeUtil.TIME.format(group.getExtension()));
          xGroup.setAttribute(XMLBuilder.A_EXTVAL,
                              String.valueOf(group.getExtension().getTime()));
        }
        groupsNode.appendChild(xGroup);
        if (lateGroups.contains(group)) {
          xGroup.setAttribute(XMLBuilder.A_LATESUBMISSION, "true");
        }
      }
      Student student = groupmem.getStudent();
      String section = student.getSection();

      Element xGroupMember = xml.createElement(XMLBuilder.TAG_MEMBER);
      xGroupMember.setAttribute(XMLBuilder.A_NETID, 
                                student.getUser().getNetID());
      xGroupMember.setAttribute(XMLBuilder.A_FIRSTNAME,
                                student.getUser().getFirstName());
      xGroupMember.setAttribute(XMLBuilder.A_LASTNAME,
                                student.getUser().getLastName());
      xGroupMember.setAttribute(XMLBuilder.A_SECTION, section);
      xGroup.appendChild(xGroupMember);
    }
//  Add submissions counts to the group node
        if ( numOfAssignedFiles == 0 ) {                        
                groupsNode.setAttribute(XMLBuilder.A_SUBMISSIONCOUNT, "None");
                groupsNode.setAttribute(XMLBuilder.A_PARTIAL, "");
                groupsNode.setAttribute(XMLBuilder.A_COMPLETE, "");
        } else {
                groupsNode.setAttribute(XMLBuilder.A_SUBMISSIONCOUNT, String.valueOf(submissions));
                groupsNode.setAttribute(XMLBuilder.A_PARTIAL, String.valueOf(partial));
                groupsNode.setAttribute(XMLBuilder.A_COMPLETE, String.valueOf(complete));
        }
       
        buildGroupGrades(assignment, xml, groupsNode);
        
    Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildGroups", "");
  }

  /**
   * Add information about the non-hidden subproblems for this assignment to the
   * given subProbsNode element. Returns a mapping from SubProblem ->
   * MaxProblemScore (Float).
   */
  public Map buildSubproblems(Assignment assignment, Document xml, Element subProbsNode) {
    Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildSubproblems", "");
    HashMap result = new HashMap();

    // sort the subproblems by their order
    // TreeMap orderToSubProblem = new TreeMap();
    Iterator subProbs = assignment.getSubProblems().iterator();

    /*
     * while(subProbs.hasNext()) { SubProblemLocal sp = (SubProblemLocal)
     * subProbs.next(); orderToSubProblem.put(new Integer(sp.getOrder()), sp); }
     * subProbs = orderToSubProblem.keySet().iterator();
     */
    while (subProbs.hasNext()) {
      SubProblem subProb = (SubProblem) subProbs.next();
      result.put(subProb, new Float(subProb.getMaxScore()));
      Element xSubProb = xml.createElement(XMLBuilder.TAG_SUBPROBLEM);
      xSubProb.setAttribute(XMLBuilder.A_SUBPROBID, subProb.toString());
      xSubProb.setAttribute(XMLBuilder.A_NAME, subProb.getName());
      xSubProb.setAttribute(XMLBuilder.A_SCORE,
                            StringUtil.roundToOne(String.valueOf(subProb.getMaxScore())));
      xSubProb.setAttribute(XMLBuilder.A_ORDER,
                            Integer.toString(subProb.getOrder()));
      xSubProb.setAttribute(XMLBuilder.A_HIDDEN,
                            Boolean.toString(subProb.getHidden()));
      subProbsNode.appendChild(xSubProb);
    }
    Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildSubproblems", "");
    return result;
  }

  public void buildAssignedGraders(User p, Assignment assign,
      Document xml, Element groupsNode) {
    Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildAssignedGraders", "");
    Iterator assignedTos = assign.getGroupAssignedTos().iterator();

    while (assignedTos.hasNext()) {
      GroupAssignedTo assignedTo = (GroupAssignedTo) assignedTos.next();
      Element xGroup =
          (Element) groupsNode.getElementsByTagNameNS(
              XMLBuilder.TAG_GROUP + assignedTo.getGroup().toString(),
              XMLBuilder.TAG_GROUP).item(0);
      if (xGroup != null && assignedTo.getGrader().getNetID() != null) {
        Element xAssignedTo =
            xml.createElementNS(XMLBuilder.TAG_ASSIGNEDTO
                + assignedTo.getSubProblem().toString(), XMLBuilder.TAG_ASSIGNEDTO);
        // Set this attribute if the Principal is assigned to grade this group
        // for any subproblem
        if (p.getNetID().equals(assignedTo.getGrader().getNetID())) {
          xGroup.setAttribute(XMLBuilder.A_CANGRADE, "true");
        }
        xAssignedTo.setAttribute(XMLBuilder.A_SUBPROBID, assignedTo.getSubProblem().toString());
        xAssignedTo.setAttribute(XMLBuilder.A_NETID,     assignedTo.getGrader().getNetID());
        xAssignedTo.setAttribute(XMLBuilder.A_FIRSTNAME, assignedTo.getGrader().getFirstName());
        xAssignedTo.setAttribute(XMLBuilder.A_LASTNAME,  assignedTo.getGrader().getLastName());
        xGroup.appendChild(xAssignedTo);
      }
    }
    Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildAssignedGraders", "");
  }

  /*public void buildGroupGrades(User user, Assignment assignment, Document xml,
      Element groupsNode, boolean adminPriv, Map subProbScores) {
    Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildGroupGrades", "");
    Iterator groupGrades = assignment.findGroupGradesByGrader(user, adminPriv, subProbScores.size()).iterator();
    Collection overMaxScore = new ArrayList();
    while (groupGrades.hasNext()) {
      GroupGrade groupGrade = (GroupGrade) groupGrades.next();
      Element xGroup =
          (Element) groupsNode.getElementsByTagNameNS(
              XMLBuilder.TAG_GROUP + groupGrade.getGroup().toString(),
              XMLBuilder.TAG_GROUP).item(0);
      if (xGroup != null) {
        Element xGrade =
            xml.createElementNS(XMLBuilder.TAG_GRADE
                + groupGrade.getSubProblem().toString(), XMLBuilder.TAG_GRADE);
        
        xGrade.setAttribute(XMLBuilder.A_SUBPROBID,
                            groupGrade.getSubProblem().toString());
        xGrade.setAttribute(XMLBuilder.A_SCORE,
                            StringUtil.roundToOne(String.valueOf(groupGrade.getScore())));
        if (groupGrade.getAveraged()) {
          xGrade.setAttribute(XMLBuilder.A_ISAVERAGE, "true");
        }
        if (groupGrade.getSubProblem() != null) // check max subproblem grade
        {
          float groupScore = groupGrade.getScore();
          if (((Float) subProbScores.get(groupGrade.getSubProblem())).floatValue() < groupScore) {
            xGrade.setAttribute(XMLBuilder.A_OVERMAX, "true");
            overMaxScore.add(groupGrade.getGroup().toString());
          }
        } else // check max assignment grade
        {
          if (groupGrade.getScore() > assignment.getMaxScore())
            xGrade.setAttribute(XMLBuilder.A_OVERMAX, "true");
        }
        xGroup.appendChild(xGrade);
      }
    }
    // Flag any groups' total scores which had over max score on at least one
    // subproblem
    Iterator i = overMaxScore.iterator();
    while (i.hasNext()) {
      long groupID = ((Long) i.next()).longValue();
      Element xGroup =
          (Element) groupsNode.getElementsByTagNameNS(
              XMLBuilder.TAG_GROUP + groupID, XMLBuilder.TAG_GROUP).item(0);
      if (xGroup != null) {
        Element xGrade =
            (Element) xGroup.getElementsByTagNameNS(XMLBuilder.TAG_GRADE + 0,
                XMLBuilder.TAG_GRADE).item(0);
        if (xGrade != null) {
          xGrade.setAttribute(XMLBuilder.A_OVERMAX, "true");
        }
      }
    }
    Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildGroupGrades", "");
  } */
  
  //TODO if one member has higher than max score and the other has null
  //  then the average could be less than max score, what to do?
  public static void buildGroupGrades(Assignment assign, Document xml, Element groupsNode) {
    Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildGroupGrades", "");
    
    // build a mapping between each subproblem and its total score by all group memebers
    HashMap subproblemGradeMap = new HashMap();
    HashMap maxScoreMap = new HashMap();
    Map lastGradeMap = new HashMap();
    Object nullKey = new Integer(0);
    
    for (Iterator i = assign.getGrades().iterator(); i.hasNext();) {
      Grade g = (Grade)i.next();
      if (g.getSubProblem() == null) {
        lastGradeMap.put(g.getUser().getNetID() + "_null", g.getGrade());
      } else {
        lastGradeMap.put(g.getUser().getNetID() + "_" + g.getSubProblem().toString(), g.getGrade());
      }
    }
    
    
    // collect all subproblems for this assignment
    Collection subproblems = assign.getSubProblems();
    Iterator si = subproblems.iterator();
    while (si.hasNext()) {
      SubProblem s = (SubProblem) si.next();
      maxScoreMap.put(s, new Float(s.getMaxScore()));
      subproblemGradeMap.put(s, null);
    }
    
    // add the other subproblem with id 0 for total score
    subproblemGradeMap.put(nullKey, null);
    Iterator groups = assign.getGroups().iterator();
    while (groups.hasNext()) {
      Group g = (Group)groups.next();
      Collection m = g.getMembers();
      Iterator members = m.iterator();
      
      while (members.hasNext()) {
        GroupMember member = (GroupMember) members.next();
        String status = (String) member.getStudent().getStatus();
        
        if (status == null || status.equals(Student.DROPPED))
                continue;
        
        Iterator sids = subproblemGradeMap.keySet().iterator();
        
        while (sids.hasNext()) {
          Object sid = sids.next();
          String lastGradeKey = member.getStudent().getUser().getNetID() + "_";
          Object key;
          if (sid == nullKey) {
            key = nullKey;
            lastGradeKey += "null";
          } else {
            key = sid;
            lastGradeKey += sid.toString();
          }
          
          Float score = (Float) lastGradeMap.get(lastGradeKey);
          ArrayList subscores = (ArrayList)subproblemGradeMap.get(key);

          if (score == null) {
            continue;
          }
          
          if (subscores == null){
            subscores = new ArrayList();
          }
          
          subscores.add(score);
          subproblemGradeMap.put(key, subscores);
        }
      }
      
      // update the groups xml nodes
      Element xGroup = (Element) groupsNode.getElementsByTagNameNS(
          XMLBuilder.TAG_GROUP + g.toString(), XMLBuilder.TAG_GROUP).item(0);
      Iterator avgIterator = subproblemGradeMap.keySet().iterator();
      while (avgIterator.hasNext()) {
        Object sid = avgIterator.next();
        
        ArrayList grades = (ArrayList) subproblemGradeMap.get(sid);
        Element xGrade = xml.createElementNS(XMLBuilder.TAG_GRADE + sid, 
          XMLBuilder.TAG_GRADE);
        
        float avg = 0f;
        String score = null;
        
        if (grades != null) 
        {
          Iterator gradeIterator = grades.iterator();
          while (gradeIterator.hasNext()) {
                  Float grade = (Float) gradeIterator.next();
                  avg += grade.floatValue();
          }

          avg /= grades.size();
          score = StringUtil.roundToOne(String.valueOf(new Float(avg)));
                                  
          if (sid instanceof Integer) {
            if (grades != null && assign.getMaxScore() < avg)
                        xGrade.setAttribute(XMLBuilder.A_OVERMAX, "true");
          } else {
            Float max = (Float) maxScoreMap.get(sid);
            if (grades != null && max.floatValue() < avg)
                    xGrade.setAttribute(XMLBuilder.A_OVERMAX, "true");
          }
          
          boolean allGradesIdentical = true;
          for (int i = 0; i < grades.size(); i++)
          {
                  Float grade = (Float)grades.get(i);
                  if (Math.abs(grade.floatValue() - avg) > 0.001)
                  {
                          allGradesIdentical = false;
                          break;
                  }
          }
          
          if (grades.size() > 1 && !allGradesIdentical)
                  xGrade.setAttribute(XMLBuilder.A_ISAVERAGE, "true");
          
          xGrade.setAttribute(XMLBuilder.A_SUBPROBID, String.valueOf(sid));
          xGrade.setAttribute(XMLBuilder.A_SCORE, score);
          xGroup.appendChild(xGrade);
        }
        
        // zero out this grade value so that we can process the next group
        subproblemGradeMap.put(sid, null);
      }
    }
    
    Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildGroupGrades", "");
  }

  public void buildRegrades(Assignment assignment, Document xml, Element groupsNode) {
    Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildRegrades", "");
    Iterator regrades = assignment.findRegradeRequests().iterator();
    while (regrades.hasNext()) {
      RegradeRequest regrade = (RegradeRequest) regrades.next();
      Element xGroup =
          (Element) groupsNode
              .getElementsByTagNameNS(
                  XMLBuilder.TAG_GROUP + regrade.getGroup().toString(),
                  XMLBuilder.TAG_GROUP).item(0);
      Element xRegrade =
          (Element) (xGroup == null ? null : xGroup.getElementsByTagName(
              XMLBuilder.TAG_REGRADE).item(0));
      if (xGroup != null && xRegrade == null) {
        xRegrade = xml.createElement(XMLBuilder.TAG_REGRADE);
        xRegrade.setAttribute(XMLBuilder.A_STATUS, regrade.getStatus());
        xGroup.appendChild(xRegrade);
      }
    }
    Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildRegrades", "");
    ;
  }

  public void buildStaffGraders(Course course, Document xml) {
    Profiler.enterMethod("AssignmentGroupsXMLBuilder.buildStaffGraders", "");
    Iterator staff = course.getStaff().iterator();
    Element root = (Element) xml.getFirstChild();
    while (staff.hasNext()) {
      Staff staffmem = (Staff) staff.next();
      User  user     = staffmem.getUser();

      if (staffmem.getAdminPriv() || staffmem.getGradesPriv()) {
        Element xStaff = xml.createElement(XMLBuilder.TAG_GRADER);
        xStaff.setAttribute(XMLBuilder.A_NETID,     user.getNetID());
        xStaff.setAttribute(XMLBuilder.A_FIRSTNAME, user.getFirstName());
        xStaff.setAttribute(XMLBuilder.A_LASTNAME,  user.getLastName());
        root.appendChild(xStaff);
      }
    }
    Profiler.exitMethod("AssignmentGroupsXMLBuilder.buildStaffGraders", "");
  }
}
