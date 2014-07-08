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
import fabric.common.exceptions.ProtocolError;
import fabric.lang.security.Principal;
import fabric.messages.AllocateMessage.Response;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.remote.RemoteCallException;
import fabric.worker.transaction.TakeOwnershipFailedException;

/**
 * This abstract class acts as a visitor for MessagesToWorker. It also documents
 * the list of all messages that a worker needs to be able to handle. The
 * methods all have the form: <br>
 * 
 * <pre>
 * public Response handle(Principal, Message)
 *   throws FabricException
 * </pre>
 * 
 * and there is one such method for each message type that the worker handles.
 */
public abstract class MessageToWorkerHandler extends AbstractMessageServer {

  public MessageToWorkerHandler(String name) {
    super(name, Logging.WORKER_LOGGER);
  }

  @Override
  public abstract AbortTransactionMessage.Response handle(Principal p,
      AbortTransactionMessage msg) throws AccessException;

  @Override
  public abstract CommitTransactionMessage.Response handle(Principal p,
      CommitTransactionMessage msg) throws TransactionCommitFailedException;

  @Override
  public abstract ObjectUpdateMessage.Response handle(Principal p,
      ObjectUpdateMessage msg);

  @Override
  public abstract PrepareTransactionMessage.Response handle(Principal p,
      PrepareTransactionMessage msg) throws TransactionPrepareFailedException;

  @Override
  public abstract DirtyReadMessage.Response handle(Principal p,
      DirtyReadMessage msg) throws AccessException;

  @Override
  public abstract RemoteCallMessage.Response handle(Principal p,
      RemoteCallMessage msg) throws RemoteCallException;

  @Override
  public abstract TakeOwnershipMessage.Response handle(Principal p,
      TakeOwnershipMessage msg) throws TakeOwnershipFailedException;

  @Override
  public abstract InterWorkerStalenessMessage.Response handle(Principal p,
      InterWorkerStalenessMessage msg);

  @Override
  public final Response handle(Principal p, AllocateMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.DissemReadMessage.Response handle(Principal p,
      DissemReadMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.GetCertChainMessage.Response handle(Principal p,
      GetCertChainMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.ReadMessage.Response handle(Principal p,
      ReadMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.MakePrincipalMessage.Response handle(Principal p,
      MakePrincipalMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.StalenessCheckMessage.Response handle(Principal p,
      StalenessCheckMessage msg) throws ProtocolError {
    throw error(msg);
  }

  private final ProtocolError error(Message<?, ?> msg) {
    return new ProtocolError("Invalid message to worker: " + msg);
  }
}
