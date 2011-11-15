package jif.extension;

import java.util.ArrayList;
import java.util.List;

import jif.translate.ToJavaExt;
import jif.types.*;
import jif.visit.LabelChecker;
import polyglot.ast.ConstructorCall;
import polyglot.ast.Node;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;

/** The Jif extension of the <code>ConstructorCall</code> node. 
 * 
 *  @see polyglot.ast.ConstructorCall
 */
public class JifConstructorCallExt extends JifStmtExt_c
{
    public JifConstructorCallExt(ToJavaExt toJava) {
        super(toJava);
    }

    protected ConstructorChecker constructorChecker = new ConstructorChecker();

    public Node labelCheckStmt(LabelChecker lc) throws SemanticException
    {
        ConstructorCall ccs = (ConstructorCall) node();

        JifContext A = lc.jifContext();
        JifTypeSystem ts = lc.typeSystem();

        List throwTypes = new ArrayList(ccs.del().throwTypes(ts)); 
        A = (JifContext) ccs.del().enterScope(A);

        JifConstructorInstance ci = (JifConstructorInstance)ccs.constructorInstance();

        ClassType ct = ci.container().toClass();
        JifClassType jct = (JifClassType) A.currentClass();
        if (ccs.kind()==ConstructorCall.SUPER) {
            ct = (ClassType) A.currentClass().superType();
        }
        if (ccs.qualifier() != null) {
            throw new InternalCompilerError(
            "Qualified constructor calls are not supported in Jif.");
        }

        constructorChecker.checkConstructorAuthority(ct, A, lc, ccs.position());

        CallHelper helper = lc.createCallHelper(jct.thisLabel(), ct, ci, 
                                           ccs.arguments(), node().position());

        helper.checkCall(lc.context(A), throwTypes, false);

        checkThrowTypes(throwTypes);
        return updatePathMap(ccs.arguments(helper.labelCheckedArgs()), helper.X());
    }
}
