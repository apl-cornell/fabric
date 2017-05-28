package old.scl;

public class SCLtest {

  /**
   * @param args
   * @throws ClassNotFoundException 
   */
  public static void main(String[] args) throws Exception {
    // TODO Auto-generated method stub
    ClassLoader cl = new ExternalClassLoader();
    Class c = cl.loadClass("scl.FabricClassWrapper");

    Object fabClassWrapper =  c.newInstance();

    Class d = ((ClassWrapper) fabClassWrapper).loadClass();

    Object tester = d.newInstance();
  }

}
