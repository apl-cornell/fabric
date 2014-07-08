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

import fabric.common.TransactionID;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.messages.Message;
import fabric.net.UnreachableNodeException;
import fabric.worker.remote.MessageHandlerThread;
import fabric.worker.remote.RemoteWorker;

/**
 * Represents a request to check staleness of data in a transaction.
 */
public class StalenessCheckMessage extends
    InterWorkerMessage<StalenessCheckMessage.Response> {
  
  public final TransactionID tid;
  
  public static class Response implements Message.Response {
    /**
     * True iff stale objects were found.
     */
    public final boolean result;
    
    public Response(boolean result) {
      this.result = result;
    }
    
    Response(DataInput in) throws IOException {
      this.result = in.readBoolean();
    }
    
    public void write(DataOutput out) throws IOException {
      out.writeBoolean(result);
    }
  }

  public StalenessCheckMessage(TransactionID tid) {
    super(MessageType.INTERWORKER_STALENESS_CHECK);
    this.tid = tid;
  }
  
  public StalenessCheckMessage(DataInput in) throws IOException {
    this(new TransactionID(in));
  }
  
  @Override
  public Response dispatch(MessageHandlerThread handler) throws ProtocolError {
    return handler.handle(this);
  }
  
  public Response send(RemoteWorker remoteWorker) {
    try {
      return super.send(remoteWorker, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from worker.", e);
    }
  }

  @Override
  public Response response(RemoteWorker node, DataInput in) throws IOException {
    return new Response(in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    tid.write(out);
  }
}
