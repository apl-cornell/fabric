package fabric.core;

import java.security.Principal;

import javax.security.auth.x500.X500Principal;

import fabric.client.Core;
import fabric.common.SerializedObject;
import fabric.lang.auth.Label;
import fabric.lang.auth.PkiPrincipal;

/**
 * This class manages authorization on the core.
 */
public class AuthManager {
  
  private final Core c;

  public AuthManager(Core c) {
    this.c = c;
  }
  
  public boolean canRead(Principal p, SerializedObject o) {
    if (o == null) {
      return true;
    }
    
    long label = o.getLabel();
    
    if (label == -1) {
      return true;
    }
    
    if (p == null) {
      return false;
    }
    
    PkiPrincipal pp = new PkiPrincipal.$Impl(c, (X500Principal) p);
    pp = (PkiPrincipal) pp.$getProxy();
    Label ll = new Label.$Proxy(c, label);
    
    return ll.canRead(pp);
  }

  public boolean canWrite(Principal p, SerializedObject o) {
    if (o == null) {
      return true;
    }
    
    long label = o.getLabel();
    
    if (label == -1) {
      return true;
    }
    
    if (p == null) {
      return false;
    }
    
    PkiPrincipal pp = new PkiPrincipal.$Impl(c, (X500Principal) p);
    pp = (PkiPrincipal) pp.$getProxy();
    Label ll = new Label.$Proxy(c, label);
    
    return ll.canWrite(pp);
  }
  
}
