package fabil.types;

import fabric.lang.Codebase;
import polyglot.types.Context;

public interface CodebaseContext extends Context {
  Codebase currentCodebase();
  CodebaseContext pushCodebase(Codebase codebase);
}
