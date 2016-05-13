package fabric.types;

import java.util.Collections;
import java.util.Set;

import jif.types.JifTypeSystem;
import jif.types.hierarchy.LabelEnv;
import jif.types.label.Label;
import jif.types.label.Label_c;
import jif.types.label.Variable;

import polyglot.types.TypeObject;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

/**
 * An implementation of the <code>NoAccesses</code> interface.
 */
public class NoAccesses_c extends Label_c implements NoAccesses {
    private static final long serialVersionUID = SerialVersionUID.generate();

    protected NoAccesses_c() {
    }

    public NoAccesses_c(JifTypeSystem ts, Position pos) {
        super(ts, pos);
        this.description =
                "pseudo-label representing " +
                "that no accesses have been made.";
    }

    @Override
    public boolean isCovariant() {
        return false;
    }

    /**
     * In this case, it should fail all comparisons with other label types.
     */
    @Override
    public boolean isComparable() {
        return true;
    }

    @Override
    public boolean isEnumerable() {
        return true;
    }

    @Override
    public boolean isCanonical() {
        return true;
    }

    @Override
    public boolean isDisambiguatedImpl() {
        return true;
    }

    @Override
    public boolean isRuntimeRepresentable() {
        return false;
    }

    @Override
    public String toString() {
        return "<No-Accesses>";
    }

    @Override
    public String componentString(Set<Label> printedLabels) {
        return "<No-Accesses>";
    }

    @Override
    public boolean equalsImpl(TypeObject o) {
        return o instanceof NoAccesses;
    }

    @Override
    public int hashCode() {
        return "<No-Accesses>".hashCode();
    }

    /**
     * Only comparisons that should pass are with other NoAccess labels (this
     * ensures that conformance checks still work).
     *
     * Other checks shouldn't be performed if it requires comparing with
     * NoAccesses.
     */
    @Override
    public boolean leq_(Label L, LabelEnv env, LabelEnv.SearchState state) {
        if (L instanceof NoAccesses) return true;
        return false;
    }

    @Override
    public Set<Variable> variables() {
        return Collections.emptySet();
    }
}
