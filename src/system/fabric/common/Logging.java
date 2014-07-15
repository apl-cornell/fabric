package fabric.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This is the clearing house of all loggers available for use in the system.
 */
public final class Logging {
  // //////////////////////////////////////////////////////////////////////////
  // static configuration
  // //////////////////////////////////////////////////////////////////////////

  static {
    System.setProperty("java.util.logging.config.class",
        LogConfigLoader.class.getName());
  }

  // //////////////////////////////////////////////////////////////////////////
  // STORE LOGGERS
  // //////////////////////////////////////////////////////////////////////////

  /**
   * For requests received by the store.
   */
  public static final Logger STORE_REQUEST_LOGGER = Logger
      .getLogger("fabric.store.requests");

  /**
   * For transaction events on the store.
   */
  public static final Logger STORE_TRANSACTION_LOGGER = Logger
      .getLogger("fabric.store.transactions");

  /**
   * For back-end DB events on the store.
   */
  public static final Logger STORE_DB_LOGGER = Logger
      .getLogger("fabric.store.db");

  /**
   * For other store-related events that don't fit into any other category. Use
   * sparingly.
   */
  public static final Logger STORE_LOGGER = Logger.getLogger("fabric.store");

  // //////////////////////////////////////////////////////////////////////////
  // WORKER LOGGERS
  // //////////////////////////////////////////////////////////////////////////

  /**
   * For transaction events on the worker.
   */
  public static final Logger WORKER_TRANSACTION_LOGGER = Logger
      .getLogger("fabric.worker.transactions");

  /**
   * For local-store events on the worker.
   */
  public static final Logger WORKER_LOCAL_STORE_LOGGER = Logger
      .getLogger("fabric.worker.localstore");

  /**
   * For deadlock-detection events on the worker.
   */
  public static final Logger WORKER_DEADLOCK_LOGGER = Logger
      .getLogger("fabric.worker.deadlocks");

  /**
   * For other worker-related events that don't fit into any other category. Use
   * sparingly.
   */
  public static final Logger WORKER_LOGGER = Logger.getLogger("fabric.worker");

  // //////////////////////////////////////////////////////////////////////////
  // NETWORKING LOGGERS
  // //////////////////////////////////////////////////////////////////////////

  /**
   * For network connection events.
   */
  public static final Logger NETWORK_CONNECTION_LOGGER = Logger
      .getLogger("fabric.net.connections");

  /**
   * For network channel events.
   */
  public static final Logger NETWORK_CHANNEL_LOGGER = Logger
      .getLogger("fabric.net.channel");

  /**
   * For network messages received from remote nodes.
   */
  public static final Logger NETWORK_MESSAGE_RECEIVE_LOGGER = Logger
      .getLogger("fabric.net.messages.received");

  /**
   * For network messages sent to remote nodes.
   */
  public static final Logger NETWORK_MESSAGE_SEND_LOGGER = Logger
      .getLogger("fabric.net.messages.sent");

  // //////////////////////////////////////////////////////////////////////////
  // MISCELLANEOUS LOGGERS
  // //////////////////////////////////////////////////////////////////////////

  /**
   * For configuration-related events.
   */
  public static final Logger CONFIG_LOGGER = Logger.getLogger("fabric.config");

  /**
   * For mey management related events
   */
  public static final Logger KEY_LOGGER = Logger.getLogger("fabric.keys");

  /**
   * For events related to the naming service.
   */
  public static final Logger NAMING_LOGGER = Logger.getLogger("fabric.naming");

  /**
   * For class-hashing events.
   */
  public static final Logger CLASS_HASHING_LOGGER = Logger
      .getLogger("fabric.class_hashing");

  /**
   * For detailed timing analysis.
   */
  public static final Logger TIMING_LOGGER = Logger.getLogger("fabric.timing");

  /**
   * For ignored InterruptedExceptions.
   */
  public static final Logger INTERRUPTED_EXCEPTION_LOGGER = Logger
      .getLogger("fabric.interruptedExceptions");

  /**
   * For other events that don't fit into any other category. Use sparingly.
   */
  public static final Logger MISC_LOGGER = Logger.getLogger("fabric");

  /**
   * For HOTOS Logging.
   */
  public static final Logger HOTOS_LOGGER = Logger.getLogger("HOTOS");

  // //////////////////////////////////////////////////////////////////////////
  // HELPER METHODS
  // //////////////////////////////////////////////////////////////////////////

  /**
   * Logs a message, with one object parameter. This is just here for
   * completeness.
   *
   * @param logger
   *          The logger to log to
   * @param level
   *          One of the message level identifiers, e.g. SEVERE
   * @param msg
   *          The string message (or a key in the message catalog)
   * @param param1
   *          first parameter to the message
   */
  public static void log(Logger logger, Level level, String msg, Object param1) {
    if (!logger.isLoggable(level)) return;
    logger.log(level, msg, param1);
  }

  /**
   * Logs a message, with two object parameters.
   *
   * @param logger
   *          The logger to log to
   * @param level
   *          One of the message level identifiers, e.g. SEVERE
   * @param msg
   *          The string message (or a key in the message catalog)
   * @param param1
   *          first parameter to the message
   * @param param2
   *          second parameter to the message
   */
  public static void log(Logger logger, Level level, String msg, Object param1,
      Object param2) {
    if (!logger.isLoggable(level)) return;
    logger.log(level, msg, new Object[] { param1, param2 });
  }

  /**
   * Logs a message, with three object parameters.
   *
   * @param logger
   *          The logger to log to
   * @param level
   *          One of the message level identifiers, e.g. SEVERE
   * @param msg
   *          The string message (or a key in the message catalog)
   * @param param1
   *          first parameter to the message
   * @param param2
   *          second parameter to the message
   * @param param3
   *          third parameter to the message
   */
  public static void log(Logger logger, Level level, String msg, Object param1,
      Object param2, Object param3) {
    if (!logger.isLoggable(level)) return;
    logger.log(level, msg, new Object[] { param1, param2, param3 });
  }

  /**
   * Logs a message, with four object parameters.
   *
   * @param logger
   *          The logger to log to
   * @param level
   *          One of the message level identifiers, e.g. SEVERE
   * @param msg
   *          The string message (or a key in the message catalog)
   * @param param1
   *          first parameter to the message
   * @param param2
   *          second parameter to the message
   * @param param3
   *          third parameter to the message
   * @param param4
   *          fourth parameter to the message
   */
  public static void log(Logger logger, Level level, String msg, Object param1,
      Object param2, Object param3, Object param4) {
    if (!logger.isLoggable(level)) return;
    logger.log(level, msg, new Object[] { param1, param2, param3, param4 });
  }

  /**
   * Logs a message, with four object parameters.
   *
   * @param logger
   *          The logger to log to
   * @param level
   *          One of the message level identifiers, e.g. SEVERE
   * @param msg
   *          The string message (or a key in the message catalog)
   * @param param1
   *          first parameter to the message
   * @param param2
   *          second parameter to the message
   * @param param3
   *          third parameter to the message
   * @param param4
   *          fourth parameter to the message
   */
  public static void log(Logger logger, Level level, String msg, Object param1,
      Object param2, Object param3, Object param4, Object param5) {
    if (!logger.isLoggable(level)) return;
    logger.log(level, msg, new Object[] { param1, param2, param3, param4,
        param5 });
  }

  /**
   * Records that an InterruptedException was ignored.
   */
  public static void logIgnoredInterruptedException(InterruptedException e) {
    INTERRUPTED_EXCEPTION_LOGGER.log(Level.FINEST,
        "Ignored interrupted exception", e);
  }

  /**
   * A class for loading the configuration for java.util.Logger.
   */
  public static class LogConfigLoader {
    public LogConfigLoader() {
      String configFile = Resources.relpathRewrite("etc", "logging.properties");

      // Read the configuration.
      try {
        final Properties p;
        try (InputStream in = new FileInputStream(configFile)) {
          p = new Properties();
          p.load(in);
        }

        // Make the log filename absolute if it isn't already.
        final String key = "java.util.logging.FileHandler.pattern";
        String logFile = p.getProperty(key);
        if (logFile != null && !new File(logFile).isAbsolute()) {
          logFile = Resources.relpathRewrite(logFile);
        }
        p.setProperty(key, logFile);

        // Ensure that the directory containing the logs exists to avoid
        // crashing
        new File(logFile).getParentFile().mkdirs();

        // Load the properties into the LogManager. ugh.
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        for (Map.Entry<Object, Object> entry : p.entrySet()) {
          pout.println(entry.getKey() + " = " + entry.getValue());
        }
        pout.flush();
        out.flush();

        LogManager.getLogManager().readConfiguration(
            new ByteArrayInputStream(out.toByteArray()));
      } catch (IOException e) {
      }

    }
  }
}
