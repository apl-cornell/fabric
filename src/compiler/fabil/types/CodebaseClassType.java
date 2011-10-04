package fabil.types;

import fabric.lang.Codebase;
import polyglot.types.ParsedClassType;

public interface CodebaseClassType extends ParsedClassType {
  Codebase codebase();
}
