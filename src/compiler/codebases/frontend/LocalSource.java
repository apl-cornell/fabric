package codebases.frontend;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Date;

import jif.parse.UTF8FileSource;
import polyglot.frontend.Source;
import polyglot.util.InternalCompilerError;

public class LocalSource extends UTF8FileSource implements CodebaseSource {
  protected URI namespace;
  protected boolean publish;
  
  public LocalSource(File f, boolean userSpecified, URI namespace)
      throws IOException {
    this(f, userSpecified, namespace, true);
  }
  
  public LocalSource(File f, boolean userSpecified, URI namespace, boolean publish)
      throws IOException {
    super(f, userSpecified);
    // assert f.getPath().startsWith(namespace.getPath());
    this.namespace = namespace;
  }

  public LocalSource(String path, String name, Date lastModified,
      boolean userSpecified, URI namespace, boolean publish) throws IOException {
    super(path, name, lastModified, userSpecified);
    this.namespace = namespace;
  }

  @Override
  public URI namespace() {
    return namespace;
  }

  @Override
  public URI canonicalNamespace() {
    return namespace;
  }

  @Override
  public boolean shouldPublish() {
    return publish;
  }

  @Override
  public Source derivedSource(String name) {
    try {
      // Create new path
      String path = file.getParent() + File.separator + name;
      return new LocalSource(path, name, new Date(System.currentTimeMillis()),
          false, namespace, false);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public Source derivedSource(URI namespace) {
    try {
      return new LocalSource(path, name, new Date(System.currentTimeMillis()),
          false, namespace, false);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }
}
