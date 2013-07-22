package sif.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import sif.html.Head;
import sif.html.Input;
import sif.html.InputNode;
import sif.html.NodeList;
import sif.html.Page;
import sif.html.PrecomputedPage;
import fabric.lang.security.Label;

/**
 * A Request represents an (HTTP) request from
 * the client. There are two kinds of requests:
 * initial requests that start a new session,
 * and requests that are part of an existing session.
 */
public class HTMLRequest extends Request {
  private boolean returnPageSet = false;

  // if returnPage is not null, it is used in preference to a precomputed page.
  private Page returnPage;

  private PrecomputedPage precomputedPage;
  private NodeList precomputedPageArgs;
  private Head precomputedPageHead;

  HTMLRequest(Servlet srv, HttpServletRequest req, HttpServletResponse resp) {
    super(srv, req, resp);
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
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;

  }

  public InputStream getFile(Label lbl, InputNode n) {
    return getFile(n.input);
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
    } else if (precomputedPage != null) {
      precomputedPage.write(this, w, precomputedPageArgs, precomputedPageHead);
    }
  }

  public boolean returnPageSet() {
    return returnPageSet;
  }
}
