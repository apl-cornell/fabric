package jif.types;

import polyglot.types.VarInstance;
import polyglot.util.Enum;

/** A parameter instance. A wrapper of all the type information 
 *  related to a label/principal parameter. 
 */
public interface ParamInstance extends VarInstance
{
    public static class Kind extends Enum {
        final boolean isPrincipal;
        final boolean isInvariantLabel;
        final boolean isCovariantLabel; 
        public Kind(String name, boolean isPrincipal, boolean isInvariantLabel, boolean isCovariantLabel) { 
            super(name); 
            this.isPrincipal = isPrincipal;
            this.isCovariantLabel = isCovariantLabel;
            this.isInvariantLabel = isInvariantLabel;
        }
        public boolean isPrincipal() {
            return isPrincipal;
        }
        public boolean isCovariantLabel() {
            return isCovariantLabel;
        }
        public boolean isInvariantLabel() {
            return isInvariantLabel;
        }
    }

    public final static Kind INVARIANT_LABEL = new Kind("label", false, true, false);
    public final static Kind COVARIANT_LABEL = new Kind("covariant label", false, false, true);
    public final static Kind PRINCIPAL       = new Kind("principal", true, false, false);

    JifClassType container();
    ParamInstance container(JifClassType container);

    Kind kind();
    ParamInstance kind(Kind kind);
    ParamInstance name(String name);

    boolean isPrincipal();
    boolean isLabel();
    boolean isInvariantLabel();
    boolean isCovariantLabel();
}
