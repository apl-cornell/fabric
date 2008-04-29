package fabric.visit;

import polyglot.ast.Block;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.qq.QQ;
import polyglot.types.Flags;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import fabric.ExtensionInfo;
import fabric.ast.FabricNodeFactory;
import fabric.types.FabricFlags;

/**
 * Removes atomic keyword from method flags and makes body an atomic block.
 */
public class AtomicMethodRewriter extends NodeVisitor {

  protected QQ qq;
  protected FabricNodeFactory nf;

  public AtomicMethodRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.nf = extInfo.nodeFactory();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node override(Node parent, Node n) {
    if (n instanceof MethodDecl) {
      MethodDecl md = (MethodDecl) n;
      Flags f = md.flags();
      
      if (f.contains(FabricFlags.ATOMIC)) {
        f = f.clear(FabricFlags.ATOMIC);
        md = md.flags(f);
        md.methodInstance().setFlags(f);
        Block b = nf.Atomic(Position.compilerGenerated(), md.body().statements());
        md = (MethodDecl) md.body(b);
        
        return visitEdgeNoOverride(parent, md);
      }
    }
    
    if (n instanceof ConstructorDecl) {
      ConstructorDecl cd = (ConstructorDecl) n;
      Flags f = cd.flags();
      
      if (f.contains(FabricFlags.ATOMIC)) {
        f = f.clear(FabricFlags.ATOMIC);
        cd = cd.flags(f);
        cd.constructorInstance().setFlags(f);
        Block b = nf.Atomic(Position.compilerGenerated(), cd.body().statements());
        cd = (ConstructorDecl) cd.body(b);
        
        return visitEdgeNoOverride(parent, cd);
      }
    }
    
    return null;
  }

}
