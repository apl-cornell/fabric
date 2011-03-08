package fabric.frontend;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

//TODO: this class should actually extend UTF8FileSource
// so that the provider label is support
import jif.parse.UTF8FileSource;

import fabil.Codebases;
import fabric.lang.Codebase;
import fabric.lang.FClass;

import polyglot.frontend.FileSource;
import polyglot.util.StringUtil;

public class RemoteSource extends FileSource implements Codebases {
  protected final FClass fcls;
  protected Codebase codebase;
  protected Reader reader = null;
  
  protected static String toFabURL(FClass fcls) {
    return "fab://" + fcls.$getStore().name() + "/" + fcls.$getOnum();
  }
  
  public RemoteSource(FClass fcls, boolean userSpecified) throws IOException {
    this(fcls.getCodebase(), StringUtil.getShortNameComponent(fcls.getName()) + ".fab",
        toFabURL(fcls), fcls, new Date(System.currentTimeMillis()), userSpecified);
    fcls.get$label();
  }
  
  public RemoteSource(FClass fcls, String name, boolean userSpecified) throws IOException {
    this(fcls.getCodebase(), name, toFabURL(fcls), fcls, new Date(System.currentTimeMillis()), userSpecified);
  }

  public RemoteSource(Codebase cb, String name, String path, FClass fcls, Date lastModified, boolean userSpecified) throws IOException {
    super(path, name, lastModified, userSpecified);
    this.codebase = cb;
    this.fcls = fcls;
    this.name = name;
    this.path = path;
    this.lastModified = lastModified;
  }

  @Override
  public Reader open() {
    if(reader == null) {
      String src = fcls.getSource();
      ByteArrayInputStream bais = new ByteArrayInputStream(src.getBytes());
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
      return new polyglot.lex.EscapedUnicodeReader(
          new InputStreamReader(str, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      System.err.println("Bad Java implementation: UTF-8 encoding must be supported");
      return null;
    }
  }
    
  public Codebase codebase() {
    return codebase;
  }

  public FClass fclass() {
    return fcls;
  }

}
