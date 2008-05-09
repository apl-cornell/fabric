package cms.www.xml;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.FinderException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.DateTimeUtil;
import cms.www.util.Profiler;
import cms.www.util.StringUtil;

import edu.cornell.csuglab.cms.author.Principal;
import edu.cornell.csuglab.cms.base.*;

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
   *          is the hashmap of grade Data objects which represents the grade of
   *          student for all subproblems of assignment, gradeMap would be null
   *          if method used for building nonstudent page
   * @return The assignment element of the tree, with general properties set and
   *         several child nodes
   * @throws FinderException
   */
  public Element buildFullSubtree(Principal p, Document xml,
      AssignmentLocal assignment, Map gradeMap, Map answerMap)
      throws FinderException {
    Profiler.enterMethod("AssignmentXMLBuilder.buildFullSubtree",
        "AssignmentID: " + assignment.getAssignmentID());
    Element xAssignment =
        AssignmentXMLBuilder.buildGeneralSubtree(xml, assignment, null);
    xAssignment.appendChild(AssignmentXMLBuilder.buildAsgnItemsSubtree(xml,
        assignment));
    xAssignment.appendChild(AssignmentXMLBuilder.buildSolutionFilesSubtree(xml,
        assignment));
    xAssignment.appendChild(AssignmentXMLBuilder.buildSubmissionsSubtree(xml,
        assignment));
    xAssignment.appendChild(AssignmentXMLBuilder.buildSubproblemsSubtree(xml,
        assignment, gradeMap, answerMap));
    xAssignment.appendChild(ScheduleXMLBuilder.buildScheduleSubtree(p, xml,
        assignment));
    if (assignment.getScheduled()) {
      xAssignment.setAttribute(XMLBuilder.A_USESCHEDULE, "");
    }
    Profiler.exitMethod("AssignmentXMLBuilder.buildFullSubtree",
        "AssignmentID: " + assignment.getAssignmentID());
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
   * @throws FinderException
   */
  public Element buildShortSubtree(Document xml, AssignmentLocal assignment)
      throws FinderException {
    Element xAssignment = xml.createElement(TAG_ASSIGNMENT);
    CourseLocal course =
        database.courseHome().findByPrimaryKey(
            new CoursePK(assignment.getCourseID()));
    xAssignment.setAttribute(A_COURSENAME, course.getCode());
    xAssignment.setAttribute(A_NAMESHORT, assignment.getNameShort());
    xAssignment.setAttribute(A_NAME, assignment.getName());
    xAssignment.setAttribute(A_DUEDATE, DateTimeUtil.ADMIN_ASGN_DUEDATE_FORMAT
        .format(assignment.getDueDate()));
    xAssignment.setAttribute(A_ID, Long.toString(assignment.getAssignmentID()));
    return xAssignment;
  }

  public void addStatSubtree(Document xml, Element xAssignment,
      AssignmentLocal assignment) {
    Float stat;
    if ((stat = assignment.getMean()) != null) {
      xAssignment.setAttribute(A_STATMEAN, StringUtil.roundToOne(""
          + stat.floatValue()));
    }
    if ((stat = assignment.getStdDev()) != null) {
      xAssignment.setAttribute(A_STATDEV, StringUtil.roundToOne(""
          + stat.floatValue()));
    }
    if ((stat = assignment.getMax()) != null) {
      xAssignment.setAttribute(A_STATMAX, StringUtil.roundToOne(""
          + stat.floatValue()));
    }
    if ((stat = assignment.getMedian()) != null) {
      xAssignment.setAttribute(A_STATMEDIAN, StringUtil.roundToOne(""
          + stat.floatValue()));
    }
  }

  public void addBasicInfo(Document xml, Element xAssignment,
      AssignmentLocal assignment) {
    xAssignment.setAttribute(A_NAME, assignment.getName());
    xAssignment.setAttribute(A_NAMESHORT, assignment.getNameShort());
    xAssignment.setAttribute(A_ASSIGNID, Long.toString(assignment
        .getAssignmentID()));
    xAssignment.setAttribute(A_WEIGHT, StringUtil.roundToOne(Float
        .toString(assignment.getWeight())));
    xAssignment.setAttribute(A_TOTALSCORE, StringUtil.roundToOne(Float
        .toString(assignment.getMaxScore())));
    xAssignment.setAttribute(A_STATUS, assignment.getStatus());
    xAssignment.setAttribute(A_ASSIGNTYPE, Integer.toString(assignment
        .getType()));
    if (assignment.getScheduled())
      xAssignment.setAttribute(A_USESCHEDULE, "true");
  }

  /**
   * Generate an XML subtree with general info about the given Assignment
   * 
   * @param xml
   *          The Document to place this element on
   * @param assignment
   *          The assignment from which to generate the branch
   * @return The assignment element of the tree, with general properties set
   * @throws FinderException
   */
  public Element buildGeneralSubtree(Document xml, AssignmentLocal assignment,
      Map gradeMap) throws FinderException {
    Profiler.enterMethod("AssignmentXMLBuilder.buildGeneralSubtree", "");
    Element xAssignment = xml.createElement(TAG_ASSIGNMENT);
    // add description node
    Element xDescription = xml.createElement(TAG_DESCRIPTION);
    String desc = assignment.getDescription();
    xDescription.appendChild(xml.createTextNode(desc == null ? "" : desc));
    xAssignment.appendChild(xDescription);
    // less verbose attributes
    addBasicInfo(xml, xAssignment, assignment);
    int graceperiod = assignment.getGracePeriod();
    xAssignment.setAttribute(A_GRACEPERIOD, Integer.toString(graceperiod));
    xAssignment.setAttribute(A_MINGROUP, Integer.toString(assignment
        .getGroupSizeMin()));
    xAssignment.setAttribute(A_MAXGROUP, Integer.toString(assignment
        .getGroupSizeMax()));
    xAssignment.setAttribute(A_ID, Long.toString(assignment.getAssignmentID()));
    if (assignment.getShowStats()) xAssignment.setAttribute(A_SHOWSTATS, "");
    Timestamp cmpTime =
        new Timestamp(System.currentTimeMillis() - graceperiod * 60000);
    Timestamp now = new Timestamp(System.currentTimeMillis());
    Timestamp due = assignment.getDueDate();
    xAssignment.setAttribute(A_DUEDATE, DateTimeUtil.DATE.format(due));
    xAssignment.setAttribute(A_DUETIME, DateTimeUtil.TIME.format(due));
    xAssignment.setAttribute(A_DUEAMPM, DateTimeUtil.AMPM.format(due));
    if (cmpTime.after(due)) {
      xAssignment.setAttribute(A_PASTDUE, "true");
    }
    if (now.before(due)) {
      xAssignment.setAttribute(A_DUECOUNTDOWN, DateTimeUtil.formatCountdown(
          now, due));
    }
    if (assignment.getAllowLate()) {
      Timestamp late = assignment.getLateDeadline();
      xAssignment.setAttribute(A_LATEDATE, DateTimeUtil.DATE.format(late));
      xAssignment.setAttribute(A_LATETIME, DateTimeUtil.TIME.format(late));
      xAssignment.setAttribute(A_LATEAMPM, DateTimeUtil.AMPM.format(late));
      xAssignment.setAttribute(A_LATEALLOWED, "true");
      xAssignment.setAttribute(A_LATEFULLDATE, DateTimeUtil.formatDate(late));
      if (now.after(late)) {
        xAssignment.setAttribute(A_PASTLATE, "true");
      }
    }
    Timestamp regrade = assignment.getRegradeDeadline();
    if (regrade != null) {
      xAssignment
          .setAttribute(A_REGRADEDATE, DateTimeUtil.DATE.format(regrade));
      xAssignment
          .setAttribute(A_REGRADETIME, DateTimeUtil.TIME.format(regrade));
      xAssignment
          .setAttribute(A_REGRADEAMPM, DateTimeUtil.AMPM.format(regrade));
      if (now.after(regrade)) {
        xAssignment.setAttribute(A_PASTREGRADE, "true");
      }
    }
    Float grade = null;
    if (gradeMap != null)
      grade = (Float) gradeMap.get(new Long(assignment.getAssignmentID()));
    if (grade != null)
      xAssignment.setAttribute(A_SCORE, StringUtil.roundToOne(""
          + grade.floatValue()));
    addStatSubtree(xml, xAssignment, assignment);
    if (assignment.getAssignedGraders())
      xAssignment.setAttribute(A_ASSIGNEDGRADERS, "true");
    if (assignment.getStudentRegrades())
      xAssignment.setAttribute(A_STUDENTREGRADES, "true");
    if (assignment.getAssignedGroups())
      xAssignment.setAttribute(A_ASSIGNEDGROUPS, "true");
    if (assignment.getShowStats())
      xAssignment.setAttribute(A_SHOWSTATS, "true");
    if (assignment.getShowSolution())
      xAssignment.setAttribute(A_SHOWSOLUTION, "true");
    if (assignment.getScheduled())
      xAssignment.setAttribute(A_USESCHEDULE, "true");
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
   * @throws FinderException
   */
  public Element buildAsgnItemsSubtree(Document xml, AssignmentLocal assignment)
      throws FinderException {
    Element xItems = xml.createElement(TAG_ITEMS);
    // visible items
    Iterator i = assignment.getAssignmentItems().iterator();
    while (i.hasNext()) {
      AssignmentItemData itemData = (AssignmentItemData) i.next();
      AssignmentItemLocal item =
          database.assignmentItemHome().findByPrimaryKey(
              new AssignmentItemPK(itemData.getAssignmentItemID()));
      AssignmentFileLocal file = item.getAssignmentFile();
      Collection hiddenFiles = item.getHiddenAssignmentFiles();
      Element xItem = xml.createElement(TAG_ITEM);
      xItem.setAttribute(A_NAME, itemData.getItemName());
      xItem.setAttribute(A_ID, Long.toString(itemData.getAssignmentItemID()));
      Element xFile = xml.createElement(TAG_FILE);
      xItem.appendChild(xFile);
      xFile.setAttribute(A_NAME, file.getItemName());
      xFile.setAttribute(A_ID, Long.toString(file.getAssignmentFileID()));
      Iterator hid = hiddenFiles.iterator();
      while (hid.hasNext()) {
        AssignmentFileData hidFile = (AssignmentFileData) hid.next();
        Element xHidFile = xml.createElement(TAG_HIDDENFILE);
        xItem.appendChild(xHidFile);
        xHidFile.setAttribute(A_NAME, hidFile.getFileName());
        xHidFile.setAttribute(A_ID, Long
            .toString(hidFile.getAssignmentFileID()));
        xHidFile.setAttribute(A_DATE, DateTimeUtil.NUMMONTH_DAY_TIME
            .format(hidFile.getFileDate()));
      }
      xItems.appendChild(xItem);
    }
    // deleted items
    i = assignment.getHiddenAssignmentItems().iterator();
    while (i.hasNext()) {
      AssignmentItemData itemData = (AssignmentItemData) i.next();
      AssignmentItemLocal item =
          database.assignmentItemHome().findByPrimaryKey(
              new AssignmentItemPK(itemData.getAssignmentItemID()));
      AssignmentFileData file =
          item.getAssignmentFile().getAssignmentFileData();
      Element xHidItem = xml.createElement(TAG_HIDDENITEM);
      xItems.appendChild(xHidItem);
      xHidItem.setAttribute(A_NAME, itemData.getItemName());
      xHidItem
          .setAttribute(A_ID, Long.toString(itemData.getAssignmentItemID()));
      xHidItem.setAttribute(A_DATE, DateTimeUtil.NUMMONTH_DAY_TIME.format(file
          .getFileDate()));
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
   * @throws FinderException
   */
  public Element buildSolutionFilesSubtree(Document xml,
      AssignmentLocal assignment) throws FinderException {
    Element xSolutions = xml.createElement(TAG_SOLUTIONS);
    if (assignment.hasSolutionFile()) {
      SolutionFileLocal sf = assignment.getSolutionFile();
      Element xSolution = xml.createElement(TAG_SOLFILE);
      xSolution.setAttribute(A_FILENAME, sf.getFileName());
      xSolution.setAttribute(A_PATH, sf.getPath());
      xSolution.setAttribute(A_ID, Long.toString(sf.getSolutionFileID()));
      xSolutions.appendChild(xSolution);
    }
    Iterator i = assignment.getHiddenSolutionFiles().iterator();
    while (i.hasNext()) {
      SolutionFileData sfd = (SolutionFileData) i.next();
      Element xSolution = xml.createElement(TAG_HIDDENSOLFILE);
      xSolution.setAttribute(A_FILENAME, sfd.getFileName());
      xSolution.setAttribute(A_ID, Long.toString(sfd.getSolutionFileID()));
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
   * @throws FinderException
   */
  public Element buildSubmissionsSubtree(Document xml,
      AssignmentLocal assignment) throws FinderException {
    Element xSubmissions = xml.createElement(TAG_SUBMISSIONS);
    // visible submissions
    Iterator i = assignment.getRequiredSubmissions().iterator();
    // We need to match RequiredFiles with the same FileName
    Hashtable nameMatch = new Hashtable();
    while (i.hasNext()) {
      RequiredSubmissionData subData = (RequiredSubmissionData) i.next();
      xSubmissions.appendChild(buildSubmissionSubtree(xml, database
          .requiredSubmissionHome().findByPrimaryKey(
              new RequiredSubmissionPK(subData.getSubmissionID())), TAG_ITEM));
    }
    // Deleted submissions
    i = assignment.getHiddenRequiredSubmissions().iterator();
    nameMatch.clear();
    while (i.hasNext()) {
      RequiredSubmissionData subData = (RequiredSubmissionData) i.next();
      xSubmissions.appendChild(buildSubmissionSubtree(xml, database
          .requiredSubmissionHome().findByPrimaryKey(
              new RequiredSubmissionPK(subData.getSubmissionID())),
          TAG_HIDDENITEM));
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
   *          The type of submission: legal values are TAG_ITEM and
   *          TAG_HIDDENITEM
   * @return An element holding info on a submission
   * @throws FinderException
   */
  private Element buildSubmissionSubtree(Document xml,
      RequiredSubmissionLocal submission, String submissionTypeTag)
      throws FinderException {
    Element xFile = xml.createElement(submissionTypeTag);
    Iterator i = submission.getRequiredFileTypes().iterator();
    xFile.setAttribute(A_ID, Long.toString(submission.getSubmissionID()));
    xFile.setAttribute(A_NAME, submission.getSubmissionName());
    xFile.setAttribute(A_SIZE, Integer.toString(submission.getMaxSize()));
    String formats = "";
    while (i.hasNext()) {
      RequiredFileTypeData type = (RequiredFileTypeData) i.next();
      Element xFormat = xml.createElement(TAG_FORMAT);
      xFile.appendChild(xFormat);
      xFormat.setAttribute(A_TYPE, type.getFileType());
      formats = (!formats.equals("")) ? formats + ", " : formats;
      formats = formats + type.getFileType();
    }
    xFile.setAttribute(A_TYPELIST, formats);
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
   *          is the hashmap of grade Data objects which represents the grade of
   *          student for all subproblems of assignment, gradeMap would be null
   *          if method used for building nonstudent page
   * @return An element holding a list of subproblems
   * @throws FinderException
   */
  public Element buildSubproblemsSubtree(Document xml,
      AssignmentLocal assignment, Map gradeMap, Map answerMap)
      throws FinderException {
    Element xSubproblems = xml.createElement(TAG_SUBPROBS);
    // TreeMap orderToSubProblem = new TreeMap();
    // visible subproblems
    // Iterator i= assignment.getSubProblems().iterator();

    // put the subproblems in a tree map whose keys are the subproblem orders
    // so that we process the subproblems in the right order
    Iterator i =
        database.subProblemHome().findByAssignmentID(
            assignment.getAssignmentID()).iterator();
    /*
     * while (i.hasNext()) { SubProblemLocal sp = (SubProblemLocal) i.next();
     * orderToSubProblem.put(new Integer(sp.getOrder()), sp); } Iterator j =
     * orderToSubProblem.keySet().iterator();
     */
    while (i.hasNext()) {
      // SubProblemLocal sp= (SubProblemLocal)orderToSubProblem.get(j.next());
      SubProblemLocal sp = (SubProblemLocal) i.next();
      Element xProb = xml.createElement(TAG_SUBPROBLEM);
      xProb.setAttribute(A_NAME, sp.getSubProblemName());
      xProb.setAttribute(A_TOTALSCORE, StringUtil.trimTrailingZero(Float
          .toString(sp.getMaxScore())));
      xProb.setAttribute(A_ID, Long.toString(sp.getSubProblemID()));
      xProb.setAttribute(A_TYPE, Long.toString(sp.getType()));
      xProb.setAttribute(A_ORDER, Long.toString(sp.getOrder()));
      if (gradeMap != null) {
        Float grade = (Float) gradeMap.get(new Long(sp.getSubProblemID()));
        if (grade != null)
          xProb.setAttribute(A_SCORE, StringUtil.roundToOne(grade.toString()));
      }
      if (answerMap != null) {
        String answer = (String) answerMap.get(new Long(sp.getSubProblemID()));
        if (answer != null) xProb.setAttribute(A_ANSWER, answer);
      }

      if (sp.getType() == SubProblemBean.MULTIPLE_CHOICE) {
        xProb.setAttribute(A_CORRECTANSWER, Integer.toString(sp.getAnswer()));
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
      SubProblemData sp = (SubProblemData) i.next();
      Element xProb = xml.createElement(TAG_HIDDENSUBPROB);
      xProb.setAttribute(A_NAME, sp.getSubProblemName());
      xProb.setAttribute(A_TOTALSCORE, StringUtil.trimTrailingZero(Float
          .toString(sp.getMaxScore())));
      xProb.setAttribute(A_ID, Long.toString(sp.getSubProblemID()));
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
   * @throws FinderException
   */

  public Element buildSubProblemChoicesSubtree(Document xml, SubProblemLocal sp)
      throws FinderException {
    Element xChoices = xml.createElement(TAG_CHOICES);

    // visible subproblems
    Iterator i =
        database.choiceHome().findBySubProblemID(sp.getSubProblemID(), false)
            .iterator();

    while (i.hasNext()) {
      ChoiceLocal choice = ((ChoiceLocal) i.next());
      Element xChoice = xml.createElement(TAG_CHOICE);
      xChoice.setAttribute(A_LETTER, choice.getLetter());
      xChoice.setAttribute(A_TEXT, choice.getText());
      xChoice.setAttribute(A_ID, Long.toString(choice.getChoiceID()));
      xChoice
          .setAttribute(A_SUBPROBID, Long.toString(choice.getSubProblemID()));
      xChoices.appendChild(xChoice);
    }
    // deleted (hidden) subproblems
    Collection debug =
        database.choiceHome().findBySubProblemID(sp.getSubProblemID(), true);
    i = debug.iterator();
    while (i.hasNext()) {
      ChoiceLocal choice = (ChoiceLocal) i.next();
      Element xChoice = xml.createElement(TAG_HIDDENCHOICE);
      xChoice.setAttribute(A_LETTER, choice.getLetter());
      xChoice.setAttribute(A_TEXT, choice.getText());
      xChoice.setAttribute(A_ID, Long.toString(choice.getChoiceID()));
      xChoices.appendChild(xChoice);
    }
    return xChoices;
  }

  public Element buildSurveySubtree(Document xml, Collection surveys)
      throws FinderException {
    Element xSurveys = xml.createElement(TAG_SURVEYS);
    Iterator i = surveys.iterator();
    while (i.hasNext()) {
      AssignmentLocal survey = (AssignmentLocal) i.next();
      Element xSurvey = xml.createElement(TAG_SURVEY);

      // include basic information and also total submissions
      Collection answerSets =
          database.answerSetHome().findByAssignmentID(survey.getAssignmentID());
      int numSubmissions = (answerSets != null) ? answerSets.size() : 0;
      Timestamp due = survey.getDueDate();

      xSurvey.setAttribute(A_ASSIGNID, Long.toString(survey.getAssignmentID()));
      xSurvey.setAttribute(A_NAME, survey.getName());
      xSurvey.setAttribute(A_COUNT, Integer.toString(numSubmissions));
      xSurvey.setAttribute(A_STATUS, survey.getStatus());
      xSurvey.setAttribute(A_DUEDATE, DateTimeUtil.DATE.format(due));

      xSurveys.appendChild(xSurvey);
    }

    return xSurveys;
  }

  public Element buildSurveyResultSubtree(Document xml,
      AssignmentLocal assignment) throws FinderException {
    long assignID = assignment.getAssignmentID();
    int assignType = assignment.getType();
    CourseLocal course =
        database.courseHome().findByCourseID(assignment.getCourseID());
    Element xResult = xml.createElement(TAG_SURVEYRESULT);
    xResult.setAttribute(A_CODE, course.getCode());
    xResult.setAttribute(A_COURSENAME, course.getName());

    Element xAssignment = xml.createElement(TAG_ASSIGNMENT);
    xAssignment.setAttribute(A_ID, Long.toString(assignID));
    xAssignment.setAttribute(A_NAME, assignment.getName());
    xAssignment.setAttribute(A_DESCRIPTION, assignment.getDescription());
    xAssignment.setAttribute(A_COURSEID, Long
        .toString(assignment.getCourseID()));
    xAssignment.setAttribute(A_TYPE, Integer.toString(assignType));
    xAssignment.setAttribute(A_MAXSCORE, Float.toString(assignment
        .getMaxScore()));

    Iterator i =
        database.subProblemHome().findByAssignmentID(assignID).iterator();
    TreeMap sidToOrderMap = new TreeMap();
    TreeMap orderToElementMap = new TreeMap();
    TreeMap sidToChoiceTallyMap = new TreeMap(); // maps subproblem id to
                                                  // choice tally map
    // TreeMap cidToLetterMap = new TreeMap();
    while (i.hasNext()) {
      SubProblemLocal subproblem = (SubProblemLocal) i.next();
      long sid = subproblem.getSubProblemID();
      int stype = subproblem.getType();
      int order = subproblem.getOrder();

      Element xSubproblem = xml.createElement(TAG_SUBPROBLEM);
      xSubproblem.setAttribute(A_ID, Long.toString(sid));
      xSubproblem.setAttribute(A_TYPE, Integer.toString(stype));
      xSubproblem.setAttribute(A_ORDER, Integer.toString(order));
      xSubproblem.setAttribute(A_NAME, subproblem.getSubProblemName());
      xSubproblem.setAttribute(A_MAXSCORE, Float.toString(subproblem
          .getMaxScore()));

      if (stype == SubProblemBean.MULTIPLE_CHOICE) {
        TreeMap choiceTallyMap = new TreeMap(); // maps choice text to count
        Iterator j =
            database.choiceHome().findBySubProblemID(sid, false).iterator();
        while (j.hasNext()) {
          ChoiceLocal choice = (ChoiceLocal) j.next();
          String letter = choice.getLetter();
          long cid = choice.getChoiceID();
          choiceTallyMap.put(new Long(cid), new Integer(0));
        }
        sidToChoiceTallyMap.put(new Long(sid), choiceTallyMap);
      }

      orderToElementMap.put(new Integer(order), xSubproblem);
      sidToOrderMap.put(new Long(sid), new Integer(order));
    }

    Collection answerSets =
        database.answerSetHome().findByAssignmentID(assignID);
    xResult.setAttribute(A_COUNT, Integer.toString(answerSets.size()));
    Iterator k = answerSets.iterator();
    while (k.hasNext()) {
      AnswerSetLocal answerSet = (AnswerSetLocal) k.next();
      Iterator l = answerSet.getAnswers().iterator();
      while (l.hasNext()) {
        AnswerData answer = (AnswerData) l.next();
        long id = answer.getAnswerID();
        long sid = answer.getSubProblemID();
        String text = answer.getText();

        try {
          Integer order = (Integer) sidToOrderMap.get(new Long(sid));
          Element xSubproblem = (Element) orderToElementMap.get(order);
          int subproblemType =
              Integer.parseInt(xSubproblem.getAttribute(A_TYPE));

          if (subproblemType == SubProblemBean.MULTIPLE_CHOICE) {
            try {
              Long choiceID = new Long(text);
              Long subproblemID = new Long(sid);
              TreeMap choiceCount =
                  (TreeMap) sidToChoiceTallyMap.get(subproblemID);
              Integer count = (Integer) choiceCount.get(choiceID);
              if (count != null)
                choiceCount.put(choiceID, new Integer(count.intValue() + 1));
              sidToChoiceTallyMap.put(subproblemID, choiceCount);
            } catch (ClassCastException ex) {
              continue;
            }
          } else {
            Element xAnswer = xml.createElement(TAG_ANSWER);
            xAnswer.setAttribute(A_ID, Long.toString(id));
            xAnswer.setAttribute(A_SUBPROBID, Long.toString(sid));
            xAnswer.setAttribute(A_TEXT, text);
            xSubproblem.appendChild(xAnswer);
          }

          orderToElementMap.put(order, xSubproblem);
        } catch (Exception ex) {
          continue;
        }
      }
    }

    Iterator sids = sidToChoiceTallyMap.keySet().iterator();
    while (sids.hasNext()) {
      Long sid = (Long) sids.next();
      TreeMap choiceTallyMap = (TreeMap) sidToChoiceTallyMap.get(sid);
      Integer order = (Integer) sidToOrderMap.get(sid);
      Element xSubProblem = (Element) orderToElementMap.get(order);
      Iterator choiceIDs = choiceTallyMap.keySet().iterator();
      while (choiceIDs.hasNext()) {
        Long cid = (Long) choiceIDs.next();
        int count = ((Integer) choiceTallyMap.get(cid)).intValue();

        ChoiceLocal choice =
            database.choiceHome().findByChoiceID(cid.longValue());
        String choiceText = choice.getText();
        String choiceLetter = choice.getLetter();

        Element xChoice = xml.createElement(TAG_ANSWER);
        xChoice.setAttribute(A_LETTER, choiceLetter);
        xChoice.setAttribute(A_TEXT, choiceText);
        xChoice.setAttribute(A_COUNT, Integer.toString(count));
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
