package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.PackageNodeToJavaExt_c;
import jif.translate.ToJavaExt;
import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import fabil.types.FabILTypeSystem;
import fabric.types.FabricContext;

public class PackageNodeToFabilExt_c extends PackageNodeToJavaExt_c implements
    ToJavaExt {
  
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    PackageNode n = (PackageNode) node();
    Package p = n.package_();
    FabricContext ctx = (FabricContext) rw.context();
    FabILTypeSystem fil_ts = (FabILTypeSystem) rw.java_ts();
    
    p = fil_ts.packageForName(ctx.namespace(), p.fullName());
    return rw.java_nf().PackageNode(n.position(), p);
}

}
