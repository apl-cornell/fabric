package codebases.frontend;

import java.io.IOException;
import java.net.URI;

import fabric.lang.Object;

import polyglot.frontend.FileSource;
import polyglot.types.reflect.ClassPathLoader;
import polyglot.util.TypeEncoder;

public interface ExtensionInfo extends polyglot.frontend.ExtensionInfo {

  TypeEncoder typeEncoder();

  ClassPathLoader classpathLoader(URI namespace);

  URISourceLoader sourceLoader(URI namespace);

  URI localNamespace();

  FileSource createRemoteSource(URI ns, Object obj, boolean b) throws IOException;

  URI platformNamespace();

}
