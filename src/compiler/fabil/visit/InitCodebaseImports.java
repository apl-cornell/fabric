package fabil.visit;

import polyglot.ast.Import;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile;
import polyglot.frontend.Job;
import polyglot.frontend.Source;
import polyglot.types.ImportTable;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.visit.InitImportsVisitor;
import polyglot.visit.NodeVisitor;
import fabil.ast.CodebaseSourceFile;
import fabil.frontend.CodebaseSource;
import fabil.types.CodebasePackage;
import fabil.types.CodebaseTypeSystem;
import fabric.common.SysUtil;
import fabric.lang.Codebase;

public class InitCodebaseImports extends InitImportsVisitor {

  protected Codebase codebase = null;
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
      CodebaseSource src = (CodebaseSource) sf.source();
      v.codebase = src.codebase();
      v.importTable = it;
      v.remote = ((CodebaseSourceFile) n).isRemote();
      return v;
    }
    return this;
  }

  @SuppressWarnings("unused")
  @Override
  public Node leaveCall(Node old, Node n, NodeVisitor v)
      throws SemanticException {

    if (n instanceof CodebaseSourceFile) {
      CodebaseSourceFile sf = (CodebaseSourceFile) n;
      InitCodebaseImports v_ = (InitCodebaseImports) v;
      ImportTable it = v_.importTable;
      return sf.codebase(v_.codebase).importTable(it);
    }
    if (n instanceof Import) {
      Import im = (Import) n;

      if (im.kind() == Import.CLASS) {
        String name = im.name();

        if (remote)
          name = ((CodebaseTypeSystem) ts).absoluteName(codebase, name, true);
        this.importTable.addClassImport(name, im.position());
        
      } else if (im.kind() == Import.PACKAGE) {
        // XXX: package imports are not currently
        // supported in codebases
        this.importTable.addPackageImport(im.name(), im.position());
      }
    }

    return n;
  }

}
