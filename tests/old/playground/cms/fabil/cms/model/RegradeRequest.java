package cms.model;

import java.util.Date;

public class RegradeRequest {

  public static final String
    PENDING= "Pending",
    REGRADED= "Regraded";
  
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Group      group;
  private SubProblem subProblem;
  private Comment    comment;
  private User       user;
  private String     request;
  private String     status;
  private Date       dateEntered;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setGroup       (final Group group)            { this.group       = group;       }
  public void setSubProblem  (final SubProblem subProblem)  { this.subProblem  = subProblem;  }
  public void setComment     (final Comment comment)        { this.comment     = comment;     }
  public void setUser        (final User user)              { this.user        = user;        }
  public void setRequest     (final String request)         { this.request     = request;     }
  public void setStatus      (final String status)          { this.status      = status;      }
  public void setDateEntered (final Date dateEntered)       { this.dateEntered = dateEntered; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Group      getGroup()       { return this.group;       }
  public SubProblem getSubProblem()  { return this.subProblem;  }
  public Comment    getComment()     { return this.comment;     }
  public User       getUser()        { return this.user;        }
  public String     getRequest()     { return this.request;     }
  public String     getStatus()      { return this.status;      }
  public Date       getDateEntered() { return this.dateEntered; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public RegradeRequest(SubProblem subProblem, Group group, User user, String request) {
    setDateEntered(new Date());
    setSubProblem(subProblem);
    setGroup(group);
    setUser(user);
    setRequest(request);
    setStatus(PENDING);
    setComment(null);
  }
  public void addResponse(String value) {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
