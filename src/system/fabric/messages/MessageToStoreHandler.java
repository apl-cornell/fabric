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
package fabric.messages;

import fabric.common.Logging;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.security.Principal;
import fabric.messages.ObjectUpdateMessage.Response;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;

/**
 * This abstract class acts as a visitor for MessagesToStore. It also documents
 * the list of all messages that a store needs to be able to handle. The methods
 * all have the form:<br>
 * 
 * <pre>
 * public Response handle(Principal, Message)
 *   throws FabricException
 * </pre>
 * 
 * and there is one such method for each message type that the store handles.
 */
public abstract class MessageToStoreHandler extends AbstractMessageServer {

  public MessageToStoreHandler(String name) {
    super(name, Logging.STORE_LOGGER);
  }

  @Override
  public abstract AbortTransactionMessage.Response handle(Principal p,
      AbortTransactionMessage msg) throws AccessException;

  @Override
  public abstract AllocateMessage.Response handle(Principal p,
      AllocateMessage msg) throws AccessException;

  @Override
  public abstract CommitTransactionMessage.Response handle(Principal p,
      CommitTransactionMessage msg) throws TransactionCommitFailedException;

  @Override
  public abstract DissemReadMessage.Response handle(Principal p,
      DissemReadMessage msg) throws AccessException;

  @Override
  public abstract GetCertChainMessage.Response handle(Principal p,
      GetCertChainMessage msg);

  @Override
  public abstract PrepareTransactionMessage.Response handle(Principal p,
      PrepareTransactionMessage msg) throws TransactionPrepareFailedException;

  @Override
  public abstract ReadMessage.Response handle(Principal p, ReadMessage msg)
      throws AccessException;

  @Override
  public abstract MakePrincipalMessage.Response handle(Principal p,
      MakePrincipalMessage msg) throws FabricGeneralSecurityException;

  @Override
  public abstract StalenessCheckMessage.Response handle(Principal p,
      StalenessCheckMessage msg) throws AccessException;

  @Override
  public final Response handle(Principal p, ObjectUpdateMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.DirtyReadMessage.Response handle(Principal p,
      DirtyReadMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.RemoteCallMessage.Response handle(Principal p,
      RemoteCallMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.TakeOwnershipMessage.Response handle(
      Principal p, TakeOwnershipMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.InterWorkerStalenessMessage.Response handle(
      Principal p, InterWorkerStalenessMessage msg) throws ProtocolError {
    throw error(msg);
  }

  private final ProtocolError error(Message<?, ?> msg) {
    return new ProtocolError("Invalid message to store: " + msg);
  }
}
