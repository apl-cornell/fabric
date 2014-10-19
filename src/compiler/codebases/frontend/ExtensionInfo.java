package codebases.frontend;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.tools.FileObject;

import codebases.types.CBTypeEncoder;
import codebases.types.ClassFile;
import codebases.types.CodebaseTypeSystem;
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
   *
   * @return the type encoder
   */
  CBTypeEncoder typeEncoder();

  /**
   * The namespace used for source resolved against the classpath and
   * sourcepath. Specifically, the namespace used for the source we are
   * compiling.
   *
   * @return the namespace
   */
  URI localNamespace();

  /**
   * The namespace used for built-in types like fabric.lang.Object. This
   * namespace is always searched first during name resolution--codebases cannot
   * provide classes in this namespace.
   *
   * @return the namespace
   */
  URI platformNamespace();

  @Override
  ClassFile createClassFile(FileObject fo, byte[] code) throws IOException;

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
   * publishing to Fabric, dependencies resolved through the sourcepath will be
   * published alongside source specified on the commandline.
   *
   * @return The list of directories and codebases to search.
   */
  List<URI> sourcepath();

  /**
   * The locations of java boot classes
   *
   * @return The list of directories on local file system
   */
  List<File> bootclasspath();

  /**
   * A map between codebase aliases used in source and the URI of the intended
   * codebase.
   *
   * @return The list of directories and codebases to search.
   */
  Map<String, URI> codebaseAliases();

  Store destinationStore();

  @Override
  CodebaseTypeSystem typeSystem();

}
