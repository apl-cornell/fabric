package cms.model;

import fabric.util.Collection;

public class Category implements Comparable {
  
  public static final String
    ASCENDING     = "asc",
    DESCENDING    = "desc",
    ANNOUNCEMENTS = "Announcements",
    DATE          = "Date",
    ANNOUNCEMENT  = "Announcement";
  
  public static final int 
    SHOWALL = Integer.MAX_VALUE;

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Course         course;
  private String         name;
  private boolean        hidden;
  private boolean        ascending;
  private CategoryColumn sortBy;
  private int            numToShow;
  private int            fileCount;
  private int            authLevel;
  private int            position;
  private boolean        isAnnouncements;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setCourse          (final Course course)                  { this.course          = course;          }
  public void setName            (final String categoryName)            { this.name            = categoryName;    }
  public void setHidden          (final boolean hidden)                 { this.hidden          = hidden;          }
  public void setAscending       (final boolean ascending)              { this.ascending       = ascending;       }
  public void setSortBy          (final CategoryColumn sortBy)          { this.sortBy          = sortBy;          }
  public void setNumToShow       (final int numToShow)                  { this.numToShow       = numToShow;       }
  public void setFileCount       (final int fileCount)                  { this.fileCount       = fileCount;       }
  public void setAuthLevel       (final int authLevel)                  { this.authLevel       = authLevel;       }
  public void setPosition        (final int position)                   { this.position        = position;        }
  public void setIsAnnouncements (final boolean isAnnouncements)        { this.isAnnouncements = isAnnouncements; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Course         getCourse()          { return this.course;          }
  public String         getCategoryName()    { return this.name;    }
  public boolean        getHidden()          { return this.hidden;          }
  public boolean        getAscending()       { return this.ascending;       }
  public CategoryColumn getSortBy()          { return this.sortBy;          }
  public int            getNumToShow()       { return this.numToShow;       }
  public int            getFileCount()       { return this.fileCount;       }
  public int            getAuthLevel()       { return this.authLevel;       }
  public int            getPosition()        { return this.position;        }
  public boolean        getIsAnnouncements() { return this.isAnnouncements; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Category(Course course, String name, boolean ascending,
                  CategoryColumn sortBy, int numToShow, boolean hidden,
                  int fileCount, int authorizationLevel, int position,
                  boolean announcements) {
    setCourse(course);
    setName(name);
    setAscending(ascending);
    setSortBy(sortBy);
    setNumToShow(numToShow);
    setHidden(hidden);
    setFileCount(fileCount);
    setAuthLevel(authorizationLevel);
    setPosition(position);
    setIsAnnouncements(announcements);
    
    course.categories.add(this);
  }
  
  public Collection/*CategoryColumn*/ getColumns() {
    throw new NotImplementedException();
  }
  public Collection/*CategoryColumn*/ getRemovedColumns() {
    throw new NotImplementedException();
  }
  public Collection/*CategoryRow*/ getRows() {
    throw new NotImplementedException();
  }
  
  public int compareTo(java.lang.Object o) {
    if (!(o instanceof Category)) return 0;
    
    Category category = (Category) o;
    if (position != category.position) return position - category.position;
    return toString().compareTo(category.toString());
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
