/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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
package fabric.worker.remote.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.MessageHandlerThread;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.lang.security.NodePrincipal;
import fabric.messages.Message;
import fabric.net.UnreachableNodeException;

public class GetPrincipalMessage extends
    InterWorkerMessage<GetPrincipalMessage.Response> {

  public static class Response implements Message.Response {
    public final NodePrincipal principal;

    public Response(NodePrincipal principal) {
      this.principal = principal;
    }

    Response(DataInput in) throws IOException {
      Store store = Worker.getWorker().getStore(in.readUTF());
      this.principal = new NodePrincipal._Proxy(store, in.readLong());
    }

    public void write(DataOutput out) throws IOException {
      out.writeUTF(principal.$getStore().name());
      out.writeLong(principal.$getOnum());
    }
  }

  public GetPrincipalMessage() {
    super(MessageType.GET_PRINCIPAL);
  }

  public GetPrincipalMessage(DataInput in) {
    this();
  }

  @Override
  public Response dispatch(MessageHandlerThread handler) {
    return handler.handle(this);
  }

  public Response send(RemoteWorker remoteWorker)
      throws UnreachableNodeException {
    try {
      return super.send(remoteWorker, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from worker.", e);
    }
  }

  @Override
  public Response response(RemoteWorker worker, DataInput in)
      throws IOException {
    return new Response(in);
  }

  @Override
  public void write(DataOutput out) {
  }

}
