package cms.model;

public class CommentFile {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Comment comment;
  private String  fileName;
  private String  path;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setComment  (final Comment comment)  { this.comment  = comment;  }
  public void setFileName (final String fileName)  { this.fileName = fileName; }
  public void setPath     (final String path)      { this.path     = path;     }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Comment getComment()  { return this.comment;  }
  public String  getFileName() { return this.fileName; }
  public String  getPath()     { return this.path;     }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public CommentFile(Comment comment, String fileName, String path) {
    setComment(comment);
    setFileName(fileName);
    setPath(path);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
