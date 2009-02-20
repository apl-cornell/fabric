package fabric.types;

import java.util.List;

import polyglot.frontend.Source;
import polyglot.types.ClassType;
import polyglot.types.ImportTable;
import polyglot.types.LazyClassInitializer;
import polyglot.types.Package;
import polyglot.types.ParsedClassType;
import polyglot.types.TypeSystem;

import fabil.types.FabILImportTable;
import jif.types.JifTypeSystem_c;

public class FabricTypeSystem_c extends JifTypeSystem_c implements FabricTypeSystem {

  public ClassType FObject() {
    return load("fabric.lang.Object");
  }
  
  public FabricTypeSystem_c(TypeSystem jlts) {
    super(jlts);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List defaultPackageImports() {
    // Include fabric.lang and fabric.client as default imports.
    List<String> result = super.defaultPackageImports();
    result.add("fabric.lang");
    result.add("fabric.client");
    return result;
  }
  
  @Override
  public ImportTable importTable(Package pkg) {
    // the FabILImportTable works around the ambiguous import of
    // java.lang.Object and fabric.lang.Object 
    return new FabILImportTable(this, pkg);
  }

  @Override
  public ImportTable importTable(String sourcename, Package pkg) {
    // the FabILImportTable works around the ambiguous import of
    // java.lang.Object and fabric.lang.Object 
    return new FabILImportTable(this, pkg, sourcename);
  }

  @Override
  public ParsedClassType createClassType(LazyClassInitializer init,
      Source fromSource) {
    return new FabricParsedClassType_c(this, init, fromSource);
  }

  
}
