package cms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class Course {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private String   code;          // the official code, e.g. "COM S 211"
  private String   displayedCode; // what the professor wants, e.g. "CS 211"
  private String   name;          // e.g. "Intro to Programming"
  private String   description;   // e.g. "In this course you will learn foo"
  private boolean  hasSection;
  private Semester semester;
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

  final Collection/*Assignment*/       assignments;
  final Collection/*Students*/         students;
  final Collection/*Staff*/            staff;
  final Collection/*Announcement*/     announcements;
  final Collection/*Category*/         categories;
  final Collection/*CategoryCol*/      ctgCols;
  final Collection/*CategoryRow*/      ctgRows;
  final Collection/*CategoryContents*/ contents;
  final Collection/*CategoryFile*/     ctgFiles;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setCode                (final String code)                 { this.code                = code;                }
  public void setDisplayedCode       (final String displayedCode)        { this.displayedCode       = displayedCode;       }
  public void setName                (final String name)                 { this.name                = name;                }
  public void setDescription         (final String description)          { this.description         = description;         }
  public void setHasSection          (final boolean hasSection)          { this.hasSection          = hasSection;          }
  public void setSemester            (final Semester semester)           { this.semester            = semester;            }
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

  Course(String name, String description, String code, Semester semester) {
    setName(name);
    setDescription(description);
    setCode(code);
    setSemester(semester);

    setFileCounter(1);

    assignments   = new ArrayList/*Assignment*/       ();
    students      = new ArrayList/*Students*/         ();
    staff         = new ArrayList/*Staff*/            ();
    announcements = new ArrayList/*Announcement*/     ();
    categories    = new ArrayList/*Category*/         ();
    ctgCols       = new ArrayList/*CategoryCol*/      ();
    ctgRows       = new ArrayList/*CategoryRow*/      ();
    contents      = new ArrayList/*CategoryContents*/ ();
    ctgFiles      = new ArrayList/*CategoryFile*/     ();

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
  }
  public Collection/*Assignment*/ getAssignments() {
    return Collections.unmodifiableCollection(assignments);
  }
  public Collection getAllAssignmentFiles() {
    throw new NotImplementedException();
  }
  public Collection getEmails() {
    throw new NotImplementedException();
  }
  public Collection getStudents() {
    throw new NotImplementedException();
  }
  public Collection getRegradeRequests() {
    throw new NotImplementedException();
  }
  
  public Collection/*Grade*/ getTotals() {
    // See gradeHome().findTotalsByCourseID
    throw new NotImplementedException();
  }
  
  public Collection/*Announcement*/ getAnnouncements() {
    throw new NotImplementedException();
  }
  
  public Collection/*Announcement*/ findHiddenAnnouncements() {
    throw new NotImplementedException();
  }
  
  public Collection/*Staff*/ getStaff() {
    throw new NotImplementedException();
  }
  
  public Collection/*Student*/ findActiveStudents() {
    throw new NotImplementedException();
  }
  
  public Collection/*Group*/ findGroupsByUser(User user) {
    throw new NotImplementedException();
  }
  
  public Collection/*GroupMember*/ findGroupMembersByUser(User user) {
    throw new NotImplementedException();
  }
  
  public Collection/*Grade*/ findRecentGradesByUser(User user, boolean b, User p) {
    // see gradeHome().findRecentByNetIDCourseID
    return null;
  }
  
  public Collection/*RequiredSubmission*/ getRequiredSubmissions() {
    throw new NotImplementedException();
  }
  
  public Map/*SubProblem, Float*/ getGradeMap(User user) {
    throw new NotImplementedException();
  }
  public Student getStudent(User user) {
    throw new NotImplementedException();
  }
  public Collection/*Assignment*/ findHiddenAssignments() {
    throw new NotImplementedException();
  }
  
  public Collection/*Category*/ getCategories() {
    throw new NotImplementedException();
  }
  public Staff getStaff(User user) {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
