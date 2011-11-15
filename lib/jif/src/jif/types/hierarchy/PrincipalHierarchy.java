package jif.types.hierarchy;

import java.util.*;

import jif.types.principal.*;

/** The principal hierarchy that defines the acts-for relationships
 *  between principals. 
 */
public class PrincipalHierarchy {
    /**
     * Map from Principal to Set[Principal], where if p actsfor p', then
     * p' is in the set actsfor.get(p)
     */
    private final Map actsfor;


    /**
     * Map from Principal to Set[Principal], where if p' actsfor p, then
     * p' is in the set actsfor.get(p)
     */
    private final Map grants;

    /**
     * Cache of results, same domain and range as actsfor.
     */
    private final Map actorCache;

    public PrincipalHierarchy() {
        this.actsfor = new HashMap();
        this.grants = new HashMap();
        this.actorCache = new HashMap();
    }

    public String toString() {
        return "[" + actsForString()+ "]";
    }

    private static void addAlreadyReported(Map alreadyReported, Principal p, Principal q) {
        // record the fact that we have already reported that q actsfor p
        Set s = (Set)alreadyReported.get(q);
        if (s == null) {
            s = new HashSet();
            alreadyReported.put(q, s);
        }
        s.add(p);
    }
    private static boolean isAlreadyReported(Map alreadyReported, Principal p, Principal q) {
        Set s = (Set)alreadyReported.get(p);        
        if (s != null) {
            return s.contains(q);
        }
        return false;
    }
    public String actsForString() {
        StringBuffer sb = new StringBuffer();
        Map alreadyReported = new HashMap();
        boolean needsComma = false;
        for (Iterator i = actsfor.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry e = (Map.Entry) i.next();
            Principal p = (Principal) e.getKey();
            Set a = (Set) e.getValue();

            for (Iterator j = a.iterator(); j.hasNext(); ) {
                Principal q = (Principal) j.next();
                if (isAlreadyReported(alreadyReported, p, q)) {
                    continue;
                }
                if (needsComma) {
                    sb.append(", ");
                }                
                sb.append("(");
                sb.append(p.toString());
                Set b = (Set)actsfor.get(q); 
                if (b != null && b.contains(p)) {
                    // q also acts for p
                    sb.append(" equiv ");
                    addAlreadyReported(alreadyReported, p, q);
                }
                else {
                    sb.append(" actsFor ");
                }
                sb.append(q.toString());
                sb.append(")");
                needsComma = true;
            }
        }
        return sb.toString();
    }

    public boolean isEmpty() {
        return actsfor.isEmpty();
    }
    public void add(Principal actor, Principal granter) {
        Set s = (Set) actsfor.get(actor);
        if (s == null) {
            // create a new set of granting principals
            s = new LinkedHashSet();
            actsfor.put(actor, s);
        }
        s.add(granter);

        Set t = (Set) grants.get(granter);
        if (t == null) {
            // create a new set of granting principals
            t = new LinkedHashSet();
            grants.put(granter, t);
        }
        t.add(actor);        
    }

    public boolean actsFor(Principal actor, Principal granter) {
        return actsFor(actor, granter, new LinkedList());
    }

    private static class PrincipalPair {
        PrincipalPair(Principal actor, Principal granter) {
            this.actor = actor;
            this.granter = granter;
        }
        final Principal actor;
        final Principal granter;
        public int hashCode() { return actor.hashCode() ^ granter.hashCode(); }
        public boolean equals(Object o) {
            if (o instanceof PrincipalPair) {
                PrincipalPair that = (PrincipalPair)o;
                return this.actor.equals(that.actor) && this.granter.equals(that.granter);
            }
            return false;
        }
    }
    protected boolean actsFor(Principal actor, Principal granter, LinkedList goalStack) {
        if (actor.isTopPrincipal()) return true;
        if (granter.isBottomPrincipal()) return true;

        Set actorCached = (Set)actorCache.get(actor);
        if (actorCached != null && actorCached.contains(granter))
            return true;

        PrincipalPair currentGoal = new PrincipalPair(actor, granter);
        if (goalStack.contains(currentGoal)) { 
            // this goal is already on the stack.
            return false;
        }

        // Check the reflexive part of actsFor relation.
        if (actor.equals(granter)) {
            return true;
        }

        Set s = (Set) actsfor.get(actor);
        if (s != null && s.contains(granter)) {
            // explicit actsfor in the hierarchy
            cacheResult(actor, granter);
            return true;
        }               

        // push this goal on the stack before making recursive calls
        goalStack.addLast(currentGoal);

        // special cases for conjunctive and disjunctive principals.
        if (actor instanceof ConjunctivePrincipal) {
            ConjunctivePrincipal cp = (ConjunctivePrincipal)actor;
            // cp actsfor granter if at least one of the conjucts act for granter
            for (Iterator iter = cp.conjuncts().iterator(); iter.hasNext();) {
                Principal p = (Principal)iter.next();
                if (actsFor(p, granter, goalStack)) {
                    cacheResult(actor, granter);
                    return true;                    
                }
            }
        }
        if (actor instanceof DisjunctivePrincipal) {
            DisjunctivePrincipal dp = (DisjunctivePrincipal)actor;
            // dp actsfor granter if all of the disjucts act for granter
            boolean all = true;
            for (Iterator iter = dp.disjuncts().iterator(); iter.hasNext();) {
                Principal p = (Principal)iter.next();
                if (!actsFor(p, granter, goalStack)) {
                    all = false; 
                    break;
                }
            }
            if (all) {
                cacheResult(actor, granter);
                return true;                                
            }
        }

        if (granter instanceof DisjunctivePrincipal) {
            DisjunctivePrincipal dp = (DisjunctivePrincipal)granter;
            // actor actsfor dp if there is one disjunct that actor can act for
            for (Iterator iter = dp.disjuncts().iterator(); iter.hasNext();) {
                Principal p = (Principal)iter.next();
                if (actsFor(actor, p, goalStack)) {
                    cacheResult(actor, granter);
                    return true;                    
                }
            }
        }

        if (granter instanceof ConjunctivePrincipal) {
            ConjunctivePrincipal cp = (ConjunctivePrincipal)granter;
            // actor actsfor cp if actor actsfor all conjuncts
            boolean all = true;
            for (Iterator iter = cp.conjuncts().iterator(); iter.hasNext();) {
                Principal p = (Principal)iter.next();
                if (!actsFor(actor, p, goalStack)) {
                    all = false; 
                    break;
                }                
            }
            if (all) {
                cacheResult(actor, granter);
                return true;                                
            }


        }

        // Check the transitive part of actsFor relation.        
        if (s != null) {
            for (Iterator iter = s.iterator(); iter.hasNext(); ) {
                Principal p = (Principal) iter.next();
                if (actsFor(p, granter, goalStack)) {
                    cacheResult(actor, granter);
                    return true;
                }
            }
        }

        // now also go through the grants set
        Set t = (Set)grants.get(granter);
        if (t != null) {
            for (Iterator iter = t.iterator(); iter.hasNext(); ) {
                Principal p = (Principal) iter.next();
                if (actsFor(actor, p, goalStack)) {
                    cacheResult(actor, granter);
                    return true;
                }
            }
        }

        // we've failed, remove the current goal from the stack.
        goalStack.removeLast();
        return false;
    }
    private void cacheResult(Principal actor, Principal granter) {
        Set s = (Set) actorCache.get(actor);
        if (s == null) {
            s = new HashSet();
            actorCache.put(actor, s);
        }
        s.add(granter);   
    }

    public PrincipalHierarchy copy() {
        PrincipalHierarchy dup = new PrincipalHierarchy();

        for (Iterator i = actsfor.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry e = (Map.Entry) i.next();
            Principal p = (Principal) e.getKey();
            Set s = (Set) e.getValue();
            dup.actsfor.put(p, new LinkedHashSet(s));
        }
        for (Iterator i = grants.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry e = (Map.Entry) i.next();
            Principal p = (Principal) e.getKey();
            Set s = (Set) e.getValue();
            dup.grants.put(p, new LinkedHashSet(s));
        }

        return dup;
    }

    public void clear() {
        actsfor.clear();
    }
}
