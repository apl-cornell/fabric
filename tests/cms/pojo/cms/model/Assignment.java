package cms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class Assignment {

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

  private Course course;
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

  Collection/*SolutionFile*/       solutionFiles;        // maintained by SolutionFile
  Collection/*Grade*/              grades;               // maintained by Grade
  Collection/*SubProblem*/         subProblems;          // maintained by SubProblem
  Collection/*AssignmentItem*/     items;                // maintained by AssignmentItem
  Collection/*AnswerSet*/          answerSets;           // maintained by AnswerSet
  Collection/*TimeSlot*/           timeSlots;            // maintained by TimeSlot
  Collection/*RequiredSubmission*/ requiredSubmissions;  // maintained by RequiredSubmission

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  Assignment(Course course, String name, String nameShort, Date due) {
    setCourse(course);
    setName(name);
    setNameShort(nameShort);
    setDueDate(due);

    this.solutionFiles       = new ArrayList/*SolutionFile*/();
    this.grades              = new ArrayList/*Grade*/();
    this.subProblems         = new ArrayList/*SubProblem*/();
    this.items               = new ArrayList/*AssignmentItem*/();
    this.answerSets          = new ArrayList/*AnswerSet*/();
    this.timeSlots           = new ArrayList/*TimeSlot*/();
    this.requiredSubmissions = new ArrayList/*RequiredSubmission*/();

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

  public void setCourse(final Course course) {
    if (this.course != null)
      this.course.assignments.remove(this);

    this.course = course;

    if (this.course != null)
      this.course.assignments.add(this);
  }

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

  public Collection/*SolutionFile*/ getSolutionFiles() {
    return Collections.unmodifiableCollection(this.solutionFiles);
  }

  public Collection/*SubProblem*/ getSubProblems() {
    return Collections.unmodifiableCollection(this.subProblems);
  }

  //////////////////////////////////////////////////////////////////////////////
  // public methods                                                           //
  //////////////////////////////////////////////////////////////////////////////

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
    throw new NotImplementedException();
  }

  public Set/*RegradeRequest*/ findRegradeRequests() {
    throw new NotImplementedException();
  }

  public Set/*GroupGrades*/ findGroupGradesByGrader(User user, boolean adminPriv, int i) {
    throw new NotImplementedException();
  }

  public Set/*GroupAssignedTo*/ getGroupAssignedTos() {
    throw new NotImplementedException();
  }

  public Set/*GroupMember*/ findActiveGroupMembers() {
    throw new NotImplementedException();
  }

  public Set/*GroupMember*/ findActiveAssignedGroupMembers(User user) {
    throw new NotImplementedException();
  }

  public Set/*Group*/ findLateGroups() {
    throw new NotImplementedException();
  }

  public Group findGroup(User user) {
    throw new NotImplementedException();
  }

  public Collection/*GroupAssignedTo*/ findGroupAssignedTos(User user) {
    throw new NotImplementedException();
  }

  public Collection/*Grade*/ getGrades() {
    throw new NotImplementedException();
  }

  public Collection/*AnswerSet*/ getAnswerSets() {
    throw new NotImplementedException();
  }

  public boolean hasSolutionFile() {
    throw new NotImplementedException();
  }

  public SolutionFile findSolutionFile() {
    throw new NotImplementedException();
  }

  public Collection/*SolutionFile*/ findHiddenSolutionFiles() {
    throw new NotImplementedException();
  }

  public Collection/*AssignmentItem*/ getAssignmentItems() {
    throw new NotImplementedException();
  }

  public Collection/*AssignmentItems*/ findHiddenAssignmentItems() {
    throw new NotImplementedException();
  }

  public Collection/*GroupMember*/ findInvitations(User user) {
    throw new NotImplementedException();
  }

  public Collection/*TimeSlot*/ getTimeSlots() {
    throw new NotImplementedException();
  }

  public Collection/*Group*/ getGroups() {
    throw new NotImplementedException();
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
    throw new NotImplementedException();
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
    throw new NotImplementedException();
  }

  /**
   * check that the total score of all of the subproblems equals the total score
   */
  public void checkScores() {
    throw new NotImplementedException();
  }

  /**
   * import the groups from another assignment.
   * @param groupsFrom
   */
  public void importGroups(Assignment groupsFrom) {
    throw new NotImplementedException();
  }

  /**
   * Find the most recent grade for the student for the entire assignment (i.e.
   * not associated with a particular subproblem.
   */
  public Grade findMostRecentGrade(Student student) {
    throw new NotImplementedException();
  }

  public Grade findMostRecentGrade(Student student, SubProblem sp) {
    throw new NotImplementedException();
  }

  public void addGrade(Group group, SubProblem subProb, Float float1) {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
