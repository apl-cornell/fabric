package fabric.visit;

import fabric.OutputExtensionInfo;
import polyglot.ast.AmbTypeNode;
import polyglot.ast.CanonicalTypeNode;
import polyglot.ast.Node;
import polyglot.qq.QQ;
import polyglot.types.ArrayType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.visit.HandoffVisitor;
import polyglot.visit.NodeVisitor;

/**
 * This visitor rewrites the AST to use JL nodes exclusively and adds jobs to
 * the JL scheduler.
 */
public class HandoffToJLVisitor extends HandoffVisitor {

  protected QQ qq;
  protected TypeSystem ts;

  public HandoffToJLVisitor(OutputExtensionInfo ext) {
    super(ext);
    this.qq = new QQ(ext);
    this.ts = ext.typeSystem();
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.HandoffVisitor#leave(polyglot.ast.Node,
   *      polyglot.ast.Node, polyglot.visit.NodeVisitor)
   */
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    try {
      n = n.del().copy(ext);
      if (n instanceof AmbTypeNode) {
        AmbTypeNode tn = (AmbTypeNode)n;
        if (tn.qual() == null) n = qq.parseType(tn.name());
      } else if (n instanceof CanonicalTypeNode) {
        Type type = ((CanonicalTypeNode)n).type();
        if (type.typeSystem() != ts) {
          StringBuffer typeString = new StringBuffer();
          while (type instanceof ArrayType) {
            type = ((ArrayType)type).base();
            typeString = typeString.append("[]");
          }
          n = qq.parseType(type.toString() + typeString);
        }
      }
    } catch (SemanticException e) {
      throw new InternalCompilerError("Problem in copying nodes.", e);
    }
    
    return super.leave(old, n, v);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.HandoffVisitor#override(polyglot.ast.Node)
   */
  @Override
  public Node override(Node n) {
    return null;
  }

}
