package cms.model;

public abstract class CategoryContents {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private CategoryRow    row;
  private CategoryColumn column;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setRow    (final CategoryRow row)       { this.row    = row;    }
  public void setColumn (final CategoryColumn column) { this.column = column; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public CategoryRow    getRow()    { return this.row;    }
  public CategoryColumn getColumn() { return this.column; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public CategoryContents(CategoryColumn column, CategoryRow row) {
  }

  //////////////////////////////////////////////////////////////////////////////
  // public methods                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public abstract String getType();

  public abstract void accept(Visitor visitor);

  public interface Visitor {
    void visitFileContents(CategoryContentsFileList     fileContents);
    void visitDateContents(CategoryContentsDate     dateContents);
    void visitLinkContents(CategoryContentsLink     linkContents);
    void visitNumberContents(CategoryContentsNumber numberContents);
    void visitStringContents(CategoryContentsString stringContents);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
