package cms.model;

import fabric.util.*;
import java.util.Date;

import org.apache.commons.fileupload.FileUploadException;

public class Assignment implements Comparable {
  private static int nextID = 1;

  public static final String OPEN   = "Open";
  public static final String CLOSED = "Closed";
  public static final String HIDDEN = "Hidden";
  public static final String GRADED = "Graded";
  
  public static final int ASSIGNMENT = 0;
  public static final int SURVEY     = 1;
  public static final int QUIZ       = 2;

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  final Course course;
  private final int id;
  private String name, nameShort;
  private String description;
  private String status;

  private float   weight, maxScore;
  private boolean studentRegrades;
  private int     gracePeriod; //in minutes
  private int     numassignedfiles;
  private Date    dueDate, // does NOT include grace period
                  regradeDeadline, lateDeadline;
  private boolean showStats, showSolution, assignedGraders,
                  allowLate, assignedGroups, hidden;
  private float   mean, max, median, stdDev;
  private int     groupSizeMax, groupSizeMin;
  private int     type;
  
  //timeslot info
  private boolean scheduled;        // enable/disable scheduling
  private Integer groupLimit;
  private Long    duration;
  private Date    timeslotLockTime; // time after which *students* can't change assigned timeslots (null = no deadline)

  //////////////////////////////////////////////////////////////////////////////
  // managed views                                                            //
  //////////////////////////////////////////////////////////////////////////////

  SolutionFile                           solutionFile;         // maintained by SolutionFile
  final Map/*Student, Grade*/            grades;               // maintained by Grade
  final Map/*Student, Set<Grade>*/       subProblemGrades;     // maintained by Grade
  final Collection/*SubProblem*/         subProblems;          // maintained by SubProblem
  final Collection/*AssignmentItem*/     items;                // maintained by AssignmentItem
  final Collection/*AnswerSet*/          answerSets;           // maintained by AnswerSet
  final Collection/*TimeSlot*/           timeSlots;            // maintained by TimeSlot
  final Collection/*RequiredSubmission*/ requiredSubmissions;  // maintained by RequiredSubmission
  final Collection/*Group*/              groups;               // maintained by Group
  final Map/*Group, Set<RegradeRequest>*/ regradeRequests;     // Maintained by RegradeRequest
  
  /**
   * Maps students to the GroupMember for which they are active (if any).
   */
  final Map/*User, GroupMember*/ groupMemberIndex; // Maintained by GroupMember.

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Assignment(Course course, String name, String nameShort, Date due) {
    this.course = course;
    this.id = nextID++;
    setName(name);
    setNameShort(nameShort);
    setDueDate(due);
    setHidden(false);
    
    this.solutionFile        = null;
    this.grades              = new HashMap/*Grade*/();
    this.subProblems         = new LinkedList/*SubProblem*/();
    this.subProblemGrades    = new HashMap()/*Student, Set<Grade>*/;
    this.items               = new ArrayList/*AssignmentItem*/();
    this.answerSets          = new ArrayList/*AnswerSet*/();
    this.timeSlots           = new ArrayList/*TimeSlot*/();
    this.requiredSubmissions = new ArrayList/*RequiredSubmission*/();
    this.groups              = new LinkedList/*Group*/();
    this.regradeRequests = new HashMap();
    this.groupMemberIndex = new HashMap();
    
    course.assignments.put(toString(), this);
    course.semester.database.assignments.put(toString(), this);

    // TODO: set up other fields
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setName             (final String name)              { this.name             = name;             }
  public void setNameShort        (final String nameShort)         { this.nameShort        = nameShort;        }
  public void setDescription      (final String description)       { this.description      = description;      }
  public void setStatus           (final String status)            { this.status           = status;           }
  public void setWeight           (final float weight)             { this.weight           = weight;           }
  public void setMaxScore         (final float maxScore)           { this.maxScore         = maxScore;         }
  public void setStudentRegrades  (final boolean studentRegrades)  { this.studentRegrades  = studentRegrades;  }
  public void setGracePeriod      (final int gracePeriod)          { this.gracePeriod      = gracePeriod;      }
  public void setNumassignedfiles (final int numassignedfiles)     { this.numassignedfiles = numassignedfiles; }
  public void setDueDate          (final Date dueDate)             { this.dueDate          = dueDate;          }
  public void setRegradeDeadline  (final Date regradeDeadline)     { this.regradeDeadline  = regradeDeadline;  }
  public void setLateDeadline     (final Date lateDeadline)        { this.lateDeadline     = lateDeadline;     }
  public void setShowStats        (final boolean showStats)        { this.showStats        = showStats;        }
  public void setShowSolution     (final boolean showSolution)     { this.showSolution     = showSolution;     }
  public void setAssignedGraders  (final boolean assignedGraders)  { this.assignedGraders  = assignedGraders;  }
  public void setAllowLate        (final boolean allowLate)        { this.allowLate        = allowLate;        }
  public void setAssignedGroups   (final boolean assignedGroups)   { this.assignedGroups   = assignedGroups;   }
  public void setHidden           (final boolean hidden)           { this.hidden           = hidden;           }
  public void setMean             (final float mean)               { this.mean             = mean;             }
  public void setMax              (final float max)                { this.max              = max;              }
  public void setMedian           (final float median)             { this.median           = median;           }
  public void setStdDev           (final float stdDev)             { this.stdDev           = stdDev;           }
  public void setGroupSizeMax     (final int groupSizeMax)         { this.groupSizeMax     = groupSizeMax;     }
  public void setGroupSizeMin     (final int groupSizeMin)         { this.groupSizeMin     = groupSizeMin;     }
  public void setType             (final int type)                 { this.type             = type;             }
  public void setScheduled        (final boolean scheduled)        { this.scheduled        = scheduled;        }
  public void setGroupLimit       (final Integer groupLimit)       { this.groupLimit       = groupLimit;       }
  public void setDuration         (final Long duration)            { this.duration         = duration;         }
  public void setTimeslotLockTime (final Date timeslotLockTime)    { this.timeslotLockTime = timeslotLockTime; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Course  getCourse()           { return this.course;           }
  public String  getName()             { return this.name;             }
  public String  getNameShort()        { return this.nameShort;        }
  public String  getDescription()      { return this.description;      }
  public String  getStatus()           { return this.status;           }
  public float   getWeight()           { return this.weight;           }
  public float   getMaxScore()         { return this.maxScore;         }
  public boolean getStudentRegrades()  { return this.studentRegrades;  }
  public int     getGracePeriod()      { return this.gracePeriod;      }
  public int     getNumassignedfiles() { return this.numassignedfiles; }
  public Date    getDueDate()          { return this.dueDate;          }
  public Date    getRegradeDeadline()  { return this.regradeDeadline;  }
  public Date    getLateDeadline()     { return this.lateDeadline;     }
  public boolean getShowStats()        { return this.showStats;        }
  public boolean getShowSolution()     { return this.showSolution;     }
  public boolean getAssignedGraders()  { return this.assignedGraders;  }
  public boolean getAllowLate()        { return this.allowLate;        }
  public boolean getAssignedGroups()   { return this.assignedGroups;   }
  public boolean getHidden()           { return this.hidden;           }
  public float   getMean()             { return this.mean;             }
  public float   getMax()              { return this.max;              }
  public float   getMedian()           { return this.median;           }
  public float   getStdDev()           { return this.stdDev;           }
  public int     getGroupSizeMax()     { return this.groupSizeMax;     }
  public int     getGroupSizeMin()     { return this.groupSizeMin;     }
  public int     getType()             { return this.type;             }
  public boolean getScheduled()        { return this.scheduled;        }
  public Integer getGroupLimit()       { return this.groupLimit;       }
  public Long    getDuration()         { return this.duration;         }
  public Date    getTimeslotLockTime() { return this.timeslotLockTime; }

  public Collection/*RequiredSubmission*/ getRequiredSubmissions() {
    return Collections.unmodifiableCollection(this.requiredSubmissions);
  }

  public SolutionFile getSolutionFile() {
    return solutionFile;
  }

  public Collection/*SubProblem*/ getSubProblems() {
    return Collections.unmodifiableCollection(this.subProblems);
  }

  //////////////////////////////////////////////////////////////////////////////
  // public methods                                                           //
  //////////////////////////////////////////////////////////////////////////////

  void addSolutionFile(SolutionFile sf) throws FileUploadException {
    if (solutionFile != null)
      throw new FileUploadException(
          "Error: Conflicting solution files<br>");
    
    this.solutionFile = sf;
  }
  
  public Collection/*RequiredSubmission*/ getHiddenRequiredSubmissions() {
    Collection result = new ArrayList();
    Iterator   i = requiredSubmissions.iterator();
    while (i.hasNext()) {
      RequiredSubmission next = (RequiredSubmission) i.next();
      if (next.isHidden())
        result.add(next);
    }
    return result;
  }
  
  public void addRequiredSubmission(RequiredSubmission submission) {
    requiredSubmissions.add(submission);
  }

  public boolean hasSubProblems() {
    return !subProblems.isEmpty();
  }

  public Collection/*SubProblem*/ getHiddenSubProblems() {
    Collection result = new ArrayList();
    Iterator   i = subProblems.iterator();
    while (i.hasNext()) {
      SubProblem next = (SubProblem) i.next();
      if (next.isHidden())
        result.add(next);
    }
    return result;
  }

  /**
   * Find the subcollection of users who are active in a group for this
   * assignment with at least one other active member.
   * @param netids a Collection containing <code>User</code>s to return
   * @return a subcollection of netids (Collection of User)
   */
  public Collection/*User*/ getNonSoloGroupMembers(Collection/*User*/ netids) {
    throw new NotImplementedException();
  }

  /**
   * Find the subcollection of users who have a grade for this assignment.
   * @param netids A collection of <code>User</code>s to return
   * @return the subcollection of netids (Collection of User)
   */
  public Collection/*User*/ getGradedStudents(Collection/*User*/ netids) {
    throw new NotImplementedException();
  }

  /**
   * Find the subcollection of groups that have a grade for this assignment
   * @param groups A collection of <code>Group</code>s to return
   * @param the subcollection of groups (Collection of Group)
   */
  public Collection/*Group*/ getGradedGroups(Collection/*Group*/ groups) {
    Collection result = new ArrayList();
    for (Iterator i = groups.iterator(); i.hasNext();) {
      Group g = (Group)i.next();
      if (g.getGrade() != null)
        result.add(g);
    }
    return result;
  }

  public Set/*RegradeRequest*/ findRegradeRequests() {
    Set result = new HashSet();
    for (Iterator i = regradeRequests.values().iterator(); i.hasNext();) {
      Set r = (Set)i.next();
      result.addAll(r);
    }
    return result;
  }
  
  public Set/*RegradeRequest*/ findRegradeRequests(Group group) {
    Set result = (Set) regradeRequests.get(group);
    if (result == null) return Collections.EMPTY_SET;
    return result;
  }

  public Set/*GroupGrades*/ findGroupGradesByGrader(User user, boolean adminPriv, int i) {
    throw new NotImplementedException();
  }

  public Set/*GroupAssignedTo*/ getGroupAssignedTos() {
    return new TreeSet(); // XXX
  }

  /**
   * Returns a set of group members that correspond to students who are enrolled
   * in the course and active in the respective group.
   */
  public Set/*GroupMember*/ findActiveGroupMembers() {
    Set result = new HashSet();
    for (Iterator git = groups.iterator(); git.hasNext();) {
      Group group = (Group) git.next();
      for (Iterator mit = group.members.values().iterator(); mit.hasNext();) {
        GroupMember member = (GroupMember) mit.next();
        if (member.getStatus().equals(GroupMember.ACTIVE)) {
          Student student = member.getStudent();
          if (student.getStatus().equals(Student.ENROLLED)) result.add(member);
        }
      }
    }
    
    return result;
  }

  public Set/*GroupMember*/ findActiveAssignedGroupMembers(User user) {
    throw new NotImplementedException();
  }

  public Set/*Group*/ findLateGroups() {
    Set result = new HashSet();
    for (Iterator i = groups.iterator(); i.hasNext();) {
      Group g = (Group)i.next();
      boolean cont = true;
      for (Iterator fi = g.getSubmittedFiles().iterator(); fi.hasNext() && cont;) {
        SubmittedFile f = (SubmittedFile)fi.next();
        if (f.getLateSubmission()) {
          result.add(g);
          cont = false;
        }
      }
    }
    return result;
  }
  
  public Grade findGrade(Student student) {
    return (Grade) grades.get(student);
  }

  public Group findGroup(User user) {
    GroupMember member = ((GroupMember) groupMemberIndex.get(user));
    if (member == null) return null;
    return member.getGroup();
  }

  public Collection/*GroupAssignedTo*/ findGroupAssignedTos(User user) {
    throw new NotImplementedException();
  }

  public Collection/*Grade*/ getGrades() {
    Collection allGrades = new LinkedList();
    allGrades.addAll(grades.values());
    for (Iterator i = subProblemGrades.values().iterator(); i.hasNext();) {
      allGrades.addAll((Collection)i.next());
    }
    return Collections.unmodifiableCollection(allGrades);
  }

  public Collection/*AnswerSet*/ getAnswerSets() {
    return Collections.unmodifiableCollection(answerSets);
  }

  public boolean hasSolutionFile() {
    return solutionFile != null;
  }

  public SolutionFile findSolutionFile() {
    return solutionFile;
  }

  public Collection/*SolutionFile*/ findHiddenSolutionFiles() {
    ArrayList c = new ArrayList();
    if (solutionFile != null) c.add(solutionFile);
    return c;
  }

  public Collection/*AssignmentItem*/ getAssignmentItems() {
    return Collections.unmodifiableCollection(items);
  }

  public Collection/*AssignmentItems*/ findHiddenAssignmentItems() {
    return Collections.unmodifiableCollection(items);
  }

  public Collection/*GroupMember*/ findInvitations(User user) {
    SortedSet result = new TreeSet();
    for (Iterator git = groups.iterator(); git.hasNext();) {
      Group group = (Group) git.next();
      for (Iterator mit = group.members.values().iterator(); mit.hasNext();) {
        GroupMember member = (GroupMember) mit.next();
        if (member.getStudent().getUser().getNetID() == user.getNetID() &&
            member.getStatus().equalsIgnoreCase("invited"))
          result.add(member);
      }
    }
    return result;
  }

  public Collection/*TimeSlot*/ getTimeSlots() {
    return Collections.unmodifiableCollection(timeSlots);
  }

  public Collection/*Group*/ getGroups() {
    return Collections.unmodifiableCollection(groups);
  }

  public Collection/*TimeSlot*/ findConflictingTimeSlots() {
    throw new NotImplementedException();
  }

  /**
   * return true if the user has already had some grades entered for this assignment.
   * @param user
   * @return
   */
  public boolean hasGrades(User user) {
    return grades.containsKey(course.getStudent(user));
  }
  
  public AnswerSet findMostRecentAnswerSet(Group group) {
    throw new NotImplementedException();
  }

  /**
   * Return the (unique) GroupMember with ACTIVE status associated with this
   * user and this assignment, or null if none exists.
   */
  public GroupMember findActiveGroupMember(User p) {
    throw new NotImplementedException();
  }

  public void removeCurrentSolutionFile() {
    solutionFile = null;
  }

  /**
   * check that the total score of all of the subproblems equals the total score
   */
  public void checkScores() {
    throw new NotImplementedException();
  }

  /**
   * Find the most recent grade for the student for the entire assignment (i.e.
   * not associated with a particular subproblem.
   */
  public Grade findMostRecentGrade(Student student) {
    if (grades.containsKey(student)) 
      return (Grade)grades.get(student);
    return null;
  }

  public Grade findMostRecentGrade(Student student, SubProblem sp) {
    Set grades = (Set)subProblemGrades.get(student);
    if (grades == null) return null;
    for (Iterator i = grades.iterator(); i.hasNext();) {
      Grade g = (Grade)i.next();
      if (g.getSubProblem().toString().equals(sp.toString()))
        return g;
    }
    return null;
  }

  public void resetGradesForStudent(User studentUser) {
    Student student = course.getStudent(studentUser);
    grades.remove(student);
    subProblemGrades.remove(student);
  }
  
  /**
   * Adds a grade for a student for the assignment. If it is a subproblem,
   * it will update to the total grade for the assignment. 
   * @param group
   * @param subProb
   * @param grade
   * @param studentUser
   * @param grader
   */
  public void addGrade(Group group, SubProblem subProb, Float grade, User studentUser, User grader) {
    Student student = course.getStudent(studentUser);
    Grade g = new Grade(this, subProb, grade, studentUser, grader);
    if (subProb == null) {
      grades.put(student, g);
    } else {
      Set grades = (Set)subProblemGrades.get(student);
      if (grades == null) grades = new HashSet();
      grades.add(g);
      subProblemGrades.put(student, grades);
      Grade gr = (Grade)this.grades.get(student);
    }
  }

  public int compareTo(java.lang.Object o) {
    if (!(o instanceof Assignment)) return 0;
    
    Assignment a = (Assignment) o;
    int result = course.compareTo(a.course);
    if (result != 0) return result;
    
    result = dueDate.compareTo(a.dueDate);
    if (result != 0) return result;
    return name.compareTo(a.name);
  }

  /**
   * Creates groups for all students in the course.
   */
  public void createGroups() {
    for (Iterator it = course.students.values().iterator(); it.hasNext();) {
      Student student = (Student) it.next();
      Group group = new Group(this, numassignedfiles);
      new GroupMember(group, student, GroupMember.ACTIVE);
    }
  }

  /**
   * Imports groups from another assignment.
   */
  public void importGroups(Assignment groupsFrom) {
    Set activeMembers = groupsFrom.findActiveGroupMembers();
    
    // Maps (groups from the other assignment) to (groups in this assignment).
    Map groupMap = new HashMap();
    for (Iterator it = activeMembers.iterator(); it.hasNext();) {
      GroupMember oldMember = (GroupMember) it.next();
      Group oldGroup = oldMember.getGroup();
      Group newGroup = (Group) groupMap.get(oldGroup);
      if (newGroup == null) {
        newGroup = new Group(this, numassignedfiles);
        groupMap.put(oldGroup, newGroup);
      }
      
      new GroupMember(newGroup, oldMember.getStudent(), GroupMember.ACTIVE);
    }
  }
  
  public String toString() {
    return "" + id;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
