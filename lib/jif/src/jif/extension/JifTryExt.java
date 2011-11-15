package jif.extension;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.types.Type;

/** Jif extension of the <code>Try</code> node.
 *  
 *  @see polyglot.ast.Try
 */
public class JifTryExt extends JifStmtExt_c
{
    public JifTryExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
        Try trs = (Try) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) trs.del().enterScope(A);

        Block t = (Block) lc.context(A).labelCheck(trs.tryBlock());
        PathMap Xs = getPathMap(t);

        PathMap Xall = ts.pathMap();

        List catches = new LinkedList();

        for (Iterator i = trs.catchBlocks().iterator(); i.hasNext(); ) {
            Catch cb = (Catch) i.next();

            // This adds the formal to the environment.
            Formal f = cb.formal();
            final JifLocalInstance vi = (JifLocalInstance) f.localInstance();
            Label Li = vi.label();

            // use the label of the exception as the pc 
            A = (JifContext) A.pushBlock();
            A.setPc(Li, lc);


            // label check the formal
            f = (Formal) lc.context(A).labelCheck(cb.formal());

            // If there is a declared label, bind the label of the formal to
            // be equivalent to it, and force this declared label to
            // be at least as high as the pc flow.
            if (ts.isLabeled(f.type().type())) {
                Label declaredLabel = ts.labelOfType(f.type().type());
                lc.constrain(new NamedLabel("local_label", 
                                            "inferred label of " + f.name(), 
                                            Li), 
                            LabelConstraint.EQUAL, 
                            new NamedLabel("declared label of " + f.name(), declaredLabel), 
                            A.labelEnv(),
                            f.position(),
                            false,
                            new ConstraintMessage() {
                    public String msg() {
                        return "Declared label of catch block variable " + vi.name() + 
                        " is incompatible with label constraints.";
                    }
                }
                );
            }

            // Constrain the variable label to be at least as much as the exc-label.
            Label pc_i = excLabel(Xs, cb.catchType(), lc, ts);

            final String catchTypeName = ts.unlabel(cb.catchType()).toClass().name();
            lc.constrain(new NamedLabel("join(pc|where exc_i could be thrown)", 
                                        "the information that could be revealed " +
                                        "by the exception " + catchTypeName + " " +
                                        "being thrown", 
                                        pc_i), 
                        LabelConstraint.LEQ,
                        new NamedLabel("label_exc_i", 
                                       "label of variable " + vi.name(), 
                                       Li), 
                       A.labelEnv(),
                       f.position(),
                       false,
                       new ConstraintMessage() {
                public String msg() {
                    return "Label of thrown exceptions of type " + catchTypeName + 
                    " not less restrictive than the label of " + vi.name();
                }
                public String detailMsg() { 
                    return "More information may be revealed by an exception of " +
                    "type " + catchTypeName + " being thrown than is " +
                    "allowed to flow to " + vi.name() + ".";
                }
            });


            Block si = (Block) lc.context(A).labelCheck(cb.body());
            PathMap Xi = getPathMap(si);

            Xall = Xall.join(Xi);

            A = (JifContext) A.pop();

            catches.add(updatePathMap(cb.formal(f).body(si), Xi));
        }
        PathMap Xunc = uncaught(Xs, trs, ts);
        Xall = Xall.join(Xunc);

        PathMap X;

        Block f = trs.finallyBlock();

        if (f != null) {
            // the pc of A is the same pc used to label check the try block
            f = (Block) lc.context(A).labelCheck(f);

            PathMap X2 = getPathMap(f);
            //X = Xall.N(ts.notTaken()).join(X2);
            Label finalPath = ts.bottomLabel();
            for (Iterator iter = X2.paths().iterator(); iter.hasNext(); ) {
                Path p = (Path) iter.next();
                finalPath = lc.upperBound(finalPath, X2.get(p));
            }
            // at this point, final path is equal to the upper bound on all paths out of the finally block 
            // This should be the normal path of f. Seeing an exception throw by the try or a catch block
            // reveals that the finally block terminated normally.

            for (Iterator iter = Xall.paths().iterator(); iter.hasNext(); ) {
                Path p = (Path) iter.next();
                if (p instanceof ExceptionPath) {
                    Xall = Xall.set(p, lc.upperBound(Xall.get(p), finalPath));
                }
            }

            X = Xall.join(X2);
        }
        else {
            X = Xall;
        }

        trs = trs.tryBlock(t).catchBlocks(catches).finallyBlock(f);

        return updatePathMap(trs, X);
    }

    private PathMap uncaught(PathMap X, Try trs, JifTypeSystem ts)
    throws SemanticException {

        PathMap Xp = X;

        for (Iterator iter = X.paths().iterator(); iter.hasNext(); ) {
            Path p = (Path) iter.next();

            if (p instanceof ExceptionPath) {
                ExceptionPath jep = (ExceptionPath) p;

                boolean sat = false;

                for (Iterator i = trs.catchBlocks().iterator(); i.hasNext(); ) {
                    Catch cb = (Catch) i.next();

                    if (ts.isImplicitCastValid(jep.exception(), cb.catchType()) ||
                            ts.equals(jep.exception(), cb.catchType())) {

                        sat = true;
                        break;
                    }
                }

                if (sat) {
                    Xp = Xp.set(jep, ts.notTaken());
                }
            }
        }

        return Xp;
    }

    private Label excLabel(PathMap X, Type ct, LabelChecker lc, JifTypeSystem ts)
    throws SemanticException {

        Label L = ts.bottomLabel(ct.position());

        for (Iterator iter = X.paths().iterator(); iter.hasNext(); ) {
            Path p = (Path) iter.next();

            if (p instanceof ExceptionPath) {
                ExceptionPath ep = (ExceptionPath) p;

                if (ts.isSubtype(ct, ep.exception()) ||
                        ts.isSubtype(ep.exception(), ct)) {
                    L = lc.upperBound(L, X.get(ep));
                }
            }
        }

        return L;
    }
}
