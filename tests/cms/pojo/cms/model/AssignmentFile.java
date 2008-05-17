package cms.model;

import java.util.Date;

public class AssignmentFile {
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private String         path;
  private String         fileName;
  private Date           fileDate;
  private AssignmentItem assignmentItem;
  private boolean        hidden;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setPath           (final String path)                   { this.path           = path;           }
  public void setFileName       (final String fileName)               { this.fileName       = fileName;       }
  public void setFileDate       (final Date fileDate)                 { this.fileDate       = fileDate;       }
  public void setAssignmentItem (final AssignmentItem assignmentItem) { this.assignmentItem = assignmentItem; }
  public void setHidden         (final boolean hidden)                { this.hidden         = hidden;         }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public String         getPath()           { return this.path;           }
  public String         getFileName()       { return this.fileName;       }
  public Date           getFileDate()       { return this.fileDate;       }
  public AssignmentItem getAssignmentItem() { return this.assignmentItem; }
  public boolean        getHidden()         { return this.hidden;         }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public AssignmentFile(AssignmentItem item, String fileName, boolean isHidden, String path) {
    setAssignmentItem(item);
    setFileName(fileName);
    setFileDate(new Date());
    setPath(path);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
