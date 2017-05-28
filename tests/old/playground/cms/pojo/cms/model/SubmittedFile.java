package cms.model;

import java.util.Date;

public class SubmittedFile implements FileEntry {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Group              group;
  private Group              originalGroup;
  private RequiredSubmission submission;
  private Date               fileDate;
  private String             fileType;      //file extension ("zip", "txt", etc)
  private User               user;
  private boolean            lateSubmission;
  private FileData           file;
  
  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setGroup          (final Group group)                       { this.group          = group;          }
  public void setOriginalGroup  (final Group originalGroup)               { this.originalGroup  = originalGroup;  }
  public void setSubmission     (final RequiredSubmission submission)     { this.submission     = submission;     }
  public void setFileDate       (final Date fileDate)                     { this.fileDate       = fileDate;       }
  public void setFileType       (final String fileType)                   { this.fileType       = fileType;       }
  public void setUser           (final User user)                         { this.user           = user;           }
  public void setLateSubmission (final boolean lateSubmission)            { this.lateSubmission = lateSubmission; }
  public void setFile           (final FileData file)                     { this.file           = file;           }
  
  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Group              getGroup()          { return this.group;          }
  public Group              getOriginalGroup()  { return this.originalGroup;  }
  public RequiredSubmission getSubmission()     { return this.submission;     }
  public Date               getFileDate()       { return this.fileDate;       }
  public String             getFileType()       { return this.fileType;       }
  public User               getUser()           { return this.user;           }
  public boolean            getLateSubmission() { return this.lateSubmission; }
  public FileData           getFile()           { return this.file;           }
  
  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public SubmittedFile(Group group, Group originalGroup, User user,
      RequiredSubmission submission, String fileType, boolean lateSubmission,
      Date fileDate, FileData file) {
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
    setLateSubmission(lateSubmission);
    setFile(file);
    
    group.submittedFiles.add(this);
  }
  public boolean isFileAuthorized(User user) {
    // See TransactionHandler.authorizeDownload
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
