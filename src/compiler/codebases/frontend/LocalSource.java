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
  protected File derivedFrom;

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

  protected LocalSource(String path, String name, File derivedFrom,
      Date lastModified, boolean userSpecified, URI namespace, boolean publish)
      throws IOException {
    super(path, name, lastModified, userSpecified);
    this.namespace = namespace;
    this.derivedFrom = derivedFrom;
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

  @Override
  public Source derivedSource(String name) {
    return derivedSource(namespace(), name);
  }

  @Override
  public Source derivedSource(URI namespace) {
    return derivedSource(namespace, name());
  }
  
  @Override
  public Source derivedSource(URI namespace, String name) {
    try {
      String path = path();
      if(!name().equals(name))
        path = file.getParent() + File.separator + name;

      return new LocalSource(path, name, file, new Date(
          System.currentTimeMillis()), false, namespace, false);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public Label label() {
    return LabelUtil._Impl.noComponents();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof LocalSource) {
      LocalSource src = (LocalSource) o;
      return super.equals(src) && namespace.equals(src.namespace)
          && src.derivedFrom == src.derivedFrom;
    }
    return false;
  }

  /**
   * Open the source file. For compiler generated source, open the file this
   * soruce is derived from.
   */
  @Override
  public Reader open() throws IOException {
    if (reader == null) {
      File toOpen = file;
      if (toOpen == null) toOpen = derivedFrom;
      FileInputStream str = new FileInputStream(toOpen);
      reader = createReader(str);
    }

    return reader;
  }

  @Override
  public int hashCode() {
    return super.hashCode() ^ namespace.hashCode()
        ^ ((derivedFrom != null) ? derivedFrom.hashCode() : 0);
  }

}
