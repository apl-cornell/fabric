package fabric.visit;

import fabil.FabILOptions;
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

  public FabricToFabilRewriter(Job job, FabricTypeSystem fab_ts,
      FabricNodeFactory fab_nf, fabil.ExtensionInfo fabil_ext) {
    super(job, fab_ts, fab_nf, fabil_ext);
  }

  @Override
  public String runtimeLabelUtil() {
    return "jif.lang.LabelUtil";
  }

  @Override
  public TypeNode typeToJava(Type t, Position pos) throws SemanticException {
    FabILNodeFactory fabil_nf = (FabILNodeFactory) java_nf();
    FabILTypeSystem fabil_ts = (FabILTypeSystem) java_ts();
    FabricTypeSystem fabric_ts = (FabricTypeSystem) jif_ts();

    if (fabric_ts.typeEquals(t, fabric_ts.Client())) {
      return canonical(fabil_nf, fabil_ts.Client(), pos);
    }

    if (fabric_ts.typeEquals(t, fabric_ts.RemoteClient())) {
      return canonical(fabil_nf, fabil_ts.RemoteClient(), pos);
    }

    if (fabric_ts.isPrincipal(t)) {
      return fabil_nf.AmbTypeNode(pos, fabil_nf.PackageNode(pos, fabil_ts
          .packageForName("fabric.lang")), fabil_nf.Id(pos, "Principal"));
    }

    return super.typeToJava(t, pos);
  }

  public boolean inSignatureMode() {
    FabILOptions opts =
        (FabILOptions) ((fabil.ExtensionInfo) java_ext).getOptions();
    return opts.signatureMode();
  }
}
