package cms.model;

import java.util.Collection;
import java.util.Date;

public class Announcement {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Course  course;
  private String  author;
  private Date    posted;
  private String  text;
  private String  editInfo;
  private boolean hidden;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setCourse (final Course course) {
    if (this.course != null)
      this.course.announcements.remove(this);
    this.course   = course;
    if (course != null)
      course.announcements.add(this);
  }

  public void setAuthor   (final String author)    { this.author   = author;   }
  public void setPosted   (final Date posted)      { this.posted   = posted;   }
  public void setText     (final String text)      { this.text     = text;     }
  public void setEditInfo (final String editInfo)  { this.editInfo = editInfo; }
  public void setHidden   (final boolean hidden)   { this.hidden   = hidden;   }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Course  getCourse()   { return this.course;   }
  public String  getAuthor()   { return this.author;   }
  public Date    getPosted()   { return this.posted;   }
  public String  getText()     { return this.text;     }
  public String  getEditInfo() { return this.editInfo; }
  public boolean getHidden()   { return this.hidden;   }

  public Collection getAnnouncementHistory() {
    // TODO
    return null;
  }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Announcement(Course course, String author, String text) {
    this.setCourse (course);
    this.setAuthor (author);
    this.setText   (text);

    this.setPosted   (new Date());
    this.setEditInfo ("");
    this.setHidden   (false);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
