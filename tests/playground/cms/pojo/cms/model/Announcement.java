package cms.model;

import java.util.Collection;
import java.util.Date;

public class Announcement implements Comparable {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Course  course;
  private User    author;
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

  public void setAuthor   (final User author)      { this.author   = author;   }
  public void setPosted   (final Date posted)      { this.posted   = posted;   }
  public void setText     (final String text)      { this.text     = text;     }
  public void setEditInfo (final String editInfo)  { this.editInfo = editInfo; }
  public void setHidden   (final boolean hidden)   { this.hidden   = hidden;   }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Course  getCourse()   { return this.course;   }
  public User    getAuthor()   { return this.author;   }
  public Date    getPosted()   { return this.posted;   }
  public String  getText()     { return this.text;     }
  public String  getEditInfo() { return this.editInfo; }
  public boolean getHidden()   { return this.hidden;   }

  public Collection getAnnouncementHistory() {
    throw new NotImplementedException();
  }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Announcement(Course course, User author, String text) {
    this.setCourse (course);
    this.setAuthor (author);
    this.setText   (text);

    this.setPosted   (new Date());
    this.setEditInfo ("");
    this.setHidden   (false);
  }

  public int compareTo(Object o) {
    if (!(o instanceof Announcement)) return 0;
    return -posted.compareTo(((Announcement) o).posted);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
