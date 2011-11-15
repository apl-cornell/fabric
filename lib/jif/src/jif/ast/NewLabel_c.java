package jif.ast;

import polyglot.util.*;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.Translator;

/** An implementation of the <code>NewLabel</code> interface. 
 */
public class NewLabel_c extends LabelExpr_c implements NewLabel
{
    public NewLabel_c(Position pos, LabelNode label) {
        super(pos, label);
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("new label {");
        print(label, w, tr);
        w.write("}");
    }

    public void translate(CodeWriter w, Translator tr) {
        throw new InternalCompilerError("cannot translate " + this);
    }

    public String toString() {
	return "new label " + label;
    }
}
