package cms.model;

public class LogDetail {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Log        log;
  private String     detail;
  private Assignment assignment;
  private final User       affectedUser;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setLog        (final Log log)               { this.log        = log;        }
  public void setDetail     (final String detail)         { this.detail     = detail;     }
  public void setAssignment (final Assignment assignment) { this.assignment = assignment; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Log        getLog()        { return this.log;        }
  public String     getDetail()     { return this.detail;     }
  public Assignment getAssignment() { return this.assignment; }
  public User       getAffectedUser()       { return this.affectedUser;       }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public LogDetail (Log log) {
    this(log, null, null, null);
  }

  public LogDetail (Log log, String detail) {
    this(log, detail, null, null);
  }
  
  public LogDetail(Log log, String detail, User affectedUser) {
    this(log, detail, affectedUser, null);
  }

  public LogDetail (Log log, String detail, User affectedUser, Assignment assign) {
    this.affectedUser = affectedUser;
    
    setLog(log);
    setDetail(detail);
    setAssignment(assign);
    
    if (affectedUser != null) {
      log.addReceivingUser(affectedUser);
    }
    
    log.detailLogs.add(this);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
