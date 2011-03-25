package fabric.worker.shell;

import java.util.List;

/**
 * A chain of command sources.
 */
public class ChainedCommandSource extends CommandSource {
  protected final CommandSource[] sources;
  protected int curSourceNum;
  protected final boolean exitOnAnyError;

  public ChainedCommandSource(CommandSource... sources) {
    this(false, sources);
  }

  public ChainedCommandSource(boolean exitOnAnyError, CommandSource... sources) {
    this.sources = sources;
    this.curSourceNum = 0;
    this.exitOnAnyError = exitOnAnyError;
  }

  @Override
  public List<String> getNextCommand(List<String> buf) {
    while (curSourceNum < sources.length) {
      List<String> result = sources[curSourceNum].getNextCommand(buf);
      if (result != null) return result;

      // Current source has dried up. Move to the next one.
      curSourceNum++;
    }

    return null;
  }

  @Override
  public boolean reportError(String message) {
    boolean curSourceExited = sources[curSourceNum].reportError(message);
    if (curSourceExited) curSourceNum++;
    return exitOnAnyError;
  }
}
