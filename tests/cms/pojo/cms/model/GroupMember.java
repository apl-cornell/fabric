package cms.model;

public class GroupMember {
  public static final String
    INVITED= "Invited",
    REJECTED= "Rejected",
    ACTIVE= "Active";

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Group   group;
  private Student member;
  private String  status;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setGroup   (final Group group)      { this.group  = group;  }
  public void setStudent (final Student member)   { this.member = member; }
  public void setStatus  (final String status)    { this.status = status; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Group   getGroup()   { return this.group;  }
  public Student getStudent() { return this.member; }
  public String  getStatus()  { return this.status; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  GroupMember(Group group, Student user, String status) {
    setGroup(group);
    setStudent(user);
    setStatus(status);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
