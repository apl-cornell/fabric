package cms.www.xml;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;
import cms.www.util.StringUtil;

import cms.auth.Principal;
import cms.model.*;

/**
 * Builds an XML subtree describing an assignment in as much detail as desired,
 * or a subtree with a list of assignments for a specific purpose. Created 3 /
 * 27 / 05
 * 
 * @author Evan
 */
public class AssignmentXMLBuilder {

  private XMLBuilder xmlBuilder;

  public AssignmentXMLBuilder(XMLBuilder builder) {
    this.xmlBuilder = builder;
  }

  /**
   * Generate an XML subtree with all known info about the given Assignment
   * 
   * @param xml
   *          The Document to place this element on
   * @param assignment
   *          The assignment from which to generate the branch
   * @param gradeMap
   *          is the hashmap of grade  objects which represents the grade of
   *          student for all subproblems of assignment, gradeMap would be null
   *          if method used for building nonstudent page
   * @return The assignment element of the tree, with general properties set and
   *         several child nodes
   */
  public Element buildFullSubtree(User p, Document xml,
      Assignment assignment, Map answerMap) {
    Profiler.enterMethod("AssignmentXMLBuilder.buildFullSubtree",
        "AssignmentID: " + assignment.toString());
    Student student = assignment.getCourse().getStudent(p);
    Element xAssignment =   buildGeneralSubtree(xml, assignment, null);
    xAssignment.appendChild(buildAsgnItemsSubtree(xml, assignment));
    xAssignment.appendChild(buildSolutionFilesSubtree(xml, assignment));
    xAssignment.appendChild(buildSubmissionsSubtree(xml, assignment));
    xAssignment.appendChild(buildSubproblemsSubtree(xml, student, assignment, answerMap));
    xAssignment.appendChild(xmlBuilder.scheduleXMLBuilder.buildScheduleSubtree(p, xml, assignment));
    if (assignment.getScheduled()) {
      xAssignment.setAttribute(XMLBuilder.A_USESCHEDULE, "");
    }
    Profiler.exitMethod("AssignmentXMLBuilder.buildFullSubtree",
        "AssignmentID: " + assignment.toString());
    return xAssignment;
  }

  /**
   * Generate an XML subtree with a bit of general info about the given
   * Assignment
   * 
   * @param xml
   *          The Document to place this element on
   * @param assignment
   *          The assignment from which to generate the branch
   * @return The assignment element of the tree, with a few general properties
   *         set
   */
  public Element buildShortSubtree(Document xml, Assignment assignment) {
    Element xAssignment = xml.createElement(XMLBuilder.TAG_ASSIGNMENT);
    Course course = assignment.getCourse();
    xAssignment.setAttribute(XMLBuilder.A_COURSENAME, course.getCode());
    xAssignment.setAttribute(XMLBuilder.A_NAMESHORT, assignment.getNameShort());
    xAssignment.setAttribute(XMLBuilder.A_NAME, assignment.getName());
    xAssignment.setAttribute(XMLBuilder.A_DUEDATE, DateTimeUtil.ADMIN_ASGN_DUEDATE_FORMAT
        .format(assignment.getDueDate()));
    xAssignment.setAttribute(XMLBuilder.A_ID, assignment.toString());
    return xAssignment;
  }

  public void addStatSubtree(Document xml, Element xAssignment, Assignment assignment) {
    Float stat;
    if ((stat = assignment.getMean()) != null) {
      xAssignment.setAttribute(XMLBuilder.A_STATMEAN, StringUtil.roundToOne(""
          + stat.floatValue()));
    }
    if ((stat = assignment.getStdDev()) != null) {
      xAssignment.setAttribute(XMLBuilder.A_STATDEV, StringUtil.roundToOne(""
          + stat.floatValue()));
    }
    if ((stat = assignment.getMax()) != null) {
      xAssignment.setAttribute(XMLBuilder.A_STATMAX, StringUtil.roundToOne(""
          + stat.floatValue()));
    }
    if ((stat = assignment.getMedian()) != null) {
      xAssignment.setAttribute(XMLBuilder.A_STATMEDIAN, StringUtil.roundToOne(""
          + stat.floatValue()));
    }
  }

  public void addBasicInfo(Document xml, Element xAssignment,
      Assignment assignment) {
    xAssignment.setAttribute(XMLBuilder.A_NAME, assignment.getName());
    xAssignment.setAttribute(XMLBuilder.A_NAMESHORT, assignment.getNameShort());
    xAssignment.setAttribute(XMLBuilder.A_ASSIGNID, assignment.toString());
    xAssignment.setAttribute(XMLBuilder.A_WEIGHT, StringUtil.roundToOne(Float
        .toString(assignment.getWeight())));
    xAssignment.setAttribute(XMLBuilder.A_TOTALSCORE, StringUtil.roundToOne(Float
        .toString(assignment.getMaxScore())));
    xAssignment.setAttribute(XMLBuilder.A_STATUS, assignment.getStatus());
    xAssignment.setAttribute(XMLBuilder.A_ASSIGNTYPE, Integer.toString(assignment
        .getType()));
    if (assignment.getScheduled())
      xAssignment.setAttribute(XMLBuilder.A_USESCHEDULE, "true");
  }

  /**
   * Generate an XML subtree with general info about the given Assignment
   * 
   * @param xml
   *          The Document to place this element on
   * @param assignment
   *          The assignment from which to generate the branch
   * @return The assignment element of the tree, with general properties set
   */
  public Element buildGeneralSubtree(Document xml, Assignment assignment, Map gradeMap) {
    Profiler.enterMethod("AssignmentXMLBuilder.buildGeneralSubtree", "");
    Element xAssignment = xml.createElement(XMLBuilder.TAG_ASSIGNMENT);
    // add description node
    Element xDescription = xml.createElement(XMLBuilder.TAG_DESCRIPTION);
    String desc = assignment.getDescription();
    xDescription.appendChild(xml.createTextNode(desc == null ? "" : desc));
    xAssignment.appendChild(xDescription);
    // less verbose attributes
    addBasicInfo(xml, xAssignment, assignment);
    int graceperiod = assignment.getGracePeriod();
    xAssignment.setAttribute(XMLBuilder.A_GRACEPERIOD, Integer.toString(graceperiod));
    xAssignment.setAttribute(XMLBuilder.A_MINGROUP, Integer.toString(assignment
        .getGroupSizeMin()));
    xAssignment.setAttribute(XMLBuilder.A_MAXGROUP, Integer.toString(assignment
        .getGroupSizeMax()));
    xAssignment.setAttribute(XMLBuilder.A_ID, assignment.toString());
    if (assignment.getShowStats()) xAssignment.setAttribute(XMLBuilder.A_SHOWSTATS, "");
    Date cmpTime =
        new Date(System.currentTimeMillis() - graceperiod * 60000);
    Date now = new Date(System.currentTimeMillis());
    Date due = assignment.getDueDate();
    xAssignment.setAttribute(XMLBuilder.A_DUEDATE, DateTimeUtil.DATE.format(due));
    xAssignment.setAttribute(XMLBuilder.A_DUETIME, DateTimeUtil.TIME.format(due));
    xAssignment.setAttribute(XMLBuilder.A_DUEAMPM, DateTimeUtil.AMPM.format(due));
    if (cmpTime.after(due)) {
      xAssignment.setAttribute(XMLBuilder.A_PASTDUE, "true");
    }
    if (now.before(due)) {
      xAssignment.setAttribute(XMLBuilder.A_DUECOUNTDOWN, DateTimeUtil.formatCountdown(
          now, due));
    }
    if (assignment.getAllowLate()) {
      Date late = assignment.getLateDeadline();
      xAssignment.setAttribute(XMLBuilder.A_LATEDATE, DateTimeUtil.DATE.format(late));
      xAssignment.setAttribute(XMLBuilder.A_LATETIME, DateTimeUtil.TIME.format(late));
      xAssignment.setAttribute(XMLBuilder.A_LATEAMPM, DateTimeUtil.AMPM.format(late));
      xAssignment.setAttribute(XMLBuilder.A_LATEALLOWED, "true");
      xAssignment.setAttribute(XMLBuilder.A_LATEFULLDATE, DateTimeUtil.formatDate(late));
      if (now.after(late)) {
        xAssignment.setAttribute(XMLBuilder.A_PASTLATE, "true");
      }
    }
    Date regrade = assignment.getRegradeDeadline();
    if (regrade != null) {
      xAssignment
          .setAttribute(XMLBuilder.A_REGRADEDATE, DateTimeUtil.DATE.format(regrade));
      xAssignment
          .setAttribute(XMLBuilder.A_REGRADETIME, DateTimeUtil.TIME.format(regrade));
      xAssignment
          .setAttribute(XMLBuilder.A_REGRADEAMPM, DateTimeUtil.AMPM.format(regrade));
      if (now.after(regrade)) {
        xAssignment.setAttribute(XMLBuilder.A_PASTREGRADE, "true");
      }
    }
    Float grade = null;
    if (gradeMap != null)
      grade = (Float) gradeMap.get(assignment);
    if (grade != null)
      xAssignment.setAttribute(XMLBuilder.A_SCORE, StringUtil.roundToOne(""
          + grade.floatValue()));
    addStatSubtree(xml, xAssignment, assignment);
    if (assignment.getAssignedGraders())
      xAssignment.setAttribute(XMLBuilder.A_ASSIGNEDGRADERS, "true");
    if (assignment.getStudentRegrades())
      xAssignment.setAttribute(XMLBuilder.A_STUDENTREGRADES, "true");
    if (assignment.getAssignedGroups())
      xAssignment.setAttribute(XMLBuilder.A_ASSIGNEDGROUPS, "true");
    if (assignment.getShowStats())
      xAssignment.setAttribute(XMLBuilder.A_SHOWSTATS, "true");
    if (assignment.getShowSolution())
      xAssignment.setAttribute(XMLBuilder.A_SHOWSOLUTION, "true");
    if (assignment.getScheduled())
      xAssignment.setAttribute(XMLBuilder.A_USESCHEDULE, "true");
    Profiler.exitMethod("AssignmentXMLBuilder.buildGeneralSubtree", "");
    return xAssignment;
  }

  /**
   * Generate an XML subtree with info on items uploaded by the staff as part of
   * the given Assignment
   * 
   * @param xml
   *          The Document to place this element on
   * @param assignment
   *          The Assignment from which to take data
   * @return An element holding a list of items, both visible and deleted
   *         (hidden)
   */
  public Element buildAsgnItemsSubtree(Document xml, Assignment assignment) {
    Element xItems = xml.createElement(XMLBuilder.TAG_ITEMS);
    // visible items
    Iterator i = assignment.getAssignmentItems().iterator();
    while (i.hasNext()) {
      AssignmentItem item = (AssignmentItem) i.next();
      AssignmentFile file = item.getAssignmentFile();
      Collection hiddenFiles = item.findHiddenAssignmentFiles();
      Element xItem = xml.createElement(XMLBuilder.TAG_ITEM);
      xItem.setAttribute(XMLBuilder.A_NAME, item.getItemName());
      xItem.setAttribute(XMLBuilder.A_ID,   item.toString());
      Element xFile = xml.createElement(XMLBuilder.TAG_FILE);
      xItem.appendChild(xFile);
      xFile.setAttribute(XMLBuilder.A_NAME, item.getItemName());
      xFile.setAttribute(XMLBuilder.A_ID,   file.toString());
      Iterator hid = hiddenFiles.iterator();
      while (hid.hasNext()) {
        AssignmentFile hidFile = (AssignmentFile) hid.next();
        Element xHidFile = xml.createElement(XMLBuilder.TAG_HIDDENFILE);
        xItem.appendChild(xHidFile);
        xHidFile.setAttribute(XMLBuilder.A_NAME, hidFile.getFile().getName());
        xHidFile.setAttribute(XMLBuilder.A_ID,   hidFile.toString());
        xHidFile.setAttribute(XMLBuilder.A_DATE,
            DateTimeUtil.NUMMONTH_DAY_TIME.format(hidFile.getFileDate()));
      }
      xItems.appendChild(xItem);
    }
    // deleted items
    i = assignment.findHiddenAssignmentItems().iterator();
    while (i.hasNext()) {
      AssignmentItem item = (AssignmentItem) i.next();
      AssignmentFile file = item.getAssignmentFile();
      Element xHidItem = xml.createElement(XMLBuilder.TAG_HIDDENITEM);
      xItems.appendChild(xHidItem);
      xHidItem.setAttribute(XMLBuilder.A_NAME, item.getItemName());
      xHidItem.setAttribute(XMLBuilder.A_ID,   item.toString());
      xHidItem.setAttribute(XMLBuilder.A_DATE,
          DateTimeUtil.NUMMONTH_DAY_TIME.format(file.getFileDate()));
    }
    return xItems;
  }

  /**
   * Generate an XML subtree with info on solution files for the given
   * Assignment
   * 
   * @param xml
   *          The Document to place this element on
   * @param assignment
   *          The Assignment from which to take data
   * @return An element holding a list of solution files
   */
  public Element buildSolutionFilesSubtree(Document xml,
      Assignment assignment) {
    Element xSolutions = xml.createElement(XMLBuilder.TAG_SOLUTIONS);
    if (assignment.hasSolutionFile()) {
      SolutionFile sf = assignment.findSolutionFile();
      Element xSolution = xml.createElement(XMLBuilder.TAG_SOLFILE);
      xSolution.setAttribute(XMLBuilder.A_FILENAME, sf.getFile().getName());
      xSolution.setAttribute(XMLBuilder.A_ID, sf.toString());
      xSolutions.appendChild(xSolution);
    }
    
    Iterator i = assignment.findHiddenSolutionFiles().iterator();
    while (i.hasNext()) {
      SolutionFile sfd = (SolutionFile) i.next();
      Element xSolution = xml.createElement(XMLBuilder.TAG_HIDDENSOLFILE);
      xSolution.setAttribute(XMLBuilder.A_FILENAME, sfd.getFile().getName());
      xSolution.setAttribute(XMLBuilder.A_ID, sfd.toString());
      xSolutions.appendChild(xSolution);
    }
    return xSolutions;
  }

  /**
   * Generate an XML subtree with info on submissions for the given Assignment
   * by the given Principal
   * 
   * @param xml
   *          The Document to place this element on
   * @param assignment
   *          The Assignment from which to take data
   * @return An element holding a list of submissions, both visible and deleted
   *         (hidden)
   */
  public Element buildSubmissionsSubtree(Document xml, Assignment assignment) {
    Element xSubmissions = xml.createElement(XMLBuilder.TAG_SUBMISSIONS);
    // visible submissions
    Iterator i = assignment.getRequiredSubmissions().iterator();
    // We need to match RequiredFiles with the same FileName
    Hashtable nameMatch = new Hashtable();
    while (i.hasNext()) {
      RequiredSubmission sub = (RequiredSubmission) i.next();
      xSubmissions.appendChild(buildSubmissionSubtree(xml, sub, XMLBuilder.TAG_ITEM));
    }
    // Deleted submissions
    i = assignment.getHiddenRequiredSubmissions().iterator();
    nameMatch.clear();
    while (i.hasNext()) {
      RequiredSubmission sub = (RequiredSubmission) i.next();
      xSubmissions.appendChild(buildSubmissionSubtree(xml, sub, XMLBuilder.TAG_HIDDENITEM));
    }
    return xSubmissions;
  }

  /**
   * Auxiliary to buildSubmissionsSubtree(): generate an XML subtree with info
   * on a given required submission (a single file)
   * 
   * @param xml
   *          The Document to place this element on
   * @param submission
   *          The submission from which to take data
   * @param submissionTypeTag
   *          The type of submission: legal values are XMLBuilder.TAG_ITEM and
   *          XMLBuilder.TAG_HIDDENITEM
   * @return An element holding info on a submission
   */
  private Element buildSubmissionSubtree(Document xml,
      RequiredSubmission submission, String submissionTypeTag) {
    Element xFile = xml.createElement(submissionTypeTag);
    Iterator i = submission.getRequiredFileTypes().iterator();
    xFile.setAttribute(XMLBuilder.A_ID, submission.toString());
    xFile.setAttribute(XMLBuilder.A_NAME, submission.getSubmissionName());
    xFile.setAttribute(XMLBuilder.A_SIZE, Integer.toString(submission.getMaxSize()));
    StringBuilder formats = new StringBuilder();
    while (i.hasNext()) {
      String type = (String) i.next();
      Element xFormat = xml.createElement(XMLBuilder.TAG_FORMAT);
      xFile.appendChild(xFormat);
      xFormat.setAttribute(XMLBuilder.A_TYPE, type);
      if (formats.length() != 0)
        formats.append(", ");
      formats.append(type);
    }
    xFile.setAttribute(XMLBuilder.A_TYPELIST, formats.toString());
    return xFile;
  }

  /**
   * Generate an XML subtree with info on subproblems in the given Assignment
   * 
   * @param p
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param gradeMap
   *          is the hashmap of grade  objects which represents the grade of
   *          student for all subproblems of assignment, gradeMap would be null
   *          if method used for building nonstudent page
   * @return An element holding a list of subproblems
   */
  public Element buildSubproblemsSubtree(Document xml, Student student,
      Assignment assignment, Map answerMap) {
    Element xSubproblems = xml.createElement(XMLBuilder.TAG_SUBPROBS);
    // TreeMap orderToSubProblem = new TreeMap();
    // visible subproblems
    // Iterator i= assignment.getSubProblems().iterator();

    // put the subproblems in a tree map whose keys are the subproblem orders
    // so that we process the subproblems in the right order
    Iterator i = assignment.getSubProblems().iterator();

    /*
     * while (i.hasNext()) { SubProblem sp = (SubProblem) i.next();
     * orderToSubProblem.put(new Integer(sp.getOrder()), sp); } Iterator j =
     * orderToSubProblem.keySet().iterator();
     */
    while (i.hasNext()) {
      // SubProblem sp= (SubProblem)orderToSubProblem.get(j.next());
      SubProblem sp = (SubProblem) i.next();
      Element xProb = xml.createElement(XMLBuilder.TAG_SUBPROBLEM);
      xProb.setAttribute(XMLBuilder.A_NAME, sp.getName());
      xProb.setAttribute(XMLBuilder.A_TOTALSCORE, StringUtil.trimTrailingZero(Float
          .toString(sp.getMaxScore())));
      xProb.setAttribute(XMLBuilder.A_ID, sp.toString());
      xProb.setAttribute(XMLBuilder.A_TYPE, Long.toString(sp.getType()));
      xProb.setAttribute(XMLBuilder.A_ORDER, Long.toString(sp.getOrder()));
      Float grade = assignment.findMostRecentGrade(student, sp).getGrade();
      if (grade != null)
        xProb.setAttribute(XMLBuilder.A_SCORE, StringUtil.roundToOne(grade.toString()));
      if (answerMap != null) {
        String answer = (String) answerMap.get(sp);
        if (answer != null) xProb.setAttribute(XMLBuilder.A_ANSWER, answer);
      }

      if (sp.getType() == SubProblem.MULTIPLE_CHOICE) {
        xProb.setAttribute(XMLBuilder.A_CORRECTANSWER, Integer.toString(sp.getAnswer()));
      }

      // build choices even if subproblem type is not multiple-choice, so if the
      // type was
      // changed to something else and back to multiple-choice, the jsp file can
      // still display it
      // survey-question.jsp will hide this according to subproblem type.
      xProb.appendChild(buildSubProblemChoicesSubtree(xml, sp));

      xSubproblems.appendChild(xProb);
    }
    // deleted (hidden) subproblems
    i = assignment.getHiddenSubProblems().iterator();
    while (i.hasNext()) {
      SubProblem sp = (SubProblem) i.next();
      Element xProb = xml.createElement(XMLBuilder.TAG_HIDDENSUBPROB);
      xProb.setAttribute(XMLBuilder.A_NAME, sp.getName());
      xProb.setAttribute(XMLBuilder.A_TOTALSCORE, StringUtil.trimTrailingZero(Float
          .toString(sp.getMaxScore())));
      xProb.setAttribute(XMLBuilder.A_ID, sp.toString());
      xSubproblems.appendChild(xProb);
    }
    return xSubproblems;
  }

  /**
   * Generate an XML subtree with info on choices in the given subproblem
   * 
   * @param xml
   *          The Document to place this element in
   * @param sp
   *          is the subproblem to place the element in
   * @return An element holding a list of subproblems
   */

  public Element buildSubProblemChoicesSubtree(Document xml, SubProblem sp) {
    Element xChoices = xml.createElement(XMLBuilder.TAG_CHOICES);

    Iterator i = sp.getChoices().iterator();
    while (i.hasNext()) {
      Choice choice = ((Choice) i.next());
      
      // hidden choices returned for debugging purposes
      String tag = choice.getHidden() ? XMLBuilder.TAG_HIDDENCHOICE
                                      : XMLBuilder.TAG_CHOICE;
      
      Element xChoice = xml.createElement(tag);
      xChoice.setAttribute(XMLBuilder.A_LETTER,    choice.getLetter());
      xChoice.setAttribute(XMLBuilder.A_TEXT,      choice.getText());
      xChoice.setAttribute(XMLBuilder.A_ID,        choice.toString());
      xChoice.setAttribute(XMLBuilder.A_SUBPROBID, choice.getSubProblem().toString());
      xChoices.appendChild(xChoice);
    }
    return xChoices;
  }

  public Element buildSurveySubtree(Document xml, Collection surveys) {
    Element xSurveys = xml.createElement(XMLBuilder.TAG_SURVEYS);
    Iterator i = surveys.iterator();
    while (i.hasNext()) {
      Assignment survey = (Assignment) i.next();
      Element xSurvey = xml.createElement(XMLBuilder.TAG_SURVEY);

      // include basic information and also total submissions
      Collection answerSets = survey.getAnswerSets();
      int numSubmissions = (answerSets != null) ? answerSets.size() : 0;
      Date due = survey.getDueDate();

      xSurvey.setAttribute(XMLBuilder.A_ASSIGNID, survey.toString());
      xSurvey.setAttribute(XMLBuilder.A_NAME, survey.getName());
      xSurvey.setAttribute(XMLBuilder.A_COUNT, Integer.toString(numSubmissions));
      xSurvey.setAttribute(XMLBuilder.A_STATUS, survey.getStatus());
      xSurvey.setAttribute(XMLBuilder.A_DUEDATE, DateTimeUtil.DATE.format(due));

      xSurveys.appendChild(xSurvey);
    }

    return xSurveys;
  }

  public Element buildSurveyResultSubtree(Document xml, Assignment assignment) {
    int assignType = assignment.getType();
    Course course = assignment.getCourse();
    Element xResult = xml.createElement(XMLBuilder.TAG_SURVEYRESULT);
    xResult.setAttribute(XMLBuilder.A_CODE, course.getCode());
    xResult.setAttribute(XMLBuilder.A_COURSENAME, course.getName());

    Element xAssignment = xml.createElement(XMLBuilder.TAG_ASSIGNMENT);
    xAssignment.setAttribute(XMLBuilder.A_ID,          assignment.toString());
    xAssignment.setAttribute(XMLBuilder.A_NAME,        assignment.getName());
    xAssignment.setAttribute(XMLBuilder.A_DESCRIPTION, assignment.getDescription());
    xAssignment.setAttribute(XMLBuilder.A_COURSEID,    assignment.getCourse().toString());
    xAssignment.setAttribute(XMLBuilder.A_TYPE,        Integer.toString(assignType));
    xAssignment.setAttribute(XMLBuilder.A_MAXSCORE,    Float.toString(assignment.getMaxScore()));

    Iterator i = assignment.getSubProblems().iterator();
    TreeMap sidToOrderMap = new TreeMap();
    TreeMap orderToElementMap = new TreeMap();
    TreeMap sidToChoiceTallyMap = new TreeMap(); // maps subproblem id to
                                                  // choice tally map
    // TreeMap cidToLetterMap = new TreeMap();
    while (i.hasNext()) {
      SubProblem subproblem = (SubProblem) i.next();
      int stype = subproblem.getType();
      int order = subproblem.getOrder();

      Element xSubproblem = xml.createElement(XMLBuilder.TAG_SUBPROBLEM);
      xSubproblem.setAttribute(XMLBuilder.A_ID, subproblem.toString());
      xSubproblem.setAttribute(XMLBuilder.A_TYPE, Integer.toString(stype));
      xSubproblem.setAttribute(XMLBuilder.A_ORDER, Integer.toString(order));
      xSubproblem.setAttribute(XMLBuilder.A_NAME, subproblem.getName());
      xSubproblem.setAttribute(XMLBuilder.A_MAXSCORE, Float.toString(subproblem
          .getMaxScore()));

      if (stype == SubProblem.MULTIPLE_CHOICE) {
        TreeMap choiceTallyMap = new TreeMap(); // maps choice text to count
        Iterator j = subproblem.getChoices().iterator();
        while (j.hasNext()) {
          Choice choice = (Choice) j.next();
          if (choice.getHidden())
            continue;
          choiceTallyMap.put(choice.getLetter(), new Integer(0));
        }
        sidToChoiceTallyMap.put(subproblem, choiceTallyMap);
      }

      orderToElementMap.put(new Integer(order), xSubproblem);
      sidToOrderMap.put(subproblem, new Integer(order));
    }

    Collection answerSets = assignment.getAnswerSets();
    xResult.setAttribute(XMLBuilder.A_COUNT, Integer.toString(answerSets.size()));
    Iterator k = answerSets.iterator();
    while (k.hasNext()) {
      AnswerSet answerSet = (AnswerSet) k.next();
      Iterator l = answerSet.getAnswers().iterator();
      while (l.hasNext()) {
        Answer answer = (Answer) l.next();
        String text = answer.getText();

        try {
          Integer order = (Integer) sidToOrderMap.get(answer.getSubProblem());
          Element xSubproblem = (Element) orderToElementMap.get(order);
          int subproblemType =
              Integer.parseInt(xSubproblem.getAttribute(XMLBuilder.A_TYPE));

          if (subproblemType == SubProblem.MULTIPLE_CHOICE) {
            TreeMap choiceCount =
                (TreeMap) sidToChoiceTallyMap.get(answer.getSubProblem());
            Integer count = (Integer) choiceCount.get(text);
            if (count != null)
              choiceCount.put(text, new Integer(count.intValue() + 1));
          } else {
            Element xAnswer = xml.createElement(XMLBuilder.TAG_ANSWER);
            xAnswer.setAttribute(XMLBuilder.A_ID, answer.toString());
            xAnswer.setAttribute(XMLBuilder.A_SUBPROBID, answer.getSubProblem().toString());
            xAnswer.setAttribute(XMLBuilder.A_TEXT, text);
            xSubproblem.appendChild(xAnswer);
          }

          orderToElementMap.put(order, xSubproblem);
        } catch (Exception ex) {
          continue;
        }
      }
    }

    Iterator sids = sidToChoiceTallyMap.entrySet().iterator();
    while (sids.hasNext()) {
      Map.Entry entry = (Map.Entry) sids.next();
      Long sid = (Long) entry.getKey();
      TreeMap choiceTallyMap = (TreeMap) entry.getValue();
      Integer order = (Integer) sidToOrderMap.get(sid);
      Element xSubProblem = (Element) orderToElementMap.get(order);
      Iterator choiceIDs = choiceTallyMap.entrySet().iterator();
      while (choiceIDs.hasNext()) {
        entry = (Map.Entry) choiceIDs.next();
        Choice choice = (Choice) entry.getKey();
        int count = ((Integer) entry.getValue()).intValue();

        String choiceText   = choice.getText();
        String choiceLetter = choice.getLetter();

        Element xChoice = xml.createElement(XMLBuilder.TAG_ANSWER);
        xChoice.setAttribute(XMLBuilder.A_LETTER, choiceLetter);
        xChoice.setAttribute(XMLBuilder.A_TEXT, choiceText);
        xChoice.setAttribute(XMLBuilder.A_COUNT, Integer.toString(count));
        xSubProblem.appendChild(xChoice);
      }
      orderToElementMap.put(order, xSubProblem);
    }

    Iterator keySet = orderToElementMap.keySet().iterator();
    while (keySet.hasNext()) {
      Element xSubproblem = (Element) orderToElementMap.get(keySet.next());
      xAssignment.appendChild(xSubproblem);
    }

    xResult.appendChild(xAssignment);
    return xResult;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
