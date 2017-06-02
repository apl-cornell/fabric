package bolt.types;

import polyglot.ext.jl5.types.JL5Context;

/**
 * The context for Bolt type checking. Introduces the notion of a
 * final-initialization context.
 */
public interface BoltContext extends JL5Context {
  /**
   * Determines whether this is a final-initialization context.
   */
  boolean inFinalContext();

  /**
   * Pushes a final-initialization context of the given kind.
   */
  BoltContext pushFinalInitializer(FinalInitKind kind);

  /**
   * Designates a kind of final-initializer context.
   */
  enum FinalInitKind {
    /**
     * Block final-initializer contexts are nested. Types, variables, and type
     * variables declared in this kind of context go out of scope at the end of
     * the context.
     */
    BLOCK,
    /**
     * Inline final-initializer contexts are non-nested. Types, variables, and
     * type variables declared in this kind of context remain in scope at the
     * end of the context.
     */
    INLINE
  }
}
