package fabil.ast;

import polyglot.ast.SourceFile;
import fabric.lang.Codebase;

public interface CodebaseSourceFile extends SourceFile {
  /** Set the source's codebase. */
  boolean isRemote();
}
