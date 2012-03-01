package fabric.worker.shell;

import java.util.List;

abstract class CommandHandler {
  /**
   * A description of the parameters supported by this command.
   */
  final String params;
  
  /**
   * A description of how to use this command.
   */
  final String usage;

  public CommandHandler(String usage) {
    this(null, usage);
  }

  public CommandHandler(String params, String usage) {
    this.params = params;
    this.usage = usage;
  }

  /**
   * Executes the command with the given arguments.
   */
  public abstract void handle(List<String> args) throws HandlerException;
}