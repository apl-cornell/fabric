package fabil.types;

import fabric.lang.Codebase;
import polyglot.types.Package;
import polyglot.types.Package_c;
import polyglot.types.Resolver;
import polyglot.types.TypeSystem;

public class FabILPackage_c extends Package_c {

  public FabILPackage_c(TypeSystem ts, Package prefix, String name) {
    super(ts, prefix, name);
  }

//  @Override
//  public String translate(Resolver c) {
//    FabILContext ctx = (FabILContext) c;
//    FabILTypeSystem ts = (FabILTypeSystem) ctx.typeSystem();
//    if (prefix() == null) {
//      if(!ts.isPlatformPackage(name()))
//        return codebasePrefix(ctx.currentCodebase()) + name();
//      return name();
//    }
//    return prefix().translate(c) + "." + name();
//  }
  

}
