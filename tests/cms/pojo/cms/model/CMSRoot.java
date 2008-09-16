package cms.model;

import java.io.IOException;
import java.util.Collections;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cms.auth.Principal;

/**
 * This class is the root container for all of the objects in the CMS Database.
 * Note that comments are descriptions of the ``real'' CMS, and may not yet
 * reflect the fabric re-implementation.
 *
 * @author mdgeorge
 * @see edu.cornell.csuglab.cms.base.Root
 * @see edu.cornell.csuglab.cms.base.RootDAO
 */
public class CMSRoot {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private int                  maxFileSize;
  private Map/*String,String*/ fileTypes;
  private boolean              debugMode;
  private Semester             currentSemester;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setMaxFileSize     (final int maxFileSize)          { this.maxFileSize     = maxFileSize;     }
  public void setDebugMode       (final boolean debugMode)        { this.debugMode       = debugMode;       }
  public void setCurrentSemester (final Semester currentSemester) { this.currentSemester = currentSemester; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public int      getMaxFileSize()     { return this.maxFileSize;     }
  public boolean  getDebugMode()       { return this.debugMode;       }
  public Semester getCurrentSemester() { return this.currentSemester; }

  /**
   * Return the collection of allowed file types.
   * @return a Collection containing <code>String</code>s, each of which is the
   *         extension corresponding to an acceptable file type
   */
  public Collection/*String*/ getFileTypes() {
    return Collections.unmodifiableSet(this.fileTypes.keySet());
  }

  //////////////////////////////////////////////////////////////////////////////
  // managed fields                                                           //
  //////////////////////////////////////////////////////////////////////////////

  Collection/*User*/ users;

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public CMSRoot() {
    setCurrentSemester(addSemester("Summer 2008"));
    this.debugMode = true;
  }

  //////////////////////////////////////////////////////////////////////////////
  // public methods                                                           //
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Set up the system state if necessary.  This includes:
   * <ol>
   *  <li>Create the current semester (corresponding to now)</li>
   *  <li>Create the SystemProperties:
   *   <ul>
   *    <li>current semester</li>
   *    <li>debugmode = false</li>
   *    <li>uploadMaxFileSize = 10000000)</li> </ul> </li>
   *  <li>Create the default file types:
   *   <ul>
   *    <li>Any file type</li>
   *    <li>Text document: txt</li>
   *    <li>Zip archive: zip</li>
   *    <li>Java source file: java</li>
   *    <li>Adobe Acrobat portable document file: pdf</li> </ul> </li> </ol>
   */
  public void ensureStartupSettings() {
    throw new NotImplementedException();
  }

  /**
   * @return the number of distinct CUIDs found in the user table
   */
  public int getCUIDCount() {
    throw new NotImplementedException();
  }
  
  public Collection/*User*/ getAllUsers() {
    throw new NotImplementedException();
  }
  
  public Collection/*LogDetail*/ findGradeLogDetails(Course course, Collection/*Group*/ groups) {
    throw new NotImplementedException();
  }
  public Collection/*Assignment*/ findAssignmentsBySemester(Semester curSemester) {
    throw new NotImplementedException();
  }
  public Collection/*Assignment*/ findAssignmentsByCourseAdmin(Semester curSemester, User user) {
    throw new NotImplementedException();
  }
  public Collection/*Announcement*/ findAnnouncementsByUserDateSemester(User user, Date weekago, Semester sem) {
    throw new NotImplementedException();
  }
  public Collection/*Announcement*/ findAnnouncementsByUserDate(User user, Date weekago) {
    throw new NotImplementedException();
  }
  public Collection/*Semester*/ getAllSemesters() {
    throw new NotImplementedException();
  }
  public Collection/*User*/ findAllAdmins() {
    // TODO: check on return type; might not be user
    throw new NotImplementedException();
  }
  public Collection/*User*/ findActiveStudentsWithoutCUID() {
    throw new NotImplementedException();
  }
  public Collection/*Course*/ findCCAccessCourses() {
    throw new NotImplementedException();
  }
  public Collection/*Course*/ findGuestAccessCourses() {
    throw new NotImplementedException();
  }
  public Collection/*Assignment*/ findOpenAssignmentsByDeadline(Date checkDeadline) {
    throw new NotImplementedException();
  }
  public Collection/*Log*/ findLogs(LogSearchParams params) {
    throw new NotImplementedException();
  }
  public Collection/*SiteNotice*/ findCurrentSiteNoticeShowing() {
    throw new NotImplementedException();
  }
  public Collection/*SiteNotice*/ findAllLivingSiteNotices() {
    throw new NotImplementedException();
  }
  public Collection/*SiteNotice*/ findDeletedSiteNotices() {
    throw new NotImplementedException();
  }
  public Collection/*User*/ findMissingNameUsers() {
    throw new NotImplementedException();
  }
  public Collection assignedToGroups(Assignment assign, User user, List/*Group*/ list) {
    throw new NotImplementedException();
  }
  public User getGuestUser() {
    throw new NotImplementedException();
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // index methods                                                            //
  //  these methods are index methods getX : String -> X                      //
  //  These should be left-inverses of toString(), i.e.                       //
  //  db.getFoo(o.toString()) == o                                            //
  //////////////////////////////////////////////////////////////////////////////
  
  public Assignment getAssignment(String assignID) {
    throw new NotImplementedException();
  }
  
  public Group getGroup(String groupID) {
    throw new NotImplementedException();
  }
  
  public Semester getSemester(String semesterID) {
    throw new NotImplementedException();
  }
  
  public Course getCourse(String courseID) {
    throw new NotImplementedException();
  }
  
  public Announcement getAnnouncement(String announceID) {
    throw new NotImplementedException();
  }
  
  public User getUser(String netID) {
    throw new NotImplementedException();
  }
  
  public Category getCategory(String catID) {
    throw new NotImplementedException();
  }
  
  public SiteNotice getSiteNotice(String noticeID) {
    throw new NotImplementedException();
  }
  
  public CategoryRow getCategoryRow(String rowID) {
    throw new NotImplementedException();
  }
  
  public TimeSlot getTimeSlot(String slotID) {
    throw new NotImplementedException();
  }
  
  public SubProblem getSubProblem(String subProblemID) {
    throw new NotImplementedException();
  }
  
  public CategoryContents getCategoryContents(String contentsID) {
    throw new NotImplementedException();
  }
  
  public RegradeRequest getRegradeRequest(String regradeID) {
    throw new NotImplementedException();
  }
  
  public CategoryColumn getCategoryColumn(String columnID) {
    throw new NotImplementedException();
  }
  
  public SolutionFile getSolutionFile(String id) {
    throw new NotImplementedException();
  }
  
  public AssignmentItem getAssignmentItem(String id) {
    throw new NotImplementedException();
  }
  
  public AssignmentFile getAssignmentFile(String id) {
    throw new NotImplementedException();
  }
  
  public SubmittedFile getSubmittedFile(String id) {
    throw new NotImplementedException();
  }
  
  public SubmittedFile getCategoryContentsFileEntry(String id) {
    throw new NotImplementedException();
  }
  
  public FileData getFileData(String id) {
    throw new NotImplementedException();
  }
  
  public CommentFile getCommentFile(String id) {
    throw new NotImplementedException();
  }
  
  public RequiredSubmission getRequiredSubmission(String id) {
    throw new NotImplementedException();
  }
  
  public Choice getChoice(String id) {
    throw new NotImplementedException();
  }
  
  public Grade getGrade(String id) {
    throw new NotImplementedException();
  }
  
  public GroupGrade getGroupGrade(String id) {
    throw new NotImplementedException();
  }
  
  public Comment getComment(String id) {
    throw new NotImplementedException();
  }

  //////////////////////////////////////////////////////////////////////////////
  // database creation methods                                                //
  //////////////////////////////////////////////////////////////////////////////

  public Announcement addAnnouncement(Course course, User author, String text) {
    throw new NotImplementedException();
  }

  public Answer addAnswer(AnswerSet answerSet, SubProblem sub, String text) {
    throw new NotImplementedException();
  }

  public AnswerSet addAnswerSet(Assignment assignment, Group group, Group originalGroup, User user, Date submitted) {
    throw new NotImplementedException();
  }

  public Assignment addAssignment(Course course, String name, String nameShort, Date due) {
    throw new NotImplementedException();
  }

  public AssignmentFile addAssignmentFile(AssignmentItem item, boolean isHidden, FileData file) {
    throw new NotImplementedException();
  }

  public AssignmentItem addAssignmentItem(Assignment assign, String name) {
    throw new NotImplementedException();
  }

  public Category addCategory(Course course, String name, boolean ascending,
                              CategoryColumn sortBy, int numToShow,
                              boolean hidden, int fileCount,
                              int authorizationLevel, int position,
                              boolean announcements) {
    throw new NotImplementedException();
  }

  public CategoryColumn addCategoryColumn(String name, String type, Category category, boolean hidden, boolean removed, int position) {
    throw new NotImplementedException();
  }

  public CategoryContentsDate addCategoryContentsDate(CategoryColumn column, CategoryRow row, Date value) {
    throw new NotImplementedException();
  }

  public CategoryContentsFileEntry addCategoryContentsFileEntry(CategoryContentsFileList list, boolean hidden, FileData file, String linkName) {
    throw new NotImplementedException();
  }

  public CategoryContentsFileList addCategoryContentsFileList(CategoryRow row, CategoryColumn col) {
    throw new NotImplementedException();
  }

  public CategoryContentsLink addCategoryContentsLink(CategoryRow row, CategoryColumn col, String address, String label) {
    throw new NotImplementedException();
  }

  public CategoryContentsNumber addCategoryContentsNumber(CategoryColumn col, CategoryRow row, int value) {
    throw new NotImplementedException();
  }

  public CategoryContentsString addCategoryContentsString(CategoryColumn col, CategoryRow row, String value) {
    throw new NotImplementedException();
  }

  public CategoryRow addCategoryRow(Category category, boolean hidden, Date releaseDate) {
    throw new NotImplementedException();
  }

  public Choice addChoice(SubProblem subproblem, String letter, String text, boolean hidden) {
    throw new NotImplementedException();
  }

  public Comment addComment(String comment, User user, Group group) {
    throw new NotImplementedException();
  }

  public CommentFile addCommentFile(Comment comment, FileData file) {
    throw new NotImplementedException();
  }

  public Course addCourse(String name, String description, String code, Semester semester) {
    throw new NotImplementedException();
  }

  public Email addEmail(Course course, User sender, String subject, String message, int recipient) {
    throw new NotImplementedException();
  }

  public FileData addFileData(String fileName) throws IOException {
    throw new NotImplementedException();
  }

  public Grade addGrade(Assignment assign, SubProblem subProblem, Float grade, User user, User grader) {
    throw new NotImplementedException();
  }

  public Group addGroup(Assignment assign, int remainingSubmissions) {
    throw new NotImplementedException();
  }

  public GroupAssignedTo addGroupAssignedTo(Group group, User user, SubProblem subProblem) {
    throw new NotImplementedException();
  }

  public GroupGrade addGroupGrade(Group group, float score, boolean isAveraged, SubProblem subproblem) {
    throw new NotImplementedException();
  }

  public GroupMember addGroupMember(Group group, Student user, String status) {
    throw new NotImplementedException();
  }

  public Log addLog() {
    throw new NotImplementedException();
  }

  public LogDetail addLogDetail (Log log, String detail, User user, Assignment assign) {
    throw new NotImplementedException();
  }

  public OldAnnouncement addOldAnnouncement(Announcement announcement, String text) {
    throw new NotImplementedException();
  }

  public RegradeRequest addRegradeRequest(SubProblem subProblem, Group group, User user, String request) {
    throw new NotImplementedException();
  }

  public RequiredSubmission addRequiredSubmission(Assignment assign, String name, int maxSize) {
    throw new NotImplementedException();
  }

  public Semester addSemester(String name) {
    throw new NotImplementedException();
  }

  public SiteNotice addSiteNotice(User author, String text, Date exp, boolean hidden) {
    throw new NotImplementedException();
  }

  public SolutionFile addSolutionFile(Assignment assign, boolean hidden, FileData data) {
    throw new NotImplementedException();
  }

  public Staff addStaff(User user, Course course) {
    throw new NotImplementedException();
  }

  public Student addStudent(Course course, User user) {
    throw new NotImplementedException();
  }

  public SubProblem addSubProblem(Assignment assign, String name, float maxScore, int type, int order, int answer) {
    throw new NotImplementedException();
  }

  public SubmittedFile addSubmittedFile(Group group, Group originalGroup, User user, RequiredSubmission submission, String fileType, boolean lateSubmission, Date fileDate, FileData file) {
    throw new NotImplementedException();
  }

  public TimeSlot addTimeSlot() {
    throw new NotImplementedException();
  }

  public User addUser(String netID, String firstName, String lastName, String CUID, String college) {
    throw new NotImplementedException();
  }


}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
