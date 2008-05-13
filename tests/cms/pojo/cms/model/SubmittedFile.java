package cms.model;

import java.util.Date;

public class SubmittedFile {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Group              group;
  private Group              originalGroup;
  private RequiredSubmission submission;
  private Date               fileDate;
  private String             fileType;      //file extension ("zip", "txt", etc)
  private User               user;
  private String             MD5;
  private boolean            lateSubmission;
  private String             path;
  private int                fileSize;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setGroup          (final Group group)                       { this.group          = group;          }
  public void setOriginalGroup  (final Group originalGroup)               { this.originalGroup  = originalGroup;  }
  public void setSubmission     (final RequiredSubmission submission)     { this.submission     = submission;     }
  public void setFileDate       (final Date fileDate)                     { this.fileDate       = fileDate;       }
  public void setFileType       (final String fileType)                   { this.fileType       = fileType;       }
  public void setUser           (final User user)                         { this.user           = user;           }
  public void setMD5            (final String MD5)                        { this.MD5            = MD5;            }
  public void setLateSubmission (final boolean lateSubmission)            { this.lateSubmission = lateSubmission; }
  public void setPath           (final String path)                       { this.path           = path;           }
  public void setFileSize       (final int fileSize)                      { this.fileSize       = fileSize;       }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Group              getGroup()          { return this.group;          }
  public Group              getOriginalGroup()  { return this.originalGroup;  }
  public RequiredSubmission getSubmission()     { return this.submission;     }
  public Date               getFileDate()       { return this.fileDate;       }
  public String             getFileType()       { return this.fileType;       }
  public User               getUser()           { return this.user;           }
  public String             getMD5()            { return this.MD5;            }
  public boolean            getLateSubmission() { return this.lateSubmission; }
  public String             getPath()           { return this.path;           }
  public int                getFileSize()       { return this.fileSize;       }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public SubmittedFile(Group group, Group originalGroup, User user,
                       RequiredSubmission submission, String fileType, int
                       fileSize, String MD5, boolean lateSubmission, String
                       path, Date fileDate) {
    setGroup(group);
    setOriginalGroup(originalGroup);
    setUser(user);
    setSubmission(submission);
    if (fileDate == null) {
      setFileDate(new Date());
    } else { 
        setFileDate(fileDate); 
    }
    setFileType(fileType);
    setFileSize(fileSize);
    setMD5(MD5);
    setLateSubmission(lateSubmission);
    setPath(path);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
