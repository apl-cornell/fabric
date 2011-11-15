package jif.types;

import java.util.*;

import jif.JifOptions;
import jif.Topics;
import jif.types.hierarchy.LabelEnv;
import jif.types.label.*;
import jif.types.principal.Principal;
import jif.types.principal.VarPrincipal;
import polyglot.frontend.Compiler;
import polyglot.main.Options;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.util.*;

/**
 * A solver of Jif constraints. Finds solution to constraints essentially by
 * propagating upper bounds backwards.
 */
public abstract class AbstractSolver implements Solver {
    protected EquationQueue Q; // Queue of active equations to work on

    protected LinkedList scc; // List of strongly connected components that
                            // are left to work on
    
    protected Set currentSCC; // Equations in the strongly connected component that we are currently working on

    // connected component that is being worked on
    protected Collection equations; // Set of all equations

    /**
     * Map from variables to (Set of) equations that may be invalidated by
     * the variable changing. That is, if the bound of
     * the variable v changes, then we may need to re-examine all equations in
     * (Set)varEqnDependencies.get(v)
     */
    private Map varEqnDependencies;

    /**
     * Map from equations to (Set of) variables whose bound may be modified as a
     * result of solving that equation. If the bound of variables in the
     * equation eqn are modified, then the variables will be contained in
     * (Set)eqnVarDependencies.get(eqn)
     */
    private Map eqnVarDependencies;

    /**
     * Map from variables to (Set of) equations that may change the value
     * of the variable. That is, when satisfying any equation in 
     * (Set)varEqnReverseDependencies.get(v), the value of v may be changed.
     */
    private Map varEqnReverseDependencies;

    /**
     * Map from equations to (Set of) variables in which a change in value
     * may invalidate this equation. That is, when the value of any variable
     * in (Set)eqnVarReverseDependencies.get(e), changes, the
     * equation e may be invalidated.
     */
    private Map eqnVarReverseDependencies;
    
    /**
     * Set of Variables that had their initial value fixed when the constraint
     * was added.
     */
    protected Set fixedValueVars;

    protected static final int STATUS_NOT_SOLVED = 0;

    protected static final int STATUS_SOLVING = 1;

    protected static final int STATUS_SOLVED = 2;

    protected static final int STATUS_NO_SOLUTION = 3;

    protected int status; // status of the current system has been solved

    protected VarMap bounds; // Current bounds on label variables

    protected JifTypeSystem ts;
    
    /**
     * Constraints that were added to the solver, and failed statically.
     * If the flag THROW_STATIC_FAILED_CONSTRAINTS is true, then the
     * constraint will be thrown immediately, otherwise the constraint
     * will be added to this set, and thrown when solve() is called.
     */
    protected Set staticFailedConstraints;
    protected static final boolean THROW_STATIC_FAILED_CONSTRAINTS = false;
    protected final Compiler compiler;

    protected Map traces; //Map from variables to their histories of refining

    protected static Collection topics = CollectionUtil.list(Topics.jif, Topics.solver);

    /**
     * This boolean is used to turn on or off whether the strongly connected
     * components optimization is used.
     * 
     * If true, the constraints are partitioned into strongly connected
     * components, which are then sorted in topological order. If false, then
     * constraints are solved in the order they are added to the solver
     */
    protected final boolean useSCC;


    /**
     * The name of the solver, for debugging purposes.
     */
    private final String solverName;

    /**
     * Number of solvers instantiated, for debugging purposes
     */
    protected static int solverCounter;
        
    /**
     * Constructor
     */
    protected AbstractSolver(JifTypeSystem ts, Compiler compiler, String solverName) {
        this(ts, compiler, solverName, false);
    }
    /**
     * Constructor
     */
    protected AbstractSolver(JifTypeSystem ts, Compiler compiler, String solverName, boolean useSCC) {
        this.ts = ts;
        this.compiler = compiler;
        this.useSCC = useSCC;
        
        Q = new EquationQueue();
        equations = new LinkedHashSet();
        varEqnDependencies = new LinkedHashMap();
        eqnVarDependencies = new LinkedHashMap();
        varEqnReverseDependencies = new LinkedHashMap();
        eqnVarReverseDependencies = new LinkedHashMap();
        traces = new LinkedHashMap();
        setStatus(STATUS_NOT_SOLVED);
        bounds = new VarMap(ts, getDefaultLabelBound(), getDefaultPrincipalBound());
        scc = null;
        currentSCC = null;
        this.solverName = solverName + " (#" + (++solverCounter) + ")";
        this.fixedValueVars = new HashSet();
    }
    

    /**
     * Constructor
     */
    protected AbstractSolver(AbstractSolver js) {
        this.ts = js.ts;
        this.compiler = js.compiler;
        this.useSCC = js.useSCC;
        Q = new EquationQueue(js.Q);
        equations = new LinkedHashSet(js.equations);

        varEqnDependencies = js.varEqnDependencies;
        eqnVarDependencies = js.eqnVarDependencies;
        traces = new LinkedHashMap(js.traces);
        status = js.status;
        bounds = js.bounds.copy();
        equations = new LinkedHashSet(js.equations);
        scc = new LinkedList(js.scc);
        solverName = js.solverName;
        fixedValueVars = js.fixedValueVars;
    }

    /**
     * Convenience method to report messages for the appropriate topics
     */
    static final public void report(int level, String s) {
        Report.report(level, s);
    }

    /**
     * Convenience method to determine if messages of the given obscurity should
     * be reported.
     */
    static final public boolean shouldReport(int obscurity) {
        return Report.should_report(topics, obscurity);
    }

    public Label applyBoundsTo(Label L) {
        return bounds.applyTo(L);
    }
    
    protected Label triggerTransforms(Label label, final LabelEnv env) {
        return env.triggerTransforms(label);
    }
    
    protected List getQueue() {
        return Collections.unmodifiableList(Q.list);
    }

    protected void addEquationToQueue(Equation eqn) {
        Q.add(eqn);
    }

    protected void addEquationToQueueHead(Equation eqn) {
        Q.addFirst(eqn);
    }


    /**
     * Get the bounds for this Solver.
     */
    public VarMap bounds() {
        return bounds;
    }

    /**
     * Set the bounds for this Solver.
     */
    public void setBounds(VarMap bnds) {
        this.bounds = bnds;
    }

    /**
     * 
     * @param v the VarLabel to set the bound for
     * @param newBound the new bound for v
     * @param responsible the constraint that was responsible for modifying the
     *            bound.
     * @throws SemanticException if the new bound violates an equality
     *             constraint.
     */
    public void setBound(VarLabel v, Label newBound, LabelConstraint responsible)
    throws SemanticException {
        bounds.setBound(v, newBound);
    }
    public void setBound(VarPrincipal v, Principal newBound, PrincipalConstraint responsible)
    throws SemanticException {
        bounds.setBound(v, newBound);
    }

    /**
     * Solve the system of constraints. If the system has already been solved,
     * then returned the cached solution.
     * 
     * @throws SemanticException if the Solver cannot find a solution to the
     *             system of contraints.
     */
    public VarMap solve() throws SemanticException {
        // Cache the solution.
        if (status == STATUS_SOLVED || status == STATUS_NO_SOLUTION) {
            return bounds;
        }
        if (status == STATUS_SOLVING) {
            throw new InternalCompilerError("solve called on solver while "
                    + "in the process of solving.");
        }

        // status at this point is STATUS_NOT_SOLVED
        setStatus(STATUS_SOLVING);

        //bounds = new VarMap(ts, getDefaultBound());

        if (shouldReport(1)) {
            report(1, "===== Starting solver " + solverName + " =====");
            report(1, "   " + equations.size() + " equations");
        }
        
        // check for static failures.
        if (staticFailedConstraints != null && !staticFailedConstraints.isEmpty()) {
            if (shouldReport(1)) {
                report(1, "   " + staticFailedConstraints.size() + " statically failed constraint");
            }
            setStatus(STATUS_NO_SOLUTION);
            for (Iterator iter = staticFailedConstraints.iterator(); iter.hasNext();) {
                Constraint cons = (Constraint)iter.next();
                SemanticException ex = new SemanticException(errorMsg(cons), 
                                                             cons.position()); 
                if (!iter.hasNext()) {
                    // throw the last one
                    throw ex;
                }
                // add all but the last to the queue.
                compiler.errorQueue().enqueue(ErrorInfo.SEMANTIC_ERROR, ex.getMessage(), ex.position());
            }
        }
        
        if (useSCC) {
            LinkedList pair = findSCCs();
            Equation[] by_scc = (Equation[])pair.getFirst();
            int[] scc_head = (int[])pair.getLast();

            scc = new LinkedList();
            Set currentScc = null;
            for (int i = 0; i < scc_head.length; i++) {
                if (scc_head[i] == -1) {
                    // it's the start of a new scc
                    // add what we've already gathered to the set of strongly connected
                    // components 
                    if (currentScc != null) {
                        scc.add(currentScc);
                        currentScc = null;
                    }
                }
                if (currentScc == null) currentScc = new LinkedHashSet();
                currentScc.add(by_scc[i]);
            }
            if (currentScc != null) {
                scc.add(currentScc);
                currentScc = null;
            }
        }
        else {
            // not using SCC, so pretend that 
            // we have a single SCC consisting of all equations
            scc = new LinkedList();
            scc.add(new LinkedHashSet(equations));
        }

        // pre-initialize the queue with the first strongly connected
        // component
        if (scc.isEmpty()) {
            currentSCC = Collections.EMPTY_SET;
            Q = new EquationQueue();
        }
        else {
            currentSCC = (Set)scc.removeFirst();
            Q = new EquationQueue(currentSCC);
        }

        try {
            VarMap soln = solve_bounds();
            setStatus(STATUS_SOLVED);
            if (shouldReport(1)) {
                report(1, "   finished " + solverName);
            }
            return soln;
        }
        catch (SemanticException e) {
            setStatus(STATUS_NO_SOLUTION);
            throw e;
        }
    }
    
    protected void setStatus(int status) {
        this.status = status;
    }

    /**
     * This method must return a constant label, which is the default bound of
     * label variables.
     */
    protected abstract Label getDefaultLabelBound();

    /**
     * This method must return a constant principal, which is the default bound of
     * principal variables.
     */
    protected abstract Principal getDefaultPrincipalBound();

    /**
     * Solve the system of constraints, by finding upper bounds for the label
     * variables.
     * 
     * @return a solution to the system of constraints, in the form of a VarMap
     *         of the upper bounds of the label variables.
     * @throws SemanticException if the Solver cannot find a solution to the
     *             system of constraints.
     */
    protected VarMap solve_bounds() throws SemanticException {
        // Solve the system of constraints. bounds may already contain a
        // partial solution, in which case attempt to complete the solution.

        if (shouldReport(3)) {
            report(3, "======EQUATIONS======");
            for (Iterator i = equations.iterator(); i.hasNext();) {
                Equation eqn = (Equation)i.next();
                report(3, eqn.toString());
            }
        }

        int counter = 0;

        if (Q.isEmpty()) checkCandidateSolution();

        while (!Q.isEmpty()) {
            while (!Q.isEmpty()) {
                counter++;
                Equation eqn = Q.removeFirst();
                considerEquation(eqn);

                // if we finished the last strongly connected component
                // move to the next one
                // 
                // done this way instead of an outer loop because of the
                // way the search method works
                if (Q.isEmpty() && !scc.isEmpty()) {
                    currentSCC = (Set)scc.removeFirst();
                    Q.addAll(currentSCC);
                }
            } // end while

            checkCandidateSolution();
        }
        if (shouldReport(2)) report(2, "Number of relaxation steps: " + counter);
        if (shouldReport(2)) {
            report(2, bounds.toString());
        }
        return bounds;
    }

    protected void considerEquation(Equation eqn) throws SemanticException {
        if (eqn instanceof LabelEquation) {
            considerEquation((LabelEquation)eqn);   
        }
        else if (eqn instanceof PrincipalEquation) {
            considerEquation((PrincipalEquation)eqn);   
        }
        else {
            throw new InternalCompilerError("Unexpected eqn " + eqn);
        }
    }
    protected void considerEquation(LabelEquation eqn) throws SemanticException {
        Label lhsbound = triggerTransforms(bounds.applyTo(eqn.lhs()), eqn.env());
        Label rhsbound = triggerTransforms(bounds.applyTo(eqn.rhs()), eqn.env());                                

        if (eqn.env().leq(lhsbound, rhsbound)) {
            if (shouldReport(5))
                    report(5, "constraint: " + eqn
                            + " already satisfied: " + lhsbound + "<="
                            + rhsbound);
        }
        else {
            if (shouldReport(4)) {
                report(4, "Considering constraint: " + eqn + " ("
                        + (eqn.position()==null?"null":("line "+eqn.position().line())) + ")");
            }
            // let the subclass deal with changing the bounds on
            // variables
            // to make this equation satisfied.
            solve_eqn(eqn);
        }        
    }
    
    /**
     * This method changes the bounds of variables in the Equation eqn, to make
     * the equation satisfied. The method may postpone solving the equation by
     * putting the equation back on the queue, using addEquationToQueue().
     */
    protected abstract void solve_eqn(LabelEquation eqn) throws SemanticException;

    protected void considerEquation(PrincipalEquation eqn) throws SemanticException {
        Principal lhsbound = bounds.applyTo(eqn.lhs());
        Principal rhsbound = bounds.applyTo(eqn.rhs());                                

        if (eqn.env().actsFor(lhsbound, rhsbound)) {
            if (shouldReport(5))
                    report(5, "constraint: " + eqn
                            + " already satisfied: " + lhsbound + " actsfor "
                            + rhsbound);
        }
        else {
            if (shouldReport(4)) {
                report(4, "Considering constraint: " + eqn + " ("
                        + (eqn.position()==null?"null":("line "+eqn.position().line())) + ")");
            }
            // let the subclass deal with changing the bounds on
            // variables
            // to make this equation satisfied.
            solve_eqn(eqn);
        }        
    }

    /**
     * This method changes the bounds of variables in the Equation eqn, to make
     * the equation satisfied. The method may postpone solving the equation by
     * putting the equation back on the queue, using addEquationToQueue().
     */
    protected abstract void solve_eqn(PrincipalEquation eqn) throws SemanticException;

    /**
     * Check the candidate solution
     */
    protected final void checkCandidateSolution() throws SemanticException {
        if (shouldReport(4)) {
            report(4, "===== Checking candidate solution =====");
        }
        // We are done refining the upper bounds of the variables.
                
        // Make one final check that all equations are satisfied.
        // This will force a check on equations with unconstrained
        // variables.
        for (Iterator i = equations.iterator(); i.hasNext();) {
            Equation eqn = (Equation)i.next();
            if (eqn instanceof LabelEquation) {
                checkEquationSatisfied((LabelEquation)eqn);
            }
        }
    }
    
    protected void checkEquationSatisfied(LabelEquation eqn) throws SemanticException {
        // Check that any variables that must be runtime representable are in fact so.
        for (Iterator iter = eqn.variables().iterator(); iter.hasNext();) {
            Variable v = (Variable)iter.next();
            if (v.mustRuntimeRepresentable()) {
                boolean isRuntimeRepresentable = false;
                if (v instanceof VarLabel) {
                    isRuntimeRepresentable = bounds.boundOf((VarLabel)v).isRuntimeRepresentable();
                }
                else if (v instanceof VarPrincipal) {
                    isRuntimeRepresentable = bounds.boundOf((VarPrincipal)v).isRuntimeRepresentable();                    
                }
                else {
                    throw new InternalCompilerError("Unexpected variable " + v);
                }
                if (!isRuntimeRepresentable) {
                    // a variable that must be runtime representable is not.
                    reportError(eqn.labelConstraint(), Collections.singleton(v));
                }
            }
        }

        Label lhsBound = triggerTransforms(bounds.applyTo(eqn.lhs()), eqn.env());
        Label rhsBound = triggerTransforms(bounds.applyTo(eqn.rhs()), eqn.env());

        if (shouldReport(4)) {
            report(4, "Checking equation: " + eqn);
        }

        if (shouldReport(6)) {
            report(6, "LHS = " + eqn.lhs());
            report(6, "LHS APP = " + lhsBound);
            report(6, "RHS APP = " + rhsBound);
        }

        // Check to see if it is currently satisfiable.
        if (!eqn.env().leq(lhsBound, rhsBound)) {
            reportError(eqn.labelConstraint(), eqn.variableComponents());
        }        
    }

    /**
     * Awakens all equations in the system that depend on the variable v,
     * ensuring that they are in the queue of active equations.
     */
    protected final void wakeUp(Variable v) {
        Set eqns = (Set)varEqnDependencies.get(v);

        if (eqns != null) {
            for (Iterator i = eqns.iterator(); i.hasNext();) {
                Equation eqn = (Equation)i.next();
                // if its in the current strongly connected set
                // and its not in the Queue, add it
                if (!Q.contains(eqn)
                   && (!useSCC || currentSCC.contains(eqn))) // offensive but simple
                        Q.add(eqn);
            }
        }
    }
    
    public String solverName() {
        return this.solverName;
    }

    /**
     * Counter of the number of constraints added to the system. For debugging
     * purposes.
     */
    static protected int constraint_counter = 0;

    /**
     * Increase the count of the number of constraints added (not just to this
     * system, but to all instances of the Solver).
     * 
     * For debugging purposes, if the constraint counter is equal to the
     * stop_constraint, then a RuntimeException is thrown.
     */
    protected final void inc_counter() {
        constraint_counter++;
    }

    protected boolean isFixedValueVar(Variable v) {
        return fixedValueVars.contains(v);
    }
    
    /**
     * Add the constraint c to the system
     */
    public void addConstraint(Constraint c) throws SemanticException {
        if (status != STATUS_NOT_SOLVED) {
            throw new InternalCompilerError("Computed solution already. "
                    + "Cannot add more constraints");
        }

        if (shouldReport(5)) {
            StackTraceElement[] stack = new Exception().getStackTrace();
            String source = stack[4].getFileName() + ":" + stack[4].getLineNumber();
            report(5, (constraint_counter + 1) + ": " + c + " << " + source);
        }
        if (shouldReport(6)) report(6, ">>> " + c.msg());
        inc_counter();

        if (!c.isCanonical()) {
            throw new SemanticException(errorMsg(c), c.position());
        }

        if (c instanceof LabelConstraint) {
            LabelConstraint lc = (LabelConstraint)c;
            if (lc.lhsLabel() instanceof NotTaken && lc.kind() == LabelConstraint.LEQ) {
                // if the LHS is NotTaken, then the constraint will always be
                // satisfied.
                return;
            }

            if (lc.rhsLabel() instanceof NotTaken && lc.kind() == LabelConstraint.LEQ) {
                // if the RHS is NotTaken (and the LHS isn't), then the
                // constraint can never be satisfied.
                throw new SemanticException(errorMsg(c), c.position());
            }
        }
        processConstraint(c);
        addConstraintEquations(c);
    }
    
    /**
     * Perform any special processing for the label constraint
     */
    protected void processConstraint(Constraint c) throws SemanticException {
        if (c instanceof LabelConstraint) {
            LabelConstraint lc = (LabelConstraint)c;
            if (lc.lhsLabel() instanceof VarLabel && lc.kind() == LabelConstraint.EQUAL) {
                // this is an equality constraint on a variable. Let's jump start the 
                // solving by setting it immediately
                VarLabel v = (VarLabel)lc.lhsLabel();
                Label initialBound = bounds.applyTo(lc.rhsLabel());
                addTrace(v, (Equation)lc.getEquations().iterator().next(), initialBound);
                setBound(v, initialBound, lc);
                // only add the variable to the fixed value vars if the RHS does not contain
                // any variables. Otherwise, the bound of v may need to change
                // as the RHS changes.
                if (!lc.rhsLabel().hasVariableComponents()) {
                    fixedValueVars.add(v);
                }
            }
        }       
        else if (c instanceof PrincipalConstraint) {
            PrincipalConstraint pc = (PrincipalConstraint)c;
            if ((pc.lhsPrincipal() instanceof VarPrincipal || pc.rhsPrincipal() instanceof VarPrincipal)
                    && pc.kind() == PrincipalConstraint.EQUIV) {
                // this is an equality constraint on a variable. Let's jump start the 
                // solving by setting it immediately
                
                VarPrincipal v = null;
                Principal other = null;
                if (pc.lhsPrincipal() instanceof VarPrincipal) {
                    v = (VarPrincipal)pc.lhsPrincipal();
                    other = pc.rhsPrincipal();
                }
                else {
                    v = (VarPrincipal)pc.rhsPrincipal();
                    other = pc.lhsPrincipal();
                }
                
                Principal initialBound = bounds.applyTo(other);
                setBound(v, initialBound, pc);
                // only add the variable to the fixed value vars if other does not contain
                // any variables. Otherwise, the bound of v may need to change
                // as the other changes.
                if (!other.hasVariables()) {
                    fixedValueVars.add(v);
                }
            }
        }
    }
    
    /**
     * Go through each equation in the constraint, add the equation
     * if needed, and register dependencies for the equation.
     * @param c
     * @throws SemanticException
     */
    protected void addConstraintEquations(Constraint c) throws SemanticException {
        Collection eqns = c.getEquations();
        Equation eqn = null;
        for (Iterator i = eqns.iterator(); i.hasNext();) {
            eqn = (Equation)i.next();
            LabelEnv eqnEnv = eqn.env();
            if (!eqnEnv.hasVariables() && !eqn.constraint().hasVariables()) {
                // The equation has no variables. We can check now if it is
                // satisfied or not                    
                boolean eqnSatisfied = false;
                if (eqn instanceof LabelEquation) {     
                    LabelEquation leqn = (LabelEquation)eqn;
                    eqnSatisfied = eqnEnv.leq(triggerTransforms(leqn.lhs(), eqnEnv), 
                                              triggerTransforms(leqn.rhs(), eqnEnv)); 
                }
                else if (eqn instanceof PrincipalEquation) {
                    PrincipalEquation peqn = (PrincipalEquation)eqn;
                    eqnSatisfied = eqnEnv.actsFor(peqn.lhs(), peqn.rhs());                     
                }
                else {
                    throw new InternalCompilerError("Unexpected kind of equation: " + eqn);
                }
                if (!eqnSatisfied) {                    
                    if (shouldReport(2)) {
                        report(2, "Statically failed " + eqn);
                    }
                    if (shouldReport(3) && eqn instanceof LabelEquation) {
                        report(3, "Statically failed " + triggerTransforms(((LabelEquation)eqn).lhs(), eqnEnv) + " <= " + triggerTransforms(((LabelEquation)eqn).rhs(), eqnEnv));
                    }

                    // The equation is not satisfied.
                    if (THROW_STATIC_FAILED_CONSTRAINTS) {
                        throw new SemanticException(errorMsg(c), 
                                                    eqn.position());
                    }
                    else {
                        if (staticFailedConstraints == null) {
                            staticFailedConstraints = new LinkedHashSet();
                        }
                        staticFailedConstraints.add(eqn.constraint());
                    }
                }
                else {
                    // The equation is satisfied, no need to add it to
                    // the queue.
                }
            }
            else {
                if (shouldReport(5)) report(5, "Adding equation: " + eqn);
                eqnEnv.setSolver(this);
                equations.add(eqn);
                addDependencies(eqn);
            }

        }
    }

    /**
     * This abstract method must add the correct dependencies from Equation eqn
     * to variables occurring in eqn, and dependencies in the other direction
     * (that is, from variables occurring in eqn to eqn).
     * 
     * There is a dependency from Equation eqn to variable var if the bound on
     * var may be modified as a result of solving eqn. This dependency should be
     * recorded by calling the method addDependency(eqn, var).
     * 
     * There is a dependency from variable var to Equation eqn if modifying the
     * bound on var may cause eqn to no longer be satisfied. This dependency
     * should be recorded by calling the method addDependency(var, eqn).
     */
    protected abstract void addDependencies(Equation eqn);

    /**
     * This method records a dependency from variable var to Equation eqn. This
     * method should only be called by subclasses during the execution of the
     * method addDependencies().
     * 
     * There is a dependency from variable var to Equation eqn if modifying the
     * bound on var may cause eqn to no longer be satisfied.
     */
    protected void addDependency(Variable var, Equation eqn) {
        Set eqns = (Set)varEqnDependencies.get(var);
        if (eqns == null) {
            eqns = new LinkedHashSet();
            varEqnDependencies.put(var, eqns);
        }
        eqns.add(eqn);
        
        Set vars = (Set)eqnVarReverseDependencies.get(eqn);
        if (vars == null) {
            vars = new LinkedHashSet();
            eqnVarReverseDependencies.put(eqn, vars);
        }
        vars.add(var);

    }

    /**
     * This method records a dependency from Equation eqn to variable var. This
     * method should only be called by subclasses during the execution of the
     * method addDependencies().
     * 
     * There is a dependency from Equation eqn to variable var if the bound on
     * var may be modified as a result of solving eqn.
     */
    protected void addDependency(Equation eqn, Variable var) {
        Set vars = (Set)eqnVarDependencies.get(eqn);
        if (vars == null) {
            vars = new LinkedHashSet();
            eqnVarDependencies.put(eqn, vars);
        }
        vars.add(var);
        
        Set eqns = (Set)varEqnReverseDependencies.get(var);
        if (eqns == null) {
            eqns = new LinkedHashSet();
            varEqnReverseDependencies.put(var, eqns);
        }
        eqns.add(eqn);
    }

    /**
     * Returns the equations that are dependent on the equation eqn by finding
     * the variables that eqn may alter if it is solved (useing the map
     * eqnVarDependencies), and then finds the equations that depend on those
     * variables (using the map varEqnDependencies)
     */
    protected Set eqnEqnDependencies(Equation eqn) {
        Set vars = (Set)eqnVarDependencies.get(eqn);

        if (vars == null || vars.isEmpty()) {
            return Collections.EMPTY_SET;
        }

        Set eqns = new LinkedHashSet();
        for (Iterator i = vars.iterator(); i.hasNext();) {
            VarLabel v = (VarLabel)i.next();
            Set s = (Set)varEqnDependencies.get(v);
            if (s != null) {
                eqns.addAll(s);
            }
        }
        return eqns;
    }

    /**
     * Returns the equations that are reverse dependent on the equation eqn by finding
     * the variables that may invalidate eqn (using the map
     * eqnVarReverseDependencies), and then finding the equations
     * that may alter those variables (using the map varEqnReverseDependencies)
     */
    protected Set eqnEqnReverseDependencies(Equation eqn) {
        Set vars = (Set)eqnVarReverseDependencies.get(eqn);

        if (vars == null || vars.isEmpty()) {
            return Collections.EMPTY_SET;
        }

        Set eqns = new LinkedHashSet();
        for (Iterator i = vars.iterator(); i.hasNext();) {
            VarLabel v = (VarLabel)i.next();
            Set s = (Set)varEqnReverseDependencies.get(v);
            if (s != null) {
                eqns.addAll(s);
            }
        }
        return eqns;
    }

    /**
     * Record the fact that label variable v, in the constraint eqn had its
     * bound set to the label lb.
     */
    protected final void addTrace(VarLabel v, Equation eqn, Label lb) {
        List trace = (List)traces.get(v);
        if (trace == null) {
            trace = new LinkedList();
            traces.put(v, trace);
        }
        trace.add(new Pair(eqn, lb.copy()));
    }

    /**
     * @return an equation that contained label variable var, and that changed
     *         the bound of var to less than/greater than threshold. Null if no
     *         such equation exists. If lowerThreshold is true, then the
     *         equation returned is one that changed var to less than threshold;
     *         otherwise the equation returned is one that changed var to
     *         greater than the threshold
     */
    protected final Equation findTrace(VarLabel var, Label threshold,
            boolean lowerThreshold) {
        List history = (List)traces.get(var);
        if (history != null) {
            for (Iterator iter = history.iterator(); iter.hasNext();) {
                Pair eqn_label = (Pair)iter.next();
                Label label = (Label)eqn_label.part2();
                Equation eqn = (Equation)eqn_label.part1();
                boolean test = lowerThreshold ? eqn.env().leq(threshold, label)
                        : eqn.env().leq(label, threshold);
                if (!test) {
                    return eqn;
                }
            }
        }
        return null;
    }

    /**
     * Report the traces for each variables in the collection
     * <code>Variables</code>
     */
    protected void reportTraces(Collection variables) {
        // NO TRACING FOR THE MOMENT.
//        if (shouldReport(3)) {
//            // We'll produce the traces...
//
//            StringBuffer trcs = new StringBuffer();
//            for (Iterator vs = variables.iterator(); vs.hasNext();) {
//                VarLabel v = (VarLabel)vs.next();
//
//                List trace = (List)traces.get(v);
//                if (trace != null) {
//                    StringBuffer trc = new StringBuffer("\nTrace for " + v
//                            + ":\n");
//
//                    trc.append("  initially : " + getDefaultBound() + "\n");
//                    for (int i = 0; i < trace.size(); ++i) {
//                        Pair p = (Pair)trace.get(i);
//                        Equation e = (Equation)p.part1();
//                        Label l = (Label)p.part2();
//                        trc.append("  " + i + ": " + l + " from eqn " + e.lhs()
//                                + " <= " + e.rhs() + "\n");
//                        trc.append("       from " + e.constraint());
//                        if (e.constraint().position() != null) {
//                            trc.append(" (line "
//                                    + e.constraint().position().line() + ")");
//                        }
//                        trc.append("\n");
//                    }
//                    trcs.append(trc.toString());
//                }
//            }
//
//            report(3, trcs.toString());
//        }
    }

    protected boolean errorShowConstraint() {
        return (errorShowTechnicalMsg() || errorShowDetailMsg());
    }

    protected boolean errorShowTechnicalMsg() {
        return false;
    }

    protected boolean errorShowDetailMsg() {
        return ((JifOptions)Options.global).explainErrors;
    }

    protected boolean errorShowDefns() {
        return (errorShowTechnicalMsg() || errorShowDetailMsg())
                && errorShowConstraint();
    }

    /**
     * Produce an error message for the constraint c, which cannot be satisfied.
     */
    protected final String errorMsg(Constraint c) {
        StringBuffer sb = new StringBuffer();

        if (errorShowConstraint()) {
            sb.append("Unsatisfiable constraint: \n");
            //sb.append(" \n------------------------ \n");
            sb.append(errorStringConstraint(c));
            sb.append(" \n \n");
        }

        if (errorShowDefns() && c instanceof LabelConstraint) {
            sb.append("Label Descriptions");
            sb.append(" \n------------------");
            sb.append(errorStringDefns((LabelConstraint)c));
            sb.append(" \n \n");
        }

        if (errorShowTechnicalMsg()) {
            sb.append(c.technicalMsg());
        }
        else if (errorShowDetailMsg()) {
            sb.append(c.detailMsg());
        }
        else {
            sb.append(c.msg());
        }
        return sb.toString();
    }

    /**
     * Produce a string appropriate for an error message that displays the
     * unsatisfiable constraint <code>c</code>.
     */
    protected String errorStringConstraint(Constraint c) {
        StringBuffer sb = new StringBuffer();
        if (c instanceof LabelConstraint) {
            LabelConstraint lc = (LabelConstraint)c;
            if (lc.namedLhs() != null || lc.namedRhs() != null) {
                sb.append("  ");
                sb.append(lc.namedLhs());
                sb.append(c.kind());
                sb.append(lc.namedRhs());
                sb.append(" \n");
            }
        }

        sb.append("\t");
        sb.append(bounds.applyTo(c.lhs));
        sb.append(c.kind());
        sb.append(bounds.applyTo(c.rhs));
        if (!c.env().isEmpty()) {
            sb.append(" \nin environment \n   ");
            sb.append(c.env());
        }

        return sb.toString();
    }

    /**
     * Produce a string appropriate for an error message that displays the
     * definitions needed by the unsatisfiable constraint <code>c</code>.
     */
    protected String errorStringDefns(LabelConstraint c) {
        StringBuffer sb = new StringBuffer();

        Map defns = c.definitions(bounds);
        for (Iterator iter = defns.entrySet().iterator(); iter.hasNext();) {
            Map.Entry e = (Map.Entry)iter.next();
            sb.append(" \n - ");
            sb.append((String)e.getKey());
            List l = (List)e.getValue();
            for (Iterator j = l.iterator(); j.hasNext();) {
                sb.append(" = ");
                sb.append((String)j.next());
                if (j.hasNext()) {
                    sb.append(" \n - ");
                    sb.append((String)e.getKey());
                }
            }
        }

        return sb.toString();
    }

    /**
     * Throws a SemanticException with the appropriate error message.
     * 
     * @param c The constraint that cannot be satisfied.
     * @throws SemanticException always.
     */
    protected void reportError(Constraint c, Collection variables)
            throws SemanticException {
        int count = 0;
        while (!c.report() && (count++) < 1000) {
            // we don't want to blame this constraint for the error, if
            // possible. Try to find the constraint that made this one
            // unsatisfiable.
            Equation eqn = findContradictiveEqn(c);
            if (eqn == null) {
                // we can't find a contradictive eqn. Just use this one.
                if (shouldReport(3))
                        report(3, "Could not find contradictive eqn for " + c);
                break;
            }
            if (shouldReport(3))
                    report(3, "Found contradictive eqn for " + c + "; it is "
                            + eqn);
            c = eqn.constraint();
        }

        Position pos = c.position();

        if (variables != null) reportTraces(variables);

        throw new SemanticException(errorMsg(c), pos);
    }

    protected Equation findContradictiveEqn(Constraint c) {
        if (c instanceof LabelConstraint) {
            return findContradictiveEqn((LabelConstraint)c);   
        }
        throw new InternalCompilerError("Unexpected constraint type: " + c.getClass());
    }
    protected abstract Equation findContradictiveEqn(LabelConstraint c);
    
    /** Returns the linked list [by_scc, scc_head] where
     *  by_scc is an array in which SCCs occur in topologically
     *  order. 
     *  scc_head[n] where n is the first peer in an SCC is set to -1.
     *  scc_head[n] where n is the last peer in a (non-singleton) SCC is set
     *  to the index of the first peer. Otherwise it is -2. 
     *  
     *   by_scc contains the peers grouped by SCC.
     *   scc_head marks where the SCCs are. The SCC
     *    begins with a -1 and ends with the index of
     *     the beginning of the SCC.
     *  */
    protected LinkedList findSCCs() {
        
        Equation[] sorted = new Equation[equations.size()];

        // First, topologically sort the nodes (put in postorder)
        int n = 0;
        LinkedList stack = new LinkedList();
        Set reachable = new HashSet();
        for (Iterator i = equations.iterator(); i.hasNext(); ) {
            Equation eq = (Equation)i.next();
            if (!reachable.contains(eq)) {
                reachable.add(eq);
                stack.addFirst(new Frame(eq, true));
                while (!stack.isEmpty()) {
                    Frame top = (Frame)stack.getFirst();
                    if (top.edges.hasNext()) {
                        Equation eqTo = (Equation)top.edges.next();
                        if (!reachable.contains(eqTo)) {
                            reachable.add(eqTo);
                            stack.addFirst(new Frame(eqTo, true));
                        }
                    } else {
                        stack.removeFirst();
                        sorted[n++] = top.eqn;
                    }
                }
            }
        }
        
//      Now, walk the transposed graph picking nodes in reverse
//      postorder, thus picking out one SCC at a time and
//      appending it to "by_scc".
        Equation[] by_scc = new Equation[n];
        int[] scc_head = new int[n];
        Set visited = new HashSet();
        int head = 0;
        for (int i=n-1; i>=0; i--) {
            if (!visited.contains(sorted[i])) {
                // First, find all the nodes in the SCC
                Set SCC = new HashSet();
                visited.add(sorted[i]);
                stack.add(new Frame(sorted[i], false));
                while (!stack.isEmpty()) {
                    Frame top = (Frame)stack.getFirst();
                    if (top.edges.hasNext()) {
                        Equation eqTo = (Equation)top.edges.next();
                        if (reachable.contains(eqTo) && !visited.contains(eqTo)) {
                            visited.add(eqTo);
                            Frame f = new Frame(eqTo, false);
                            stack.addFirst(f);
                        }
                    } else {
                        stack.removeFirst();
                        SCC.add(top.eqn);
                    }
                }
                // Now, topologically sort the SCC (as much as possible)
                // and place into by_scc[head..head+scc_size-1]
                stack.add(new Frame(sorted[i], true));
                Set revisited = new HashSet();
                revisited.add(sorted[i]);
                int scc_size = SCC.size();
                int nsorted = 0;
                while (stack.size() != 0) {
                    Frame top = (Frame)stack.getFirst();
                    if (top.edges.hasNext()) {
                        Equation eqTo = (Equation)top.edges.next();
                        if (SCC.contains(eqTo) && !revisited.contains(eqTo)) {
                            revisited.add(eqTo);
                            Frame f = new Frame(eqTo, true);
                            stack.addFirst(f);
                        }
                    } else {
                        stack.removeFirst();
                        int n3 = head + scc_size - nsorted - 1;
                        scc_head[n3] = -2;
                        by_scc[n3] = top.eqn;
                        nsorted++;
                    }
                }
                scc_head[head+scc_size-1] = head;
                scc_head[head] = -1;
                head = head + scc_size;
            }
        }
//        for (int j = 0; j < n; j++) {
//            switch(scc_head[j]) {
//            case -1: Report.report(2, j + "[HEAD] : " + by_scc[j]); break;
//            case -2: Report.report(2, j + "       : " + by_scc[j]); break;
//            default: Report.report(2, j + " ->"+ scc_head[j] + " : " + by_scc[j]);
//            }
//            for (Iterator i = eqnEqnDependencies(by_scc[j]).iterator(); i.hasNext(); ) {
//                Report.report(3, "     successor: " + ((Equation)i.next()));
//            }
//        }
        LinkedList ret = new LinkedList();
        ret.addFirst(scc_head);
        ret.addFirst(by_scc);
        return ret;
    }
    protected class Frame {
        Equation eqn;
        Iterator edges;
        Frame(Equation e, boolean forward) {
            eqn = e;
            if (forward) {
                edges = eqnEqnDependencies(e).iterator();
            }
            else {
                edges = eqnEqnReverseDependencies(e).iterator();
            }
        }
    }

    /**
     * A queue for equations. This class ensures that an equation
     * is in the queue at most once.
     */
    protected static class EquationQueue {
        final LinkedList list;
        final Set elements;
        public EquationQueue() {
            list = new LinkedList();
            elements = new HashSet();
        }
        public EquationQueue(Collection c) {
            list = new LinkedList(c);
            elements = new HashSet(c);
        }
        public EquationQueue(EquationQueue q) {
            list = new LinkedList(q.list);
            elements = new HashSet(q.elements);
        }
        public boolean contains(Equation eqn) {
            return elements.contains(eqn);
        }
        public void addAll(Collection c) {
            if (c != null) {
                for (Iterator iter = c.iterator(); iter.hasNext();) {
                    Equation e = (Equation)iter.next();
                    add(e);
                }
            }
        }
        public Equation removeFirst() {
            Equation e = (Equation)list.removeFirst();
            elements.remove(e);
            return e;
        }
        public boolean isEmpty() {
            return list.isEmpty();
        }
        public void add(Equation eqn) {
            if (!elements.contains(eqn)) {
                list.add(eqn);
                elements.add(eqn);
            }
        }
        public void addFirst(Equation eqn) {
            if (elements.contains(eqn)) {
                // already in the queue. remove it.
                list.remove(eqn);
            }
            list.addFirst(eqn);
            elements.add(eqn);                        
        }
    }
}
