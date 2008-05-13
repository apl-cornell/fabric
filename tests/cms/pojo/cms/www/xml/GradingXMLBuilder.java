/*
 * Created on Apr 19, 2005
 */
package cms.www.xml;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;
import cms.www.util.StringUtil;

import cms.auth.Principal;
import cms.model.*;
import cms.log.LogDetail;

/**
 * Builds the grading page (where grades are set and regrades/comments are
 * handled) for a collection of groups.
 * 
 * @author Jon
 */
public class GradingXMLBuilder {

  public void buildGradingSubtree(Principal p, Document xml, long assignmentID,
      Collection groupids) throws FinderException {
    Profiler.enterMethod("GradingXMLBuilder.buildGradingSubtree",
        "AssignmentID: " + assignmentID);
    AssignmentLocal assignment =
        database.assignmentHome().findByAssignmentID(assignmentID);
    Map subNames = database.getSubmissionNameMap(assignmentID);
    addGroups(p.getNetID(), xml, assignmentID, groupids);
    Profiler.enterMethod("RootBean.addGroupMemberNames", "");
    GroupXMLBuilder.addGroupMemberNames(xml, assignment.getCourseID());
    Profiler.exitMethod("RootBean.addGroupMemberNames", "");
    boolean canGradeAll =
        p.isAdminPrivByCourseID(assignment.getCourseID())
            || !assignment.getAssignedGraders();
    int numSubProbs = addSubProblems(xml, assignmentID, canGradeAll);
    addSubmissions(xml, assignmentID);
    addGrades(p, xml, groupids, assignment, numSubProbs);
    if (assignment.getType() == AssignmentBean.QUIZ
        || assignment.getType() == AssignmentBean.SURVEY) {
      addAnswers(p, xml, groupids, assignment, numSubProbs);
    }
    addSubmittedFiles(xml, groupids, subNames);
    if (assignment.getAssignedGraders()) {
      addAssignedTos(p, xml, assignmentID);
    }
    Profiler.enterMethod("RootBean.getCommentFileRequestIDMap", "");
    Map requestIDs = database.getCommentFileRequestIDMap(assignmentID);
    Profiler.exitMethod("RootBean.getCommentFileRequestIDMap", "");
    Profiler.enterMethod("RootBean.getCommentFileGroupIDMap", "");
    Map groupIDs = database.getCommentFileGroupIDMap(assignmentID);
    Profiler.exitMethod("RootBean.getCommentFileGroupIDMap", "");
    Profiler.enterMethod("RootBean.getSubProblemNameMap", "");
    Map subProbNames = database.getSubProblemNameMap(assignmentID);
    Profiler.exitMethod("RootBean.getSubProblemNameMap", "");
    addRegradeInfo(xml, groupids, subProbNames, requestIDs, groupIDs);
    addGradeLogs(xml, assignment.getCourseID(), groupids);
    Profiler.exitMethod("GradingXMLBuilder.buildGradingSubtree",
        "AssignmentID: " + assignmentID);
  }

  public void addGroups(String netid, Document xml, long assignmentID,
      Collection groupids) throws FinderException {
    Profiler.enterMethod("GradingXMLBuilder.addGroups", "");
    Iterator members =
        database.groupMemberHome().findByGroupIDsAssignedTo(netid,
            assignmentID, groupids).iterator();
    Element root = (Element) xml.getFirstChild();
    while (members.hasNext()) {
      GroupMemberLocal member = (GroupMemberLocal) members.next();
      NodeList group =
          root.getElementsByTagNameNS(XMLBuilder.TAG_GROUP
              + member.getGroupID(), XMLBuilder.TAG_GROUP);
      Element xGroup = null;
      if (group.getLength() == 0) {
        xGroup =
            xml.createElementNS(XMLBuilder.TAG_GROUP + member.getGroupID(),
                XMLBuilder.TAG_GROUP);
        xGroup.setAttribute(XMLBuilder.A_GROUPID, String.valueOf(member
            .getGroupID()));
        root.appendChild(xGroup);
      } else {
        xGroup = (Element) group.item(0);
      }
      Element xMember =
          xml.createElementNS(XMLBuilder.TAG_MEMBER + member.getNetID(),
              XMLBuilder.TAG_MEMBER);
      xMember.setAttribute(XMLBuilder.A_NETID, member.getNetID());
      xGroup.appendChild(xMember);
    }
    Profiler.exitMethod("GradingXMLBuilder.addGroups", "");
  }

  public void addGradeLogs(Document xml, Course course, Collection groups) {
    Profiler.enterMethod("GradingXMLBuilder.addGradeLogs", "");
    Iterator details =
        database.findGradeLogDetails(courseID, groupIDs).iterator();
    Element root = (Element) xml.getFirstChild();
    Map groups = database.getGroupIDMapByCourse(courseID);
    while (details.hasNext()) {
      LogDetail d = (LogDetail) details.next();
      long groupID =
          ((Long) groups.get(d.getNetID() + "_"
              + d.getAssignmentID().toString())).longValue();
      Element xGroup =
          (Element) xml.getElementsByTagNameNS(TAG_GROUP + groupID, TAG_GROUP)
              .item(0);
      if (xGroup != null) {
        Element xGradeLog = xml.createElement(TAG_GRADELOG);
        xGradeLog.setAttribute(A_DATE, DateTimeUtil.formatDate(d.getLogRef()
            .getTimestamp()));
        xGradeLog.setAttribute(A_TEXT, d.getDetailString());
        xGradeLog.setAttribute(A_GRADER, d.getLogRef().getActingNetID());
        xGradeLog.setAttribute(A_NETID, d.getNetID());
        xGroup.appendChild(xGradeLog);
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addGradeLogs", "");
  }

  public int addSubProblems(Document xml, long assignmentID, boolean canGradeAll)
      throws FinderException {
    Profiler.enterMethod("GradingXMLBuilder.addSubProblems", "");
    Collection subprobscol =
        database.subProblemHome().findByAssignmentID(assignmentID);
    Iterator subprobs = subprobscol.iterator();
    AssignmentLocal assignment =
        database.assignmentHome().findByAssignmentID(assignmentID);
    Element root = (Element) xml.getFirstChild();
    Element xMaxScore = xml.createElement(TAG_TOTALPROBLEM);
    xMaxScore.setAttribute(XMLBuilder.A_SUBPROBID, "0");
    xMaxScore.setAttribute(XMLBuilder.A_NAME, "Total");
    xMaxScore.setAttribute(XMLBuilder.A_SCORE, StringUtil.roundToOne(""
        + assignment.getMaxScore()));
    root.appendChild(xMaxScore);

    /*
     * Map subProblemMap = new TreeMap(); while (subprobs.hasNext()) {
     * SubProblemLocal subProblem = (SubProblemLocal) subprobs.next();
     * subProblemMap.put(new Integer(subProblem.getOrder()), subProblem); }
     */

    int numSubProbs = 0;
    long[] subProbIDs = new long[subprobscol.size()];
    // subprobs = subProblemMap.keySet().iterator();
    while (subprobs.hasNext()) {
      SubProblemLocal subprob = (SubProblemLocal) subprobs.next();
      int type = subprob.getType();
      subProbIDs[numSubProbs++] = subprob.getSubProblemID();
      Element xSubProb =
          xml.createElementNS(XMLBuilder.TAG_SUBPROBLEM
              + subprob.getSubProblemID(), XMLBuilder.TAG_SUBPROBLEM);
      xSubProb.setAttribute(XMLBuilder.A_SUBPROBID, String.valueOf(subprob
          .getSubProblemID()));
      xSubProb.setAttribute(XMLBuilder.A_NAME, subprob.getSubProblemName());
      xSubProb.setAttribute(XMLBuilder.A_SCORE, StringUtil.roundToOne(""
          + subprob.getMaxScore()));
      xSubProb.setAttribute(XMLBuilder.A_ORDER, Integer.toString(subprob
          .getOrder()));
      xSubProb.setAttribute(XMLBuilder.A_TYPE, Integer.toString(type));

      if (type == SubProblemBean.MULTIPLE_CHOICE) {
        String correctAnswer = "";

        // get the choice text of the correct answer
        // if somehow we can't find the correct answer, then this subproblem
        // has no correct answer
        try {
          Integer choiceID = new Integer(subprob.getAnswer());
          ChoiceLocal choice =
              database.choiceHome().findByChoiceID(choiceID.longValue());
          xSubProb.setAttribute(XMLBuilder.A_CORRECTANSWER, choice.getLetter()
              + ". " + choice.getText());
        } catch (FinderException e) {
        }

      }

      root.appendChild(xSubProb);
    }
    if (canGradeAll) {
      NodeList xGroups = root.getElementsByTagName(TAG_GROUP);
      for (int i = 0; i < xGroups.getLength(); i++) {
        NodeList xMembers =
            ((Element) xGroups.item(i)).getElementsByTagName(TAG_MEMBER);
        for (int j = 0; j < xMembers.getLength(); j++) {
          Element xMember = (Element) xMembers.item(j);
          for (int k = 0; k < subProbIDs.length; k++) {
            Element xGrade =
                xml.createElementNS(TAG_GRADE + subProbIDs[k], TAG_GRADE);
            xGrade.setAttribute(A_CANGRADE, "true");
            xMember.appendChild(xGrade);
          }
          Element xGrade = xml.createElementNS(TAG_GRADE + 0, TAG_GRADE);
          xGrade.setAttribute(A_CANGRADE, "true");
          xMember.appendChild(xGrade);
        }
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addSubProblems", "");
    return numSubProbs;
  }

  public void addSubmissions(Document xml, long assignmentID)
      throws FinderException {
    Profiler.enterMethod("GradingXMLBuilder.addSubmissions", "");
    Iterator subs =
        database.requiredSubmissionHome().findByAssignmentID(assignmentID)
            .iterator();
    Element root = (Element) xml.getFirstChild();
    while (subs.hasNext()) {
      RequiredSubmissionLocal s = (RequiredSubmissionLocal) subs.next();
      Element xSubmission = xml.createElement(TAG_SUBMISSION);
      xSubmission.setAttribute(A_NAME, s.getSubmissionName());
      xSubmission.setAttribute(A_ID, String.valueOf(s.getSubmissionID()));
      root.appendChild(xSubmission);
    }
    Profiler.exitMethod("GradingXMLBuilder.addSubmissions", "");
  }

  public void addGrades(Principal p, Document xml, Collection groupIDs,
      AssignmentLocal assignment, int numSubProbs) throws FinderException {
    Profiler.enterMethod("GradingXMLBuilder.addGrades", "");
    Collection c;
    if (assignment.getAssignedGraders()
        && !p.isAdminPrivByCourseID(assignment.getCourseID())) {
      c =
          database.gradeHome().findByGroupIDsAssignedTo(groupIDs, p.getNetID(),
              numSubProbs);
    } else {
      c = database.gradeHome().findByGroupIDs(groupIDs);
    }
    Iterator grades = c.iterator();
    Element root = (Element) xml.getFirstChild();
    Map subProbNames =
        database.getSubProblemNameMap(assignment.getAssignmentID());
    Map groups = database.getGroupIDMap(assignment.getAssignmentID());
    boolean hasSubProbs = !assignment.getSubProblems().isEmpty();
    while (grades.hasNext()) {
      GradeLocal grade = (GradeLocal) grades.next();
      Long groupID = (Long) groups.get(grade.getNetID());
      Element xGroup =
          (Element) root.getElementsByTagNameNS(
              XMLBuilder.TAG_GROUP + groupID.toString(), XMLBuilder.TAG_GROUP)
              .item(0);
      if (xGroup != null) {
        Element xMember =
            (Element) xGroup
                .getElementsByTagNameNS(
                    XMLBuilder.TAG_MEMBER + grade.getNetID(),
                    XMLBuilder.TAG_MEMBER).item(0);
        if (xMember != null) {
          Element latergrade =
              (Element) xMember.getElementsByTagNameNS(
                  XMLBuilder.TAG_GRADE + grade.getSubProblemID(),
                  XMLBuilder.TAG_GRADE).item(0);
          if (latergrade == null) {
            Element xGrade =
                xml.createElementNS(XMLBuilder.TAG_GRADE
                    + grade.getSubProblemID(), XMLBuilder.TAG_GRADE);
            xGrade.setAttribute(XMLBuilder.A_SUBPROBID, String.valueOf(grade
                .getSubProblemID()));
            xGrade.setAttribute(XMLBuilder.A_SCORE, StringUtil
                .trimTrailingZero("" + grade.getGrade()));
            xMember.appendChild(xGrade);
          } else if (!latergrade.hasAttribute(A_SCORE)) {
            latergrade.setAttribute(A_SCORE, StringUtil.trimTrailingZero(""
                + grade.getGrade()));
          }
        }
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addGrades", "");
  }

  public void addAnswers(Principal p, Document xml, Collection groupIDs,
      AssignmentLocal assignment, int numSubProbs) throws FinderException {
    Profiler.enterMethod("GradingXMLBuilder.addAnswers", "");
    Collection c;
    if (assignment.getAssignedGraders()
        && !p.isAdminPrivByCourseID(assignment.getCourseID())) {
      c =
          database.answerSetHome().findByGroupIDsAssignedTo(groupIDs,
              p.getNetID(), numSubProbs);
    } else {
      c = database.answerSetHome().findByGroupIDs(groupIDs);
    }
    Iterator answerSets = c.iterator();
    Element root = (Element) xml.getFirstChild();
    Map subProbNames =
        database.getSubProblemNameMap(assignment.getAssignmentID());
    boolean hasSubProbs = !assignment.getSubProblems().isEmpty();
    while (answerSets.hasNext()) {
      AnswerSetLocal answerSet = (AnswerSetLocal) answerSets.next();

      Iterator answers = answerSet.getAnswers().iterator();

      while (answers.hasNext()) {
        AnswerData answer = (AnswerData) answers.next();
        Element xGroup =
            (Element) root.getElementsByTagNameNS(
                XMLBuilder.TAG_GROUP + Long.toString(answerSet.getGroupID()),
                XMLBuilder.TAG_GROUP).item(0);
        if (xGroup != null) {
          Element xMember =
              (Element) xGroup.getElementsByTagNameNS(
                  XMLBuilder.TAG_MEMBER + answerSet.getNetID(),
                  XMLBuilder.TAG_MEMBER).item(0);
          if (xMember != null) {
            Element laterAnswer =
                (Element) xMember.getElementsByTagNameNS(
                    XMLBuilder.TAG_ANSWERS + answer.getSubProblemID(),
                    XMLBuilder.TAG_ANSWERS).item(0);
            if (laterAnswer == null) {
              Element xAnswer =
                  xml.createElementNS(XMLBuilder.TAG_ANSWERS
                      + answer.getSubProblemID(), XMLBuilder.TAG_ANSWERS);
              xAnswer.setAttribute(XMLBuilder.A_SUBPROBID, String
                  .valueOf(answer.getSubProblemID()));
              SubProblemLocal subProb =
                  database.subProblemHome().findByPrimaryKey(
                      new SubProblemPK(answer.getSubProblemID()));
              String text = answer.getText();

              if (subProb.getType() == SubProblemBean.MULTIPLE_CHOICE) {
                xAnswer.setAttribute(XMLBuilder.A_ANSWER, "");
                try {
                  ChoiceLocal choice =
                      database.choiceHome()
                          .findByChoiceID(Long.parseLong(text));
                  String choiceText =
                      choice.getLetter() + ". " + choice.getText();
                  xAnswer.setAttribute(XMLBuilder.A_ANSWERTEXT, choiceText);
                } catch (Exception e) {
                  xAnswer.setAttribute(XMLBuilder.A_ANSWERTEXT, text);
                }
              } else {
                xAnswer.setAttribute(XMLBuilder.A_ANSWERTEXT, text);
              }
              xMember.appendChild(xAnswer);
            } else if (!laterAnswer.hasAttribute(A_ANSWER)) {
              /*
               * SubProblemLocal subProb =
               * database.subProblemHome().findByPrimaryKey(new
               * SubProblemPK(answer.getSubProblemID())); if(subProb.getType() ==
               * SubProblemBean.MULTIPLE_CHOICE) { //ChoiceLocal choice =
               * database.choiceHome().findByChoiceID(Long.parseLong(answer.getText()));
               * //laterAnswer.setAttribute(A_ANSWER, answer.getText());
               * //laterAnswer.setAttribute(XMLBuilder.A_ANSWERTEXT,
               * choice.getText()); String text = answer.getText();
               * laterAnswer.setAttribute(A_ANSWERTEXT, text); try { ChoiceLocal
               * choice =
               * database.choiceHome().findByChoiceID(Long.parseLong(text));
               * laterAnswer.setAttribute(XMLBuilder.A_ANSWER,
               * choice.getText()); } catch (Exception e) {
               * laterAnswer.setAttribute(XMLBuilder.A_ANSWER, ""); } } else {
               * laterAnswer.setAttribute(A_ANSWERTEXT, answer.getText()); }
               */
            }
          }
        }
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addAnswers", "");
  }

  public void addSubmittedFiles(Document xml, Collection groups) {
    // mdg: orginially subNames was database.getSubmissionNames or
    // getSubmissionNamesByCourse
    Profiler.enterMethod("GradingXMLBuilder.addSubmittedFiles", "");
    Iterator submittedFiles =
        database.submittedFileHome().findAllByGroupIDs(groupids).iterator();
    Element root = (Element) xml.getFirstChild();
    while (submittedFiles.hasNext()) {
      SubmittedFile file = (SubmittedFile) submittedFiles.next();
      Element xGroup =
          (Element) root.getElementsByTagNameNS(
              XMLBuilder.TAG_GROUP + file.getGroupID(), XMLBuilder.TAG_GROUP)
              .item(0);
      if (xGroup != null) {
        boolean isFirst = false;
        Element xSubFile =
            (Element) xGroup.getElementsByTagNameNS(
                XMLBuilder.TAG_FILE + file.getSubmissionID(),
                XMLBuilder.TAG_FILE).item(0);
        if (xSubFile == null) isFirst = true;
        xSubFile =
            xml.createElementNS(XMLBuilder.TAG_FILE + file.getSubmissionID(),
                (isFirst ? XMLBuilder.TAG_FILE : XMLBuilder.TAG_OLDFILE));
        xSubFile.setAttribute(XMLBuilder.A_FILENAME, file
            .appendFileType((String) subNames.get(new Long(file
                .getSubmissionID()))));
        xSubFile.setAttribute(XMLBuilder.A_DATE, DateTimeUtil.MONTH_DAY_TIME
            .format(file.getFileDate()));
        xSubFile.setAttribute(XMLBuilder.A_SUBMITTEDFILEID, Long.toString(file
            .getSubmittedFileID()));
        if (file.getLateSubmission()) {
          xSubFile.setAttribute(XMLBuilder.A_LATESUBMISSION, "true");
        }
        xGroup.appendChild(xSubFile);
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addSubmittedFiles", "");
  }

  public void addRegradeInfo(Document xml, Collection groups) {
    // mdg:
    //Map groupidsMap = database.getCommentFileGroupIDMapByCourse(course);   or not by course
    //Map requestIDs  = database.getCommentFileRequestIDMapByCourse(course); "
    //Map subProbNames = database.getSubProblemNameMapByCourse(course);      "

    Profiler.enterMethod("GradingXMLBuilder.addRegradeInfo", "");
    Iterator regrades =
        database.regradeRequestHome().findByGroupIDs(groupids).iterator();
    Iterator comments =
        database.commentHome().findByGroupIDs(groupids).iterator();
    Iterator commentfiles =
        database.commentFileHome().findByGroupIDs(groupids).iterator();
    Element root = (Element) xml.getFirstChild();
    while (regrades.hasNext()) {
      RegradeRequestLocal regrade = (RegradeRequestLocal) regrades.next();
      Element xGroup =
          (Element) root
              .getElementsByTagNameNS(
                  XMLBuilder.TAG_GROUP + regrade.getGroupID(),
                  XMLBuilder.TAG_GROUP).item(0);
      if (xGroup != null) {
        Element xRegrade =
            xml.createElementNS(XMLBuilder.TAG_REGRADE
                + (regrade.getCommentID() == null ? 0 : regrade.getCommentID()
                    .longValue()), XMLBuilder.TAG_REGRADE);
        xRegrade.setAttribute(XMLBuilder.A_SUBPROBID, String.valueOf(regrade
            .getSubProblemID()));
        xRegrade.setAttribute(XMLBuilder.A_SUBPROBNAME, (regrade
            .getSubProblemID() == 0 ? "All problems" : (String) subNames
            .get(new Long(regrade.getSubProblemID()))));
        xRegrade.setAttribute(XMLBuilder.A_STATUS, regrade.getStatus());
        xRegrade.setAttribute(XMLBuilder.A_DATE, DateTimeUtil.DATE_TIME_AMPM
            .format(regrade.getDateEntered()));
        xRegrade.setAttribute(XMLBuilder.A_NETID, regrade.getNetID());
        xRegrade.setAttribute(XMLBuilder.A_REQUESTID, String.valueOf(regrade
            .getRequestID()));
        Text text =
            xml.createTextNode(StringUtil.formatNoHTMLString(regrade
                .getRequest()));
        xRegrade.appendChild(text);
        xGroup.appendChild(xRegrade);
      }
    }
    while (comments.hasNext()) {
      CommentLocal comment = (CommentLocal) comments.next();
      Element xGroup =
          (Element) root
              .getElementsByTagNameNS(
                  XMLBuilder.TAG_GROUP + comment.getGroupID(),
                  XMLBuilder.TAG_GROUP).item(0);
      if (xGroup != null) {
        NodeList xRegrades =
            xGroup.getElementsByTagNameNS(XMLBuilder.TAG_REGRADE
                + comment.getCommentID(), XMLBuilder.TAG_REGRADE);
        for (int i = 0; i < xRegrades.getLength(); i++) {
          Element xRegrade = (Element) xRegrades.item(i);
          Element xComment =
              xml.createElementNS(XMLBuilder.TAG_RESPONSE
                  + comment.getCommentID(), XMLBuilder.TAG_RESPONSE);
          xComment.setAttribute(XMLBuilder.A_COMMENTID, String.valueOf(comment
              .getCommentID()));
          Text text =
              xml.createTextNode(StringUtil.formatNoHTMLString(comment
                  .getComment()));
          xComment.appendChild(text);
          xComment.setAttribute(XMLBuilder.A_DATE, DateTimeUtil.DATE_TIME_AMPM
              .format(comment.getDateEntered()));
          xComment.setAttribute(XMLBuilder.A_NETID, comment.getNetID());
          xRegrade.appendChild(xComment);
        }
        if (xRegrades.getLength() == 0) {
          Element xComment =
              xml.createElementNS(XMLBuilder.TAG_COMMENT
                  + comment.getCommentID(), XMLBuilder.TAG_COMMENT);
          xComment.setAttribute(XMLBuilder.A_COMMENTID, String.valueOf(comment
              .getCommentID()));
          Text text =
              xml.createTextNode(StringUtil.formatWebString(comment
                  .getComment()));
          xComment.appendChild(text);
          xComment.setAttribute(XMLBuilder.A_DATE, DateTimeUtil.DATE_TIME_AMPM
              .format(comment.getDateEntered()));
          xComment.setAttribute(XMLBuilder.A_NETID, comment.getNetID());
          xGroup.appendChild(xComment);
        }
      }
    }
    while (commentfiles.hasNext()) {
      CommentFileLocal commentFile = (CommentFileLocal) commentfiles.next();
      long groupID =
          ((Long) groupIDs.get(new Long(commentFile.getCommentFileID())))
              .longValue();
      Element xGroup =
          (Element) root.getElementsByTagNameNS(XMLBuilder.TAG_GROUP + groupID,
              XMLBuilder.TAG_GROUP).item(0);
      if (xGroup != null) {
        Iterator requestids = null;
        try {
          requestids =
              ((Collection) requestIDs.get(new Long(commentFile
                  .getCommentFileID()))).iterator();
        } catch (NullPointerException e) {
        }
        if (requestids != null) {
          while (requestids.hasNext()) {
            long requestID = ((Long) requestids.next()).longValue();
            Element xRegrade =
                (Element) XMLUtil.getChildrenByAttributeValue(xGroup,
                    XMLBuilder.A_REQUESTID, String.valueOf(requestID)).item(0);
            Element xComment = null;
            if (xRegrade != null) {
              xComment =
                  (Element) xRegrade.getElementsByTagNameNS(
                      XMLBuilder.TAG_RESPONSE + commentFile.getCommentID(),
                      XMLBuilder.TAG_RESPONSE).item(0);
            }
            if (xComment != null) {
              Element xCommentFile =
                  xml.createElement(XMLBuilder.TAG_COMMENTFILE);
              xCommentFile.setAttribute(XMLBuilder.A_COMMENTFILEID, String
                  .valueOf(commentFile.getCommentFileID()));
              xCommentFile.setAttribute(XMLBuilder.A_FILENAME, commentFile
                  .getFileName());
              xComment.appendChild(xCommentFile);
            }
          }
        } else {
          Element xComment =
              (Element) xGroup.getElementsByTagNameNS(
                  XMLBuilder.TAG_COMMENT + commentFile.getCommentID(),
                  XMLBuilder.TAG_COMMENT).item(0);
          if (xComment != null) {
            Element xCommentFile =
                xml.createElement(XMLBuilder.TAG_COMMENTFILE);
            xCommentFile.setAttribute(XMLBuilder.A_COMMENTFILEID, String
                .valueOf(commentFile.getCommentFileID()));
            xCommentFile.setAttribute(XMLBuilder.A_FILENAME, commentFile
                .getFileName());
            xComment.appendChild(xCommentFile);
          }
        }
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addRegradeInfo", "");
  }

  public void addAssignedTos(Principal p, Document xml, long assignmentID)
      throws FinderException {
    Profiler.enterMethod("GradingXMLBuilder.addAssignedTos", "");
    Iterator assignedTos =
        database.groupAssignedToHome().findByNetIDAssignmentID(p.getNetID(),
            assignmentID).iterator();
    Collection subProbs =
        database.subProblemHome().findByAssignmentID(assignmentID);
    Element root = (Element) xml.getFirstChild();
    while (assignedTos.hasNext()) {
      GroupAssignedToLocal assignedTo =
          (GroupAssignedToLocal) assignedTos.next();
      Element xGroup =
          (Element) root.getElementsByTagNameNS(
              TAG_GROUP + assignedTo.getGroupID(), TAG_GROUP).item(0);
      if (xGroup != null) {
        NodeList members = xGroup.getElementsByTagName(TAG_MEMBER);
        String subProbID = String.valueOf(assignedTo.getSubProblemID());
        for (int i = 0; i < members.getLength(); i++) {
          Element xMember = (Element) members.item(i);
          Element xGrade =
              (Element) xMember.getElementsByTagNameNS(TAG_GRADE + subProbID,
                  TAG_GRADE).item(0);
          if (xGrade == null) {
            xGrade = xml.createElementNS(TAG_GRADE + subProbID, TAG_GRADE);
            xGrade.setAttribute(A_SUBPROBID, subProbID);
            xGrade.setAttribute(A_CANGRADE, "true");
            xMember.appendChild(xGrade);
            if (xMember.getChildNodes().getLength() >= subProbs.size()) {
              xGrade =
                  (Element) xMember.getElementsByTagNameNS(TAG_GRADE + 0,
                      TAG_GRADE).item(0);
              if (xGrade == null) {
                xGrade = xml.createElementNS(TAG_GRADE + 0, TAG_GRADE);
                xGrade.setAttribute(A_SUBPROBID, "0");
                xGrade.setAttribute(A_CANGRADE, "true");
                xMember.appendChild(xGrade);
              }
            }
          } else {
            xGrade.setAttribute(A_CANGRADE, "true");
          }
        }
      }
    }
    Profiler.exitMethod("GradingXMLBuilder.addAssignedTos", "");
  }

}
