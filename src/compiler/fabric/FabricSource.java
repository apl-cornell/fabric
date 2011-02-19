package fabric;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import jif.types.JifTypeSystem;
import jif.types.Provided;
import jif.types.label.ConfPolicy;
import jif.types.label.JoinIntegPolicy_c;
import jif.types.label.JoinPolicy_c;
import jif.types.label.MeetIntegPolicy_c;
import jif.types.label.MeetPolicy_c;
import jif.types.label.WriterPolicy;
import jif.types.principal.Principal;

import fabric.lang.FClass;
import fabric.lang.security.IntegPolicy;
import fabric.lang.security.Label;

import polyglot.frontend.Source;
import polyglot.util.InternalCompilerError;

public class FabricSource extends Source implements Provided {
  protected final FClass fcls;
  protected Reader reader = null;
  protected Principal provider;
  
  protected FabricSource(FClass fcls, boolean userSpecified, Principal provider) {
    super(fcls.getName());
    this.fcls = fcls;
    this.name = fcls.getName();
    this.path = "fab://" + fcls.$getStore().name() + "/" + fcls.$getOnum();
    this.provider = provider;
  }

  public FabricSource(FClass fcls, URI srcURI, boolean b, Principal provider) {
    super(fcls.getName());
    this.fcls = fcls;
    this.name = fcls.getName();
    this.path = srcURI.toString();
    this.provider = provider;
  }

  public Reader open() throws IOException {
    if(reader == null) {
      String src = fcls.getSourcecode();
      ByteArrayInputStream bais = new ByteArrayInputStream(src.getBytes());
      reader = createReader(bais);
    }
    return reader;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see polyglot.frontend.Source#close()
   */
  public void close() throws IOException {
    if (reader != null) {
      reader.close();
      reader = null;
    }
  }

  protected Reader createReader(InputStream str) {
    try {
      return new polyglot.lex.EscapedUnicodeReader(
          new InputStreamReader(str, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      System.err.println("Bad Java implementation: UTF-8 encoding must be supported");
      return null;
    }
  }

  public Principal provider() {
    return provider;
  }
}
