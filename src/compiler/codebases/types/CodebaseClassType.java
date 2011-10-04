package codebases.types;

import java.net.URI;

import fabric.lang.Codebase;
import polyglot.types.ParsedClassType;

public interface CodebaseClassType extends ParsedClassType {
  URI canonicalNamespace();
}
