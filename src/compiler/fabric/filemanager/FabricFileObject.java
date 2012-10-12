package fabric.filemanager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import polyglot.filemanager.ExtFileObject;

/**
 * FabricSourceObject represents an FClass associated with a particular
 * codebase. This object is being used in FabricFileManager.
 */
public class FabricFileObject extends ExtFileObject {
  private final fabric.lang.FClass data;
  private final String name;

  public FabricFileObject(fabric.lang.FClass o, URI uri, String name) {
    super(uri, Kind.OTHER);
    data = o;
    this.name = name;
  }

  public fabric.lang.FClass getData() {
    return data;
  }

  @Override
  public InputStream openInputStream() throws IOException {
    String src = data.getSource();
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
