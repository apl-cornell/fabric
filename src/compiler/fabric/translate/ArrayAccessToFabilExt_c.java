package fabric.translate;

import fabil.ast.FabILNodeFactory;

import fabric.extension.FabricArrayAccessDel;

import jif.translate.ArrayAccessToJavaExt_c;
import jif.translate.JifToJavaRewriter;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Node;

public class ArrayAccessToFabilExt_c extends ArrayAccessToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) {
    ArrayAccess orig = (ArrayAccess) node();
    FabricArrayAccessDel aad = (FabricArrayAccessDel) orig.del();
    ArrayAccess aa = (ArrayAccess) super.toJava(rw);
    if (aad.stageCheck() != null) {
      FabILNodeFactory nf = (FabILNodeFactory) rw.java_nf();
      return aa.array(nf.StageCall(aa.position(), aa.array(), rw.visitEdge(orig, aad.stageCheck())));
    }
    return aa;
  }
}
