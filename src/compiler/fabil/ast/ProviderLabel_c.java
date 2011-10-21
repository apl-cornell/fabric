package fabil.ast;

import java.util.List;

import polyglot.ast.Expr_c;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

/**
 * AST node implementation for representing the provider label for a class.
 */
public class ProviderLabel_c extends Expr_c implements ProviderLabel {
  protected TypeNode tn;

  /**
   * @param pos
   * @param tn
   */
  public ProviderLabel_c(Position pos, TypeNode tn) {
    super(pos);
    this.tn = tn;
  }

  @Override
  public TypeNode typeNode() {
    return tn;
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    if (!tn.type().isClass()) {
      throw new SemanticException("Cannot get provider label of a non-class "
          + "type.", tn.position());
    }
    
    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    return type(ts.Label());
  }

  @Override
  public Term firstChild() {
    return null;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public List<Term> acceptCFG(CFGBuilder v, List succs) {
    return succs;
  }

}
