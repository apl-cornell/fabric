package bolt.types;

import polyglot.ext.jl5.types.JL5Context;

/**
 * The context for Bolt type checking. Introduces the notion of an
 * object-initialization context.
 */
public interface BoltContext extends JL5Context {
  /**
   * Determines whether this is an object-initialization context.
   */
  boolean inObjectInitContext();

  /**
   * Pushes an object-initialization context of the given kind.
   */
  BoltContext pushObjectInitializer(ObjectInitKind kind);

  /**
   * Designates a kind of object-initializer context.
   */
  enum ObjectInitKind {
    /**
     * Block object-initializer contexts are nested. Types, variables, and type
     * variables declared in this kind of context go out of scope at the end of
     * the context. Examples of block object-initializer contexts include
     * initializer blocks and field initializers.
     */
    BLOCK,
    /**
     * Inline object-initializer contexts are non-nested. Types, variables, and
     * type variables declared in this kind of context remain in scope at the
     * end of the context. The constructor prologue is an example of an inline
     * object-initializer context: variables declared in the prologue remain in
     * scope after the prologue.
     */
    INLINE
  }
}
