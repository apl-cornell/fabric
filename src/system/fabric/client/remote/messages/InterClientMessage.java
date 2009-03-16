package fabric.client.remote.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.remote.RemoteClient;
import fabric.common.exceptions.InternalError;
import fabric.lang.Object._Proxy;
import fabric.messages.Message;
import fabric.messages.Message.Response;

public abstract class InterClientMessage<R extends Response> extends
    Message<RemoteClient, R> {

  public InterClientMessage(MessageType messageType) {
    super(messageType);
  }

  /**
   * Used for passing object references between clients.
   */
  public static void writeRef(_Proxy ref, DataOutput out) throws IOException {
    out.writeUTF(ref.$getCore().name());
    out.writeLong(ref.$getOnum());
  }

  /**
   * Used for passing object references between clients.
   * 
   * @param type
   *          The type of the reference being read. This must be the interface
   *          corresponding to the Fabric type, and not the _Proxy or _Impl
   *          classes.
   */
  @SuppressWarnings("unchecked")
  public static _Proxy readRef(Class<?> type, DataInput in) throws IOException {
    Core core = Client.getClient().getCore(in.readUTF());
    Class<? extends _Proxy> proxyType = null;
    for (Class<?> c : type.getClasses()) {
      if (c.getSimpleName().equals("_Proxy")) {
        proxyType = (Class<? extends _Proxy>) c;
        break;
      }
    }

    if (proxyType == null)
      throw new InternalError("Unable to find proxy class for " + type);

    try {
      Constructor<? extends _Proxy> constructor =
          proxyType.getConstructor(Core.class, long.class);

      return constructor.newInstance(core, in.readLong());
    } catch (SecurityException e) {
      throw new InternalError(e);
    } catch (NoSuchMethodException e) {
      throw new InternalError(e);
    } catch (IllegalArgumentException e) {
      throw new InternalError(e);
    } catch (InstantiationException e) {
      throw new InternalError(e);
    } catch (IllegalAccessException e) {
      throw new InternalError(e);
    } catch (InvocationTargetException e) {
      throw new InternalError(e);
    }
  }
}
