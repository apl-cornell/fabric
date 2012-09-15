package cms.model;

import java.util.Date;

public class SiteNotice implements Comparable {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private CMSRoot db;
  private User    author;
  private Date    postedDate;
  private Date    expireDate;
  private String  text;
  private boolean hidden;
  private boolean deleted;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setAuthor     (final User author)        { this.author     = author;     }
  public void setPostedDate (final Date postedDate)    { this.postedDate = postedDate; }
  public void setExpireDate (final Date expireDate)    { this.expireDate = expireDate; }
  public void setText       (final String text)        { this.text       = text;       }
  public void setHidden     (final boolean hidden)     { this.hidden     = hidden;     }
  public void setDeleted    (final boolean deleted)    { this.deleted    = deleted;    }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public User    getAuthor()     { return this.author;     }
  public Date    getPostedDate() { return this.postedDate; }
  public Date    getExpireDate() { return this.expireDate; }
  public String  getText()       { return this.text;       }
  public boolean getHidden()     { return this.hidden;     }
  public boolean getDeleted()    { return this.deleted;    }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public SiteNotice(CMSRoot db, User author, String text, Date exp, boolean hidden) {
    this.db = db;
    setAuthor(author);
    setText(text);
    setPostedDate(new Date());
    setExpireDate(exp);
    setHidden(hidden);
    setDeleted(false);
    
    db.notices.put(toString(), this);
  }
  
  public int compareTo(java.lang.Object o) {
    if (!(o instanceof SiteNotice)) return 0;
    return -getPostedDate().compareTo(((SiteNotice) o).getPostedDate());
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
