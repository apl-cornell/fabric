package fabric.common;

import java.net.URI;

import javax.tools.JavaFileManager.Location;

import fabric.lang.Codebase;

/**
 * Concrete implementation of FabricLocation
 */
public class FabricLocation_c implements FabricLocation {

  private final String name;
  private final boolean isOutputLocation;
  private final URI uri;
  private final boolean isFabricReference;
  private final boolean isFileReference;
  private final boolean isOpaque;
  private transient Codebase codebase;

  public FabricLocation_c(String name, boolean isOutputLocation, URI uri) {
    this.name = name;
    this.isOutputLocation = isOutputLocation;
    this.uri = uri;
    isFabricReference = "fab".equals(uri.getScheme());
    isFileReference = "file".equals(uri.getScheme());
    isOpaque = uri.isOpaque();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isOutputLocation() {
    return isOutputLocation;
  }

  @Override
  public boolean isFabricReference() {
    return isFabricReference;
  }

  @Override
  public boolean isFileReference() {
    return isFileReference;
  }

  @Override
  public Codebase getCodebase() {
    if (isFabricReference && codebase == null)
      codebase = NSUtil.fetch_codebase(uri);
    return codebase;
    // return NSUtil.fetch_codebase(uri);
  }

  @Override
  public URI getUri() {
    return uri;
  }

  @Override
  public boolean isOpaque() {
    return isOpaque;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Location) {
      Location f = (Location) o;
      return f.getName().equals(name);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return uri.hashCode();
  }

  @Override
  public String toString() {
    return uri.toString();
  }

}
