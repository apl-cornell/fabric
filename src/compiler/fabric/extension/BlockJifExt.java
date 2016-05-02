package fabric.extension;

import java.util.ArrayList;
import java.util.List;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifBlockExt;
import jif.translate.ToJavaExt;
import jif.types.JifTypeSystem;
import jif.visit.LabelChecker;

import polyglot.ast.Block;
import polyglot.ast.LocalClassDecl;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

/** The Fabric version of the Jif extension of the <code>Block</code> node.
 * 
 *  @see polyglot.ast.Block_c
 *  @see jif.extension.JifBlockExt
 */
public class BlockJifExt extends JifBlockExt {
    private static final long serialVersionUID = SerialVersionUID.generate();

    public BlockJifExt(ToJavaExt toJava) {
        super(toJava);
    }

    @Override
    public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
        Block bs = (Block) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        FabricContext A = (FabricContext) lc.jifContext();
        A = (FabricContext) bs.del().enterScope(A);

        // A path map incorporating all statements in the block seen so far.
        FabricPathMap Xblock = (FabricPathMap) ts.pathMap();
        Xblock = Xblock.N(A.pc());
        Xblock = Xblock.CL(A.conflictLabel());

        A = (FabricContext) A.pushBlock();

        List<Stmt> l = new ArrayList<>(bs.statements().size());

        for (Stmt s : bs.statements()) {
            s = (Stmt) lc.context(A).labelCheck(s);
            l.add(s);

            if (s instanceof LocalClassDecl)
                // nothing else required
                continue;

            FabricPathMap Xs = (FabricPathMap) getPathMap(s);

            // At this point, the environment A should have been extended
            // to include any declarations of s.  Reset the PC label.
            A.setPc(Xs.N(), lc);
            A.setConflictLabel(ts.join(A.conflictLabel(), Xs.CL()));

            if (Report.should_report(jif.Topics.pc, 1)) {
                Report.report(1, "pc after statement at " + s.position() + " : "
                        + A.pc().toString());
            }

            Xblock = Xblock.N(ts.notTaken()).join(Xs);
        }

        A = (FabricContext) A.pop();

        return updatePathMap(bs.statements(l), Xblock);
    }
}
