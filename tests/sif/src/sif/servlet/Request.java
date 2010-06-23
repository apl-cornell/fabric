package sif.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;
import fabric.lang.security.Principal;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sif.html.*;

/**
 * A Request represents an (HTTP) request from
 * the client. There are two kinds of requests:
 * initial requests that start a new session,
 * and requests that are part of an existing session.
 */
public final class Request {
    public final Servlet servlet;
    public final SessionPrincipal session;
    public final Label bnd; // upper bound on the output channel
    final HttpServletRequest request;
    private boolean returnPageSet = false;
    
    // if returnPage is not null, it is used in preference to a precomputed page.
    private Page returnPage;
    
    private PrecomputedPage precomputedPage;
    private NodeList precomputedPageArgs;
    private Head precomputedPageHead;
    
    private boolean isMultipart;
    private Map<String, String> parameterMap;
    private Map<String, FileItem> fileMap = Collections.EMPTY_MAP;
    
    Request(Servlet srv, HttpServletRequest req) {
        servlet = srv;
        request = req;
        session = this.getSessionState().sessionPrincipal();
        bnd = Servlet.getOutputChannelBound(this);
        
        this.isMultipart = ServletFileUpload.isMultipartContent(req);
        if (!isMultipart) {
            parameterMap = req.getParameterMap();
        }
        else {
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
                List /* FileItem */ items = upload.parseRequest(request);
                for (Iterator iter = items.iterator(); iter.hasNext();) {
                    FileItem item = (FileItem)iter.next();
                    if (item.isFormField()) {
                        parameterMap.put(item.getFieldName(), item.getString());
                    }
                    else {
                        // put the item into the fileMap
                        fileMap.put(item.getFieldName(), item);
                    }
                }
            }
            catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
    }
//    void setReturnPage(Page p) {
//        returnPage = p;
//    }
//    Page getReturnPage() {
//        return returnPage;
//    }
    boolean isParamEmpty() {
    	return parameterMap.isEmpty() && fileMap.isEmpty();
    }
    private String getParam(String name) {
        Object p = parameterMap.get(name);
        if (p == null) return null;
        if (p instanceof String)
            return (String)p;
        else if (p instanceof String[]) {
            String[] sa = (String[])p;
            if (sa.length > 0) return sa[0];
        }
        return null;
    }
    public String getParam(Input inp) {
        if (inp == null) return null;
        return getParam(inp.getName());
    }
    public String getParam(Label lbl, InputNode n) {
        return getParam(n.input);
    }
    public InputStream getFile(Input inp) {
        String name = inp.getName();
        FileItem fi = fileMap.get(name);
        if (fi != null) {
            try {
                return fi.getInputStream();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
            
    }
    public InputStream getFile(Label lbl, InputNode n) {
        return getFile(n.input);
    }
    public String action_name() {
        return getParam("action");
    }
    public String remoteAddr() {
        return request.getRemoteAddr();
    }
    public String contextURL() {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
    public String servletURL() {
        return request.getRequestURL().toString();
        // request.getRequestURL() is equivalent to the string below.
        //return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getServletPath();
    }
    
    public SessionState getSessionState() {
	SessionState result =
	  (SessionState)request.getSession(true).getAttribute("session_state");

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
    public void setReturnPage(Page p) {
        this.returnPageSet = true;
        
        this.returnPage = p;
        this.precomputedPage = null;
        this.precomputedPageArgs = null;
        this.precomputedPageHead = null;
    }
    public void setReturnPage(PrecomputedPage pp, NodeList args, Head head) {
        this.returnPageSet = true;
        
        this.returnPage = null;
        this.precomputedPage = pp;
        this.precomputedPageArgs = args;
        this.precomputedPageHead = head;
        
    }
    public void writeReturnPage(PrintWriter w, String colorCoding) {
        if (returnPage != null) {
            returnPage.write(new StandardHTMLWriter(this, w, colorCoding), null);
        }
        else if (precomputedPage != null) {
            precomputedPage.write(this, w, precomputedPageArgs, precomputedPageHead);
        }        
    }
    public boolean returnPageSet() {
        return returnPageSet;
    }
}
