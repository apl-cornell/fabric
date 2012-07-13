package fabric.filemanager;

import static java.io.File.separatorChar;

import java.lang.Iterable;
import java.net.URI;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import polyglot.frontend.FileSource;
import polyglot.main.Options;
import polyglot.util.InternalCompilerError;

import codebases.frontend.ExtensionInfo;

import fabil.types.ClassFile;
import fabric.common.FabricLocation;
import fabric.common.NSUtil;
import fabric.common.FabricLocationFactory;

/**
 * FileManager implementation for Fabric - a class that provides input and
 * output access to the local file system and input access to the codebase.
 */
public class FabricFileManager extends polyglot.filemanager.ExtFileManager {
  private final ExtensionInfo extInfo;
  private boolean outputToLocalFS;

  public FabricFileManager(ExtensionInfo extInfo) {
    super(extInfo);
    this.extInfo = extInfo;
    outputToLocalFS = true;
  }
  
  public void disableOutputToLocalFS() {
    outputToLocalFS = false;
  }

  @Override
  public FileObject getFileForInput(Location location, String packageName,
      String relativeName) throws IOException {
    if (location instanceof FabricLocation) {
      FabricLocation loc = (FabricLocation) location;
      if (loc.isFabricReference()) {
        boolean hasExtension =
            relativeName.endsWith(".fab") || relativeName.endsWith(".fil");
        String clazz =
            hasExtension ? relativeName.substring(0,
                relativeName.lastIndexOf('.')) : relativeName;
        String classname =
            packageName.equals("") ? clazz : (packageName.replace('/', '.')
                + "." + clazz);
        return FileManagerUtil.getJavaFileObject(loc.getCodebase(), classname);
      }
    }
    return super.getFileForInput(location, packageName, relativeName);
  }

  @Override
  public JavaFileObject getJavaFileForOutput(Location location,
      String className, Kind kind, FileObject sibling) throws IOException {
    if (!outputToLocalFS && kind.equals(Kind.CLASS)) {
      Options options = extInfo.getOptions();
      Location classOutputLoc = options.classOutputDirectory();
      if (location == null || !classOutputLoc.equals(location)
          || !javac_fm.hasLocation(classOutputLoc)) return null;
      URI classUri, classParentUri;
      if (sibling == null) {
        File classdir = null;
        for (File f : javac_fm.getLocation(classOutputLoc)) {
          classdir = f;
          break;
        }
        if (classdir == null)
          throw new IOException("Class output directory is not set.");
        File classfile =
            new File(classdir, className.replace('.', separatorChar)
                + kind.extension);
        classUri = classfile.toURI();
        classParentUri = classfile.getParentFile().toURI();
      } else {
        File classdir = new File(sibling.toUri()).getParentFile();
        File classfile =
            new File(classdir,
                className.substring(className.lastIndexOf('.') + 1)
                    + kind.extension);
        classUri = classfile.toURI();
        classParentUri = classfile.getParentFile().toURI();
      }
      JavaFileObject jfo = new ClassObject(classUri);
      absPathObjMap.put(classUri, jfo);
      if (pathObjectMap.containsKey(classParentUri))
        pathObjectMap.get(classParentUri).add(jfo);
      else {
        Set<JavaFileObject> s = new HashSet<JavaFileObject>();
        s.add(jfo);
        pathObjectMap.put(classParentUri, s);
      }
      return jfo;
    }
    return super.getJavaFileForOutput(location, className, kind, sibling);
  }

  @Override
  public boolean packageExists(Location location, String name) {
    if (location instanceof FabricLocation) {
      FabricLocation loc = (FabricLocation) location;
      if (loc.isFabricReference())
        return FileManagerUtil.packageExists(loc.getCodebase(), name);
    }
    return super.packageExists(location, name);
  }

  @Override
  public ClassFile loadFile(Location location, String name) {
    String className = name;
    JavaFileObject jfo = null;
    if (location instanceof FabricLocation) {
      FabricLocation loc = (FabricLocation) location;
      try {
        if (loc.isFabricReference()) {
          className = extInfo.namespaceToJavaPackagePrefix(loc) + name;
          jfo =
              getJavaFileForInput(extInfo.getOptions().classOutputDirectory(),
                  className, Kind.CLASS);
        } else jfo = getJavaFileForInput(loc, className, Kind.CLASS);
      } catch (IOException e) {
        throw new InternalCompilerError(e);
      }
    } else try {
      jfo = getJavaFileForInput(location, className, Kind.CLASS);
    } catch (IOException e1) {
      throw new InternalCompilerError(e1);
    }
    try {
      if (jfo != null) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InputStream is = jfo.openInputStream();
        byte buf[] = new byte[BUF_SIZE];
        int len;
        while ((len = is.read(buf, 0, BUF_SIZE)) != -1)
          bos.write(buf, 0, len);
        return extInfo.createClassFile(jfo, bos.toByteArray());
      }
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
    return null;
  }

  @Override
  public String inferBinaryName(Location location, JavaFileObject file) {
    if (file instanceof FabricSourceObject) {
      String className = ((FabricSourceObject) file).getName();
      return className.substring(className.lastIndexOf('.') + 1);
    }
    return super.inferBinaryName(location, file);
  }

  @Override
  public Iterable<JavaFileObject> list(Location location, String packageName,
      Set<Kind> kinds, boolean recurse) throws IOException {
    if (location instanceof FabricLocation) {
      FabricLocation loc = (FabricLocation) location;
      if (loc.isFabricReference())
        return FileManagerUtil.getJavaFileObjects(loc.getCodebase(),
            packageName, recurse);
    }
    return super.list(location, packageName, kinds, recurse);
  }

  @Override
  public FileSource fileSource(String fileName) throws IOException {
    URI u = URI.create(fileName);
    if (!u.isAbsolute())
      throw new InternalCompilerError("Expected absolute URI");
    return fileSource(
        FabricLocationFactory.getLocation(false, NSUtil.dirname(u)),
        NSUtil.basename(u), false);
  }

  @Override
  public FileSource fileSource(String fileName, boolean userSpecified)
      throws IOException {
    URI u = URI.create(fileName);
    if (!u.isAbsolute())
      throw new InternalCompilerError("Expected absolute URI");
    FabricLocation loc =
        FabricLocationFactory.getLocation(false, NSUtil.dirname(u));
    if (loc.isFabricReference()) {
      FileObject fo = getFileForInput(loc, "", NSUtil.basename(u));
      return extInfo.createFileSource(fo, userSpecified);
    } else if (loc.isFileReference())
      return fileSource(loc, NSUtil.basename(u), userSpecified);
    throw new IOException("Error: Illegal location " + loc);
  }
}