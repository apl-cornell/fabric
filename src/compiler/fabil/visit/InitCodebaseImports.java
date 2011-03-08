package fabil.visit;

import java.util.List;

import fabil.Codebases;
import fabil.ast.CodebaseSourceFile;
import fabric.lang.Codebase;
import polyglot.ast.Import;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile;
import polyglot.frontend.Job;
import polyglot.types.ImportTable;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.visit.InitImportsVisitor;
import polyglot.visit.NodeVisitor;

public class InitCodebaseImports extends InitImportsVisitor {

  protected Codebase codebase = null;

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

      if (pn != null) {
        it = ts.importTable(sf.source().name(), pn.package_());
      } else {
        it = ts.importTable(sf.source().name(), null);
      }

      InitCodebaseImports v = (InitCodebaseImports) copy();
      Codebases src = (Codebases) sf.source();
      v.codebase = src.codebase();
      v.importTable = it;
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
        if(codebase != null && codebase.resolveClassName(name) != null)
          name = codebasePrefix(codebase) + name;
        this.importTable.addClassImport(name, im.position());
      } else if (im.kind() == Import.PACKAGE) {
        //XXX: package imports are not currently 
        // supported in codebases
        this.importTable.addPackageImport(im.name(), im.position());
      }
    }

    return n;
  }

  private static String codebasePrefix(Codebase cb) {
    if (cb != null)
      return "fab://" + cb.$getStore().name() + "/" + cb.$getOnum() + "/";
    else return "";
  }

}
