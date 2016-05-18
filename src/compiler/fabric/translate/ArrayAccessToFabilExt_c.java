package fabric.translate;

import fabric.ast.FabricUtil;
import fabric.extension.FabricStagingExt;
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
    FabricStagingExt fse = FabricUtil.fabricStagingExt(orig);
    ArrayAccess aa = (ArrayAccess) super.toJava(rw);
    return aa.array(fse.stageCheck(frw, orig, aa.array()));
  }
}
