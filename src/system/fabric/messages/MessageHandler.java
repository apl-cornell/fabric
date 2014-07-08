/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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
import fabric.lang.security.Principal;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.remote.RemoteCallException;
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
  public AbortTransactionMessage.Response handle(Principal p,
      AbortTransactionMessage msg) throws AccessException;

  public AllocateMessage.Response handle(Principal p, AllocateMessage msg)
      throws ProtocolError, AccessException;

  public CommitTransactionMessage.Response handle(Principal p,
      CommitTransactionMessage msg) throws TransactionCommitFailedException;

  public DissemReadMessage.Response handle(Principal p, DissemReadMessage msg)
      throws ProtocolError, AccessException;

  public GetCertChainMessage.Response handle(Principal p,
      GetCertChainMessage msg) throws ProtocolError;

  public PrepareTransactionMessage.Response handle(Principal p,
      PrepareTransactionMessage msg) throws TransactionPrepareFailedException;

  public ReadMessage.Response handle(Principal p, ReadMessage msg)
      throws ProtocolError, AccessException;

  public MakePrincipalMessage.Response handle(Principal p,
      MakePrincipalMessage msg) throws ProtocolError,
      FabricGeneralSecurityException;

  public StalenessCheckMessage.Response handle(Principal p,
      StalenessCheckMessage msg) throws ProtocolError, AccessException;

  public ObjectUpdateMessage.Response handle(Principal p,
      ObjectUpdateMessage msg) throws ProtocolError;

  public DirtyReadMessage.Response handle(Principal p, DirtyReadMessage msg)
      throws ProtocolError, AccessException;

  public RemoteCallMessage.Response handle(Principal p, RemoteCallMessage msg)
      throws ProtocolError, RemoteCallException;

  public TakeOwnershipMessage.Response handle(Principal p,
      TakeOwnershipMessage msg) throws ProtocolError,
      TakeOwnershipFailedException;

  public InterWorkerStalenessMessage.Response handle(Principal p,
      InterWorkerStalenessMessage msg) throws ProtocolError;
}
