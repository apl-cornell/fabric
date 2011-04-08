package fabil.visit;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.frontend.Job;
import polyglot.types.ImportTable;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.visit.InitImportsVisitor;
import polyglot.visit.NodeVisitor;
import fabil.ast.CodebasePackageNode;
import fabil.ast.CodebaseSourceFile;
import fabil.frontend.CodebaseSource;
import fabil.types.CodebaseTypeSystem;

/**
 * This class creates an import table for each source file and sets the source
 * reference for each package node.
 */
public class InitCodebaseImports extends InitImportsVisitor {

  protected CodebaseSource source = null;
  protected boolean remote = true;

  public InitCodebaseImports(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  @SuppressWarnings("unused")
  @Override
  public NodeVisitor enterCall(Node n) throws SemanticException {
    if (n instanceof CodebaseSourceFile) {
      CodebaseSourceFile sf = (CodebaseSourceFile) n;

      PackageNode pn = sf.package_();

      ImportTable it;
      CodebaseTypeSystem cts = (CodebaseTypeSystem) ts;
      if (pn != null) {
        it =
            (ImportTable) cts.importTable((CodebaseSource) sf.source(), pn
                .package_());
      } else {
        it = (ImportTable) cts.importTable((CodebaseSource) sf.source(), null);
      }

      InitCodebaseImports v = (InitCodebaseImports) copy();
      v.importTable = it;
      v.source = (CodebaseSource) sf.source();
      return v;
    }
    return this;
  }

  @SuppressWarnings("unused")
  @Override
  public Node leaveCall(Node old, Node n, NodeVisitor v)
      throws SemanticException {
    n = super.leaveCall(old, n, v);  
    //Set the source of CodebasePackageNodes
    if (n instanceof CodebasePackageNode) {
      CodebasePackageNode pn = (CodebasePackageNode) n;
      InitCodebaseImports v_ = (InitCodebaseImports) v;
      return pn.source(v_.source);
    }
    return n;
  }

}
