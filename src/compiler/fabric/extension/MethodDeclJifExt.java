package fabric.extension;

import jif.extension.JifMethodDeclExt;
import jif.translate.ToJavaExt;

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

}
