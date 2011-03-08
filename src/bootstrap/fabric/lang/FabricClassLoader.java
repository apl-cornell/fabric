package fabric.lang;

public class FabricClassLoader extends java.lang.ClassLoader {
    
    native public java.lang.Class findClass(java.lang.String name)
          throws java.lang.ClassNotFoundException;
    
    native public java.lang.Class loadClass(java.lang.String name,
                                            boolean resolve)
          throws java.lang.ClassNotFoundException;
    
    native java.lang.Class getJavaClass(java.lang.String cls);
    
    private FabricClassLoader(fabric.lang.Codebase cb,
                              java.lang.ClassLoader parent) {
        super(parent);
    }
    
    native public static fabric.lang.FabricClassLoader getClassLoader(
      fabric.lang.Codebase codebase);
    
    native public java.io.InputStream getResourceAsStream(
      java.lang.String name);
    
    native public fabric.lang.Codebase getCodebase();
}
