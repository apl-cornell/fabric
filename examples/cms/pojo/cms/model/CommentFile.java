package cms.model;

public class CommentFile implements FileEntry {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Comment  comment;
  private FileData file;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setComment  (final Comment comment)  { this.comment  = comment;  }
  public void setFile     (final FileData file)    { this.file     = file;     }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Comment  getComment()  { return this.comment;  }
  public FileData getFile()     { return this.file;     }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public CommentFile(Comment comment, FileData file) {
    setComment(comment);
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
