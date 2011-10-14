package codebases.frontend;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import polyglot.frontend.FileSource;
import polyglot.frontend.SourceLoader;
import fabric.common.NSUtil;

/**
 * This class is used for loading explicit source files from the command line.
 * It extends SourceLoader only to be backwards compatible with polyglot. 
 * 
 * NB:One difference from polyglot is this: the directory of files specified on the
 * command line is not automatically added to the source path.
 * 
 * @author owen
 */
public class URISourceDispatcher extends SourceLoader implements URISourceLoader {
  protected final ExtensionInfo extInfo;
  private static URI file = URI.create("file:///");

  public URISourceDispatcher(ExtensionInfo extInfo) {
    super(extInfo, Collections.EMPTY_LIST);
    this.extInfo = extInfo;
  }

  @Override
  public boolean packageExists(String name) {
    throw new UnsupportedOperationException("Cannot use " + getClass()
        + " to check raw package names");
  }

  @Override
  public FileSource classSource(String className) {
    throw new UnsupportedOperationException("Cannot use " + getClass()
        + " to load source files using raw class names");
  }

  @Override
  public FileSource fileSource(String fileName) throws IOException {
    if(!fileName.startsWith("/")) {
      File f = new File(fileName);
      fileName = f.getAbsolutePath();
    }
    return fileSource(file.resolve(URI.create(fileName)), false);
  }

  @Override
  public FileSource fileSource(String fileName, boolean userSpecified)
      throws IOException {
    if(!fileName.startsWith("/")) {
      File f = new File(fileName);
      fileName = f.getAbsolutePath();
    }
    return fileSource(file.resolve(URI.create(fileName)), userSpecified);
  }

  @Override
  public FileSource fileSource(URI uri) throws IOException {
    return fileSource(uri,false);
  }
  
  @Override
  public FileSource fileSource(URI uri, boolean userSpecified)
      throws IOException { 
    URI cbURI = NSUtil.dirname(uri);
    URISourceLoader loader = extInfo.sourceLoader(cbURI);
    return loader.fileSource(uri, userSpecified);
  }
}
