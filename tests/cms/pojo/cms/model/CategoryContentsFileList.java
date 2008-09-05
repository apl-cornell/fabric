package cms.model;

import java.util.Collection;

public class CategoryContentsFileList extends CategoryContents {

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  CategoryContentsFileList(CategoryRow row, CategoryColumn col) {
    super(col, row);
  }

  //////////////////////////////////////////////////////////////////////////////
  // public methods                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void accept(Visitor visitor) {
    visitor.visitFileContents(this);
  }

  public String getType() {
    return CategoryColumn.FILE;
  }
  
  public Collection/*CategoryContentsFileEntry*/ getFiles() {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
