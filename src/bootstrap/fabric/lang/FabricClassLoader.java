package fabric.lang;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import fabric.lang.Codebase;
import fabric.Main;
import fabric.common.Logging;
import fabric.common.NSUtil;

/**
 * Loads Java classes from Fabric.
 */
public class FabricClassLoader extends java.lang.ClassLoader {
    java.util.Map classes;
    java.util.Map classBytecodeMap;
    
    public FabricClassLoader(java.lang.ClassLoader parent) {  }
    
    public native java.lang.Class loadClass(java.lang.String name,
                                            boolean resolve)
          throws java.lang.ClassNotFoundException;
    
    protected native java.lang.Class findClass(java.lang.String name)
          throws java.lang.ClassNotFoundException;
    
    private native java.lang.Class getJavaClass(java.lang.String cls);
    
    public native java.io.InputStream getResourceAsStream(
      java.lang.String name);
}
