package fabric.common;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the clearing house of all loggers available for use in the system.
 */
public final class Logging {
  // //////////////////////////////////////////////////////////////////////////
  // STORE LOGGERS
  // //////////////////////////////////////////////////////////////////////////

  /**
   * For requests received by the store.
   */
  public static final Logger STORE_REQUEST_LOGGER =
      Logger.getLogger("fabric.store.requests");

  /**
   * For transaction events on the store.
   */
  public static final Logger STORE_TRANSACTION_LOGGER =
      Logger.getLogger("fabric.store.transactions");

  /**
   * For back-end DB events on the store.
   */
  public static final Logger STORE_DB_LOGGER =
      Logger.getLogger("fabric.store.db");

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
  public static final Logger WORKER_TRANSACTION_LOGGER =
      Logger.getLogger("fabric.worker.transactions");

  /**
   * For local-store events on the worker.
   */
  public static final Logger WORKER_LOCAL_STORE_LOGGER =
      Logger.getLogger("fabric.worker.localstore");

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
  public static final Logger NETWORK_CONNECTION_LOGGER =
      Logger.getLogger("fabric.net.connections");

  /**
   * For network channel events.
   */
  public static final Logger NETWORK_CHANNEL_LOGGER =
      Logger.getLogger("fabric.net.channel");

  /**
   * For network messages received from remote nodes.
   */
  public static final Logger NETWORK_MESSAGE_RECEIVE_LOGGER =
      Logger.getLogger("fabric.net.messages.received");

  /**
   * For network messages sent to remote nodes.
   */
  public static final Logger NETWORK_MESSAGE_SEND_LOGGER =
      Logger.getLogger("fabric.net.messages.sent");

  // //////////////////////////////////////////////////////////////////////////
  // MISCELLANEOUS LOGGERS
  // //////////////////////////////////////////////////////////////////////////

  /**
   * For configuration-related events.
   */
  public static final Logger CONFIG_LOGGER = Logger.getLogger("fabric.config");
  
  /**
   * For events related to the naming service.
   */
  public static final Logger NAMING_LOGGER = Logger.getLogger("fabric.naming");

  /**
   * For other events that don't fit into any other category. Use sparingly.
   */
  public static final Logger MISC_LOGGER = Logger.getLogger("fabric");

  // //////////////////////////////////////////////////////////////////////////
  // HELPER METHODS
  // //////////////////////////////////////////////////////////////////////////

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
}
