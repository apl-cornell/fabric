package codebases.types;

import java.net.URI;

import polyglot.types.Context;

public interface CodebaseContext extends Context {
  URI namespace();
  URI resolveCodebaseName(String id);
}
