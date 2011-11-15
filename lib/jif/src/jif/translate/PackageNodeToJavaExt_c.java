package jif.translate;

import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.types.Package;
import polyglot.types.SemanticException;

public class PackageNodeToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        PackageNode n = (PackageNode) node();
        Package p = n.package_();
        p = rw.java_ts().packageForName(p.fullName());
        return rw.java_nf().PackageNode(n.position(), p);
    }
}
