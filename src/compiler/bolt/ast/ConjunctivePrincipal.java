package bolt.ast;

import java.util.List;

/**
 * A conjunctive principal node in the AST.
 */
public interface ConjunctivePrincipal extends Principal {
  List<Principal> conjuncts();

  ConjunctivePrincipal conjuncts(List<Principal> conjuncts);
}
