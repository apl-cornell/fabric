package jif.ast;

import jif.types.Param;
import jif.types.label.ConfPolicy;
import jif.types.label.IntegPolicy;
import jif.types.label.Label;
import jif.types.label.PairLabel;
import polyglot.ast.Node_c;
import polyglot.util.CodeWriter;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.StringUtil;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.Translator;

/** An implementation of the <code>LabelNode</code> interface. 
 */
public abstract class LabelNode_c extends Node_c implements LabelNode
{
    private Label label;

    public LabelNode_c(Position pos) {
        super(pos);
    }

    protected LabelNode_c(Position pos, Label label) {
        super(pos);
        this.label = label;
    }

    public boolean isDisambiguated() {
        return label != null && label.isCanonical() && super.isDisambiguated();
    }

    public Label label() {
	return label;
    }

    public LabelNode label(Label label) {
	LabelNode_c n = (LabelNode_c) copy();
	n.label = label;
	return n;
    }

    public Param parameter() {
	return label();
    }

    public String toString() {
	if (label != null) {
	    return label.toString();
	}
	else {
	    return "<unknown-label>";
	}
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write(this.toString());
    }
    
    public void dump(CodeWriter w) {
        if (label != null) {
            w.write("(" + StringUtil.getShortNameComponent(label.getClass().getName()) + " ");
            if (label instanceof PairLabel) {
                PairLabel pl = (PairLabel) label;
                ConfPolicy cp = pl.confPolicy();
                IntegPolicy ip = pl.integPolicy();
                w.write("{" + StringUtil.getShortNameComponent(cp.getClass().getName()) + " " + cp.toString() + ";" +
                        StringUtil.getShortNameComponent(ip.getClass().getName()) + " " + ip.toString() + "}" + ")");
            } else {
                w.write(label.toString());
            }
        } else {
            w.write("<null-label>");
        }
    }    
    
    public final void translate(CodeWriter w, Translator tr) {
        throw new InternalCompilerError("cannot translate " + this);
    }
    
}
