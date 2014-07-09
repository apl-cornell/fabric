package codebases.frontend;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;

import javax.tools.FileObject;

public class DerivedLocalSource extends LocalSource {
  private final String name;
  private URI uri;

  protected DerivedLocalSource(String name, FileObject derivedFrom,
      URI namespace) throws IOException {
    super(derivedFrom, Kind.COMPILER_GENERATED, namespace, false);
    this.name = name;
  }

  /**
   * Open the source file. For compiler generated source, open the file this
   * source is derived from.
   */
  @Override
  public Reader open() throws IOException {
    if (reader == null) reader = fileObject().openReader(false);
    return reader;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public URI toUri() {
    if (uri == null) {
      File f = new File(super.toUri().getPath()).getParentFile();
      uri = new File(f, name).toURI();
    }
    return uri;
  }

}
