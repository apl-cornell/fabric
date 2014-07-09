/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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
import fabric.common.net.RemoteIdentity;
import fabric.messages.AllocateMessage.Response;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.remote.RemoteCallException;
import fabric.worker.remote.RemoteWorker;
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
  public abstract AbortTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, AbortTransactionMessage msg)
      throws AccessException;

  @Override
  public abstract CommitTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, CommitTransactionMessage msg)
      throws TransactionCommitFailedException;

  @Override
  public abstract ObjectUpdateMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, ObjectUpdateMessage msg);

  @Override
  public abstract PrepareTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, PrepareTransactionMessage msg)
      throws TransactionPrepareFailedException;

  @Override
  public abstract DirtyReadMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, DirtyReadMessage msg)
      throws AccessException;

  @Override
  public abstract RemoteCallMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, RemoteCallMessage msg)
      throws RemoteCallException;

  @Override
  public abstract TakeOwnershipMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, TakeOwnershipMessage msg)
      throws TakeOwnershipFailedException;

  @Override
  public abstract InterWorkerStalenessMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, InterWorkerStalenessMessage msg);

  @Override
  public final Response handle(RemoteIdentity<RemoteWorker> client,
      AllocateMessage msg) throws ProtocolError {
    throw error(msg);
  }

  @Override
  public final fabric.messages.DissemReadMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, DissemReadMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.GetCertChainMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, GetCertChainMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.ReadMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, ReadMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.MakePrincipalMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, MakePrincipalMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  @Override
  public fabric.messages.StalenessCheckMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, StalenessCheckMessage msg)
      throws ProtocolError {
    throw error(msg);
  }

  private final ProtocolError error(Message<?, ?> msg) {
    return new ProtocolError("Invalid message to worker: " + msg);
  }
}
