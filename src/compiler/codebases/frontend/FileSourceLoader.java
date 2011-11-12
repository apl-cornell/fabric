package codebases.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import fabric.common.NSUtil;

import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.FileSource;
import polyglot.frontend.SourceLoader;

/**
 * Simple adapter class to abstract away some of the warts of the Polyglot
 * sourceloader
 * 
 * @author owen
 */
public class FileSourceLoader extends SourceLoader implements URISourceLoader {
  protected final URI uri;

  public FileSourceLoader(ExtensionInfo sourceExt, URI uri) {
    super(sourceExt, Collections.singleton(new File(uri.getPath())));
    this.uri = uri;
  }

  @Override
  public FileSource fileSource(URI file) throws IOException {
    if (!NSUtil.dirname(file).equals(this.uri))
      throw new FileNotFoundException("Cannot load " + file + " from " + uri);
    return super.fileSource(file.getPath(), false);
  }

  @Override
  public FileSource fileSource(URI file, boolean userSpecified)
      throws IOException {
    if (!NSUtil.dirname(file).equals(this.uri))
      throw new FileNotFoundException("Cannot load " + file + " from " + uri);
    return super.fileSource(file.getPath(), userSpecified);
  }
}
