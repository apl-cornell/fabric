/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
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
