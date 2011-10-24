package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.SourceFileToJavaExt_c;
import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.ast.SourceFile;
import polyglot.frontend.Source;
import polyglot.types.SemanticException;
import codebases.frontend.CodebaseSource;
import fabric.visit.FabricToFabilRewriter;

public class SourceFileToFabilExt_c extends SourceFileToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    SourceFile n = (SourceFile) node();
    Source source = n.source();
    FabricToFabilRewriter ftfr = (FabricToFabilRewriter) rw;
    PackageNode pkgNode = n.package_();
        
    n = rw.java_nf().SourceFile(n.position(), pkgNode, n.imports(), n.decls());
    //n = (SourceFile)n.del(null);
    source = ftfr.createDerivedSource((CodebaseSource) source, source.name());
    n = n.source(source);
    return rw.leavingSourceFile(n);
  }
}
