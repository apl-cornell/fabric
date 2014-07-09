package codebases.ast;

import java.net.URI;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode_c;
import polyglot.frontend.ExtensionInfo;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import codebases.types.CBPackage;
import codebases.types.CodebaseTypeSystem;

/**
 * A CodebaseNode is a qualifier to a type in another namespace. CodebaseNodes
 * are syntactic 'aliases': they do not affect the type (like a package
 * qualifier would), only how the type is resolved.
 */
//XXX Should be replaced with extension
@Deprecated
public class CodebaseNode_c extends PackageNode_c implements CodebaseNode {
  protected URI namespace;
  protected String alias;
  protected URI externalNS;

  @Deprecated
  public CodebaseNode_c(Position pos, URI namespace, String alias,
      URI externalNS) {
    this(pos, namespace, alias, externalNS, (Ext) null);
  }

  public CodebaseNode_c(Position pos, URI namespace, String alias,
      URI externalNS, Ext ext) {
    // XXX: PackageNode_c has an assertion that prevents package_ from being
    // null
    // but the implementation seems to allow it. For now, we ignore the
    // assertion and extend PackageNode_c
    this(pos, namespace, alias, externalNS, null, null);
  }

  @Deprecated
  public CodebaseNode_c(Position pos, URI namespace, String alias,
      URI externalNS, Package package_) {
    this(pos, namespace, alias, externalNS, package_, null);
  }

  public CodebaseNode_c(Position pos, URI namespace, String alias,
      URI externalNS, Package package_, Ext ext) {
    super(pos, package_, ext);
    this.namespace = namespace;
    this.externalNS = externalNS;
    this.alias = alias;
    if (package_ != null) {
      CBPackage pkg = (CBPackage) package_;
      if (!pkg.namespace().equals(externalNS)) {
        throw new InternalCompilerError("Expected package " + pkg
            + " to have namespace " + externalNS + " but got "
            + pkg.namespace());
      }
    }
  }

  @Override
  public boolean isDisambiguated() {
    // CodebaseNodes may have null packages.
    return package_ == null || super.isDisambiguated();
  }

  @Override
  public URI namespace() {
    return namespace;
  }

  @Override
  public String alias() {
    return alias;
  }

  @Override
  public URI externalNamespace() {
    return externalNS;
  }

  @Override
  public Node copy(NodeFactory nf) {
    CodebaseNodeFactory cnf = (CodebaseNodeFactory) nf;
    return cnf.CodebaseNode(this.position, namespace, alias, externalNS,
        package_);
  }

  @Override
  public Node copy(ExtensionInfo extInfo) throws SemanticException {
    Package pkg = null;
    if (package_ != null) {
      CodebaseTypeSystem ts = (CodebaseTypeSystem) extInfo.typeSystem();
      pkg = ts.packageForName(externalNS, package_.fullName());
    }
    CodebaseNodeFactory nf = (CodebaseNodeFactory) extInfo;
    return nf.CodebaseNode(position(), namespace, alias, externalNS, pkg);
  }

  @Override
  public String toString() {
    return externalNS.toString() + ((package_ == null) ? "" : super.toString());
  }

}
