package fabil.types;

import fabil.frontend.CodebaseSource;
import fabric.lang.Codebase;
import polyglot.types.Context;

public interface CodebaseContext extends Context {
  Codebase currentCodebase();
  CodebaseContext pushCodebase(Codebase codebase);
  CodebaseSource currentSource();

}
