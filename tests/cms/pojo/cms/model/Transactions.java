package cms.model;

import java.util.Date;

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
}
