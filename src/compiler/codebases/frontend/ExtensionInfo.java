package codebases.frontend;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import polyglot.frontend.FileSource;
import polyglot.types.reflect.ClassPathLoader;
import codebases.types.CBTypeEncoder;
import codebases.types.CodebaseTypeSystem;
import fabric.lang.Object;
import fabric.worker.Store;

/**
 * This interface defines methods expected by the classes supporting
 * decentralized name resolution.
 * 
 * @author owen
 */
public interface ExtensionInfo extends polyglot.frontend.ExtensionInfo {

  /**
   * Return the type encoder used by this extension.
   * @return the type encoder
   */
  CBTypeEncoder typeEncoder();

  /**
   * Create a classpath loader for a given namespace.
   * @return the classpath loader
   */
  ClassPathLoader classpathLoader(URI namespace);

  /**
   * Create a source loader for a given namespace.
   * @return the source loader
   */
  URISourceLoader sourceLoader(URI namespace);

  /**
   * The namespace used for source resolved against the classpath and
   * sourcepath. Specifically, the namespace used for the source we are
   * compiling.
   * 
   * @return the namespace
   */
  URI localNamespace();

  /**
   * Create a source file from a remote Fabric object.
   * 
   * @return the namespace
   */
  FileSource createRemoteSource(URI ns, Object obj, boolean b) throws IOException;

  /**
   * The namespace used for built-in types like fabric.lang.Object. This
   * namespace is always searched first during name resolution--codebases cannot
   * provide classes in this namespace.
   * 
   * @return the namespace
   */
  URI platformNamespace();

  /**
   * Return the java package prefix for the given namespace. This prefix is
   * prepended to the names of published classes to obtain a unique Java name.
   * For the localNamespace or platformNamespace, returns the empty string.
   * 
   * @return the java package prefix string
   */
  String namespaceToJavaPackagePrefix(URI ns);

  /**
   * The classpath used to resolve dependencies during compilation. May contain
   * local directories or Fabric codebase URLs.
   * 
   * @return The list of directories and codebases to search.
   */
  List<URI> classpath();
  
  /**
   * The sourcepath used to resolve source dependencies during compilation. When
   * publishing to Fabric, dependencies resolved through the sourcepath
   * will be published alongside source specified on the commandline.
   * 
   * @return The list of directories and codebases to search.
   */
  List<URI> sourcepath();

  /**
   * The locations of signature files for native classes.
   * @return The list of directories and codebases to search.
   */
  List<URI> signaturepath();

  /**
   * The locations of platform classes such as fabric.lang.Object.
   * @return The list of directories and codebases to search.
   */
  List<URI> bootclasspath();

  /**
   * A map between codebase aliases used in source and the URI of the intended codebase.
   * @return The list of directories and codebases to search.
   */
  Map<String, URI> codebaseAliases();

  Store destinationStore();
  
  @Override
  CodebaseTypeSystem typeSystem();

}
