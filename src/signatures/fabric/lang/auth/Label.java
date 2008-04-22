package fabric.lang.auth;

public interface Label {

  public static Label DEFAULT = null;
  
  public long $getOnum();
  
  public static class $Proxy implements Label {
    
    public $Proxy(Object core, long onum) {}
    
    public native long $getOnum();
    
  }
  
}
