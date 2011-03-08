package fabil.types;

import polyglot.types.Context;
import fabric.lang.Codebase;

public interface FabILContext extends Context {
  Codebase currentCodebase();
  Context pushCodebase(Codebase codebase);
}
