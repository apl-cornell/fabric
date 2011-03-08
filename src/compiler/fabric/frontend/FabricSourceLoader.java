package fabric.frontend;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.FileSource;
import polyglot.frontend.SourceLoader;
import polyglot.main.Report;
import polyglot.util.InternalCompilerError;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.worker.Store;
import fabric.worker.Worker;

public class FabricSourceLoader extends SourceLoader {
  protected Map<URI, Codebase> codebaseCache;
  protected Collection<URI> sourcePath;
  public FabricSourceLoader(ExtensionInfo sourceExt, Collection<URI> sourcePath) {
    super(sourceExt, null);
    this.sourcePath = sourcePath;
    codebaseCache = new HashMap<URI, Codebase>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.frontend.SourceLoader#classSource(java.lang.String)
   */
  @Override
  public FileSource classSource(String className) {
      return checkForSource(className);
  }
  
  /* (non-Javadoc)
   * @see polyglot.frontend.SourceLoader#packageExists(java.lang.String)
   */
  @Override
  public boolean packageExists(String name) {
    throw new UnsupportedOperationException();
  }

  /** Load the source file for the given class name using the source path. */
  protected FileSource checkForSource(String className) {
    /* Search the source path. */
    FileSource s = null;
    for (Iterator<URI> i = sourcePath.iterator(); i.hasNext();) {
      URI uri = i.next();
      if (uri.getScheme().equals("file")) {
        File directory = new File(uri);
        if (!directory.isDirectory()) continue;
        s = checkDirectoryForSource(directory, className);
      } else if (uri.getScheme().equals("fab")) {
        // In Fabric, source is associated with CodeBase objects by class name 
        s = checkCodeBaseForSource(uri, className);
      } else throw new InternalCompilerError(
          "Don't know how to load source from " + uri);
      
      if (s != null) return s;
    }
    return null;
  }
    
  @SuppressWarnings("unchecked")
  protected FileSource checkCodeBaseForSource(URI uri, String className) {     
    Codebase codebase = codebaseCache.get(uri);
    if (codebase == null) {
      Store store = Worker.getWorker().getStore(uri.getHost());
      Long onum = Long.parseLong(uri.getPath().substring(1)); // skip leading
                                                              // '/'
      Object o =
          fabric.lang.Object._Proxy.$getProxy(new fabric.lang.Object._Proxy(
              store, onum));
      if (!(o instanceof Codebase))
        throw new InternalCompilerError("The Fabric object at " + uri
            + " is not a codebase.");

      codebase = (Codebase) o;
      codebaseCache.put(uri, codebase);
    }

    FClass fcls = codebase.resolveClassName(className);

    if (fcls != null) {
      URI srcURI;
      String uriStr =
          "fab://" + fcls.$getStore().name() + "/" + fcls.$getOnum();
      srcURI = URI.create(uriStr);

      FileSource s = (FileSource) loadedSources.get(srcURI.toString());
      if (s != null) return s;

//      IntegPolicy pol = fcls.get$label().integPolicy();
//      Principal provider = transformIntegToPrincipal(pol);
      fabric.ExtensionInfo ext = (fabric.ExtensionInfo) sourceExt;
      try {
        s = ext.createRemoteSource(fcls, false);
      } catch (IOException e) {
        throw new InternalCompilerError(e);
      }
      loadedSources.put(srcURI.toString(), s);
      return s;
    }

    return null;
  }

  /**
   * The current user directory. We make it static so we don't need to keep on
   * making copies of it.
   */
  protected static File current_dir = null;

  /**
   * The current user directory.
   */
  protected static File current_dir() {
    if (current_dir == null) {
      current_dir = new File(System.getProperty("user.dir"));
    }
    return current_dir;
  }

  // This is mostly copied from SourceLoader_c.checkForSource
  @SuppressWarnings("unchecked")
  protected FileSource checkDirectoryForSource(File directory, String className) {
    String[] exts = sourceExt.fileExtensions();

    for (int k = 0; k < exts.length; k++) {

      String fileName =
          className.replace('.', File.separatorChar) + "." + exts[k];

      Set<String> dirContents =
          (Set<String>) directoryContentsCache.get(directory);
      if (dirContents == null) {
        dirContents = new HashSet<String>();
        directoryContentsCache.put(directory, dirContents);
        if (directory.exists()) {
          String[] contents = directory.list();
          // May return null if directory is not found
          if (contents == null) continue;
          for (int j = 0; j < contents.length; j++) {
            dirContents.add(contents[j]);
          }
        }
      }
      // check if the source file exists in the directory
      int index = fileName.indexOf(File.separatorChar);
      if (index < 0) index = fileName.length();
      String firstPart = fileName.substring(0, index);

      if (dirContents.contains(firstPart)) {
        // the directory contains at least the first part of the
        // file path. We will check if this file exists.
        File sourceFile;

        if (directory != null && directory.equals(current_dir())) {
          sourceFile = new File(fileName);
        } else {
          sourceFile = new File(directory, fileName);
        }

        // Skip it if already loaded
        FileSource s = (FileSource) loadedSources.get(fileKey(sourceFile));

        if (s != null) {
          return s;
        }

        try {
          if (Report.should_report(Report.loader, 2))
            Report.report(2, "Loading " + className + " from " + sourceFile);
          s = sourceExt.createFileSource(sourceFile, false);
          loadedSources.put(fileKey(sourceFile), s);
          return s;
        } catch (IOException e) {
        }
      }
    }
    return null;

  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.frontend.SourceLoader#fileSource(java.lang.String, boolean)
   */
  @SuppressWarnings("unchecked")
  @Override
  public FileSource fileSource(String fileName, boolean userSpecified)
      throws IOException {
    URI uri = URI.create(fileName);
    if ("fab".equals(uri.getScheme())) {
      Store store = Worker.getWorker().getStore(uri.getHost());
      String path = uri.getPath().substring(1);

      FClass fcls;
      if (path.contains("/")) {
        // parse as a codebase oid + class name
        String[] pair = path.split("/");
        long onum = Long.parseLong(pair[0]);
        String className = pair[1];
        Object o =
            fabric.lang.Object._Proxy.$getProxy(new fabric.lang.Object._Proxy(
                store, onum));
        if (!(o instanceof Codebase))
          throw new InternalCompilerError("The Fabric object at " + uri
              + " is not a Codebase.");
        Codebase cb = (Codebase) o;
        fcls = cb.resolveClassName(className);
      }
      else {
        // parse as an fclass oid
        long onum = Long.parseLong(path); 
        Object o =
            fabric.lang.Object._Proxy.$getProxy(new fabric.lang.Object._Proxy(
                store, onum));
        if (!(o instanceof FClass))
          throw new InternalCompilerError("The Fabric object at " + uri
              + " is not a Fabric class.");
        fcls = (FClass)o;
      }

      FileSource s = (FileSource) loadedSources.get(uri.toString());

      if (s != null) {
        if (!s.userSpecified() && userSpecified) {
          s.setUserSpecified(true);
        }
        return s;
      }
      
      s = ((fabric.ExtensionInfo) sourceExt).createRemoteSource(fcls, false);
      loadedSources.put(uri.toString(), s);
      return s;

    } else return super.fileSource(fileName, userSpecified);
  }

  @SuppressWarnings("unchecked")
  public FileSource fileSource(URI fileURI, boolean userSpecified) throws IOException {
    if (fileURI.isAbsolute() && "fab".equals(fileURI.getScheme())) {
      Store store = Worker.getWorker().getStore(fileURI.getHost());
      Long onum = Long.parseLong(fileURI.getPath().substring(1)); // skip leading
                                                              // '/'
      Object o =
          fabric.lang.Object._Proxy.$getProxy(new fabric.lang.Object._Proxy(
              store, onum));
      if (!(o instanceof FClass))
        throw new InternalCompilerError("The Fabric object at " + fileURI
            + " is not a Fabric class.");

      FClass fcls = (FClass) o;
      FileSource s = (FileSource) loadedSources.get(fileURI.toString());

      if (s != null) {
        if (!s.userSpecified() && userSpecified) {
          s.setUserSpecified(true);
        }
        return s;
      }
      
      s = ((fabric.ExtensionInfo) sourceExt).createRemoteSource(fcls, false);
      loadedSources.put(fileURI.toString(), s);
      return s;
    }
      return super.fileSource(fileURI.getPath(), userSpecified);
  }
}
