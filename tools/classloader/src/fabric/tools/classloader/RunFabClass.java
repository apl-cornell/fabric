/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.tools.classloader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;

import fabric.lang.Codebase;
import fabric.lang.FabricClassLoader;
import fabric.common.exceptions.UsageError;
import fabric.lang.arrays.ObjectArray;
import fabric.lang.security.Label;
import fabric.worker.Store;
import fabric.worker.Worker;

public class RunFabClass {

  /**
   * RunClass workerName codebaseStore codebaseOnum className
   */
  public static void main(String[] args) 
    throws IOException, GeneralSecurityException, IllegalAccessException, 
    ClassNotFoundException, InvocationTargetException, NoSuchMethodException, UsageError {
    if (args.length != 4) {
      System.out.println("Usage: runfabclass workerName codebaseStore codebaseOnum className");
      return;
    }
    String workerName = args[0], storeName = args[1], className = args[3];
    long onum = Long.parseLong(args[2]);
    
    Worker.initialize(workerName);
    final Store s = Worker.getWorker().getStore(storeName);
    final Store ls = Worker.getWorker().getLocalStore();
    Codebase c = (Codebase)getObjectByOid(s, onum);
    
    FabricClassLoader loader = FabricClassLoader.getClassLoader(c);
    final Class cls = loader.loadClass(className);
    Worker.runInSubTransaction(new Worker.Code<Void>() {
      @SuppressWarnings("unchecked")
      public Void run() {
        ObjectArray programArgs = toObjectArray(new String[0], ls, 
            Worker.getWorker().getLocalStore().getEmptyLabel());
        try {
          cls.getMethod("main", ObjectArray.class).invoke(null, programArgs);
          System.exit(0);
          return null;
        } catch(Exception ex) {
          System.err.println(ex.getMessage());
          throw new RuntimeException(ex);
        }
      }
    });
    
  }
  
  private static ObjectArray toObjectArray(String[] args, Store s, Label l) {
    ObjectArray n = (ObjectArray)new ObjectArray._Impl(s, l, fabric.lang.Object.class, 
        args.length).$getProxy();
    for(int i = 0; i < args.length; i++)
      n.set(i, fabric.lang.WrappedJavaInlineable.$wrap(args[i]));
    return n;
  }
  
  private static Object getObjectByOid(Store s, long onum) {
    return fabric.lang.Object._Proxy.$getProxy(
        new fabric.lang.Object._Proxy(s, onum));
  }
}
