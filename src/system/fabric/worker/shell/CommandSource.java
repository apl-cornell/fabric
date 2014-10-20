package fabric.worker.shell;

import java.io.PrintStream;
import java.util.List;

/**
 * Encapsulates a source of commands for the worker shell.
 */
public abstract class CommandSource {
  protected final PrintStream err;

  public CommandSource() {
    this(System.err);
  }

  public CommandSource(PrintStream err) {
    this.err = err;
  }

  /**
   * @param command
   *          a list into which the command will be parsed.
   * @return the parsed command, or null if there are no more commands.
   */
  public abstract List<String> getNextCommand(List<String> buf);

  /**
   * Reports an error back to the user.
   *
   * @return whether the shell should exit.
   */
  public abstract boolean reportError(String message);

  /**
   * Reports an error back to the user.
   *
   * @return whether the shell should exit.
   */
  public boolean reportError(HandlerException e) {
    boolean result = reportError(e.getMessage());
    if (e.getCause() != null) e.getCause().printStackTrace(err);
    return result;
  }
}
