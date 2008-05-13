package fabric.dissemination.pastry.messages;

/**
 * Identifiers for each message sent over the network. Pastry uses shorts as
 * message type ids.
 */
public class MessageType {
  
  public static final short FETCH = 1;
  public static final short FETCH_REPLY = 2;
  
  public static final short REPLICATE = 11;
  public static final short REPLICATE_REPLY = 12;

}
