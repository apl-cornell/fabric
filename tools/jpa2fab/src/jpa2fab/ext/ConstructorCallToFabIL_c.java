package jpa2fab.ext;

import jif.translate.ClassDeclToJavaExt_c;
import polyglot.ast.ConstructorCall;
import polyglot.ast.Node;
import polyglot.ext.jl5.types.JL5ParsedClassType;
import polyglot.translate.ExtensionRewriter;
import polyglot.translate.ext.ConstructorCallToExt_c;
import polyglot.types.ClassType;
import polyglot.types.ConstructorInstance;
import polyglot.types.SemanticException;

public class ConstructorCallToFabIL_c extends ConstructorCallToExt_c {
	
    /** Rewrite this(a) to this.C$(a); Rewrite super(a) to super.C$(a) */
    @Override
    public Node toExt(ExtensionRewriter rw) throws SemanticException {
    	JPA2FabILRewriter j2f = (JPA2FabILRewriter) rw;

        ConstructorCall n = (ConstructorCall) node();
        ConstructorInstance ci = n.constructorInstance();
        ClassType ct = ci.container().toClass();
        
        if (!j2f.isPersistent(ct))
        	return super.toExt(rw);

        ConstructorCall.Kind kind = n.kind();

        String name = ClassDeclToJavaExt_c.constructorTranslatedName(ct);

        if (kind == ConstructorCall.THIS) {
            return rw.qq().parseStmt("this.%s(%LE);", name, n.arguments());
        } else {
            return rw.qq().parseStmt("this.%s(%LE);", name, n.arguments());
        }
    }
}
