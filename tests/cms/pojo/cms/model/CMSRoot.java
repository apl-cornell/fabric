package cms.model;

import java.util.Collections;

import java.util.*;

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
  private final User           guestUser;

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

  final Map/*String, User*/ users; // Managed by User.
  final Map/*String, Semester*/ semesters; // Managed by Semester.
  final Map/*String, SiteNotice*/ notices; // Managed by SiteNotice.
  final Map/*String, Course*/ courses; // Managed by Course.  Maps CourseIDs to Courses.
  final Collection/*Log*/ logs; // Managed by Log.

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public CMSRoot() {
    this.debugMode = true;
    
    this.users = new HashMap/*String, User*/();
    this.semesters = new HashMap/*String, Semester*/();
    this.notices = new HashMap/*String, SiteNotice*/();
    this.courses = new HashMap/*String, Course*/();
    this.logs = new ArrayList();

    setCurrentSemester(new Semester(this, "Summer 2008"));
    this.guestUser = new User(this, "guest", "Guest", "User", "0", "none");
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
    Set cuidSet = new HashSet();
    for (Iterator it = users.values().iterator(); it.hasNext();) {
      User user = (User) it.next();
      if (!user.missingCUID()) cuidSet.add(user.getCUID());
    }
    
    return cuidSet.size();
  }
  
  public Collection/*User*/ getAllUsers() {
    return Collections.unmodifiableCollection(users.values());
  }
  
  public Collection/*LogDetail*/ findGradeLogDetails(Course course, Collection/*Group*/ groups) {
    throw new NotImplementedException();
  }
  public Collection/*Assignment*/ findAssignmentsByCourseAdmin(Semester curSemester, User user) {
    throw new NotImplementedException();
  }
  public Collection/*Semester*/ getAllSemesters() {
    SortedSet result = new TreeSet();
    result.addAll(semesters.values());
    return result;
  }
  public Collection/*User*/ findAllAdmins() {
    List result = new ArrayList();
    for (Iterator it = users.values().iterator(); it.hasNext();) {
      User user = (User) it.next();
      if (user.isCMSAdmin()) result.add(user);
    }
    
    return result;
  }
  public Collection/*User*/ findActiveStudentsWithoutCUID() {
    SortedSet result = new TreeSet();
    
    for (Iterator uit = users.values().iterator(); uit.hasNext();) {
      User user = (User) uit.next();
      if (!user.missingCUID()) continue;
      
      // Ensure the user is actively enrolled in a course as a student.
      boolean enrolled = false;
      for (Iterator sit = user.studentIndex.values().iterator(); !enrolled
          && sit.hasNext();) {
        enrolled = ((Student) sit.next()).getStatus().equals(Student.ENROLLED);
      }
      
      if (enrolled) result.add(user);
    }
    
    return result;
  }
  public Collection/*Course*/ findCCAccessCourses() {
    return getCurrentSemester().findCCAccessCourses();
  }
  public Collection/*Course*/ findGuestAccessCourses() {
    return getCurrentSemester().findGuestAccessCourses();
  }
  public Collection/*Assignment*/ findOpenAssignmentsByDeadline(Date checkDeadline) {
    // Subtract a bit from current time to allow for grace periods of very
    // recently due assignments.
    long MILLISECS_PER_HOUR = 1000 * 60 * 60;
    Date now = new Date(new Date().getTime() - 8 * MILLISECS_PER_HOUR);
    SortedSet result = new TreeSet();
    Collection courses = getCurrentSemester().getCourses();
    for (Iterator cit = courses.iterator(); cit.hasNext();) {
      Course course = (Course) cit.next();
      for (Iterator ait = course.getAssignments().iterator(); ait.hasNext();) {
        Assignment assignment = (Assignment) ait.next();
        if (!assignment.getHidden() && assignment.getDueDate().after(now)) {
          result.add(assignment);
        }
      }
    }
    
    return result;
  }
  public Collection/*Log*/ findLogs(LogSearchParams params) {
    throw new NotImplementedException();
  }
  public Collection/*SiteNotice*/ findCurrentSiteNoticeShowing() {
    Date now = new Date();
    
    SortedSet result = new TreeSet();
    
    for (Iterator it = notices.values().iterator(); it.hasNext();) {
      SiteNotice notice = (SiteNotice) it.next();
      if (!notice.getHidden() && !notice.getDeleted() && notice.getExpireDate().after(now))
        result.add(notice);
    }
    
    return result;
  }
  public Collection/*SiteNotice*/ findAllLivingSiteNotices() {
    SortedSet result = new TreeSet();
    
    for (Iterator it = notices.values().iterator(); it.hasNext();) {
      SiteNotice notice = (SiteNotice) it.next();
      if (!notice.getDeleted()) result.add(notice);
    }
    
    return result;
  }
  public Collection/*SiteNotice*/ findDeletedSiteNotices() {
    SortedSet result = new TreeSet();
    for (Iterator it = notices.values().iterator(); it.hasNext();) {
      SiteNotice notice = (SiteNotice) it.next();
      if (notice.getDeleted()) result.add(notice);
    }
    
    return result;
  }
  
  public Collection/*User*/ findMissingNameUsers() {
    List result = new ArrayList();
    for (Iterator it = users.values().iterator(); it.hasNext();) {
      User user = (User) it.next();
      if (user.getFirstName().isEmpty() || user.getLastName().isEmpty())
        result.add(user);
    }
    
    return result;
  }
  
  public Collection assignedToGroups(Assignment assign, User user, List/*Group*/ list) {
    throw new NotImplementedException();
  }
  public User getGuestUser() {
    return this.guestUser;
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
    return (Semester) semesters.get(semesterID);
  }
  
  public Course getCourse(String courseID) {
    return (Course) courses.get(courseID);
  }
  
  public Announcement getAnnouncement(String announceID) {
    throw new NotImplementedException();
  }
  
  public User getUser(String netID) {
    return (User) users.get(netID);
  }
  
  public Category getCategory(String catID) {
    throw new NotImplementedException();
  }
  
  public SiteNotice getSiteNotice(String noticeID) {
    return (SiteNotice) notices.get(noticeID);
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
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
