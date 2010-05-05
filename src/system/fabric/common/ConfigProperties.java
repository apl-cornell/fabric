package fabric.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConfigProperties {

  public final static Properties defaults;

  public final String name;

  public final int    workerPort;
  public final String dissemClass;

  public final String keystore;
  public final char[] password;
  public final int    maxConnections;
  public final int    retries;
  public final int    timeout;
  public final boolean useSSL;
  public final String address;

  public final String backendClass;
  public final int storeAuthPort;
  public final int storeUnauthPort;

  public final String workerPrincipal;

  private static Logger logger = Logger.getLogger("fabric.config");

  static {
    //
    // load the default properties files
    //

    defaults       = new Properties();
    InputStream in = null;
    try {
      in = Resources.readFile("etc", "defaults.properties");
      defaults.load(in);
    } catch(IOException e) {
      // continue with system properties
    } finally {
      try { in.close(); } catch(Exception e) {}
    }
  }


  public ConfigProperties(String name) {
    this(name, readProperties(name));
  }
  
  public static ConfigProperties getDefaults() {
    return new ConfigProperties("default", defaults);
  }
  
  private ConfigProperties(String name, Properties p) {
    this.name = name;

    //
    // load the configuration file
    //


    //
    // parse properties with default values
    //

    /************************** Node   Properties *****************************/
    this.password        =                       removeProperty(p, "fabric.node.password",             "password").toCharArray();
    this.maxConnections  = Integer.parseInt(     removeProperty(p, "fabric.node.maxConnections",       "50"));
    this.timeout         = Integer.parseInt(     removeProperty(p, "fabric.node.timeout",              "2"));
    this.retries         = Integer.parseInt(     removeProperty(p, "fabric.node.retries",              "6"));
    this.useSSL          = Boolean.parseBoolean( removeProperty(p, "fabric.node.useSSL",               "true"));
    this.keystore        = Resources.relpathRewrite("etc", "keys",
                                                 removeProperty(p, "fabric.node.keystore",             name + ".keystore"));
    this.address         =                       removeProperty(p, "fabric.node.address",              "localhost");
    
    /************************** Worker Properties *****************************/
    this.workerPort      = Integer.parseInt(     removeProperty(p, "fabric.worker.port",               "3372"));
    this.dissemClass     =                       removeProperty(p, "fabric.worker.fetchmanager.class", "fabric.dissemination.pastry.PastryFetchManager");
    this.workerPrincipal =                       removeProperty(p, "fabric.worker.principal",          null);

    /************************** Store  Properties *****************************/
    this.storeAuthPort   = Integer.parseInt(     removeProperty(p, "fabric.store.authport",            "3472"));
    this.storeUnauthPort = Integer.parseInt(     removeProperty(p, "fabric.store.unauthport",          "3472"));
    this.backendClass    =                       removeProperty(p, "fabric.store.db.class",            null);

    //
    // print out unused properties
    //

    for (Object prop : p.keySet())
      logger.log(Level.WARNING, "Unused property: {0}", prop);
  }

  /** like p.getProperty(name, default), but removes the property. */
  private static String removeProperty(Properties p, String name, String defaultValue) {
    String result = p.getProperty(name, defaultValue);
    p.remove(name);
    return result;
  }

  private static Properties readProperties(String name) {
    Properties  p    = new Properties(defaults);
    InputStream file = null;
    try {
      file = Resources.readFile("etc", "config.d", name + ".properties"); 
      p.load(file);
    } catch (IOException e) {
      // continue with defaults
    } finally {
      try { file.close(); } catch (Exception e) {}
    }

    return p;
  }
}
