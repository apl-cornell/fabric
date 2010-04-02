package sif.servlet;

import java.io.*;
import java.util.*;
import fabric.util.Collections;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fabric.worker.Worker;
import fabric.worker.Store;

import sif.html.*;

import fabric.lang.Principal;
import fabric.lang.security.Label;
import fabric.lang.security.PrincipalUtil;
import fabric.lang.security.LabelUtil;
import jif.lang.JifObject;
import jif.lang.JifString;

/** A servlet contains the information that is shared across users and sessions.
 * It converts between the Java HttpServlet request processing style and this one. */

abstract public class Servlet extends HttpServlet {

    private ThreadLocal nonce;
    private Map<String, Action> startActions;

    private final Principal servletP;

    static final int DEBUG_LEVEL = -1;
    public static final PrintStream DEBUG = System.err;
    
    static {
      try {
        Worker.initialize("gaia.systems.cs.cornell.edu");
      } catch(IllegalStateException e) {
        // TODO: need to fix this up
        // do nothing, worker already initialized.
      } catch (final Exception e) {
        System.out.println("Fabric: Worker unable to initialize");
      }      
    }


    protected Servlet(Principal servletP) {
        this.servletP = servletP;
    }

    public final void init(ServletConfig sc) throws ServletException {
        super.init(sc);
        nonce = new ThreadLocal() {
            protected Object initialValue() {
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
        throw new ServletException("Jif subclasses of Servlet must have " +
            "a default constructor, i.e., a constructor " +
        "with no formal arguments.");
    }

    public abstract void initialize() throws ServletException;

    public final void createPage(Request req, String title, Label cL, Label cE, Node body) {
        String style_sheet = getInitParameter("style_sheet_url");
        style_sheet = relURLToAbsURL(style_sheet, req);
        String scriptFile = getInitParameter("javascript_url");
        scriptFile = relURLToAbsURL(scriptFile, req);
        
        String colorCoding = getInitParameter("color_coding");
        String colorCodingFile;
        if(colorCoding!=null && colorCoding.equals("true")) {
        	colorCodingFile = relURLToAbsURL("../preamble.js", req);
        } else {
        	colorCodingFile = null;
        }

        Page p = new Page(cL, cE, cL, cE, new Head(cL, cE, title, style_sheet, scriptFile, colorCodingFile),
            new Body(cL, cE, cL, cE, body));

        req.setReturnPage(p);
    }

    public final void createPage(Request req, String title, Label preL, Label preE, 
        PrecomputedPage pp, Label dynL, Label dynE, NodeList args) {

        String style_sheet = getInitParameter("style_sheet_url");
        style_sheet = relURLToAbsURL(style_sheet, req);
        String scriptFile = getInitParameter("javascript_url");
        scriptFile = relURLToAbsURL(scriptFile, req);
        String colorCoding = getInitParameter("color_coding");
        String colorCodingFile;
        if(colorCoding!=null && colorCoding.equals("true")) {
        	colorCodingFile = relURLToAbsURL("../preamble.js", req);
        } else {
        	colorCodingFile = null;
        }

        req.setReturnPage(pp, args, new Head(dynL, dynE, title, style_sheet, scriptFile, colorCodingFile));
    }

    private static String relURLToAbsURL(String url, Request req) {
        if (url != null && url.indexOf("://") < 0) {
            // url isn't the full path. Prefix it with
            // the context URL
            String contextURL = req.contextURL();
            if (!contextURL.endsWith("/")) {
                contextURL += "/";
            }
            url =  contextURL + url;
        }
        return url;

    }
    public final Node createForm(Label L, Label E, Action action, Label cL, Label cE, Node body) {
        return new Form(servletP, L, E, action, cL, cE, body);
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

    public final void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        long time_start = System.currentTimeMillis();
        long time_page = 0;
//        long label_time = LabelUtil.singleton().getAndClearTime();
//        int count = LabelUtil.singleton().getAndClearCount(), topCount = LabelUtil.singleton().getAndClearTopCount();

//        if(label_time != 0 || count != 0 || topCount != 0) {
//            System.err.println("SIF: Something is seriously wrong");
//        }

//      increment_req_cnt();

        PrintWriter rw = response.getWriter();
        try {
            response.setContentType("text/html");
            if (request.getCharacterEncoding() == null)
                request.setCharacterEncoding("ISO-8859-1");						

            Request req = new Request(this, request);
//          hw = new StandardHTMLWriter(req, rw);
//          Node loadMsg = checkLoad(req);
//          if (loadMsg != null) {
//          loadMsg.write(hw, null, LabelUtil.singleton().noComponents());
//          return;
//          }


            producePage(req);

            if (debug(1)) {
                DEBUG.println("*** writing return page");
            }
            
//            label_time = LabelUtil.singleton().getAndClearTime();
//            count = LabelUtil.singleton().getAndClearCount();
//            topCount = LabelUtil.singleton().getAndClearTopCount();
            time_page = System.currentTimeMillis();

            String colorCoding = getInitParameter("color_coding");
            req.writeReturnPage(rw, colorCoding);
            if (debug(1)) {
                DEBUG.println("*** available actions are: " + getSessionActions(req.request));                

            }
        }
        finally { 
            if (rw != null) rw.close();
            // decrement_req_cnt(); 
            if (debug(1)) {
                DEBUG.println("*** finished doGet");
                long time_end = System.currentTimeMillis();

                DEBUG.println(" Total " + (time_end - time_start) + "  JSF_and_Action  " + (time_page - time_start) + " HTML_Rendering " + (time_end - time_page));
            }
//            long time_end = System.currentTimeMillis();
//            DEBUG.println("SIF: " + (time_end - time_start) + " " + (time_page - time_start) + " " + (time_end - time_page));
//            DEBUG.println("LAB: " + label_time + " " + count + " " + topCount + ", " + LabelUtil.singleton().getAndClearTime() + " " + LabelUtil.singleton().getAndClearCount() + " " + LabelUtil.singleton().getAndClearTopCount());
        }
    }

    private final void producePage(Request req) throws IOException, ServletException {        
        LabeledAction laction = null;
        //            if (false) { // debugging: dump parameter names
        //                String params = "";
        //                for (Enumeration i = request.getParameterNames(); i.hasMoreElements();) {
        //                    String name = (String)i.nextElement();
        //                    params += name;
        //                    params += "=";
        //                    params += request.getParameter(name);
        //                    params += "\n";
        //                }
        //                
        //                createPage("params", new Pre(new Text(params))).write(hw);
        //            }
        //            if (false) { // debugging: dump form data
        //                String s = "";
        //                BufferedReader in = request.getReader();
        //                while (true) {
        //                    String ln = in.readLine();
        //                    if (ln == null) break;
        //                    s += ln;
        //                    s += '\n';
        //                }
        //                createPage("request", new Pre(new Text(s))).write(hw);
        //            }	

        String action_name = req.action_name();


        if (action_name != null) {
            laction = findAction(req, action_name);                
        }
        else {
            Action a = this.findDefaultAction(req);
            if (a != null) {
                if(req.isParamEmpty()) {
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
                DEBUG.println("*** invoking action " + laction.a.getName() + " with label " + laction.L);
            }
            if (debug(2)) {
                // time the invocation of the action
                DEBUG.print(laction.a.getName() + " " + laction.a.getClass()+ " ");
//                long clear = LabelUtil._Impl.getAndClearTime();
//                long time_start = System.currentTimeMillis();
                laction.a.invoke(laction.L, req);
//                long time_end = System.currentTimeMillis();
//                clear = LabelUtil._Impl.getAndClearTime();
//                DEBUG.print("Action " + (time_end - time_start) + " Dynamic_Security " + clear + " ");
            }
            else {
                laction.a.invoke(laction.L, req);
            }

        } 
        catch (ServletException se) {
            reportError(req.bnd, req, "Servlet exception", "Failure: Servlet Exception",
                "An unexpected error occurred during servlet processing: " +
                se.getRootCause() == null ? "" : se.toString(),
                    se.getRootCause() == null ? se : se.getRootCause());
        } 
        catch (Throwable t) {
            reportError(req.bnd, req, "Servlet exception", "Failure: Servlet Exception",
                "An unexpected exception occurred during servlet processing: ",
                t);
        }

        // TODO KV: Replace the null label with something more sensible
        if (!req.returnPageSet()) {
            reportError(sessionPrincipalLabel(req.session), req,  
                "Error handling request", "Error Handling Request",
                "The servlet did not generate any output for your request. " +
            "This probably means that your request was ill-formed.");

        }            
    }


    Label trustedBySessionLabel(final Request req) {
      return Worker.runInSubTransaction(new fabric.worker.Worker.Code<Label>() {
        public Label run() {
          Store local = Worker.getWorker().getLocalStore();
          return LabelUtil._Impl.writerPolicyLabel(local, req.session, req.session);        
        }
      });
    }

    /**
     * @throws ServletException
     */
    protected void invalidActionRequested(Request req, String action_name) throws ServletException {
        if (action_name == null) {
          // TODO KV: the null param is a hack
            reportError(sessionPrincipalLabel(req.session), req, "Access violation", "Improper request",
            "The request includes no action identifier.");
        }
        reportError(sessionPrincipalLabel(req.session), req, "Access violation", "Invalid Action",
            "The action identifier in the request is invalid: <" + action_name + ">");    
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
    private Map<String, LabeledAction> getSessionActions(HttpServletRequest request) {
        Map<String, LabeledAction> sessionActions = (Map<String, LabeledAction>)request.getSession(true).getAttribute("session_actions");
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
    protected void reportError(Label lbl, Request req, String title, String header, String explanation) 
    throws ServletException {
        reportError(lbl, req, title, header, explanation, null);
    }

    protected void reportError(Label lbl, Request req, String title, String header, String explanation, Throwable t) 
    throws ServletException {
        Node stackTrace;
        if (t != null) {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            PrintWriter pw = new PrintWriter(bs);
            t.printStackTrace(pw);
            pw.flush();
            stackTrace = new Pre(lbl, lbl, lbl, lbl, new Text(lbl, lbl, new String(bs.toByteArray()).replaceAll("\r\n", "\n")));
        } else {
            stackTrace = new Text(lbl, lbl, "");
        }
        Node content = new NodeList(lbl, lbl, lbl, lbl, new Paragraph(lbl, lbl, lbl, lbl, new Text(lbl, lbl, explanation)),
            stackTrace);

        Label none = LabelUtil._Impl.noComponents();
        Action a = findDefaultAction(req);
        if (a != null) {
            String url = req.contextURL();
            content = new NodeList(none, none, none, none, content, 
                new Paragraph(none, none, none, none, 
                    new Hyperlink(servletP, none, none, url, "Return")));
        }
        createPage(req, title, none, none, 
            new NodeList(none, none, none, none, new Header(lbl, lbl, 1, header),
                content));
    }

    public final Action findStartAction(String action_name) {
        if (startActions.containsKey(action_name)) {
            return startActions.get(action_name);
        }
        return null;        
    }
    public final Action findStartAction(Request req, String action_name) {
        return findStartAction(action_name);
    }

    public final Action findDefaultAction(Request req) {
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
    public final String getInitParameter(String name) {
        return super.getInitParameter(name);
    }

    /** These servlets currently do not distinguish between GET and POST requests. */
    public final void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException {
        doGet(req, res);	
    }

    public abstract String getPrivateHostID() throws ServletException;

    public final String addAction(Action a, Label L, Request req) {
        LabeledAction la = new LabeledAction(a, L);
        String name = generateNonce();
        getSessionActions(req.request).put(name, la);
        return name;
    }
    public final void addStartAction(Action a) {
        startActions.put(a.getExtName(), a);
    }

    public final Name generateName() {
        return ((Nonce)nonce.get()).generate();
    }
    public final String generateNonce() {
        return ((Nonce)nonce.get()).generate().toHex();
    }

    /** Construct a node that contains an invocation of this servlet with the
     * named request and the inputs provided.
     * @param inputs : (Input or JifString of Input name)-> String
     * @param req : the request that initiated this
     * @return a new node.
     */
    public final Node createRequest(Label L, Label E, Action a, jif.util.Map inputs, Label cL, Label cE, Node body) {        
        return new HyperlinkRequest(servletP, L, E, a, inputs, cL, cE, body);
    }

    public String createRequestURL(String actionName, jif.util.Map inputs, Request req) {
        StringWriter w = new StringWriter();
        w.write(req.servletURL());
        w.write("?action=");
        w.write(HTMLWriter.escape_URI(actionName));

        if (inputs != null)
            for (jif.util.Iterator i = inputs.keySet().iterator(); i.hasNext();) {
                JifObject key = i.next();
                if (key instanceof JifString) {
                    JifString jkey = (JifString)key;
                    String inputName = jkey.toString();
                    JifString jval = (JifString)inputs.get(jkey);
                    String val = jval.toString();
                    w.write("&");
                    w.write(inputName);
                    w.write("=");
                    w.write(HTMLWriter.escape_URI(val));
                }
            }
        return w.toString();
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


    private Label sessionPrincipalLabel(final SessionPrincipal pp) {
      return Worker.runInSubTransaction(new fabric.worker.Worker.Code<Label>() {
        public Label run() {
          Store local = Worker.getWorker().getLocalStore();
          return LabelUtil._Impl.readerPolicyLabel(local, pp, pp);
        }
      });
    }

    protected SessionState createSessionState(fabric.lang.security.Label lbl, String id, sif.servlet.SessionPrincipal prin) {
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
        public Label run() {
          Store local = Worker.getWorker().getLocalStore();
          return LabelUtil._Impl.toLabel(local, PrincipalUtil._Impl.readableByPrinPolicy(local,session));
        }
      });
        //return LabelUtil.privacyPolicyLabel(session, Collections.EMPTY_LIST);
    }

    public static final boolean debug(int i) {
        return i <= DEBUG_LEVEL;
    }

    static class LabeledAction /*implements java.io.Serializable*/ { // XXX Labels are not seriablizable
        LabeledAction(Action a, Label L) {
            this.a = a; this.L = L;
        }
        public String toString() {
            return "(" + a + "; " + L + ")";
        }
        public final Action a;
        public final Label L;
        public boolean equals(Object o) {
            if (o instanceof LabeledAction) {
                LabeledAction that = (LabeledAction)o;
                return that.a == a && that.L == L;
            }
            return false;
        }
        public int hashCode() {
            return (a == null?0:a.hashCode()) ^ (L == null?0:L.hashCode()); 
        }
    }
}
