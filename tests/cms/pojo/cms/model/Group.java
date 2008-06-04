package cms.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

public class Group {
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////
  
  private Assignment assignment;
  private Date       latestSubmission;
  private Date       extension;
  private int        fileCounter;
  private int        remainingSubmissions;
  private TimeSlot   timeSlot;
  
  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////
  
  public void setAssignment           (final Assignment assignment)           { this.assignment           = assignment;           }
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
    return Collections.unmodifiableCollection(this.members);
  }

  //////////////////////////////////////////////////////////////////////////////
  // managed views                                                            //
  //////////////////////////////////////////////////////////////////////////////

  Collection/*GroupMember*/ members;

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Group(Assignment assign, int remainingSubmissions) {
    setAssignment(assign);
    setTimeSlot(null);
    setRemainingSubmissions(remainingSubmissions);
    setExtension(null);
    setFileCounter(1);
    setLatestSubmission(null);
  }
  
  public Collection/*Comment*/ getComments() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public Collection/*RegradeRequest*/ getRegradeRequests() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public Collection/*SubmittedFile*/ getSubmittedFiles() {
    // TODO Auto-generated method stub
    return null;
  }
  public GroupMember findGroupMember(User user) {
    // TODO Auto-generated method stub
    return null;
  }
  public Collection/*GroupMember*/ findActiveMembers() {
    // TODO Auto-generated method stub
    return null;
  }
  public boolean isAssignedTo(User p) {
    // TODO Auto-generated method stub
    return false;
  }
  public GroupGrade getGrade() {
    // TODO Auto-generated method stub
    return null;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
