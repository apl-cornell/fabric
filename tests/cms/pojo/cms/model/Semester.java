package cms.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import cms.auth.Principal;

public class Semester {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private CMSRoot db;
  
  private String  name;
  private boolean hidden;
  
  private Collection courses;

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

  Semester(CMSRoot db, String name) {
    this.db = db;
    
    setName(name);
    setHidden(false);
    
    courses = new LinkedList();
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // public methods                                                           //
  //////////////////////////////////////////////////////////////////////////////

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
    throw new NotImplementedException();
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // convenience methods                                                      //
  //////////////////////////////////////////////////////////////////////////////

  /** Convenience method for database.addCourse(name, desc, code, this) */
  public Course addCourse(String name, String description, String code) {
    return db.addCourse(name, description, code, this);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
