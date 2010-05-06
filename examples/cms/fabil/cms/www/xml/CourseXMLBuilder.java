package cms.www.xml;

import fabric.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cms.www.util.Profiler;
import cms.www.util.StringUtil;

import cms.auth.Principal;
import cms.model.*;

/**
 * Builds an XML subtree describing a course in as much detail as desired
 * Created 3 / 26 / 05
 * 
 * @author Evan
 */
public class CourseXMLBuilder {
  private XMLBuilder xmlBuilder;
  
  public CourseXMLBuilder(XMLBuilder builder) {
    this.xmlBuilder = builder;
  }

  /**
   * Generate an XML subtree with all known info about the given Course that
   * relates to the given Principal
   * 
   * @param user
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param course
   *          The course from which to generate the branch
   * @return The course element of the tree, with general properties set and
   *         several subtrees
   * @throws FinderException
   * @deprecated This method is very slow and should usually be avoided
   */
  public Element buildFullSubtree(User user, Document xml, Course course) {
    Profiler.enterMethod("CourseXMLBuilder.buildFullSubtree", "Course: " + course.toString());
    boolean isStaff = user.isStaffInCourseByCourse(course);
    Element xCourse = buildGeneralSubtree(user, xml, course);
    xCourse.appendChild(xmlBuilder.announcementXMLBuilder.buildAnnouncementsSubtree(user, xml, course));
    xmlBuilder.announcementXMLBuilder.appendHiddenAnnouncements(xml, course);
    xCourse.appendChild(buildAssignmentsSubtree(user, xml, course));
    xCourse.appendChild(buildSurveySubtree(user, xml, course));
    xCourse.appendChild(buildHiddenAssignsSubtree(xml, course));
    xCourse.appendChild(buildAssignmentFilesSubtree(user, xml, course));
    xCourse.appendChild(buildCategoriesSubtree(user, xml, course));
    if (isStaff) xCourse.appendChild(buildStaffListSubtree(user, xml, course));
    Profiler.exitMethod("CourseXMLBuilder.buildFullSubtree", "Course: " + course.toString());
    return xCourse;
  }

  public Element buildSurveySubtree(User p, Document xml, Course course) {
    Collection surveys = new ArrayList();
    Iterator i = course.getAssignments().iterator();
    while (i.hasNext()) {
      Assignment assignment = (Assignment) i.next();
      boolean isOpen = assignment.getStatus() == Assignment.OPEN;
      boolean isSurvey = assignment.getType() == Assignment.SURVEY;
      if (isSurvey && (isOpen || p.isStaffInCourseByCourse(course)))
        surveys.add(assignment);
    }
    return xmlBuilder.assignmentXMLBuilder.buildSurveySubtree(xml, surveys);
  }

  /**
   * Sets the privileges of a course element of the XML tree
   * 
   * @param user
   *          User principal making this request
   * @param xCourse
   *          Element of the tree representing the course
   * @param course
   *          CourseData for the course
   */
  private void setCoursePrivileges(User user, Element xCourse, Course course) {
    // the given principal's permissions for this course
    if (!user.isInStaffAsBlankMode()) {
      if (user.isAdminPrivByCourse(course))
        xCourse.setAttribute(XMLBuilder.A_ISADMIN, "true");
      if (user.isGroupsPrivByCourse(course))
        xCourse.setAttribute(XMLBuilder.A_ISGROUPS, "true");
      if (user.isGradesPrivByCourse(course))
        xCourse.setAttribute(XMLBuilder.A_ISGRADES, "true");
      if (user.isAssignPrivByCourse(course))
        xCourse.setAttribute(XMLBuilder.A_ISASSIGN, "true");
      if (user.isCategoryPrivByCourse(course))
        xCourse.setAttribute(XMLBuilder.A_ISCATEGORY, "true");
    } else {
      xCourse.setAttribute(XMLBuilder.A_ISVIEWAS, "true");
    }
    
    Student student = course.getStudent(user);
    if (student != null && student.getStatus().equals(Student.ENROLLED)) {
      xCourse.setAttribute(XMLBuilder.A_ISSTUDENT, "true");
      xCourse.setAttribute(XMLBuilder.A_TOTALSCORE, StringUtil.roundToOne(String
          .valueOf(student.getTotalScore())));
    }
  }

  /**
   * Generate an XML subtree with a bit of general info about the given Course
   * Used primarily for the navigation bar
   * 
   * @param xml
   *          The Document to place this element on
   * @param course
   *          The course from which to generate the branch
   * @return The course element of the tree, with some general properties set
   * @throws FinderException
   */
  public Element buildShortSubtree(User p, Document xml, Course course) {
    Element xCourse = xml.createElement(XMLBuilder.TAG_COURSE);
    xCourse.setAttribute(XMLBuilder.A_COURSEID,      course.toString());
    xCourse.setAttribute(XMLBuilder.A_CODE,          course.getCode());
    xCourse.setAttribute(XMLBuilder.A_DISPLAYEDCODE, course.getDisplayedCode());
    xCourse.setAttribute(XMLBuilder.A_COURSENAME,    course.getName());
    xCourse.setAttribute(XMLBuilder.A_COURSEFROZEN,
        Boolean.toString(course.getFreezeCourse()));
    return xCourse;
  }

  /**
   * Generates an XML subtree with the general information for listing course in
   * homepage
   * 
   * @param xml
   * @param course
   * @return
   * @throws FinderException
   */
  public Element buildHomepageSubtree(Document xml, Course course) {
    Element xCourse = xml.createElement(XMLBuilder.TAG_COURSE);
    xCourse.setAttribute(XMLBuilder.A_COURSEID,   course.toString());
    xCourse.setAttribute(XMLBuilder.A_CODE,       course.getCode());
    xCourse.setAttribute(XMLBuilder.A_COURSENAME, course.getName());
    return xCourse;
  }

  /**
   * Fill in an element with the basic properties of a course, including its
   * semester
   * 
   * @param xml
   * @param course
   * @return
   * @throws FinderException
   */
  public Element buildBasicPropNode(User user, Document xml, Course course) {
    // general course data
    Element xCourse = xml.createElement(XMLBuilder.TAG_COURSE);
    xCourse.setAttribute(XMLBuilder.A_COURSEID,      course.toString());
    xCourse.setAttribute(XMLBuilder.A_CODE,          course.getCode());
    xCourse.setAttribute(XMLBuilder.A_DISPLAYEDCODE, course.getDisplayedCode());
    xCourse.setAttribute(XMLBuilder.A_COURSENAME,    course.getName());
    xCourse.setAttribute(XMLBuilder.A_SEMESTER,      course.getSemester().getName());

    if (course.getHasSection()) xCourse.setAttribute(XMLBuilder.A_HASSECTION, "true");

    setCoursePrivileges(user, xCourse, course);
    return xCourse;
  }

  public void addTotalScoreStats(Course course, Element xCourse) {
    // Float score = course.getMaxTotalScore();

    Float totalScore = course.getMaxTotalScore();
    Float highTotalScore = course.getHighTotalScore();
    Float meanTotalScore = course.getMeanTotalScore();
    Float medianTotalScore = course.getMedianTotalScore();
    Float stDev = course.getStDevTotalScore();

    if (totalScore == null || totalScore.toString().equals("NaN"))
      xCourse.setAttribute(XMLBuilder.A_MAXTOTALSCORE, "0.0");
    else xCourse.setAttribute(XMLBuilder.A_MAXTOTALSCORE, StringUtil.roundToOne(totalScore
        .toString()));

    if (highTotalScore == null || highTotalScore.toString().equals("NaN"))
      xCourse.setAttribute(XMLBuilder.A_HIGHTOTALSCORE, "0.0");
    else xCourse.setAttribute(XMLBuilder.A_HIGHTOTALSCORE, StringUtil
        .roundToOne(highTotalScore.toString()));

    if (meanTotalScore == null || meanTotalScore.toString().equals("NaN"))
      xCourse.setAttribute(XMLBuilder.A_MEANTOTALSCORE, "0.0");
    else xCourse.setAttribute(XMLBuilder.A_MEANTOTALSCORE, StringUtil
        .roundToOne(meanTotalScore.toString()));

    if (medianTotalScore == null || medianTotalScore.toString().equals("NaN"))
      xCourse.setAttribute(XMLBuilder.A_MEDIANTOTALSCORE, "0.0");
    else xCourse.setAttribute(XMLBuilder.A_MEDIANTOTALSCORE, StringUtil
        .roundToOne(medianTotalScore.toString()));

    if (stDev == null || stDev.toString().equals("NaN"))
      xCourse.setAttribute(XMLBuilder.A_STDEVTOTALSCORE, "0.0");
    else xCourse.setAttribute(XMLBuilder.A_STDEVTOTALSCORE, StringUtil.roundToOne(stDev
        .toString()));

    // xCourse.setAttribute(XMLBuilder.A_HIGHTOTALSCORE, score == null ? "0.0" :
    // StringUtil.roundToOne(score.toString()));
    // xCourse.setAttribute(XMLBuilder.A_MEANTOTALSCORE, score == null ? "0.0" :
    // StringUtil.roundToOne(score.toString()));
    // xCourse.setAttribute(XMLBuilder.A_MEDIANTOTALSCORE, score == null ? "0.0" :
    // StringUtil.roundToOne(score.toString()));
    // xCourse.setAttribute(XMLBuilder.A_STDEVTOTALSCORE, score == null ? "0.0" :
    // StringUtil.roundToOne(score.toString()));
  }

  public void addCourseProperties(Course course, Element xCourse) {
    xCourse.setAttribute(XMLBuilder.A_COURSEFROZEN, Boolean.toString(course
        .getFreezeCourse()));
    if (course.getShowFinalGrade()) {
      xCourse.setAttribute(XMLBuilder.A_SHOWFINALGRADES, "true");
    }
    if (course.getShowTotalScores()) {
      xCourse.setAttribute(XMLBuilder.A_SHOWTOTALSCORES, "true");
    }
    if (course.getShowAssignWeights()) {
      xCourse.setAttribute(XMLBuilder.A_SHOWASSIGNWEIGHTS, "true");
    }
    if (course.getHasSection()) {
      xCourse.setAttribute(XMLBuilder.A_HASSECTION, "true");
    }
  }

  public void addAccessInfo(Course course, Element xCourse) {
    if (course.getCourseGuestAccess()) {
      xCourse.setAttribute(XMLBuilder.A_COURSEGUESTACCESS, "true");
    }
    if (course.getCourseCCAccess()) {
      xCourse.setAttribute(XMLBuilder.A_COURSECCACCESS, "true");
    }
    if (course.getAssignGuestAccess()) {
      xCourse.setAttribute(XMLBuilder.A_ASSIGNGUESTACCESS, "true");
    }
    if (course.getAssignCCAccess()) {
      xCourse.setAttribute(XMLBuilder.A_ASSIGNCCACCESS, "true");
    }
    if (course.getAnnounceGuestAccess()) {
      xCourse.setAttribute(XMLBuilder.A_ANNOUNCEGUESTACCESS, "true");
    }
    if (course.getAnnounceCCAccess()) {
      xCourse.setAttribute(XMLBuilder.A_ANNOUNCECCACCESS, "true");
    }
    if (course.getSolutionGuestAccess()) {
      xCourse.setAttribute(XMLBuilder.A_SOLUTIONGUESTACCESS, "true");
    }
    if (course.getSolutionCCAccess()) {
      xCourse.setAttribute(XMLBuilder.A_SOLUTIONCCACCESS, "true");
    }
  }

  /**
   * Generate an XML subtree with general info about the given Course
   * 
   * @param user
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param course
   *          The course from which to generate the branch
   * @return The course element of the tree, with general properties set
   * @throws FinderException
   */
  public Element buildGeneralSubtree(User user, Document xml, Course course) {
    Profiler.enterMethod("CourseXMLBuilder.buildGeneralSubtree", "CourseID: "
        + course.toString());
    Element xCourse = buildBasicPropNode(user, xml, course);

    Student student = course.getStudent(user);
    if (student != null && student.getStatus().equals(Student.ENROLLED) &&
        student.getFinalGrade() != null) {
      xCourse.setAttribute(XMLBuilder.A_FINALGRADE, student.getFinalGrade());
    }

    Element xDescription = xml.createElement(XMLBuilder.TAG_DESCRIPTION);
    xDescription.appendChild(xml.createTextNode(course.getDescription()));
    xCourse.appendChild(xDescription);
    // behavioral flags
    xCourse.setAttribute(XMLBuilder.A_SHOWGRADERID, Boolean.toString(course
        .getShowGraderNetID()));
    addCourseProperties(course, xCourse);
    addAccessInfo(course, xCourse);
    addTotalScoreStats(course, xCourse);
    // This info is rarely used, and only when the user is CMS Admin
    if (user.isCMSAdmin()) {
      Collection/*Student*/ enrolledStudents = course.findActiveStudents();
      xCourse.setAttribute(XMLBuilder.A_ENROLLMENT,
          Integer.toString(enrolledStudents.size()));
    }
    Profiler.exitMethod("CourseXMLBuilder.buildGeneralSubtree", "CourseID: " + course.toString());
    return xCourse;
  }

  /**
   * Generate an XML subtree with info on staff in the given Course
   * 
   * @param p
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param course
   *          The course from which to generate the branch
   * @return An XML element with a staff list for the given course
   * @throws FinderException
   */
  public Element buildStaffListSubtree(User p, Document xml, Course course) {
    Element xStaff = xml.createElement(XMLBuilder.TAG_STAFF);
    Iterator i = course.getStaff().iterator();
    while (i.hasNext()) {
      Staff sd = (Staff) i.next();
      Element xItem = xml.createElement(XMLBuilder.TAG_ITEM);
      User u = sd.getUser();
      xItem.setAttribute(XMLBuilder.A_NETID, sd.getUser().getNetID());
      xItem.setAttribute(XMLBuilder.A_NAME,  u.getFirstName() + " " + u.getLastName());
      if (sd.getAdminPriv()) xItem.setAttribute(XMLBuilder.A_ISADMIN, "");
      if (sd.getGroupsPriv()) xItem.setAttribute(XMLBuilder.A_ISGROUPS, "");
      if (sd.getGradesPriv()) xItem.setAttribute(XMLBuilder.A_ISGRADES, "");
      if (sd.getAssignmentsPriv()) xItem.setAttribute(XMLBuilder.A_ISASSIGN, "");
      if (sd.getCategoryPriv()) xItem.setAttribute(XMLBuilder.A_ISCATEGORY, "");
      xStaff.appendChild(xItem);
    }
    return xStaff;
  }

  /**
   * Generate an XML subtree with a list of assignments for the given Course
   * 
   * @param user
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param course
   *          The course from which to generate the branch
   * @return An XML element with assignment info for the given course
   * @throws FinderException
   */
  public Element buildAssignmentsSubtree(User user, Document xml, Course course) {
    Profiler.enterMethod("CourseXMLBuilder.buildAssignmentsSubtree", "");
    boolean isstaff = user.isStaffInCourseByCourse(course);
    boolean isstudent = user.isStudentInCourseByCourse(course);
    Element xAssignments = xml.createElement(XMLBuilder.TAG_ASSIGNMENTS);

    Iterator/*Assignment*/ i = course.getAssignments().iterator();
    Collection/*TODO*/ surveys = new ArrayList();
    Map/*TODO*/ gradeMap = course.getGradeMap(user);
    int count = 1;
    while (i.hasNext()) {
      Assignment assignment = (Assignment) i.next();
      boolean show = assignment.getStatus() != Assignment.HIDDEN;
      if (assignment.getType() != Assignment.SURVEY && (isstaff || show)) {
        xAssignments.appendChild(xmlBuilder.assignmentXMLBuilder.buildGeneralSubtree(xml,assignment, gradeMap));
      }
    }

    // xAssignments.appendChild(AssignmentXMLBuilder.buildSurveySubtree(xml,
    // surveys));

    Profiler.exitMethod("CourseXMLBuilder.buildAssignmentsSubtree", "");
    return xAssignments;
  }

  public Element buildHiddenAssignsSubtree(Document xml, Course course) {
    Element xHiddenAssigns = xml.createElement(XMLBuilder.TAG_HIDDENASSIGNMENTS);
    Iterator i = course.findHiddenAssignments().iterator();

    while (i.hasNext()) {
      Assignment assign = (Assignment) i.next();
      Element xHiddenAssign =
          xml.createElement(XMLBuilder.TAG_HIDDENASSIGNMENT);
      xHiddenAssign.setAttribute(XMLBuilder.A_ASSIGNID,   assign.toString());
      xHiddenAssign.setAttribute(XMLBuilder.A_ASSIGNNAME, assign.getName());
      xHiddenAssign.setAttribute(XMLBuilder.A_NAMESHORT,  assign.getNameShort());
      xHiddenAssigns.appendChild(xHiddenAssign);
    }
    return xHiddenAssigns;
  }

  /**
   * Generate an XML subtree with info on all visible and hidden categories in
   * the given Course
   * 
   * @param user
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param course
   *          The course from which to generate the branch
   * @return A XMLBuilder.TAG_CATEGORIES element with category data for the given course,
   *         in the form of child nodes
   * @throws FinderException
   */
  public Element buildCategoriesSubtree(User user, Document xml, Course course) {
    Element xCategories = xml.createElement(XMLBuilder.TAG_CATEGORIES);
    Iterator catIter = course.getCategories(user).iterator();
    while (catIter.hasNext()) {
      Category ctg = (Category) catIter.next();
      
      if (user.getAuthoriznLevelByCourse(course) >= ctg.getAuthLevel())
        continue;
      
      Element xCategory = xmlBuilder.categoryXMLBuilder.buildShortSubtree(xml, ctg);
      // build and append column and row data
      Element xColumns  = xmlBuilder.categoryXMLBuilder.buildNonremovedColumnListsSubtree(xml, ctg);
      Element xVisibleRows = xml.createElement(XMLBuilder.TAG_VISIBLEROWS),
              xHiddenRows  = xml.createElement(XMLBuilder.TAG_HIDDENROWS);
      xmlBuilder.categoryXMLBuilder.buildRowListSubtrees(xml, ctg, xVisibleRows, xHiddenRows);
      // we only want nonhidden data
      xCategory.appendChild(xColumns);
      xCategory.appendChild(xVisibleRows);
      xCategories.appendChild(xCategory);
    }
    return xCategories;
  }

  /**
   * Generate an XML subtree with info on files provided for assignments in the
   * given Course
   * 
   * @param p
   *          The Principal to generate the page for
   * @param xml
   *          The Document to place this element on
   * @param course
   *          The course from which to generate the branch
   * @return An XML element with assignment file info for the given course
   * @throws FinderException
   */
  public Element buildAssignmentFilesSubtree(User p, Document xml, Course course) {
    Element xItems = xml.createElement(XMLBuilder.TAG_ITEMS);
    Iterator i = course.getAllAssignmentFiles().iterator();
    while (i.hasNext()) {
      Element xFile = xml.createElement(XMLBuilder.TAG_ITEM);
      xItems.appendChild(xFile);
      AssignmentFile data = (AssignmentFile) i.next();
      xFile.setAttribute(XMLBuilder.A_NAME, data.getFile().getName());
      // TODO Fix after AssignmentFiles are implemented fully
      // xFile.setAttribute(XMLBuilder.A_DATE, Util.formatDate(data.getDate(),
      // Util.SMALL));
      // xFile.setAttribute(XMLBuilder.A_ID, Long.toString(data.getAssignmentFileID()));
    }
    return xItems;
  }

}
