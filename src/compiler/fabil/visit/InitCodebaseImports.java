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
import fabil.Codebases;
import fabil.ast.CodebaseSourceFile;
import fabil.frontend.CodebaseSource;
import fabil.types.CodebaseTypeSystem;
import fabric.common.Util;
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
    if (n instanceof SourceFile) {
      SourceFile sf = (SourceFile) n;

      PackageNode pn = sf.package_();
     
      ImportTable it;
      CodebaseTypeSystem cts = (CodebaseTypeSystem) ts;
      if (pn != null) {
        it = cts.importTable(sf.source(), pn.package_());
      } else {
        it = cts.importTable(sf.source(), null);
      }

      InitCodebaseImports v = (InitCodebaseImports) copy();
      CodebaseSource src = (CodebaseSource) sf.source();
      v.codebase = src.codebase();
      v.importTable = it;
      v.remote = ((CodebaseSourceFile)n).isRemote();
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
        //if(codebase != null && codebase.resolveClassName(name) != null)
        if(remote)
          name = Util.codebasePrefix(codebase) + name;
        this.importTable.addClassImport(name, im.position());
      } else if (im.kind() == Import.PACKAGE) {
        //XXX: package imports are not currently 
        // supported in codebases
        this.importTable.addPackageImport(im.name(), im.position());
      }
    }

    return n;
  }


}
