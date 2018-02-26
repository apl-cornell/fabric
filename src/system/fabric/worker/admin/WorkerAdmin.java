package fabric.worker.admin;

import static fabric.common.Logging.NETWORK_CONNECTION_LOGGER;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;

import fabric.common.Threading;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NotImplementedException;
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
   */
  public static void connect(int adminPort, String[] cmd)
      throws UsageError, WorkerNotRunningException {
    try (Socket socket = new Socket((String) null, adminPort)) {
      // Successfully connected. Ensure we have commands to run.
      if (cmd == null) {
        throw new UsageError(
            "Worker already running. Must specify worker commands to execute.");
      }

      // Send our commands over.
      DataOutputStream out = new DataOutputStream(
          new BufferedOutputStream(socket.getOutputStream()));
      out.writeInt(cmd.length);
      for (String arg : cmd) {
        out.writeUTF(arg);
      }
      out.flush();

      // Wait for the worker to finish running our commands.
      socket.getInputStream().read();
    } catch (ConnectException e) {
      throw new WorkerNotRunningException();
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

  /**
   * Listens on the admin port for commands.
   */
  public static void listen(int adminPort, Worker worker) {
    // Spawn off a new thread to receive admin connections.
    new Acceptor(adminPort, worker);
  }

  private static class Acceptor extends Thread {
    private final int adminPort;
    private final Worker worker;

    Acceptor(int adminPort, Worker worker) {
      super("connection handler for worker admin port");
      setDaemon(true);
      this.adminPort = adminPort;
      this.worker = worker;
      start();
    }

    @Override
    public void run() {
      try (ServerSocket server = new ServerSocket()) {
        server.setReuseAddress(true);
        server.bind(new InetSocketAddress(InetAddress.getByName(null), adminPort),
            50);
        while (true) {
          try {
            handleConnection(server.accept());
          } catch (IOException e) {
            throw new InternalError(e);
          }
        }
      } catch (IOException e) {
        System.err.println("WorkerAdmin had an IOException:");
        e.printStackTrace();
      }
      System.exit(1);
    }

    private void handleConnection(final Socket socket) {
      Threading.getPool().submit(
          new Threading.NamedRunnable("Worker admin connection handler") {
            @Override
            protected void runImpl() {
              try {
                DataInput in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
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
              } catch (SocketException e) {
                if ("Connection reset".equalsIgnoreCase(e.getMessage())) {
                  NETWORK_CONNECTION_LOGGER.log(Level.WARNING,
                      "WorkerAdmin connection reset ({0})",
                      socket.getRemoteSocketAddress());
                  return;
                }

                throw new NotImplementedException(e);
              } catch (EOFException e) {
                NETWORK_CONNECTION_LOGGER.log(Level.WARNING,
                    "WorkerAdmin connection closed ({0})",
                    socket.getRemoteSocketAddress());
                return;
              } catch (IOException e) {
                throw new InternalError(e);
              }
            }
          });
    }
  }
}
