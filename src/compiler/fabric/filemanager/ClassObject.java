package fabric.filemanager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * 
 */
public class ClassObject extends SimpleJavaFileObject {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
  private ByteArrayInputStream bais;
  
  public ClassObject(URI u) {
    super(u, Kind.CLASS);
  }
  
  @Override
  public OutputStream openOutputStream() {
    return baos;
  }
  
  @Override
  public InputStream openInputStream() {
    bais = new ByteArrayInputStream(baos.toByteArray());
    return bais;
  }
  
  public byte[] getBytes() {
    return baos.toByteArray();
  }
}
