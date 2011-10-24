package codebases.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.Date;

import jif.parse.UTF8FileSource;
import polyglot.frontend.Source;
import polyglot.util.InternalCompilerError;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;

public class LocalSource extends UTF8FileSource implements CodebaseSource {
  protected URI namespace;
  protected boolean publish;

  public LocalSource(File f, boolean userSpecified, URI namespace)
      throws IOException {
    this(f, userSpecified, namespace, true);
  }

  public LocalSource(File f, boolean userSpecified, URI namespace,
      boolean publish) throws IOException {
    super(f, userSpecified);
    // assert f.getPath().startsWith(namespace.getPath());
    this.namespace = namespace;
  }

  protected LocalSource(String path, String name, Date lastModified,
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
  public void setPublish(boolean pub) {
    this.publish = pub;
  }
  
  protected File file() {
    return file;
  }

  @Override
  public Source derivedSource(String name) {
    String path = path();
    if (!name().equals(name))
      //don't trust that file != null
      path = new File(path).getParent() + File.separator + name;
    try {
      return new DerivedLocalSource(path, name, file(), new Date(
          System.currentTimeMillis()), false, namespace);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public Source publishedSource(URI namespace, String name) {
    // XXX: this is fudging it a little so we can have LocalSources equal
    // RemoteSources when we compile down to bytecode.
    // we define equality on name and NS only if path is null
    try {
      return new PublishedLocalSource(path, name, file, new Date(
          System.currentTimeMillis()), false, namespace);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public Label label() {
    return LabelUtil._Impl.noComponents();
  }

  /**
   * Sources are equal to each other if they refer to the same resource.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof LocalSource) {
      LocalSource s = (LocalSource) o;
      return path.equals(s.path);
    }
    return false;
  }
  @Override
  public int hashCode() {
    return path.hashCode();
  }
}
