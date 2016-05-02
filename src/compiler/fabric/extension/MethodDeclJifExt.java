package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricMethodInstance;

import jif.extension.JifMethodDeclExt;
import jif.translate.ToJavaExt;
import jif.types.JifMethodInstance;
import jif.visit.LabelChecker;

public class MethodDeclJifExt extends JifMethodDeclExt {

  protected boolean is_remote;

  public MethodDeclJifExt(ToJavaExt toJava) {
    super(toJava);
    this.is_remote = false;
  }

  public void setRemote() {
    is_remote = true;
  }

  public boolean isRemote() {
    return is_remote;
  }

  @Override
  protected void initContextForBody(LabelChecker lc, JifMethodInstance mi) {
    super.initContextForBody(lc, mi);
    FabricMethodInstance fmi = (FabricMethodInstance) mi;
    FabricContext A = (FabricContext) lc.context();
    A.setBeginConflictBound(fmi.beginConflictLabel());
    //A.setConflictLabel(fmi.beginConflictLabel());
    A.setEndConflictBound(fmi.endConflictLabel());
  }
}
