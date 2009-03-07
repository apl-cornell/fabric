package fabric.visit;

import java.util.ArrayList;
import java.util.List;

import fabil.FabILOptions;
import fabil.ast.FabILNodeFactory;
import fabil.types.FabILTypeSystem;
import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import jif.translate.JifToJavaRewriter;

public class FabricToFabilRewriter extends JifToJavaRewriter {
  protected boolean principalExpected = false;

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
  
//private Expr loc;
//private Call addLoc(Call c) {
//  List<Expr> args = new ArrayList<Expr>(c.arguments().size() + 1);
//  args.add(loc);
//  for(Expr expr : (List<Expr>)c.arguments()) {
//    Expr addExpr = expr;
//    if(expr instanceof Call) {
//      Call subcall = (Call) expr;
//      addExpr = addLoc(subcall);
//    }
//    args.add(addExpr);
//  }
//  c = (Call)c.arguments(args);
//  return c;
//  
//}

  public Expr updateLabelLocation(Expr labelExpr, Expr locExpr) {
    if (labelExpr instanceof Call) {
      Call c = (Call)labelExpr;
      
      // XXX Hack
      // Several special cases, for which no change should be made.
      if (c.name().equals("getPrincipal") && c.arguments().size() == 0) {
        return c;
      }
      
      List<Expr> args = new ArrayList<Expr>(c.arguments().size() + 1);
      args.add(locExpr);
      for (Expr expr : (List<Expr>)c.arguments()) {
        if (expr instanceof Call) {
          args.add(updateLabelLocation(expr, locExpr));
        }
        else {
          args.add(expr);
        }
      }
      c = (Call)c.arguments(args);
      return c;
    }
    
    return labelExpr;
  }
}
