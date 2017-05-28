/*
 * Created on Apr 19, 2005
 */
package cms.www.xml;

import java.util.Collection;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;
import cms.www.util.StringUtil;

import cms.model.*;

/**
 * Builds the grading page (where grades are set and regrades/comments are
 * handled) for a collection of groups.
 * 
 * @author Jon
 */
public class GradingXMLBuilder {

  private XMLBuilder xmlBuilder;
  
  public GradingXMLBuilder(final XMLBuilder builder) {
    this.xmlBuilder = builder;
  }

  public void buildGradingSubtree(User user, Document xml, Assignment assignment, Collection groupids) {
    Profiler.enterMethod("GradingXMLBuilder.buildGradingSubtree",
        "AssignmentID: " + assignment.toString());
    addGroups(user.getNetID(), xml, assignment, groupids);
    Profiler.enterMethod("RootBean.addGroupMemberNames", "");
    xmlBuilder.groupXMLBuilder.addGroupMemberNames(xml, assignment.getCourse());
    Profiler.exitMethod("RootBean.addGroupMemberNames", "");
    boolean canGradeAll = user.isAdminPrivByCourse(assignment.getCourse())
                          || !assignment.getAssignedGraders();
    addSubProblems(xml, assignment, canGradeAll);
    addSubmissions(xml, assignment);
    addGrades(user, xml, groupids, assignment);
    if (assignment.getType() == Assignment.QUIZ
        || assignment.getType() == Assignment.SURVEY) {
      addAnswers(user, xml, groupids, assignment);
    }
    addSubmittedFiles(xml, groupids);
    if (assignment.getAssignedGraders()) {
      addAssignedTos(user, xml, assignment);
    }
    addRegradeInfo(xml, groupids);
    addGradeLogs(xml, assignment.getCourse(), groupids);
    Profiler.exitMethod("GradingXMLBuilder.buildGradingSubtree",
        "AssignmentID: " + assignment.toString());
  }

  public void addGroups(String netid, Document xml, Assignment assignment, Collection groupids){
    Profiler.enterMethod("GradingXMLBuilder.addGroups", "");
    Element root = (Element) xml.getFirstChild();
    
    Iterator giter = groupids.iterator();
    while (giter.hasNext()) {
      Group group = (Group) giter.next();      
      
      NodeList query = root.getElementsByTagNameNS(
          XMLBuilder.TAG_GROUP + group.toString(),
          XMLBuilder.TAG_GROUP);
      Element xGroup = null;
      if (query.getLength() == 0) {
        xGroup = xml.createElementNS(
            XMLBuilder.TAG_GROUP + group.toString(),
            XMLBuilder.TAG_GROUP);
        xGroup.setAttribute(XMLBuilder.A_GROUPID, group.toString());
        root.appendChild(xGroup);
      } else {
        xGroup = (Element) query.item(0);
      }

      Iterator members = group.getMembers().iterator();
      while (members.hasNext()) {
        GroupMember member = (GroupMember) members.next();

        Element xMember = xml.createElementNS(
            XMLBuilder.TAG_MEMBER + member.getStudent().getUser().getNetID(),
            XMLBuilder.TAG_MEMBER);
        xMember.setAttribute(XMLBuilder.A_NETID, member.getStudent().getUser().getNetID());
        xGroup.appendChild(xMember);
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addGroups", "");
  }

  public void addGradeLogs(Document xml, Course course, Collection groups) {
    Profiler.enterMethod("GradingXMLBuilder.addGradeLogs", "");
    Iterator details = xmlBuilder.database.findGradeLogDetails(course, groups).iterator();
    while (details.hasNext()) {
      LogDetail d = (LogDetail) details.next();
      Group group = d.getAssignment().findGroup(d.getAffectedUser());
      Element xGroup = (Element) xml.getElementsByTagNameNS(
          XMLBuilder.TAG_GROUP + group.toString(),
          XMLBuilder.TAG_GROUP).item(0);
      if (xGroup != null) {
        Element xGradeLog = xml.createElement(XMLBuilder.TAG_GRADELOG);
        xGradeLog.setAttribute(XMLBuilder.A_DATE, DateTimeUtil.formatDate(d.getLog()
            .getTimestamp()));
        xGradeLog.setAttribute(XMLBuilder.A_TEXT, d.getDetail());
        xGradeLog.setAttribute(XMLBuilder.A_GRADER, d.getLog().getActingNetID());
        xGradeLog.setAttribute(XMLBuilder.A_NETID, d.getAffectedUser().getNetID());
        xGroup.appendChild(xGradeLog);
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addGradeLogs", "");
  }

  public void addSubProblems(Document xml, Assignment assignment, boolean canGradeAll) {
    Profiler.enterMethod("GradingXMLBuilder.addSubProblems", "");
    Iterator subprobs = assignment.getSubProblems().iterator();
    Element root = (Element) xml.getFirstChild();
    Element xMaxScore = xml.createElement(XMLBuilder.TAG_TOTALPROBLEM);
    xMaxScore.setAttribute(XMLBuilder.A_SUBPROBID, "0");
    xMaxScore.setAttribute(XMLBuilder.A_NAME, "Total");
    xMaxScore.setAttribute(XMLBuilder.A_SCORE,
                           StringUtil.roundToOne(String.valueOf(assignment.getMaxScore())));
    root.appendChild(xMaxScore);

    while (subprobs.hasNext()) {
      SubProblem subprob = (SubProblem) subprobs.next();
      int type = subprob.getType();
      Element xSubProb = xml.createElementNS(
          XMLBuilder.TAG_SUBPROBLEM + subprob.toString(),
          XMLBuilder.TAG_SUBPROBLEM);
      xSubProb.setAttribute(XMLBuilder.A_SUBPROBID, subprob.toString());
      xSubProb.setAttribute(XMLBuilder.A_NAME,      subprob.getName());
      xSubProb.setAttribute(XMLBuilder.A_SCORE,
                            StringUtil.roundToOne(String.valueOf(subprob.getMaxScore())));
      xSubProb.setAttribute(XMLBuilder.A_ORDER,
                            Integer.toString(subprob.getOrder()));
      xSubProb.setAttribute(XMLBuilder.A_TYPE,
                            Integer.toString(type));

      if (type == SubProblem.MULTIPLE_CHOICE) {
        // get the choice text of the correct answer
        // if somehow we can't find the correct answer, then this subproblem
        // has no correct answer
        Choice choice = subprob.getAnswerChoice();
        if (choice != null) {
          xSubProb.setAttribute(XMLBuilder.A_CORRECTANSWER, choice.getLetter()
              + ". " + choice.getText());
        }
      }

      root.appendChild(xSubProb);
    }
    if (canGradeAll) {
      NodeList xGroups = root.getElementsByTagName(XMLBuilder.TAG_GROUP);
      for (int i = 0; i < xGroups.getLength(); i++) {
        NodeList xMembers =
            ((Element) xGroups.item(i)).getElementsByTagName(XMLBuilder.TAG_MEMBER);
        for (int j = 0; j < xMembers.getLength(); j++) {
          Element xMember = (Element) xMembers.item(j);
          Iterator subProblems = assignment.getSubProblems().iterator();
          while (subProblems.hasNext()) {
            SubProblem next = (SubProblem) subProblems.next();
            Element xGrade  = xml.createElementNS(
                XMLBuilder.TAG_GRADE + next.toString(),
                XMLBuilder.TAG_GRADE);
            xGrade.setAttribute(XMLBuilder.A_CANGRADE, "true");
            xMember.appendChild(xGrade);
          }
          Element xGrade = xml.createElementNS(XMLBuilder.TAG_GRADE + 0, XMLBuilder.TAG_GRADE);
          xGrade.setAttribute(XMLBuilder.A_CANGRADE, "true");
          xMember.appendChild(xGrade);
        }
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addSubProblems", "");
  }

  public void addSubmissions(Document xml, Assignment assignment) {
    Profiler.enterMethod("GradingXMLBuilder.addSubmissions", "");
    Iterator subs = assignment.getRequiredSubmissions().iterator();
    Element root = (Element) xml.getFirstChild();
    while (subs.hasNext()) {
      RequiredSubmission s = (RequiredSubmission) subs.next();
      Element xSubmission = xml.createElement(XMLBuilder.TAG_SUBMISSION);
      xSubmission.setAttribute(XMLBuilder.A_NAME, s.getSubmissionName());
      xSubmission.setAttribute(XMLBuilder.A_ID,   s.toString());
      root.appendChild(xSubmission);
    }
    Profiler.exitMethod("GradingXMLBuilder.addSubmissions", "");
  }

  public void addGrades(User user, Document xml, Collection groups,
      Assignment assignment) {
    Profiler.enterMethod("GradingXMLBuilder.addGrades", "");

    Element root = (Element) xml.getFirstChild();
    
    boolean canSeeAllGrades =
        !assignment.getAssignedGraders()
            || user.isAdminPrivByCourse(assignment.getCourse());
    // XXX This does not reflect the original code's behaviour. The "groups"
    // variable is completely ignored here. --MJL
    for (Iterator grades = assignment.getGrades().iterator(); grades.hasNext();) {
      Grade grade = (Grade) grades.next();
      Group group = assignment.findGroup(grade.getUser());
      
      // check user's authorization to view the grade
      // TODO: still not totally sure if this is correct wrt CMS
      if (!canSeeAllGrades
          && !GroupAssignedTo.isAssignedTo(grade.getSubProblem(), group, user))
        continue;
      
      Element xGroup = (Element) root.getElementsByTagNameNS(
          XMLBuilder.TAG_GROUP + group.toString(),
          XMLBuilder.TAG_GROUP).item(0);
      if (xGroup != null) {
        Element xMember = (Element) xGroup.getElementsByTagNameNS(
            XMLBuilder.TAG_MEMBER + grade.getUser().getNetID(),
            XMLBuilder.TAG_MEMBER).item(0);
        if (xMember != null) {
          Element latergrade = (Element) xMember.getElementsByTagNameNS(
              XMLBuilder.TAG_GRADE + (grade.getSubProblem() != null ? grade.getSubProblem().toString() : "0"),
              XMLBuilder.TAG_GRADE).item(0);
          if (latergrade == null) {
            Element xGrade = xml.createElementNS(
                XMLBuilder.TAG_GRADE + grade.getSubProblem().toString(),
                XMLBuilder.TAG_GRADE);
            xGrade.setAttribute(XMLBuilder.A_SUBPROBID,
                                grade.getSubProblem().toString());
            xGrade.setAttribute(XMLBuilder.A_SCORE,
                                StringUtil.trimTrailingZero(String.valueOf(grade.getGrade())));
            xMember.appendChild(xGrade);
          } else if (!latergrade.hasAttribute(XMLBuilder.A_SCORE)) {
            latergrade.setAttribute(XMLBuilder.A_SCORE,
                                    StringUtil.trimTrailingZero(String.valueOf(grade.getGrade())));
          }
        }
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addGrades", "");
  }

  public void addAnswers(User user, Document xml, Collection groups,
      Assignment assignment) {
    Profiler.enterMethod("GradingXMLBuilder.addAnswers", "");
    Iterator answerSets = assignment.getAnswerSets().iterator();
    Element root = (Element) xml.getFirstChild();
    while (answerSets.hasNext()) {
      AnswerSet answerSet = (AnswerSet) answerSets.next();
      Iterator answers = answerSet.getAnswers().iterator();
      
      while (answers.hasNext()) {
        Answer answer = (Answer) answers.next();

        // check authorization for user to see answer
        // TODO: double check that this is correct
        if (assignment.getAssignedGraders() &&
           !user.isAdminPrivByCourse(assignment.getCourse()) &&
           !GroupAssignedTo.isAssignedTo(answer.getSubProblem(), answerSet.getGroup(), user))
          continue;
        
        Element xGroup = (Element) root.getElementsByTagNameNS(
            XMLBuilder.TAG_GROUP + answerSet.getGroup().toString(),
            XMLBuilder.TAG_GROUP).item(0);
        if (xGroup != null) {
          Element xMember = (Element) xGroup.getElementsByTagNameNS(
              XMLBuilder.TAG_MEMBER + answerSet.getUser().getNetID(),
              XMLBuilder.TAG_MEMBER).item(0);
          if (xMember != null) {
            Element laterAnswer = (Element) xMember.getElementsByTagNameNS(
                XMLBuilder.TAG_ANSWERS + answer.getSubProblem().toString(),
                XMLBuilder.TAG_ANSWERS).item(0);
            if (laterAnswer == null) {
              Element xAnswer = xml.createElementNS(
                  XMLBuilder.TAG_ANSWERS + answer.getSubProblem().toString(),
                  XMLBuilder.TAG_ANSWERS);
              xAnswer.setAttribute(XMLBuilder.A_SUBPROBID, answer.getSubProblem().toString());
              
              SubProblem subProb = answer.getSubProblem();
              String text        = answer.getText();

              Choice choice = subProb.getAnswerChoice();
              if (choice != null) {
                String choiceText = choice.getLetter() + ". " + choice.getText();
                xAnswer.setAttribute(XMLBuilder.A_ANSWER, "");
                xAnswer.setAttribute(XMLBuilder.A_ANSWERTEXT, choiceText);
              } else {
                xAnswer.setAttribute(XMLBuilder.A_ANSWERTEXT, text);
              }
              xMember.appendChild(xAnswer);
            } else if (!laterAnswer.hasAttribute(XMLBuilder.A_ANSWER)) {
              /*
               * SubProblemLocal subProb =
               * database.subProblemHome().findByPrimaryKey(new
               * SubProblemPK(answer.getSubProblemID())); if (subProb.getType() ==
               * SubProblemBean.MULTIPLE_CHOICE) { //ChoiceLocal choice =
               * database.choiceHome().findByChoiceID(Long.parseLong(answer.getText()));
               * //laterAnswer.setAttribute(XMLBuilder.A_ANSWER, answer.getText());
               * //laterAnswer.setAttribute(XMLBuilder.A_ANSWERTEXT,
               * choice.getText()); String text = answer.getText();
               * laterAnswer.setAttribute(XMLBuilder.A_ANSWERTEXT, text); try { ChoiceLocal
               * choice =
               * database.choiceHome().findByChoiceID(Long.parseLong(text));
               * laterAnswer.setAttribute(XMLBuilder.A_ANSWER,
               * choice.getText()); } catch (Exception e) {
               * laterAnswer.setAttribute(XMLBuilder.A_ANSWER, ""); } } else {
               * laterAnswer.setAttribute(XMLBuilder.A_ANSWERTEXT, answer.getText()); }
               */
            }
          }
        }
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addAnswers", "");
  }

  public void addSubmittedFiles(Document xml, Collection groups) {
    Profiler.enterMethod("GradingXMLBuilder.addSubmittedFiles", "");
    Iterator giter = groups.iterator();
    while (giter.hasNext()) {
      Group group = (Group) giter.next();

      Element root   = (Element) xml.getFirstChild();
      Element xGroup = (Element) root.getElementsByTagNameNS(
          XMLBuilder.TAG_GROUP + group.toString(),
          XMLBuilder.TAG_GROUP).item(0);
      if (xGroup == null)
        continue;

      Iterator submittedFiles = group.getSubmittedFiles().iterator();
      while (submittedFiles.hasNext()) {
        SubmittedFile file = (SubmittedFile) submittedFiles.next();

        boolean isFirst = false;
        Element xSubFile = (Element) xGroup.getElementsByTagNameNS(
            XMLBuilder.TAG_FILE + file.getSubmission().toString(),
            XMLBuilder.TAG_FILE).item(0);
        if (xSubFile == null) isFirst = true;
        xSubFile = xml.createElementNS(
            XMLBuilder.TAG_FILE + file.getSubmission().toString(),
            (isFirst ? XMLBuilder.TAG_FILE : XMLBuilder.TAG_OLDFILE));
        xSubFile.setAttribute(XMLBuilder.A_FILENAME,
                              file.getSubmission().getSubmissionName());
        xSubFile.setAttribute(XMLBuilder.A_DATE,
                              DateTimeUtil.MONTH_DAY_TIME.format(file.getFileDate()));
        xSubFile.setAttribute(XMLBuilder.A_SUBMITTEDFILEID, file.toString());
        if (file.getLateSubmission()) {
          xSubFile.setAttribute(XMLBuilder.A_LATESUBMISSION, "true");
        }
        xGroup.appendChild(xSubFile);
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addSubmittedFiles", "");
  }

  public void addRegradeInfo(Document xml, Collection groups) {
    Profiler.enterMethod("GradingXMLBuilder.addRegradeInfo", "");
    Iterator giter = groups.iterator();
    while (giter.hasNext()) {
      Group group = (Group) giter.next();

      Iterator regrades = group.getRegradeRequests().iterator();
      Element root   = (Element) xml.getFirstChild();
      Element xGroup = (Element) root.getElementsByTagNameNS(
          XMLBuilder.TAG_GROUP + group.toString(),
          XMLBuilder.TAG_GROUP).item(0);
      while (regrades.hasNext()) {
        RegradeRequest regrade = (RegradeRequest) regrades.next();
        if (xGroup != null) {
          Element xRegrade = xml.createElementNS(
              XMLBuilder.TAG_REGRADE + (regrade.getComment() == null ?
                                            "" :
                                            regrade.getComment().toString()),
              XMLBuilder.TAG_REGRADE);
          xRegrade.setAttribute(XMLBuilder.A_SUBPROBID,
                                regrade.getSubProblem().toString());
          xRegrade.setAttribute(XMLBuilder.A_SUBPROBNAME,
                                (regrade.getSubProblem() == null ?
                                    "All problems" :
                                    regrade.getSubProblem().getName()));
          xRegrade.setAttribute(XMLBuilder.A_STATUS,
                                regrade.getStatus());
          xRegrade.setAttribute(XMLBuilder.A_DATE,
                                DateTimeUtil.DATE_TIME_AMPM.format(regrade.getDateEntered()));
          xRegrade.setAttribute(XMLBuilder.A_NETID, regrade.getUser().getNetID());
          xRegrade.setAttribute(XMLBuilder.A_REQUESTID, regrade.toString());
          Text text = xml.createTextNode(
              StringUtil.formatNoHTMLString(regrade.getRequest()));
          xRegrade.appendChild(text);
          xGroup.appendChild(xRegrade);
        }
      }

      Iterator comments = group.getComments().iterator();
      while (comments.hasNext()) {
        Comment comment = (Comment) comments.next();
        NodeList xRegrades = xGroup.getElementsByTagNameNS(
            XMLBuilder.TAG_REGRADE + comment.toString(),
            XMLBuilder.TAG_REGRADE);
        for (int i = 0; i < xRegrades.getLength(); i++) {
          Element xRegrade = (Element) xRegrades.item(i);
          Element xComment = xml.createElementNS(
              XMLBuilder.TAG_RESPONSE + comment.toString(),
              XMLBuilder.TAG_RESPONSE);
          xComment.setAttribute(XMLBuilder.A_COMMENTID, comment.toString());
          Text text = xml.createTextNode(StringUtil.formatNoHTMLString(comment.getComment()));
          xComment.appendChild(text);
          xComment.setAttribute(XMLBuilder.A_DATE,
              DateTimeUtil.DATE_TIME_AMPM.format(comment.getDateEntered()));
          xComment.setAttribute(XMLBuilder.A_NETID,
              comment.getUser().getNetID());
          xRegrade.appendChild(xComment);
        }
        if (xRegrades.getLength() == 0) {
          Element xComment = xml.createElementNS(
              XMLBuilder.TAG_COMMENT + comment.toString(),
              XMLBuilder.TAG_COMMENT);
          xComment.setAttribute(XMLBuilder.A_COMMENTID, comment.toString());
          Text text = xml.createTextNode(StringUtil.formatWebString(comment.getComment()));
          xComment.appendChild(text);
          xComment.setAttribute(XMLBuilder.A_DATE,
              DateTimeUtil.DATE_TIME_AMPM.format(comment.getDateEntered()));
          xComment.setAttribute(XMLBuilder.A_NETID,
              comment.getUser().getNetID());
          xGroup.appendChild(xComment);
        }

        Iterator commentfiles = comment.getFiles().iterator();
        while (commentfiles.hasNext()) {
          FileData commentFile = (FileData) commentfiles.next();
          Iterator requests = comment.findRequests().iterator();
          while (requests.hasNext()) {
            RegradeRequest request = (RegradeRequest) requests.next();
            Element xRegrade = (Element) XMLUtil.getChildrenByAttributeValue(
                xGroup,
                XMLBuilder.A_REQUESTID, request.toString()).item(0);
            Element xComment = null;
            if (xRegrade != null) {
              xComment = (Element) xRegrade.getElementsByTagNameNS(
                  XMLBuilder.TAG_RESPONSE + comment.toString(),
                  XMLBuilder.TAG_RESPONSE).item(0);
              Element xCommentFile = xml.createElement(XMLBuilder.TAG_COMMENTFILE);
              xCommentFile.setAttribute(XMLBuilder.A_COMMENTFILEID, commentFile.toString());
              xCommentFile.setAttribute(XMLBuilder.A_FILENAME,      commentFile.getName());
              xComment.appendChild(xCommentFile);
            }
          }
        }
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addRegradeInfo", "");
  }

  public void addAssignedTos(User user, Document xml, Assignment assignment) {
    Profiler.enterMethod("GradingXMLBuilder.addAssignedTos", "");
    Iterator assignedTos = assignment.findGroupAssignedTos(user).iterator();
    Collection subProbs = assignment.getSubProblems();
    Element root = (Element) xml.getFirstChild();
    while (assignedTos.hasNext()) {
      GroupAssignedTo assignedTo = (GroupAssignedTo) assignedTos.next();
      Element xGroup = (Element) root.getElementsByTagNameNS(
          XMLBuilder.TAG_GROUP + assignedTo.getGroup().toString(),
          XMLBuilder.TAG_GROUP).item(0);
      if (xGroup != null) {
        NodeList members = xGroup.getElementsByTagName(XMLBuilder.TAG_MEMBER);
        String subProbID = assignedTo.getSubProblem().toString();
        for (int i = 0; i < members.getLength(); i++) {
          Element xMember = (Element) members.item(i);
          Element xGrade  = (Element) xMember.getElementsByTagNameNS(
              XMLBuilder.TAG_GRADE + subProbID,
              XMLBuilder.TAG_GRADE).item(0);
          if (xGrade == null) {
            xGrade = xml.createElementNS(
                XMLBuilder.TAG_GRADE + subProbID,
                XMLBuilder.TAG_GRADE);
            xGrade.setAttribute(XMLBuilder.A_SUBPROBID, subProbID);
            xGrade.setAttribute(XMLBuilder.A_CANGRADE, "true");
            xMember.appendChild(xGrade);
            if (xMember.getChildNodes().getLength() >= subProbs.size()) {
              xGrade = (Element) xMember.getElementsByTagNameNS(
                  XMLBuilder.TAG_GRADE + 0,
                  XMLBuilder.TAG_GRADE).item(0);
              if (xGrade == null) {
                xGrade = xml.createElementNS(
                    XMLBuilder.TAG_GRADE + 0,
                    XMLBuilder.TAG_GRADE);
                xGrade.setAttribute(XMLBuilder.A_SUBPROBID, "0");
                xGrade.setAttribute(XMLBuilder.A_CANGRADE, "true");
                xMember.appendChild(xGrade);
              }
            }
          } else {
            xGrade.setAttribute(XMLBuilder.A_CANGRADE, "true");
          }
        }
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addAssignedTos", "");
  }

}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
