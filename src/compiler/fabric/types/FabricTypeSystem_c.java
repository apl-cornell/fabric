package fabric.types;

import java.util.List;

import polyglot.types.ImportTable;
import polyglot.types.Package;

import fabil.types.FabILImportTable;
import fabil.types.FabILTypeSystem;
import jif.types.JifTypeSystem_c;

public class FabricTypeSystem_c extends JifTypeSystem_c implements FabricTypeSystem {

  public FabricTypeSystem_c(FabILTypeSystem filts) {
    super(filts);
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
}
