package cms.model;

public class CategoryColumn {

  // legal column data types
  public static final String
    DATE = "date",
    TEXT = "text",
    FILE = "file",
    LINK = "url",
    NUMBER = "number";

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private String   name;
  private String   type;
  private Category category;
  private boolean  hidden;
  private boolean  removed;
  private int      position;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setName     (final String name)       { this.name     = name;     }
  public void setType     (final String type)       { this.type     = type;     }
  public void setCategory (final Category category) { this.category = category; }
  public void setHidden   (final boolean hidden)    { this.hidden   = hidden;   }
  public void setRemoved  (final boolean removed)   { this.removed  = removed;  }
  public void setPosition (final int position)      { this.position = position; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public String   getName()     { return this.name;     }
  public String   getType()     { return this.type;     }
  public Category getCategory() { return this.category; }
  public boolean  getHidden()   { return this.hidden;   }
  public boolean  getRemoved()  { return this.removed;  }
  public int      getPosition() { return this.position; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public CategoryColumn(String name, String type, Category category, boolean hidden, boolean removed, int position) {
    setName(name);
    setType(type);
    setCategory(category);
    setHidden(hidden);
    setRemoved(removed);
    setPosition(position);
  }
  public CategoryColumn(Category parent) {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
