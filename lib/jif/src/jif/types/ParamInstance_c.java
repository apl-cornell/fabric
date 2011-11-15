package jif.types;

import java.io.IOException;

import polyglot.main.Report;
import polyglot.types.Type;
import polyglot.types.TypeObject;
import polyglot.types.VarInstance_c;
import polyglot.util.Position;

/** An implementation of the <code>ParamInstance</code> interface.
 */
public class ParamInstance_c extends VarInstance_c implements ParamInstance
{
    JifClassType container;
    Kind kind;

    public ParamInstance_c(JifTypeSystem ts, Position pos,
	JifClassType container, Kind kind, String name) {

	super(ts, pos, ts.Public().Static().Final(),
	      kind == PRINCIPAL ? ts.Principal() : ts.Label(), name);
	this.kind = kind;
	this.container = container;
    }

    public JifClassType container() {
	return container;
    }

    public boolean equalsImpl(TypeObject o) {
        if (o instanceof ParamInstance) {
            ParamInstance that = (ParamInstance) o;
            return super.equalsImpl(that) &&
                    this.kind.equals(that.kind()) &&
                    this.container.fullName().equals(that.container().fullName());
	}

	return false;
    }

    public ParamInstance container(JifClassType container) {
	ParamInstance_c n = (ParamInstance_c) copy();
	n.container = container;
	return n;
    }

    public Kind kind() {
	return kind;
    }

    public ParamInstance kind(Kind kind) {
	ParamInstance_c n = (ParamInstance_c) copy();
	n.kind = kind;
	return n;
    }

    public ParamInstance name(String name) {
	ParamInstance_c n = (ParamInstance_c) copy();
	n.name = name;
	return n;
    }

    public boolean isPrincipal() {
	return kind.isPrincipal();
    }

    public boolean isLabel() {
	return isInvariantLabel() || isCovariantLabel();
    }

    public boolean isInvariantLabel() {
	return kind.isInvariantLabel();
    }

    public boolean isCovariantLabel() {
	return kind.isCovariantLabel();
    }

    public String toString() {
        if (Report.should_report(Report.debug, 1)) {
            return kind + " " + container().name() + "." + name();
        }
        return kind + " " + name();
    }

    protected void writeObject(java.io.ObjectOutputStream out)
	throws IOException
    {
	out.writeObject(container);
	if (kind == INVARIANT_LABEL) out.writeInt(0);
	else if (kind == COVARIANT_LABEL) out.writeInt(1);
	else if (kind == PRINCIPAL) out.writeInt(2);
	else throw new IOException("invalid kind");
    }

    protected void readObject(java.io.ObjectInputStream in)
	throws IOException, ClassNotFoundException
    {
	this.container = (JifClassType) in.readObject();
	int k = in.readInt();
	switch (k) {
	case 0: kind = INVARIANT_LABEL; break;
	case 1: kind = COVARIANT_LABEL; break;
	case 2: kind = PRINCIPAL; break;
	default: throw new IOException("invalid kind");
	}
    }

    public void setType(Type t) {
    	//Do nothing
    }
}
