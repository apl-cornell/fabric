package fabil.types;

import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.types.ClassType;
import polyglot.types.ImportTable;
import polyglot.types.Named;
import polyglot.types.Package;
import polyglot.types.TopLevelResolver;
import polyglot.types.TypeSystem;

public interface CodebaseTypeSystem extends TypeSystem {
   
  ImportTable importTable(Source source, Package pkg);
   
   boolean isPlatformType(Named dep);
   
   CodebaseClassContextResolver createClassContextResolver(ClassType type);
   
   CodebasePackageContextResolver createPackageContextResolver(Package p);
   
   CodebaseSystemResolver createSystemResolver(TopLevelResolver loadedResolver, ExtensionInfo extInfo);

   CodebasePackage createPackage(Package prefix, String name);
}
