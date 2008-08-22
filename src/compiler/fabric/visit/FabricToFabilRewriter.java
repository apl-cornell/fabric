package fabric.visit;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;
import polyglot.frontend.Job;
import jif.translate.JifToJavaRewriter;

public class FabricToFabilRewriter extends JifToJavaRewriter {

  public FabricToFabilRewriter(Job job, FabricTypeSystem fab_ts, FabricNodeFactory fab_nf, fabil.ExtensionInfo fabil_ext) {
    super(job, fab_ts, fab_nf, fabil_ext);
    // TODO Auto-generated constructor stub
  }
}
