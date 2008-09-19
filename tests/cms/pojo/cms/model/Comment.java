package cms.model;

import java.util.Collection;
import java.util.Date;

public class Comment {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Group   group;
  private String  comment;
  private User    user;
  private Date    dateEntered;
  private boolean hidden;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setGroup       (final Group group)         { this.group       = group;       }
  public void setComment     (final String comment)      { this.comment     = comment;     }
  public void setUser        (final User user)           { this.user        = user;        }
  public void setDateEntered (final Date dateEntered)    { this.dateEntered = dateEntered; }
  public void setHidden      (final boolean hidden)      { this.hidden      = hidden;      }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Group   getGroup()       { return this.group;       }
  public String  getComment()     { return this.comment;     }
  public User    getUser()        { return this.user;        }
  public Date    getDateEntered() { return this.dateEntered; }
  public boolean getHidden()      { return this.hidden;      }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Comment(String comment, User user, Group group) {
    setComment(comment);
    setUser(user);
    setGroup(group);
    setDateEntered(new Date());
    setHidden(false);
  }
  
  public Collection/*RegradeRequest*/ findRequests() {
    throw new NotImplementedException();
  }
  
  public Collection/*CommentFiles*/ getFiles() {
    throw new NotImplementedException();
  }
  
  public CommentFile getCommentFile() {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
