package fabric.worker;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;
import jline.Terminal;

import fabric.common.exceptions.InternalError;

public class WorkerShell {
  protected final Worker worker;
  protected final StreamTokenizer in;
  protected final PrintStream out;
  protected final PrintStream err;
  protected final boolean interactive;
  protected final Map<String, CommandHandler> handlers;
  protected final CommandHandler defaultHandler;

  static {
    Terminal.setupTerminal();
  }

  private static interface CommandHandler {
    void handle(List<String> args) throws HandlerException;
  }

  private static class HandlerException extends Exception {
    public HandlerException(String message, Throwable cause) {
      super(message, cause);
    }

    public HandlerException(String message) {
      super(message);
    }
  }

  public WorkerShell(Worker worker) throws IOException {
    this(worker, new ConsoleReaderInputStream(new ConsoleReader(System.in,
        new OutputStreamWriter(System.out))), System.out, true);
  }

  public WorkerShell(Worker worker, File in, PrintStream out)
      throws FileNotFoundException {
    this(worker, new BufferedInputStream(new FileInputStream(in)), out, false);
  }

  protected WorkerShell(Worker worker, InputStream in, PrintStream out,
      boolean consoleMode) {
    this.worker = worker;
    this.in = new StreamTokenizer(new InputStreamReader(in));
    this.out = out;
    this.err = System.err;
    this.interactive = consoleMode;

    // Configure the input reader.
    this.in.commentChar('#');
    this.in.eolIsSignificant(true);
    this.in.slashSlashComments(false);
    this.in.slashStarComments(false);
    this.in.ordinaryChars('0', '9');
    this.in.ordinaryChar('-');
    this.in.wordChars('0', '9');
    this.in.wordChars('-', '-');
    this.in.wordChars(':', ':');
    this.in.wordChars('/', '/');

    // Set up the command handlers.
    this.handlers = new HashMap<String, CommandHandler>();
    this.defaultHandler = new CommandHandler() {
      public void handle(List<String> args) throws HandlerException {
        if (args.size() == 0) {
          throw new HandlerException("run: missing main class");
        }

        // Run a Fabric program.
        String mainClassName = args.get(0);
        String[] appArgs = new String[args.size() - 1];
        for (int i = 0; i < appArgs.length; i++) {
          appArgs[i] = args.get(i + 1);
        }

        try {
          WorkerShell.this.worker.runFabricApp(mainClassName, appArgs);
        } catch (Throwable e) {
          throw new HandlerException("Exception encountered while running "
              + mainClassName, e);
        }
      }
    };

    this.handlers.put("run", defaultHandler);
  }

  public void run() {
    while (true) {
      List<String> commandLine = new ArrayList<String>();
      try {
        commandLine = prompt(commandLine);
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }

      if (commandLine == null) return;

      if (commandLine.size() == 0) continue;

      String command = commandLine.get(0);
      List<String> args = commandLine.subList(1, commandLine.size());
      if (command.equals("exit")) return;

      CommandHandler handler = handlers.get(command);
      if (handler == null) {
        handler = defaultHandler;
        args = commandLine;
      }

      try {
        handler.handle(args);
      } catch (HandlerException e) {
        reportError(e.getMessage());
        if (e.getCause() != null) e.getCause().printStackTrace(err);
        if (!interactive) return;
      }
    }
  }

  private void reportError(String message) {
    if (!interactive) {
      err.print("Error on line " + in.lineno() + ": ");
    }

    err.println(message);
  }

  /**
   * Prompts the user for a command, reads the command, parses it, and returns
   * the result.
   * 
   * @param command
   *          a list into which the command will be parsed.
   * @return the parsed command, or null if there are no more comands.
   */
  private List<String> prompt(List<String> command) throws IOException {
    out.println();
    out.print(worker.config.name + "> ");
    out.flush();

    command.clear();

    while (true) {
      int token = in.nextToken();
      switch (token) {
      case StreamTokenizer.TT_EOF:
        if (command.isEmpty()) return null;
        //$FALL-THROUGH$
      case StreamTokenizer.TT_EOL:
        return command;

      case StreamTokenizer.TT_NUMBER:
        throw new InternalError("Tokenizer returned unexpected number.");

      case StreamTokenizer.TT_WORD:
      case '\'':
      case '"':
        command.add(in.sval);
        continue;

      default:
        reportError("Unexpected character: '" + (char) token + "'");
        command.clear();

        if (interactive) {
          // Read to end of line.
          while (token != StreamTokenizer.TT_EOF
              && token != StreamTokenizer.TT_EOL) {
            token = in.nextToken();
          }
        }

        return interactive ? command : null;
      }
    }
  }
}
