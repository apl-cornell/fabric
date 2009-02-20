package sif.servlet;

/**
 * Global state for a single session.
 */

public class SessionState {
    protected SessionState() {
        sessionPrincipal = new SessionPrincipal(this);
    }
    
    /**
     * Principal representing this session.
     */
    public final SessionPrincipal sessionPrincipal;
    /**
     * String that uniquely identifies this session. 
     */
    private String sessionId;
        
    void setSessionId(String id) {
        this.sessionId = id;
    }
    public final String getSessionId() {
        return this.sessionId;
    }
}

