package fabric.filemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import polyglot.filemanager.ExtFileObject;
import polyglot.frontend.FileSource;
import polyglot.frontend.Source;
import polyglot.util.InternalCompilerError;
import polyglot.util.StringUtil;
import codebases.frontend.ExtensionInfo;
import codebases.types.ClassFile;
import fabric.common.NSUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.WrappedJavaInlineable;
import fabric.util.Iterator;
import fabric.util.Map.Entry;
import fabric.util.Set;

/**
 * FileManager implementation for Fabric - a class that provides input and
 * output access to the local file system and input access to the codebase.
 *
 * TODO: store output files in Fabric. Support bytecode sharing between
 *      trusted principals
 */
public class FabricFileManager extends polyglot.filemanager.ExtFileManager {
  protected final Map<Location, List<URI>> pathMap;
  protected final Map<URI, Codebase> codebaseCache;
//  protected final Map<URI, List<File>> dirCache;
  protected final Map<URI, Location> nsClassLocation;
  protected final Map<URI, Location> nsSrcLocation;
  private final ExtensionInfo extInfo;

  public FabricFileManager(ExtensionInfo extInfo) {
    super(extInfo);
    this.extInfo = extInfo;
    pathMap = new HashMap<>();
    codebaseCache = new HashMap<>();
//    dirCache = new HashMap<URI, List<File>>();
    nsClassLocation = new HashMap<>();
    nsClassLocation.put(extInfo.platformNamespace(),
        extInfo.getOptions().bootclasspath);
    nsClassLocation.put(extInfo.localNamespace(),
        extInfo.getOptions().classpath);

    this.nsSrcLocation = new HashMap<>();
    nsSrcLocation.put(extInfo.platformNamespace(),
        extInfo.getOptions().bootclasspath);
    nsSrcLocation.put(extInfo.localNamespace(),
        extInfo.getOptions().source_path);
  }

  @Override
  public FileObject getFileForInput(Location location, String packageName,
      String relativeName) throws IOException {
    List<URI> locPath;
    if (location instanceof CodebaseLocation) {
      CodebaseLocation cbloc = (CodebaseLocation) location;
      locPath = Collections.singletonList(cbloc.namespace());
    } else locPath = pathMap.get(location);

    if (locPath != null) {
      boolean isSource =
          relativeName.endsWith(".fab") || relativeName.endsWith(".fil");
      String classname =
          relativeName.substring(0, relativeName.lastIndexOf('.'));
      classname =
          packageName.equals("") ? classname : (packageName.replace('/', '.')
              + "." + classname);

      for (URI pathEntry : locPath) {
        if (pathEntry.getScheme().equals("fab")) {
          Codebase codebase = codebase(pathEntry);
          if (isSource) {
            // if we are looking for source, check the codebase
            FClass obj = codebase.resolveClassName(classname);
            if (obj != null) {
              return new FabricFileObject(obj, NSUtil.namespace(
                  obj.getCodebase()).resolve(classname), classname + ".fab");
            }
          } else {
            // otherwise, check the output directory for .class or .java
            FileObject fo =
                super.getFileForInput(extInfo.getOptions()
                    .classOutputLocation(), packageName, relativeName);
            if (fo != null) return fo;
          }
        } else {
          if (pathEntry.getPath().endsWith(".jar")) {
            final JarFile jar;
            try {
              @SuppressWarnings("resource")
              JarFile jarFile = new JarFile(pathEntry.getPath());
              jar = jarFile;
            } catch (FileNotFoundException e) {
              continue;
            } catch (java.util.zip.ZipException e) {
              continue;
            }

            String entryName = fileKey(packageName, relativeName);
            final ZipEntry entry = jar.getEntry(entryName);
            if (entry != null) {
              URI u =
                  URI.create("file://" + pathEntry.getPath() + "!/" + entryName);
              Kind k = isSource ? Kind.SOURCE : Kind.CLASS;
              FileObject fo = new ExtFileObject(u, k) {
                @Override
                public InputStream openInputStream() throws IOException {
                  return jar.getInputStream(entry);
                }
              };
              return fo;
            }
          } else {
            File dirEntry = new File(pathEntry);
            File f = new File(dirEntry, fileKey(packageName, relativeName));
            if (f.exists() && f.isFile()) {
              for (JavaFileObject jfo : super.getJavaFileObjects(f))
                return jfo;
            }
          }
        }
      }
    }
    return super.getFileForInput(location, packageName, relativeName);
  }

  /**
   * @param u
   * @return
   */
  protected Codebase codebase(URI u) {
    Codebase cb = codebaseCache.get(u);
    if (cb == null) {
      cb = NSUtil.fetch_codebase(u);
      codebaseCache.put(u, cb);
    }
    return cb;
  }

  @Override
  public boolean packageExists(Location location, String name) {
    List<URI> locPath = pathMap.get(location);
    if (locPath != null) {
      for (URI pathEntry : locPath) {
        if (pathEntry.getScheme().equals("fab")) {
          Codebase codebase = codebase(pathEntry);
          Set names = codebase.getClasses().entrySet();
          for (Iterator it = names.iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();
            String classname =
                (String) WrappedJavaInlineable.$unwrap(entry.getKey());
            String pkgName = StringUtil.getPackageComponent(classname);
            if (pkgName.startsWith(name)) return true;
          }
        } else {
          String packageName = name.replace('.', File.separatorChar);
          if (pathEntry.getPath().endsWith(".jar")) {
            JarFile jar;
            try {
              jar = new JarFile(pathEntry.getPath());
              final ZipEntry entry = jar.getEntry(packageName);
              if (entry != null) {
                return true;
              }
            } catch (IOException e) {
            }
          } else {
            File dirEntry = new File(pathEntry);
            File f = new File(dirEntry, packageName);
            if (f.exists()) return true;
          }
        }
      }
      return false;
    } else return super.packageExists(location, name);
  }

  @Override
  public FileSource fileSource(String fileName) throws IOException {
    return fileSource(fileName, Source.Kind.DEPENDENCY);
  }

  @Override
  public FileSource fileSource(String fileName, Source.Kind kind)
      throws IOException {
    URI u = URI.create(fileName);
    if (!u.isAbsolute())
      throw new InternalCompilerError("Expected absolute URI");
    JavaFileObject jfo = getJavaFileObject(u);
    if (jfo == null) throw new FileNotFoundException(fileName);
    return extInfo.createFileSource(jfo, kind);
  }

  /**
   * Associate a list of files with a location, removing any previous associations.
   */
  @Override
  public void setLocation(Location location, Iterable<? extends File> path)
      throws IOException {
    super.setLocation(location, path);
    // remove any URI associations.
    pathMap.remove(location);
  }

  /**
   * Associate a list of URIs with a location, removing any previous associations.
   */
  public void setLocation(Location location, List<URI> directories)
      throws IOException {
    if (location.isOutputLocation())
      throw new InternalCompilerError(
          "Configuring output locations via this method is "
              + "currently unsupported.");
    pathMap.put(location, directories);
    // remove any file associations.
    super.setLocation(location, Collections.<File> emptySet());
  }

  @Override
  public Iterable<? extends File> getLocation(Location location) {
    return super.getLocation(location);
  }

  public List<URI> getLocationURIs(Location location) {
    return pathMap.get(location);
  }

  @Override
  public boolean hasLocation(Location location) {
    return super.hasLocation(location) || pathMap.containsKey(location);
  }

  /**
   * @param namespace
   * @param name
   * @return
   */
  public ClassFile loadFile(URI namespace, String name) {
    String className = extInfo.namespaceToJavaPackagePrefix(namespace) + name;
    JavaFileObject jfo = null;
    if (!className.equals(name)) {
      //className is a mangled name. Check class cache
      try {
        jfo =
            getJavaFileForInput(extInfo.getOptions().classOutputLocation(),
                className, Kind.CLASS);
        if (jfo != null) return extInfo.createClassFile(jfo, getBytes(jfo));

      } catch (IOException e) {
        throw new InternalCompilerError(
            "Error while checking cache for class file " + name, e);
      }
      return null;
    } else {
      Location location = nsClassLocation.get(namespace);
      return (ClassFile) loadFile(location, className);
    }
  }

  /**
   * @param namespace
   * @param name
   * @return
   */
  public Source classSource(URI namespace, String name) {
    Location location = nsSrcLocation.get(namespace);
    if (location != null) {
      return classSource(location, name);
    } else {
      if (namespace.isOpaque())
        throw new InternalCompilerError("No location for " + namespace);
      // namespace should be a Fabric reference
      location = namespaceSourceLocation(namespace);
      return classSource(location, name);
    }
  }

  public JavaFileObject getJavaFileObject(URI uri) throws IOException {
    if (uri.getScheme().equals("file")) {
      for (JavaFileObject jfo : getJavaFileObjects(new File(uri)))
        return jfo;
      return null;
    } else {
      if (uri.isOpaque())
        throw new InternalCompilerError("Can't get file object for " + uri);

      Location location = namespaceSourceLocation(NSUtil.dirname(uri));
      String className = NSUtil.basename(uri);
      return (JavaFileObject) getFileForInput(location, "", className + ".fab");
    }
  }

  /**
   * Returns the location associated with class files for this namespcae.
   * For remote namespaces, the class location in always the class output directory.
   */
  public Location namespaceClassLocation(URI namespace) {
    Location location = nsClassLocation.get(namespace);
    if (location != null) {
      return location;
    } else {
      if (namespace.isOpaque())
        throw new InternalCompilerError("No location for " + namespace);
      // namespace is a codebase.  Use the class cache.
      return extInfo.getOptions().classOutputLocation();
    }
  }

  /**
   * Returns the location associated with source files for this namespcae.
   */
  public Location namespaceSourceLocation(URI namespace) {
    Location location = nsSrcLocation.get(namespace);
    if (location == null) {
      if (namespace.isOpaque())
        throw new InternalCompilerError("No location for " + namespace);
      // namespace is a codebase.  Create a new Location.
      location = createCodebaseLocation(namespace);
      nsSrcLocation.put(namespace, location);
    }
    return location;
  }

  protected Location createCodebaseLocation(URI namespace) {
    return new CodebaseLocation_c(namespace);
  }

  /**
   * @param namespace
   * @param name
   * @return
   */
  public boolean packageExists(URI namespace, String name) {
    Location classLoc = namespaceClassLocation(namespace);
    Location srcLoc = namespaceSourceLocation(namespace);
    return packageExists(classLoc, name) || packageExists(srcLoc, name);
  }
}
