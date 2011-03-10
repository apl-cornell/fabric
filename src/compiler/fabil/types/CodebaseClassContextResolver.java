package fabil.types;

import polyglot.types.ClassContextResolver;
import polyglot.types.ClassType;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;

public class CodebaseClassContextResolver extends ClassContextResolver {

  public CodebaseClassContextResolver(TypeSystem ts, ClassType type) {
    super(ts, type);
  }
  //XXX: publishing member classes in codebases is not currently supported
  //     since Fabric does not support member classes
  @Override
  public Named find(String name, ClassType accessor) throws SemanticException {
    Named n = super.find(name, accessor);
    if(n instanceof ClassType) {
      ClassType ct = (ClassType) n;
      if(ct.isMember())
        throw new InternalCompilerError("Unexpected member class " + ct);
    }
    return n;
  }
  
  
}
