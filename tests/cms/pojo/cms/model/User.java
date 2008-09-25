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

  Collection/*Student*/ studentCourses; // managed by student class
  Map/*Course, Student*/ studentIndex;  // managed by student class
  Collection/*Staff*/   staffCourses;   // managed by staff   class
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
    this.studentCourses = new ArrayList/*Student*/();
    this.staffCourses   = new ArrayList/*Staff*/  ();
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

  public Collection/*Course*/ findStaffCourses() {
    return findStaffCoursesBySemester(db.getCurrentSemester());
  }
  
  public Collection/*Course*/ findStaffCoursesBySemester(Semester semester) {
    SortedSet result = new TreeSet(new Comparator(){
      public int compare(Object o1, Object o2) {
        return ((String) o1).compareTo((String) o2);
      }
    });
    
    for (Iterator it = staffCourses.iterator(); it.hasNext();) {
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
    SortedSet result = new TreeSet(new Comparator() {
      public int compare(Object o1, Object o2) {
        return ((String) o1).compareTo((String) o2);
      }
    });
    
    for (Iterator it = studentCourses.iterator(); it.hasNext();) {
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
    SortedSet result = new TreeSet(new Comparator() {
      public int compare(Object o1, Object o2) {
        Announcement a1 = (Announcement) o1;
        Announcement a2 = (Announcement) o2;
        return -a1.getPosted().compareTo(a2.getPosted());
      }
    });
    
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
    SortedSet result = new TreeSet(new Comparator() {
      public int compare(Object o1, Object o2) {
        String name1 = ((Semester) o1).getName();
        String name2 = ((Semester) o2).getName();
        
        int year1 = Integer.parseInt(name1.substring(name1.indexOf(' ')+1));
        int year2 = Integer.parseInt(name2.substring(name2.indexOf(' ')+1));
        if (year1 != year2) return year1 - year2;
        
        String session1 = name1.substring(0, name1.indexOf(' '));
        String session2 = name2.substring(0, name2.indexOf(' '));
        if (session1.equalsIgnoreCase(session2)) return 0;
        
        if (session1.equalsIgnoreCase("Spring")) return -1;
        if (session2.equalsIgnoreCase("Spring")) return 1;
        if (session1.equalsIgnoreCase("Summer")) return 1;
        if (session2.equalsIgnoreCase("Summer")) return 1;
        if (session1.equalsIgnoreCase("Fall")) return -1;
        if (session2.equalsIgnoreCase("Fall")) return 1;
        if (session1.equalsIgnoreCase("Winter")) return 1;
        if (session2.equalsIgnoreCase("Winter")) return 1;
        
        return 0;
      }
    });

    for (Iterator it = studentCourses.iterator(); it.hasNext();) {
      Student student = (Student) it.next();
      if (student.getStatus().equals(Student.ENROLLED)) {
        Semester semester = student.getCourse().getSemester();
        if (!semester.getHidden()) result.add(semester);
      }
    }

    for (Iterator it = staffCourses.iterator(); it.hasNext();) {
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
    Iterator/*Student*/ i = studentCourses.iterator();
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
    Iterator/*Staff*/ j = staffCourses.iterator();
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
    throw new NotImplementedException();
  }
  public boolean isInStaffAsBlankMode() {
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
    throw new NotImplementedException();
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
    throw new NotImplementedException();
  }
  public boolean isAdminPrivByAssignment(Assignment assign) {
    throw new NotImplementedException();
  }
  public boolean isCategoryPrivByCategory(Category category) {
    throw new NotImplementedException();
  }
  public boolean isGradesPrivByAssignment(Assignment assign) {
    throw new NotImplementedException();
  }
  public boolean isGroupsPrivByAssignment(Assignment assign) {
    throw new NotImplementedException();
  }
  public boolean isStudentInCourseByAssignment(Assignment assign) {
    throw new NotImplementedException();
  }
  public boolean isStudentInCourseByGroup(Group group) {
    throw new NotImplementedException();
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
