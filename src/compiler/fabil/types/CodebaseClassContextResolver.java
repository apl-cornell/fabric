package fabil.types;

import polyglot.types.ClassContextResolver;
import polyglot.types.ClassType;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;

public class CodebaseClassContextResolver extends ClassContextResolver {

  public CodebaseClassContextResolver(TypeSystem ts, ClassType type) {
    super(ts, type);
  }
  
  //Member classes are not supported in Fabric, but are in FabIL. 
  //  Overriding this method might be necessary if we ever supported 
  //  FabIL types published in Fabric.
//  @Override
//  public Named find(String name, ClassType accessor) throws SemanticException {
//    return super.find(name, accessor);
//  }
  
  
}
