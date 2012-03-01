package codebases.ast;

import java.net.URI;

import polyglot.types.Qualifier;

public interface CodebaseQualifier extends Qualifier {
  URI namespace();
}
