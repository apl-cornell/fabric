package fabil.types;

import polyglot.types.Package;
import fabil.frontend.CodebaseSource;
import fabric.lang.Codebase;

public interface CodebasePackage extends Package {
  Codebase codebase();
  CodebasePackage codebase(Codebase cb);
  CodebaseSource source();
  CodebasePackage source(CodebaseSource source);

}
