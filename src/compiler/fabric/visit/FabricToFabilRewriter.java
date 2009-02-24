package fabric.visit;

import fabil.ast.FabILNodeFactory;
import fabil.types.FabILTypeSystem;
import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;
import polyglot.ast.TypeNode;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import jif.translate.JifToJavaRewriter;

public class FabricToFabilRewriter extends JifToJavaRewriter {

  public FabricToFabilRewriter(Job job, FabricTypeSystem fab_ts, FabricNodeFactory fab_nf, fabil.ExtensionInfo fabil_ext) {
    super(job, fab_ts, fab_nf, fabil_ext);
    // TODO Auto-generated constructor stub
  }
  
  @Override
  public String runtimeLabelUtil() {
    return "jif.lang.LabelUtil";
  }
  
  @Override
  public TypeNode typeToJava(Type t, Position pos) throws SemanticException {
    FabILNodeFactory fabil_nf = (FabILNodeFactory)java_nf();
    FabILTypeSystem fabil_ts = (FabILTypeSystem)java_ts();
    FabricTypeSystem fabric_ts = (FabricTypeSystem)jif_ts();
    
    if (fabric_ts.typeEquals(t, fabric_ts.Client())) {
      return canonical(fabil_nf, fabil_ts.Client(), pos);
    }
    else if (fabric_ts.typeEquals(t, fabric_ts.RemoteClient())) {
      return canonical(fabil_nf, fabil_ts.RemoteClient(), pos);
    }
    
    return super.typeToJava(t, pos);
  }
}
