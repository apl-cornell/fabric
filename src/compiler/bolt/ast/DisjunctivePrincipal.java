package bolt.ast;

import java.util.List;

/**
 * A disjunctive principal node in the AST.
 */
public interface DisjunctivePrincipal extends Principal {
  List<Principal> disjuncts();

  DisjunctivePrincipal disjuncts(List<Principal> disjuncts);
}
