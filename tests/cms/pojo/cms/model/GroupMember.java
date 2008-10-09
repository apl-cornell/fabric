package cms.model;

public class GroupMember implements Comparable {
  public static final String
    INVITED= "Invited",
    REJECTED= "Rejected",
    ACTIVE= "Active";

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Group   group;
  private Student student;
  private String  status;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setGroup   (final Group group)      { this.group  = group;  }
  public void setStudent (final Student student)   { this.student = student; }
  public void setStatus  (final String status)    { this.status = status; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Group   getGroup()   { return this.group;  }
  public Student getStudent() { return this.student; }
  public String  getStatus()  { return this.status; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public GroupMember(Group group, Student student, String status) {
    setGroup(group);
    setStudent(student);
    setStatus(status);
    
    group.members.put(student.getUser(), this);
  }
  
  public int compareTo(Object o) {
    if (!(o instanceof GroupMember)) return 0;
    
    GroupMember member = (GroupMember) o;
    return Student.NETID_COMPARATOR.compare(this.student, member.student);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
