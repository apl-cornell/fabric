package cms.model;

import java.util.Collections;

import java.util.Collection;
import java.util.Map;

/**
 * This class is the root container for all of the objects in the CMS Database.
 * Note that comments are descriptions of the ``real'' CMS, and may not yet
 * reflect the fabric re-implementation.
 *
 * @author mdgeorge
 * @see edu.cornell.csuglab.cms.base.Root
 * @see edu.cornell.csuglab.cms.base.RootDAO
 */
public class CMSRoot {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private int                  maxFileSize;
  private Map/*String,String*/ fileTypes;
  private boolean              debugMode;
  private Semester             currentSemester;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setMaxFileSize     (final int maxFileSize)          { this.maxFileSize     = maxFileSize;     }
  public void setDebugMode       (final boolean debugMode)        { this.debugMode       = debugMode;       }
  public void setCurrentSemester (final Semester currentSemester) { this.currentSemester = currentSemester; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public int      getMaxFileSize()     { return this.maxFileSize;     }
  public boolean  getDebugMode()       { return this.debugMode;       }
  public Semester getCurrentSemester() { return this.currentSemester; }

  /**
   * Return the collection of allowed file types.
   * @return a Collection containing <code>String</code>s, each of which is the
   *         extension corresponding to an acceptable file type
   */
  public Collection/*String*/ getFileTypes() {
    return Collections.unmodifiableSet(this.fileTypes.keySet());
  }

  //////////////////////////////////////////////////////////////////////////////
  // managed fields                                                           //
  //////////////////////////////////////////////////////////////////////////////

  Collection/*User*/ users;

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  // TODO

  //////////////////////////////////////////////////////////////////////////////
  // public methods                                                           //
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Set up the system state if necessary.  This includes:
   * <ol>
   *  <li>Create the current semester (corresponding to now)</li>
   *  <li>Create the SystemProperties:
   *   <ul>
   *    <li>current semester</li>
   *    <li>debugmode = false</li>
   *    <li>uploadMaxFileSize = 10000000)</li> </ul> </li>
   *  <li>Create the default file types:
   *   <ul>
   *    <li>Any file type</li>
   *    <li>Text document: txt</li>
   *    <li>Zip archive: zip</li>
   *    <li>Java source file: java</li>
   *    <li>Adobe Acrobat portable document file: pdf</li> </ul> </li> </ol>
   */
  public void ensureStartupSettings() {
    // TODO
    throw new UnsupportedOperationException();
  }

  /**
   * @return the number of distinct CUIDs found in the user table
   */
  public int getCUIDCount() {
    // TODO
    throw new UnsupportedOperationException();
  }
  
  public Collection/*User*/ getAllUsers() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public Collection/*LogDetail*/ findGradeLogDetails(Course course, Collection/*Group*/ groups) {
    // TODO Auto-generated method stub
    return null;
  }

  /* This method doesn't seem to work, and doesn't seem to be called in CMS
   * public Collection getNonStudentNetIDs(java.util.Collection netids,long courseID) ;
   */

  /* These methods have been moved to the assignment class:
   * public Collection<String> getNonSoloGroupMembers(Collection<String> netids, long assignmentID);
   * public Collection<String> getGradedStudents(Collection<String> netids,long assignmentID) ;
   * public Collection<long> getGradedGroups(Collection<long> groupIDs,long asgnID) ;
   */


  /* TODO
  public Collection getGradedAssignments(java.util.Collection netids,Course course) ;

  public Map getAssignmentIDMap(Course course) ;

  public Map getSubmissionNameMap(Assignment assignment) ;

  public Map getSubmissionNameMapByCourse(Course course) ;

  public Map getSubProblemIDMap(Assignment assignment) ;

  public Map getCIDCourseCodeMap();

  public Map getAssignmentNameMap() ;

  public Map getAssignmentNameMap(Course course) ;

  public Map getCategoryIDMap(Course course) ;

  public Map getCourseCodeMap() ;

  public Map getRemainingSubmissionMap(Assignment assignment) ;

  public Map getNameMap(Course courseID) ;

  public Map getFirstLastNameMap(Course course) ;

  public java.lang.Long hasSoloCourse(java.lang.String netID) ;

  public java.lang.Long hasSoloCourseBySemester(java.lang.String netID,Semester semester) ;

  public Collection findGradeLogDetails(Course course,java.util.Collection groupids) ;

  public Map getSubProblemNameMap(Assignment assignment) ;

  public Map getSubProblemNameMapByCourse(Course course) ;

  public Map getGroupIDMap(Assignment assignment) ;

  public Map getGroupIDMap(java.lang.String netID) ;

  public Map getGroupIDMapByCourse(Course course) ;

  public Map getGroupMemberListMap(Course course) ;

  public Map getGroupMembersMap(Course course) ;

  public Map getGroupSizeMap(Assignment assignment) ;

  public Map getCommentFileRequestIDMap(Assignment assignment) ;

  public Map getStaffNameMap(Course course) ;

  public Map getStaffFirstLastNameMap(Course course) ;

  public Map getStaffNameMap(java.lang.String netID) ;

  public Map getCommentFileGroupIDMap(Assignment assignment) ;

  public Map getCommentFileRequestIDMapByCourse(Course course) ;

  public Map getCommentFileGroupIDMapByCourse(Course course) ;

  public boolean isAssignedTo(java.lang.String netID,java.util.Collection groupIDs) ;

  public Collection getAllUsers() ;

  */
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
