package fabric.worker.shell;

import java.util.List;

/**
 * A source of commands that has been pre-tokenized.
 */
public class TokenizedCommandSource extends CommandSource {
  protected int nextToken;
  protected final String[] tokens;
  
  /**
   * @param tokens an array wherein each element is an individual token.
   */
  public TokenizedCommandSource(String[] tokens) {
    this.tokens = tokens;
    this.nextToken = 0;
  }

  @Override
  public List<String> getNextCommand(List<String> buf) {
    if (nextToken >= tokens.length) return null;
    
    buf.clear();
    while (nextToken < tokens.length) {
      String token = tokens[nextToken++];
      if (";".equals(token)) return buf;
      buf.add(token);
    }
    
    return buf;
  }

  @Override
  public boolean reportError(String message) {
    err.println("Error: " + message);
    return true;
  }

}
