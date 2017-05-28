package cms.model;

import java.net.InetAddress;
import java.util.Collections;

import java.util.*;

/**
 * This class is the root container for all of the objects in the CMS Database.
 * Note that comments are descriptions of the ``real'' CMS, and may not yet
 * reflect the Fabric re-implementation.
 *
 * @author mdgeorge
 * @see edu.cornell.csuglab.cms.base.Root
 * @see edu.cornell.csuglab.cms.base.RootDAO
 */
public class CMSRoot {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private final int                  maxFileSize;
  private final Map/*String,String*/ fileTypes;
  private final boolean              debugMode;
  private Semester             currentSemester;
  private final User           guestUser;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

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
  final Map/*String, RequiredSubmission*/ requiredSubmissions; //Managed by RequiredSubmission.
  final Map/*String, AssignmentItem*/ assignmentItems; //Managed by AssignmentItem
  final Map/*String, SubProblem*/ subProblems; //Managed by SubProblem
  final Collection/*Log*/ logs; // Managed by Log.
  
  // XXX YUCK  These wouldn't be needed if our URLs gave us more information.
  final Map/*String, Assignment*/ assignments; // Managed by Assignment.
  final Map/*String, Group*/ groups; // Managed by Group.

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public CMSRoot() {
    this.users = new HashMap/*String, User*/();
    this.semesters = new HashMap/*String, Semester*/();
    this.notices = new HashMap/*String, SiteNotice*/();
    this.courses = new HashMap/*String, Course*/();
    this.logs = new ArrayList();
    this.assignments = new HashMap();
    this.groups = new HashMap();
    this.requiredSubmissions = new HashMap();
    this.assignmentItems = new HashMap();
    this.subProblems = new HashMap();
    
    this.debugMode = true;

    setCurrentSemester(new Semester(this, "Summer 2008"));
    this.guestUser = new User(this, "guest", "Guest", "User", "0", "none");
    maxFileSize = 10000000;
    
    // Create default file types.
    this.fileTypes = new TreeMap();
    fileTypes.put(RequiredSubmission.ANY_TYPE, "Any file type");
    fileTypes.put("txt", "Text document");
    fileTypes.put("zip", "Zip archive");
    fileTypes.put("java", "Java source file");
    fileTypes.put("pdf", "Adobe Acrobat portable document file");
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
    //TODO: enhance with indexing
    Collection result = new ArrayList();
    Iterator i = logs.iterator();
    while(i.hasNext()) {
      Log l = (Log)i.next();
      if (l.getCourse().toString() != course.toString())
        continue;
      for (Iterator di = l.getDetailLogs().iterator(); di.hasNext();) {
        LogDetail d = (LogDetail)di.next();
        Group g = d.getAssignment().findGroup(d.getAffectedUser());
        for (Iterator gi = groups.iterator(); gi.hasNext();)
        if (g.equals(gi.next())) {
          result.add(g);
          break;
        }
      }
    }
    return result;
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
    List result = new ArrayList();
    
    String actingNetID = params.getActingNetID();
    String receivingNetID = params.getReceivingNetID();
    String simulatedNetID = params.getSimulatedNetID();
    String ip = params.getActingIPAddress();
    Date startTime = params.getStartTime();
    Date endTime = params.getEndTime();
    long logTypes = params.getLogTypes();
    Collection logNames = params.getLogNames();
    Long courseID = params.getCourseID();
    Long assignmentID = params.getAssignmentID();
    
    for (Iterator it = logs.iterator(); it.hasNext();) {
      Log log = (Log) it.next();
      
      if (actingNetID != null && !actingNetID.equals(log.getActingNetID()))
        continue;
      
      if (receivingNetID != null) {
        Map receivingUsers = log.getReceivingUsers();
        if (receivingUsers == null
            || !receivingUsers.containsKey(receivingNetID)) continue;
      }
      
      if (simulatedNetID != null
          && !simulatedNetID.equals(log.getSimulatedNetID())) continue;
      
      if (ip != null) {
        InetAddress actingIPAddress = log.getActingIPAddress();
        if (actingIPAddress == null || !ip.equals(actingIPAddress.toString()))
          continue;
      }
      
      Date timestamp = log.getTimestamp();
      if (startTime != null && timestamp.before(startTime)) continue;
      
      if (endTime != null && timestamp.after(endTime)) continue;
      
      if ((params.getLogTypes() & log.getLogType()) == 0) continue;
      
      if (!logNames.isEmpty() && !logNames.contains(log.getLogName()))
        continue;
      
      if (courseID != null && !courseID.equals(log.getClass().toString()))
        continue;
      
      if (assignmentID != null) throw new NotImplementedException(); // XXX
      
      result.add(log);
    }
    
    return result;
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
    return (Assignment) assignments.get(assignID);
  }
  
  public Group getGroup(String groupID) {
    return (Group) groups.get(groupID);
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
    if (subProblemID.equals("0")) return null;
    return (SubProblem)subProblems.get(subProblemID);
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
    return (AssignmentItem)assignmentItems.get(id);
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
    return (RequiredSubmission)requiredSubmissions.get(id);
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
