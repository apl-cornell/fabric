package jif.visit;

import java.util.*;
import java.util.Map.Entry;

import jif.ast.DowngradeExpr;
import jif.extension.JifPreciseClassDel;
import polyglot.ast.*;
import polyglot.frontend.Job;
import polyglot.types.*;
import polyglot.visit.*;

/**
 * Visitor which determines at which program points more precise information
 * is known about the runtime class of local variables and
 * final access paths. This information is then stored in the appropriate
 * delegates. 
 */
public class PreciseClassChecker extends DataFlow
{
    public PreciseClassChecker(Job job, TypeSystem ts, NodeFactory nf) {
	super(job, ts, nf, true /* forward analysis */);
        EDGE_KEY_CLASS_CAST_EXC = null; 
    }

    public PreciseClassChecker(Job job) {
        this(job, job.extensionInfo().typeSystem(), job.extensionInfo().nodeFactory());
    }

    private FlowGraph.ExceptionEdgeKey EDGE_KEY_CLASS_CAST_EXC;
    
    public NodeVisitor begin() {
        EDGE_KEY_CLASS_CAST_EXC = new FlowGraph.ExceptionEdgeKey(typeSystem().ClassCastException());                
        return super.begin();
            
    }

    public Item createItem(FlowGraph graph, Term n) {
        return new DataFlowItem();
    }

    static class DataFlowItem extends Item {
        // Maps AccessPaths of Sets of ClassTypes
        Map classTypes;

        DataFlowItem() {
            classTypes = new HashMap();
        }
        DataFlowItem(Map classTypes) {
            this.classTypes = classTypes;
        }
        DataFlowItem(DataFlowItem d) {
            classTypes = new HashMap(d.classTypes);
        }
        public boolean equals(Object o) {
            if (o instanceof DataFlowItem) {
                return this.classTypes == ((DataFlowItem)o).classTypes || 
                       this.classTypes.equals(((DataFlowItem)o).classTypes);
            }
            return false;
        }
        public int hashCode() {
            return classTypes.hashCode();
        }
        public String toString() {
            return "[" + classTypes + "]";
        }
    }


    /**
     * Create an initial Item for the dataflow analysis. By default, the 
     * set of not null variables is empty.
     */
    protected Item createInitialItem(FlowGraph graph, Term node, boolean entry) {
        return new DataFlowItem();
    }

    protected Map flow(List inItems, List inItemKeys, FlowGraph graph, 
            Term n, boolean entry, Set edgeKeys) {
        return this.flowToBooleanFlow(inItems, inItemKeys, graph, n, entry, edgeKeys);
    }

    /**
     * If a local variable is initialized with a non-null expression, then
     * the variable is not null. If a local variable is assigned non-null
     * expression then the variable is not null; if a local variable is assigned
     * a possibly null expression, then the local variable is possibly null.
     */
    public Map flow(Item trueItem, Item falseItem, Item otherItem, 
            FlowGraph graph, Term n, boolean entry, Set succEdgeKeys) {
        DataFlowItem dfIn = (DataFlowItem)safeConfluence(trueItem, FlowGraph.EDGE_KEY_TRUE, 
                                     falseItem, FlowGraph.EDGE_KEY_FALSE,
                                     otherItem, FlowGraph.EDGE_KEY_OTHER,
                                     n, entry, graph);
        
        if (entry) {
            return itemToMap(dfIn, succEdgeKeys);
        }

        if (n instanceof Instanceof) {
            Instanceof io = (Instanceof)n;
            Expr e = io.expr();
            AccessPath ap = findAccessPathForExpr(e);
            if (ap != null) {                        
                // on the true branch of an instanceof, we know that
                // the path is in fact an instance of the compare type.
                Map trueBranch = addClass(dfIn.classTypes, ap, io.compareType().type());
                return itemsToMap(new DataFlowItem(trueBranch), 
                                  dfIn, dfIn, succEdgeKeys);
            }
        }
        else if (n instanceof Cast) {
            Cast cst = (Cast)n;
            Expr ex = cst.expr();
            AccessPath ap = findAccessPathForExpr(ex);
            if (ap != null) {                        
                // on the non-ClassCastException edges, we know that
                // the cast succeeded, and var instance is in fact an 
                // instance of the cast type.
                Map m = itemToMap(dfIn, succEdgeKeys);
                for (Iterator i = m.entrySet().iterator(); i.hasNext(); ) {
                    Map.Entry e = (Map.Entry)i.next();
                    if (!e.getKey().equals(EDGE_KEY_CLASS_CAST_EXC)) {
                        DataFlowItem df = (DataFlowItem)e.getValue();
                        e.setValue(new DataFlowItem(addClass(df.classTypes, ap, cst.castType().type())));
                    }
                }
                return m;
            }            
        }
        else if (n instanceof LocalDecl) {
            LocalDecl x = (LocalDecl)n; 
            // remove the precise class information...
            Map m = killClasses(dfIn.classTypes, new AccessPathLocal(x.localInstance()));
            return itemToMap(new DataFlowItem(m), succEdgeKeys);
        }
        else if (n instanceof Assign) {
            Assign x = (Assign)n; 
            // remove the precise class information...
            AccessPath ap = findAccessPathForExpr(x.left());
            if (ap != null) {                        
                Map m = killClasses(dfIn.classTypes, ap);
                return itemToMap(new DataFlowItem(m), succEdgeKeys);
            }                
        }
        else if (n instanceof Expr && ((Expr)n).type().isBoolean() && 
                (n instanceof Binary || n instanceof Unary)) {
            if (trueItem == null) trueItem = dfIn;
            if (falseItem == null) falseItem = dfIn;
            
            Map ret = flowBooleanConditions(trueItem, falseItem, dfIn, graph, (Expr)n, succEdgeKeys);
            if (ret == null) {
                ret = itemToMap(dfIn, succEdgeKeys);
            }
            return ret; 
        } 
        else if (n instanceof DowngradeExpr && ((Expr)n).type().isBoolean()) {
            if (trueItem == null) trueItem = dfIn;
            if (falseItem == null) falseItem = dfIn;
            return itemsToMap(trueItem, falseItem, dfIn, succEdgeKeys);
        }

        return itemToMap(dfIn, succEdgeKeys);
    }
    
    
    private Map killClasses(Map map, AccessPath ap) {
        Map m = new HashMap(map);
        boolean changed = (m.remove(ap) != null);
        if (ap instanceof AccessPathLocal) {
            // go through the map and remove any access paths rooted at this local
            for (Iterator iter = m.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry entry = (Entry)iter.next();
                AccessPath key = (AccessPath)entry.getKey();
                if (ap.equals(key.findRoot())) {
                    iter.remove();
                    changed = true;
                }
            }
            
        }
        return changed?m:map;
    }

    private Map addClass(Map map, AccessPath ap, Type type) {        
        if (!type.isClass()) {
            // don't bother adding this type.
            return map;
        }
        Map m = new HashMap(map);
        Set s = (Set)m.get(ap);
        if (s == null) {
            s = new LinkedHashSet();
        }
        else {
            s = new LinkedHashSet(s);
        }
        m.put(ap, s);
        s.add(type);
        return m;
    }

    /**
     * The confluence operator is intersection: a variable is not null only
     * if it is not null on all paths flowing in. 
     */
    protected Item confluence(List items, Term node, boolean entry, FlowGraph graph) {
        return intersect(items);
    }
        
    /**
     * Utility method takes the intersection of a List of DataFlowItems,
     * by intersecting all of their classTypes sets.
     */
    private static DataFlowItem intersect(List items) {
        // take the intersection of all the maps of the
        // DataFlowItems, by examining the smallest one

        // find the smallest Map.
        Map smallest = null;

        for (int i = 0; i < items.size(); i++) {
            Map candidate = ((DataFlowItem)items.get(i)).classTypes;
            if (candidate.isEmpty()) {
                // any intersection will be empty.
                return new DataFlowItem(Collections.EMPTY_MAP);
            }
            if (smallest == null || smallest.size() > candidate.size()) {
                smallest = candidate;
            }
        }
        
        Map intersectMap = new HashMap(smallest);
        for (int i = 0; i < items.size(); i++) {
            Map m = ((DataFlowItem)items.get(i)).classTypes;
            // go through the entries of intersectMap
            for (Iterator iter = intersectMap.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry e = (Entry)iter.next();
                if (m.containsKey(e.getKey())) {
                    Set s = new LinkedHashSet((Set)e.getValue());
                    Set t = (Set)m.get(e.getKey());
                    s.retainAll(t); // could be more precise here, a la SubtypeSet
                }
                else {
                    // no entry for the set, the intersection is empty, so remove
                    // the key.
                    iter.remove();
                }
            }
        }

        return new DataFlowItem(intersectMap);
    }

    
    /**
     * "Check" the nodes of the graph for the precise class analysis. This actually
     * consists of setting the preciseClass field in the Jif extensions to
     * nodes, so that their exceptionCheck methods can decide whether to 
     * suppress the ClassCastExceptions that they would otherwise declare
     * would be thrown.
     */
    protected void check(FlowGraph graph, Term n, boolean entry, 
            Item inItem, Map outItems) throws SemanticException {
        if (n.del() instanceof JifPreciseClassDel) {
            DataFlowItem dfi = (DataFlowItem)inItem;
            JifPreciseClassDel jpcd = (JifPreciseClassDel)n.del();
            AccessPath ap = findAccessPathForExpr(jpcd.getPreciseClassExpr());
            if (ap != null) {
                jpcd.setPreciseClass((Set)dfi.classTypes.get(ap));
            }            
        }
    }

    static AccessPath findAccessPathForExpr(Expr expr) {
        if (expr instanceof Special) {
            return new AccessPathThis();
        }
        if (expr instanceof Local) {
            return new AccessPathLocal(((Local)expr).localInstance());
        }
        if (expr instanceof Field) {            
            Field f = (Field)expr;
            if (f.flags().isFinal()) {
                AccessPath target = null;
                if (f.target() instanceof Expr) {
                    target = findAccessPathForExpr((Expr)f.target());
                }
                else if (f.target() instanceof TypeNode) {
                    target = new AccessPathClass(((TypeNode)f.target()).type());
                }
                if (target == null) return null;
                return new AccessPathFinalField(target, f.fieldInstance());
            }
        }
        if (expr instanceof DowngradeExpr) {
            DowngradeExpr de = (DowngradeExpr)expr;
            return findAccessPathForExpr(de.expr());
        }
        if (expr instanceof Cast) {
            Cast ce = (Cast)expr;
            return findAccessPathForExpr(ce.expr());
        }
        return null;
    }

    static abstract class AccessPath {
        public abstract AccessPath findRoot();
    }
    static class AccessPathLocal extends AccessPath {
        final LocalInstance li;
        public AccessPathLocal(LocalInstance li) {
            this.li = li;
        }
        public AccessPath findRoot() { return this; }
        public int hashCode() { return li.hashCode(); }
        public boolean equals(Object o) {
            return (o instanceof AccessPathLocal &&
                    ((AccessPathLocal)o).li.equals(this.li));
        }
        public String toString() { return li.name(); }
    }
    static class AccessPathFinalField extends AccessPath {
        final AccessPath target;
        final FieldInstance fi;
        public AccessPathFinalField(AccessPath target, FieldInstance fi) {
            this.target = target;
            this.fi = fi;
        }
        public AccessPath findRoot() { return target.findRoot(); }
        public int hashCode() { return fi.hashCode() + target.hashCode(); }
        public boolean equals(Object o) {
            if (o instanceof AccessPathFinalField) {
                AccessPathFinalField that = (AccessPathFinalField)o;
                return that.fi.equals(this.fi) && that.target.equals(this.target);
            }
            return false;
        }
        public String toString() { return target + "." + fi.name(); }
    }
    static class AccessPathThis  extends AccessPath { 
        public AccessPath findRoot() { return this; }
        public int hashCode() { return -45; }
        public boolean equals(Object o) {
            return (o instanceof AccessPathThis);
        }
        public String toString() { return "this"; }
    }
    static class AccessPathClass extends AccessPath {
        final Type type;
        public AccessPathClass(Type type) {
            this.type = type;
        }
        public AccessPath findRoot() { return this; }
        public int hashCode() { return type.hashCode(); }
        public boolean equals(Object o) {
            return (o instanceof AccessPathClass &&
                    ((AccessPathClass)o).type.equals(this.type));
        }
        public String toString() { return type.toString(); }
    }
}
