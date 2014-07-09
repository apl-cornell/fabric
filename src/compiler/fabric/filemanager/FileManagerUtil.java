/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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
package fabric.filemanager;

/**
 * FabricUtil provides helper methods
 */
public class FileManagerUtil {

//  public static JavaFileObject getJavaFileObject(Codebase codebase,
//      String classname) {
//    FClass obj = codebase.resolveClassName(classname);
//    if (obj != null) {
//      return new FabricFileObject(obj, NSUtil.namespace(
//          obj.getCodebase()).resolve(classname), classname + ".fab");
//    }
//    return null;
//  }
//
//  public static boolean packageExists(Codebase codebase, String packageName) {
//    Set names = codebase.getClasses().entrySet();
//    for (Iterator it = names.iterator(); it
//        .hasNext();) {
//      Entry entry = (Entry) it.next();
//      String classname = (String) WrappedJavaInlineable.$unwrap(entry.getKey());
//      String pkgName = StringUtil.getPackageComponent(classname);
//      if (pkgName.startsWith(packageName))
//        return true;
//    }
//    return false;
//  }
//
//  public static List<JavaFileObject> getJavaFileObjects(Codebase codebase,
//      String packageName, boolean recurse) {
//    List<JavaFileObject> l = new ArrayList<JavaFileObject>();
//    Set names = codebase.getClasses().entrySet();
//    for (Iterator it = names.iterator(); it.hasNext();) {
//      Entry entry = (Entry) it.next();
//      String classname = (String) WrappedJavaInlineable.$unwrap(entry.getKey());
//      String pkgName = StringUtil.getPackageComponent(classname);
//      if (pkgName.startsWith(packageName)) {
//        if (recurse || pkgName.equals(packageName)) {
//          fabric.lang.FClass obj = (FClass) entry.getValue();
//          l.add(new FabricFileObject(obj, NSUtil.namespace(codebase)
//              .resolve(classname), classname + ".fab"));
//        }
//      }
//    }
//    return l;
//  }
}
