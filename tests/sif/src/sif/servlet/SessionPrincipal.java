package sif.servlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jif.lang.AbstractPrincipal;
import jif.lang.ActsForProof;
import jif.lang.Closure;
import jif.lang.DelegatesProof;
import jif.lang.Label;
import jif.lang.Principal;
import jif.lang.PrincipalUtil;
import jif.lang.TransitiveProof;

/**
 * A session principal. This principal represents a HTTP session.
 */
public final class SessionPrincipal implements Principal {
    final SessionState session;
    private String name;
    
    // SessionPrincipal will never delegate authority to any other principal
    // So, its a lot simpler than regular principals

    SessionPrincipal(SessionState session) {
        this.session = session;
    }
    
    void sessionInitialized() {
        jif$lang$AbstractPrincipal$("SIF Session #" + session.getSessionId());        
    }

    public SessionPrincipal() { super(); }
    
    private void jif$init() {  }
    
    protected SessionPrincipal jif$lang$AbstractPrincipal$(final String name) {
        this.jif$init();
        { this.name = name; }
        return this;
    }

    protected SessionPrincipal(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public boolean delegatesTo(Principal p) {
      // SessionPrincipal never delegates authority to any other principal
      return false;
    }

    public void addDelegatesTo(Principal p) {
      // This method would never be called
    }
    
    public void removeDelegatesTo(Principal p) {
      // This method would never be called      
    }

    public boolean isAuthorized(Object authPrf, 
            Closure closure,
            Label lb,
            boolean executeNow) {
        // The default is that this principal authorizes no closures.
        return false;
    }


    public ActsForProof findProofDownto(Principal q, Object searchState) {    
        // don't even try! We don't have any information
        // about who we can act for.
      // Fabric Update: Actually the session principal acts for some particular
      // user, but we don't quite know which principal that is.
        return null;
    }

    public ActsForProof findProofUpto(Principal p, Object searchState) {
      // No principal ever acts for SessionPrincipal
        return null;        
    }

    public int hashCode() {
        return this.name == null ? 0 : this.name.hashCode();
    }
    
    public boolean equals(Object o) {
        if (o instanceof Principal) {
            Principal p = (Principal)o;
            return (this.name == p.name() || (this.name != null && 
                    this.name.equals(p.name()))) &&
                    this.getClass() == p.getClass();        
        }
        return false;
    }

    public boolean equals(Principal p) {
        if (p == null) return false;
        return (this.name == p.name() || (this.name != null && 
                this.name.equals(p.name()))) &&
                this.getClass() == p.getClass();        
    }
    
}
