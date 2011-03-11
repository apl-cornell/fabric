package fabil.types;

import polyglot.types.ClassContextResolver;
import polyglot.types.ClassType;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;

public class CodebaseClassContextResolver extends ClassContextResolver {

  public CodebaseClassContextResolver(TypeSystem ts, ClassType type) {
    super(ts, type);
  }
}
