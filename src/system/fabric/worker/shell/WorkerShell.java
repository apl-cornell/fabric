package fabric.worker.shell;

import java.io.*;
import java.util.*;

import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;
import jline.Terminal;
import fabric.common.exceptions.InternalError;
import fabric.worker.Worker;

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

  private static abstract class CommandHandler {
    private final String params;
    private final String usage;

    public CommandHandler(String usage) {
      this(null, usage);
    }

    public CommandHandler(String params, String usage) {
      this.params = params;
      this.usage = usage;
    }

    public abstract void handle(List<String> args) throws HandlerException;
  }

  private static class HandlerException extends Exception {
    public HandlerException(String message, Throwable cause) {
      super(message, cause);
    }

    public HandlerException(String message) {
      super(message);
    }
  }

  /**
   * Creates an interactive shell for the given worker that reads from System.in
   * and outputs to System.out.
   */
  public WorkerShell(Worker worker) throws IOException {
    this(worker, new ConsoleReader(System.in,
        new OutputStreamWriter(System.out)), System.out);
  }

  protected WorkerShell(Worker worker, ConsoleReader cr, PrintStream out) {
    this(worker, new ConsoleReaderInputStream(cr), out, true);
    cr.setDefaultPrompt("\n" + worker.config.name + "> ");
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
    this.handlers = new TreeMap<String, CommandHandler>();
    this.defaultHandler =
        new CommandHandler("<APP> [ARG]...",
            "Executes the Fabric application APP in this worker, passing ARGs "
                + "as command-line arguments.") {
          @Override
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

    this.handlers.put("fabc", new CommandHandler("Compiles a Fabric program.") {
      @Override
      public void handle(List<String> args) throws HandlerException {
        //TODO: should we pass in the correct i/o streams?
        fabric.Main.compile_from_shell(args, System.in, System.out);
      }
    });

    this.handlers.put("filc", new CommandHandler("Compiles a FabIL program.") {
      @Override
      public void handle(List<String> args) throws HandlerException {
        throw new HandlerException("Not implemented yet!");
      }
    });

    this.handlers.put("help", new CommandHandler("[CMD]", "Displays a help "
        + "message for CMD.") {
      final int SCREEN_WIDTH = 76;
      final int INDENT = 2;

      @Override
      public void handle(List<String> args) {
        String argument = null;
        CommandHandler handler = null;
        if (args.size() > 0) {
          argument = args.iterator().next();
          handler = WorkerShell.this.handlers.get(argument);
        }

        final PrintStream out = WorkerShell.this.out;
        if (handler != null) {
          // Print command usage template.
          out.print("Usage: " + argument);
          if (handler.params == null)
            out.println();
          else out.println(" " + handler.params);
          out.println();

          // Print command usage message.
          printSpaces(out, INDENT);
          int cur = INDENT;
          StringTokenizer st = new StringTokenizer(handler.usage);
          while (st.hasMoreTokens()) {
            String s = st.nextToken();
            if (cur + s.length() > SCREEN_WIDTH) {
              out.println();
              printSpaces(out, INDENT);
              cur = INDENT;
            }

            out.print(s);
            cur += s.length();
            if (st.hasMoreTokens()) {
              if (cur + 1 > SCREEN_WIDTH) {
                out.println();
                printSpaces(out, INDENT);
                cur = INDENT;
              } else {
                out.print(" ");
                cur++;
              }
            }
          }

          out.println();
        } else {
          // Show a list of commands.
          if (argument != null) {
            out.println("Unknown command: " + argument);
            out.println();
          }
          showCommands();

          out.println();
          out.println("Use 'help [CMD]' to see help for a specific command.");
        }
      }

      /**
       * Displays a list of commands to the user.
       */
      protected void showCommands() {
        final int PADDING = 2; // Amount of space between commands.
        final int MAX_NUM_COLS =
            (SCREEN_WIDTH + PADDING - INDENT) / (PADDING + 1);
        Set<String> commandNames = WorkerShell.this.handlers.keySet();

        int numRows = commandNames.size() / MAX_NUM_COLS;
        if (commandNames.size() % MAX_NUM_COLS != 0) numRows++;

        // Find a numRows that will fit.
        int numCols = commandNames.size() / numRows;
        if (commandNames.size() % numRows != 0) numCols++;
        int[] colWidth = new int[numCols];
        L: while (numRows != commandNames.size()) {
          int width = INDENT - PADDING;
          int curColWidth = 0;
          int commandNum = 0;
          for (String name : commandNames) {
            if (name.length() > curColWidth) {
              curColWidth = name.length();
              if (width + curColWidth + PADDING > SCREEN_WIDTH) {
                // Too wide.
                numRows++;
                numCols = commandNames.size() / numRows;
                if (commandNames.size() % numRows != 0) numCols++;
                colWidth = new int[numCols];
                continue L;
              }
            }

            commandNum++;
            if (commandNum % numRows == 0) {
              // New column.
              width += curColWidth + PADDING;
              colWidth[commandNum / numRows - 1] = curColWidth;
              curColWidth = 0;
            }
          }
          break;
        }

        // Format the output.
        StringBuffer[] output = new StringBuffer[numRows];
        for (int i = 0; i < numRows; i++) {
          output[i] = new StringBuffer();
          appendSpaces(output[i], INDENT);
        }
        int commandNum = 0;
        int colNum = 0;
        for (String name : commandNames) {
          final int rowNum = commandNum % numRows;
          output[rowNum].append(name);
          int padding = 0;
          if (name.length() < colWidth[colNum])
            padding += colWidth[colNum] - name.length();
          if (colNum * numRows < commandNames.size()) padding += PADDING;
          appendSpaces(output[rowNum], padding);

          commandNum++;
          if (rowNum == numRows - 1) colNum++;
        }

        // Output.
        WorkerShell.this.out.println("Available commands:");
        for (StringBuffer line : output)
          WorkerShell.this.out.println(line);
      }

      private void printSpaces(PrintStream out, int n) {
        while (n-- > 0)
          out.print(' ');
      }

      private void appendSpaces(StringBuffer sb, int n) {
        while (n-- > 0)
          sb.append(' ');
      }
    });

    this.handlers.put("exit", new CommandHandler("Exits the worker shell.") {
      @Override
      public void handle(List<String> args) {
        // Exiting is handled specially in the shell loop.
        throw new InternalError();
      }
    });
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
   * @return the parsed command, or null if there are no more commands.
   */
  private List<String> prompt(List<String> command) throws IOException {
    command.clear();

    while (true) {
      int token = in.nextToken();
      switch (token) {
      case StreamTokenizer.TT_EOF:
        if (command.isEmpty()) {
          if (interactive) out.println("exit");
          return null;
        }
        //$FALL-THROUGH$
      case StreamTokenizer.TT_EOL:
      case ';':
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
