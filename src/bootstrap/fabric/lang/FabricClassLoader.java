package fabric.lang;

public class FabricClassLoader extends java.lang.ClassLoader {
    
    native public java.lang.Class loadClass(java.lang.String name,
                                            boolean resolve)
          throws java.lang.ClassNotFoundException;
    
    public FabricClassLoader(java.lang.ClassLoader parent) {
        super(parent);
    }
    
}
