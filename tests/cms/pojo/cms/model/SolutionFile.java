package cms.model;

public class SolutionFile {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Assignment assignment;
  private boolean    hidden;
  private String     fileName;
  private String     path;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setAssignment (final Assignment assignment) { this.assignment = assignment; }
  public void setHidden     (final boolean hidden)        { this.hidden     = hidden;     }
  public void setFileName   (final String fileName)       { this.fileName   = fileName;   }
  public void setPath       (final String path)           { this.path       = path;       }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Assignment getAssignment() { return this.assignment; }
  public boolean    getHidden()     { return this.hidden;     }
  public String     getFileName()   { return this.fileName;   }
  public String     getPath()       { return this.path;       }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public SolutionFile(Assignment assign, String fileName, boolean hidden, String path) {
    setAssignment(assign);
    setFileName(fileName);
    setHidden(hidden);
    setPath(path);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
