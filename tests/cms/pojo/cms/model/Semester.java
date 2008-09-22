package cms.model;

import java.util.*;

import cms.auth.Principal;

public class Semester {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private final CMSRoot database;
  private String  name;
  private boolean hidden;
  
  //////////////////////////////////////////////////////////////////////////////
  // managed fields
  //////////////////////////////////////////////////////////////////////////////
  
  Collection courses; // Managed by Course.

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setName   (final String name)    { this.name   = name;   }
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
    setName(name);
    setHidden(false);
    
    this.database = database;
    this.courses = new LinkedList();
    database.semesters.add(this);
  }
  
  public Collection getCourses() {
    return Collections.unmodifiableCollection(courses);
  }
  public Collection/*Course*/ findStaffAdminCourses(User user) {
    throw new NotImplementedException();
  }
  public Collection/*Course*/ findCCAccessCourses() {
    throw new NotImplementedException();
  }
  public Collection/*Course*/ findGuestAccessCourses() {
    SortedSet result = new TreeSet(new Comparator() {
      public int compare(Object o1, Object o2) {
        Course c1 = (Course) o1;
        Course c2 = (Course) o2;
        return c1.getCode().compareTo(c2.getCode());
      }
    });
    
    for (Iterator cit = courses.iterator(); cit.hasNext();) {
      Course course = (Course) cit.next();
      if (course.getCourseGuestAccess()) result.add(course);
    }
    return result;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
