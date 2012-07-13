package codebases.types;

import java.util.List;

import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.types.Named;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import fabric.common.FabricLocation;
import fabric.lang.Codebase;

public interface CodebaseTypeSystem extends TypeSystem {

  CBImportTable importTable(Source source, FabricLocation ns, Package pkg);

  void initialize(ExtensionInfo extensionInfo) throws SemanticException;

  NamespaceResolver namespaceResolver(FabricLocation uri);

  NamespaceResolver platformResolver();

  List<NamespaceResolver> sourcepathResolvers();

  List<NamespaceResolver> classpathResolvers();

  List<NamespaceResolver> signatureResolvers();

  List<NamespaceResolver> runtimeResolvers();

  boolean packageExists(FabricLocation namespace, String name);

  Named forName(FabricLocation namespace, String name) throws SemanticException;

  Package packageForName(FabricLocation ns, Package prefix, String name)
      throws SemanticException;

  Package createPackage(FabricLocation ns, Package prefix, String name);

  Package packageForName(FabricLocation ns, String name)
      throws SemanticException;

  Codebase codebaseFromNS(FabricLocation namespace);

}
