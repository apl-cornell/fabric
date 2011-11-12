package cms.model;

public class OldAnnouncement {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Announcement announcement;
  private String       text;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setAnnouncement (final Announcement announcement) { this.announcement = announcement; }
  public void setText         (final String text)               { this.text         = text;         }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Announcement getAnnouncement() { return this.announcement; }
  public String       getText()         { return this.text;         }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public OldAnnouncement(Announcement announcement, String text) {
    setAnnouncement(announcement);
    setText(text);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
