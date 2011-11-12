package cms.model;

import java.util.*;

import cms.auth.Principal;

// TODO: not sure if User should extend Principal
// TODO: figure out when to use what
public class User implements Principal {

  public static final int AUTHORIZATION_LEVEL_STAFF = 1;
  public static final int AUTHORIZATION_LEVEL_STUDENT = 2;
  public static final int AUTHORIZATION_LEVEL_CORNELL_COMMUNITY = 3;
  public static final int AUTHORIZATION_LEVEL_GUEST = 3;
  
  public static final Comparator LAST_NAME_COMPARATOR = new Comparator() {
    public int compare(Object o1, Object o2) {
      if (!(o1 instanceof User && o2 instanceof User)) return 0;
      User u1 = (User) o1;
      User u2 = (User) o2;
      int result = u1.getLastName().compareTo(u2.getLastName());
      if (result != 0) return result;
      
      result = u1.getFirstName().compareTo(u2.getFirstName());
      if (result != 0) return result;
      
      return u1.getNetID().compareTo(u2.getNetID());
    }
  };
  
  public static final Comparator NETID_COMPARATOR = new Comparator() {
    public int compare(Object o1, Object o2) {
      if (!(o1 instanceof User && o2 instanceof User)) return 0;
      User u1 = (User) o1;
      User u2 = (User) o2;
      return u1.getNetID().compareTo(u2.getNetID());
    }
  };
  
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private final CMSRoot db;
  private boolean isAdmin;
  private String userID;
  private String firstName;
  private String lastName;
  private String CUID;
  private String college;

  //////////////////////////////////////////////////////////////////////////////
  // managed fields                                                           //
  //////////////////////////////////////////////////////////////////////////////

  Map/*Course, Student*/ studentIndex;  // managed by student class
  Map/*Course, Staff*/  staffIndex;     // managed by staff   class
  
  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setNetID     (final String userID)    {
    // guarantee uniqueness of NetIDs
    if (db.users.containsKey(userID))
      throw new IllegalArgumentException();
    
    // remove under old netID
    if (userID != null)
      db.users.remove(userID);
    
    // add under new netID
    this.userID    = userID;
    db.users.put(userID, this);
  }
  
  public void setFirstName (final String firstName) { this.firstName = firstName; }
  public void setLastName  (final String lastName)  { this.lastName  = lastName;  }
  public void setCUID      (final String CUID)      { this.CUID      = CUID;      }
  public void setCollege   (final String college)   { this.college   = college;   }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public String getNetID()     { return this.userID;    }
  public String getFirstName() { return this.firstName; }
  public String getLastName()  { return this.lastName;  }
  public String getCUID()      { return this.CUID;      }
  public String getCollege()   { return this.college;   }
  
  public User (CMSRoot db, String netID, String firstName, String lastName, String CUID, String college) {
    this.db = db;
    this.isAdmin = false;
    this.studentIndex   = new HashMap/*Course, Student*/();
    this.staffIndex     = new HashMap/*Course, Staff*/();
    
    setNetID(netID); // adds this to db
    setFirstName(firstName);
    setLastName(lastName);
    setCUID(CUID);
    setCollege(college);
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // public methods                                                           //
  //////////////////////////////////////////////////////////////////////////////
  
  public boolean missingCUID() {
    return CUID == null || CUID.isEmpty() || CUID.equals("0");
  }

  public Collection/*Course*/ findStaffCourses() {
    return findStaffCoursesBySemester(db.getCurrentSemester());
  }
  
  public Collection/*Course*/ findStaffCoursesBySemester(Semester semester) {
    SortedSet result = new TreeSet();
    
    for (Iterator it = staffIndex.values().iterator(); it.hasNext();) {
      Staff staff = (Staff) it.next();
      Course course = staff.getCourse();
      if (staff.getStatus().equals(Staff.ACTIVE) && course.getSemester() == semester)
        result.add(course);
    }
    return result;
  }
  
  public Collection/*Course*/ findStudentCourses() {
    return findStudentCoursesBySemester(db.getCurrentSemester());
  }
  
  public Collection/*Course*/ findStudentCoursesBySemester(Semester semester) {
    SortedSet result = new TreeSet();
    
    for (Iterator it = studentIndex.values().iterator(); it.hasNext();) {
      Student student = (Student) it.next();
      Course course = student.getCourse();
      if (student.getStatus().equals(Student.ENROLLED) && course.getSemester() == semester)
        result.add(course);
    }
    return result;
  }
  
  public Collection/*Assignment*/ findAssignmentsByDate(Date current) {
    return findAssignmentsByDateAndSemester(current, db.getCurrentSemester());
  }
  
  public Collection/* Assignment */findAssignmentsByDateAndSemester(
      Date current, Semester semester) {
    Set result = new HashSet();
    for (Iterator cit = findStudentCoursesBySemester(semester).iterator(); cit.hasNext();) {
      Course course = (Course) cit.next();
      for (Iterator ait = course.getAssignments().iterator(); ait.hasNext();) {
        Assignment assign = (Assignment) ait.next();
        if (!assign.getHidden() && assign.getStatus().equals(Assignment.OPEN) && assign.getDueDate().after(current)) {
          result.add(assign);
        }
      }
    }
    
    return result;
  }
  
  public Collection/*Announcement*/ findAnnouncementsByDate(Date fromDate) {
    return findAnnouncementsByDateAndSemester(fromDate, db.getCurrentSemester());
  }
  
  public Collection/*Announcement*/findAnnouncementsByDateAndSemester(
      Date fromDate, Semester semester) {
    SortedSet result = new TreeSet();
    
    for (Iterator cit = findStudentCoursesBySemester(semester).iterator(); cit.hasNext();) {
      Course course = (Course) cit.next();
      for (Iterator ait = course.getAnnouncements().iterator(); ait.hasNext();) {
        Announcement announcement = (Announcement) ait.next();
        if (!announcement.getHidden() && announcement.getPosted().after(fromDate))
          result.add(announcement);
      }
    }
    
    return result;
  }

  public String canonicalName() {
    if (getFirstName().length() > 0)
      return getFirstName() + " " + getLastName();
    else
      return getLastName();
  }
  
  public Collection/*Semester*/ findSemesters() {
    SortedSet result = new TreeSet();

    for (Iterator it = studentIndex.values().iterator(); it.hasNext();) {
      Student student = (Student) it.next();
      if (student.getStatus().equals(Student.ENROLLED)) {
        Semester semester = student.getCourse().getSemester();
        if (!semester.getHidden()) result.add(semester);
      }
    }

    for (Iterator it = staffIndex.values().iterator(); it.hasNext();) {
      Staff staff = (Staff) it.next();
      if (staff.getStatus().equals(Staff.ACTIVE)) {
        Semester semester = staff.getCourse().getSemester();
        if (!semester.getHidden()) result.add(semester);
      }
    }
    
    return result;
  }
  
  /**
   * @return the unique course for this user (as staff or student) for the given
   *         semester, or null if the student is enrolled in more or less than
   *         one course.
   */
  public Course getSoloCourse(Semester semester) {
    Course result = null;

    // loop through students
    Iterator/*Student*/ i = studentIndex.values().iterator();
    while(i.hasNext()) {
      Student s = (Student) i.next();
     
      if (s.getCourse().getSemester() != semester)
        continue;
      else if (result != null)
        // course is not unique
        return null;
      else
        result = s.getCourse();
    }
    
    // loop through staff
    Iterator/*Staff*/ j = staffIndex.values().iterator();
    while (j.hasNext()) {
      Staff s = (Staff) j.next();
      
      if (s.getCourse().getSemester() != semester)
        continue;
      else if (result != null)
        // course is not unique
        return null;
      else
        result = s.getCourse();
    }
    
    return result;
  }
  
  public boolean hasAssignAccess(Course course, Assignment assign) {
    return hasCourseAccess(course);
  }
  public boolean isInStaffAsBlankMode() {
    return false; // XXX
  }
  public boolean isInStaffAsCornellMemMode() {
    return false; // XXX
  }
  public boolean isInStaffAsGuestMode() {
    return false; // XXX
  }
  public boolean isInStaffAsStudentMode() {
    return false; // XXX
  }
  public boolean isAdminPrivByCourseID(String courseID) {
    return isAdminPrivByCourse(db.getCourse(courseID));
  }
  public boolean isAdminPrivByCourse(Course course) {
    Staff staff = (Staff) staffIndex.get(course);
    return staff != null && staff.getStatus().equals(Staff.ACTIVE)
        && staff.getAdminPriv();
  }
  public boolean isGroupsPrivByCourse(Course course) {
    Staff staff = (Staff) staffIndex.get(course);
    return staff != null && staff.getStatus().equals(Staff.ACTIVE)
        && staff.getGroupsPriv();
  }
  public boolean isGradesPrivByCourse(Course course) {
    Staff staff = (Staff) staffIndex.get(course);
    return staff != null && staff.getStatus().equals(Staff.ACTIVE)
        && staff.getGradesPriv();
  }
  public boolean isAssignPrivByCourse(Course course) {
    Staff staff = (Staff) staffIndex.get(course);
    return staff != null && staff.getStatus().equals(Staff.ACTIVE)
        && staff.getAssignmentsPriv();
  }
  public boolean isCategoryPrivByCourse(Course course) {
    Staff staff = (Staff) staffIndex.get(course);
    return staff != null && staff.getStatus().equals(Staff.ACTIVE)
        && staff.getCategoryPriv();
  }
  public boolean isStaffInCourseByCourse(Course course) {
    Staff staff = (Staff) staffIndex.get(course);
    return staff != null && staff.getStatus().equals(Staff.ACTIVE);
  }
  public boolean isCMSAdmin() {
    return isAdmin;
  }
  public void setCMSAdmin(boolean status) {
    isAdmin = status;
  }
  public boolean isStudentInCourseByCourse(Course course) {
    Student student = (Student) studentIndex.get(course);
    return student != null && student.getStatus().equals(Student.ENROLLED);
  }
  
  public int getAuthoriznLevelByCourse(Course course) {
    if (isGuest()) return AUTHORIZATION_LEVEL_GUEST;
    if (isStudentInCourseByCourse(course)) return AUTHORIZATION_LEVEL_STUDENT;
    if (isStaffInCourseByCourse(course)) return AUTHORIZATION_LEVEL_STAFF;
    return AUTHORIZATION_LEVEL_CORNELL_COMMUNITY;
  }
  
  public boolean isAssignPrivByAssignment(Assignment assignment) {
    return isAssignPrivByCourse(assignment.getCourse());
  }
  public boolean isAuthenticated() {
    return this != db.getGuestUser();
  }
  public boolean isGuest() {
    return this == db.getGuestUser();
  }
  public boolean delegatesTo(Principal delagatee) {
    return false;
  }
  public boolean hasCourseAccess(Course course) {
    if (isStaffInCourseByCourse(course) || isStudentInCourseByCourse(course))
      return true;
    
    if (isAuthenticated()) return course.getCourseCCAccess();
    
    return course.getCourseGuestAccess();
  }
  
  public boolean hasStudentsPageAccess(Course course) {
    return isAdminPrivByCourse(course) || isGroupsPrivByCourse(course)
        && isGradesPrivByCourse(course);
  }
  public boolean isAdminPrivByAssignment(Assignment assign) {
    return isAdminPrivByCourse(assign.getCourse());
  }
  public boolean isCategoryPrivByCategory(Category category) {
    return isGroupsPrivByCourse(category.getCourse());
  }
  public boolean isGradesPrivByAssignment(Assignment assign) {
    return isGradesPrivByCourse(assign.getCourse());
  }
  public boolean isGroupsPrivByAssignment(Assignment assign) {
    return isGroupsPrivByCourse(assign.getCourse());
  }
  public boolean isStudentInCourseByAssignment(Assignment assign) {
    return isStudentInCourseByCourse(assign.getCourse());
  }
  public boolean isStudentInCourseByGroup(Group group) {
    return isStudentInCourseByAssignment(group.getAssignment());
  }
  public boolean canView(SolutionFile sol) {
    // See TransactionHandler.authorizeDownload
    throw new NotImplementedException();
  }
  /**
   * return true if this user is a staff member assigned to grade a given student.
   * @param student
   * @param assignment
   * @return
   */
  public boolean isAssignedTo(Student student, Assignment assignment) {
    throw new NotImplementedException();
  }
  public boolean isAssignedTo(Group group, Assignment assignment) {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
