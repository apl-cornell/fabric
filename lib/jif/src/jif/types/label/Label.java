package jif.types.label;

import java.util.List;
import java.util.Set;

import jif.translate.JifToJavaRewriter;
import jif.types.JifContext;
import jif.types.LabelSubstitution;
import jif.types.Param;
import jif.types.PathMap;
import jif.types.hierarchy.LabelEnv;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;

/**
 * This class represents the Jif security label.
 */
public interface Label extends Param {
    /**
     * Is this label equivalent to bottom?
     * <p>
     * For example, a JoinLabel with no components would return true for this
     * method.
     */
    boolean isBottom();

    /**
     * Is this label equivalent to top?
     * <p>
     * For example, a JoinLabel with two components, one of which is Top, would
     * return true for this method.
     */
    boolean isTop();

    /**
     * Is this label invariant?
     */
    boolean isInvariant();

    /**
     * Is this label covariant?
     */
    boolean isCovariant();

//    /**
//     * Returns the join of this label and L.
//     */
//    Label join(Label L);

    /**
     * Is this label comparable to other labels?
     * <p>
     * For example, an UnknownLabel is not comparable to others, neither is a
     * VarLabel. Most other labels are.
     */
    boolean isComparable();

    String description();

    void setDescription(String d);

    /**
     * @param labelSubst The <code>LabelSubstitution</code> to apply to this
     *            label
     * @return the result of applying labelSubst to this label.
     * @throws SemanticException
     */
    Label subst(LabelSubstitution labelSubst) throws SemanticException;

    /**
     * Label check the label, which will determine how much information may be
     * gained if the label is evaluated at runtime. For example, given the
     * dynamic label {*lb}, where lb is a local variable, evaluation of this
     * label at runtime will reveal as much information as the label of lb. For
     * example, the following code is illegal, as the runtime evaluation of the
     * label reveals too much information
     * 
     * <pre>
     * 
     *  boolean{Alice:} secret = ...;
     *  final label{Alice:} lb = secret?new label{}:new label{Bob:};
     *  boolean{} leak = false;
     *  if ((*lb} &lt;= new label{}) { // evaluation of lb reveals 
     *                           // information at level {Alice:} 
     *     leak = true;
     *  } 
     *  
     * </pre>
     * 
     * @see jif.ast.Jif#labelCheck(LabelChecker)
     * @see jif.types.principal.Principal#labelCheck(JifContext, LabelChecker)
     */
    PathMap labelCheck(JifContext A, LabelChecker lc);

    /**
     * Are the components of this label enumerable?
     * <p>
     * For example, Singletons are enumerable, JoinLabels are enumerable,
     * RuntimeLabel (the label of all runtime representable components) is not
     * enumerable.
     * 
     * NOTE: The components of a label are not neccessarily stuck together with
     * a join operation. For example, the MeetLabel uses the meet operation
     * between its components.
     */
    boolean isEnumerable();

    /**
     * Are the components of this label all disambiguated?
     */
    boolean isDisambiguated();

    /**
     * Retrieve the collection of components. This method should only be called
     * if isEnumerable returns true.
     * 
     * This collection should not be modified.
     */
//    Collection components();

//    /**
//     * Does this label represent only a single label?
//     * <p>
//     * For example, a JoinLabel with more than one component returns false, a
//     * MeetLabel with more than one component returns false, most other Labels
//     * return true.
//     */
//    boolean isSingleton();

//    /**
//     * Retrieve the singleton component that this label represents. Should only
//     * be called is isSingleton returns true.
//     */
//    Label singletonComponent();

    /**
     * Simplify the label, using leq if needed
     */
    Label simplify();
    
    /**
     * Normalize the label. Essentially, simplify as much as possible without
     * using the leq ordering
     */
    Label normalize();

    /**
     * Does the label contain any writersToReaders constructs?
     */
    boolean hasWritersToReaders();
    
    /**
     * Does the label contain any variables as components? This does not include variables
     * that are in bounds of arg labels.
     */
    boolean hasVariableComponents();

    /**
     * Does the label contain any variables at all? This includes variables
     * that are in bounds of arg labels.
     */
    boolean hasVariables();

    /**
     * The set of variables that this label contains as components. This
     * is a subset of variables(), since it does not count var labels contained
     * in upper bounds of arg labels.
     */
    Set variableComponents();
    
    /**
     * The set of variables that this label contains including variables contained
     * in upper bounds of arg labels.
     */
    Set variables();

    /**
     * Implementation of leq, should only be called by JifTypeSystem
     * 
     * @param L the label to determine if this label is leq to. This label
     *            always satisfies !this.equals(L)
     * @param H the label environment (including principal hierarchy). Will
     *            always be non-null.
     */
    boolean leq_(Label L, LabelEnv H, LabelEnv.SearchState state);

    boolean isRuntimeRepresentable();

    ConfPolicy confProjection();
    IntegPolicy integProjection();
    
    /**
     * If the label is runtime representable, when it is evaluated at runtime it
     * may throw exceptions. This method returns a list of the exceptions that
     * the runtime evaluation of the label may produce. If the label cannot be
     * evaluated at runtime, an empty list should be returned.
     */
    List throwTypes(TypeSystem ts);

    Expr toJava(JifToJavaRewriter rw) throws SemanticException;

    String componentString();

    String componentString(Set printedLabels);

    String toString(Set printedLabels);    
}