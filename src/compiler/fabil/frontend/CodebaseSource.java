package fabil.frontend;

import fabric.lang.Codebase;

public interface CodebaseSource {
  /** Return the codebase associated with this object. */
  Codebase codebase();
  /** Return whether this represents a local or remote source. */
  boolean isRemote();
}
