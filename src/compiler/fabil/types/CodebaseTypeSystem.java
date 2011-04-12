package fabil.types;

import fabil.frontend.CodebaseSource;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.types.ClassType;
import polyglot.types.LazyClassInitializer;
import polyglot.types.Named;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.types.TopLevelResolver;
import polyglot.types.TypeSystem;

public interface CodebaseTypeSystem extends TypeSystem {

  CodebaseImportTable importTable(CodebaseSource source, Package pkg);
  
  boolean localTypesOnly();

  CodebasePackageContextResolver createPackageContextResolver(Package p);

  CodebaseSystemResolver createSystemResolver(TopLevelResolver loadedResolver,
      ExtensionInfo extInfo);

  CodebasePackage createPackage(Package prefix, String name);

  CodebaseClassType createClassType(LazyClassInitializer init, Source fromSource);
  
  String absoluteName(Codebase cb, String fullName, boolean resolve) throws SemanticException;
  
  void addRemoteFClass(Codebase cb, Named n);
}
