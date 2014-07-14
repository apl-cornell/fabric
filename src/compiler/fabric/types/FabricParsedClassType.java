package fabric.types;

import java.net.URI;
import java.util.Collection;
import java.util.Set;

import jif.types.JifParsedPolyType;
import jif.types.label.ConfPolicy;
import polyglot.types.MethodInstance;
import polyglot.types.SemanticException;
import codebases.types.CodebaseClassType;

public interface FabricParsedClassType extends JifParsedPolyType,
FabricClassType, CodebaseClassType {

  void removeMethod(MethodInstance mi);

  void setCanonicalNamespace(URI ns);

  /**
   * @param Namespace
   *          dependencies
   */
  void setNamespaceDependencies(Set<CodebaseClassType> dependencies);

  /**
   * @return
   */
  Collection<CodebaseClassType> namespaceDependencies();

  /**
   * @param confProjection
   * @throws SemanticException
   */
  void setAccessPolicy(ConfPolicy policy) throws SemanticException;

}
