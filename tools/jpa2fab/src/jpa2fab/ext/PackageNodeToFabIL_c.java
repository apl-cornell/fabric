package jpa2fab.ext;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.translate.ExtensionRewriter;
import polyglot.translate.ext.PackageNodeToExt_c;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import fabil.ExtensionInfo;
import fabil.types.FabILTypeSystem;

/**
 * 
 */
public class PackageNodeToFabIL_c extends PackageNodeToExt_c implements Ext {
  @Override
  public Node toExt(ExtensionRewriter rw) throws SemanticException {
    PackageNode n = (PackageNode) node();
    Package p = n.package_();
    FabILTypeSystem ts = (FabILTypeSystem) rw.to_ts();
    ExtensionInfo extInfo = (ExtensionInfo) rw.to_ext();
    p = ts.packageForName(extInfo.localNamespace(), p.fullName());
    return rw.to_nf().PackageNode(n.position(), p);
  }
}
