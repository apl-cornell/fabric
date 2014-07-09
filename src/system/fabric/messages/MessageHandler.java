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

import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricGeneralSecurityException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.remote.RemoteCallException;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.transaction.TakeOwnershipFailedException;

/**
 * This interface acts as a visitor for Messages. It also documents the list of
 * all messages that can be handled. The methods all have the form:<br>
 * 
 * <pre>
 * public Response handle(Principal, Message)
 *   throws FabricException
 * </pre>
 * 
 * and there is one such method for each message type that can be handled.
 */
public interface MessageHandler {
  public AbortTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, AbortTransactionMessage msg)
      throws AccessException;

  public AllocateMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      AllocateMessage msg) throws ProtocolError, AccessException;

  public CommitTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, CommitTransactionMessage msg)
      throws TransactionCommitFailedException;

  public DissemReadMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      DissemReadMessage msg) throws ProtocolError, AccessException;

  public GetCertChainMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, GetCertChainMessage msg)
      throws ProtocolError;

  public PrepareTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, PrepareTransactionMessage msg)
      throws TransactionPrepareFailedException;

  public ReadMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      ReadMessage msg) throws ProtocolError, AccessException;

  public MakePrincipalMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, MakePrincipalMessage msg)
      throws ProtocolError, FabricGeneralSecurityException;

  public StalenessCheckMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, StalenessCheckMessage msg)
      throws ProtocolError, AccessException;

  public ObjectUpdateMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, ObjectUpdateMessage msg)
      throws ProtocolError;

  public DirtyReadMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      DirtyReadMessage msg) throws ProtocolError, AccessException;

  public RemoteCallMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      RemoteCallMessage msg) throws ProtocolError, RemoteCallException;

  public TakeOwnershipMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, TakeOwnershipMessage msg)
      throws ProtocolError, TakeOwnershipFailedException;

  public InterWorkerStalenessMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, InterWorkerStalenessMessage msg)
      throws ProtocolError;
}
