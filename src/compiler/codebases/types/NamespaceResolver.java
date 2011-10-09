package codebases.types;

import java.net.URI;

import fabric.lang.security.Label;

import polyglot.types.Importable;
import polyglot.types.SemanticException;
import polyglot.types.TopLevelResolver;
import polyglot.util.Copy;

public interface NamespaceResolver extends TopLevelResolver, Copy {
  
  /**
   * Check if a type object is in the cache, returning null if not.
   * @param name The name to search for.
   */
  Importable check(String name);
  
  /**
   * Add a type object to the cache, returning null if not.
   * @param name The name to search for.
   */
  void add(String name, Importable q);
  
  /**
   * The URI of this namespace
   */
  URI namespace();

  /**
   * Get the URI of a codebase using its local name.
   * @throws SemanticException 
   */
  URI resolveCodebaseName(String name) throws SemanticException;
  
  /**
   * Find a type object in this namespace by name.
   * @param name The name to search for.
   */
  Importable find(String name) throws SemanticException;
  
  /**
   * Find a type object in this namespace by name.
   * @param name The name to search for.
   */
  Importable findImpl(String name) throws SemanticException;
  
  /**
   * Check if a package exists.
   * @param name The name to search for.
   */
  boolean packageExistsImpl(String name);

  /**
   * Specify whether to use encoded class files to resolve names in this
   * namespace. 
   * @return previous value
   */
  boolean loadEncodedClasses(boolean use);
  
  /**
   * Specify whether to use raw class files to resolve names in this
   * namespace. 
   * @return previous value
   */
  boolean loadRawClasses(boolean use);

  /**
   * Specify whether to use raw class files to resolve names in this
   * namespace. 
   * @return previous value
   */
  boolean loadSource(boolean use);

  /**
   * A lower bound on the integrity of resolution through this namespace.
   * @return
   */
  Label integrity();

}
