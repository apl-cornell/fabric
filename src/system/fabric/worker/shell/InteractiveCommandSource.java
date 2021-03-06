package fabric.worker.shell;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StreamTokenizer;
import java.util.List;

import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;
import jline.Terminal;
import fabric.worker.Worker;

/**
 * An interactive console-based source of worker-shell commands.
 */
public class InteractiveCommandSource extends StreamCommandSource {

  final PrintStream out;

  static {
    Terminal.setupTerminal();
  }

  public InteractiveCommandSource(Worker worker) throws IOException {
    this(worker, new ConsoleReader(System.in,
        new OutputStreamWriter(System.out)));
  }

  public InteractiveCommandSource(Worker worker, ConsoleReader cr) {
    this(worker, cr, System.out);
  }

  public InteractiveCommandSource(Worker worker, ConsoleReader cr,
      PrintStream out) {
    super(new ConsoleReaderInputStream(cr), false);
    this.out = out;
    cr.setDefaultPrompt("\n" + worker.config.name + "> ");
  }

  @Override
  public List<String> getNextCommand(List<String> buf) {
    List<String> result = super.getNextCommand(buf);
    if (result == null) out.println("exit");
    return result;
  }

  @Override
  protected void handleSyntaxError() throws IOException {
    // Read to end of line.
    int token = StreamTokenizer.TT_WORD;
    while (token != StreamTokenizer.TT_EOF && token != StreamTokenizer.TT_EOL) {
      token = in.nextToken();
    }
  }

  @Override
  public boolean reportError(String message) {
    err.println("Error: " + message);
    return exitOnError;
  }
}
