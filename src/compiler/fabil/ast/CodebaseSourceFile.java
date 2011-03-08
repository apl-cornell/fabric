package fabil.ast;

import polyglot.ast.SourceFile;
import fabil.Codebases;
import fabric.lang.Codebase;

public interface CodebaseSourceFile extends SourceFile, Codebases {
  /** Set the source's codebase. */
  SourceFile codebase(Codebase codebase);
}
