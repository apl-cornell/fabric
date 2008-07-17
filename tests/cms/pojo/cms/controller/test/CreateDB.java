package cms.controller.test;

import java.util.Iterator;

import cms.model.CMSRoot;
import cms.model.Semester;

public class CreateDB {
  public void create(CMSRoot database) {
    // create semesters    
    Semester oldSem = database.addSemester("Spring 2008");
    Semester newSem = database.addSemester("Fall 2008");
    Semester curSem = database.addSemester("Summer 2008");
    newSem.setHidden(true);
    database.setCurrentSemester(curSem);
    
    // create courses
    Iterator sems = database.getAllSemesters().iterator();
    while(sems.hasNext()) {
      Semester next = (Semester) sems.next();
      next.addCourse("Intro to Programming II", "In this course you will program a lot", "COM S 211");
      next.addCourse("Intro to Programming", "In this course you will learn to program", "COM S 100");
    }
    oldSem.addCourse("Intro to Programming III", "Yet more programming", "COM S 312");
    newSem.addCourse("Programming Languages", "PL theory", "COM S 611");
    
    // create users
    database.addUser("mg");
    database.addUser("am");
    database.addUser("jl");
    database.addUser("kv");
    database.addUser("xq");
  }
}
