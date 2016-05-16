package fabric.translate;

import fabil.ast.FabILNodeFactory;

import fabric.extension.FabricArrayAccessDel;
import fabric.visit.FabricToFabilRewriter;

import jif.translate.ArrayAccessToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Node;

public class ArrayAccessToFabilExt_c extends ArrayAccessToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) {
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    ArrayAccess orig = (ArrayAccess) node();
    FabricArrayAccessDel aad = (FabricArrayAccessDel) orig.del();
    ArrayAccess aa = (ArrayAccess) super.toJava(rw);
    if (aad.startStage() != null || aad.endStage() != null) {
      FabILNodeFactory nf = (FabILNodeFactory) rw.java_nf();
      return aa.array(nf.StageCall(aa.position(), aa.array(),
            frw.stageCheckExpr(orig, aad.startStage(), aad.endStage())));
    }
    return aa;
  }
}
