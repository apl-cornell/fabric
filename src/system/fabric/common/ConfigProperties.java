package fabric.common;

import static fabric.common.Logging.CONFIG_LOGGER;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;

import fabric.common.util.RegexMapping;
import fabric.worker.metrics.PresetMetricStatistics;

public class ConfigProperties {

  public final static Properties defaults;

  private static final String INBOUND_DELAY_PROPERTY_PREFIX =
      "fabric.node.netdelay.in|";

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
  public final String surrogateManagerClass;
  public final int storePort;

  public final String homeStore;

  public final Properties disseminationProperties;

  private final char[] password;
  private final String keyStoreName;
  private final String certStoreName;
  private KeyMaterial keyMaterial;

  /**
   * Added network delay on inbound traffic.
   */
  public final Map<String, Short> inDelays;

  /**
   * Contract properties (allows for selecting which strategy to use for either
   * our approach or "baseline" approaches).
   */
  // Flag to indicate if the strategy should be a preset (instead of the
  // default)
  public final boolean usePreset;
  // How many updates between logging a sampled metric's stats.
  public final int metricStatsLogInterval;
  // Rates to assign if using presets
  public final Map<String, Double> rates;
  // Bounds to assign if using presets
  public final Map<String, Double> bounds;
  // Velocities to use if using presets
  public final Map<String, Double> velocities;
  // Noises to use if using presets
  public final Map<String, Double> noises;
  // Mapping regexes to preset values for statistics
  public final RegexMapping<PresetMetricStatistics> presets;
  // Statistics estimation parameters
  // exponential weight for updates
  public final double alpha;
  // Window for binning observations for estimating poisson events.
  public final long window;

  /**
   * Whether to exponentially back off when retrying transactions.
   */
  public final boolean txRetryBackoff;

  /**
   * Whether to use the subscription service.
   */
  public final boolean useSubscriptions;

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
    this.keyStoreName = Resources.relpathRewrite("etc", "keys",
        removeProperty(p, "fabric.node.keystore", name + ".keystore"));
    this.certStoreName = Resources.relpathRewrite("var", "certs",
        removeProperty(p, "fabric.worker.certs", name + ".keystore"));
    this.dissemClass = removeProperty(p, "fabric.node.fetchmanager.class",
        "fabric.dissemination.DummyFetchManager");

    this.txRetryBackoff = Boolean
        .parseBoolean(removeProperty(p, "fabric.node.txRetryBackoff", "true"));

    this.useSubscriptions = Boolean.parseBoolean(
        removeProperty(p, "fabric.node.useSubscriptions", "false"));

    // Collect network-delay properties.
    Map<String, Short> inDelays = new HashMap<>();
    for (Object prop : p.keySet()) {
      String key = (String) prop;
      if (key.startsWith(INBOUND_DELAY_PROPERTY_PREFIX)) {
        String nodeName = key.substring(INBOUND_DELAY_PROPERTY_PREFIX.length());
        inDelays.put(nodeName, Short.parseShort(p.getProperty(key)));
      }
    }
    this.inDelays = Collections.unmodifiableMap(inDelays);

    // Remove the network-delay properties that we just processed.
    for (String node : inDelays.keySet()) {
      p.remove(INBOUND_DELAY_PROPERTY_PREFIX + node);
    }

    /************************** Contract Properties ***************************/
    this.usePreset = Boolean
        .parseBoolean(removeProperty(p, "fabric.metrics.usePreset", "false"));

    this.metricStatsLogInterval = Integer
        .parseInt(removeProperty(p, "fabric.metrics.statsLogInterval", "10"));

    String[] rawRates =
        removeProperty(p, "fabric.metrics.rates", "").split(",");
    this.rates = new HashMap<>();
    if (this.usePreset) {
      for (int i = 0; i < rawRates.length; i++) {
        String[] kv = rawRates[i].split(":");
        if (kv.length != 2) continue;
        this.rates.put(kv[0], Double.parseDouble(kv[1]));
      }
    }

    String[] rawBounds =
        removeProperty(p, "fabric.metrics.bounds", "").split(",");
    this.bounds = new HashMap<>();
    if (this.usePreset) {
      for (int i = 0; i < rawBounds.length; i++) {
        String[] kv = rawBounds[i].split(":");
        if (kv.length != 2) continue;
        this.bounds.put(kv[0], Double.parseDouble(kv[1]));
      }
    }

    String[] rawVelocities =
        removeProperty(p, "fabric.metrics.velocities", "").split(",");
    this.velocities = new HashMap<>();
    if (this.usePreset) {
      for (int i = 0; i < rawVelocities.length; i++) {
        String[] kv = rawVelocities[i].split(":");
        if (kv.length != 2) continue;
        this.velocities.put(kv[0], Double.parseDouble(kv[1]));
      }
    }

    String[] rawNoises =
        removeProperty(p, "fabric.metrics.noises", "").split(",");
    this.noises = new HashMap<>();
    if (this.usePreset) {
      for (int i = 0; i < rawNoises.length; i++) {
        String[] kv = rawNoises[i].split(":");
        if (kv.length != 2) continue;
        this.noises.put(kv[0], Double.parseDouble(kv[1]));
      }
    }

    String[] rawPresets =
        removeProperty(p, "fabric.metrics.presets", "").split(";");
    this.presets = new RegexMapping<>();
    for (int i = 0; i < rawPresets.length; i++) {
      String[] kv = rawPresets[i].split(":");
      if (kv.length != 2) continue;
      String[] values = kv[1].split(",");
      // TODO: note the bad mapping?
      if (values.length != 4) continue;
      PresetMetricStatistics val = new PresetMetricStatistics(
          Double.parseDouble(values[0]), Double.parseDouble(values[1]),
          Double.parseDouble(values[2]), Integer.parseInt(values[3]));
      this.presets.put(kv[0], val);
    }

    this.alpha =
        Double.parseDouble(removeProperty(p, "fabric.metrics.alpha", "0.001"));

    this.window =
        Long.parseLong(removeProperty(p, "fabric.metrics.window", "1000"));

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
    this.surrogateManagerClass =
        removeProperty(p, "fabric.store.SurrogateManager.class",
            "fabric.store.DummySurrogateManager");

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
    if (this.keyMaterial == null) this.keyMaterial = new KeyMaterial(this.name,
        this.certStoreName, this.keyStoreName, this.password);

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
