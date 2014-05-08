package sif.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sif.html.Body;
import sif.html.Form;
import sif.html.Head;
import sif.html.Header;
import sif.html.Hyperlink;
import sif.html.HyperlinkRequest;
import sif.html.Input;
import sif.html.Node;
import sif.html.NodeList;
import sif.html.OffsiteForm;
import sif.html.Page;
import sif.html.Paragraph;
import sif.html.Pre;
import sif.html.PrecomputedPage;
import sif.html.Text;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;
import fabric.lang.security.Principal;
import fabric.worker.Store;
import fabric.worker.Worker;

//import fabricated.util.Collections;

/** A servlet contains the information that is shared across users and sessions.
 * It converts between the Java HttpServlet request processing style and this one. */

abstract public class HTMLServlet extends Servlet {
  protected HTMLServlet(Principal servletP) {
    super(servletP);
  }

  public final void createPage(HTMLRequest req, String title, Label cL,
      Label cE, Node body) {
    String style_sheet = getInitParameter("style_sheet_url");
    style_sheet = relURLToAbsURL(style_sheet, req);
    String scriptFile = getInitParameter("javascript_url");
    scriptFile = relURLToAbsURL(scriptFile, req);

    String colorCoding = getInitParameter("color_coding");
    String colorCodingFile;
    if (colorCoding != null && colorCoding.equals("true")) {
      colorCodingFile = relURLToAbsURL("../preamble.js", req);
    } else {
      colorCodingFile = null;
    }

    Page p =
        new Page(cL, cE, cL, cE, new Head(cL, cE, title, style_sheet,
            scriptFile, colorCodingFile), new Body(cL, cE, cL, cE, body));

    req.setReturnPage(p);
  }

  public final void createPage(HTMLRequest req, String title, Label preL,
      Label preE, PrecomputedPage pp, Label dynL, Label dynE, NodeList args) {

    String style_sheet = getInitParameter("style_sheet_url");
    style_sheet = relURLToAbsURL(style_sheet, req);
    String scriptFile = getInitParameter("javascript_url");
    scriptFile = relURLToAbsURL(scriptFile, req);
    String colorCoding = getInitParameter("color_coding");
    String colorCodingFile;
    if (colorCoding != null && colorCoding.equals("true")) {
      colorCodingFile = relURLToAbsURL("../preamble.js", req);
    } else {
      colorCodingFile = null;
    }

    req.setReturnPage(pp, args, new Head(dynL, dynE, title, style_sheet,
        scriptFile, colorCodingFile));
  }

  private static String relURLToAbsURL(String url, Request req) {
    if (url != null && url.indexOf("://") < 0) {
      // url isn't the full path. Prefix it with
      // the context URL
      String contextURL = req.contextURL();
      if (!contextURL.endsWith("/")) {
        contextURL += "/";
      }
      url = contextURL + url;
    }
    return url;

  }

  public final Node createForm(Label L, Label E, Action action, Label cL,
      Label cE, Node body) {
    return new Form(servletP, L, E, action, cL, cE, body);
  }

  public final Node createOffsiteForm(Label L, Label E, String action,
      boolean useGetMethod, Label cL, Label cE, Node body) {
    return new OffsiteForm(servletP, L, E, action, useGetMethod, cL, cE, body);
  }

  /** checkLoad implements load checking for the servlet. This
   * behavior is supplied by the servlet implementation. A typical
   * implementation would be to compare concurrentRequests() against some
   * specified maximum.
   * @return null if load is acceptable, or else the page to be returned as an
   * error message.
   */
  protected Node checkLoad(Request req) throws ServletException {
    return null;
  }

  @Override
  public final void doGet(HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
    long time_start = System.currentTimeMillis();
    long time_page = 0;

    PrintWriter rw = response.getWriter();
    try {
      response.setContentType("text/html");
      if (request.getCharacterEncoding() == null)
        request.setCharacterEncoding("ISO-8859-1");

      HTMLRequest req = new HTMLRequest(this, request, response);

      producePage(req);

      if (debug(1)) {
        DEBUG.println("*** writing return page");
      }

      time_page = System.currentTimeMillis();

      String colorCoding = getInitParameter("color_coding");
      req.writeReturnPage(rw, colorCoding);
      if (debug(1)) {
        DEBUG.println("*** available actions are: "
            + getSessionActions(req.request));

      }
    } finally {
      if (rw != null) rw.close();
      if (debug(1)) {
        DEBUG.println("*** finished doGet");
        long time_end = System.currentTimeMillis();

        DEBUG.println(" Total " + (time_end - time_start)
            + "  JSF_and_Action  " + (time_page - time_start)
            + " HTML_Rendering " + (time_end - time_page));
      }
    }
  }

  private final void producePage(final HTMLRequest req) throws IOException,
      ServletException {
    LabeledAction laction = null;

    String action_name = req.action_name();

    if (action_name != null) {
      laction = findAction(req, action_name);
    } else {
      Action a = this.findDefaultAction(req);
      if (a != null) {
        if (req.isParamEmpty()) {
          // assuming that this is the first of a series of requests from the user, so there is no history
          // and is trusted to have been sent by the session principal and not modified by anyone else
          // ideally this might have to be 'met' with {servP!:} to ensure that servP believes so
          laction = new LabeledAction(a, trustedBySessionLabel(req));
        } else {
          laction = new LabeledAction(a, LabelUtil._Impl.noComponents());
        }
      }
    }
    if (laction == null) {
      // no valid action specified, and no default action.
      invalidActionRequested(req, action_name);
      return;
    }

    // now action is not null
    // clear the session actions
    getSessionActions(req.request).clear();

    try {
      if (debug(1)) {
        DEBUG.println("*** invoking action " + laction.a.getName()
            + " with label " + laction.L);
      }
      if (debug(2)) {
        // time the invocation of the action
        DEBUG.print(laction.a.getName() + " " + laction.a.getClass() + " ");
//                long clear = LabelUtil._Impl.getAndClearTime();
//                long time_start = System.currentTimeMillis();
        laction.a.invoke(laction.L, req);
//                long time_end = System.currentTimeMillis();
//                clear = LabelUtil._Impl.getAndClearTime();
//                DEBUG.print("Action " + (time_end - time_start) + " Dynamic_Security " + clear + " ");
      } else {
        final LabeledAction laction_ = laction;
        Worker.runInSubTransaction(new Worker.Code<Void>() {
          @Override
          public Void run() throws ServletException {
            laction_.a.invoke(laction_.L, req);
            return null;
          }
        });
      }

    } catch (ServletException se) {
      reportError(
          req.bnd,
          req,
          "Servlet exception",
          "Failure: Servlet Exception",
          "An unexpected error occurred during servlet processing: "
              + se.getRootCause() == null ? "" : se.toString(),
          se.getRootCause() == null ? se : se.getRootCause());
    } catch (Throwable t) {
      reportError(req.bnd, req, "Servlet exception",
          "Failure: Servlet Exception",
          "An unexpected exception occurred during servlet processing: ", t);
    }

    // TODO KV: Replace the null label with something more sensible
    if (!req.returnPageSet()) {
      reportError(sessionPrincipalLabel(req.session), req,
          "Error handling request", "Error Handling Request",
          "The servlet did not generate any output for your request. "
              + "This probably means that your request was ill-formed.");

    }
  }

  @Override
  Label trustedBySessionLabel(final Request req) {
    return Worker.runInSubTransaction(new fabric.worker.Worker.Code<Label>() {
      @Override
      public Label run() {
        Store local = Worker.getWorker().getLocalStore();
        return LabelUtil._Impl.writerPolicyLabel(local, req.session,
            req.session);
      }
    });
  }

  @Override
  Label trustedBySessionLabel(final SessionPrincipal session) {
    return Worker.runInSubTransaction(new fabric.worker.Worker.Code<Label>() {
      @Override
      public Label run() {
        Store local = Worker.getWorker().getLocalStore();
        return LabelUtil._Impl.writerPolicyLabel(local, session, session);
      }
    });
  }

  /**
   * @throws ServletException
   */
  @Override
  protected void invalidActionRequested(Request req, String action_name)
      throws ServletException {
    if (action_name == null) {
      // TODO KV: the null param is a hack
      reportError(sessionPrincipalLabel(req.session), req, "Access violation",
          "Improper request", "The request includes no action identifier.");
    }
    reportError(sessionPrincipalLabel(req.session), req, "Access violation",
        "Invalid Action", "The action identifier in the request is invalid: <"
            + action_name + ">");
  }

  /**
   * Given an action name, find the appropriate action.
   * @param req
   * @param action_name
   * @return the action for the given action name
   */
  private LabeledAction findAction(Request req, String action_name) {
    // first look in the session-specific actions, then the start actions
    Map<String, LabeledAction> sessionActions = getSessionActions(req.request);
    if (sessionActions.containsKey(action_name)) {
      return sessionActions.get(action_name);

    }
    if (startActions.containsKey(action_name)) {
      Action a = startActions.get(action_name);
      return new LabeledAction(a, trustedBySessionLabel(req));
    }
    return null;
  }

  /**
   * Get the map of session actions. It is a map from nonce strings to
   * LabeledAction objects.
   * @param request
   * @return the map of session actions
   */
  private Map<String, LabeledAction> getSessionActions(
      HttpServletRequest request) {
    Map<String, LabeledAction> sessionActions =
        (Map<String, LabeledAction>) request.getSession(true).getAttribute(
            "session_actions");
    if (sessionActions == null) {
      sessionActions = new HashMap<String, LabeledAction>();
      request.getSession(true).setAttribute("session_actions", sessionActions);
    }

    return sessionActions;
  }

  /**
   * If the request does not specify an action name, and there is no default
   * action name, this method is called to get a default action to invoke. May return
   * null if there is no default action to invoke.
   * 
   * @param req
   * @return the default action, used if the request does not specify an
   *         action.
   */
  @Override
  protected Action defaultAction(Request req) {
    return null;
  }

  /**
   * If the request does not specify an action name, 
   * this method is called to get a default action name. May return
   * null if there is no default action name.
   * 
   * @param req
   * @return the default action name, used if the request does not specify an
   *         action.
   */
  @Override
  protected String defaultActionName(Request req) {
    return null;
  }

  @Override
  protected void reportError(Label lbl, Request req, String title,
      String header, String explanation) throws ServletException {
    reportError(lbl, req, title, header, explanation, null);
  }

  @Override
  protected void reportError(Label lbl, Request req, String title,
      String header, String explanation, Throwable t) throws ServletException {
    Node stackTrace;
    if (t != null) {
      ByteArrayOutputStream bs = new ByteArrayOutputStream();
      PrintWriter pw = new PrintWriter(bs);
      t.printStackTrace(pw);
      pw.flush();
      stackTrace =
          new Pre(lbl, lbl, lbl, lbl, new Text(lbl, lbl, new String(
              bs.toByteArray()).replaceAll("\r\n", "\n")));
    } else {
      stackTrace = new Text(lbl, lbl, "");
    }
    Node content =
        new NodeList(lbl, lbl, lbl, lbl, new Paragraph(lbl, lbl, lbl, lbl,
            new Text(lbl, lbl, explanation)), stackTrace);

    Label none = LabelUtil._Impl.noComponents();
    Action a = findDefaultAction(req);
    if (a != null) {
      String url = req.contextURL();
      content =
          new NodeList(none, none, none, none, content, new Paragraph(none,
              none, none, none, new Hyperlink(servletP, none, none, url,
                  "Return")));
    }
    createPage((HTMLRequest) req, title, none, none, new NodeList(none, none,
        none, none, new Header(lbl, lbl, 1, header), content));
  }

  /** Construct a node that contains an invocation of this servlet with the
   * named request and the inputs provided.
   * @param inputs : (Input or string of Input name)-> String
   * @param req : the request that initiated this
   * @return a new node.
   */
  public final Node createRequest(Label L, Label E, Action a,
      fabricated.util.Map inputs, Label cL, Label cE, Node body) {
    return new HyperlinkRequest(servletP, L, E, a, inputs, cL, cE, body);
  }

  /** The set of (non-nonce generated) input names used so far. Each 
   * such input must have
   * a unique name, ensuring that the program doesn't receive
   * data on one input that was intended for another input with
   * a different label. 
   */
  private Set<String> inputNames = new HashSet<String>();

  public void addNamedInput(Input input) {
    String name = input.getName();
    if (inputNames.contains(name))
      throw new Error("Duplicate input name: " + name);
    inputNames.add(name);
  }
}
