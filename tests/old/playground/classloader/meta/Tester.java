package meta;

import java.io.IOException;

public class Tester {

  public static void main(String[] args) throws IOException, 
    ClassNotFoundException, InstantiationException, IllegalAccessException {
    
    /* Aliasing fail test is "foo/Fail" */
    String className = "foo/A";
    Class clazz = Class.getClass(className);
    java.lang.Class javaClass = clazz.toJavaClass();
    Object obj = javaClass.newInstance();
  }
  
}
