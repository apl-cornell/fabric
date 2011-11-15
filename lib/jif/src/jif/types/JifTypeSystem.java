package jif.types;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import jif.extension.LabelTypeCheckUtil;
import jif.translate.PrincipalToJavaExpr;
import jif.types.hierarchy.LabelEnv;
import jif.types.label.*;
import jif.types.principal.*;
import polyglot.ext.param.types.PClass;
import polyglot.ext.param.types.ParamTypeSystem;
import polyglot.types.*;
import polyglot.util.Position;

/** Jif type system.
 */
public interface JifTypeSystem extends ParamTypeSystem
{
    // Type constructors

    /** Returns the "label" type. */
    PrimitiveType Label();

    /**
     * Returns the name of the "principal" type. In Jif, this is
     * "jif.lang.Principal". In languages that extend Jif, this may be different.
     */
    String PrincipalClassName();
    
    /**
     * Returns the name of the PrincipalUtil class. In Jif, this is
     * "jif.lang.PrincipalUtil". In languages that extend Jif, this may be
     * different.
     */
    String PrincipalUtilClassName();
    
    /**
     * Returns the name of the "label" type. In Jif, this is
     * "jif.lang.Label". In languages that extend Jif, this may be different.
     */
    String LabelClassName();

    /**
     * Returns the name of the LabelUtil class. In Jif, this is
     * "jif.lang.LabelUtil". In languages that extend Jif, this may be different.
     */
    String LabelUtilClassName();

    /**
     * Returns the name of the Jif runtime package. In Jif, this is
     * "jif.runtime". In languages that extend Jif, this may be different.
     */
    String RuntimePackageName();

    /** Returns the "principal" type. */
    PrimitiveType Principal();
    
    /** Returns the class jif.lang.Principal. */
    Type PrincipalClass();

    /** Returns a labeled type, type{label}. */
    LabeledType labeledType(Position pos, Type type, Label label);

    ClassType nullInstantiate(Position pos, PClass pc);
    
    /** Constructs a parameter instance for a class parameter declaration */
    ParamInstance paramInstance(Position pos, JifClassType container,
				ParamInstance.Kind kind, String name);

    /** Constructs a principal instance for an external principal. */
    PrincipalInstance principalInstance(Position pos,
	                                ExternalPrincipal principal);

    /* constant array constructors */
    ConstArrayType constArrayOf(Type type);

    ConstArrayType constArrayOf(Position pos, Type type);

    ConstArrayType constArrayOf(Type type, int dims);

    ConstArrayType constArrayOf(Position pos, Type type, int dims);
    ConstArrayType constArrayOf(Position position, Type type, int dims, boolean castableToNonConst);
    ConstArrayType constArrayOf(Position position, Type type, int dims, boolean castableToNonConst, boolean recurseIntoBaseType);
    
    JifMethodInstance jifMethodInstance(Position pos,
            ReferenceType container,
            Flags flags,
            Type returnType,
            String name,
            Label startLabel,
            boolean isDefaultStartLabel,
            List formalTypes, List formalArgLabels,
            Label endLabel,
            boolean isDefaultEndLabel,
            List excTypes,
            List constraints);
    
    /** Tests if the type is "principal". */
    boolean isPrincipal(Type t);

    /** Tests if the type is "label". */
    boolean isLabel(Type t);

    // Path and path map constructors

    PathMap pathMap();
    PathMap pathMap(Path path, Label L);
    ExceptionPath exceptionPath(Type type);
    Path gotoPath(polyglot.ast.Branch.Kind kind, String target);

    // Param constructors

    Param unknownParam(Position pos);

    // Principal constructors

    ParamPrincipal principalParam(Position pos, ParamInstance pi);
    DynamicPrincipal dynamicPrincipal(Position pos, AccessPath path);
    ExternalPrincipal externalPrincipal(Position pos, String name);
    UnknownPrincipal unknownPrincipal(Position pos);
    TopPrincipal topPrincipal(Position pos);
    BottomPrincipal bottomPrincipal(Position pos);
    Principal conjunctivePrincipal(Position pos, Principal conjunctLeft, Principal conjunctRight);
    Principal conjunctivePrincipal(Position pos, Collection principals);
    Principal disjunctivePrincipal(Position pos, Principal disjunctLeft, Principal disjunctRight);
    Principal disjunctivePrincipal(Position pos, Collection principals);
    Principal pathToPrincipal(Position pos, AccessPath path);

    VarPrincipal freshPrincipalVariable(Position pos, String s, String description);

    // Label constructors
    VarLabel freshLabelVariable(Position pos, String s, String description);

    Label topLabel(Position pos);
    Label bottomLabel(Position pos);
    Label noComponentsLabel(Position pos);
    Label notTaken(Position pos);

    Label topLabel();
    Label bottomLabel();
    Label noComponentsLabel();
    Label notTaken();

    /* Label methods */
    CovariantParamLabel covariantLabel(Position pos, ParamInstance pi);
    ParamLabel paramLabel(Position pos, ParamInstance pi);
    DynamicLabel dynamicLabel(Position pos, AccessPath path);
    ArgLabel argLabel(Position pos, LocalInstance li, CodeInstance ci);
    ArgLabel argLabel(Position pos, ParamInstance li);
    Label callSitePCLabel(JifProcedureInstance pi);
    ThisLabel thisLabel(Position pos, JifClassType ct);
    ThisLabel thisLabel(JifClassType ct);
    ThisLabel thisLabel(ArrayType ct);
    UnknownLabel unknownLabel(Position pos);
    PairLabel pairLabel(Position pos, ConfPolicy confPol, IntegPolicy integPol);
    WritersToReadersLabel writersToReadersLabel(Position pos, Label L);
    Label pathToLabel(Position pos, AccessPath path);

    ReaderPolicy readerPolicy(Position pos, Principal owner, Principal reader);
    ReaderPolicy readerPolicy(Position pos, Principal owner, Collection readers);
    WriterPolicy writerPolicy(Position pos, Principal owner, Principal writer);
    WriterPolicy writerPolicy(Position pos, Principal owner, Collection writers);
    ConfPolicy bottomConfPolicy(Position pos);
    IntegPolicy bottomIntegPolicy(Position pos);
    ConfPolicy topConfPolicy(Position pos);
    IntegPolicy topIntegPolicy(Position pos);

    
    /** Returns true iff L1 <= L2 in the empty environment. */
    boolean leq(Label L1, Label L2);

    /** Returns true iff p actsfor q in the empty environment. */
    boolean actsFor(Principal p, Principal q);

    /** Returns the join of L1 and L2. */
    Label join(Label L1, Label L2);
    Label joinLabel(Position pos, Set components);

    /** Returns the meet of L1 and L2. */
    Label meet(Label L1, Label L2);
    Label meetLabel(Position pos, Set components);

    /* methods for policies */
    boolean leq(Policy p1, Policy p2);
    ConfPolicy joinConfPolicy(Position pos, Set components);
    IntegPolicy joinIntegPolicy(Position pos, Set components);
    ConfPolicy meetConfPolicy(Position pos, Set components);
    IntegPolicy meetIntegPolicy(Position pos, Set components);
    ConfPolicy join(ConfPolicy p1, ConfPolicy p2);
    ConfPolicy meet(ConfPolicy p1, ConfPolicy p2);
    IntegPolicy join(IntegPolicy p1, IntegPolicy p2);
    IntegPolicy meet(IntegPolicy p1, IntegPolicy p2);
    
    ConfPolicy confProjection(Label L);
    IntegPolicy integProjection(Label L);
    
    /** Construct an acts-for constraint. */
    ActsForConstraint actsForConstraint(Position pos, Principal actor, Principal granter, boolean isEquiv);

    /** Construct an acts-for constraint. */
    LabelLeAssertion labelLeAssertion(Position pos, Label lhs, Label rhs);

    /** Construct an authority constraint. */
    AuthConstraint authConstraint(Position pos, List principals);

    /** Construct a caller constraint. */
    CallerConstraint callerConstraint(Position pos, List principals);

    /** Construct an autoendorse constraint. */
    AutoEndorseConstraint autoEndorseConstraint(Position pos, Label endorseTo);

    /** Get the label of the field, folding in the PC if appropriate. */
    Label labelOfField(FieldInstance vi, Label pc);

    /** Get the label of the local, folding in the PC if appropriate. */
    Label labelOfLocal(LocalInstance vi, Label pc);

    /** Get the label of the type, or bottom if unlabeled */
    Label labelOfType(Type type);

    /** Get the label of the type, or <code>defaultLabel</code> if unlabeled. */
    Label labelOfType(Type type, Label defaultLabel);

    /** Remove the label from a type, if any. */
    Type unlabel(Type type);

    /** Returns true if the type is labeled. */
    boolean isLabeled(Type type);

    /**
     * Returns true if the type is a Jif class (will return false if the type
     * is just a jif signature for a java class).
     */
    boolean isJifClass(Type t);
    
    /**
     * Returns true if the type is a Jif class, or if it is a non-Jif class
     * that represents parameters at runtime.
     */
    boolean isParamsRuntimeRep(Type t);    


    /**
     * Check if the class has an untrusted non-jif ancestor.
     *
     * An untrusted non-jif ancestor is any non-jif
     * ancestor that is not one of java.lang.Object, java.lang.Throwable,
     * java.lang.Error, java.lang.Exception, java.lang.IllegalArgumentException,
     * java.lang.IllegalStateException, java.lang.IndexOutOfBoundsException,
     * java.lang.RuntimeException or java.lang.SecurityException.

     *
     * @param t Type to check
     * @return null if ct has no untrusted non-Jif ancestor, and the
     *  ClassType of an untrusted non-Jif ancestor otherwise.
     *
     */
    ClassType hasUntrustedAncestor(Type t);

    /**
     * Exposes utility method of TypeSystem_c
     */
    List abstractSuperInterfaces(ReferenceType rt);

    /**
     * Exposes utility method of TypeSystem_c
     */
    boolean isAccessible(MemberInstance mi, ClassType contextClass);

    /** Returns a new label constraint system solver. */
    Solver createSolver(String solverName);
    
    LabelEnv createLabelEnv();

    DefaultSignature defaultSignature();

    /**
     * Compares t1 to t2 without stripping off all the parameters and labels
     */
    boolean equalsNoStrip(TypeObject t1, TypeObject t2);
    /**
     * Compares t1 to t2, stripping off all the parameters and labels
     */
    boolean equalsStrip(TypeObject t1, TypeObject t2);

    LabelTypeCheckUtil labelTypeCheckUtil();

    /**
     * Is the string s a special marker field name?
     */
    boolean isMarkerFieldName(String s);

    /**
     * @return class for translating conjunctive principals to java expressions
     */
    PrincipalToJavaExpr conjunctivePrincipalTranslator();
    /**
     * @return class for translating disjunctive principals to java expressions
     */
    PrincipalToJavaExpr disjunctivePrincipalTranslator();
}
