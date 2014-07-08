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
package fabric.messages;

import java.io.*;
import java.security.cert.Certificate;

import fabric.common.Util;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;
import fabric.store.MessageHandlerThread;
import fabric.worker.RemoteStore;
import fabric.worker.debug.Timing;

/**
 * A request to get the certificate chain that certifies a store's public SSL
 * key.
 */
public class GetCertificateChainMessage extends
    Message<RemoteStore, GetCertificateChainMessage.Response> {

  public static class Response implements Message.Response {
    public final Certificate[] certificateChain;

    public Response(Certificate[] certificateChain) {
      this.certificateChain = certificateChain;
    }

    /**
     * Deserialization constructor, used by the worker.
     * 
     * @param node
     *          The node from which the response is being read.
     * @param in
     *          the input stream from which to read the response.
     */
    Response(RemoteNode node, DataInput in) throws IOException {
      byte[] buf = new byte[in.readInt()];
      in.readFully(buf);

      ObjectInputStream ois =
          new ObjectInputStream(new ByteArrayInputStream(buf));
      Certificate[] certificateChain;
      try {
        certificateChain = (Certificate[]) ois.readObject();
      } catch (ClassNotFoundException e) {
        certificateChain = null;
      }

      this.certificateChain = certificateChain;
    }

    public void write(DataOutput out) throws IOException {
      byte[] buf = Util.serialize(certificateChain);
      out.writeInt(buf.length);
      out.write(buf);
    }
  }

  public GetCertificateChainMessage() {
    super(MessageType.GET_CERT_CHAIN);
  }

  /**
   * Deserialization constructor.
   */
  protected GetCertificateChainMessage(DataInput in) {
    this();
  }
  
  @Override
  public Response dispatch(MessageHandlerThread w) {
    return w.handle(this);
  }
  
  public Response send(RemoteStore store) throws UnreachableNodeException {
    try {
      Timing.STORE.begin();
      return send(store, false);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from store.", e);
    } finally {
      Timing.STORE.end();
    }
  }

  @Override
  public Response response(RemoteStore node, DataInput in) throws IOException {
    return new Response(node, in);
  }

  @Override
  public void write(DataOutput out) {
  }
}
