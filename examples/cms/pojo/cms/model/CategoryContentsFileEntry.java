package cms.model;

/**
 * @see CategoryContentsFileList
 */
public class CategoryContentsFileEntry {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private CategoryContentsFileList list;
  private boolean                  hidden;
  private FileData                 file;
  private String                   linkName;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setList     (final CategoryContentsFileList list)     { this.list     = list;     }
  public void setHidden   (final boolean hidden)                    { this.hidden   = hidden;   }
  public void setFile     (final FileData file)                     { this.file     = file;     }
  public void setLinkName (final String linkName)                   { this.linkName = linkName; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public CategoryContentsFileList getList()     { return this.list;     }
  public boolean                  getHidden()   { return this.hidden;   }
  public FileData                 getFile()     { return this.file;     }
  public String                   getLinkName() { return this.linkName; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public CategoryContentsFileEntry(CategoryContentsFileList list,
      boolean hidden, FileData file, String linkName) {
    setList(list);
    setHidden(hidden);
    setFile(file);
    setLinkName(linkName);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
