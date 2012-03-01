package meta;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class Util {

  public static byte[] readFully(String filename) throws IOException {
    File f   = new File(filename);
    int  len = (int) f.length();
    
    byte[] bytecode = new byte[len];
    InputStream  in = new FileInputStream(f);
    
    for (int i = 0; i < len; i++) {
      int r = in.read();
      if (r < 0)
        throw new IOException("bytecode shorter than advertised");
      bytecode[i] = (byte) r; 
    }
    
    if (in.read() > 0)
      throw new IOException("bytecode longer than advertised");
    
    in.close();
    return bytecode;
  }
  
  public static Properties readProperties(String filename) throws IOException {
    InputStream in = new FileInputStream(filename);
    Properties  p  = new Properties();
    p.load(in);
    in.close();
    return p;
  }
}
