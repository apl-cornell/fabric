package cms.model;

import java.util.*;
import java.util.Map.Entry;

public class Course implements Comparable {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private String   code;          // the official code, e.g. "COM S 211"
  private String   displayedCode; // what the professor wants, e.g. "CS 211"
  private String   name;          // e.g. "Intro to Programming"
  private String   description;   // e.g. "In this course you will learn foo"
  private boolean  hasSection;
  final Semester semester;
  private boolean  showFinalGrade;
  private boolean  showTotalScores;
  private boolean  showAssignWeights;
  private boolean  showGraderNetID;
  private boolean  freezeCourse;
  private long     fileCounter;
  private boolean  courseGuestAccess;
  private boolean  assignGuestAccess;
  private boolean  announceGuestAccess;
  private boolean  solutionGuestAccess;
  private boolean  courseCCAccess;
  private boolean  assignCCAccess;
  private boolean  announceCCAccess;
  private boolean  solutionCCAccess;
  private Float    maxTotalScore;
  private Float    highTotalScore;
  private Float    meanTotalScore;
  private Float    medianTotalScore;
  private Float    stDevTotalScore;

  //////////////////////////////////////////////////////////////////////////////
  // managed views                                                            //
  //////////////////////////////////////////////////////////////////////////////

  public final Map/* String, Assignment */assignments; // assignmentID -> Assignment
  final Map/*User,Student*/            students;
  final Map/*User,Staff*/              staff;
  final Collection/*Announcement*/     announcements;
  final Collection/*Category*/         categories;
  final Collection/*CategoryCol*/      ctgCols;
  final Collection/*CategoryRow*/      ctgRows;
  final Collection/*CategoryContents*/ contents;
  final Collection/*CategoryFile*/     ctgFiles;
  final SortedSet/*Email*/             emails;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setCode                (final String code)                 { this.code                = code;                }
  public void setDisplayedCode       (final String displayedCode)        { this.displayedCode       = displayedCode;       }
  public void setName                (final String name)                 { this.name                = name;                }
  public void setDescription         (final String description)          { this.description         = description;         }
  public void setHasSection          (final boolean hasSection)          { this.hasSection          = hasSection;          }
  public void setShowFinalGrade      (final boolean showFinalGrade)      { this.showFinalGrade      = showFinalGrade;      }
  public void setShowTotalScores     (final boolean showTotalScores)     { this.showTotalScores     = showTotalScores;     }
  public void setShowAssignWeights   (final boolean showAssignWeights)   { this.showAssignWeights   = showAssignWeights;   }
  public void setShowGraderNetID     (final boolean showGraderNetID)     { this.showGraderNetID     = showGraderNetID;     }
  public void setFreezeCourse        (final boolean freezeCourse)        { this.freezeCourse        = freezeCourse;        }
  public void setFileCounter         (final long fileCounter)            { this.fileCounter         = fileCounter;         }
  public void setCourseGuestAccess   (final boolean courseGuestAccess)   { this.courseGuestAccess   = courseGuestAccess;   }
  public void setAssignGuestAccess   (final boolean assignGuestAccess)   { this.assignGuestAccess   = assignGuestAccess;   }
  public void setAnnounceGuestAccess (final boolean announceGuestAccess) { this.announceGuestAccess = announceGuestAccess; }
  public void setSolutionGuestAccess (final boolean solutionGuestAccess) { this.solutionGuestAccess = solutionGuestAccess; }
  public void setCourseCCAccess      (final boolean courseCCAccess)      { this.courseCCAccess      = courseCCAccess;      }
  public void setAssignCCAccess      (final boolean assignCCAccess)      { this.assignCCAccess      = assignCCAccess;      }
  public void setAnnounceCCAccess    (final boolean announceCCAccess)    { this.announceCCAccess    = announceCCAccess;    }
  public void setSolutionCCAccess    (final boolean solutionCCAccess)    { this.solutionCCAccess    = solutionCCAccess;    }
  public void setMaxTotalScore       (final Float maxTotalScore)         { this.maxTotalScore       = maxTotalScore;       }
  public void setHighTotalScore      (final Float highTotalScore)        { this.highTotalScore      = highTotalScore;      }
  public void setMeanTotalScore      (final Float meanTotalScore)        { this.meanTotalScore      = meanTotalScore;      }
  public void setMedianTotalScore    (final Float medianTotalScore)      { this.medianTotalScore    = medianTotalScore;    }
  public void setStDevTotalScore     (final Float stDevTotalScore)       { this.stDevTotalScore     = stDevTotalScore;     }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public String   getCode()                { return this.code;                }
  public String   getDisplayedCode()       { return this.displayedCode;       }
  public String   getName()                { return this.name;                }
  public String   getDescription()         { return this.description;         }
  public boolean  getHasSection()          { return this.hasSection;          }
  public Semester getSemester()            { return this.semester;            }
  public boolean  getShowFinalGrade()      { return this.showFinalGrade;      }
  public boolean  getShowTotalScores()     { return this.showTotalScores;     }
  public boolean  getShowAssignWeights()   { return this.showAssignWeights;   }
  public boolean  getShowGraderNetID()     { return this.showGraderNetID;     }
  public boolean  getFreezeCourse()        { return this.freezeCourse;        }
  public long     getFileCounter()         { return this.fileCounter;         }
  public boolean  getCourseGuestAccess()   { return this.courseGuestAccess;   }
  public boolean  getAssignGuestAccess()   { return this.assignGuestAccess;   }
  public boolean  getAnnounceGuestAccess() { return this.announceGuestAccess; }
  public boolean  getSolutionGuestAccess() { return this.solutionGuestAccess; }
  public boolean  getCourseCCAccess()      { return this.courseCCAccess;      }
  public boolean  getAssignCCAccess()      { return this.assignCCAccess;      }
  public boolean  getAnnounceCCAccess()    { return this.announceCCAccess;    }
  public boolean  getSolutionCCAccess()    { return this.solutionCCAccess;    }
  public Float    getMaxTotalScore()       { return this.maxTotalScore;       }
  public Float    getHighTotalScore()      { return this.highTotalScore;      }
  public Float    getMeanTotalScore()      { return this.meanTotalScore;      }
  public Float    getMedianTotalScore()    { return this.medianTotalScore;    }
  public Float    getStDevTotalScore()     { return this.stDevTotalScore;     }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Course(CMSRoot db, Semester semester, String name, String description,
      String code) {
    setName(name);
    setDescription(description);
    setCode(code);
    setDisplayedCode(code);
    
    this.semester = semester;
    this.semester.courses.add(this);

    setFileCounter(1);

    assignments   = new HashMap/*String, Assignment*/       ();
    students      = new HashMap/*User,Student*/       ();
    staff         = new HashMap/*User,Staff*/         ();
    announcements = new ArrayList/*Announcement*/     ();
    categories    = new ArrayList/*Category*/         ();
    ctgCols       = new ArrayList/*CategoryCol*/      ();
    ctgRows       = new ArrayList/*CategoryRow*/      ();
    contents      = new ArrayList/*CategoryContents*/ ();
    ctgFiles      = new ArrayList/*CategoryFile*/     ();
    emails = new TreeSet();

    // TODO: this is probably unnecessary, since these are the java defaults
    setAnnounceGuestAccess(false);
    setAssignGuestAccess(false);
    setCourseGuestAccess(false);
    setSolutionGuestAccess(false);
    setFreezeCourse(false);
    setShowFinalGrade(false);
    setShowTotalScores(false);
    setShowAssignWeights(false);
    setShowGraderNetID(false);
    setCourseCCAccess(false);
    setAssignCCAccess(false);
    setAnnounceCCAccess(false);
    setSolutionCCAccess(false);
    setMaxTotalScore(null);
    setHasSection(false);
    setHighTotalScore(null);
    setMeanTotalScore(null);
    setMedianTotalScore(null);
    setStDevTotalScore(null);
    
    db.courses.put(toString(), this);
  }
  
  /**
   * Returns the set of unhidden assignments.
   */
  public Collection getAssignments() {
    SortedSet result = new TreeSet();
    for (Iterator it = assignments.values().iterator(); it.hasNext();) {
      Assignment assignment = (Assignment) it.next();
      if (!assignment.getHidden())
        result.add(assignment);
    }
    
    return result;
  }
  
  public Assignment getAssignment(String assignmentID) {
    return (Assignment) assignments.get(assignmentID);
  }
  
  public Collection getAllAssignmentFiles() {
    return new ArrayList();
  }
  public Collection/*Email*/ getEmails() {
    return Collections.unmodifiableCollection(emails);
  }
  public Collection/*Student*/ getStudents() {
    return Collections.unmodifiableCollection(students.values());
  }
  public Collection getRegradeRequests() {
    throw new NotImplementedException();
  }
  
  public Collection/*Grade*/ getTotals() {
    // See gradeHome().findTotalsByCourseID
    throw new NotImplementedException();
  }
  
  public Collection/*Announcement*/ getAnnouncements() {
    SortedSet result = new TreeSet();
    
    for (Iterator it = announcements.iterator(); it.hasNext();) {
      Announcement announcement = (Announcement) it.next();
      if (!announcement.getHidden()) result.add(announcement);
    }
    
    return result;
  }
  
  public Collection/*Announcement*/ findHiddenAnnouncements() {
    SortedSet result = new TreeSet();
    
    for (Iterator it = announcements.iterator(); it.hasNext();) {
      Announcement announcement = (Announcement) it.next();
      if (announcement.getHidden()) result.add(announcement);
    }
    
    return result;
  }
  
  public Collection/*Staff*/ getStaff() {
    SortedSet result = new TreeSet();
    for (Iterator it = staff.values().iterator(); it.hasNext();) {
      Staff staff = (Staff) it.next();
      if (staff.getStatus().equals(Staff.ACTIVE)) result.add(staff);
    }
    return result;
  }
  
  public Collection/*Student*/ findActiveStudents() {
    SortedSet result = new TreeSet(Student.LAST_NAME_COMPARATOR);
    
    for (Iterator it = students.values().iterator(); it.hasNext();) {
      Student student = (Student) it.next();
      if (student.getStatus().equals(Student.ENROLLED)) result.add(student);
    }
    
    return result;
  }
  
  public Collection/*Group*/ findGroupsByUser(User user) {
    Collection/*Group*/ result = new ArrayList();
    for (Iterator ai = assignments.values().iterator(); ai.hasNext();) {
      Assignment a = (Assignment)ai.next();
      for (Iterator gi = a.getGroups().iterator(); gi.hasNext();) {
        Group g = (Group)gi.next();
        if (g.hasMember(user)) {
          result.add(g);
        }
      }
    }
    return result;
  }
  
  public Collection/*GroupMember*/ findGroupMembersByUser(User user) {
    Collection/*GroupMember*/ result = new ArrayList();
    for (Iterator ai = assignments.values().iterator(); ai.hasNext();) {
      Assignment a = (Assignment)ai.next();
      for (Iterator gi = a.getGroups().iterator(); gi.hasNext();) {
        Group g = (Group)gi.next();
        if (g.hasMember(user)) {
          result.add(g.getMember(user));
        }
      }
    }
    return result;
  }
  
  public Collection/*Grade*/ findRecentGradesByUser(User user, boolean admin, User grader) {
    // XXX This method could probably be done more efficiently by creating appropriate indices.
    
    Map/*SubProblem, Grade*/ result = new HashMap/*SubProblem, Grade*/();
    
    for (Iterator ait = assignments.values().iterator(); ait.hasNext();) {
      Assignment assignment = (Assignment) ait.next();
      
      if (assignment.getHidden()) continue;
      boolean assignedGraders = assignment.getAssignedGraders();
      
      // Find the user's group for the assignment.
      Group group = null;
      for (Iterator git = assignment.groups.iterator(); git.hasNext();) {
        Group curGroup = (Group) git.next();
        GroupMember member = (GroupMember) curGroup.members.get(user);
        if (member != null && member.getStatus().equals(GroupMember.ACTIVE)) {
          group = curGroup;
          break;
        }
      }
      
      // Build up a set of partners for the assignment.
      Set/*User*/ partners = new HashSet();
      for (Iterator eit = group.members.entrySet().iterator(); eit.hasNext();) {
        Map.Entry entry = (Entry) eit.next();
        if (((GroupMember) entry.getValue()).getStatus().equals(GroupMember.ACTIVE)) {
          partners.add(entry.getKey());
        }
      }

      for (Iterator git = assignment.grades.values().iterator(); git.hasNext();) {
        Grade grade = (Grade) git.next();
        if (grade.getGrade() == null) continue;
        if (!partners.contains(grade.getUser())) continue;
        
        SubProblem subProblem = grade.getSubProblem();
        if (assignedGraders && !admin
            && !GroupAssignedTo.isAssignedTo(subProblem, group, user))
          continue;
        
        Grade oldGrade = (Grade) result.get(subProblem);
        if (oldGrade == null || oldGrade.getEntered().before(grade.getEntered()))
          result.put(subProblem, grade);
      }
    }
    
    return result.values();
  }
  
  public Collection/*RequiredSubmission*/ getRequiredSubmissions() {
    Collection result = new ArrayList();
    for (Iterator i = assignments.values().iterator(); i.hasNext();) {
      Assignment a = (Assignment)i.next();
      result.addAll(a.requiredSubmissions);
    }
    return result;
  }
  
  public Map/*Assignment, Float*/ getGradeMap(User user) {
    // XXX This method could probably be done more efficiently by creating appropriate indices.
    Map result = new HashMap();
    
    for (Iterator ait = assignments.values().iterator(); ait.hasNext();) {
      Assignment assignment = (Assignment) ait.next();
      
      if (assignment.getHidden()) continue;
      
      for (Iterator git = assignment.grades.values().iterator(); git.hasNext();) {
        Grade grade = (Grade) git.next();
        if (grade.getUser() != user || grade.getGrade() == null
            || grade.getSubProblem() != null) continue;
        
        Grade oldGrade = (Grade) result.get(assignment);
        if (oldGrade == null || oldGrade.getEntered().before(grade.getEntered()))
          result.put(assignment, grade);
      }
    }
    
    // Extract actual grades.
    for (Iterator it = result.entrySet().iterator(); it.hasNext();) {
      Entry entry = (Entry) it.next();
      entry.setValue(((Grade) entry.getValue()).getGrade());
    }
    
    return result;
  }
  public Student getStudent(User user) {
    if (!students.containsKey(user))
      return null;
    return (Student) students.get(user);
  }
  public Collection/*Assignment*/ findHiddenAssignments() {
    Set result = new HashSet();
    for (Iterator it = assignments.values().iterator(); it.hasNext();) {
      Assignment assignment = (Assignment) it.next();
      if (assignment.getHidden()) result.add(assignment);
    }
    return result;
  }
  
  public Collection/*Category*/ getCategories(User user) {
    int authLevel = user.getAuthoriznLevelByCourse(this);
    SortedSet result = new TreeSet();
    
    for (Iterator it = categories.iterator(); it.hasNext();) {
      Category category = (Category) it.next();
      if (!category.getHidden() && category.getAuthLevel() >= authLevel)
        result.add(category);
    }
    
    return result;
  }
  
  public Staff getStaff(User user) {
    return (Staff) staff.get(user);
  }
  
  public int compareTo(Object o) {
    if (!(o instanceof Course)) return 0;
    
    return getCode().compareTo(((Course) o).getCode());
  }
  
  public String toString() {
    return semester.getName() + "|" + code;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
