package codebases.frontend;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;

import fabric.lang.security.Label;

import polyglot.frontend.Source;

public interface CodebaseSource {
  /**
   * The namespace loading this source.
   */
  URI namespace();

  /**
   * The short name of this source (i.e. its filename).
   * @return
   */
  String name();

  /**
   * The canonical namespace of source is the namespace its dependencies are resolved in.
   * @return The canonical namespace of this source
   */
  URI canonicalNamespace();

  /**
   * Some compiler jobs are dynamically generated during compiling a resource,
   * but are not directly stored in the resource. For instance when a Fabric
   * interface is translated to FabIL, the compiler generates a *_JIF_IMPL class
   * that implements instanceof for that interface, among other things. To
   * ensure flexible handling between phases, we create a 'derived' source that
   * is backed by the link to the original resource, but permits a new job to be
   * scheduled.
   * 
   * @param name
   * @return A source derived from this CodebaseSource with a new (short) name
   */
  Source derivedSource(String name);

  /**
   * When local Fabric source is published and then compiled to bytecode, during
   * FabIL compilation the namespace should be the published namespace. This
   * ensures the compiled code is compatible with the published classes and
   * prevents, and avoids forcing the user to invoke the compiler again with new
   * arguments.
   * 
   * @param namespace
   * @return A source derived from this CodebaseSource in a new namespace
   */
  Source derivedSource(URI namespace);

  /**
   * Whether a new class object should be published for this source file and
   * associated with the codebase representing the namespace.
   * 
   * @return 
   */
  boolean shouldPublish();

  /**
   * The label of this source file.
   */
  Label label();

  /** Open the source file. */
  public Reader open() throws IOException;
  /** Close the source file. */
  public void close() throws IOException;

  void setPublish(boolean pub);
}
