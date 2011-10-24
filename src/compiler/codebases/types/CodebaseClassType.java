package codebases.types;

import java.net.URI;

import polyglot.types.ClassType;

public interface CodebaseClassType extends ClassType {
  URI canonicalNamespace();
}
