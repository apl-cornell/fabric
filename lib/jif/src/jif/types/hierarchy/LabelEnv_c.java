package jif.types.hierarchy;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

import jif.Topics;
import jif.types.*;
import jif.types.label.*;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * The wrapper of a set of assumptions that can be used to decide
 * whether L1 &lt;= L2. 
 */
public class LabelEnv_c implements LabelEnv
{
    protected final PrincipalHierarchy ph;
    protected final List<LabelLeAssertion_c> labelAssertions; // a list of LabelLeAssertions
    protected final StringBuffer displayLabelAssertions; 
    protected final JifTypeSystem ts;

    /**
     * A map from AccessPath to representatives of the
     * equivalent set of the AcessPath. No mapping if the
     * element is its own representative.
     */
    protected final Map<AccessPath, AccessPath> accessPathEquivReps;

    protected final LabelEnv_c parent; // a more general (i.e., fewer assertions) LabelEnv, used only for cache lookup.
    protected Solver solver;
    
    /**
     * Do any of the assertions have variables in them?
     */
    protected boolean hasVariables;

    /**
     * Topics to report
     */
    protected static Collection topics = CollectionUtil.list(Topics.jif, Topics.labelEnv);

    public LabelEnv_c(JifTypeSystem ts, boolean useCache) {
        this(ts, new PrincipalHierarchy(), new LinkedList(), "", false, useCache, new LinkedHashMap(), null);
    }
    protected LabelEnv_c(JifTypeSystem ts, PrincipalHierarchy ph, List<LabelLeAssertion_c> assertions, String displayLabelAssertions, boolean hasVariables, boolean useCache, Map<AccessPath, AccessPath> accessPathEquivReps, LabelEnv_c parent) {
        this.ph = ph;
        this.labelAssertions = assertions;
        this.accessPathEquivReps = accessPathEquivReps;
        this.displayLabelAssertions = new StringBuffer(displayLabelAssertions);
        this.hasVariables = false;
        this.solver = null;        
        this.hasVariables = hasVariables;
        this.ts = ts;
        this.useCache = useCache;
        this.parent = parent;
        this.cacheTrue = new HashSet<LeqGoal>();
        this.cacheFalse = new HashSet();        
    }
    
    public void setSolver(Solver s) {
        if (this.solver == null) {
            this.solver = s;
        }
        else if (this.solver != s) {
            throw new InternalCompilerError("LabelEnv given two different solvers");
        }
    }
    
    public PrincipalHierarchy ph() {
        return ph;
    }
    
    public boolean hasVariables() {
        return this.hasVariables;
    }
    
    public void addActsFor(Principal p1, Principal p2) {
        // clear the cache of false leq results, since this may let us prove more results
        cacheFalse.clear();
        ph.add(p1, p2);
    }

    public void addEquiv(Principal p1, Principal p2) {
        // clear the cache of false leq results, since this may let us prove more results
        cacheFalse.clear();
        ph.add(p1, p2);
        ph.add(p2, p1);
    }
    
    public void addAssertionLE(Label L1, Label L2) {
        addAssertionLE(L1, L2, true);
    }
    private boolean addAssertionLE(Label L1, Label L2, boolean updateDisplayString) {
        // clear the cache of false leq results, since this may let us prove more results
        cacheFalse.clear();
        boolean added = false;
        // break up the components
        if (L1 instanceof JoinLabel) {
            for (Iterator<Label> c = ((JoinLabel)L1).joinComponents().iterator(); c.hasNext(); ) {
                Label cmp = c.next();
                added = addAssertionLE(cmp, L2, false) || added;                
            }            
        }
        else if (L2 instanceof MeetLabel) {
            for (Iterator<Label> c = ((MeetLabel)L2).meetComponents().iterator(); c.hasNext(); ) {
                Label cmp = c.next();
                added = addAssertionLE(L1, cmp, false) || added;                
            }                        
        }
        else {
            // don't bother adding the assertion if we already know 
            // L1 is less than L2. However, if it has variables, we
            // need to add it regardless.
            if (L1.hasVariables() || L2.hasVariables() || 
                    !(this.leq(L1, L2, freshSearchState()))) {
                labelAssertions.add(new LabelLeAssertion_c(ts, L1, L2, Position.compilerGenerated()));
                added = true;
                if (!this.hasVariables && (L1.hasVariables() || L2.hasVariables())) {
                    // at least one assertion in this label env has a variable.
                    this.hasVariables = true;
                }
            }            
        }
        
        if (updateDisplayString && added) {
            if (displayLabelAssertions.length() > 0) {
                displayLabelAssertions.append(", ");
            }
            displayLabelAssertions.append(L1 + " <= " + L2);
        }
        return added;
    }

    public void addEquiv(Label L1, Label L2) {
        addAssertionLE(L1, L2, false);
        addAssertionLE(L2, L1, false);
        if (displayLabelAssertions.length() > 0) {
            displayLabelAssertions.append(", ");
        }
        displayLabelAssertions.append(L1 + " equiv " + L2);        
    }
    
    public LabelEnv_c copy() {
        return new LabelEnv_c(ts, ph.copy(), new LinkedList<LabelLeAssertion_c>(labelAssertions), 
                              displayLabelAssertions.toString(), 
                              hasVariables, useCache, 
                              new LinkedHashMap<AccessPath, AccessPath>(this.accessPathEquivReps), this);
    }
    
    public boolean actsFor(Principal p, Principal q) {
        // try converting the principals to dynamic principals and trying again
        AccessPath pathp = null;
        AccessPath pathq = null;
        if (p instanceof DynamicPrincipal) {
            pathp = ((DynamicPrincipal)p).path();
        }
        else if (p.isRuntimeRepresentable()) {
            pathp = new AccessPathConstant(p, ts.Principal(), p.position());
        }
        if (q instanceof DynamicPrincipal) {
            pathq = ((DynamicPrincipal)q).path();
        }
        else if (q.isRuntimeRepresentable()) {
            pathq = new AccessPathConstant(q, ts.Principal(), q.position());
        }

        if (pathp != null && pathq != null && equivalentAccessPaths(pathp, pathq)) return true;

        return (ph.actsFor(p, q));        
    }
    
    public boolean leq(Label L1, Label L2) { 
        if (Report.should_report(topics, 1))
            Report.report(1, "Testing " + L1 + " <= " + L2);
        
        return leq(L1, L2, 
                   new SearchState_c(new AssertionUseCount()));
    }
    
    public boolean equivalentAccessPaths(AccessPath p, AccessPath q) {
        if (p == q) return true;
        if (findAccessPathRepr(p).equals(findAccessPathRepr(q))) {
            return true;
        }
        return p.equivalentTo(q, this);
    }

    /**
     * Returns the representative of the equivalence class of p. 
     * Provided p is not-null, returns a not-null value.
     */
    private AccessPath findAccessPathRepr(AccessPath p) {
        AccessPath last = p;
        AccessPath next = accessPathEquivReps.get(last);
        while (next != null && next != last) {
            last = next;
            next = accessPathEquivReps.get(last);            
        }
        return last;
    }
    public void addEquiv(AccessPath p, AccessPath q) {
        // clear the cache of false leq results, since this may let us prove more results
        cacheFalse.clear();
        AccessPath repr1 = findAccessPathRepr(p);
        AccessPath repr2 = findAccessPathRepr(q);
        accessPathEquivReps.put(p, repr2);
        if (repr1 != p) {
            accessPathEquivReps.put(repr1, repr2);            
        }
        if (!accessPathEquivReps.containsKey(q)) {
            accessPathEquivReps.put(q, repr2);            
        }
    }
    
    protected Set<Serializable> equivAccessPaths(AccessPathRoot p) {
        if (!accessPathEquivReps.containsKey(p)) {
            return Collections.emptySet();
        }
        Set<Serializable> s = new LinkedHashSet<Serializable>();
        AccessPath repr = findAccessPathRepr(p);
        
        for (Iterator<AccessPath> iter = accessPathEquivReps.keySet().iterator(); iter.hasNext();) {
            AccessPath q = iter.next();
            if (repr == findAccessPathRepr(q)) {
                s.add(q);
            }
        }
        return s;
    }
    
    /*
     * Cache the results of leq(Label, Label, SearchState), when we are
     * using assertions only. Note that the rest of the
     * search state contains only information for pruning the search,
     * and so we can ignore it and consider only L1 and L2 when caching 
     * the results. 
     */
    private final Set<LeqGoal> cacheTrue;
    private final Set cacheFalse;
    
    protected final boolean useCache;
    
    private static class LeqGoal {
        final int hash;
        final Object lhs;
        final Object rhs;
        LeqGoal(Policy lhs, Policy rhs) { 
            this.lhs = lhs; this.rhs = rhs;
            if (lhs == null || rhs == null) 
                throw new InternalCompilerError("Null policy!");

            int lhash = lhs.hashCode(); 
            int rhash = rhs.hashCode();
            if (lhash == rhash)
                this.hash = lhash;
            else
                this.hash = lhash ^ rhash;
        }
        LeqGoal(Label lhs, Label rhs) { 
            this.lhs = lhs; this.rhs = rhs;
            if (lhs == null || rhs == null) 
                throw new InternalCompilerError("Null label!");
            int lhash = lhs.hashCode(); 
            int rhash = rhs.hashCode();
            if (lhash == rhash)
                this.hash = lhash;
            else
                this.hash = lhash ^ rhash;
        }
        public int hashCode() {
            return hash;
        }
        public boolean equals(Object o) {
            if (o instanceof LeqGoal) {
                LeqGoal that = (LeqGoal)o;
                return this.hash == that.hash &&
                     this.lhs.equals(that.lhs) && this.rhs.equals(that.rhs);
                
            }
            return false;
        }
        public String toString() {
            return lhs + "<=" + rhs;
        }
    }
    
    /** 
     * Recursive implementation of L1 <= L2.
     */
    public boolean leq(Label L1, Label L2, SearchState state) {
        if (!useCache || 
                !((SearchState_c)state).useAssertions || 
                this.hasVariables()) {
            if (Report.should_report(topics, 3))
                Report.report(3, "Not using cache for " + L1 + " <= " + L2 + 
                              " : useCache = " + useCache + 
                              "; state.useAssertions = " +((SearchState_c)state).useAssertions + 
                              "; this.hasVariables() = " + this.hasVariables());
            return leqImpl(L1, L2, (SearchState_c)state);
        }

        // only use the cache if we are using assertions, and there are no
        // variables
        LeqGoal g = new LeqGoal(L1, L2);
    
        Boolean b = checkCache(g);
        if (b != null) {
            if (Report.should_report(topics, 3))
                Report.report(3, "Found cache value for " + L1 + " <= " + L2 
                              + " : " + b.booleanValue());
            return b.booleanValue();
        }        
        boolean result = leqImpl(L1, L2, (SearchState_c)state);
        cacheResult(g, state, result);
        return result;
    }
    
    protected Boolean checkCache(LeqGoal g) {
        if (!useCache || this.hasVariables()) {
            return null;
        }
        if (cacheTrue.contains(g)) return Boolean.TRUE;
        if (cacheFalse.contains(g)) return Boolean.FALSE;
        
        // try looking in the trueCache of more general label envs
        LabelEnv_c ancestor = this.parent;
        while (ancestor != null) {
            if (!ancestor.useCache || ancestor.hasVariables()) {
                break;
            }
            
            if (ancestor.cacheTrue.contains(g)) {
                this.cacheTrue.add(g);
                return Boolean.TRUE;
            }
            
            ancestor = ancestor.parent;
        }
        return null;
    }
    
    protected void cacheResult(LeqGoal g, SearchState s, boolean result) {
        if (!useCache || this.hasVariables() || !((SearchState_c)s).auc.allZero()) {
            return;
        }
        
        // add the result to the correct cache.
        (result?cacheTrue:cacheFalse).add(g); 
    }
        
    /**
     * Non-caching implementation of L1 <= L2
     */
    private boolean leqImpl(Label L1, Label L2, SearchState_c state) {
        AssertionUseCount auc = state.auc;
        L1 = L1.normalize();
        L2 = L2.normalize();

        if (L1 instanceof WritersToReadersLabel) {
            Label tL1 = triggerTransforms(L1).normalize();
            if (Report.should_report(topics, 3))
                Report.report(3, "Transforming " + L1 + " to " + tL1);
            if (!L1.equals(tL1)) return leq(tL1, L2, state); 
        }
        if (L2 instanceof WritersToReadersLabel) {
            Label tL2 = triggerTransforms(L2).normalize(); 
            if (Report.should_report(topics, 3))
                Report.report(3, "Transforming " + L2 + " to " + tL2);
            if (!L2.equals(tL2)) return leq(L1, tL2, state); 
        }

        if (! L1.isComparable() || ! L2.isComparable()) {
            if (Report.should_report(topics, 3))
                Report.report(3, "Goal " + L1 + " <= " + L2 + " already on goal stack");
            throw new InternalCompilerError("Cannot compare " + L1 +
                                            " with " + L2 + ".");
        }
        
                
        // do some easy tests firsts.
        if (L1.isBottom()) return true;
        //if (L2.isBottom()) return false;
        
        if (L2.isTop()) return true;
        if (L1.isTop()) return false;
                 
        // check the current goals, to make sure we don't go into an infinite
        // recursion...
        LeqGoal newGoal = new LeqGoal(L1, L2);
        if (state.containsGoal(newGoal)) {
            // already have this subgoal on the stack
            if (Report.should_report(topics, 3))
                Report.report(3, "Goal " + L1 + " <= " + L2 + " already on goal stack");
            return false;
        }
        state = new SearchState_c(auc, state, newGoal);        
        
        if (L1.equals(L2)) return true;        

        // L1 <= L2 if for all components of L1, there is one component
        // of L2 that is greater.  We need to filter out all L1, and L2
        // that are not enumerable.        
        if (! L1.isEnumerable()) {
            return L1.leq_(L2, this, state);        
        }
        if (! L1.isEnumerable() || ! L2.isEnumerable()) {
            throw new InternalCompilerError("Cannot compare " + L1 +
                                            " <= " + L2);
        }
        
        if (L2 instanceof MeetLabel) {
            // L1 <= C1 meet ... meet Cn if
            // for all j L1 <= Cj
            MeetLabel ml = (MeetLabel)L2;
            boolean allSat = true;
            for (Iterator<Label> j = ml.meetComponents().iterator(); j.hasNext(); ) {
                Label cj = j.next();
                if (!leq(L1, cj, state)) {
                    allSat = false;
                    break;
                }
            }
            if (allSat) return true;            
        }
        if (L2 instanceof JoinLabel) {
            // L1 <= c1 join ... join cn if there exists a cj 
            // such that L1 <= cj
            JoinLabel jl = (JoinLabel)L2;
            for (Iterator<Label> j = jl.joinComponents().iterator(); j.hasNext(); ) {
                Label cj = j.next();
                if (leq(L1, cj, state)) {
                    return true;
                }
            }            
        }
        
        if (L1.leq_(L2, this, state)) {
            return true;
        }
        
        if (L1 instanceof ArgLabel) {
            ArgLabel al = (ArgLabel)L1;
            // recurse on upper bound.
            if (leq(al.upperBound(), L2, state))
                return true;
        }
        
        if (L1 instanceof MeetLabel || L1 instanceof JoinLabel ||
                L2 instanceof MeetLabel || L2 instanceof JoinLabel) {
            // see if using a conf and integ projections will work
            ConfPolicy conf1 = ts.confProjection(L1);
            ConfPolicy conf2 = ts.confProjection(L2);
            IntegPolicy integ1 = ts.integProjection(L1);
            IntegPolicy integ2 = ts.integProjection(L2);
                        
            if (leq(conf1, conf2, state) && leq(integ1, integ2, state)) {
                    return true;
            }
        }
        
        // do a finer-grain search using pair labels
        if (L1 instanceof PairLabel && !(L2 instanceof PairLabel)) {      
            if (fineGrainPairLabelSearch((PairLabel)L1, L2)) {
                return true;
            }
        }
        
        // try to use assertions
        return leqApplyAssertions(L1, L2, (SearchState_c)state, true);
        
    }
    
    /**
     * Breaks PairLabel L1 into smaller components to see if L1 <= L2.
     * Returns true if successfully discover that L1 <= L2, false otherwise.
     * 
     * More precisely, given L1 = {o_1->r_1 ; ... ; o_m->r_m ; p_1<-w_1 ; ... ; p_n<-w_n},
     * this method will return true if
     *    {o_i->r_i ; *<-*} <= L2 for all i in 1..m
     *  and
     *    {_<-_ ; p_j<-w_j} <= L2 for all j in 1..n.
     * 
     * @param L1 a PairLabel
     * @param L2 any label (shouldn't be a PairLabel)
     * @return
     */
    private boolean fineGrainPairLabelSearch(PairLabel L1, Label L2) {
        ConfPolicy cp = L1.confPolicy();
        IntegPolicy ip = L1.integPolicy();
        
        if ((!(cp instanceof JoinConfPolicy_c) && !(ip instanceof JoinIntegPolicy_c)) 
                || (cp.isSingleton() && ip.isSingleton())) {
            // either cp and ip are both not Join Policies, or they are 
            // both singletons, i.e., cannot be decomposed.
            // nothing we can do to help here 
            return false;
        }
        
        Position pos = L1.position();

        if (cp instanceof JoinConfPolicy_c) {
            // break cp down and try to satisfy each one individually
            JoinConfPolicy_c jcp = (JoinConfPolicy_c)cp;
            IntegPolicy bottomInteg = ts.bottomIntegPolicy(pos);

            for (Iterator iter = jcp.joinComponents().iterator(); iter.hasNext();) {
                ConfPolicy joinComponent = (ConfPolicy)iter.next();
                if (!leq(ts.pairLabel(pos, joinComponent, bottomInteg), L2)) {
                    return false;
                }
            }
        }
        else {
            // cp isn't a join policy, make sure that it still is <= L2
            if (!leq(ts.pairLabel(pos, cp, ts.bottomIntegPolicy(pos)), L2)) {
                return false;
            }            
        }

        if (ip instanceof JoinIntegPolicy_c) {
            // break ip down and try to satisfy each one individually
            JoinIntegPolicy_c jip = (JoinIntegPolicy_c)ip;
            ConfPolicy bottomConf = ts.bottomConfPolicy(pos);
            for (Iterator iter = jip.joinComponents().iterator(); iter.hasNext();) {
                IntegPolicy joinComponent = (IntegPolicy)iter.next();
                if (!leq(ts.pairLabel(pos, bottomConf, joinComponent), L2)) {
                    return false;
                }
            }
        }
        else {
            // ip isn't a join policy, make sure that it still is <= L2
            if (!leq(ts.pairLabel(pos, ts.bottomConfPolicy(pos), ip), L2)) {
                return false;
            }
        }

        return true;
    }
        
    /**
     * Bound the number of times any particular assertion can be used; this bounds
     * the search in leqImpl.
     */
    private static final int ASSERTION_USE_BOUND = 1;

    /**
     * Bound the number of times any particular assertion can be used; this bounds
     * the search in leqImpl.
     */
    private static final int EQUIV_PATH_USE_BOUND = 1;

    /**
     * Bound the total number of assertion uses; this bounds
     * the search in leqImpl.
     */
    private static final int ASSERTION_TOTAL_BOUND = 6 * ASSERTION_USE_BOUND;
        
    /**
     * Bound the total number of assertion uses; this bounds
     * the search in leqImpl.
     */
    private static final int EQUIV_PATH_TOTAL_BOUND = 8 * EQUIV_PATH_USE_BOUND;

    private boolean leqApplyAssertions(Label L1, Label L2, SearchState_c state, boolean beSmart) {
        AssertionUseCount auc = state.auc;
        if (!state.useAssertions || auc.size() >= ASSERTION_TOTAL_BOUND || auc.accessPathSize() > EQUIV_PATH_TOTAL_BOUND) 
            return false;
        if (Report.should_report(topics, 2))
            Report.report(2, "Applying assertions for " + L1 + " <= " + L2);

        for (Iterator<LabelLeAssertion_c> i = labelAssertions.iterator(); i.hasNext();) { 
            LabelLeAssertion c = i.next();

            if (auc.get(c) >= ASSERTION_USE_BOUND) {
                continue;
            }
            AssertionUseCount newAUC = new AssertionUseCount(auc, c);
            SearchState newState = new SearchState_c(newAUC, state, null);

            Label cLHS = c.lhs();
            if (cLHS.hasVariables()) { 
                cLHS = this.solver.applyBoundsTo(c.lhs());
            }
            Label cRHS = c.rhs();
            if (cRHS.hasVariables()) { 
                cRHS = this.solver.applyBoundsTo(c.rhs());
            }
            if (Report.should_report(topics, 4))
                Report.report(4, "Considering assertion " + c + " for " + L1 + " <= " + L2);

//            if (beSmart) {
//                boolean useConstraint = false;
//                useConstraint = L1.equals(cLHS) || L2.equals(cRHS);
//                if (!useConstraint) {
//                    // check if L2 contains cRHS
//                    if (L2 instanceof JoinLabel) {
//                        JoinLabel jl = (JoinLabel)L2;
//                        useConstraint = jl.joinComponents().contains(cRHS);
//                    }
//                }
//                if (!useConstraint) {
//                    continue;
//                }
//            }
            if (beSmart) {
                // only use assertions that match one or the other of our labels                
                if (!L1.equals(cLHS) && !L2.equals(cRHS)) {
                    continue;
                }
            }
            if (Report.should_report(topics, 3))
                Report.report(3, "Trying assertion " + c + " for " + L1 + " <= " + L2);
            if (leq(L1, cLHS, newState) && 
                    leq(cRHS, L2, newState)) {
                return true;
            }
        }
        
        // try to use the access path equivalences
        if (L2 instanceof DynamicLabel) {
            AccessPath p2 = ((DynamicLabel)L2).path();
            AccessPathRoot p2root = p2.root();
            // see if there are any other access paths equivalent
            Set<Serializable> equivAccessPaths = equivAccessPaths(p2root);
            for (Iterator<Serializable> iter = equivAccessPaths.iterator(); iter.hasNext(); ) {
                AccessPath p = (AccessPath)iter.next();
                if (p.equals(p2root)) continue;
                AccessPathEquivalence ea = new AccessPathEquivalence(p, p2root); 
                if (auc.get(ea) >= EQUIV_PATH_USE_BOUND) {
                    continue;
                }
                AssertionUseCount newAUC = new AssertionUseCount(auc, ea);
                SearchState newState = new SearchState_c(newAUC, state, null);
                Label equiv = ts.dynamicLabel(Position.compilerGenerated(),
                                              p2.subst(p2root, p));
                if (leq(L1, equiv, newState)) {
                    return true;
                }
            }
        }
        if (L1 instanceof DynamicLabel) {
            AccessPath p1 = ((DynamicLabel)L1).path();
            AccessPathRoot p1root = p1.root();
            // see if there are any other access paths equivalent
            Set<Serializable> equivAccessPaths = equivAccessPaths(p1root);
            for (Iterator<Serializable> iter = equivAccessPaths.iterator(); iter.hasNext(); ) {
                AccessPath p = (AccessPath)iter.next();
                if (p.equals(p1root)) continue;
                AccessPathEquivalence ea = new AccessPathEquivalence(p, p1root); 
                if (auc.get(ea) >= EQUIV_PATH_USE_BOUND) {
                    continue;
                }
                AssertionUseCount newAUC = new AssertionUseCount(auc, ea);
                SearchState newState = new SearchState_c(newAUC, state, null);
                Label equiv = ts.dynamicLabel(Position.compilerGenerated(),
                                              p1.subst(p1root, p));
                if (leq(equiv, L2, newState)) {
                    return true;
                }
                
            }
            
        }
        return false;

    }
    
    
    public boolean leq(Policy p1, Policy p2) {
        return leq(p1.simplify(), p2.simplify(), new SearchState_c(new AssertionUseCount()));
    }
    public boolean leq(Policy p1, Policy p2, SearchState state_) {
        // check the current goals
        SearchState_c state = (SearchState_c)state_;
        AssertionUseCount auc = state.auc;
        LeqGoal newGoal = new LeqGoal(p1, p2);
        if (state.containsGoal(newGoal)) {
            // already have this subgoal on the stack
            return false;
        }
        state = new SearchState_c(auc, state, newGoal);        

        
        if (p1 instanceof ConfPolicy && p2 instanceof ConfPolicy) {
            return leq((ConfPolicy)p1, (ConfPolicy)p2, state);
        }
        if (p1 instanceof IntegPolicy && p2 instanceof IntegPolicy) {
            return leq((IntegPolicy)p1, (IntegPolicy)p2, state);
        }
        return false;
    }
    
    public boolean leq(ConfPolicy p1, ConfPolicy p2, SearchState state) {
        if (p2.isSingleton() || !p1.isSingleton()) {
            if (p1.leq_(p2, this, state)) return true;
        }
        if (p2 instanceof JoinPolicy_c) {
            // we need to find one element ci of p2 such that p1 <= ci
            JoinPolicy_c jp = (JoinPolicy_c)p2;
            for (Iterator i = jp.joinComponents().iterator(); i.hasNext(); ) {
                ConfPolicy ci = (ConfPolicy) i.next();
                
                if (leq(p1, ci, state)) {
                    return true;
                }
            }
        }
        else if (p2 instanceof MeetPolicy_c) {
            // for all elements ci of p2 we require p1 <= ci             
            MeetPolicy_c mp = (MeetPolicy_c)p2;
            boolean allSat = true;
            for (Iterator i = mp.meetComponents().iterator(); i.hasNext(); ) {
                ConfPolicy ci = (ConfPolicy) i.next();                
                if (!leq(p1, ci, state)) {
                    allSat = false;
                    break;
                }
            }
            if (allSat) return true;
        }
        if (p2.isSingleton() || !p1.isSingleton()) return false;
        return p1.leq_(p2, this, state);
    }
    public boolean leq(IntegPolicy p1, IntegPolicy p2, SearchState state) {
        if (p2 instanceof WriterPolicy) {
            WriterPolicy wp2 = (WriterPolicy)p2;
            // if the writer policy is o<-_, then the policy
            // does not restrict the integrity at all
            if (wp2.writer().isBottomPrincipal()) return true;
        }
        if (p2.isSingleton() || !p1.isSingleton()) {
            if (p1.leq_(p2, this, state)) return true;
        }
        if (p2 instanceof JoinPolicy_c) {
            // we need to find one element ci of p2 such that p1 <= ci
            JoinPolicy_c jp = (JoinPolicy_c)p2;
            for (Iterator i = jp.joinComponents().iterator(); i.hasNext(); ) {
                IntegPolicy ci = (IntegPolicy) i.next();
                
                if (leq(p1, ci, state)) {
                    return true;
                }
            }
        }
        else if (p2 instanceof MeetPolicy_c) {
            // for all elements ci of p2 we require p1 <= ci
            MeetPolicy_c mp = (MeetPolicy_c)p2;
            boolean allSat = true;
            for (Iterator i = mp.meetComponents().iterator(); i.hasNext(); ) {
                IntegPolicy ci = (IntegPolicy) i.next();                
                if (!leq(p1, ci, state)) {
                    allSat = false;
                    break;
                }
            }
            if (allSat) return true;
        }
        if (p2.isSingleton() || !p1.isSingleton()) return false;
        return p1.leq_(p2, this, state);        
    }
    /**
     * Is this enviornment empty, or does is contain some constraints?
     */
    public boolean isEmpty() {
        return labelAssertions.isEmpty() && ph.isEmpty();
    }
    
    
    /**
     * Finds a PairLabel upper bound. It does not use leq
     *
     */
    public Label findUpperBound(Label L) {
        return findUpperBound(L, Collections.EMPTY_SET, false);
    }
    /**
     * Finds an upper bound that does not contain arg labels. It does not use leq.
     *
     */
    public Label findNonArgLabelUpperBound(Label L) {
        return findUpperBound(L, Collections.EMPTY_SET, true);
    }
    protected Label findUpperBound(Label L, Collection<Serializable> seen, boolean noArgLabels) {        
        // L is a pair label.
        if (L instanceof PairLabel) return L;
        if (L instanceof VarLabel_c) {
            // cant do anything.
            return L;
        }        
        if (noArgLabels && (L instanceof DynamicLabel || 
                L instanceof ParamLabel ||
                L instanceof CovariantParamLabel)) {
            // good enough
            return L;
        }
        
        if (seen.contains(L)) return ts.topLabel();
        
        Collection<Serializable> newSeen = new ArrayList<Serializable>(seen.size() + 1);
        newSeen.addAll(seen);
        newSeen.add(L);
                
        Set<Label> allBounds = new LinkedHashSet<Label>();
        if (L instanceof JoinLabel) {
            JoinLabel jl = (JoinLabel)L;
            Label ret = ts.bottomLabel();
            for (Iterator<Label> iter = jl.joinComponents().iterator(); iter.hasNext();) {
                Label comp = iter.next();
                ret = ts.join(ret, this.findUpperBound(comp, newSeen, noArgLabels));
            }
            allBounds.add(ret);
        }
        if (L instanceof MeetLabel) {
            MeetLabel ml = (MeetLabel)L;
            Label ret = ts.topLabel();
            for (Iterator<Label> iter = ml.meetComponents().iterator(); iter.hasNext();) {
                Label comp = iter.next();
                ret = ts.meet(ret, this.findUpperBound(comp, newSeen, noArgLabels));
            }
            allBounds.add(ret);
        }
                
        // check the assertions
        for (Iterator<LabelLeAssertion_c> i = labelAssertions.iterator(); i.hasNext();) { 
            LabelLeAssertion c = i.next();

            Label cLHS = c.lhs();
            if (cLHS.hasVariables()) { 
                cLHS = this.solver.applyBoundsTo(c.lhs());
            }
            Label cRHS = c.rhs();
            if (cRHS.hasVariables()) { 
                cRHS = this.solver.applyBoundsTo(c.rhs());
            }
            if (L.equals(cLHS)) {
                allBounds.add(findUpperBound(cRHS, newSeen, noArgLabels));
            }
        }

        if (L instanceof ArgLabel) {
            ArgLabel al = (ArgLabel)L;
            // we want to make sure that we don't end up recursing.
            // Check that al.upperbound() is not recursively defined.            
            if (!argLabelBoundRecursive(al)) {
                allBounds.add(findUpperBound(al.upperBound(), newSeen, noArgLabels));
            }
        }
        
        if (!allBounds.isEmpty()) {
            Label upperBound;
            if (allBounds.size() == 1) {
                upperBound = allBounds.iterator().next();
            }
            else {
                upperBound = ts.meetLabel(L.position(), allBounds);
            }
            if (Report.should_report(topics, 4))
                Report.report(4, "Using " + upperBound + " as upper bound for " + L);
            return upperBound;
        }

        if (Report.should_report(topics, 4))
            Report.report(4, "Using top as upper bound for " + L);
        return ts.topLabel();
    }
    
    private boolean argLabelBoundRecursive(ArgLabel al) {
        ArgLabelGatherer alg = new ArgLabelGatherer();
        try {
            al.upperBound().subst(alg);
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("Unexpcted SemanticError");
        }
        return alg.argLabels.contains(al);        
    }
    private static class ArgLabelGatherer extends LabelSubstitution {
        private final Set<Label> argLabels = new LinkedHashSet<Label>();
        
        public Label substLabel(Label L) throws SemanticException {
            if (L instanceof ArgLabel) {
                argLabels.add(L);
            }
            return L;
        }        
    }        
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(this.displayLabelAssertions);
//        for (Iterator i = labelAssertions.iterator(); i.hasNext(); ) {
//            LabelLeAssertion c = (LabelLeAssertion) i.next();
//            sb.append(c.lhs());
//            sb.append(" <= ");
//            sb.append(c.rhs());
//            if (i.hasNext())
//                sb.append(", ");
//        }
        if (!ph().isEmpty()) {
            if (!labelAssertions.isEmpty()) {
                sb.append(", ");
            }
            sb.append(ph().actsForString());
        }
        if (/*Report.should_report(Report.debug, 1) && */!accessPathEquivReps.isEmpty()) {
            for (Iterator iter = accessPathEquivReps.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry e = (Entry)iter.next();
                if (e.getKey() == e.getValue()) continue;
                if (sb.length() > 1) sb.append(", ");
                sb.append(((AccessPath)e.getKey()).exprString());
                sb.append("==");
                sb.append(((AccessPath)e.getValue()).exprString());
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Returns a Map of Strings to List[String]s which is the descriptions of any 
     * components that appear in the environment. This map is used for verbose 
     * output to the user, to help explain the meaning of constraints and 
     * labels.
     * 
     * Seen components is a Set of Labels whose definitions will not be 
     * displayed.
     */
    public Map<String, List<String>> definitions(VarMap bounds, Set seenComponents) {
        Map<String, List<String>> defns = new LinkedHashMap<String, List<String>>();
        
        Set<Label> labelComponents = new LinkedHashSet<Label>();
        for (Iterator<LabelLeAssertion_c> iter = labelAssertions.iterator(); iter.hasNext(); ) {
            LabelLeAssertion c = iter.next();
            Label bound = bounds.applyTo(c.lhs());
            Collection<Label> components;
            if (bound instanceof JoinLabel) {
                components = ((JoinLabel)bound).joinComponents();
            }
            else if (bound instanceof MeetLabel) {
                components = ((MeetLabel)bound).meetComponents();
            }
            else {
                components = Collections.singleton(bound);
            }
            
            for (Iterator<Label> i = components.iterator(); i.hasNext(); ) {
                Label l = i.next();
                labelComponents.add(l);
            }
            
            bound = bounds.applyTo(c.rhs());
            if (bound instanceof JoinLabel) {
                components = ((JoinLabel)bound).joinComponents();
            }
            else if (bound instanceof MeetLabel) {
                components = ((MeetLabel)bound).meetComponents();
            }
            else {
                components = Collections.singleton(bound);
            }
            for (Iterator<Label> i = components.iterator(); i.hasNext(); ) {
                Label l = i.next();
                labelComponents.add(l);
            }
        }
        
        labelComponents.removeAll(seenComponents);
        
        for (Iterator<Label> iter = labelComponents.iterator(); iter.hasNext(); ) {
            Label l = iter.next();
            if (l.description() != null) {
                String s = l.componentString();
                if (s.length() == 0)
                    s = l.toString();
                defns.put(s, Collections.singletonList(l.description()));
            }
        } 
        
        return defns;
    }   
    
    /**
     * Trigger the transformation of WritersToReaders labels. Not guaranteed
     * to remove all writersToReaders labels. 
     */
    public Label triggerTransforms(Label label) {
        LabelSubstitution subst = new LabelSubstitution() {
            public Label substLabel(Label L) throws SemanticException {
                if (L instanceof WritersToReadersLabel) {
                    return ((WritersToReadersLabel)L).transform(LabelEnv_c.this);
                }
                return L;
            }            
        };
        
        try {
            return label.subst(subst).simplify();
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("Unexpected SemanticException", e);
        }
    }
    
    /**
     * Class used to keep track of how many times each constraint has been used 
     * during the search to solve label inequality.
     */
    private static class AssertionUseCount {
        private final AssertionUseCount previousAUC;
        private final Object use;
        private final int size;
        private final int accesspathsize;
        AssertionUseCount() {
            this.use = null;
            this.previousAUC = null;
            this.size = 0;
            this.accesspathsize = 0;
        }
        AssertionUseCount(AssertionUseCount auc, Assertion a) {
            this.use = a;
            this.previousAUC = auc;
            int s = 0, aps = 0;
            if (previousAUC != null) {
                s = previousAUC.size();
                aps = previousAUC.accessPathSize();
            }
            if (use != null) {                
                s++;
            }
            this.size = s;
            this.accesspathsize = aps;
        }
        AssertionUseCount(AssertionUseCount auc, AccessPathEquivalence a) {
            this.use = a;
            this.previousAUC = auc;
            int s = 0, aps = 0;
            if (previousAUC != null) {
                s = previousAUC.size();
                aps = previousAUC.accessPathSize();
            }
            if (use != null) {                
                aps++;
            }
            this.size = s;
            this.accesspathsize = aps;
        }
        
        public boolean allZero() {
            return size() == 0 && accessPathSize() == 0;
        }
        public int get(Assertion a) {
            int prev = 0;
            if (previousAUC != null) prev = previousAUC.get(a);
            if (use != null && use.equals(a)) return 1 + prev;
            return prev;
        }
        public int get(AccessPathEquivalence a) {
            int prev = 0;
            if (previousAUC != null) prev = previousAUC.get(a);
            if (use != null && use.equals(a)) return 1 + prev;
            return prev;
        }
        public int size() {
            return size;
        }
        public int accessPathSize() {
            return accesspathsize;
        }
//        public String toString() {
//            return tally.toString();
//        }
    }
    protected SearchState freshSearchState() {
        return new SearchState_c(null, null, null);
    }
    private static class SearchState_c implements SearchState {
        public final AssertionUseCount auc;
        public final LeqGoal currentGoal;
        public final SearchState_c prevState;
        public final boolean useAssertions;
        SearchState_c(AssertionUseCount auc, SearchState_c prevState, LeqGoal currentGoal) {
            this.useAssertions = (auc != null);
            this.auc = auc;
            this.prevState = prevState;
            this.currentGoal = currentGoal;
        }
        public SearchState_c(AssertionUseCount auc) {
            this(auc, null, null);
        }
        public boolean containsGoal(LeqGoal g) {
            if (currentGoal != null && currentGoal.equals(g)) {
                return true;
            }
            if (prevState != null) {
                return prevState.containsGoal(g);
            }
            return false;
        }
    }
    
    private static class AccessPathEquivalence {
        final private AccessPath p;
        final private AccessPath q;
        AccessPathEquivalence(AccessPath p, AccessPath q) {
            this.p = p;
            this.q = q;
        }
        public boolean equalsImpl(TypeObject t) {
            if (t instanceof AccessPathEquivalence) {
                AccessPathEquivalence that = (AccessPathEquivalence)t;
                return (this.p.equals(that.p) && this.q.equals(that.q)) ||
                       (this.p.equals(that.q) && this.q.equals(that.p));
            }
            return false;
        }
    }    
}
