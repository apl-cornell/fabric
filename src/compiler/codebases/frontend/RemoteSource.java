package codebases.frontend;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Date;

import jif.parse.UTF8FileSource;
import polyglot.frontend.Source;
import polyglot.util.InternalCompilerError;
import polyglot.util.StringUtil;
import fabric.common.NSUtil;
import fabric.lang.FClass;
import fabric.lang.security.Label;

// XXX: this class should actually extend jif.parse.UTF8FileSource
public class RemoteSource extends UTF8FileSource implements CodebaseSource {
  protected final URI namespace;
  protected final FClass fcls;
  protected boolean publish;

  protected Reader reader = null;

  public RemoteSource(URI namespace, FClass fcls, boolean userSpecified)
      throws IOException {
    this(namespace, fcls, fcls.getName() + ".fab", userSpecified);
  }

  public RemoteSource(URI namespace, FClass fcls, String name,
      boolean userSpecified) throws IOException {
    this(namespace, name, namespace.resolve(name).toString(), fcls, new Date(
        System.currentTimeMillis()), userSpecified, false);
  }

  public RemoteSource(URI namespace, FClass fcls, String name,
      boolean userSpecified, boolean publish) throws IOException {
    this(namespace, name, namespace.resolve(name).toString(), fcls, new Date(
        System.currentTimeMillis()), userSpecified, publish);
  }

  public RemoteSource(URI namespace, String name, String path, FClass fcls,
      Date lastModified, boolean userSpecified, boolean publish)
      throws IOException {
    super(path, name, lastModified, userSpecified);
    this.namespace = namespace;
    this.fcls = fcls;
    this.name = name;
    this.path = path;
    this.lastModified = lastModified;
    this.publish = publish;
  }

  @Override
  public boolean shouldPublish() {
    return publish;
  }

  @Override
  public Reader open() {
    if (reader == null) {
      String src = fcls.getSource();
      ByteArrayInputStream bais;
      try {
        bais = new ByteArrayInputStream(src.getBytes("UTF-8"));
      } catch (UnsupportedEncodingException e) {
        System.err
            .println("Bad Java implementation: UTF-8 encoding must be supported");
        return null;
      }
      reader = createReader(bais);
    }
    return reader;
  }

  @Override
  public void close() throws IOException {
    if (reader != null) {
      reader.close();
      reader = null;
    }
  }

  @Override
  protected Reader createReader(InputStream str) {
    try {
      return new polyglot.lex.EscapedUnicodeReader(new InputStreamReader(str,
          "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      System.err
          .println("Bad Java implementation: UTF-8 encoding must be supported");
      return null;
    }
  }

  
  /** 
   * Sources are equal to each other if they refer to the same resource.
   */
  @Override
  public boolean equals(Object o) {
    if(o instanceof CodebaseSource) {
      CodebaseSource s = (CodebaseSource)o;
      return name().equals(s.name()) && canonicalNamespace().equals(s.canonicalNamespace());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return name().hashCode() ^ canonicalNamespace().hashCode();
  }

  @Override
  public URI namespace() {
    return namespace;
  }

  @Override
  public URI canonicalNamespace() {
    return NSUtil.namespace(fcls.getCodebase());
  }

  @Override
  public Source derivedSource(String name) {
    try {
      return new RemoteSource(namespace, fcls, name, userSpecified, false);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }
//
//  @Override
//  public Source derivedSource(URI namespace) {
//    try {
//      return new RemoteSource(namespace, fcls, name, userSpecified, false);
//    } catch (IOException e) {
//      throw new InternalCompilerError(e);
//    }
//  }

  @Override
  public Source publishedSource(URI namespace, String name) {
    try {
      return new RemoteSource(namespace, fcls, name, userSpecified, false);
    } catch (IOException e) {
      throw new InternalCompilerError(e);
    }
  }

  @Override
  public Label label() {
    return fcls.get$label();
  }

  @Override
  public void setPublish(boolean pub) {
    // We're not supporting this yet.
    throw new InternalCompilerError("Tried to republish remote source!");
  }

}
