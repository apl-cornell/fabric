package sif.servlet;
import fabric.lang.security.Principal;

/**
 * Global state for a single session.
 */

public class SessionState {
    public SessionPrincipal sessionPrincipal;
    public String sessionId;
    public native final String getSessionId();
    public native final SessionPrincipal sessionPrincipal();
}
