package fabric.common.net.naming;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyNameService implements NameService {

  private static final Logger logger = Logger.getLogger("fabric.common.net");
  
  private Map<String, SocketAddress> entries;
  private SocketAddress              defaultAddr;
  
  /** Loads the entries in the name service from properties files in a directory.
   * Scans the provided directory looking for files named *.properties.  Creates
   * a name service entry for each file found, with the name given by the filename
   * (e.g foo for foo.properties), and address given by the hostProp and portProp
   * properties in the file.
   * 
   * @param hostProp  the property name for the host.  Host properties are
   *                  parsed by {@link InetAddress#getByName}
   * @param portProp  the property name for the port.
   */
  public PropertyNameService(File directory, String hostProp, String portProp, File defProperty) throws IOException {
    //
    // load default properties file
    //
    Properties      p  = new Properties();
    FileInputStream in = null;
    try {
      in = new FileInputStream(defProperty);
      p.load(in);
    } finally {
      if (in != null)
        in.close();
    }
    
    String defaultHost = p.getProperty(hostProp);
    String defaultPort = p.getProperty(portProp);
    
    this.defaultAddr   = new SocketAddress(InetAddress.getByName(defaultHost), Integer.parseInt(defaultPort));
    
    //
    // load other properties files from the directory
    //
    entries = new HashMap<String, SocketAddress> ();
    
    for (File f : directory.listFiles())
      if (f.getName().endsWith(".properties")) {
        //
        // read the file
        //
        try {
          in = new FileInputStream(f);
          p.clear();
          p.load(in);
        } catch(IOException e) {
          logger.log(Level.WARNING, "exception while loading name service entry from " + f.getName());
          logger.log(Level.INFO,    "  ... filename:  " + f.getPath());
          logger.log(Level.INFO,    "  ... exception: ", e);
          logger.log(Level.INFO,    "  ... ignoring entry");
          continue;
        } finally {
          try { in.close(); } catch (IOException e) {}
        }
        
        //
        // get the properties
        //
        String name = f.getName();
        name = name.substring(0, name.lastIndexOf('.'));
        
        String host =                  p.getProperty(hostProp, defaultHost);
        int    port = Integer.parseInt(p.getProperty(portProp, defaultPort));
        
        this.entries.put(name, new SocketAddress(InetAddress.getByName(host), port));
    }
    
    //
    // log entries
    //
    if (logger.isLoggable(Level.FINEST)) {
      // find length so output is pretty
      int size = 0;
      for (String name : entries.keySet())
        size = name.length() > size ? name.length() : size;
    
      // print nicely
      for (Map.Entry<String, SocketAddress> e : entries.entrySet())
        logger.finest(String.format("name service: %1$" + size + "s -> %2$s",
                                                   e.getKey(), e.getValue()));
    }
  }
  
  public SocketAddress localResolve(String name) throws IOException{
    SocketAddress result = entries.get(name);
    if (result == null)
      result = defaultAddr;
    
    return result;
  }

  public SocketAddress resolve(String name) throws IOException {
    SocketAddress result = entries.get(name);
    if (result == null)
      result = defaultAddr;
    
    return result;
  }

}
