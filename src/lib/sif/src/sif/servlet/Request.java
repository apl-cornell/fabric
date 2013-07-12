package sif.servlet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import fabric.lang.security.Label;

/**
 * A Request represents an (HTTP) request from
 * the client. There are two kinds of requests:
 * initial requests that start a new session,
 * and requests that are part of an existing session.
 */
public class Request {
  public final Servlet servlet;
  public final SessionPrincipal session;
  public final Label bnd; // upper bound on the output channel
  final HttpServletRequest request;

  private boolean isMultipart;
  private Map<String, String> parameterMap;
  protected Map<String, FileItem> fileMap = Collections.EMPTY_MAP;

  Request(Servlet srv, HttpServletRequest req) {
    servlet = srv;
    request = req;
    session = this.getSessionState(null).sessionPrincipal();
    bnd = Servlet.getOutputChannelBound(this);

    this.isMultipart = FileUploadBase.isMultipartContent(req);
    if (!isMultipart) {
      parameterMap = req.getParameterMap();
    } else {
      parameterMap = new HashMap<String, String>();
      fileMap = new HashMap<String, FileItem>();

      // Create a factory for disk-based file items
      FileItemFactory factory = new DiskFileItemFactory();

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // set the maximum upload size to 100 kB
      upload.setSizeMax(100 * 1024);

      // Parse the request
      try {
        List<FileItem> items = upload.parseRequest(request);
        for (FileItem element : items) {
          FileItem item = element;
          if (item.isFormField()) {
            parameterMap.put(item.getFieldName(), item.getString());
          } else {
            // put the item into the fileMap
            fileMap.put(item.getFieldName(), item);
          }
        }
      } catch (FileUploadException e) {
        e.printStackTrace();
      }
    }
  }

  boolean isParamEmpty() {
    return parameterMap.isEmpty() && fileMap.isEmpty();
  }

  public String getParam(String name) {
    Object p = parameterMap.get(name);
    if (p == null) return null;
    if (p instanceof String)
      return (String) p;
    else if (p instanceof String[]) {
      String[] sa = (String[]) p;
      if (sa.length > 0) return sa[0];
    }
    return null;
  }

  public String action_name() {
    return getParam("action");
  }

  public String remoteAddr() {
    return request.getRemoteAddr();
  }

  public String contextURL() {
    return request.getScheme() + "://" + request.getServerName() + ":"
        + request.getServerPort() + request.getContextPath();
  }

  public String servletURL() {
    return request.getRequestURL().toString();
    // request.getRequestURL() is equivalent to the string below.
    //return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getServletPath();
  }

  // need lbl argument just for compatibility with the signature
  public SessionState getSessionState(Label lbl) {
    SessionState result =
        (SessionState) request.getSession(true).getAttribute("session_state");

    if (result == null) {
      String id = request.getSession().getId();
      SessionPrincipal sessionPrin = servlet.createSessionPrincipal(id);
      Label initLbl = servlet.trustedBySessionLabel(sessionPrin);
      result = servlet.createSessionState(initLbl, id, sessionPrin);
      request.getSession(true).setAttribute("session_state", result);
    }

    return result;
  }

//    public SessionState getSessionState() {
//      return getSessionState(LabelUtil._Impl.noComponents());
//    }

  public void invalidateSession() {
    request.getSession().invalidate();
  }
}
