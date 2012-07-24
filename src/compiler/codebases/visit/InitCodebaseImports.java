package codebases.visit;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.visit.InitImportsVisitor;
import polyglot.visit.NodeVisitor;
import codebases.ast.CodebaseDecl;
import codebases.frontend.CodebaseSource;
import codebases.types.CBImportTable;
import codebases.types.CodebaseTypeSystem;
import fabric.common.FabricLocation;

/**
 * This class creates an import table for each source file and sets the source
 * reference for each package node.
 */
public class InitCodebaseImports extends InitImportsVisitor {
  protected FabricLocation ns;
  private final CodebaseTypeSystem ts;

  public InitCodebaseImports(Job job, CodebaseTypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
    this.ns = ((CodebaseSource) job.source()).canonicalNamespace();
    this.ts = ts;
  }

  @Override
  public NodeVisitor enterCall(Node n) throws SemanticException {
    if (n instanceof SourceFile) {
      SourceFile sf = (SourceFile) n;

      PackageNode pn = sf.package_();

      CBImportTable it;
      if (pn != null) {
        it = ts.importTable(sf.source(), ns, pn.package_());
      } else {
        it = ts.importTable(sf.source(), ns, null);
      }

      InitCodebaseImports v = (InitCodebaseImports) copy();
      v.importTable = it;
      return v;
    }

    return this;
  }

  @Override
  public Node leaveCall(Node old, Node n, NodeVisitor v)
      throws SemanticException {
    if (n instanceof CodebaseDecl) {
      CodebaseDecl cbd = (CodebaseDecl) n;
      ((CBImportTable) importTable).addCodebaseName(cbd.name().id(),
          cbd.position());
      return n;
    } else return super.leaveCall(old, n, v);
  }
}
