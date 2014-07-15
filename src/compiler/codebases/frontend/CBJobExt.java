package codebases.frontend;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import polyglot.frontend.JobExt;
import codebases.types.CodebaseClassType;

public class CBJobExt implements JobExt {
  protected Set<CodebaseClassType> dependencies;
  protected Map<CodebaseClassType, String> externalDeps;

  public CBJobExt() {
    dependencies = new HashSet<>();
    externalDeps = new HashMap<>();
  }

  public boolean addDependency(CodebaseClassType ct) {
    // System.err.println("ADDING DEPENDENCY:" + ct + " :"
    // +ct.canonicalNamespace() + ":"+ct.getClass());

    return dependencies.add(ct);
  }

  public Set<CodebaseClassType> dependencies() {
    return Collections.unmodifiableSet(dependencies);
  }

  public boolean addExternalDependency(CodebaseClassType ct, String alias) {
    return externalDeps.put(ct, alias) == null;
  }

  public boolean isExternal(CodebaseClassType ct) {
    return externalDeps.containsKey(ct);
  }

  public String aliasFor(CodebaseClassType ct) {
    return externalDeps.get(ct);
  }

}
