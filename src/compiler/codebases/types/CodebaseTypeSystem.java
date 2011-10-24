package codebases.types;

import java.net.URI;
import java.util.List;

import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.types.Named;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import fabric.lang.Codebase;

public interface CodebaseTypeSystem extends TypeSystem {

  CBImportTable importTable(Source source, URI ns, Package pkg);

  void initialize(ExtensionInfo extensionInfo) throws SemanticException;

  NamespaceResolver namespaceResolver(URI uri);
  NamespaceResolver platformResolver();
  List<NamespaceResolver> sourcepathResolvers();
  List<NamespaceResolver> classpathResolvers();
  List<NamespaceResolver> signatureResolvers();
  List<NamespaceResolver> runtimeResolvers();

  boolean packageExists(URI namespace, String name);
  
  Named forName(URI namespace, String name) throws SemanticException;

  Package packageForName(URI ns, Package prefix, String name)
      throws SemanticException;

  Package createPackage(URI ns, Package prefix, String name);

  Package packageForName(URI ns, String name) throws SemanticException;

  Codebase codebaseFromNS(URI namespace);
  
}
