package cms.model;

import java.util.*;

import cms.auth.Principal;

public class Semester implements Comparable {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  final CMSRoot database;
  private final String  name;
  private boolean hidden;
  
  //////////////////////////////////////////////////////////////////////////////
  // managed fields
  //////////////////////////////////////////////////////////////////////////////
  
  Collection courses; // Managed by Course.

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setHidden (final boolean hidden) { this.hidden = hidden; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public String  getName()   { return this.name;   }
  public boolean getHidden() { return this.hidden; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public Semester(CMSRoot database, String name) {
    this.name = name;
    setHidden(false);
    
    this.database = database;
    this.courses = new LinkedList();
    database.semesters.put(toString(), this);
  }
  
  public Collection getCourses() {
    return Collections.unmodifiableCollection(courses);
  }
  
  public Collection/*Course*/ findStaffAdminCourses(User user) {
    throw new NotImplementedException();
  }
  
  public Collection/*Course*/ findCCAccessCourses() {
    SortedSet result = new TreeSet();
    
    for (Iterator cit = courses.iterator(); cit.hasNext();) {
      Course course = (Course) cit.next();
      if (course.getCourseCCAccess()) result.add(course);
    }
    return result;
  }
  
  public Collection/*Course*/ findGuestAccessCourses() {
    SortedSet result = new TreeSet();
    
    for (Iterator cit = courses.iterator(); cit.hasNext();) {
      Course course = (Course) cit.next();
      if (course.getCourseGuestAccess()) result.add(course);
    }
    return result;
  }

  /**
   * Returns a collection of all unhidden assignments in the semester, sorted by
   * course code.
   */
  public Collection/*Assignment*/ findAssignments() {
    SortedSet result = new TreeSet();
    for (Iterator cit = courses.iterator(); cit.hasNext();) {
      Course course = (Course) cit.next();
      for (Iterator ait = course.getAssignments().iterator(); ait.hasNext();) {
        Assignment assignment = (Assignment) ait.next();
        if (!assignment.getHidden()) result.add(assignment);
      }
    }
    
    return result;
  }
  
  public int compareTo(Object o) {
    if (!(o instanceof Semester)) return 0;
    
    String name1 = getName();
    String name2 = ((Semester) o).getName();
    
    int year1 = Integer.parseInt(name1.substring(name1.indexOf(' ')+1));
    int year2 = Integer.parseInt(name2.substring(name2.indexOf(' ')+1));
    if (year1 != year2) return year1 - year2;
    
    String session1 = name1.substring(0, name1.indexOf(' '));
    String session2 = name2.substring(0, name2.indexOf(' '));
    if (session1.equalsIgnoreCase(session2)) return 0;
    
    if (session1.equalsIgnoreCase("Spring")) return -1;
    if (session2.equalsIgnoreCase("Spring")) return 1;
    if (session1.equalsIgnoreCase("Summer")) return -1;
    if (session2.equalsIgnoreCase("Summer")) return 1;
    if (session1.equalsIgnoreCase("Fall")) return -1;
    if (session2.equalsIgnoreCase("Fall")) return 1;
    if (session1.equalsIgnoreCase("Winter")) return -1;
    if (session2.equalsIgnoreCase("Winter")) return 1;
    
    return 0;
  }
  
  public String toString() {
    return name;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
