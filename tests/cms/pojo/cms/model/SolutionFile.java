package cms.model;

public class SolutionFile implements FileEntry {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Assignment assignment;
  private boolean    hidden;
  private FileData   file;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setAssignment (final Assignment assignment) { this.assignment = assignment; }
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

  public SolutionFile(Assignment assign, boolean hidden, FileData data) {
    setAssignment(assign);
    setHidden(hidden);
    setFile(data);
  }
  public boolean isFileAuthorized(User user) {
    // TODO Auto-generated method stub
    //  See TransactionHandler.authorizeDownload
    return false;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
