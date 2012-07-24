package fabric.worker.shell;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.List;

import fabric.common.exceptions.InternalError;

/**
 * An InputStream-based source of commands.
 */
class StreamCommandSource extends CommandSource {

  final boolean exitOnError;
  final StreamTokenizer in;

  public StreamCommandSource(InputStream in) {
    this(in, true);
  }

  public StreamCommandSource(InputStream in, boolean exitOnError) {
    this.in =
        new StreamTokenizer(new InputStreamReader(new BufferedInputStream(in)));
    this.exitOnError = exitOnError;

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
  }

  @Override
  public List<String> getNextCommand(List<String> buf) {
    buf.clear();

    try {
      while (true) {
        int token = in.nextToken();
        switch (token) {
        case StreamTokenizer.TT_EOF:
          if (buf.isEmpty()) return null;
          //$FALL-THROUGH$
        case StreamTokenizer.TT_EOL:
        case ';':
          return buf;

        case StreamTokenizer.TT_NUMBER:
          throw new InternalError("Tokenizer returned unexpected number.");

        case StreamTokenizer.TT_WORD:
        case '\'':
        case '"':
          buf.add(in.sval);
          continue;

        default:
          reportError("Unexpected character: '" + (char) token + "'");
          handleSyntaxError();
          if (exitOnError) return null;

          buf.clear();
          return buf;
        }
      }
    } catch (IOException e) {
      e.printStackTrace(err);
      return null;
    }
  }

  /**
   * @throws IOException
   */
  protected void handleSyntaxError() throws IOException {
  }

  @Override
  public boolean reportError(String message) {
    err.println("Error on line " + in.lineno() + ": " + message);
    return exitOnError;
  }

}
