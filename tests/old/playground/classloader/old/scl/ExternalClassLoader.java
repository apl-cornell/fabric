package old.scl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;

public class ExternalClassLoader extends ClassLoader {

  private Hashtable<String, Class<?>> classes = new Hashtable();
  private String[] ignored_packages = {"java"};
  private java.lang.ClassLoader deferTo = ClassLoader.getSystemClassLoader();

  public ExternalClassLoader() {
  }

  public ExternalClassLoader(String[] ignored_packages) {
    addIgnoredPkgs(ignored_packages);
  }

  private void addIgnoredPkgs(String[] ignored_packages) {
    String[] new_p = new String[ignored_packages.length + this.ignored_packages.length];

    System.arraycopy(this.ignored_packages, 0, new_p, 0, this.ignored_packages.length);

    System.arraycopy(ignored_packages, 0, new_p, this.ignored_packages.length, ignored_packages.length);

    this.ignored_packages = new_p;
  }

  @Override
  protected Class loadClass(String class_name, boolean resolve) throws ClassNotFoundException{
    Class cl = null;

    /* First look in cache */
    if ((cl=(Class)classes.get(class_name)) == null) {

      /* Load system classes */
      for (int i = 0; i < ignored_packages.length; i++) {
        // scl.ClassWrapper is deferred so that it forms a common interface between the classes loaded
        // by the system and the classes loaded by this classloader.
        if (class_name.startsWith(ignored_packages[i]) || class_name.equals("scl.ClassWrapper")) {
          cl = deferTo.loadClass(class_name);
          break;
        }
      }

      /* Fabric loader */

      if (cl == null ) {
        System.out.println("Attempting to load " + class_name);
        byte[] classData = loadClassData(class_name);
        cl = defineClass(class_name, classData, 0, classData.length);
        classes.put(class_name, cl);
        //				try {
        //					FabricClassWrapper tester = (FabricClassWrapper) cl.newInstance();
        //					return tester.icl.loadClass(tester.className);
        //				} catch (InstantiationException e) {
        //					// TODO Auto-generated catch block
        //					e.printStackTrace();
        //				} catch (IllegalAccessException e) {
        //					// TODO Auto-generated catch block
        //					e.printStackTrace();
        //				}
      }
    }

    return cl;
  }

  private byte[] loadClassData (String filename) {
    File f = null;

    f = new File("/home/athereso/Documents/Simple Class Loader/bin/" + filename.replace('.', '/')+ ".class");

    //use Worker.getWorker.fetchManager()

    int size = (int) f.length();
    byte buff[] = new byte[size];
    System.out.println(f.isFile());
    DataInputStream dis;
    try {
      dis = new DataInputStream(new FileInputStream(f));
      dis.readFully(buff);
      dis.close();
    } catch (Exception e) {

    }

    return buff;
  }

}
