package cms.model;

import java.util.Date;

public class AssignmentFile implements FileEntry {
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Date           fileDate;
  private FileData       file;
  private AssignmentItem assignmentItem;
  private boolean        hidden;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setFile           (final FileData file)                 { this.file           = file;           }
  public void setFileDate       (final Date fileDate)                 { this.fileDate       = fileDate;       }
  public void setAssignmentItem (final AssignmentItem assignmentItem) { this.assignmentItem = assignmentItem; }
  public void setHidden         (final boolean hidden)                { this.hidden         = hidden;         }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public FileData       getFile()           { return this.file;           }
  public Date           getFileDate()       { return this.fileDate;       }
  public AssignmentItem getAssignmentItem() { return this.assignmentItem; }
  public boolean        getHidden()         { return this.hidden;         }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  AssignmentFile(AssignmentItem item, boolean isHidden, FileData file) {
    setAssignmentItem(item);
    setFileDate(new Date());
    setFile(file);
  }

  public boolean isFileAuthorized(User user) {
    // See TransactionHandler.authorizeDownload
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
