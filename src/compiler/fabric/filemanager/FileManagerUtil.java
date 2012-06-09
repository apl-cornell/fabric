package fabric.filemanager;

import java.util.List;
import java.util.ArrayList;

import javax.tools.JavaFileObject;

import polyglot.util.StringUtil;

import fabric.common.NSUtil;
import fabric.lang.FClass;
import fabric.lang.Object;
import fabric.lang.Codebase;
import fabric.lang.WrappedJavaInlineable;
import fabric.util.Iterator;
import fabric.util.Set;
import fabric.util.Map.Entry;

/**
 * FabricUtil provides helper methods
 */
public class FileManagerUtil {

  public static JavaFileObject getJavaFileObject(Codebase codebase,
      String classname) {
    Object obj = codebase.resolveClassName(classname);
    if (obj != null) {
      if (obj instanceof FClass)
        return new FabricSourceObject(obj, NSUtil.namespace(
            ((FClass) obj).getCodebase()).resolve(classname), classname + ".fab");
      return new FabricSourceObject(obj, NSUtil.namespace(codebase).resolve(
          classname), classname + ".fab");
    }
    return null;
  }
  
  public static boolean packageExists(Codebase codebase, String packageName) {
    Set names = codebase.getClasses().entrySet();
    for (Iterator it = names.iterator(); it
        .hasNext();) {
      Entry entry = (Entry) it.next();
      String classname = (String) WrappedJavaInlineable.$unwrap(entry.getKey());
      String pkgName = StringUtil.getPackageComponent(classname);
      if (pkgName.startsWith(packageName))
        return true;
    }
    return false;
  }

  public static List<JavaFileObject> getJavaFileObjects(Codebase codebase,
      String packageName, boolean recurse) {
    List<JavaFileObject> l = new ArrayList<JavaFileObject>();
    Set names = codebase.getClasses().entrySet();
    for (Iterator it = names.iterator(); it.hasNext();) {
      Entry entry = (Entry) it.next();
      String classname = (String) WrappedJavaInlineable.$unwrap(entry.getKey());
      String pkgName = StringUtil.getPackageComponent(classname);
      if (pkgName.startsWith(packageName)) {
        if (recurse || pkgName.equals(packageName)) {
          fabric.lang.Object obj = entry.getValue();
          if (obj instanceof FClass)
            l.add(new FabricSourceObject(obj, NSUtil.namespace(
                ((FClass) obj).getCodebase()).resolve(classname), classname + ".fab"));
          else l.add(new FabricSourceObject(obj, NSUtil.namespace(codebase)
              .resolve(classname), classname + ".fab"));
        }
      }
    }
    return l;
  }
}
