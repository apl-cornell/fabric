package codebases.frontend;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Date;

import polyglot.frontend.Source;

public class PublishedLocalSource extends DerivedLocalSource {

  protected PublishedLocalSource(String path, String name, File derivedFrom,
      Date lastModified, boolean userSpecified, URI namespace)
      throws IOException {
    super(path, name, derivedFrom, lastModified, userSpecified, namespace);
  }

  //Allow PublishedLocalSource to be equal to RemoteSource
  @Override
  public boolean equals(Object o) {
    if (o instanceof LocalSource) {
      return super.equals(o);
    }
    else if (o instanceof CodebaseSource) {
      CodebaseSource src = (CodebaseSource) o;
      return name().equals(src.name())
          && canonicalNamespace().equals(src.canonicalNamespace());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return name().hashCode() ^ namespace().hashCode();
  }

}
