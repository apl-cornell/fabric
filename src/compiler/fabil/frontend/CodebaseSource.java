package fabil.frontend;

import fabil.Codebases;
import fabric.lang.Codebase;

public interface CodebaseSource {
  /** Return the codebase associated with this object. */
  Codebase codebase();

}
