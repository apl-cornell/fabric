package cms.model;

import java.util.Date;

import org.apache.commons.fileupload.FileUploadException;

public class AssignmentFile implements FileEntry {
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Date           fileDate;
  private FileData       file;
  private final AssignmentItem assignmentItem;
  private boolean        hidden;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setFile           (final FileData file)                 { this.file           = file;           }
  public void setFileDate       (final Date fileDate)                 { this.fileDate       = fileDate;       }
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

  public AssignmentFile(AssignmentItem item, boolean isHidden, FileData file)
      throws FileUploadException {
    this.assignmentItem = item;
    setFileDate(new Date());
    setFile(file);
    
    if (assignmentItem.file != null) {
      throw new FileUploadException("Error: Conflicting files chosen for assignment item.<br>");
    }
    
    assignmentItem.file = this;
  }
  
  public boolean isFileAuthorized(User user) {
    // See TransactionHandler.authorizeDownload
    throw new NotImplementedException();
  }

  public String getFileName() {
    return file.getName();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
