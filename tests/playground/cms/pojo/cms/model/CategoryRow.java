package cms.model;

import java.util.Date;

public class CategoryRow {
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Category category;
  private boolean  hidden;
  private Date     releaseDate;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setCategory    (final Category category)    { this.category    = category;    }
  public void setHidden      (final boolean hidden)       { this.hidden      = hidden;      }
  public void setReleaseDate (final Date releaseDate)     { this.releaseDate = releaseDate; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Category getCategory()    { return this.category;    }
  public boolean  getHidden()      { return this.hidden;      }
  public Date     getReleaseDate() { return this.releaseDate; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public CategoryRow(Category category, boolean hidden, Date releaseDate) {
    this.setCategory(category);
    this.setHidden(hidden);
    this.setReleaseDate(releaseDate);
  }

  public CategoryContents getContents(CategoryColumn column) {
    throw new NotImplementedException();
  }
  
  /**
   * Create a and return new entry in this row for the given column. The type of
   * the created contents will be determined by the type of the column.
   */
  public CategoryContents createContents(CategoryColumn col) {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
