package codebases.frontend;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;

import polyglot.frontend.Source;

public interface CodebaseSource {
  URI namespace();
  String name();
  URI canonicalNamespace();
  /**
   * Some compiler jobs are dynamically generated during compiling a resource, but are not 
   * directly stored in the resource.  For instance when a Fabric interface is translated 
   * to FabIL, the compiler generates a *_JIF_IMPL class that implements instanceof for that 
   * interface, among other things.
   * 
   * To ensure flexible handling between phases, we create a 'derived' source that 
   * is backed by the link to the original resource, but permits a new job to be scheduled. 
   * 
   * @param name
   * @return A source derived from this CodebaseSource with a new (short) name

   */
  Source derivedSource(String name);
  
  /**
   * Similarly, when local Fabric source is published and then compiled to
   * bytecode, during FabIL compilation the namespace should be the published
   * namespace. This ensures the compiled code is compatible with the published
   * classes and prevents, and avoids forcing the user to invoke the compiler
   * again with new arguments.
   * 
   * @param namespace
   * @return A source derived from this CodebaseSource in a new namespace
   */
  Source derivedSource(URI namespace);
  boolean shouldPublish();

  /** Open the source file. */
  public Reader open() throws IOException;
  /** Close the source file. */
  public void close() throws IOException;
}
