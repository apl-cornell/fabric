package codebases.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.Date;

public class DerivedLocalSource extends LocalSource {
  protected File derivedFrom;
  protected DerivedLocalSource(String path, String name, File derivedFrom,
      Date lastModified, boolean userSpecified, URI namespace)
      throws IOException {
    super(path, name, lastModified, userSpecified, namespace, false);
    this.derivedFrom = derivedFrom;
  }
  /**
   * Open the source file. For compiler generated source, open the file this
   * soruce is derived from.
   */
  @Override
  public Reader open() throws IOException {
    if (reader == null) {
      FileInputStream str = new FileInputStream(derivedFrom);
      reader = createReader(str);
    }

    return reader;
  }
  
  @Override
  protected File file() {
    return derivedFrom;
  }

}
