package cms.model;

import fabric.util.*;
import java.util.Date;

public class TimeSlot {
  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private Assignment assignment;
  private Course     course;
  private int        population;
  private User       staff;
  private String     name;
  private String     location;
  private Date       startTime;
  private boolean    hidden;

  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setAssignment (final Assignment assignment) { this.assignment = assignment; }
  public void setCourse     (final Course course)         { this.course     = course;     }
  public void setPopulation (final int population)        { this.population = population; }
  public void setStaff      (final User staff)            { this.staff      = staff;      }
  public void setName       (final String name)           { this.name       = name;       }
  public void setLocation   (final String location)       { this.location   = location;   }
  public void setStartTime  (final Date startTime)        { this.startTime  = startTime;  }
  public void setHidden     (final boolean hidden)        { this.hidden     = hidden;     }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Assignment getAssignment() { return this.assignment; }
  public Course     getCourse()     { return this.course;     }
  public int        getPopulation() { return this.population; }
  public User       getStaff()      { return this.staff;      }
  public String     getName()       { return this.name;       }
  public String     getLocation()   { return this.location;   }
  public Date       getStartTime()  { return this.startTime;  }
  public boolean    getHidden()     { return this.hidden;     }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public TimeSlot() {
    // TODO: unclear from CMS what happens in corresponding ejbCreate
  }
  
  public Collection/*Group*/ findGroups() {
    throw new NotImplementedException();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
