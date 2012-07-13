package fabric.common;

import java.io.Serializable;
import java.net.URI;

import javax.tools.JavaFileManager.Location;

import fabric.lang.Codebase;

/**
 * This interface represents a namespace in Fabric.
 */
public interface FabricLocation extends Location, Serializable {
  /** Indicates whether or not this location contains a Fabric reference */
  boolean isFabricReference();

  /** Indicates whether or not this location refers to local file system */
  boolean isFileReference();

  /**
   * Returns a corresponding Codebase to this location (NOTE: May return null if
   * this location does not contain a Fabric reference)
   */
  Codebase getCodebase();

  /** Returns a uri associated with this location */
  URI getUri();

  /** Indicates whether or not the uri contained in this location is opaque */
  boolean isOpaque();

  @Override
  boolean equals(Object o);

  @Override
  int hashCode();

  @Override
  String toString();
}
