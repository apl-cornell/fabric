package jif.lang;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * This is an abstract implementation of the principal interface.
 * It provides convenience methods for delgating authority to superiors.
 * This class assumes that principals are equal if they are the same class
 * with the same name.
 */
public abstract class AbstractPrincipal implements Principal {
    private String name;
    private static Principal NULL_PRINCIPAL = new AbstractPrincipal("NULL PRINCIPAL") { 
        public boolean equals(Object o) { return this == o; }
    };

    private Map<Principal, Principal> superiors = new ConcurrentHashMap<Principal, Principal>(); // treat this like a set

    public AbstractPrincipal() { super(); }
    
    private void jif$init() {  }
    
    protected AbstractPrincipal jif$lang$AbstractPrincipal$(final String name) {
        this.jif$init();
        { this.name = name; }
        return this;
    }

    protected AbstractPrincipal(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public boolean delegatesTo(Principal p) {
        return superiorsContains((Principal)p);
    }

    public void addDelegatesTo(Principal p) {
        if (p == null) p = NULL_PRINCIPAL;
        if (this.superiors.put(p, this) == null) {
            PrincipalUtil.notifyNewDelegation(this, p);
        }
    }
    public void removeDelegatesTo(Principal p) {
        if (p == null) p = NULL_PRINCIPAL;
        if (this.superiors.remove(p) != null) {
            PrincipalUtil.notifyRevokeDelegation(this, p);            
        }
    }

    protected boolean superiorsContains(Principal p) {
        if (p == null) p = NULL_PRINCIPAL;
        return this.superiors.containsKey(p);
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
        return null;
    }

    public ActsForProof findProofUpto(Principal p, Object searchState) {
        for (Principal s : this.superiors.keySet()) {
            ActsForProof prf = PrincipalUtil.findActsForProof(p, s, searchState);            
            if (prf != null) {
                if (PrincipalUtil.actsFor(s, this)) {
                    return new TransitiveProof(prf, s, new DelegatesProof(s, this));
                }
            }
        }
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
