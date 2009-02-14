package fabil.ast;

import java.util.List;

import polyglot.ast.Block;
import polyglot.types.LocalInstance;

/**
 * An <code>Atomic</code> represents an <code>atomic</code> block.
 */
public interface Atomic extends Block {
  List<LocalInstance> updatedLocals();
  Atomic updatedLocals(List<LocalInstance> s);
}
