package cms.model;

import fabric.util.*;
import java.util.Date;

public class Group {
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////
  
  private final Assignment assignment;
  private Date       latestSubmission;
  private Date       extension;
  private int        fileCounter;
  private int        remainingSubmissions;
  private TimeSlot   timeSlot;
  
  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////
  
  public void setLatestSubmission     (final Date latestSubmission)           { this.latestSubmission     = latestSubmission;     }
  public void setExtension            (final Date extension)                  { this.extension            = extension;            }
  public void setFileCounter          (final int fileCounter)                 { this.fileCounter          = fileCounter;          }
  public void setRemainingSubmissions (final int remainingSubmissions)        { this.remainingSubmissions = remainingSubmissions; }
  public void setTimeSlot             (final TimeSlot timeSlot)               { this.timeSlot             = timeSlot;             }
  
  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////
  
  public Assignment getAssignment()           { return this.assignment;           }
  public Date       getLatestSubmission()     { return this.latestSubmission;     }
  public Date       getExtension()            { return this.extension;            }
  public int        getFileCounter()          { return this.fileCounter;          }
  public int        getRemainingSubmissions() { return this.remainingSubmissions; }
  public TimeSlot   getTimeSlot()             { return this.timeSlot;             }

  public Collection/*GroupMember*/ getMembers() {
    return Collections.unmodifiableCollection(this.members.values());
  }

  //////////////////////////////////////////////////////////////////////////////
  // managed views                                                            //
  //////////////////////////////////////////////////////////////////////////////

  final Map/*User, GroupMember*/ members;  // by GroupMember
  final Collection/*SubmittedFile*/ submittedFiles; //by SubmittedFile

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Group(Assignment assign, int remainingSubmissions) {
    this.assignment = assign;
    setTimeSlot(null);
    setRemainingSubmissions(remainingSubmissions);
    setExtension(null);
    setFileCounter(1);
    setLatestSubmission(null);
    
    assign.groups.add(this);
    assign.course.semester.database.groups.put(toString(), this);
    this.members = new HashMap();
    this.submittedFiles = new ArrayList();
  }
  
  public Collection/*Comment*/ getComments() {
    return new ArrayList(); // XXX
  }
  
  public Collection/*RegradeRequest*/ getRegradeRequests() {
    return assignment.findRegradeRequests(this);
  }
  
  public Collection/*SubmittedFile*/ getSubmittedFiles() {
    return Collections.unmodifiableCollection(submittedFiles);
  }
  
  public boolean hasMember(User u) {
    return members.containsKey(u);
  }
  
  public GroupMember getMember(User u) {
    return (GroupMember)members.get(u);
  }
  
  public GroupMember findGroupMember(User user) {
    return (GroupMember)members.get(user);
  }
  public Collection/*GroupMember*/ findActiveMembers() {
    Collection result = new ArrayList();
    for (Iterator i = members.values().iterator(); i.hasNext();) {
      GroupMember m = (GroupMember)i.next();
      if (m.getStatus().equalsIgnoreCase("active"))
        result.add(m);
    }
    return result;
  }
  public boolean isAssignedTo(User p) {
    throw new NotImplementedException();
  }
  public GroupGrade getGrade() {
    throw new NotImplementedException();
  }
  public GroupGrade findMostRecentGrade(SubProblem subProb) {
    throw new NotImplementedException();
  }
  
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Group)) return false;
    Group g = (Group)obj;
    if (g.members.size() != members.size()) return false;
    if (g.assignment.toString() != assignment.toString()) return false;
    for (Iterator i = members.keySet().iterator(); i.hasNext();) {
      User u = (User)i.next();
      if (!g.members.containsKey(u)) return false;
    }
    return true;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
