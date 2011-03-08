package fabric.worker;

import java.lang.reflect.InvocationTargetException;

import fabric.lang.FabricClassLoader;
import fabric.lang.arrays.ObjectArray;

/**
 * This is a wrapper for <code>fabric.worker.Worker</code> that will use
 * mobile code loading by loading the <code>Worker</code> with the
 * <code>FabricClassLoader</code>
 * 
 * @see fabric.worker.Worker
 * @see fabric.lang.FabricClassLoader
 * 
 */
public class MobileWorker {

  public static void main(String[] args) throws Throwable {
    FabricClassLoader loader = new FabricClassLoader(
        MobileWorker.class.getClassLoader()); 
    Class<?> cls = loader.loadClass("fabric.worker.Worker");
    
    //TODO: we don't want the user to specify the class name to fab to be a 
    // mangled name, because that would be hard and annoying to write. We'll
    // want to come up with a way to specify the name of the class to run in a
    // nicer way. But, for now, you can just use the mangled name to get the
    // class loader into running mobile code.
    
    cls.getMethod("main", String[].class).invoke(null, (Object)args);
  }
  
}
