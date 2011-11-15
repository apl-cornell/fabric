package jif.extension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jif.ast.JifUtil;
import jif.ast.Jif_c;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.*;
import polyglot.types.MethodInstance;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;

/** The Jif extension of the <code>Call</code> node. 
 * 
 *  @see polyglot.ast.Call_c
 */
public class JifCallExt extends JifExprExt
{
    public JifCallExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheck(LabelChecker lc) throws SemanticException {
        Call me = (Call) node();

        JifContext A = lc.jifContext();
        A = (JifContext) me.del().enterScope(A);
        JifTypeSystem ts = lc.jifTypeSystem();

        if (A.checkingInits()) {
            // in the constructor prologue, the this object cannot be the receiver or an argument
            if (me.target() instanceof Expr && JifUtil.effectiveExpr((Expr)me.target()) instanceof Special) {
                throw new SemanticDetailedException("No methods may be called on \"this\" object in a constructor prologue.", 
                                                    "In a constructor body before the call to the super class, no " +
                                                    "reference to the \"this\" object is allowed to escape. This means " +
                                                    "that no methods of the current object may be called.", 
                                                    me.position());
            }
            for (Iterator iter = me.arguments().iterator(); iter.hasNext();) {
                Expr arg = (Expr)iter.next();
                if (JifUtil.effectiveExpr(arg) instanceof Special) {
                    throw new SemanticDetailedException("The \"this\" object cannot be used as a method argument in a constructor prologue.", 
                                                        "In a constructor body before the call to the super class, no " +
                                                        "reference to the \"this\" object is allowed to escape. This means " +
                                                        "that the \"this\" object cannot be used as a method argument.", 
                                                        arg.position());
                }

            }

        }

        List throwTypes = new ArrayList(me.del().throwTypes(ts));

        Receiver target = (Receiver) lc.context(A).labelCheck(me.target());

        // Find the method instance again. This ensures that
        // we have the correctly instantiated type, as label checking
        // of the target may have produced a new type for the target.
        JifMethodInstance mi = (JifMethodInstance)ts.findMethod(target.type().toReference(), 
                                          me.name(), 
                                          me.methodInstance().formalTypes(), 
                                          A.currentClass());

        me = me.methodInstance(mi);
        
        if (mi.flags().isStatic()) {
            new ConstructorChecker().checkStaticMethodAuthority(mi, A, lc, me.position());
        }


        A = (JifContext) A.pushBlock();

        boolean npExc = false;
        Label objLabel = null;

        if (target instanceof Expr) {
            Expr e = (Expr) target;

            if (e.type() == null) 
                throw new InternalCompilerError("Type of " + e + " is null", e.position());

            PathMap Xs = getPathMap(target);
            A.setPc(Xs.N(), lc);

            if (! (target instanceof Special)) {
                // a NPE may be thrown depending on the target.
                npExc = (!((JifCallDel)node().del()).targetIsNeverNull());
                objLabel = Xs.NV();
                A.setPc(Xs.NV(), lc);
            }
            else {
                objLabel = ((JifClassType) lc.context().currentClass()).thisLabel();
            }
        }

        CallHelper helper = lc.createCallHelper(objLabel, target, mi.container(), mi, me.arguments(), node().position());
        LabelChecker callLC = lc.context(A);
        helper.checkCall(callLC, throwTypes, npExc);

        // now use the call helper to bind the var labels that were created
        // during type checking of the call (see JifCallDel#typeCheck)
        JifCallDel del = (JifCallDel)me.del();
        helper.bindVarLabels(callLC, 
                             del.receiverVarLabel, 
                             del.argVarLabels, 
                             del.paramVarLabels);

        A = (JifContext) A.pop();

        //subst arguments of inst_type
        if (helper.returnType() != me.type()) {
            me = (Call) me.type(helper.returnType());
        }

        checkThrowTypes(throwTypes);
        return updatePathMap(me.target(target).arguments(helper.labelCheckedArgs()), helper.X());
    }
}
