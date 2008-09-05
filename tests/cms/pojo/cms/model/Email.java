package cms.model;

import java.util.Date;

public class Email {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Course course;
  private String subject;
  private String message;
  private User   sender;
  private int    recipient;
  private Date   dateSent;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setCourse    (final Course course)    { this.course    = course;    }
  public void setSubject   (final String subject)   { this.subject   = subject;   }
  public void setMessage   (final String message)   { this.message   = message;   }
  public void setSender    (final User sender)      { this.sender    = sender;    }
  public void setRecipient (final int recipient)    { this.recipient = recipient; }
  public void setDateSent  (final Date dateSent)    { this.dateSent  = dateSent;  }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Course getCourse()    { return this.course;    }
  public String getSubject()   { return this.subject;   }
  public String getMessage()   { return this.message;   }
  public User   getSender()    { return this.sender;    }
  public int    getRecipient() { return this.recipient; }
  public Date   getDateSent()  { return this.dateSent;  }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  Email(Course course, User sender, String subject, String message, int recipient) {
    setCourse(course);
    setSender(sender);
    setSubject(subject);
    setMessage(message);
    setRecipient(recipient);
    setDateSent(new Date(System.currentTimeMillis()));
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
