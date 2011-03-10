package fabil.types;

import fabil.Codebases;
import fabric.lang.Codebase;
import polyglot.types.Package;

public interface CodebasePackage extends Package, Codebases {
  Codebase codebase();
  CodebasePackage codebase(Codebase cb);
}
