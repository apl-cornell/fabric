package jif.types;

import java.util.*;

import jif.types.label.Label;
import jif.types.label.NotTaken;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.CodeWriter;

/**
 * Implements the mapping from paths to labels.  All updates are functional, so
 * that old path maps are still accessible.  This means that all updates, etc.
 * create clones of the pathmap.  There may be a more efficient way to do this
 * sharing, with "lazy" copying of the necessary entries, but this is easy to
 * implement.
 */
public class PathMap
{
    protected Map map;
    protected JifTypeSystem ts;

    public PathMap(JifTypeSystem ts) {
        this.ts = ts;
        this.map = new HashMap(5);
    }

    public Label get(Path p) {
        Label l = (Label) map.get(p);
        if (l == null) return ts.notTaken();
        return l;
    }

    public PathMap set(Path p, Label L) {
        PathMap n = new PathMap(ts);
        n.map.putAll(map);

        if (L instanceof NotTaken) {
            n.map.remove(p);
        }
        else {
            n.map.put(p, L);
        }

        return n;
    }

    public Label N() { return get(Path.N); }
    public PathMap N(Label label) { return set(Path.N, label); }

    public Label NV() { return get(Path.NV); }
    public PathMap NV(Label label) { return set(Path.NV, label); }

    public Label R() { return get(Path.R); }
    public PathMap R(Label label) { return set(Path.R, label); }

    public PathMap exception(Type type, Label label) {
        return set(ts.exceptionPath(type), label);
    }

    public PathMap exc(Label label, Type type) {
        if (ts.isUncheckedException(type)) {
            return this;
        }

        Path C = ts.exceptionPath(type);
        Label c = ts.join(label, get(C));
        Label n = ts.join(label, N());
        Label nv = ts.join(label, NV());
        return this.N(n).NV(nv).set(C, c);
    }

    /** Return all paths in the map except NV (which isn't really a
     * path). */
    public Set paths() {
        Set s = allPaths();
        s.remove(Path.NV);
        return s;
    }

    /** Return all paths in the map including NV. */
    public Set allPaths() {
        return new LinkedHashSet(map.keySet());
    }

    public PathMap join(PathMap m) {
        PathMap n = new PathMap(ts);
        n.map.putAll(map);

        // Iterate over the elements of X, joining those labels with the ones
        // in this and adding the ones that aren't there.
        for (Iterator i = m.map.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry e = (Map.Entry) i.next();
            Path p = (Path) e.getKey();
            Label l1 = (Label) e.getValue();
            Label l2 = n.get(p);
            n.map.put(p, ts.join(l1, l2));
        }

        return n;
    }

    public PathMap subst(LabelSubstitution subst) throws SemanticException {
        PathMap n = new PathMap(ts);

        for (Iterator i = map.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry e = (Map.Entry) i.next();
            Path p = (Path) e.getKey();
            Label L = (Label) e.getValue();
            n.map.put(p, L.subst(subst));
        }

        return n;
    }
    
    public PathMap subst(VarMap bounds) {
        PathMap n = new PathMap(ts);

        for (Iterator i = map.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry e = (Map.Entry) i.next();
            Path p = (Path) e.getKey();
            Label L = (Label) e.getValue();
            n.map.put(p, bounds.applyTo(L));
        }

        return n;
    }

    public String toString() {
        String s = "";

        for (Iterator i = map.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry e = (Map.Entry) i.next();
            Path p = (Path) e.getKey();
            Label L = (Label) e.getValue();

            s += p.toString() + ":" + L.toString();

            if (i.hasNext()) {
                s += " ";
            }
        }

        return s;
    }

    public void dump(CodeWriter w) {
        w.write("X[");
        w.begin(0);
        boolean first = true;

        for (Iterator i = map.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry e = (Map.Entry) i.next();
            Path p = (Path) e.getKey();
            Label L = (Label) e.getValue();

            if (! first) {
                w.allowBreak(0);
                first = false;
            }

            w.write(p.toString()+ ":" + L.toString());
        }

        w.end();
        w.write("]");
    }
}
