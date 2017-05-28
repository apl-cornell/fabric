package cms.controller.test;

import java.util.Iterator;
import java.util.Date;
import java.util.Collection;
import java.util.Random;
import cms.model.*;

public class CreateDB {
  public static void create(CMSRoot database) {
    // Populate the database for testing.
    
    Transactions transactions = new Transactions(database);

    // create semesters
    Semester oldSem = new Semester(database, "Spring 2008");
    Semester newSem = new Semester(database, "Fall 2008");
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
      for (int i = 0; i < 3; i++) {
        Course c = new Course(database, next, "Test Class " + i, "Test", "TEST 10" + i);
       
        Staff s = new Staff(andru, c);
        s.setAdminPriv(true);
        s.setGroupsPriv(true);
        s.setGradesPriv(true);
        s.setAssignmentsPriv(true);
        s.setCategoryPriv(true);
        
        s = new Staff(mike, c);
        s.setAdminPriv(true);
        s.setGroupsPriv(true);
        s.setGradesPriv(true);
        s.setAssignmentsPriv(true);
        s.setCategoryPriv(true);
        
        if (!next.getHidden()) {
          //Create fake test data
          createFakeUsersForCourse(database, c, 20);
          createFakeAssignments(andru, c, 2, transactions);
        }
      }
    }
    
  }
  
  public static void createFakeUsersForCourse(CMSRoot database, Course course, int count) {
    count += 1;
    while(count-- > 1) {
      String id = "au" + count;
      User u = database.getUser(id);
      if (u == null)
        u = new User(database, "au" + count, "Test", "User " + count, "00000", "Eng");
      new Student(course, u);
    }
  }
  
  public static void createFakeAssignments(User creator, Course c, int count, 
      Transactions transactions) {
    float weight = Math.round(100f / count);
    count += 1;
    Date dueDate = new Date(System.currentTimeMillis() + (604800000)); //1 week
    
    while(count-- > 1) {
      Assignment assign = new Assignment(c, "Test Assignment " + count, "PS" 
          + count, dueDate);
      assign.setMaxScore(100f);
      assign.setWeight(weight);
      assign.setGroupSizeMin(1);
      assign.setGroupSizeMax(1);
      assign.setStatus(Assignment.OPEN);
      
      Iterator i= c.getStudents().iterator();
      while (i.hasNext()) {
        Student student = (Student)i.next();
        Group g= new Group(assign, 0);
        new GroupMember(g, student, GroupMember.ACTIVE);
      }
      
      //Disable below to turn off random grades
      createFakeGradesForAssignment(creator, assign);
      transactions.computeAssignmentStats(creator, assign, null);
      transactions.computeTotalScores(creator, c, null);
      
    }
  }

  public static void createFakeGradesForAssignment(User grader, Assignment assign) {
    Collection/*Group*/ groups = assign.getGroups();
    Collection/*SubProblem*/ subProblems = assign.getSubProblems();
    Random r = new Random();
    for (Iterator i = groups.iterator(); i.hasNext();) {
      Group g = (Group)i.next();
      for (Iterator mi = g.getMembers().iterator(); mi.hasNext();) {
        if (!assign.hasSubProblems()) {
          assign.addGrade(g, null, r.nextFloat() * 100, 
              ((GroupMember)mi.next()).getStudent().getUser(), grader);
        } else {
          throw new NotImplementedException();
        }
      }
    }
  }
  
  public static void main(String[] args) {
    CMSRoot db = new CMSRoot();
    create(db);
  }
}
