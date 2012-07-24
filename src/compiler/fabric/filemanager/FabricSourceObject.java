package fabric.filemanager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import polyglot.filemanager.SourceObject;
import fabric.lang.FClass;

/**
 * FabricSourceObject represents an FClass associated with a particular
 * codebase. This object is being used in FabricFileManager.
 */
public class FabricSourceObject extends SourceObject {
  private final fabric.lang.Object data;
  private final String name;

  public FabricSourceObject(fabric.lang.Object o, URI uri, String name) {
    super(uri, Kind.OTHER);
    data = o;
    this.name = name;
  }

  public fabric.lang.Object getData() {
    return data;
  }

  @Override
  public InputStream openInputStream() throws IOException {
    if (!(data instanceof FClass))
      throw new IOException(
          "Expected an FClass inside a FabricObject while calling openInputStream");
    String src = ((FClass) data).getSource();
    ByteArrayInputStream bais;
    try {
      bais = new ByteArrayInputStream(src.getBytes("UTF-8"));
    } catch (IOException e) {
      throw new IOException(
          "Bad Java implementation: UTF-8 encoding must be supported");
    }
    return bais;
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  @Override
  public String toString() {
    return toUri().resolve(name).toString();
  }
}
