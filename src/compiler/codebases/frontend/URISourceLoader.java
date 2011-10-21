package codebases.frontend;

import java.io.IOException;
import java.net.URI;

import polyglot.frontend.FileSource;

public interface URISourceLoader {

  boolean packageExists(String name);

  FileSource classSource(String className);

  FileSource fileSource(URI uri) throws IOException;

  FileSource fileSource(URI uri, boolean userSpecified) throws IOException;
}
