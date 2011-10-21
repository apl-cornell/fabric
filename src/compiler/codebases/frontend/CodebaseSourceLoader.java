package codebases.frontend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import polyglot.frontend.FileSource;
import polyglot.main.Report;
import polyglot.util.InternalCompilerError;
import fabric.Topics;
import fabric.common.NSUtil;
import fabric.lang.Codebase;

public class CodebaseSourceLoader implements URISourceLoader {
  protected static String[] TOPICS = new String[] {Report.loader, Topics.mobile};

  protected final ExtensionInfo extInfo;
  protected final URI ns;
  protected final Codebase codebase;

  protected final Map<URI, FileSource> loadedSources;
  
  public CodebaseSourceLoader(ExtensionInfo extInfo, URI ns) {
    this(extInfo, ns, NSUtil.fetch_codebase(ns));
  }
  
  public CodebaseSourceLoader(ExtensionInfo extInfo, Codebase codebase) {
    this(extInfo, NSUtil.namespace(codebase));
  }

  /**
   * This constructor can be used to decouple remote source from its codebase. For
   * instance, to recompile/relink published code and publish under a new
   * namespace, provide the codebase where the source is stored, and the new ns
   * to load the new source into.
   * 
   * @param extInfo
   * @param uri
   * @param codebase
   */
  public CodebaseSourceLoader(ExtensionInfo extInfo, URI uri, Codebase codebase) {
    this.extInfo = extInfo;
    this.ns = uri;
    this.codebase = codebase;
    this.loadedSources = new HashMap<URI, FileSource>();
  }

  @Override
  //FIXME:
  public boolean packageExists(String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public FileSource classSource(String className) {
    if (Report.should_report(TOPICS, 3))
      Report.report(3, "Checking " + NSUtil.namespace(codebase) + " for " + className);

    fabric.lang.Object obj = codebase.resolveClassName(className);

    if (obj != null) {
      URI class_uri = ns.resolve(className);

      FileSource s = loadedSources.get(class_uri);
      if (s != null) return s;

      //NB: ns may be different from obj.getCodebase() if we are re-publishing
      try {
        s = extInfo.createRemoteSource(ns, obj, false);
      } catch (IOException e) {
        throw new InternalCompilerError(e);
      }
      loadedSources.put(class_uri, s);
      return s;
    }

    return null;
  }
  @Override
  public FileSource fileSource(URI uri) throws IOException {
    return fileSource(uri, false);
  }
  
  @Override
  public FileSource fileSource(URI file, boolean userSpecified)
      throws IOException {
    
    FileSource s = loadedSources.get(file);
    if (s != null)
      return s;
    
    if (!NSUtil.dirname(file).equals(this.ns))
      throw new FileNotFoundException("Cannot load " + file + " from " + ns);
    
    String className = NSUtil.basename(file);
    fabric.lang.Object obj = codebase.resolveClassName(className);

    if (obj != null) {
      URI class_uri = ns.resolve(className);

      s = extInfo.createRemoteSource(ns, obj, false);
      loadedSources.put(class_uri, s);
      return s;
    }
    throw new FileNotFoundException(file.toString());
  }
  
}
