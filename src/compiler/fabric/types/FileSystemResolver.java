package fabric.types;

import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TopLevelResolver;

public class FileSystemResolver implements TopLevelResolver {

  /**
   * @throws SemanticException  
   */
  @Override
  public Named find(String name) throws SemanticException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean packageExists(String name) {
    // TODO Auto-generated method stub
    return false;
  }

}
