package codebases.frontend;

import static fabric.common.FabricLocationFactory.getLocation;

import java.io.IOException;
import java.io.Reader;

import javax.tools.FileObject;

import jif.parse.UTF8FileSource;
import polyglot.frontend.Source;
import polyglot.util.InternalCompilerError;
import fabric.common.FabricLocation;
import fabric.common.NSUtil;
import fabric.lang.FClass;
import fabric.lang.security.Label;
import fabric.filemanager.FabricSourceObject;

public class RemoteSource extends UTF8FileSource implements CodebaseSource {
  protected final FabricLocation namespace;
  protected final FClass fcls;
  protected boolean publish;

  protected Reader reader = null;

  public RemoteSource(FileObject fo, FClass fcls, boolean userSpecified)
      throws IOException {
    this(fo, fcls, userSpecified, false);
  }

  public RemoteSource(FileObject fo, FClass fcls, boolean userSpecified,
      boolean publish) throws IOException {
    super(fo, userSpecified);
    this.fcls = fcls;
    this.namespace = getLocation(false, NSUtil.namespace(fcls.getCodebase()));
    this.publish = publish;
  }

  @Override
  public boolean shouldPublish() {
    return publish;
  }

  @Override
  public void close() throws IOException {
    if (reader != null) {
      reader.close();
      reader = null;
    }
  }

  /**
   * Sources are equal to each other if they refer to the same resource.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof CodebaseSource) {
      CodebaseSource s = (CodebaseSource) o;
      return name().equals(s.name())
          && canonicalNamespace().equals(s.canonicalNamespace());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return name().hashCode() ^ canonicalNamespace().hashCode();
  }

  @Override
  public FabricLocation namespace() {
    return namespace;
  }

  @Override
  public FabricLocation canonicalNamespace() {
    return getLocation(false, NSUtil.namespace(fcls.getCodebase()));
  }

  @Override
  public Source derivedSource(final String name) {
    try {
      return new RemoteSource(new FabricSourceObject(fcls,
          NSUtil.absoluteName(fcls), name), fcls, user_specified, false);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public Source publishedSource(final FabricLocation ns, final String name) {
    try {
      return new RemoteSource(new FabricSourceObject(fcls,
          NSUtil.absoluteName(fcls), name), fcls, user_specified, false) {
        @Override
        public String path() {
          return ns.getUri().resolve(name).toString();
        }
      };
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public Label label() {
    return fcls.get$$updateLabel();
  }

  @Override
  public void setPublish(boolean pub) {
    // We're not supporting this yet.
    throw new InternalCompilerError("Tried to republish remote source!");
  }

  @Override
  public Reader open() throws IOException {
    return openReader(false);
  }

  @Override
  public String path() {
    return toUri().toString() + ".fab";
  }

  @Override
  public String toString() {
    return path();
  }
}
