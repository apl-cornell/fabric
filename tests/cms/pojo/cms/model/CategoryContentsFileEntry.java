package cms.model;

/**
 * @see CategoryContentsFileList
 */
public class CategoryContentsFileEntry {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private CategoryContentsFileList list;
  private String                   fileName;
  private boolean                  hidden;
  private String                   path;
  private String                   linkName;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setList     (final CategoryContentsFileList list)     { this.list     = list;     }
  public void setFileName (final String fileName)                   { this.fileName = fileName; }
  public void setHidden   (final boolean hidden)                    { this.hidden   = hidden;   }
  public void setPath     (final String path)                       { this.path     = path;     }
  public void setLinkName (final String linkName)                   { this.linkName = linkName; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public CategoryContentsFileList getList()     { return this.list;     }
  public String                   getFileName() { return this.fileName; }
  public boolean                  getHidden()   { return this.hidden;   }
  public String                   getPath()     { return this.path;     }
  public String                   getLinkName() { return this.linkName; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public CategoryContentsFileEntry(CategoryContentsFileList list,
      String fileName, boolean hidden, String path, String linkName) {
    setList(list);
    setFileName(fileName);
    setHidden(hidden);
    setPath(path);
    setLinkName(linkName);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
