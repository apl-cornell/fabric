package fabil.ast;

import java.util.List;

import fabil.types.FabricTypeSystem;

import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;

public class New_c extends polyglot.ast.New_c {

  public New_c(Position pos, Expr qualifier, TypeNode tn, List<Expr> arguments,
      ClassBody body) {
    super(pos, qualifier, tn, arguments, body);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.New_c#disambiguateOverride(polyglot.ast.Node,
   *      polyglot.visit.AmbiguityRemover)
   */
  @Override
  public Node disambiguateOverride(Node parent, AmbiguityRemover ar)
      throws SemanticException {
    New_c nn = (New_c) super.disambiguateOverride(parent, ar);

    // If we have an anonymous class implementing an interface, make its
    // supertype fabric.lang.Object.
    if (nn.body() != null && nn.objectType().isDisambiguated()
        && nn.objectType().type().toClass().flags().isInterface()) {
      ParsedClassType anonType = nn.anonType();
      anonType.superType(((FabricTypeSystem) ar.typeSystem()).FObject());
    }

    return nn;
  }
}
