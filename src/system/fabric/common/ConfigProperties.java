package fabric.common;

import static fabric.common.Logging.CONFIG_LOGGER;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;

public class ConfigProperties {

  public final static Properties defaults;

  /**
   * The name of this node.
   */
  public final String name;

  public final int workerPort;
  public final String dissemClass;

  /**
   * Port on which to listen for shell commands.
   */
  public final int workerAdminPort;

  public final boolean useSSL;
  public final String hostname;

  public final String backendClass;
  public final int storePort;

  public final String homeStore;

  public final Properties disseminationProperties;

  private final char[] password;
  private final String keyStoreName;
  private final String certStoreName;
  private KeyMaterial keyMaterial;

  static {
    //
    // load the default properties files
    //

    defaults = new Properties();
    try (InputStream in = Resources.readFile("etc", "config.properties")) {
      defaults.load(in);
    } catch (IOException e) {
      // continue with system properties
    }

    for (Entry<Object, Object> e : defaults.entrySet())
      CONFIG_LOGGER.log(Level.FINE, "default property: {0}", e);
  }

  public ConfigProperties(String name) {
    this(name, readProperties(name));
  }

  public static ConfigProperties getDefaults() {
    return new ConfigProperties("default", new Properties(defaults));
  }

  private ConfigProperties(String name, Properties p) {
    CONFIG_LOGGER.log(Level.FINE, "properties for {0}", name);
    for (Entry<?, ?> e : defaults.entrySet())
      CONFIG_LOGGER.log(Level.FINE, " ... {0}", e);

    this.name = name;

    //
    // parse properties with default values
    //

    /************************** Node Properties *****************************/
    this.useSSL =
        Boolean.parseBoolean(removeProperty(p, "fabric.node.useSSL", "true"));
    this.hostname = removeProperty(p, "fabric.node.hostname", name);

    this.password =
        removeProperty(p, "fabric.node.password", "password").toCharArray();
    this.keyStoreName =
        Resources.relpathRewrite("etc", "keys",
            removeProperty(p, "fabric.node.keystore", name + ".keystore"));
    this.certStoreName =
        Resources.relpathRewrite("var", "certs",
            removeProperty(p, "fabric.worker.certs", name + ".keystore"));
    this.dissemClass =
        removeProperty(p, "fabric.node.fetchmanager.class",
            "fabric.dissemination.pastry.PastryFetchManager");

    /************************** Worker Properties *****************************/
    this.workerPort =
        Integer.parseInt(removeProperty(p, "fabric.worker.port", "3372"));
    this.homeStore = removeProperty(p, "fabric.worker.homeStore", null);
    this.workerAdminPort =
        Integer.parseInt(removeProperty(p, "fabric.worker.adminPort", "3572"));

    /************************** Store Properties *****************************/
    this.storePort =
        Integer.parseInt(removeProperty(p, "fabric.store.port", "3472"));
    this.backendClass =
        removeProperty(p, "fabric.store.db.class", "fabric.store.db.BdbDB");

    //
    // Collect dissemination properties while printing other unused properties.
    //

    this.disseminationProperties = new Properties(defaults);
    for (Object prop : p.keySet()) {
      String key = (String) prop;
      if (key.startsWith("fabric.dissemination."))
        disseminationProperties.setProperty(key, p.getProperty(key));
      else CONFIG_LOGGER.log(Level.WARNING, "Unused property: {0}", key);
    }
  }

  public synchronized KeyMaterial getKeyMaterial() {
    if (this.keyMaterial == null)
      this.keyMaterial =
      new KeyMaterial(this.name, this.certStoreName, this.keyStoreName,
          this.password);

    return this.keyMaterial;
  }

  /**
   * Like p.getProperty(name, defaultValue), but removes the property.
   */
  private static String removeProperty(Properties p, String name,
      String defaultValue) {
    String result = p.getProperty(name, defaultValue);
    p.remove(name);
    return result;
  }

  private static Properties readProperties(String name) {
    Properties p = new Properties(defaults);
    try (InputStream file =
        Resources.readFile("etc", "config", name + ".properties")) {
      p.load(file);
      // load() does not trim whitespace from property values.
      for (String key : p.stringPropertyNames()) {
        String rawvalue = p.getProperty(key);
        p.put(key, rawvalue.trim());
      }
    } catch (IOException e) {
      // continue with defaults
    }

    return p;
  }
}
