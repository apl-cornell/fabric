package cms.model;

public class GroupMember implements Comparable {
  public static final String
    INVITED= "Invited",
    REJECTED= "Rejected",
    ACTIVE= "Active";

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private final Group   group;
  private final Student student;
  private String  status;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setStatus(final String status) {
    this.status = status;

    Assignment assignment = group.getAssignment();
    User user = student.getUser();
    GroupMember oldGroupMember =
        (GroupMember) assignment.groupMemberIndex.get(user);
    
    if (status.equals(ACTIVE)) {
      if (oldGroupMember != this) {
        if (oldGroupMember != null) {
          oldGroupMember.setStatus(REJECTED);
        }
        
        assignment.groupMemberIndex.put(user, this);
      }
    } else if (oldGroupMember == this) {
      assignment.groupMemberIndex.remove(user);
    }
  }

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
    this.group = group;
    this.student = student;
    
    group.members.put(student.getUser(), this);
    
    setStatus(status);
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
