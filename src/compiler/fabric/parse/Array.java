package fabric.parse;

import jif.ast.ConstArrayTypeNode;
import jif.ast.LabeledTypeNode;
import jif.parse.Grm;
import polyglot.ast.TypeNode;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import fabric.ast.FabricNodeFactory;

public class Array extends jif.parse.Array {

  protected final boolean isNative;
  
  public Array(Grm parser, Position pos, TypeNode prefix) {
    this(parser, pos, prefix, false);
  }
  
  public Array(Grm parser, Position pos, TypeNode prefix, boolean isConst) {
    this(parser, pos, prefix, isConst, false);
  }
  
  public Array(Grm parser, Position pos, TypeNode prefix, boolean isConst, boolean isNative) {
    super(parser, pos, prefix, isConst);
    this.isNative = isNative;
  }
  
  public boolean isNative() {
    return isNative;
  }
  
  @Override
  public TypeNode toType() {
    // if the (unlabeled) base type is an array, inherit the constness
    // and nativity from that.
    TypeNode base = prefix;
    if (base instanceof LabeledTypeNode) {
        base = ((LabeledTypeNode)base).typePart();
    }
    
    //  XXX: this design is ridiculous, and I'm not sure if this logic is right
    
    boolean isConst  =  isConst()   || (base instanceof ConstArrayTypeNode);
    boolean isFabric = !isNative();
    
    FabricNodeFactory nf = (FabricNodeFactory) parser.nf;
    
    if ( isConst &&  isFabric)
      throw new InternalCompilerError("Const fabric arrays not yet supported" + base.position());
    if ( isConst && !isFabric)
      return nf.ConstArrayTypeNode(pos, prefix);
    if (!isConst &&  isFabric)
      return nf.FabricArrayTypeNode(pos, prefix);
    if (!isConst && !isFabric)
      return nf.ArrayTypeNode(pos, prefix);
    
    throw new InternalCompilerError("This line should be unreachable");
  }
}
