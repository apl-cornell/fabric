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

import java.io.*;
import java.lang.reflect.Method;

import fabric.worker.remote.RemoteCallException;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.UpdateMap;
import fabric.worker.remote.MessageHandlerThread;
import fabric.common.TransactionID;
import fabric.common.Util;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.security.Principal;
import fabric.lang.Object._Proxy;
import fabric.messages.Message;
import fabric.net.UnreachableNodeException;

public class RemoteCallMessage extends
    InterWorkerMessage<RemoteCallMessage.Response> {

  public final TransactionID tid;
  public final UpdateMap updateMap;
  public final Class<?> receiverType;
  public final _Proxy receiver;
  public final String methodName;
  public final Class<?>[] parameterTypes;
  public final Object[] args;

  public static class Response implements Message.Response {
    public final Object result;
    public final UpdateMap updateMap;

    public Response(Object result, UpdateMap updateMap) {
      this.result = result;
      this.updateMap = updateMap;
    }

    /**
     * Deserialization constructor.
     * 
     * @param worker
     *          The worker from which the response is being read.
     * @param in
     *          The input stream from which to read the response.
     * @throws ClassNotFoundException
     */
    Response(RemoteWorker worker, DataInput in) throws IOException,
        RemoteCallException {
      if (in.readBoolean()) {
        this.result = readRef(fabric.lang.Object.class, in);
      } else {
        byte[] buf = new byte[in.readInt()];
        in.readFully(buf);

        try {
          this.result = Util.deserialize(buf);
        } catch (ClassNotFoundException e) {
          throw new RemoteCallException(e);
        }
      }

      if (in.readBoolean())
        updateMap = new UpdateMap(in);
      else updateMap = null;
    }

    public void write(DataOutput out) throws IOException {
      out.writeBoolean(result instanceof _Proxy);
      if (result instanceof _Proxy)
        writeRef((_Proxy) result, out);
      else {
        byte[] buf = Util.serialize(result);
        out.writeInt(buf.length);
        out.write(buf);
      }

      out.writeBoolean(updateMap != null);
      if (updateMap != null) updateMap.write(out);
    }
  }

  /**
   * @param tid
   *          The identifier for the transaction in which the remote call is
   *          being made.
   * @param receiver
   *          The object that is receiving the call.
   * @param methodName
   *          The name of the method to be called.
   * @param args
   *          The arguments to the method.
   */
  public RemoteCallMessage(TransactionID tid, UpdateMap updateMap,
      Class<?> receiverType, _Proxy receiver, String methodName,
      Class<?>[] parameterTypes, Object[] args) {
    super(MessageType.REMOTE_CALL);

    if (parameterTypes == null ? args != null
        : parameterTypes.length != args.length)
      throw new IllegalArgumentException();

    this.tid = tid;
    this.updateMap = updateMap;
    this.receiverType = receiverType;
    this.receiver = receiver;
    this.methodName = methodName;
    this.parameterTypes = parameterTypes;
    this.args = args;
  }

  /**
   * Deserialization constructor.
   */
  @SuppressWarnings("unchecked")
  public RemoteCallMessage(DataInput in) throws IOException,
      ClassNotFoundException {
    super(MessageType.REMOTE_CALL);

    if (in.readBoolean())
      this.tid = new TransactionID(in);
    else this.tid = null;

    if (in.readBoolean())
      this.updateMap = new UpdateMap(in);
    else this.updateMap = null;

    byte[] buf = new byte[in.readInt()];
    in.readFully(buf);

    ObjectInputStream ois =
        new ObjectInputStream(new ByteArrayInputStream(buf));

    this.receiverType = (Class<? extends _Proxy>) ois.readObject();
    this.receiver = readRef(receiverType, ois);

    this.methodName = ois.readUTF();
    this.parameterTypes = new Class<?>[ois.readInt()];
    this.args = new Object[parameterTypes.length];

    for (int i = 0; i < args.length; i++) {
      parameterTypes[i] = (Class<?>) ois.readObject();
      if (ois.readBoolean())
        args[i] = readRef(parameterTypes[i], ois);
      else args[i] = ois.readObject();
    }
  }

  @Override
  public Response dispatch(MessageHandlerThread handler) throws RemoteCallException,
      ProtocolError {
    return handler.handle(this);
  }

  public Response send(RemoteWorker worker) throws UnreachableNodeException,
      RemoteCallException {
    try {
      return super.send(worker, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (RemoteCallException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from worker " + worker.name,
          e);
    }
  }

  @Override
  public Response response(RemoteWorker c, DataInput in) throws IOException,
      RemoteCallException {
    return new Response(c, in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeBoolean(tid != null);
    if (tid != null) tid.write(out);

    out.writeBoolean(updateMap != null);
    if (updateMap != null) updateMap.write(out);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(receiverType);
    writeRef(receiver, oos);

    oos.writeUTF(methodName);
    oos.writeInt(args == null ? 0 : args.length);

    if (args != null) {
      for (int i = 0; i < args.length; i++) {
        oos.writeObject(parameterTypes[i]);
        if (args[i] instanceof _Proxy) {
          oos.writeBoolean(true);
          writeRef((_Proxy) args[i], oos);
        } else {
          oos.writeBoolean(false);
          oos.writeObject(args[i]);
        }
      }
    }

    oos.flush();
    baos.flush();

    byte[] buf = baos.toByteArray();
    out.writeInt(buf.length);
    out.write(buf);
  }

  public Method getMethod() throws SecurityException, NoSuchMethodException {
    Class<?>[] mangledParamTypes = new Class<?>[parameterTypes.length + 1];
    mangledParamTypes[0] = Principal.class;
    for (int i = 0; i < parameterTypes.length; i++)
      mangledParamTypes[i + 1] = parameterTypes[i];

    // Get the receiver's _Proxy class.
    Class<?> proxyType = null;
    for (Class<?> c : receiverType.getClasses()) {
      if (c.getSimpleName().equals("_Proxy")) {
        proxyType = c;
        break;
      }
    }
    
    if (proxyType == null) {
      throw new NoSuchMethodException(
          "Remote method call on non-Fabric object: " + receiverType);
    }

    return proxyType.getMethod(methodName + "_remote", mangledParamTypes);
  }

}
