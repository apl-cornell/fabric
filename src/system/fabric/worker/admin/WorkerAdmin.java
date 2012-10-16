package fabric.worker.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import fabric.common.Threading;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.UsageError;
import fabric.worker.Worker;
import fabric.worker.shell.CommandSource;
import fabric.worker.shell.TokenizedCommandSource;
import fabric.worker.shell.WorkerShell;

public class WorkerAdmin {
  /**
   * Connects to a remote worker and executes commands via its admin port.
   * 
   * @throws WorkerNotRunningException
   *           if no worker is listening on the admin port.
   */
  public static void connect(int adminPort, String[] cmd) throws UsageError,
      WorkerNotRunningException {
    Socket socket = null;
    try {
      socket = new Socket((String) null, adminPort);
    } catch (ConnectException e) {
      throw new WorkerNotRunningException();
    } catch (IOException e) {
      throw new InternalError(e);
    }

    // Successfully connected. Ensure we have commands to run.
    if (cmd == null) {
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
        }
      }
      throw new UsageError(
          "Worker already running. Must specify worker commands to execute.");
    }

    try {
      // Send our commands over.
      DataOutputStream out =
          new DataOutputStream(new BufferedOutputStream(
              socket.getOutputStream()));
      out.writeInt(cmd.length);
      for (String arg : cmd) {
        out.writeUTF(arg);
      }
      out.flush();

      // Wait for the worker to finish running our commands.
      socket.getInputStream().read();
      socket.close();
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

  /**
   * Listens on the admin port for commands.
   */
  public static void listen(int adminPort, Worker worker) {
    // Bind to the admin port.
    ServerSocket server;
    try {
      server = new ServerSocket(adminPort, 50, InetAddress.getByName(null));
    } catch (IOException e) {
      throw new InternalError(e);
    }

    // Spawn off a new thread to receive admin connections.
    new Acceptor(server, worker);
  }

  private static class Acceptor extends Thread {
    private final ServerSocket server;
    private final Worker worker;

    Acceptor(ServerSocket server, Worker worker) {
      super("connection handler for worker admin port");
      setDaemon(true);
      this.server = server;
      this.worker = worker;
      start();
    }

    @Override
    public void run() {
      while (true) {
        try {
          handleConnection(server.accept());
        } catch (IOException e) {
          throw new InternalError(e);
        }
      }
    }

    private void handleConnection(final Socket socket) {
      Threading.getPool().submit(
          new Threading.NamedRunnable("Worker admin connection handler") {
            @Override
            protected void runImpl() {
              try {
                DataInput in =
                    new DataInputStream(new BufferedInputStream(socket
                        .getInputStream()));
                // Read commands from network.
                String[] cmd = new String[in.readInt()];
                for (int i = 0; i < cmd.length; i++) {
                  cmd[i] = in.readUTF();
                }

                // Hand the commands off to the worker shell to execute.
                CommandSource commandSource = new TokenizedCommandSource(cmd);
                new WorkerShell(worker, commandSource).run();

                // Write a byte to indicate we're done running.
                socket.getOutputStream().write(0);
                socket.getOutputStream().flush();
                socket.close();
              } catch (IOException e) {
                throw new InternalError(e);
              }
            }
          });
    }
  }
}
