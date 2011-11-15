package jif.ast;

import java.util.*;

import jif.types.*;
import jif.types.label.*;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * This class contains a number of static utility methods to help instantiate
 * labels, principals and types. Instantiation includes:
 * <ul>
 * <li>the substitution of actual parameters for formal parameters in
 * parametric types;
 * <li>the substitution of receiver labels for the "this" label;
 * <li>the substitution of actual arg labels for the formal arg labels;
 * <li>the substitution of the receiver expression for dynamic labels and
 * principals mentioning the "this" access path;
 * <li>the substitution of the actual argument expressions for dynamic labels
 * and principals mentioning formal arguments in their access path.
 * </ul>
 */
public class JifInstantiator
{
    private final JifTypeSystem ts;
    private final ReferenceType receiverType;
    private final Label receiverLbl;
    private final AccessPath receiverPath;
    private final List formalArgLabels; 
    private final List formalArgTypes; 
    private final List actualArgLabels; 
    private final List actualArgExprs;
    private final List actualParamLabels; 
    private final JifContext callerContext;
    
    // temp labels and paths
    private final List formalTempLabels;
    private final List formalTempAccessPathRoots;
    private final AccessPathRoot tempThisRoot;
    private final Label tempThisLbl;
    
    private JifInstantiator(ReferenceType receiverType,
                            Label receiverLbl,
                            AccessPath receiverPath,
                            List formalArgLabels,
                            List formalArgTypes,
                            List actualArgLabels,
                            List actualArgExprs,
                            List actualParamLabels,
                            JifContext callerContext) {
        this.callerContext = callerContext;
        this.receiverType = receiverType;
        this.receiverLbl = receiverLbl;
        this.receiverPath = receiverPath;
        this.formalArgLabels = formalArgLabels;
        this.formalArgTypes = formalArgTypes;
        this.actualArgLabels = actualArgLabels;
        this.actualArgExprs = actualArgExprs;
        this.actualParamLabels = actualParamLabels;
        
        this.ts = (JifTypeSystem)callerContext.typeSystem();
        
        if (formalArgLabels != null) {
            this.formalTempAccessPathRoots = new ArrayList(formalArgLabels.size());
            this.formalTempLabels = new ArrayList(formalArgLabels.size());
            for (int i = 0; i < formalArgLabels.size(); i++) {
                Label t = ts.unknownLabel(Position.compilerGenerated());
                t.setDescription("temp formal arg " + i);
                formalTempLabels.add(t);
                formalTempAccessPathRoots.add(new AccessPathUninterpreted("temp arg path", 
                                                                          Position.compilerGenerated(),
                                                                          true));
            }
        }
        else {
            this.formalTempAccessPathRoots = null;
            this.formalTempLabels = null;            
        }
        this.tempThisLbl = ts.unknownLabel(Position.compilerGenerated());
        this.tempThisLbl.setDescription("temp this");
        this.tempThisRoot = new AccessPathUninterpreted("temp this", 
                                                        Position.compilerGenerated(),
                                                        true);  
        
    }
    

    // replace the formal argLabels, formal arg 
    // AccessPathRoots, the "this" label, and the "this"
    // access path root with appropriate temporary values.
    private Object substTempsForFormals(Object L, Position pos) {
        if (L == null) return null;
                
        // formal argLabels to formalTempLabels
        for (int i = 0; formalArgLabels != null && i < formalArgLabels.size(); i++) {
            Label temp = (Label)formalTempLabels.get(i);
            ArgLabel formalArgLbl = (ArgLabel)formalArgLabels.get(i);            
            try {
                L = substImpl(L, new LabelInstantiator(formalArgLbl, temp, false));
            }
            catch (SemanticException e) {
                throw new InternalCompilerError("Unexpected SemanticException " +
                                                "during label substitution: " + e.getMessage(), pos);
            }
        }            

        // formal this label to temp this label
        try {
            L = substImpl(L, new ThisLabelInstantiator(tempThisLbl));
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("Unexpected SemanticException " +
                                            "during label substitution: " + e.getMessage(), pos);
        }
        
        // formal arg access paths to temp access paths
        for (int i = 0; formalArgLabels != null && i < formalArgLabels.size(); i++) {
            try {
                ArgLabel formalArgLbl = (ArgLabel)formalArgLabels.get(i);            
                if (formalArgLbl.formalInstance().flags().isFinal()) {
                    AccessPathRoot formalRoot = (AccessPathRoot)JifUtil.varInstanceToAccessPath(formalArgLbl.formalInstance(), formalArgLbl.name(), formalArgLbl.position());
                    AccessPathRoot tempRoot = (AccessPathRoot)formalTempAccessPathRoots.get(i);

                    L = substImpl(L, new AccessPathInstantiator(formalRoot, tempRoot));
                }
            }
            catch (SemanticException e) {
                throw new InternalCompilerError("Unexpected SemanticException " + e.getMessage(), pos);
            }
        }            


        // formal this access path to temp this access path
        if (receiverType != null && receiverType.isClass()) {
            AccessPathRoot formalThisRoot = new AccessPathThis(receiverType.toClass(), receiverType.position());
            try {
                L = substImpl(L, new AccessPathInstantiator(formalThisRoot, tempThisRoot));
            }
            catch (SemanticException e) {
                throw new InternalCompilerError("Unexpected SemanticException " + e.getMessage(), pos);
            }
        }
        
        return L;
        
    }
    
    private Object instantiateImpl(Object L, Position pos) {
        if (L == null) return L;
        
        // now go through and substitute things...

        // this label and params
        ThisLabelAndParamInstantiator labelInstantiator = new ThisLabelAndParamInstantiator();
        try {
            L = substImpl(L, labelInstantiator);
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("Unexpected SemanticException " +
                                            "during label substitution: " + e.getMessage(), pos);
        }
                
        // this access path
        try {                
            L = substImpl(L, new AccessPathInstantiator(tempThisRoot, receiverPath));
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("Unexpected SemanticException " +
                                            "during label substitution: " + e.getMessage(), pos);
        }
        
        // replace arg labels
        for (int i = 0; formalTempLabels != null && i < formalTempLabels.size(); i++) {
            Label formalArgTempLbl = (Label)formalTempLabels.get(i);
            if (actualArgLabels != null) {
                Label actualArgLbl = (Label)actualArgLabels.get(i);
                try {
                    L = substImpl(L, new ExactLabelInstantiator(formalArgTempLbl, actualArgLbl));
                }
                catch (SemanticException e) {
                    throw new InternalCompilerError("Unexpected SemanticException " +
                                                    "during label substitution: " + e.getMessage(), pos);
                }
            }

            // arg access paths
            if (actualArgExprs != null) {
                try {
                    Expr actualExpr = (Expr)actualArgExprs.get(i);
                    Type formalArgType = (Type)formalArgTypes.get(i);
                    AccessPath target;
                    if (JifUtil.isFinalAccessExprOrConst(ts, actualExpr, formalArgType)) {
                        target = JifUtil.exprToAccessPath(actualExpr, formalArgType, callerContext);
                    }
                    else {
                        target = new AccessPathUninterpreted(actualExpr, actualExpr.position());                            
                    }
                    
                    AccessPathRoot formalTempRoot = (AccessPathRoot)formalTempAccessPathRoots.get(i);
                    
                    L = substImpl(L, new AccessPathInstantiator(formalTempRoot, target));                    
                }
                catch (SemanticException e) {
                    throw new InternalCompilerError("Unexpected SemanticException " +
                                                    "during label substitution: " + e.getMessage(), pos);
                }
            }
        }
        
                        
        // param arg labels
        // they only occur in static methods
        // of parameterized classes, but no harm in always instantiating them.
        if (actualParamLabels != null && !actualParamLabels.isEmpty() &&
                receiverType != null) {
            // go through the formal params, and the actual param labels.
            JifSubstType jst = (JifSubstType)receiverType;
            JifPolyType jpt = (JifPolyType)jst.base();
            Iterator iFormalParams = jpt.params().iterator();
            Iterator iActualParamLabels = actualParamLabels.iterator();
            
            // go through each formal and actual param, and make substitutions.
            if (jpt.params().size() != actualParamLabels.size()) {
                throw new InternalCompilerError("Inconsistent sizes for params. Error, please contact a Jif developer");
            }
            while (iActualParamLabels.hasNext()) {
                Label actualParamLabel = (Label)iActualParamLabels.next();                    
                ParamInstance pi = (ParamInstance)iFormalParams.next();
                ArgLabel paramArgLabel = ts.argLabel(pi.position(), pi);
                paramArgLabel.setUpperBound(ts.topLabel());
                try {
                    L = substImpl(L, new LabelInstantiator(paramArgLabel, actualParamLabel));
                }
                catch (SemanticException e) {
                    throw new InternalCompilerError("Unexpected SemanticException " +
                                                    "during label substitution: " + e.getMessage(), pos);
                }                    
            }
            if (iActualParamLabels.hasNext() || iFormalParams.hasNext()) {
                throw new InternalCompilerError("Inconsistent param lists");
            }
        }

        
        // check if L is ill-formed
        try {
            substImpl(L, new CheckLeftOvers());
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("Unexpected SemanticException " +
                                            "during label substitution: " + e.getMessage(), pos);
        }
        
        
        return L;
    }
    
    private Object substImpl(Object o, LabelSubstitution lblsubst) throws SemanticException {
        if (o instanceof Principal) {
            return ((Principal)o).subst(lblsubst);
        }
        return ((Label)o).subst(lblsubst);
    }
    public Principal instantiate(Principal p) {
        p = (Principal)substTempsForFormals(p, p.position());
        return (Principal)instantiateImpl(p, p.position());
    }
    public Label instantiate(Label L) {
        L = (Label)substTempsForFormals(L, L.position());
        return (Label)instantiateImpl(L, L.position());
    }

    public Type instantiate(Type t) {
        if (t instanceof ArrayType) {
            ArrayType at = (ArrayType)t;
            Type baseType = at.base();
            t = at.base(instantiate(baseType));
        }
        
        if (ts.isLabeled(t)) {
            Label newL = instantiate(ts.labelOfType(t));
            Type newT = instantiate(ts.unlabel(t));
            return ts.labeledType(t.position(), newT, newL);
        }
        
        // t is unlabeled
        if (t instanceof JifSubstType) {
            JifSubstType jit = (JifSubstType)t;
            Map newMap = new HashMap();
            boolean diff = false;
            for (Iterator i = jit.entries(); i.hasNext();) {
                Map.Entry e = (Map.Entry)i.next();
                Object arg = e.getValue();
                Param p;
                if (arg instanceof Label) {
                    p = instantiate((Label)arg);
                }
                else if (arg instanceof Principal) {
                    p = instantiate((Principal)arg);
                }
                else {
                    throw new InternalCompilerError(
                        "Unexpected type for entry: "
                            + arg.getClass().getName());
                }
                newMap.put(e.getKey(), p);

                if (p != arg)
                    diff = true;
            }
            if (diff) {
                t = ts.subst(jit.base(), newMap);
            }
        }
        
        return t;        
    }
    
    /**
     * Replaces the temp "this" label with receiverLabel, and uses
     * receiverType to perform substitution of actual parameters for formal 
     * parameters of a parameterized type.
     */
    private class ThisLabelAndParamInstantiator extends LabelSubstitution {        
        public Label substLabel(Label L) {
            Label result = L;
            if (receiverLbl != null && result == tempThisLbl) {
                result = receiverLbl;
            }

            if (receiverType instanceof JifSubstType) {
                JifSubstType t = (JifSubstType)receiverType;
                result = ((JifSubst)t.subst()).substLabel(result);
            }

            return result;
        }

        public Principal substPrincipal(Principal p) {
            if (receiverType instanceof JifSubstType) {
                JifSubst subst = (JifSubst) ((JifSubstType)receiverType).subst();
                return subst.substPrincipal(p);
            }
            return p;
        }

    }

    /**
     * Check there are no temp labels or access paths still hanging around
     */
    private class CheckLeftOvers extends LabelSubstitution {
        Set thisClasses = new HashSet();
        public Label substLabel(Label L) {
            if (L instanceof ThisLabel) {
                ThisLabel tl = (ThisLabel)L;
                if (!thisClasses.contains(tl.classType()) && !thisClasses.isEmpty()) {
                    throw new InternalCompilerError("multiple this classes: " + L);                    
                }
                thisClasses.add(tl.classType());
                
            }

            if (formalTempLabels != null && formalTempLabels.contains(L)) {
                throw new InternalCompilerError("Left over: " + L);                
            }
            return L;
        }
        public AccessPath substAccessPath(AccessPath ap) {            
            AccessPathRoot root = ap.root();
            if (tempThisRoot == root) {
                throw new InternalCompilerError("Left over: " + ap);
            }
            if (formalTempAccessPathRoots != null && formalTempAccessPathRoots.contains(root)) {
                throw new InternalCompilerError("Left over: " + ap);
            }
            return ap;
        }

    }
    
    /**
     * Replaces L with trgLabel if srcLabel.equals(L) 
     */
    private static class LabelInstantiator extends LabelSubstitution {
        private Label srcLabel;
        private Label trgLabel;
        private boolean recurseArgLabelBounds;
        protected LabelInstantiator(Label srcLabel, Label trgLabel) {
            this(srcLabel, trgLabel, true);
        }
        protected LabelInstantiator(Label srcLabel, Label trgLabel, boolean recurseArgLabelBounds) {
            this.srcLabel = srcLabel;
            this.trgLabel = trgLabel;
            this.recurseArgLabelBounds = recurseArgLabelBounds;
        }
        
        public Label substLabel(Label L) {
            if (srcLabel.equals(L)) {
                return trgLabel;
            }
            return L;
        }

        public boolean recurseIntoChildren(Label L) {
            return recurseArgLabelBounds || !(L instanceof ArgLabel);
        }        
    }

    /**
     * Replaces L with trgLabel if srcLabel == L 
     */
    private static class ExactLabelInstantiator extends LabelSubstitution {
        private Label srcLabel;
        private Label trgLabel;
        protected ExactLabelInstantiator(Label srcLabel, Label trgLabel) {
            this.srcLabel = srcLabel;
            this.trgLabel = trgLabel;
        }
        
        public Label substLabel(Label L) {
            if (srcLabel == L) {
                return trgLabel;
            }
            return L;
        }
    }

    /**
     * Replaces all ThisLabels with trgLabel 
     */
    private static class ThisLabelInstantiator extends LabelSubstitution {
        private Label trgLabel;
        protected ThisLabelInstantiator(Label trgLabel) {
            this.trgLabel = trgLabel;
        }
        
        public Label substLabel(Label L) {
            if (L instanceof ThisLabel) {
                return trgLabel;
            }
            return L;
        }
    }
    
    /**
     * Replaces srcRoot with trgPath in dynamic labels and principals 
     */
    private static class AccessPathInstantiator extends LabelSubstitution {
        private AccessPathRoot srcRoot;
        private AccessPath trgPath;
        protected AccessPathInstantiator(AccessPathRoot srcRoot, AccessPath trgPath) {
            this.srcRoot = srcRoot;
            this.trgPath = trgPath;
        }
        
        public AccessPath substAccessPath(AccessPath ap) {            
            if (ap.root().equals(srcRoot))
                return ap.subst(srcRoot, trgPath);
            return ap;
        }
    }

    public static Label instantiate(Label L, 
                                    JifContext callerContext, 
                                    Expr receiverExpr, 
                                    ReferenceType receiverType, 
                                    Label receiverLabel, 
                                    List formalArgLabels, 
                                    List formalArgTypes,
                                    List actualArgLabels, 
                                    List actualArgExprs, 
                                    List actualParamLabels) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem)callerContext.typeSystem();
        AccessPath receiverPath;
        if (JifUtil.isFinalAccessExprOrConst(ts, receiverExpr, receiverType)) {
            receiverPath = JifUtil.exprToAccessPath(receiverExpr, receiverType, callerContext);
        }
        else {
            receiverPath = new AccessPathUninterpreted(receiverExpr, L.position()); 
        }
        JifInstantiator inst = new JifInstantiator(receiverType,
                                                   receiverLabel,
                                                   receiverPath,
                                                   formalArgLabels,
                                                   formalArgTypes,
                                                   actualArgLabels,
                                                   actualArgExprs,
                                                   actualParamLabels,
                                                   callerContext);
        return inst.instantiate(L);
    }

    public static Label instantiate(Label L, 
                                    JifContext callerContext, 
                                    Expr receiverExpr, 
                                    ReferenceType receiverType, 
                                    Label receiverLbl) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem)callerContext.typeSystem();
        AccessPath receiverPath;
        if (JifUtil.isFinalAccessExprOrConst(ts, receiverExpr, receiverType)) {
            receiverPath = JifUtil.exprToAccessPath(receiverExpr, receiverType, callerContext);
        }
        else {
            receiverPath = new AccessPathUninterpreted(receiverExpr, L.position()); 
        }
        return instantiate(L, callerContext, receiverPath, receiverType, receiverLbl);
    }
    
    public static Label instantiate(Label L, 
                                    JifContext callerContext, 
                                    AccessPath receiverPath, 
                                    ReferenceType receiverType, 
                                    Label receiverLbl) {        
        JifInstantiator inst = new JifInstantiator(receiverType,
                                                   receiverLbl,
                                                   receiverPath,
                                                   null,
                                                   null,
                                                   null,
                                                   null,
                                                   null,
                                                   callerContext);
        return inst.instantiate(L);

    }
    
    public static Type instantiate(Type t, 
            JifContext callerContext, 
            AccessPath receiverPath, 
            ReferenceType receiverType, 
            Label receiverLbl) {        
        JifInstantiator inst = new JifInstantiator(receiverType,
                receiverLbl,
                receiverPath,
                null,
                null,
                null,
                null,
                null,
                callerContext);
        return inst.instantiate(t);
    }
    
    
    public static Principal instantiate(Principal p, 
            JifContext callerContext, 
            Expr receiverExpr, 
            ReferenceType receiverType, 
            Label receiverLabel, 
            List formalArgLabels, 
            List formalArgTypes,
            List actualArgExprs, 
            List actualParamLabels) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem)callerContext.typeSystem();
        AccessPath receiverPath;
        if (JifUtil.isFinalAccessExprOrConst(ts, receiverExpr, receiverType)) {
            receiverPath = JifUtil.exprToAccessPath(receiverExpr, receiverType, callerContext);
        }
        else {
            receiverPath = new AccessPathUninterpreted(receiverExpr, p.position()); 
        }
        JifInstantiator inst = new JifInstantiator(receiverType,
                                                   receiverLabel,
                                                   receiverPath,
                                                   formalArgLabels,
                                                   formalArgTypes,
                                                   null,
                                                   actualArgExprs,
                                                   actualParamLabels,
                                                   callerContext);
        return inst.instantiate(p);
    }


    public static Type instantiate(Type t, 
            JifContext callerContext, 
            Expr receiverExpr, 
            ReferenceType receiverType, 
            Label receiverLabel, 
            List formalArgLabels, 
            List formalArgTypes,
            List actualArgLabels, 
            List actualArgExprs, 
            List actualParamLabels) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem)callerContext.typeSystem();
        AccessPath receiverPath;
        if (JifUtil.isFinalAccessExprOrConst(ts, receiverExpr, receiverType)) {
            receiverPath = JifUtil.exprToAccessPath(receiverExpr, receiverType, callerContext);
        }
        else {
            receiverPath = new AccessPathUninterpreted(receiverExpr, t.position()); 
        }
        JifInstantiator inst = new JifInstantiator(receiverType,
                                                   receiverLabel,
                                                   receiverPath,
                                                   formalArgLabels,
                                                   formalArgTypes,
                                                   actualArgLabels,
                                                   actualArgExprs,
                                                   actualParamLabels,
                                                   callerContext);
        return inst.instantiate(t);
    }


    public static Type instantiate(Type t, 
            JifContext callerContext, 
            Expr receiverExpr, 
            ReferenceType receiverType, 
            Label receiverLbl) throws SemanticException {
        
        JifTypeSystem ts = (JifTypeSystem)callerContext.typeSystem();
        AccessPath receiverPath;
        if (JifUtil.isFinalAccessExprOrConst(ts, receiverExpr, receiverType)) {
            receiverPath = JifUtil.exprToAccessPath(receiverExpr, receiverType, callerContext);
        }
        else {
            receiverPath = new AccessPathUninterpreted(receiverExpr, t.position()); 
        }
        JifInstantiator inst = new JifInstantiator(receiverType,
                                                   receiverLbl,
                                                   receiverPath,
                                                   null,
                                                   null,
                                                   null,
                                                   null,
                                                   null,
                                                   callerContext);
        return inst.instantiate(t);
        
    }
}
