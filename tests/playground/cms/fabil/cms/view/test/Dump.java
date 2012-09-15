package cms.view.test;

import cms.model.*;
import java.net.URI;
import fabric.util.Iterator;
import fabric.util.Map;
import fabric.util.Collection;

public class Dump {

  public void dump(CMSRoot database) {
    output("<database currentSemester=\"" + database.getCurrentSemester().toString() + "\" guestUser=\"" + database.getGuestUser().getNetID() + "\">");
    indent();

    for (Iterator i = database.getAllUsers().iterator(); i.hasNext(); )
      dump((User) i.next());

    output("");

    for (Iterator i = database.getAllSemesters().iterator(); i.hasNext(); )
      dump((Semester) i.next());

    undent();
    output("</database>");
  }

  public void dump(User u) {
    output("<user netID = \"" + u.getNetID()      + "\"");
    output("      name  = \"" + u.canonicalName() + "\"");
    output("      admin = \"" + bool(u.isCMSAdmin()) + "\"");
    output("      cuid  = \"" + u.getCUID()       + "\">");
    indent();

    Collection semesters = u.findSemesters();
    for (Iterator i = semesters.iterator(); i.hasNext(); )
      ref((Semester) i.next());

    output("");

    output("<students>");
    indent();
    for (Iterator i = semesters.iterator(); i.hasNext(); )
      for (Iterator j = u.findStudentCoursesBySemester((Semester) i.next()).iterator(); j.hasNext(); )
        output("<course id=\"" + j.next().toString() + "\"/>");
    undent();
    output("</students>");

    output("");

    output("<staffs>");
    indent();
    for (Iterator i = semesters.iterator(); i.hasNext(); )
      for (Iterator j = u.findStaffCoursesBySemester((Semester) i.next()).iterator(); j.hasNext(); )
        ref((Course) j.next());
    undent();
    output("</staffs>");

    undent();
    output("</user>");
  }

  public void dump(Semester s) {
    output("<semester id=\"" + s.toString() + "\" name=\"" + s.getName() + "\" hidden=\"" + bool(s.getHidden()) + "\">");
    indent();

    for (Iterator i = s.getCourses().iterator(); i.hasNext(); )
      dump((Course) i.next());

    output("<guestCourses>");
    indent();
    for (Iterator i = s.findGuestAccessCourses().iterator(); i.hasNext(); )
      ref((Course) i.next());
    undent();
    output("</guestCourses>");

    output("");

    output("<ccCourses>");
    indent();
    for (Iterator i = s.findCCAccessCourses().iterator(); i.hasNext(); )
      ref((Course) i.next());
    undent();
    output("</ccCourses>");

    undent();
    output("</semester>");
  }

  public void dump(Course c) {
    output("<course id       = \"" + c.toString()    + "\"");
    output("        semester = \"" + c.getSemester().toString() + "\"");
    output("        name     = \"" + c.getName()     + "\">");
    indent();

    output("<description>");
    indent();
    output(c.getDescription());
    undent();
    output("</description>");

    output("");

    output("<students>");
    indent();
    for (Iterator i = c.getStudents().iterator(); i.hasNext(); )
      dump((Student) i.next());
    undent();
    output("</students>");

    output("");

    output("<staff>");
    indent();
    for (Iterator i = c.getStaff().iterator(); i.hasNext(); )
      dump((Staff) i.next());
    undent();
    output("</staff>");

    undent();
    output("</course>");
  }

  public void dump(Student s) {
    output("<student id=\"" + s.toString() + "\" course=\"" + s.getCourse().toString() + "\" user=\"" + s.getUser().getNetID() + "\"/>");
  }

  public void dump(Staff s) {
    output("<staff   id=\"" + s.toString() + "\" course=\"" + s.getCourse().toString() + "\" user=\"" + s.getUser().getNetID() + "\"/>");
  }

  public void ref(Course c) {
    output("<course id=\"" + c.toString() + "\"/>");
  }

  public void ref(Semester s) {
    output("<semester id=\"" + s.toString() + "\"/>");
  }

  public void ref(User u) {
  }

  public String bool(boolean b) {
    return b ? "true" : "false";
  }

  //////////////////////////////////////////////////////////////////////////////
  // printing methods                                                         //
  //////////////////////////////////////////////////////////////////////////////

  private int indent = 0; 

  private void indent() {
    indent++;
  }

  private void undent() {
    indent--;
  }

  private void output(String s) {
    String pad = "";
    for (int i = 0; i < indent; i++)
      pad += "\t";

    System.out.println(pad + s.replaceAll("\n", pad + "\n"));
  }

  //////////////////////////////////////////////////////////////////////////////
  // main method                                                              //
  //////////////////////////////////////////////////////////////////////////////

  public static void main(String[] args) {
    Store local = Worker.getWorker().getLocalStore();
    Store core  = Worker.getWorker().getStore(args[0]);

    atomic {
      CMSRoot database = (CMSRoot) ((Map) core.getRoot()).get(args[1]);
      Dump    d        = new Dump@local();
      d.dump(database);
    }
  }
}
