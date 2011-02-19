package fabric;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jif.types.JifTypeSystem;
import jif.types.label.ConfPolicy;
import jif.types.label.JoinIntegPolicy_c;
import jif.types.label.JoinPolicy_c;
import jif.types.label.MeetIntegPolicy_c;
import jif.types.label.MeetPolicy_c;
import jif.types.label.WriterPolicy;
import jif.types.principal.Principal;

import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.FileSource;
import polyglot.frontend.FileSourceLoader;
import polyglot.frontend.Source;
import polyglot.frontend.SourceLoader;
import polyglot.main.Report;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.security.IntegPolicy;

public class FabricSourceLoader extends FileSourceLoader {
  protected Map<URI, Codebase> codebaseCache;

  public FabricSourceLoader(ExtensionInfo sourceExt, Collection<URI> sourcePath) {
    super(sourceExt, sourcePath);
    codebaseCache = new HashMap<URI, Codebase>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.frontend.SourceLoader#classSource(java.lang.String)
   */
  @Override
  public Source classSource(String className) {
    String name = className;
    boolean done = false;
    while (!done) {
      Source source = checkForSource(name);
      if (source != null) return source;
      int dot = name.lastIndexOf('.');
      if (dot == -1)
        done = true;
      else name = name.substring(0, dot);
    }
    return null;
  }

  /** Load the source file for the given class name using the source path. */
  protected Source checkForSource(String className) {
    /* Search the source path. */
    Source s = null;
    for (Iterator i = sourcePath.iterator(); i.hasNext();) {
      URI uri = (URI) i.next();
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

  private Source checkCodeBaseForSource(URI uri, String className) {

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

    FClass fcls = codebase.getClass(className);

    if (fcls != null) {
      URI srcURI;
      String uriStr =
          "fab://" + fcls.$getStore().name() + "/" + fcls.$getOnum();
      srcURI = URI.create(uriStr);

      Source s = (Source) loadedSources.get(srcURI.toString());
      if (s != null) return s;

      IntegPolicy pol = fcls.get$label().integPolicy();
      Principal provider = transformIntegToPrincipal(pol);

      s = new FabricSource(fcls, srcURI, false, provider);
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
  @Override
  public Source fileSource(String fileName, boolean userSpecified)
      throws IOException {
    URI uri = URI.create(fileName);
    if ("fab".equals(uri.getScheme())) {
      Store store = Worker.getWorker().getStore(uri.getHost());
      Long onum = Long.parseLong(uri.getPath().substring(1)); // skip leading
                                                              // '/'
      Object o =
          fabric.lang.Object._Proxy.$getProxy(new fabric.lang.Object._Proxy(
              store, onum));
      if (!(o instanceof FClass))
        throw new InternalCompilerError("The Fabric object at " + uri
            + " is not a Fabric class.");

      FClass fcls = (FClass) o;
      Source s = (Source) loadedSources.get(uri.toString());

      if (s != null) {
        if (!s.userSpecified() && userSpecified) {
          s.setUserSpecified(true);
        }
        return s;
      }
      
      IntegPolicy pol = fcls.get$label().integPolicy();
      Principal provider = transformIntegToPrincipal(pol);
      
      s = new FabricSource(fcls, uri, userSpecified, provider);
      loadedSources.put(uri.toString(), s);
      return s;
    } else return super.fileSource(fileName, userSpecified);
  }

  protected Principal transformIntegToPrincipal(IntegPolicy pol) {
    JifTypeSystem ts = (JifTypeSystem) sourceExt.typeSystem();
    if (pol instanceof WriterPolicy) {
      WriterPolicy wp = (WriterPolicy) pol;
      if (wp.owner() != null && wp.writer() != null)
        return ts.disjunctivePrincipal(wp.position(), wp.owner(), wp.writer());
      else if (wp.owner() != null)
        return wp.owner();
      else return wp.writer();
    }
    if (pol instanceof JoinIntegPolicy_c) {
      JoinPolicy_c jp = (JoinPolicy_c) pol;
      Set principals = new HashSet(jp.joinComponents().size());
      for (Iterator iter = jp.joinComponents().iterator(); iter.hasNext();) {
        IntegPolicy ip = (IntegPolicy) iter.next();
        Principal p = transformIntegToPrincipal(ip);
        principals.add(p);
      }
      return ts.disjunctivePrincipal(jp.position(), principals);
    }
    if (pol instanceof MeetIntegPolicy_c) {
      MeetPolicy_c mp = (MeetPolicy_c) pol;
      Set principals = new HashSet(mp.meetComponents().size());
      for (Iterator iter = mp.meetComponents().iterator(); iter.hasNext();) {
        IntegPolicy ip = (IntegPolicy) iter.next();
        Principal p = transformIntegToPrincipal(ip);
        principals.add(p);
      }
      return ts.conjunctivePrincipal(mp.position(), principals);
    }
    throw new InternalCompilerError("Unexpected integ policy: " + pol);
  }
}
