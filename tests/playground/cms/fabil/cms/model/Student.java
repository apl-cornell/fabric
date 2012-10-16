package cms.model;

import fabric.util.Comparator;

public class Student {

  public static final String ENROLLED = "Enrolled", DROPPED = "Dropped";
  
  public static Comparator LAST_NAME_COMPARATOR;
  
  public static Comparator NETID_COMPARATOR;

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private User    user;
  private Course  course;
  private String  finalGrade;
  private String  status;
  private Float   totalScore;
  private String  lecture;
  private String  lab;
  private String  section;
  private String  credits;
  private String  gradeOption;
  private boolean emailNewAssignment;
  private boolean emailDueDate;
  private boolean emailGroup;
  private boolean emailNewGrade;
  private boolean emailRegrade;
  private boolean emailFile;
  private boolean emailFinalGrade;
  private boolean emailTimeSlot;
  private String  department;
  private String  courseNum;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setUser               (final User user)                  { this.user               = user;               }
  public void setCourse             (final Course course)              { this.course             = course;             }
  public void setFinalGrade         (final String finalGrade)          { this.finalGrade         = finalGrade;         }
  public void setStatus             (final String status)              { this.status             = status;             }
  public void setTotalScore         (final Float totalScore)           { this.totalScore         = totalScore;         }
  public void setLecture            (final String lecture)             { this.lecture            = lecture;            }
  public void setLab                (final String lab)                 { this.lab                = lab;                }
  public void setSection            (final String section)             { this.section            = section;            }
  public void setCredits            (final String credits)             { this.credits            = credits;            }
  public void setGradeOption        (final String gradeOption)         { this.gradeOption        = gradeOption;        }
  public void setEmailNewAssignment (final boolean emailNewAssignment) { this.emailNewAssignment = emailNewAssignment; }
  public void setEmailDueDate       (final boolean emailDueDate)       { this.emailDueDate       = emailDueDate;       }
  public void setEmailGroup         (final boolean emailGroup)         { this.emailGroup         = emailGroup;         }
  public void setEmailNewGrade      (final boolean emailNewGrade)      { this.emailNewGrade      = emailNewGrade;      }
  public void setEmailRegrade       (final boolean emailRegrade)       { this.emailRegrade       = emailRegrade;       }
  public void setEmailFile          (final boolean emailFile)          { this.emailFile          = emailFile;          }
  public void setEmailFinalGrade    (final boolean emailFinalGrade)    { this.emailFinalGrade    = emailFinalGrade;    }
  public void setEmailTimeSlot      (final boolean emailTimeSlot)      { this.emailTimeSlot      = emailTimeSlot;      }
  public void setDepartment         (final String department)          { this.department         = department;         }
  public void setCourseNum          (final String courseNum)           { this.courseNum          = courseNum;          }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public User    getUser()               { return this.user;               }
  public Course  getCourse()             { return this.course;             }
  public String  getFinalGrade()         { return this.finalGrade;         }
  public String  getStatus()             { return this.status;             }
  public Float   getTotalScore()         { return this.totalScore;         }
  public String  getLecture()            { return this.lecture;            }
  public String  getLab()                { return this.lab;                }
  public String  getSection()            { return this.section;            }
  public String  getCredits()            { return this.credits;            }
  public String  getGradeOption()        { return this.gradeOption;        }
  public boolean getEmailNewAssignment() { return this.emailNewAssignment; }
  public boolean getEmailDueDate()       { return this.emailDueDate;       }
  public boolean getEmailGroup()         { return this.emailGroup;         }
  public boolean getEmailNewGrade()      { return this.emailNewGrade;      }
  public boolean getEmailRegrade()       { return this.emailRegrade;       }
  public boolean getEmailFile()          { return this.emailFile;          }
  public boolean getEmailFinalGrade()    { return this.emailFinalGrade;    }
  public boolean getEmailTimeSlot()      { return this.emailTimeSlot;      }
  public String  getDepartment()         { return this.department;         }
  public String  getCourseNum()          { return this.courseNum;          }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public static Comparator getLAST_NAME_COMPARATOR() {
    if (LAST_NAME_COMPARATOR == null) {
      LAST_NAME_COMPARATOR = new Comparator() {
        public int compare(Object o1, Object o2) {
          if (!(o1 instanceof Student && o2 instanceof Student)) return 0;
          Student s1 = (Student) o1;
          Student s2 = (Student) o2;
          return User.getLAST_NAME_COMPARATOR().compare(s1.getUser(), s2.getUser());
        }
      };
    }
    return LAST_NAME_COMPARATOR;
  }
  
  public static Comparator getNETID_COMPARATOR() {
    if (NETID_COMPARATOR == null) {
      NETID_COMPARATOR = new Comparator() {
        public int compare(Object o1, Object o2) {
          if (!(o1 instanceof Student && o2 instanceof Student)) return 0;
          Student s1 = (Student) o1;
          Student s2 = (Student) o2;
          return User.getNETID_COMPARATOR().compare(s1.getUser(), s2.getUser());
        }
      };
    }
    return NETID_COMPARATOR;
  }
  
  public Student(Course course, User user) {
    
    
   
    
    
    setUser(user);
    setCourse(course);
    setCredits(null);
    setEmailDueDate(false);
    setEmailFile(false);
    setEmailFinalGrade(false);
    setEmailGroup(false);
    setEmailNewAssignment(false);
    setEmailNewGrade(false);
    setEmailRegrade(false);
    setEmailTimeSlot(false);
    setFinalGrade(null);
    setGradeOption(null);
    setLab(null);
    setLecture(null);
    setSection(null);
    setStatus(ENROLLED);
    setTotalScore(null);
    
    course.students.put(user, this);
    user.studentIndex.put(course, this);
  }
}
