package fabil.types;

import polyglot.types.Package;
import fabil.Codebases;
import fabric.lang.Codebase;

public interface CodebasePackage extends Codebases, Package {
  void setCodebase(Codebase codebase);
}
