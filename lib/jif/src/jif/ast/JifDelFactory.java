package jif.ast;

import polyglot.ast.DelFactory;
import polyglot.ast.JL;

public interface JifDelFactory extends DelFactory {

	JL delAmbNewArray();
	
    JL delNewLabel();

    JL delLabelExpr();

}
