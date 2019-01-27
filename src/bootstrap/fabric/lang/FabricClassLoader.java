package fabric.lang;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
    
    public FabricClassLoader(java.lang.ClassLoader parent) {
        super(parent);
        this.classes = new java.util.HashMap();
        this.classBytecodeMap = new java.util.HashMap();
    }
    
    public java.lang.Class loadClass(java.lang.String name, boolean resolve)
          throws java.lang.ClassNotFoundException {
        fabric.common.Logging.log(fabric.common.Logging.TIMING_LOGGER,
                                  java.util.logging.Level.INFO, "resolving {0}",
                                  name);
        try { return super.loadClass(name, resolve); }
        finally {
            fabric.common.Logging.log(fabric.common.Logging.TIMING_LOGGER,
                                      java.util.logging.Level.INFO,
                                      "returning {0}", name);
        }
    }
    
    protected java.lang.Class findClass(java.lang.String name)
          throws java.lang.ClassNotFoundException {
        fabric.common.Logging.log(fabric.common.Logging.TIMING_LOGGER,
                                  java.util.logging.Level.FINE,
                                  "checking bytecode cache for {0}", name);
        if (!classBytecodeMap.containsKey(name)) {
            fabric.lang.FClass fcls = fabric.common.NSUtil.toProxy(name);
            try {
                fabric.common.Logging.log(fabric.common.Logging.TIMING_LOGGER,
                                          java.util.logging.Level.FINE,
                                          "compiling {0}", name);
                fabric.Main.compile(fcls, classBytecodeMap);
            }
            catch (java.security.GeneralSecurityException e) {
                throw new java.lang.ClassNotFoundException(name, e);
            }
            catch (java.io.IOException e) {
                throw new java.lang.ClassNotFoundException(name, e);
            }
        }
        fabric.common.Logging.log(fabric.common.Logging.TIMING_LOGGER,
                                  java.util.logging.Level.FINE, "loading {0}",
                                  name);
        java.lang.Class javaCls = getJavaClass(name);
        if (fabric.lang.Object._Proxy.idEquals(javaCls, null)) {
            throw new java.lang.ClassNotFoundException(name);
        }
        return javaCls;
    }
    
    private java.lang.Class getJavaClass(java.lang.String cls) {
        java.lang.Class result = (java.lang.Class) classes.get(cls);
        if (fabric.lang.Object._Proxy.idEquals(result, null)) {
            if (classBytecodeMap.containsKey(cls)) {
                byte[] bytecode = (byte[]) classBytecodeMap.get(cls);
                result = defineClass(cls, bytecode, 0, bytecode.length);
                classes.put(cls, result);
            }
        }
        return result;
    }
    
    public java.io.InputStream getResourceAsStream(java.lang.String name) {
        java.io.InputStream in = super.getResourceAsStream(name);
        if (!fabric.lang.Object._Proxy.idEquals(in, null)) { return in; }
        name = name.substring(0, name.length() - ".class".length());
        name = name.replace("/", ".");
        if (!classBytecodeMap.containsKey(name)) {
            try { findClass(name); }
            catch (java.lang.ClassNotFoundException e) { return null; }
        }
        byte[] bytecode = (byte[]) classBytecodeMap.get(name);
        return new java.io.ByteArrayInputStream(bytecode);
    }
    
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
