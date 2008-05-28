package cms.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import cms.www.TransactionResult;

public class Transactions {
  public Transactions(CMSRoot database) {
    // TODO
  }

  public boolean acceptInvitation(User user, Group group) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean addCMSAdmin(User p, User admin) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean createCourse(User p, String courseCode, String courseName) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean addSiteNotice(User p, String text, User author, Date exp, boolean hidden) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean editSiteNotice(User p, SiteNotice id, String text, Date exp, boolean hidden, boolean deleted) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean addRegradeRequest(User p, Group group, Collection subProblems, String requestText) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean assignGrader(User p, Assignment assign, SubProblem subProblem, User grader, Collection groups) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean cancelInvitation(User p, User canceled, Group group) {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean changeGroupSlot(User p, Group group, Assignment assign, TimeSlot slotNum, boolean addGroup) {
    // TODO Auto-generated method stub
    return false;
  }

  public TransactionResult commitFinalGradesFile(User p, Course course, List table) {
    // TODO Auto-generated method stub
    return null;
  }
}
