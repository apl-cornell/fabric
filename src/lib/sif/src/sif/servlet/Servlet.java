package sif.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;
import fabric.lang.security.Principal;
import fabric.lang.security.PrincipalUtil;
import fabric.worker.Store;
import fabric.worker.Worker;

//import fabricated.util.Collections;

/** A servlet contains the information that is shared across users and sessions.
 * It converts between the Java HttpServlet request processing style and this one. */

abstract public class Servlet extends HttpServlet {

  protected Map<String, Action> startActions;
  private ThreadLocal<Nonce> nonce;

  protected final Principal servletP;

  static final int DEBUG_LEVEL = -1;
  public static final PrintStream DEBUG = System.err;

  protected Servlet(Principal servletP) {
    this.servletP = servletP;
  }

  @Override
  public final void init(ServletConfig sc) throws ServletException {
    super.init(sc);
    nonce = new ThreadLocal<Nonce>() {
      @Override
      protected Nonce initialValue() {
        try {
          return new Nonce(Servlet.this);
        } catch (ServletException e) {
          throw new RuntimeException(e);
        }
      }
    };

    startActions = new HashMap<String, Action>();
    this.jif$invokeDefConstructor();

    initialize();
  }

  /**
   * Place holder for the method to invoke the default constructor of
   * Jif subclasses of this class.
   */
  protected void jif$invokeDefConstructor() throws ServletException {
    throw new ServletException("Jif subclasses of Servlet must have "
        + "a default constructor, i.e., a constructor "
        + "with no formal arguments.");
  }

  public abstract void initialize() throws ServletException;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    long time_start = System.currentTimeMillis();
    long time_page = 0;

    PrintWriter rw = response.getWriter();
    try {
      //response.setContentType("text/html");
      //if (request.getCharacterEncoding() == null)
      //    request.setCharacterEncoding("ISO-8859-1");

      Request req = new Request(this, request);

      handleRequest(req);

      if (debug(1)) {
        DEBUG.println("*** writing return page");
      }

      if (debug(1)) {
        time_page = System.currentTimeMillis();
      }

      //req.writeReturnPage(rw, colorCoding);
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

  private final void handleRequest(final Request req) throws IOException,
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
        DEBUG.print(laction.a.getName() + " " + laction.a.getClass() + " ");
        laction.a.invoke(laction.L, req);
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
//    if (!req.returnPageSet()) {
//      reportError(sessionPrincipalLabel(req.session), req,
//          "Error handling request", "Error Handling Request",
//          "The servlet did not generate any output for your request. "
//              + "This probably means that your request was ill-formed.");
//
//    }
  }

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
  protected String defaultActionName(Request req) {
    return null;
  }

  protected void reportError(Label lbl, Request req, String title,
      String header, String explanation) throws ServletException {
    reportError(lbl, req, title, header, explanation, null);
  }

  protected void reportError(Label lbl, Request req, String title,
      String header, String explanation, Throwable t) throws ServletException {
    if (t != null) {
      ByteArrayOutputStream bs = new ByteArrayOutputStream();
      PrintWriter pw = new PrintWriter(bs);
      //XXX: LEAK
      t.printStackTrace(pw);
      pw.flush();
    }
    // TODO: find an error action and invoke
  }

  public Action findStartAction(String action_name) {
    if (startActions.containsKey(action_name)) {
      return startActions.get(action_name);
    }
    return null;
  }

  public Action findStartAction(Request req, String action_name) {
    return findStartAction(action_name);
  }

  public Action findDefaultAction(Request req) {
    String def_action_name = this.defaultActionName(req);
    if (def_action_name == null) {
      return this.defaultAction(req);
    }
    LabeledAction la = findAction(req, def_action_name);
    if (la != null) return la.a;
    return null;
  }

  /**
   * Override to make final.
   */
  @Override
  public String getInitParameter(String name) {
    return super.getInitParameter(name);
  }

  /** These servlets currently do not distinguish between GET and POST requests. */
  @Override
  public final void doPost(HttpServletRequest req, HttpServletResponse res)
      throws IOException, ServletException {
    doGet(req, res);
  }

  public abstract String getPrivateHostID() throws ServletException;

  public String addAction(Action a, Label L, Request req) {
    LabeledAction la = new LabeledAction(a, L);
    String name = generateNonce();
    getSessionActions(req.request).put(name, la);
    return name;
  }

  public final void addStartAction(Action a) {
    startActions.put(a.getExtName(), a);
  }

  public final Name generateName() {
    return nonce.get().generate();
  }

  public final String generateNonce() {
    return nonce.get().generate().toHex();
  }

//  /** Construct a node that contains an invocation of this servlet with the
//   * named request and the inputs provided.
//   * @param inputs : (Input or string of Input name)-> String
//   * @param req : the request that initiated this
//   * @return a new node.
//   */
//  public final Node createRequest(Label L, Label E, Action a,
//      fabricated.util.Map inputs, Label cL, Label cE, Node body) {
//    return new HyperlinkRequest(servletP, L, E, a, inputs, cL, cE, body);
//  }

  public String createRequestURL(String actionName, fabricated.util.Map inputs,
      Request req) {
    StringWriter w = new StringWriter();
    w.write(req.servletURL());
    w.write("?action=");
    w.write(HTMLWriter.escape_URI(actionName));

    if (inputs != null)
      for (fabricated.util.Iterator i = inputs.entrySet().iterator(); i
          .hasNext();) {
        fabricated.util.MapEntry entry = (fabricated.util.MapEntry) i.next();
        if (entry.getKey().$unwrap() instanceof String
            && entry.getValue().$unwrap() instanceof String) {
          String inputName = (String) entry.getKey().$unwrap();
          String val = (String) entry.getValue().$unwrap();
          w.write("&");
          w.write(inputName);
          w.write("=");
          w.write(HTMLWriter.escape_URI(val));
        } else throw new Error("Expected String but got "
            + entry.getKey().getClass() + "," + entry.getValue().getClass());
      }
    return w.toString();
  }

  protected Label sessionPrincipalLabel(final SessionPrincipal pp) {
    return Worker.runInSubTransaction(new fabric.worker.Worker.Code<Label>() {
      @Override
      public Label run() {
        Store local = Worker.getWorker().getLocalStore();
        return LabelUtil._Impl.readerPolicyLabel(local, pp, pp);
      }
    });
  }

  protected SessionState createSessionState(fabric.lang.security.Label lbl,
      String id, sif.servlet.SessionPrincipal prin) {
    throw new Error("Only applications can create a session state");
  }

  protected SessionPrincipal createSessionPrincipal(String id) {
    throw new Error("Only applications can create a session principal");
  }

  /**
   * Return a Label representing the upper bound on the output channel for
   * the Request request.
   */
  public static Label getOutputChannelBound(Request request) {
    return getOutputChannelBound(request.session);
  }

  public static Label getOutputChannelBound(final Principal session) {
    return Worker.runInSubTransaction(new fabric.worker.Worker.Code<Label>() {
      @Override
      public Label run() {
        Store local = Worker.getWorker().getLocalStore();
        return LabelUtil._Impl.toLabel(local,
            PrincipalUtil._Impl.readableByPrinPolicy(local, session));
      }
    });
    //return LabelUtil.privacyPolicyLabel(session, Collections.EMPTY_LIST);
  }

  public static final boolean debug(int i) {
    return i <= DEBUG_LEVEL;
  }

  static class LabeledAction /*implements java.io.Serializable*/{ // XXX Labels are not seriablizable
    LabeledAction(Action a, Label L) {
      this.a = a;
      this.L = L;
    }

    @Override
    public String toString() {
      return "(" + a + "; " + L + ")";
    }

    public final Action a;
    public final Label L;

    @Override
    public boolean equals(Object o) {
      if (o instanceof LabeledAction) {
        LabeledAction that = (LabeledAction) o;
        return that.a == a && that.L == L;
      }
      return false;
    }

    @Override
    public int hashCode() {
      return (a == null ? 0 : a.hashCode()) ^ (L == null ? 0 : L.hashCode());
    }
  }
}
