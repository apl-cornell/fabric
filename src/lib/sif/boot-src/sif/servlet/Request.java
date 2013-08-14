package sif.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import fabric.lang.security.*;

/**
 * A Request represents an (HTTP) request from
 * the client. There are two kinds of requests:
 * initial requests that start a new session,
 * and requests that are part of an existing session.
 */
public class Request {
  	public final static int HTTP_OK = 200;
  	public final static int HTTP_FORBIDDEN = 403;

  	public Servlet servlet;
  	public SessionPrincipal session;

    public native String contextURL();
    public native String getParam(Label lbl, String name);

    public native SessionState getSessionState(Label lbl);
    public native void invalidateSession();
    public native PrintWriter getResponseWriter() throws IOException;
    public native void setResponseStatus(int code);
}
