package fabric.types;

import java.util.Collection;
import java.util.Set;

import jif.types.JifParsedPolyType;
import polyglot.types.MethodInstance;
import codebases.types.CodebaseClassType;
import fabric.common.FabricLocation;

public interface FabricParsedClassType extends JifParsedPolyType,
    FabricClassType, CodebaseClassType {

  void removeMethod(MethodInstance mi);

  void setCanonicalNamespace(FabricLocation ns);

  /**
   * @param Namespace
   *          dependencies
   */
  void setNamespaceDependencies(Set<CodebaseClassType> dependencies);

  /**
   * @return
   */
  Collection<CodebaseClassType> namespaceDependencies();

}
