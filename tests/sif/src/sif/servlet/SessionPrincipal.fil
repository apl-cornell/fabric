package sif.servlet;

import jif.lang.AbstractPrincipal;

/**
 * A session principal. This principal represents an HTTP session.
 */
public final class SessionPrincipal extends AbstractPrincipal {
    final SessionState session;

    SessionPrincipal(SessionState session) {
        this.session = session;
    }    
    void sessionInitialized() {
        super.jif$lang$AbstractPrincipal$("SIF Session #" + session.getSessionId());        
    }
}
