package cms.controller.www;

import javax.servlet.ServletRequest;

import org.w3c.dom.Document;

import cms.auth.Principal;

public abstract class Action {
  
  public static final String P_ACTION = "action";
  
  protected ServletRequest request;
  
  public Action(ServletRequest request) {
    this.request = request;
  }
  
  public abstract void     parse(ServletRequest request) throws ActionException;
  public abstract boolean  checkPermission(Principal p);
  public abstract void     executeImpl() throws ActionException;
  public abstract String   redirectTo();
  public abstract Document data();
}
