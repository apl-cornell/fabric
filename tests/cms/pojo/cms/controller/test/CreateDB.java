package cms.controller.test;

import java.util.Iterator;

import cms.model.*;

public class CreateDB {
  public static void create(CMSRoot database) {
    // Populate the database for testing.

    // create semesters
    Semester oldSem = new Semester(database, "Spring 2008");
    Semester newSem = new Semester(database, "Fall 2008");
    Semester curSem = new Semester(database, "Summer 2008");
    newSem.setHidden(true);
    
    // create users
    User mike   = new User(database, "mdg39", "Michael", "George", "11111", "Eng");
    User andru  = new User(database, "acm22", "Andrew",  "Myers",  "22222", "Eng");
    User jed    = new User(database, "ml103", "Jed",     "Liu",    "33333", "Eng");
    User vikram = new User(database, "kv48",  "Vikram",  "K",      "44444", "Eng");
    User xin    = new User(database, "xq24",  "Xin",     "Qi",     "55555", "Eng");
    
    andru.setCMSAdmin(true);
    
    // create courses
    for (Iterator sems = database.getAllSemesters().iterator(); sems.hasNext();) {
      Semester next = (Semester) sems.next();
      Course c = new Course(database, next, "Intro to Programming II", "In this course you will program a lot", "COM S 211");
      new Student(c, jed);
      Staff s = new Staff(andru, c);
      s = new Staff(mike, c);
      s.setAdminPriv(true);
      
      new Course(database, next, "Intro to Programming", "In this course you will learn to program", "COM S 100");
    }
    
               new Course(database, oldSem, "Intro to Programming III", "Yet more programming", "COM S 312");
    Course c = new Course(database, newSem, "Programming Languages",    "PL theory",            "COM S 611");
    new Student(c, jed);
  }
  
  public static void main(String[] args) {
    CMSRoot db = new CMSRoot();
    create(db);
  }
}
