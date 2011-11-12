package old.scl;

import java.io.*;

public class InternalClassLoader extends ClassLoader {
  public InternalClassLoader() {
    // TODO Auto-generated constructor stub
  }

  public InternalClassLoader(ClassLoader arg0) {
    super(arg0);
    // TODO Auto-generated constructor stub
  }

  public synchronized Class loadClass(String className, boolean resolveIt) {
    Class result;
    byte classData[];

    //match classname with java. or fabric.

    try {
      result = super.findSystemClass(className);
      return result;
    } catch (ClassNotFoundException e) {
      System.out.println("Not system class");
    }

    try {
      result = super.findClass(className);
      return result;
    } catch (ClassNotFoundException e) {
      System.out.println("could not find class");
    }

    try {
      classData = loadClassData(className);

      System.out.println(classData);

      result = defineClass(className, classData, 0, classData.length);
      return result;
    } catch (Exception e) {
      System.out.println("Wtf");
    }


    return null;
  }

  private byte[] loadClassData (String filename) throws Exception {
    File f = new File("/home/athereso/Documents/Simple Class Loader/bin/scl/TestClass.class");

    int size = (int) f.length();
    byte buff[] = new byte[size];
    System.out.println(f.isFile());
    DataInputStream dis = new DataInputStream(new FileInputStream(f));
    dis.readFully(buff);
    dis.close();

    return buff;
  }

  public synchronized void addClassPath() {

  }
}


