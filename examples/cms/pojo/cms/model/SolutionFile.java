package cms.model;

import org.apache.commons.fileupload.FileUploadException;

public class SolutionFile implements FileEntry {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private final Assignment assignment;
  private boolean    hidden;
  private FileData   file;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setHidden     (final boolean hidden)        { this.hidden     = hidden;     }
  public void setFile       (final FileData file)         { this.file       = file;       }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Assignment getAssignment() { return this.assignment; }
  public boolean    getHidden()     { return this.hidden;     }
  public FileData   getFile()       { return this.file;       }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public SolutionFile(Assignment assign, boolean hidden, FileData data)
      throws FileUploadException {
    this.assignment = assign;
    setHidden(hidden);
    setFile(data);
    assignment.addSolutionFile(this);
  }
  
  public boolean isFileAuthorized(User user) {
    //  See TransactionHandler.authorizeDownload
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
