package fabil;

import fabric.lang.Codebase;

/**
 * An interface implemented by classes that use codebases to resolve types
 * during compilation.
 */
public interface Codebases {
  /** Return the codebase associated with this object. */
  Codebase codebase();
}
