package fabric.worker.shell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.exceptions.InternalError;
import fabric.worker.Worker;

public class WorkerShell {
  protected final Worker worker;
  protected final CommandSource commandSource;
  protected final PrintStream out;
  protected final Map<String, CommandHandler> handlers;
  protected final CommandHandler defaultHandler;

  /**
   * Creates an interactive shell for the given worker.
   */
  public WorkerShell(Worker worker) throws IOException {
    this(worker, new InteractiveCommandSource(worker));
  }

  public WorkerShell(Worker worker, File in) throws FileNotFoundException {
    this(worker, new StreamCommandSource(new FileInputStream(in)));
  }

  public WorkerShell(Worker worker, CommandSource commandSource) {
    this.worker = worker;
    this.commandSource = commandSource;

    this.out = System.out;

    // Set up the command handlers.
    this.handlers = new TreeMap<>();
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
      public void handle(List<String> args) {
        // TODO: should we pass in the correct i/o streams?
        fabric.Main.compileFromShell(args, System.in, System.out);
      }
    });

    this.handlers.put("filc", new CommandHandler("Compiles a FabIL program.") {
      @Override
      public void handle(List<String> args) throws HandlerException {
        throw new HandlerException("Not implemented yet!");
      }
    });

    this.handlers.put("java", new CommandHandler("<APP> [ARG]...", "Executes "
        + "the Java application APP in this worker, passing ARGs as "
        + "command-line arguments.") {
      @Override
      public void handle(List<String> args) throws HandlerException {
        if (args.size() == 0) {
          throw new HandlerException("java: missing main class");
        }

        // Run a Java program.
        String mainClassName = args.get(0);
        String[] appArgs = new String[args.size() - 1];
        for (int i = 0; i < appArgs.length; i++) {
          appArgs[i] = args.get(i + 1);
        }

        try {
          WorkerShell.this.worker.runJavaApp(mainClassName, appArgs);
        } catch (Throwable e) {
          throw new HandlerException("Exception encountered while running "
              + mainClassName, e);
        }
      }
    });

    this.handlers.put("time", new CommandHandler(
        "Times a worker shell command.") {
      @Override
      public void handle(List<String> commandLine) throws HandlerException {
        String command = commandLine.get(0);
        List<String> args = commandLine.subList(1, commandLine.size());
        if (command.equals("exit")) return;

        CommandHandler handler = handlers.get(command);
        if (handler == null) {
          handler = defaultHandler;
          args = commandLine;
        }
        final long startTime = System.currentTimeMillis();
        try {
          handler.handle(args);
        } finally {
          System.out.println("   Run time: "
              + (System.currentTimeMillis() - startTime) + " ms");
        }
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
    try {
      List<String> commandLine = new ArrayList<>();
      while (true) {
        Logging.TIMING_LOGGER.log(Level.INFO, "waiting for command");
        commandLine = commandSource.getNextCommand(commandLine);
        Logging.TIMING_LOGGER.log(Level.INFO, "{0}", commandLine);

        if (commandLine == null) return;

        if (commandLine.size() == 0) continue;

        String command = commandLine.get(0);
        List<String> args = commandLine.subList(1, commandLine.size());
        if (command.equals("exit")) {
          DummyCommandSource.signalToQuit();
          return;
        }

        CommandHandler handler = handlers.get(command);
        if (handler == null) {
          handler = defaultHandler;
          args = commandLine;
        }

        try {
          handler.handle(args);
        } catch (HandlerException e) {
          if (commandSource.reportError(e)) return;
        }
      }
    } finally {
      Logging.TIMING_LOGGER.log(Level.INFO, "command line terminating");
    }
  }
}
