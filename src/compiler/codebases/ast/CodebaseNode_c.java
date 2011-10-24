package codebases.ast;

import java.net.URI;

import codebases.types.CBPackage;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.PackageNode;
import polyglot.ast.PackageNode_c;
import polyglot.frontend.ExtensionInfo;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.util.CodeWriter;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.Translator;

/**
 * A CodebaseNode is a qualifier to a type in another namespace. CodebaseNodes
 * are syntactic 'aliases': they do not affect the type (like a package
 * qualifier would), only how the type is resolved.
 * 
 * @author owen
 */

public class CodebaseNode_c extends PackageNode_c implements CodebaseNode {
  protected URI namespace;
  protected String alias;
  protected URI externalNS;

  public CodebaseNode_c(Position pos, URI namespace, String alias, URI externalNS) {
    //XXX: PackageNode_c has an assertion that prevents package_ from being null
    //          but the implementation seems to allow it. For now, we ignore the
    //          assertion and extend PackageNode_c
    this(pos,namespace, alias, externalNS, null);
  }
  
  public CodebaseNode_c(Position pos, URI namespace, String alias, URI externalNS, Package package_) {
    super(pos, package_);
    this.namespace = namespace;
    this.externalNS = externalNS;
    this.alias = alias;
    if (package_ != null) {
      CBPackage pkg = (CBPackage) package_;
      if(!pkg.namespace().equals(externalNS)) {
        throw new InternalCompilerError("Expected package " + pkg
            + " to have namespace " + externalNS + " but got "
            + pkg.namespace());
      }
    }
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
    return cnf.CodebaseNode(this.position,  namespace, alias, externalNS, package_);
  }

  @Override
  public Node copy(ExtensionInfo extInfo) throws SemanticException {
    PackageNode pn = (PackageNode) this.del().copy(extInfo.nodeFactory());
    if (pn.package_() != null) {
      pn =
          pn.package_(extInfo.typeSystem().packageForName(
              pn.package_().fullName()));
    }
    return pn;
  }

  public String toString() {
      return namespace.toString() +  
          ((package_ == null) ? "": "."+super.toString());
  }

}
