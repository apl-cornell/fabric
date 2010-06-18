
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.FabricClassLoader;
import fabric.Codebase;
import fabric.lang.security.Label;
import java.io.IOException;
import java.security.GeneralSecurityException;
import fabric.common.exceptions.UsageError;
import java.lang.reflect.InvocationTargetException;
import fabric.lang.arrays.ObjectArray;

public class RunClass {

  /**
   * RunClass workerName codebaseStore codebaseOnum className
   */
  public static void main(String[] args) 
    throws IOException, GeneralSecurityException, IllegalAccessException, 
    ClassNotFoundException, InvocationTargetException, NoSuchMethodException, UsageError {
    if(args.length != 4) {
      System.out.println("Usage: RunClass workerName codebaseStore codebaseOnum className");
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
      public Void run() {
        ObjectArray programArgs = toObjectArray(new String[0], ls, 
            Worker.getWorker().getLocalStore().getEmptyLabel());
        try {
          cls.getMethod("main", ObjectArray.class).invoke(null, programArgs);
          return null;
        } catch(Exception ex) {
          System.err.println(ex.getMessage());
          throw new RuntimeException(ex);
        }
      }
    });
    
    System.exit(0);
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
