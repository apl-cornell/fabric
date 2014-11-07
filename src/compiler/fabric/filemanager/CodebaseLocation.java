package fabric.filemanager;

import java.net.URI;

import javax.tools.JavaFileManager.Location;

import fabric.lang.Codebase;

/**
 *
 */
public interface CodebaseLocation extends Location {
  /**
   * Returns the Codebase of this location
   */
  Codebase codebase();

  /** Returns a uri associated with this location */
  URI namespace();
}
